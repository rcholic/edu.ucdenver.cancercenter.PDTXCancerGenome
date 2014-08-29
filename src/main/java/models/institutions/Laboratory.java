package models.institutions;

import models.cancer.StudyCase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tonywang on 6/23/14.
 */
@Entity
@Table(name = "pdtx_laboratories")
public class Laboratory implements Serializable
{
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "lab_id", unique = true, nullable = false)
    private Long labId;

    @Column(name = "lab_address")
    private String labAddress;

    @Column(name = "lab_pi_name")
    private String labPIName;

    @Column(name = "lab_web_url")
    private String labWebUrl;

    @Column(name = "lab_phone")
    private String labPhone;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institution institution;     /* owner entity is laboratory of this oneTomany relationship */

    @OneToMany(mappedBy = "laboratory")
    private Set<StudyCase> studyCases = new HashSet<>();

    public Laboratory() {}

    public Long getLabId() {
        return labId;
    }

    public void setLabId(Long labId) {
        this.labId = labId;
    }

    public String getLabAddress() {
        return labAddress;
    }

    public void setLabAddress(String labAddress) {
        this.labAddress = labAddress;
    }

    public String getLabPIName() {
        return labPIName;
    }

    public void setLabPIName(String labPIName) {
        this.labPIName = labPIName;
    }

    public String getLabWebUrl() {
        return labWebUrl;
    }

    public void setLabWebUrl(String labWebUrl) {
        this.labWebUrl = labWebUrl;
    }

    public String getLabPhone() {
        return labPhone;
    }

    public void setLabPhone(String labPhone) {
        this.labPhone = labPhone;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public Set<StudyCase> getStudyCases() {
        return studyCases;
    }

    public void setStudyCases(Set<StudyCase> studyCases) {
        this.studyCases = studyCases;
    }
}
