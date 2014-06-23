package models.institutions;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "pdtx_institutions")
public class Institution
{
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "institute_id", unique = true, nullable = false)
    private int instituteId;

    @Column(name = "institute_type_id")
    private int instityteTypeId;

    @Column(name = "institute_name")
    private String instituteName;

    @Column(name = "institute_address")
    private String instituteAddress;

    @Column(name = "institute_country")
    private String instituteCountry;

    @OneToMany(mappedBy = "institution")
    private Set<Laboratory> laboratories;

    public Institution() {}

    public int getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(int instituteId) {
        this.instituteId = instituteId;
    }

    public int getInstityteTypeId() {
        return instityteTypeId;
    }

    public void setInstityteTypeId(int instityteTypeId) {
        this.instityteTypeId = instityteTypeId;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getInstituteAddress() {
        return instituteAddress;
    }

    public void setInstituteAddress(String instituteAddress) {
        this.instituteAddress = instituteAddress;
    }

    public String getInstituteCountry() {
        return instituteCountry;
    }

    public void setInstituteCountry(String instituteCountry) {
        this.instituteCountry = instituteCountry;
    }

    public Set<Laboratory> getLaboratories() {
        return laboratories;
    }

    public void setLaboratories(Set<Laboratory> laboratories) {
        this.laboratories = laboratories;
    }
}