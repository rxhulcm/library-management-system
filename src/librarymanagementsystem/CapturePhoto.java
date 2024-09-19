package librarymanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class CapturePhoto extends JFrame implements ActionListener {
    private JLabel cameraScreen;
    private JButton btncapture;
    private VideoCapture capture;
    private Mat image;
    private boolean clicked = false;
    private String ID;
    
    CapturePhoto(String ID) {
        this.ID = ID;
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        
        cameraScreen = new JLabel();
        cameraScreen.setBounds(80, 0, 640, 480);
        add(cameraScreen);
        
        btncapture = new JButton("CAPTURE");
        btncapture.setBounds(325, 500, 150, 50);
        btncapture.addActionListener(this);
        add(btncapture);
        
        setVisible(true);
        
        startCamera(); // Start the camera feed when the window is created
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btncapture) {
            clicked = true;
        }
    }
    
    private void startCamera() {
        capture = new VideoCapture(0, Videoio.CAP_DSHOW);
        if (!capture.isOpened()) {
            System.out.println("Camera not opened");
            return; // Exit if the camera couldn't be opened
        }

        image = new Mat();
        
        Timer timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                capture.read(image);
                if (!image.empty()) {
                    final MatOfByte buff = new MatOfByte();
                    Imgcodecs.imencode(".jpg", image, buff);
                    byte[] imageData = buff.toArray();
                    
                    ImageIcon icon = new ImageIcon(imageData);
                    cameraScreen.setIcon(icon);
                    
                    if (clicked) {
                        String name = ID;
                        Imgcodecs.imwrite("C:\\Users\\rahul\\OneDrive\\Desktop\\Images\\" + name + ".jpg", image);
                        clicked = false;
                        setVisible(false);
                        new SignUp(ID).setVisible(true);
                        ((Timer)e.getSource()).stop(); // Stop the timer
                        capture.release(); // Release the camera
                    }
                }
            }
        });
        
        timer.start(); // Start the timer
    }
    
    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        SwingUtilities.invokeLater(() -> new CapturePhoto("1232343"));
    }
}
