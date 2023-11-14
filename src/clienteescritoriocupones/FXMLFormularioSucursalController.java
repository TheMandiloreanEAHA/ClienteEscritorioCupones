package clienteescritoriocupones;

import clienteescritoriocupones.utils.Constantes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class FXMLFormularioSucursalController implements Initializable {

    @FXML
    private JFXButton btnCerrar;
    @FXML
    private Label lbAlertNombre;
    @FXML
    private JFXTextField tfNombre;
    @FXML
    private Label lbAlertApellidoP;
    @FXML
    private JFXTextField tfTelefono;
    @FXML
    private Label lbAlertApellidoM;
    @FXML
    private Label lbAlertEmail;
    @FXML
    private JFXButton btnRegistrarSucursal;
    @FXML
    private Label lbAlertNombreUsuario;
    @FXML
    private JFXTextField tfNombreEncargado;
    @FXML
    private Label lbAlertPassword;
    @FXML
    private Label lbAlertRol;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnCerrar.setGraphic(new ImageView(Constantes.imagenCerrar));
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
