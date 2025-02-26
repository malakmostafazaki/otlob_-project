/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

/**
 *
 * @author MalakMostafaZaky
 */
import java.io.Serializable;
import java.util.InputMismatchException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;



public class Order implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
     private int orderId;
    private int productId;
    private int quantity;
    private String status;
    private double totalPrice;
    private String orderDate;
    private String customerName;
    public static HashMap<Integer, Order> orderMap = new HashMap<>();
    public static HashMap<Integer, Order> getOrderMap() {
        return orderMap;
    }
    public Order(){};
    public Order(int orderId, int productId, int quantity,String status, double totalPrice, String orderDate, String customerName) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.status="pending";
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.customerName = customerName;
    }
    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            System.out.println("Quantity cannot be negative.");
            return;
        }
        this.quantity = quantity;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    

    public static ArrayList<Order> readOrdersFromFile() {
        ArrayList<Order> orders = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("orders.dat"))) {
            orders = (ArrayList<Order>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Starting with an empty order list.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return orders;
    }
    public static void writeOrdersToFile(ArrayList<Order> orders) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("orders.dat"))) {
            oos.writeObject(orders);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void saveOrdersToFile() {
        Order.writeOrdersToFile(new ArrayList<>(orderMap.values()));
    }
    public static void loadOrdersFromFile() {
        ArrayList<Order> orders = Order.readOrdersFromFile();
        for (Order order : orders) {
            orderMap.put(order.getOrderId(), order); // Add to the map
        }
    }
    public static void viewAllOrdersDetails() {
          loadOrdersFromFile();
        if (orderMap.isEmpty()) {
            System.out.println("No orders available.");
        } else {
            System.out.println("All Orders:");
            for (Order order : orderMap.values()) {
                System.out.println(order);
            }
        }
    }
    public static double getProductPriceById(int productId) {
        Product product = Product.getProductMap().get(productId);
        if (product != null) {
            return product.getPrice();
        }
        return 0.0;
    }
    
   public static void changeStatusToShipped(int orderId) {
    // Load orders from file to ensure we work with the latest data
    loadOrdersFromFile();
    Order order = orderMap.get(orderId);
    if (order != null) {
        if (order.getStatus().equals("pending")) {
            order.setStatus("shipped");
            System.out.println("Order " + orderId + " status changed to shipped.");
            
            // Save updated orders back to the file
            saveOrdersToFile();
        } else {
            System.out.println("Order " + orderId + " is already " + order.getStatus());
        }
    } else {
        System.out.println("Order with ID " + orderId + " not found.");
    }
}
   public static double calculateRevenueForPeriod(String startDate, String endDate) {
        double totalRevenue = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = sdf.parse(startDate);// Converts the startDate (a String) into a Date object using the specified format.
            Date end = sdf.parse(endDate);

            for (Order order : orderMap.values()) {
                Date orderDate = sdf.parse(order.getOrderDate());
                if ((orderDate.after(start) || orderDate.equals(start)) && (orderDate.before(end) || orderDate.equals(end))) {
                    totalRevenue += order.getTotalPrice();
                }
            }
        } catch (Exception e) {
            System.out.println("Error with date format: " + e.getMessage());
        }
        return totalRevenue;
    }
   public static double calculateTotalRevenue(String startDate, String endDate) {
        return calculateRevenueForPeriod(startDate, endDate);
    }
   public static double calculateAverageRevenue(String startDate, String endDate) {
        double totalRevenue = calculateRevenueForPeriod(startDate, endDate);
        int orderCount = 0;
        for (Order order : orderMap.values()) {
            try {
                Date orderDate = new SimpleDateFormat("yyyy-MM-dd").parse(order.getOrderDate());
                Date start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                Date end = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
                if ((orderDate.after(start) || orderDate.equals(start)) && (orderDate.before(end) || orderDate.equals(end))) {
                    orderCount++;
                }
            } catch (Exception e) {
                System.out.println("Error with date format: " + e.getMessage());
            }
        }
        return orderCount > 0 ? totalRevenue / orderCount : 0;
    }
     

//   public static void placeOrder() {
//    Scanner scanner = new Scanner(System.in);
//
//    try {
//        // Load existing orders into the order map
//        loadOrdersFromFile(); // Ensures we have the current orders from "orders.dat"
//
//        // Generate Order ID
//        int orderId = orderMap.size() + 1;
//
//        // Enter Customer Name
//        System.out.print("Enter your name: ");
//        String customerName = scanner.nextLine();
//
//        // Enter Product Name
//        System.out.print("Enter product name: ");
//        String productName = scanner.nextLine();
//
//        // Read products from products.dat
//        ArrayList<Product> products = Product.readProductsFromFile();
//
//        // Search for the product by name
//        Product selectedProduct = null;
//        for (Product product : products) {
//            if (product.getName().equalsIgnoreCase(productName)) {
//                selectedProduct = product;
//                break;
//            }
//        }
//
//        if (selectedProduct == null) {
//            System.out.println("Product not found. Please check the product name and try again.");
//            return;
//        }
//
//        // Enter Quantity
//        System.out.print("Enter quantity: ");
//        int quantity = scanner.nextInt();
//        if (quantity <= 0 || quantity > selectedProduct.getQuantity()) {
//            System.out.println("Invalid quantity. Please enter a valid quantity.");
//            return;
//        }
//
//        // Update product quantity and sold counter
//        selectedProduct.setQuantity(selectedProduct.getQuantity() - quantity);  // Reduce stock
//        selectedProduct.setSold(selectedProduct.getSold() + quantity);         // Increase sold count
//
//        // Save updated products back to products.dat
//        Product.writeProductsToFile(products);
//
//        // Calculate Total Price
//        double totalPrice = selectedProduct.getPrice() * quantity;
//
//        // Set Order Date
//        String orderDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//
//        // Create New Order
//        Order newOrder = new Order(orderId, selectedProduct.getId(), quantity, "pending", totalPrice, orderDate, customerName);
//
//        // Add the new order to the order map
//        orderMap.put(orderId, newOrder);
//
//        // Save all orders (including new one) to file
//        saveOrdersToFile();
//
//        System.out.println("Order placed successfully!");
//        System.out.println("Order Details: " + newOrder);
//
//    } catch (InputMismatchException e) {
//        System.out.println("Invalid input. Please try again.");
//        scanner.nextLine(); // Clear the scanner buffer
//    } catch (Exception e) {
//        System.out.println("An unexpected error occurred: " + e.getMessage());
//    }
//}
   public static int piecesSoldOverPeriod(String startDate, String endDate) {
    loadOrdersFromFile(); // Ensure orders are loaded
    int totalPieces = 0;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    try {
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);
        
        for (Order order : orderMap.values()) {
            Date orderDate = sdf.parse(order.getOrderDate());
            if ((orderDate.after(start) || orderDate.equals(start)) &&
                (orderDate.before(end) || orderDate.equals(end))) {
                totalPieces += order.getQuantity(); // Accumulate quantity
            }
        }
    } catch (ParseException e) {
        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
    }
    
    return totalPieces;
}
  

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", productId=" + productId + ", quantity=" + quantity + ", status=" + status + ", totalPrice=" + totalPrice + ", orderDate=" + orderDate + ", customerName=" + customerName + '}';
    }
    
}
    

