package com.geeksoftware.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import android.app.AlertDialog;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geeksoftware.modelos.Parada;
import com.geeksoftware.modelos.PuntoRuta;
import com.geeksoftware.modelos.Ruta;
import com.geeksoftware.presentadores.MapActivityPresenter;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
 * Clase de la actividad principal donde se muestra el mapa y sobre ??l
 * las paradas de autobuses.
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, MapActivityView {

    /** Mapa de Google sobre el que se muestran las paradas de autobuses. */
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    /** Enlace de la vista con el procesamiento de la l??gica de negocio y los datos. */
    private MapActivityPresenter presentador;
    /** Polyline que se encarga de mostrar el recorrido de cada ruta. */
    private Polyline recorridoRuta;
    /** En esta lista se guardar??n todos los marcadores para f??cil acceso **/
    private List<Marker> markers;
    /** ID de la parada bajar **/
    private LatLng paradaBajada;
    /** ID de la parada subida **/
    private LatLng paradaSubida;
    /** Se guarda la posici??n actual **/
    private LatLng ubicacionActual;
    /** TEMP **/
    private FusedLocationProviderClient client;
    /** ID de la imagen de la ruta **/
    private int id_imagen;
    /** Lista de destinos populares para la ruta **/
    private List<String> listaDestinosPopulares;
    AlertDialog alerta = null;
    LocationManager locationManager;
    final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presentador = new MapActivityPresenter(this);
        presentador.inicializarBaseDeDatos();
        checkLocationPermission();
    }

    /**
     * Revisa si se tiene el acceso a la localizaci??n del dispositivo y lo solicita
     * en caso de que no est?? otorgado.
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

                //Se obtienen las coordenadas actuales del usuario.
                client = LocationServices.getFusedLocationProviderClient(MapActivity.this);

                if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                client.getLastLocation().addOnSuccessListener(MapActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            ubicacionActual = new LatLng(location.getLatitude(), location.getLongitude());
                        }
                    }
                });
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
        recorridoRuta = mMap.addPolyline(new PolylineOptions());
        recorridoRuta.remove();
        // Se posiciona la c??mara del mapa en la zona conurbada Zacatecas-Guadalupe.
        LatLng zacGpe = new LatLng(22.76424926, -102.5482729);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zacGpe,15.5f));

        // Se habilita la opci??n en el mapa para que el usuario pueda visualizar
        // su localizaci??n
        mMap.setMyLocationEnabled(true);
        // Solicitud de todas las paradas de autobuses.
        presentador.cargarParadas();

        // Se configura el autocompletador de lugares.
        inicializarCompletadorLugares();
        // Se define la nueva posici??n del bot??n para visualizar la localizaci??n
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
                float zoom = mMap.getCameraPosition().zoom;
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        marker.getPosition(),zoom));
                return true;
            }
        });

        // Evento para cuando se presiona el bot??n de Ver Rutas.
        Button btnVerRutas = findViewById(R.id.ver_rutas);
        btnVerRutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentador.cargarRutas();
            }
        });

        FloatingActionButton btnInfoRuta = (FloatingActionButton)findViewById(R.id.botonInfoRuta);
        FloatingActionButton btnReiniciarMapa = (FloatingActionButton)findViewById(R.id.botonReiniciarMapa);
        FloatingActionButton btnLimpiarMapa = (FloatingActionButton)findViewById(R.id.botonLimpiarMapa);
        CardView cardInfo = (CardView) findViewById(R.id.cardInfoRutas);

        // Evento para cuando se presiona el bot??n de Reiniciar el mapa.
        btnReiniciarMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cuando se presiona el bot??n se vuelven a cargar todas las paradas.
                mMap.clear();
                presentador.cargarParadas();
                btnReiniciarMapa.setVisibility(View.GONE);
                btnInfoRuta.setVisibility(View.GONE);
                cardInfo.setVisibility(View.GONE);
                btnLimpiarMapa.setVisibility(View.VISIBLE);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zacGpe,15.5f));
                paradaBajada = null;
                paradaSubida = null;
            }
        });

        // Evento para cuando se presiona el bot??n de Limpiar el mapa.
        btnLimpiarMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cuando se presiona el bot??n se borran todas las paradas del mapa.
                mMap.clear();
                btnReiniciarMapa.setVisibility(View.VISIBLE);
                btnInfoRuta.setVisibility(View.GONE);
                cardInfo.setVisibility(View.GONE);
                btnLimpiarMapa.setVisibility(View.GONE);
                paradaBajada = null;
                paradaSubida = null;
            }
        });

        // Se actualizara la ubicaci??n actual del dispositivo cada segundo solo si la localizaci??n esta activada
        // y se posee el permiso de localizaci??n.
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 225);
        }else{
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, locListener, Looper.getMainLooper());
        }
    }

    /**
     * Realiza la configuraci??n inicial del cuadro de texto
     * que autocompleta destinos en el mapa.
     */
    private void inicializarCompletadorLugares() {
        String apiKey = getResources().getString(R.string.google_maps_key);
        // Se inicializa el SDK
        Places.initialize(getApplicationContext(), apiKey);
        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(this);


        // Inicializaci??n del AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.getView().setBackgroundColor(Color.WHITE);


        // Especificaci??n de los datos del lugar a regresar en cada consulta.
        autocompleteFragment.setPlaceFields(Arrays.asList(
                Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setHint(getString(R.string.buscar_destino));

        // Asignaci??n de un PlaceSelectionListener para manejar las respuestas.
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

        // Se limitan las b??squedas a M??xico
        autocompleteFragment.setCountry("MX");
    }

    /**
     * Modifica la posici??n del bot??n para consultar la localizaci??n del dispositivo
     * a la esquina inferior derecha del mapa.
     */
    private void cambiarPosicionMiLocalizacion() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        View mapView = mapFragment.getView();

        // Se posiciona el bot??n para buscar la localizaci??n del dispositivo
        // en la esquina inferior derecha del mapa
        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            // Se obtiene la View del bot??n
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).
                    getParent()).findViewById(Integer.parseInt("2"));
            // Ahora se posiciona en la parte inferior derecha del mapa.
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // Se definen los par??metros y m??rgenes para posicionar el bot??n.
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 200);
        }
    }

    @Override
    public void mostrarParadas(List<Parada> listaParadas) {
        markers = new ArrayList<>();
        for(Parada parada : listaParadas) {
            int height = 128;
            int width = 128;
            BitmapDrawable bitmapdraw = (BitmapDrawable)getDrawable(R.drawable.ic_autobus);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
            LatLng ubicacionParada = new LatLng(parada.getLatitud(), parada.getLongitud());
            Marker marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                    .position(ubicacionParada)
                    .title("Por aqu?? pasa"));
            markers.add(marker);
            marker.setSnippet(String.valueOf(parada.getId()));
        }
    }

    @Override
    public void mostrarInfoParada(List<Ruta> listaRutas, Marker marcador) {
        // Asignaci??n de una ventana personalizada cuando se selecciona
        // una parada.
        mMap.setInfoWindowAdapter(
                new CustomInfoWindowAdapter(MapActivity.this, listaRutas));
    }

    @Override
    public void mostrarErrorParadas() {
        mostrarMensaje("Ocurri?? un error al mostrar las paradas de autobuses");
    }

    @Override
    public void mostrarErrorInfoParada() {
        mostrarMensaje("Ocurri?? un error al mostrar la informaci??n de la parada");
    }

    @Override
    public void mostrarOpcionesDeRuta(List<Ruta> listaRutas) {

        BottomSheetDialog dialog = new BottomSheetDialog(MapActivity.this);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        BottomSheetListView listView = dialog.findViewById(R.id.listViewBtmSheet);
        listView.setAdapter(new BottomSheetListAdapter(
                MapActivity.this, listaRutas, R.layout.layout_list_view_row_items));
        dialog.show();

        FloatingActionButton infoRuta = (FloatingActionButton)findViewById(R.id.botonInfoRuta);
        ImageView imagenRuta = (ImageView) findViewById(R.id.imagenRuta);
        CardView cardInfo = (CardView) findViewById(R.id.cardInfoRutas);
        TextView textViewDestinos = (TextView) findViewById(R.id.textDestinosPopulares);
        Context context = imagenRuta.getContext();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Si no esta activada la localizaci??n se nos mostrara una ventana en donde nos pediran activarla.
                if(!verEstadoLocalizacion()){
                    mostrarAlertaActivarLocalizacion();
                }
                else {
                    // Temporizador para esperar a que la app obtenga la ubicaci??n atual.
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        }
                    }, 1600);

                    listaDestinosPopulares = new ArrayList<>();
                    recorridoRuta.remove();
                    Ruta rutaElegida = (Ruta) adapterView.getItemAtPosition(i);
                    presentador.cargarRecorridoRuta(rutaElegida);
                    presentador.cargarParadaSubida(rutaElegida, ubicacionActual);
                    presentador.cargarParadaBajada(rutaElegida);
                    if (cardInfo.getVisibility() == View.VISIBLE) {
                        cardInfo.setVisibility(View.GONE);
                    }
                    dialog.dismiss();
                    infoRuta.setVisibility(View.VISIBLE);
                    id_imagen = context.getResources().getIdentifier(rutaElegida.getImagen(), "drawable", context.getPackageName());
                    // obtener la lista de descripciones de los destinos populares
                    try {
                        listaDestinosPopulares = presentador.extraerDestinosRutaDescripcion(rutaElegida.getId());
                    }catch (Exception e){
                        textViewDestinos.setText("Ocurri?? un error al cargar los destinos populares");
                    }
                }
            }
        });

        infoRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cardInfo.getVisibility() == View.VISIBLE){
                    cardInfo.setVisibility(View.GONE);
                }else{
                    imagenRuta.setImageResource(id_imagen);
                    String descripcionDestinos = "";
                    if (listaDestinosPopulares.size() != 0){
                        for (int destino = 0; destino < listaDestinosPopulares.size(); destino++){
                            descripcionDestinos = descripcionDestinos.concat("- " + listaDestinosPopulares.get(destino)+"\n");
                        }
                        textViewDestinos.setText(descripcionDestinos);

                    } else {
                        textViewDestinos.setText(descripcionDestinos);
                    }
                    cardInfo.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // Este listener actualizara la ubicaci??n actual del dispositivo cada segundo.
    public LocationListener locListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Actualiza la ubicaci??n actual.
            ubicacionActual = new LatLng(location.getLatitude(), location.getLongitude());

            if(paradaBajada !=  null) {
                // Muestra una alerta si se esta cerca de la parada de bajada.
                presentador.actualizarUbicacion(ubicacionActual);
            }
        }

        public void onProviderDisabled(String provider) {
        }
        public void onProviderEnabled(String provider) {
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    @Override
    public void resaltarParadaSubida(Parada parada) {
        // Cargar el ??cono que indica la parada de subida
        int height = 192;
        int width = 256;
        BitmapDrawable bitmapdraw = (BitmapDrawable)getDrawable(R.drawable.ic_parada_subida);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        // Se genera un objeto Latlng a partir de la parada de subida.
        LatLng posicionParadaSubida = new LatLng(parada.getLatitud(), parada.getLongitud());

        // Se recorre la lista de marcadores
        for(Marker marker : markers){
            // Si ya hay una parada de subida registrada y la posici??n del marcador actual coincide
            // con la posici??n de la parada de subida registrada.
            if (paradaSubida != null && marker.getPosition().toString().equals(paradaSubida.toString())){
                // Se restaura el icono de la parada
                BitmapDrawable bitmapdraw2 = (BitmapDrawable)getDrawable(R.drawable.ic_autobus);
                Bitmap b2 = bitmapdraw2.getBitmap();
                Bitmap smallMarker2 = Bitmap.createScaledBitmap(b2, 128, 128, false);
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker2));
            }
            // Si se encuentra la nueva parada de subida en la lista
            if (marker.getPosition().toString().equals(posicionParadaSubida.toString())){
                // Se cambia el ??cono de la parada para indicar que hay que subir ah??.
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicionParadaSubida, 17f));
            }
        }
        // Se define la localizaci??n de la parada de bajada actualmente seleccionada.
        paradaSubida = posicionParadaSubida;
    }

    @Override
    public void resaltarParadaBajada(Parada parada) {
        // Cargar el ??cono que indica la parada de bajada
        int height = 192;
        int width = 256;
        BitmapDrawable bitmapdraw = (BitmapDrawable)getDrawable(R.drawable.ic_parada_bajada);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        // Se genera un objeto Latlng a partir de la parada de bajada.
        LatLng posicionParadaBajada = new LatLng(parada.getLatitud(), parada.getLongitud());

        // Se recorre la lista de marcadores
        for(Marker marker : markers){
            // Si ya hay una parada de bajada registrada y la posici??n del marcador actual coincide
            // con la posici??n de la parada de bajada registrada.
            if (paradaBajada != null && marker.getPosition().toString().equals(paradaBajada.toString())){
                // Se restaura el icono de la parada
                BitmapDrawable bitmapdraw2 = (BitmapDrawable)getDrawable(R.drawable.ic_autobus);
                Bitmap b2 = bitmapdraw2.getBitmap();
                Bitmap smallMarker2 = Bitmap.createScaledBitmap(b2, 128, 128, false);
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker2));
            }
            // Si se encuentra la nueva parada de bajada en la lista
            if (marker.getPosition().toString().equals(posicionParadaBajada.toString())){
                // Se cambia el ??cono de la parada para indicar que hay que bajar ah??.
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicionParadaBajada, 17f));
            }
        }
        // Se define la localizaci??n de la parada de bajada actualmente seleccionada.
        paradaBajada = posicionParadaBajada;
    }

    @Override
    public void mostrarRecorridoRuta(List<PuntoRuta> puntos, Ruta ruta) {
        mMap.clear();
        FloatingActionButton btnReiniciarMapa = (FloatingActionButton)findViewById(R.id.botonReiniciarMapa);
        FloatingActionButton btnLimpiarMapa = (FloatingActionButton)findViewById(R.id.botonLimpiarMapa);
        btnReiniciarMapa.setVisibility(View.VISIBLE);
        btnLimpiarMapa.setVisibility(View.VISIBLE);
        presentador.cargarParadasRuta(ruta.getId());
        recorridoRuta = mMap.addPolyline(new PolylineOptions().color(Color.parseColor(ruta.getColor())).geodesic(true));
        List<LatLng>puntosRuta = new ArrayList<>();
        for (int i=0; i<puntos.size();i++){
            puntosRuta.add(new LatLng(puntos.get(i).getLatitud(),puntos.get(i).getLongitud()));
        }
        recorridoRuta.setPoints(puntosRuta);
    }

    @Override
    public void mostrarRutas(List<Ruta> rutas) {
        BottomSheetDialog dialog = new BottomSheetDialog(MapActivity.this);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        BottomSheetListView listView = dialog.findViewById(R.id.listViewBtmSheet);
        listView.setAdapter(new BottomSheetListAdapter(
                MapActivity.this, rutas, R.layout.layout_list_view_row_items));
        dialog.show();

        FloatingActionButton infoRuta = (FloatingActionButton)findViewById(R.id.botonInfoRuta);
        ImageView imagenRuta = (ImageView) findViewById(R.id.imagenRuta);
        TextView textViewDestinos = (TextView) findViewById(R.id.textDestinosPopulares);
        CardView cardInfo = (CardView) findViewById(R.id.cardInfoRutas);
        Context context = imagenRuta.getContext();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listaDestinosPopulares = new ArrayList<>();
                if(cardInfo.getVisibility() == View.VISIBLE){
                    cardInfo.setVisibility(View.GONE);
                }
                recorridoRuta.remove();
                Ruta rutaElegida = (Ruta) adapterView.getItemAtPosition(i);
                try {
                    presentador.cargarRecorridoRuta(rutaElegida);
                }
                catch (Exception e){
                    // Si no es posible obtener el recorrido o las paradas de la ruta, entonces se muestra un mensaje de error.
                    mostrarMensaje("Ocurri?? un error al cargar la informaci??n de la ruta","#dc143c","#FFFFFF");
                }

                dialog.dismiss();
                infoRuta.setVisibility(View.VISIBLE);
                id_imagen = context.getResources().getIdentifier(rutaElegida.getImagen(),"drawable",context.getPackageName());
                // obtener la lista de descripciones de los destinos populares
                try {
                    listaDestinosPopulares = presentador.extraerDestinosRutaDescripcion(rutaElegida.getId());
                }catch (Exception e){
                    textViewDestinos.setText("Ocurri?? un error al cargar los destinos populares");
                }

                // Se aleja la vista del mapa para que se logre apreciar el trazo de la ruta.
                LatLng zacGpe = new LatLng(22.76424926, -102.5482729);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zacGpe,11.0f));
                System.out.println(">>> TAMA??O DEL ARREGLO" + listaDestinosPopulares);
             }
        });

        infoRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cardInfo.getVisibility() == View.VISIBLE){
                    cardInfo.setVisibility(View.GONE);
                }else{
                    imagenRuta.setImageResource(id_imagen);
                    String descripcionDestinos = "";
                    if (listaDestinosPopulares.size() != 0){
                        for (int destino = 0; destino < listaDestinosPopulares.size(); destino++){
                            descripcionDestinos = descripcionDestinos.concat("- " + listaDestinosPopulares.get(destino)+"\n");
                        }
                        textViewDestinos.setText(descripcionDestinos);

                    }
                    cardInfo.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void mostrarAlertaBajada() {
        Toast toast = Toast.makeText(this, "Prep??rate para bajar del autob??s",
                Toast.LENGTH_LONG);
        toast.getView().setBackgroundColor(Color.parseColor("#FFEB3B"));
        toast.show();
    }

    @Override
    public void mostrarAlertaActivarUbicacion() {
        Toast toast = Toast.makeText(this,
                "Activa los servicios de localizaci??n", Toast.LENGTH_LONG);
        toast.getView().setBackgroundColor(Color.parseColor("#FFEB3B"));
        toast.show();
    }

    /**
     * Crea y muestra un mensaje tipo Toast.
     * @param mensaje Texto a incluir en el mensaje.
     */
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    /**
     * Crea y muestra un mensaje tipo Toast.
     * @param mensaje Texto a incluir en el mensaje.
     * @param colorFondo Color del Toast.
     * @param colorTexto Color del texto que se mostrara.
     */
    private void mostrarMensaje(String mensaje, String colorFondo, String colorTexto) {
        Toast toast = Toast.makeText(this, Html.fromHtml("<font color='" + colorTexto +
                "' ><b>" + mensaje + "</b></font>"), Toast.LENGTH_LONG);
        toast.getView().setBackgroundColor(Color.parseColor(colorFondo));

        toast.show();
    }


    /**
     * Verifica si la localizaci??n esta activada.
     * @return Regresa true si la localizaci??n esta activada o false si no lo esta.
     */
    private boolean verEstadoLocalizacion(){
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        /* Verificamos si la localizaci??n esta activada o no */
        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            return false;
        }
        return true;
    }

    /**
     * Muestra una alerta en la que se pide que se active la localizaci??n del dispositivo movil.
     */
    private void mostrarAlertaActivarLocalizacion(){
        // Se crea la alerta
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Titulo de la alerta
        alertDialog.setTitle("La localizaci??n esta desactivada");
        // Mensaje que se mostrara en la alerta
        alertDialog.setMessage("Para poder acceder a esta caracter??stica es necesario activar la localizaci??n");

        alertDialog.setPositiveButton("Activar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                mostrarMensaje("Es necesario activar la localizaci??n para acceder a esta funci??n");
            }
        });
        alerta = alertDialog.create();
        // Se muestra la alerta
        alertDialog.show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(alerta != null)
        {
            alerta.dismiss ();
        }
    }
}