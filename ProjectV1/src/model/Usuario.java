package model;

public class Usuario {

    private int user_codigo;
    private String user_nombre;
    private String user_apellido;
    private String user_correo;
    private String user_telefono;
    private String user_usuario;
    private String user_contrasena;
    private int user_estado;

    public Usuario() {
    }

    
    public int getId() {
        return user_codigo;
    }

    public void setId(int user_codigo) {
        this.user_codigo = user_codigo;
    }

    public String getNombre() {
        return user_nombre;
    }

    public void setNombre(String nombre) {
        this.user_nombre = nombre;
    }

    public String getApellido() {
        return user_apellido;
    }

    public void setApellido(String apellido) {
        this.user_apellido = apellido;
    }

    public String getCorreo() {
        return user_correo;
    }

    public void setCorreo(String correo) {
        this.user_correo = correo;
    }

    public String getTelefono() {
        return user_telefono;
    }

    public void setTelefono(String telefono) {
        this.user_telefono = telefono;
    }

    public String getUsuario() {
        return user_usuario;
    }

    public void setUsuario(String usuario) {
        this.user_usuario = usuario;
    }

    public String getContrasena() {
        return user_contrasena;
    }

    public void setContrasena(String contrasena) {
        this.user_contrasena = contrasena;
    }

    public int getEstado() {
        return user_estado;
    }

    public void setEstado(int estado) {
        this.user_estado = estado;
    }
    
    

}
