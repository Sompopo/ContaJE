package model;

public class Bitacora {

    private int codigo;
    private String accion;
    private String modulo;
    private String fecha;
    private String nombre;
    private String user;
    
    public Bitacora(int codigo, String accion, String modulo, String fecha, String nombre, String user){
        this.codigo = codigo;
        this.accion = accion;
        this.modulo = modulo;
        this.fecha =  fecha;
        this.nombre = nombre;
        this.user = user;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
