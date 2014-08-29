package models.results;

import models.HumanGene;
import models.KeggPathway;
import models.cancer.Sample;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by tonywang on 6/25/14.
 *
 *
 */

@Entity
@Table(name = "pdtx_dnaseq_resultId_sampleId")
public class DNASeqResultSample implements Serializable, Comparable<DNASeqResultSample>
{
    @Column(name = "genotype", nullable = true)
    private String genotype;

    @Column(name = "num_variant_reads")
    private int numVariantReads;

    @Column(name = "num_ref_reads")
    private int numRefReads;

    @Id
    @ManyToOne
    @JoinColumn(name = "dnaseq_result_id")
    private DNASeqResult dnaSeqResult;

    @Id
    @ManyToOne
    @JoinColumn(name = "sample_id")
    private Sample sample;


    @ManyToOne
    @JoinColumn(name = "humangene_id")
    private HumanGene humanGene;       /* owner entity is DNASeqResult of this OneToMany relationship */


    public DNASeqResultSample() {}

    public String getGenotype() {
        return genotype;
    }

    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    public int getNumVariantReads() {
        return numVariantReads;
    }

    public void setNumVariantReads(int numVariantReads) {
        this.numVariantReads = numVariantReads;
    }

    public int getNumRefReads() {
        return numRefReads;
    }

    public void setNumRefReads(int numRefReads) {
        this.numRefReads = numRefReads;
    }

    public DNASeqResult getDnaSeqResult() {
        return dnaSeqResult;
    }

    public void setDnaSeqResult(DNASeqResult dnaSeqResult) {
        this.dnaSeqResult = dnaSeqResult;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public HumanGene getHumanGene() {
        return humanGene;
    }

    public void setHumanGene(HumanGene humanGene) {
        this.humanGene = humanGene;
    }

    public String getAAChange() { return this.dnaSeqResult.getAminoAcidSubstition(); }

    public String getChromosome() { return this.dnaSeqResult.getChromosome(); }

    public Long getStartPosition() { return this.dnaSeqResult.getStartPosition(); }

    public Long getEndPosition() { return this.dnaSeqResult.getEndPosition(); }

    public double getPolyphenScore() { return this.dnaSeqResult.getPolyPhenScore(); }

    public String getExonicFunction() { return this.dnaSeqResult.getExonicFunction(); }

    public String getStudyCaseName() { return this.sample.getStudyCase().getStudyName(); }

    public String getSampleName() { return this.sample.getSampleName(); }

    public String getGeneSymbol() { return this.humanGene.getGeneSymbol(); }



    public List<String> getPathways()
    {
        List<String> pathways = new ArrayList<>();
        if (humanGene.getKeggPathways() != null)
        {
            for (KeggPathway path : humanGene.getKeggPathways())
            {
                pathways.add(path.getKeggPathHSASymbol());
            }
        }

        return pathways;
    }

    public Long getStudyId()
    {
        return this.getSample().getStudyCase().getStudyId();
    }

    public Long getSampleId()
    {
        return this.getSample().getSampleId();
    }




    @Override
    public int compareTo(DNASeqResultSample that)
    {
        return this.sample.compareTo(that.sample);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof DNASeqResultSample))
            return false;

        if (obj == this)
            return true;

        DNASeqResultSample that = (DNASeqResultSample) obj;

        if (!that.sample.equals(this.sample))
            return false;

        if(!that.humanGene.equals(this.humanGene))
            return false;

        return this.dnaSeqResult.getDnaSeqResultId() == that.dnaSeqResult.getDnaSeqResultId();

//        return this.genotype == that.genotype && this.numRefReads == that.numRefReads && this.numVariantReads == that.numVariantReads;
    }

    @Override
    public int hashCode() { return this.genotype.hashCode() + this.getGeneSymbol().hashCode(); }

    public static final Comparator<DNASeqResultSample> groupComboByStudySample = new Comparator<DNASeqResultSample>() {
        @Override
        public int compare(DNASeqResultSample a, DNASeqResultSample b)
        {
            if (a.getSample().getStudyCase().getStudyId() > b.getSample().getStudyCase().getStudyId()) return -1;
            if (a.getSample().getStudyCase().getStudyId() < b.getSample().getStudyCase().getStudyId()) return +1;
            if (a.getSample().getStudyCase().getStudyId() == b.getSample().getStudyCase().getStudyId())
            {
                if (a.getSample().getSampleId() > b.getSample().getSampleId()) return -1;
                if (a.getSample().getSampleId() < b.getSample().getSampleId()) return +1;
            }


            return 0;
        }
    };

    public static final Comparator<DNASeqResultSample> groupComboByGeneSymbol = new Comparator<DNASeqResultSample>() {
        @Override
        public int compare(DNASeqResultSample a, DNASeqResultSample b) {

            return a.getGeneSymbol().compareTo(b.getGeneSymbol());

        }
    };
}
