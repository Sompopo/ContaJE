package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;

import data.Alerta;
import data.Conexion;
import data.TextFile;
import first.loadWindow4;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class infoBDController implements Initializable{

    static ArrayList<String> datos = new ArrayList<String>();

    @FXML
    private TextField text1;

    @FXML
    private TextField text2;

    @FXML
    private TextField text3;

    @FXML
    private PasswordField text4;

    @FXML
    private Button btnCancelar;

    @FXML
    void btnActionEvent(ActionEvent event) {
        validarDatos(event);
    }

    @FXML
    void btnCancelarAction(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }

    public void logic() {
        datos.add("aux");
        datos.set(0, text1.getText() + "/" + text2.getText() + "/" + text3.getText() + "/" + text4.getText());

        File file = new File("src/ficheros/InfoBD.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            TextFile.setToFile(datos, "src/ficheros/InfoBD.txt");
        } else {
            TextFile.setToFile(datos, "src/ficheros/InfoBD.txt");
        }
    }

    public void validarDatos(Event event) {
        if (!text1.getText().equals("") && !text2.getText().equals("") && !text3.getText().equals("")
                && !text4.getText().equals("")) {
            logic();
            Connection con = Conexion.getConexion();
            if (con != null) {
                Alerta.mostrarAlertInfo("SI HAY CONEXIÓN CON LA BASE DE DATOS!!");
                limpiar();
                loadWindow4 lw4 = new loadWindow4();
                lw4.loadStage("/view/viewLogin.fxml", event,897,652,"");
                

            } else {

            }
        } else {
            Alerta.mostrarAlertWarning("RELLENE TODOS LOS CAMPOS!!");
        }
    }

    private void limpiar() {
        text1.clear();
        text2.clear();
        text3.clear();
        text4.clear();
    }

    
    
}
