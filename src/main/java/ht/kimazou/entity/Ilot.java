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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nahum
 */
@Entity
@Table(name = "Ilot")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ilot.findAll", query = "SELECT i FROM Ilot i"),
    @NamedQuery(name = "Ilot.findByIlotID", query = "SELECT i FROM Ilot i WHERE i.ilotID = :ilotID")})
public class Ilot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ilotID")
    private Integer ilotID;
    @OneToMany(mappedBy = "ilot")
    private List<Pompe> pompeList;
    @JoinColumn(name = "stationID_Station", referencedColumnName = "stationID")
    @ManyToOne
    private Station station;

    public Ilot() {
        
    }

    public Ilot(Integer ilotID, Station station) {
        this.ilotID = ilotID;
        this.station = station;
    }
    
    public Ilot(Station station) {
        this.station = station;
    }
    

    public Ilot(Integer ilotID) {
        this.ilotID = ilotID;
    }

    public Integer getIlotID() {
        return ilotID;
    }

    public void setIlotID(Integer ilotID) {
        this.ilotID = ilotID;
    }

    @XmlTransient
    public List<Pompe> getPompeList() {
        return pompeList;
    }

    public void setPompeList(List<Pompe> pompeList) {
        this.pompeList = pompeList;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ilotID != null ? ilotID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ilot)) {
            return false;
        }
        Ilot other = (Ilot) object;
        if ((this.ilotID == null && other.ilotID != null) || (this.ilotID != null && !this.ilotID.equals(other.ilotID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Ilot[ ilotID=" + ilotID + " ]";
    }
    
}
