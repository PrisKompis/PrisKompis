package com.example.android.priskompis.DataHandling;

import java.util.ArrayList;

public class DataOperations
    {


    /**
     * Find different total price in different supermarkets
     * @param shopList the shopping list intended by the shopper
     * @return an array that contains the total shoplist prices for the different supermarkets
     *         0=ICA ,1=Willys , 2=Coop
     */
    public float[] total(ArrayList<ProductModel> shopList)
            {

                float sum[]={0f,0f,0f};
                for(ProductModel product:shopList)
                {
                    sum[0]+=product.getPriceICA();
                    sum[1]+=product.getPriceWillys();
                    sum[2]+=product.getPriceCoop();
                }

                return sum;
            }


    }
