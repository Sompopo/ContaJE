package first;

import java.io.IOException;

import data.Sentencias;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class loadWindow2 {

    public void loadStage(String url, Event event, int width, int height, String title) {

        try {

            Parent root = FXMLLoader.load(getClass().getResource(url));
            Scene scene = new Scene(root, width, height);
            scene.getStylesheets().add(getClass().getResource(temaController.getTema()).toExternalForm());
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle(title);
            if (Sentencias.verificaEmpresa("Call verificaEmpresa();") != 0) {
                newStage.getIcons().add(Sentencias.retornaEmpresaLogo("Call retornaLogoEmpresa()"));
            }
            newStage.showAndWait();

            newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent event) {
                    newStage.hide();
                }

            });

        } catch (IOException ex) {
            // Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null,ex);
        }
    }
}
