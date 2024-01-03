package clienteescritoriocupones.modelo.dao;

import clienteescritoriocupones.modelo.ConexionWS;
import clienteescritoriocupones.modelo.pojo.Empresa;
import clienteescritoriocupones.modelo.pojo.Mensaje;
import clienteescritoriocupones.modelo.pojo.RespuestaHTTP;
import clienteescritoriocupones.utils.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class EmpresaDAO {
    
    //---------------------------------------- Obtener lista de empresas ----------------------------------------\\
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
    
    //---------------------------------------- Agregar empresa ----------------------------------------\\
    public static Mensaje agregarEmpresa(Empresa empresa){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"empresa/agregarEmpresa";
        Gson gson = new Gson();
        String parametros = gson.toJson(empresa);
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTJSON(url, parametros);
        if(respuestaPeticion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Hubo un error al procesar la solictud, porfavor intentalo más tarde");
        }        
        return msj;
    }
    
    //---------------------------------------- Modificar empresa ----------------------------------------\\
    public static Mensaje modificarEmpresa(Empresa empresa){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"empresa/editarEmpresa";
        Gson gson = new Gson();
        String parametros = gson.toJson(empresa);
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPUTJSON(url, parametros);
        if(respuestaPeticion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Hubo un error al procesar la solictud, porfavor intentalo más tarde");
        }        
        return msj;
    }
    //---------------------------------------- Eliminar empresa por RFC----------------------------------------\\
    public static Mensaje eliminarEmpresa(Empresa empresa){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"empresa/eliminarEmpresa";
        Gson gson = new Gson();
        String parametros = gson.toJson(empresa);
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionDELETEJSON(url, parametros);
        if(respuestaPeticion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Hubo un error al procesar la solictud, porfavor intentalo más tarde");
        }        
        return msj;
    }
    //---------------------------------------- Buscar empresa por RFC----------------------------------------\\
    public static HashMap<String, Object> buscarEmpresaPorRFC(String RFC){
        HashMap<String, Object> respService = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"empresa/empresaRFC/"+RFC;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        System.out.println(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            respService.put("error", false);
            respService.put("empresa", respuesta.getContenido());
        
        }else{
            respService.put("error", true);
            respService.put("mensaje","Hubo un error en la peticion, por el momento no se puede cargar "
                    + "la informacion de las empresas");
        }
        return respService;        
    }
    
    //---------------------------------------- Buscar empresa por ID----------------------------------------\\
    public static HashMap<String, Object> buscarEmpresaPorId(int idEmpresa){
        HashMap<String, Object> respService = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"empresa/empresaPorId/"+idEmpresa;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url); 
        Empresa empresa = new Empresa();
        Gson gson = new Gson();
        System.out.println(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            empresa = gson.fromJson(respuesta.getContenido(), Empresa.class);
            respService.put("error", false);
            respService.put("empresa", empresa);
        
        }else{
            respService.put("error", true);
            respService.put("mensaje","Hubo un error en la peticion, por el momento no se puede cargar "
                    + "la informacion de las empresas");
        }
        return respService;        
    }
    
    //---------------------------------------- Buscar empresa por nombreComercial----------------------------------------\\
    public static HashMap<String, Object> buscarEmpresaPorNombreComercial(String nombreComercial){
        HashMap<String, Object> respService = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"empresa/empresaNombreComercial/"+nombreComercial;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        System.out.println(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            respService.put("error", false);
            respService.put("empresa", respuesta.getContenido());
        
        }else{
            respService.put("error", true);
            respService.put("mensaje","Hubo un error en la peticion, por el momento no se puede cargar "
                    + "la informacion de las empresas");
        }
        return respService;        
    }
    
    //---------------------------------------- Buscar empresa por nombre del Representante----------------------------------------\\
    public static HashMap<String, Object> buscarEmpresaPorNombreRepresentante(String nombreRepresentante){
        HashMap<String, Object> respService = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"empresa/empresaRepresentante/"+nombreRepresentante;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        System.out.println(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            respService.put("error", false);
            respService.put("empresa", respuesta.getContenido());
        
        }else{
            respService.put("error", true);
            respService.put("mensaje","Hubo un error en la peticion, por el momento no se puede cargar "
                    + "la informacion de las empresas");
        }
        return respService;        
    }
    //---------------------------------------- Subir logo Empresa ----------------------------------------\\
     public static Mensaje subirLogoEmpresa(int idEmpresa, byte[] logo){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "empresa/subirLogo/" + idEmpresa;
        RespuestaHTTP respuesta = ConexionWS.peticionPUTImagen(url, logo);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Error al envíar el logo de la empresa, por favor intnte más tarde.");
        }
        return msj;
    }
    
     //---------------------------------------- Obtener Logo empresa ----------------------------------------\\
    public static Empresa obtenerLogoEmpresa(int idEmpresa){
        Empresa empresa = null;
        String url = Constantes.URL_WS + "empresa/obtenerLogo/"+idEmpresa;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            empresa = gson.fromJson(respuesta.getContenido(), Empresa.class);
        }
               
        return empresa;
    }
}
