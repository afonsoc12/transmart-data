# WCGNA is in CRAN but depends on several packages not in cran (impute, GO.db,
# AnnotationDbi), which is something that really should not happen with a sane
# repository.
# Anyway, that's why it is installed here

# BiocManager and the packages required
install.packages("BiocManager", repos=Sys.getenv("CRAN_MIRROR"))
BiocManager::install(version = "3.10") # This version is suitable for R 3.6.1
BiocManager::install("impute")
BiocManager::install("AnnotationDbi")
BiocManager::install("GO.db")
BiocManager::install("CGHbase")
BiocManager::install("CGHcall")
BiocManager::install("DNAcopy")
BiocManager::install("GenomicRanges")
BiocManager::install("matrixStats")
BiocManager::install("R.utils")
BiocManager::install("Rsamtools")
BiocManager::install("future")
BiocManager::install("future.apply")
BiocManager::install("survival")
BiocManager::install("dynamicTreeCut")
BiocManager::install("fastcluster")
BiocManager::install("jpeg")
BiocManager::install("latticeExtra")
BiocManager::install("foreign")
BiocManager::install("Hmisc")
BiocManager::install("preprocessCore ")

required.packages <- c("WGCNA", "impute", "multtest", "CGHbase", "CGHtest",
					   "CGHtestpar", "edgeR", "snpStats", "preprocessCore",
					   "GO.db", "AnnotationDbi", "QDNAseq");
missing.packages <- function(required) {
	return(required[
		!(required %in% installed.packages()[,"Package"])]);
}
new.packages <- missing.packages(required.packages);
if (!length(new.packages))
	q();

# This no longer works for R 3.6.1. Use BiocManager instead (above)
#source("http://bioconductor.org/biocLite.R");
#bioclite.packages <-
#		intersect(new.packages, c("impute", "multtest", "CGHbase", "edgeR",
#								  "snpStats", "preprocessCore", "GO.db",
#								  "AnnotationDbi", "QDNAseq"));
#if (length(bioclite.packages))
#	biocLite(bioclite.packages);

# 1.10.0 version contains an important fix.
# We still need to install the old package with biocLite first to install all dependencies.
# For some reasons below installations does not take care of installing dependencies first.

# Update to version 1.16 -> 1.26
download.file(
                url="http://www.bioconductor.org/packages/release/bioc/src/contrib/QDNAseq_1.26.0.tar.gz",
                dest="/tmp/QDNAseq_1.26.0.tar.gz", method="internal");
install.packages("/tmp/QDNAseq_1.26.0.tar.gz",
                repos=NULL, type="source");

if (length(intersect(new.packages, c("CGHtest")))) {
	download.file(
			url="http://files.thehyve.net/CGHtest_1.1.tar.gz",
			dest="/tmp/CGHtest_1.1.tar.gz", method="internal");
	install.packages("/tmp/CGHtest_1.1.tar.gz",
			repos=NULL, type="source")
}
if (length(intersect(new.packages, c("CGHtestpar")))) {
	download.file(
			url="http://files.thehyve.net/CGHtestpar_0.0.tar.gz",
			dest="/tmp/CGHtestpar_0.0.tar.gz", method="internal");
	install.packages("/tmp/CGHtestpar_0.0.tar.gz",
			repos=NULL, type="source")
}
if (length(intersect(new.packages, c("WGCNA")))) {
	# Install directly from tarball
	if (length(intersect(new.packages, c("WGCNA")))) {
        download.file(
                        url="http://cran.r-project.org/src/contrib/WGCNA_1.69.tar.gz",
                        dest="/tmp/WGCNA_1.69.tar.gz", method="internal");
        install.packages("/tmp/WGCNA_1.69.tar.gz",
                        repos=NULL, type="source")
	}
}

if (length(missing.packages(required.packages))) {
	print("Failed packages...")
	failed.packages <- missing.packages(required.packages)
	print(failed.packages)
	warning('Some packages not installed');
	quit("no");
}
