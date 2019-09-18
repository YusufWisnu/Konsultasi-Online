package com.example.konsulyuk.Models;

public class User {
    public String name, email, hp, password, user_level ,userid, profilImageUri;

    public User(String name, String email, String hp, String password, String user_level, String userid) {
        this.name = name;
        this.email = email;
        this.hp = hp;
        this.password = password;
        this.user_level = user_level;
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_level() {
        return user_level;
    }

    public void setUser_level(String user_level) {
        this.user_level = user_level;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
