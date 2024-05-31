import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface extends JFrame {
    private User user;
    ShoppingCart cart ;
    ShoppingCartUI shoppingCartUI;
    JLabel selectionLabel;
    JComboBox<String> selectionComboBox;
    JTable productTable;
    DefaultTableModel tableModel;
    JSeparator separator;
    JScrollPane productSP;
    JLabel productDetailsHeading;
    JLabel selectedProductID;
    JLabel selectedProductCategory;
    JLabel selectedProductName;
    JLabel selectedProductSize;
    JLabel selectedProductColor;
    JLabel selectedProductBrandName;
    JLabel selectedProductWarrantyPeriod;
    JLabel selectedProductAvailableItemCount;
    JPanel belowTablePanel;
    JButton addToCart;
    JButton shoppingCart;

    public UserInterface(WestminsterShoppingManager westminsterShoppingManager, User user){
        this.user = user;
        this.cart = new ShoppingCart(user);
        setTitle("Westminster Shopping Centre"); //title of the user interface

        selectionLabel = new JLabel();
        selectionLabel.setText("Select Product Category");
        selectionLabel.setBounds(70,40,600,30);

        String[] selection = { "All","Electronics", "Clothing"}; //Drop down menu options

        String[] productTableDataColumns = {"Product ID","Name","Category","Price(£)","Info","Available Item Count"}; //table column names
        String[][] productTableDataRows = new String[westminsterShoppingManager.getProductList().size()][6]; //table data

        initializeTableData(productTableDataRows,westminsterShoppingManager,"All"); //fill data of the table

        tableModel = new DefaultTableModel(productTableDataRows,productTableDataColumns); //model for the table
        productTable = new JTable(tableModel);

        productTable.setDefaultEditor(Object.class, null); //makes the table uneditable

        //Make custom renderer to highlight rows with less than 3 items available
        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (row >= 0 && row < tableModel.getRowCount() && !isSelected && !hasFocus) {
                    if(tableModel.getValueAt(row, 5) != null) {
                        int availableItemCount = Integer.parseInt(tableModel.getValueAt(row, 5).toString());
                        if (availableItemCount <= 3) {
                            component.setBackground(new Color(255, 0, 0, 70));
                        } else {
                            component.setBackground(table.getBackground());
                        }
                    } else {
                        component.setBackground(table.getBackground());
                    }
                }
                return component;
            }
        };
        //Make table data center aligned
        customRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < productTable.getColumnCount(); i++) {
            productTable.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }

        //Make table header center aligned
        JTableHeader tableHeader = productTable.getTableHeader();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) productTable.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableHeader.setFont(tableHeader.getFont().deriveFont(Font.BOLD));
        tableHeader.setDefaultRenderer(headerRenderer);

        //Drop down menu to select product category
        selectionComboBox = new JComboBox<>(selection);
        selectionComboBox.setBounds(250,45,200,30);
        selectionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) selectionComboBox.getSelectedItem(); //get selected category
                //fill table data according to the selected category
                if(selectedCategory.equals("All")){
                    initializeTableData(productTableDataRows,westminsterShoppingManager,"All");
                    tableModel.setDataVector(productTableDataRows, productTableDataColumns);
                    for (int i = 0; i < productTable.getColumnCount(); i++) {
                        productTable.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
                    }
                    productTable.repaint();
                    productTable.getColumnModel().getColumn(4).setPreferredWidth(200);
                    productTable.getColumnModel().getColumn(5).setMinWidth(0);
                    productTable.getColumnModel().getColumn(5).setMaxWidth(0);
                    productTable.getColumnModel().getColumn(5).setWidth(0);
                }else if(selectedCategory.equals("Electronics")){
                    initializeTableData(productTableDataRows,westminsterShoppingManager,"Electronics");
                    tableModel.setDataVector(productTableDataRows, productTableDataColumns);
                    for (int i = 0; i < productTable.getColumnCount(); i++) {
                        productTable.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
                    }
                    productTable.repaint();
                    productTable.getColumnModel().getColumn(4).setPreferredWidth(200);
                    productTable.getColumnModel().getColumn(5).setMinWidth(0);
                    productTable.getColumnModel().getColumn(5).setMaxWidth(0);
                    productTable.getColumnModel().getColumn(5).setWidth(0);
                }else if(selectedCategory.equals("Clothing")){
                    initializeTableData(productTableDataRows,westminsterShoppingManager,"Clothing");
                    tableModel.setDataVector(productTableDataRows, productTableDataColumns);
                    for (int i = 0; i < productTable.getColumnCount(); i++) {
                        productTable.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
                    }
                    productTable.repaint();
                    productTable.getColumnModel().getColumn(4).setPreferredWidth(200);
                    productTable.getColumnModel().getColumn(5).setMinWidth(0);
                    productTable.getColumnModel().getColumn(5).setMaxWidth(0);
                    productTable.getColumnModel().getColumn(5).setWidth(0);
                }
            }
        });

        //Hide the table column with items available count and make the info column wider
        productTable.getColumnModel().getColumn(4).setPreferredWidth(200);
        productTable.getColumnModel().getColumn(5).setMinWidth(0);
        productTable.getColumnModel().getColumn(5).setMaxWidth(0);
        productTable.getColumnModel().getColumn(5).setWidth(0);
        //Add listener to the table to display product details when a row is selected
        //Change the visibility of the labels according to the selected product's category
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow >= 0) {
                    productDetailsHeading.setText("Selected Product - Details");
                    for(Product temp : westminsterShoppingManager.getProductList()){
                        if(temp.getId().equals(productTable.getValueAt(selectedRow,0).toString())){
                            selectedProductID.setText("Product ID:         " + temp.getId());
                            selectedProductCategory.setText("Category:            " + temp.getClass().getSimpleName());
                            selectedProductName.setText("Name:                 " + temp.getProductName());
                            selectedProductID.setVisible(true);
                            selectedProductCategory.setVisible(true);
                            selectedProductName.setVisible(true);
                            if(temp instanceof Electronics) {
                                selectedProductBrandName.setText("Brand Name:       " + ((Electronics) temp).getBrandName());
                                selectedProductWarrantyPeriod.setText("Warranty Period: " + ((Electronics) temp).getWarrantyPeriod() + " Months");
                                selectedProductSize.setVisible(false);
                                selectedProductColor.setVisible(false);
                                selectedProductBrandName.setVisible(true);
                                selectedProductWarrantyPeriod.setVisible(true);
                            } else {
                                selectedProductSize.setText("Size:                    " + ((Clothing) temp).getSize());
                                selectedProductColor.setText("Color:                  " + ((Clothing) temp).getColor());
                                selectedProductBrandName.setVisible(false);
                                selectedProductWarrantyPeriod.setVisible(false);
                                selectedProductSize.setVisible(true);
                                selectedProductColor.setVisible(true);
                            }
                            selectedProductAvailableItemCount.setText("Items Available:  " + temp.getAvailableItemCount());
                            selectedProductAvailableItemCount.setVisible(true);
                            break;
                        }
                    }
                } else {
                    //If no row is selected, hide the product details
                    productDetailsHeading.setText("No Product Selected");
                    selectedProductID.setVisible(false);
                    selectedProductCategory.setVisible(false);
                    selectedProductName.setVisible(false);
                    selectedProductSize.setVisible(false);
                    selectedProductColor.setVisible(false);
                    selectedProductBrandName.setVisible(false);
                    selectedProductWarrantyPeriod.setVisible(false);
                    selectedProductAvailableItemCount.setVisible(false);
                }
            }
        });
        productSP = new JScrollPane(productTable);
        productSP.setBounds(30,80,730,300);

        //Add a separator to separate the table and the product details
        separator = new JSeparator();
        separator.setBounds(0,400,800,20);

        productDetailsHeading = new JLabel();
        productDetailsHeading.setText("No Product Selected");
        productDetailsHeading.setFont(productDetailsHeading.getFont().deriveFont(Font.BOLD));
        productDetailsHeading.setBounds(70,430,200,20);

        belowTablePanel = new JPanel();
        belowTablePanel.setLayout(new BoxLayout(belowTablePanel, BoxLayout.Y_AXIS));
        belowTablePanel.setBounds(70,480,800,200);

        selectedProductID = new JLabel();
        selectedProductID.setBorder(new EmptyBorder(0,0,10,0)); //adds padding to the label
        selectedProductID.setVisible(false);

        selectedProductCategory = new JLabel();
        selectedProductCategory.setBorder(new EmptyBorder(10,0,10,0));
        selectedProductCategory.setVisible(false);

        selectedProductName = new JLabel();
        selectedProductName.setBorder(new EmptyBorder(10,0,10,0));
        selectedProductName.setVisible(false);

        selectedProductSize = new JLabel();
        selectedProductSize.setBorder(new EmptyBorder(10,0,10,0));
        selectedProductSize.setVisible(false);

        selectedProductColor = new JLabel();
        selectedProductColor.setBorder(new EmptyBorder(10,0,10,0));
        selectedProductColor.setVisible(false);

        selectedProductBrandName = new JLabel();
        selectedProductBrandName.setBorder(new EmptyBorder(10,0,10,0));
        selectedProductBrandName.setVisible(false);

        selectedProductWarrantyPeriod = new JLabel();
        selectedProductWarrantyPeriod.setBorder(new EmptyBorder(10,0,10,0));
        selectedProductWarrantyPeriod.setVisible(false);

        selectedProductAvailableItemCount = new JLabel();
        selectedProductAvailableItemCount.setBorder(new EmptyBorder(10,0,10,0));
        selectedProductAvailableItemCount.setVisible(false);

        //Add to cart button
        addToCart = new JButton("Add to Shopping Cart");
        addToCart.setBounds(300,700,200,35);
        addToCart.addActionListener(new  ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selectedRow = productTable.getSelectedRow();
                //Check if a product is selected and add it to the cart
                if (selectedRow >= 0) {
                    for(Product temp : westminsterShoppingManager.getProductList()){
                        if(temp.getId().equals(productTable.getValueAt(selectedRow,0).toString())){
                            if(temp.getAvailableItemCount() > 0) {
                                cart.addProduct(temp);
                                //Reduce the available item count of the product by 1
                                temp.setAvailableItemCount(temp.getAvailableItemCount() - 1);
                                selectedProductAvailableItemCount.setText("Items Available:  " + temp.getAvailableItemCount());
                                //If the shopping cart UI windows is opened, dispose it to open a new one with updated data
                                if(shoppingCartUI != null) {
                                    shoppingCartUI.dispose();
                                }
                                //Display a message dialog to confirm that the product is added to the cart
                                JOptionPane.showMessageDialog(null, "Product added to cart", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                //Display an error message if the product is out of stock
                                JOptionPane.showMessageDialog(null, "No items available", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } else {
                    //Display an error message if no product is selected
                    JOptionPane.showMessageDialog(null, "No product selected", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Shopping cart button
        shoppingCart = new JButton("Shopping Cart");
        shoppingCart.setBounds(670,20,120,35);
        shoppingCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Open the shopping cart UI window
                shoppingCartUI = new ShoppingCartUI(cart);
            }
        });

        //Add all the components to the frame
        add(selectionLabel);
        add(selectionComboBox);
        add(productSP);
        add(separator);
        add(productDetailsHeading);
        add(belowTablePanel);
        add(addToCart);
        add(shoppingCart);

        //Add all the product detail labels to the panel
        belowTablePanel.add(selectedProductID);
        belowTablePanel.add(selectedProductCategory);
        belowTablePanel.add(selectedProductName);
        belowTablePanel.add(selectedProductSize);
        belowTablePanel.add(selectedProductColor);
        belowTablePanel.add(selectedProductBrandName);
        belowTablePanel.add(selectedProductWarrantyPeriod);
        belowTablePanel.add(selectedProductAvailableItemCount);

        setSize(800,800);
        setLayout(null);
        setVisible(true);
    }

    //Method to initialize the table data according to the selected category
    public void initializeTableData(String[][] rows,WestminsterShoppingManager westminsterShoppingManager, String selection) {
        //Reset the table data
        for (int i = 0; i < rows.length; i++) {
            for(int j = 0; j < rows[i].length; j++)
                rows[i][j] = null;
        }

        Product[] productList = westminsterShoppingManager.reorderProductList(); //get the reordered product list

        int i=0;
        //Fill the table data according to the selected category
        if (selection.equals("All")) {
            for (Product temp : productList) {
                rows[i][0] = temp.getId();
                rows[i][1] = temp.getProductName();
                if(temp instanceof Electronics) { //Check the category of the product and update the category column accordingly
                    rows[i][2] = "Electronics";
                } else {
                    rows[i][2] = "Clothing";
                }
                rows[i][3] = String.format("%.2f", temp.getPrice());
                if(temp instanceof Electronics) { //Check the category of the product and update the info column accordingly
                    rows[i][4] = ((Electronics) temp).getBrandName() + ", " + ((Electronics) temp).getWarrantyPeriod() + " Months Warranty";
                } else {
                    rows[i][4] = ((Clothing) temp).getSize() + ", " + ((Clothing) temp).getColor();
                }
                rows[i][5] = String.valueOf(temp.getAvailableItemCount());
                i++;
            }
        }
        else if(selection.equals("Electronics")){
            for (Product temp : productList) {
                if(temp instanceof Electronics){
                    rows[i][0] = temp.getId();
                    rows[i][1] = temp.getProductName();
                    rows[i][2] = "Electronics";
                    rows[i][3] = String.format("%.2f", temp.getPrice());
                    rows[i][4] = ((Electronics) temp).getBrandName() + ", " + ((Electronics) temp).getWarrantyPeriod() + " Months Warranty";
                    rows[i][5] = String.valueOf(temp.getAvailableItemCount());
                    i++;
                }
            }
        }
        else if(selection.equals("Clothing")){
            for (Product temp : productList) {
                if(temp instanceof Clothing){
                    rows[i][0] = temp.getId();
                    rows[i][1] = temp.getProductName();
                    rows[i][2] = "Clothing";
                    rows[i][3] = String.format("%.2f", temp.getPrice());
                    rows[i][4] = ((Clothing) temp).getSize() + ", " + ((Clothing) temp).getColor();
                    rows[i][5] = String.valueOf(temp.getAvailableItemCount());
                    i++;
                }
            }
        }
    }

}
