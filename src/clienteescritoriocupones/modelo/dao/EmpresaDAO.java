/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritoriocupones.modelo.dao;

import clienteescritoriocupones.modelo.ConexionWS;
import clienteescritoriocupones.modelo.pojo.Empresa;
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
public class EmpresaDAO {
    
    public static HashMap<String, Object> listaEmpresa(){
        HashMap<String, Object> respService = new LinkedHashMap<>();
        List<Empresa> empresa =null;
        String url = Constantes.URL_WS+"empresa/listaEmpresa";
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        System.out.println(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
           Gson gson = new Gson();
           Type tipoListaEmpresa = new TypeToken<List<Empresa>>(){}.getType();
           empresa = gson.fromJson(respuesta.getContenido(), tipoListaEmpresa);
           respService.put("error", false);
           respService.put("empresa", empresa);
        }else{
            respService.put("error", true);
            respService.put("mensaje","Hubo un error en la peticion, por el momento no se puede cargar "
                    + "la informacion de las empresas");
        }
        return respService;
    }
}
