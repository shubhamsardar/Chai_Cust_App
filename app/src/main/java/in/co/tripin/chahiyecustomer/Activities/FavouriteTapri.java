package in.co.tripin.chahiyecustomer.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.Model.AddressModel;
import in.co.tripin.chahiyecustomer.Model.OrderItemModel;
import in.co.tripin.chahiyecustomer.Model.Requests.PlaceOrderRequestBody;
import in.co.tripin.chahiyecustomer.Model.responce.AddressResponse;
import in.co.tripin.chahiyecustomer.Model.responce.TapriMenuResponce;
import in.co.tripin.chahiyecustomer.Model.responce.TapriMenuResponses;
import in.co.tripin.chahiyecustomer.Model.responce.WalletResponse;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Constants;
import in.co.tripin.chahiyecustomer.helper.SharedPreferenceManager;
import in.co.tripin.chahiyecustomer.javacode.activity.AddAddressActivity;
import in.co.tripin.chahiyecustomer.javacode.activity.TapriDetailsActivity;
import in.co.tripin.chahiyecustomer.services.AddressService;
import in.co.tripin.chahiyecustomer.services.TapariService;
import in.co.tripin.chahiyecustomer.services.WalletService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

public class FavouriteTapri extends AppCompatActivity {

    private Spinner spinnerPayment,spinnerAddresss;
    ArrayList<String> paymentType;
    ArrayList<String> addressList;
    ArrayList<AddressModel> addressModelList;
    private ImageView imageViewMap;
    private LinearLayout linearQR;

    private TextView tvTeaCount, tvSugerFreeCount, tvCoffeeCount,tvAddAddress;
    private ImageView ivAddTea, ivRemoveTea, ivAddSugerFree, ivRemoveSugerFree, ivAddCoffee, ivRemoveCoffee;
    private TextView tvTotal;
    private TextView tvClearOrder, tvFullMenu, tvPlaceOrder, tvAddMoney,tvFavTapriName;
    private TextView tvTea,tvTeaSugerFree,tvCoffee;
    String teaId,teaSugerFreeId,coffeeId;
    int countTea = 0, countCoffee = 0, countSugerFree = 0;
    int total = 0;
    String teaRate,teaSugerFreeRate,coffeeRate;
    String balance;
    int walletBalance = 0;
    public static String FAV_TAPRI_ID="favTapri";
    public static String FAV_TAPRI_NAME="favTapriName";
    String paymentTypes ;
    String addressId;
    boolean firstTimeExecuted = false;

    PreferenceManager preferenceManager;
    List<OrderItemModel> orderItemModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_tapri);
        spinnerPayment = (Spinner) findViewById(R.id.spinnerPayment);
        imageViewMap = (ImageView) findViewById(R.id.imageViewMap);
        linearQR = (LinearLayout) findViewById(R.id.linearQR);
        preferenceManager = PreferenceManager.getInstance(this);
        tvTeaCount = (TextView) findViewById(R.id.textViewTeaCount);
        tvSugerFreeCount = (TextView) findViewById(R.id.textViewSugerFreeCount);
        tvCoffeeCount = (TextView) findViewById(R.id.textViewCoffeeCount);
        ivAddTea = (ImageView) findViewById(R.id.ivAddTea);
        ivRemoveTea = (ImageView) findViewById(R.id.ivRemoveTea);
        ivAddCoffee = (ImageView) findViewById(R.id.ivAddCoffee);
        ivRemoveCoffee = (ImageView) findViewById(R.id.ivRemoveCoffee);
        ivAddSugerFree = (ImageView) findViewById(R.id.ivAddSugerFree);
        ivRemoveSugerFree = (ImageView) findViewById(R.id.ivRemoveSugerFree);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvClearOrder = (TextView) findViewById(R.id.tvClearOrder);
        tvFullMenu = (TextView) findViewById(R.id.tvFullMenu);
        tvPlaceOrder = (TextView) findViewById(R.id.tvPlaceOrder);
        tvAddMoney = (TextView) findViewById(R.id.tvAddMoney);
        spinnerAddresss = (Spinner)findViewById(R.id.spinnerAddress);
        tvAddAddress = (TextView)findViewById(R.id.tvAddAddress);
        tvFavTapriName = (TextView) findViewById(R.id.tvFavTapriName);
        tvTea =(TextView)findViewById(R.id.tvTea);
        tvTeaSugerFree =(TextView)findViewById(R.id.tvTeaSugerFree);
        tvCoffee =(TextView)findViewById(R.id.tvCoffee);
        tvAddMoney.setVisibility(View.GONE);
        orderItemModelList = new ArrayList<>();


        addressList =  new ArrayList<>();
        getCurrentWallet();
        getAddress();
        getTapriMenu();
        Intent intent = getIntent();
        FAV_TAPRI_ID = preferenceManager.getFavTapriId();
        FAV_TAPRI_NAME = intent.getStringExtra(FAV_TAPRI_NAME);
        tvFavTapriName.setText(preferenceManager.getFavTapriName());
        //Log.d("FAV_TAPRI",FAV_TAPRI_ID);
        imageViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavouriteTapri.this, MainLandingMapActivity.class));
            }
        });
        linearQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavouriteTapri.this, QRCodeActivity.class));
            }
        });

        spinnerPayment.setSelection(0, false);

        spinnerPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int posi =spinnerPayment.getSelectedItemPosition();
                if(posi ==1)
                {
                    tvAddMoney.setVisibility(View.VISIBLE);
                }
                else
                {
                    tvAddMoney.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerAddresss.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                int posi =spinnerPayment.getSelectedItemPosition();
                if(firstTimeExecuted) {
                    if (i == 0) {
                        Intent intent = new Intent(FavouriteTapri.this, AddAddressActivity.class);
                        intent.putExtra(AddAddressActivity.FROM_FAV, "fromFav");
                        startActivity(intent);
                    }
                }
                else
                {
                    firstTimeExecuted = true;
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        ivAddTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countTea >= 0) {
                    countTea = countTea + 1;
                    tvTeaCount.setText(countTea + "");
                    total = total + Integer.parseInt(teaRate);
                    tvTotal.setText("TOTAL: " + total);

                }
            }
        });
        ivRemoveTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countTea > 0) {
                    countTea = countTea - 1;
                    tvTeaCount.setText(countTea + "");
                    total = total - Integer.parseInt(teaRate);
                    tvTotal.setText("TOTAL: " + total);
                }
            }
        });

        ivAddSugerFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countSugerFree >= 0) {
                    countSugerFree = countSugerFree + 1;
                    tvSugerFreeCount.setText(countSugerFree + "");
                    total = total + Integer.parseInt(teaSugerFreeRate);
                    tvTotal.setText("TOTAL: " + total);
                }
            }
        });
        ivRemoveSugerFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countSugerFree > 0) {
                    countSugerFree = countSugerFree - 1;
                    tvSugerFreeCount.setText(countSugerFree + "");
                    total = total - Integer.parseInt(teaSugerFreeRate);
                    tvTotal.setText("TOTAL: " + total);
                }
            }
        });

        ivAddCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countCoffee >= 0) {
                    countCoffee = countCoffee + 1;
                    tvCoffeeCount.setText(countCoffee + "");
                    total = total + Integer.parseInt(coffeeRate);
                    tvTotal.setText("TOTAL: " + total);
                }
            }
        });
        ivRemoveCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countCoffee > 0) {
                    countCoffee = countCoffee - 1;
                    tvCoffeeCount.setText(countCoffee + "");
                    total = total - Integer.parseInt(coffeeRate);
                    tvTotal.setText("TOTAL: " + total);
                }
            }
        });

        tvClearOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countTea = 0;
                countSugerFree = 0;
                countCoffee = 0;
                total = 0;
                tvTotal.setText("TOTAL: " + total);
                tvTeaCount.setText(countTea + "");
                tvCoffeeCount.setText(countCoffee + "");
                tvSugerFreeCount.setText(countSugerFree + "");
            }
        });

        tvFullMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FavouriteTapri.this, TapriDetailsActivity.class);
                i.putExtra("tapri_id", "5c07e402f25f9f00104ed0e1");
                i.putExtra("tapri_name", "Jack");
                startActivity(i);
            }
        });

        tvPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( spinnerPayment.getSelectedItemPosition() ==1)
                {
                    paymentTypes = "Wallet";
                }
                if(spinnerPayment.getSelectedItemPosition() ==2)
                {
                    paymentTypes = "COD";
                }
                if(spinnerPayment.getSelectedItemPosition() ==3)
                {
                    paymentTypes = "WOD";
                }

                if(total==0)
                {
                    Toast.makeText(FavouriteTapri.this, "Please select the items", Toast.LENGTH_SHORT).show();
                }
                else if (total > walletBalance) {
                    Toast.makeText(FavouriteTapri.this, "Please Recharge the Wallet", Toast.LENGTH_SHORT).show();
                }
                else if(spinnerPayment.getSelectedItemPosition()==0)
                {
                    Toast.makeText(FavouriteTapri.this, "Please select the payment mode", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(countTea>0)
                    {
                        orderItemModelList.add(new OrderItemModel(tvTea.getText().toString(),countTea*Integer.parseInt(teaRate),teaId,countTea));
                    }
                    if(countCoffee>0)
                    {
                        orderItemModelList.add(new OrderItemModel(tvCoffee.getText().toString(),countCoffee*Integer.parseInt(coffeeRate),coffeeId,countCoffee));
                    }
                    if(countSugerFree>0)
                    {
                        orderItemModelList.add(new OrderItemModel(tvTeaSugerFree.getText().toString(),countSugerFree*Integer.parseInt(teaSugerFreeRate),teaSugerFreeId,countSugerFree));

                    }

                    PlaceOrderRequestBody placeOrderRequestBody = new PlaceOrderRequestBody(preferenceManager.getFavTapriId(),paymentTypes,total,orderItemModelList,addressId);
                    Gson gson = new Gson();
                    String placeOrder = gson.toJson(placeOrderRequestBody);
                    Log.d("PlaceOrder",placeOrder);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    TapariService tapariService =retrofit.create(TapariService.class);
                    Call<PlaceOrderRequestBody> call = tapariService.toPlaceOrder(preferenceManager.getAccessToken(),placeOrderRequestBody);
                    call.enqueue(new Callback<PlaceOrderRequestBody>() {
                        @Override
                        public void onResponse(Call<PlaceOrderRequestBody> call, Response<PlaceOrderRequestBody> response) {
                            if(response.isSuccessful()) {
                                Toast.makeText(FavouriteTapri.this, "Order Placed Successfully...!!!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                try {
                                    Log.d("ERR",response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<PlaceOrderRequestBody> call, Throwable t) {
Log.d("FAIL",t.getMessage());
                        }
                    });




                }
            }
        });

        tvAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FavouriteTapri.this, WalletActivity.class);
                if(walletBalance<total){
                    Double tobeadded = Double.valueOf(total-walletBalance);
                    i.putExtra("money",""+tobeadded);
                }
                startActivity(i);
            }
        });
        tvAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FavouriteTapri.this,AddAddressActivity.class);
                intent.putExtra(AddAddressActivity.FROM_FAV,"fromFav");
                startActivity(intent);
            }
        });

    }

    public class CustomAdapter extends ArrayAdapter<String> implements SpinnerAdapter {
        Context context;
        ArrayList<String> paymentMode = new ArrayList<>();

        public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
            super(context, resource, objects);
            this.context = context;
            this.paymentMode = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


            View view = super.getView(position, convertView, parent);

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            textView.setGravity(Gravity.CENTER);
            Typeface typeface = ResourcesCompat.getFont(context, R.font.source_sans_pro_semibold);
            textView.setTypeface(typeface);
            return view;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            convertView = LayoutInflater.from(FavouriteTapri.this).inflate(
                    R.layout.custom_spinner, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setText(paymentMode.get(position));

            return convertView;
        }
    }


    public class CustomAddressAdapter extends ArrayAdapter<String> implements SpinnerAdapter {
        Context context;
        ArrayList<String> addressList = new ArrayList<>();


        public CustomAddressAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
            super(context, resource, objects);
            this.context = context;
            this.addressList = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


            View view = super.getView(position, convertView, parent);

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textView.setGravity(Gravity.CENTER);
            Typeface typeface = ResourcesCompat.getFont(context, R.font.source_sans_pro_semibold);
            textView.setTypeface(typeface);

                    textView.setText(addressList.get(position));

            return view;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            convertView = LayoutInflater.from(FavouriteTapri.this).inflate(
                    R.layout.custom_address_spinner, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.text);


                    textView.setText(addressList.get(position));

            return convertView;
        }
    }


    public void getCurrentWallet() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WalletService walletService = retrofit.create(WalletService.class);
        Call<WalletResponse> call = walletService.getWallet(preferenceManager.getAccessToken());
        Log.d("TOKEN",preferenceManager.getAccessToken());
        call.enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                if (response.isSuccessful()) {
                    WalletResponse walletResponse = response.body();
                    balance = walletResponse.getData().getBalance();
                    walletBalance = Integer.parseInt(balance);
                    Log.d("Balance", balance);

                    paymentType = new ArrayList<>();
                    paymentType.add("PAYMENT MODE");
                    paymentType.add("WALLET  ( Rs." + balance + " )");
                    paymentType.add("COD");
                    paymentType.add("WALLET AFTER DELIVERY");

                    CustomAdapter customAdapter = new CustomAdapter(FavouriteTapri.this, android.R.layout.simple_list_item_1, paymentType);
                    spinnerPayment.setAdapter(customAdapter);
                } else {
                    String err = String.valueOf(response.errorBody());
                    Log.d("ERR", err);

                }
            }

            @Override
            public void onFailure(Call<WalletResponse> call, Throwable t) {
                Log.d("Fail", t.getMessage());
            }
        });
    }

    public void getAddress()
    {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AddressService addressService = retrofit.create(AddressService.class);
        Call<AddressResponse> call = addressService.getAddress(preferenceManager.getAccessToken());
        call.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                if(response.isSuccessful())
                {

                    AddressResponse addressResponse = response.body();
                      addressList.add("ADD ADDRESS");
                    for(int i =0;i<addressResponse.getData().size();i++)
                    {
                        addressList.add(addressResponse.getData().get(i).getFullAddressString());
                        String fullAddress =  addressResponse.getData().get(i).getFullAddressString();
                        addressId = addressResponse.getData().get(i).get_id();
                       Log.d("Address",fullAddress);
                       String id = addressResponse.getData().get(i).get_id();
                    }

                    CustomAddressAdapter customAddressAdapter = new CustomAddressAdapter(FavouriteTapri.this, android.R.layout.simple_list_item_1, addressList);
                    spinnerAddresss.setAdapter(customAddressAdapter);

                }
                else
                {
                    String err = String.valueOf(response.errorBody());
                    Log.d("ERR", err);
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                Log.d("Fail", t.getMessage());
            }
        });

    }
    public void getTapriMenu()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TapariService tapariService = retrofit.create(TapariService.class);
        Call<TapriMenuResponses> call = tapariService.getTapriMenu(preferenceManager.getAccessToken(),preferenceManager.getFavTapriId());
        call.enqueue(new Callback<TapriMenuResponses>() {
            @Override
            public void onResponse(Call<TapriMenuResponses> call, Response<TapriMenuResponses> response) {

                if(response.isSuccessful())
                {
                    TapriMenuResponses tapriMenuResponses =response.body();
                    tvTea.setText(tapriMenuResponses.getData().getChaihiyeh().get(0).getName());
                    tvTeaSugerFree.setText(tapriMenuResponses.getData().getChaihiyeh().get(1).getName());
                    tvCoffee.setText(tapriMenuResponses.getData().getChaihiyeh().get(2).getName());
                    teaRate = tapriMenuResponses.getData().getChaihiyeh().get(0).getRate();
                     teaSugerFreeRate = tapriMenuResponses.getData().getChaihiyeh().get(1).getRate();
                     coffeeRate = tapriMenuResponses.getData().getChaihiyeh().get(2).getRate();
                     teaId =  tapriMenuResponses.getData().getChaihiyeh().get(0).get_id();
                    teaSugerFreeId =  tapriMenuResponses.getData().getChaihiyeh().get(1).get_id();
                    coffeeId =  tapriMenuResponses.getData().getChaihiyeh().get(2).get_id();



                }
                else
                {
                    try {
                        Log.d("ERR",response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TapriMenuResponses> call, Throwable t) {
Log.d("FAIL",t.getMessage());
            }
        });
    }


}
