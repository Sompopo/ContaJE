package first;

import java.io.IOException;

import java.awt.*;
import data.Sentencias;
import javafx.scene.image.Image;

//import java.util.logging.Level;
//import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class loadWindow3 {

    public void loadStage(String url, Event event, int width, int height, Stage estage){
        
        try {

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

            Scene scene = new Scene(container,wid,hei);
            scene.getStylesheets().add(getClass().getResource(temaController.getTema()).toExternalForm());
            if (Sentencias.verificaEmpresa("Call verificaEmpresa();") != 0) {
                estage.getIcons().add(Sentencias.retornaEmpresaLogo("Call retornaLogoEmpresa()"));
                }
                
            estage.setScene(scene);
            //estage.setResizable(false);
            
        } catch (IOException ex) {
            //Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

