/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

/**
 *
 * @author MalakMostafaZaky
 */
import java.util.HashMap;
import java.util.*;
import java.util.Map;

public class Cart {
  private Map<Product, Integer> cartItems = new HashMap<>(); // Map to store Product instance and quantity
   // public List<Product> products;         // List to hold all available products
    public List<Product> products = new ArrayList<>();
    public Cart(List<Product> products) {
        this.products = products;
        this.cartItems = new HashMap<>();
    }
    public Cart(){
        this.products = Product.readProductsFromFile(); 
    }

    // Find a product by name in the list of products
  public Product findProductByName(String productName) {
        if (products == null) {
            System.out.println("Error: Product list is not initialized.");
            return null;
        }

        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }

    // Add a product to the cart by name
    public void addToCart(String productName, int quantity) {
        Product product = findProductByName(productName);
        if (product != null) {
            System.out.println("Product with name '" + productName + "' not found.");
            
            return;
        }

        else {
            System.out.println("Product not found!");
        }
        if (product.getQuantity() < quantity) {
            System.out.println("Insufficient stock for product: " + product.getName());
            return;
        }

        // Add or update the product in the cart
        cartItems.put(product, cartItems.getOrDefault(product, 0) + quantity);

        // Update the product's stock
        product.setQuantity(product.getQuantity() - quantity);

        System.out.println("Added " + quantity + " " + product.getName() + "(s) to the cart.");
    }

    // Remove a product from the cart by name
    public void removeFromCart(String productName) {
        Product product = findProductByName(productName);
        if (product == null || !cartItems.containsKey(product)) {
            System.out.println("Product not found in the cart.");
            
            return;
        }

        int quantityInCart = cartItems.get(product);
        cartItems.remove(product);
     

        // Restore the product's stock
        product.setQuantity(product.getQuantity() + quantityInCart);

        System.out.println("Removed " + product.getName() + " from the cart.");
    }

    // Display the contents of the cart
    public void displayCart() {
        if (cartItems.isEmpty()) {
            System.out.println("The cart is empty.");
            return;
        }

        System.out.println("Cart items:");
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.println("Product Name: " + product.getName() +
                               ", Quantity: " + quantity +
                               ", Price per unit: " + product.getPrice() +
                               ", Total: " + (quantity * product.getPrice()));
        }
    }

    // Get the total price of items in the cart
    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalPrice += product.getPrice() * quantity;
        }
        return totalPrice;
    }

    // Clear the cart and restore product stock
   public void clearCart() {
        if (cartItems != null && !cartItems.isEmpty()) {
            cartItems.clear();
            System.out.println("Cart cleared.");
        } else {
            System.out.println("Cart is already empty.");
        }
    }
}