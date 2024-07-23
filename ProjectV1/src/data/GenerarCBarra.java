package data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

public class GenerarCBarra {

    public static void retornaCodigo(String codigo) {
        System.out.println("Creando su código de barras");
        // Creando el bean de barcode
        Code39Bean codebean = new Code39Bean();
        // Se declara los pixels por pulgada = dpi (Dots Per Inch)
        final int dpi = 1200;

        // Configurando el generador de barcode
        /** hace la distancia entre las barras, del ancho exacto a 1 pixel **/
        double ancho = UnitConv.in2mm(2.0f / dpi);
        /** Establece el ancho del módulo estrecho **/
        codebean.setModuleWidth(ancho);
        /**
         * Define el factor por el cual las barras anchas son más anchas que las barras
         * estrechas
         **/
        codebean.setWideFactor(10);
        /** Establece si una zona tranquila debe ser incluida o no **/
        codebean.doQuietZone(true);

        // Abriendo el archivo de salida
        String directorio = "C:/Descargas/resources/images/";
        File carpeta = new File(directorio);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        String nombreArchivo = "Codigo_"+codigo+".png";
        File miFile = new File(directorio + nombreArchivo);
        FileOutputStream out = null;

        BitmapCanvasProvider canvas;
        try {
            out = new FileOutputStream(miFile);
            // Configurando el 'canvas provider' para un PNG monocromatico
            // BufferedImage imageBuff = new BufferedImage(100, 100,
            // BufferedImage.TYPE_INT_RGB);

            canvas = new BitmapCanvasProvider(out, // el outputstream para escribir
                    "image/x-png", // el tipo MIME del formato de salida deseado
                    dpi, // la resolución de imagen deseada
                    BufferedImage.TYPE_BYTE_BINARY, // el tipo de imagen deseado
                    false, // true si se debe habilitar el anti-aliasing
                    0); // Orientation debe ser 0, 90, 180, 270, -90, -180 or -270

            // Generando el codigo de barras
            codebean.generateBarcode(canvas, codigo);

            // Cerrando el fin de la generacion
            canvas.finish();
        } catch (IOException ioe) {
            System.err.println("Ha ocurrido un error: " + ioe.getMessage());
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                System.err.println("No se ha podido cerrar el outputstream " + e.getMessage());
            }
        }
        System.out.println("Imagen creada exitosamente en " + miFile.getAbsoluteFile());
        System.out.println("fin del programa =)");
        System.exit(0);
    }

}
