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

public class FundTransfer implements ActionListener {

    private JFrame mainFrame;
    private JLabel statusLabel;
    private JLabel headerLabel;

    private JLabel card1, card2, amount;
    private JTextField cardno1, cardno2, amountval;
    private JButton Transfer;
    private Connection connection;
    private final int card;
    private Statement statement;

    public FundTransfer(int card) throws SQLException {
        this.card = card;
        Conection conn = new Conection();
        if (conn.isConnection()) {
            connection = conn.conreturn();
        }
        prepareLayout();
        transferFund();
    }

    private void prepareLayout() {
        mainFrame = new JFrame("ATM Simulator System");      //Main Heading name
        mainFrame.setSize(600, 400);         //Set Size of window
        mainFrame.setLocation(383, 84);
        mainFrame.setLayout(null);

        headerLabel = new JLabel("Transfer Fund");
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

    private void transferFund() {
        card1 = new JLabel("Enter Account No: ");
        card1.setBounds(100, 100, 200, 25);

        card2 = new JLabel("Enter Beneficiary Account No: ");
        card2.setBounds(100, 150, 200, 25);

        amount = new JLabel("Enter amount: ");
        amount.setBounds(100, 200, 200, 25);

        cardno1 = new JTextField("");
        cardno1.setBounds(300, 100, 200, 25);

        cardno2 = new JTextField("");
        cardno2.setBounds(300, 150, 200, 25);

        amountval = new JTextField("");
        amountval.setBounds(300, 200, 200, 25);

        Transfer = new JButton("Transfer");
        Transfer.setBounds(250, 250, 100, 50);
        Transfer.setActionCommand("Transfer");
        Transfer.addActionListener(this);

        mainFrame.add(card1);
        mainFrame.add(cardno1);
        mainFrame.add(card2);
        mainFrame.add(cardno2);
        mainFrame.add(amount);
        mainFrame.add(amountval);
        mainFrame.add(Transfer);
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (command.equals("Transfer")) {
            String txt_amount = amountval.getText();
            String txt_card1 = cardno1.getText();
            String txt_card2 = cardno2.getText();
            if(txt_card1.length()==0){
                JOptionPane.showMessageDialog(mainFrame, "Cardno is required");
                cardno1.requestFocus();
            }else if(txt_card2.length()==0){
                JOptionPane.showMessageDialog(mainFrame, "Beneficiary Cardno is required");
                cardno2.requestFocus();
            }else if(txt_amount.length()==0){
                JOptionPane.showMessageDialog(mainFrame, "Amount field can't be empty");
                amountval.requestFocus();
            }else{
                int table_bal;
                try {
                    statement = connection.createStatement();
                    String query1 = "SELECT * FROM register WHERE card_no=" + txt_card1;
                    ResultSet rs1 = statement.executeQuery(query1);
                    if (rs1.next()) {
                        table_bal = rs1.getInt("balance");
                        String query2 = "SELECT * FROM register WHERE card_no=" + txt_card2;
                        ResultSet rs2 = statement.executeQuery(query2);
                        if (rs2.next()) {
                            int table_bal2 = rs2.getInt("balance");
                            if (table_bal > Integer.parseInt(txt_amount)) {
                                table_bal = table_bal - Integer.parseInt(txt_amount);
                                table_bal2 = table_bal2 + Integer.parseInt(txt_amount);
                                String sql1 = "UPDATE REGISTER SET balance=" + table_bal + " WHERE card_no=" + txt_card1;
                                statement.executeUpdate(sql1);
                                String sql3 = "UPDATE REGISTER SET balance=" + table_bal2 + " WHERE card_no=" + txt_card2;
                                statement.executeUpdate(sql3);
                                String sql2 = "INSERT INTO `transaction`(`sCardno`,`rCardno`, `change_balance`, `acc_balance`,`type`) VALUES (" + txt_card1 + "," + txt_card2 + "," + Integer.parseInt(txt_amount) + "," + table_bal + ",\"Transfer\")";
                                statement.executeUpdate(sql2);
                                
                                JOptionPane.showMessageDialog(mainFrame, "Amount Transfered Successfully");
                                mainFrame.setVisible(false);
                                ATM.mainFrame.setVisible(true);

                            } else {
                                JOptionPane.showMessageDialog(mainFrame, "Balance is not valid !");
                                amountval.requestFocus();
                            }

                        } else {
                            JOptionPane.showMessageDialog(mainFrame, "Card2 is Invaild!");
                            card2.requestFocus();
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FundTransfer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}