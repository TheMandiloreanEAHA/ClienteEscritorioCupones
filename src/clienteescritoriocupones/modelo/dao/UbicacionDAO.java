package clienteescritoriocupones.modelo.dao;

import clienteescritoriocupones.modelo.ConexionWS;
import clienteescritoriocupones.modelo.pojo.Mensaje;
import clienteescritoriocupones.modelo.pojo.RespuestaHTTP;
import clienteescritoriocupones.modelo.pojo.Ubicacion;
import clienteescritoriocupones.utils.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
public class UbicacionDAO {
    
    //-------------------------------- Agregar Ubicación --------------------------------\\
    public static Mensaje agregarUbicacion(Ubicacion ubicacion){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"ubicacion/agregarUbicacion";
        Gson gson = new Gson();
        String parametros = gson.toJson(ubicacion);
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTJSON(url, parametros);
        if(respuestaPeticion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Hubo un error al procesar la solictud, porfavor intentalo más tarde");
        }        
        return msj;
    }
    
    //-------------------------------- Editar Ubiciacion --------------------------------\\
    public static Mensaje editarUbicacion(Ubicacion ubicacion){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"ubicacion/editarUbicacion";
        Gson gson = new Gson();
        String parametros = gson.toJson(ubicacion);
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPUTJSON(url, parametros);
        if(respuestaPeticion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Hubo un error al procesar la solictud, porfavor intentalo más tarde");
        }        
        return msj;
    }
    //-------------------------------- Obtener Ubicación --------------------------------\\
    public static HashMap<String, Object> buscarUbicacionPorId(int idUbicacion){
        HashMap<String, Object> respService = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"ubicacion/obtenerUbicacion/"+idUbicacion;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        System.out.println(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            respService.put("error", false);
            respService.put("sucursal", respuesta.getContenido());        
        }else{
            respService.put("error", true);
            respService.put("mensaje","Hubo un error en la peticion, por el momento no se puede cargar "
                    + "la informacion de las ubicaciones");
        }
        return respService;
        
    }
    



}
