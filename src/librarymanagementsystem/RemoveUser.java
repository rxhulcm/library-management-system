/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
/**
 *
 * @author rahul
 */
public class RemoveUser extends JFrame implements ActionListener{
    JTextField searchbar;
    JButton search,mark,back,proceed;
    JLabel canceltext;
    RemoveUser(){
        setSize(800,800);
        setLayout(null);
        setTitle("Remove User");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(186, 152, 73));
        JLabel welcome = new JLabel("Membership Cancellation");
        welcome.setBounds(200,30,500,40);
        welcome.setFont(new Font("DialogInput",Font.PLAIN,35));
        welcome.setBackground(new Color(36, 26, 2));
        add(welcome);
        searchbar = new JTextField();
        //TextPrompt tp = new TextPrompt("ENTER NAME OF A BOOK OR USER",searchbar);
        searchbar.setBounds(100, 200, 500, 40);
        searchbar.setFont(new Font("Times New Roman",Font.PLAIN,18));
        searchbar.setBorder(new CompoundBorder(searchbar.getBorder(), new EmptyBorder(2,10,2,10)));
        add(searchbar);
        ImageIcon i1= new ImageIcon("C:\\Users\\rahul\\Downloads\\searchbutton.png");
        Image i2 = i1.getImage().getScaledInstance(40,40, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        search = new JButton(i3);
        search.setBounds(630,200,40,40);
        search.setToolTipText("SEARCH");
        search.addActionListener(this);
        add(search);
        
        canceltext = new JLabel();
        canceltext.setBounds(100,300,600,100);
        canceltext.setFont(new Font("DialogInput",Font.PLAIN,30));
        canceltext.setBackground(new Color(36, 26, 2));
        add(canceltext);
        
        mark = new JButton("Mark as Paid");
        mark.setBounds(300,400,150,40);
        mark.setBackground(new Color(252, 225, 124));
        mark.setForeground(new Color(36, 26, 2));
        mark.setFont(new Font("Monospaced",Font.PLAIN,18));
        mark.setBorder(new EmptyBorder(0,0,0,0));
        mark.setVisible(false);
        mark.addActionListener(this);
        add(mark);
        
        proceed = new JButton("PROCEED");
        proceed.setBounds(500,400,150,40);
        proceed.setBackground(new Color(252, 225, 124));
        proceed.setForeground(new Color(36, 26, 2));
        proceed.setFont(new Font("Monospaced",Font.PLAIN,18));
        proceed.setBorder(new EmptyBorder(0,0,0,0));
        proceed.setVisible(false);
        proceed.addActionListener(this);
        add(proceed);
        
        back = new JButton("BACK");
        back.setBounds(600, 700, 120, 40);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Monospaced",Font.PLAIN,18));
        back.addActionListener(this);
        add(back);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()== search){
            String text=searchbar.getText();
            if(text.equals("")){
                JOptionPane.showMessageDialog(null, "Enter the User ID");
                
            }
            else{
                try{
                    Conn c  = new Conn();
                    Double dues;
                    String q = "select * from issuedbooks where id=?";
                    PreparedStatement p1 = c.c.prepareStatement(q);
                    p1.setString(1,searchbar.getText());
                    ResultSet rs1 = p1.executeQuery();
                    if(rs1.next()){
                        JOptionPane.showMessageDialog(null, "User has Books to return");
                        setVisible(false);
                        new MainMenu().setVisible(true);
                    }
                    else{
                    
                        String query="select * from amounttopay where id='"+searchbar.getText()+"';";
                        ResultSet rs = c.s.executeQuery(query);
                        if(rs.next()){
                            dues = rs.getDouble("dueamount");
                            if(dues==0){
                                canceltext.setText("The user has no dues :)");
                            
                                proceed.setVisible(true);
                            
                             
                            
                            }
                        else{
                            JOptionPane.showMessageDialog(null, "The User has Dues to Settle. :(");
                            
                            canceltext.setForeground(Color.red);
                            canceltext.setFont(new Font("DialogInput",Font.BOLD,30));
                            canceltext.setText("Dues to be Settled : Rs "+dues);
                            mark.setVisible(true);
                        }
                    }
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        }
        else if(ae.getSource() == mark){
            try{
                Conn c = new Conn();
                String query = "update amounttopay set dueamount=0 where id=?";
                PreparedStatement pstmt = c.c.prepareStatement(query);
                pstmt.setString(1, searchbar.getText());
                int row=pstmt.executeUpdate();
                if(row>0){
                    System.out.println("Dues Settled");
                    canceltext.setFont(new Font("DialogInput",Font.PLAIN,30));
                    canceltext.setBackground(new Color(36, 26, 2));
                    canceltext.setForeground(Color.BLACK);
                    canceltext.setText("Dues Settled");
                    mark.setVisible(false);
                    proceed.setVisible(true);
                }
                else{
                    System.out.println("Due Settling Unsuccessful");
                }
            }catch(Exception e){
                System.out.println(e);
            }
        }
        else if(ae.getSource() == back){
            setVisible(false);
            new MainMenu().setVisible(true);
        }
        else if(ae.getSource() == proceed){
            try{
                Conn c = new Conn();
                String query = "delete from signup where regno=?";
                PreparedStatement p = c.c.prepareStatement(query);
                p.setString(1, searchbar.getText());
                p.executeUpdate();
                query= "delete from amounttopay where id=?";
                PreparedStatement p1 = c.c.prepareStatement(query);
                p1.setString(1, searchbar.getText());
                p1.executeUpdate();
                
                query= "delete from issuedbooks where id=?";
                PreparedStatement p2 = c.c.prepareStatement(query);
                p2.setString(1, searchbar.getText());
                p2.executeUpdate();
                
                query= "delete from kidsmembership where id=?";
                PreparedStatement p3 = c.c.prepareStatement(query);
                p3.setString(1, searchbar.getText());
                p3.executeUpdate();
                
                query= "delete from adultmembership where id=?";
                PreparedStatement p4 = c.c.prepareStatement(query);
                p4.setString(1, searchbar.getText());
                p4.executeUpdate();
                
                query= "delete from premiummembership where id=?";
                PreparedStatement p5 = c.c.prepareStatement(query);
                p5.setString(1, searchbar.getText());
                p5.executeUpdate();
                
            }catch(Exception e){
                System.out.println(e);
            }
            setVisible(false);
            new MainMenu().setVisible(true);
        }
    }
    
    public static void main(String args[]){
        new RemoveUser();
    }
}
