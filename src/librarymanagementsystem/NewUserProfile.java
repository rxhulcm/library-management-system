/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.*;
import java.util.Random;

/**
 *
 * @author rahul
 */
public class NewUserProfile extends JFrame implements ActionListener{
    JButton print,cont;
    String ID;
    JPanel panelToPrint;
    JLabel userPhotoFrame;
    ImageIcon userPhoto;
    NewUserProfile(String ID){
        setLayout(null);
        setSize(800,800);
        //setLocation(300,200);
        
        this.ID=ID;
        setTitle("User Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        panelToPrint = new JPanel();
        panelToPrint.setBounds(0, 0, 800, 400); 
        panelToPrint.setBackground(Color.WHITE);
        panelToPrint.setLayout(null);
        add(panelToPrint);
        JLabel text = new JLabel("Library ID Card");
        text.setBounds(250, 60, 450, 40);
        text.setFont(new Font("DialogInput",Font.PLAIN,35));
        //add(text);
        panelToPrint.add(text);
        String name="",gender="",phone="",membership="";
        try{
            Conn c =  new Conn();
            String query = "select * from signup where regno=?";
            PreparedStatement pstmt = c.c.prepareStatement(query);
            pstmt.setString(1, ID);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                name=rs.getString("name");
                gender=rs.getString("gender");
                phone=rs.getString("phone");
                membership=rs.getString("membershiptype");
            }
            else{
                System.out.println("No records of regno found in Database");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
        JLabel registrationlabel = new JLabel("Registration No : " + ID);
        registrationlabel.setBounds(80, 150, 500, 25);
        registrationlabel.setFont(new Font("Monospaced",Font.PLAIN,19));
        //add(registrationlabel);
        panelToPrint.add(registrationlabel);
        
        
        JLabel namelabel = new JLabel("Name : "+name);
        namelabel.setBounds(80, 190, 500, 25);
        namelabel.setFont(new Font("Monospaced",Font.PLAIN,19));
        //add(namelabel);
        panelToPrint.add(namelabel);
        
        
        
        JLabel genderlabel = new JLabel("Gender : "+gender);
        genderlabel.setBounds(80, 230, 500, 25);
        genderlabel.setFont(new Font("Monospaced",Font.PLAIN,19));
        //add(genderlabel);
        panelToPrint.add(genderlabel);
        
        
        
        JLabel phonelabel = new JLabel("Phone Number : "+phone);
        phonelabel.setBounds(80, 270, 500, 25);
        phonelabel.setFont(new Font("Monospaced",Font.PLAIN,19));
        //add(phonelabel);
        panelToPrint.add(phonelabel);
        
        
        userPhotoFrame = new JLabel();
        userPhotoFrame.setBounds(570,150,150,150);
        
        ImageIcon i1 = new ImageIcon("C:\\Users\\rahul\\OneDrive\\Desktop\\Images\\" + ID + ".jpg");
        Image i2 = i1.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
        userPhoto = new ImageIcon(i2);
        userPhotoFrame.setIcon(userPhoto);
        panelToPrint.add(userPhotoFrame);
        
        JLabel membershiptypelabel = new JLabel("Membership Type : "+membership);
        membershiptypelabel.setBounds(80, 310, 500, 25);
        membershiptypelabel.setFont(new Font("Monospaced",Font.PLAIN,19));
        //add(membershiptypelabel);
        panelToPrint.add(membershiptypelabel);
        
        print = new JButton ("PRINT");
        print.setFont(new Font("Arial",Font.BOLD,25));
        print.setForeground(Color.white);
        print.setBackground(Color.black);
        print.setBorder(new EmptyBorder(0,0,0,0));
        print.setBounds(200,400,180,30);
        add(print);
        print.addActionListener(this);
        
        cont = new JButton("CONTINUE");
        cont.setFont(new Font("Arial",Font.BOLD,25));
        cont.setForeground(Color.white);
        cont.setBackground(Color.black);
        cont.setBorder(new EmptyBorder(0,0,0,0));
        cont.setBounds(400,400,180,30);
        add(cont);
        cont.addActionListener(this);
        
        
        setVisible(true);
        
       
    }   
    
    
    
    public void actionPerformed (ActionEvent ae){
        if(ae.getSource() == print){
            
            PrinterJob job=PrinterJob.getPrinterJob();
            job.setJobName("Print ID");
            job.setPrintable(new Printable() {
                public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
                    if (page > 0) {
                        return NO_SUCH_PAGE;
                    }

                    Graphics2D g2d = (Graphics2D) g;
                    g2d.translate(pf.getImageableX(), pf.getImageableY());

                    
                    panelToPrint.printAll(g2d);

                    return PAGE_EXISTS;
                }
            });
            boolean doPrint= job.printDialog();
            if(doPrint){
                try{
                    job.print();
                }catch(PrinterException e){
                    System.out.println(e);
                }
            }
            
        }
        else if(ae.getSource() == cont){
            setVisible(false);
            new MainMenu().setVisible(true);
        }
    }
    
    public static void main(String args[]){
        new NewUserProfile("");
    }
    
}
