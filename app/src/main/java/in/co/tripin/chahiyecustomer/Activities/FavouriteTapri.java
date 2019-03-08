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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.Model.responce.WalletResponse;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Constants;
import in.co.tripin.chahiyecustomer.helper.SharedPreferenceManager;
import in.co.tripin.chahiyecustomer.javacode.activity.TapriDetailsActivity;
import in.co.tripin.chahiyecustomer.services.WalletService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

public class FavouriteTapri extends AppCompatActivity {

    private Spinner spinnerPayment;
    ArrayList<String> paymentType;
    private ImageView imageViewMap;
    private LinearLayout linearQR;

    private TextView tvTeaCount, tvSugerFreeCount, tvCoffeeCount;
    private ImageView ivAddTea, ivRemoveTea, ivAddSugerFree, ivRemoveSugerFree, ivAddCoffee, ivRemoveCoffee;
    private TextView tvTotal;
    private TextView tvClearOrder, tvFullMenu, tvPlaceOrder, tvAddMoney;
    int countTea = 0, countCoffee = 0, countSugerFree = 0;
    int total = 0;
    String balance;
    int walletBalance = 0;

    PreferenceManager preferenceManager;


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

        getCurrentWallet();
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
                    total = total + 10;
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
                    total = total - 10;
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
                    total = total + 20;
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
                    total = total - 20;
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
                    total = total + 30;
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
                    total = total - 30;
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
                if (total > walletBalance) {
                    Toast.makeText(FavouriteTapri.this, "Please Recharge the Wallet", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FavouriteTapri.this, "Order Placed Successfully...!!!", Toast.LENGTH_SHORT).show();
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

    public void getCurrentWallet() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WalletService walletService = retrofit.create(WalletService.class);
        Call<WalletResponse> call = walletService.getWallet(preferenceManager.getAccessToken());
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


}
