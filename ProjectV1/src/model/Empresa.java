package model;

import java.sql.Blob;
import java.time.LocalDate;

public class Empresa {

    private int codigo;
    private String nombre;
    private String rtn;
    private String cai;
    private String direccion;
    private String correo;
    private String telefono1;
    private String telefono2;
    private Blob logo;
    private String logoname;
    private String desde;
    private String hasta;
    private String desdeAux;
    private LocalDate fechaLimite;

    public Empresa(int codigo, String nombre, String rtn, String cai, String direccion, String telefono1, String telefono2, String correo, Blob logo, String logoname, String desde, String hasta, String desdeAux, LocalDate fechaLimite) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.rtn = rtn;
        this.cai = cai;
        this.direccion = direccion;
        this.correo = correo;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.logo = logo;
        this.logoname = logoname;
        this.desde = desde;
        this.hasta = hasta;
        this.desdeAux = desdeAux;
        this.fechaLimite = fechaLimite;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRtn() {
        return rtn;
    }

    public void setRtn(String rtn) {
        this.rtn = rtn;
    }

    public String getCai() {
        return cai;
    }

    public void setCai(String cai) {
        this.cai = cai;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public Blob getLogo() {
        return logo;
    }

    public void setLogo(Blob logo) {
        this.logo = logo;
    }

    public String getLogoname() {
        return logoname;
    }

    public void setLogoname(String logoname) {
        this.logoname = logoname;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    } 
    
    public String getDesdeAux() {
        return desdeAux;
    }

    public void setDesdeAux(String desdeAux) {
        this.desdeAux = desdeAux;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
}
