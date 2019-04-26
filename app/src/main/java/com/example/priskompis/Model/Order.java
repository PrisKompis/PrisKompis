package com.example.priskompis.Model;

import java.io.Serializable;

public class Order implements Serializable
    {
        private ProductModel product;
        private float quantity;
        private float singlePrice;
        private float totalPrice;

    public ProductModel getProduct()
        {
        return product;
        }

    public void setProduct(ProductModel product)
        {
        this.product = product;
        }

    public float getQuantity()
        {
        return quantity;
        }

    public void setQuantity(float quantity)
        {
        this.quantity = quantity;
        }

    public float getSinglePrice()
        {
        return singlePrice;
        }

    public void setSinglePrice(float singlePrice)
        {
        this.singlePrice = singlePrice;
        }

    public float getTotalPrice()
        {
        return totalPrice;
        }

    public void setTotalPrice(float totalPrice)
        {
        this.totalPrice = totalPrice;
        }
    }
