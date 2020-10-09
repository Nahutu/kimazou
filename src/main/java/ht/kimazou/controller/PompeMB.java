/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht.kimazou.controller;

import ht.kimazou.entity.Ilot;
import ht.kimazou.entity.Pistolet;
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
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import ht.kimazou.session.PompeFacade;

/**
 *
 * @author nahum
 */
@Named(value = "pompeMB")
@SessionScoped
public class PompeMB implements Serializable {
    
    @EJB
    private PompeFacade pompeFacade;
    private Integer pompeID, metersID;
    private Integer ilotID_Ilot, pistoletID_Pistolet;
    private Ilot ilot;
    private Pompe pompe;
    private String modele, produit;
    private String no_Serie;
    private Integer nbrePistolet;
    private Date date_Prelevement;
    private double quantitePrelevee;
    
    public PompeMB() {
    }

    public PompeFacade getPompeFacade() {
        return pompeFacade;
    }

    public void setPompeFacade(PompeFacade pompeFacade) {
        this.pompeFacade = pompeFacade;
    }

    public Integer getPompeID() {
        return pompeID;
    }

    public void setPompeID(Integer pompeID) {
        this.pompeID = pompeID;
    }

    public Integer getIlotID_Ilot() {
        return ilotID_Ilot;
    }

    public void setIlotID_Ilot(Integer ilotID_Ilot) {
        this.ilotID_Ilot = ilotID_Ilot;
    }

    public Ilot getIlot() {
        return ilot;
    }

    public void setIlot(Ilot ilot) {
        this.ilot = ilot;
    }

    public Pompe getPompe() {
        return pompe;
    }

    public void setPompe(Pompe pompe) {
        this.pompe = pompe;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getNo_Serie() {
        return no_Serie;
    }

    public void setNo_Serie(String no_Serie) {
        this.no_Serie = no_Serie;
    }

    public Integer getNbrePistolet() {
        return nbrePistolet;
    }

    public void setNbrePistolet(Integer nbrePistolet) {
        this.nbrePistolet = nbrePistolet;
    }

    public Integer getMetersID() {
        return metersID;
    }

    public void setMetersID(Integer metersID) {
        this.metersID = metersID;
    }

    public Integer getPistoletID_Pistolet() {
        return pistoletID_Pistolet;
    }

    public void setPistoletID_Pistolet(Integer pistoletID_Pistolet) {
        this.pistoletID_Pistolet = pistoletID_Pistolet;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public Date getDate_Prelevement() {
        return date_Prelevement;
    }

    public void setDate_Prelevement(Date date_Prelevement) {
        this.date_Prelevement = date_Prelevement;
    }

    public double getQuantitePrelevee() {
        return quantitePrelevee;
    }

    public void setQuantitePrelevee(double quantitePrelevee) {
        this.quantitePrelevee = quantitePrelevee;
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public String creerPompe() throws SQLException {
        try {
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO public.\"Pompe\" (\"ilotID_Ilot\", \"modele\", \"no_Serie\", \"nbrePistolet\") VALUES (?,?,?,?)");
            statement.setInt(1, ilotID_Ilot);
            statement.setString(2, modele);
            statement.setString(3, no_Serie);
            statement.setInt(4, nbrePistolet);
            statement.executeUpdate();
            System.out.println("Inserting Successfully!");
            statement.close();
            connection.close();
            FacesMessage msg = new FacesMessage("Succesful Pompe with ID= is created.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (SQLException e) {
                System.out.println("Exception-File Upload." + e.getMessage());
            }
       blankField();
       return "pompeList?faces-redirect=true";
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public List<Pompe> listerPompe(){
        List<Pompe> listPompe = new ArrayList<>();
        try{
            Connection connection = ConnectionMB.getConnection();
            Statement st = connection.createStatement();
            st.setFetchSize(0);
            ResultSet rs = st.executeQuery("SELECT * FROM public.\"Pompe\" ");
            while (rs.next()){
                pompeID = rs.getInt("pompeID");
                ilotID_Ilot = rs.getInt("ilotID_Ilot");
                ilot = chargerIlot();
                modele = rs.getString("modele");
                no_Serie = rs.getString("no_Serie");
                nbrePistolet = rs.getInt("nbrePistolet");
                listPompe.add(new Pompe(pompeID, ilot, modele, no_Serie, nbrePistolet));
                blankField();
                System.out.print("many rows were returned.");
            }
            rs.close();
            st.close();
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return listPompe;
    }
     
    public String showDetails(int id) {  
        return "pompeDetails?pompeID=" + id;   
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public Pompe chargerPompe(){
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Pompe\" WHERE \"Pompe\".\"pompeID\" = ? ");
            st.setInt(1, pompeID);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                pompeID = rs.getInt("pompeID");
                ilotID_Ilot = rs.getInt("ilotID_Ilot");
                ilot = chargerIlot();
                modele = rs.getString("modele");
                no_Serie = rs.getString("no_Serie");
                nbrePistolet = rs.getInt("nbrePistolet");
                pompe = new Pompe(pompeID, ilot, modele, no_Serie, nbrePistolet);
                System.out.print("One row returned.");
            }
            rs.close();
            st.close(); 
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return pompe;
    }
    
    public String backToList() {
        return "pompeList?faces-redirect=true";
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public Ilot chargerIlot(){
       
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Ilot\" WHERE \"Ilot\".\"ilotID\" = ? ");
            st.setInt(1, ilotID_Ilot);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                ilotID_Ilot = rs.getInt("ilotID");
                Integer stationID_Station = rs.getInt("stationID_Station");
                Station station = chargerStation(stationID_Station);
                ilot = new Ilot(ilotID_Ilot, station);
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
    
    @SuppressWarnings("ConvertToTryWithResources")
    public String modifierPompe(){
       if (ilot != null) {
            try {
                Connection connection = ConnectionMB.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE public.\"Pompe\" SET \"ilotID_Ilot\" = ?, \"modele\" = ?, \"no_Serie\" = ?, \"nbrePistolet\" = ? WHERE \"Pompe\".\"pompeID\" = ?");
                statement.setInt(1, ilotID_Ilot);
                statement.setString(2, modele);
                statement.setString(3, no_Serie);
                statement.setInt(4, nbrePistolet);
                statement.setInt(5, pompeID);
                statement.executeUpdate();
                System.out.println("Updating Successfully!");
                blankField();
                statement.close();
                connection.close();
                FacesMessage msg = new FacesMessage("Succesful pompe with ID=", pompeID + " is created.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (SQLException e) {
                System.out.println("Exception-File Upload." + e.getMessage());
            }
        } else{
            FacesMessage msg = new FacesMessage("Please fill all the fields!!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
       return "pompeList?faces-redirect=true";
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public Station chargerStation(Integer id){
        Station station = new Station(); 
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
                station = new Station(id,stationName, stationAddress, stationPhone, proprietaire);
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
    
    @SuppressWarnings({"ConvertToTryWithResources"})
    public List<Pistolet> chargerPistoletByPompe(){
        List<Pistolet> listpist = new ArrayList<>();
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Pistolet\" WHERE \"Pistolet\".\"pompeID_Pompe\" = ? ");
            st.setInt(1, pompeID);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Integer pistoletID = rs.getInt("pistoletID");
                double volumeVendu = rs.getDouble("volumeVendu");
                String typeGaz = rs.getString("typeGaz");
                Integer pompeID_Pompe = rs.getInt("pompeID_Pompe");
                pompeID = pompeID_Pompe;
                pompe = chargerPompe();
                listpist.add(new Pistolet(pistoletID, volumeVendu, typeGaz, pompe));
                System.out.print("One row returned.");
            }
            rs.close();
            st.close(); 
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return listpist;
    }
    
    public String showMeters(int id) {  
        return "addMeters?pompeID=" + id;   
    }
    
    public void blankField(){
        nbrePistolet = 0;
        no_Serie = "";
        modele = "";
        ilotID_Ilot = 0;
        ilot = new Ilot();
               
    }
    
    public String annulerPompe(){
       return "/a/index?faces-redirect=true";
    }
}
