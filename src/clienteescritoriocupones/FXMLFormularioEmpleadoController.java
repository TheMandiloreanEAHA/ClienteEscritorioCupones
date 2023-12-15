package clienteescritoriocupones;

import clienteescritoriocupones.modelo.dao.EmpresaDAO;
import clienteescritoriocupones.modelo.pojo.Empresa;
import java.net.URL;
import java.util.ResourceBundle;

import clienteescritoriocupones.utils.Constantes;
import clienteescritoriocupones.utils.Utilidades;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.util.HashMap;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    private JFXComboBox<String> cbRol;
    @FXML
    private JFXComboBox<Empresa> cbEmpresa;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEmpresasCB();
        llenarComboRol();
        btnCerrar.setGraphic(new ImageView(Constantes.imagenCerrar));
    }
    
    private void cargarEmpresasCB(){
         HashMap<String, Object> respuesta = EmpresaDAO.listaEmpresa();
         if(!(boolean)respuesta.get("error")){
             List<Empresa> empresas = (List<Empresa>)respuesta.get("empresa");
            //Limpiar el combo antes de agregar los estados
            cbEmpresa.getItems().clear();
            //agregar las empresas:
            cbEmpresa.getItems().addAll(empresas);
         }else{
             Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
         }        
    }
    
    private void llenarComboRol(){
        cbRol.getItems().add("Administrador general");
        cbRol.getItems().add("Administrador Comercial");
    }
    
    private boolean validarCampos(){
        boolean isValid = true;
        if(cbRol.getValue().equals("Administrador Comercial")){
            if(cbEmpresa.getValue() == null){
                Utilidades.mostrarAlertaSimple("Información incompleta", "Se debe asignar una empresa al Administrador", Alert.AlertType.ERROR);
                isValid = false;
            }
        }else if(tfNombre.getText() == null || tfNombre.getText().isEmpty()){
            isValid = false;
        }
        
        
        return isValid;
    }
    
    



    @FXML
    public void cerrarVentana(ActionEvent actionEvent) {
        cerrarVentana();
    }
    private void cerrarVentana() {
        Stage escenario = (Stage) btnCerrar.getScene().getWindow();
        escenario.close();
    }

    @FXML
    private void listenerComboRol(ActionEvent event) {
        String rolSeleccionado = cbRol.getValue();
        if(rolSeleccionado.equals("Administrador general")){
            cbEmpresa.setDisable(true);
            cbEmpresa.setValue(null);
        }
        if(rolSeleccionado.equals("Administrador Comercial")){
            cbEmpresa.setDisable(false);
        }
    }
}
