package models.cancer;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by tonywang on 6/23/14.
 */
@Entity
@Table(name = "pdtx_samples")
public class Sample
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
    private String sampleSource;      /* patient of animals ? */

    @Column(name = "genomic_method_typeId")
    private int genomeMethodTypeId;

    @ManyToOne
    @JoinColumn(name = "study_id")
    private StudyCase studyCase;

    public Sample() {}

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

    public void setStudyCase(StudyCase studyCase) {
        this.studyCase = studyCase;
    }
}
