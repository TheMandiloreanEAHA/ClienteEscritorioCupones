package clienteescritoriocupones;

import clienteescritoriocupones.interfaz.IRespuesta;
import clienteescritoriocupones.modelo.dao.EmpresaDAO;
import clienteescritoriocupones.modelo.dao.PromocionDAO;
import clienteescritoriocupones.modelo.pojo.Categoria;
import clienteescritoriocupones.modelo.pojo.Empresa;
import clienteescritoriocupones.modelo.pojo.Mensaje;
import clienteescritoriocupones.modelo.pojo.Promocion;
import clienteescritoriocupones.utils.Constantes;
import clienteescritoriocupones.utils.Utilidades;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class FXMLFormularioPromocionesController implements Initializable {
    
    private Promocion promocion;
    
    private int idEmpresa;
    
    private IRespuesta observador;
    
    ToggleGroup radioButtonsEstatus = new ToggleGroup();
    
    private String estatusPromocion;
    
    ToggleGroup radioButtonsTipoPromo = new ToggleGroup();
    
    private String tipoPromocion;
    
    private File imagenSeleccionada;

    @FXML
    private JFXButton btnCerrar;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbSubtitulo;
    @FXML
    private JFXTextField tfNombrePromo;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private JFXTextArea taRestriccion;
    @FXML
    private JFXComboBox<Categoria> cbCategoria;
    @FXML
    private JFXButton btnCargarImagen;
    @FXML
    private JFXButton btnSeleccionar;
    @FXML
    private RadioButton rbDescuento;
    @FXML
    private RadioButton rbCostoRebajado;
    @FXML
    private JFXTextField tfNumCupones;
    @FXML
    private JFXTextField tfCodigo;
    @FXML
    private JFXComboBox<Empresa> cbEmpresa;
    @FXML
    private RadioButton rbActiva;
    @FXML
    private RadioButton rbInactiva;
    @FXML
    private JFXButton btnRegistrarPromo;
    @FXML
    private ImageView ivImg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEmpresasCB();
        llenarComboCategoria();
        btnCerrar.setGraphic(new ImageView(Constantes.imagenCerrar));
        //-- Configurar los dos grupos de radioButtons --\\
        radioButtonsEstatus.getToggles().addAll(rbActiva, rbInactiva);
        radioButtonsEstatus.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(rbActiva.isSelected()){
                    estatusPromocion = "Activa";
                }
                if(rbInactiva.isSelected()){
                    estatusPromocion = "Inactiva";
                }
            }
        });       
        
        radioButtonsTipoPromo.getToggles().addAll(rbCostoRebajado, rbDescuento);
        radioButtonsTipoPromo.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(rbDescuento.isSelected()){
                    tipoPromocion = "Descuento";
                }
                if(rbCostoRebajado.isSelected()){
                    tipoPromocion = "Costo Rebajado";
                }
            }
        });
        
        btnCargarImagen.setDisable(true);
        btnSeleccionar.setDisable(true);
        
    }
    
    public void inicializarIdEmpresa(int idEmpresa, IRespuesta observador){
        this.idEmpresa = idEmpresa;
        this.observador = observador;
        if(this.observador == null){
        }
        if(this.idEmpresa == 0){
            System.out.println("Admin General");
        }else{
            System.out.println("Admin Comercial");
            cbEmpresa.setDisable(true);
            //agregar validacion de combo 
        }
    }
    
    //se manda a llamar cuando se está editando una promoción
    public void inicializarPromocion(Promocion promocion){
        this.promocion = promocion;
        btnRegistrarPromo.setText("Guardar Cambios");
        btnCargarImagen.setDisable(false);
        btnSeleccionar.setDisable(false);
        lbTitulo.setText("EDICIÓN DE PROMOCIÓN");
        lbSubtitulo.setText("EDITA LOS DATOS DE LA PROMOCIÓN");
        //Cargar info promoción
        llenarCampos(this.promocion);
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
    
    private void llenarComboCategoria(){
        HashMap<String, Object> respuesta = PromocionDAO.listaCategorias();
        if(!(boolean)respuesta.get("error")){
            List<Categoria> categorias = (List<Categoria>)respuesta.get("categorias");
            //Limpiar el combo antes de agregar los estados
            cbCategoria.getItems().clear();
            //agregar las empresas:
            cbCategoria.getItems().addAll(categorias);
        }else{
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }     
    }
    
    private void llenarCampos(Promocion promo){
        tfNombrePromo.setText(promo.getNombre());
        tfNumCupones.setText(promo.getNumeroCupones().toString());
        tfCodigo.setText(promo.getCodigoPromocion());
        tfCodigo.setDisable(true);
        taDescripcion.setText(promo.getDescripcion());
        taRestriccion.setText(promo.getRestriccion());
        //Parsear los datos para poder mostrar las fechas establecidas
        LocalDate fechaInicio = LocalDate.parse(promo.getInicioPromocion());
        dpFechaInicio.setValue(fechaInicio);
        LocalDate fechaFin = LocalDate.parse(promo.getFinPromocion());
        dpFechaFin.setValue(fechaFin);
        
        //Llenamos los comboBox       
        for(Categoria cat: cbCategoria.getItems()){
            if(cat.getIdCategoria() == promo.getIdCategoria()){
                cbCategoria.setValue(cat);
                break;
            }
        }
        
        for(Empresa empr: cbEmpresa.getItems()){
            if(empr.getIdEmpresa() == promo.getIdEmpresa()){
                cbEmpresa.setValue(empr);
                break;
            }
        }

        //Llenamos los RadioButtons
        if(promo.getEstatus().equals("Activa")){
            rbActiva.setSelected(true);
        }else{
            rbInactiva.setSelected(true);
        }
        if(promo.getTipo().equals("Descuento")){
            rbDescuento.setSelected(true);
        }else{
            rbCostoRebajado.setSelected(true);
        }
        
    }
    
    private boolean validarCampos(){
        boolean isValid = true;
        //-- Validamos los ComboBox --\\
        if(cbCategoria.getValue() == null){
            Utilidades.mostrarAlertaSimple("Información incompleta", "Se debe asignar una categoría a la promoción", Alert.AlertType.ERROR);
            isValid = false;
        }else if(cbEmpresa.getValue() == null){
            Utilidades.mostrarAlertaSimple("Información incompleta", "La promoción se debe asignar a una empresa", Alert.AlertType.ERROR);
            isValid = false;
        }else if(tfNombrePromo.getText() == null || tfNombrePromo.getText().isEmpty()){
            isValid = false;
        }else if(taDescripcion.getText() == null || taDescripcion.getText().isEmpty()){
            isValid = false;
        }else if(dpFechaFin.getValue() == null){
            isValid = false;
        }else if(dpFechaInicio.getValue() == null){
            isValid = false;
        }else if(taRestriccion.getText() == null || taRestriccion.getText().isEmpty()){
            isValid = false;
        }else if(tipoPromocion == null){
            isValid = false;
        }else if(estatusPromocion == null){
            isValid = false;
        }else if(tfNumCupones.getText() == null || tfNumCupones.getText().isEmpty()){
            isValid = false;
        }else if(tfCodigo.getText() == null || tfCodigo.getText().isEmpty()){
            isValid = false;
        }
                
        if(!isValid){
            Utilidades.mostrarAlertaSimple("Información incompleta", "Debe de llenar todo los campos", Alert.AlertType.ERROR);
        }
        
        return isValid;
    }

    @FXML
    private void btnCerrarVentana(ActionEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana() {
        Stage escenario = (Stage) btnCerrar.getScene().getWindow();
        escenario.close();
    }
    
    private void registrarPromo(Promocion promo){
        Mensaje msj = PromocionDAO.agregarPromocion(promo);
        if(!msj.getError()){
            Utilidades.mostrarAlertaSimple("Promoción registrada", msj.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notificarGuardado();
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar la promoción", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void modificarPromo(Promocion promo){
        Mensaje msj = PromocionDAO.modificarPromocion(promo);
        if(!msj.getError()){
            Utilidades.mostrarAlertaSimple("Promoción Modificada", msj.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notificarGuardado();
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al modifcar la promoción", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnRegistrarPromo(ActionEvent event) {
        Promocion promo = new Promocion();
        if(validarCampos()){
            promo.setNombre(tfNombrePromo.getText());
            promo.setDescripcion(taDescripcion.getText());
            promo.setFinPromocion(dpFechaFin.getValue().toString());
            promo.setInicioPromocion(dpFechaInicio.getValue().toString());
            promo.setRestriccion(taRestriccion.getText());
            promo.setNumeroCupones(Integer.parseInt(tfNumCupones.getText()));
            promo.setCodigoPromocion(tfCodigo.getText());
            //LLenamos con la información de los combos:
            promo.setIdCategoria(cbCategoria.getValue().getIdCategoria());
            promo.setIdEmpresa(cbEmpresa.getValue().getIdEmpresa());
            //Info de los RB
            promo.setEstatus(estatusPromocion);
            promo.setTipo(tipoPromocion);
            if(promo.getTipo().equals("Descuento")){
                promo.setIdTipoPromocion(1);
            }else if(promo.getTipo().equals("Costo Rebajado")){
                promo.setIdTipoPromocion(2);
            }
            
            if(btnRegistrarPromo.getText().equals("Guardar Cambios")){
                //Modificar promo
                promo.setIdPromocion(promocion.getIdPromocion());
                modificarPromo(promo);
            }else{
                //Registrar Promo
                registrarPromo(promo);
            }
        }
    }

    @FXML
    private void btnCargarImagen(ActionEvent event) {
        
    }

    @FXML
    private void btnSeleccionarImagen(ActionEvent event) {
    }
    
}
