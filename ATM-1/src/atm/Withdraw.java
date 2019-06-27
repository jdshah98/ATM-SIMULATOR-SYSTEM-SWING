package atm;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Withdraw implements ActionListener {
    private JFrame mainFrame;
    private JLabel statusLabel;
    private JLabel headerLabel;

    private JLabel amount;
    private JTextField amountval;
    private JButton Withdraw;
    private Connection connection;
    private final int card;

    public Withdraw(int card) throws SQLException {
        this.card = card;
        Conection conn = new Conection();
        if (conn.isConnection()) {
            connection = conn.conreturn();
        }
        prepareLayout();
        withdrawBalance();
    }

    private void prepareLayout() {
        mainFrame = new JFrame("ATM Simulator System");      //Main Heading name
        mainFrame.setSize(600, 400);         //Set Size of window
        mainFrame.setLocation(383, 184);
        mainFrame.setLayout(null);

        headerLabel = new JLabel("Withdraw Amount");
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

    private void withdrawBalance() {
        amount = new JLabel("Enter Amount to withdraw: ");
        amount.setBounds(100, 100, 200, 25);

        amountval = new JTextField("");
        amountval.setBounds(300, 100, 200, 25);

        Withdraw = new JButton("Withdraw");
        Withdraw.setBounds(250, 200, 100, 50);
        Withdraw.setActionCommand("Withdraw");
        Withdraw.addActionListener(this);

        mainFrame.add(amount);
        mainFrame.add(amountval);
        mainFrame.add(Withdraw);
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (command.equals("Withdraw")) {
            int input;
            input = JOptionPane.showConfirmDialog(null, "Do you want to proceed?", "select an option", JOptionPane.YES_NO_OPTION);
            if (input == 1) {
                return;
            }
            String balance = amountval.getText();
            int bal_pre = Integer.parseInt(balance);
            int table_bal = 0;
            try {
                Double.parseDouble(balance);
                Statement statement = connection.createStatement();
                String sql = "SELECT balance FROM REGISTER WHERE card_no=" + card;
                ResultSet rs = statement.executeQuery(sql);
                if (rs.next()) {
                    table_bal = rs.getInt("balance");   
                }
                if (table_bal > bal_pre) {
                    balance = String.valueOf(table_bal - bal_pre);
                    String sql1 = "UPDATE REGISTER SET balance=" + Integer.parseInt(balance) + " WHERE card_no=" + card;
                    statement.executeUpdate(sql1);
                    String sql2 = "INSERT INTO `transaction`(`sCardno`, `change_balance`, `acc_balance`,`type`) VALUES (" + card + "," + bal_pre + "," + Integer.parseInt(balance) + ",\"Withdraw\")";
                    statement.executeUpdate(sql2);
                    JOptionPane.showMessageDialog(mainFrame, "Amount Withdrawed Successfully");
                    mainFrame.setVisible(false);
                    ATM.mainFrame.setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(mainFrame, "Balance requested is greater than your account balance");
                    amountval.requestFocus();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Enter correct Balance");
                amountval.requestFocus();
            } catch (SQLException ex) {
                Logger.getLogger(Deposit.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}