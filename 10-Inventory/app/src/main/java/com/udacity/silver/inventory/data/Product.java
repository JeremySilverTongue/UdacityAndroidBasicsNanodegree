package com.udacity.silver.inventory.data;


public class Product {

    public final String name;
    public final int priceInCents;
    public int quantity;
//    public final String vendorEmail;
//    public final String imagePath;


    public Product(String name, int quantity, int priceInCents) {
        this.name = name;
        this.quantity = quantity;
        this.priceInCents = priceInCents;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", priceInCents=" + priceInCents +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return quantity == product.quantity && priceInCents == product.priceInCents && name.equals(product.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + quantity;
        result = 31 * result + priceInCents;
        return result;
    }
}
