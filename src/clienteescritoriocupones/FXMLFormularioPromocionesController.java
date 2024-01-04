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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;


public class FXMLFormularioPromocionesController implements Initializable {
    
    private double xOffset = 0;
    private double yOffset = 0;
    
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
    @FXML
    private JFXButton btnAsignarSucursales;

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
        btnAsignarSucursales.setDisable(true);
        
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
            for(Empresa empr: cbEmpresa.getItems()){
            if(empr.getIdEmpresa() == this.idEmpresa){
                cbEmpresa.setValue(empr);
                break;
            }
        }
            
        }
    }
    
    //se manda a llamar cuando se está editando una promoción
    public void inicializarPromocion(Promocion promocion){
        this.promocion = promocion;
        btnRegistrarPromo.setText("Guardar Cambios");
        btnCargarImagen.setDisable(false);
        btnSeleccionar.setDisable(false);
        btnAsignarSucursales.setDisable(false);
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
        
        //Mostrar imagen
        obtenerImgServicio();
        
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
        Utilidades.mostrarAlertaSimple("Información incompleta", "Ingrese el nombre de la promoción", Alert.AlertType.ERROR);
        isValid = false;
    }else if(taDescripcion.getText() == null || taDescripcion.getText().isEmpty()){
        Utilidades.mostrarAlertaSimple("Información incompleta", "Ingrese una descripción de la promoción", Alert.AlertType.ERROR);
        isValid = false;
    }else if(dpFechaFin.getValue() == null){
        Utilidades.mostrarAlertaSimple("Información incompleta", "Ingrese la fecha de finalización de la promoción", Alert.AlertType.ERROR);
        isValid = false;
    }else if(dpFechaInicio.getValue() == null){
        Utilidades.mostrarAlertaSimple("Información incompleta", "Ingrese la fecha de inicio de la promoción", Alert.AlertType.ERROR);
        isValid = false;
    }else if(taRestriccion.getText() == null || taRestriccion.getText().isEmpty()){
        Utilidades.mostrarAlertaSimple("Información incompleta", "Ingrese las restricciones de la promoción", Alert.AlertType.ERROR);
        isValid = false;
    }else if(tipoPromocion == null){
        Utilidades.mostrarAlertaSimple("Información incompleta", "Seleccione el tipo de promoción", Alert.AlertType.ERROR);
        isValid = false;
    }else if(estatusPromocion == null){
        Utilidades.mostrarAlertaSimple("Información incompleta", "Seleccione el estatus de la promoción", Alert.AlertType.ERROR);
        isValid = false;
    }else if(tfNumCupones.getText() == null || tfNumCupones.getText().isEmpty()){
        Utilidades.mostrarAlertaSimple("Información incompleta", "Ingrese el número de cupones", Alert.AlertType.ERROR);
        isValid = false;
    }else{
        //-- Validamos el número de cupones --\\
        tfNumCupones.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfNumCupones.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        if (!tfNumCupones.getText().matches("\\d+")){
            Utilidades.mostrarAlertaSimple("Información incompleta", "El número de cupones debe ser un número entero", Alert.AlertType.ERROR);
            isValid = false;
        }else if (Integer.parseInt(tfNumCupones.getText()) < 1){
            Utilidades.mostrarAlertaSimple("Información incompleta", "El número de cupones debe ser mayor o igual a 1", Alert.AlertType.ERROR);
            isValid = false;
        }
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
    
    private String generarCodigo() {
        // Generar un UUID
        UUID uuid = UUID.randomUUID();
        // Obtener la representación en cadena del UUID y seleccionar solo los primeros 8 caracteres
        String codigo = uuid.toString().substring(0, 8);
        return codigo;
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
            promo.setCodigoPromocion(generarCodigo());
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

    
    //---- Únicamente se selecciona la imagen ----\\
    @FXML
    private void btnSeleccionarImagen(ActionEvent event) {
        FileChooser dialogoSeleccionImg = new FileChooser();
        dialogoSeleccionImg.setTitle("Selecciona una Imagen");
        //Configuración del tipo de archivo 
        FileChooser.ExtensionFilter filtroImg = new FileChooser.ExtensionFilter("Archivos de imagen (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        dialogoSeleccionImg.getExtensionFilters().add(filtroImg);
        Stage stageActual = (Stage) tfNombrePromo.getScene().getWindow();
        imagenSeleccionada = dialogoSeleccionImg.showOpenDialog(stageActual);
        if(imagenSeleccionada != null){
            mostrarLogoSeleccionado(imagenSeleccionada);
        }
    }
    
    @FXML
    private void btnCargarImagen(ActionEvent event) {
        if(imagenSeleccionada != null){
            cargarLogoServidor(imagenSeleccionada);
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona una foto", "Para cargar una imagen a la promoción, debes seleccionarla previamente", Alert.AlertType.WARNING);
        }
    }
    
     //--- Muestra la imagen seleccionada en el ImageView
    private void mostrarLogoSeleccionado(File img){
        try{
            BufferedImage buffer = ImageIO.read(img);
            Image image = SwingFXUtils.toFXImage(buffer, null);
            ivImg.setImage(image);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    //Carga la imagen al servidor
    private void cargarLogoServidor(File img){
        try {
            byte[] imgBytes = Files.readAllBytes(img.toPath());
            Mensaje msj = PromocionDAO.subirImagenPromo(promocion.getIdPromocion(), imgBytes);
            
            if(!msj.getError()){
                Utilidades.mostrarAlertaSimple("Imagen Guardada", msj.getMensaje(), Alert.AlertType.INFORMATION);
            }else{
                Utilidades.mostrarAlertaSimple("Error al subir la imagen", msj.getMensaje(), Alert.AlertType.ERROR);
            }
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("ERROR", "Error: "+ex.getMessage(), Alert.AlertType.ERROR);

        }
    }
    
    //Metodo para mostrar el logo de la empresa cagrado anteriormente
    private void obtenerImgServicio(){
        Promocion promoImg = PromocionDAO.obtenerImgPromo(promocion.getIdPromocion());
        if(promoImg != null && promoImg.getImagenBase64()!= null && !promoImg.getImagenBase64().isEmpty()){
            mostrarLogoServidor(promoImg.getImagenBase64());
        }
    }
    
    private void mostrarLogoServidor(String imgBase64){
        byte[] img = Base64.getDecoder().decode(imgBase64.replaceAll("\\n", "")); //Se tiene que eliminar los saltos de linea, debido a que el Base64 sólo soporta letrar, signos de suma o igual
        Image image = new Image(new ByteArrayInputStream(img));
        ivImg.setImage(image);
    }

    @FXML
    private void btnAsignarSuc(ActionEvent event) {
        try {
            //Cargar las vistas a memoria
            FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLPromocionSucursal.fxml"));
            Parent vista = loadMain.load();

            //Cargamos la información
            FXMLPromocionSucursalController controller = loadMain.getController();
            controller.inicializarIds(idEmpresa, promocion.getIdPromocion(), promocion.getCodigoPromocion());

            //Creamos un nuevo stage
            Stage stageNuevo = new Stage();
            Scene escena = new Scene(vista);
            
            stageNuevo.initStyle(StageStyle.DECORATED.UNDECORATED);
            vista.setOnMousePressed(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event){
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }

            });
            vista.setOnMouseDragged(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event){
                    stageNuevo.setX(event.getScreenX() -xOffset);
                    stageNuevo.setY(event.getScreenY() -yOffset);
                }

            });

            stageNuevo.setScene(escena);
            stageNuevo.setTitle("Asignar Sucursales");
            stageNuevo.initModality(Modality.APPLICATION_MODAL); //Configuracion que nos ayuda a elegir el control de las pantallas. No perimte que otro stage tenga el control hasta que se cierre el stage actual
            stageNuevo.showAndWait(); //Bloquea la pantalla de atras 


            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
