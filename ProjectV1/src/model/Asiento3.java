package model;

public class Asiento3 {
    private String fecha;
    private String detalle;
    private String rm;
    private String debe;
    private String haber;

    public Asiento3(String fecha, String detalle, String rm, String debe, String haber) {
        this.fecha = fecha;
        this.detalle = detalle;
        this.rm = rm;
        this.debe = debe;
        this.haber = haber;
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

    public String getRm() {
        return rm;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    public String getDebe() {
        return debe;
    }

    public void setDebe(String debe) {
        this.debe = debe;
    }

    public String getHaber() {
        return haber;
    }

    public void setHaber(String haber) {
        this.haber = haber;
    }
}
