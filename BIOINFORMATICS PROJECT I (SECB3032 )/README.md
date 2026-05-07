🎓 SECB3032 BIOINFORMATICS PROJECT I (PSM1)– Projek Sarjana Muda 1 (Final Year Project I)

This folder contains the research proposal and project materials for my Final Year Project at Universiti Teknologi Malaysia, supervised by Ts. Dr. Chan Weng Howe.

The research is titled "Deep Learning-Based Methods for Latent Representation Quality in Multi-Omics for Cancer Subtype Classification".


📌 Project Background & Problem Statement

Accurate cancer subtype classification is essential for precision medicine due to the disease's high heterogeneity at the molecular level. Multi-omics data—which combines mRNA, miRNA, DNA methylation, and copy number variation (CNV)—offers a rich molecular view of tumor behavior, but it is highly dimensional and challenging to analyze. While many latent representation learning methods exist (such as VAE, MOFA+, and CustOmics), their evaluations are often inconsistent and rarely assess the actual quality of the learned latent representations beyond basic classification accuracy.


🎯 Project Objectives


- Review existing statistical-based and deep learning-based latent representation methods for multi-omics cancer subtype classification.
- Implement a Variational Autoencoder (VAE) to effectively learn latent representations from complex multi-omics data.
- Evaluate the strengths and limitations of these methods based on classification performance, latent space quality, and biological relevance.


🛠️ Methodology & Tech Stack

- Data Source: Publicly available, de-identified multi-omics datasets from The Cancer Genome Atlas (TCGA).
- Core Algorithms: Variational Autoencoder (VAE) for latent representation learning, and Support Vector Machine (SVM) for subtype classification.
- Evaluation Metrics:
  
                        Classification Performance: Accuracy, F1-score, and AUC-ROC.

                        Latent Space Quality: Silhouette Score and Normalised Mutual Information (NMI).

                        Biological Relevance: Survival analysis using the lifelines library.

- Development Environment: Python, Visual Studio Code, Git, and GitHub.
