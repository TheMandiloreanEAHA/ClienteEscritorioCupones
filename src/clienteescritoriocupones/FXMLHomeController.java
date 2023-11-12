package clienteescritoriocupones;

import com.jfoenix.controls.JFXButton;
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
    private BorderPane bp;
    @FXML
    private AnchorPane ap;
    @FXML
    private JFXButton btnInicio;
    @FXML
    private JFXButton btnEmpresa;
    @FXML
    private JFXButton btnSucursales;
    @FXML
    private JFXButton btnEmpleados;
    @FXML
    private JFXButton btnPromociones;
    @FXML
    private JFXButton btnCerrarSesion;
    @FXML
    private Label lbNombreUsuario;
    @FXML
    private Label lbNombreEmpresa;
    @FXML
    private Label lbRFCEmpresa;
    @FXML
    private Label lbNombreComercial;
    @FXML
    private ImageView imgEmpresa;
    @FXML
    private Label lbNombreEmpleado;
    @FXML
    private Label lbCorreo;
    @FXML
    private Label lbApellidosEmpleado;
    @FXML
    private Label lbNumeroTelefono;
    @FXML
    private JFXButton btnEditarPerfil;
    @FXML
    private JFXButton btnCanjeCupones;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void btnInicio(ActionEvent event) {
         bp.setCenter(ap);
    }
    @FXML
    private void btnEmpresas(MouseEvent event) {
       
    }

    @FXML
    private void btnSucursales(MouseEvent event) {
        cargarVista("FXMLAdminSucursales");
    }

    @FXML
    private void btnEmpleados(MouseEvent event) {
        cargarVista("FXMLAdminEmpleados");
    }

    @FXML
    private void btnPromociones(MouseEvent event) {
        cargarVista("FXMLAdminPromociones");
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

    @FXML
    private void btnEditarPerfil(ActionEvent event) {
    }

    

    
}
