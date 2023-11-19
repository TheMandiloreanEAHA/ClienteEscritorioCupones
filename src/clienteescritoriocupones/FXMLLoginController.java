package clienteescritoriocupones;
//------------ LIBRERIAS USADAS --------------//

import clienteescritoriocupones.modelo.dao.InicioSesionDAO;
import clienteescritoriocupones.modelo.pojo.Empleado;
import clienteescritoriocupones.modelo.pojo.RespuestaLogin;
import clienteescritoriocupones.utils.Constantes;
import clienteescritoriocupones.utils.Utilidades;
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
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.stage.Stage;

public class FXMLLoginController implements Initializable {

    //----------------- DECALARCION DE COMPONENTES FXML -------------------//
    @FXML
    private JFXButton btnApi;
    @FXML
    private JFXButton btnFrontend;
    @FXML
    private JFXPasswordField tfContraseña;
    @FXML
    private JFXTextField tfNombreUsuario;
    @FXML
    private JFXButton btnIngresar;
    @FXML
    private JFXButton btnCerrarVentana;

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
        String nombreUsr = tfNombreUsuario.getText();
        String password = tfContraseña.getText();
        boolean isValido = true;
        if (nombreUsr.isEmpty() && password.isEmpty()) {
            Utilidades.mostrarAlertaSimple("Acceso denegado", "Ingrese las credenciales solicitadas", Alert.AlertType.ERROR);
            isValido = false;
        } else if (nombreUsr.isEmpty()) {
            Utilidades.mostrarAlertaSimple("Acceso denegado", "Nombre de usuario requerido", Alert.AlertType.ERROR);
            isValido = false;
        } else if (password.isEmpty()) {
            Utilidades.mostrarAlertaSimple("Acceso denegado", "Contraseña requerida", Alert.AlertType.ERROR);
            isValido = false;
        }

        if (isValido) {
            verificarCredenciales();
        }

    }
//--------- Metodo para asignar iconos a los botones ---------//

    private void colocarImagenBotones() {
        // creacionn de los links de la imagen a partir de un recurso
        URL linkAPI = getClass().getResource("/clienteescritoriocupones/recursos/icon_API.png");
        URL linkFrontend = getClass().getResource("/clienteescritoriocupones/recursos/icon_Frontend.png");

        // creacion de la imagen a partir de los links
        Image imagenAPI = new Image(linkAPI.toString(), 40, 40, false, false);
        Image imagenFrontend = new Image(linkFrontend.toString(), 40, 40, false, false);

        // asignación de la imagen a los botones
        btnApi.setGraphic(new ImageView(imagenAPI));
        btnFrontend.setGraphic(new ImageView(imagenFrontend));
        btnCerrarVentana.setGraphic(new ImageView(Constantes.imagenCerrar));

    }
//--------- Método para cerrar la ventana ---------//

    private void cerrarVentana() {
        Stage escenario = (Stage) btnApi.getScene().getWindow();
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
//--------- Metodo para abrir la pantalla Home ---------//    

    private void irPantallaHome(Empleado empleado) {
        Stage stageActual = (Stage) btnApi.getScene().getWindow();
        try {
            FXMLLoader loadMain = new FXMLLoader(getClass().getResource("FXMLHome.fxml"));
            Parent vista = loadMain.load();

            FXMLHomeController controladorHome = loadMain.getController();
            controladorHome.inicializarEmpleado(empleado);
            Scene escenea = new Scene(vista);

            Stage nuevoStage = new Stage();
            nuevoStage.setScene(escenea);
            nuevoStage.setTitle("Panel de Control");
            nuevoStage.getIcons().add(Constantes.imagenIcon);
            nuevoStage.setResizable(false);
            nuevoStage.show();

            // Cerrar la ventana actual
            stageActual.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

//--------- Metodo para verificar las credenciales e inicar sesión ---------//
    public void verificarCredenciales() {
        Empleado empleado = new Empleado();
        String contraseña = tfContraseña.getText();
        String nombreUsuario = tfNombreUsuario.getText();
        empleado.setNombreUsuario(nombreUsuario);
        empleado.setContraseña(contraseña);
        RespuestaLogin respuesta = InicioSesionDAO.iniciarSesion(empleado);
        if (respuesta.getError() == false) {
            Utilidades.mostrarAlertaSimple("Acceso concedido", respuesta.getMensaje(), Alert.AlertType.CONFIRMATION);
            irPantallaHome(respuesta.getEmpleadoSesion());
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
}
