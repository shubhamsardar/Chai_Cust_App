package in.co.tripin.chahiyecustomer.Activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.Model.AddressModel;
import in.co.tripin.chahiyecustomer.Model.OrderItemModel;
import in.co.tripin.chahiyecustomer.Model.Requests.PlaceOrderRequestBody;
import in.co.tripin.chahiyecustomer.Model.responce.AddressResponse;
import in.co.tripin.chahiyecustomer.Model.responce.TapriMenuResponses;
import in.co.tripin.chahiyecustomer.Model.responce.WalletResponse;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Constants;
import in.co.tripin.chahiyecustomer.javacode.activity.AddAddressActivity;
import in.co.tripin.chahiyecustomer.javacode.activity.EditPinActivity;
import in.co.tripin.chahiyecustomer.javacode.activity.OrderHistoryActivity;
import in.co.tripin.chahiyecustomer.javacode.activity.TapriDetailsActivity;
import in.co.tripin.chahiyecustomer.services.AddressService;
import in.co.tripin.chahiyecustomer.services.TapariService;
import in.co.tripin.chahiyecustomer.services.WalletService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavouriteTapriNew extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Spinner spinnerPayment, spinnerAddresss;
    ArrayList<String> paymentType;
    ArrayList<String> addressList;
    ArrayList<String> addressIdList;
    ArrayList<AddressModel> addressModelList;
    private ImageView imageViewMap;
    private LinearLayout linearQR;

    private TextView tvUsername , tvUserMobile;
    private TextView tvTeaCount, tvSugerFreeCount, tvCoffeeCount, textViewMobile;
    private ImageView ivAddTea, ivRemoveTea, ivAddSugerFree, ivRemoveSugerFree, ivAddCoffee, ivRemoveCoffee;
    private TextView tvTotal;
    private TextView tvClearOrder, tvFullMenu, tvPlaceOrder, tvAddMoney, tvFavTapriName,tvOrderHistory;
    private TextView tvTea, tvTeaSugerFree, tvCoffee;
    String teaId, teaSugerFreeId, coffeeId;
    int countTea = 0, countCoffee = 0, countSugerFree = 0;
    int total = 0;
    String teaRate, teaSugerFreeRate, coffeeRate;
    String balance;
    float walletBalance = 0;
    public static String FAV_TAPRI_ID = "favTapri";
    public static String FAV_TAPRI_NAME = "favTapriName";
    public static String IS_CREDIT = "isCredit";
    boolean isCreditPaymentEnabled;
    String paymentTypes;
    String addressId;

    int check = 0;
    private Context mContext;
    PreferenceManager preferenceManager;
    List<OrderItemModel> orderItemModelList;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_tapri_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView =(NavigationView)findViewById(R.id.nav_view_fav_tap);
        mContext = this;
        spinnerPayment = (Spinner) findViewById(R.id.spinnerPayment);
        imageViewMap = (ImageView) findViewById(R.id.imageViewMap);
        linearQR = (LinearLayout) findViewById(R.id.linearQR);
        preferenceManager = PreferenceManager.getInstance(this);
        tvTeaCount = (TextView) findViewById(R.id.textViewTeaCount);
        tvSugerFreeCount = (TextView) findViewById(R.id.textViewSugerFreeCount);
        tvCoffeeCount = (TextView) findViewById(R.id.textViewCoffeeCount);
        ivAddTea = (ImageView) findViewById(R.id.ivAddTea);
        textViewMobile = findViewById(R.id.textViewMobile);
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
        spinnerAddresss = (Spinner) findViewById(R.id.spinnerAddress);
        tvFavTapriName = (TextView) findViewById(R.id.tvFavTapriName);
        tvTea = (TextView) findViewById(R.id.tvTea);
        tvTeaSugerFree = (TextView) findViewById(R.id.tvTeaSugerFree);
        tvCoffee = (TextView) findViewById(R.id.tvCoffee);
        tvAddMoney.setVisibility(View.GONE);

        //orderItemModelList = new ArrayList<>();



        View headerView = navigationView.getHeaderView(0);
        tvUsername = (TextView)headerView.findViewById(R.id.tvUsername);
        tvUserMobile = (TextView)headerView.findViewById(R.id.tvUserMobile);

        tvUsername.setText(preferenceManager.getUserName());
        tvUserMobile.setText(preferenceManager.getMobileNo());

        addressList = new ArrayList<>();
        addressIdList = new ArrayList<>();
        getCurrentWallet();
        getAddress();
        getTapriMenu();
        Intent intent = getIntent();
        FAV_TAPRI_ID = preferenceManager.getFavTapriId();
        FAV_TAPRI_NAME = intent.getStringExtra(FAV_TAPRI_NAME);
        isCreditPaymentEnabled= intent.getBooleanExtra(IS_CREDIT,false);
        tvFavTapriName.setText(preferenceManager.getFavTapriName());
        textViewMobile.setText(preferenceManager.getFavTapriMobile());

        textViewMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + textViewMobile.getText()));
                startActivity(intent);
            }
        });

        //tvFavTapriName.setText("Jack");
        //Log.d("FAV_TAPRI",FAV_TAPRI_ID);
        imageViewMap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavouriteTapriNew.this, MainLandingMapActivity.class));
            }
        });
        linearQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(FavouriteTapriNew.this, QRCodeActivity.class), 0);
            }
        });

        spinnerPayment.setSelection(0, false);

        spinnerPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int posi = spinnerPayment.getSelectedItemPosition();
                if (posi == 1) {
                    tvAddMoney.setVisibility(View.VISIBLE);
                } else {
                    tvAddMoney.setVisibility(View.GONE);
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
                clearOrder();
            }
        });

        tvFullMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FavouriteTapriNew.this, TapriDetailsActivity.class);
                i.putExtra("tapri_id", "5c07e402f25f9f00104ed0e1");
                i.putExtra("tapri_name", "Jack");
                startActivity(i);
            }
        });

        tvPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (spinnerPayment.getSelectedItemPosition() == 1) {
                    paymentTypes = "Wallet";
                }
                if (spinnerPayment.getSelectedItemPosition() == 2) {
                    paymentTypes = "COD";
                }
                if (spinnerPayment.getSelectedItemPosition() == 3) {
                    paymentTypes = "WOD";
                }
                if (spinnerPayment.getSelectedItemPosition() == 4) {
                    paymentTypes = "Credit";
                }
                if (total == 0) {
                    Toast.makeText(FavouriteTapriNew.this, "Please select the items", Toast.LENGTH_SHORT).show();
                } else if (spinnerPayment.getSelectedItemPosition() == 0) {
                    Toast.makeText(FavouriteTapriNew.this, "Please select the payment mode", Toast.LENGTH_SHORT).show();
                } else if (total > walletBalance && paymentTypes.matches("Wallet")) {
                    Toast.makeText(FavouriteTapriNew.this, "Please Recharge the Wallet", Toast.LENGTH_SHORT).show();
                } else if (spinnerAddresss.getSelectedItemPosition() == addressList.size() - 1) {
                    Toast.makeText(FavouriteTapriNew.this, "Please select the Address", Toast.LENGTH_SHORT).show();
                } else {
                    orderItemModelList = new ArrayList<>();
                    if (countTea > 0) {
                        orderItemModelList.add(new OrderItemModel(tvTea.getText().toString(), countTea * Integer.parseInt(teaRate), teaId, countTea));
                    }
                    if (countCoffee > 0) {
                        orderItemModelList.add(new OrderItemModel(tvCoffee.getText().toString(), countCoffee * Integer.parseInt(coffeeRate), coffeeId, countCoffee));
                    }
                    if (countSugerFree > 0) {
                        orderItemModelList.add(new OrderItemModel(tvTeaSugerFree.getText().toString(), countSugerFree * Integer.parseInt(teaSugerFreeRate), teaSugerFreeId, countSugerFree));

                    }
                    if (addressIdList != null) {

                        int pos = spinnerAddresss.getSelectedItemPosition();
                        addressId = addressIdList.get(pos);
                    }

                    PlaceOrderRequestBody placeOrderRequestBody = new PlaceOrderRequestBody(preferenceManager.getFavTapriId(), paymentTypes, total, orderItemModelList, addressId);
                    Gson gson = new Gson();
                    String placeOrder = gson.toJson(placeOrderRequestBody);
                    Log.d("PlaceOrder", placeOrder);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    TapariService tapariService = retrofit.create(TapariService.class);
                    Call<PlaceOrderRequestBody> call = tapariService.toPlaceOrder(preferenceManager.getAccessToken(), placeOrderRequestBody);
                    call.enqueue(new Callback<PlaceOrderRequestBody>() {
                        @Override
                        public void onResponse(Call<PlaceOrderRequestBody> call, Response<PlaceOrderRequestBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(FavouriteTapriNew.this, "Order Placed Successfully...!!!", Toast.LENGTH_SHORT).show();
                                clearOrder();
                                getCurrentWallet();

                            } else {
                                try {
                                    Log.d("ERR", response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<PlaceOrderRequestBody> call, Throwable t) {
                            Log.d("FAIL", t.getMessage());
                        }
                    });


                }
            }
        });

        tvAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FavouriteTapriNew.this, WalletActivity.class);
                if (walletBalance < total) {
                    Double tobeadded = Double.valueOf(total - walletBalance);
                    i.putExtra("money", "" + tobeadded);
                }
                startActivity(i);
            }
        });

//        tvOrderHistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(FavouriteTapriNew.this, OrderHistoryActivity.class));
//            }
//        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.favourite_tapri_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_QRCode) {

            startActivity(new Intent(FavouriteTapriNew.this, QRCodeActivity.class));

        }

        else if (id == R.id.nav_history) {

            startActivity(new Intent(FavouriteTapriNew.this, OrderHistoryActivity.class));

        } else if (id == R.id.nav_wallet) {

            //open Wallet Activity
            startActivity(new Intent(FavouriteTapriNew.this, WalletActivity.class));

        } else if (id == R.id.nav_changepin) {

            Intent intent = new Intent(FavouriteTapriNew.this, EditPinActivity.class);
            intent.putExtra("mobile", preferenceManager.getMobileNo());
            startActivity(intent);

        } else if (id == R.id.nav_logout) {

            preferenceManager.setUserId(null);
            preferenceManager.setMobileNo(null);
            preferenceManager.setAccessToken(null);
            preferenceManager.setFavTapriId(null);
            preferenceManager.setFavTapriName(null);
            preferenceManager.setFavTapriMobile(null);
            startActivity(new Intent(FavouriteTapriNew.this, SplashActivity.class));
            finish();


        } else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "Check it out. Waah Chai Android App!";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
        } else if (id == R.id.nav_rate) {
            rateApp();
        } else if (id == R.id.nav_call) {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + "02228907966"));
            startActivity(intent);

        } else if (id == R.id.nav_web) {
            Uri uri = Uri.parse("http://www.waahchai.in"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
    }
        else if (id == R.id.nav_terms) {
            Uri uri = Uri.parse("http://waahchai.in/t-c.html"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        else if (id == R.id.nav_privacy) {
            Uri uri = Uri.parse("http://waahchai.in/privacy-policy.html"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            convertView = LayoutInflater.from(FavouriteTapriNew.this).inflate(
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
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            textView.setGravity(Gravity.CENTER);
            textView.setMaxLines(1);
            Typeface typeface = ResourcesCompat.getFont(context, R.font.source_sans_pro_semibold);
            textView.setTypeface(typeface);

            textView.setText(addressList.get(position));
            textView.setEllipsize(TextUtils.TruncateAt.END);

            return view;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            convertView = LayoutInflater.from(FavouriteTapriNew.this).inflate(
                    R.layout.custom_address_spinner, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.text);

            textView.setText(addressList.get(position));

            if (position == addressList.size() - 1) {
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(FavouriteTapriNew.this, AddAddressActivity.class);
                        intent.putExtra(AddAddressActivity.FROM_FAV, "fromFav");
                        startActivity(intent);
                        spinnerAddresss.setSelection(addressList.size() - 1, true);
                    }
                });
            }

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
        Log.d("TOKEN", preferenceManager.getAccessToken());
        call.enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                if (response.isSuccessful()) {
                    WalletResponse walletResponse = response.body();
                    balance = walletResponse.getData().getBalance();
                    walletBalance = Float.parseFloat(balance);
                    Log.d("Balance", balance);

                    paymentType = new ArrayList<>();
                    paymentType.add("PAYMENT MODE");
                    paymentType.add("WALLET  ( Rs." + balance + " )");
                    paymentType.add("COD");
                    paymentType.add("WALLET AFTER DELIVERY");
                    if(preferenceManager.getIsCreditPaymentEnabled()) {
                        paymentType.add("CREDIT");
                    }

                    FavouriteTapriNew.CustomAdapter customAdapter = new FavouriteTapriNew.CustomAdapter(FavouriteTapriNew.this, android.R.layout.simple_list_item_1, paymentType);
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

    public void getAddress() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AddressService addressService = retrofit.create(AddressService.class);
        Call<AddressResponse> call = addressService.getAddress(preferenceManager.getAccessToken());
        call.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                if (response.isSuccessful()) {

                    AddressResponse addressResponse = response.body();

                    for (int i = 0; i < addressResponse.getData().size(); i++) {
                        addressList.add(addressResponse.getData().get(i).getFullAddressString());
                        addressIdList.add(addressResponse.getData().get(i).get_id());
                        String fullAddress = addressResponse.getData().get(i).getFullAddressString();
                        //addressId = addressResponse.getData().get(i).get_id();
                        //Log.d("Address", fullAddress);
                        String id = addressResponse.getData().get(i).get_id();
                    }
                    addressList.add("ADD ADDRESS");
                    addressIdList.add("Dummy");

                    FavouriteTapriNew.CustomAddressAdapter customAddressAdapter = new FavouriteTapriNew.CustomAddressAdapter(FavouriteTapriNew.this, android.R.layout.simple_list_item_1, addressList);
                    spinnerAddresss.setAdapter(customAddressAdapter);
                    spinnerAddresss.setSelection(addressList.size() - 1);

                } else {
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

    public void getTapriMenu() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TapariService tapariService = retrofit.create(TapariService.class);
        Call<TapriMenuResponses> call = tapariService.getTapriMenu(preferenceManager.getAccessToken(), preferenceManager.getFavTapriId());
        call.enqueue(new Callback<TapriMenuResponses>() {
            @Override
            public void onResponse(Call<TapriMenuResponses> call, Response<TapriMenuResponses> response) {

                if (response.isSuccessful()) {
                    TapriMenuResponses tapriMenuResponses = response.body();
                    tvTea.setText(tapriMenuResponses.getData().getChaihiyeh().get(0).getName());
                    tvTeaSugerFree.setText(tapriMenuResponses.getData().getChaihiyeh().get(1).getName());
                    tvCoffee.setText(tapriMenuResponses.getData().getChaihiyeh().get(2).getName());
                    teaRate = tapriMenuResponses.getData().getChaihiyeh().get(0).getRate();
                    teaSugerFreeRate = tapriMenuResponses.getData().getChaihiyeh().get(1).getRate();
                    coffeeRate = tapriMenuResponses.getData().getChaihiyeh().get(2).getRate();
                    teaId = tapriMenuResponses.getData().getChaihiyeh().get(0).get_id();
                    teaSugerFreeId = tapriMenuResponses.getData().getChaihiyeh().get(1).get_id();
                    coffeeId = tapriMenuResponses.getData().getChaihiyeh().get(2).get_id();


                } else {
                    try {
                        Log.d("ERR", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TapriMenuResponses> call, Throwable t) {
                Log.d("FAIL", t.getMessage());
            }
        });
    }

    public void clearOrder() {
        countTea = 0;
        countSugerFree = 0;
        countCoffee = 0;
        total = 0;
        tvTotal.setText("TOTAL: " + total);
        tvTeaCount.setText(countTea + "");
        tvCoffeeCount.setText(countCoffee + "");
        tvSugerFreeCount.setText(countSugerFree + "");
        spinnerPayment.setSelection(0);
        spinnerAddresss.setSelection(addressList.size() - 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getCurrentWallet();
        clearOrder();
    }

    void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + mContext.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + mContext.getPackageName())));
        }
    }
}
