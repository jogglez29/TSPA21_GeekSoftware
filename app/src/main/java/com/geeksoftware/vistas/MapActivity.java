package com.geeksoftware.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.geeksoftware.basedatos.SQLiteDataBase;
import com.geeksoftware.modelos.Parada;
import com.geeksoftware.modelos.Ruta;
import com.geeksoftware.presentadores.MapActivityPresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.geeksoftware.vistas.databinding.ActivityMapsBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, MainActivityView {
    SQLiteDataBase myDb; // Base de datos

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private MapActivityPresenter presentador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presentador = new MapActivityPresenter(this);
        checkLocationPermission();
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
                mapFragment.getMapAsync(MapActivity.this);
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
        presentador.cargarParadas();
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapActivity.this));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                String idParada = marker.getSnippet();
                marker.setSnippet("");
                presentador.cargarInfoParada(Integer.parseInt(idParada), marker);
                marker.showInfoWindow();
                marker.setSnippet(idParada);
                return true;
            }
        });
    }

    @Override
    public void mostrarParadas(List<Parada> listaParadas) {
        for(Parada parada : listaParadas) {
            int height = 128;
            int width = 128;
            BitmapDrawable bitmapdraw = (BitmapDrawable)getDrawable(R.drawable.ic_autobus);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
            LatLng ubicacionParada = new LatLng(parada.getLatitud(), parada.getLongitud());
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                    .position(ubicacionParada)
                    .title("Por aquí pasa"))
                    .setSnippet(String.valueOf(parada.getId()));
        }
    }

    @Override
    public void mostrarInfoParada(List<Ruta> listaRutas, Marker marcador) {
        marcador.setSnippet("");
        String rutas = "";
        for(Ruta ruta : listaRutas) {
            rutas += "\n" + ruta.getNombre();
        }
        marcador.setSnippet(rutas);
    }

    @Override
    public void mostrarErrorParadas() {
        Toast.makeText(this,"Ocurrió un error al mostrar las paradas de autobuses",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void mostrarErrorInfoParada() {
        Toast.makeText(this,"Ocurrió un error al mostrar la información" +
                " de la parada", Toast.LENGTH_LONG).show();
    }
}

