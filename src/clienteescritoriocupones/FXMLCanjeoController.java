package clienteescritoriocupones;

import clienteescritoriocupones.interfaz.IRespuesta;
import clienteescritoriocupones.modelo.dao.PromocionDAO;
import clienteescritoriocupones.modelo.pojo.CanjeoCupon;
import clienteescritoriocupones.modelo.pojo.Mensaje;
import clienteescritoriocupones.utils.Constantes;
import clienteescritoriocupones.utils.Utilidades;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FXMLCanjeoController implements Initializable {
    
    private IRespuesta observador;

    @FXML
    private JFXButton btnCerrar;
    @FXML
    private Label lbTitulo;
    @FXML
    private JFXButton btnCanjear;
    @FXML
    private JFXTextField tfCodigo;
    @FXML
    private JFXTextField tfEmail;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCerrar.setGraphic(new ImageView(Constantes.imagenCerrar));
    }

    public void inicializarObservador(IRespuesta observador){
        this.observador = observador;
    }

    private boolean validar(){
        boolean isValid = true;
        
        if(tfCodigo.getText() == null || tfCodigo.getText().isEmpty()){
            isValid = false;
            Utilidades.mostrarAlertaSimple("Ingrese un código válido", "debe ingresar un código de una promoción activa", Alert.AlertType.ERROR);
        }else if(tfCodigo.getText().length() < 8 || tfCodigo.getText().length() > 8){
            isValid = false;
            Utilidades.mostrarAlertaSimple("Ingrese un código válido", "El código debede ser de 8 caracteres", Alert.AlertType.ERROR);
        }else if(tfEmail.getText() == null || tfEmail.getText().isEmpty()){
            isValid = false;
            Utilidades.mostrarAlertaSimple("Correo Obligatorio", "Ingrese un correo para poder realizar el canjeo", Alert.AlertType.ERROR);
        }        
        return isValid;
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
    private void btnCanjear(ActionEvent event) {
        boolean isValid = validar();
        if(isValid){
            CanjeoCupon canjeo = new CanjeoCupon();
            canjeo.setCodigoPromocion(tfCodigo.getText());
            canjeo.setCorreo(tfEmail.getText());
            Mensaje msj = PromocionDAO.canjearCupon(canjeo);
            if(!msj.getError()){
                Utilidades.mostrarAlertaSimple("Cupón Válido!", "Disfrute sus beneficios", Alert.AlertType.INFORMATION);
                observador.notificarGuardado();
                cerrarVentana();
            }else{
                Utilidades.mostrarAlertaSimple("Algo salió mal", msj.getMensaje(), Alert.AlertType.ERROR);
            }
        }
        
    }
    
}
