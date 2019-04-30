/*import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.priskompis.Model.Order;
import com.example.priskompis.R;

import java.util.HashMap;

public class MyAdapter extends RecyclerView.com.example.priskompis.Adapter<MyAdapter.MyViewHolder> {
private HashMap<String, Order> mDataset;

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public static class MyViewHolder extends RecyclerView.ViewHolder {
// each data item is just a string in this case
public TextView textView;
public MyViewHolder(TextView v) {
super(v);
textView = v;
}
}

// Provide a suitable constructor (depends on the kind of dataset)
public MyAdapter(HashMap<String,Order> orderList) {
mDataset = orderList;
}

// Create new views (invoked by the layout manager)
@Override
public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
// create a new view
TextView productName = (TextView) LayoutInflater.from(parent.getContext())
        .inflate(R.layout.myorderview, parent, false);

MyViewHolder productNameHolder = new MyViewHolder(productName);
return productNameHolder;
}

// Replace the contents of a view (invoked by the layout manager)
@Override
public void onBindViewHolder(MyViewHolder holder, int position) {
// - get element from your dataset at this position
// - replace the contents of the view with that element
//holder.textView.setText(mDataset[position]);

}

// Return the size of your dataset (invoked by the layout manager)
@Override
public int getItemCount() {
//return mDataset.length;
}
}*/