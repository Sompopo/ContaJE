package data;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import first.temaController;

public class Conexion {
    static String url;
    static String user;
    static String pass;
    static String db;
    static String localizacion;
    static ArrayList<String> datos;
    static String[] info;

    Conexion() {
        //validaTema();
        valida();
    }

    public static Connection getConexion() {
        valida();
        try {
            Connection con = null;
            try {
                String driver = "com.mysql.cj.jdbc.Driver";
                Class.forName(driver);
                con = DriverManager.getConnection(url, user, pass);
                return con;
            } catch (Exception e) {
                Alerta.mostrarAlertError("NO HAY CONEXIÓN CON LA BASE DE DATOS");
            }
        } catch (Exception e) {
            Alerta.mostrarAlertError("ERROR EN CONEXIÓN DE RED A BASE DE DATOS\n" + e.getMessage() +
                    "Conexión a Base de Datos Incorrecta");
        }
        return null;
    }

    public static void valida() {

        File file = new File("src/ficheros/InfoBD.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            datos = TextFile.getFromFile("src/ficheros/InfoBD.txt");
            if(!datos.isEmpty()){
                info = TextFile.sacarData(datos);
            }
            
        } else {
            datos = TextFile.getFromFile("src/ficheros/InfoBD.txt");
            if(!datos.isEmpty()){
                info = TextFile.sacarData(datos);
            }
        }

        if (datos.isEmpty()) {

            db = "";
            localizacion = "";
            url = "jdbc:mysql://" + localizacion + ":3306/" + db;
            user = "";
            pass = "";

            // jdbc:mysql://localhost:3306/bdcontable

        } else if(info.length >= 4){
            db = info[0];
            localizacion = info[1];
            url = "jdbc:mysql://" + localizacion + ":3306/" + db;
            user = info[2];
            pass = info[3];
        } 
    }

    public static void validaTema(){
        File file = new File("src/ficheros/InfoTemas.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
                temaController.logic("1");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

