package clienteescritoriocupones;

import clienteescritoriocupones.modelo.dao.EmpresaDAO;
import clienteescritoriocupones.modelo.pojo.Empresa;
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


public class FXMLAdminEmpresasController implements Initializable {
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    private ObservableList<Empresa> empresa;
    @FXML
    private TextField tfBarraBusqueda;
    @FXML
    private JFXButton btnAgregarE;
    @FXML
    private JFXButton btnModificarE;
    @FXML
    private JFXButton btnEliminarE;
    @FXML
    private JFXButton btnUbicacion;
    @FXML
    private TableView<Empresa> tvEmpresa;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colRFC;
    @FXML
    private TableColumn colRepresentante;
    @FXML
    private TableColumn colPaginaWeb;
    @FXML
    private TableColumn colEstatus;
    @FXML
    private TableColumn colCorreo;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        empresa = FXCollections.observableArrayList();
        configurarTabla();
        descargarEmpresas();
    }    
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colRFC.setCellValueFactory(new PropertyValueFactory("RFC"));
        colRepresentante.setCellValueFactory(new PropertyValueFactory("representante"));
        colPaginaWeb.setCellValueFactory(new PropertyValueFactory("paginaWeb"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
    }
    
    private void descargarEmpresas(){
        HashMap<String, Object> respuesta = EmpresaDAO.listaEmpresa();
        if(!(boolean)respuesta.get("error")){
            List<Empresa> listaWS = (List<Empresa>)respuesta.get("empresa");
            empresa.addAll(listaWS);
            tvEmpresa.setItems(empresa);
        }else{
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void btnAgregar(ActionEvent event) {
        try {
            //Cargar las vistas a memoria
            FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLFormularioEmpresas.fxml"));
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
            stageNuevo.setTitle("Registro de empresa");
            stageNuevo.initModality(Modality.APPLICATION_MODAL); //Configuracion que nos ayuda a elegir el control de las pantallas. No perimte que otro stage tenga el control hasta que se cierre el stage actual
            stageNuevo.showAndWait(); //Bloquea la pantalla de atras 

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
