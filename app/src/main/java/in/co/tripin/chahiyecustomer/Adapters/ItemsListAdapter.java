package in.co.tripin.chahiyecustomer.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import in.co.tripin.chahiyecustomer.Model.responce.TapriMenuResponce;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Logger;

public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListAdapter.ViewHolder> {

    public TapriMenuResponce.Data.Item[] data;
    public Context context;

    public ItemsListAdapter(Context context,TapriMenuResponce.Data.Item[] data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_select_item, parent, false);
        return new ItemsListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.itemName.setText(data[position].getName());
        holder.itemRate.setText("₹" + data[position].getRate());
        holder.display.setText("" + data[position].getQuantity());

        holder.display.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()){
                    data[position].setQuantity(0);
                }else {
                    if(!s.toString().equals("0")){
                        data[position].setQuantity(Integer.decode(s.toString().trim()));
                        Logger.v("Quantity: " + data[position].getQuantity());

                        if(data[position].getQuantity()!=0){
                            holder.display.setBackgroundColor(ContextCompat.getColor(context,R.color.colorAccent));
                            holder.display.setTextColor(ContextCompat.getColor(context,R.color.black));
                            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorHighlight));
                        }else {
                            holder.display.setBackground(ContextCompat.getDrawable(context,R.drawable.brown_border_bg));
                            holder.display.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
                            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.transparent));

                        }

                    }else {
                        data[position].setQuantity(0);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        if(data[position].getQuantity()!=0){
            holder.display.setBackgroundColor(ContextCompat.getColor(context,R.color.colorAccent));
            holder.display.setTextColor(ContextCompat.getColor(context,R.color.black));
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorHighlight));
        }else {
            holder.display.setBackground(ContextCompat.getDrawable(context,R.drawable.brown_border_bg));
            holder.display.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.transparent));

        }


        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int next = data[position].getQuantity() + 1;
                if (next <= 100) {
                    data[position].setQuantity(next);
                    notifyItemChanged(position);
                    Logger.v("Quantity: " + data[position].getQuantity());
                }
            }
        });

        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int next = data[position].getQuantity() - 1;
                if (next >= 0) {
                    data[position].setQuantity(next);
                    notifyItemChanged(position);
                    Logger.v("Quantity: " + data[position].getQuantity());

                }
            }
        });



    }



    @Override
    public int getItemCount() {
        if (data != null) {
            return data.length;
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemName;
        public TextView itemRate;
        public Button increment, decrement;
        public EditText display;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemRate = itemView.findViewById(R.id.itemrate);
            increment = itemView.findViewById(R.id.increment);
            decrement = itemView.findViewById(R.id.decrement);
            display = itemView.findViewById(R.id.display);
        }

    }
}
