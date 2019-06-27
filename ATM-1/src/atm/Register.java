package atm;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;
import java.util.logging.*;
import javax.swing.*;

public class Register implements ActionListener {

    static Connection connection;

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;

    private JRadioButton male, female;
    private JTextField Fname, Mname, Lname, number, email;
    private JScrollPane scrollPane;
    private JTextArea address;
    private DefaultComboBoxModel title;
    private JComboBox Title;

    public Register() throws SQLException {
        Conection conn = new Conection();
        if (conn.isConnection()) {
            connection = conn.conreturn();
        }
        prepareForm();
        designForm();
    }

    private void prepareForm() {
        mainFrame = new JFrame("ATM Simulator System");      //Main Heading name
        mainFrame.setSize(800, 600);         //Set Size of window
        mainFrame.setLocation(300, 84);
        mainFrame.setLayout(null);

        headerLabel = new JLabel("REGISTRATION WINDOW");
        headerLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        headerLabel.setBounds(275, 20, 250, 25);

        long millis = System.currentTimeMillis();
        java.util.Date date = new java.util.Date(millis);
        statusLabel = new JLabel("");
        statusLabel.setText(date.toString());
        statusLabel.setFont(new Font("Serif", Font.PLAIN, 17));
        statusLabel.setBounds(10, 530, 250, 25);

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        mainFrame.add(headerLabel);
        mainFrame.add(statusLabel);
    }

    private void designForm() {
        title = new DefaultComboBoxModel();
        title.addElement("MR");
        title.addElement("MRS");
        title.addElement("MRS");
        title.addElement("MISS");
        title.addElement("DR");
        title.addElement("MS");
        Title = new JComboBox(title);
        Title.setSelectedIndex(0);
        Title.setBounds(400, 100, 200, 20);

        JLabel TitleName = new JLabel("Title:");
        TitleName.setBounds(200, 100, 200, 20);
        JLabel FirstName = new JLabel("First Name:");
        FirstName.setBounds(200, 140, 200, 20);
        JLabel MiddleName = new JLabel("Middle Name:");
        MiddleName.setBounds(200, 180, 200, 20);
        JLabel LastName = new JLabel("Last Name:");
        LastName.setBounds(200, 220, 200, 20);
        JLabel Gender = new JLabel("Gender:");
        Gender.setBounds(200, 260, 200, 20);
        JLabel MobileNumber = new JLabel("Mobile Number:");
        MobileNumber.setBounds(200, 300, 200, 20);
        JLabel EmailId = new JLabel("Email Id:");
        EmailId.setBounds(200, 340, 200, 20);
        JLabel Address = new JLabel("Address:");
        Address.setBounds(200, 380, 200, 20);

        Fname = new JTextField("");
        Fname.setBounds(400, 140, 200, 20);

        Mname = new JTextField("");
        Mname.setBounds(400, 180, 200, 20);

        Lname = new JTextField("");
        Lname.setBounds(400, 220, 200, 20);

        male = new JRadioButton("Male", true);
        male.setEnabled(true);
        male.setBounds(400, 260, 100, 20);
        male.setActionCommand("male");
        male.addActionListener(this);

        female = new JRadioButton("female", false);
        female.setEnabled(true);
        female.setBounds(500, 260, 100, 20);
        female.setActionCommand("female");
        female.addActionListener(this);

        number = new JTextField("");
        number.setBounds(400, 300, 200, 20);

        email = new JTextField("");
        email.setBounds(400, 340, 200, 20);

        address = new JTextArea(50, 20);
        scrollPane = new JScrollPane(address);
        scrollPane.setBounds(400, 380, 200, 100);

        JButton Submit = new JButton("Submit");
        Submit.setBounds(275, 500, 100, 30);
        Submit.setActionCommand("Submit");
        Submit.addActionListener(new ButtonClickListener());

        JButton Reset = new JButton("Reset");
        Reset.setBounds(425, 500, 100, 30);
        Reset.setActionCommand("Reset");
        Reset.addActionListener(new ButtonClickListener());

        mainFrame.add(Submit);
        mainFrame.add(Reset);
        mainFrame.add(TitleName);
        mainFrame.add(Title);
        mainFrame.add(FirstName);
        mainFrame.add(Fname);
        mainFrame.add(MiddleName);
        mainFrame.add(Mname);
        mainFrame.add(LastName);
        mainFrame.add(Lname);
        mainFrame.add(Gender);
        mainFrame.add(male);
        mainFrame.add(female);
        mainFrame.add(MobileNumber);
        mainFrame.add(number);
        mainFrame.add(EmailId);
        mainFrame.add(email);
        mainFrame.add(Address);
        mainFrame.add(scrollPane);
        ATM.mainFrame.setVisible(false);
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("male")) {
            male.setSelected(true);
            female.setSelected(false);
        } else if (command.equals("female")) {
            male.setSelected(false);
            female.setSelected(true);
        }
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("Submit")) {
                int input;
                input = JOptionPane.showConfirmDialog(null, "Do you want to proceed?", "select an option", JOptionPane.YES_NO_OPTION);
                if (input == 1) {
                    return;
                }
                String init;
                init = (String) Title.getSelectedItem();
                String fn = Fname.getText();
                if (fn.length() == 0) {
                    JOptionPane.showMessageDialog(mainFrame, "First Name is required");
                    Fname.requestFocus();
                    return;
                }
                String mn = Mname.getText();
                if (mn.length() == 0) {
                    JOptionPane.showMessageDialog(mainFrame, "Middle Name is required");
                    Mname.requestFocus();
                    return;
                }
                String ln = Lname.getText();
                if (ln.length() == 0) {
                    JOptionPane.showMessageDialog(mainFrame, "Last Name is required");
                    Lname.requestFocus();
                    return;
                }
                String gn;
                if (male.isSelected()) {
                    gn = male.getText();
                } else {
                    gn = female.getText();
                }
                String num = number.getText();
                if (num.length() == 0) {
                    JOptionPane.showMessageDialog(mainFrame, "Mobile Number can't be empty");
                    number.requestFocus();
                    return;
                } else if (num.length() != 10) {
                    JOptionPane.showMessageDialog(mainFrame, "10 digits Mobile Number required");
                    number.requestFocus();
                    return;
                } else {
                    try {
                        Double.parseDouble(num);
                    } catch (NumberFormatException n) {
                        JOptionPane.showMessageDialog(mainFrame, "Mobile Number couldn't contain character");
                        number.requestFocus();
                        return;
                    }
                }
                String em = email.getText();
                if (em.length() != 0) {
                    if (em.contains(" ") || !em.contains("@") || em.indexOf(".") < em.indexOf("@") || em.indexOf(".") >= (em.length() - 1) || em.indexOf("@") < 1) {
                        JOptionPane.showMessageDialog(mainFrame, "email is incorrect");
                        email.requestFocus();
                        return;
                    }
                }
                String str = address.getText();
                if (str.length() == 0) {
                    JOptionPane.showMessageDialog(mainFrame, "Address is required");
                    address.requestFocus();
                    return;
                }

                Statement statment = null;
                try {
                    Random ran=new Random();
                    int pin,cvv;
                    statment = connection.createStatement();
                    pin=ran.nextInt(10000);
                    cvv=ran.nextInt(1000);
                    Double balance=10000.00;
                    String sql_insert = "INSERT INTO REGISTER (`lname`, `fname`, `mname`, `address`, `gender`, `phone_no`, `email_id`, `pin`, `cvv`, `balance`) VALUES (" + "\"" + ln + "\"" + "," + "\"" + fn + "\"" + "," + "\"" + mn + "\"" + "," + "\"" + str + "\"" + "," + "\"" + gn + "\"" + "," + "\"" + num + "\"" + "," + "\"" + em + "\"" + "," + pin +","+ cvv+"," + balance + ")";
                    //System.out.println(sql_insert+"");
                    statment.executeUpdate(sql_insert);
                    String sql_insert_userdata = "INSERT INTO USER (`password`,`cvv`) VALUES (" + pin +","+cvv+ ")";
                    statment.executeUpdate(sql_insert_userdata);
                    String sql_card="SELECT * FROM USER WHERE `password`="+pin+" AND `cvv`="+cvv;
                    ResultSet rs1=statment.executeQuery(sql_card);
                    String card_no_holder = null;
                    if(rs1.next())
                    {
                        card_no_holder=rs1.getString("user_id");
                    }
                    JOptionPane.showMessageDialog(mainFrame, "Card No: "+card_no_holder+"\r\nPin No: "+pin+"\r\nCvv: "+cvv);
                    ATM.mainFrame.setVisible(true);
                    mainFrame.setVisible(false);
                } catch (SQLException ex) {
                    Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (command.equals("Reset")) {
                Title.setSelectedIndex(0);
                Fname.setText("");
                Mname.setText("");
                Lname.setText("");
                male.setSelected(true);
                female.setSelected(false);
                number.setText("");
                email.setText("");
                address.setText("");
            }
        }
    }
}
