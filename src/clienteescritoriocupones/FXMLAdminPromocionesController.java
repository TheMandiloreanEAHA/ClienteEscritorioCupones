package clienteescritoriocupones;

import clienteescritoriocupones.interfaz.IRespuesta;
import clienteescritoriocupones.modelo.dao.PromocionDAO;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
    @FXML
    private JFXButton btnCanjearCupon;

   @Override
    public void initialize(URL url, ResourceBundle rb) {
        promocion = FXCollections.observableArrayList();
        configurarTabla();
        
    } 
    
    public void inicializarIdEmpresa(int idEmpresa){
        this.idEmpresa = idEmpresa;
        descargarPromos();
        inicializarBusqueda();
        if(this.idEmpresa != 0){
            //Tiene una empresa signada, por lo que sólo ve las promos de su empresa
            colEmpresaPromocion.setVisible(false);
            
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
        HashMap<String, Object> respuesta = null;        
        //SI el idEmpresa es 0, es porque es un Admin general, por lo que verá todas las promos registradas
        if(idEmpresa == 0){
            respuesta = PromocionDAO.listaPromociones();
        }else{
            respuesta = PromocionDAO.listaPromocionesPorempresa(idEmpresa);
        }
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
    
    private void recargarTabla(){
        promocion.clear();
        descargarPromos(); 
    }
    
    private void inicializarBusqueda(){
        if(promocion != null){
            FilteredList<Promocion> filtropromo = new FilteredList<>(promocion, p -> true); //Filtro, se le manda un observable list y un predicado, el cual determina qué se hará dependiedo si este es true o false
            tfBarraBusqueda.textProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtropromo.setPredicate(busqueda ->{
                        //Si no hay nada en el tf (es vacío), mete todo los valores
                        if(newValue == null || newValue.isEmpty()){
                            return true;                        
                        } 
                        String lowerFilter = newValue.toLowerCase();
                        //-- Reglas de filtrado --\\
                        //Por nombre
                        if(busqueda.getNombre().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        //Por fecha de inicio
                        if(busqueda.getInicioPromocion().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        //Por fecha de fin
                        if(busqueda.getFinPromocion().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        return false;
                    
                    });
                }
            });//Modifica las porpiedades de un TF, para saber qué se escribe caracter por caracter y poder hacer algo con respecto a esto
            SortedList<Promocion> promocionesOrdenadas = new SortedList(filtropromo);
            promocionesOrdenadas.comparatorProperty().bind(tvPromociones.comparatorProperty());
            tvPromociones.setItems(promocionesOrdenadas);
        }
    }

    @FXML
    private void btnCanjear(ActionEvent event) {
        try {
            //Cargar las vistas a memoria
            FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLCanjeo.fxml"));
            Parent vista = loadMain.load();

            //Cargamos la información
            FXMLCanjeoController controller = loadMain.getController();
            controller.inicializarObservador(this);
            

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
            stageNuevo.setTitle("Canejar cupón");
            stageNuevo.initModality(Modality.APPLICATION_MODAL); //Configuracion que nos ayuda a elegir el control de las pantallas. No perimte que otro stage tenga el control hasta que se cierre el stage actual
            stageNuevo.showAndWait(); //Bloquea la pantalla de atras 


            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
