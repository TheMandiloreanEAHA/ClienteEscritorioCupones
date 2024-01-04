package clienteescritoriocupones;

import clienteescritoriocupones.modelo.dao.PromocionDAO;
import clienteescritoriocupones.modelo.dao.SucursalDAO;
import clienteescritoriocupones.modelo.pojo.Mensaje;
import clienteescritoriocupones.modelo.pojo.PromocionSucursal;
import clienteescritoriocupones.modelo.pojo.Sucursal;
import clienteescritoriocupones.utils.Constantes;
import clienteescritoriocupones.utils.Utilidades;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class FXMLPromocionSucursalController implements Initializable {
    
    private int idEmpresa;
    
    private int idPromocion;
    
    private String codigo;
    
    private ObservableList<Sucursal> sucursales;

    @FXML
    private JFXButton btnCerrar;
    @FXML
    private Label lbTitulo;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private TableView<Sucursal> tvSucursales;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colSeleccionar;
    @FXML
    private JFXButton btnEliminar;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCerrar.setGraphic(new ImageView(Constantes.imagenCerrar));
        sucursales = FXCollections.observableArrayList();
        configurarTabla();
    }
    public void inicializarIds(int idEmpresa, int idPromocion, String codigo){
        this.idEmpresa = idEmpresa;
        this.idPromocion = idPromocion;
        this.codigo = codigo;
        System.out.println("ID empresa: " + this.idEmpresa);
        descargarSucursales();
    }
    
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        //colSeleccionar.setCellFactory(new PropertyValueFactory("asignada"));
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
            System.out.println("Lista Empresa: " + listaWS);
            sucursales.addAll(listaWS);
            tvSucursales.setItems(sucursales);
        }else{
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    

    @FXML
    private void cerrarVentana(ActionEvent event) {
        cerrarVentana();
    }
    private void cerrarVentana() {
        Stage escenario = (Stage) btnCerrar.getScene().getWindow();
        escenario.close();
    }

    @FXML
    private void btnRegistrar(ActionEvent event) {
        int posicion = tvSucursales.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            Sucursal sucursal = sucursales.get(posicion);
            PromocionSucursal promoSuc = new PromocionSucursal();
            promoSuc.setCodigoPromocion(codigo);
            promoSuc.setIdPromocion(idPromocion);
            promoSuc.setIdSucursal(sucursal.getIdSucursal());
            Mensaje msj = PromocionDAO.verSucursalPorPromocion(promoSuc);
            if(msj.getError()){ //Si da error, es porque no está asignada
                msj = PromocionDAO.agregarPromocionPorSucursal(promoSuc);
                if(!msj.getError()){
                    Utilidades.mostrarAlertaSimple("Sucursal asignada a la Promoción", msj.getMensaje(), Alert.AlertType.INFORMATION);
                }else{
                    Utilidades.mostrarAlertaSimple("Error al asignar la Sucursal", msj.getMensaje(), Alert.AlertType.ERROR);
                }
            }else{
                Utilidades.mostrarAlertaSimple("Advertencia", msj.getMensaje(), Alert.AlertType.WARNING);
            }
            
        }else{
            Utilidades.mostrarAlertaSimple("Selección Requerida","Debes seleccionar una sucursal", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
        int posicion = tvSucursales.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            Sucursal sucursal = sucursales.get(posicion);
            PromocionSucursal promoSuc = new PromocionSucursal();
            promoSuc.setCodigoPromocion(codigo);
            promoSuc.setIdPromocion(idPromocion);
            promoSuc.setIdSucursal(sucursal.getIdSucursal());
            Mensaje msj = PromocionDAO.verSucursalPorPromocion(promoSuc);
            if(!msj.getError()){
                msj = PromocionDAO.eliminarSucursalPorPromocion(promoSuc);
                if(!msj.getError()){
                    Utilidades.mostrarAlertaSimple("Sucursal eliminada de la Promoción", msj.getMensaje(), Alert.AlertType.INFORMATION);
                }else{
                    Utilidades.mostrarAlertaSimple("Error al retirar la Sucursal", msj.getMensaje(), Alert.AlertType.ERROR);
                }
            }else{
                Utilidades.mostrarAlertaSimple("Advertencia", msj.getMensaje(), Alert.AlertType.WARNING);
            }            
            
        }else{
            Utilidades.mostrarAlertaSimple("Selección Requerida","Debes seleccionar una sucursal", Alert.AlertType.WARNING);
        }
    }
    
}
