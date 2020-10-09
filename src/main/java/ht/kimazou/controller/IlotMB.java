/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht.kimazou.controller;

import ht.kimazou.entity.Ilot;
import ht.kimazou.entity.Pompe;
import ht.kimazou.entity.Proprietaire;
import ht.kimazou.entity.Station;
import javax.inject.Named;
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
import javax.faces.context.FacesContext;
import ht.kimazou.session.IlotFacade;

/**
 *
 * @author nahum
 */
@Named(value = "ilotMB")
@SessionScoped
public class IlotMB implements Serializable {

    @EJB
    private IlotFacade ilotFacade;
    private Integer ilotID;
    private Station station;
    private Integer stationID_Station;
    private Ilot ilot;
    
    public IlotMB() {
    }

    public IlotFacade getIlotFacade() {
        return ilotFacade;
    }

    public void setIlotFacade(IlotFacade ilotFacade) {
        this.ilotFacade = ilotFacade;
    }

    public Integer getIlotID() {
        return ilotID;
    }

    public void setIlotID(Integer ilotID) {
        this.ilotID = ilotID;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Integer getStationID_Station() {
        return stationID_Station;
    }

    public void setStationID_Station(Integer stationID_Station) {
        this.stationID_Station = stationID_Station;
    }

    public Ilot getIlot() {
        return ilot;
    }

    public void setIlot(Ilot ilot) {
        this.ilot = ilot;
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public String creerIlot() throws SQLException {
        try {
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO public.\"Ilot\" (\"stationID_Station\") VALUES (?)");
            statement.setInt(1, stationID_Station);
            statement.executeUpdate();
            System.out.println("Inserting Successfully!");
            statement.close();
            connection.close();
            FacesMessage msg = new FacesMessage("Succesful station with ID= is created.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (SQLException e) {
                System.out.println("Exception-File Upload." + e.getMessage());
            }
       blankField();
       return "ilotList?faces-redirect=true";
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public List<Ilot> listerIlot(){
        List<Ilot> listIlot = new ArrayList<>();
        try{
            Connection connection = ConnectionMB.getConnection();
            Statement st = connection.createStatement();
            st.setFetchSize(0);
            ResultSet rs = st.executeQuery("SELECT * FROM public.\"Ilot\" ");
            while (rs.next()){
                ilotID = rs.getInt("ilotID");
                stationID_Station = rs.getInt("stationID_Station");
                station = chargerStation(stationID_Station);
                listIlot.add(new Ilot(ilotID, station));
                System.out.print("many rows were returned.");
            }
            rs.close();
            st.close();
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        blankField();
        return listIlot;
    }
     
    public String showDetails(int id) {  
        return "ilotDetails?ilotID=" + id;   
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public Ilot chargerIlot(){
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Ilot\" WHERE \"Ilot\".\"ilotID\" = ? ");
            st.setInt(1, ilotID);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                ilotID = rs.getInt("ilotID");
                stationID_Station = rs.getInt("stationID_Station");
                station = chargerStation(stationID_Station);
                ilot = new Ilot(ilotID, station);
                System.out.print("One row returned.");
            }
            rs.close();
            st.close(); 
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return ilot;
    }
    
    public String backToList() {
        return "ilotList?faces-redirect=true";
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public Station chargerStation(Integer id){
       
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Station\" WHERE \"Station\".\"stationID\" = ? ");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                id = rs.getInt("stationID");
                String stationName = rs.getString("stationName");
                String stationAddress = rs.getString("stationAddress");
                String stationPhone = rs.getString("stationPhone");
                Integer propID_Proprietaire = rs.getInt("propID_Proprietaire");
                Proprietaire proprietaire = chargerProp(propID_Proprietaire);
                station = new Station(id, stationName, stationAddress, stationPhone, proprietaire);
                System.out.print("One row returned.");
            }
            rs.close();
            st.close();  
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return station;
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public String modifierIlot(){
       if (station != null) {
            try {
                Connection connection = ConnectionMB.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE public.\"Ilot\" SET \"stationID_Station\" = ? WHERE \"Ilot\".\"ilotID\" = ?");
                statement.setInt(1, stationID_Station);
                statement.setInt(2, ilotID);
                statement.executeUpdate();
                System.out.println("Updating Successfully!");
                blankField();
                statement.close();
                connection.close();
                FacesMessage msg = new FacesMessage("Succesful ilot with ID=", ilotID + " is created.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (SQLException e) {
                System.out.println("Exception-File Upload." + e.getMessage());
            }
        } else{
            FacesMessage msg = new FacesMessage("Please fill all the fields!!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
       return "ilotList?faces-redirect=true";
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public Proprietaire chargerProp(Integer id){
        Proprietaire proprietaire = new Proprietaire(); 
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Proprietaire\" WHERE \"Proprietaire\".\"propID\" = ? ");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                id = rs.getInt("propID");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String address = rs.getString("address");
                String telephone = rs.getString("telephone");
                String email = rs.getString("email");
                String nif = rs.getString("nif");
                proprietaire = new Proprietaire(id, firstname, lastname, address, telephone, email, nif);
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
    public List<Pompe> chargerPompeByIlot(){
        List<Pompe> listPompe = new ArrayList<>();
        try{
           Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Pompe\" WHERE \"Pompe\".\"ilotID_Ilot\" = ? ");
            st.setInt(1, ilotID);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Integer pompeID = rs.getInt("pompeID");
                Integer ilotID_Ilot = rs.getInt("ilotID_Ilot");
                ilotID = ilotID_Ilot;
                ilot = chargerIlot();
                String modele = rs.getString("modele");
                String no_Serie = rs.getString("no_Serie");
                Integer nbrePistolet = rs.getInt("nbrePistolet");
                listPompe.add(new Pompe(pompeID, ilot, modele, no_Serie, nbrePistolet));
                System.out.print("One row returned.");
            }
            rs.close();
            st.close(); 
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return listPompe;
    }
    
     public void blankField(){
        stationID_Station = 0;
        station = new Station();
    }
    
}
