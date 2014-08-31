package models.results;

import models.HumanGene;
import models.cancer.CancerType;
import models.cancer.Sample;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tonywang on 6/24/14.
 */
@Entity
@Table(name = "pdtx_dnaseq_results")
public class DNASeqResult implements Serializable, Comparable<DNASeqResult>
{
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "dnaseq_result_id", unique = true, nullable = false)
    private Long dnaSeqResultId;

    @Column(name = "chromosome")
    private String chromosome;

    @Column(name = "start_position")
    private Long startPosition;      /* chromosomal coordinates */

    @Column(name = "end_position")
    private Long endPosition;

    @Column(name = "aminoacid_change")
    private String aminoAcidSubstition;      /* <em>A32G</em> - Alanine 32 changed to Glycine, for example */

    @Column(name = "polyphen_score", nullable = true)
    private double polyPhenScore;

    @Column(name = "exonic_function", nullable = true)
    private String exonicFunction;           /* stopgain SNV, etc */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dnaSeqResult", cascade = CascadeType.ALL)
    private Set<DNASeqResultSample> dnaSeqResultSamples = new HashSet<>();


    public Long getDnaSeqResultId() {
        return dnaSeqResultId;
    }

    public void setDnaSeqResultId(Long dnaSeqResultId) {
        this.dnaSeqResultId = dnaSeqResultId;
    }

     public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public Long getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Long startPosition) {
        this.startPosition = startPosition;
    }

    public Long getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Long endPosition) {
        this.endPosition = endPosition;
    }

    public String getAminoAcidSubstition() {
        return aminoAcidSubstition;
    }

    public void setAminoAcidSubstition(String aminoAcidSubstition) {
        this.aminoAcidSubstition = aminoAcidSubstition;
    }

    public double getPolyPhenScore() {
        return polyPhenScore;
    }

    public void setPolyPhenScore(double polyPhenScore) {
        this.polyPhenScore = polyPhenScore;
    }

    public String getExonicFunction() {
        return exonicFunction;
    }

    public void setExonicFunction(String exonicFunction) {
        this.exonicFunction = exonicFunction;
    }

    public Set<DNASeqResultSample> getDnaSeqResultSamples() {
        return dnaSeqResultSamples;
    }

    public void setDnaSeqResultSamples(Set<DNASeqResultSample> dnaSeqResultSamples) {
        this.dnaSeqResultSamples = dnaSeqResultSamples;
    }

    @Override
    public int compareTo(DNASeqResult that)
    {
        if (this.chromosome.compareTo(that.chromosome) == 0)
            return this.aminoAcidSubstition.compareTo(that.aminoAcidSubstition);

        return this.chromosome.compareTo(that.chromosome);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof DNASeqResult))
            return false;

        if (obj == this)
            return true;

        DNASeqResult that = (DNASeqResult) obj;

        return that.chromosome.equals(this.chromosome) && that.aminoAcidSubstition.equals(this.aminoAcidSubstition) &&
                that.startPosition == this.startPosition && that.endPosition == this.endPosition;
    }

    @Override
    public int hashCode() { return this.aminoAcidSubstition.hashCode() + this.chromosome.hashCode(); }


}
