package com.example.teacher_rating;

import java.util.HashMap;
import java.util.Map;

public class database {

    private String name;
    private String mobile;
    private String email;
    private String Password;



    public database(String name, String mobile , String email , String password) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.Password = password;
    }

    public database() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
