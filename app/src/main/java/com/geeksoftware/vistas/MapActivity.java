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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.geeksoftware.modelos.Parada;
import com.geeksoftware.modelos.PuntoRuta;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Clase de la actividad principal donde se muestra el mapa y sobre él
 * las paradas de autobuses.
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, MapActivityView {

    /** Mapa de Google sobre el que se muestran las paradas de autobuses. */
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    /** Enlace de la vista con el procesamiento de la lógica de negocio y los datos. */
    private MapActivityPresenter presentador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presentador = new MapActivityPresenter(this);
        checkLocationPermission();
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
        // Se posiciona la cámara del mapa en la zona conurbada Zacatecas-Guadalupe.
        LatLng zacGpe = new LatLng(22.76424926, -102.5482729);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zacGpe,15.5f));

        // Se habilita la opción en el mapa para que el usuario pueda visualizar
        // su localización
        mMap.setMyLocationEnabled(true);
        // Solicitud de todas las paradas de autobuses.
        presentador.cargarParadas();
        // Asignación de una ventana personalizada cuando se selecciona
        // una parada.
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapActivity.this));

        // Se configura el autocompletador de lugares.
        inicializarCompletadorLugares();
        // Se define la nueva posición del botón para visualizar la localización
        // del dispositivo
        cambiarPosicionMiLocalizacion();

        // Evento para cuando se selecciona una parada de autobus.
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

        // Evento para cuando se presiona el botón de Ver Rutas.
        Button btnVerRutas = findViewById(R.id.ver_rutas);
        btnVerRutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentador.cargarRutas();
            }
        });
    }

    /**
     * Realiza la configuración inicial del cuadro de texto
     * que autocompleta destinos en el mapa.
     */
    private void inicializarCompletadorLugares() {
        String apiKey = getResources().getString(R.string.google_maps_key);
        // Se inicializa el SDK
        Places.initialize(getApplicationContext(), apiKey);
        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(this);


        // Inicialización del AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.getView().setBackgroundColor(Color.WHITE);


        // Especificación de los datos del lugar a regresar en cada consulta.
        autocompleteFragment.setPlaceFields(Arrays.asList(
                Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setHint(getString(R.string.buscar_destino));

        // Asignación de un PlaceSelectionListener para manejar las respuestas.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
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
     * Modifica la posición del botón para consultar la localización del dispositivo
     * a la esquina inferior derecha del mapa.
     */
    private void cambiarPosicionMiLocalizacion() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        View mapView = mapFragment.getView();

        // Se posiciona el botón para buscar la localización del dispositivo
        // en la esquina inferior derecha del mapa
        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            // Se obtiene la View del botón
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).
                    getParent()).findViewById(Integer.parseInt("2"));
            // Ahora se posiciona en la parte inferior derecha del mapa.
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // Se definen los parámetros y márgenes para posicionar el botón.
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 200);
        }
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

        BottomSheetDialog dialog = new BottomSheetDialog(MapActivity.this);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        BottomSheetListView listView = dialog.findViewById(R.id.listViewBtmSheet);
        listView.setAdapter(new BottomSheetListAdapter(MapActivity.this, listaRutas));
        dialog.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ruta rutaElegida = (Ruta) adapterView.getItemAtPosition(i);
                System.out.println("RUTA ELEGIDA: " + rutaElegida.getNombre());
                dialog.dismiss();
            }
        });
    }

    @Override
    public void resaltarParadaSubida(Parada parada) {

    }

    @Override
    public void resaltarParadaBajada(Parada parada) {

    }

    @Override
    public void mostrarRecorridoRuta(List<PuntoRuta> puntos, Ruta ruta) {
        Polyline recorridoRuta = mMap.addPolyline(new PolylineOptions().color(Color.parseColor(ruta.getColor())).geodesic(true));
        List<LatLng>puntosRuta = new ArrayList<>();
        for (int i=0; i<puntos.size();i++){
            puntosRuta.add(new LatLng(puntos.get(i).getLatitud(),puntos.get(i).getLongitud()));
        }
        recorridoRuta.setPoints(puntosRuta);
    }

    @Override
    public void marcarPunto(LatLng punto) {

    }

    @Override
    public void mostrarRutas(List<Ruta> rutas) {
        BottomSheetDialog dialog = new BottomSheetDialog(MapActivity.this);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        BottomSheetListView listView = dialog.findViewById(R.id.listViewBtmSheet);
        listView.setAdapter(new BottomSheetListAdapter(MapActivity.this, rutas));
        dialog.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ruta rutaElegida = (Ruta) adapterView.getItemAtPosition(i);
                System.out.println("RUTA ELEGIDA: " + rutaElegida.getNombre());
                System.out.println("RECORRIDO RUTA");
                presentador.cargarRecorridoRuta(rutaElegida);
                dialog.dismiss();
            }
        });
    }

    /**
     * Crea y muestra un mensaje tipo Toast.
     * @param mensaje Texto a incluir en el mensaje.
     */
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}