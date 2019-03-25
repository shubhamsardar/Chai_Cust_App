package in.co.tripin.chahiyecustomer.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import dmax.dialog.SpotsDialog;
import in.co.tripin.chahiyecustomer.Adapters.InfoWindowCustom;
import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.Managers.TapriManager;
import in.co.tripin.chahiyecustomer.Model.responce.Tapri;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Logger;
import in.co.tripin.chahiyecustomer.javacode.activity.EditPinActivity;
import in.co.tripin.chahiyecustomer.javacode.activity.OTPForChangePINActivity;
import in.co.tripin.chahiyecustomer.javacode.activity.OrderHistoryActivity;
import in.co.tripin.chahiyecustomer.javacode.activity.SelectAddressActivity;
import in.co.tripin.chahiyecustomer.javacode.activity.TapriDetailsActivity;

public class MainLandingMapActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    private static final LatLngBounds BOUNDS_INDIA =
            new LatLngBounds(new LatLng(19.052027, 72.835055),
                    new LatLng(19.060195, 72.852520));
    private static final int MAP_PADDING = 50;

    private DrawerLayout mDrawerLayout;
    private GoogleMap map;
    private MapView mapView;
    private TapriManager mTapriManager;
    private PreferenceManager preferenceManager;
    private AlertDialog dialog;
    private Context mContext;

    static final int REQUEST_CODE_ASK_PERMISSIONS = 1002;

    private FusedLocationProviderClient mFusedLocationClient;


    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_landing_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mContext = this;
        mTapriManager = new TapriManager(this);
        setSupportActionBar(toolbar);
        preferenceManager = PreferenceManager.getInstance(this);
        setTitle("Your Location");
        Objects.requireNonNull(getSupportActionBar()).setSubtitle("0 Results");
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .setMessage("Loading")
                .build();
        checkPermissions();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Local Offers.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set up map
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }


    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_landing_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_setlocation) {
            LaunchPlacePickerFragment();
        } else if (id == R.id.action_currentlocation) {
            FetchFromCurrectLocation();
        }

        return super.onOptionsItemSelected(item);
    }

    private void FetchFromCurrectLocation() {
        dialog.show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Permissions Not Granted", Toast.LENGTH_LONG).show();
            checkPermissions();
        } else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            dialog.dismiss();
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    String cityName = addresses.get(0).getAddressLine(0);
                                    if (cityName != null) {
                                        setTitle(cityName);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                FetchTapriList(new LatLng(location.getLatitude(), location.getLongitude()));

                            } else {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Select a location!", Toast.LENGTH_LONG).show();
                            }
                        }


                    });
        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_QRCode) {

            startActivity(new Intent(MainLandingMapActivity.this, QRCodeActivity.class));

        }

        else if (id == R.id.nav_history) {

            startActivity(new Intent(MainLandingMapActivity.this, OrderHistoryActivity.class));

        } else if (id == R.id.nav_wallet) {

            //open Wallet Activity
            startActivity(new Intent(MainLandingMapActivity.this, WalletActivity.class));

        } else if (id == R.id.nav_changepin) {

            Intent intent = new Intent(MainLandingMapActivity.this, EditPinActivity.class);
            intent.putExtra("mobile", preferenceManager.getMobileNo());
            startActivity(intent);

        } else if (id == R.id.nav_logout) {

            preferenceManager.setUserId(null);
            preferenceManager.setMobileNo(null);
            preferenceManager.setAccessToken(null);
            preferenceManager.setFavTapriId(null);
            preferenceManager.setFavTapriName(null);
            preferenceManager.setFavTapriMobile(null);
            startActivity(new Intent(MainLandingMapActivity.this, SplashActivity.class));
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
            //call
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);

        FetchFromCurrectLocation();


    }

    private void animateCamera(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)      // Sets the center of the map to location user
                .zoom(18)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void FetchTapriList(final LatLng latLng) {

        dialog.show();
        mTapriManager.getTupriList(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude),
                new TapriManager.TapriListListener() {
                    @Override
                    public void onSuccess(Tapri.Data[] tapriData) {
                        try {
                            if (tapriData != null) {

                                map.clear();
                                animateCamera(latLng);
                                Objects.requireNonNull(getSupportActionBar()).setSubtitle(tapriData.length + " Tapris Found");

                                for (final Tapri.Data data : tapriData) {
                                    String[] location = data.getLocation().getCoordinates();
                                    double lat = Double.parseDouble(location[1].trim());
                                    double lng = Double.parseDouble(location[0].trim());
                                    Logger.v("PointAdded : " + data.getName());

                                    LatLng point = new LatLng(lat, lng);

                                    MarkerOptions markerOptions = new MarkerOptions();
                                    markerOptions.position(point)
                                            .title(data.getName())
                                            .snippet(data.get_id())
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                                    map.addMarker(markerOptions);

                                    map.setInfoWindowAdapter(new InfoWindowCustom(MainLandingMapActivity.this));

                                    map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                        @Override
                                        public void onInfoWindowClick(Marker marker) {
                                            Intent i = new Intent(MainLandingMapActivity.this, TapriDetailsActivity.class);
                                            i.putExtra("tapri_id", marker.getSnippet());
                                            i.putExtra("tapri_name", marker.getTitle());

                                            Logger.v("Tapri Id Opened : " + marker.getSnippet());

                                            startActivity(i);
                                        }
                                    });
                                }

                            }
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailed(String message) {
                        dialog.dismiss();

                    }
                });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Logger.v("Place: " + place.getName());
                setTitle(place.getName());
                MarkerOptions markerOptions = new MarkerOptions();
                animateCamera(place.getLatLng());
                FetchTapriList(place.getLatLng());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Logger.v("PlaceError: " + status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


    private void LaunchPlacePickerFragment() {

        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                    .setCountry("IN")
                    .build();

            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .setFilter(typeFilter)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }

    }

    protected void checkPermissions() {
        Log.d("checkPermissions", "Inside");
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            Log.d("checkPermissions", "missingPermissions is not empty");
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions,
                    REQUEST_CODE_ASK_PERMISSIONS);
        } else {
//            Logger.v(" premissions already granted ");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        Log.d("onRequestPermissions", "Inside");
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
//                Logger.v("all premissions granted from dialog");
                break;
        }
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
