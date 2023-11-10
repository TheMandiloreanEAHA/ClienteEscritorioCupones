package clienteescritoriocupones.modelo.pojo;

public class Sucursal {
    private Integer idSucursal;
    private String nombre;
    private String telefono;
    private String encargado;
    private Integer idUbicacion;
    private Integer idEmpresa;

    public Sucursal() {
    }

    public Sucursal(Integer idSucursal, String nombre, String telefono, String encargado, Integer idUbicacion, Integer idEmpresa) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.telefono = telefono;
        this.encargado = encargado;
        this.idUbicacion = idUbicacion;
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public Integer getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Integer idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
    
    
}
