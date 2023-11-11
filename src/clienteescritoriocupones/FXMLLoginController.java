package clienteescritoriocupones;
//------------ LIBRERIAS USADAS --------------//

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.stage.Stage;

public class FXMLLoginController implements Initializable {

    //----------------- DECALARCION DE COMPONENTES FXML -------------------//
    private Label label;
    @FXML
    private JFXButton btn_api;
    @FXML
    private JFXPasswordField tf_contraseña;
    @FXML
    private JFXTextField tf_nombreUsuario;
    @FXML
    private JFXButton btn_ingresar;
    @FXML
    private JFXButton btn_frontend;
    @FXML
    private JFXButton btn_cerrarVentana;

//--------- Método initialize ---------//
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        colocarImagenBotones();
        
    }
    
//--------- ActionEvents ---------//
    @FXML
    private void btnCerrarVentana(ActionEvent event) {
        cerrarVentana();
    }
    
    @FXML
    private void btnAbrirApi(ActionEvent event) {
         String url = "https://github.com/MarcosYahirDCP/APIcuponsmart";
         abrirLink(url);
    }

    @FXML
    private void btnAbrirFrontEnd(ActionEvent event) {
         String url = "https://github.com/TheMandiloreanEAHA/ClienteEscritorioCupones";
         abrirLink(url);
    }
    
     @FXML
    private void btnIngresar(ActionEvent event) {
        irPantallaHome();
    }
//--------- Metodo para asignar iconos a los botones ---------//
    private void colocarImagenBotones() {
        // creacionn de los links de la imagen a partir de un recurso
        URL linkAPI = getClass().getResource("/clienteescritoriocupones/recursos/icon_API.png");
        URL linkCerrar = getClass().getResource("/clienteescritoriocupones/recursos/icon_Cerrar.png");
        URL linkFrontend = getClass().getResource("/clienteescritoriocupones/recursos/icon_Frontend.png");

        // creacion de la imagen a partir de los links
        Image imagenAPI = new Image(linkAPI.toString(), 40, 40, false, false);
        Image imagenCerrar = new Image(linkCerrar.toString(), 20, 20, false, false);
        Image imagenFrontend = new Image(linkFrontend.toString(), 40, 40, false, false);

        // asignación de la imagen a los botones
        btn_api.setGraphic(new ImageView(imagenAPI));
        btn_frontend.setGraphic(new ImageView(imagenFrontend));
        btn_cerrarVentana.setGraphic(new ImageView(imagenCerrar));
        
    }
//--------- Método para cerrar la ventana ---------//
    private void cerrarVentana() {
        Stage escenario = (Stage) btn_api.getScene().getWindow();
        escenario.close();
    }
//--------- Metodos para abrir link desde el boton ---------//
    private void abrirLink(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void irPantallaHome() {
    Stage stageActual = (Stage) btn_api.getScene().getWindow();

    try {
        FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLHome.fxml"));
        Parent vista = loadMain.load();

        FXMLHomeController controladorHome = loadMain.getController();
        Scene escenea = new Scene(vista);
        
        Stage nuevoStage = new Stage();  
        nuevoStage.setScene(escenea);
        nuevoStage.setTitle("Panel de Control");
        nuevoStage.show();

        // Cerrar la ventana actual
        stageActual.close();
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}

   
}
