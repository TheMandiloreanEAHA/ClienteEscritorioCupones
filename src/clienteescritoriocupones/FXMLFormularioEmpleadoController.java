package clienteescritoriocupones;

import java.net.URL;
import java.util.ResourceBundle;

import clienteescritoriocupones.utils.Constantes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class FXMLFormularioEmpleadoController implements Initializable {

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
    private JFXButton btnCerrar;
    @FXML
    private JFXTextField tfNombre;
    @FXML
    private JFXTextField tfApellidoP;
    @FXML
    private JFXTextField tfApellidoM;
    @FXML
    private JFXTextField tfCurp;
    @FXML
    private JFXButton btnRegistrarEmpleado;
    @FXML
    private JFXTextField tfEmail;
    @FXML
    private JFXTextField tfNombreUsuario;
    @FXML
    private JFXTextField tfPassword;
    @FXML
    private JFXComboBox cbRol;
    @FXML
    private JFXComboBox cbEmpresa;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        btnCerrar.setGraphic(new ImageView(Constantes.imagenCerrar));
    }    

    @FXML
    private void btnAgregarEmpleado(ActionEvent event) {

    }

    @FXML
    public void cerrarVentana(ActionEvent actionEvent) {
        cerrarVentana();
    }
    private void cerrarVentana() {
        Stage escenario = (Stage) btnCerrar.getScene().getWindow();
        escenario.close();
    }
}
