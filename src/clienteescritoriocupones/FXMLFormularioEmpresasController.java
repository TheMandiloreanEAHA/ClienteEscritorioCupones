package clienteescritoriocupones;

import clienteescritoriocupones.interfaz.IRespuesta;
import clienteescritoriocupones.modelo.dao.EmpresaDAO;
import clienteescritoriocupones.modelo.pojo.Empresa;
import clienteescritoriocupones.modelo.pojo.Mensaje;
import java.net.URL;
import java.util.ResourceBundle;

import clienteescritoriocupones.utils.Constantes;
import clienteescritoriocupones.utils.Utilidades;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class FXMLFormularioEmpresasController implements Initializable {
    
    private String estatusEmpresa;
    
    private Empresa empresa;
    
    ToggleGroup grupoRadios = new ToggleGroup();
    
    private IRespuesta observador;
    
    private File imagenSeleccionada;


    @FXML
    private Label lbAlertNombre;
    @FXML
    private JFXTextField tfNombreEmpresa;
    @FXML
    private Label lbAlertApellidoP;
    @FXML
    private JFXTextField tfNombreComercial;
    @FXML
    private JFXTextField tfRFC;
    @FXML
    private Label lbAlertEmail;
    @FXML
    private JFXTextField tfEmail;
    @FXML
    private JFXButton btnCargarImagen;
    @FXML
    private Label lbAlertNombreUsuario;
    @FXML
    private RadioButton rbActiva;
    @FXML
    private RadioButton rbInactiva;
    @FXML
    private JFXButton btnRegistrarEmpresa;
    @FXML
    private JFXButton btnCerrar;
    @FXML
    private JFXTextField tfTelefono;
    @FXML
    private JFXTextField tfRepresentante;
    @FXML
    private JFXTextField tfPaginaWeb;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbSubtitulo;
    @FXML
    private JFXButton btnSeleccionar;
    @FXML
    private ImageView ivLogo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCerrar.setGraphic(new ImageView(Constantes.imagenCerrar));
        grupoRadios.getToggles().addAll(rbActiva, rbInactiva);
        rbActiva.setDisable(true); //Desactivar lor RB porque cuando se agrega una empresa, siempre debe ser activa
        rbInactiva.setDisable(true); //Esto cambiará cuando se modifique la empresa
        rbActiva.setSelected(true);
        grupoRadios.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(rbActiva.isSelected()){
                    estatusEmpresa = "Activa";
                }else{
                    estatusEmpresa = "Inactiva";
                }
            }
        });
        
        btnCargarImagen.setDisable(true);
        btnSeleccionar.setDisable(true);

    }
    public void inicializarObservador(IRespuesta observador){
        this.observador = observador;              
    }
    
    //Este métdodo es para cuando se va a editar una sucursal
    public void inicializarEmpresa(Empresa empresa){
        this.empresa = empresa;
        System.out.println("Empresa: "+ this.empresa.getNombre());
        lbTitulo.setText("EDICIÓN DE EMPRESA");
        lbSubtitulo.setText("INGRESA LOS NUEVOS DATOS DE LA EMPRESA");
        btnRegistrarEmpresa.setText("Guardar cambios");
        tfRFC.setDisable(true);
        rbActiva.setDisable(false); //Desactivar lor RB porque cuando se agrega una empresa, siempre debe ser activa
        rbInactiva.setDisable(false); //Esto cambiará cuando se modifique la empresa
        rbActiva.setSelected(false);
        mostrarInfoEmpresa(this.empresa);
        btnCargarImagen.setDisable(false);
        btnSeleccionar.setDisable(false);
        
    }
    
    private void mostrarInfoEmpresa(Empresa empr){
        tfNombreEmpresa.setText(empr.getNombre());
        tfNombreComercial.setText(empr.getNombreComercial());
        tfRepresentante.setText(empr.getRepresentante());
        tfEmail.setText(empr.getCorreo());
        tfTelefono.setText(empr.getTelefono());
        tfPaginaWeb.setText(empr.getPaginaWeb());
        tfRFC.setText(empr.getRFC());
        //Ahora dependiendo de si su estatus es Activa o inactiva, será el radioButtom que se seleccionará
        if(empr.getEstatus().equals("Activa"))
            rbActiva.setSelected(true);
        else
            rbInactiva.setSelected(true);
        
        //Mostrar logo
        obtenerLogoServicio();
        
    }

    private void cerrarVentana() {
        Stage escenario = (Stage) btnCerrar.getScene().getWindow();
        escenario.close();
    }
    
    private boolean validarCampos(){
        boolean isValid = true;
        if(tfNombreEmpresa.getText().isEmpty()){
            isValid = false;
        }else if(tfNombreComercial.getText().isEmpty()){
            isValid = false;
        }else if(tfRepresentante.getText().isEmpty()){
            isValid = false;
        }else if(tfEmail.getText().isEmpty()){
            isValid = false;
        }else if(tfTelefono.getText().isEmpty()){
            isValid = false;
        }else if(tfPaginaWeb.getText().isEmpty()){
            isValid = false;
        }else if(tfRFC.getText().isEmpty()){
            isValid = false;
        }
        if(empresa != null){
            if(estatusEmpresa == null){
                isValid = false;
            }
        }else{ //Si empresa es null, es porque se va a registrar, por lo que su estatus debe ser "Activa"
            estatusEmpresa = "Activa";
        }
        if(!isValid){
            Utilidades.mostrarAlertaSimple("Campos Incompletos", "Verifique que todos los campos se encuentren completamente registrados", Alert.AlertType.ERROR);
        }        
        return isValid;
    }
    
    private void registrarEmpresa(Empresa empresa){
        Mensaje msj = EmpresaDAO.agregarEmpresa(empresa);
        if(!msj.getError()){
            Utilidades.mostrarAlertaSimple("Empresa registrada", msj.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notificarGuardado();
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar la Empresa", msj.getMensaje(), Alert.AlertType.ERROR);
        }        
    }
    
    private void modificarEmpresa(Empresa empresa){
        Mensaje msj = EmpresaDAO.modificarEmpresa(empresa);
        if(!msj.getError()){
            Utilidades.mostrarAlertaSimple("Empresa modificada", msj.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notificarGuardado();
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al modificar la Empresa", msj.getMensaje(), Alert.AlertType.ERROR);
        }  
    }

    @FXML
    public void btnRegistrarEmpresa(ActionEvent actionEvent) {
        boolean isValid = validarCampos();
        if(isValid){
            Empresa emp = new Empresa();
            emp.setNombre(tfNombreEmpresa.getText());
            emp.setNombreComercial(tfNombreComercial.getText());
            emp.setRepresentante(tfRepresentante.getText());
            emp.setCorreo(tfEmail.getText());
            emp.setTelefono(tfTelefono.getText());
            emp.setPaginaWeb(tfPaginaWeb.getText());
            emp.setRFC(tfRFC.getText());
            emp.setEstatus(estatusEmpresa);
            
            if(btnRegistrarEmpresa.getText().equals("Guardar cambios")){
                modificarEmpresa(emp);
            }else{
                registrarEmpresa(emp);
            }            
        }
    }

    @FXML
    public void btnCerrarVentana(ActionEvent actionEvent) {
        cerrarVentana();
    }

    //---- Únicamente se selecciona la imagen ----\\
    @FXML
    private void btnSeleccionarImagen(ActionEvent event) {
        FileChooser dialogoSeleccionLogo = new FileChooser();
        dialogoSeleccionLogo.setTitle("Selecciona un logo");
        //Configuración del tipo de archivo 
        FileChooser.ExtensionFilter filtroImg = new FileChooser.ExtensionFilter("Archivos de imagen (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        dialogoSeleccionLogo.getExtensionFilters().add(filtroImg);
        Stage stageActual = (Stage) tfEmail.getScene().getWindow();
        imagenSeleccionada = dialogoSeleccionLogo.showOpenDialog(stageActual);
        if(imagenSeleccionada != null){
            mostrarLogoSeleccionado(imagenSeleccionada);
        }
    }
    
    //---- Se carga la imagen(logo) en la BD ----\\
    @FXML
    private void btnCargarImagen(ActionEvent event) {
        if(imagenSeleccionada != null){
            cargarLogoServidor(imagenSeleccionada);
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona una foto", "Para cargar un logo a la empresa, debes seleccionarlo previamente", Alert.AlertType.WARNING);
        }
    }
    
    //--- Muestra la imagen seleccionada en el ImageView
    private void mostrarLogoSeleccionado(File logo){
        try{
            BufferedImage buffer = ImageIO.read(logo);
            Image image = SwingFXUtils.toFXImage(buffer, null);
            ivLogo.setImage(image);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    
    //Carga la imagen al servidor
    private void cargarLogoServidor(File logo){
        try {
            byte[] imgBytes = Files.readAllBytes(logo.toPath());
            Mensaje msj = EmpresaDAO.subirLogoEmpresa(empresa.getIdEmpresa(), imgBytes);
            
            if(!msj.getError()){
                Utilidades.mostrarAlertaSimple("Logo Guardado", msj.getMensaje(), Alert.AlertType.INFORMATION);
            }else{
                Utilidades.mostrarAlertaSimple("Error al subir el logo", msj.getMensaje(), Alert.AlertType.ERROR);
            }
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("ERROR", "Error: "+ex.getMessage(), Alert.AlertType.ERROR);

        }
    }
    
    //Metodo para mostrar el logo de la empresa cagrado anteriormente
    private void obtenerLogoServicio(){
        Empresa empresaLogo = EmpresaDAO.obtenerLogoEmpresa(empresa.getIdEmpresa());
        if(empresaLogo != null && empresaLogo.getFotoBase64() != null && !empresaLogo.getFotoBase64().isEmpty()){
            mostrarLogoServidor(empresaLogo.getFotoBase64());
        }
    }
    
    private void mostrarLogoServidor(String imgBase64){
        byte[] logo = Base64.getDecoder().decode(imgBase64.replaceAll("\\n", "")); //Se tiene que eliminar los saltos de linea, debido a que el Base64 sólo soporta letrar, signos de suma o igual
        Image image = new Image(new ByteArrayInputStream(logo));
        ivLogo.setImage(image);
    }
    
}
