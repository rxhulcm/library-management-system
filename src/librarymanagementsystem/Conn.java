/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;
import java.sql.*;
/**
 *
 * @author rahul
 */
public class Conn {
    Connection c;
    Statement s;
    Conn(){
        
        try{
            c=DriverManager.getConnection("jdbc:mysql:///librarymanagementsystem","root","porowski2020");
            s=c.createStatement();
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
}
