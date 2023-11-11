package clienteescritoriocupones;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class FXMLHomeController implements Initializable {

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
    private BorderPane bp;
    @FXML
    private AnchorPane ap;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    @FXML
    private void btnEmpresas(MouseEvent event) {
        
    }

    @FXML
    private void btnSucursales(MouseEvent event) {
    }

    @FXML
    private void btnEmpleados(MouseEvent event) {
        cargarVista("FXMLAdminEmpleados");
    }

    @FXML
    private void btnPromociones(MouseEvent event) {
    }

    @FXML
    private void btnCupones(MouseEvent event) {
    }
    
    
    private void cargarVista(String vista){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(vista+".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        bp.setCenter(root);
    }

    
}
