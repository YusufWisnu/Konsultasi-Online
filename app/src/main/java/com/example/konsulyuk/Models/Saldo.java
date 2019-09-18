package com.example.konsulyuk.Models;

public class Saldo {
    public int saldo;
    public String name, userid;

    public Saldo(){}

    public Saldo(int saldo, String name, String userid) {
        this.saldo = saldo;
        this.name = name;
        this.userid = userid;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
