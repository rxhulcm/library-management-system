/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.util.Random;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
/**
 *
 * @author rahul
 */
public class MainMenu extends JFrame implements ActionListener{
    JButton addbtn,borrowbtn,returnbtn,searchbook,searchuser,dues,overdue,markaspaid,viewunpaid,logout,addmember,removemember;
    JTextField searchbar;
    JLabel library;
    MainMenu(){
        setSize(800,800);
        setLayout(null);
        setTitle("Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(186, 152, 73));
        JLabel welcome = new JLabel("Welcome to LIBMANAGE");
        welcome.setBounds(190,30,420,40);
        welcome.setFont(new Font("DialogInput",Font.PLAIN,35));
        welcome.setBackground(new Color(36, 26, 2));
        add(welcome);
        
        addbtn = new JButton("ADD A NEW BOOK");
        addbtn.setBounds(30, 120, 250, 40);
        addbtn.setBackground(new Color(252, 225, 124));
        addbtn.setForeground(new Color(36, 26, 2));
        addbtn.setFont(new Font("Monospaced",Font.PLAIN,18));
        addbtn.setBorder(new EmptyBorder(0,0,0,0));
        addbtn.addActionListener(this);
        add(addbtn);
        
        borrowbtn = new JButton("ISSUE A BOOK");
        borrowbtn.setBounds(30, 200, 250, 40);
        borrowbtn.setBackground(new Color(252, 225, 124));
        borrowbtn.setForeground(new Color(36, 26, 2));
        borrowbtn.setFont(new Font("Monospaced",Font.PLAIN,18));
        borrowbtn.setBorder(new EmptyBorder(0,0,0,0));
        borrowbtn.addActionListener(this);
        add(borrowbtn);
        
        returnbtn = new JButton("RETURN ISSUED BOOK");
        returnbtn.setBounds(30, 280, 250, 40);
        returnbtn.setBackground(new Color(252, 225, 124));
        returnbtn.setForeground(new Color(36, 26, 2));
        returnbtn.setFont(new Font("Monospaced",Font.PLAIN,18));
        returnbtn.setBorder(new EmptyBorder(0,0,0,0));
        returnbtn.addActionListener(this);
        add(returnbtn);
        
        addmember = new JButton("ADD NEW MEMBER");
        addmember.setBounds(30, 360, 250, 40);
        addmember.setBackground(new Color(252, 225, 124));
        addmember.setForeground(new Color(36, 26, 2));
        addmember.setFont(new Font("Monospaced",Font.PLAIN,18));
        addmember.setBorder(new EmptyBorder(0,0,0,0));
        addmember.addActionListener(this);
        add(addmember);
        
        
        overdue = new JButton("OVERDUE BOOKS");
        overdue.setBounds(500, 120, 250, 40);
        overdue.setBackground(new Color(252, 225, 124));
        overdue.setForeground(new Color(36, 26, 2));
        overdue.setFont(new Font("Monospaced",Font.PLAIN,18));
        overdue.setBorder(new EmptyBorder(0,0,0,0));
        overdue.addActionListener(this);
        add(overdue);
        
        markaspaid = new JButton("MARK PAID USERS");
        markaspaid.setBounds(500, 200, 250, 40);
        markaspaid.setBackground(new Color(252, 225, 124));
        markaspaid.setForeground(new Color(36, 26, 2));
        markaspaid.setFont(new Font("Monospaced",Font.PLAIN,18));
        markaspaid.setBorder(new EmptyBorder(0,0,0,0));
        //markaspaid.addActionListener(this);
        add(markaspaid);
        
        viewunpaid = new JButton("VIEW UNPAID USERS");
        viewunpaid.setBounds(500, 280, 250, 40);
        viewunpaid.setBackground(new Color(252, 225, 124));
        viewunpaid.setForeground(new Color(36, 26, 2));
        viewunpaid.setFont(new Font("Monospaced",Font.PLAIN,18));
        viewunpaid.setBorder(new EmptyBorder(0,0,0,0));
        viewunpaid.addActionListener(this);
        add(viewunpaid);
        
        removemember = new JButton("REMOVE MEMBER");
        removemember.setBounds(500, 360, 250, 40);
        removemember.setBackground(new Color(252, 225, 124));
        removemember.setForeground(new Color(36, 26, 2));
        removemember.setFont(new Font("Monospaced",Font.PLAIN,18));
        removemember.setBorder(new EmptyBorder(0,0,0,0));
        removemember.addActionListener(this);
        add(removemember);
        
        searchbar = new JTextField();
        //TextPrompt tp = new TextPrompt("ENTER NAME OF A BOOK OR USER",searchbar);
        searchbar.setBounds(100, 500, 600, 40);
        searchbar.setFont(new Font("Times New Roman",Font.PLAIN,18));
        searchbar.setBorder(new CompoundBorder(searchbar.getBorder(), new EmptyBorder(2,10,2,10)));
        add(searchbar);
        
        searchbook = new JButton("SEARCH BOOK");
        searchbook.setBounds(200, 600, 200, 40);
        searchbook.setBackground(Color.BLACK);
        searchbook.setForeground(Color.WHITE);
        searchbook.setFont(new Font("Monospaced",Font.PLAIN,18));
        //searchbook.addActionListener(this);
        add(searchbook);
        
        
        searchuser = new JButton("SEARCH USER");
        searchuser.setBounds(450,600,200,40);
        searchuser.setBackground(Color.BLACK);
        searchuser.setForeground(Color.WHITE);
        searchuser.setFont(new Font("Monospaced",Font.PLAIN,18));
        //searchuser.addActionListener(this);
        add(searchuser);
        
        logout = new JButton("LOG OUT");
        logout.setBounds(600, 700, 120, 40);
        logout.setBackground(Color.RED);
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font("Monospaced",Font.PLAIN,18));
        //logout.addActionListener(this);
        add(logout);
        
        
        
//        ImageIcon i1=new ImageIcon("C:\\Users\\rahul\\OneDrive\\Pictures\\libimage.jpg");
//        Image i2 = i1.getImage().getScaledInstance(800, 450, Image.SCALE_DEFAULT);
//        ImageIcon i3 = new ImageIcon(i2);
//        library = new JLabel();
//        library.setBounds(0, 0, 800, 450);
//        library.setIcon(i3);
//        add(library);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == addbtn){
            setVisible(false);
            new BookCodeScan("addbtn").setVisible(true);
        }
        else if(e.getSource() == borrowbtn){
            setVisible(false);
            new BookCodeScan("borrowbtn").setVisible(true);
        }
        else if(e.getSource() == returnbtn){
            setVisible(false);
            new BookCodeScan("returnbtn").setVisible(true);
        }
        else if(e.getSource() == overdue){
            setVisible(false);
            new OverdueBooks().setVisible(true);
        }
        else if(e.getSource() == markaspaid){
            setVisible(false);
            new MarkPaid().setVisible(true);
        }
        else if(e.getSource() == viewunpaid){
            setVisible(false);
            new UnpaidMembers().setVisible(true);
        }
        else if(e.getSource() == removemember){
            setVisible(false);
            new RemoveUser().setVisible(true);
        }
        else if(e.getSource() == addmember){
            Random rand = new Random();
            int random1=rand.nextInt(9999)+1000, random2=rand.nextInt(9999)+1000;
            String ID=""+random1+random2;
            setVisible(false);
            new CapturePhoto(ID).setVisible(true);
        }
    }
    public static void main(String args[]){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        new MainMenu();
    }
}
