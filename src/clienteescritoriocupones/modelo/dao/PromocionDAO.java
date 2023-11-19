package clienteescritoriocupones.modelo.dao;

import clienteescritoriocupones.modelo.ConexionWS;
import clienteescritoriocupones.modelo.pojo.Promocion;
import clienteescritoriocupones.modelo.pojo.RespuestaHTTP;
import clienteescritoriocupones.utils.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author brandon
 */
public class PromocionDAO {
    
    public static HashMap<String, Object> listaPromociones(){
        HashMap<String, Object> respService = new LinkedHashMap<>();
        List<Promocion> promocion =null;
        String url = Constantes.URL_WS+"promocion/promociones";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        System.out.println(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
           Gson gson = new Gson();
           Type tipoListaPromocion = new TypeToken<List<Promocion>>(){}.getType();
           promocion = gson.fromJson(respuesta.getContenido(), tipoListaPromocion);
           respService.put("error", false);
           respService.put("promocion", promocion);
        }else{
            respService.put("error", true);
            respService.put("mensaje","Hubo un error en la peticion, por el momento no se puede cargar "
                    + "la informacion de los pacientes");
        }
        return respService;
    }
}
