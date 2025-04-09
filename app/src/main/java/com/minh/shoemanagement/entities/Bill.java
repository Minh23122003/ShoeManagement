package com.minh.shoemanagement.entities;

public class Bill {
    private long id;
    private User user;
    private Shoe shoe;
    private long quantity;
    private long size;
    private String createdDate;
    private String paymentDate;

    public Bill(){

    }

    public Bill(long id, User user, Shoe shoe, long quantity, long size, String createdDate, String paymentDate) {
        this.id = id;
        this.user = user;
        this.shoe = shoe;
        this.quantity = quantity;
        this.size = size;
        this.createdDate = createdDate;
        this.paymentDate = paymentDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
}
