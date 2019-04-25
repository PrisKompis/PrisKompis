package com.example.priskompis.Operations;


import com.example.priskompis.Model.ProductModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class Database
    {

    DatabaseReference reference;

    public HashMap getItems()
        {
        return items;
        }

    public ArrayList<ProductModel> getProductList()
        {
        return productList;
        }

    ArrayList<ProductModel> productList=new ArrayList<>();
    HashMap<String,ArrayList<String>> products;


    HashMap<String,ProductModel> items=new HashMap<>();
  /*  public  ArrayList<ProductModel> getItems()
        {
        return items;
        }



    public ArrayList<String> getCategories()
        {
        return categories;

        }*/

  public ProductModel getItemByID(String ID)
      {
      return items.get(ID);
      }

    /**
     * Find different total price in different supermarkets
     *
     * @param shopList the shopping list intended by the shopper
     * @return an array that contains the total shoplist prices for the different supermarkets
     * 0=ICA ,1=Willys , 2=Coop
     */
    public float[] total(ArrayList<ProductModel> shopList)
        {

        float sum[] = {0, 0, 0};
        for (ProductModel product : shopList)
            {
            sum[0] += product.getPriceICA();
            sum[1] += product.getPriceWillys();
            sum[2] += product.getPriceCoop();
            }

        return sum;
        }

    public void init()
        {
        products = new HashMap<>();
        productList=new ArrayList<>();
        items=new HashMap<>();
        }


    public void retrieveDatabase()
        {

        reference = FirebaseDatabase.getInstance().getReference();
        init();
        reference.addValueEventListener(new ValueEventListener()
            {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
                {


                for (DataSnapshot ds1 : dataSnapshot.getChildren())
                    {
                    String category = ds1.getKey();
                    products.put(category,new ArrayList<String>());

                    }


                for (String cat : products.keySet())
                    {


                    for (DataSnapshot ds2 : dataSnapshot.child(cat).getChildren())
                        {
                        String subcategory = ds2.getKey();
                        products.get(cat).add(subcategory);

                        }
                    }

                for (String cat : products.keySet())
                    {
                    for (String subcat : products.get(cat))
                        {

                        for (DataSnapshot ds3 : dataSnapshot.child(cat).child(subcat).getChildren())
                            {

                            String id = ds3.getKey();
                            ProductModel product = ds3.getValue(ProductModel.class);

                            items.put(id,product);
                            productList.add(product);
                            //Log.i("Product Name:",products.get(products.size()-1).getName());

                            }
                        }
                    }


                }


            @Override
            public void onCancelled(DatabaseError databaseError)
                {
                //Toast.makeText(this,"Error with database",Toast.LENGTH_SHORT).show();
                }
            });

        }


    }