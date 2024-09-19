/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagementsystem;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author rahul
 */
public class OverdueBooks extends JFrame implements ActionListener{
    JButton back;
    OverdueBooks(){
        setSize(800,800);
        setLayout(null);
        setTitle("Overdue Books");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(186, 152, 73));
        JLabel welcome = new JLabel("List of Overdue Books");
        welcome.setBounds(150,30,500,40);
        welcome.setFont(new Font("DialogInput",Font.PLAIN,35));
        welcome.setBackground(new Color(36, 26, 2));
        add(welcome);
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        model.addColumn("Book ID");
        model.addColumn("Book Name");
        model.addColumn("Issued To ID");
        model.addColumn("Issued to name");
        model.addColumn("No of Days Due");
        
        table.setGridColor(Color.BLACK);
        table.setShowGrid(true); 
        table.setShowHorizontalLines(true); // Show horizontal lines
        table.setShowVerticalLines(true);
        table.setBackground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100,100,550,400);
        add(scrollPane, BorderLayout.CENTER);
        
        String query="select b.barcode,b.title,i.id,s.name,i.duedate from books b,issuedbooks i,signup s where b.barcode=i.barcode and i.id=s.regno";
        try{
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery(query);
            if(!rs.next()){
                JOptionPane.showMessageDialog(null,"THERE ARE NO DUE BOOKS :)");
               
            }
            while(rs.next()){
                String bookid=rs.getString("barcode");
                String bookname=rs.getString("title");
                String id=rs.getString("id");
                String name=rs.getString("name");
                LocalDate duedate = rs.getDate("duedate").toLocalDate();
                long days = ChronoUnit.DAYS.between(LocalDate.now(),duedate);
                if(days<0){
                    days=days*-1;
                    model.addRow(new Object[]{bookid,bookname,id,name,""+days});
                    
                }
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
        new OverdueBooks();
    }
}
