package atm;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Login implements ActionListener {

    private JFrame mainFrame;
    private JLabel statusLabel;
    private JLabel headerLabel;

    private int pin, cvv;
    private int card;
    private JLabel Card;
    private JTextField Cardno;
    private JLabel Cvv;
    private JTextField Cvvno;
    private JLabel Pin;
    private JPasswordField Pinno;
    private JButton login;
    Connection connection;

    public Login() throws SQLException {
        Conection conn = new Conection();
        if (conn.isConnection()) {
            connection = conn.conreturn();
        }
        prepare();
        loginInfo();
    }

    private void prepare() {
        mainFrame = new JFrame("ATM Simulator System");      //Main Heading name
        mainFrame.setSize(600, 500);         //Set Size of window
        mainFrame.setLocation(383, 134);
        mainFrame.setLayout(null);

        headerLabel = new JLabel("LOGIN WINDOW");
        headerLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        headerLabel.setBounds(225, 20, 200, 25);

        long millis = System.currentTimeMillis();
        java.util.Date date = new java.util.Date(millis);
        statusLabel = new JLabel("");
        statusLabel.setText(date.toString());
        statusLabel.setBounds(10, 430, 250, 25);
        statusLabel.setFont(new Font("Serif", Font.PLAIN, 17));

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        mainFrame.add(headerLabel);
        mainFrame.add(statusLabel);
    }

    private void loginInfo() {
        Card = new JLabel("Enter Card No: ");
        Card.setBounds(100, 150, 200, 20);
        Cvv = new JLabel("Enter CVV No: ");
        Cvv.setBounds(100, 200, 200, 20);
        Pin = new JLabel("Enter Pin No: ");
        Pin.setBounds(100, 250, 200, 20);

        Cardno = new JTextField("");
        Cardno.setBounds(300, 150, 200, 20);
        Cvvno = new JTextField("");
        Cvvno.setBounds(300, 200, 200, 20);
        Pinno = new JPasswordField("");
        Pinno.setBounds(300, 250, 200, 20);

        login = new JButton("Login");
        login.setBounds(250, 350, 100, 50);
        login.setActionCommand("Login");
        login.addActionListener(this);

        mainFrame.add(Card);
        mainFrame.add(Cardno);
        mainFrame.add(Cvv);
        mainFrame.add(Cvvno);
        mainFrame.add(Pin);
        mainFrame.add(Pinno);
        mainFrame.add(login);
        ATM.mainFrame.setVisible(false);
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (command.equals("Login")) {
            if(Cardno.getText().length()==0){
                JOptionPane.showMessageDialog(mainFrame, "Card no is required");
                Cardno.requestFocus();
                
            }else if(Pinno.getText().length()==0){
                JOptionPane.showMessageDialog(mainFrame, "Pin no is required");
                Pinno.requestFocus();
                
            }else if(Cvvno.getText().length()==0){
                JOptionPane.showMessageDialog(mainFrame, "CVV no is required");
                Cvvno.requestFocus();
                
            }else{
                card = Integer.parseInt(Cardno.getText());
                pin = Integer.parseInt(Pinno.getText());
                cvv = Integer.parseInt(Cvvno.getText());

                try {
                    Statement statement = connection.createStatement();
                    String sql_select_user = "SELECT * FROM `USER` WHERE user_id=" + Integer.parseInt(Cardno.getText()) + " AND password=" + pin + " AND cvv=" + cvv + "";
                    ResultSet set = statement.executeQuery(sql_select_user);
                    if (set.next()) {
                           mainFrame.setVisible(false);
                           Menu menu=new Menu(card,cvv,pin);
                    } else {
                           JOptionPane.showMessageDialog(mainFrame, "Login Unsuccessful");
                           mainFrame.setVisible(false);
                           ATM.mainFrame.setVisible(true);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
