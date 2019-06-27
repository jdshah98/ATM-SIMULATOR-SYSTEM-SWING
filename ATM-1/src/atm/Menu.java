/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author shah jainam
 */
public class Menu implements ActionListener {

    private JFrame mainFrame;
    private JLabel statusLabel;
    private JLabel headerLabel;

    private JButton Deposit;
    private JButton Withdraw;
    private JButton Balance;
    private JButton ResetPin;
    private JButton FundTransfer;
    private JButton MiniStat;
    private final int card,cvv,pin;

    public Menu(int card, int cvv, int pin) {
        this.card = card;
        this.cvv = cvv;
        this.pin = pin;
        prepareMenu();
        designMenu();
    }

    private void prepareMenu() {
        mainFrame = new JFrame("ATM Simulator System");      //Main Heading name
        mainFrame.setSize(800, 600);         //Set Size of window
        mainFrame.setLocation(300, 84);
        mainFrame.setLayout(null);

        headerLabel = new JLabel("Select your choice....");
        headerLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        headerLabel.setBounds(300, 20, 200, 25);

        long millis = System.currentTimeMillis();
        java.util.Date date = new java.util.Date(millis);
        statusLabel = new JLabel("");
        statusLabel.setText(date.toString());
        statusLabel.setBounds(10, 530, 250, 25);
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

    private void designMenu() {
        Balance = new JButton("Balance Inquiry");
        Balance.setBounds(50, 150, 150, 40);
        Balance.setActionCommand("Balance");
        Balance.addActionListener(this);

        Deposit = new JButton("Deposit Balance");
        Deposit.setBounds(600, 150, 150, 40);
        Deposit.setActionCommand("Deposit");
        Deposit.addActionListener(this);

        Withdraw = new JButton("Withdraw Balance");
        Withdraw.setBounds(50, 250, 150, 40);
        Withdraw.setActionCommand("Withdraw");
        Withdraw.addActionListener(this);

        ResetPin = new JButton("Reset Pin");
        ResetPin.setBounds(600, 250, 150, 40);
        ResetPin.setActionCommand("ResetPin");
        ResetPin.addActionListener(this);

        FundTransfer = new JButton("Fund Transfer");
        FundTransfer.setBounds(50, 350, 150, 40);
        FundTransfer.setActionCommand("FundTransfer");
        FundTransfer.addActionListener(this);

        MiniStat = new JButton("Mini Statement");
        MiniStat.setBounds(600, 350, 150, 40);
        MiniStat.setActionCommand("MiniStat");
        MiniStat.addActionListener(this);

        mainFrame.add(Balance);
        mainFrame.add(Deposit);
        mainFrame.add(Withdraw);
        mainFrame.add(ResetPin);
        mainFrame.add(FundTransfer);
        mainFrame.add(MiniStat);
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        switch (command) {
            case "Balance":
                try {
                    BalanceInquiry bal = new BalanceInquiry(card, cvv, pin);
                    mainFrame.setVisible(false);
                } catch (SQLException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "Deposit":
                try {
                    Deposit dep = new Deposit(card);
                    mainFrame.setVisible(false);
                } catch (SQLException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "Withdraw":
                    try {
                        Withdraw with = new Withdraw(card);
                        mainFrame.setVisible(false);
                    } catch (SQLException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                break;
            case "ResetPin":
                try {
                    ResetPin rpin = new ResetPin(card, pin);
                    mainFrame.setVisible(false);
                } catch (SQLException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "FundTransfer":
                try {
                    FundTransfer ft = new FundTransfer(card);
                    mainFrame.setVisible(false);
                } catch (SQLException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "MiniStat":
                try {
                    MiniStat ms = new MiniStat(card);
                    mainFrame.setVisible(false);
                } catch (SQLException | IOException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                break;
        }
    }
}