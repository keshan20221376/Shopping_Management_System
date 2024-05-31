import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ShoppingCartUI extends JFrame {
    JTable shoppingCartTable;
    JLabel totalPriceLabel;
    JLabel totalPriceValue;
    JScrollPane tableSP;
    JLabel discount1Label;
    JLabel discount1Value;
    JLabel discount2Label;
    JLabel discount2Value;
    JLabel finalTotalLabel;
    JLabel finalTotalValue;
    JButton checkoutButton;
    private double discount1 = 0;
    private double discount2 = 0;
    private double finalTotal = 0;

    public ShoppingCartUI(ShoppingCart shoppingCart){
        //Check if shopping cart is empty and show message without opening the UI window
        if(shoppingCart.getProductList().isEmpty()){
            JOptionPane.showMessageDialog(null,"Shopping Cart is Empty");
            return;
        }

        //Create table and fill data of the table
        fillRowData(shoppingCart);
        shoppingCartTable.setDefaultEditor(Object.class, null); //Disable editing
        shoppingCartTable.getColumnModel().getColumn(0).setPreferredWidth(300);

        //Make table data center aligned
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < shoppingCartTable.getColumnCount(); i++) {
            shoppingCartTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        //Make table header center aligned
        JTableHeader tableHeader = shoppingCartTable.getTableHeader();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) shoppingCartTable.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableHeader.setFont(tableHeader.getFont().deriveFont(Font.BOLD));
        tableHeader.setDefaultRenderer(headerRenderer);

        //Add table to scroll pane
        tableSP = new JScrollPane(shoppingCartTable);
        tableSP.setBounds(30,30,640,150);

        shoppingCart.calculateTotalCost(); //Calculate total cost of the shopping cart
        totalPriceLabel = new JLabel("Total Price " );
        totalPriceLabel.setBounds(480,200,100,20);

        //Display total price of the shopping cart
        totalPriceValue = new JLabel(String.format("%.2f",shoppingCart.getTotalCost()) + " £");
        totalPriceValue.setBounds(600,200,100,20);

        discount1Label = new JLabel("First Purchase Discount (10%) " );
        discount1Label.setBounds(360,260,200,20);
        discount1Label.setVisible(false);

        discount1Value = new JLabel();
        discount1Value.setBounds(600,260,200,20);
        discount1Value.setVisible(false);

        discount2Label = new JLabel("Three Items in same Category Discount (20%) " );
        discount2Label.setBounds(260,320,300,20);
        discount2Label.setVisible(false);

        discount2Value = new JLabel();
        discount2Value.setBounds(600,320,200,20);
        discount2Value.setVisible(false);

        finalTotalLabel = new JLabel("Final Total " );
        finalTotalLabel.setBounds(480,380,100,20);
        finalTotalLabel.setFont(finalTotalLabel.getFont().deriveFont(Font.BOLD));
        finalTotalLabel.setVisible(false);

        finalTotalValue = new JLabel();
        finalTotalValue.setBounds(600,380,100,20);
        finalTotalValue.setFont(finalTotalValue.getFont().deriveFont(Font.BOLD));
        finalTotalValue.setVisible(false);

        //Checkout button to save the purchase of the user and clear the shopping cart
        checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(300,450,100,30);
        checkoutButton.addActionListener(e -> {
            //Check if discount is available and if it is available
            //then save the final total cost as the total cost of the shopping cart for future reference
            if (finalTotal != 0) {
                shoppingCart.setTotalCost(finalTotal);
                shoppingCart.getUser().getPurchaseHistory().add(new Purchase(shoppingCart.getProductList(),finalTotal));
            } else {
                shoppingCart.getUser().getPurchaseHistory().add(new Purchase(shoppingCart.getProductList(),shoppingCart.getTotalCost()));
            }

            shoppingCart.getProductList().clear(); //Clear the shopping cart

            //Show message after successful checkout
            JOptionPane.showMessageDialog(null,"Checkout Successful");
            dispose(); //Close the shopping cart window
        });

        //Check if discount is available and if it is available then show the discounted prices
        checkDiscountOne(shoppingCart);
        checkDiscountTwo(shoppingCart);
        //Calculate final total of the shopping cart if discount is available
        calculateFinalTotal(shoppingCart);

        add(tableSP);
        add(totalPriceLabel);
        add(totalPriceValue);
        add(discount1Label);
        add(discount1Value);
        add(discount2Label);
        add(discount2Value);
        add(finalTotalLabel);
        add(finalTotalValue);
        add(checkoutButton);

        setTitle("Shopping Cart");
        setSize(700,520);
        setLayout(null);
        setVisible(true);
    }

    //Check if the user is purchasing for the first time and if it is then show the discount
    public void checkDiscountOne(ShoppingCart shoppingCart){
        if(shoppingCart.getUser().getPurchaseHistory().isEmpty()){
            discount1Label.setVisible(true);
            discount1Value.setVisible(true);
            discount1 = shoppingCart.getTotalCost() * 0.1;
            discount1Value.setText("-" + String.format("%.2f",discount1) + " £");
            finalTotalLabel.setVisible(true);
            finalTotalValue.setVisible(true);
        }
    }

    //Check if the user is purchasing three items of the same category and if it is then show the discount
    public void checkDiscountTwo(ShoppingCart shoppingCart){
        int countElectronics = 0;
        int countClothing = 0;
        for (ShoppingCartElement temp : shoppingCart.getProductList()) {
            if (temp.getProduct() instanceof Electronics) {
                countElectronics = countElectronics + temp.getQuantity();
                if (countElectronics == 3) {
                    discount2Label.setVisible(true);
                    discount2Value.setVisible(true);
                    discount2 = shoppingCart.getTotalCost() * 0.2;
                    discount2Value.setText("-" + String.format("%.2f",discount2) + " £");
                    finalTotalLabel.setVisible(true);
                    finalTotalValue.setVisible(true);
                    return;
                }
            } else {
                countClothing = countClothing + temp.getQuantity();
                if (countClothing == 3) {
                    discount2Label.setVisible(true);
                    discount2Value.setVisible(true);
                    discount2 = shoppingCart.getTotalCost() * 0.2;
                    discount2Value.setText("-" + String.format("%.2f",discount2) + " £");
                    finalTotalLabel.setVisible(true);
                    finalTotalValue.setVisible(true);
                    return;
                }
            }
        }
    }


    //Calculate final total of the shopping cart if discount is available
    public void calculateFinalTotal(ShoppingCart shoppingCart) {
        if (discount1 != 0 && discount2 != 0) {
            finalTotal = shoppingCart.getTotalCost() - (discount1 + discount2);
            finalTotalValue.setText(String.format("%.2f",finalTotal) + " £");
        } else if (discount1 != 0) {
            finalTotal = shoppingCart.getTotalCost() - discount1;
            finalTotalValue.setText(String.format("%.2f",finalTotal) + " £");
        } else if (discount2 != 0) {
            finalTotal = shoppingCart.getTotalCost() - discount2;
            finalTotalValue.setText(String.format("%.2f",finalTotal) + " £");
        }
    }

    //Fill data of the table
    public void fillRowData(ShoppingCart shoppingCart) {
        String[] columnHeading = {"Product Name", "Quantity", "Price(£)"}; //Column headings of the table
        String[][] rows = new String[shoppingCart.getProductList().size()][3]; //Data of the table

        int i = 0;
        for (ShoppingCartElement temp : shoppingCart.getProductList()) {
            String info = "";
            //Check the product's category and display the information accordingly
            if (temp.getProduct() instanceof Electronics) {
                info = ((Electronics) temp.getProduct()).getBrandName() + ", " + ((Electronics) temp.getProduct()).getWarrantyPeriod() + " Months Warranty";
            } else {
                info = ((Clothing) temp.getProduct()).getSize() + ", " + ((Clothing) temp.getProduct()).getColor();
            }

            rows[i][0] = temp.getProduct().getId() + " " + temp.getProduct().getProductName() + " " + info;
            rows[i][1] = String.valueOf(temp.getQuantity());
            rows[i][2] = String.format("%.2f", (temp.getProduct().getPrice() * temp.getQuantity()));

            i++;
        }

        shoppingCartTable = new JTable(rows, columnHeading);
        shoppingCartTable.repaint();
    }
}
