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
    private JFXButton btnAgregarP;
    @FXML
    private JFXButton btnModificarP;
    @FXML
    private JFXButton btnEliminarP;
    @FXML
    private TableView<?> tvPromociones;
    @FXML
    private TableColumn<?, ?> colDescripcion;
    @FXML
    private TableColumn<?, ?> colInicio;
    @FXML
    private TableColumn<?, ?> colFin;
    @FXML
    private TableColumn<?, ?> colRestriccion;
    @FXML
    private TableColumn<?, ?> colTipoPromocion;
    @FXML
    private TableColumn<?, ?> colCuponesDisponibles;
    @FXML
    private TableColumn<?, ?> colEstatus;
    @FXML
    private TableColumn<?, ?> colEmpresaPromocion;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
}
