package clienteescritoriocupones;

import java.net.URL;
import java.util.ResourceBundle;

import clienteescritoriocupones.utils.Constantes;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FXMLFormularioEmpresasController implements Initializable {


    @javafx.fxml.FXML
    private Label lbAlertNombre;
    @javafx.fxml.FXML
    private JFXTextField tfNombreEmpresa;
    @javafx.fxml.FXML
    private Label lbAlertApellidoP;
    @javafx.fxml.FXML
    private JFXTextField tfNombreComercial;
    @javafx.fxml.FXML
    private JFXTextField tfRFC;
    @javafx.fxml.FXML
    private Label lbAlertEmail;
    @javafx.fxml.FXML
    private JFXTextField tfEmail;
    @javafx.fxml.FXML
    private ImageView imgEmpresa;
    @javafx.fxml.FXML
    private JFXButton btnCargarImagen;
    @javafx.fxml.FXML
    private JFXButton btnActualizarImagen;
    @javafx.fxml.FXML
    private Label lbAlertNombreUsuario;
    @javafx.fxml.FXML
    private RadioButton rbActiva;
    @javafx.fxml.FXML
    private RadioButton rbInactiva;
    @javafx.fxml.FXML
    private JFXButton btnRegistrarEmpresa;
    @javafx.fxml.FXML
    private JFXButton btnCerrar;
    @javafx.fxml.FXML
    private Label lbAlertRol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnCerrar.setGraphic(new ImageView(Constantes.imagenCerrar));

    }

    private void cerrarVentana() {
        Stage escenario = (Stage) btnCerrar.getScene().getWindow();
        escenario.close();
    }

    @javafx.fxml.FXML
    public void btnRegistrarEmpresa(ActionEvent actionEvent) {
        
    }

    @javafx.fxml.FXML
    public void btnCerrarVentana(ActionEvent actionEvent) {
        cerrarVentana();
    }
}
