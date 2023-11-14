package clienteescritoriocupones.modelo.dao;

import clienteescritoriocupones.modelo.ConexionWS;
import clienteescritoriocupones.modelo.pojo.Empleado;
import clienteescritoriocupones.modelo.pojo.RespuestaHTTP;
import clienteescritoriocupones.modelo.pojo.RespuestaLogin;
import clienteescritoriocupones.utils.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;

/*
public class InicioSesionDAO {
    public static RespuestaLogin iniciarSesion(String nombreUsuario, String contraseña){
        RespuestaLogin respuesta =  new RespuestaLogin();
        String url = Constantes.URL_WS + "autenticacion/iniciarSesion";
        Gson gson = new Gson();
        String parametros = String.format("nombreUsuario=%s&contraseña=%s", nombreUsuario,contraseña);
        //String parametros = gson.toJson(nombreUsuario, contraseña);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTJSON(url, parametros);
        
        System.out.println(respuestaPeticion.getContenido());//->204
      
        if(respuestaPeticion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), RespuestaLogin.class);
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("Hubo un error al procesar la solictud, porfavor intentalo más tarde");
        }
        
        return respuesta;
    }
}*/
public class InicioSesionDAO {
    public static RespuestaLogin iniciarSesion(Empleado empleado){
        RespuestaLogin respuesta =  new RespuestaLogin();
        String url = Constantes.URL_WS + "autenticacion/iniciarSesion";
        Gson gson = new Gson();
        String parametros = gson.toJson(empleado);
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTJSON(url, parametros);
        if(respuestaPeticion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), RespuestaLogin.class);
        }else{
            respuesta.setError(true);
            respuesta.setMensaje("Hubo un error al procesar la solictud, porfavor intentalo más tarde");
        }
        return respuesta;
    }
}
