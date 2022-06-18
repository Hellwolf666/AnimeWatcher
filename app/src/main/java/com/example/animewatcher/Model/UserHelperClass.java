package com.example.animewatcher.Model;

public class UserHelperClass {
    String name, username, email, password, image,uid;

    public UserHelperClass() {
    }

    public UserHelperClass(String name, String username, String email, String password, String image, String uid) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.image = image;
        this.uid = uid;
    }

    public UserHelperClass(String name, String username, String email, String password, String image) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public UserHelperClass(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
