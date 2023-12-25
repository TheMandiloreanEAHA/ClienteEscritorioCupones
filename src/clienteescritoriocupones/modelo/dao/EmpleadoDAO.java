package clienteescritoriocupones.modelo.dao;

import clienteescritoriocupones.modelo.ConexionWS;
import clienteescritoriocupones.modelo.pojo.Empleado;
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

public class EmpleadoDAO {
    
    //-------------------------------- Lista de empleados por empresa --------------------------------\\
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
    
    //-------------------------------- Agregar emplado --------------------------------\\
    public static Mensaje agregarEmpleado(Empleado empleado){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"empleado/agregarEmpleado";
        Gson gson = new Gson();
        String parametros = gson.toJson(empleado);
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTJSON(url, parametros);
        if(respuestaPeticion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Hubo un error al procesar la solictud, porfavor intentalo más tarde");
        }        
        return msj;
    }
    
    //-------------------------------- Modificar emplado --------------------------------\\
    public static Mensaje modificarEmpleado(Empleado empleado){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"empleado/editarEmpleado";
        Gson gson = new Gson();
        String parametros = gson.toJson(empleado);
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPUTJSON(url, parametros);
        if(respuestaPeticion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Hubo un error al procesar la solictud, porfavor intentalo más tarde");
        }        
        return msj;
    }
    
    //-------------------------------- Eliminar empleado --------------------------------\\
    public static Mensaje eliminarEmpleado(Empleado empleado){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"empleado/eliminarEmpleado";
        Gson gson = new Gson();
        String parametros = gson.toJson(empleado);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionDELETEJSON(url, parametros);
        if(respuestaPeticion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Hubo un error al procesar la solictud, porfavor intentalo más tarde");
        }        
        return msj;
    }
    
    //-------------------------------- Lista de emplados por rol --------------------------------\\
    public static HashMap<String, Object> listaSucursal(int idRol){
        HashMap<String, Object> respService = new LinkedHashMap<>();
        List<Empleado> empleados =null;
        String url = Constantes.URL_WS+"empleado/empleadoPorRol/"+ idRol;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        System.out.println(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoListaEmpleado = new TypeToken<List<Empleado>>(){}.getType();
            empleados = gson.fromJson(respuesta.getContenido(), tipoListaEmpleado);
            respService.put("error", false);
            respService.put("empleado", empleados);
        
        }else{
            respService.put("error", true);
            respService.put("mensaje","Hubo un error en la peticion, por el momento no se puede cargar "
                    + "la informacion de los empleados");
        }
        return respService;
         
    }
    
    //-------------------------------- Buscar emplado por nombre --------------------------------\\
    public static HashMap<String, Object> buscarEmpleadoPorNombre(String nombre){
        HashMap<String, Object> respService = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"empleado/empleadoPorNombre/"+nombre;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        System.out.println(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            respService.put("error", false);
            respService.put("empleado", respuesta.getContenido());
        
        }else{
            respService.put("error", true);
            respService.put("mensaje","Hubo un error en la peticion, por el momento no se puede cargar "
                    + "la informacion de los empleados");
        }
        return respService;
        
    }
    //-------------------------------- Buscar emplado por nombre de usuario --------------------------------\\
    public static HashMap<String, Object> buscarEmpleadoPorNombreDeUsuario(String nombreUsuario){
        HashMap<String, Object> respService = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"empleado/empleadoPorNombreUsuario/"+nombreUsuario;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        System.out.println(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            respService.put("error", false);
            respService.put("empleado", respuesta.getContenido());
        
        }else{
            respService.put("error", true);
            respService.put("mensaje","Hubo un error en la peticion, por el momento no se puede cargar "
                    + "la informacion de los empleados");
        }
        return respService;
        
    }
    //-------------------------------- Buscar emplado por id--------------------------------\\
    public static HashMap<String, Object> buscarEmpleadoPorNombreDeUsuario(int idUsuario){
        HashMap<String, Object> respService = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"empleado/empleadoPorId/"+idUsuario;
        RespuestaHTTP respuesta = ConexionWS.peticionGET(url);
        System.out.println(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            respService.put("error", false);
            respService.put("empleado", respuesta.getContenido());
        
        }else{
            respService.put("error", true);
            respService.put("mensaje","Hubo un error en la peticion, por el momento no se puede cargar "
                    + "la informacion de los empleados");
        }
        return respService;
        
    }
    //
}
