package clienteescritoriocupones.modelo.pojo;

public class PromocionSucursal {
    private Integer idPromocion;
    private String codigoPromocion;
    private Integer idSucursal;

    public PromocionSucursal() {
    }

    public PromocionSucursal(Integer idPromocion, String codigoPromocion,Integer idSucursal) {
        this.idPromocion = idPromocion;
        this.codigoPromocion = codigoPromocion;
        this.idSucursal = idSucursal;
    }

    public String getCodigoPromocion() {
        return codigoPromocion;
    }

    public void setCodigoPromocion(String codigoPromocion) {
        this.codigoPromocion = codigoPromocion;
    }
    
    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }
    
}