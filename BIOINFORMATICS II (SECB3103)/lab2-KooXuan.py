import streamlit as st
from Bio import PDB
import numpy as np
import py3Dmol

# Function 1: Retrieve protein structure
def get_protein_structure(protID):
    pdb_list = PDB.PDBList()
    pdb_filename = pdb_list.retrieve_pdb_file(protID, pdir=".", file_format="pdb", overwrite=True)
    
    parser = PDB.PDBParser(QUIET=True)
    structure = parser.get_structure(protID, pdb_filename)
    
    return structure


def get_structure_info(prot_structure):
    model = prot_structure[0]
    total_mass = 0.0
    weighted_coords = np.array([0.0, 0.0, 0.0])
    atomic_masses = {
        'H': 1.008, 'C': 12.011, 'N': 14.007,
        'O': 15.999, 'S': 32.06, 'P': 30.974
    }

    # Calculate COM
    for chain in model:
        for residue in chain:
            if residue.id[0] == ' ':
                for atom in residue:
                    element = atom.element.strip()
                    mass = atomic_masses.get(element, 12.0)
                    coord = atom.coord
                    weighted_coords += mass * coord
                    total_mass += mass

    center = weighted_coords / total_mass

    # Calculate Rg
    weighted_dist = 0.0
    for chain in model:
        for residue in chain:
            if residue.id[0] == ' ':
                for atom in residue:
                    element = atom.element.strip()
                    mass = atomic_masses.get(element, 12.0)
                    coord = atom.coord
                    dist_sq = np.sum((coord - center) ** 2)
                    weighted_dist += mass * dist_sq

    rg = np.sqrt(weighted_dist / total_mass)

    #3D View
    viewer = py3Dmol.view(query=f'pdb:{prot_structure.id}')
    viewer.setStyle({'cartoon': {'color': 'spectrum'}})
    viewer.show()
   
    view_html = viewer._make_html()

    return {
        "Center of Mass": center,
        "Radius of Gyration": rg,
        "3D View HTML": view_html
    }


# STREAMLIT APPLICATION
st.title('Lab 2 - Koo Xuan')
st.subheader("Protein Structure Analysis App")
st.write("To retrieve and analyze protein 3D structure")

protID = st.text_input("Enter PDB ID (e.g. 1A3N):")

if st.button("Analyze Structure"):
    with st.spinner("Downloading and analyzing structure..."):
        try:
            prot_structure = get_protein_structure(protID)
            info = get_structure_info(prot_structure)

            st.success(f"Structure {protID} retrieved successfully!")

            # Display structure information
            st.subheader("Structural Information")
            st.write(f"**Center of Mass (COM):** {np.round(info['Center of Mass'], 3)}")
            st.write(f"**Radius of Gyration (Rg):** {info['Radius of Gyration']:.3f} Å")

            # Display 3D visualization
            st.subheader("3D Structure View")
            st.components.v1.html(info['3D View HTML'], height=500, scrolling=False)

        except Exception as e:
            st.error(f"Error: {str(e)}")
            st.info("Please make sure you entered a valid PDB ID .")
