
package clienteescritoriocupones.modelo.pojo;

public class RespuestaHTTP {
    private int codigoRespuesta;
    private String contenido;

    public RespuestaHTTP() {
    }

    public RespuestaHTTP(int codigoRespuesta, String contenido) {
        this.codigoRespuesta = codigoRespuesta;
        this.contenido = contenido;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public String getContenido() {
        return contenido;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
