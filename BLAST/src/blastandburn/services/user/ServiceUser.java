package blastandburn.services.user;

import blastandburn.entities.user.User;
import blastandburn.utils.MyConnection;
import blastandburn.iservices.user.IServiceUser;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServiceUser implements IServiceUser{
    Connection cnx;
    List<User> Users = new ArrayList<>();
    
    public ServiceUser(){
        cnx=MyConnection.getInstance().getConnection();
    }
    
    //ajouter un utilisateur "SingUp"
    @Override
    public void AddUser(User u){           
        try {            
            Statement stm=cnx.createStatement();
            String query = "INSERT INTO user(email,password2,first_name,last_name,date_of_birth) VALUES('"+u.getEmail()+"','"+u.getPassword()+"','"+u.getFirstName()+"','"+u.getLastName()+"','"+u.getDateOfBirth()+"')";
            stm.executeUpdate(query);  
            query = "INSERT INTO user_role(user_id,role_id) VALUES(LAST_INSERT_ID(),4)";
            stm.executeUpdate(query);
            System.out.println("user ajouter");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
  
    //---------login----------------------------------------------------
    @Override
    public boolean Validate_Login(String email , String password){           
        try {     
            String query = "SELECT * FROM user WHERE email = ? and password2 = ? and is_deleted=0 ";
            // Step 2:Create a statement using connection object
            PreparedStatement pS = cnx.prepareStatement(query);
            pS.setString(1, email);
            pS.setString(2, password);            
            ResultSet resultSet = pS.executeQuery();
            
            if (resultSet.next()) {
                //recuperation d'id de user loged in
                int ID = resultSet.getInt("user_id") ;
                String EMAIL =resultSet.getString("email");
                String PASSWORD =resultSet.getString("password2");
                String FIRSTNAME =resultSet.getString("first_name");
                String LASTNAME =resultSet.getString("last_name");
                Date DATEOFBIRTH =resultSet.getDate("date_of_birth");
                //user session created with user id when logging in
                //
                String req="select r.role_name from role r,user_role ur where ur.role_id=r.role_id and ur.user_id="+ID+"";
                Statement stm=cnx.createStatement();
                ResultSet resultSet2 = stm.executeQuery(req);

                if(resultSet2.next()){
                   String role =resultSet2.getString("role_name"); 
                   UserSession.getInstace(ID,EMAIL,PASSWORD,FIRSTNAME,LASTNAME,DATEOFBIRTH,role);
                }
                
                return true;
            }
            

        } catch (SQLException ex) {
            // print SQL exception information
            printSQLException(ex);
        }
        return false;
    }
    
    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

//---------------------------------------------
    @Override
    public List<User> AfficherPersonne() {        
        try {
            Statement stm=cnx.createStatement();
             String query = "SELECT * FROM `user` ";
             ResultSet rst=stm.executeQuery(query);
             
             while (rst.next()){
                 User p=new User();
                 p.setUserId(rst.getInt("user_id"));
                 p.setEmail(rst.getString("email"));
                 p.setPassword(rst.getString("password"));
                 
                 Users.add(p);
             }
             
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Users;
       
    }
    
    public int userCount() {  
        int x=0;
        try {
            Statement stm=cnx.createStatement();
             String query = "SELECT count(*) as total FROM `user` where is_deleted=0";
             ResultSet rst=stm.executeQuery(query);
             
             while (rst.next()){
                 x=rst.getInt("total");
             }
             
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return x;
       
    }
 //---------------------------------------------
    @Override
    public void ModifierUser(User u ,int id) {
        try {
            String query = "UPDATE `user` SET `email`= '"+u.getEmail()+"' ,"
                + "`first_name`= '"+u.getFirstName()+"' ,`last_name`= '"
                +u.getLastName()+"' ,`date_of_birth`= '"+u.getDateOfBirth()+"' "
                + " WHERE user_id = "+id+" ";
            Statement st = cnx.createStatement();
            st.executeUpdate(query);
            System.out.println("User updated succesfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }   
   //---------------------------------------------
    public void ModifierUserPassword(String pw ,int id) {
        try {
            String query = "UPDATE `user` SET `password2`='"+pw+"' WHERE user_id = "+id+" ";
            Statement st = cnx.createStatement();
            st.executeUpdate(query);
            System.out.println("User password updated succesfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    } 
    
    
 //---------------------------------------------   
    @Override
    public void DeleteUser(int idU) {
        try {
            String query = "UPDATE  user set  is_deleted=1,deleted_at=CURRENT_TIMESTAMP()  where user_id=" + idU +" ";
            Statement st = cnx.createStatement();
            st.executeUpdate(query);
            System.out.println("User deleted succesfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
  //---------------------------------------------
    
    public void UpdateBalance(double newbalance,User u){
        try {
            //to modify
            String query = "UPDATE  user set  balance=" + newbalance +" where user_id=" + u.getUserId() +" ";
            Statement st = cnx.createStatement();
            st.executeUpdate(query);
            System.out.println("User's balance updated succesfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //--------------------------------------------

}
