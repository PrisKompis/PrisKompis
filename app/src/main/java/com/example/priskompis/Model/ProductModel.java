package com.example.priskompis.Model;

import java.io.Serializable;

public class ProductModel implements Serializable
{
    private String ID;
    private String category;
    private String subCategory;

    private String name;
    private boolean isBulk;
    private String quantity;


    private float priceICA;
    private float priceWillys;
    private float priceCoop;



    public float getPriceWillys()
    {
        return priceWillys;
    }

    public void setPriceWillys(float priceWillys)
    {
        this.priceWillys = priceWillys;
    }

    public float getPriceCoop()
    {
        return priceCoop;
    }

    public void setPriceCoop(float priceCoop)
    {
        this.priceCoop = priceCoop;
    }

    public String getQuantity()
        {
        return quantity;
    }

    public void setQuantity(String weight)
        {
        this.quantity = weight;
    }

    public String getSubCategory()
    {
        return subCategory;
    }

    public void setSubCategory(String subCategory)
    {
        this.subCategory = subCategory;
    }

    public String getID()
    {
        return ID;
    }

    public void setID(String ID)
    {
        this.ID = ID;
    }

    public float getPriceICA()
    {
        return priceICA;
    }

    public void setPriceICA(float price)
    {
        this.priceICA = price;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public boolean isBulk()
    {
        return isBulk;
    }

    public void setBulk(boolean bulk)
    {
        isBulk = bulk;
    }


}
