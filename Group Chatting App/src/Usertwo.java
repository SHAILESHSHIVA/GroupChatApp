

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;
public class Usertwo  implements ActionListener,Runnable {
    
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame fr = new JFrame();
    static DataOutputStream dout; 
    BufferedReader reader;
    BufferedWriter writer;
    
    String name = "Vegeta";
    
    Usertwo(){
        fr.setLayout(null);
        
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(34,139,34));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        fr.add(p1);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);
        
        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });
        
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/goku.png"));
        Image i5 = i4.getImage().getScaledInstance(60,60,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,5,60,60);
        p1.add(profile);
        
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);
        
        
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360,20,35,30);
        p1.add(phone);
        
        
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(410,20,10,25);
        p1.add(morevert);
        
        JLabel name = new JLabel("DragonBallZ");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);
        
        JLabel status = new JLabel("Vegeta, Goku ,Chichi, Bulma, Gohan, Piccolo");
        status.setBounds(110,35,160,18);
        status.setForeground(Color.white);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        p1.add(status);
        
        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        a1.setBackground(Color.white);
        fr.add(a1);
        
        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        fr.add(text);
        
        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        send.setForeground(Color.white);
        send.addActionListener(this);
        fr.add(send);
        
        fr.setSize(450,700);
        fr.setLocation(490,30);
        fr.setUndecorated(true);
        fr.getContentPane().setBackground(Color.WHITE);
        fr.setVisible(true);
        
        try{
            Socket socket = new Socket("localhost",2003);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch(Exception e){
            e.printStackTrace();
        
        }
        
        
    
    }
    
    public void actionPerformed(ActionEvent ae){
        
     try{
        String out =  "<html><p>"+ name +"</p> <p>"+text.getText() +"</p></html>";
        
//        JLabel output = new JLabel(out);
        
//        JPanel p2 = new JPanel();
        JPanel p2 = formatLabel(out);
//        p2.add(output);
        a1.setLayout(new BorderLayout());
        
        JPanel right = new JPanel(new BorderLayout());
        
        right.add(p2,BorderLayout.LINE_END);
        right.setBackground(Color.white);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        
        a1.add(vertical,BorderLayout.PAGE_START);
        
        try{
            writer.write(out);
            writer.write("\r\n");
            writer.flush();
        }catch(Exception e){
            e.printStackTrace();
        
        }
        
       
        
        text.setText("");
        
        fr.repaint();
        fr.invalidate();
        fr.validate();
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
   
    
    }
    
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
         panel.setBackground(Color.white);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        
        JLabel output = new  JLabel("<html><p style=\"width:150px\">"+out+"</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,210,100));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(0,15,0,50));
        
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        
        return panel;
        
    
    }
    
    public void run(){
        
        try{
            String msg = "";
            
            while(true){
                msg = reader.readLine();
                if(msg.contains(name)){
                    continue;
                }
                
                JPanel panel = formatLabel(msg);
                
                JPanel left = new JPanel(new BorderLayout());
                left.setBackground(Color.white);
                left.add(panel,BorderLayout.LINE_START);
                vertical.add(left);
                
                a1.add(vertical,BorderLayout.PAGE_START);
                
                fr.repaint();
                fr.invalidate();
                fr.validate();
            
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[]args){
        
        Usertwo two  = new Usertwo();
        Thread t1 = new Thread(two);
        t1.start();
        
        
     
    
    }

   
    
    
}
