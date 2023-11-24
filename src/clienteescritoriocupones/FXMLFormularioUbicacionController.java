package clienteescritoriocupones;

import clienteescritoriocupones.modelo.pojo.Estado;
import clienteescritoriocupones.modelo.pojo.Municipio;
import clienteescritoriocupones.modelo.pojo.Ubicacion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class FXMLFormularioUbicacionController implements Initializable {
    
    private Ubicacion ubicacion;

    @FXML
    private JFXButton btnCerrar;
    @FXML
    private JFXTextField tfCalle;
    @FXML
    private JFXTextField tfNumero;
    @FXML
    private JFXTextField tfNumero1;
    @FXML
    private JFXButton btnGuardarDatos;
    @FXML
    private JFXTextField tfLatitud;
    @FXML
    private JFXTextField tfLongitud;
    @FXML
    private ComboBox<Estado> cbEstados;
    @FXML
    private ComboBox<Municipio> cbMunicipios;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarUbicacion(Ubicacion ubicacion){
        this.ubicacion = ubicacion;
        if(ubicacion == null){
            System.out.println("La ubicacion es nula");
        }else{
            System.out.print(ubicacion.getCalle()); 
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
    private void btnGuardarCambios(ActionEvent event) {
        

    }
    
}
