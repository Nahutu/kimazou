/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht.kimazou.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nahum
 */
@Entity
@Table(name = "Pistolet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pistolet.findAll", query = "SELECT p FROM Pistolet p"),
    @NamedQuery(name = "Pistolet.findByPistoletID", query = "SELECT p FROM Pistolet p WHERE p.pistoletID = :pistoletID"),
    @NamedQuery(name = "Pistolet.findByVolumeVendu", query = "SELECT p FROM Pistolet p WHERE p.volumeVendu = :volumeVendu"),
    @NamedQuery(name = "Pistolet.findByTypeGaz", query = "SELECT p FROM Pistolet p WHERE p.typeGaz = :typeGaz")})
public class Pistolet implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "volumeVendu")
    private Double volumeVendu;
    @Column(name = "dateReleve")
    @Temporal(TemporalType.DATE)
    private Date dateReleve;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "typeGaz")
    private String typeGaz;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pistolet")
    private List<Meters> metersList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pistoletID")
    private Integer pistoletID;
    @JoinColumn(name = "pompeID_Pompe", referencedColumnName = "pompeID")
    @ManyToOne(optional = false)
    private Pompe pompe;

    public Pistolet() {
    }

    public Pistolet(Integer pistoletID) {
        this.pistoletID = pistoletID;
    }

    public Pistolet(Integer pistoletID, double volumeVendu, String typeGaz) {
        this.pistoletID = pistoletID;
        this.volumeVendu = volumeVendu;
        this.typeGaz = typeGaz;
    }
    
    public Pistolet(double volumeVendu, String typeGaz, Pompe pompe) {
        this.volumeVendu = volumeVendu;
        this.typeGaz = typeGaz;
        this.pompe = pompe;
    }
    
    public Pistolet(Integer pistoletID, double volumeVendu, String typeGaz, Pompe pompe) {
        this.pistoletID = pistoletID;
        this.volumeVendu = volumeVendu;
        this.typeGaz = typeGaz;
        this.pompe = pompe;
    }
    
    

    public Integer getPistoletID() {
        return pistoletID;
    }

    public void setPistoletID(Integer pistoletID) {
        this.pistoletID = pistoletID;
    }


    public Pompe getPompe() {
        return pompe;
    }

    public void setPompe(Pompe pompe) {
        this.pompe = pompe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pistoletID != null ? pistoletID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pistolet)) {
            return false;
        }
        Pistolet other = (Pistolet) object;
        if ((this.pistoletID == null && other.pistoletID != null) || (this.pistoletID != null && !this.pistoletID.equals(other.pistoletID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pistolet[ pistoletID=" + pistoletID + " ]";
    }
    
    public void volumeCumule(double volume){
        volumeVendu +=volume;
    }


    @XmlTransient
    public List<Meters> getMetersList() {
        return metersList;
    }

    public void setMetersList(List<Meters> metersList) {
        this.metersList = metersList;
    }

    public Double getVolumeVendu() {
        return volumeVendu;
    }

    public void setVolumeVendu(Double volumeVendu) {
        this.volumeVendu = volumeVendu;
    }

    public Date getDateReleve() {
        return dateReleve;
    }

    public void setDateReleve(Date dateReleve) {
        this.dateReleve = dateReleve;
    }

    public String getTypeGaz() {
        return typeGaz;
    }

    public void setTypeGaz(String typeGaz) {
        this.typeGaz = typeGaz;
    }
}
