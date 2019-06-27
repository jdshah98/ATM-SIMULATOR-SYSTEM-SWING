package atm;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BalanceInquiry {

    private JFrame mainFrame;
    private JLabel statusLabel;
    private JLabel headerLabel;
    private JLabel Balance;

    Connection connection;
    String balance_number;

    public BalanceInquiry() {
        
    }

    BalanceInquiry(int card, int cvv, int pin) throws SQLException {
        Conection conn = new Conection();
        if (conn.isConnection()) {
            connection = conn.conreturn();
            Statement statement = connection.createStatement();
            String sql_bal = "SELECT * FROM `REGISTER` WHERE card_no=" + card + "";
            ResultSet resultSet = statement.executeQuery(sql_bal);
            if (resultSet.next()) {
                int balance = resultSet.getInt("balance");
                balance_number = String.valueOf(balance);
                prepareLayout();
                retrieveBalance();
            }
        }
    }

    private void prepareLayout() {
        mainFrame = new JFrame("ATM Simulator System");      //Main Heading name
        mainFrame.setSize(600, 400);         //Set Size of window
        mainFrame.setLocation(383, 184);
        mainFrame.setLayout(null);
        
        headerLabel = new JLabel("Your Account Balance: ");
        headerLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        headerLabel.setBounds(150, 150, 200, 25);
 
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

    private void retrieveBalance() {
        Balance = new JLabel(balance_number);
        Balance.setBounds(400, 150, 200, 25);
        Balance.setFont(new Font("Serif",Font.PLAIN,20));
        mainFrame.add(Balance);
        mainFrame.setVisible(true);
    }
}
