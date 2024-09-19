/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author rahul
 */
public class UnpaidMembers extends JFrame implements ActionListener{
    JButton back;
    UnpaidMembers(){
        setSize(800,800);
        setLayout(null);
        setTitle("Unpaid Members");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(186, 152, 73));
        JLabel welcome = new JLabel("List of Members with Dues");
        welcome.setBounds(100,30,600,40);
        welcome.setFont(new Font("DialogInput",Font.PLAIN,35));
        welcome.setBackground(new Color(36, 26, 2));
        add(welcome);
        
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        model.addColumn("User ID");
        model.addColumn("User Name");
        model.addColumn("Due Amount");
        
        
        table.setGridColor(Color.BLACK);
        table.setShowGrid(true); 
        table.setShowHorizontalLines(true); // Show horizontal lines
        table.setShowVerticalLines(true);
        table.setBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100,100,550,400);
        add(scrollPane, BorderLayout.CENTER);
        
        String query="select s.regno,s.name,a.dueamount from amounttopay a,signup s where a.id=s.regno and a.dueamount>0";
        try{
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery(query);
//            if(!rs.next()){
//                JOptionPane.showMessageDialog(null,"THERE ARE NO UNPAID USERS :)");
//               
//            }
            while(rs.next()){
                String id=rs.getString("regno");
                String name=rs.getString("name");
                Double amount = rs.getDouble("dueamount");
                
                
                    model.addRow(new Object[]{id,name,amount});
                    
                
            }
        }catch(Exception e){
            System.out.println(e);
        }
        //table.setBounds(200,200,550,400);
        

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
        if(ae.getSource() == back){
            setVisible(false);
            new MainMenu().setVisible(true);
        }
    }
    
    public static void main(String args[]){
        new UnpaidMembers();
        
    }
}
