/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht.kimazou.controller;

import ht.kimazou.entity.Ilot;
import ht.kimazou.entity.Meters;
import ht.kimazou.entity.Pistolet;
import ht.kimazou.entity.Pompe;
import ht.kimazou.entity.Proprietaire;
import ht.kimazou.entity.Station;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import ht.kimazou.session.StationFacade;

/**
 *
 * @author nahum
 */
@Named(value = "stationMB")
@SessionScoped
public class StationMB implements Serializable {

    @EJB
    private StationFacade stationFacade;
    private Station station;
    private Integer stationID, statID;
    private String stationName, typeGaz;
    private String stationAddress;
    private String stationPhone;
    private Proprietaire proprietaire;
    private Integer propID_Proprietaire;
    private Date dateReleve, dtReleve;
    //private final Integer statID1 = 1;
    public static Integer statID1;
    
    public StationMB() {
    }

    public StationFacade getStationFacade() {
        return stationFacade;
    }

    public void setStationFacade(StationFacade stationFacade) {
        this.stationFacade = stationFacade;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Integer getStationID() {
        return stationID;
    }

    public void setStationID(Integer stationID) {
        this.stationID = stationID;
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

    public Proprietaire getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Proprietaire proprietaire) {
        this.proprietaire = proprietaire;
    }

    public Integer getPropID_Proprietaire() {
        return propID_Proprietaire;
    }

    public void setPropID_Proprietaire(Integer propID_Proprietaire) {
        this.propID_Proprietaire = propID_Proprietaire;
    }

    public Integer getStatID() {
        return statID;
    }

    public void setStatID(Integer statID) {
        this.statID = statID;
    }

    public String getTypeGaz() {
        return typeGaz;
    }

    public void setTypeGaz(String typeGaz) {
        this.typeGaz = typeGaz;
    }

    public Date getDateReleve() {
        return dateReleve;
    }

    public void setDateReleve(Date dateReleve) {
        this.dateReleve = dateReleve;
    }

    public Date getDtReleve() {
        return dtReleve;
    }

    public void setDtReleve(Date dtReleve) {
        this.dtReleve = dtReleve;
    }
    
    
    @PostConstruct
    public void init() {
        statID = 1;
        stationID = 1;
        //dateReleve = Date.from(Instant.MIN);
    
    }
      
    @SuppressWarnings("ConvertToTryWithResources")
    public String creerStation() throws SQLException {
        if (stationName != null || stationAddress!= null || stationPhone!= null) {
            try {
                Connection connection = ConnectionMB.getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO public.\"Station\" (\"stationName\", \"stationAddress\", \"stationPhone\", \"propID_Proprietaire\") VALUES (?,?,?,?)");
                statement.setString(1, stationName);
                statement.setString(2, stationAddress);
                statement.setString(3, stationPhone);
                statement.setInt(4, propID_Proprietaire);
                statement.executeUpdate();
                System.out.println("Inserting Successfully!");
                statement.close();
                connection.close();
                FacesMessage msg = new FacesMessage("Succesful station with ID= is created.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (SQLException e) {
                System.out.println("Exception-File Upload." + e.getMessage());
            }
        } else{
            FacesMessage msg = new FacesMessage("Please fill all the fields!!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        blankField();
       return "stationList?faces-redirect=true";
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public List<Station> listerStation(){
        List<Station> listStat = new ArrayList<>();
        try{
            Connection connection = ConnectionMB.getConnection();
            Statement st = connection.createStatement();
            st.setFetchSize(0);
            ResultSet rs = st.executeQuery("SELECT * FROM public.\"Station\" ");
            while (rs.next()){
                stationID = rs.getInt("stationID");
                stationName = rs.getString("stationName");
                stationAddress = rs.getString("stationAddress");
                stationPhone = rs.getString("stationPhone");
                propID_Proprietaire = rs.getInt("propID_Proprietaire");
                proprietaire = chargerProp(propID_Proprietaire);
                listStat.add(new Station(stationID, stationName, stationAddress, stationPhone, proprietaire));
                System.out.print("many rows were returned.");
            }
            rs.close();
            st.close();
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        blankField();
        return listStat;
    }
     
    public String showDetails(int id) {  
        return "stationDetails?stationID=" + id;   
    }
    
    public String showMeters(int id) {  
       // statID1 = id;
        return "ajouterMeters?stationID=" + id;   
    }
    
    public String showMetersSup(int id) {  
        return "voirMeters?stationID=" + id;   
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public Station chargerStation(){
       
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Station\" WHERE \"Station\".\"stationID\" = ? ");
            st.setInt(1, stationID);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                stationID = rs.getInt("stationID");
                stationName = rs.getString("stationName");
                stationAddress = rs.getString("stationAddress");
                stationPhone = rs.getString("stationPhone");
                propID_Proprietaire = rs.getInt("propID_Proprietaire");
                proprietaire = chargerProp(propID_Proprietaire);
                station = new Station(stationID, stationName, stationAddress, stationPhone, proprietaire);
                System.out.print("One row returned.");
            }
            rs.close();
            st.close();
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        statID1 = station.getStationID();
        return station;
    }
    
    public String backToList() {
        return "stationList?faces-redirect=true";
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public Proprietaire chargerProp(Integer id){
       
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
    public String modifierStation(){
       if (stationName != null || stationAddress!= null || stationPhone!= null) {
            try {
                Connection connection = ConnectionMB.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE public.\"Station\" SET \"stationName\" = ?, \"stationAddress\" = ?, \"stationPhone\" = ?, \"propID_Proprietaire\" = ? WHERE \"Station\".\"stationID\" = ?");
                statement.setString(1, stationName);
                statement.setString(2, stationAddress);
                statement.setString(3, stationPhone);
                statement.setInt(4, propID_Proprietaire);
                statement.setInt(5, stationID);
                statement.executeUpdate();
                System.out.println("Updating Successfully!");
                statement.close();
                connection.close();
                FacesMessage msg = new FacesMessage("Succesful station with ID=", stationID + " is created.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (SQLException e) {
                System.out.println("Exception-File Upload." + e.getMessage());
            }
        } else{
            FacesMessage msg = new FacesMessage("Please fill all the fields!!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
       return "stationList?faces-redirect=true";
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public List<Ilot> chargerIlotByStation(){
        List<Ilot> listIlot = new ArrayList<>();
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Ilot\" WHERE \"Ilot\".\"stationID_Station\" = ? ");
            st.setInt(1, stationID);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Integer ilotID = rs.getInt("ilotID");
                Integer stationID_Station = rs.getInt("stationID_Station");
                stationID = stationID_Station;
                station = chargerStation();
                listIlot.add(new Ilot(ilotID, station));
                System.out.print("One row returned.");
            }
            rs.close();
            st.close(); 
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return listIlot;
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public List<Pompe> chargerPompeByStation(){
        List<Ilot> ilotList = new ArrayList<>();
        List<Pompe> pompeList = new ArrayList<>();
        try{
            chargerStation();
            ilotList = chargerIlotByStation();
            for (Ilot ilot : ilotList){
            Integer id = ilot.getIlotID();
            try{
                Connection connection = ConnectionMB.getConnection();
                PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Pompe\" WHERE \"Pompe\".\"ilotID_Ilot\" = ? ");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery(); 
                while (rs.next()){
                    Integer pompeID = rs.getInt("pompeID");
                    id = rs.getInt("ilotID_Ilot");
                    String modele = rs.getString("modele");
                    String noSerie = rs.getString("no_Serie");
                    Integer nbrePistolet = rs.getInt("nbrePistolet");
                    pompeList.add(new Pompe(pompeID, ilot, modele, noSerie, nbrePistolet));
                    System.out.print("many rows were returned.");
                }
            rs.close();
            st.close(); 
            connection.close();
            }catch(SQLException e){
                throw new IllegalStateException();
            }
        }return pompeList;
        
        }catch(IllegalStateException e){
        }
        return pompeList;
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public List<Pistolet> chargerPistoletByStation(){
        List<Pistolet> pistoletList = new ArrayList<>();
        List<Pompe> pompeList = new ArrayList<>();
        try{
            pompeList = chargerPompeByStation();
            for (Pompe pompe : pompeList){
                Integer id = pompe.getPompeID();
            //List<Pistolet> lp = pompe.getPistoletList();
            try{
                Connection connection = ConnectionMB.getConnection();
                PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Pistolet\" WHERE \"Pistolet\".\"pompeID_Pompe\" = ? ");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                while (rs.next()){
                    Integer pistoletID = rs.getInt("pistoletID");
                    double volumeVendu = rs.getDouble("volumeVendu");
                    String typeGaz1 = rs.getString("typeGaz");
                    //id = rs.getInt("pompeID_Pompe");
                    pistoletList.add(new Pistolet(pistoletID, volumeVendu, typeGaz1, pompe));
                    System.out.print("One row returned.");
                }
                rs.close();
                st.close(); 
                connection.close();
            }catch (SQLException e) {
                System.out.println("Exception-File Upload." + e.getMessage());
            }
            return pistoletList;
            }
        }catch(Exception e){
        }
        return pistoletList;
    }
    
    public void stationIDChanged(ValueChangeEvent e){
        statID = (Integer) e.getNewValue();  
         System.out.println("Exception-File Upload. New Value " + statID);
    }
    
    public void stationIDChanged1(ValueChangeEvent e){
        statID = (Integer) e.getNewValue(); 
        dateReleve = null;
         System.out.println("Exception-File Upload. New Value " + statID);
    }
    
    public void pistoletTypeGazChanged(ValueChangeEvent e){
        typeGaz = e.getNewValue().toString();	
    }
    
    public void pistoletDateChanged(SelectEvent event){
        dateReleve = (Date) event.getObject();
    }
    
    public void pistoletDateChanged1(SelectEvent event){
        dtReleve = (Date) event.getObject();
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public List<Pistolet> chargerAllPistoletByStation(){
        List<Pistolet> pistoletList = new ArrayList<>();
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Ilot\" WHERE \"Ilot\".\"stationID_Station\" = ? ");
            st.setInt(1, stationID);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Integer ilotID = rs.getInt("ilotID");
                Integer stationID_Station = rs.getInt("stationID_Station");
                stationID = stationID_Station;
                station = chargerStation();
                Ilot ilot = new Ilot(ilotID, station);
                try{
                    Connection connection1 = ConnectionMB.getConnection();
                    PreparedStatement st1 = connection1.prepareStatement("SELECT * FROM public.\"Pompe\" WHERE \"Pompe\".\"ilotID_Ilot\" = ? ");
                    st1.setInt(1, ilotID);
                    ResultSet rs1 = st1.executeQuery(); 
                    while (rs1.next()){
                        Integer pompeID = rs1.getInt("pompeID");
                        String modele = rs1.getString("modele");
                        String no_Serie = rs1.getString("no_Serie");
                        Integer nbrePistolet = rs1.getInt("nbrePistolet");
                        Pompe pompe = new Pompe(pompeID, ilot, modele, no_Serie, nbrePistolet);
                        try{
                            Connection connection2 = ConnectionMB.getConnection();
                            PreparedStatement st2 = connection2.prepareStatement("SELECT * FROM public.\"Pistolet\" WHERE \"Pistolet\".\"pompeID_Pompe\" = ? ");
                            st2.setInt(1, pompeID);
                            ResultSet rs2 = st2.executeQuery();
                            while (rs2.next()){
                                Integer pistoletID = rs2.getInt("pistoletID");
                                double volumeVendu = rs2.getDouble("volumeVendu");
                                String typeGaz1 = rs2.getString("typeGaz");
                                pistoletList.add(new Pistolet(pistoletID, volumeVendu, typeGaz1, pompe));
                                System.out.print("One row returned.");
                            }
                            rs2.close();
                            st2.close(); 
                            connection2.close();
                        }catch (SQLException e) {
                            System.out.println("Exception-File Upload." + e.getMessage());
                        }
                    }
                rs1.close();
                st1.close(); 
                connection1.close();
                }catch(SQLException e){
                    throw new IllegalStateException();
                }
            }
            rs.close();
            st.close(); 
            connection.close();
        }catch (IllegalStateException | SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return pistoletList;
    }
    
    public void showMessage() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "What we do in life", "Echoes in eternity.");
    }
    
    public void blankField(){
        stationID = 0;
        stationName = "";
        stationAddress = "";
        stationPhone = "";
        propID_Proprietaire = 0;
        proprietaire = new Proprietaire();
               
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public List<Meters> chargerAllMetersByStation(){
        List<Meters> metersList = new ArrayList<>();
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"Ilot\" WHERE \"Ilot\".\"stationID_Station\" = ? ");
            st.setInt(1, statID);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Integer ilotID = rs.getInt("ilotID");
                Ilot ilot = new Ilot(ilotID, station);
                try{
                    Connection connection1 = ConnectionMB.getConnection();
                    PreparedStatement st1 = connection1.prepareStatement("SELECT * FROM public.\"Pompe\" WHERE \"Pompe\".\"ilotID_Ilot\" = ? ");
                    st1.setInt(1, ilotID);
                    ResultSet rs1 = st1.executeQuery(); 
                    while (rs1.next()){
                        Integer pompeID = rs1.getInt("pompeID");
                        String modele = rs1.getString("modele");
                        String no_Serie = rs1.getString("no_Serie");
                        Integer nbrePistolet = rs1.getInt("nbrePistolet");
                        Pompe pompe = new Pompe(pompeID, ilot, modele, no_Serie, nbrePistolet);
                        try{
                            Connection connection2 = ConnectionMB.getConnection();
                            PreparedStatement st2 = connection2.prepareStatement("SELECT * FROM public.\"Pistolet\" WHERE \"Pistolet\".\"pompeID_Pompe\" = ? ");
                            st2.setInt(1, pompeID);
                            ResultSet rs2 = st2.executeQuery();
                            while (rs2.next()){
                                Integer pistoletID = rs2.getInt("pistoletID");
                                double volumeVendu = rs2.getDouble("volumeVendu");
                                String typeGaz1 = rs2.getString("typeGaz");
                                Pistolet pistolet = new Pistolet(pistoletID, volumeVendu, typeGaz1, pompe);
                                try{
                                    Connection connection3 = ConnectionMB.getConnection();
                                    PreparedStatement st3 = connection3.prepareStatement("SELECT * FROM public.\"Meters\" WHERE \"Meters\".\"pistoletID_Pistolet\" = ? ");
                                    st3.setInt(1, pistoletID);
                                    ResultSet rs3 = st3.executeQuery();
                                    while (rs3.next()){
                                        Integer metersID = rs3.getInt("metersID");
                                        Timestamp date_Prelevement = rs3.getTimestamp("date_Prelevement");
                                        double quantitePrelevee = rs3.getDouble("quantitePrelevee");
                                        String produit = rs3.getString("produit");
                                        metersList.add(new Meters(metersID, date_Prelevement, quantitePrelevee, produit, pistolet));
                                    }
                                    rs3.close();
                                    st3.close(); 
                                    connection3.close();
                                }catch (SQLException e) {
                                    System.out.println("Exception-File Upload." + e.getMessage());
                                }
                            }
                            rs2.close();
                            st2.close(); 
                            connection2.close();
                        }catch (SQLException e) {
                            System.out.println("Exception-File Upload." + e.getMessage());
                        }
                    }
                rs1.close();
                st1.close(); 
                connection1.close();
                }catch(SQLException e){
                    throw new IllegalStateException();
                }
            }
            rs.close();
            st.close(); 
            connection.close();
        }catch (IllegalStateException | SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return metersList;
    }
    
    public double volumeTotal(){
        double volumeTotal = 0.0;
        List<Meters> lm = new ArrayList<>();
        lm = chargerAllMetersByStationByDate();
        for (Meters m : lm){
            volumeTotal += m.getQuantitePrelevee();
        }
        return volumeTotal;
    }
    
    public double allVolumeTotal(){
        double allVolumeTotal = 0.0;
        List<Meters> lm = new ArrayList<>();
        try{
            lm = chargerAllMetersByStation();
            for (Meters m : lm){
                allVolumeTotal += m.getQuantitePrelevee();
        }
        return allVolumeTotal;
        }catch(NullPointerException e){
            return allVolumeTotal;
        }
        
    }
    
    public double volumeGazoline(){
        double volumeGazoline = 0.0;
        List<Meters> lm = new ArrayList<>();
        try{
            lm = chargerAllMetersByStationByDate();
            for (Meters m : lm){
                if("Gazoline".equals(m.getProduit())){
                    volumeGazoline += m.getQuantitePrelevee();
                }       
        }
        return volumeGazoline;
        }catch(NullPointerException e){
            return volumeGazoline;
        }
    }
    
    public double volumeDiesel(){
        double volumeDiesel = 0.0;
        List<Meters> lm = new ArrayList<>();
        try{
            lm = chargerAllMetersByStationByDate();
            for (Meters m : lm){
                if("Diesel".equals(m.getProduit())){
                    volumeDiesel += m.getQuantitePrelevee();
                }       
        }
        return volumeDiesel;
        }catch(NullPointerException e){
            return volumeDiesel;
        }
    }
    
    public double volumeKerozene(){
        double volumeKerozene = 0.0;
        List<Meters> lm = new ArrayList<>();
        try{
            lm = chargerAllMetersByStationByDate();
            for (Meters m : lm){
                if("Kérozène".equals(m.getProduit())){
                    volumeKerozene += m.getQuantitePrelevee();
                }       
        }
        return volumeKerozene;
        }catch(NullPointerException e){
            return volumeKerozene;
        }
    }
    
    public List<Meters> chargerAllMetersByStation1(){
        dateReleve = null;
        return chargerAllMetersByStation();
    }
    
    public String allMetersByStation(){
        dateReleve = null;
        return "/a/allMetersByStation?faces-redirect=true";
    }
    
    public String allMetersByStationByDate(){
        return "/a/allMetersByStationByDate?faces-redirect=true";
    }
    
    public List<Meters> chargerAllMetersByStationByDate(){
        List<Meters> metersList = new ArrayList<>();
        List<Meters> metersList1 = new ArrayList<>();
        try{
            metersList = chargerAllMetersByStation();
            for (Meters meters : metersList){
                Date dr = meters.getDatePrelevement();
                if(dr.getYear() == dateReleve.getYear() && dr.getMonth() == dateReleve.getMonth() && dr.getDay() == dateReleve.getDay()){
                    metersList1.add(meters);
                } 
            }
        }catch(NullPointerException e){
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        
        return metersList1;
    }
    
    public List<Double> chargerAllVolumeByStationByDate(){
        List<Meters> metersList = new ArrayList<>();
        List<Double> volumeList = new ArrayList<>();
        try{
            metersList = chargerAllMetersByStation();
            for (Meters meters : metersList){
                Date dr = meters.getDatePrelevement();
                if(dr.getYear() == dtReleve.getYear() && dr.getMonth() == dtReleve.getMonth() && dr.getDay() == dtReleve.getDay()){
                    double qte = meters.getQuantitePrelevee();
                    volumeList.add(qte);
                } 
            }
        }catch(NullPointerException e){
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        
        return volumeList;
    }
    
    @SuppressWarnings("Convert2Diamond")
    public List<Map.Entry<Meters, Double>> getAllMeters() {
        HashMap<Meters, Double> mtv = new HashMap<Meters, Double>();
        try{
            //HashMap<Meters, Double> mtv = new HashMap<Meters, Double>();
            List<Meters> ml = new ArrayList<>();
            List<Double> vl = new ArrayList<>();
            ml = chargerAllMetersByStationByDate();
            vl = chargerAllVolumeByStationByDate();
            int i = 0;
            for(Meters m : ml){
                mtv.put(m, vl.get(i));
                i++;
            }
            
        }catch(IndexOutOfBoundsException e){
            return null;
        }
        Set<Map.Entry<Meters, Double>> metersSet = mtv.entrySet();
        return new ArrayList<Map.Entry<Meters, Double>> (metersSet);
    }
    
    public Double volumeTotalVendu(){
        double vt = 0.0;
        try{
            List<Map.Entry<Meters, Double>> am = new ArrayList<>();
            am = getAllMeters();
            for (Map.Entry<Meters, Double> mtv : am){
                vt += mtv.getValue()-mtv.getKey().getQuantitePrelevee();
            }
           
        }catch(NullPointerException e){
           System.out.println("Exception-File Upload." + e.getMessage());
        }
        return vt;
    }
    
    public Double volumeGazolineTotal(){
        double vt = 0.0;
        try{
            List<Map.Entry<Meters, Double>> am = new ArrayList<>();
            am = getAllMeters();
            for (Map.Entry<Meters, Double> mtv : am){
                if("Gazoline".equals(mtv.getKey().getProduit())){
                    vt += mtv.getValue()-mtv.getKey().getQuantitePrelevee();
                }    
            }
           
        }catch(NullPointerException e){
           System.out.println("Exception-File Upload." + e.getMessage());
        }
        return vt;
    }
    
    public Double volumeDieselTotal(){
        double vt = 0.0;
        try{
            List<Map.Entry<Meters, Double>> am = new ArrayList<>();
            am = getAllMeters();
            for (Map.Entry<Meters, Double> mtv : am){
                if("Diesel".equals(mtv.getKey().getProduit())){
                    vt += mtv.getValue()-mtv.getKey().getQuantitePrelevee();
                }    
            }
           
        }catch(NullPointerException e){
           System.out.println("Exception-File Upload." + e.getMessage());
        }
        return vt;
    }
    
    public Double volumeKerozeneTotal(){
        double vt = 0.0;
        try{
            List<Map.Entry<Meters, Double>> am = new ArrayList<>();
            am = getAllMeters();
            for (Map.Entry<Meters, Double> mtv : am){
                if("Kérozène".equals(mtv.getKey().getProduit())){
                    vt += mtv.getValue()-mtv.getKey().getQuantitePrelevee();
                }    
            }
           
        }catch(NullPointerException e){
           System.out.println("Exception-File Upload." + e.getMessage());
        }
        return vt;
    }
    
    public String annulerStation(){
       return "/a/index?faces-redirect=true";
    }
    
}