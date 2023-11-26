package clienteescritoriocupones;

import clienteescritoriocupones.modelo.dao.EmpleadoDAO;
import clienteescritoriocupones.modelo.pojo.Empleado;
import clienteescritoriocupones.utils.Utilidades;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXMLAdminEmpleadosController implements Initializable {
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TextField tfBarraBusqueda;
    @FXML
    private JFXButton btnAgregarE;
    @FXML
    private JFXButton btnModificarE;
    @FXML
    private JFXButton btnEliminarE;
    @FXML
    private TableView<Empleado> tvEmpleados;
    @FXML
    private TableColumn colApellidoP;
    @FXML
    private TableColumn colApellidoM;
    @FXML
    private TableColumn colCURP;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TableColumn colRol;
    private int idEmpresa;
    private ObservableList<Empleado> empleados;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        empleados = FXCollections.observableArrayList();
        configurarTabla();
    }    
    
     void inicializarIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
        System.out.print(this.idEmpresa); 
        descargarSucursales();
    }
     
     private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoP.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoM.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colCURP.setCellValueFactory(new PropertyValueFactory("CURP"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colRol.setCellValueFactory(new PropertyValueFactory("nombreRol"));
        
    }
    
    private void descargarSucursales(){
        HashMap<String, Object> respuesta = EmpleadoDAO.listaEmpleado(idEmpresa);//Aquí debería ser una variable
        if(!(boolean)respuesta.get("error")){
            List<Empleado> listaWS = (List<Empleado>)respuesta.get("empleado");
            empleados.addAll(listaWS);
            tvEmpleados.setItems(empleados);
        }else{
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnAgregar(ActionEvent event) {
        try {
            //Cargar las vistas a memoria
            FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLFormularioEmpleado.fxml"));
            Parent vista = loadMain.load();

            //Cargamos la información
            //FXMLRegistroPacienteController registroController = loadMain.getController();
            //registroController.inicializarIdMedico(this.idMedico, this);

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
            stageNuevo.setTitle("Registro de Empleado");
            stageNuevo.initModality(Modality.APPLICATION_MODAL); //Configuracion que nos ayuda a elegir el control de las pantallas. No perimte que otro stage tenga el control hasta que se cierre el stage actual
            stageNuevo.showAndWait(); //Bloquea la pantalla de atras 

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    
}
