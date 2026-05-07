import streamlit as st
from Bio import Entrez, SeqIO
from Bio.SeqUtils.ProtParam import ProteinAnalysis

def retrieve_data(uniprot_id):
    """Retrieve protein sequence data from NCBI using a UniProt ID"""
    Entrez.email = "xuan-04@graduate.utm.my" 
    try:
        handle = Entrez.efetch(db='protein', id=uniprot_id, rettype='fasta', retmode='text')
        record = SeqIO.read(handle, 'fasta')
        handle.close()
        return record
    except Exception as e:
        st.error(f"❌ Failed to retrieve data for ID {uniprot_id}. Error: {e}")
        return None

def get_basic_analysis(sequence):
    """Perform basic protein sequence analysis"""
    seq_analysis = ProteinAnalysis(str(sequence))
    seq_length = len(sequence)
    aa_composition = seq_analysis.count_amino_acids()
    molecular_weight = seq_analysis.molecular_weight()
    isoelectric_point = seq_analysis.isoelectric_point()

    return seq_length, aa_composition, molecular_weight, isoelectric_point

st.title('Lab 1 - Koo Xuan')
st.subheader("Protein Sequence Retrieval and Basic Analysis")

protein_id = st.text_input('Enter UniProt ID')
retrieve = st.button('Retrieve')

if retrieve:
    if protein_id!= "":
        record = retrieve_data(protein_id)

        if record:
            st.success(f"✅ Successfully retrieved data for {protein_id}")

            st.markdown("### Retreived Protein")
            st.write(f"**Sequence Name:** {record.name}")
            st.write(f"**Description:** {record.description}")
            st.text_area("Sequence:", value=str(record.seq), height=150)

            seq_length, aa_composition, mol_weight, pI = get_basic_analysis(record.seq)

            st.markdown("### Basic Protein Analysis")
            st.write(f"**Sequence Length:** {seq_length}")
            st.write(f"**Molecular Weight (Da):** {mol_weight:.2f}")
            st.write(f"**Isoelectric Point (pI):** {pI:.2f}")

            st.markdown("**Amino Acid Composition:**")
            st.json(aa_composition)

    else:
        st.warning('Please enter a UniProt ID')
