package clienteescritoriocupones;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class FXMLFormularioEmpleadoController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoP;
    @FXML
    private TextField tfApellidoM;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfNombreUsuario;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfCurp;
    @FXML
    private ComboBox<?> cbRol;
    @FXML
    private Label lbAlertNombre;
    @FXML
    private Label lbAlertApellidoP;
    @FXML
    private Label lbAlertApellidoM;
    @FXML
    private Label lbAlertEmail;
    @FXML
    private Label lbAlertNombreUsuario;
    @FXML
    private Label lbAlertPassword;
    @FXML
    private Label lbAlertRol;
    @FXML
    private Button btnRegistrarEmpleado;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnAgregarEmpleado(ActionEvent event) {
    }
    
}
