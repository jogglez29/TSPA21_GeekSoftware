package com.geeksoftware.bussify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.geeksoftware.bussify.databinding.ActivityMapsBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    DatabaseHelper myDb; // Base de datos

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLocationPermission();
        myDb = new DatabaseHelper(this);
        myDb.registrarParadas();
        myDb.registrarRutas();
        myDb.registrarParadaRutas();
    }

    private void checkLocationPermission() {

        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                binding = ActivityMapsBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());

                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(MapsActivity.this);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package",getPackageName(),"");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng zacGpe = new LatLng(22.76424926, -102.5482729);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zacGpe,15.5f));

        mMap.setMyLocationEnabled(true);
        mostrarParadas(googleMap);
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                String idParada = marker.getSnippet();
                mostrarInfoParada(marker,marker.getSnippet());
                marker.showInfoWindow();
                marker.setSnippet(idParada);
                return true;
            }
        });
    }

    public void mostrarParadas(GoogleMap googleMap){
        Cursor res = myDb.getAllDataParadas();
        if (res == null){ // Verificar si hay paradas
            Toast.makeText(this,"Ocurrió un error al mostrar las paradas de autobuses",
                    Toast.LENGTH_LONG).show();
        } else {
            if(res.getCount() == 0) {
                Toast.makeText(this,"No hay paradas registradas",
                        Toast.LENGTH_LONG).show();
            } else {
                while(res.moveToNext()){
                    int height = 128;
                    int width = 128;
                    BitmapDrawable bitmapdraw = (BitmapDrawable)getDrawable(R.drawable.ic_autobus);
                    Bitmap b = bitmapdraw.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                    LatLng parada = new LatLng(res.getDouble(2), res.getDouble(3));
                    googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                            .position(parada)
                            .title("Por aquí pasa"))
                            .setSnippet(res.getString(0));
                }
            }
        }
    }

    public void mostrarInfoParada(Marker marker, String idParada) {
        String listaRutas = "";
        Cursor rutas = myDb.getAllDataRutas(Integer.parseInt(idParada));
        if(rutas == null) {
            Toast.makeText(this,"Ocurrió un error al mostrar la información" +
                    " de la parada", Toast.LENGTH_LONG).show();
            marker.setSnippet("");
        } else {
            while (rutas.moveToNext()){
                listaRutas += "\n" + rutas.getString(3);
            }
            marker.setSnippet(listaRutas);
        }
    }
}

