package in.co.tripin.chahiyecustomer.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import in.co.tripin.chahiyecustomer.Model.responce.UserAddress;
import in.co.tripin.chahiyecustomer.R;

public class AddressListRecyclerAdapter extends RecyclerView.Adapter<AddressListRecyclerAdapter.Viewholder> {

    private UserAddress.Data[] data;
    private AddresslistInteractionCallback callback;

    public AddressListRecyclerAdapter(UserAddress.Data[] data, AddresslistInteractionCallback callback){
        this.data = data;
        this.callback = callback;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_item_layout, parent, false);

        return new Viewholder(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {

        holder.nick.setText(data[position].getNickname());

        holder.full.setText(data[position].getFullAddressString());

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onAddressRemoved(data[position]);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onAddressSelected(data[position]);
            }
        });



    }

    @Override
    public int getItemCount() {
        if(data != null){
            return data.length;
        }else {
            return 0;
        }
    }

    class Viewholder extends RecyclerView.ViewHolder{

        public TextView nick, full;
        public ImageView remove;

        public Viewholder(View itemView) {
            super(itemView);
            nick = itemView.findViewById(R.id.addressnick);
            full = itemView.findViewById(R.id.fulladdress);
            remove = itemView.findViewById(R.id.remove);
        }
    }
}
