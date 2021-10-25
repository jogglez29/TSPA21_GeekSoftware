package com.geeksoftware.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.geeksoftware.modelos.Parada;
import com.geeksoftware.modelos.Ruta;
import com.geeksoftware.presentadores.MapActivityPresenter;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.geeksoftware.vistas.databinding.ActivityMapsBinding;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Arrays;
import java.util.List;

/**
 * Clase de la actividad principal donde se muestra el mapa y sobre él
 * las paradas de autobuses.
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, MapActivityView {

    /** Mapa de Google sobre el que se muestran las paradas de autobuses. */
    private GoogleMap mMap;
    /** Vista del mapa */
    private View mapView;
    private ActivityMapsBinding binding;
    /** Enlace de la vista con el procesamiento de la lógica de negocio y los datos. */
    private MapActivityPresenter presentador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presentador = new MapActivityPresenter(this);
        checkLocationPermission();

        // Se obtiene el SupportMapFragment y se notifica cuando el mapa está
        // listo para usarse.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);

        String apiKey = getResources().getString(R.string.google_maps_key);
        // Initialize the SDK
        Places.initialize(getApplicationContext(), apiKey);
        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(this);


        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.getView().setBackgroundColor(Color.WHITE);


        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(
                Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                System.out.println("DESTINO:" +  place.getName());
                presentador.cargarOpcionesDeRuta(place.getLatLng());
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i("ERR", "An error occurred: " + status);
            }
        });
    }

    /**
     * Revisa si se tiene el acceso a la localización del dispositivo y lo solicita
     * en caso de que no esté otorgado.
     */
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

        // Se posiciona el botón para buscar la localización del dispositivo
        // en la esquina inferior derecha del mapa
        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).
                    getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
        }

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
        mostrarMensaje("Ocurrió un error al mostrar las paradas de autobuses");
    }

    @Override
    public void mostrarErrorInfoParada() {
        mostrarMensaje("Ocurrió un error al mostrar la información de la parada");
    }

    @Override
    public void mostrarOpcionesDeRuta(List<Ruta> listaRutas) {
        ScrollView scrollView = findViewById(R.id.scroll_view);
        scrollView.setVisibility(View.VISIBLE);
        System.out.println("\nOpciones de ruta:");
        for(Ruta ruta : listaRutas) {
            System.out.println("\n" + ruta.getNombre());
        }
    }

    @Override
    public void resaltarParadaSubida(Parada parada) {

    }

    @Override
    public void resaltarParadaBajada(Parada parada) {

    }

    @Override
    public void mostrarRecorridoRuta(List<Parada> puntos) {

    }

    @Override
    public void marcarPunto(LatLng punto) {

    }

    /**
     * Crea y muestra un mensaje tipo Toast.
     * @param mensaje Texto a incluir en el mensaje.
     */
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}

