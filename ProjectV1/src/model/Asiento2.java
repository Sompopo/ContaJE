package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Asiento2{

    private final StringProperty codigo = new SimpleStringProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty debe = new SimpleStringProperty();
    private final StringProperty haber = new SimpleStringProperty();

     public Asiento2(String code, String name, String debe, String haber){
        this.codigo.set(code);
        this.nombre.set(name);
        this.debe.set(debe);
        this.haber.set(haber);
     }

     public Asiento2(){
        
     }

     public final StringProperty codigoProperty() {
        return this.codigo;
    }

    public final java.lang.String getCodigo() {
        return this.codigoProperty().get();
    }

    public final void setCodigo(final java.lang.String codigo) {
        this.codigoProperty().set(codigo);
    }

    public final StringProperty nombreProperty() {
        return this.nombre;
    }

    public final java.lang.String getNombre() {
        return this.nombreProperty().get();
    }

    public final void setNombre(final java.lang.String nombre) {
        this.nombreProperty().set(nombre);
    }

    public final StringProperty debeProperty() {
        return this.debe;
    }

    public final java.lang.String getDebe() {
        return this.debeProperty().get();
    }

    public final void setDebe(final java.lang.String debe) {
        this.debeProperty().set(debe);
    }

    public final StringProperty haberProperty() {
        return this.haber;
    }

    public final java.lang.String getHaber() {
        return this.haberProperty().get();
    }

    public final void setHaber(final java.lang.String haber) {
        this.haberProperty().set(haber);
    }
    
}
