package atm;

import java.awt.Font;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.*;
import javax.swing.*;

public class Deposit implements ActionListener {

    private JFrame mainFrame;
    private JLabel statusLabel;
    private JLabel headerLabel;

    private JLabel amount;
    private JTextField amountval;
    private JButton Deposit;
    private Connection connection;
    private final int card;

    public Deposit(int cardno) throws SQLException {
        this.card = cardno;
        Conection conn = new Conection();
        if (conn.isConnection()) {
            connection = conn.conreturn();
        }
        prepareLayout();
        depositBalance();
    }

    private void prepareLayout() {
        mainFrame = new JFrame("ATM Simulator System");      //Main Heading name
        mainFrame.setSize(600, 400);         //Set Size of window
        mainFrame.setLocation(383, 184);
        mainFrame.setLayout(null);

        headerLabel = new JLabel("Deposit Amount");
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

    private void depositBalance() {
        amount = new JLabel("Enter Amount to Deposit: ");
        amount.setBounds(100, 100, 200, 25);

        amountval = new JTextField("");
        amountval.setBounds(300, 100, 200, 25);

        Deposit = new JButton("Deposit");
        Deposit.setBounds(250, 200, 100, 50);
        Deposit.setActionCommand("Deposit");
        Deposit.addActionListener(this);

        mainFrame.add(amount);
        mainFrame.add(amountval);
        mainFrame.add(Deposit);
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (command.equals("Deposit")) {
            int input;
            input = JOptionPane.showConfirmDialog(null, "Do you want to proceed?", "select an option", JOptionPane.YES_NO_OPTION);
            if (input == 1) {
                return;
            }
            String balance = amountval.getText();
            int bal_pre = Integer.parseInt(balance);
            try {
                Double.parseDouble(balance);
                Statement statement = connection.createStatement();
                String sql = "SELECT balance FROM REGISTER WHERE card_no=" + card;
                ResultSet rs = statement.executeQuery(sql);
                if (rs.next()) {
                    int balance_previous = rs.getInt("balance");
                    balance = String.valueOf(balance_previous + Integer.parseInt(balance));
                }
                String sql1 = "UPDATE REGISTER SET balance=" + Integer.parseInt(balance) + " WHERE card_no=" + card;
                statement.executeUpdate(sql1);
                String sql2 = "INSERT INTO `transaction`(`sCardno`, `change_balance`, `acc_balance`,`type`) VALUES (" + card + "," + bal_pre + "," + Integer.parseInt(balance) + ",\"Deposit\")";
                statement.executeUpdate(sql2);
                JOptionPane.showMessageDialog(mainFrame, "Amount Deposited Successfully");
                mainFrame.setVisible(false);
                ATM.mainFrame.setVisible(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Enter correct Balance");
                amountval.requestFocus();
            } catch (SQLException ex) {
                Logger.getLogger(Deposit.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}