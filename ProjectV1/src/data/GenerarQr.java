package data;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class GenerarQr {
    
    public static BufferedImage qr(String code){
        int size = 1000;
    /*String FileType = "png";
    String filePath = "";
    UUID uuid = UUID.randomUUID();
    String randonName = uuid.toString();*/

    QRCodeWriter qrcode = new QRCodeWriter();
    try {
        BitMatrix matrix = qrcode.encode(code, BarcodeFormat.QR_CODE, size, size);
        //File f = new File(filePath + "/" + randonName + "." + FileType);
        int matrixWidth = matrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D gd = (Graphics2D) image.getGraphics();
        gd.setColor(Color.WHITE); // Fondo
        gd.fillRect(0, 0, size, size);
        gd.setColor(Color.black); // Qr

        for (int b = 0; b < matrixWidth; b++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (matrix.get(b, j)) {
                    gd.fillRect(b, j, 1, 1);
                }
            }
        }
        
        return image;
        
        
    } catch (WriterException ex) {
        //Logger.getLogger(QR.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
    }
       
}
