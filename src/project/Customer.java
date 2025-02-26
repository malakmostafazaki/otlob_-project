/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Customer extends User {

    private static int cartIDCounter = 1; // Static counter for unique cart IDs
    private String customerAddress;
    private int cartID;
    private Cart cart;
    private int numberOfConfirmedOrders = 0;
    private Map<Integer, Cart> confirmedOrders = new HashMap<>(); // Store confirmed carts with unique IDs
    private String name;

    // Constructor with parameters
    public Customer(int id, String name, String username, String email, String password, String address) throws FileNotFoundException {
        super(id, name, username, email, password, address);
        this.cartID = cartIDCounter++;
        this.cart= new Cart();
    }

    public void setCustomerName(String s) {
        this.name = s;
    }

    public void setCustomerAddress(String s) {
        this.customerAddress = s;
    }

    public String getCustomerName() {
        return name;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    // Default Constructor
    public Customer()  {
        super(0, "", "", "", "", "");
        this.cartID = cartIDCounter++;
        this.cart = new Cart();
    }

    // Methods for Cart Management
    public void createCart() throws FileNotFoundException {
        cart = new Cart();
        this.cartID = cartIDCounter++;
    }

    public void addToCart(String product, int quantity) throws IOException {
        cart.addToCart(product, quantity);
    }

    public void removeFromCart(String product, int quantity) {
        cart.removeFromCart(product);
    }

    public void clearALL() {
        cart.clearCart();
    }

    public String viewPayment() throws FileNotFoundException {
        double payment = cart.getTotalPrice();
        return "Total payment for the cart: " + payment;
    }

    public void confirmCart() throws FileNotFoundException {
        numberOfConfirmedOrders++;
        confirmedOrders.put(cartID, cart);
        saveConfirmedOrder(cartID, cart);
        createCart();
    }

    public String viewPastOrders() {
        StringBuilder pastOrders = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream("ConfirmedOrders.dat");
             DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {

            while (dataInputStream.available() > 0) {
                int orderId = dataInputStream.readInt();
                int cartDataLength = dataInputStream.readInt();
                byte[] cartDataBytes = new byte[cartDataLength];
                dataInputStream.read(cartDataBytes);
                String cartData = new String(cartDataBytes);

                pastOrders.append("Order ID: ").append(orderId)
                          .append("\nCart Details: ").append(cartData).append("\n\n");
            }
        } catch (IOException e) {
            return "Error reading past orders: " + e.getMessage();
        }
        return pastOrders.toString();
    }

    private void saveConfirmedOrder(int cartID, Cart cart) {
        try (FileOutputStream fileOutputStream = new FileOutputStream("ConfirmedOrders.dat", true);
             DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {

            String cartData = cart.toString(); // Assuming Cart has a meaningful toString method
            byte[] cartDataBytes = cartData.getBytes();

            dataOutputStream.writeInt(cartID);
            dataOutputStream.writeInt(cartDataBytes.length);
            dataOutputStream.write(cartDataBytes);

        } catch (IOException e) {
            System.out.println("Error saving order: " + e.getMessage());
        }
    }

    @Override
    public String getUserType() {
        return "Customer";
    }
  
    

 public String viewCart() {
        return cart.products.toString(); // Assuming Cart has a meaningful toString implementation
    }

    // Clear current cart contents
    public void clearCart() {
        cart.clearCart(); // This calls the Cart class's clearCart method
    }
    
   @Override
    public void showMenu() {
      
    Scanner scanner = new Scanner(System.in);
    while (true) {
        System.out.println("Select an operation:");
        System.out.println("1. Create cart");
        System.out.println("2. Add to cart");
        System.out.println("3. Remove from cart");
        System.out.println("4. Confirm cart");
        System.out.println("5. View payment");
        System.out.println("6. View cart");
        System.out.println("7. Clear cart");
        System.out.println("8. View past orders");
        System.out.println("9. Exit");

        int Choice = scanner.nextInt();

        try {
            if (Choice == 1) {
                createCart();
                System.out.println("Cart created successfully.");
            } else if (Choice == 2) {
                System.out.print("Enter product name: ");
                String productName = scanner.next();
                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();
                addToCart(productName, quantity);
                System.out.println("Product added to cart.");
            } else if (Choice == 3) {
                System.out.print("Enter product name: ");
                String productName = scanner.next();
                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();
                removeFromCart(productName, quantity);
                System.out.println("Product removed from cart.");
            } else if (Choice == 4) {
                confirmCart();
                System.out.println("Cart confirmed.");
            } else if (Choice == 5) {
                System.out.println(viewPayment());
            } else if (Choice == 6) {
                System.out.println(viewCart());
            } else if (Choice == 7) {
                clearCart();
                System.out.println("Cart cleared.");
            } else if (Choice == 8) {
                System.out.println(viewPastOrders());
            } else if (Choice == 9) {
                System.out.println("Exiting menu.");
                return;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
    
   
    @Override
    public boolean login(String username, String password) {
      List<String> customerData = readFile(CUSTOMER_FILE);

        // Check if the provided username and password match any record
        for (String entry : customerData) {
            String[] userDetails = entry.replace("[", "").replace("]", "").split(",");
            String storedUsername = userDetails[1].trim(); // Assuming index 1 is the username
            String storedPassword = userDetails[2].trim(); // Assuming index 2 is the password

            if (storedUsername.equals(username) && storedPassword.equals(password)) {
                System.out.println("Login successful for Customer: " + username);
                return true;
            }
        }

        System.out.println("Invalid username or password. Please try again.");
        return false;    }
    }

 private static List<String> readFile(File file) {
    List<String> fileContent = new ArrayList<>();
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
        fileContent = (List<String>) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error reading file: " + e.getMessage());
    }
    return fileContent;
}
      private static final File CUSTOMER_FILE = new File("CustomerData.dat");


   
    
   


  

    


public class Customer {
    
}
