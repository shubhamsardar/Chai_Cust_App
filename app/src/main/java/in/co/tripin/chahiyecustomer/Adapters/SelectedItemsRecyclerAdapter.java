package in.co.tripin.chahiyecustomer.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.tripin.chahiyecustomer.Model.responce.OrderHistoryResponce;
import in.co.tripin.chahiyecustomer.Model.responce.TapriMenuResponce;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Logger;

public class SelectedItemsRecyclerAdapter extends RecyclerView.Adapter<SelectedItemsRecyclerAdapter.ViewHolder> {

    public ArrayList<TapriMenuResponce.Data.Item> data;
    public OrderHistoryResponce.Data.Details[] details;

    public SelectedItemsRecyclerAdapter(ArrayList<TapriMenuResponce.Data.Item>data) {
        this.data = data;
    }

    public SelectedItemsRecyclerAdapter(OrderHistoryResponce.Data.Details[] details) {
        this.details = details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_selected_item, parent, false);

        return new SelectedItemsRecyclerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(data!=null){
            holder.name.setText(data.get(position).getName());
            holder.quant.setText(data.get(position).getQuantity()+"");
            holder.rate.setText("₹"+data.get(position).getRate());
        }else {
            Logger.v("Data List Null");
            if(details!=null){
                holder.name.setText(details[position].getItemName());
                holder.quant.setText(details[position].getQuantity()+"");
                holder.rate.setText("₹"+details[position].getAmount());
            }else {
                Logger.v("Details list null");
            }
        }



    }

    @Override
    public int getItemCount() {
        if(data!=null){
            return data.size();
        }else {
            if(details!=null){
                return details.length;
            }else {
                return 0;
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView quant;
        public TextView rate;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            quant = itemView.findViewById(R.id.item_quant);
            rate = itemView.findViewById(R.id.item_rate);
        }
    }
}
