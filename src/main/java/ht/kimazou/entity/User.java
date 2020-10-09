/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht.kimazou.entity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nahum
 */
@Entity
@Table(name = "User")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUserID", query = "SELECT u FROM User u WHERE u.userID = :userID"),
    @NamedQuery(name = "User.findByUname", query = "SELECT u FROM User u WHERE u.uname = :uname"),
    @NamedQuery(name = "User.findByUpass", query = "SELECT u FROM User u WHERE u.upass = :upass"),
    @NamedQuery(name = "User.findByUrole", query = "SELECT u FROM User u WHERE u.urole = :urole"),
    @NamedQuery(name = "User.findByUkey", query = "SELECT u FROM User u WHERE u.ukey = :ukey")})
public class User implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "uname")
    private String uname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "upass")
    private String upass;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "urole")
    private String urole;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "userID")
    private Integer userID;
    @Column(name = "ukey")
    private Boolean ukey;

    public User() {
    }

    public User(Integer userID) {
        this.userID = userID;
    }

    public User(Integer userID, String uname, String upass, String urole) {
        this.userID = userID;
        this.uname = uname;
        this.upass = upass;
        this.urole = urole;
        this.ukey = false;
    }
    
     public User(Integer userID, String uname, String upass, String urole, Boolean ukey) {
        this.userID = userID;
        this.uname = uname;
        this.upass = upass;
        this.urole = urole;
        this.ukey = ukey;
    }
    
    public User(String uname, String upass, String urole) {
        this.uname = uname;
        this.upass = upass;
        this.urole = urole;
        this.ukey = false;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }


    public Boolean getUkey() {
        return ukey;
    }

    public void setUkey(Boolean ukey) {
        this.ukey = ukey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.User[ userID=" + userID + " ]";
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpass() {
        return upass;
    }

    public void setUpass(String upass) {
        this.upass = upass;
    }

    public String getUrole() {
        return urole;
    }

    public void setUrole(String urole) {
        this.urole = urole;
    }
    
}
