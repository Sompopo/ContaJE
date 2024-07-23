package first;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.Toolkit;
import java.awt.Dimension;

public class Imagen {

    public static ImageView imagen(String ruta)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double wid = screenSize.getWidth();
        double hei = screenSize.getHeight();
        ImageView imageLogo;
        Image image = new Image(ruta);
        imageLogo = new ImageView(image);

        if(wid > 3000){
            imageLogo.setFitHeight(wid/18);
            imageLogo.setFitWidth(hei/9);
            imageLogo.setPreserveRatio(true);
            return imageLogo;
        }
        if(wid > 2000 && wid < 3000){
            imageLogo.setFitHeight(wid/10);
            imageLogo.setFitWidth(hei/5);
            imageLogo.setPreserveRatio(true);
            return imageLogo;
        }
        if(wid > 1200 && wid < 2000){
            imageLogo.setFitHeight(wid/8);
            imageLogo.setFitWidth(hei/4);
            imageLogo.setPreserveRatio(true);
            return imageLogo;
        }
        if(wid < 1200){
            imageLogo.setFitHeight(wid/8);
            imageLogo.setFitWidth(hei/4);
            imageLogo.setPreserveRatio(true);
            return imageLogo;
        }
        
        return imageLogo;
    }

}

