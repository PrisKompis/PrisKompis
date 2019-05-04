package com.example.priskompis.Model;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.example.priskompis.InShop;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Order implements Serializable
{
    private HashMap<String, ProductModel> products;
    private HashMap<String, Float>productQuantity = new HashMap<>();
    private float totalPrice = 0;


    public Order() {
        this.products = new HashMap<>();


    }

    @Override
    public String toString()
    {
        DecimalFormat df = new DecimalFormat("#.#");
        String result="";
        for(String id:products.keySet())
        {
            result+= products.get(id).getName() + "\t" + products.get(id).getQuantity() + "\t" + productQuantity.get(id) + " x " + products.get(id).getPriceICA() + "\t" + df.format((double)productQuantity.get(id)*(double)products.get(id).getPriceICA())+"\n";
        }
        return result;
    }




    public HashMap<String, ProductModel> getProducts()
    {
        return products;
    }

    public void setProducts(HashMap<String, ProductModel> product)
    {
        this.products = products;
    }

    public HashMap<String, Float> getProductQuantity() {
        return productQuantity;
    }

    public void setTotalPrice(float totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public void addProduct(ProductModel product, Float quantity) {

        Boolean isProductInCart = (this.products.get(product.getID()) != null);
        if (!isProductInCart) {
            System.out.println("Product not in cart");
            this.products.put(product.getID(), product);
            productQuantity.put(product.getID(),quantity);
        }
        else{
            System.out.println("Product is in cart");
            Float currentQuantity = productQuantity.get(product.getID());
            System.out.println("Product is in cart" + currentQuantity);

            productQuantity.put(product.getID(), currentQuantity + quantity);
        }
        printProductQuantity();
    }

    public void setProductQuantity(String productID, Float quantity) {
        productQuantity.put(productID, quantity);
    }

    public float getTotalPrice()
    {
        float total = 0;
        Set<String> keys = products.keySet();
        //print all the keys
        for (String key : keys) {
            ProductModel product = products.get(key);
            total += product.getPriceICA() * productQuantity.get(product.getID());
        }
        return total;
    }

    public void printProductQuantity() {
        System.out.println("Printing product quantity");
        Set<String> keys = productQuantity.keySet();
        //print all the keys
        for (String key : keys) {
            System.out.println(key + ":/:" + productQuantity.get(key));
        }
    }

    public void removeProduct(ProductModel product) {
        Boolean isProductInCart = (this.products.get(product.getID()) != null);
        if (!isProductInCart) {
            System.out.println("Product not in cart");
        }
        else{
            products.remove(product.getID());
            productQuantity.remove(product.getID());
            System.out.println("Product removed");
        }
        printProductQuantity();
    }

    public void printProducts() {
        System.out.println("Printing products");
        Set<String> keys = products.keySet();
        //print all the keys
        for (String key : keys) {
            System.out.println(key + ":/:" + products.get(key).getName() + "\t" + products.get(key).getID());
        }
    }
}
