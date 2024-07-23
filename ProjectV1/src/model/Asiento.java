package model;

public class Asiento {
    private String codigo;
    private String fecha;
    private String detalle;

    public Asiento(String code, String fecha, String detalle){
        this.codigo = code;
        this.fecha= fecha;
        this.detalle = detalle;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    
}

