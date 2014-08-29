package models.cancer;

import models.institutions.Laboratory;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tonywang on 6/23/14.
 */
@Entity
@Table(name = "pdtx_study_cases")
public class StudyCase implements Serializable, Comparable<StudyCase>
{
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "study_id", unique = true, nullable = false)
    private Long studyId;

    @Column(name = "study_name")
    private String studyName;

    @Column(name = "num_samples")
    private int numSamples;

    @Column(name = "disease_name")
    private String diseaseName;

    @Column(name = "study_description", columnDefinition = "mediumtext")
    private String studyDescription;

    @Temporal(TemporalType.DATE)
    @Column(name = "study_start_date")
    private Date studyStartDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "study_end_date")
    private Date studyEndDate;

    @Column(name = "public_study")
    private boolean publicStudy;

    @Column(name = "pmid")
    private String pmid;        /* if the study has been published, what is its PMID? */

    @OneToMany(mappedBy = "studyCase")
    private Set<Sample> samples = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "lab_id")
    private Laboratory laboratory;

    public StudyCase() {}

    public Long getStudyId() {
        return studyId;
    }

    public void setStudyId(Long studyId) {
        this.studyId = studyId;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public int getNumSamples() {
        return numSamples;
    }

    public void setNumSamples(int numSamples) {
        this.numSamples = numSamples;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getStudyDescription() {
        return studyDescription;
    }

    public void setStudyDescription(String studyDescription) {
        this.studyDescription = studyDescription;
    }

    public Date getStudyStartDate() {
        return studyStartDate;
    }

    public void setStudyStartDate(Date studyStartDate) {
        this.studyStartDate = studyStartDate;
    }

    public boolean isPublicStudy() {
        return publicStudy;
    }

    public void setPublicStudy(boolean publicStudy) {
        this.publicStudy = publicStudy;
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    public Set<Sample> getSamples() {
        return samples;
    }

    public void setSamples(Set<Sample> samples) {
        this.samples = samples;
    }

    public Date getStudyEndDate() {
        return studyEndDate;
    }

    public void setStudyEndDate(Date studyEndDate) {
        this.studyEndDate = studyEndDate;
    }

    public Laboratory getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(Laboratory laboratory) {
        this.laboratory = laboratory;
    }

    public String getLabPI()
    {
        return this.laboratory.getLabPIName();
    }

    @Override
    public int compareTo(StudyCase that)
    {
        if (this.studyId > that.studyId)
            return +1;
        if (this.studyId < that.studyId)
            return -1;

        return 0;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof StudyCase))
            return false;

        if (obj == this)
            return true;

        StudyCase that = (StudyCase) obj;

        return that.studyName.equals(this.studyName);
    }

    @Override
    public int hashCode() { return this.studyName.hashCode(); }
}
