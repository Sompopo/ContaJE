package first;

import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;

import java.awt.*;
import javafx.scene.image.Image;

import data.Sentencias;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class loadWindow {

    public void loadStage(String url, Event event, int width, int height, String user) {

        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            /*
             * Object eventSouerce = event.getSource();
             * Node sourceAsNode = (Node) eventSouerce;
             * Scene oldScene = sourceAsNode.getScene();
             * Window window = oldScene.getWindow();
             * Stage stage = (Stage) window;
             * stage.hide();
             */

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double wid = screenSize.getWidth();
            double hei = screenSize.getHeight()-65;

            if(wid > 1920.0){
                wid = 1540.0;
                hei = 800.0;
            }

            Parent root = FXMLLoader.load(getClass().getResource(url));

            StackPane container = new StackPane();
            ImageView logo = new ImageView();
            logo.setImage(new Image("/Images/logotrp.png"));
            logo.setFitHeight(840);
            logo.setFitWidth(840);
            container.getChildren().add(logo);
            container.getChildren().add(root);

            Scene scene = new Scene(container, wid, hei);
            scene.getStylesheets().add(getClass().getResource(temaController.getTema()).toExternalForm());
            Stage newStage = new Stage();
            newStage.setScene(scene);
            if (Sentencias.verificaEmpresa("Call verificaEmpresa();") != 0) {
                newStage.getIcons().add(Sentencias.retornaEmpresaLogo("Call retornaLogoEmpresa()"));
                newStage.setTitle("Sistema de Facturacion: "
                        + Sentencias.retornaEmpresaNombre("Call retornaNombreEmpresa();") + " - Usuario: " + user);
            }else{
                newStage.setTitle("Sistema de Facturacion - Usuario: " + user);
            }
            // newStage.initStyle( StageStyle.UTILITY );
            // newStage.setMaximized(true);
            newStage.show();
            //newStage.setResizable(false);

            newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent event) {
                    Platform.exit();
                }

            });

        } catch (IOException ex) {
            // Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null,
            // ex);
        }
    }
}
