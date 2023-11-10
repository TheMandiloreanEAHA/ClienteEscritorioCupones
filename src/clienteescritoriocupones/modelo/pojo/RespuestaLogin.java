
package clienteescritoriocupones.modelo.pojo;

public class RespuestaLogin {
    public Boolean error;
    private String mensaje;
    private Empleado empleadoSesion;

    public RespuestaLogin() {
    }

    public RespuestaLogin(Boolean error, String mensaje, Empleado empleadoSesion) {
        this.error = error;
        this.mensaje = mensaje;
        this.empleadoSesion = empleadoSesion;
    }

    public Boolean getError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Empleado getEmpleadoSesion() {
        return empleadoSesion;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setEmpleadoSesion(Empleado empleadoSesion) {
        this.empleadoSesion = empleadoSesion;
    }
}