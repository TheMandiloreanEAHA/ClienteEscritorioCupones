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
    private String estatus;
    private Integer idTipoPromocion;
    private String tipo;
    private Integer idCategoria;
    private Integer idEmpresa;
    private String empresa;
    private String imagenBase64;

    public Promocion() {
    }

    public Promocion(Integer idPromocion, String nombre, String descripcion, Byte[] imagen, String inicioPromocion, String finPromocion, String restriccion, Integer numeroCupones, String codigoPromocion, String estatus, Integer idTipoPromocion, String tipo, Integer idCategoria, Integer idEmpresa, String empresa, String imagenBase64) {
        this.idPromocion = idPromocion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.inicioPromocion = inicioPromocion;
        this.finPromocion = finPromocion;
        this.restriccion = restriccion;
        this.numeroCupones = numeroCupones;
        this.codigoPromocion = codigoPromocion;
        this.estatus = estatus;
        this.idTipoPromocion = idTipoPromocion;
        this.tipo = tipo;
        this.idCategoria = idCategoria;
        this.idEmpresa = idEmpresa;
        this.empresa = empresa;
        this.imagenBase64 = imagenBase64;
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

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Integer getIdTipoPromocion() {
        return idTipoPromocion;
    }

    public void setIdTipoPromocion(Integer idTipoPromocion) {
        this.idTipoPromocion = idTipoPromocion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getImagenBase64() {
        return imagenBase64;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

   
}