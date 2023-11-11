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
    private Button btn_modificarE;
    @FXML
    private Button btn_eliminarE;
    @FXML
    private TableColumn<?, ?> col_nombre;
    @FXML
    private TableColumn<?, ?> col_apellidoP;
    @FXML
    private TableColumn<?, ?> col_apellidoM;
    @FXML
    private Button btn_agregarE;
    @FXML
    private TextField tf_barraBusqueda;
    @FXML
    private JFXButton btn_Ubicacion;
    @FXML
    private TableColumn<?, ?> col_RFC;
    @FXML
    private TableColumn<?, ?> col_correo;
    @FXML
    private TableColumn<?, ?> col_rol;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
}
