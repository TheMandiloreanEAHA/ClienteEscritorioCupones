package clienteescritoriocupones;

import clienteescritoriocupones.interfaz.IRespuesta;
import clienteescritoriocupones.modelo.dao.EmpleadoDAO;
import clienteescritoriocupones.modelo.dao.EmpresaDAO;
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


public class FXMLAdminEmpresasController implements Initializable, IRespuesta {
    
    private double xOffset = 0;
    private double yOffset = 0;
    
    private ObservableList<Empresa> empresa;
    @FXML
    private TextField tfBarraBusqueda;
    @FXML
    private JFXButton btnAgregarE;
    @FXML
    private JFXButton btnModificarE;
    @FXML
    private JFXButton btnEliminarE;
    @FXML
    private JFXButton btnUbicacion;
    @FXML
    private TableView<Empresa> tvEmpresa;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colRFC;
    @FXML
    private TableColumn colRepresentante;
    @FXML
    private TableColumn colPaginaWeb;
    @FXML
    private TableColumn colEstatus;
    @FXML
    private TableColumn colCorreo;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        empresa = FXCollections.observableArrayList();
        configurarTabla();
        descargarEmpresas();
        inicializarBusqueda();
    }    
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colRFC.setCellValueFactory(new PropertyValueFactory("RFC"));
        colRepresentante.setCellValueFactory(new PropertyValueFactory("representante"));
        colPaginaWeb.setCellValueFactory(new PropertyValueFactory("paginaWeb"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
    }
    
    private void descargarEmpresas(){
        HashMap<String, Object> respuesta = EmpresaDAO.listaEmpresa();
        if(!(boolean)respuesta.get("error")){
            List<Empresa> listaWS = (List<Empresa>)respuesta.get("empresa");
            empresa.addAll(listaWS);
            tvEmpresa.setItems(empresa);
        }else{
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void btnAgregar(ActionEvent event) {
        try {
            //Cargar las vistas a memoria
            FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLFormularioEmpresas.fxml"));
            Parent vista = loadMain.load();

            //Cargamos la información
            FXMLFormularioEmpresasController formularioEmpresaController = loadMain.getController();
            formularioEmpresaController.inicializarObservador(this);

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
            stageNuevo.setTitle("Registro de empresa");
            stageNuevo.initModality(Modality.APPLICATION_MODAL); //Configuracion que nos ayuda a elegir el control de las pantallas. No perimte que otro stage tenga el control hasta que se cierre el stage actual
            stageNuevo.showAndWait(); //Bloquea la pantalla de atras 

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    @FXML
    private void btnModificar(ActionEvent event) {
        int posicion = tvEmpresa.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            Empresa empr = empresa.get(posicion);
            try{
                //Cargar las vistas a memoria
                FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLFormularioEmpresas.fxml"));
                Parent vista = loadMain.load();

                //Cargamos la información
                FXMLFormularioEmpresasController formularioEmpresaController = loadMain.getController();
                formularioEmpresaController.inicializarObservador(this);
                formularioEmpresaController.inicializarEmpresa(empr);

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
                stageNuevo.setTitle("Modificar información de la empresa");
                stageNuevo.initModality(Modality.APPLICATION_MODAL); //Configuracion que nos ayuda a elegir el control de las pantallas. No perimte que otro stage tenga el control hasta que se cierre el stage actual
                stageNuevo.showAndWait(); //Bloquea la pantalla de atras 
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Selección Requerida","Debes seleccionar una Empresa de la tabla para poder modificar su información", Alert.AlertType.WARNING);
        }
        
    }
    

    @FXML
    private void btnEliminar(ActionEvent event) {
        Empresa empresaSeleccion = tvEmpresa.getSelectionModel().getSelectedItem();
        if(empresaSeleccion != null){
            Optional<ButtonType> respuesta = Utilidades.mostrarAlertaConfirmacion("Confirmar Eliminación", "¿Está seguro que desea eliminar la empresa " + empresaSeleccion.getNombre() + ", juntos con TODOS sus empleados dentro de su registro?");
            //Comparar el resultado del botón en la conficación
            if(respuesta.get() == ButtonType.OK){
                eliminar(empresaSeleccion);
                recargarTabla();
            }
        }
    }
    
    private void eliminar(Empresa empr){
        HashMap<String, Object> respuesta = SucursalDAO.listaSucursal(empr.getIdEmpresa()); //Comprobamos si la empresa tiene sucursales asignadas
        if(!(boolean)respuesta.get("error")){
            List<Sucursal> listaEmpresas = (List<Sucursal>)respuesta.get("sucursal");
            if(!listaEmpresas.isEmpty()){
                Utilidades.mostrarAlertaSimple("Sucursales asignadas:", "LA empresa tiene sucursales asignadas. Asegurese se eliminar las sucursales asociadas a esta empresa antes de realizar su eliminación", Alert.AlertType.ERROR);           
            }else{ //Si no tiene sucursdales asignadas, elimina a la empresa junto con sus empleados asociados.  
                Mensaje msj = EmpleadoDAO.eliminarEmpleadosEmpresa(empr);                
                if (msj.getError() == false) {
                    Utilidades.mostrarAlertaSimple("Empleados eliminados", "Empleados de la empresa "+ empr.getNombreComercial() + "Eliminados", Alert.AlertType.INFORMATION);
                    msj = EmpresaDAO.eliminarEmpresa(empr);
                    if (msj.getError() == false) {
                        Utilidades.mostrarAlertaSimple("Empresa eliminada con exito", msj.getMensaje(), Alert.AlertType.INFORMATION);
                    }else {
                        Utilidades.mostrarAlertaSimple("Error:", msj.getMensaje(), Alert.AlertType.ERROR);           
                    }                    
                }else {
                    Utilidades.mostrarAlertaSimple("Error:", msj.getMensaje(), Alert.AlertType.ERROR);           
                }
                                 
            }    
        }else{
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
        //Si tiene sucursales asignadas, no se realiza la eliminación
           
        
    }
    @Override
    public void notificarGuardado() {
        recargarTabla();
    }
    
    public void recargarTabla(){
        empresa.clear();
        descargarEmpresas();
    }

    @FXML
    private void btnUbicacion(ActionEvent event) {
        int posicion = tvEmpresa.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            Empresa empr = empresa.get(posicion);
            try{
                 //Cargar las vistas a memoria
                FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLFormularioUbicacion.fxml"));
                Parent vista = loadMain.load();
            
                //Cargamos la información
                FXMLFormularioUbicacionController formUbiController = loadMain.getController();
                formUbiController.inializarEmpresa(empr);

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
                stageNuevo.setTitle("Ubicación Empresa");
                stageNuevo.initModality(Modality.APPLICATION_MODAL); //Configuracion que nos ayuda a elegir el control de las pantallas. No perimte que otro stage tenga el control hasta que se cierre el stage actual
                stageNuevo.showAndWait(); //Bloquea la pantalla de atras 
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }else{
            Utilidades.mostrarAlertaSimple("Selección Requerida","Debes seleccionar una Empresa de la tabla para poder ver acerca de su ubicación", Alert.AlertType.WARNING);
        }
    }
    
    private void inicializarBusqueda(){
        if(empresa != null){
            FilteredList<Empresa> filtroEmpresa = new FilteredList<>(empresa, p -> true); //Filtro, se le manda un observable list y un predicado, el cual determina qué se hará dependiedo si este es true o false
            tfBarraBusqueda.textProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroEmpresa.setPredicate(busqueda ->{
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
                        //Por RFC
                        if(busqueda.getRFC().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        //Por Nombre del representante
                        if(busqueda.getRepresentante().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        return false;
                    
                    });
                }
            });//Modifica las porpiedades de un TF, para saber qué se escribe caracter por caracter y poder hacer algo con respecto a esto
            SortedList<Empresa> empresasOrdenadas = new SortedList(filtroEmpresa);
            empresasOrdenadas.comparatorProperty().bind(tvEmpresa.comparatorProperty());
            tvEmpresa.setItems(empresasOrdenadas);
        }
    }


    
}
