package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.PdfWriter;

import data.GenerarCBarra2;

public class GenerarCBarra2 {

    public static void retornaBarra(String codigo) {

        try {

            String directorio = "C:/ContaJESistem/resources/CodeBarra/";
            File carpeta = new File(directorio);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            String nombreArchivo = "Codigo_"+codigo+".pdf";
            File miFile = new File(directorio + nombreArchivo);
            FileOutputStream out = null;
            out = new FileOutputStream(miFile);

            Document doc = new Document();
            PdfWriter pdf = PdfWriter.getInstance(doc, out);
            doc.open();

            Barcode39 code39 = new Barcode39();
            code39.setCode(codigo);
            Image img = code39.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
            img.scalePercent(100);
            doc.add(img);

            doc.add(new Paragraph(" "));

            Barcode128 code128 = new Barcode128();
            code128.setCode(codigo);
            Image img128 = code128.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
            //doc.add(img128);

            doc.close();

        } catch (FileNotFoundException e) {
            // Logger.getLogger(Barras.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException e) {

        }
    }
}
