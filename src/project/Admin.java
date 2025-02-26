/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

/**
 *
 * @author MalakMostafaZaky
 */
import java.io.*;
import java.util.*;
public class Admin extends User {
    
    private static final File ADMIN_FILE = new File("AdminData.dat");
    private static final File SELLER_FILE = new File("sellerData.dat");
    private static final File CUSTOMER_FILE = new File("CustomerData.dat");

    public static HashMap<ArrayList<String>, String> Map = new HashMap<>();
    public Admin(String id1, String name1, String email1) {
        super(0, "", "", "", "", "");
    }

    public Admin(int id, String name, String username, String email, String password, String Address) {
        super(id, name, username, email, password, Address);
    }
    
     private static List<String> readFile(File file) {
        List<String> fileContent = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            fileContent = (List<String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Handle file not found case by returning an empty list
        }
        return fileContent;
    }

    private static void writeFile(File file, List<String> fileContent) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(fileContent);
            System.out.println("File updated successfully: " + file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Add(String email, String username, String password, String address, String userType) {
        ArrayList<String> userDetails = new ArrayList<>();
        userDetails.add(email);
        userDetails.add(username);
        userDetails.add(password);
        userDetails.add(address);

        File targetFile;
        if ("Seller".equals(userType)) {
            targetFile = SELLER_FILE;
        } else if ("Customer".equals(userType)) {
            targetFile = CUSTOMER_FILE;
        } else if ("Admin".equals(userType)) {
            targetFile = ADMIN_FILE;
        } else {
            System.out.println("Invalid user type.");
            return;
        }

        // Read the current file content
        List<String> fileContent = readFile(targetFile);

        // Add new user details if they don't already exist
        String entry = userDetails.toString();
        if (!fileContent.contains(entry)) {
            fileContent.add(entry);
            writeFile(targetFile, fileContent);
        } else {
            System.out.println("User already exists in " + targetFile.getName());
        }
    }

    public static void Remove(String emailToRemove, String userType) {
        File targetFile;
        if ("Seller".equals(userType)) {
            targetFile = SELLER_FILE;
        } else if ("Customer".equals(userType)) {
            targetFile = CUSTOMER_FILE;
        } else if ("Admin".equals(userType)) {
            targetFile = ADMIN_FILE;
        } else {
            System.out.println("Invalid user type.");
            return;
        }

        List<String> fileContent = readFile(targetFile);
        boolean found = false;

        Iterator<String> iterator = fileContent.iterator();
        while (iterator.hasNext()) {
            String entry = iterator.next();
            String[] userDetails = entry.replace("[", "").replace("]", "").split(",");
            String storedEmail = userDetails[0].trim();

            if (storedEmail.equals(emailToRemove)) {
                found = true;
                iterator.remove();
                System.out.println("User removed: " + storedEmail);
            }
        }

        if (found) {
            writeFile(targetFile, fileContent);
        } else {
            System.out.println("No user found with the given email in " + targetFile.getName());
        }
    }

    public static String Search(String emailToSearch, String userType) {
        File targetFile;
        if ("Seller".equals(userType)) {
            targetFile = SELLER_FILE;
        } else if ("Customer".equals(userType)) {
            targetFile = CUSTOMER_FILE;
        } else if ("Admin".equals(userType)) {
            targetFile = ADMIN_FILE;
        } else {
            return "Invalid user type.";
        }

        List<String> fileContent = readFile(targetFile);

        for (String entry : fileContent) {
            String[] userDetails = entry.replace("[", "").replace("]", "").split(",");
            String storedEmail = userDetails[0].trim();

            if (storedEmail.equals(emailToSearch)) {
                return "User found in " + targetFile.getName() + ": " + entry;
            }
        }

        return "No user found with the given email in " + targetFile.getName();
    }

public static boolean Edit(String emailToEdit, String newUsername, String newPassword, String newAddress) {
    Scanner scan = new Scanner(System.in);
    boolean found = false;

    // Array of files to check
    File[] files = {ADMIN_FILE, SELLER_FILE, CUSTOMER_FILE};

    for (File file : files) {
        List<String> fileContent = readFile(file);

        for (int i = 0; i < fileContent.size(); i++) {
            String line = fileContent.get(i);
            String[] parts = line.split(":");
            String[] userDetails = parts[0].replace("[", "").replace("]", "").split(",");

            String storedEmail = userDetails[0].trim();

            if (storedEmail.equals(emailToEdit)) {
                found = true;
                System.out.println("User found in " + file.getName() + ": " + String.join(", ", userDetails));
                System.out.println("Updating user details...");

                // Update the user details
                ArrayList<String> updatedDetails = new ArrayList<>();
                updatedDetails.add(storedEmail); // Keep the email the same
                updatedDetails.add(newUsername);
                updatedDetails.add(newPassword);
                updatedDetails.add(newAddress);

                String updatedLine = updatedDetails.toString() + ":" + parts[1];
                fileContent.set(i, updatedLine);

                // Save changes back to the file
                writeFile(file, fileContent);
                System.out.println("User details updated successfully in " + file.getName() + ".");
                break;
            }
        }

        if (found) {
            break; // Stop searching once the user is found and updated
        }
    }

    if (!found) {
        System.out.println("No user found with the given email in any file.");
    }

    return found;
}

    
    public void getTopCustomerOrSeller() {
       
        Order.loadOrdersFromFile();

        HashMap<String, Integer> customerOrderCount = new HashMap<>();
        HashMap<Integer, Integer> sellerOrderCount = new HashMap<>();

        for (Order order : Order.getOrderMap().values()) {
            
            String customerName = order.getCustomerName();
            customerOrderCount.put(customerName, customerOrderCount.getOrDefault(customerName, 0) + 1);

            int productId = order.getProductId();
            Product product = Product.getProductMap().get(productId);
            if (product != null) {
                int sellerId = product.getId(); 
                sellerOrderCount.put(sellerId, sellerOrderCount.getOrDefault(sellerId, 0) + 1);
            }
        }

        String topCustomer = null;
        int maxCustomerOrders = 0;
        for (Map.Entry<String, Integer> entry : customerOrderCount.entrySet()) {
            if (entry.getValue() > maxCustomerOrders) {
                maxCustomerOrders = entry.getValue();
                topCustomer = entry.getKey();
            }
        }

        int topSellerId = -1;
        int maxSellerOrders = 0;
        for (Map.Entry<Integer, Integer> entry : sellerOrderCount.entrySet()) {
            if (entry.getValue() > maxSellerOrders) {
                maxSellerOrders = entry.getValue();
                topSellerId = entry.getKey();
            }
        }

        if (topCustomer != null) {
            System.out.println("Customer with the most orders: " + topCustomer + " (" + maxCustomerOrders + " orders)");
        } else {
            System.out.println("No customers have placed orders.");
        }

        if (topSellerId != -1) {
            System.out.println("Seller with the most orders: Seller ID " + topSellerId + " (" + maxSellerOrders + " orders)");
        } else {
            System.out.println("No sellers have orders associated.");
        }
    }
   
   public void getUserWithGreatestRevenue() {
        double maxRevenue = 0;
        String topUser = null;

        Order.loadOrdersFromFile();

       
        HashMap<Integer, Double> sellerRevenue = new HashMap<>();
        for (Order order : Order.getOrderMap().values()) {
            double orderRevenue = order.getTotalPrice();
            int sellerId = order.getProductId();  

            sellerRevenue.put(sellerId, sellerRevenue.getOrDefault(sellerId, 0.0) + orderRevenue);
        }

        HashMap<String, Double> customerSpending = new HashMap<>();
        for (Order order : Order.getOrderMap().values()) {
            double orderRevenue = order.getTotalPrice();
            String customerName = order.getCustomerName();

            customerSpending.put(customerName, customerSpending.getOrDefault(customerName, 0.0) + orderRevenue);
        }

       
        int topSellerId = -1;
        for (Integer sellerId : sellerRevenue.keySet()) {
            double revenue = sellerRevenue.get(sellerId);
            if (revenue > maxRevenue) {
                maxRevenue = revenue;
                topSellerId = sellerId;
                topUser = "Seller with ID: " + sellerId;
            }
        }

       
        String topCustomerName = null;
        for (String customerName : customerSpending.keySet()) {
            double spending = customerSpending.get(customerName);
            if (spending > maxRevenue) {
                maxRevenue = spending;
                topCustomerName = customerName;
                topUser = "Customer: " + customerName;
            }
        }

        if (topUser != null) {
            System.out.println("User with greatest revenue: " + topUser + " with revenue: $" + maxRevenue);
        } else {
            System.out.println("No orders found to calculate revenue.");
        }
   }
   
   public void getOrdersPerCustomer() {
        Order.loadOrdersFromFile();
        Map<String, List<Order>> customerOrders = new HashMap<>();

        for (Order order : Order.getOrderMap().values()) {
            customerOrders.computeIfAbsent(order.getCustomerName(), k -> new ArrayList<>()).add(order);
        }

        if (customerOrders.isEmpty()) {
            System.out.println("No customer orders found.");
        } else {
            for (Map.Entry<String, List<Order>> entry : customerOrders.entrySet()) {
                System.out.println("Customer: " + entry.getKey());
                System.out.println("Number of Orders: " + entry.getValue().size());
                for (Order order : entry.getValue()) {
                    System.out.println(order);
                }
                System.out.println();
            }
        }
    }

    
    public void getOrdersPerSeller() {
        Order.loadOrdersFromFile();
        Map<Integer, List<Order>> sellerOrders = new HashMap<>();

        for (Order order : Order.getOrderMap().values()) {
            int sellerId = order.getProductId();
            sellerOrders.computeIfAbsent(sellerId, k -> new ArrayList<>()).add(order);
        }

        if (sellerOrders.isEmpty()) {
            System.out.println("No seller orders found.");
        } else {
            for (Map.Entry<Integer, List<Order>> entry : sellerOrders.entrySet()) {
                System.out.println("Seller ID: " + entry.getKey());
                System.out.println("Number of Orders: " + entry.getValue().size());
                for (Order order : entry.getValue()) {
                    System.out.println(order);
                }
                System.out.println();
            }
        }
    }

    // View details of a specific order by ID
    public void viewAllOrdersDetails() {
        Order.loadOrdersFromFile();  // Ensure orders are loaded from file

        if (Order.getOrderMap().isEmpty()) {
            System.out.println("No orders available.");
        } else {
            System.out.println("All Orders:");
            for (Order order : Order.getOrderMap().values()) {
                System.out.println(order);  // This will call the toString() method of the Order class
            }
        }
    }


public static void SaveToFile() {
    // Separate maps for each user type
    HashMap<ArrayList<String>, String> adminEntries = new HashMap<>();
    HashMap<ArrayList<String>, String> sellerEntries = new HashMap<>();
    HashMap<ArrayList<String>, String> customerEntries = new HashMap<>();

    // Categorize entries by user type
    for (Map.Entry<ArrayList<String>, String> entry : Map.entrySet()) {
        String userType = entry.getValue();
        switch (userType) {
            case "Admin":
                adminEntries.put(entry.getKey(), userType);
                break;
            case "Seller":
                sellerEntries.put(entry.getKey(), userType);
                break;
            case "Customer":
                customerEntries.put(entry.getKey(), userType);
                break;
            default:
                System.out.println("Unknown user type: " + userType);
                break;
        }
    }

    // Write to respective files
    saveEntriesToFile(ADMIN_FILE, adminEntries);
    saveEntriesToFile(SELLER_FILE, sellerEntries);
    saveEntriesToFile(CUSTOMER_FILE, customerEntries);
}

// Helper method to save entries to a file
private static void saveEntriesToFile(File file, HashMap<ArrayList<String>, String> entries) {
    List<String> fileContent = readFile(file);

    for (Map.Entry<ArrayList<String>, String> entry : entries.entrySet()) {
        String entryLine = entry.getKey().toString() + ":" + entry.getValue();
        if (!fileContent.contains(entryLine)) {
            fileContent.add(entryLine);
        }
    }

    writeFile(file, fileContent);
}


   @Override
public String getUserType() {
    // Placeholder or default return value
    return "Admin";  // You can return something meaningful here.
}

    @Override
    public void showMenu() {
       
    Scanner in = new Scanner(System.in);
    int choice;
    do {
        System.out.println("\n===== Admin Menu =====");
        System.out.println("1. Add ");
        System.out.println("2. Edit ");
        System.out.println("3. Remove ");
        System.out.println("4. Search");
        System.out.println("5. Display Order details ");
        System.out.println("6. User with most revenue");
        System.out.println("7. Customer with most orders");
        System.out.println("8. Seller with most orders");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
        choice = in.nextInt();
        in.nextLine(); // Consume the newline character
        if (choice == 1) {
//           Add();
        } else if (choice == 2) {
//          Edit();
        } else if (choice == 3) {
//           Remove();
        } else if (choice == 4) {
//          Search();
        } else if (choice == 5) {
          viewAllOrdersDetails();
        } else if (choice == 6) {
          getUserWithGreatestRevenue();
        } else if (choice == 7) {
         getOrdersPerCustomer();
        } else if (choice == 8) {
           getOrdersPerSeller();
        }
          else if (choice == 9) {
            System.out.println("Exiting...");
        }
        
        else {
            System.out.println("Invalid choice. Please try again.");
        }
    } while (choice != 9);
}

    @Override
    public boolean login(String username, String password) {
          // Read the admin file content
    List<String> adminData = readFile(ADMIN_FILE);

    // Check if the provided username and password match any record
    for (String entry : adminData) {
        String[] userDetails = entry.replace("[", "").replace("]", "").split(",");
        String storedUsername = userDetails[1].trim();
        String storedPassword = userDetails[2].trim();

        if (storedUsername.equals(username) && storedPassword.equals(password)) {
            System.out.println("Login successful for Admin: " + username);
            return true;
        }
    }

    System.out.println("Invalid username or password. Please try again.");
    return false;
}


    }
    
