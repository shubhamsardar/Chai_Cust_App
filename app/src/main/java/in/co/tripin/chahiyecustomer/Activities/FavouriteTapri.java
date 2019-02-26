package in.co.tripin.chahiyecustomer.Activities;

import android.content.Context;
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
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.co.tripin.chahiyecustomer.R;

import static java.security.AccessController.getContext;

public class FavouriteTapri extends AppCompatActivity {

    private Spinner spinnerPayment;
    ArrayList<String >paymentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_tapri);
        spinnerPayment = (Spinner)findViewById(R.id.spinnerPayment);

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
