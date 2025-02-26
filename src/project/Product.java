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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;

    public class Product implements Serializable {
        
    private static final long serialVersionUID = 1L; // 34an mat3mlsh corrubtion fi files 
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String description;
    private int sold;
    private double revenue;

    private static HashMap<Integer, Product> productMap = new HashMap<>();
    
    public static HashMap<Integer, Product> getProductMap() {
        return productMap;
    }

    public Product() {}

    public Product(int id, String name, String description, double price, int quantity, int sold, double revenue) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.sold = sold;
        this.revenue = revenue;
  
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSold() {
        return sold;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSold(int sold) {
    if (sold < 0) {
        System.out.println("Sold quantity cannot be negative.");
        return;
    }
    this.sold = sold;
    this.revenue = this.sold * this.price; // Update revenue whenever sold is updated
}
    public void setQuantity(int quantity) {
    if (quantity < 0) {
        System.out.println("Quantity cannot be negative.");
        return;
    }
     this.quantity = quantity;
}
    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
    
    public static ArrayList<Product> readProductsFromFile() {
        ArrayList<Product> products = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("products.dat"))) {
            products = (ArrayList<Product>) ois.readObject();
            for (Product p : products) {
                productMap.put(p.getId(), p); // Load into map
            }
        } catch (FileNotFoundException e) {
            System.out.println("products.dat file not found.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return products;
    }

    public static void writeProductsToFile(ArrayList<Product> products) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("products.dat"))) {//This class writes objects to an output stream, enabling serialization.
            oos.writeObject(products);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void saveProductsToFile() {
            Product.writeProductsToFile(new ArrayList<>(productMap.values()));
        }//Converts the collection of Product objects into an ArrayList to match the parameter type expected by writeProductsToFile.

    
    public static void addProduct() {
        Scanner input = new Scanner(System.in);
        int id = 0, quantity = 0;
        double price = 0.0;
        String name = "", description = "";

        try {
            while (true) {  
                try {
                    System.out.println("Enter Product ID:");
                    id = input.nextInt();
                    input.nextLine(); 
                    if (productMap.containsKey(id)) {
                        System.out.println("Product with this ID already exists. Cannot add.");
                        return;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a numeric Product ID.");
                    input.nextLine(); 
                }
            }

            System.out.println("Enter Product Name:");
            name = input.nextLine();

            System.out.println("Enter Product Description:");
            description = input.nextLine();

            while (true) { 
                try {
                    System.out.println("Enter Product Price:");
                    price = input.nextDouble();
                    break; 
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid price.");
                    input.nextLine(); 
                }
            }

            while (true) {
                try {
                    System.out.println("Enter Product Quantity:");
                    quantity = input.nextInt();
                    break; 
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid quantity.");
                    input.nextLine();
                }
            }
            int sold=0;
            double revenue = sold * price;
            Product newProduct = new Product(id, name, description, price, quantity, sold, revenue);
            productMap.put(id, newProduct);
            saveProductsToFile();
            System.out.println("Product added successfully!");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    public static void removeProduct() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Product ID to Remove:");
        int id = input.nextInt();

        if (productMap.remove(id) != null) {
            System.out.println("Product removed successfully!");
            saveProductsToFile();
        } else {
            System.out.println("Product not found!");
        }
    }

    public static void editProduct() {
        
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Product ID to Edit:");
        int id = input.nextInt();
        input.nextLine(); 

        Product productToEdit = productMap.get(id);
        if (productToEdit == null) {
            System.out.println("Product not found!");
            return;
        }
        System.out.println("Enter New Name (or press Enter to skip):");
        String newName = input.nextLine();
        if (!newName.isEmpty()) {
            productToEdit.setName(newName);
        }
        System.out.println("Enter New Description (or press Enter to skip):");
        String newDescription = input.nextLine();
        if (!newDescription.isEmpty()) {
            productToEdit.setDescription(newDescription);
        }
        System.out.println("Enter New Price (or 0 to skip):");
        double newPrice = input.nextDouble();
        if (newPrice != 0) {
            productToEdit.setPrice(newPrice);
        }

        System.out.println("Enter New Quantity (or 0 to skip):");
        int newQuantity = input.nextInt();
        if (newQuantity != 0) {
            productToEdit.setQuantity(newQuantity);
        }

        System.out.println("Product updated successfully!");
        saveProductsToFile();
    }

    public static void displayProducts() {
        if (productMap.isEmpty()) {
            System.out.println("No products available.");
        } else {
            System.out.println("All Products:");
            for (Product product : productMap.values()) {
             product.setRevenue(product.getSold() * product.getPrice());
                System.out.println(product);
            }
        }
    }

    public static void searchProducts(String field, String value) {
    boolean found = false;

    for (Product product : productMap.values()) {
        boolean match = false;

        if (field.equalsIgnoreCase("id")) {
            match = String.valueOf(product.getId()).contains(value);
        } else if (field.equalsIgnoreCase("name")) {
            match = product.getName().toLowerCase().contains(value.toLowerCase());
        } else if (field.equalsIgnoreCase("description")) {
            match = product.getDescription().toLowerCase().contains(value.toLowerCase());
        } else if (field.equalsIgnoreCase("price")) {
            match = String.valueOf(product.getPrice()).contains(value);
        } else if (field.equalsIgnoreCase("quantity")) {
            match = String.valueOf(product.getQuantity()).contains(value);
        } else if (field.equalsIgnoreCase("sold")) {
            match = String.valueOf(product.getSold()).contains(value);
        } else if (field.equalsIgnoreCase("revenue")) {
            match = String.valueOf(product.getRevenue()).contains(value);
        } else {
            System.out.println("Invalid search field: " + field);
            return;
        }

        if (match) {
            System.out.println(product);
            found = true;
        }
    }

    if (!found) {
        System.out.println("No matching products found for " + field + ": " + value);
    }
}
    public static Product getBestSellingProduct() {
        Product bestSellingProduct = null;
        int maxSold = 0;
        
        for (Product product : productMap.values()) {
            if (product.getSold() > maxSold) {
                maxSold = product.getSold();
                bestSellingProduct = product;
            }
        }
        
        return bestSellingProduct;
    }
    public static Product mostrevenueproduct ( String startDate, String endDate){
         Product highestRevenueProduct = null;
    double maxRevenue = 0.0;

   try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedStartDate = sdf.parse(startDate);
        Date parsedEndDate = sdf.parse(endDate);

        // Iterate over all products
        for (Product p : productMap.values()) {
            double totalRevenueInPeriod = 0.0;

            // Calculate total revenue (assuming the sold field is within the date range)
            totalRevenueInPeriod = p.getSold() * p.getPrice();

            // Check if this product has the highest revenue
            if (totalRevenueInPeriod > maxRevenue) {
                maxRevenue = totalRevenueInPeriod;
                highestRevenueProduct = p;
            }
        }

        // Output the result
        if (highestRevenueProduct != null) {
            System.out.println("Product with Highest Revenue: " + highestRevenueProduct.getName());
            System.out.println("Total Revenue: " + maxRevenue);
        } else {
            System.out.println("No sales found in the given period.");
        }
    } catch (Exception e) {
        System.out.println("Error parsing dates: " + e.getMessage());
    }

    return highestRevenueProduct;
}
    @Override
    public String toString() {
       return "Product[ID: " + id +
            ", Name: " + name +
            ", Description: " + description +
            ", Price: " + price +
            ", Quantity: " + quantity +
            ", Sold: " + sold +
            ", Revenue: " + revenue + "]";
    }
}