/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;


import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.io.*;
import java.util.Scanner;

public class Seller extends User{
    
public static final File SELLER_FILE = new File("sellers.dat");
private String typeOfProductSold;

public static HashMap<Integer, Product> productMap;
public static HashMap<Integer, Integer> productOrderCount = new HashMap<>();
public static ArrayList<Seller> sellerList = new ArrayList<>(); 



public Seller(){
   super(0, "", "", "", "","");};


private void initializeProductMap() { // 
        ArrayList<Product> products = Product.readProductsFromFile();//Reading a list of Product objects from a file 
        for (Product product : products) {
            productMap.put(product.getId(), product); // id key  w el product value 
        }
    }



public Seller (int id, String name, String username, String email, String password, String typeOfProductSold,String address) {

        super(id, name, username, email, password,address);
        this.typeOfProductSold = typeOfProductSold;
        initializeProductMap();

    }

public String getTypeOfProductSold() {
        return typeOfProductSold;
    }
public void setTypeOfProductSold(String typeOfProductSold) {
        this.typeOfProductSold = typeOfProductSold;
    }
//public static  void TrackOrderOfSellerr(int sellerid){
//       
//    for (Order ord : Order.getOrderMap().values()) { 
//        if (ord.getOrderId() == sellerid) { 
//            productOrderCount.put(ord.getProductId(), productOrderCount.getOrDefault(ord.getProductId(), 0) + ord.getQuantity());
//        }
//    }
//
//    if (productOrderCount.isEmpty()) {
//        System.out.println("No orders found for this seller.");
//    } else {
//        System.out.println("Orders made by seller ID " + sellerid + ":");
//        for (Integer productId : productOrderCount.keySet()) {
//            System.out.println("Product ID: " + productId + ", Total Orders: " + productOrderCount.get(productId));
//        }
//    }
//}
 
@Override
    public void showMenu() {
    Scanner in = new Scanner(System.in);
    int choice;
    do {
        System.out.println("\n===== Seller Menu =====");
        System.out.println("1. Add Product");
        System.out.println("2. Edit Product");
        System.out.println("3. Remove Product");
        System.out.println("4. Search Products");
        System.out.println("5. Display Products");
        System.out.println("6. Best Selling Product");
        System.out.println("7. Total Revenue");
        System.out.println("8. Average Revenue");
        System.out.println("9. View All Orders");
        System.out.println("10. Change Order Status");
        System.out.println("11. Pieces Sold Over Specific time");
        System.out.println("12.most revenue product over Specific time ");
        System.out.println("13. Exit");
        System.out.print("Enter your choice: ");
        choice = in.nextInt();
        in.nextLine(); // Consume the newline character
        if (choice == 1) {
            Product.addProduct();
        } else if (choice == 2) {
            Product.editProduct();
        } else if (choice == 3) {
            Product.removeProduct();
        } else if (choice == 4) {
            System.out.println("Enter the field to search by (id, name, description, price, quantity, sold, revenue):");
            String field = in.nextLine();
            System.out.println("Enter the value to search for:");
            String value = in.nextLine();
            Product.searchProducts(field, value);
        } else if (choice == 5) {
            Product.displayProducts();
        } else if (choice == 6) {
        Product bestSellingProduct = Product.getBestSellingProduct();
                if (bestSellingProduct != null) {
                    System.out.println("Best Selling Product: " + bestSellingProduct); 
                } else {
                    System.out.println("No products available.");
                }

        } else if (choice == 7) {
            System.out.print("Enter the start date (yyyy-MM-dd): ");
            String startDate = in.nextLine();
            System.out.print("Enter the end date (yyyy-MM-dd): ");
            String endDate = in.nextLine();
            double totalRevenue = Order.calculateTotalRevenue(startDate, endDate);
            System.out.println("Total Revenue: " + totalRevenue);
        } else if (choice == 8) {
            System.out.print("Enter the start date (yyyy-MM-dd): ");
            String startDate = in.nextLine();
            System.out.print("Enter the end date (yyyy-MM-dd): ");
            String endDate = in.nextLine();
            double averageRevenue = Order.calculateAverageRevenue(startDate, endDate);
            System.out.println("Average Revenue: " + averageRevenue);
        } else if (choice == 9) {
            Order.viewAllOrdersDetails();
        } else if (choice == 10) {
            System.out.print("Enter Order ID to change status: ");
            int orderId = in.nextInt();
            Order.changeStatusToShipped(orderId);
        } 
        else if (choice == 11) {
            System.out.print("Enter the start date (yyyy-MM-dd): ");
            String startDate = in.nextLine();
            System.out.print("Enter the end date (yyyy-MM-dd): ");
            String endDate = in.nextLine();
            int totalPiecesSold = Order.piecesSoldOverPeriod(startDate, endDate);
            System.out.println("Total pieces sold: " + totalPiecesSold);
       } 
        else if (choice == 12) {
            System.out.print("Enter the start date (yyyy-MM-dd): ");
            String startDate = in.nextLine();
            System.out.print("Enter the end date (yyyy-MM-dd): ");
            String endDate = in.nextLine();
            Product highestRevenueProduct = Product.mostrevenueproduct(startDate, endDate);
            if (highestRevenueProduct != null) {
                System.out.println("Product with the highest revenue in the period: " + highestRevenueProduct);
            } else {
                System.out.println("No products found with revenue in the specified time period.");
            }
        } else if (choice == 13) {
            System.out.println("Exiting...");
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    } while (choice != 13);
}
    @Override
    public String getUserType() {
        return "Seller";
    }

    @Override
    public String toString() {
        return super.toString() + ", Type of Product Sold: " + typeOfProductSold;
    }

    public boolean login(String usernameInput, String passwordInput) {
        // Load sellers from the SELLER_FILE
        List<String> fileContent = readFile(SELLER_FILE); // efta7 el file w shofo lw l2eet fi space e3mlo split 

        for (String entry : fileContent) {
            // Parse each entry to extract username and password
            String[] userDetails = entry.replace("[", "").replace("]", "").split(","); 
            String storedUsername = userDetails[1].trim(); // Assuming username is at index 1
            String storedPassword = userDetails[2].trim(); // Assuming password is at index 2 // trim (emsah el space ely fi el genab w sebli ely fi el nos )

            if (storedUsername.equals(usernameInput) && storedPassword.equals(passwordInput)) {
                return true; // Login successful
            }
        }

        return false; // Login failed
    }

    private static List<String> readFile(File file) {
        List<String> fileContent = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            fileContent = (List<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file.getName());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return fileContent;
    }

    }