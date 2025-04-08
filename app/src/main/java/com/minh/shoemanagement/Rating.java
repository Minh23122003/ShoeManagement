package com.minh.shoemanagement;

public class Rating {
    private long id;
    private long star;
    private String content;
    private User user;
    private Shoe shoe;
    private String createdDate;

    public Rating(){

    }

    public Rating(long id, long star, String content, User user, Shoe shoe, String createdDate) {
        this.id = id;
        this.star = star;
        this.content = content;
        this.user = user;
        this.shoe = shoe;
        this.createdDate = createdDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStar() {
        return star;
    }

    public void setStar(long star) {
        this.star = star;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
