package clienteescritoriocupones.modelo.pojo;

public class Coordenada {
    private String latitud;
    private String longitud;

    public Coordenada() {
    }

    public Coordenada(String latitud, String longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }
    
        

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
    
}
