package clienteescritoriocupones.utils;
import javafx.scene.control.Alert;

public class Utilidades {
    public static void mostrarAlertaSimple(String titulo, String mensaje, Alert.AlertType tipo){
    
        Alert dialogoAlerta = new Alert(tipo);
        dialogoAlerta.setTitle(titulo);
        dialogoAlerta.setHeaderText(null);
        dialogoAlerta.setContentText(mensaje);
        dialogoAlerta.showAndWait();
    }
}