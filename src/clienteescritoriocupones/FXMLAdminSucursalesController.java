package clienteescritoriocupones;

import clienteescritoriocupones.modelo.dao.SucursalDAO;
import clienteescritoriocupones.modelo.pojo.Empresa;
import clienteescritoriocupones.modelo.pojo.Sucursal;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXMLAdminSucursalesController implements Initializable {
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    private int idEmpresa;
    
    private ObservableList<Sucursal> sucursales;

    @FXML
    private TextField tfBarraBusqueda;
    @FXML
    private JFXButton btnAgregarS;
    @FXML
    private JFXButton btnModificarS;
    @FXML
    private JFXButton btnEliminarS;
    @FXML
    private JFXButton btnUbicacion;
    @FXML
    private TableView<Sucursal> tvSucursales;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colNumTelefono;
    @FXML
    private TableColumn colEncargado;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TableColumn colEmpresa;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sucursales = FXCollections.observableArrayList();
        configurarTabla();
        descargarSucursales();
    }    
    
    public void inicializarIdEmpresa(int idEmpresa){
        this.idEmpresa = idEmpresa;
        System.out.print(this.idEmpresa);        
    }
    
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colNumTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        colEncargado.setCellValueFactory(new PropertyValueFactory("encargado"));
        colDireccion.setCellValueFactory(new PropertyValueFactory("codigoPostal"));
        colEmpresa.setCellValueFactory(new PropertyValueFactory("nombreEmpresa"));
        
    }
    
    private void descargarSucursales(){
        HashMap<String, Object> respuesta = SucursalDAO.listaSucursal(2);//Aquí debería ser una variable
        if(!(boolean)respuesta.get("error")){
            List<Sucursal> listaWS = (List<Sucursal>)respuesta.get("sucursal");
            sucursales.addAll(listaWS);
            tvSucursales.setItems(sucursales);
        }else{
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnAgregar(ActionEvent event) {
        try {
            //Cargar las vistas a memoria
            FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLFormularioSucursal.fxml"));
            Parent vista = loadMain.load();

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
            stageNuevo.setTitle("Registro de Sucursal");
            stageNuevo.initModality(Modality.APPLICATION_MODAL); //Configuracion que nos ayuda a elegir el control de las pantallas. No perimte que otro stage tenga el control hasta que se cierre el stage actual
            stageNuevo.showAndWait(); //Bloquea la pantalla de atras 

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
