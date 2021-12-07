package com.example.misael.Dto;


public class UserDTO {
    int  id ;
    String name;
    String lastname;
    String user;
    String password;

    public UserDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return name;
    }

    public void setStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return lastname;
    }

    public void setName(String lastname) {
        this.name = lastname;
    }

    public String getRole() {
        return user;
    }

    public void setRole(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
