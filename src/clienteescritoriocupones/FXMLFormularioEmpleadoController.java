package clienteescritoriocupones;

import clienteescritoriocupones.interfaz.IRespuesta;
import clienteescritoriocupones.modelo.dao.EmpleadoDAO;
import clienteescritoriocupones.modelo.dao.EmpresaDAO;
import clienteescritoriocupones.modelo.pojo.Empleado;
import clienteescritoriocupones.modelo.pojo.Empresa;
import clienteescritoriocupones.modelo.pojo.Mensaje;
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
    
    private Empleado empleado;
    
    private IRespuesta observador;

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
    
    public void inicializarObservador(IRespuesta observador){
        this.observador = observador;
    }
    
    public void inicializarEmpleado(Empleado empleado, IRespuesta observador){
        btnRegistrarEmpleado.setText("Guardar Cambios");
        this.empleado = empleado;
        this.observador = observador;
        mostrarInfoEmpleado(this.empleado);
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
        if(cbRol.getValue() == null){
            Utilidades.mostrarAlertaSimple("Información incompleta", "Se debe asignar un rol al empleado", Alert.AlertType.ERROR);
        }else{
            if(cbRol.getValue().equals("Administrador Comercial")){
                if(cbEmpresa.getValue() == null){
                    Utilidades.mostrarAlertaSimple("Información incompleta", "Se debe asignar una empresa al Administrador", Alert.AlertType.ERROR);
                    isValid = false;
                    return isValid;
                }
            }else if(tfNombre.getText() == null || tfNombre.getText().isEmpty()){
                isValid = false;
            }
            else if(tfApellidoP.getText() == null || tfApellidoP.getText().isEmpty()){
                isValid = false;
            }else if(tfApellidoM.getText() == null || tfApellidoM.getText().isEmpty()){
                isValid = false;
            }else if(tfCurp.getText() == null || tfCurp.getText().isEmpty()){
                isValid = false;
            }else if(tfEmail.getText() == null || tfEmail.getText().isEmpty()){
                isValid = false;
            }else if(tfNombreUsuario.getText() == null || tfNombreUsuario.getText().isEmpty()){
                isValid = false;
            }else if(tfPassword.getText() == null || tfPassword.getText().isEmpty()){
                isValid = false;
            }
        }
        
        if(!isValid){
            Utilidades.mostrarAlertaSimple("Información incompleta", "Debe de llenar todo los campos", Alert.AlertType.ERROR);
        }
        
        return isValid;
    }
    
    private void mostrarInfoEmpleado(Empleado empleado){
        tfNombre.setText(empleado.getNombre());
        tfApellidoP.setText(empleado.getApellidoPaterno());
        tfApellidoM.setText(empleado.getApellidoMaterno());
        tfCurp.setText(empleado.getCURP());
        tfEmail.setText(empleado.getCorreo());
        tfPassword.setText(empleado.getContraseña());
        tfNombreUsuario.setText(empleado.getNombreUsuario());
        cbRol.setDisable(true);
        cbEmpresa.setDisable(true);
        
        if(empleado.getIdRol() == 1){
            cbRol.setValue("Administrador general");            
        }else{
            cbRol.setValue("Administrador Comercial");
            HashMap<String, Object> respuesta = EmpresaDAO.buscarEmpresaPorId(empleado.getIdEmpresa());
            Empresa empresa =(Empresa)respuesta.get("empresa");
            //Seleccionar la empresa en el combobox
            
            for(Empresa empr: cbEmpresa.getItems()){
                if(empr.getIdEmpresa() == empresa.getIdEmpresa()){
                    cbEmpresa.setValue(empr);
                    break;
                }
            }
            
        }
        
    }
    
    private void registrarEmpleado(Empleado empleado){
        Mensaje msj = EmpleadoDAO.agregarEmpleado(empleado);
        if(!msj.getError()){
            Utilidades.mostrarAlertaSimple("Empleado registrado", msj.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notificarGuardado();
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar al empleado", msj.getMensaje(), Alert.AlertType.ERROR);
        }
        
    }
    
    private void modificarEmpleado(Empleado empleado){
        System.out.println("Nombre: " + empleado.getNombre());
        Mensaje msj = EmpleadoDAO.modificarEmpleado(empleado);
        if(!msj.getError()){
            Utilidades.mostrarAlertaSimple("Empleado modifcado", msj.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notificarGuardado();
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al modificar al empleado", msj.getMensaje(), Alert.AlertType.ERROR);
        }
        
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

    @FXML
    private void btnRegistrarEmpleado(ActionEvent event) {
        Empleado emp = new Empleado();
        if(validarCampos()){
            emp.setNombre(tfNombre.getText());
            emp.setApellidoPaterno(tfApellidoP.getText());
            emp.setApellidoMaterno(tfApellidoM.getText());
            emp.setCURP(tfCurp.getText());
            emp.setCorreo(tfEmail.getText());
            emp.setNombreUsuario(tfNombreUsuario.getText());
            emp.setContraseña(tfPassword.getText());
            if(cbRol.getValue().equals("Administrador general")){
                emp.setIdRol(1);
            }else if(cbRol.getValue().equals("Administrador Comercial")){
                emp.setIdRol(2);
                emp.setIdEmpresa(cbEmpresa.getValue().getIdEmpresa());
            }   
                     
            if(btnRegistrarEmpleado.getText().equals("Guardar Cambios")){
                emp.setIdEmpleado(empleado.getIdEmpleado());
                modificarEmpleado(emp);
            }else{
                registrarEmpleado(emp);
            }
        }               
                
    }
}
