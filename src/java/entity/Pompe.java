/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nahum
 */
@Entity
@Table(name = "Pompe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pompe.findAll", query = "SELECT p FROM Pompe p"),
    @NamedQuery(name = "Pompe.findByPompeID", query = "SELECT p FROM Pompe p WHERE p.pompeID = :pompeID")})
public class Pompe implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "modele")
    private String modele;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "no_Serie")
    private String noSerie;
    @Column(name = "nbrePistolet")
    private Integer nbrePistolet;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "pompeID")
    private Integer pompeID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pompe")
    private List<Pistolet> pistoletList;
    @JoinColumn(name = "ilotID_Ilot", referencedColumnName = "ilotID")
    @ManyToOne
    private Ilot ilot;

    public Pompe() {
    }

    public Pompe(Integer pompeID) {
        this.pompeID = pompeID;
    }
    
    public Pompe(Integer pompeID, Ilot ilot) {
        this.pompeID = pompeID;
        this.ilot = ilot;
    }
    
    public Pompe(Ilot ilot) {
        this.ilot = ilot;
    }

    public Pompe(Integer pompeID, Ilot ilot, String modele, String noSerie, Integer nbrePistolet) {
        this.pompeID = pompeID;
        this.ilot = ilot;
        this.modele = modele;
        this.noSerie = noSerie;
        this.nbrePistolet = nbrePistolet;
    }

    public Pompe(Ilot ilot, String modele, String noSerie, Integer nbrePistolet) {
        this.ilot = ilot;
        this.modele = modele;
        this.noSerie = noSerie;
        this.nbrePistolet = nbrePistolet;
    }

    public Integer getPompeID() {
        return pompeID;
    }

    public void setPompeID(Integer pompeID) {
        this.pompeID = pompeID;
    }

    @XmlTransient
    public List<Pistolet> getPistoletList() {
        return pistoletList;
    }

    public void setPistoletList(List<Pistolet> pistoletList) {
        this.pistoletList = pistoletList;
    }

    public Ilot getIlot() {
        return ilot;
    }

    public void setIlot(Ilot ilot) {
        this.ilot = ilot;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pompeID != null ? pompeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pompe)) {
            return false;
        }
        Pompe other = (Pompe) object;
        if ((this.pompeID == null && other.pompeID != null) || (this.pompeID != null && !this.pompeID.equals(other.pompeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pompe[ pompeID=" + pompeID + " ]";
    }


    public String getNoSerie() {
        return noSerie;
    }

    public void setNoSerie(String noSerie) {
        this.noSerie = noSerie;
    }


    public Integer getNbrePistolet() {
        return nbrePistolet;
    }

    public void setNbrePistolet(Integer nbrePistolet) {
        this.nbrePistolet = nbrePistolet;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

   
    
    
}
