package com.geeksoftware.presentadores;

import android.content.Context;

import com.geeksoftware.basedatos.ConectorBaseDatos;
import com.geeksoftware.basedatos.SQLiteDataBase;
import com.geeksoftware.modelos.Parada;
import com.geeksoftware.modelos.Ruta;
import com.geeksoftware.utilidades.BuscadorParada;
import com.geeksoftware.vistas.MapActivityView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Recibe las interacciones del usuario echas a través de una MapActivityView
 * y consulta u obtiene los datos correspondientes para actualizar esta misma
 * vista con la información obtenida.
 */
public class MapActivityPresenter {

    /** Vista del mapa a enlazar con el presentador. */
    private MapActivityView vista;
    /** Motor de base de datos a usar. */
    private ConectorBaseDatos baseDatos;

    /**
     * Define el constructor del presentador.
     * @param vista Interfaz de usuario a ser enlazada con el presentador.
     */
    public MapActivityPresenter(MapActivityView vista) {
        this.vista = vista;
        baseDatos = new SQLiteDataBase((Context) vista);
        registrarParadas();
        registrarRutas();
        registrarParadaRutas();
    }

    /**
     * Extrae todas las paradas de autobuses que existen en la base de datos.
     */
    public void cargarParadas() {
        List<Parada> listaParadas = baseDatos.obtenerParadas();
        if(listaParadas != null) {
            vista.mostrarParadas(listaParadas);
        } else {
            vista.mostrarErrorParadas();
        }
    }

    /**
     * Extrae la información de las rutas que se detienen en una determinada parada de autobus.
     * @param idParada Identificador de la parada a consultar sus rutas.
     * @param marcador Punto del mapa donde se mostrará la información de las rutas
     *                 que se detienen ahí.
     */
    public void cargarInfoParada(Integer idParada, Marker marcador) {
        List<Ruta> listaRutas = baseDatos.obtenerRutasPorParada(idParada);
        if(listaRutas !=  null) {
            vista.mostrarInfoParada(listaRutas, marcador);
        } else {
            vista.mostrarErrorInfoParada();
        }
    }

    /**
     * Encuentra las rutas más óptimas para llegar a la localización
     * indicada.
     * @param localizacion Coordenadas del lugar a llegar.
     */
    public void cargarOpcionesDeRuta(LatLng localizacion) {
        // Se obtienen todas las paradas de la base de datos.
        List<Parada> listaParadas = baseDatos.obtenerParadas();
        // Se buscan las paradas cercanas al destino elegido.
        List<Parada> paradasCercanas = BuscadorParada.
                buscarParadasCercanas(localizacion, listaParadas);

        List<Ruta> rutasOptimas = new ArrayList<>();
        for(Parada parada : paradasCercanas) {
            // Se buscan las rutas que pasan por cada parada cercana.
            List<Ruta> rutasParada = baseDatos.obtenerRutasPorParada(parada.getId());
            for(Ruta ruta : rutasParada) {
                // Se agregan dichas rutas a la lista de rutas óptimas.
                if(!rutasOptimas.contains(ruta)) {
                    rutasOptimas.add(ruta);
                }
            }
        }
        vista.mostrarOpcionesDeRuta(rutasOptimas);
    }

    public void cargarParadaSubida(Ruta ruta, LatLng localizacion) {
        vista.resaltarParadaSubida(null);
    }

    public void cargarParadaBajada(Ruta ruta, LatLng destino) {
        vista.resaltarParadaBajada(null);
    }

    public void cargarRecorridoRuta(Ruta ruta) {
        vista.mostrarRecorridoRuta(null);
    }

    /**
     * Obtiene todas las rutas almacenadas en la base de datos.
     */
    public void cargarRutas() {
        List<Ruta> listaRutas = new ArrayList<>();
        // Se obtienen todas las rutas de la base de datos
        listaRutas = baseDatos.obtenerRutas();
        if(listaRutas != null) {
            // No hubo problemas al obtener las rutas.
            vista.mostrarRutas(listaRutas);
        }
    }

    /**
     * Realiza la inserción de las paradas de autobuses en la base de datos.
     */
    private void registrarParadas(){
        baseDatos.agregarParada(new Parada(22.751152,-102.666829,""));
        baseDatos.agregarParada(new Parada(22.761301,-102.670530,""));
        baseDatos.agregarParada(new Parada(22.756438,-102.663113,""));
        baseDatos.agregarParada(new Parada(22.757222,-102.662159,""));
        baseDatos.agregarParada(new Parada(22.761125,-102.654795,""));
        baseDatos.agregarParada(new Parada(22.760933,-102.654559,""));
        baseDatos.agregarParada(new Parada(22.769525,-102.642554,""));
        baseDatos.agregarParada(new Parada(22.768735,-102.641977,""));
        baseDatos.agregarParada(new Parada(22.770451,-102.632906,""));
        baseDatos.agregarParada(new Parada(22.754969,-102.634289,""));
        baseDatos.agregarParada(new Parada(22.754298,-102.632314,""));
        baseDatos.agregarParada(new Parada(22.773458,-102.626813,""));
        baseDatos.agregarParada(new Parada(22.773621,-102.626931,""));
        baseDatos.agregarParada(new Parada(22.775438,-102.620899,""));
        baseDatos.agregarParada(new Parada(22.782629,-102.614922,""));
        baseDatos.agregarParada(new Parada(22.783159,-102.615740,""));
        baseDatos.agregarParada(new Parada(22.780220,-102.619834,""));
        baseDatos.agregarParada(new Parada(22.780287,-102.619953,""));
        baseDatos.agregarParada(new Parada(22.778010,-102.618501,""));
        baseDatos.agregarParada(new Parada(22.778167,-102.618724,""));
        baseDatos.agregarParada(new Parada(22.776962,-102.616982,""));
        baseDatos.agregarParada(new Parada(22.775927,-102.614739,""));
        baseDatos.agregarParada(new Parada(22.774804,-102.611957,""));
        baseDatos.agregarParada(new Parada(22.775193,-102.612013,""));
        baseDatos.agregarParada(new Parada(22.775581,-102.612324,""));
        baseDatos.agregarParada(new Parada(22.774450,-102.608997,""));
        baseDatos.agregarParada(new Parada(22.747553,-102.614391,""));
        baseDatos.agregarParada(new Parada(22.749048,-102.613180,""));
        baseDatos.agregarParada(new Parada(22.749493,-102.611743,""));
        baseDatos.agregarParada(new Parada(22.750189,-102.613877,""));
        baseDatos.agregarParada(new Parada(22.750907,-102.611683,""));
        baseDatos.agregarParada(new Parada(22.749580,-102.610714,""));
        baseDatos.agregarParada(new Parada(22.752045,-102.612091,""));
        baseDatos.agregarParada(new Parada(22.753796,-102.608707,""));
        baseDatos.agregarParada(new Parada(22.754154,-102.608028,""));
        baseDatos.agregarParada(new Parada(22.754553,-102.607238,""));
        baseDatos.agregarParada(new Parada(22.754409,-102.605636,""));
        baseDatos.agregarParada(new Parada(22.754249,-102.605064,""));
        baseDatos.agregarParada(new Parada(22.754883,-102.604347,""));
        baseDatos.agregarParada(new Parada(22.755442,-102.603267,""));
        baseDatos.agregarParada(new Parada(22.755546,-102.602658,""));
        baseDatos.agregarParada(new Parada(22.755648,-102.601932,""));
        baseDatos.agregarParada(new Parada(22.755805,-102.599909,""));
        baseDatos.agregarParada(new Parada(22.755215,-102.595342,""));
        baseDatos.agregarParada(new Parada(22.754772,-102.597624,""));
        baseDatos.agregarParada(new Parada(22.751555,-102.599614,""));
        baseDatos.agregarParada(new Parada(22.753691,-102.603226,""));
        baseDatos.agregarParada(new Parada(22.753459,-102.603308,""));
        baseDatos.agregarParada(new Parada(22.751661,-102.603936,""));
        baseDatos.agregarParada(new Parada(22.750906,-102.605253,""));
        baseDatos.agregarParada(new Parada(22.751831,-102.608059,""));
        baseDatos.agregarParada(new Parada(22.748815,-102.607532,""));
        baseDatos.agregarParada(new Parada(22.748940,-102.601012,""));
        baseDatos.agregarParada(new Parada(22.749029,-102.600320,""));
        baseDatos.agregarParada(new Parada(22.750349,-102.597718,""));
        baseDatos.agregarParada(new Parada(22.765346,-102.604969,""));
        baseDatos.agregarParada(new Parada(22.775492,-102.597505,""));
        baseDatos.agregarParada(new Parada(22.773910,-102.596980,""));
        baseDatos.agregarParada(new Parada(22.773925,-102.597332,""));
        baseDatos.agregarParada(new Parada(22.773650,-102.597441,""));
        baseDatos.agregarParada(new Parada(22.773259,-102.597757,""));
        baseDatos.agregarParada(new Parada(22.773289,-102.600223,""));
        baseDatos.agregarParada(new Parada(22.770924,-102.596787,""));
        baseDatos.agregarParada(new Parada(22.770337,-102.596680,""));
        baseDatos.agregarParada(new Parada(22.770369,-102.595358,""));
        baseDatos.agregarParada(new Parada(22.768558,-102.596702,""));
        baseDatos.agregarParada(new Parada(22.767472,-102.595865,""));
        baseDatos.agregarParada(new Parada(22.766698,-102.595596,""));
        baseDatos.agregarParada(new Parada(22.766484,-102.595523,""));
        baseDatos.agregarParada(new Parada(22.766325,-102.594923,""));
        baseDatos.agregarParada(new Parada(22.765537,-102.596296,""));
        baseDatos.agregarParada(new Parada(22.765576,-102.596397,""));
        baseDatos.agregarParada(new Parada(22.765437,-102.596796,""));
        baseDatos.agregarParada(new Parada(22.763860,-102.597702,""));
        baseDatos.agregarParada(new Parada(22.763456,-102.597490,""));
        baseDatos.agregarParada(new Parada(22.761337,-102.597441,""));
        baseDatos.agregarParada(new Parada(22.760880,-102.597604,""));
        baseDatos.agregarParada(new Parada(22.760134,-102.594749,""));
        baseDatos.agregarParada(new Parada(22.760177,-102.594860,""));
        baseDatos.agregarParada(new Parada(22.759713,-102.594708,""));
        baseDatos.agregarParada(new Parada(22.759598,-102.594595,""));
        baseDatos.agregarParada(new Parada(22.759912,-102.596414,""));
        baseDatos.agregarParada(new Parada(22.758411,-102.597539,""));
        baseDatos.agregarParada(new Parada(22.769246,-102.590317,""));
        baseDatos.agregarParada(new Parada(22.769053,-102.590204,""));
        baseDatos.agregarParada(new Parada(22.766791,-102.591578,""));
        baseDatos.agregarParada(new Parada(22.765584,-102.591274,""));
        baseDatos.agregarParada(new Parada(22.765961,-102.590227,""));
        baseDatos.agregarParada(new Parada(22.765876,-102.590120,""));
        baseDatos.agregarParada(new Parada(22.766171,-102.589133,""));
        baseDatos.agregarParada(new Parada(22.766300,-102.588688,""));
        baseDatos.agregarParada(new Parada(22.766741,-102.587838,""));
        baseDatos.agregarParada(new Parada(22.766973,-102.587704,""));
        baseDatos.agregarParada(new Parada(22.763323,-102.592890,""));
        baseDatos.agregarParada(new Parada(22.762357,-102.591268,""));
        baseDatos.agregarParada(new Parada(22.763772,-102.589874,""));
        baseDatos.agregarParada(new Parada(22.755937,-102.593127,""));
        baseDatos.agregarParada(new Parada(22.756278,-102.591888,""));
        baseDatos.agregarParada(new Parada(22.756816,-102.590691,""));
        baseDatos.agregarParada(new Parada(22.757041,-102.591227,""));
        baseDatos.agregarParada(new Parada(22.758451,-102.590287,""));
        baseDatos.agregarParada(new Parada(22.758925,-102.589804,""));
        baseDatos.agregarParada(new Parada(22.759102,-102.589693,""));
        baseDatos.agregarParada(new Parada(22.761389,-102.589313,""));
        baseDatos.agregarParada(new Parada(22.762186,-102.587744,""));
        baseDatos.agregarParada(new Parada(22.760769,-102.587860,""));
        baseDatos.agregarParada(new Parada(22.759957,-102.586103,""));
        baseDatos.agregarParada(new Parada(22.760260,-102.586176,""));
        baseDatos.agregarParada(new Parada(22.760711,-102.585689,""));
        baseDatos.agregarParada(new Parada(22.761413,-102.585005,""));
        baseDatos.agregarParada(new Parada(22.762197,-102.584474,""));
        baseDatos.agregarParada(new Parada(22.762358,-102.584635,""));
        baseDatos.agregarParada(new Parada(22.762478,-102.584395,""));
        baseDatos.agregarParada(new Parada(22.763213,-102.583777,""));
        baseDatos.agregarParada(new Parada(22.751219,-102.583535,""));
        baseDatos.agregarParada(new Parada(22.745364,-102.589473,""));
        baseDatos.agregarParada(new Parada(22.738999,-102.586606,""));
        baseDatos.agregarParada(new Parada(22.764489,-102.581414,""));
        baseDatos.agregarParada(new Parada(22.764209,-102.581327,""));
        baseDatos.agregarParada(new Parada(22.762913,-102.579496,""));
        baseDatos.agregarParada(new Parada(22.762528,-102.577110,""));
        baseDatos.agregarParada(new Parada(22.763088,-102.576832,""));
        baseDatos.agregarParada(new Parada(22.761120,-102.577011,""));
        baseDatos.agregarParada(new Parada(22.760887,-102.575180,""));
        baseDatos.agregarParada(new Parada(22.762937,-102.575231,""));
        baseDatos.agregarParada(new Parada(22.754675,-102.576155,""));
        baseDatos.agregarParada(new Parada(22.760318,-102.573487,""));
        baseDatos.agregarParada(new Parada(22.760299,-102.573280,""));
        baseDatos.agregarParada(new Parada(22.757963,-102.573405,""));
        baseDatos.agregarParada(new Parada(22.757907,-102.573195,""));
        baseDatos.agregarParada(new Parada(22.756086,-102.573141,""));
        baseDatos.agregarParada(new Parada(22.755727,-102.573231,""));
        baseDatos.agregarParada(new Parada(22.754249,-102.573247,""));
        baseDatos.agregarParada(new Parada(22.754433,-102.572947,""));
        baseDatos.agregarParada(new Parada(22.752995,-102.573779,""));
        baseDatos.agregarParada(new Parada(22.752055,-102.573182,""));
        baseDatos.agregarParada(new Parada(22.752317,-102.572579,""));
        baseDatos.agregarParada(new Parada(22.752998,-102.571855,""));
        baseDatos.agregarParada(new Parada(22.763057,-102.572050,""));
        baseDatos.agregarParada(new Parada(22.762973,-102.571940,""));
        baseDatos.agregarParada(new Parada(22.763686,-102.571980,""));
        baseDatos.agregarParada(new Parada(22.765664,-102.569479,""));
        baseDatos.agregarParada(new Parada(22.765274,-102.568056,""));
        baseDatos.agregarParada(new Parada(22.765098,-102.567532,""));
        baseDatos.agregarParada(new Parada(22.764900,-102.566563,""));
        baseDatos.agregarParada(new Parada(22.761206,-102.569632,""));
        baseDatos.agregarParada(new Parada(22.760258,-102.569045,""));
        baseDatos.agregarParada(new Parada(22.759563,-102.567650,""));
        baseDatos.agregarParada(new Parada(22.759629,-102.567261,""));
        baseDatos.agregarParada(new Parada(22.759126,-102.565362,""));
        baseDatos.agregarParada(new Parada(22.758995,-102.564897,""));
        baseDatos.agregarParada(new Parada(22.755719,-102.566355,""));
        baseDatos.agregarParada(new Parada(22.754205,-102.566466,""));
        baseDatos.agregarParada(new Parada(22.752847,-102.566590,""));
        baseDatos.agregarParada(new Parada(22.752225,-102.566736,""));
        baseDatos.agregarParada(new Parada(22.750572,-102.566995,""));
        baseDatos.agregarParada(new Parada(22.780400,-102.583859,""));
        baseDatos.agregarParada(new Parada(22.779898,-102.583441,""));
        baseDatos.agregarParada(new Parada(22.779675,-102.583760,""));
        baseDatos.agregarParada(new Parada(22.779241,-102.584259,""));
        baseDatos.agregarParada(new Parada(22.778838,-102.583177,""));
        baseDatos.agregarParada(new Parada(22.778188,-102.582533,""));
        baseDatos.agregarParada(new Parada(22.777885,-102.585336,""));
        baseDatos.agregarParada(new Parada(22.777194,-102.581919,""));
        baseDatos.agregarParada(new Parada(22.776338,-102.585199,""));
        baseDatos.agregarParada(new Parada(22.776267,-102.585588,""));
        baseDatos.agregarParada(new Parada(22.775512,-102.585874,""));
        baseDatos.agregarParada(new Parada(22.776478,-102.587871,""));
        baseDatos.agregarParada(new Parada(22.775028,-102.589504,""));
        baseDatos.agregarParada(new Parada(22.774436,-102.590189,""));
        baseDatos.agregarParada(new Parada(22.774178,-102.589795,""));
        baseDatos.agregarParada(new Parada(22.773316,-102.592534,""));
        baseDatos.agregarParada(new Parada(22.773516,-102.592334,""));
        baseDatos.agregarParada(new Parada(22.773507,-102.59195,""));
        baseDatos.agregarParada(new Parada(22.773538,-102.58764,""));
        baseDatos.agregarParada(new Parada(22.773828,-102.587426,""));
        baseDatos.agregarParada(new Parada(22.772706,-102.585055,""));
        baseDatos.agregarParada(new Parada(22.773009,-102.584958,""));
        baseDatos.agregarParada(new Parada(22.774016,-102.585255,""));
        baseDatos.agregarParada(new Parada(22.773815,-102.584920,""));
        baseDatos.agregarParada(new Parada(22.774109,-102.583798,""));
        baseDatos.agregarParada(new Parada(22.774431,-102.583212,""));
        baseDatos.agregarParada(new Parada(22.774465,-102.583078,""));
        baseDatos.agregarParada(new Parada(22.775386,-102.581431,""));
        baseDatos.agregarParada(new Parada(22.775294,-102.580801,""));
        baseDatos.agregarParada(new Parada(22.775302,-102.580447,""));
        baseDatos.agregarParada(new Parada(22.775309,-102.580275,""));
        baseDatos.agregarParada(new Parada(22.774748,-102.579569,""));
        baseDatos.agregarParada(new Parada(22.771142,-102.585051,""));
        baseDatos.agregarParada(new Parada(22.770377,-102.584741,""));
        baseDatos.agregarParada(new Parada(22.771162,-102.583594,""));
        baseDatos.agregarParada(new Parada(22.770965,-102.583732,""));
        baseDatos.agregarParada(new Parada(22.77101,-102.582519,""));
        baseDatos.agregarParada(new Parada(22.770664,-102.581981,""));
        baseDatos.agregarParada(new Parada(22.770027,-102.582224,""));
        baseDatos.agregarParada(new Parada(22.768219,-102.582275,""));
        baseDatos.agregarParada(new Parada(22.767724,-102.582088,""));
        baseDatos.agregarParada(new Parada(22.767217,-102.581927,""));
        baseDatos.agregarParada(new Parada(22.767319,-102.581639,""));
        baseDatos.agregarParada(new Parada(22.76541,-102.578646,""));
        baseDatos.agregarParada(new Parada(22.765568,-102.578648,""));
        baseDatos.agregarParada(new Parada(22.765168,-102.578774,""));
        baseDatos.agregarParada(new Parada(22.765176,-102.578586,""));
        baseDatos.agregarParada(new Parada(22.765379,-102.578412,""));
        baseDatos.agregarParada(new Parada(22.765524,-102.576806,""));
        baseDatos.agregarParada(new Parada(22.765632,-102.576886,""));
        baseDatos.agregarParada(new Parada(22.766567,-102.576769,""));
        baseDatos.agregarParada(new Parada(22.765544,-102.575267,""));
        baseDatos.agregarParada(new Parada(22.768224,-102.573056,""));
        baseDatos.agregarParada(new Parada(22.768619,-102.572278,""));
        baseDatos.agregarParada(new Parada(22.768859,-102.571874,""));
        baseDatos.agregarParada(new Parada(22.769197,-102.571702,""));
        baseDatos.agregarParada(new Parada(22.768581,-102.567931,""));
        baseDatos.agregarParada(new Parada(22.767983,-102.566864,""));
        baseDatos.agregarParada(new Parada(22.769387,-102.572842,""));
        baseDatos.agregarParada(new Parada(22.767564,-102.575306,""));
        baseDatos.agregarParada(new Parada(22.769961,-102.573232,""));
        baseDatos.agregarParada(new Parada(22.772416,-102.573205,""));
        baseDatos.agregarParada(new Parada(22.775506,-102.572599,""));
        baseDatos.agregarParada(new Parada(22.78055,-102.571214,""));
        baseDatos.agregarParada(new Parada(22.783703,-102.567646,""));
        baseDatos.agregarParada(new Parada(22.783558,-102.567584,""));
        baseDatos.agregarParada(new Parada(22.789293,-102.561288,""));
        baseDatos.agregarParada(new Parada(22.771157,-102.574985,""));
        baseDatos.agregarParada(new Parada(22.771332,-102.57499,""));
        baseDatos.agregarParada(new Parada(22.771528,-102.574902,""));
        baseDatos.agregarParada(new Parada(22.771718,-102.574776,""));
        baseDatos.agregarParada(new Parada(22.77254,-102.576147,""));
        baseDatos.agregarParada(new Parada(22.772691,-102.576151,""));
        baseDatos.agregarParada(new Parada(22.773016,-102.577119,""));
        baseDatos.agregarParada(new Parada(22.766502,-102.560375,""));
        baseDatos.agregarParada(new Parada(22.766491,-102.559494,""));
        baseDatos.agregarParada(new Parada(22.765988,-102.557476,""));
        baseDatos.agregarParada(new Parada(22.766174,-102.557088,""));
        baseDatos.agregarParada(new Parada(22.766498,-102.557021,""));
        baseDatos.agregarParada(new Parada(22.767138,-102.556819,""));
        baseDatos.agregarParada(new Parada(22.767919,-102.556527,""));
        baseDatos.agregarParada(new Parada(22.768453,-102.556112,""));
        baseDatos.agregarParada(new Parada(22.769668,-102.554917,""));
        baseDatos.agregarParada(new Parada(22.769885,-102.554554,""));
        baseDatos.agregarParada(new Parada(22.769211,-102.552055,""));
        baseDatos.agregarParada(new Parada(22.768407,-102.550414,""));
        baseDatos.agregarParada(new Parada(22.764437,-102.557358,""));
        baseDatos.agregarParada(new Parada(22.764568,-102.557014,""));
        baseDatos.agregarParada(new Parada(22.763149,-102.550482,""));
        baseDatos.agregarParada(new Parada(22.763522,-102.550078,""));
        baseDatos.agregarParada(new Parada(22.764798,-102.548207,""));
        baseDatos.agregarParada(new Parada(22.764216,-102.547034,""));
        baseDatos.agregarParada(new Parada(22.7642,-102.546434,""));
        baseDatos.agregarParada(new Parada(22.763817,-102.545805,""));
        baseDatos.agregarParada(new Parada(22.763364,-102.547242,""));
        baseDatos.agregarParada(new Parada(22.761175,-102.549352,""));
        baseDatos.agregarParada(new Parada(22.761628,-102.549154,""));
        baseDatos.agregarParada(new Parada(22.762030,-102.548356,""));
        baseDatos.agregarParada(new Parada(22.761733,-102.552251,""));
        baseDatos.agregarParada(new Parada(22.762084,-102.546017,""));
        baseDatos.agregarParada(new Parada(22.762457,-102.545705,""));
        baseDatos.agregarParada(new Parada(22.763351,-102.544946,""));
        baseDatos.agregarParada(new Parada(22.762948,-102.544138,""));
        baseDatos.agregarParada(new Parada(22.760845,-102.543337,""));
        baseDatos.agregarParada(new Parada(22.762347,-102.542453,""));
        baseDatos.agregarParada(new Parada(22.761967,-102.54203,""));
        baseDatos.agregarParada(new Parada(22.759034,-102.544726,""));
        baseDatos.agregarParada(new Parada(22.759059,-102.544555,""));
        baseDatos.agregarParada(new Parada(22.761423,-102.540879,""));
        baseDatos.agregarParada(new Parada(22.760716,-102.53948,""));
        baseDatos.agregarParada(new Parada(22.760631,-102.538195,""));
        baseDatos.agregarParada(new Parada(22.76118,-102.537806,""));
        baseDatos.agregarParada(new Parada(22.757425,-102.542859,""));
        baseDatos.agregarParada(new Parada(22.756275,-102.541023,""));
        baseDatos.agregarParada(new Parada(22.756403,-102.540776,""));
        baseDatos.agregarParada(new Parada(22.755361,-102.536986,""));
        baseDatos.agregarParada(new Parada(22.754832,-102.535916,""));
        baseDatos.agregarParada(new Parada(22.754739,-102.535905,""));
        baseDatos.agregarParada(new Parada(22.754698,-102.535088,""));
        baseDatos.agregarParada(new Parada(22.756131,-102.535783,""));
        baseDatos.agregarParada(new Parada(22.756541,-102.53519,""));
        baseDatos.agregarParada(new Parada(22.763419,-102.537843,""));
        baseDatos.agregarParada(new Parada(22.763088,-102.538048,""));
        baseDatos.agregarParada(new Parada(22.764978,-102.537272,""));
        baseDatos.agregarParada(new Parada(22.765157,-102.537406,""));
        baseDatos.agregarParada(new Parada(22.766121,-102.538003,""));
        baseDatos.agregarParada(new Parada(22.768637,-102.536053,""));
        baseDatos.agregarParada(new Parada(22.770365,-102.537202,""));
        baseDatos.agregarParada(new Parada(22.772496,-102.542039,""));
        baseDatos.agregarParada(new Parada(22.765271,-102.534005,""));
        baseDatos.agregarParada(new Parada(22.762534,-102.534104,""));
        baseDatos.agregarParada(new Parada(22.758044,-102.539195,""));
        baseDatos.agregarParada(new Parada(22.758229,-102.538448,""));
        baseDatos.agregarParada(new Parada(22.756160,-102.531995,""));
        baseDatos.agregarParada(new Parada(22.755593,-102.531906,""));
        baseDatos.agregarParada(new Parada(22.754023,-102.532779,""));
        baseDatos.agregarParada(new Parada(22.753767,-102.532425,""));
        baseDatos.agregarParada(new Parada(22.752544,-102.529263,""));
        baseDatos.agregarParada(new Parada(22.752225,-102.528071,""));
        baseDatos.agregarParada(new Parada(22.754729,-102.525065,""));
        baseDatos.agregarParada(new Parada(22.754797,-102.525563,""));
        baseDatos.agregarParada(new Parada(22.756559,-102.523952,""));
        baseDatos.agregarParada(new Parada(22.756566,-102.521784,""));
        baseDatos.agregarParada(new Parada(22.756188,-102.521969,""));
        baseDatos.agregarParada(new Parada(22.755606,-102.520691,""));
        baseDatos.agregarParada(new Parada(22.75579,-102.518086,""));
        baseDatos.agregarParada(new Parada(22.756333,-102.517732,""));
        baseDatos.agregarParada(new Parada(22.756488,-102.516747,""));
        baseDatos.agregarParada(new Parada(22.756791,-102.513951,""));
        baseDatos.agregarParada(new Parada(22.756506,-102.512249,""));
        baseDatos.agregarParada(new Parada(22.756306,-102.511958,""));
        baseDatos.agregarParada(new Parada(22.756891,-102.511618,""));
        baseDatos.agregarParada(new Parada(22.755982,-102.50927,""));
        baseDatos.agregarParada(new Parada(22.756062,-102.50866,""));
        baseDatos.agregarParada(new Parada(22.755881,-102.508134,""));
        baseDatos.agregarParada(new Parada(22.756927,-102.507958,""));
        baseDatos.agregarParada(new Parada(22.753469,-102.502654,""));
        baseDatos.agregarParada(new Parada(22.753372,-102.502254,""));
        baseDatos.agregarParada(new Parada(22.754799,-102.501465,""));
        baseDatos.agregarParada(new Parada(22.753097,-102.499743,""));
        baseDatos.agregarParada(new Parada(22.750146,-102.499131,""));
        baseDatos.agregarParada(new Parada(22.750251,-102.499107,""));
        baseDatos.agregarParada(new Parada(22.74728,-102.498808,""));
        baseDatos.agregarParada(new Parada(22.745508,-102.500104,""));
        baseDatos.agregarParada(new Parada(22.745217,-102.500339,""));
        baseDatos.agregarParada(new Parada(22.739869,-102.496775,""));
        baseDatos.agregarParada(new Parada(22.732668,-102.49559,""));
        baseDatos.agregarParada(new Parada(22.732601,-102.495204,""));
        baseDatos.agregarParada(new Parada(22.717047,-102.492182,""));
        baseDatos.agregarParada(new Parada(22.716335,-102.489705,""));
        baseDatos.agregarParada(new Parada(22.71698,-102.482084,""));
        baseDatos.agregarParada(new Parada(22.719479,-102.481176,""));
        baseDatos.agregarParada(new Parada(22.718969,-102.47883,""));
        baseDatos.agregarParada(new Parada(22.720992,-102.475707,""));
        baseDatos.agregarParada(new Parada(22.744118,-102.503232,""));
        baseDatos.agregarParada(new Parada(22.740393,-102.504312,""));
        baseDatos.agregarParada(new Parada(22.74324,-102.508469,""));
        baseDatos.agregarParada(new Parada(22.749125,-102.502853,""));
        baseDatos.agregarParada(new Parada(22.748141,-102.505312,""));
        baseDatos.agregarParada(new Parada(22.746864,-102.511307,""));
        baseDatos.agregarParada(new Parada(22.744038,-102.512366,""));
        baseDatos.agregarParada(new Parada(22.737851,-102.514153,""));
        baseDatos.agregarParada(new Parada(22.739418,-102.517733,""));
        baseDatos.agregarParada(new Parada(22.736858,-102.51779,""));
        baseDatos.agregarParada(new Parada(22.736442,-102.517762,""));
        baseDatos.agregarParada(new Parada(22.733326,-102.516031,""));
        baseDatos.agregarParada(new Parada(22.733278,-102.51208,""));
        baseDatos.agregarParada(new Parada(22.731425,-102.51119,""));
        baseDatos.agregarParada(new Parada(22.728215,-102.515923,""));
        baseDatos.agregarParada(new Parada(22.72475,-102.517479,""));
        baseDatos.agregarParada(new Parada(22.719864,-102.518214,""));
        baseDatos.agregarParada(new Parada(22.718343,-102.511805,""));
        baseDatos.agregarParada(new Parada(22.719634,-102.508854,""));
        baseDatos.agregarParada(new Parada(22.699678,-102.524349,""));
        baseDatos.agregarParada(new Parada(22.726499,-102.520406,""));
        baseDatos.agregarParada(new Parada(22.726922,-102.520757,""));
        baseDatos.agregarParada(new Parada(22.727889,-102.522563,""));
        baseDatos.agregarParada(new Parada(22.729606,-102.523745,""));
        baseDatos.agregarParada(new Parada(22.730028,-102.524504,""));
        baseDatos.agregarParada(new Parada(22.731278,-102.523313,""));
        baseDatos.agregarParada(new Parada(22.731088,-102.52332,""));
        baseDatos.agregarParada(new Parada(22.734206,-102.524042,""));
        baseDatos.agregarParada(new Parada(22.735666,-102.524593,""));
        baseDatos.agregarParada(new Parada(22.736035,-102.52432,""));
        baseDatos.agregarParada(new Parada(22.739157,-102.524882,""));
        baseDatos.agregarParada(new Parada(22.741049,-102.523925,""));
        baseDatos.agregarParada(new Parada(22.741277,-102.523789,""));
        baseDatos.agregarParada(new Parada(22.741296,-102.523073,""));
        baseDatos.agregarParada(new Parada(22.742762,-102.519464,""));
        baseDatos.agregarParada(new Parada(22.743597,-102.519132,""));
        baseDatos.agregarParada(new Parada(22.745395,-102.518691,""));
        baseDatos.agregarParada(new Parada(22.745908,-102.519203,""));
        baseDatos.agregarParada(new Parada(22.745528,-102.518153,""));
        baseDatos.agregarParada(new Parada(22.745699,-102.518122,""));
        baseDatos.agregarParada(new Parada(22.744186,-102.516269,""));
        baseDatos.agregarParada(new Parada(22.744332,-102.516144,""));
        baseDatos.agregarParada(new Parada(22.744493,-102.516327,""));
        baseDatos.agregarParada(new Parada(22.746121,-102.515747,""));
        baseDatos.agregarParada(new Parada(22.747892,-102.514148,""));
        baseDatos.agregarParada(new Parada(22.747974,-102.514874,""));
        baseDatos.agregarParada(new Parada(22.748769,-102.516965,""));
        baseDatos.agregarParada(new Parada(22.748598,-102.516915,""));
        baseDatos.agregarParada(new Parada(22.749495,-102.51746,""));
        baseDatos.agregarParada(new Parada(22.749276,-102.519173,""));
        baseDatos.agregarParada(new Parada(22.750294,-102.519138,""));
        baseDatos.agregarParada(new Parada(22.752230,-102.518051,""));
        baseDatos.agregarParada(new Parada(22.749349,-102.519651,""));
        baseDatos.agregarParada(new Parada(22.753034,-102.516327,""));
        baseDatos.agregarParada(new Parada(22.749559,-102.515713,""));
        baseDatos.agregarParada(new Parada(22.750627,-102.520378,""));
        baseDatos.agregarParada(new Parada(22.754132,-102.519800,""));
        baseDatos.agregarParada(new Parada(22.749208,-102.522249,""));
        baseDatos.agregarParada(new Parada(22.751141,-102.524357,""));
        baseDatos.agregarParada(new Parada(22.750771,-102.52509,""));
        baseDatos.agregarParada(new Parada(22.751023,-102.525696,""));
        baseDatos.agregarParada(new Parada(22.753906,-102.524663,""));
        baseDatos.agregarParada(new Parada(22.754131,-102.524694,""));
        baseDatos.agregarParada(new Parada(22.748033,-102.519945,""));
        baseDatos.agregarParada(new Parada(22.748022,-102.520098,""));
        baseDatos.agregarParada(new Parada(22.750258,-102.514102,""));
        baseDatos.agregarParada(new Parada(22.750394,-102.514083,""));
        baseDatos.agregarParada(new Parada(22.750518,-102.513927,""));
        baseDatos.agregarParada(new Parada(22.751129,-102.513641,""));
        baseDatos.agregarParada(new Parada(22.751002,-102.512883,""));
        baseDatos.agregarParada(new Parada(22.751984,-102.513051,""));
        baseDatos.agregarParada(new Parada(22.752013,-102.512518,""));
        baseDatos.agregarParada(new Parada(22.753351,-102.511085,""));
        baseDatos.agregarParada(new Parada(22.755148,-102.509381,""));
        baseDatos.agregarParada(new Parada(22.755306,-102.509102,""));
        baseDatos.agregarParada(new Parada(22.761873,-102.51016,""));
        baseDatos.agregarParada(new Parada(22.763073,-102.509358,""));
        baseDatos.agregarParada(new Parada(22.765142,-102.509476,""));
        baseDatos.agregarParada(new Parada(22.767354,-102.508626,""));
        baseDatos.agregarParada(new Parada(22.76682,-102.508843,""));
        baseDatos.agregarParada(new Parada(22.768409,-102.508212,""));
        baseDatos.agregarParada(new Parada(22.770348,-102.507255,""));
        baseDatos.agregarParada(new Parada(22.77138,-102.506505,""));
        baseDatos.agregarParada(new Parada(22.770373,-102.503986,""));
        baseDatos.agregarParada(new Parada(22.769085,-102.500413,""));
        baseDatos.agregarParada(new Parada(22.76893,-102.500198,""));
        baseDatos.agregarParada(new Parada(22.767971,-102.50061,""));
        baseDatos.agregarParada(new Parada(22.766977,-102.501184,""));
        baseDatos.agregarParada(new Parada(22.7668,-102.500997,""));
        baseDatos.agregarParada(new Parada(22.765721,-102.501385,""));
        baseDatos.agregarParada(new Parada(22.76345,-102.50196,""));
        baseDatos.agregarParada(new Parada(22.761999,-102.50305,""));
        baseDatos.agregarParada(new Parada(22.761169,-102.503052,""));
        baseDatos.agregarParada(new Parada(22.759494,-102.502262,""));
        baseDatos.agregarParada(new Parada(22.760096,-102.503424,""));
        baseDatos.agregarParada(new Parada(22.758311,-102.50511,""));
        baseDatos.agregarParada(new Parada(22.757885,-102.505419,""));
        baseDatos.agregarParada(new Parada(22.757728,-102.504734,""));
        baseDatos.agregarParada(new Parada(22.756259,-102.50184,""));
        baseDatos.agregarParada(new Parada(22.755912,-102.501059,""));
        baseDatos.agregarParada(new Parada(22.755625,-102.500982,""));
        baseDatos.agregarParada(new Parada(22.755152,-102.499418,""));
        baseDatos.agregarParada(new Parada(22.754891,-102.498576,""));
        baseDatos.agregarParada(new Parada(22.754158,-102.497114,""));
        baseDatos.agregarParada(new Parada(22.757521,-102.497891,""));
        baseDatos.agregarParada(new Parada(22.756208,-102.494231,""));
        baseDatos.agregarParada(new Parada(22.756049,-102.49428,""));
        baseDatos.agregarParada(new Parada(22.755362,-102.490747,""));
        baseDatos.agregarParada(new Parada(22.756082,-102.483894,""));
        baseDatos.agregarParada(new Parada(22.760368,-102.483541,""));
        baseDatos.agregarParada(new Parada(22.761472,-102.489291,""));
        baseDatos.agregarParada(new Parada(22.761378,-102.489428,""));
        baseDatos.agregarParada(new Parada(22.762205,-102.497632,""));
        baseDatos.agregarParada(new Parada(22.762358,-102.494605,""));
        baseDatos.agregarParada(new Parada(22.755647,-102.478186,""));
        baseDatos.agregarParada(new Parada(22.755553,-102.478468,""));
        baseDatos.agregarParada(new Parada(22.756627,-102.478427,""));
        baseDatos.agregarParada(new Parada(22.757415,-102.47826,""));
        baseDatos.agregarParada(new Parada(22.758931,-102.478459,""));
        baseDatos.agregarParada(new Parada(22.759799,-102.478802,""));
        baseDatos.agregarParada(new Parada(22.760615,-102.47914,""));
        baseDatos.agregarParada(new Parada(22.760996,-102.478929,""));
        baseDatos.agregarParada(new Parada(22.761666,-102.479936,""));
        baseDatos.agregarParada(new Parada(22.768298,-102.480565,""));
        baseDatos.agregarParada(new Parada(22.768437,-102.482584,""));
        baseDatos.agregarParada(new Parada(22.769986,-102.481675,""));
        baseDatos.agregarParada(new Parada(22.769976,-102.481966,""));
        baseDatos.agregarParada(new Parada(22.771086,-102.484951,""));
        baseDatos.agregarParada(new Parada(22.770645,-102.488341,""));
        baseDatos.agregarParada(new Parada(22.775025,-102.485414,""));
        baseDatos.agregarParada(new Parada(22.783089,-102.49616,""));
    }

    /**
     * Realiza la inserción de las rutas existentes en la base de datos.
     */
    private void registrarRutas(){
        baseDatos.agregarRuta(new Ruta("Ruta 1","#02baed"));
        baseDatos.agregarRuta(new Ruta("Ruta 2 Zacatecas","#037b1a"));
        baseDatos.agregarRuta(new Ruta("Ruta 2 Guadalupe","#037b1a"));
        baseDatos.agregarRuta(new Ruta("Ruta 3","#6a6c69"));
        baseDatos.agregarRuta(new Ruta("Ruta 4","#292cb1"));
        baseDatos.agregarRuta(new Ruta("Ruta 5","#fee800"));
        baseDatos.agregarRuta(new Ruta("Ruta 6","#f7a4d0"));
        baseDatos.agregarRuta(new Ruta("Ruta 7","#8f0c12"));
        baseDatos.agregarRuta(new Ruta("Ruta 8","#de8239"));
        baseDatos.agregarRuta(new Ruta("Ruta 14","#a95942"));
        baseDatos.agregarRuta(new Ruta("Ruta 15","#7117aa"));
        baseDatos.agregarRuta(new Ruta("Ruta 16","#5e247b"));
        baseDatos.agregarRuta(new Ruta("Ruta 17","#44ba16"));
        baseDatos.agregarRuta(new Ruta("Transportes de Guadalupe","#8e180e"));
        baseDatos.agregarRuta(new Ruta("Transportes de Guadalupe Tierra y Libertad","#8e180e"));
    }

    /**
     * Realiza la inserción de la relación de una parada con una ruta en la base de datos.
     * @param idParada Identificador de la parada.
     * @param idRuta Identificador de la ruta.
     */
    private void insertarParadaRuta(Integer idParada, Integer idRuta) {
        baseDatos.agregarParadaRuta(new Parada(idParada),new Ruta(idRuta));
    }

    /**
     * Solicita la inserción de la relación de una parada con una ruta en la base de datos.
     */
    private void registrarParadaRutas() {
        insertarParadaRuta(1,1);
        insertarParadaRuta(2,1);
        insertarParadaRuta(3,1);
        insertarParadaRuta(4,1);
        insertarParadaRuta(5,1);
        insertarParadaRuta(6,1);

        insertarParadaRuta(7,1);
        insertarParadaRuta(7,13);

        insertarParadaRuta(8,1);
        insertarParadaRuta(8,13);

        insertarParadaRuta(9,1);
        insertarParadaRuta(10,1);
        insertarParadaRuta(11,1);

        insertarParadaRuta(12,1);
        insertarParadaRuta(12,13);

        insertarParadaRuta(13,1);
        insertarParadaRuta(13,13);

        insertarParadaRuta(14,14);
        insertarParadaRuta(15,14);
        insertarParadaRuta(16,8);
        insertarParadaRuta(17,14);
        insertarParadaRuta(18,14);
        insertarParadaRuta(19,14);

        insertarParadaRuta(20,1);
        insertarParadaRuta(20,8);
        insertarParadaRuta(20,13);
        insertarParadaRuta(20,14);

        insertarParadaRuta(21,1);
        insertarParadaRuta(21,13);
        insertarParadaRuta(21,14);

        insertarParadaRuta(22,1);
        insertarParadaRuta(22,13);
        insertarParadaRuta(22,14);

        insertarParadaRuta(23,1);
        insertarParadaRuta(23,13);
        insertarParadaRuta(23,14);

        insertarParadaRuta(24,1);
        insertarParadaRuta(24,13);
        insertarParadaRuta(24,14);

        insertarParadaRuta(25,8);
        insertarParadaRuta(26,14);
        insertarParadaRuta(26,14);
        insertarParadaRuta(27,10);
        insertarParadaRuta(28,10);
        insertarParadaRuta(29,10);
        insertarParadaRuta(30,1);
        insertarParadaRuta(31,1);
        insertarParadaRuta(32,10);
        insertarParadaRuta(33,6);
        insertarParadaRuta(34,6);
        insertarParadaRuta(35,6);
        insertarParadaRuta(36,6);
        insertarParadaRuta(37,6);
        insertarParadaRuta(38,6);
        insertarParadaRuta(39,6);
        insertarParadaRuta(40,6);
        insertarParadaRuta(41,6);
        insertarParadaRuta(42,6);
        insertarParadaRuta(43,6);
        insertarParadaRuta(44,10);
        insertarParadaRuta(45,1);
        insertarParadaRuta(46,1);
        insertarParadaRuta(47,1);
        insertarParadaRuta(48,1);
        insertarParadaRuta(49,1);
        insertarParadaRuta(50,1);
        insertarParadaRuta(51,1);
        insertarParadaRuta(52,10);
        insertarParadaRuta(53,10);
        insertarParadaRuta(54,10);
        insertarParadaRuta(55,10);
        insertarParadaRuta(56,6);
        insertarParadaRuta(57,9);
        insertarParadaRuta(58,9);

        insertarParadaRuta(59,1);
        insertarParadaRuta(59,13);
        insertarParadaRuta(59,14);

        insertarParadaRuta(60,1);
        insertarParadaRuta(60,13);
        insertarParadaRuta(60,14);

        insertarParadaRuta(61,14);
        insertarParadaRuta(62,14);
        insertarParadaRuta(63,5);
        insertarParadaRuta(64,5);
        insertarParadaRuta(65,5);
        insertarParadaRuta(66,5);
        insertarParadaRuta(67,5);
        insertarParadaRuta(68,5);
        insertarParadaRuta(69,5);

        insertarParadaRuta(70,6);
        insertarParadaRuta(71,6);
        insertarParadaRuta(72,5);
        insertarParadaRuta(73,6);

        insertarParadaRuta(74,5);
        insertarParadaRuta(74,6);

        insertarParadaRuta(75,5);

        insertarParadaRuta(76,5);
        insertarParadaRuta(76,6);

        insertarParadaRuta(77,5);
        insertarParadaRuta(78,6);
        insertarParadaRuta(79,5);
        insertarParadaRuta(80,6);
        insertarParadaRuta(81,5);
        insertarParadaRuta(82,6);
        insertarParadaRuta(83,6);
        insertarParadaRuta(84,5);
        insertarParadaRuta(85,5);
        insertarParadaRuta(86,5);
        insertarParadaRuta(87,5);
        insertarParadaRuta(88,5);
        insertarParadaRuta(89,5);
        insertarParadaRuta(90,5);
        insertarParadaRuta(91,5);
        insertarParadaRuta(92,5);
        insertarParadaRuta(93,5);
        insertarParadaRuta(94,6);
        insertarParadaRuta(95,6);
        insertarParadaRuta(96,6);
        insertarParadaRuta(97,10);
        insertarParadaRuta(98,10);
        insertarParadaRuta(99,10);
        insertarParadaRuta(100,1);
        insertarParadaRuta(101,1);
        insertarParadaRuta(102,5);
        insertarParadaRuta(103,1);
        insertarParadaRuta(104,6);
        insertarParadaRuta(105,1);
        insertarParadaRuta(106,6);
        insertarParadaRuta(107,10);
        insertarParadaRuta(108,6);

        insertarParadaRuta(109,6);
        insertarParadaRuta(109,10);

        insertarParadaRuta(110,6);
        insertarParadaRuta(110,10);

        insertarParadaRuta(111,10);
        insertarParadaRuta(112,1);
        insertarParadaRuta(113,10);
        insertarParadaRuta(114,1);

        insertarParadaRuta(115,8);
        insertarParadaRuta(115,9);

        insertarParadaRuta(116,9);
        insertarParadaRuta(117,9);
        insertarParadaRuta(118,9);

        insertarParadaRuta(119,1);
        insertarParadaRuta(119,6);
        insertarParadaRuta(119,8);
        insertarParadaRuta(119,9);
        insertarParadaRuta(119,10);

        insertarParadaRuta(120,9);

        insertarParadaRuta(121,6);
        insertarParadaRuta(121,10);

        insertarParadaRuta(122,2);
        insertarParadaRuta(122,5);

        insertarParadaRuta(123,8);
        insertarParadaRuta(123,9);

        insertarParadaRuta(124,8);
        insertarParadaRuta(125,5);

        insertarParadaRuta(126,8);
        insertarParadaRuta(126,9);

        insertarParadaRuta(127,8);
        insertarParadaRuta(128,8);

        insertarParadaRuta(129,8);
        insertarParadaRuta(129,9);

        insertarParadaRuta(130,8);
        insertarParadaRuta(130,9);

        insertarParadaRuta(131,8);
        insertarParadaRuta(132,8);
        insertarParadaRuta(133,8);
        insertarParadaRuta(134,9);
        insertarParadaRuta(135,8);
        insertarParadaRuta(136,8);
        insertarParadaRuta(137,8);
        insertarParadaRuta(138,8);
        insertarParadaRuta(139,5);
        insertarParadaRuta(140,3);
        insertarParadaRuta(141,5);
        insertarParadaRuta(142,5);
        insertarParadaRuta(143,5);
        insertarParadaRuta(144,5);
        insertarParadaRuta(145,5);
        insertarParadaRuta(146,3);
        insertarParadaRuta(147,3);
        insertarParadaRuta(148,3);
        insertarParadaRuta(149,3);
        insertarParadaRuta(150,3);
        insertarParadaRuta(151,3);
        insertarParadaRuta(152,8);
        insertarParadaRuta(153,8);
        insertarParadaRuta(154,8);

        insertarParadaRuta(155,8);
        insertarParadaRuta(156,8);
        insertarParadaRuta(157,2);
        insertarParadaRuta(158,2);
        insertarParadaRuta(159,2);
        insertarParadaRuta(160,2);
        insertarParadaRuta(161,2);
        insertarParadaRuta(162,2);
        insertarParadaRuta(163,2);
        insertarParadaRuta(164,2);
        insertarParadaRuta(165,2);
        insertarParadaRuta(166,2);
        insertarParadaRuta(167,2);
        insertarParadaRuta(168,2);
        insertarParadaRuta(169,2);
        insertarParadaRuta(170,2);
        insertarParadaRuta(171,2);

        insertarParadaRuta(172,8);
        insertarParadaRuta(172,14);

        insertarParadaRuta(173,2);
        insertarParadaRuta(174,8);

        insertarParadaRuta(175,8);
        insertarParadaRuta(175,13);
        insertarParadaRuta(175,14);

        insertarParadaRuta(176,1);
        insertarParadaRuta(176,8);
        insertarParadaRuta(176,13);
        insertarParadaRuta(176,14);

        insertarParadaRuta(177,5);

        insertarParadaRuta(178,4);
        insertarParadaRuta(178,5);
        insertarParadaRuta(178,12);
        insertarParadaRuta(178,13);

        insertarParadaRuta(179,8);
        insertarParadaRuta(180,1);

        insertarParadaRuta(181,5);
        insertarParadaRuta(181,8);

        insertarParadaRuta(182,1);
        insertarParadaRuta(182,2);
        insertarParadaRuta(182,5);
        insertarParadaRuta(183,8);
        insertarParadaRuta(184,1);
        insertarParadaRuta(184,2);
        insertarParadaRuta(184,5);
        insertarParadaRuta(184,8);
        insertarParadaRuta(185,5);
        insertarParadaRuta(186,8);
        insertarParadaRuta(187,1);
        insertarParadaRuta(188,8);
        insertarParadaRuta(189,5);
        insertarParadaRuta(190,5);
        insertarParadaRuta(191,12);
        insertarParadaRuta(191,14);
        insertarParadaRuta(192,4);
        insertarParadaRuta(192,12);
        insertarParadaRuta(192,14);
        insertarParadaRuta(193,4);
        insertarParadaRuta(193,12);
        insertarParadaRuta(194,12);
        insertarParadaRuta(195,13);
        insertarParadaRuta(195,14);
        insertarParadaRuta(196,4);
        insertarParadaRuta(197,14);
        insertarParadaRuta(198,13);
        insertarParadaRuta(199,4);
        insertarParadaRuta(199,13);
        insertarParadaRuta(200,9);
        insertarParadaRuta(200,10);
        insertarParadaRuta(200,12);
        insertarParadaRuta(200,13);
        insertarParadaRuta(200,14);
        insertarParadaRuta(201,4);
        insertarParadaRuta(202,1);
        insertarParadaRuta(202,2);
        insertarParadaRuta(202,6);
        insertarParadaRuta(202,8);
        insertarParadaRuta(202,9);
        insertarParadaRuta(203,1);
        insertarParadaRuta(203,2);
        insertarParadaRuta(203,5);
        insertarParadaRuta(203,6);
        insertarParadaRuta(203,8);
        insertarParadaRuta(204,2);
        insertarParadaRuta(205,14);
        insertarParadaRuta(206,4);
        insertarParadaRuta(206,10);
        insertarParadaRuta(206,12);
        insertarParadaRuta(206,13);
        insertarParadaRuta(207,6);
        insertarParadaRuta(208,10);
        insertarParadaRuta(208,12);
        insertarParadaRuta(208,13);
        insertarParadaRuta(208,14);
        insertarParadaRuta(209,4);
        insertarParadaRuta(210,10);
        insertarParadaRuta(211,12);
        insertarParadaRuta(211,13);
        insertarParadaRuta(211,14);
        insertarParadaRuta(212,4);
        insertarParadaRuta(212,10);
        insertarParadaRuta(212,12);
        insertarParadaRuta(212,13);
        insertarParadaRuta(212,14);
        insertarParadaRuta(212,15);
        insertarParadaRuta(213,4);
        insertarParadaRuta(213,10);
        insertarParadaRuta(213,12);
        insertarParadaRuta(213,13);
        insertarParadaRuta(213,14);
        insertarParadaRuta(214,4);
        insertarParadaRuta(214,10);
        insertarParadaRuta(214,12);
        insertarParadaRuta(214,13);
        insertarParadaRuta(214,14);
        insertarParadaRuta(215,6);
        insertarParadaRuta(215,9);
        insertarParadaRuta(216,6);
        insertarParadaRuta(217,2);
        insertarParadaRuta(217,5);
        insertarParadaRuta(218,6);
        insertarParadaRuta(218,9);
        insertarParadaRuta(219,9);
        insertarParadaRuta(220,9);
        insertarParadaRuta(221,9);
        insertarParadaRuta(222,9);
        insertarParadaRuta(223,9);
        insertarParadaRuta(224,1);
        insertarParadaRuta(224,8);
        insertarParadaRuta(225,8);
        insertarParadaRuta(225,9);
        insertarParadaRuta(226,1);
        insertarParadaRuta(227,2);
        insertarParadaRuta(227,5);
        insertarParadaRuta(227,6);
        insertarParadaRuta(228,5);
        insertarParadaRuta(229,1);
        insertarParadaRuta(229,2);
        insertarParadaRuta(230,5);
        insertarParadaRuta(231,4);
        insertarParadaRuta(232,4);
        insertarParadaRuta(233,4);
        insertarParadaRuta(234,4);
        insertarParadaRuta(235,4);
        insertarParadaRuta(236,4);
        insertarParadaRuta(237,4);
        insertarParadaRuta(238,4);
        insertarParadaRuta(239,4);
        insertarParadaRuta(240,4);
        insertarParadaRuta(241,4);
        insertarParadaRuta(242,4);
        insertarParadaRuta(243,10);
        insertarParadaRuta(243,12);
        insertarParadaRuta(243,13);
        insertarParadaRuta(243,14);
        insertarParadaRuta(244,10);
        insertarParadaRuta(244,12);
        insertarParadaRuta(244,13);
        insertarParadaRuta(244,14);
        insertarParadaRuta(244,15);
        insertarParadaRuta(245,10);
        insertarParadaRuta(245,12);
        insertarParadaRuta(245,13);
        insertarParadaRuta(245,14);
        insertarParadaRuta(246,10);
        insertarParadaRuta(246,12);
        insertarParadaRuta(246,13);
        insertarParadaRuta(246,14);
        insertarParadaRuta(247,4);
        insertarParadaRuta(248,4);
        insertarParadaRuta(249,4);
        insertarParadaRuta(250,4);
        insertarParadaRuta(251,3);
        insertarParadaRuta(251,12);
        insertarParadaRuta(251,13);
        insertarParadaRuta(251,14);
        insertarParadaRuta(252,3);
        insertarParadaRuta(253,3);
        insertarParadaRuta(253,10);
        insertarParadaRuta(254,3);
        insertarParadaRuta(254,10);
        insertarParadaRuta(255,3);
        insertarParadaRuta(256,3);
        insertarParadaRuta(256,12);
        insertarParadaRuta(256,13);
        insertarParadaRuta(256,14);
        insertarParadaRuta(257,3);
        insertarParadaRuta(257,12);
        insertarParadaRuta(257,13);
        insertarParadaRuta(257,14);
        insertarParadaRuta(258,4);
        insertarParadaRuta(259,4);
        insertarParadaRuta(260,13);
        insertarParadaRuta(261,4);
        insertarParadaRuta(262,4);
        insertarParadaRuta(263,10);
        insertarParadaRuta(264,10);
        insertarParadaRuta(265,4);
        insertarParadaRuta(266,4);
        insertarParadaRuta(267,4);
        insertarParadaRuta(268,4);
        insertarParadaRuta(269,10);
        insertarParadaRuta(270,10);
        insertarParadaRuta(271,10);
        insertarParadaRuta(272,10);
        insertarParadaRuta(273,10);
        insertarParadaRuta(274,10);
        insertarParadaRuta(275,10);
        insertarParadaRuta(276,3);
        insertarParadaRuta(276,12);
        insertarParadaRuta(276,13);
        insertarParadaRuta(276,14);
        insertarParadaRuta(277,3);
        insertarParadaRuta(277,12);
        insertarParadaRuta(277,13);
        insertarParadaRuta(278,4);
        insertarParadaRuta(279,4);
        insertarParadaRuta(280,4);
        insertarParadaRuta(281,4);
        insertarParadaRuta(282,4);
        insertarParadaRuta(283,4);
        insertarParadaRuta(284,4);
        insertarParadaRuta(285,4);
        insertarParadaRuta(286,4);
        insertarParadaRuta(287,4);
        insertarParadaRuta(288,3);
        insertarParadaRuta(288,12);
        insertarParadaRuta(288,13);
        insertarParadaRuta(288,14);
        insertarParadaRuta(289,3);
        insertarParadaRuta(289,12);
        insertarParadaRuta(289,13);
        insertarParadaRuta(289,14);
        insertarParadaRuta(290,3);
        insertarParadaRuta(290,12);
        insertarParadaRuta(290,13);
        insertarParadaRuta(290,14);
        insertarParadaRuta(291,3);
        insertarParadaRuta(291,12);
        insertarParadaRuta(291,13);
        insertarParadaRuta(291,14);
        insertarParadaRuta(292,10);
        insertarParadaRuta(293,10);
        insertarParadaRuta(294,10);
        insertarParadaRuta(295,10);
        insertarParadaRuta(296,12);
        insertarParadaRuta(296,13);
        insertarParadaRuta(296,15);
        insertarParadaRuta(297,3);
        insertarParadaRuta(297,12);
        insertarParadaRuta(297,13);
        insertarParadaRuta(297,14);
        insertarParadaRuta(298,3);
        insertarParadaRuta(298,12);
        insertarParadaRuta(298,13);
        insertarParadaRuta(299,3);
        insertarParadaRuta(299,12);
        insertarParadaRuta(299,13);
        insertarParadaRuta(299,15);
        insertarParadaRuta(300,12);
        insertarParadaRuta(300,13);
        insertarParadaRuta(301,3);
        insertarParadaRuta(302,12);
        insertarParadaRuta(302,13);
        insertarParadaRuta(303,12);
        insertarParadaRuta(303,13);
        insertarParadaRuta(304,12);
        insertarParadaRuta(304,13);
        insertarParadaRuta(305,12);
        insertarParadaRuta(306,12);

        insertarParadaRuta(307,13);
        insertarParadaRuta(308,12);
        insertarParadaRuta(308,13);
        insertarParadaRuta(309,12);
        insertarParadaRuta(309,13);
        insertarParadaRuta(310,12);
        insertarParadaRuta(310,13);
        insertarParadaRuta(311,13);
        insertarParadaRuta(312,7);
        insertarParadaRuta(313,13);
        insertarParadaRuta(314,13);
        insertarParadaRuta(315,13);
        insertarParadaRuta(316,13);
        insertarParadaRuta(317,13);
        insertarParadaRuta(318,10);
        insertarParadaRuta(319,10);
        insertarParadaRuta(319,14);
        insertarParadaRuta(320,10);
        insertarParadaRuta(321,10);
        insertarParadaRuta(322,10);
        insertarParadaRuta(323,10);
        insertarParadaRuta(324,10);
        insertarParadaRuta(325,10);
        insertarParadaRuta(326,10);
        insertarParadaRuta(327,10);
        insertarParadaRuta(328,10);
        insertarParadaRuta(329,10);
        insertarParadaRuta(330,10);
        insertarParadaRuta(331,10);
        insertarParadaRuta(332,14);
        insertarParadaRuta(333,10);
        insertarParadaRuta(334,14);
        insertarParadaRuta(335,14);
        insertarParadaRuta(336,14);
        insertarParadaRuta(337,10);
        insertarParadaRuta(338,14);
        insertarParadaRuta(339,11);
        insertarParadaRuta(340,14);
        insertarParadaRuta(341,7);
        insertarParadaRuta(341,14);
        insertarParadaRuta(342,11);
        insertarParadaRuta(343,11);
        insertarParadaRuta(344,11);
        insertarParadaRuta(345,11);
        insertarParadaRuta(346,11);
        insertarParadaRuta(347,11);
        insertarParadaRuta(348,11);
        insertarParadaRuta(349,11);
        insertarParadaRuta(350,11);
        insertarParadaRuta(351,7);
        insertarParadaRuta(352,7);
        insertarParadaRuta(353,7);
        insertarParadaRuta(354,7);
        insertarParadaRuta(355,7);
        insertarParadaRuta(356,7);
        insertarParadaRuta(357,7);
        insertarParadaRuta(358,7);
        insertarParadaRuta(359,7);
        insertarParadaRuta(360,14);
        insertarParadaRuta(361,7);
        insertarParadaRuta(362,7);
        insertarParadaRuta(363,7);
        insertarParadaRuta(364,7);
        insertarParadaRuta(365,7);
        insertarParadaRuta(366,7);
        insertarParadaRuta(367,7);
        insertarParadaRuta(368,10);
        insertarParadaRuta(368,14);
        insertarParadaRuta(369,7);
        insertarParadaRuta(369,11);
        insertarParadaRuta(370,10);
        insertarParadaRuta(370,11);
        insertarParadaRuta(371,10);
        insertarParadaRuta(372,10);
        insertarParadaRuta(373,11);
        insertarParadaRuta(373,14);
        insertarParadaRuta(374,11);
        insertarParadaRuta(374,14);
        insertarParadaRuta(375,14);
        insertarParadaRuta(376,11);
        insertarParadaRuta(376,14);
        insertarParadaRuta(377,11);
        insertarParadaRuta(378,7);
        insertarParadaRuta(378,11);
        insertarParadaRuta(379,14);
        insertarParadaRuta(380,7);
        insertarParadaRuta(381,14);
        insertarParadaRuta(382,3);
        insertarParadaRuta(383,7);
        insertarParadaRuta(384,3);
        insertarParadaRuta(385,7);
        insertarParadaRuta(386,14);
        insertarParadaRuta(387,3);
        insertarParadaRuta(388,14);
        insertarParadaRuta(389,14);
        insertarParadaRuta(390,7);
        insertarParadaRuta(390,10);
        insertarParadaRuta(391,7);
        insertarParadaRuta(391,10);
        insertarParadaRuta(391,14);
        insertarParadaRuta(392,14);
        insertarParadaRuta(393,3);
        insertarParadaRuta(394,7);
        insertarParadaRuta(394,10);
        insertarParadaRuta(395,7);
        insertarParadaRuta(395,10);
        insertarParadaRuta(395,14);
        insertarParadaRuta(396,7);
        insertarParadaRuta(396,11);
        insertarParadaRuta(397,11);
        insertarParadaRuta(398,7);
        insertarParadaRuta(399,3);
        insertarParadaRuta(400,3);
        insertarParadaRuta(401,3);
        insertarParadaRuta(402,7);
        insertarParadaRuta(402,11);
        insertarParadaRuta(403,7);
        insertarParadaRuta(403,11);
        insertarParadaRuta(404,7);
        insertarParadaRuta(404,11);
        insertarParadaRuta(405,7);
        insertarParadaRuta(405,11);
        insertarParadaRuta(406,7);
        insertarParadaRuta(407,7);
        insertarParadaRuta(408,7);
        insertarParadaRuta(409,7);
        insertarParadaRuta(410,7);
        insertarParadaRuta(411,7);
        insertarParadaRuta(412,7);
        insertarParadaRuta(413,7);
        insertarParadaRuta(414,7);
        insertarParadaRuta(415,7);
        insertarParadaRuta(416,7);
        insertarParadaRuta(416,12);
        insertarParadaRuta(417,12);
        insertarParadaRuta(418,12);
        insertarParadaRuta(419,7);
        insertarParadaRuta(419,7);
        insertarParadaRuta(420,7);
        insertarParadaRuta(420,12);
        insertarParadaRuta(421,12);
        insertarParadaRuta(422,12);
        insertarParadaRuta(423,7);
        insertarParadaRuta(424,12);
        insertarParadaRuta(425,12);
        insertarParadaRuta(426,7);
        insertarParadaRuta(427,11);
        insertarParadaRuta(427,12);
        insertarParadaRuta(428,11);
        insertarParadaRuta(428,12);
        insertarParadaRuta(429,11);
        insertarParadaRuta(429,12);
        insertarParadaRuta(430,11);
        insertarParadaRuta(430,12);
        insertarParadaRuta(431,13);
        insertarParadaRuta(432,12);
        insertarParadaRuta(433,12);
        insertarParadaRuta(434,12);
        insertarParadaRuta(435,11);
        insertarParadaRuta(436,11);
        insertarParadaRuta(437,12);
        insertarParadaRuta(438,13);
        insertarParadaRuta(439,13);
        insertarParadaRuta(440,11);
        insertarParadaRuta(441,11);
        insertarParadaRuta(442,11);
        insertarParadaRuta(443,12);
        insertarParadaRuta(444,11);
        insertarParadaRuta(445,13);
        insertarParadaRuta(446,13);
        insertarParadaRuta(447,13);
        insertarParadaRuta(448,13);
        insertarParadaRuta(449,13);
        insertarParadaRuta(450,13);
        insertarParadaRuta(451,13);
        insertarParadaRuta(452,13);
        insertarParadaRuta(453,11);
        insertarParadaRuta(453,13);
        insertarParadaRuta(454,13);
        insertarParadaRuta(455,13);
        insertarParadaRuta(456,13);
        insertarParadaRuta(457,11);
        insertarParadaRuta(458,11);
        insertarParadaRuta(459,11);
        insertarParadaRuta(460,11);
        insertarParadaRuta(461,12);
    }
}
