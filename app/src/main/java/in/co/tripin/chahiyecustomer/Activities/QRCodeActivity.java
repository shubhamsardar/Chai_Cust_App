package in.co.tripin.chahiyecustomer.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.print.PrintAttributes;
import android.print.pdf.PrintedPdfDocument;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.R;

public class QRCodeActivity extends AppCompatActivity {

    //VIEWS
    private ImageView imageViewQRCode;
    private TextView textViewUserName;
    private View linearLayoutQR;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferenceManager = PreferenceManager.getInstance(this);
        imageViewQRCode = (ImageView) findViewById(R.id.imageViewQRCode);
        textViewUserName = (TextView) findViewById(R.id.textViewUsername);
        linearLayoutQR = findViewById(R.id.linearLayoutQR);
        textViewUserName.setText(preferenceManager.getUserName());
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(preferenceManager.getMobileNo(), BarcodeFormat.QR_CODE, 400, 400);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageViewQRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        setResult(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_qr_code, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.pdf:
                downloadQRCode();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void downloadQRCode() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        } else {

            PrintAttributes printAttributes = (new PrintAttributes.Builder())
                    .setMediaSize(PrintAttributes.MediaSize.ISO_A3)
                    .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                    .setColorMode(PrintAttributes.COLOR_MODE_MONOCHROME)
                    .build();

            PrintedPdfDocument document = new PrintedPdfDocument(this, printAttributes);

            // start a page
            PdfDocument.Page page = document.startPage(0);

            // draw something on the page
            //imageViewQRCode.getImageMatrix().setScale(1.1f, 1.1f);
            View content = linearLayoutQR;
            //textViewUserName.setTextScaleX(0.9f);
            content.draw(page.getCanvas());
            //textViewUserName.setTextScaleX(0.9f);

            // finish the page
            document.finishPage(page);

            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String filePath = file.getAbsolutePath();
            File pdfFile = new File(filePath, "WaahChai.pdf");
            try {
                FileOutputStream fos = new FileOutputStream(pdfFile);
                document.writeTo(fos);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //close the document
            document.close();

            Intent myIntent = new Intent(Intent.ACTION_VIEW);
            myIntent.setData(Uri.fromFile(pdfFile));
            Intent j = Intent.createChooser(myIntent, "Choose an application to open with:");
            startActivity(j);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        downloadQRCode();
    }
}
