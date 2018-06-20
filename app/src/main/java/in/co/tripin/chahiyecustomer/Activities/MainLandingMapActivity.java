package in.co.tripin.chahiyecustomer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import in.co.tripin.chahiyecustomer.Adapters.InfoWindowCustom;
import in.co.tripin.chahiyecustomer.Managers.PreferenceManager;
import in.co.tripin.chahiyecustomer.Managers.TapriManager;
import in.co.tripin.chahiyecustomer.Model.responce.Tapri;
import in.co.tripin.chahiyecustomer.R;
import in.co.tripin.chahiyecustomer.helper.Logger;
import in.co.tripin.chahiyecustomer.javacode.activity.SelectAddressActivity;
import in.co.tripin.chahiyecustomer.javacode.activity.TapriDetailsActivity;

public class MainLandingMapActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,OnMapReadyCallback {
    private static final LatLngBounds BOUNDS_INDIA =
            new LatLngBounds(new LatLng(19.052027, 72.835055),
                    new LatLng(19.060195, 72.852520));
    private static final int MAP_PADDING = 50;

    private FloatingSearchView mSearchView;
    private DrawerLayout mDrawerLayout;
    private GoogleMap map;
    private MapView mapView;
    private TapriManager mTapriManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_landing_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mSearchView = findViewById(R.id.floating_search_view);
        mTapriManager = new TapriManager(this);
        setSupportActionBar(toolbar);


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
        mSearchView.attachNavigationDrawerToMenuButton(drawer);


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
            super.onBackPressed();
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

        if (id == R.id.nav_history) {
            // Handle the camera action
        } else if (id == R.id.nav_address) {

            startActivity(new Intent(MainLandingMapActivity.this, SelectAddressActivity.class));

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_logout) {
            PreferenceManager preferenceManager = PreferenceManager.getInstance(MainLandingMapActivity.this);

            if (preferenceManager.isLogin()) {
                preferenceManager.setUserId(null);
                Toast.makeText(MainLandingMapActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        startActivity(new Intent(MainLandingMapActivity.this, SplashActivity.class));
                    }
                }, 3000);
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng tripin = new LatLng(19.117418, 72.856531);

        mTapriManager.getTupriList("19.1127517", "72.8311449", new TapriManager.TapriListListener() {
            @Override
            public void onSuccess(Tapri.Data[] tapriData) {
                try {
                    if (tapriData != null) {

                        for (final Tapri.Data data : tapriData) {
                            String[] location = data.getLocation().getCoordinates();
                            double lat = Double.parseDouble(location[1].trim());
                            double lng = Double.parseDouble(location[0].trim());
                            Log.v("Point", "Added : " + location.toString());

                            LatLng point = new LatLng(lat, lng);

                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(point)
                                    .title(data.getName())
                                    .icon(BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_ORANGE));
                            Marker m = map.addMarker(markerOptions);
                            map.setInfoWindowAdapter(new InfoWindowCustom(MainLandingMapActivity.this));
                            map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(Marker marker) {
                                    Intent i = new Intent(MainLandingMapActivity.this,TapriDetailsActivity.class);
                                    i.putExtra("tapri_id","5af56383817a5925ed444773");
                                    i.putExtra("tapri_name",data.getName());
                                    Logger.v("Tapri Id Opened : "+data.get_id());
                                    startActivity(i);
                                }
                            });
                        }

                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(String message) {

            }
        });

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(tripin)      // Sets the center of the map to location user
                .zoom(18)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(tripin)
//                .title("Dubey Tapriwala")
//                .snippet("2.0 Km.")
//                .icon(BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_ORANGE));
//        Marker m = map.addMarker(markerOptions);
//        map.setInfoWindowAdapter(new InfoWindowCustom(this));
//        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                startActivity(new Intent(MainLandingMapActivity.this,TapriDetailsActivity.class));
//            }
//        });

    }
}
