/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht.kimazou.controller;

import ht.kimazou.entity.User;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author nahum
 */
@Named(value = "userMB")
@SessionScoped
public class UserMB implements Serializable {
    
    private String uname, upass, urole;
    private Boolean ukey, adminRenderedValue, clientRenderedValue, ukey1;
    private Boolean superRenderedValue, operateurRenderedValue;
    private Integer userID, sessionID;
    private User user;
    private final String salt1 = PasswordUtils.getSalt(30);
    private static Integer connectedIDUser;
    //public final Integer idConn = connectedIDUser;
    
    public UserMB() {
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

    public Boolean getUkey() {
        return ukey;
    }

    public void setUkey(Boolean ukey) {
        this.ukey = ukey;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getAdminRenderedValue() {
        return adminRenderedValue;
    }

    public void setAdminRenderedValue(Boolean adminRenderedValue) {
        this.adminRenderedValue = adminRenderedValue;
    }

    public Integer getSessionID() {
        return sessionID;
    }

    public void setSessionID(Integer sessionID) {
        this.sessionID = sessionID;
    }

    public Boolean getClientRenderedValue() {
        return clientRenderedValue;
    }

    public void setClientRenderedValue(Boolean clientRenderedValue) {
        this.clientRenderedValue = clientRenderedValue;
    }

    public Boolean getUkey1() {
        return ukey1;
    }

    public void setUkey1(Boolean ukey1) {
        this.ukey1 = ukey1;
    }

    public Boolean getSuperRenderedValue() {
        return superRenderedValue;
    }

    public void setSuperRenderedValue(Boolean superRenderedValue) {
        this.superRenderedValue = superRenderedValue;
    }

    public Boolean getOperateurRenderedValue() {
        return operateurRenderedValue;
    }

    public void setOperateurRenderedValue(Boolean operateurRenderedValue) {
        this.operateurRenderedValue = operateurRenderedValue;
    }
    
    
    
    // Generate Salt. The generated value can be stored in DB. 
    private final int logRounds = 10;
    
    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
    }

    public boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public String creerUser() throws SQLException, InvalidKeySpecException {
        try {
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO public.\"User\" (\"uname\", \"upass\", \"urole\") VALUES (?,?,?)");
            statement.setString(1, uname);
            String mySecurePassword = hash(upass);//PasswordUtils.generateSecurePassword(upass, salt1);
            statement.setString(2, mySecurePassword);
            statement.setString(3, urole);
            statement.executeUpdate();   
            System.out.println("Inserting Successfully!");
            statement.close();
            connection.close();
            FacesMessage msg = new FacesMessage("Succesful User with ID= is created.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
           
            } catch (SQLException e) {
                System.out.println("Exception-File Upload." + e.getMessage());
            }
       blankField();
       return "/a/userList?faces-redirect=true";
    }
    
    /**
     *
     * @return
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public List<User> listerUser(){
        List<User> listUser = new ArrayList<>();
        try{
            Connection connection = ConnectionMB.getConnection();
            Statement st = connection.createStatement();
            st.setFetchSize(0);
            ResultSet rs = st.executeQuery("SELECT * FROM public.\"User\" ");
            while (rs.next()){
                userID = rs.getInt("userID");
                uname = rs.getString("uname");
                upass = rs.getString("upass");
                urole = rs.getString("urole");
                ukey = rs.getBoolean("ukey");
                listUser.add(new User(userID, uname, upass, urole, ukey));
                blankField();
            }
            rs.close();
            st.close();
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        blankField();
        return listUser;
    }
     
    public String showDetails(int id) {  
        //blankField();
        return "/a/userDetails?userID=" + id;   
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public User chargerUser(){
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"User\" WHERE \"User\".\"userID\" = ? ");
            st.setInt(1, userID);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                userID = rs.getInt("userID");
                uname = rs.getString("uname");
                upass = rs.getString("upass");
                urole = rs.getString("urole");
                ukey = rs.getBoolean("ukey");
                user = new User(userID, uname, upass, urole);
            }
            rs.close();
            st.close(); 
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return user;
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public User chargerUser(Integer id){
        User user1 = new User();
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"User\" WHERE \"User\".\"userID\" = ? ");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                //userID = rs.getInt("userID");
                String uname1 = rs.getString("uname");
                String upass1 = rs.getString("upass");
                String urole1 = rs.getString("urole");
                Boolean ukey2 = rs.getBoolean("ukey");
                user1 = new User(id, uname1, upass1, urole1, ukey2);
            }
            rs.close();
            st.close(); 
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        return user1;
    }
    
     @SuppressWarnings({"ConvertToTryWithResources", "StringEquality"})
    public String modifierUser() throws InvalidKeySpecException{
       if (uname != "" && upass != "" && urole != "") {
            try {
                Connection connection = ConnectionMB.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE public.\"User\" SET \"uname\" = ?, \"upass\" = ?, \"urole\" = ? WHERE \"User\".\"userID\" = ?");
                statement.setString(1, uname);
                String mySecurePassword = hash(upass);//PasswordUtils.generateSecurePassword(upass, salt1);
                statement.setString(2, mySecurePassword);
                statement.setString(3, urole);
                statement.setInt(4, userID);
                statement.executeUpdate();
                blankField();
                System.out.println("Updating Successfully!");
                statement.close();
                connection.close();
                FacesMessage msg = new FacesMessage("Succesful pompe with ID=", userID + " is created.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (SQLException e) {
                System.out.println("Exception-File Upload." + e.getMessage());
            }
        } else{
            FacesMessage msg = new FacesMessage("Please fill all the fields!!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
       return "/a/userList?faces-redirect=true";
    }
    
    public String backToList() {
        return "/a/userList?faces-redirect=true";
    }
    
    public void blankField(){
        uname = " ";
        upass = "";
        urole = " ";      
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public String login() throws InvalidKeySpecException {
        try{
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM public.\"User\" WHERE \"User\".\"uname\" = ? AND \"User\".\"urole\" = ? ");
            st.setString(1, uname);
            st.setString(2, urole);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                userID = rs.getInt("userID");
                uname = rs.getString("uname");
                String upass1 = rs.getString("upass");
                urole = rs.getString("urole");
                ukey = rs.getBoolean("ukey");
                user = new User(userID, uname, upass1, urole, ukey);
            }
            rs.close();
            st.close(); 
            connection.close();
        }catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        String upass2 = user.getUpass();
        Boolean isValid = verifyHash(upass, upass2);//PasswordUtils.verifyUserPassword(upass, upass2, salt1);
        if(isValid){
            try{
                if ("Administrateur".equals(user.getUrole()) && user.getUkey() && isValid ){
                    adminRenderedValue = true;
                    superRenderedValue = true;
                    operateurRenderedValue = true;
                    connectedIDUser = user.getUserID();
                    return "/a/index?faces-redirect=true";
                }
                else if ("Superviseur".equals(user.getUrole()) && user.getUkey() && isValid ){
                    superRenderedValue = true;
                    connectedIDUser = user.getUserID();
                    return "/s/super?faces-redirect=true";
                }
                else if ("Operateur".equals(user.getUrole()) && user.getUkey() && isValid ){
                    operateurRenderedValue = true;
                    connectedIDUser = user.getUserID();
                    return "/o/operateur?faces-redirect=true";
                }
                else{
                    return "/login?faces-redirect=true";
                }
            }catch(NullPointerException e){
                System.out.println("Exception-File Upload." + e.getMessage());
                return "/login?faces-redirect=true";
            }      
        }
        else{
            return "/login?faces-redirect=true";
        }
        
    }
 
    //logout event, invalidate session
    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
        adminRenderedValue = false;
        superRenderedValue = false;
        operateurRenderedValue = false;
        connectedIDUser = 0;
        return "/login?faces-redirect=true";
    }
    
    public void addMessage() {
        String summary = ukey ? "Checked" : "Unchecked";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
    
    public void onCellEdit(CellEditEvent event) {
        ukey1 = (Boolean) event.getNewValue();
        activerUserKey();
    }
    
    public void onRowEdit(RowEditEvent event) {
        ukey = (Boolean) event.getObject();
        activerUserKey();
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public String activerUserKey() {
       // try{
            
           // String upass2 = user1.getUpass();
            //Boolean isValid = verifyHash(upass, upass2);
            //if(isValid){
            try {
                User user1 = chargerUser(connectedIDUser);
                Connection connection = ConnectionMB.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE public.\"User\" SET \"ukey\" = ? WHERE \"User\".\"userID\" = ?");
                statement.setBoolean(1, ukey);
                statement.setInt(2, userID);
                statement.executeUpdate();
                System.out.println("Updating Successfully!");
                statement.close();
                connection.close();
                } catch (SQLException e) {
                    System.out.println("Exception-File Upload." + e.getMessage());
                }
        //  }else{
          //      System.out.println("Mot de passe incorrect");
          //  }
       // }catch(NullPointerException e){
          //  System.out.println("Exception-File Upload." + e.getMessage() +"    "+ connectedIDUser);
        //}
       return "/a/userList?faces-redirect=true";
    }
    
    public String showActivate(int id) {  
        return "/a/activerUser?userID=" +id;   
    }
    
    @SuppressWarnings("ConvertToTryWithResources")
    public String changePassword(){
        FacesMessage message = null;
        try {
            Connection connection = ConnectionMB.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE public.\"User\" SET \"upass\" = ? WHERE \"User\".\"userID\" = ?");
            String mySecurePassword = hash(upass);//PasswordUtils.generateSecurePassword(upass, salt1);
            statement.setString(1, mySecurePassword);
            statement.setInt(2, connectedIDUser);
            statement.executeUpdate();
            System.out.println("Updating Successfully!");
            statement.close();
            connection.close();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mot de passe modifié avec succès", "Prière de se connecter à nouveau!");
        } catch (SQLException e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        //logout();
        return "";
    }
    
    public String annulerUser(){
       return "/a/index?faces-redirect=true";
    }
    
    public String annulerPassword(){
       return "/s/super?faces-redirect=true";
    }
    
    public String annulerPassword1(){
       return "/o/operateur?faces-redirect=true";
    }
      
}
