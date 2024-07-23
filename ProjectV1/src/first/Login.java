package first;

import java.io.IOException;
import java.sql.Connection;

import data.Conexion;
import data.Sentencias;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login {
    public Login() throws IOException {
        Connection con = Conexion.getConexion();
        Stage primaryStage = new Stage();
        //Sentencias sentencias = new Sentencias();

        Parent root;

        if (con != null) {
            root = FXMLLoader.load(getClass().getResource("/view/viewLogin.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(temaController.getTema()).toExternalForm());

            primaryStage.setScene(scene);
            if (Sentencias.verificaEmpresa("Call verificaEmpresa();") != 0) {
                primaryStage.getIcons().add(Sentencias.retornaEmpresaLogo("Call retornaLogoEmpresa()"));
            }
            primaryStage.setTitle("Sistema de Facturacion");
            primaryStage.show();
        } else {
            root = FXMLLoader.load(getClass().getResource("/view/viewInfoBD.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/estilos/Style.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle("Sistema de Facturacion");
            primaryStage.show();
        }
        primaryStage.setResizable(false);
    }
}
