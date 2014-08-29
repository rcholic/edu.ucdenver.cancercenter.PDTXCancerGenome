package models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tonywang on 6/23/14.
 */
@Entity
@Table(name = "pdtx_keggpathway")
public class KeggPathway implements Serializable, Comparable<KeggPathway>
{
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "pathway_id", unique = true, nullable = false)
    private int pathwayId;

    @Column(name = "kegg_pathway_fullname")
    private String KeggPathwayFullName;

    @Transient
    private String keggPathwayUrl;   /* http://www.genome.jp/kegg/pathway/hsa/{keggPathHSASymbol.tolowerCase()}.html*/


    @Column(name = "kegg_pathway_symbol")
    private String keggPathHSASymbol;

    @Column(name = "num_genes")
    private int numGene;

    @ManyToMany(cascade = {CascadeType.ALL})    /* owner entity of this manytomany relationship */
    @JoinTable(name = "pdtx_kegg_pathId_humangeneId",
            joinColumns = {@JoinColumn(name = "pathway_id")},
            inverseJoinColumns = {@JoinColumn(name = "humangene_id")})
    private Set<HumanGene> humanGenes = new HashSet<HumanGene>();

    public KeggPathway() {}

    public int getPathwayId() {
        return pathwayId;
    }

    public void setPathwayId(int pathwayId) {
        this.pathwayId = pathwayId;
    }

    public String getKeggPathwayUrl() {
        return keggPathwayUrl;
    }

    public void setKeggPathwayUrl(String keggPathwayUrl) {
        this.keggPathwayUrl = keggPathwayUrl;
    }

    public String getKeggPathwayFullName() {
        return KeggPathwayFullName;
    }

    public void setKeggPathwayFullName(String keggPathwayFullName) {
        KeggPathwayFullName = keggPathwayFullName;
    }

    public String getKeggPathHSASymbol() {
        return keggPathHSASymbol;
    }

    public void setKeggPathHSASymbol(String keggPathHSASymbol) {
        this.keggPathHSASymbol = keggPathHSASymbol;
    }

    public int getNumGene() {
        return numGene;
    }

    public void setNumGene(int numGene) {
        this.numGene = numGene;
    }

    public Set<HumanGene> getHumanGenes() {
        return humanGenes;
    }

    public void setHumanGenes(Set<HumanGene> humanGenes) {
        this.humanGenes = humanGenes;
    }


    @Override
    public int compareTo(KeggPathway that)
    {
        return this.keggPathHSASymbol.compareTo(that.keggPathHSASymbol);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof KeggPathway))
            return false;

        if (obj == this)
            return true;

        KeggPathway that = (KeggPathway) obj;

        return that.keggPathHSASymbol.equals(this.keggPathHSASymbol);
    }

    @Override
    public int hashCode() { return this.keggPathHSASymbol.hashCode(); }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.KeggPathwayFullName)
                .append(", ")
                .append(this.keggPathHSASymbol);

        return sb.toString();

    }
}
