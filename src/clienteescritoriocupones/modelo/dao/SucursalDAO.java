package clienteescritoriocupones.modelo.dao;

import clienteescritoriocupones.modelo.ConexionWS;
import clienteescritoriocupones.modelo.pojo.Mensaje;
import clienteescritoriocupones.modelo.pojo.RespuestaHTTP;
import clienteescritoriocupones.utils.Constantes;
import clienteescritoriocupones.modelo.pojo.Sucursal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.lang.reflect.Type;
import java.util.List;

public class SucursalDAO {
    
    //-------------------------------- Agregar sucursal --------------------------------\\
    public static Mensaje agregarSucursal(Sucursal sucursal){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"sucursal/agregarSucursal";
        Gson gson = new Gson();
        String parametros = gson.toJson(sucursal);
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTJSON(url, parametros);
        if(respuestaPeticion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Hubo un error al procesar la solictud, porfavor intentalo más tarde");
        }        
        return msj;
    }
    
    //-------------------------------- Lista de sucursales por empresa --------------------------------\\
    public static HashMap<String, Object> listaSucursal(int idEmpresa){
        HashMap<String, Object> respService = new LinkedHashMap<>();
        List<Sucursal> sucursal =null;
        String url = Constantes.URL_WS+"sucursal/sucursalEmpresa/"+ idEmpresa;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        System.out.println(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoListaSucursal = new TypeToken<List<Sucursal>>(){}.getType();
            sucursal = gson.fromJson(respuesta.getContenido(), tipoListaSucursal);
            respService.put("error", false);
            respService.put("sucursal", sucursal);
        
        }else{
            respService.put("error", true);
            respService.put("mensaje","Hubo un error en la peticion, por el momento no se puede cargar "
                    + "la informacion de las sucursales");
        }
        return respService;
         
    }
    
    //-------------------------------- Modificar sucursal --------------------------------\\
    public static Mensaje modificarSucursal(Sucursal sucursal){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"sucursal/editarSucursal";
        Gson gson = new Gson();
        String parametros = gson.toJson(sucursal);
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPUTJSON(url, parametros);
        if(respuestaPeticion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Hubo un error al procesar la solictud, porfavor intentalo más tarde");
        }        
        return msj;      
    }
    
    //-------------------------------- Eliminar sucursal --------------------------------\\
    public static Mensaje eliminarSucursal(int idSucursal){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"sucursal/eliminarSucursal";
        Gson gson = new Gson();
        String parametros = gson.toJson(idSucursal);
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionDELETEJSON(url, parametros);
        if(respuestaPeticion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Hubo un error al procesar la solictud, porfavor intentalo más tarde");
        }        
        return msj;  
    }
    
    //-------------------------------- Buscar sucursal por nombre --------------------------------\\
    public static HashMap<String, Object> buscarSucursalPorNombre(String nombre){
        HashMap<String, Object> respService = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"sucursal/sucursalNombre/"+nombre;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        System.out.println(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            respService.put("error", false);
            respService.put("sucursal", respuesta.getContenido());
        
        }else{
            respService.put("error", true);
            respService.put("mensaje","Hubo un error en la peticion, por el momento no se puede cargar "
                    + "la informacion de las sucursales");
        }
        return respService;
        
    }
    
    //-------------------------------- Buscar sucursal por ubicación --------------------------------\\
    public static HashMap<String, Object> buscarSucursalPorUbicacion(int idUbicacion){
        HashMap<String, Object> respService = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"sucursal/sucursalUbicacion/"+idUbicacion;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        System.out.println(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            respService.put("error", false);
            respService.put("sucursal", respuesta.getContenido());
        
        }else{
            respService.put("error", true);
            respService.put("mensaje","Hubo un error en la peticion, por el momento no se puede cargar "
                    + "la informacion de las sucursales");
        }
        return respService;
        
    }
    
    //-------------------------------- Buscar sucursal por Id--------------------------------\\
    public static HashMap<String, Object> buscarSucursalPorId(int idSucursal){
        HashMap<String, Object> respService = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"sucursal/sucursalPorId/"+idSucursal;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        System.out.println(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            respService.put("error", false);
            respService.put("sucursal", respuesta.getContenido());
        
        }else{
            respService.put("error", true);
            respService.put("mensaje","Hubo un error en la peticion, por el momento no se puede cargar "
                    + "la informacion de las sucursales");
        }
        return respService;
        
    }
    
}
