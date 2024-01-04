package clienteescritoriocupones;

import clienteescritoriocupones.interfaz.IRespuesta;
import clienteescritoriocupones.modelo.dao.SucursalDAO;
import clienteescritoriocupones.modelo.pojo.Empresa;
import clienteescritoriocupones.modelo.pojo.Mensaje;
import clienteescritoriocupones.modelo.pojo.Sucursal;
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


public class FXMLAdminSucursalesController implements Initializable, IRespuesta {
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    private int idEmpresa;
    
    private ObservableList<Sucursal> sucursales;

    @FXML
    private TextField tfBarraBusqueda;
    @FXML
    private JFXButton btnAgregarS;
    @FXML
    private JFXButton btnModificarS;
    @FXML
    private JFXButton btnEliminarS;
    @FXML
    private TableView<Sucursal> tvSucursales;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colNumTelefono;
    @FXML
    private TableColumn colEmpresa;
    @FXML
    private TableColumn colCalle;
    @FXML
    private TableColumn colCodigoPostal;
    @FXML
    private TableColumn colNumero;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sucursales = FXCollections.observableArrayList();
        configurarTabla();
    }    
    
    public void inicializarIdEmpresa(int idEmpresa){
        this.idEmpresa = idEmpresa;
        if(this.idEmpresa != 0){
            colEmpresa.setText("Colonia");
            colEmpresa.setCellValueFactory(new PropertyValueFactory("colonia"));
        }else{
            btnAgregarS.setDisable(true);
        }
        descargarSucursales();
        inicializarBusqueda();
    }
    
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colNumTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        colCodigoPostal.setCellValueFactory(new PropertyValueFactory("codigoPostal"));
        colCalle.setCellValueFactory(new PropertyValueFactory("calle"));
        colNumero.setCellValueFactory(new PropertyValueFactory("numero"));        
        colEmpresa.setCellValueFactory(new PropertyValueFactory("nombreEmpresa"));      
    }
    
    
    private void descargarSucursales(){
        HashMap<String, Object> respuesta = null;
        //SI el idEmpresa es 0, es porque es un Admin general, por lo que verá todas las sucursales registradas
        if(idEmpresa == 0){
            respuesta = SucursalDAO.listaSucursales();
        }else{ //Caso contrario, es un administrador comercial y sólo tiene acceso a las sucursales de su empresa
            respuesta = SucursalDAO.listaSucursal(idEmpresa);
        }
        if(!(boolean)respuesta.get("error")){
            List<Sucursal> listaWS = (List<Sucursal>)respuesta.get("sucursal");
            sucursales.addAll(listaWS);
            tvSucursales.setItems(sucursales);
        }else{
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnAgregar(ActionEvent event) {
        try {
            //Cargar las vistas a memoria
            FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLFormularioSucursal.fxml"));
            Parent vista = loadMain.load();
            
            //Pasamos el id de la empresa
            FXMLFormularioSucursalController formSucursalController = loadMain.getController();
            formSucursalController.inicializarIdEmpresa(idEmpresa, this);

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
            stageNuevo.setTitle("Registro de Sucursal");
            stageNuevo.initModality(Modality.APPLICATION_MODAL); //Configuracion que nos ayuda a elegir el control de las pantallas. No perimte que otro stage tenga el control hasta que se cierre el stage actual
            stageNuevo.showAndWait(); //Bloquea la pantalla de atras 

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnModificar(ActionEvent event) {
        int posicion = tvSucursales.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            Sucursal sucursal = sucursales.get(posicion);
            try{
                //Cargar las vistas a memoria
                FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLFormularioSucursal.fxml"));
                Parent vista = loadMain.load();
               
                //Cargamos la información de la sucursal
                FXMLFormularioSucursalController formSucController = loadMain.getController();
                //Pasar la info de la sucursal, el observador y el id de la Empresa
                formSucController.inicializarSucursal(sucursal);
                formSucController.inicializarIdEmpresa(idEmpresa, this);

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
                stageNuevo.setTitle("Registro de Sucursal");
                stageNuevo.initModality(Modality.APPLICATION_MODAL); //Configuracion que nos ayuda a elegir el control de las pantallas. No perimte que otro stage tenga el control hasta que se cierre el stage actual
                stageNuevo.showAndWait(); //Bloquea la pantalla de atras 

            }catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }else{
            Utilidades.mostrarAlertaSimple("Selección Requerida","Debes seleccionar una sucursal de la tabla para poder modificarla", Alert.AlertType.WARNING);
        }
        
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
        Sucursal sucSeleccion = tvSucursales.getSelectionModel().getSelectedItem();
        if(sucSeleccion != null){
            Optional<ButtonType> respuesta = Utilidades.mostrarAlertaConfirmacion("Confirmar Eliminación", "¿Está seguro que desea eliminar la sucursal de " + sucSeleccion.getNombre() + ", de su registro?");
            //Comparar el resultado del botón en la conficación
            if(respuesta.get()== ButtonType.OK){
                eliminar(sucSeleccion);
                recargarTabla();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Selección Requerida", "Debes Seleccionar una sucursal para su ELIMINACIÓN", Alert.AlertType.WARNING);
        }
    }
    
    private void eliminar(Sucursal sucursal){
        Mensaje msj = SucursalDAO.eliminarSucursal(sucursal);
        System.out.println(msj);
        if (msj.getError() == false) {
            Utilidades.mostrarAlertaSimple("Sucursal eliminada con exito", msj.getMensaje(), Alert.AlertType.INFORMATION);
        } else {
            Utilidades.mostrarAlertaSimple("Error:", msj.getMensaje(), Alert.AlertType.ERROR);
        }
        
    }

    @Override
    public void notificarGuardado() {
        recargarTabla();
    }
    
    public void recargarTabla(){
        sucursales.clear();
        descargarSucursales();
    }
    
    private void inicializarBusqueda(){
        if(sucursales != null){
            FilteredList<Sucursal> filtroSucursal = new FilteredList<>(sucursales, p -> true); //Filtro, se le manda un observable list y un predicado, el cual determina qué se hará dependiedo si este es true o false
            tfBarraBusqueda.textProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroSucursal.setPredicate(busqueda ->{
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
                        //Por Codigo Postal
                        if(busqueda.getCodigoPostal().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        //Por calle
                        if(busqueda.getCalle().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        //Por numero
                        if(busqueda.getNumero().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        //Por colonia
                        if(busqueda.getColonia().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        return false;
                    
                    });
                }
            });//Modifica las porpiedades de un TF, para saber qué se escribe caracter por caracter y poder hacer algo con respecto a esto
            SortedList<Sucursal> sucursalesOrdenadas = new SortedList(filtroSucursal);
            sucursalesOrdenadas.comparatorProperty().bind(tvSucursales.comparatorProperty());
            tvSucursales.setItems(sucursalesOrdenadas);
        }
    }
    
}
