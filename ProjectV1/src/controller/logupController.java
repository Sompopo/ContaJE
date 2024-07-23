package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import data.Alerta;
import data.Conexion;
import data.Encoder;
import data.Sentencias;
import data.ValidacionCampos;
import first.loadWindow4;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.Usuario;

public class logupController implements Initializable{

    loadWindow4 lw4 = new loadWindow4();

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnGuardar;

    @FXML
    private TextField text1;

    @FXML
    private TextField text2;

    @FXML
    private TextField text3;

    @FXML
    private TextField text4;

    @FXML
    private TextField text5;

    @FXML
    private PasswordField text6;

    @FXML
    private PasswordField text7;

    @FXML
    void btnGuardarActionEvent(ActionEvent event) {
        registroUsuarioRoot(event);
    }

    @FXML
    void bynAtrasActionEvent(ActionEvent event) {
        lw4.loadStage("/view/viewLogin.fxml", event, 897, 652, "");
    }

    public void registroUsuarioRoot(Event event) {
        if (!text1.getText().equals("") && !text2.getText().equals("") && !text3.getText().equals("")
                && !text4.getText().equals("") && !text5.getText().equals("") && !text6.getText().equals("")) {
            if (text6.getText().equals(text7.getText())) {
                if (Sentencias.validaNickUsuario("CALL validaNickUsuario('" + text5.getText() + "')") == 0) {
                    Usuario user = new Usuario();
                    user.setNombre(text1.getText());
                    user.setApellido(text2.getText());
                    user.setCorreo(text3.getText());
                    user.setTelefono(text4.getText());
                    user.setUsuario(text5.getText());
                    user.setContrasena(Encoder.ecnode(text6.getText()));
                    user.setEstado(2);
                    if(Conexion.getConexion() != null){
                        if (Sentencias.crearUsuario(user)) {
                            agregaAccesosUsuarioAdmin();
                            Alerta.mostrarAlertInfo("Usuario creado exitosamente!");
                            limpiar();
                            lw4.loadStage("/view/viewLogin.fxml", event,897,652,"");
                        } else{
                            Alerta.mostrarAlertError("No se pudo crear el usuario!");
                        }
                    } 
                } else {
                    Alerta.mostrarAlertWarning("El usuario ingresado ya existe, pruebe con otro!");
                }
            } else {
                Alerta.mostrarAlertWarning("Las contraseñas no coinciden!");
            }

        } else {
            Alerta.mostrarAlertWarning("Rellene todos los campos!");
        }
    }

    public void agregaAccesosUsuarioAdmin(){
        ArrayList<String> accesos = Sentencias.retornaTodosAccesos("Call retornaTodosAccesos();");
        for(int i = 0; i < accesos.size(); i++){
            Sentencias.crud("Call agregarAccesosUsuarioAdmin('"+accesos.get(i)+"');");
        }
    }

    private void limpiar() {
        text1.clear();
        text2.clear();
        text3.clear();
        text4.clear();
        text5.clear();
        text6.clear();
        text7.clear();
    }

    public void campos(){
        text1.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letras(event, text1, true, 15));
        text2.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letras(event, text2, true, 15));
        text3.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.validaLongitud(event, text3, false, 30));
        text4.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.enteros(event, text4, 15));
        text5.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letrasNumeros(event, text5, false, 15));
        text6.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letrasNumeros(event, text6, false, 15));
        text7.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letrasNumeros(event, text7, false, 15));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        campos();
    }
}
