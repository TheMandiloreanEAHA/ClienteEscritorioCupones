package clienteescritoriocupones.modelo.pojo;

public class Promocion {
    private Integer idPromocion;
    private String nombre;
    private String descripcion;
    private Byte[] imagen;
    private String inicioPromocion;
    private String finPromocion;
    private String restriccion;
    private Integer numeroCupones;
    private String codigoPromocion;
    private String status;
    private Integer idTipoPromocion;
    private Integer idCategoria;
    private Integer idEmpresa;

    public Promocion() {
    }

    public Promocion(Integer idPromocion, String nombre, String descripcion, Byte[] imagen, String inicioPromocion, String finPromocion, String restriccion, Integer numeroCupones, String codigoPromocion, String status, Integer idTipoPromocion, Integer idCategoria, Integer idEmpresa) {
        this.idPromocion = idPromocion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.inicioPromocion = inicioPromocion;
        this.finPromocion = finPromocion;
        this.restriccion = restriccion;
        this.numeroCupones = numeroCupones;
        this.codigoPromocion = codigoPromocion;
        this.status = status;
        this.idTipoPromocion = idTipoPromocion;
        this.idCategoria = idCategoria;
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Byte[] getImagen() {
        return imagen;
    }

    public void setImagen(Byte[] imagen) {
        this.imagen = imagen;
    }

    public String getInicioPromocion() {
        return inicioPromocion;
    }

    public void setInicioPromocion(String inicioPromocion) {
        this.inicioPromocion = inicioPromocion;
    }

    public String getFinPromocion() {
        return finPromocion;
    }

    public void setFinPromocion(String finPromocion) {
        this.finPromocion = finPromocion;
    }

    public String getRestriccion() {
        return restriccion;
    }

    public void setRestriccion(String restriccion) {
        this.restriccion = restriccion;
    }

    public Integer getNumeroCupones() {
        return numeroCupones;
    }

    public void setNumeroCupones(Integer numeroCupones) {
        this.numeroCupones = numeroCupones;
    }

    public String getCodigoPromocion() {
        return codigoPromocion;
    }

    public void setCodigoPromocion(String codigoPromocion) {
        this.codigoPromocion = codigoPromocion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdTipoPromocion() {
        return idTipoPromocion;
    }

    public void setIdTipoPromocion(Integer idTipoPromocion) {
        this.idTipoPromocion = idTipoPromocion;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
    
    
}
