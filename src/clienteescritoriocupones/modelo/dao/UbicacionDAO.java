package clienteescritoriocupones.modelo.dao;

import clienteescritoriocupones.modelo.ConexionWS;
import clienteescritoriocupones.modelo.pojo.Estado;
import clienteescritoriocupones.modelo.pojo.Mensaje;
import clienteescritoriocupones.modelo.pojo.Municipio;
import clienteescritoriocupones.modelo.pojo.RespuestaHTTP;
import clienteescritoriocupones.modelo.pojo.Ubicacion;
import clienteescritoriocupones.utils.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
            respService.put("ubicacion", respuesta.getContenido());        
        }else{
            respService.put("error", true);
            
            respService.put("mensaje","Hubo un error en la peticion, por el momento no se puede cargar "
                    + "la informacion de las ubicaciones");
        }
        return respService;
        
    }
    
    public static List<Estado> obtenerEstados(){
        List<Estado> estados = new ArrayList<>();
        String url = Constantes.URL_WS+"catalogo/obtenerEstados";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        System.out.print(respuesta.getCodigoRespuesta());
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Type tipoListaEstado = new TypeToken<List<Estado>>(){}.getType(); //Este se ocupa cuando la lista es directo en el json
            Gson gson = new Gson();
            estados = gson.fromJson(respuesta.getContenido(), tipoListaEstado);
        }        
        return estados;
    }
    
    public static List<Municipio>obtenerMunicipioEstado(int idEstado){
        List<Municipio> municipios = new ArrayList<>();
        String url = Constantes.URL_WS+"catalogo/obtenerMunicipiosEstados/"+idEstado;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Type tipoListaMunicipio = new TypeToken<List<Municipio>>(){}.getType(); //Este se ocupa cuando la lista es directo en el json 
            Gson gson = new Gson();
            municipios = gson.fromJson(respuesta.getContenido(), tipoListaMunicipio);
        }        
        return municipios;
    }
    
    



}
