package clienteescritoriocupones;

import clienteescritoriocupones.interfaz.IRespuesta;
import clienteescritoriocupones.modelo.dao.EmpleadoDAO;
import clienteescritoriocupones.modelo.pojo.Empleado;
import clienteescritoriocupones.modelo.pojo.Mensaje;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXMLAdminEmpleadosController implements Initializable, IRespuesta {
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TextField tfBarraBusqueda;
    @FXML
    private JFXButton btnAgregarE;
    @FXML
    private JFXButton btnModificarE;
    @FXML
    private JFXButton btnEliminarE;
    @FXML
    private TableView<Empleado> tvEmpleados;
    @FXML
    private TableColumn colApellidoP;
    @FXML
    private TableColumn colCURP;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TableColumn colRol;
    
    private int idEmpresa;
    
    private ObservableList<Empleado> empleados;
    @FXML
    private TableColumn colUserName;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        empleados = FXCollections.observableArrayList();
        configurarTabla();
    }    
    
     void inicializarIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
        System.out.print(this.idEmpresa); 
        descargarEmpleados();
        inicializarBusqueda();
    }
     
     private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoP.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colUserName.setCellValueFactory(new PropertyValueFactory("nombreUsuario"));
        colCURP.setCellValueFactory(new PropertyValueFactory("CURP"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colRol.setCellValueFactory(new PropertyValueFactory("nombreRol"));
        
    }
    
    private void descargarEmpleados(){
        HashMap<String, Object> respuesta = null;
        //Si el idEmpresa es 0, es porque es un admin generla, el cual peude ver todos los empleados
        if(idEmpresa == 0){
            respuesta = EmpleadoDAO.empleados();
        }else{ //SI no, es porque es un admin comercial, el cual sólo puede ver los empleados relacionados a su empresa
            respuesta = EmpleadoDAO.listaEmpleado(idEmpresa);
        }
        
        if(!(boolean)respuesta.get("error")){
            List<Empleado> listaWS = (List<Empleado>)respuesta.get("empleado");
            empleados.addAll(listaWS);
            tvEmpleados.setItems(empleados);
        }else{
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnAgregar(ActionEvent event) {
        try {
            //Cargar las vistas a memoria
            FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLFormularioEmpleado.fxml"));
            Parent vista = loadMain.load();

            //Cargamos la información
            FXMLFormularioEmpleadoController formularioEmpleadoController = loadMain.getController();
            formularioEmpleadoController.inicializarObservador(this);

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
            stageNuevo.setTitle("Registro de Empleado");
            stageNuevo.initModality(Modality.APPLICATION_MODAL); //Configuracion que nos ayuda a elegir el control de las pantallas. No perimte que otro stage tenga el control hasta que se cierre el stage actual
            stageNuevo.showAndWait(); //Bloquea la pantalla de atras 

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void notificarGuardado() {
        empleados.clear();
        descargarEmpleados();        
    }
    
    public void recargarTabla(){
        empleados.clear();
        descargarEmpleados(); 
    }

    @FXML
    private void btnModificar(ActionEvent event) {
        int posicion = tvEmpleados.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            Empleado empleado = empleados.get(posicion);
            try{
                //Cargar las vistas a memoria
                FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLFormularioEmpleado.fxml"));
                Parent vista = loadMain.load();
               
                //Cargamos la información de la sucursal
                FXMLFormularioEmpleadoController formEmpController = loadMain.getController();
                //Pasar la info de la sucursal
                formEmpController.inicializarEmpleado(empleado, this);

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
                stageNuevo.setTitle("Datos empleado");
                stageNuevo.initModality(Modality.APPLICATION_MODAL); //Configuracion que nos ayuda a elegir el control de las pantallas. No perimte que otro stage tenga el control hasta que se cierre el stage actual
                stageNuevo.showAndWait(); //Bloquea la pantalla de atras 

            }catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }else{
            Utilidades.mostrarAlertaSimple("Selección Requerida","Debes seleccionar un empleado de la tabla para poder modificar su información", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
        Empleado empSeleccion = tvEmpleados.getSelectionModel().getSelectedItem();
        if(empSeleccion != null){
            Optional<ButtonType> respuesta = Utilidades.mostrarAlertaConfirmacion("Confirmar Eliminación", "¿Está seguro que desea eliminar al Empleado " + empSeleccion.getNombre() + ", de su registro?");
            //Comparar el resultado del botón en la conficación
            if(respuesta.get()== ButtonType.OK){
                eliminar(empSeleccion);
                recargarTabla();
            }else{
            Utilidades.mostrarAlertaSimple("Selección Requerida", "Debes Seleccionar un empleado para su ELIMINACIÓN", Alert.AlertType.WARNING);
            }
        }
    }
    
    private void eliminar(Empleado emp){
        Mensaje msj = EmpleadoDAO.eliminarEmpleado(emp);
        System.out.println(msj);
        if (msj.getError() == false) {
            Utilidades.mostrarAlertaSimple("Empleado eliminado con exito", msj.getMensaje(), Alert.AlertType.INFORMATION);
        } else {
            Utilidades.mostrarAlertaSimple("Error:", msj.getMensaje(), Alert.AlertType.ERROR);
        }
        
    }
    
    private void inicializarBusqueda(){
        if(empleados != null){
            FilteredList<Empleado> filtroEmpleado = new FilteredList<>(empleados, p -> true); //Filtro, se le manda un observable list y un predicado, el cual determina qué se hará dependiedo si este es true o false
            tfBarraBusqueda.textProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroEmpleado.setPredicate(busqueda ->{
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
                        //Por userName
                        if(busqueda.getNombreUsuario().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        //Por ROl
                        if(busqueda.getNombreRol().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        //Por Curp
                        if(busqueda.getCURP().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        return false;
                    
                    });
                }
            });//Modifica las porpiedades de un TF, para saber qué se escribe caracter por caracter y poder hacer algo con respecto a esto
            SortedList<Empleado> empeladosOrdenados = new SortedList(filtroEmpleado);
            empeladosOrdenados.comparatorProperty().bind(tvEmpleados.comparatorProperty());
            tvEmpleados.setItems(empeladosOrdenados);
        }
    }
    
}
