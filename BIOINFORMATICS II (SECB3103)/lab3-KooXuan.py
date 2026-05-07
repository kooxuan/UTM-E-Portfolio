import requests
import pandas as pd
import networkx as nx
import streamlit as st
import matplotlib.pyplot as plt

# Retrieve PPI data from BioGRID
def retrieve_ppi_biogrid(target_protein):
    biogrid_url = "https://webservice.thebiogrid.org/interactions"

    params = {
        "accessKey": "585b23750ef5fd7f8c3b0a3170e41e3d",  
        "format": "json",
        "searchNames": True,
        "geneList": target_protein,
        "organism": 9606,
        "searchbiogridids": True,
        "includeInteractors": True
    }

    response = requests.get(biogrid_url, params=params)

    if response.status_code != 200:
        st.error("BioGRID API request failed.")
        return None

    data = response.json()

    if len(data) == 0:
        st.warning("No BioGRID interactions found.")
        return None

    df = pd.DataFrame.from_dict(data, orient="index")

    df["OFFICIAL_SYMBOL_A"] = df["OFFICIAL_SYMBOL_A"].str.upper()
    df["OFFICIAL_SYMBOL_B"] = df["OFFICIAL_SYMBOL_B"].str.upper()

    ppi_df = df[["OFFICIAL_SYMBOL_A", "OFFICIAL_SYMBOL_B"]]
    ppi_df.columns = ["protein1", "protein2"]
    ppi_df['BIOGRID_ID'] = df['BIOGRID_INTERACTION_ID']

    return ppi_df



# Retrieve PPI data from STRING
def retrieve_ppi_string(target_protein):

    string_url = "https://string-db.org/api/json/network"

    params = {
        "identifiers": target_protein,
        "species": 9606,
        "limit": 20
    }

    response = requests.get(string_url, params=params)

    if response.status_code != 200:
        st.error("STRING API request failed.")
        return None

    data = response.json()

    if len(data) == 0:
        st.warning("No STRING interactions found.")
        return None

    df = pd.json_normalize(data)

    ppi_df = df[["preferredName_A", "preferredName_B", "score"]]
    ppi_df.columns = ["protein1", "protein2", "score"]

    return ppi_df



# Create network graph
def generate_network(dataframe):
    graph = nx.from_pandas_edgelist(dataframe, "protein1", "protein2")
    return graph


# 5 centrality measures
def get_centralities(network_graph):

    degree_cent = nx.degree_centrality(network_graph)
    betweenness_cent = nx.betweenness_centrality(network_graph)
    closeness_cent = nx.closeness_centrality(network_graph)

    try:
        eigen_cent = nx.eigenvector_centrality(network_graph, max_iter=500)
    except:
        eigen_cent = {node: 0 for node in network_graph.nodes()}

    try:
        pagerank_cent = nx.pagerank(network_graph)
    except:
        pagerank_cent = {node: 0 for node in network_graph.nodes()}

    return {
        "Degree Centrality": degree_cent,
        "Betweenness Centrality": betweenness_cent,
        "Closeness Centrality": closeness_cent,
        "Eigenvector Centrality": eigen_cent,
        "PageRank Centrality": pagerank_cent
    }


# STREAMLIT APPLICATION

st.title("Lab 3 - Koo Xuan")
st.write("Protein-Protein Interaction (BioGRID & STRING)")

protein_input = st.text_input("Enter a human protein ID (example: TP53, BRCA1, MB):").upper()

db_choice = st.selectbox(
    "Choose a Database",
    ("BioGRID", "STRING")
)

if st.button("Retrieve PPI Data"):

    if protein_input == "":
        st.warning("Please enter a valid protein ID.")
        st.stop()

    # Retrieve PPI Data
    if db_choice == "BioGRID":
        ppi_df = retrieve_ppi_biogrid(protein_input)
    else:
        ppi_df = retrieve_ppi_string(protein_input)

    if ppi_df is None:
        st.stop()

    col1, col2 = st.columns(2)

    #   COLUMN 1 – PPI DATA INFORMATION
    with col1:
        st.subheader("PPI Data Information")
        st.dataframe(ppi_df)

        network_graph = generate_network(ppi_df)
        st.write(f"**Number of Nodes:** {network_graph.number_of_nodes()}")
        st.write(f"**Number of Edges:** {network_graph.number_of_edges()}")

        st.write("### Network Visualization")
        fig, ax = plt.subplots(figsize=(5,5))
        layout = nx.spring_layout(network_graph, seed=125)
        nx.draw(network_graph, layout, with_labels=True, node_color="skyblue", node_size=300, font_size=5)
        st.pyplot(fig)

    #   COLUMN 2 – CENTRALITY MEASURES
    with col2:
        st.subheader("Centrality Measures")

        centralities = get_centralities(network_graph)

        for name, values in centralities.items():
            st.write(f"### {name}")
            sorted_nodes = sorted(values.items(), key=lambda x: -x[1])[:5]
            for node, score in sorted_nodes:
                st.write(f"{node}: {score}")

        st.success("Centrality measurements computed successfully.")

