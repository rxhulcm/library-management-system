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
import java.util.*;
/**
 *
 * @author rahul
 */
public class SignUp extends JFrame implements ActionListener{
    JButton terms,submit,cancel;
    JTextField nametf,fathernametf,agetf,phonenumtf,emailtf,addresstf,citytf,pintf;
    JRadioButton male,female,other;
    JComboBox membershiptype;
    JCheckBox termscheck;
    String ID;
    JLabel userPhotoFrame;
    ImageIcon userPhoto;
    JButton capture;
    SignUp(String ID){
        setLayout(null);
        setSize(800,800);
        //setLocation(300,200);
        this.ID=ID;
        setTitle("Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        JLabel text = new JLabel("Registration Form");
        text.setBounds(210, 60, 450, 40);
        text.setFont(new Font("DialogInput",Font.PLAIN,35));
        add(text);
        
        JLabel namelabel = new JLabel("Name ");
        namelabel.setBounds(80, 150, 250, 25);
        namelabel.setFont(new Font("Monospaced",Font.PLAIN,19));
        add(namelabel);
        
        nametf = new JTextField();
        nametf.setBounds(300,150,250,25);
        nametf.setFont(new Font("Monospaced",Font.PLAIN,18));
        nametf.setBorder(new CompoundBorder(nametf.getBorder(), new EmptyBorder(2,10,2,10)));
        add(nametf);
        
        userPhotoFrame = new JLabel();
        userPhotoFrame.setBounds(570,150,150,150);
        add(userPhotoFrame);
        
        
        JLabel fathernamelabel = new JLabel("Father Name ");
        fathernamelabel.setBounds(80, 190, 250, 25);
        fathernamelabel.setFont(new Font("Monospaced",Font.PLAIN,19));
        add(fathernamelabel);
        
        fathernametf = new JTextField();
        fathernametf.setBounds(300,190,250,25);
        fathernametf.setFont(new Font("Monospaced",Font.PLAIN,18));
        fathernametf.setBorder(new CompoundBorder(fathernametf.getBorder(), new EmptyBorder(2,10,2,10)));
        add(fathernametf);
        
        JLabel agelabel = new JLabel("Age ");
        agelabel.setBounds(80, 230, 250, 25);
        agelabel.setFont(new Font("Monospaced",Font.PLAIN,19));
        add(agelabel);
        
        agetf = new JTextField();
        agetf.setBounds(300,230,250,25);
        agetf.setFont(new Font("Monospaced",Font.PLAIN,18));
        agetf.setBorder(new CompoundBorder(agetf.getBorder(), new EmptyBorder(2,10,2,10)));
        add(agetf);
        
        JLabel genderlabel = new JLabel("Gender ");
        genderlabel.setBounds(80, 270, 250, 25);
        genderlabel.setFont(new Font("Monospaced",Font.PLAIN,19));
        add(genderlabel);
        
        male = new JRadioButton("Male");
        male.setFont(new Font("Monospaced",Font.PLAIN,18));
        male.setBounds(300,270,70,25);
        male.setBackground(Color.WHITE);
        add(male);
        
        female = new JRadioButton("Female");
        female.setFont(new Font("Monospaced",Font.PLAIN,18));
        female.setBounds(375,270,95,25);
        female.setBackground(Color.WHITE);
        add(female);
        
        other = new JRadioButton("Other");
        other.setFont(new Font("Monospaced",Font.PLAIN,18));
        other.setBounds(470,270,100,25);
        other.setBackground(Color.WHITE);
        add(other);
        
        ButtonGroup gendergroup = new ButtonGroup();
        gendergroup.add(male);
        gendergroup.add(female);
        gendergroup.add(other);
        
        JLabel phonelabel = new JLabel("Phone Number ");
        phonelabel.setBounds(80, 310, 250, 25);
        phonelabel.setFont(new Font("Monospaced",Font.PLAIN,19));
        add(phonelabel);
        
        phonenumtf = new JTextField();
        phonenumtf.setBounds(300,310,250,25);
        phonenumtf.setFont(new Font("Monospaced",Font.PLAIN,18));
        phonenumtf.setBorder(new CompoundBorder(phonenumtf.getBorder(), new EmptyBorder(2,10,2,10)));
        add(phonenumtf);
        
        capture = new JButton("CAPTURE IMAGE");
        capture.setBounds(570,310,150,25);
//        capture.setFont(new Font("Arial",Font.BOLD,25));
        capture.setForeground(Color.white);
        capture.setBackground(Color.black);
        capture.setBorder(new EmptyBorder(0,0,0,0));
        capture.addActionListener(this);
        add(capture);
        
        JLabel emaillabel = new JLabel("Email ID ");
        emaillabel.setBounds(80, 350, 250, 25);
        emaillabel.setFont(new Font("Monospaced",Font.PLAIN,19));
        add(emaillabel);
        
        emailtf = new JTextField();
        emailtf.setBounds(300,350,250,25);
        emailtf.setFont(new Font("Monospaced",Font.PLAIN,18));
        emailtf.setBorder(new CompoundBorder(emailtf.getBorder(), new EmptyBorder(2,10,2,10)));
        add(emailtf);
        
        JLabel addresslabel = new JLabel("Address ");
        addresslabel.setBounds(80, 390, 250, 25);
        addresslabel.setFont(new Font("Monospaced",Font.PLAIN,19));
        add(addresslabel);
        
        addresstf = new JTextField();
        addresstf.setBounds(300,390,250,25);
        addresstf.setFont(new Font("Monospaced",Font.PLAIN,18));
        addresstf.setBorder(new CompoundBorder(addresstf.getBorder(), new EmptyBorder(2,10,2,10)));
        add(addresstf);
        
        JLabel citylabel = new JLabel("City ");
        citylabel.setBounds(80, 430, 250, 25);
        citylabel.setFont(new Font("Monospaced",Font.PLAIN,19));
        add(citylabel);
        
        citytf = new JTextField();
        citytf.setBounds(300,430,250,25);
        citytf.setFont(new Font("Monospaced",Font.PLAIN,18));
        citytf.setBorder(new CompoundBorder(citytf.getBorder(), new EmptyBorder(2,10,2,10)));
        add(citytf);
        
        JLabel pincodelabel = new JLabel("Pin Code ");
        pincodelabel.setBounds(80, 470, 250, 25);
        pincodelabel.setFont(new Font("Monospaced",Font.PLAIN,19));
        add(pincodelabel);
        
        pintf = new JTextField();
        pintf.setBounds(300,470,250,25);
        pintf.setFont(new Font("Monospaced",Font.PLAIN,18));
        pintf.setBorder(new CompoundBorder(pintf.getBorder(), new EmptyBorder(2,10,2,10)));
        add(pintf);
        
        JLabel membershiptypelabel = new JLabel("Membership Type ");
        membershiptypelabel.setBounds(80, 510, 250, 25);
        membershiptypelabel.setFont(new Font("Monospaced",Font.PLAIN,19));
        add(membershiptypelabel);
        
        String membershipOpt[]={"Kids Membership", "Adult Membership","Premium Membership"};
        membershiptype = new JComboBox(membershipOpt);
        membershiptype.setFont(new Font("Monospaced",Font.PLAIN,18));
        membershiptype.setBounds(300,510,250,25);
        membershiptype.setBackground(Color.WHITE);
        membershiptype.setSelectedItem(null);
        add(membershiptype);
        
        
        terms = new JButton("Terms and Conditions");
        terms.setFont(new Font("SansSerif",Font.BOLD,15));
        terms.setForeground(Color.red);
        terms.setBackground(Color.white);
        terms.setBorder(new EmptyBorder(0,0,0,0));
        terms.setBounds(100,580,180,18);
        add(terms);
        
        termscheck = new JCheckBox();
        termscheck.setBounds(80,580,20,18);
        add(termscheck);
        termscheck.addActionListener(this);
        
        submit = new JButton("SUBMIT");
        submit.setFont(new Font("Arial",Font.BOLD,25));
        submit.setForeground(Color.white);
        submit.setBackground(Color.black);
        submit.setBorder(new EmptyBorder(0,0,0,0));
        submit.setBounds(270,670,120,30);
        add(submit);
        submit.addActionListener(this);
        
        cancel = new JButton("CANCEL");
        cancel.setFont(new Font("Arial",Font.BOLD,25));
        cancel.setForeground(Color.white);
        cancel.setBackground(Color.black);
        cancel.setBorder(new EmptyBorder(0,0,0,0));
        cancel.setBounds(420,670,120,30);
        add(cancel);
        cancel.addActionListener(this);
        ImageIcon i1 = new ImageIcon("C:\\Users\\rahul\\OneDrive\\Desktop\\Images\\" + ID + ".jpg");
            Image i2 = i1.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
            userPhoto = new ImageIcon(i2);
            userPhotoFrame.setIcon(userPhoto);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == terms){
            
        }
        else if(ae.getSource() == submit){
            String name=nametf.getText(),fname=fathernametf.getText(),age=agetf.getText(),gender="",phone=phonenumtf.getText(),email=emailtf.getText(),address=addresstf.getText(),city=citytf.getText(),pin=pintf.getText(),membership="";
            if(male.isSelected())
                gender="Male";
            else  if(female.isSelected())
                gender="Female";
            else if(other.isSelected())
                gender="Others";
            
            membership=(String)membershiptype.getSelectedItem();
            if(name.equals("")){
                JOptionPane.showMessageDialog(null, "ENTER NAME");
            }
            else if(fname.equals("")){
                JOptionPane.showMessageDialog(null, "ENTER FATHER'S NAME");
            }
            else if(age.equals("")){
                JOptionPane.showMessageDialog(null, "ENTER AGE");
            }
            else if(gender.equals("")){
                JOptionPane.showMessageDialog(null, "SELECT GENDER");
            }
            else if(phone.equals("")){
                JOptionPane.showMessageDialog(null, "ENTER PHONE NUMBER");
            }
            else if(email.equals("")){
                JOptionPane.showMessageDialog(null, "ENTER EMAIL");
            }
            else if(address.equals("")){
                JOptionPane.showMessageDialog(null, "ENTER ADDRESS");
            }
            else if(city.equals("")){
                JOptionPane.showMessageDialog(null, "ENTER CITY");
            }
            else if(pin.equals("")){
                JOptionPane.showMessageDialog(null, "ENTER PIN CODE");
            }
            else if(membership.equals("")){
                JOptionPane.showMessageDialog(null, "SELECT MEMBERSHIP TYPE");
            }
            else if(termscheck.isSelected()==false){
                JOptionPane.showMessageDialog(null, "AGREE TO TERMS AND CONDITION TO CONTINUE");
            }
            else
            {
                try{
                    Conn c = new Conn();
                    if(membership.equals("Kids Membership")){
                        String kidsmember="insert into kidsmembership values(?,'')";
                        PreparedStatement pstmt1=c.c.prepareStatement(kidsmember);
                        pstmt1.setString(1,ID);
                        pstmt1.executeUpdate();
                    }
                    if(membership.equals("Adult Membership")){
                        String adultmember ="insert into adultmembership values(?,'','')";
                        PreparedStatement pstmt2=c.c.prepareStatement(adultmember);
                        pstmt2.setString(1,ID);
                        pstmt2.executeUpdate();
                    }
                    if(membership.equals("Premium Membership")){
                        String premiummember="insert into premiummembership values(?,'','','')";
                        PreparedStatement pstmt3=c.c.prepareStatement(premiummember);
                        pstmt3.setString(1,ID);
                        pstmt3.executeUpdate();
                    }
                    String query = "insert into signup(regno, name, fname, age, gender, phone, email, address, city, pin, membershiptype) values('" + ID + "', '" + name + "', '" + fname + "', '" + age + "', '" + gender + "', '" + phone + "', '" + email + "', '" + address + "', '" + city + "', '" + pin + "', '" + membership + "');";

                    int rowsAffected = c.s.executeUpdate(query);
                    if(rowsAffected>0){
                        System.out.println("Successfully Signed Up");
                        setVisible(false);
                        new NewUserProfile(ID).setVisible(true);
                    }
                    else{
                        System.out.println("Sign up unsuccessful");
                    }
                    
                    
                }catch(Exception e){
                    System.out.println(e);
                }
            }
            
        }
        else if(ae.getSource() == cancel){
            
        }
        else if(ae.getSource() == capture){
            setVisible(false);
            new CapturePhoto(ID).setVisible(true);
            
            
        }
    }
    
    public static void main(String args[]){
        new SignUp("");
    }
}
