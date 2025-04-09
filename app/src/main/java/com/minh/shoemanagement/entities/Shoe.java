package com.minh.shoemanagement.entities;

public class Shoe {
    private long id;
    private String name;
    private String information;
    private long quantity;
    private Category category;
    private long price;
    private String image;
    private String note;

    public Shoe(){

    }

    public Shoe(long id, String name, String information, long quantity, Category category, long price, String image, String note) {
        this.id = id;
        this.name = name;
        this.information = information;
        this.quantity = quantity;
        this.category = category;
        this.price = price;
        this.image = image;
        this.note = note;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
