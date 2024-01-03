package clienteescritoriocupones;

import clienteescritoriocupones.interfaz.IRespuesta;
import clienteescritoriocupones.modelo.dao.InicioSesionDAO;
import clienteescritoriocupones.modelo.pojo.Empleado;
import clienteescritoriocupones.modelo.pojo.RespuestaLogin;
import clienteescritoriocupones.utils.Constantes;
import clienteescritoriocupones.utils.Utilidades;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLHomeController implements Initializable, IRespuesta {
    
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;
    @FXML
    private JFXButton btnInicio;
    @FXML
    private JFXButton btnEmpresa;
    @FXML
    private JFXButton btnSucursales;
    @FXML
    private JFXButton btnEmpleados;
    @FXML
    private JFXButton btnPromociones;
    @FXML
    private JFXButton btnCerrarSesion;
    @FXML
    private Label lbNombreUsuario;
    @FXML
    private Label lbNombreEmpresa;
    @FXML
    private Label lbRFCEmpresa;
    @FXML
    private Label lbNombreComercial;
    @FXML
    private ImageView imgEmpresa;
    @FXML
    private Label lbNombreEmpleado;
    @FXML
    private Label lbCorreo;
    @FXML
    private Label lbApellidosEmpleado;
    @FXML
    private JFXButton btnEditarPerfil;
    @FXML
    private JFXButton btnCanjeCupones;
    
    private Empleado empleadoSesion;
    @FXML
    private Label lbRFCEmpresa1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    public void inicializarEmpleado(Empleado empleadoSesion){
        this.empleadoSesion = empleadoSesion;
        lbNombreUsuario.setText(empleadoSesion.getNombreUsuario());
        lbNombreEmpleado.setText(empleadoSesion.getNombre());
        lbApellidosEmpleado.setText(empleadoSesion.getApellidoPaterno() + " "+ empleadoSesion.getApellidoMaterno());
        lbCorreo.setText(empleadoSesion.getCorreo());
        //Si es un administrador de empresa
        if(this.empleadoSesion.getIdEmpresa() != null){
            lbNombreEmpresa.setText(empleadoSesion.getNombreEmpresa());
            lbRFCEmpresa.setText(empleadoSesion.getRFC());
            lbNombreComercial.setText(empleadoSesion.getNombreComercial());
            btnEmpresa.setDisable(true);
            btnEmpleados.setDisable(true);
        }else{ //SI no, es un administrador general
            lbNombreEmpresa.setText("Administrador General");
            lbRFCEmpresa.setText("Accesso TOTAL");
            //lbNombreComercial.setText(empleadoSesion.getNombreComercial());        
        }
        
        
        
    }

    @FXML
    private void btnInicio(MouseEvent event) {
        bp.setCenter(ap);
    }

    @FXML
    private void btnEmpresas(MouseEvent event) throws IOException {
        cargarVista("FXMLAdminEmpresas");
        
    }

    @FXML
    private void btnSucursales(MouseEvent event) {
        cargarVista("FXMLAdminSucursales");
        
    }

    @FXML
    private void btnEmpleados(MouseEvent event) {
        cargarVista("FXMLAdminEmpleados");
    }

    @FXML
    private void btnPromociones(MouseEvent event) {
        cargarVista("FXMLAdminPromociones");
    }

    @FXML
    private void btnCupones(MouseEvent event) {
    }

    private void cargarVista(String vista) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(vista + ".fxml"));
            Parent root = loader.load();
            if (vista.equals("FXMLAdminSucursales") ) {
                FXMLAdminSucursalesController controladorAdminSuc = loader.getController();
                if(empleadoSesion.getIdEmpresa() == null){ //Si el id de la empresa es null, significa que es un administrador general
                    controladorAdminSuc.inicializarIdEmpresa(0);
                }else{ //De lo contrario, es un administrador comercial y se debe mandar su id
                    controladorAdminSuc.inicializarIdEmpresa(empleadoSesion.getIdEmpresa());
                }
                
            }else if(vista.equals("FXMLAdminEmpleados")){
                FXMLAdminEmpleadosController controladorAdminEmp = loader.getController();
                if(empleadoSesion.getIdEmpresa() == null){ //Si el id de la empresa es null, significa que es un administrador general
                    controladorAdminEmp.inicializarIdEmpresa(0);
                }else{ //De lo contrario, es un administrador comercial y se debe mandar su id
                    controladorAdminEmp.inicializarIdEmpresa(empleadoSesion.getIdEmpresa());
                }                
            }

            bp.setCenter(root);
        } catch (IOException ex) {
            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    private void btnEditarPerfil(MouseEvent event) {
        try{
            //Cargar las vistas a memoria
            FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLFormularioEmpleado.fxml"));
            Parent vista = loadMain.load();
             
            //Cargamos la información de la sucursal
            FXMLFormularioEmpleadoController formEmpController = loadMain.getController();
            //Pasar la info de la sucursal
            formEmpController.inicializarEmpleado(empleadoSesion, this);

            //Creamos un nuevo stage
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
            stageNuevo.setTitle("Datos empleado");
            stageNuevo.initModality(Modality.APPLICATION_MODAL); //Configuracion que nos ayuda a elegir el control de las pantallas. No perimte que otro stage tenga el control hasta que se cierre el stage actual
            stageNuevo.showAndWait(); //Bloquea la pantalla de atras 

        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    public void btnCerrarSesion(Event event) {
        Optional<ButtonType> respuesta = Utilidades.mostrarAlertaConfirmacion("Cerrar sesión", "¿Está seguro que desea salir de la sesión");
        if(respuesta.get()== ButtonType.OK){
            Stage stageActual = (Stage) btnCanjeCupones.getScene().getWindow();
            try {
                FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLLogin.fxml"));
                Parent vista = loadMain.load();

                //FXMLHomeController controlador = loadMain.getController();
                //controladorHome.inicializarEmpleado(empleado);
                Scene escenea = new Scene(vista);
                Stage nuevoStage = new Stage();
                nuevoStage.setScene(escenea);
                nuevoStage.getIcons().add(Constantes.imagenIcon);
                nuevoStage.setResizable(false);
                nuevoStage.show();

                // Cerrar la ventana actual
                stageActual.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }

    @Override
    public void notificarGuardado() {
        RespuestaLogin respuesta = InicioSesionDAO.iniciarSesion(empleadoSesion);
        inicializarEmpleado(respuesta.getEmpleadoSesion());
        
    }
}
