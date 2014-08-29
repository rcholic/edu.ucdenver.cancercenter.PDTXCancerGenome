package models;

import models.cancer.Sample;
import models.results.DNASeqResult;
import models.results.DNASeqResultSample;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tonywang on 6/23/14.
 */
@Entity
@Table(name = "pdtx_humangeneshugo")
public class HumanGene implements Serializable, Comparable<HumanGene>
{
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "humangene_id", unique = true, nullable = false)
    private int geneId;

    @Column(name = "gene_symbol", nullable = false, unique = true)
    private String geneSymbol;

    @Column(name = "gene_fullname")
    private String geneFullName;

    @Column(name = "gene_synonyms", nullable = true)
    private String geneSynonyms;

    @Column(name = "chromosome")
    private String chromosome;

    @Column(name = "entrez_gene_id")
    private String entrezGeneId;

    @Column(name = "ensemble_gene_id")
    private String ensembleGeneId;

    @Column(name = "unitprot_id")
    private String unitProtId;

    @Column(name = "refSeq_id")
    private String refSeqId;

    @Column(name = "gene_family_description", columnDefinition = "mediumtext")
    private String geneFamilyDescription;

    @ManyToMany(mappedBy = "humanGenes")
    private Set<KeggPathway> keggPathways = new HashSet<KeggPathway>();

    @OneToMany(mappedBy = "humanGene")
    private Set<DNASeqResultSample> dnaSeqResultSampleSet = new HashSet<DNASeqResultSample>();

    public HumanGene() {}

    public HumanGene(String geneSymbol, String geneFullName,
                     String chromosome, String entrezGeneId,
                     String ensembleGeneId, String unitProtId,
                     String refSeqId, String geneFamilyDescription, String geneSynonyms)
    {
        this.geneSymbol = geneSymbol;
        this.geneFullName = geneFullName;
        this.chromosome = chromosome;
        this.entrezGeneId = entrezGeneId;
        this.ensembleGeneId = ensembleGeneId;
        this.unitProtId = unitProtId;
        this.refSeqId = refSeqId;
        this.geneFamilyDescription = geneFamilyDescription;
        this.geneSynonyms = geneSynonyms;
    }

    public int getGeneId() {
        return geneId;
    }

    public void setGeneId(int geneId) {
        this.geneId = geneId;
    }

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    public String getGeneFullName() {
        return geneFullName;
    }

    public void setGeneFullName(String geneFullName) {
        this.geneFullName = geneFullName;
    }

    public String getGeneSynonyms() {
        return geneSynonyms;
    }

    public void setGeneSynonyms(String geneSynonyms) {
        this.geneSynonyms = geneSynonyms;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public String getEntrezGeneId() {
        return entrezGeneId;
    }

    public void setEntrezGeneId(String entrezGeneId) {
        this.entrezGeneId = entrezGeneId;
    }

    public String getEnsembleGeneId() {
        return ensembleGeneId;
    }

    public void setEnsembleGeneId(String ensembleGeneId) {
        this.ensembleGeneId = ensembleGeneId;
    }

    public String getUnitProtId() {
        return unitProtId;
    }

    public void setUnitProtId(String unitProtId) {
        this.unitProtId = unitProtId;
    }

    public Set<KeggPathway> getKeggPathways() {
        return keggPathways;
    }

    public void setKeggPathways(Set<KeggPathway> keggPathways) {
        this.keggPathways = keggPathways;
    }

    public String getRefSeqId() {
        return refSeqId;
    }

    public void setRefSeqId(String refSeqId) {
        this.refSeqId = refSeqId;
    }

    public String getGeneFamilyDescription() {
        return geneFamilyDescription;
    }

    public void setGeneFamilyDescription(String geneFamilyDescription) {
        this.geneFamilyDescription = geneFamilyDescription;
    }

    public Set<DNASeqResultSample> getDnaSeqResultSampleSet() {
        return dnaSeqResultSampleSet;
    }

    public void setDnaSeqResultSampleSet(Set<DNASeqResultSample> dnaSeqResultSampleSet) {
        this.dnaSeqResultSampleSet = dnaSeqResultSampleSet;
    }

    @Override
    public int compareTo(HumanGene that)
    {
        if (this.geneId > that.geneId)
            return +1;

        if (this.geneId < that.geneId)
            return -1;

        return 0;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof HumanGene))
            return false;

        if (obj == this)
            return true;

        HumanGene that = (HumanGene) obj;

        return that.geneId == this.geneId && that.geneSymbol.equals(this.geneSymbol);
    }

    @Override
    public int hashCode() { return this.geneSymbol.hashCode(); }
}
