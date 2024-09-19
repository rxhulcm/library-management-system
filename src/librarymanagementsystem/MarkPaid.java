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
public class MarkPaid extends JFrame implements ActionListener{
    JTextField searchbar;
    JButton search,mark,back;
    JLabel duestext;
    MarkPaid(){
        setSize(800,800);
        setLayout(null);
        setTitle("Settle Dues");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(186, 152, 73));
        JLabel welcome = new JLabel("Settle Dues");
        welcome.setBounds(280,30,420,40);
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
        duestext = new JLabel();
        duestext.setBounds(100,300,600,100);
        duestext.setFont(new Font("DialogInput",Font.PLAIN,30));
        duestext.setBackground(new Color(36, 26, 2));
        add(duestext);
        mark = new JButton("Mark as Paid");
        mark.setBounds(300,400,150,40);
        mark.setBackground(new Color(252, 225, 124));
        mark.setForeground(new Color(36, 26, 2));
        mark.setFont(new Font("Monospaced",Font.PLAIN,18));
        mark.setBorder(new EmptyBorder(0,0,0,0));
        mark.setVisible(false);
        mark.addActionListener(this);
        add(mark);
        
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
                    String query="select * from amounttopay where id='"+searchbar.getText()+"';";
                    ResultSet rs = c.s.executeQuery(query);
                    if(rs.next()){
                        dues = rs.getDouble("dueamount");
                        if(dues==0){
                            duestext.setText("The user has no dues :)");
                        }
                        else{
                            duestext.setForeground(Color.red);
                            duestext.setFont(new Font("DialogInput",Font.BOLD,30));
                            duestext.setText("Dues to be Settled : Rs "+dues);
                            mark.setVisible(true);
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
                    duestext.setFont(new Font("DialogInput",Font.PLAIN,30));
                    duestext.setBackground(new Color(36, 26, 2));
                    duestext.setForeground(Color.BLACK);
                    duestext.setText("Dues Settled");
                    mark.setVisible(false);
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
    }
    
    public static void main(String args[]){
        new MarkPaid();
    }
}
