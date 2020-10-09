/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht.kimazou.controller;

import ht.kimazou.entity.Proprietaire;
import ht.kimazou.entity.Station;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import ht.kimazou.session.ProprietaireFacade;

/**
 *
 * @author nahum
 */
@ManagedBean(name = "proprietaireMB")
@SessionScoped
public class ProprietaireMB implements Serializable {

    /**
     * Creates a new instance of ProprietaireMB
     */
    @EJB
    private ProprietaireFacade proprietaireFacade;
    private Proprietaire proprietaire;
    private Integer propID;
    private String firstname;
    private String lastname;
    private String address;
    private String telephone;
    private String email;
    private Station station;
    private String nif;
    
    public ProprietaireMB() {
    }

    public ProprietaireFacade getProprietaireFacade() {
        return proprietaireFacade;
    }

    public void setProprietaireFacade(ProprietaireFacade proprietaireFacade) {
        this.proprietaireFacade = proprietaireFacade;
    }

    public Proprietaire getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Proprietaire proprietaire) {
        this.proprietaire = proprietaire;
    }

    public Integer getPropID() {
        return propID;
    }

    public void setPropID(Integer propID) {
        this.propID = propID;
    }
    

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
    

    @SuppressWarnings("ConvertToTryWithResources")
    public String creerProp() throws SQLException {
        if (firstname != null || lastname!= null || address!= null || telephone!= null || email!= null) {
            try {
                Connection connection = ConnectionMB.getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO public.\"Proprietaire\" (firstname, lastname, address, telephone, email, nif) VALUES (?,?,?,?,?,?)");
                statement.setString(1, firstname);
                statement.setString(2, lastname.toUpperCase());
                statement.setString(3, address);
                statement.setString(4, telephone);
                statement.setString(5, email);
                statement.setString(6, nif);
                statement.executeUpdate();
                System.out.println("Inserting Successfully!");
                statement.close();
                connection.close();
                FacesMessage msg = new FacesMessage("Succesful proprietaire with ID=", propID + " is created.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (SQLException e) {
                System.out.println("Exception-File Upload." + e.getMessage());
            }
        } else{
            FacesMessage msg = new FacesMessage("Please fill all the fields!!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
       return "/a/proprietaireList?faces-redirect=true";
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public List<Proprietaire> listerProp(){
        List<Proprietaire> listprop = new ArrayList<>();
        try{
            Connection connection = ConnectionMB.getConnection();
            Statement st = connection.createStatement();
            st.setFetchSize(0);
            ResultSet rs = st.executeQuery("SELECT * FROM public.\"Proprietaire\" ");
            while (rs.next()){
                propID = rs.getInt("propID");
                firstname = rs.getString("firstname");
                lastname = rs.getString("lastname").toUpperCase();
                address = rs.getString("address");
                telephone = rs.getString("telephone");
                email = rs.getString("email");
                nif = rs.getString("nif");
                listprop.add(new Proprietaire(propID, firstname, lastname, address, telephone, email, nif));
                System.out.print("many rows were returned.");
            }
            rs.close();
            st.close();
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return listprop;
    }
    
    public List<Station> getStations(){
        //return stationMB.chargerStationByProp(propID);
        return null;
    }
     
    public String showDetails(int id) {  
        return "/a/proprietaireDetails?propID=" + id;   
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public Proprietaire chargerProp(){
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Proprietaire\" WHERE \"Proprietaire\".\"propID\" = ? ");
            st.setInt(1, propID);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                propID = rs.getInt("propID");
                firstname = rs.getString("firstname");
                lastname = rs.getString("lastname").toUpperCase();
                address = rs.getString("address");
                telephone = rs.getString("telephone");
                email = rs.getString("email");
                nif = rs.getString("nif");
                proprietaire = new Proprietaire(propID, firstname, lastname, address, telephone, email, nif);
                System.out.print("One row returned.");
            }
            rs.close();
            st.close();  
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return proprietaire;
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public String modifierProp(){
       if (firstname != null || lastname!= null || address!= null || telephone!= null || email!= null) {
            try {
                Connection connection = ConnectionMB.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE public.\"Proprietaire\" SET \"firstname\" = ?, \"lastname\" = ?, \"address\" = ?, \"telephone\" = ?, \"email\" = ?, \"nif\" = ? WHERE \"Proprietaire\".\"propID\" = ?");
                statement.setString(1, firstname);
                statement.setString(2, lastname.toUpperCase());
                statement.setString(3, address);
                statement.setString(4, telephone);
                statement.setString(5, email);
                statement.setString(6, nif);
                statement.setInt(7, propID);
                statement.executeUpdate();
                System.out.println("Updating Successfully!");
                statement.close();
                connection.close();
                FacesMessage msg = new FacesMessage("Succesful proprietaire with ID=", propID + " is created.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (SQLException e) {
                System.out.println("Exception-File Upload." + e.getMessage());
            }
        } else{
            FacesMessage msg = new FacesMessage("Please fill all the fields!!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
       return "/a/proprietaireList?faces-redirect=true";
    }
    
    public Proprietaire getDetails() {
        return chargerProp();
    }
    
    public String backToList() {
        return "/a/proprietaireList?faces-redirect=true";
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public List<Station> chargerStationByProp(){
        List<Station> listStat = new ArrayList<>();
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Station\" WHERE \"Station\".\"propID_Proprietaire\" = ? ");
            st.setInt(1, propID);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Integer stationID = rs.getInt("stationID");
                String stationName = rs.getString("stationName");
                String stationAddress = rs.getString("stationAddress");
                String stationPhone = rs.getString("stationPhone");
                Integer propID_Proprietaire = rs.getInt("propID_Proprietaire");
                propID = propID_Proprietaire;
                proprietaire = chargerProp();
                listStat.add(new Station(stationID, stationName, stationAddress, stationPhone, proprietaire));
            }
            rs.close();
            st.close(); 
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return listStat;
    }
    
    public String annulerProp(){
       return "/a/index?faces-redirect=true";
    }
    
}
