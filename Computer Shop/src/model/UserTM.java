package model;

import java.util.Date;

public class UserTM {
   private String userId;
   private String name;
   private String address;
   private String city;
   private Date date;

    public UserTM(String userId, String name, String address, String city, Date date) {
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.city = city;
        this.date = date;
    }

    public UserTM(String userId, String name, String address, String city) {
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.city = city;
    }

    public UserTM() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "UserTM{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", date=" + date +
                '}';
    }
}
