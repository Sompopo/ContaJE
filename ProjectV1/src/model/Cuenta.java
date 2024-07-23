package model;

public class Cuenta {

    private String codigo;
    private String nombre;
    private String tipo;
    private String order;
    private String descrip;
    private String estado;

    public Cuenta(){}

    public Cuenta(String codigo, String nombre, String tipo, String descrip, String order, String estado){
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.order = order;
        this.descrip = descrip;
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }
    public String getDescrip() {
        return descrip;
    }
    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
