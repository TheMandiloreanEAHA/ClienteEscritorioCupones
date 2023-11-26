package clienteescritoriocupones.modelo.dao;

import clienteescritoriocupones.modelo.ConexionWS;
import clienteescritoriocupones.modelo.pojo.Empleado;
import clienteescritoriocupones.modelo.pojo.RespuestaHTTP;
import clienteescritoriocupones.utils.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class EmpleadoDAO {
    
    public static HashMap<String, Object> listaEmpleado(int idEmpresa){
        HashMap<String, Object> respService = new LinkedHashMap<>();
        List<Empleado> empleado =null;
        String url = Constantes.URL_WS+"empleado/listaEmpleados/"+ idEmpresa;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        System.out.println(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoListaEmpleado = new TypeToken<List<Empleado>>(){}.getType();
            empleado = gson.fromJson(respuesta.getContenido(), tipoListaEmpleado);
            respService.put("error", false);
            respService.put("empleado", empleado);
        
        }else{
            respService.put("error", true);
            respService.put("mensaje","Hubo un error en la peticion, por el momento no se puede cargar "
                    + "la informacion de las sucursales");
        }
        return respService;
         
    }
}
