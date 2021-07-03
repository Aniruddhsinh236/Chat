/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatting_application;

import static chatting_application.Server.a1;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.net.*;
import java.io.*;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import javax.swing.JScrollPane;
import javax.swing.Timer;

/**
 *
 * @author Aniruddhsinh Parmar
 */

public class Client extends JFrame implements ActionListener {

    JPanel p1;
    JTextField t1;
    JButton b1;
    static JTextArea a1;
    static Socket s;
    static DataOutputStream dout;
    static DataInputStream din;
Boolean typing;
    Client() {

        p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        add(p1);

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chatting_application/img/icon/tom.jpg"));
        Image i5 = i4.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i6);
        l2.setBounds(5, 17, 60, 60);
        p1.add(l2);

        JLabel name = new JLabel("Tom");
        name.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        name.setForeground(Color.white);
        name.setBounds(10, 15, 100, 18);
        p1.add(name);

        JLabel status = new JLabel("      ..Active now");
        status.setFont(new Font("SAN_SERIF", Font.PLAIN, 8));
        status.setForeground(Color.white);
        status.setBounds(10, 25, 100, 18);
        p1.add(status);
        
        
         Timer t = new Timer(1, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!typing){
                status.setText("      ..Active now");
                }
                
            }
        
        });
        t.setInitialDelay(500);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chatting_application/img/icon/arrow.jpeg"));
        Image i2 = i1.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(0, 17, 20, 20);
        p1.add(l1);

        //click on cross for exit from application 
        l1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        //text area , message will see here ...........................
        a1 = new JTextArea();
        a1.setBounds(0, 70, 400, 370);
        a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        a1.setForeground(Color.black);
        a1.setBackground(Color.white);
        a1.setEditable(false);
        a1.setLineWrap(true);
        a1.setWrapStyleWord(true);
        add(a1);
        JScrollPane sp= new JScrollPane(a1);
        sp.setBounds(0, 70, 400, 370);
        add(sp);
        //to see message on text area 
        //message typing span ....................................................
        t1 = new JTextField();
        t1.setBounds(20, 450, 290, 40);
        t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        add(t1);

        
         t1.addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent ke){
        status.setText("      ..typing now");
        
        t.stop();
        
        typing = true;
        }
        public void keyReleased(KeyEvent ke){
        typing = false;
        if(!t.isRunning()){
        t.start();
        }
        }
        
        });
         
         
        //message send button..........................................
        b1 = new JButton("SEND");
        b1.setBackground(new Color(7, 94, 84));
        b1.setForeground(Color.white);
        b1.setBounds(305, 450, 85, 40);
        b1.addActionListener(this);
        add(b1);

        //getContentPane().setBackground(Color.orange);
        setLayout(null);
        setSize(400, 500);
        setLocation(800, 100);
        setUndecorated(true);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            String out = t1.getText();
            a1.setText(a1.getText() + "\n\t\t" + out);
            dout.writeUTF(out);
            t1.setText("");
        } catch (IOException ex) {
            return;
        }
    }

    public static void main(String[] args) {
        new Client().setVisible(true);

        try {

            s = new Socket("127.0.0.1", 6001);
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            String msgin = "";
            while (true) {

                msgin = din.readUTF();
                a1.setText(a1.getText() + "\n" + msgin);
            }

        } catch (Exception e) {
            return;
        }

    }

}
