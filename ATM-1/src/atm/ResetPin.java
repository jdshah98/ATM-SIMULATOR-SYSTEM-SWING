package atm;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class ResetPin implements ActionListener {

    private JFrame mainFrame;
    private JLabel statusLabel;
    private JLabel headerLabel;

    private JLabel oldp, newp, confirmp;
    private JPasswordField oldpin, newpin, confirmpin;
    private JButton ResetPIN;
    private int card, pin;
    private Connection connection;
    private int op,np,cp;

    public ResetPin(int card, int pin) throws SQLException {
        this.card = card;
        this.pin = pin;
        Conection conn = new Conection();
        if (conn.isConnection()) {
            connection = conn.conreturn();
        }
        prepareLayout();
        resetPin();
    }

    private void prepareLayout() {
        mainFrame = new JFrame("ATM Simulator System");      //Main Heading name
        mainFrame.setSize(600, 400);         //Set Size of window
        mainFrame.setLocation(383, 184);
        mainFrame.setLayout(null);

        headerLabel = new JLabel("Reset Your Pin");
        headerLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        headerLabel.setBounds(225, 20, 200, 25);

        long millis = System.currentTimeMillis();
        java.util.Date date = new java.util.Date(millis);
        statusLabel = new JLabel("");
        statusLabel.setText(date.toString());
        statusLabel.setBounds(10, 325, 250, 25);
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

    private void resetPin() {
        oldp = new JLabel("OLD PIN: ");
        oldp.setBounds(100, 100, 200, 25);

        newp = new JLabel("NEW PIN: ");
        newp.setBounds(100, 150, 200, 25);

        confirmp = new JLabel("CONFIRM PIN: ");
        confirmp.setBounds(100, 200, 200, 25);

        oldpin = new JPasswordField("");
        oldpin.setBounds(300, 100, 200, 25);

        newpin = new JPasswordField("");
        newpin.setBounds(300, 150, 200, 25);

        confirmpin = new JPasswordField("");
        confirmpin.setBounds(300, 200, 200, 25);

        ResetPIN = new JButton("Reset Pin");
        ResetPIN.setActionCommand("Reset");
        ResetPIN.addActionListener(this);
        ResetPIN.setBounds(250, 250, 100, 50);

        mainFrame.add(oldp);
        mainFrame.add(oldpin);
        mainFrame.add(newp);
        mainFrame.add(newpin);
        mainFrame.add(confirmp);
        mainFrame.add(confirmpin);
        mainFrame.add(ResetPIN);
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (command.equals("Reset")) {
            op=Integer.parseInt(oldpin.getText());
            np=Integer.parseInt(newpin.getText());
            cp=Integer.parseInt(confirmpin.getText());
            if(op==pin){
                if(np==cp && newpin.getText().length()==4){
                   if(op!=np)
                   {
                       try {
                           Statement statement=connection.createStatement();
                           String aql="UPDATE REGISTER SET pin="+np+" WHERE card_no="+card;
                           statement.executeUpdate(aql);
                           String sql1="UPDATE USER SET password="+np+" WHERE user_id="+card;
                           statement.executeUpdate(sql1);
                       } catch (SQLException ex) {
                           Logger.getLogger(ResetPin.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   }else{
                       JOptionPane.showMessageDialog(mainFrame, "Enter different pin other than old pin");
                       newpin.requestFocus();
                   }
                }else{
                    JOptionPane.showMessageDialog(mainFrame, "new pin and confirm pin doesn't match");
                    confirmpin.requestFocus();
                }
            }else{
                JOptionPane.showMessageDialog(mainFrame, "Old pin is incorrect");
                oldpin.requestFocus();
            }
        }
    }
}