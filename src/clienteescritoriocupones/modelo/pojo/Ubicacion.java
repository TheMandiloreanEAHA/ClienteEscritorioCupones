package clienteescritoriocupones.modelo.pojo;

public class Ubicacion {
    private Integer idUbicacion;
    private String calle;
    private String numero;
    private String codigoPostal;
    private String colonia;
    private String latitud;
    private String longitud;
    private Integer idMunicipio;
    private String municipio;
    private Integer idEstado;
    private String estado;

    public Ubicacion() {
    }

    public Ubicacion(Integer idUbicacion, String calle, String numero, String codigoPostal, String colonia, String latitud, String longitud, Integer idMunicipio, String municipio, Integer idEstado, String estado) {
        this.idUbicacion = idUbicacion;
        this.calle = calle;
        this.numero = numero;
        this.codigoPostal = codigoPostal;
        this.colonia = colonia;
        this.latitud = latitud;
        this.longitud = longitud;
        this.idMunicipio = idMunicipio;
        this.municipio = municipio;
        this.idEstado = idEstado;
        this.estado = estado;
    }

    public Integer getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Integer idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
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

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
