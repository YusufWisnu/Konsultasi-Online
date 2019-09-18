package com.example.konsulyuk.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Psikolog implements Parcelable {
    String name, email, hp, password, user_level, kategori, psi_id;
    float rating;
    boolean status_aktif;

    public Psikolog(){}

    public Psikolog(String name, String email, String hp, String password, String user_level, String kategori, String psi_id, float rating, boolean status_aktif) {
        this.name = name;
        this.email = email;
        this.hp = hp;
        this.password = password;
        this.user_level = user_level;
        this.kategori = kategori;
        this.psi_id = psi_id;
        this.rating = rating;
        this.status_aktif = status_aktif;
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

    public String getPsi_id() {
        return psi_id;
    }

    public void setPsi_id(String psi_id) {
        this.psi_id = psi_id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public boolean isStatus_aktif() {
        return status_aktif;
    }

    public void setStatus_aktif(boolean status_aktif) {
        this.status_aktif = status_aktif;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.hp);
        dest.writeString(this.password);
        dest.writeString(this.user_level);
        dest.writeString(this.kategori);
        dest.writeString(this.psi_id);
        dest.writeFloat(this.rating);
        dest.writeByte(this.status_aktif ? (byte) 1 : (byte) 0);
    }

    protected Psikolog(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
        this.hp = in.readString();
        this.password = in.readString();
        this.user_level = in.readString();
        this.kategori = in.readString();
        this.psi_id = in.readString();
        this.rating = in.readFloat();
        this.status_aktif = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Psikolog> CREATOR = new Parcelable.Creator<Psikolog>() {
        @Override
        public Psikolog createFromParcel(Parcel source) {
            return new Psikolog(source);
        }

        @Override
        public Psikolog[] newArray(int size) {
            return new Psikolog[size];
        }
    };
}
