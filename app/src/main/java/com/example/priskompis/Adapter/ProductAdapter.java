package com.example.priskompis.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.priskompis.InShop;
import com.example.priskompis.Model.Order;
import com.example.priskompis.Model.ProductModel;
import com.example.priskompis.R;
import com.example.priskompis.ShoppingCart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;
    private DecimalFormat df = new DecimalFormat("#.#");
    //we are storing all the products in a list
    private List<ProductModel> productList;
    private HashMap<String, ProductModel> productsMap;
    private HashMap<String, Float> productQuantity;

    //getting the context and product list with constructor
    public ProductAdapter(Context mCtx, HashMap<String, ProductModel> productsMap, HashMap<String, Float> productQuantity) {
        this.mCtx = mCtx;
        this.productsMap = productsMap;
        this.productQuantity = productQuantity;
        this.productList = new ArrayList<ProductModel>(productsMap.values());
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_products, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder productViewHolder, int position) {
        //getting the product of the specified position
        ProductModel product = productList.get(position);

        //binding the data with the viewholder views
        productViewHolder.textItem.setText(product.getName());
        productViewHolder.textPrice.setText(String.valueOf(product.getPriceICA()));

        productViewHolder.textQuantity.setText(String.valueOf(productQuantity.get(product.getID())));
        productViewHolder.textSize.setText(product.getQuantity());
        productViewHolder.textTotalPrice.setText(df.format(product.getPriceICA()*productQuantity.get(product.getID())));
    }

    // delete item from recycler view
    void deleteItem(int index) {
        ProductModel product = productList.get(index);
        String productID = product.getID();
        System.out.println(productID);
        if(mCtx instanceof ShoppingCart){
            ((ShoppingCart)mCtx).removeProduct(product);
        }
        productList.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    
    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textItem, textPrice, textQuantity, textSize, textTotalPrice;
        ImageButton delete;

        public ProductViewHolder(final View itemView) {
            super(itemView);

            textItem = itemView.findViewById(R.id.itemName);
            textPrice = itemView.findViewById(R.id.itemPrice);
            textQuantity = itemView.findViewById(R.id.itemQuantity);
            textSize = itemView.findViewById(R.id.itemSize);
            textTotalPrice=itemView.findViewById(R.id.itemTotalPrice);
            delete = itemView.findViewById(R.id.item_remove);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(getAdapterPosition());
                }
            });
        }
    }


}

