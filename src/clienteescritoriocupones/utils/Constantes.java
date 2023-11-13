package clienteescritoriocupones.utils;

import javafx.scene.image.Image;

import java.net.URL;

public class Constantes {
    
    public static final String URL_WS ="http://localhost:8084/cuponsmart/api/";
    public static final int ERROR_URL = 101;
    public static final int ERROR_PETICION = 102;
    //------ URL DE IMAGENES CONSTANTES ------//
    public static final URL linkIcon = Constantes.class.getResource("/clienteescritoriocupones/recursos/icon_BN.png");
    public  static final URL linkCerrar = Constantes.class.getResource("/clienteescritoriocupones/recursos/icon_Cerrar.png");
    //------ DECALRACION DE IMAGENES CONSTANTES ------//
    public static final Image imagenIcon = new Image(linkIcon.toString(), 40, 40, false, false);
    public  static final Image imagenCerrar = new Image(linkCerrar.toString(), 20, 20, false, false);


}
