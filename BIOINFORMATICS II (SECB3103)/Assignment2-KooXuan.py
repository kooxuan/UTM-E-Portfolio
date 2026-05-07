import streamlit as st
import time

# Distance Computations
def hamming_distance(s1, s2):
    if len(s1) != len(s2):
        return None
    return sum(c1 != c2 for c1, c2 in zip(s1, s2))


def edit_distance_dp(s1, s2):
    m, n = len(s1), len(s2)
    dp = [[0] * (n + 1) for _ in range(m + 1)]

    for i in range(m + 1):
        dp[i][0] = i
    for j in range(n + 1):
        dp[0][j] = j

    for i in range(1, m + 1):
        for j in range(1, n + 1):
            cost = 0 if s1[i - 1] == s2[j - 1] else 1
            dp[i][j] = min(
                dp[i - 1][j] + 1,    
                dp[i][j - 1] + 1,   
                dp[i - 1][j - 1] + cost  
            )

    return dp[m][n], dp


# Exact Matching Algorithms
def naive_match(text, pattern):
    matches = []
    n, m = len(text), len(pattern)
    for i in range(n - m + 1):
        if text[i:i + m] == pattern:
            matches.append(i)
    return matches


def build_bad_char_table(pattern):
    table = {}
    for i, char in enumerate(pattern):
        table[char] = i
    return table


def boyer_moore(text, pattern):
    bad_char = build_bad_char_table(pattern)
    matches = []
    n, m = len(text), len(pattern)
    shift = 0

    while shift <= n - m:
        j = m - 1
        while j >= 0 and pattern[j] == text[shift + j]:
            j -= 1

        if j < 0:
            matches.append(shift)
            shift += m - bad_char.get(text[shift + m], -1) if shift + m < n else 1
        else:
            shift += max(1, j - bad_char.get(text[shift + j], -1))

    return matches


# Visualization
def visualize_match(text, pattern, positions):
    text_list = list(text)
    for pos in positions:
        for i in range(len(pattern)):
            text_list[pos + i] = f"[{text_list[pos + i]}]"
    return "".join(text_list)


# Streamlit UI
st.set_page_config(
    page_title="Sequence Analysis & Matching",
    layout="wide"
)

st.title("Assignment 2 - Koo Xuan")
st.write("Sequence Analysis & Matching Application")

tab1, tab2 = st.tabs(["Distance Analysis", "Exact Matching"])

# TAB 1: Distance Analysis
with tab1:
    st.header("Hamming Distance & Edit Distance")

    ref = st.text_input(
        "Reference Sequence",
        key="distance_ref"
    )

    pattern = st.text_input(
        "Pattern Sequence",
        key="distance_pattern"
    )

    if st.button("Compute Distances", key="distance_btn"):
        if ref and pattern:
            st.subheader("Results")

            # Hamming Distance
            hd = hamming_distance(ref, pattern)
            if hd is None:
                st.warning("Hamming Distance requires sequences of equal length.")
            else:
                st.write(f"Hamming Distance: {hd}")

            # Edit Distance
            ed, dp_table = edit_distance_dp(ref, pattern)
            st.write(f"Minimum Edit Distance: {ed}")
            st.write(f"Maximum Edit Distance: {max(len(ref), len(pattern))}")

        else:
            st.error("Please enter both sequences.")


# TAB 2: Exact Matching
with tab2:
    st.header("Exact Sequence Matching")

    text = st.text_input(
        "Reference Sequence",
        key="matching_ref"
    )

    pattern2 = st.text_input(
        "Pattern Sequence",
        key="matching_pattern"
    )

    if st.button("Run Matching Algorithms", key="matching_btn"):
        if text and pattern2:
            col1, col2 = st.columns(2)

            # Naive Algorithm 
            start = time.perf_counter()
            naive_matches = naive_match(text, pattern2)
            naive_time = time.perf_counter() - start

            with col1:
                st.subheader("Naive Algorithm")
                st.write("Match exists:", bool(naive_matches))
                st.write("Number of matches:", len(naive_matches))
                st.write("Offsets:", naive_matches)
                st.write("Execution time (seconds):", naive_time)
                st.code(visualize_match(text, pattern2, naive_matches))

            # Boyer-Moore Algorithm 
            start = time.perf_counter()
            bm_matches = boyer_moore(text, pattern2)
            bm_time = time.perf_counter() - start

            with col2:
                st.subheader("Boyer-Moore Algorithm")
                st.write("Match exists:", bool(bm_matches))
                st.write("Number of matches:", len(bm_matches))
                st.write("Offsets:", bm_matches)
                st.write("Execution time (seconds):", bm_time)
                st.code(visualize_match(text, pattern2, bm_matches))


        else:
            st.error("Please enter both sequences.")
