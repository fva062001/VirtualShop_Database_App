package VirtualShop;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.io.IOException;


public class central extends JFrame {
    private JPanel Panel1;
    private JTabbedPane Panel25;
    private JPanel Panel2;
    private JPanel Panel3;
    private JTabbedPane tbpanel2;
    private JPanel pPhone;
    private JPanel pLaptop;
    private JLabel lPhoneName;
    private JPanel Panel4;
    private JTextField tfPhoneName;
    private JPanel Panel5;
    private JLabel lPhonePrice;
    private JTextField tfPhonePrice;
    private JPanel Panel6;
    private JLabel lPhoneQuantity;
    private JTextField tfPhoneQuantity;
    private JPanel Panel7;
    private JButton btnAddPhone;
    private JButton btnDeletePhone;
    private JButton btnEditPhone;
    private JScrollPane sc1;
    private JTable tablePanel1;
    private JPanel Panel9;
    private JLabel lPhoneCode;
    private JPanel Panel10;
    private JTextField tfPhoneCode;
    private JLabel lLaptopCode;
    private JPanel Panel11;
    private JTextField tfLaptopCode;
    private JPanel Panel12;
    private JLabel lLaptopName;
    private JPanel Panel13;
    private JTextField tfLaptopName;
    private JPanel Panel14;
    private JLabel lLaptopPrice;
    private JPanel Panel15;
    private JTextField tfLaptopPrice;
    private JPanel Panel16;
    private JLabel lLaptopQuantity;
    private JPanel Panel17;
    private JTextField tfLaptopQuantity;
    private JPanel Panel18;
    private JPanel Panel19;
    private JButton btnAddLaptop;
    private JButton btnEditLaptop;
    private JButton btnDeleteLaptop;
    private JPanel Panel20;
    private JButton btnRefresh;
    private JLabel lLaptopDetails;
    private JTextArea taLaptopDetails;
    private JPanel Panel22;
    private JLabel lPhoneDetails;
    private JTextArea taPhoneDetails;
    private JPanel Panel26;
    private JLabel lVenta;
    private JPanel Panel27;
    private JLabel lProductCode;
    private JPanel Panel28;
    private JTextField tfProductCode;
    private JTextField tfQuantity;
    private JLabel lQuantity;
    private JButton btnSell;
    private JButton btnReplenish;
    private JPanel Panel30;
    private JButton btnPhoneList;
    private JPanel Panel31;
    private JButton btnLaptopList;
    private JLabel lQuantityPut;
    private JTextField tfQuantityPut;
    private JTextField tfPhoneOperator;
    private int total = 0;
    private int firstnumber = 0;
    private int total1 = 0;
    private int secondnumber = 0;
    private double pagar = 0;
    private double totalpagar = 0;



    public central() {
        this.setTitle("VirtualShop");
        this.setSize(650, 650);
        this.add(Panel1);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //Setting Phones

        btnAddPhone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MyConnection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualshop","root","123456");
                    Statement stmt = con.createStatement();

                    //Variables
                    String phonecode = tfPhoneCode.getText();
                    String phonename = tfPhoneName.getText();
                    double phoneprice = Double.parseDouble(tfPhonePrice.getText());
                    int phonequantity = Integer.parseInt(tfPhoneQuantity.getText());
                    String phonedetails = taPhoneDetails.getText();
                    //Adding
                    stmt.executeUpdate("INSERT into products (productcode, productname,productprice, productquantity, producttype,productdescription)\n" +
                            "VALUES( '" + phonecode + "', '"+phonename+"', "+phoneprice+", "+phonequantity+", 'phone', '"+phonedetails+"')");
                    JOptionPane.showMessageDialog(central.this, "Se ha insertado el dispositivo dentro de la base de datos correctamente");
                    //Setting text field to clean
                    tfPhoneCode.setText("");
                    tfPhoneName.setText("");
                    tfPhonePrice.setText("");
                    tfPhoneQuantity.setText("");
                    taPhoneDetails.setText("");
                    stmt.close();


                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(central.this, "No se ha insertado el dispositivo, porfavor intentelo denuevo");
                    exception.printStackTrace();
                }
            }
        });

        btnDeletePhone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualshop","root","123456");
                    PreparedStatement prstm = con.prepareStatement("DELETE FROM products WHERE productcode =?");
                    String phonecode =  tfPhoneCode.getText();
                    prstm.setString(1, phonecode);
                    prstm.executeUpdate();
                    tfPhoneCode.setText("");
                    JOptionPane.showMessageDialog(central.this, "Se ha eliminado exitosamente el producto");
                    prstm.close();

                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(central.this, "El  producto no ha podido eliminarse correctamente, intentelo nuevamente");
                }
            }
        });


        btnEditPhone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualshop", "root", "123456");
                    if(tfPhoneCode.isEnabled()) {
                        ResultSet rs =  con.createStatement().executeQuery("SELECT * FROM products WHERE productcode = '"+tfPhoneCode.getText()+"'");
                        if(rs.next()) {
                            tfPhoneName.setText(rs.getString("productcode"));
                            tfPhoneName.setEnabled(false);
                            tfPhonePrice.setText(rs.getDouble("productprice") + "");
                            tfPhoneQuantity.setText(rs.getInt("productquantity") + "");
                            tfPhoneQuantity.setEnabled(false);
                            taPhoneDetails.setText(rs.getString("productdescription"));
                            tfPhoneCode.setEnabled(false);
                        }
                    }else
                    {
                        PreparedStatement preparedStmt = con.prepareStatement("update products set productdescription = ?,productprice = ? where productcode = ?");
                        preparedStmt.setString(1, taPhoneDetails.getText());
                        preparedStmt.setDouble(2, Double.parseDouble(tfPhonePrice.getText()));
                        preparedStmt.setString(3, tfPhoneCode.getText());
                        tfPhoneName.setEnabled(true);
                        tfPhoneQuantity.setEnabled(true);
                        tfPhoneCode.setEnabled(true);
                        tfPhoneName.setText("");
                        tfPhoneQuantity.setText("");
                        tfPhonePrice.setText("");
                        taPhoneDetails.setText("");
                        JOptionPane.showMessageDialog(central.this, "Se ha eliminado exitosamente el producto");
                        preparedStmt.executeUpdate();
                        con.close();
                    }
                }catch (SQLException exception)
                {
                    exception.printStackTrace();
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        });


        //Settings Laptops

        btnAddLaptop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualshop","root","123456");
                    Statement stmt = con.createStatement();

                    //Variables
                    String laptopcode = tfLaptopCode.getText();
                    String laptopname = tfLaptopName.getText();
                    double laptopprice = Double.parseDouble(tfLaptopPrice.getText());
                    int laptopquantity = Integer.parseInt(tfLaptopQuantity.getText());

                    String laptopdetails = taLaptopDetails.getText();
                    //Adding
                    stmt.executeUpdate("INSERT into products (productcode, productname,productprice, productquantity, producttype,productdescription)\n" +
                            "VALUES( '" + laptopcode + "', '"+laptopname+"', "+laptopprice+", "+laptopquantity+", 'laptop', '"+laptopdetails+"')");
                    JOptionPane.showMessageDialog(central.this, "Se ha insertado el dispositivo dentro de la base de datos correctamente");
                    //Setting text field to clean
                    tfLaptopCode.setText("");
                    tfLaptopName.setText("");
                    tfLaptopPrice.setText("");
                    tfLaptopQuantity.setText("");
                    taLaptopDetails.setText("");
                    stmt.close();


                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(central.this, "No se ha insertado el dispositivo, porfavor intentelo denuevo");
                }
            }
        });

        btnDeleteLaptop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualshop","root","123456");
                    PreparedStatement prstm = con.prepareStatement("DELETE FROM products WHERE productcode =?");
                    String laptopcode =  tfLaptopCode.getText();
                    prstm.setString(1, laptopcode);
                    prstm.executeUpdate();
                    tfLaptopCode.setText("");
                    JOptionPane.showMessageDialog(central.this, "Se ha eliminado exitosamente el producto");
                    prstm.close();

                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(central.this, "El  producto no ha podido eliminarse correctamente, intentelo nuevamente");
                }
            }
        });

        btnEditLaptop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try
                {
                    Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualshop", "root", "123456");
                    if(tfLaptopCode.isEnabled()) {
                        ResultSet rs =  con.createStatement().executeQuery("SELECT * FROM products WHERE productcode = '"+tfLaptopCode.getText()+"'");
                        if(rs.next()) {
                            tfLaptopName.setText(rs.getString("productcode"));
                            tfLaptopName.setEnabled(false);
                            tfLaptopPrice.setText(rs.getDouble("productprice") + "");
                            tfLaptopQuantity.setText(rs.getInt("productquantity") + "");
                            tfLaptopQuantity.setEnabled(false);
                            taLaptopDetails.setText(rs.getString("productdescription"));
                            tfLaptopCode.setEnabled(false);
                        }
                    }else
                    {
                        PreparedStatement preparedStmt = con.prepareStatement("update products set productdescription = ?,productprice = ? where productcode = ?");
                        preparedStmt.setString(1, taLaptopDetails.getText());
                        preparedStmt.setDouble(2, Double.parseDouble(tfLaptopPrice.getText()));
                        preparedStmt.setString(3, tfLaptopCode.getText());
                        tfLaptopName.setEnabled(true);
                        tfLaptopQuantity.setEnabled(true);
                        tfLaptopPrice.setEnabled(true);
                        tfLaptopName.setText("");
                        tfLaptopQuantity.setText("");
                        tfLaptopPrice.setText("");
                        taLaptopDetails.setText("");
                        JOptionPane.showMessageDialog(central.this, "El producto se ha editado exitosamente");
                        preparedStmt.executeUpdate();

                        con.close();
                    }
                }catch (SQLException exception)
                {
                    exception.printStackTrace();
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        });

        //Setting Search

        btnPhoneList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualshop", "root", "123456");
                    ResultSet rs =  con.createStatement().executeQuery("SELECT * FROM products WHERE productcode = '"+tfPhoneCode.getText()+"'");
                    if(rs.next()) {
                        tfPhoneName.setText(rs.getString("productcode"));
                        tfPhonePrice.setText(rs.getDouble("productprice") + "");
                        tfPhoneQuantity.setText(rs.getInt("productquantity") + "");
                        taPhoneDetails.setText(rs.getString("productdescription"));
                        rs.close();
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        btnLaptopList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualshop", "root", "123456");
                    ResultSet rs =  con.createStatement().executeQuery("SELECT * FROM products WHERE productcode = '"+tfLaptopCode.getText()+"'");
                    if(rs.next()) {
                        tfLaptopName.setText(rs.getString("productcode"));
                        tfLaptopPrice.setText(rs.getDouble("productprice") + "");
                        tfLaptopQuantity.setText(rs.getInt("productquantity") + "");
                        taLaptopDetails.setText(rs.getString("productdescription"));
                        rs.close();
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
        //Setting Refresh

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DefaultTableModel algo = new DefaultTableModel(new String[]{"Product Code","Product Name","Product Price", "Product Type","Product Quantity"},0);
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualshop", "root", "123456");
                    ResultSet rs =  con.createStatement().executeQuery("SELECT*FROM products");
                    while(rs.next())
                    {
                        algo.addRow(new Object[]{
                                rs.getString("productcode"),rs.getString("productname"),rs.getString("productprice"),
                               rs.getString("producttype"),rs.getString("productquantity")
                                }
                                );

                    }
                    tablePanel1.setModel(algo);
                }catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        });

        //Setting Sell & Replenish
        btnSell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualshop", "root", "123456");
                    if(tfProductCode.isEnabled()) {
                        ResultSet rs =  con.createStatement().executeQuery("SELECT * FROM products WHERE productcode = '"+tfProductCode.getText()+"'");
                        if(rs.next()) {
                            tfQuantity.setText(rs.getString("productquantity"));
                            tfQuantity.setEnabled(false);
                            secondnumber = Integer.parseInt(tfQuantity.getText());
                            pagar = rs.getDouble("productprice");
                            tfProductCode.setEnabled(false);
                        }
                    }else
                    {
                        int fin = 0;
                        total1 = secondnumber - Integer.parseInt(tfQuantityPut.getText());
                        totalpagar = pagar*Double.parseDouble(tfQuantityPut.getText());
                        PreparedStatement preparedStmt = con.prepareStatement("update products set productquantity = ? where productcode = ?");
                        preparedStmt.setDouble(1, total1);
                        preparedStmt.setString(2,tfProductCode.getText());
                        preparedStmt.executeUpdate();
                        ResultSet rs =  con.createStatement().executeQuery("SELECT * FROM products WHERE productcode = '"+tfProductCode.getText()+"'");
                        if(rs.next()) {
                            fin = rs.getInt("productquantity");
                        }
                        JOptionPane.showMessageDialog(central.this, "Se han vendido -> "+tfQuantityPut.getText()+" y ha quedado como resultado: "+ fin+"\n"+"Total a pagar -> "+totalpagar+"$");
                        con.close();
                        tfProductCode.setEnabled(true);
                        tfProductCode.setText("");
                        tfQuantity.setEnabled(true);
                        tfQuantity.setText("");
                        tfQuantityPut.setText("");
                    }
                }catch (SQLException exception)
                {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(central.this, "Ha ocurrido un error, intentelo nuevamente");

                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }



            }
        });

        btnReplenish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualshop", "root", "123456");
                    if(tfProductCode.isEnabled()) {
                        ResultSet rs =  con.createStatement().executeQuery("SELECT * FROM products WHERE productcode = '"+tfProductCode.getText()+"'");
                        if(rs.next()) {
                            tfQuantity.setText(rs.getString("productquantity"));
                            tfQuantity.setEnabled(false);
                            firstnumber = Integer.parseInt(tfQuantity.getText());
                            tfProductCode.setEnabled(false);
                        }
                    }else
                    {
                        int fin = 0;
                        total = firstnumber + Integer.parseInt( tfQuantityPut.getText());
                        PreparedStatement preparedStmt = con.prepareStatement("update products set productquantity = ? where productcode = ?");
                        preparedStmt.setDouble(1, total);
                        preparedStmt.setString(2,tfProductCode.getText());
                        preparedStmt.executeUpdate();
                        ResultSet rs =  con.createStatement().executeQuery("SELECT * FROM products WHERE productcode = '"+tfProductCode.getText()+"'");
                        if(rs.next()) {
                            fin = rs.getInt("productquantity");
                        }
                        JOptionPane.showMessageDialog(central.this, "Se han agregado -> "+tfQuantityPut.getText()+" y ha quedado como resultado: "+ fin);
                        con.close();
                        tfProductCode.setEnabled(true);
                        tfProductCode.setText("");
                        tfQuantity.setEnabled(true);
                        tfQuantity.setText("");
                        tfQuantityPut.setText("");
                    }
                }catch (SQLException exception)
                {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(central.this, "Ha ocurrido un error, intentelo nuevamente");
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        });

    }
    private void createUIComponents() {
        tablePanel1 = new JTable(new DefaultTableModel(new String[]{"Product Code","Product Name","Product Price", "Product Type","Product Quantity"},0));
    }
}
