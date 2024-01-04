package clienteescritoriocupones.modelo.pojo;

public class CanjeoCupon {
    private String correo;
    private String codigoPromocion;

    
    public CanjeoCupon() {
    }

    public CanjeoCupon(String correo, String codigoPromocion) {
        this.correo = correo;
        this.codigoPromocion = codigoPromocion;  
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCodigoPromocion() {
        return codigoPromocion;
    }

    public void setCodigoPromocion(String codigoPromocion) {
        this.codigoPromocion = codigoPromocion;
    }
    
}
