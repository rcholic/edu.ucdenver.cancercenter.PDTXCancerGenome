package models.cancer;

import models.institutions.Laboratory;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by tonywang on 6/24/14.
 */
@Entity
@Table(name = "pdtx_cancer_type")
public class CancerType
{
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "cancertype_id", unique = true, nullable = false)
    private int cancerTypeId;

    @Column(name = "cancer_typecode")
    private String name;

    @Column(name = "cancer_description", nullable = true)
    private String cancerDescription;

    @OneToMany(mappedBy = "cancerType")
    private Set<Sample> samples;

    public CancerType() {}

    public int getCancerTypeId() {
        return cancerTypeId;
    }

    public void setCancerTypeId(int cancerTypeId) {
        this.cancerTypeId = cancerTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Sample> getSamples() {
        return samples;
    }

    public void setSamples(Set<Sample> samples) {
        this.samples = samples;
    }

    public String getCancerDescription() {
        return cancerDescription;
    }

    public void setCancerDescription(String cancerDescription) {
        this.cancerDescription = cancerDescription;
    }
}
