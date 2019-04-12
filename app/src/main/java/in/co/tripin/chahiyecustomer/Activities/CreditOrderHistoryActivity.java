package in.co.tripin.chahiyecustomer.Activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import in.co.tripin.chahiyecustomer.Adapters.CustomLinearLayoutManager;
import in.co.tripin.chahiyecustomer.Adapters.SelectedItemsRecyclerAdapter;
import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.Model.responce.OrderHistoryResponce;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Constants;
import in.co.tripin.chahiyecustomer.services.CreditService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreditOrderHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tvFetching;
    PreferenceManager preferenceManager ;
    OrderHistoryResponce.Data [] data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_order_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferenceManager = PreferenceManager.getInstance(this);
        recyclerView = (RecyclerView) findViewById(R.id.creditOrderList);
        tvFetching = (TextView)findViewById(R.id.loadingtv);
        fetchCreditOrders();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_refresh:
                fetchCreditOrders();
                //
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_order_history,menu);
        return true;
    }

    public void fetchCreditOrders() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CreditService creditService = retrofit.create(CreditService.class);
        Call<OrderHistoryResponce> call = creditService.getCreditOrders(preferenceManager.getAccessToken(),preferenceManager.getOfficeId());

        call.enqueue(new Callback<OrderHistoryResponce>() {
            @Override
            public void onResponse(Call<OrderHistoryResponce> call, Response<OrderHistoryResponce> response) {
                if(response.isSuccessful())
                {
                    OrderHistoryResponce orderHistoryResponce = response.body();

                    if(orderHistoryResponce != null) {
                        data = orderHistoryResponce.getData();
                        if(data.length == 0)
                        {
                            tvFetching.setText("Empty Order History, Get some orders!");
                        }else {
                            tvFetching.setVisibility(View.GONE);
                            Toast.makeText(CreditOrderHistoryActivity.this, "successs", Toast.LENGTH_SHORT).show();
                            CreditOrdersRecyclerAdapter creditOrdersRecyclerAdapter = new CreditOrdersRecyclerAdapter(CreditOrderHistoryActivity.this, data);
                            recyclerView.setAdapter(creditOrdersRecyclerAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(CreditOrderHistoryActivity.this));
                        }
                    }
                }
                else
                {
                    Toast.makeText(CreditOrderHistoryActivity.this, "not success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderHistoryResponce> call, Throwable t) {
                Toast.makeText(CreditOrderHistoryActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                Log.d("Failure : ",t.getMessage());
            }
        });
    }


    public class CreditOrdersRecyclerAdapter extends RecyclerView.Adapter<CreditOrdersRecyclerAdapter.ViewHolder> {

        public OrderHistoryResponce.Data[] data;
        public Context context;

        public CreditOrdersRecyclerAdapter(Context context, OrderHistoryResponce.Data[] data) {
            this.data = data;
            this.context = context;
        }

        @NonNull
        @Override
        public CreditOrdersRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_credit_history, parent, false);
            return new CreditOrdersRecyclerAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final CreditOrdersRecyclerAdapter.ViewHolder holder, final int position) {

            SelectedItemsRecyclerAdapter selectedItemsRecyclerAdapter = new SelectedItemsRecyclerAdapter(data[position].getDetails());
            RecyclerView.LayoutManager layoutManager = new CustomLinearLayoutManager(context);
            holder.mSelctedItemsList.setLayoutManager(layoutManager);
            holder.mSelctedItemsList.setAdapter(selectedItemsRecyclerAdapter);

            //holder.mTapriName.setText(data[position].getTapriId().getName());
            holder.mOrderId.setText("#" + data[position].getShortId().toUpperCase());
            holder.mOrderStatus.setText(data[position].getOrderStatus().toUpperCase());
            String timestamp = data[position].getCreatedAt();
            holder.mDate.setText(timestamp.substring(0, timestamp.indexOf('T')));

            holder.mTotalCost.setText("₹" + data[position].getTotalAmount());
            holder.mAddress.setText(data[position].getAddressId().getFullAddressString());
            holder.mPaymentMethod.setText(data[position].getPaymentType());

            if (position == data.length - 1) {
                holder.mBody.setVisibility(View.VISIBLE);
                holder.mOrderId.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_black_24dp, 0, 0, 0);
            }


        }

        @Override
        public int getItemCount() {
            return data.length;
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
                mTotalCost = itemView.findViewById(R.id.totalcost);
                mAddress = itemView.findViewById(R.id.addressall);
                mPaymentMethod = itemView.findViewById(R.id.paymentmethod);
                mSelctedItemsList = itemView.findViewById(R.id.selected_items_list);
                mOrderId = itemView.findViewById(R.id.orderid);
                mOrderStatus = itemView.findViewById(R.id.orderstatus);
                mHeader = itemView.findViewById(R.id.order_header);
                mBody = itemView.findViewById(R.id.order_footer);
                mDate = itemView.findViewById(R.id.orderdate);


                mHeader.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mBody.getVisibility() == View.GONE) {
                            mBody.setVisibility(View.VISIBLE);
                            mOrderId.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_black_24dp, 0, 0, 0);


                        } else {
                            mBody.setVisibility(View.GONE);
                            mOrderId.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_down_black_24dp, 0, 0, 0);
                        }
                    }
                });

            }
        }
    }
}
