/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritoriocupones;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author yahir
 */
public class FXMLAdminSucursalesController implements Initializable {
    
    private double xOffset = 0;
    private double yOffset = 0;

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
    private TableView<?> tvSucursales;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colNumTelefono;
    @FXML
    private TableColumn<?, ?> colEncargado;
    @FXML
    private TableColumn<?, ?> colDireccion;
    @FXML
    private TableColumn<?, ?> colEmpresa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnAgregar(ActionEvent event) {
        try {
            //Cargar las vistas a memoria
            FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLFormularioSucursal.fxml"));
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
            stageNuevo.setTitle("Registro de Sucursal");
            stageNuevo.initModality(Modality.APPLICATION_MODAL); //Configuracion que nos ayuda a elegir el control de las pantallas. No perimte que otro stage tenga el control hasta que se cierre el stage actual
            stageNuevo.showAndWait(); //Bloquea la pantalla de atras 

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
