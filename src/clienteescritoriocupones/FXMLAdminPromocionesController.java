package clienteescritoriocupones;

import clienteescritoriocupones.interfaz.IRespuesta;
import clienteescritoriocupones.modelo.dao.EmpresaDAO;
import clienteescritoriocupones.modelo.dao.PromocionDAO;
import clienteescritoriocupones.modelo.pojo.Empresa;
import clienteescritoriocupones.modelo.pojo.Mensaje;
import clienteescritoriocupones.modelo.pojo.Promocion;
import clienteescritoriocupones.utils.Utilidades;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXMLAdminPromocionesController implements Initializable, IRespuesta {
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    private int idEmpresa;

    private ObservableList<Promocion> promocion;
    @FXML
    private TextField tfBarraBusqueda;
    @FXML
    private JFXButton btnAgregarP;
    @FXML
    private JFXButton btnModificarP;
    @FXML
    private JFXButton btnEliminarP;
    @FXML
    private TableView<Promocion> tvPromociones;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colInicio;
    @FXML
    private TableColumn colFin;
    @FXML
    private TableColumn colRestriccion;
    @FXML
    private TableColumn colTipoPromocion;
    @FXML
    private TableColumn colCuponesDisponibles;
    @FXML
    private TableColumn colEstatus;
    @FXML
    private TableColumn colEmpresaPromocion;

   @Override
    public void initialize(URL url, ResourceBundle rb) {
        promocion = FXCollections.observableArrayList();
        configurarTabla();
        descargarPromos();
    } 
    
    public void inicializarIdEmpresa(int idEmpresa){
        this.idEmpresa = idEmpresa;
        if(this.idEmpresa == 0){
            System.out.println("Admin General");
        }else{
            System.out.println("Admin Comercial");
        }
    }
    
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        colRestriccion.setCellValueFactory(new PropertyValueFactory("restriccion"));
        colInicio.setCellValueFactory(new PropertyValueFactory("inicioPromocion"));
        colFin.setCellValueFactory(new PropertyValueFactory("finPromocion"));
        colTipoPromocion.setCellValueFactory(new PropertyValueFactory("tipo"));
        colCuponesDisponibles.setCellValueFactory(new PropertyValueFactory("numeroCupones"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        colEmpresaPromocion.setCellValueFactory(new PropertyValueFactory("empresa"));
    }
    
    private void descargarPromos(){
        HashMap<String, Object> respuesta = PromocionDAO.listaPromociones();
        if(!(boolean)respuesta.get("error")){
            List<Promocion> listaWS = (List<Promocion>)respuesta.get("promocion");
            promocion.addAll(listaWS);
            tvPromociones.setItems(promocion);
        }else{
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }    

    @FXML
    private void btnAgregar(ActionEvent event) {
        try {
            //Cargar las vistas a memoria
            FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLFormularioPromociones.fxml"));
            Parent vista = loadMain.load();

            //Cargamos la información
            FXMLFormularioPromocionesController formularioPromoController = loadMain.getController();
            formularioPromoController.inicializarIdEmpresa(idEmpresa, this);

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
            stageNuevo.setTitle("Registro de promoción");
            stageNuevo.initModality(Modality.APPLICATION_MODAL); //Configuracion que nos ayuda a elegir el control de las pantallas. No perimte que otro stage tenga el control hasta que se cierre el stage actual
            stageNuevo.showAndWait(); //Bloquea la pantalla de atras 


            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnModificar(ActionEvent event) {
        int posicion = tvPromociones.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            Promocion promo = promocion.get(posicion);
            try{
                //Cargar las vistas a memoria
                FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLFormularioPromociones.fxml"));
                Parent vista = loadMain.load();

                //Cargamos la información
                FXMLFormularioPromocionesController formularioPromoController = loadMain.getController();
                formularioPromoController.inicializarIdEmpresa(idEmpresa, this);
                formularioPromoController.inicializarPromocion(promo);

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
                stageNuevo.setTitle("Edición de promoción");
                stageNuevo.initModality(Modality.APPLICATION_MODAL); //Configuracion que nos ayuda a elegir el control de las pantallas. No perimte que otro stage tenga el control hasta que se cierre el stage actual
                stageNuevo.showAndWait(); //Bloquea la pantalla de atras 
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Selección Requerida","Debes seleccionar una promoción de la tabla para poder modificar su información", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
        Promocion promoSeleccion = tvPromociones.getSelectionModel().getSelectedItem();
        if(promoSeleccion != null){
            Optional<ButtonType> respuesta = Utilidades.mostrarAlertaConfirmacion("Confirmar Eliminación", "¿Está seguro que desea eliminar la promoción " +promoSeleccion.getNombre() + ". Recuerde que también puede cambiar su status en el apartado de Modificar");
            //Comparar el resultado del botón en la conficación
            if(respuesta.get() == ButtonType.OK){
                System.out.println("ID promo a eliminar: "+ promoSeleccion.getIdPromocion());
                eliminar(promoSeleccion);
                recargarTabla();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Selección Requerida","Debes seleccionar una promoción de la tabla para poder eliminarla", Alert.AlertType.WARNING);
        }
    }
    
    private void eliminar(Promocion promo){
        Mensaje msj = PromocionDAO.eliminarPromocion(promo);
        if (msj.getError() == false) {
            Utilidades.mostrarAlertaSimple("Promoción eliminada", msj.getMensaje(), Alert.AlertType.INFORMATION);             
        }else {
            Utilidades.mostrarAlertaSimple("Error:", msj.getMensaje(), Alert.AlertType.ERROR);           
        }
    }

    @Override
    public void notificarGuardado() {
        recargarTabla();
    }
    
    public void recargarTabla(){
        System.out.println("HOA");
        promocion.clear();
        descargarPromos(); 
    }
    
}
