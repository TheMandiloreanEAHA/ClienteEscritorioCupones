/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritoriocupones;

import clienteescritoriocupones.modelo.dao.EmpresaDAO;
import clienteescritoriocupones.modelo.dao.PromocionDAO;
import clienteescritoriocupones.modelo.pojo.Empresa;
import clienteescritoriocupones.modelo.pojo.Promocion;
import clienteescritoriocupones.utils.Utilidades;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class FXMLAdminPromocionesController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        promocion = FXCollections.observableArrayList();
        configurarTabla();
        descargarEmpresas();
    }    
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        colRestriccion.setCellValueFactory(new PropertyValueFactory("restriccion"));
        colInicio.setCellValueFactory(new PropertyValueFactory("inicioPromocion"));
        colFin.setCellValueFactory(new PropertyValueFactory("finPromocion"));
        colTipoPromocion.setCellValueFactory(new PropertyValueFactory("idTipoPromocion"));
        colCuponesDisponibles.setCellValueFactory(new PropertyValueFactory("numeroCupones"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        colEmpresaPromocion.setCellValueFactory(new PropertyValueFactory("idEmpresa"));
    }
    
    private void descargarEmpresas(){
        HashMap<String, Object> respuesta = PromocionDAO.listaPromociones();
        if(!(boolean)respuesta.get("error")){
            List<Promocion> listaWS = (List<Promocion>)respuesta.get("promocion");
            promocion.addAll(listaWS);
            tvPromociones.setItems(promocion);
        }else{
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }    
    
}
