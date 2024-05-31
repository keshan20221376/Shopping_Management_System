import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager{
    //ArrayLists to store users and products
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<Product> productList = new ArrayList<>();

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void removeProduct(Product product) {
        productList.remove(product);
    }

    //Adds new product to the system
    public void addNewProduct(){
        Scanner input = new Scanner(System.in);
        System.out.println("------------------------------------------------------------------");
        System.out.println("--------------------ADD NEW PRODUCT TO STORE----------------------");
        System.out.println("------------------------------------------------------------------");

        if(productList.size() <= 50) { //Checks whether the product list is full
            //Handle invalid input types
            try {
                System.out.print("Enter product ID : ");
                String id = input.nextLine();
                System.out.print("Enter product name : ");
                String productName = input.nextLine();
                System.out.print("Enter stock count : ");
                int availableItemCount = input.nextInt();
                if (availableItemCount < 0) {
                    System.out.println("\u001B[31m------------------------------------------------------------------");
                    System.out.println("------------Stock count cannot be negative. Please try again!------");
                    System.out.println("------------------------------------------------------------------\u001B[0m");
                    return; // Exit the method or handle accordingly
                }
                System.out.print("Enter price of a piece : £");
                double price = input.nextDouble();
                if (price < 0) {
                    System.out.println("\u001B[31m------------------------------------------------------------------");
                    System.out.println("-------------Price cannot be negative. Please try again!----------");
                    System.out.println("------------------------------------------------------------------\u001B[0m");
                    return; // Exit the method or handle accordingly
                }
                input.nextLine();
                System.out.print("Enter product type (E for electronics, C for clothing) : ");
                String productType = input.nextLine().toUpperCase();

                //Get required details according to the product type
                if (productType.equals("E")) {
                    System.out.print("Enter brand name : ");
                    String brandName = input.nextLine();
                    System.out.print("Enter warranty period (Months) : ");
                    int warrantyPeriod = input.nextInt();
                    addProduct(new Electronics(id, productName, availableItemCount, price, brandName, warrantyPeriod));
                    System.out.println("\u001B[32m------------------------------------------------------------------");
                    System.out.println("-------------------Product added successfully!--------------------");
                    System.out.println("------------------------------------------------------------------\u001B[0m");
                } else if (productType.equals("C")) {
                    System.out.print("Enter color : ");
                    String color = input.next();
                    System.out.print("Enter size : ");
                    String size = input.next();
                    addProduct(new Clothing(id, productName, availableItemCount, price, color, size));
                    System.out.println("\u001B[32m------------------------------------------------------------------");
                    System.out.println("-------------------Product added successfully!--------------------");
                    System.out.println("------------------------------------------------------------------\u001B[0m");
                } else {
                    System.out.println("\u001B[31m------------------------------------------------------------------");
                    System.out.println("-------------Invalid product type. Please try again!--------------");
                    System.out.println("------------------------------------------------------------------\u001B[0m");
                }
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31m------------------------------------------------------------------");
                System.out.println("-------------Invalid input. Please try again!---------------------");
                System.out.println("------------------------------------------------------------------\u001B[0m");
            }
        } else {
            System.out.println("\u001B[31m------------------------------------------------------------------");
            System.out.println("------------------Product list is full!---------------------------");
            System.out.println("------------------------------------------------------------------\u001B[0m");
        }
    }

    //Removes a product from the system
    public void deleteProduct(){
        Scanner input = new Scanner(System.in);
        System.out.println("------------------------------------------------------------------");
        System.out.println("--------------------REMOVE PRODUCT FROM STORE---------------------");
        System.out.println("------------------------------------------------------------------");
        System.out.print("Enter product ID : ");
        String id = input.next();
        boolean isFound = false;
        //Check for the product in the product list
        for(Product product : productList){
            if(product.getId().equals(id)){
                removeProduct(product);
                product.printProductDetails();
                isFound = true;
                break;
            }
        }
        if(!isFound){
            System.out.println("\u001B[31m------------------------------------------------------------------");
            System.out.println("-------------------Product not found!------------------------------");
            System.out.println("------------------------------------------------------------------\u001B[0m");
        } else {
            System.out.println("\u001B[32m------------------------------------------------------------------");
            System.out.println("-------------------Product removed successfully!------------------\u001B[0m");
            System.out.println("\u001B[32m  ----------Number of products left in the system : "+ productList.size() +"-----------\u001B[0m");
            System.out.println("\u001B[32m------------------------------------------------------------------\u001B[0m");
        }
    }

    //Displays the product list in alphabetical order
    public void displayProductList(){
        System.out.println("------------------------------------------------------------------");
        System.out.println("--------------------PRODUCT LIST OF STORE-------------------------");
        System.out.println("------------------------------------------------------------------");
        if(productList.isEmpty()){
            System.out.println("\u001B[31m------------------------------------------------------------------");
            System.out.println("-------------------Product list is empty!-------------------------");
            System.out.println("------------------------------------------------------------------\u001B[0m");
        } else {
            // Makes a copy of the arrayList to re-arrange the elements
            Product[] productsRef = new Product[productList.size()];
            for (int i = 0; i < productsRef.length; i++) {
                productsRef[i] = productList.get(i);
            }

            // Elements are re-arranged in alphabetical order using bubble sort algorithm.
            for (int i = 0; i < productsRef.length - 1; i++) {
                for (int j = 0; j <= productsRef.length - 2; j++) {
                    if (productsRef[j].getProductName().toLowerCase().compareTo(productsRef[j + 1].getProductName().toLowerCase()) > 0) {
                        Product temp = productsRef[j];
                        productsRef[j] = productsRef[j + 1];
                        productsRef[j + 1] = temp;
                    }
                }
            }
            //Print the re-rearranged product list
            for (Product temp : productsRef) {
                temp.printProductDetails();
            }
        }
    }

    //Returns the product list in alphabetical order
    public Product[] reorderProductList(){
        // Makes a copy of the arrayList to re-arrange the elements
        Product[] productsRef = new Product[productList.size()];
        for (int i = 0; i < productsRef.length; i++) {
            productsRef[i] = productList.get(i);
        }

        // Elements are re-arranged in alphabetical order using bubble sort algorithm.
        for (int i = 0; i < productsRef.length - 1; i++) {
            for (int j = 0; j <= productsRef.length - 2; j++) {
                if (productsRef[j].getProductName().toLowerCase().compareTo(productsRef[j + 1].getProductName().toLowerCase()) > 0) {
                    Product temp = productsRef[j];
                    productsRef[j] = productsRef[j + 1];
                    productsRef[j + 1] = temp;
                }
            }
        }
        return productsRef;
    }

    //Save data to a file
    public void saveData(){
        //Saves data to a file (Users and products lists)
        try {
            File file = new File("WestminsterShoppingManager.txt");
            file.createNewFile(); // creates a new file when cannot find the file mentioned above

            //Output Streams
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

            objOut.writeObject(productList); //save drivers array list to the file
            objOut.writeObject(userList);  //save users array list to the file

            objOut.close();
            fileOut.close();

            System.out.println("\u001B[32m------------------------------------------------------------------");
            System.out.println("---------------------Data saved successfully!---------------------");
            System.out.println("------------------------------------------------------------------\u001B[0m");
        }catch (IOException e){
            System.out.println("\u001B[31m------------------------------------------------------------------");
            System.out.println("----------------Error occurred when saving data-------------------");
            System.out.println("------------------------------------------------------------------\u001B[0m");
        }
    }

    //Load data from a file
    public void readData() {
        //Load data from a file (Users and products lists)

        //Input streams
        try {
            FileInputStream fileIn = new FileInputStream("WestminsterShoppingManager.txt");
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            productList = (ArrayList<Product>) objIn.readObject(); //Load product list from save file
            userList = (ArrayList<User>) objIn.readObject(); //Load user list from save file
            //input close
            objIn.close();
            fileIn.close();

            System.out.println("\u001B[32m------------------------------------------------------------------");
            System.out.println("---------------------Data loaded successfully!--------------------");
            System.out.println("------------------------------------------------------------------\u001B[0m");
        }catch (IOException|ClassNotFoundException e) {
            System.out.println("\u001B[31m------------------------------------------------------------------");
            System.out.println("----------------Error occurred when loading data------------------");
            System.out.println("------------------------------------------------------------------\u001B[0m");
        }
    }

    //Register a new user
    public void registerUser(){
        Scanner input = new Scanner(System.in);
        System.out.println("------------------------------------------------------------------");
        System.out.println("--------------------REGISTER AS A NEW USER------------------------");
        System.out.println("------------------------------------------------------------------");
        System.out.print("Enter your username : ");
        String userName = input.nextLine();
        System.out.print("Enter your password : ");
        String password = input.nextLine();
        addUser(new User(userName, password));
        System.out.println("\u001B[32m------------------------------------------------------------------");
        System.out.println("-------------------User added successfully!----------------------");
        System.out.println("------------------------------------------------------------------\u001B[0m");
    }

    //Check whether the user can be found in the user list using username and password
    public User loginUser(){
        System.out.println("------------------------------------------------------------------");
        System.out.println("------------------------LOG IN WINDOW-----------------------------");
        System.out.println("------------------------------------------------------------------");
        Scanner input = new Scanner(System.in);
        System.out.print("Please Enter your username : ");
        String username = input.nextLine();
        System.out.print("Please Enter your password : ");
        String password = input.nextLine();
        for (User user : userList) {
            System.out.println(user.getUserName() + " " + user.getPassword());
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                System.out.println("hello");
                return user;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.readData(); //Load data from save file
        boolean isQuit = false;
        while (!isQuit) {
            //Console menu of the application
            System.out.println("\u001B[33m------------------------------------------------------------------");
            System.out.println("--------------WELCOME TO WESTMINSTER SHOPPING MANAGER-------------");
            System.out.println("------------------------------------------------------------------\u001B[0m");
            System.out.println("\tPress N to add a new product\n" +
                    "\tPress R to remove a product\n" +
                    "\tPress L to display list of products\n" +
                    "\tPress S to save data\n" +
                    "\tPress G to open GUI of westminster shopping manager\n" +
                    "\tPress Q to quit westminster shopping manager");
            System.out.println("------------------------------------------------------------------");
            System.out.print("Please enter your selection : ");
            String selection = input.nextLine();
            //Run the system functions according to user selection
            switch (selection) {
                case "Q":
                    isQuit = true;
                    System.out.println("\u001B[31m------------------------------------------------------------------");
                    System.out.println("---------------------------EXITING--------------------------------");
                    System.out.println("------------------------------------------------------------------\u001B[0m");
                    System.exit(0); //Quit the system forcibly to close the gui if running
                    break;
                case "N":
                    shoppingManager.addNewProduct();
                    break;
                case "R":
                    shoppingManager.deleteProduct();
                    break;
                case "L":
                    shoppingManager.displayProductList();
                    break;
                case "S":
                    shoppingManager.saveData();
                    break;
                case "G":
                    //Ask the user to login or register
                    System.out.println("------------------------------------------------------------------");
                    System.out.print("Press L to login as an existing user or Press R to register as a new user : ");
                    String loginSelection = input.nextLine();
                    if (loginSelection.equalsIgnoreCase("L")){
                        User currentUser = shoppingManager.loginUser();
                        if (currentUser != null) {
                            System.out.println("\u001B[32m------------------------------------------------------------------");
                            System.out.println("-------------------Login successful!------------------------------");
                            System.out.println("------------------------------------------------------------------\u001B[0m");
                            new UserInterface(shoppingManager, currentUser); //Open the GUI
                        } else {
                            System.out.println("\u001B[31m------------------------------------------------------------------");
                            System.out.println("-------------------Invalid username or password-------------------");
                            System.out.println("------------------------------------------------------------------\u001B[0m");
                            System.out.println("Want to register as a new user? (Y/N)");
                            String choice = input.nextLine();
                            if(choice.equalsIgnoreCase("Y")){
                                shoppingManager.registerUser();
                            }
                        }
                    } else if (loginSelection.equalsIgnoreCase("R")){
                        shoppingManager.registerUser();
                    } else {
                        System.out.println("\u001B[31m------------------------------------------------------------------");
                        System.out.println("-------------------Invalid input. Please try again---------------");
                        System.out.println("------------------------------------------------------------------\u001B[0m");
                    }
                    break;
                default:
                    System.out.println("\u001B[31m------------------------------------------------------------------");
                    System.out.println("-----------------Invalid input. Please try again------------------");
                    System.out.println("------------------------------------------------------------------\u001B[0m");
            }
        }
    }
}
