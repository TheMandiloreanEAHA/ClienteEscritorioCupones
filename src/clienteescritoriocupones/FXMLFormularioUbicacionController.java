package clienteescritoriocupones;

import clienteescritoriocupones.modelo.dao.UbicacionDAO;
import clienteescritoriocupones.modelo.pojo.Estado;
import clienteescritoriocupones.modelo.pojo.Mensaje;
import clienteescritoriocupones.modelo.pojo.Municipio;
import clienteescritoriocupones.modelo.pojo.Ubicacion;
import clienteescritoriocupones.utils.Utilidades;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import javafx.scene.layout.Pane;



public class FXMLFormularioUbicacionController  implements Initializable {

    private Ubicacion ubicacion;

    @FXML
    private JFXButton btnCerrar;
    @FXML
    private JFXTextField tfCalle;
    @FXML
    private JFXTextField tfNumero;
     @FXML
    private JFXTextField tfCodigoPostal;
    @FXML
    private JFXButton btnGuardarDatos;
    @FXML
    private JFXTextField tfLatitud;
    @FXML
    private JFXTextField tfLongitud;
    @FXML
    private JFXTextField tfColonia;
    @FXML
    private JFXComboBox<Estado> cbEstados;
    @FXML
    private JFXComboBox<Municipio> cbMunicipios;
    @FXML
    private WebView pnMapa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEstadosCB();
        inicializarMapa();

    }

    private void inicializarMapa() {
     pnMapa.setPrefSize(800, 600);

     WebEngine webEngine = pnMapa.getEngine();

        // Obtener la URL del archivo HTML en el mismo paquete
        String htmlFilePath = getClass().getResource("/clienteescritoriocupones/map/googlemap.html").toExternalForm();
        inicializarComunicacion();
        // Cargar el contenido HTML en el WebView
        webEngine.load(htmlFilePath);

}
    private void inicializarComunicacion() {
        WebEngine webEngine = pnMapa.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {
                        // Configurar la interfaz entre JavaScript y JavaFX
                        JSObject window = (JSObject) webEngine.executeScript("window");
                        window.setMember("javaConnector", new JavaConnector());
                    }
                });
    }
    public class JavaConnector {

       public void enviarInformacionMarcador(double latitud, double longitud, String calle, String cp) {
        Platform.runLater(() -> {
            // Aquí puedes realizar acciones en JavaFX con la información del marcador
            // por ejemplo, mostrar información en los campos de texto
            // o realizar cualquier otra lógica que necesites.
            // Puedes acceder a los campos de tu controlador de JavaFX desde aquí.
            // Ejemplo:
            tfLatitud.setText(Double.toString(latitud));
            tfLongitud.setText(Double.toString(longitud));
            tfCalle.setText(calle);
            tfCodigoPostal.setText(cp);
        });
    }
    }
    public void inicializarUbicacion(Ubicacion ubicacion){
        this.ubicacion = ubicacion;
        if(ubicacion == null){
            System.out.println("La ubicacion es nula");
        }else{
            System.out.print(ubicacion.getCalle());
        }

    }

    private void cargarEstadosCB(){
        //Obtener los estados
        List<Estado> estados = UbicacionDAO.obtenerEstados();
        //Limpiar el combo antes de agregar los estados
        cbEstados.getItems().clear();
        //Agregar los estados:
        cbEstados.getItems().addAll(estados);
    }


    private List<Municipio> cargarMunicipios(Estado estado){
        return UbicacionDAO.obtenerMunicipioEstado(estado.getIdEstado());
    }

    //Funcion para validar que todos los campos esten vacios
    private boolean esValido(
            String calle,
            String colonia,
            String numero,
            String codigoPostal,
            String latitud,
            String longitud,
            Estado estado,
            Municipio municipio){
        //Variable que cambiara su valor en caso de que algún campo este vacio
        boolean esValido = true;

        //Comienzan las validaciones
        if(calle.isEmpty()){
            //lbAlert_calle.setText("*Campo oblogatorio");
            esValido = false;
        }
       if(colonia.isEmpty()){
            //lbAlert_colonia.setText("*Campo oblogatorio");
            esValido = false;
        }
       if(numero.isEmpty()){
            //lbAlert_numero.setText("*Campo oblogatorio");
            esValido = false;
        }
       if(codigoPostal.isEmpty()){
            //lbAlert_cp.setText("*Campo oblogatorio");
            esValido = false;
        }
       if(longitud.isEmpty()){
            //lbAlert_municipio.setText("*Campo oblogatorio");
            esValido = false;
        }
       if(latitud.isEmpty()){
            //lbAlert_municipio.setText("*Campo oblogatorio");
            esValido = false;
        }
       if(estado == null){
            //lbAlert_estado.setText("*Campo oblogatorio");
            esValido = false;
        }
       if(municipio == null){
            //lbAlert_municipio.setText("*Campo oblogatorio");
            esValido = false;
        }

        return esValido;
    }

    private void mandarUbicacion(Ubicacion ubi){
         //Cargar las vistas a memoria
        FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLFormularioSucursal.fxml"));
        //Parent vista = loadMain.load();
        //Cargamos la información
        FXMLFormularioSucursalController formSucursalController = loadMain.getController();
        formSucursalController.inicializarUbicacion(ubi);
    }


    @FXML
    private void cerrarVentana(ActionEvent event) {
        cerrarVentana();
    }
    private void cerrarVentana() {
        Stage escenario = (Stage) btnCerrar.getScene().getWindow();
        escenario.close();
    }

    @FXML
    private void btnGuardarCambios(ActionEvent event) {
        String calle = tfCalle.getText();
        String colonia = tfColonia.getText();
        String numero = tfNumero.getText();
        String codigoPostal = tfCodigoPostal.getText();
        String latitud = tfLatitud.getText();
        String longitud = tfLongitud.getText();
        Estado estado = cbEstados.getValue();
        Municipio municipio = cbMunicipios.getValue();
        boolean esValido = esValido(calle, colonia, numero, codigoPostal, latitud, longitud, estado, municipio);
        Ubicacion ubi = new Ubicacion();

        if(esValido){
            ubi.setCalle(calle);
            ubi.setColonia(colonia);
            ubi.setNumero(numero);
            ubi.setCodigoPostal(codigoPostal);
            ubi.setLatitud(latitud);
            ubi.setLongitud(longitud);
            ubi.setIdMunicipio(municipio.getIdMunicipio());
            ubi.setIdEstado(municipio.getIdEstado());
            ubi.setEstado(estado.getNombre());
            ubi.setMunicipio(municipio.getNombre());
            mandarUbicacion(ubi);

            Utilidades.mostrarAlertaSimple("Información Guardada", "La información de la ubicación guardada con éxito", Alert.AlertType.ERROR);
            cerrarVentana();

        }else{
            Utilidades.mostrarAlertaSimple("Información incompleta", "Asegurese de llenar todos los campos solicitados", Alert.AlertType.ERROR);
        }

    }

    @FXML
    private void comboListenerEstado(ActionEvent event){

        Estado estadoSeleccionado = cbEstados.getValue();
        if(estadoSeleccionado != null){
            //obtener la lista de los municipios de ese estado:
            List<Municipio> municipios = UbicacionDAO.obtenerMunicipioEstado(estadoSeleccionado.getIdEstado());
            //Limpiar los datos del combo por si las moscas
            cbMunicipios.getItems().clear();
            //Agregar los municipios al comboBox
            cbMunicipios.getItems().addAll(municipios);
        }

    }

}
