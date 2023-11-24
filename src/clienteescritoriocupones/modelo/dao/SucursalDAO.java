package clienteescritoriocupones.modelo.dao;

import clienteescritoriocupones.modelo.ConexionWS;
import clienteescritoriocupones.modelo.pojo.Mensaje;
import clienteescritoriocupones.modelo.pojo.RespuestaHTTP;
import clienteescritoriocupones.modelo.pojo.Sucursal;
import clienteescritoriocupones.utils.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.lang.reflect.Type;
import java.util.List;

public class SucursalDAO {
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
    
}
