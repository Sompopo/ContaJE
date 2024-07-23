package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import data.Alerta;
import data.Encoder;
import data.Sentencias;
import data.TextFile;
import data.ValidacionCampos;
import first.loadWindow;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class loginController implements Initializable {

    ArrayList<String> datos = new ArrayList<String>();
    //Sentencias sentencias = new Sentencias();

    loadWindow lw = new loadWindow();

    @FXML
    private Button btnEntrar;

    @FXML
    private PasswordField txtPasswd;

    @FXML
    private TextField txtPasswd2;

    @FXML
    private TextField txtUser;

    @FXML
    private Label laCuenta;

    @FXML
    private Label laPasswd;

    @FXML
    private Label laText;

    @FXML
    private CheckBox chVer;

    @FXML
    private ImageView imaLogo;

    @FXML
    void chRecordarMouseEntered(MouseEvent event) {
        chVer.setTextFill(Color.web("#326496"));
    }

    @FXML
    void chRecordarMouseExited(MouseEvent event) {
        chVer.setTextFill(Color.web("#FFFFFF"));
    }

    @FXML
    void laCuentaMouseClicked(MouseEvent event) {
        Object evt = event.getSource();

        if (evt.equals(laCuenta)) {
            lw.loadStage("/view/viewLogup.fxml", event, 1280, 720, "");
        }
    }

    @FXML
    void laCuentaMouseEntered(MouseEvent event) {
        laCuenta.setTextFill(Color.web("#326496"));
    }

    @FXML
    void laCuentaMouseExited(MouseEvent event) {
        laCuenta.setTextFill(Color.web("#FFFFFF"));
    }

    @FXML
    void laPasswdMouseClicked(MouseEvent event) {
        Alerta.mostrarAlertInfo(
                "INFORMACIÓN IMPORTANTE\n\nSi no recuerda la contraseña i/o usuario, deberá ponerse en contacto con el usuario administrador del sistema para que este pueda reestablecer sus datos de acceso.\n\n¡Si usted es el administrador del sistema, deberá contactar con la persona que le brinda soporte técnico!\nContacto: guevara.jerson@yahoo.es");
    }

    @FXML
    void laPasswdMouseEntered(MouseEvent event) {
        laPasswd.setTextFill(Color.web("#326496"));
    }

    @FXML
    void laPasswdMouseExited(MouseEvent event) {
        laPasswd.setTextFill(Color.web("#FFFFFF"));
    }

    @FXML
    void eventAction(ActionEvent event) {

        Object evt = event.getSource();

        if (evt.equals(btnEntrar)) {
            validaIngreso(txtUser.getText(), txtPasswd.getText(), event);
        }
    }

    private void validaIngreso(String user, String passwd, Event event) {
        if (!user.isEmpty() && !passwd.isEmpty()) {
            int codigo = Sentencias
                    .validaUsuario("CALL validaUsuario('" + user + "','" + Encoder.ecnode(passwd) + "')");
            if (codigo != 0) {
                logic();
                Sentencias.crud("Call crearRegistroBitacora('Inicio de Sesión','NINGUNO','"
                        + Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + user + "');") + "')");
                lw.loadStage("/view/viewInicio.fxml", event, 1280, 720, user);
            } else {
                Alerta.mostrarAlertError("Usuario o contraseña incorrectos!");
            }
        } else {
            Alerta.mostrarAlertError("Ingrese el usuario y contraseña!");
        }
    }

    public void logic() {
        datos.add("aux");
        datos.set(0, txtUser.getText());

        File file = new File("src/ficheros/InfoUser.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            TextFile.setToFile(datos, "src/ficheros/InfoUser.txt");
        } else {
            TextFile.setToFile(datos, "src/ficheros/InfoUser.txt");
        }
    }

    public void campos() {
        txtUser.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letrasNumeros(event, txtUser, false, 15));
        txtPasswd.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.letrasNumeros(event, txtPasswd, false, 15));
    }

    public void maskPassword(PasswordField pass, TextField text, CheckBox check) {

        text.setVisible(false);
        text.setManaged(false);

        text.managedProperty().bind(check.selectedProperty());
        text.visibleProperty().bind(check.selectedProperty());

        text.textProperty().bindBidirectional(pass.textProperty());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Sentencias.verificaEmpresa("Call verificaEmpresa();") != 0) {
            imaLogo.setImage(Sentencias.retornaEmpresaLogo("Call retornaLogoEmpresa()"));
        } else {
            imaLogo.setImage(new Image("/Images/LogoAux.png"));
        }
        if (Sentencias.totalUsuarios("CALL totalUsuarios()") != 0) {
            laText.setVisible(false);
            laCuenta.setVisible(false);
        }
        txtUser.setText("che");
        txtPasswd.setText("admin1234");

        maskPassword(txtPasswd, txtPasswd2, chVer);

        campos();
    }

}
