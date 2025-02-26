/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

/**
 *
 * @author MalakMostafaZaky
 */
    public abstract class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String address;
    
    public User(int id, String name, String username, String email, String password,String address) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address=address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public abstract String getUserType();
    public  abstract boolean login(String username, String password);
    public abstract void  showMenu();
    @Override
    public String toString() {
        return "ID: " + id +
               ", Name: " + name +
               ", Username: " + username +
               ", Email: " + email +
               ", User Type: " + getUserType();
    }
}
    

