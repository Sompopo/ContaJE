package first;

import java.io.IOException;

import data.Sentencias;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class loadWindow4 {

    public void loadStage(String url, Event event, int width, int height, String user) {

        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();

            Parent root = FXMLLoader.load(getClass().getResource(url));
            Scene scene = new Scene(root, width, height);
            scene.getStylesheets().add(getClass().getResource(temaController.getTema()).toExternalForm());
            Stage newStage = new Stage();
            newStage.setScene(scene);
            if (Sentencias.verificaEmpresa("Call verificaEmpresa();") != 0) {
                newStage.getIcons().add(Sentencias.retornaEmpresaLogo("Call retornaLogoEmpresa()"));
                newStage.setTitle("Sistema de Facturacion: "
                        + Sentencias.retornaEmpresaNombre("Call retornaNombreEmpresa();") + " - Usuario: " + user);
            }
            newStage.show();
            newStage.setResizable(false);

            newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent event) {
                    Platform.exit();
                }

            });

        } catch (IOException ex) {

        }
    }
}
