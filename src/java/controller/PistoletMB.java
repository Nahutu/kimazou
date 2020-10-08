/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Ilot;
import entity.Pistolet;
import entity.Pompe;
import entity.Proprietaire;
import entity.Station;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import session.PistoletFacade;

/**
 *
 * @author nahum
 */
@Named(value = "pistoletMB")
@SessionScoped
public class PistoletMB implements Serializable {
    
    @EJB
    private PistoletFacade pistoletFacade;
    private Pistolet pistolet;
    private Integer pistoletID, ps1, ps2;
    private double volumeVendu, quantitePrelevee;
    private String typeGaz, produit;
    private Pompe pompe;
    private Integer pompeID_Pompe, metersID;
    public static Integer stationID;
    //private StationMB smb;
    
    public PistoletMB() {
    }

    public PistoletFacade getPistoletFacade() {
        return pistoletFacade;
    }

    public void setPistoletFacade(PistoletFacade pistoletFacade) {
        this.pistoletFacade = pistoletFacade;
    }

    public Pistolet getPistolet() {
        return pistolet;
    }

    public void setPistolet(Pistolet pistolet) {
        this.pistolet = pistolet;
    }

    public Integer getPistoletID() {
        return pistoletID;
    }

    public void setPistoletID(Integer pistoletID) {
        this.pistoletID = pistoletID;
    }

    public double getVolumeVendu() {
        return volumeVendu;
    }

    public void setVolumeVendu(double volumeVendu) {
        this.volumeVendu = volumeVendu;
    }

    public String getTypeGaz() {
        return typeGaz;
    }

    public void setTypeGaz(String typeGaz) {
        this.typeGaz = typeGaz;
    }

    public Pompe getPompe() {
        return pompe;
    }

    public void setPompe(Pompe pompe) {
        this.pompe = pompe;
    }

    public Integer getPompeID_Pompe() {
        return pompeID_Pompe;
    }

    public void setPompeID_Pompe(Integer pompeID_Pompe) {
        this.pompeID_Pompe = pompeID_Pompe;
    }

    public Integer getPs1() {
        return ps1;
    }

    public void setPs1(Integer ps1) {
        this.ps1 = ps1;
    }

    public Integer getPs2() {
        return ps2;
    }

    public void setPs2(Integer ps2) {
        this.ps2 = ps2;
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

    public Integer getMetersID() {
        return metersID;
    }

    public void setMetersID(Integer metersID) {
        this.metersID = metersID;
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public String creerPistolet() throws SQLException {
        try {
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO public.\"Pistolet\" (\"volumeVendu\", \"typeGaz\", \"pompeID_Pompe\") VALUES (?,?,?)");
            statement.setDouble(1, volumeVendu);
            statement.setString(2, typeGaz);
            statement.setInt(3, pompeID_Pompe);
            statement.executeUpdate();   
            System.out.println("Inserting Successfully!");
            statement.close();
            connection.close();
            FacesMessage msg = new FacesMessage("Succesful Pistolet with ID= is created.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
           
            } catch (SQLException e) {
                System.out.println("Exception-File Upload." + e.getMessage());
            }
       blankField();
       return "pistoletList?faces-redirect=true";
    }
    
    /**
     *
     * @return
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public List<Pistolet> listerPistolet(){
        List<Pistolet> listPistolet = new ArrayList<>();
        try{
            Connection connection = ConnectionMB.getConnection();
            Statement st = connection.createStatement();
            st.setFetchSize(0);
            ResultSet rs = st.executeQuery("SELECT * FROM public.\"Pistolet\" ");
            while (rs.next()){
                pistoletID = rs.getInt("pistoletID");
                volumeVendu = rs.getDouble("volumeVendu");
                typeGaz = rs.getString("typeGaz");
                pompeID_Pompe = rs.getInt("pompeID_Pompe");
                pompe = chargerPompe(pompeID_Pompe);
                listPistolet.add(new Pistolet(pistoletID, volumeVendu, typeGaz, pompe));
                blankField();
            }
            rs.close();
            st.close();
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        blankField();
        return listPistolet;
    }
     
    public String showDetails(int id) {  
        //blankField();
        return "pistoletDetails?pistoletID=" + id;   
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public Pistolet chargerPistolet(){
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Pistolet\" WHERE \"Pistolet\".\"pistoletID\" = ? ");
            st.setInt(1, pistoletID);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                pistoletID = rs.getInt("pistoletID");
                volumeVendu = rs.getDouble("volumeVendu");
                typeGaz = rs.getString("typeGaz");
                pompeID_Pompe = rs.getInt("pompeID_Pompe");
                Ilot ilot = chargerIlot(pompeID_Pompe);
                pompe = new Pompe(pompeID_Pompe, ilot);
                pistolet = new Pistolet(pistoletID, volumeVendu, typeGaz, pompe);
            }
            rs.close();
            st.close(); 
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return pistolet;
    }
    
    public String backToList() {
        return "pistoletList?faces-redirect=true";
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public Pompe chargerPompe(Integer id){
       
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Pompe\" WHERE \"Pompe\".\"pompeID\" = ? ");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                id = rs.getInt("pompeID");
                Integer ilotID_Ilot = rs.getInt("ilotID_Ilot");
                Ilot ilot = chargerIlot(ilotID_Ilot);
                pompe = new Pompe(id, ilot);
            }
            rs.close();
            st.close();  
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return pompe;
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public String modifierPistolet(){
       if (pompe != null) {
            try {
                Connection connection = ConnectionMB.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE public.\"Pistolet\" SET \"volumeVendu\" = ?, \"typeGaz\" = ?, \"pompeID_Pompe\" = ? WHERE \"Pistolet\".\"pistoletID\" = ?");
                statement.setDouble(1, volumeVendu);
                statement.setString(2, typeGaz);
                statement.setInt(3, pompeID_Pompe);
                statement.setInt(4, pistoletID);
                statement.executeUpdate();
                blankField();
                System.out.println("Updating Successfully!");
                statement.close();
                connection.close();
                FacesMessage msg = new FacesMessage("Succesful pompe with ID=", pistoletID + " is created.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (SQLException e) {
                System.out.println("Exception-File Upload." + e.getMessage());
            }
        } else{
            FacesMessage msg = new FacesMessage("Please fill all the fields!!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
       return "pistoletList?faces-redirect=true";
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
    public Ilot chargerIlot(Integer id){
       Ilot ilot = new Ilot();
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Ilot\" WHERE \"Ilot\".\"ilotID\" = ? ");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                id = rs.getInt("ilotID");
                Integer stationID_Station = rs.getInt("stationID_Station");
                Station station = chargerStation(stationID_Station);
                ilot = new Ilot(id, station);
            }
            rs.close();
            st.close();  
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return ilot;
    }
    
    public void blankField(){
        volumeVendu = 0.0;
        typeGaz = "";
        pompeID_Pompe = 0;
        pompe = null;
               
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public Pistolet chargerPistolet(Integer id){
        Pistolet pistolet1 = new Pistolet();
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Pistolet\" WHERE \"Pistolet\".\"pistoletID\" = ? ");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                id = rs.getInt("pistoletID");
                volumeVendu = rs.getDouble("volumeVendu");
                typeGaz = rs.getString("typeGaz");
                pompeID_Pompe = rs.getInt("pompeID_Pompe");
                Ilot ilot = chargerIlot(pompeID_Pompe);
                pompe = new Pompe(pompeID_Pompe, ilot);
                pistolet1 = new Pistolet(id, volumeVendu, typeGaz, pompe);
            }
            rs.close();
            st.close(); 
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return pistolet1;
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public List<Pistolet> chargerPistoletByGaz(){
        List<Pistolet> listpist = new ArrayList<>();
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Pistolet\" WHERE \"Pistolet\".\"typeGaz\" = ? ");
            st.setString(1, typeGaz);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                pistoletID = rs.getInt("pistoletID");
                volumeVendu = rs.getDouble("volumeVendu");
                typeGaz = rs.getString("typeGaz");
                pompeID_Pompe = rs.getInt("pompeID_Pompe");
                Ilot ilot = chargerIlot(pompeID_Pompe);
                pompe = new Pompe(pompeID_Pompe, ilot);
                listpist.add(new Pistolet(pistoletID, volumeVendu, typeGaz, pompe));
            }
            rs.close();
            st.close(); 
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return listpist;
    }
    
    public double volumeTotal(){ 
        double v1 = 0.0;
        double v2 = 0.0;
        //double vtotal = v1+v2;
        try{
            Pistolet p1 = chargerPistolet(ps1);
            Pistolet p2 = chargerPistolet(ps2);
            if(!Objects.equals(p1.getPistoletID(), p2.getPistoletID())){
                v1 = p1.getVolumeVendu();
                v2 = p2.getVolumeVendu();
                System.out.println("Volume total :" + (v1+v2));
                return v1+v2;
            }else{
                return v1+v2;
            }          
        }catch(NullPointerException e){
            return 0.0;
        }
        
    }
    
    public void pistoletTypeGazChanged(ValueChangeEvent e){
        typeGaz = e.getNewValue().toString();	
    }
    
    public String showMeters(int id) {
        stationID = StationMB.statID1;
        return "addMeters?pistoletID=" +id;   
    }
    @SuppressWarnings("ConvertToTryWithResources")
    public String creerMeter(){
        Pistolet pst = new Pistolet();
        pst = chargerPistolet();
        //Integer id = pst.getPompe().getIlot().getStation().getStationID();
               // pompe.getIlot().getStation().getStationID();
        try {
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO public.\"Meters\" (\"date_Prelevement\", \"quantitePrelevee\", \"produit\", \"pistoletID_Pistolet\") VALUES (?,?,?,?)");
            //Timestamp tp = (Timestamp) date_Prelevement;
            Timestamp tp = Timestamp.valueOf(LocalDateTime.now());
            statement.setTimestamp(1, tp);
            statement.setDouble(2, quantitePrelevee);
            statement.setString(3, typeGaz);
            statement.setInt(4, pistoletID);
            if(quantitePrelevee > 0.0){
                statement.executeUpdate(); 
                System.out.println("Inserting Successfully!");
                modifierVolumeVendu();
            }
            statement.close();
            connection.close(); 
        } catch (SQLException e) {
                System.out.println("Exception-File Upload." + e.getMessage());
        }
        
        quantitePrelevee = 0.0;
        return "ajouterMeters?stationID=" + stationID;  
    }   
    
    public String annulerVolume(){
        return "ajouterMeters?stationID=" + stationID;
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public void modifierVolumeVendu(){
        try {
                Connection connection = ConnectionMB.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE public.\"Pistolet\" SET \"volumeVendu\" = ? WHERE \"Pistolet\".\"pistoletID\" = ?");
                statement.setDouble(1, quantitePrelevee);
                statement.setInt(2, pistoletID);
                statement.executeUpdate();
                System.out.println("Updating Successfully!");
                statement.close();
                connection.close();
                FacesMessage msg = new FacesMessage("Succesful pompe with ID=", pistoletID + " is created.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (SQLException e) {
                System.out.println("Exception-File Upload." + e.getMessage());
            }
    
    }
    
    public String annulerPistolet(){
       return "/a/index?faces-redirect=true";
    }

}
