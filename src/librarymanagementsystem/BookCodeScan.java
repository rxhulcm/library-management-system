package librarymanagementsystem;

import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookCodeScan extends JFrame implements ActionListener {

    JLabel cameraScreen;
    VideoCapture capture;
    Mat image;
    JButton back;
    Timer timer;
    String source;
    BookCodeScan(String source) {
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(186, 152, 73));
        this.source=source;
        cameraScreen = new JLabel();
        cameraScreen.setBounds(80, 20, 640, 480);
        add(cameraScreen);

        back = new JButton("BACK");
        back.setBounds(300, 550, 250, 40);
        back.setBackground(new Color(252, 225, 124));
        back.setForeground(new Color(36, 26, 2));
        back.setFont(new Font("Monospaced", Font.PLAIN, 18));
        back.setBorder(new EmptyBorder(0, 0, 0, 0));
        back.addActionListener(this);
        add(back);

        setVisible(true);
        startCamera();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            if (timer != null) {
                timer.stop();
            }
            setVisible(false);
            new MainMenu().setVisible(true);
        }
    }

    public void startCamera() {
        capture = new VideoCapture(0, Videoio.CAP_DSHOW);
        if (capture.isOpened()) {
            System.out.println("Camera Opened");
        } else {
            System.out.println("Camera not opened");
            return;
        }

        image = new Mat();

        // Use a Timer to periodically update the camera feed and check for barcodes
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (capture.read(image)) {
                    updateCameraScreen();
                    checkForBarcode();
                }
            }
        });
        timer.start();
    }

    // Update the camera screen with the current frame
    private void updateCameraScreen() {
        MatOfByte buff = new MatOfByte();
        Imgcodecs.imencode(".jpg", image, buff);
        byte[] imageData = buff.toArray();
        ImageIcon icon = new ImageIcon(imageData);
        cameraScreen.setIcon(icon);
    }

    // Check for a barcode in the current frame
    private void checkForBarcode() {
        BufferedImage bufferedImage = MatToBufferedImage(image);
        if (bufferedImage != null) {
            String barcode = decodeBarcode(bufferedImage);
            if (barcode != null) {
                System.out.println("Barcode detected: " + barcode);
                if(source.equals("addbtn")){
                    String name = JOptionPane.showInputDialog(this, "Barcode Detected: " + barcode + ". Enter name of book");
                    try {
                        Conn c = new Conn();
                        String query = "insert into books values(?,?)";
                        PreparedStatement pstmt = c.c.prepareStatement(query);
                        pstmt.setString(1, barcode);
                        pstmt.setString(2, name);
                        int rs = pstmt.executeUpdate();
                        if (rs > 0) {
                            System.out.println("Book Added Successfully");
                        } else {
                            System.out.println("Failed to Add Book");
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                else if(source.equals("borrowbtn")){
                    String membership,bookname="";
                    String ID=JOptionPane.showInputDialog(this,"Barcode Detected: "+barcode+". Enter User RegNo.");
                    try{
                        
                        Conn c = new Conn();
                        String bookquery = "select * from books where barcode=?";
                        PreparedStatement p=c.c.prepareStatement(bookquery);
                        p.setString(1,barcode);
                        ResultSet r=p.executeQuery();
                        if(r.next()){
                            bookname=r.getString("title");
                        }
                        String query = "select * from signup where regno=?";
                        PreparedStatement pstmt = c.c.prepareStatement(query);
                        pstmt.setString(1, ID);
                        ResultSet rs = pstmt.executeQuery();
                        if(rs.next()){
                            membership=rs.getString("membershiptype");
                            if(membership.equals("Kids Membership"))
                            {
                                String kidsmember="select * from kidsmembership where id=?";
                                PreparedStatement pstmt1=c.c.prepareStatement(kidsmember);
                                pstmt1.setString(1,ID);
                                ResultSet rs1 = pstmt1.executeQuery();
                                if(rs1.next()){
                                    String title=rs1.getString("title");
                                    if(title.equals("")){
                                        String q1="update kidmembership set title=? where id=?";
                                        PreparedStatement p1=c.c.prepareStatement(q1);
                                        p1.setString(1, bookname);
                                        p1.setString(2, ID);
                                        p1.executeUpdate();
                                        String q2="insert into issuedbooks values(?,?,?)";
                                        PreparedStatement p2=c.c.prepareStatement(q2);
                                        p2.setString(1,barcode);
                                        p2.setString(2,ID);
                                        LocalDate duedate = LocalDate.now().plusDays(14);
                                        p2.setDate(3,java.sql.Date.valueOf(duedate));
                                        p2.executeUpdate();
                                        
                                        
                                    }else{
                                        JOptionPane.showMessageDialog(null, "USER HAS TAKEN MAX ALLOWED BOOKS");
                                    }
                                }
                            }
                            else if(membership.equals("Adult Membership")){
                                String adultmember="select * from adultmembership where id=?";
                                PreparedStatement pstmt1=c.c.prepareStatement(adultmember);
                                pstmt1.setString(1,ID);
                                ResultSet rs1 = pstmt1.executeQuery();
                                if(rs1.next()){
                                    String title1=rs1.getString("title1");
                                    String title2=rs1.getString("title2");
                                    
                                    if(title1.equals("")){
                                        String q1="update adultmembership set title1=? where id=?";
                                        PreparedStatement p1=c.c.prepareStatement(q1);
                                        p1.setString(1, bookname);
                                        p1.setString(2, ID);
                                        p1.executeUpdate();
                                        String q2="insert into issuedbooks values(?,?,?)";
                                        PreparedStatement p2=c.c.prepareStatement(q2);
                                        p2.setString(1,barcode);
                                        p2.setString(2,ID);
                                        LocalDate duedate = LocalDate.now().plusDays(14);
                                        p2.setDate(3,java.sql.Date.valueOf(duedate));
                                        p2.executeUpdate();
                                        
                                    }
                                    else if(title2.equals("")){
                                        String q1="update adultmembership set title2=? where id=?";
                                        PreparedStatement p1=c.c.prepareStatement(q1);
                                        p1.setString(1, bookname);
                                        p1.setString(2, ID);
                                        p1.executeUpdate();
                                        String q2="insert into issuedbooks values(?,?,?)";
                                        PreparedStatement p2=c.c.prepareStatement(q2);
                                        p2.setString(1,barcode);
                                        p2.setString(2,ID);
                                        LocalDate duedate = LocalDate.now().plusDays(14);
                                        p2.setDate(3,java.sql.Date.valueOf(duedate));
                                        p2.executeUpdate();
                                        
                                    }
                                    
                                    else{
                                        JOptionPane.showMessageDialog(null, "USER HAS TAKEN MAX ALLOWED BOOKS");
                                    }
                                }
                            }else{
                                String premiummember="select * from premiummembership where id=?";
                                PreparedStatement pstmt1=c.c.prepareStatement(premiummember);
                                pstmt1.setString(1,ID);
                                ResultSet rs1 = pstmt1.executeQuery();
                                if(rs1.next()){
                                    String title1=rs1.getString("title1");
                                    String title2=rs1.getString("title2");
                                    String title3=rs1.getString("title3");
                                    if(title1.equals("")){
                                        String q1="update premiummembership set title1=? where id=?";
                                        PreparedStatement p1=c.c.prepareStatement(q1);
                                        p1.setString(1, bookname);
                                        p1.setString(2, ID);
                                        p1.executeUpdate();
                                        String q2="insert into issuedbooks values(?,?,?)";
                                        PreparedStatement p2=c.c.prepareStatement(q2);
                                        p2.setString(1,barcode);
                                        p2.setString(2,ID);
                                        LocalDate duedate = LocalDate.now().plusDays(14);
                                        p2.setDate(3,java.sql.Date.valueOf(duedate));
                                        p2.executeUpdate();
                                        
                                    }
                                    else if(title2.equals("")){
                                        String q1="update premiummembership set title2=? where id=?";
                                        PreparedStatement p1=c.c.prepareStatement(q1);
                                        p1.setString(1, bookname);
                                        p1.setString(2, ID);
                                        p1.executeUpdate();
                                        String q2="insert into issuedbooks values(?,?,?)";
                                        PreparedStatement p2=c.c.prepareStatement(q2);
                                        p2.setString(1,barcode);
                                        p2.setString(2,ID);
                                        LocalDate duedate = LocalDate.now().plusDays(14);
                                        p2.setDate(3,java.sql.Date.valueOf(duedate));
                                        p2.executeUpdate();
                                        
                                    }
                                    else if(title3.equals("")){
                                        String q1="update adultmembership set title3=? where id=?";
                                        PreparedStatement p1=c.c.prepareStatement(q1);
                                        p1.setString(1, bookname);
                                        p1.setString(2, ID);
                                        p1.executeUpdate();
                                        String q2="insert into issuedbooks values(?,?,?)";
                                        PreparedStatement p2=c.c.prepareStatement(q2);
                                        p2.setString(1,barcode);
                                        p2.setString(2,ID);
                                        LocalDate duedate = LocalDate.now().plusDays(14);
                                        p2.setDate(3,java.sql.Date.valueOf(duedate));
                                        p2.executeUpdate();
                                        
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(null, "USER HAS TAKEN MAX ALLOWED BOOKS");
                                    }
                                }
                            }
                        }           
                        else{
                            System.out.println("No records of regno found in Database");
                        }
                    }catch(Exception e){
                        System.out.println(e);
                    }
                            
                }
                else if(source.equals("returnbtn")){
                    String membership,bookname="";
                    String ID=JOptionPane.showInputDialog(this,"Barcode Detected: "+barcode+". Enter User RegNo.");
                    try{
                        
                        Conn c = new Conn();
                        String bookquery = "select * from books where barcode=?";
                        PreparedStatement p=c.c.prepareStatement(bookquery);
                        p.setString(1,barcode);
                        ResultSet r=p.executeQuery();
                        if(r.next()){
                            bookname=r.getString("title");
                        }
                        String query = "select * from signup where regno=?";
                        PreparedStatement pstmt = c.c.prepareStatement(query);
                        pstmt.setString(1, ID);
                        ResultSet rs = pstmt.executeQuery();
                        if(rs.next()){
                            membership=rs.getString("membershiptype");
                            if(membership.equals("Kids Membership"))
                            {
                                
                                        
                                        
                                String kidsmember="select * from kidsmembership where id=?";
                                PreparedStatement pstmt1=c.c.prepareStatement(kidsmember);
                                pstmt1.setString(1,ID);
                                ResultSet rs1 = pstmt1.executeQuery();
                                if(rs1.next()){
                                    String title=rs1.getString("title");
                                    if(title.equals(bookname)){
                                        String q1="update kidmembership set title='' where id=?";
                                        PreparedStatement p1=c.c.prepareStatement(q1);
                                        
                                        p1.setString(1, ID);
                                        p1.executeUpdate();
                                        String q2="select * from issuedbooks where barcode=?";
                                        PreparedStatement p2=c.c.prepareStatement(q2);
                                        p2.setString(1,barcode);
                                        ResultSet r1 = p2.executeQuery();
                                        if(r1.next()){
                                            LocalDate duedate = r1.getDate("duedate").toLocalDate();
                                            long days = ChronoUnit.DAYS.between(LocalDate.now(),duedate);
                                            if(days<0){
                                                days=days*-1;
                                                JOptionPane.showMessageDialog(null, "THE BOOK WAS "+days+" LATE");
                                                String q3="select * from amounttopay where id=?";
                                                PreparedStatement p3=c.c.prepareStatement(q3);
                                                p3.setString(1, ID);
                                                ResultSet r2= p3.executeQuery();
                                                if(r2.next()){
                                                    Double amount = r2.getDouble("dueamount");
                                                    amount+=(days*1);
                                                    String q4="update amounttopay set dueamount=? where id=?";
                                                    PreparedStatement p4=c.c.prepareStatement(q4);
                                                    p4.setDouble(1, amount);
                                                    p4.setString(2, ID);
                                                    p.executeUpdate();
                                                }
                                            }
                                        }
                                        String q5="delete from issuedbooks where barcode=?";
                                        PreparedStatement p5=c.c.prepareStatement(q5);
                                        p5.setString(1, barcode);
                                        p5.executeUpdate();
                                        
                                    }else{
                                        JOptionPane.showMessageDialog(null, "BOOK DOESNT MATCH");
                                    }
                                }
                            }
                            else if(membership.equals("Adult Membership")){
                                String adultmember="select * from adultmembership where id=?";
                                PreparedStatement pstmt1=c.c.prepareStatement(adultmember);
                                pstmt1.setString(1,ID);
                                ResultSet rs1 = pstmt1.executeQuery();
                                if(rs1.next()){
                                    String title1=rs1.getString("title1");
                                    String title2=rs1.getString("title2");
                                    
                                    if(title1.equals(bookname)){
                                        String q1="update adultmembership set title1='' where id=?";
                                        PreparedStatement p1=c.c.prepareStatement(q1);
                                        
                                        p1.setString(1, ID);
                                        p1.executeUpdate();
                                        String q2="select * from issuedbooks where barcode=?";
                                        PreparedStatement p2=c.c.prepareStatement(q2);
                                        p2.setString(1,barcode);
                                        ResultSet r1 = p2.executeQuery();
                                        if(r1.next()){
                                            LocalDate duedate = r1.getDate("duedate").toLocalDate();
                                            long days = ChronoUnit.DAYS.between(LocalDate.now(),duedate);
                                            if(days<0){
                                                days=days*-1;
                                                JOptionPane.showMessageDialog(null, "THE BOOK WAS "+days+" LATE");
                                                String q3="select * from amounttopay where id=?";
                                                PreparedStatement p3=c.c.prepareStatement(q3);
                                                p3.setString(1, ID);
                                                ResultSet r2= p3.executeQuery();
                                                if(r2.next()){
                                                    Double amount = r2.getDouble("dueamount");
                                                    amount+=(days*2);
                                                    String q4="update amounttopay set dueamount=? where id=?";
                                                    PreparedStatement p4=c.c.prepareStatement(q4);
                                                    p4.setDouble(1, amount);
                                                    p4.setString(2, ID);
                                                    p.executeUpdate();
                                                }
                                            }
                                        }
                                        String q5="delete from issuedbooks where barcode=?";
                                        PreparedStatement p5=c.c.prepareStatement(q5);
                                        p5.setString(1, barcode);
                                        p5.executeUpdate();
                                        
                                    }
                                    else if(title2.equals(bookname)){
                                        String q1="update adultmembership set title2='' where id=?";
                                        PreparedStatement p1=c.c.prepareStatement(q1);
                                        
                                        p1.setString(1, ID);
                                        p1.executeUpdate();
                                        String q2="select * from issuedbooks where barcode=?";
                                        PreparedStatement p2=c.c.prepareStatement(q2);
                                        p2.setString(1,barcode);
                                        ResultSet r1 = p2.executeQuery();
                                        if(r1.next()){
                                            LocalDate duedate = r1.getDate("duedate").toLocalDate();
                                            long days = ChronoUnit.DAYS.between(LocalDate.now(),duedate);
                                            if(days<0){
                                                days=days*-1;
                                                JOptionPane.showMessageDialog(null, "THE BOOK WAS "+days+" LATE");
                                                String q3="select * from amounttopay where id=?";
                                                PreparedStatement p3=c.c.prepareStatement(q3);
                                                p3.setString(1, ID);
                                                ResultSet r2= p3.executeQuery();
                                                if(r2.next()){
                                                    Double amount = r2.getDouble("dueamount");
                                                    amount+=(days*2);
                                                    String q4="update amounttopay set dueamount=? where id=?";
                                                    PreparedStatement p4=c.c.prepareStatement(q4);
                                                    p4.setDouble(1, amount);
                                                    p4.setString(2, ID);
                                                    p.executeUpdate();
                                                }
                                            }
                                        }
                                        String q5="delete from issuedbooks where barcode=?";
                                        PreparedStatement p5=c.c.prepareStatement(q5);
                                        p5.setString(1, barcode);
                                        p5.executeUpdate();
                                    }
                                    
                                    else{
                                        JOptionPane.showMessageDialog(null, "BOOK DOESNT MATCH");
                                    }
                                }
                            }else{
                                String premiummember="select * from premiummembership where id=?";
                                PreparedStatement pstmt1=c.c.prepareStatement(premiummember);
                                pstmt1.setString(1,ID);
                                ResultSet rs1 = pstmt1.executeQuery();
                                if(rs1.next()){
                                    String title1=rs1.getString("title1");
                                    String title2=rs1.getString("title2");
                                    String title3=rs1.getString("title3");
                                    if(title1.equals(bookname)){
                                        String q1="update premiummembership set title1='' where id=?";
                                        PreparedStatement p1=c.c.prepareStatement(q1);
                                        
                                        p1.setString(1, ID);
                                        p1.executeUpdate();
                                        String q2="select * from issuedbooks where barcode=?";
                                        PreparedStatement p2=c.c.prepareStatement(q2);
                                        p2.setString(1,barcode);
                                        ResultSet r1 = p2.executeQuery();
                                        if(r1.next()){
                                            LocalDate duedate = r1.getDate("duedate").toLocalDate();
                                            long days = ChronoUnit.DAYS.between(LocalDate.now(),duedate);
                                            if(days<0){
                                                days=days*-1;
                                                JOptionPane.showMessageDialog(null, "THE BOOK WAS "+days+" LATE");
                                                String q3="select * from amounttopay where id=?";
                                                PreparedStatement p3=c.c.prepareStatement(q3);
                                                p3.setString(1, ID);
                                                ResultSet r2= p3.executeQuery();
                                                if(r2.next()){
                                                    Double amount = r2.getDouble("dueamount");
                                                    amount+=(days*3);
                                                    String q4="update amounttopay set dueamount=? where id=?";
                                                    PreparedStatement p4=c.c.prepareStatement(q4);
                                                    p4.setDouble(1, amount);
                                                    p4.setString(2, ID);
                                                    p.executeUpdate();
                                                }
                                            }
                                        }
                                        String q5="delete from issuedbooks where barcode=?";
                                        PreparedStatement p5=c.c.prepareStatement(q5);
                                        p5.setString(1, barcode);
                                        p5.executeUpdate();
                                        
                                    }
                                    else if(title2.equals(bookname)){
                                        String q1="update premiummembership set title2=? where id=?";
                                        PreparedStatement p1=c.c.prepareStatement(q1);
                                        
                                        p1.setString(1, ID);
                                        p1.executeUpdate();
                                        String q2="select * from issuedbooks where barcode=?";
                                        PreparedStatement p2=c.c.prepareStatement(q2);
                                        p2.setString(1,barcode);
                                        ResultSet r1 = p2.executeQuery();
                                        if(r1.next()){
                                            LocalDate duedate = r1.getDate("duedate").toLocalDate();
                                            long days = ChronoUnit.DAYS.between(LocalDate.now(),duedate);
                                            if(days<0){
                                                days=days*-1;
                                                JOptionPane.showMessageDialog(null, "THE BOOK WAS "+days+" LATE");
                                                String q3="select * from amounttopay where id=?";
                                                PreparedStatement p3=c.c.prepareStatement(q3);
                                                p3.setString(1, ID);
                                                ResultSet r2= p3.executeQuery();
                                                if(r2.next()){
                                                    Double amount = r2.getDouble("dueamount");
                                                    amount+=(days*3);
                                                    String q4="update amounttopay set dueamount=? where id=?";
                                                    PreparedStatement p4=c.c.prepareStatement(q4);
                                                    p4.setDouble(1, amount);
                                                    p4.setString(2, ID);
                                                    p.executeUpdate();
                                                }
                                            }
                                        }
                                        String q5="delete from issuedbooks where barcode=?";
                                        PreparedStatement p5=c.c.prepareStatement(q5);
                                        p5.setString(1, barcode);
                                        p5.executeUpdate();
                                        
                                    }
                                    else if(title3.equals(bookname)){
                                        String q1="update adultmembership set title3='' where id=?";
                                        PreparedStatement p1=c.c.prepareStatement(q1);
                                        
                                        p1.setString(1, ID);
                                        p1.executeUpdate();
                                        String q2="select * from issuedbooks where barcode=?";
                                        PreparedStatement p2=c.c.prepareStatement(q2);
                                        p2.setString(1,barcode);
                                        ResultSet r1 = p2.executeQuery();
                                        if(r1.next()){
                                            LocalDate duedate = r1.getDate("duedate").toLocalDate();
                                            long days = ChronoUnit.DAYS.between(LocalDate.now(),duedate);
                                            if(days<0){
                                                days=days*-1;
                                                JOptionPane.showMessageDialog(null, "THE BOOK WAS "+days+" LATE");
                                                String q3="select * from amounttopay where id=?";
                                                PreparedStatement p3=c.c.prepareStatement(q3);
                                                p3.setString(1, ID);
                                                ResultSet r2= p3.executeQuery();
                                                if(r2.next()){
                                                    Double amount = r2.getDouble("dueamount");
                                                    amount+=(days*3);
                                                    String q4="update amounttopay set dueamount=? where id=?";
                                                    PreparedStatement p4=c.c.prepareStatement(q4);
                                                    p4.setDouble(1, amount);
                                                    p4.setString(2, ID);
                                                    p.executeUpdate();
                                                }
                                            }
                                        }
                                        String q5="delete from issuedbooks where barcode=?";
                                        PreparedStatement p5=c.c.prepareStatement(q5);
                                        p5.setString(1, barcode);
                                        p5.executeUpdate();
                                        
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(null, "BOOK DOESNT MATCH");
                                    }
                                }
                            }
                        }           
                        else{
                            System.out.println("No records of regno found in Database");
                        }
                    }catch(Exception e){
                        System.out.println(e);
                    }
                            
                }
            }
        }
    }

    // Convert Mat to BufferedImage
    private BufferedImage MatToBufferedImage(Mat matrix) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", matrix, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new ByteArrayInputStream(byteArray));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

    // Decode barcode using ZXing
    private String decodeBarcode(BufferedImage image) {
        try {
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();  // Return the decoded barcode text
        } catch (NotFoundException e) {
            // Barcode not found in the image
            return null;
        }
    }

    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookCodeScan("");
            }
        });
    }
}
