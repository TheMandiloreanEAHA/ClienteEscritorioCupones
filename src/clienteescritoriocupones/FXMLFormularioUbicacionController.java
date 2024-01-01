package clienteescritoriocupones;

import clienteescritoriocupones.interfaz.IUbicacion;
import clienteescritoriocupones.modelo.dao.EmpresaDAO;
import clienteescritoriocupones.modelo.dao.UbicacionDAO;
import clienteescritoriocupones.modelo.pojo.Coordenada;
import clienteescritoriocupones.modelo.pojo.Empresa;
import clienteescritoriocupones.modelo.pojo.Estado;
import clienteescritoriocupones.modelo.pojo.Mensaje;
import clienteescritoriocupones.modelo.pojo.Municipio;
import clienteescritoriocupones.modelo.pojo.Ubicacion;
import clienteescritoriocupones.utils.Constantes;
import clienteescritoriocupones.utils.Utilidades;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;



public class FXMLFormularioUbicacionController  implements Initializable {

    private Ubicacion ubicacion;
    
    private IUbicacion intertfazUbicacion;
    
    private Empresa empresa;

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
    @FXML
    private JFXButton btnLongitud;
    @FXML
    private JFXButton btnLatitud;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCerrar.setGraphic(new ImageView(Constantes.imagenCerrar));
        cargarEstadosCB();
        inicializarMapa();

    }

    private void inicializarMapa() {
        pnMapa.setPrefSize(800, 600);

        WebEngine webEngine = pnMapa.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                // Configurar el entorno del WebView para permitir el acceso al portapapeles
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("java", this);

                // Desactivar la prevención de copia en el documento
                webEngine.executeScript("document.addEventListener('copy', function(e) {e.preventDefault();});");

                // Definir funciones de Java que estarán disponibles en JavaScript
                webEngine.executeScript("function copyToClipboard(data) {" +
                        "   java.copyToClipboard(data);" +
                        "}");
            }
        });

        // Obtener la URL del archivo HTML en el mismo paquete
        String htmlFilePath = getClass().getResource("/clienteescritoriocupones/map/googlemap.html").toExternalForm();
        // Cargar el contenido HTML en el WebView
        webEngine.load(htmlFilePath);

    }
    public void copyToClipboard(String data) {
        Platform.runLater(() -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(data);
            clipboard.setContent(content);
        });
    }
    
    public void inicializarUbicacion(Ubicacion ubicacion, IUbicacion interfazUbicacion){
        this.ubicacion = ubicacion;
        this.intertfazUbicacion = interfazUbicacion;
        if(this.ubicacion != null){
            System.out.println("Si pasó la ubicación :3");
            llenarCampos(this.ubicacion);
            bloquearCampos();
        }else{
            System.out.println("La ubicacion es nula");
        }
    }
    
    public void inializarEmpresa(Empresa empresa){
        btnGuardarDatos.setText("Asignar Ubicación");
        if(empresa.getIdUbicacion() != null){
            System.out.println("La empresa tiene una ubicación asignada");
             HashMap<String, Object> respuesta = UbicacionDAO.buscarUbicacionPorId(empresa.getIdUbicacion());
             if(!(boolean)respuesta.get("error")){
                ubicacion = (Ubicacion)respuesta.get("ubicacion");
                llenarCampos(ubicacion);
                bloquearCampos();
             }else{
                 Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
             }             
        }else{
            System.out.println("La empresa NO tiene una ubicación asignada");
            this.empresa = empresa;
            
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
        System.out.println(ubi.getCalle() + " " + ubi.getLatitud() + " ID Municipio: " + ubi.getIdMunicipio());
        intertfazUbicacion.devolverUbicacion(ubi);
    }
    
    private void bloquearCampos(){
        tfCalle.setDisable(true);
        tfColonia.setDisable(true);
        tfNumero.setDisable(true);
        tfCodigoPostal.setDisable(true);
        tfLongitud.setDisable(true);
        tfLatitud.setDisable(true);
        btnLongitud.setDisable(true);
        btnLatitud.setDisable(true);
        cbEstados.setDisable(true);
        cbMunicipios.setDisable(true);
        btnGuardarDatos.setVisible(false);
    }
    
    private void llenarCampos(Ubicacion ubi){
        tfCalle.setText(ubi.getCalle());
        tfColonia.setText(ubi.getColonia());
        tfNumero.setText(ubi.getNumero());
        tfCodigoPostal.setText(ubi.getCodigoPostal());
        tfLatitud.setText(ubi.getLatitud());
        tfLongitud.setText(ubi.getLongitud());
        
        if(ubi.getIdEstado() == null){
            Municipio mun = UbicacionDAO.obtenerMunicipioPorId(ubi.getIdMunicipio());
            ubi.setIdEstado(mun.getIdEstado());
            System.out.println("IDMun:"+ubi.getIdMunicipio());
            System.out.println("IDEstado:"+ubi.getIdEstado());
        }        
        
        for(Estado estado: cbEstados.getItems()){
            if(estado.getIdEstado() == ubi.getIdEstado()){
                cbEstados.setValue(estado);
                break;
            }
        }
        
        List<Municipio> municipios = UbicacionDAO.obtenerMunicipioEstado(ubi.getIdEstado());
        cbMunicipios.getItems().clear();
        cbMunicipios.getItems().addAll(municipios);
        for(Municipio municipio: municipios){
            if(municipio.getIdMunicipio() == ubicacion.getIdMunicipio()){
                cbMunicipios.setValue(municipio);
                break;
            }
        }
        

    }

    private void cerrarVentana() {
        Stage escenario = (Stage) btnCerrar.getScene().getWindow();
        escenario.close();
    }
    
    private int registrarUbicacion(Ubicacion ubicacion){
        int idUbicacion;        
        Mensaje msj = UbicacionDAO.agregarUbicacion(ubicacion);
        if(!msj.getError()){
            //Si si se regisró la ubicación, realizar nuevamente la consulta para obtener su ID y así registrar la sucursal
            Utilidades.mostrarAlertaSimple("Ubicacion registrada", msj.getMensaje(), Alert.AlertType.INFORMATION);
            Coordenada coordenada = new Coordenada(ubicacion.getLatitud(), ubicacion.getLongitud());
            idUbicacion = UbicacionDAO.obtenerUbicacionCoordenadas(coordenada); 
            //cerrarVentana();  
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar la ubicacion", msj.getMensaje(), Alert.AlertType.ERROR);
            return 0;
        }
        
        return idUbicacion;
    }
    
    

    private void asignarUbicacionEmpresa(Empresa empr){
        Mensaje msj = EmpresaDAO.modificarEmpresa(empresa);
        if(!msj.getError()){
            Utilidades.mostrarAlertaSimple("Ubicación asignada a la Empresa " + empresa.getNombreComercial(), msj.getMensaje(), Alert.AlertType.INFORMATION);
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al adignar la ubicación a la Empresa", msj.getMensaje(), Alert.AlertType.ERROR);
        }  
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
            
            if(btnGuardarDatos.getText().equals("Asignar Ubicación")){
                System.out.println("Aquí se registra la ubicación y se le asigna a la empresa");
                empresa.setIdUbicacion(registrarUbicacion(ubi));
                System.out.println("IdUbicacion de la Empresa: " + empresa.getIdUbicacion());                
                asignarUbicacionEmpresa(empresa);
                
            }else{
                mandarUbicacion(ubi);
                Utilidades.mostrarAlertaSimple("Información Guardada", "La información de la ubicación guardada con éxito", Alert.AlertType.INFORMATION);
                cerrarVentana();
            }            

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

    @FXML
    public void copiarLongitud(ActionEvent actionEvent) {
        Clipboard clipboard = Clipboard.getSystemClipboard();

        // Verifica si el contenido del portapapeles es de tipo String
        if (clipboard.hasString()) {
            // Obtiene el contenido del portapapeles y lo pega en el TextField
            String clipboardContent = clipboard.getString();
            tfLongitud.setText(clipboardContent);
        }
    }

    @FXML
    public void copiarLatitud(ActionEvent actionEvent) {
        Clipboard clipboard = Clipboard.getSystemClipboard();

        // Verifica si el contenido del portapapeles es de tipo String
        if (clipboard.hasString()) {
            // Obtiene el contenido del portapapeles y lo pega en el TextField
            String clipboardContent = clipboard.getString();
            tfLatitud.setText(clipboardContent);
        }
    }

    @FXML
    public void btnCerrarVentana(ActionEvent actionEvent) {
        cerrarVentana();
    }
}
