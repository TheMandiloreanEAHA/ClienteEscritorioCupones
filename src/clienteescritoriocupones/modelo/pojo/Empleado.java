package clienteescritoriocupones.modelo.pojo;

public class Empleado {
    private Integer idEmpleado;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String CURP;
    private String correo;
    private String nombreUsuario;
    private String contraseña;
    private Integer idRol;
    private Integer idEmpresa;
    private String nombreRol;
    private String nombreEmpresa;
    private String nombreComercial;
    private String RFC;
    private Byte[] logo;
    private String logoBase64;

    public Empleado() {
    }

    public Empleado(Integer idEmpleado, String nombre, String apellidoPaterno, String apellidoMaterno, String CURP, String correo, String nombreUsuario, String contraseña, Integer idRol, Integer idEmpresa, String nombreRol, String nombreEmpresa, String nombreComercial, String RFC, Byte[] logo, String logoBase64) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.CURP = CURP;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.idRol = idRol;
        this.idEmpresa = idEmpresa;
        this.nombreRol = nombreRol;
        this.nombreEmpresa = nombreEmpresa;
        this.nombreComercial = nombreComercial;
        this.RFC = RFC;
        this.logo = logo;
        this.logoBase64 = logoBase64;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public Byte[] getLogo() {
        return logo;
    }

    public void setLogo(Byte[] logo) {
        this.logo = logo;
    }

    public String getLogoBase64() {
        return logoBase64;
    }

    public void setLogoBase64(String logoBase64) {
        this.logoBase64 = logoBase64;
    }
    
    
}
