package in.co.tripin.chahiyecustomer.Adapters;

import android.content.Context;
import android.opengl.Visibility;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.tripin.chahiyecustomer.Model.responce.OrderHistoryResponce;
import in.co.tripin.chahiyecustomer.Model.responce.TapriMenuResponce;
import in.co.tripin.chahiyecustomer.R;

public class OrderHistoryRecyclerAdapter extends RecyclerView.Adapter<OrderHistoryRecyclerAdapter.ViewHolder> {

    public OrderHistoryResponce.Data[] data;
    public OrderStatusToggleCallback callback;
    public Context context;

    public OrderHistoryRecyclerAdapter(Context context, OrderHistoryResponce.Data[] data, OrderStatusToggleCallback callback) {
        this.data = data;
        this.callback = callback;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_history, parent, false);
        return new OrderHistoryRecyclerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        SelectedItemsRecyclerAdapter selectedItemsRecyclerAdapter = new SelectedItemsRecyclerAdapter(data[position].getDetails());
        RecyclerView.LayoutManager layoutManager = new CustomLinearLayoutManager(context);
        holder.mSelctedItemsList.setLayoutManager(layoutManager);
        holder.mSelctedItemsList.setAdapter(selectedItemsRecyclerAdapter);

        if(position==0){
            holder.mBody.setVisibility(View.VISIBLE);
        }

        holder.mHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.mBody.getVisibility()== View.GONE){
                    holder.mBody.setVisibility(View.VISIBLE);
                    holder.mOrderId.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_black_24dp, 0, 0, 0);

                }else {
                    holder.mBody.setVisibility(View.GONE);
                    holder.mOrderId.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_down_black_24dp, 0, 0, 0);
                }
            }
        });

        if(data[position].getOrderStatus().equals("delivered")){
            holder.mRecivedSwitch.setChecked(true);
            holder.mRecivedSwitch.setTextColor(ContextCompat.getColor(context,R.color.colorGreen));
            holder.mRecivedSwitch.setClickable(false);
        }else {
            holder.mRecivedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        callback.OnOrderMakedRecived(data[position].get_id());
                        holder.mRecivedSwitch.setTextColor(ContextCompat.getColor(context,R.color.colorGreen));
                    }
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        if(data==null){
            return 0;
        }else {
            return data.length;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //UI Elements
        public TextView mTapriName;
        public TextView mTotalCost;
        public TextView mAddress;
        public TextView mPaymentMethod;
        public RecyclerView mSelctedItemsList;
        public Switch mRecivedSwitch;
        public TextView mOrderId;
        public TextView mOrderStatus;
        public LinearLayout mHeader;
        public LinearLayout mBody;
        public TextView mDate;


        public ViewHolder(View itemView) {
            super(itemView);
            mTapriName = itemView.findViewById(R.id.tapriname);
            mTotalCost = itemView.findViewById(R.id.totalcost);
            mAddress = itemView.findViewById(R.id.addressall);
            mPaymentMethod = itemView.findViewById(R.id.paymentmethod);
            mSelctedItemsList = itemView.findViewById(R.id.selected_items_list);
            mRecivedSwitch = itemView.findViewById(R.id.switchRecived);
            mOrderId = itemView.findViewById(R.id.orderid);
            mOrderStatus = itemView.findViewById(R.id.orderstatus);
            mHeader = itemView.findViewById(R.id.order_header);
            mBody = itemView.findViewById(R.id.order_footer);
            mDate = itemView.findViewById(R.id.orderdate);

        }
    }
}
