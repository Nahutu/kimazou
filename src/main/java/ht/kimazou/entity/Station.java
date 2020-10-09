/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht.kimazou.entity;

import java.io.Serializable;
import java.util.List;
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
@Table(name = "Station")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Station.findAll", query = "SELECT s FROM Station s"),
    @NamedQuery(name = "Station.findByStationID", query = "SELECT s FROM Station s WHERE s.stationID = :stationID"),
    @NamedQuery(name = "Station.findByStationName", query = "SELECT s FROM Station s WHERE s.stationName = :stationName"),
    @NamedQuery(name = "Station.findByStationAddress", query = "SELECT s FROM Station s WHERE s.stationAddress = :stationAddress"),
    @NamedQuery(name = "Station.findByStationPhone", query = "SELECT s FROM Station s WHERE s.stationPhone = :stationPhone")})
public class Station implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "stationName")
    private String stationName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "stationAddress")
    private String stationAddress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "stationPhone")
    private String stationPhone;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "stationID")
    private Integer stationID;
    @JoinColumn(name = "propID_Proprietaire", referencedColumnName = "propID")
    @ManyToOne
    private Proprietaire proprietaire;
    @OneToMany(mappedBy = "station")
    private List<Ilot> ilotList;

    public Station() {
    }

    public Station(Integer stationID) {
        this.stationID = stationID;
    }

    public Station(String stationName, String stationAddress, String stationPhone, Proprietaire proprietaire) {
        this.stationName = stationName;
        this.stationAddress = stationAddress;
        this.stationPhone = stationPhone;
        this.proprietaire = proprietaire;
    }
    
    public Station(Integer stationID, String stationName, String stationAddress, String stationPhone, Proprietaire proprietaire) {
        this.stationID = stationID;
        this.stationName = stationName;
        this.stationAddress = stationAddress;
        this.stationPhone = stationPhone;
        this.proprietaire = proprietaire;
    }

    public Integer getStationID() {
        return stationID;
    }

    public void setStationID(Integer stationID) {
        this.stationID = stationID;
    }


    public Proprietaire getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Proprietaire proprietaire) {
        this.proprietaire = proprietaire;
    }

    @XmlTransient
    public List<Ilot> getIlotList() {
        return ilotList;
    }

    public void setIlotList(List<Ilot> ilotList) {
        this.ilotList = ilotList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stationID != null ? stationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Station)) {
            return false;
        }
        Station other = (Station) object;
        if ((this.stationID == null && other.stationID != null) || (this.stationID != null && !this.stationID.equals(other.stationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Station[ stationID=" + stationID + " ]";
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public String getStationPhone() {
        return stationPhone;
    }

    public void setStationPhone(String stationPhone) {
        this.stationPhone = stationPhone;
    }
    
}
