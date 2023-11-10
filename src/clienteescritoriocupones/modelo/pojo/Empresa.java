package clienteescritoriocupones.modelo.pojo;

public class Empresa {
    private Integer idEmpresa;
    private String nombre;
    private String nombreComercial;
    private byte[] logo;
    private String representante;
    private String correo;
    private String telefono;
    private String paginaWeb;
    private String RFC;
    private String status;
    private Integer idUbicación;

    public Empresa() {
    }

    public Empresa(Integer idEmpresa, String nombre, String nombreComercial, byte[] logo, String representante, String correo, String telefono, String paginaWeb, String RFC, String status, Integer idUbicación) {
        this.idEmpresa = idEmpresa;
        this.nombre = nombre;
        this.nombreComercial = nombreComercial;
        this.logo = logo;
        this.representante = representante;
        this.correo = correo;
        this.telefono = telefono;
        this.paginaWeb = paginaWeb;
        this.RFC = RFC;
        this.status = status;
        this.idUbicación = idUbicación;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdUbicación() {
        return idUbicación;
    }

    public void setIdUbicación(Integer idUbicación) {
        this.idUbicación = idUbicación;
    }
    
    
}
