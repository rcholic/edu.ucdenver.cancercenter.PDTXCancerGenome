package models.cancer;

import models.HumanGene;
import models.institutions.Institution;
import models.results.DNASeqResult;
import models.results.DNASeqResultSample;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tonywang on 6/23/14.
 */
@Entity
@Table(name = "pdtx_samples")
public class Sample implements Serializable, Comparable<Sample>
{
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "sample_id", unique = true, nullable = false)
    private Long sampleId;

    @Column(name = "sample_type")
    private String sampleType;   /* normal or cancer samples, for example? */

    @Column(name = "sample_name", unique = false, nullable = false)
    private String sampleName;

    @Column(name = "sample_source")
    private String sampleSource;      /* patient or animals ? */

    @Column(name = "genomic_method_typeId")
    private int genomeMethodTypeId;

    @Column(name = "sequencing_instrument", nullable = true)
    private String sequencingInstrument;

    @Column(name = "sequencing_center", nullable = true)
    private String sequencingCenter;

    @Column(name = "sample_description", nullable = true)
    private String sampleDescription;


    @ManyToOne
    @JoinColumn(name = "cancertype_id")
    private CancerType cancerType;     /* owner entity is laboratory of this oneTomany relationship */

    @ManyToOne
    @JoinColumn(name = "study_id")
    private StudyCase studyCase;

    public Sample() {}


    /* getters and setters */

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getSampleSource() {
        return sampleSource;
    }

    public void setSampleSource(String sampleSource) {
        this.sampleSource = sampleSource;
    }

    public int getGenomeMethodTypeId() {
        return genomeMethodTypeId;
    }

    public void setGenomeMethodTypeId(int genomeMethodTypeId) {
        this.genomeMethodTypeId = genomeMethodTypeId;
    }

    public StudyCase getStudyCase() {
        return studyCase;
    }

    public String getStudyCaseName() { return this.studyCase.getStudyName(); }

    public void setStudyCase(StudyCase studyCase) {
        this.studyCase = studyCase;
    }

    public CancerType getCancerType() {
        return cancerType;
    }

    public void setCancerType(CancerType cancerType) {
        this.cancerType = cancerType;
    }

    public String getSequencingInstrument() {
        return sequencingInstrument;
    }

    public void setSequencingInstrument(String sequencingInstrument) {
        this.sequencingInstrument = sequencingInstrument;
    }

    public String getSequencingCenter() {
        return sequencingCenter;
    }

    public void setSequencingCenter(String sequencingCenter) {
        this.sequencingCenter = sequencingCenter;
    }

    public String getSampleDescription() {
        return sampleDescription;
    }

    public void setSampleDescription(String sampleDescription) {
        this.sampleDescription = sampleDescription;
    }

    @Override
    public int compareTo(Sample that)
    {
        if (this.sampleId > that.sampleId)
            return +1;
        if (this.sampleId < that.sampleId)
            return -1;

        return 0;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Sample))
            return false;

        if (obj == this)
            return true;

        Sample that = (Sample) obj;

        return that.sampleId == this.sampleId && that.sampleName.equalsIgnoreCase(this.sampleName);
    }

    @Override
    public int hashCode() { return this.sampleName.hashCode(); }

    public static final Comparator<Sample> groupSampleByStudyCase = new Comparator<Sample>() {
        @Override
        public int compare(Sample a, Sample b)
        {
            if (a.studyCase.getStudyId() > b.studyCase.getStudyId()) return -1;
            if (a.studyCase.getStudyId() < b.studyCase.getStudyId()) return +1;
            if (a.studyCase.getStudyId() == b.studyCase.getStudyId())
            {
                if (a.sampleId > b.sampleId) return -1;
                if (a.sampleId < b.sampleId) return +1;
            }

            return 0;
        }
    };
}
