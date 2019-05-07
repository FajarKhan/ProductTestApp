package com.example.prodecttestapp.model;

public class data {

    private String image;

    private sort_props sort_props;

    private String category_name;

    private String category_id;

    private String price;

    private String qty;

    private String name;

    private String description;

    private String id;

    private String size;

    public data(String image, com.example.prodecttestapp.model.sort_props sort_props, String category_name, String category_id, String price, String qty, String name, String description, String id, String size) {
        this.image = image;
        this.sort_props = sort_props;
        this.category_name = category_name;
        this.category_id = category_id;
        this.price = price;
        this.qty = qty;
        this.name = name;
        this.description = description;
        this.id = id;
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public com.example.prodecttestapp.model.sort_props getSort_props() {
        return sort_props;
    }

    public void setSort_props(com.example.prodecttestapp.model.sort_props sort_props) {
        this.sort_props = sort_props;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
