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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.javacode.activity.TapriDetailsActivity;

import static java.security.AccessController.getContext;

public class FavouriteTapri extends AppCompatActivity {

    private Spinner spinnerPayment;
    ArrayList<String >paymentType;
    private ImageView imageViewMap;
    private LinearLayout linearQR;

    private TextView tvTeaCount,tvSugerFreeCount,tvCoffeeCount;
    private  ImageView ivAddTea,ivRemoveTea,ivAddSugerFree,ivRemoveSugerFree,ivAddCoffee,ivRemoveCoffee;
    private TextView tvTotal;
    private TextView tvClearOrder, tvFullMenu;
    int countTea=0,countCoffee=0,countSugerFree=0;
    int total =0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_tapri);
        spinnerPayment = (Spinner)findViewById(R.id.spinnerPayment);
        imageViewMap = (ImageView)findViewById(R.id.imageViewMap);
        linearQR = (LinearLayout) findViewById(R.id.linearQR);

        tvTeaCount = (TextView)findViewById(R.id.textViewTeaCount);
        tvSugerFreeCount = (TextView)findViewById(R.id.textViewSugerFreeCount);
        tvCoffeeCount = (TextView)findViewById(R.id.textViewCoffeeCount);
        ivAddTea= (ImageView)findViewById(R.id.ivAddTea);
        ivRemoveTea= (ImageView)findViewById(R.id.ivRemoveTea);
        ivAddCoffee= (ImageView)findViewById(R.id.ivAddCoffee);
        ivRemoveCoffee= (ImageView)findViewById(R.id.ivRemoveCoffee);
        ivAddSugerFree= (ImageView)findViewById(R.id.ivAddSugerFree);
        ivRemoveSugerFree= (ImageView)findViewById(R.id.ivRemoveSugerFree);
        tvTotal = (TextView)findViewById(R.id.tvTotal);
        tvClearOrder = (TextView)findViewById(R.id.tvClearOrder);
        tvFullMenu = (TextView)findViewById(R.id.tvFullMenu);

        imageViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(FavouriteTapri.this,MainLandingMapActivity.class));
            }
        });
        linearQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavouriteTapri.this,QRCodeActivity.class));
            }
        });

        spinnerPayment.setSelection(0,false);

        paymentType= new ArrayList<>();
        paymentType.add("PAYMENT MODE");
        paymentType.add("WALLET");
        paymentType.add("COD");
        paymentType.add("WALLET AFTER DELIVERY");
        spinnerPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        CustomAdapter customAdapter = new CustomAdapter(this,android.R.layout.simple_list_item_1,paymentType);
        spinnerPayment.setAdapter(customAdapter);

        ivAddTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countTea>=0) {
                    countTea = countTea + 1;
                    tvTeaCount.setText(countTea + "");
                    total = total + 10;
                    tvTotal.setText("TOTAL: "+total);
                }
            }
        });
        ivRemoveTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countTea> 0){
                countTea = countTea - 1;
                tvTeaCount.setText(countTea+"");
                total = total -10;
                tvTotal.setText("TOTAL: "+total);
                }
            }
        });

        ivAddSugerFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countSugerFree>=0) {
                    countSugerFree = countSugerFree + 1;
                    tvSugerFreeCount.setText(countSugerFree + "");
                    total = total + 20;
                    tvTotal.setText("TOTAL: "+total);
                }
            }
        });
        ivRemoveSugerFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countSugerFree> 0){
                    countSugerFree = countSugerFree - 1;
                    tvSugerFreeCount.setText(countSugerFree+"");
                    total = total -20;
                    tvTotal.setText("TOTAL: "+total);
                }
            }
        });

        ivAddCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countCoffee>=0) {
                    countCoffee = countCoffee + 1;
                    tvCoffeeCount.setText(countCoffee + "");
                    total = total +30;
                    tvTotal.setText("TOTAL: "+total);
                }
            }
        });
        ivRemoveCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countCoffee> 0){
                    countCoffee = countCoffee - 1;
                    tvCoffeeCount.setText(countCoffee+"");
                    total = total -30;
                    tvTotal.setText("TOTAL: "+total);
                }
            }
        });

        tvClearOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countTea = 0;countSugerFree=0;countCoffee=0;total=0;
                tvTotal.setText("TOTAL: "+total);
                tvTeaCount.setText(countTea+"");
                tvCoffeeCount.setText(countCoffee+"");
                tvSugerFreeCount.setText(countSugerFree+"");
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



    }

    public class CustomAdapter extends ArrayAdapter<String> implements SpinnerAdapter {
        Context context;
        ArrayList<String > paymentMode = new ArrayList<>();

        public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
            super(context, resource, objects);
            this.context = context;
            this.paymentMode = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


            View view =  super.getView(position, convertView, parent);

            TextView textView = (TextView)view.findViewById(android.R.id.text1);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            textView.setGravity(Gravity.CENTER);
            Typeface typeface = ResourcesCompat.getFont(context, R.font.source_sans_pro_semibold);
            textView.setTypeface(typeface);
            return view;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            convertView = LayoutInflater.from(FavouriteTapri.this).inflate(
                    R.layout.custom_spinner, parent, false);
            TextView textView = (TextView)convertView.findViewById(R.id.text);
            textView.setText(paymentMode.get(position));

            return convertView;
        }
    }
}
