package clienteescritoriocupones;

import clienteescritoriocupones.utils.Constantes;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Erick Adran Hernandez Aburto
 * @author Brandon Vasquez Lozano
 * @author Marcos Yahir De la caña Pérez
 */
public class ClienteEscritorioCupones extends Application {
    private double xOffset = 0;
    private double yOffset = 0;
    @Override
    public void start(Stage stage) throws Exception {
           if (isSecureEnvironment()) {
            System.out.println("La aplicación se está ejecutando en un entorno seguro (HTTPS).");
        } else {
            System.out.println("La aplicación se está ejecutando en un entorno no seguro (HTTP).");
        }
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
        
        Scene scene = new Scene(root);
       
        stage.initStyle(StageStyle.DECORATED.UNDECORATED);
        root.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
            
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>(){
               @Override
               public void handle(MouseEvent event){
                   stage.setX(event.getScreenX() -xOffset);
                   stage.setY(event.getScreenY() -yOffset);
               }
            
        });
        
        stage.setScene(scene);
        stage.getIcons().add(Constantes.imagenIcon);
        stage.show();
    }
private static boolean isSecureEnvironment() {
        SecurityManager securityManager = System.getSecurityManager();
        return securityManager != null;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
