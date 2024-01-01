package clienteescritoriocupones;

import clienteescritoriocupones.interfaz.IRespuesta;
import clienteescritoriocupones.interfaz.IUbicacion;
import clienteescritoriocupones.modelo.dao.SucursalDAO;
import clienteescritoriocupones.modelo.dao.UbicacionDAO;
import clienteescritoriocupones.modelo.pojo.Coordenada;
import clienteescritoriocupones.modelo.pojo.Mensaje;
import clienteescritoriocupones.modelo.pojo.Sucursal;
import clienteescritoriocupones.modelo.pojo.Ubicacion;
import clienteescritoriocupones.utils.Constantes;
import clienteescritoriocupones.utils.Utilidades;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXMLFormularioSucursalController implements Initializable, IUbicacion {
    
    private int idEmpresa;
    
    private Ubicacion ubicacion;
    
    private Sucursal sucursal;
    
    private IRespuesta observador;
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    @FXML
    private JFXButton btnCerrar;
    @FXML
    private JFXTextField tfNombre;
    @FXML
    private JFXTextField tfTelefono;
    @FXML
    private JFXButton btnRegistrarSucursal;
    @FXML
    private JFXTextField tfNombreEncargado;
    @FXML
    private JFXButton btnUbi;
    @FXML
    private Label lbTitulo;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnCerrar.setGraphic(new ImageView(Constantes.imagenCerrar));
    } 
    
    public void inicializarIdEmpresa(int idEmpresa, IRespuesta observador){
        this.idEmpresa = idEmpresa;
        this.observador = observador;
        System.out.println("IdEmpresa: " + this.idEmpresa);
        
              
    }
    //--- Método que se ejecuta cuando se modifica la sucursal
    public void inicializarSucursal(Sucursal sucursal){
        btnRegistrarSucursal.setText("Guardar Cambios");
        lbTitulo.setText("Editar Sucursal");
        this.sucursal = sucursal;        
        mostrarInfoSucursal(this.sucursal);      
        //Obtenemos la información de la ubicación de la sucursal
        
        HashMap<String, Object> respuesta = UbicacionDAO.buscarUbicacionPorId(this.sucursal.getIdUbicacion());
        if(!(boolean)respuesta.get("error")){
            this.ubicacion = (Ubicacion)respuesta.get("ubicacion");
        }else{
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }        
    }
    
    private void mostrarInfoSucursal(Sucursal sucrusal){
        tfNombre.setText(sucrusal.getNombre());
        tfNombreEncargado.setText(sucrusal.getEncargado());
        tfTelefono.setText(sucrusal.getTelefono());
        System.out.println("ID sucursal: "+sucursal.getIdSucursal());
    }
    
    @FXML
    public void cerrarVentana(ActionEvent actionEvent) {
        cerrarVentana();
    }
    private void cerrarVentana() {
        Stage escenario = (Stage) btnCerrar.getScene().getWindow();
        escenario.close();
    }

    @FXML
    private void btnRegistrar(ActionEvent event) {
        boolean isValido = validarCampos();
         String nombre = tfNombre.getText();
         String encargado = tfNombreEncargado.getText();
         String telefono = tfTelefono.getText(); 
         Sucursal suc = new Sucursal();
         
         if (isValido) {       
            suc.setNombre(nombre);
            suc.setEncargado(encargado);
            suc.setTelefono(telefono);
            suc.setIdEmpresa(idEmpresa);
            System.out.println("IdEpresaSUC:" + suc.getIdEmpresa());
            if(btnRegistrarSucursal.getText().equals("Guardar Cambios")){
                //Asignarle el id a la sucursla a modificar
                suc.setIdSucursal(sucursal.getIdSucursal()); 
                //Aquí se debe obtener el id del domicilio para asignarle una ubicación al domicilio                
                modificarSucursal(suc);
            }else{
                if(ubicacion != null){
                    suc.setIdUbicacion(registrarUbicacion(ubicacion)); //Se debe obtener el id de la ibicación
                    System.out.println("IdUbicacion de la sucursal. " + suc.getIdUbicacion());
                    registrarSucursal(suc);
                }else{
                    Utilidades.mostrarAlertaSimple("Ubicación requerida", "Por favor, presione el botón de ubicación y llene los campos requeridos. Asrgurese de guardar los datos", Alert.AlertType.ERROR);
                }
                
            }           
               
        }
        
        
    }
    
    private boolean validarCampos(){
        boolean isValido = true;
        if (tfNombre.getText().isEmpty() && tfNombreEncargado.getText().isEmpty() && tfTelefono.getText().isEmpty()) {
                Utilidades.mostrarAlertaSimple("Campos completos", "Ingrese todos los campos solicitados, por favor", Alert.AlertType.ERROR);
                isValido = false;
            } else if (tfNombre.getText().isEmpty()) {
                Utilidades.mostrarAlertaSimple("Campo Incompleto", "Nombre de sucursal requerida", Alert.AlertType.ERROR);
                isValido = false;
            } else if (tfNombreEncargado.getText().isEmpty()) {
                Utilidades.mostrarAlertaSimple("Campo Incompleto", "Nombre del encargado de la sucursal requerido", Alert.AlertType.ERROR);
                isValido = false;
            } else if (tfTelefono.getText().isEmpty()) {
                Utilidades.mostrarAlertaSimple("Campo Incompleto", "Teléfono de la sucursal requerido", Alert.AlertType.ERROR);
                isValido = false;
            }
        
        return isValido;
    }
    
    private void registrarSucursal(Sucursal sucursal){
        Mensaje msj = SucursalDAO.agregarSucursal(sucursal);
        if(!msj.getError()){
            Utilidades.mostrarAlertaSimple("Sucursal registrada", msj.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notificarGuardado();
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar la Sucursal", msj.getMensaje(), Alert.AlertType.ERROR);
        }
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
    
    private void modificarSucursal(Sucursal sucursal){
        Mensaje msj = SucursalDAO.modificarSucursal(sucursal);
        if(!msj.getError()){
            Utilidades.mostrarAlertaSimple("Sucursal Modifcada", msj.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notificarGuardado();
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al modificar la Sucursal", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnUbicacion(ActionEvent event) { //Abre el formulario de la ubicación
        try{
            //Cargar las vistas a memoria
            FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLFormularioUbicacion.fxml"));
            Parent vista = loadMain.load();
            
            //Cargamos la información
            FXMLFormularioUbicacionController formUbiController = loadMain.getController();
            formUbiController.inicializarUbicacion(ubicacion, this);
            
            Stage stageNuevo = new Stage();
            Scene escena = new Scene(vista);
            
            stageNuevo.initStyle(StageStyle.DECORATED.UNDECORATED);
            vista.setOnMousePressed(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event){
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }

            });
            vista.setOnMouseDragged(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event){
                    stageNuevo.setX(event.getScreenX() -xOffset);
                    stageNuevo.setY(event.getScreenY() -yOffset);
                }

            });

            stageNuevo.setScene(escena);
            stageNuevo.setTitle("Ubicación");
            stageNuevo.initModality(Modality.APPLICATION_MODAL); //Configuracion que nos ayuda a elegir el control de las pantallas. No perimte que otro stage tenga el control hasta que se cierre el stage actual
            stageNuevo.showAndWait(); //Bloquea la pantalla de atras 
            
        }catch(IOException ex){
             ex.printStackTrace();
        }
    }

    @Override
    public void devolverUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
        
    }
    
    
}
