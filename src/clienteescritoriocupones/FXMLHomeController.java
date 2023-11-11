package clienteescritoriocupones;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class FXMLHomeController implements Initializable {

    @FXML
    private Label lb_bienvenida;
    @FXML
    private Button btn_sucursales;
    @FXML
    private Button btn_empresa;
    @FXML
    private Button btn_empleados;
    @FXML
    private Button btn_promociones;
    @FXML
    private Button btn_cupones;
    @FXML
    private Label lb_empresa;
    @FXML
    private ImageView iv_logoEmpresa;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnSucursales(ActionEvent event) {
    }

    @FXML
    private void btnEmpresa(ActionEvent event) {
    }

    @FXML
    private void btnEmpleados(ActionEvent event) {
    }

    @FXML
    private void btnPromos(ActionEvent event) {
    }

    @FXML
    private void btnCupones(ActionEvent event) {
    }
    
}
