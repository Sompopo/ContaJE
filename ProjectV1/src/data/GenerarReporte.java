package data;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GenerarReporte {

    public GenerarReporte(String ruta, Map map) {

        try {

            JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource(ruta));
            JasperPrint jprint = JasperFillManager.fillReport(report, map, Conexion.getConexion());

            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            view.setAlwaysOnTop(true);
            view.setFocusable(true);
            ;
            view.setVisible(true);

        } catch (JRException ex) {
            ex.getMessage();
        }
    }

    public GenerarReporte() {
    }

    public void ImprimirDirecto(String ruta, Map map) {
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        try {
            jasperReport = (JasperReport) JRLoader.loadObject(getClass().getResource(ruta));
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, Conexion.getConexion());
            JasperPrintManager.printReport(jasperPrint, false);
        } catch (JRException ex) {
            System.err.println("Error iReport: " + ex.getMessage());
        }
    }

    public void Imprimir2(String ruta, Map map) {
        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, null);
        if (printService.length > 0) {
            PrintService impresora = (PrintService) JOptionPane.showInputDialog(null, "Eliga impresora:",
                    "Imprimir Reporte", JOptionPane.QUESTION_MESSAGE, null, printService, printService[0]);
            if (impresora != null) {
                try {
                    JasperReport jasperReport;
                    JasperPrint jasperPrint;
                    jasperReport = (JasperReport) JRLoader.loadObject(getClass().getResource(ruta));
                    jasperPrint = JasperFillManager.fillReport(jasperReport, map, Conexion.getConexion());
                    JRPrintServiceExporter jrprintServiceExporter = new JRPrintServiceExporter();
                    jrprintServiceExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, impresora);
                    jrprintServiceExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
                            Boolean.TRUE);
                    jrprintServiceExporter.exportReport();
                } catch (JRException ex) {
                    System.err.println("Error JRException: " + ex.getMessage());
                }
            }
        }
    }

    public void ImprimirImagen(String ruta, Map map) {
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        try {
            jasperReport = (JasperReport) JRLoader.loadObject(getClass().getResource(ruta));
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, Conexion.getConexion());
            BufferedImage bufferedImage = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, 0, 2f);
            ImageIO.write(bufferedImage, "jpg", new File("C:/Descargas/resources/images/reportecanchero.jpg"));
            System.out.println("Reporte creado en formato JPG");
        } catch (IOException ex) {
            System.err.println("Error IOException: " + ex.getMessage());
        } catch (JRException ex) {
            System.err.println("Error iReport: " + ex.getMessage());
        }
    }

    public void verReporte(String ruta, Map map) {
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        try {
            jasperReport = (JasperReport) JRLoader.loadObject(getClass().getResource(ruta));
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, Conexion.getConexion());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException ex) {
            System.err.println("Error iReport: " + ex.getMessage());
        }
    }

    public void guardarDirectoPdf(String ruta, Map map) {
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        try {
            jasperReport = (JasperReport) JRLoader.loadObject(getClass().getResource(ruta));
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, Conexion.getConexion());
            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/Descargas/resources/images/reporte.pdf");
        } catch (JRException ex) {
            System.err.println("Error iReport: " + ex.getMessage());
        }
    }

    public void guardarPdf2(String ruta, Map map) {
        // setting report parameters
        try {
            HashMap parameters = new HashMap();

            FileInputStream inputStream = new FileInputStream(ruta);
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            // Pass compiled report to print
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, Conexion.getConexion());
            JasperViewer jasperViewer = new JasperViewer(jasperPrint);
            //jasperViewer.setVisible(true);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/Descargas/resources/images/reporte.pdf");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}