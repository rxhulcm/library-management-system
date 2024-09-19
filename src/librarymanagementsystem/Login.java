/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;
import org.opencv.core.Core;
/**
 *
 * @author rahul
 */
public class Login extends JFrame implements ActionListener{
     JButton login,clear;
    JTextField username;
    JPasswordField password;
    
    Login() {
        setLayout(null);
        setSize(800,800);
        //setLocation(300,200);
        setTitle("LibManage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        JLabel text = new JLabel("Welcome to LibManage");
        text.setBounds(210, 160, 450, 40);
        text.setFont(new Font("DialogInput",Font.PLAIN,35));
        add(text);
        
        JLabel userid = new JLabel("User ID ");
        userid.setBounds(180, 280, 200, 40);
        userid.setFont(new Font("Monospaced",Font.PLAIN,28));
        add(userid);
        
        username = new JTextField();
        username.setBounds(320, 280, 250, 40);
        username.setFont(new Font("Times New Roman",Font.PLAIN,18));
        username.setBorder(new CompoundBorder(username.getBorder(), new EmptyBorder(2,10,2,10)));
        add(username);
        
        JLabel pass = new JLabel("Password ");
        pass.setBounds(180, 340, 200, 40);
        pass.setFont(new Font("Monospaced",Font.PLAIN,28));
        add(pass);
        
        password = new JPasswordField();
        password.setBounds(320, 340, 250, 40);
        password.setFont(new Font("Times New Roman",Font.BOLD,18));
        password.setBorder(new CompoundBorder(password.getBorder(), new EmptyBorder(2,10,2,10)));

        add(password);
        
        login = new JButton("LOG IN");
        login.setBounds(320, 400, 120, 40);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.setFont(new Font("Monospaced",Font.PLAIN,18));
        login.addActionListener(this);
        add(login);
        
        
        clear = new JButton("CLEAR");
        clear.setBounds(450,400,120,40);
        clear.setBackground(Color.BLACK);
        clear.setForeground(Color.WHITE);
        clear.setFont(new Font("Monospaced",Font.PLAIN,18));
        clear.addActionListener(this);
        add(clear);
        
        
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == login){
            String userid = username.getText();
            char pin[]= password.getPassword();
            String pinno = new String(pin);
            try{
                Conn c = new Conn();
                String query = "select * from adminlogin where id=?";
                PreparedStatement p = c.c.prepareStatement(query);
                p.setString(1,userid);
                ResultSet rs = p.executeQuery();
                if(rs.next()){
                    String passcode = rs.getString("pin");
                    if(passcode.equals(pinno)){
                        setVisible(false);
                        new MainMenu().setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Incorrect Password");
                        password.setText("");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Incorrect Username");
                    username.setText("");
                    password.setText("");
                }
                
            }catch(Exception e){
                System.out.println(e);
            }
        }
        
        else if (ae.getSource() == clear){
            username.setText("");
            password.setText("");
        }
    }
    
    public static void main(String args[]){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        new Login();
    }
}
