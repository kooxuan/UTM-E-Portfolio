import streamlit as st
import numpy as np
import pandas as pd

st.title("Assignment 1 - Koo Xuan")
st.write("Sequence Alignment Web App [Global (Needleman–Wunsch) & Local (Smith–Waterman) Alignment]")

# INPUTS
col1, col2 = st.columns(2)

with col1:
    seq1 = st.text_input("Sequence 1: (eg:ACAGT )", "")
with col2:
    seq2 = st.text_input("Sequence 2: (eg:ACG )", "")

with col1:
    match_score = st.number_input("Match Score", value=1)
with col2:
    mismatch_score = st.number_input("Mismatch Score", value=-1)

gap_penalty = st.number_input("Gap Penalty", value=-2)

alignment_type = st.radio("Alignment Type:", ("Global Alignment", "Local Alignment"))

# UTILITY FUNCTION
def score(a, b, match, mismatch):
    return match if a == b else mismatch


# GLOBAL ALIGNMENT (NW)
def needleman_wunsch(s1, s2, match, mismatch, gap):
    m, n = len(s1), len(s2)
    M = np.zeros((m+1, n+1), dtype=int)
    trace = np.zeros((m+1, n+1), dtype=str)

    # initialization
    for i in range(1, m+1):
        M[i][0] = i * gap
        trace[i][0] = "U"
    for j in range(1, n+1):
        M[0][j] = j * gap
        trace[0][j] = "L"

    # fill matrix
    for i in range(1, m+1):
        for j in range(1, n+1):
            diag = M[i-1][j-1] + score(s1[i-1], s2[j-1], match, mismatch)
            up = M[i-1][j] + gap
            left = M[i][j-1] + gap
            
            best = max(diag, up, left)
            M[i][j] = best

            if best == diag:
                trace[i][j] = "D"
            elif best == up:
                trace[i][j] = "U"
            else:
                trace[i][j] = "L"

    # traceback
    aligned1, aligned2 = "", ""
    i, j = m, n
    path = [(i, j)]

    while i > 0 or j > 0:
        if trace[i][j] == "D":
            aligned1 = s1[i-1] + aligned1
            aligned2 = s2[j-1] + aligned2
            i -= 1
            j -= 1
        elif trace[i][j] == "U":
            aligned1 = s1[i-1] + aligned1
            aligned2 = "-" + aligned2
            i -= 1
        else:
            aligned1 = "-" + aligned1
            aligned2 = s2[j-1] + aligned2
            j -= 1
        path.append((i, j))

    return M, aligned1, aligned2, M[m][n], set(path)


# LOCAL ALIGNMENT (SW)
def smith_waterman(s1, s2, match, mismatch, gap):
    m, n = len(s1), len(s2)
    M = np.zeros((m+1, n+1), dtype=int)
    trace = np.zeros((m+1, n+1), dtype=str)

    max_val = 0
    max_pos = (0, 0)

    # fill matrix
    for i in range(1, m+1):
        for j in range(1, n+1):
            diag = M[i-1][j-1] + score(s1[i-1], s2[j-1], match, mismatch)
            up = M[i-1][j] + gap
            left = M[i][j-1] + gap

            best = max(0, diag, up, left)
            M[i][j] = best

            if best == 0:
                trace[i][j] = "0"
            elif best == diag:
                trace[i][j] = "D"
            elif best == up:
                trace[i][j] = "U"
            else:
                trace[i][j] = "L"

            if best > max_val:
                max_val = best
                max_pos = (i, j)

    # traceback
    aligned1, aligned2 = "", ""
    i, j = max_pos
    path = [(i, j)]

    while M[i][j] != 0:
        if trace[i][j] == "D":
            aligned1 = s1[i-1] + aligned1
            aligned2 = s2[j-1] + aligned2
            i -= 1
            j -= 1
        elif trace[i][j] == "U":
            aligned1 = s1[i-1] + aligned1
            aligned2 = "-" + aligned2
            i -= 1
        elif trace[i][j] == "L":
            aligned1 = "-" + aligned1
            aligned2 = s2[j-1] + aligned2
            j -= 1
        path.append((i, j))

    return M, aligned1, aligned2, max_val, set(path)


# RUN ALIGNMENT
if st.button("Run Alignment"):

    if alignment_type == "Global Alignment":
        M, a1, a2, final_score, path = needleman_wunsch(
            seq1, seq2, match_score, mismatch_score, gap_penalty
        )
    else:
        M, a1, a2, final_score, path = smith_waterman(
            seq1, seq2, match_score, mismatch_score, gap_penalty
        )

    col3, col4 = st.columns(2)
    with col3:
        st.subheader("Alignment Score")
        st.success(final_score)

    with col4:
        st.subheader("Aligned Sequences")
        st.code(a1)
        st.code(a2)

    # Matrix with highlight
    st.subheader("Alignment Matrix (Backtrace highlighted)")

    df = pd.DataFrame(M)

    # convert matrix to strings with highlight
    styled_df = df.style.apply(
        lambda x: ["background-color: yellow" if (x.name, i) in path else "" for i in range(len(x))],
        axis=1
    )

    st.dataframe(styled_df, use_container_width=True)

    st.write("Backtrace path coordinates:", path)
