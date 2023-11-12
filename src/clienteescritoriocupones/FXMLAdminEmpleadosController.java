package clienteescritoriocupones;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class FXMLAdminEmpleadosController implements Initializable {

    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TextField tfBarraBusqueda;
    @FXML
    private JFXButton btnAgregarE;
    @FXML
    private JFXButton btnModificarE;
    @FXML
    private JFXButton btnEliminarE;
    @FXML
    private TableView<?> tvEmpleados;
    @FXML
    private TableColumn<?, ?> colApellidoP;
    @FXML
    private TableColumn<?, ?> colApellidoM;
    @FXML
    private TableColumn<?, ?> colCURP;
    @FXML
    private TableColumn<?, ?> colCorreo;
    @FXML
    private TableColumn<?, ?> colRol;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
}
