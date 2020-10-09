/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht.kimazou.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nahum
 */
@Entity
@Table(name = "Meters")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Meters.findAll", query = "SELECT m FROM Meters m"),
    @NamedQuery(name = "Meters.findByMetersID", query = "SELECT m FROM Meters m WHERE m.metersID = :metersID"),
    @NamedQuery(name = "Meters.findByDatePrelevement", query = "SELECT m FROM Meters m WHERE m.datePrelevement = :datePrelevement"),
    @NamedQuery(name = "Meters.findByQuantitePrelevee", query = "SELECT m FROM Meters m WHERE m.quantitePrelevee = :quantitePrelevee"),
    @NamedQuery(name = "Meters.findByProduit", query = "SELECT m FROM Meters m WHERE m.produit = :produit")})
public class Meters implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "date_Prelevement")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePrelevement;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantitePrelevee")
    private double quantitePrelevee;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "produit")
    private String produit;
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "metersID")
    private Integer metersID;

    @JoinColumn(name = "pistoletID_Pistolet", referencedColumnName = "pistoletID")
    @ManyToOne(optional = false)
    private Pistolet pistolet;

    public Meters() {
    }

    public Meters(Integer metersID) {
        this.metersID = metersID;
    }

    public Meters(Integer metersID, Date datePrelevement, double quantitePrelevee, String produit) {
        this.metersID = metersID;
        this.datePrelevement = datePrelevement;
        this.quantitePrelevee = quantitePrelevee;
        this.produit = produit;
    }
    
    public Meters(Date datePrelevement, double quantitePrelevee, String produit, Pistolet pistolet) {
        this.datePrelevement = datePrelevement;
        this.quantitePrelevee = quantitePrelevee;
        this.produit = produit;
        this.pistolet = pistolet;
    }

    public Meters(Integer metersID, Timestamp date_Prelevement, double quantitePrelevee, String produit, Pistolet pistolet) {
        this.metersID = metersID;
        this.datePrelevement = date_Prelevement;
        this.quantitePrelevee = quantitePrelevee;
        this.produit = produit;
        this.pistolet = pistolet;
       }

    public Integer getMetersID() {
        return metersID;
    }

    public void setMetersID(Integer metersID) {
        this.metersID = metersID;
    }

    public String getDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd/MMM/yyyy HH:mm:ss");
        String dateString = sdf.format(datePrelevement);
        return dateString;
    }

    public Date getDatePrelevement() {
        return datePrelevement;
    }

    public void setDatePrelevement(Date datePrelevement) {
        this.datePrelevement = datePrelevement;
    }


    public Pistolet getPistolet() {
        return pistolet;
    }

    public void setPistolet(Pistolet pistolet) {
        this.pistolet = pistolet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (metersID != null ? metersID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Meters)) {
            return false;
        }
        Meters other = (Meters) object;
        if ((this.metersID == null && other.metersID != null) || (this.metersID != null && !this.metersID.equals(other.metersID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Meters[ metersID=" + metersID + " ]";
    }

    public double getQuantitePrelevee() {
        return quantitePrelevee;
    }

    public void setQuantitePrelevee(double quantitePrelevee) {
        this.quantitePrelevee = quantitePrelevee;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }
    
}
