package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import first.temaController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;

public class Alerta {

    public static void mostrarAlertError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().getStylesheets().add(temaController.getTema());
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void mostrarAlertInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().getStylesheets().add(temaController.getTema());
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void mostrarAlertWarning(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.getDialogPane().getStylesheets().add(temaController.getTema());
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static int mostrarAlertConfirmation(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().getStylesheets().add(temaController.getTema());
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);
        alert.setTitle("Confirmacion");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            return 1;
        } else {
            return 0;
        }

    }

    public static ButtonType alertaPerzonalizada(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().getStylesheets().add(temaController.getTema());
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);
        alert.setTitle("Confirmacion");
        alert.setContentText(mensaje);
        alert.showAndWait();
        return alert.getResult();
    }

    public static void mostrarAlertCabecera(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().getStylesheets().add(temaController.getTema());
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText("Cabecera");
        alert.setTitle("Info");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static String retornaPago(String valor) {
        TextInputDialog dialogoTexto = new TextInputDialog("0.00");
        dialogoTexto.getDialogPane().getStylesheets().add(temaController.getTema());
        dialogoTexto.getDialogPane().getStyleClass().add("dialogo");
        dialogoTexto.setTitle("PAGO");
        dialogoTexto.setHeaderText("VALOR A PAGAR: " + valor);
        dialogoTexto.setContentText("INGRESE EL VALOR:");
        dialogoTexto.getEditor().addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.decimales(event, dialogoTexto.getEditor(), 15));
        Optional<String> texto = dialogoTexto.showAndWait();

        if (texto.isPresent()) {
            return texto.get();
        }
        return "0.00";
    }

    public static String retornaCantidadUnidad(String valor) {
        TextInputDialog dialogoTexto = new TextInputDialog("1");
        dialogoTexto.getDialogPane().getStylesheets().add(temaController.getTema());
        dialogoTexto.getDialogPane().getStyleClass().add("dialogo");
        dialogoTexto.setTitle("CANTIDAD EN UNIDADES");
        dialogoTexto.setHeaderText("PRODUCTO: " + valor);
        dialogoTexto.setContentText("INGRESE LA CANTIDAD:");
        dialogoTexto.getEditor().addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.enteros(event, dialogoTexto.getEditor(), 15));
        Optional<String> texto = dialogoTexto.showAndWait();

        if (texto.isPresent()) {
            return texto.get();
        }

        return "0";
    }

    public static String retornaCantidadLibra(String valor) {
        TextInputDialog dialogoTexto = new TextInputDialog("1");
        dialogoTexto.getDialogPane().getStylesheets().add(temaController.getTema());
        dialogoTexto.getDialogPane().getStyleClass().add("dialogo");
        dialogoTexto.setTitle("CANTIDAD EN LIBRAS");
        dialogoTexto.setHeaderText("PRODUCTO: " + valor);
        dialogoTexto.setContentText("INGRESE LA CANTIDAD:");
        dialogoTexto.getEditor().addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.decimales(event, dialogoTexto.getEditor(), 15));
        Optional<String> texto = dialogoTexto.showAndWait();

        if (texto.isPresent()) {
            return texto.get();
        }

        return "0";
    }

    public static String formaPago() {
        List<String> opciones = new ArrayList<String>();
        opciones.add("EFECTIVO");
        opciones.add("TARJETA");
        opciones.add("CHEQUÉ");
        opciones.add("DEPÓSITO");

        ChoiceDialog<String> dialogoEleccion = new ChoiceDialog<String>("EFECTIVO", opciones);
        dialogoEleccion.getDialogPane().getStylesheets().add(temaController.getTema());
        dialogoEleccion.getDialogPane().getStyleClass().add("dialogo");
        dialogoEleccion.setTitle("FORMA DE PAGO");
        dialogoEleccion.setHeaderText(null);
        dialogoEleccion.setContentText("ELIJA LA FORMA DE PAGO:");
        Optional<String> texto = dialogoEleccion.showAndWait();
        if (texto.isPresent()) {
            return texto.get();
        }
        dialogoEleccion.close();
        return "EFECTIVO";
    }

    public static String tipoPago() {
        List<String> opciones = new ArrayList<String>();
        opciones.add("CONTADO");
        opciones.add("CRÉDITO");

        ChoiceDialog<String> dialogoEleccion = new ChoiceDialog<String>("CONTADO", opciones);
        dialogoEleccion.getDialogPane().getStylesheets().add(temaController.getTema());
        dialogoEleccion.getDialogPane().getStyleClass().add("dialogo");
        dialogoEleccion.setTitle("TIPO DE PAGO");
        dialogoEleccion.setHeaderText(null);
        dialogoEleccion.setContentText("ELIJA EL TIPO DE PAGO:");
        Optional<String> texto = dialogoEleccion.showAndWait();
        if (texto.isPresent()) {
            return texto.get();
        }
        dialogoEleccion.close();
        return "CONTADO";
    }

    public static String tipoNota() {
        List<String> opciones = new ArrayList<String>();
        opciones.add("NOTA DE CRÉDITO");
        opciones.add("NOTA DE DEBITO");

        ChoiceDialog<String> dialogoEleccion = new ChoiceDialog<String>("NOTA DE CRÉDITO", opciones);
        dialogoEleccion.getDialogPane().getStylesheets().add(temaController.getTema());
        dialogoEleccion.getDialogPane().getStyleClass().add("dialogo");
        dialogoEleccion.setTitle("TIPO DE COMPROBANTE");
        dialogoEleccion.setHeaderText(null);
        dialogoEleccion.setContentText("ELIJA EL TIPO DE COMPROBANTE:");
        Optional<String> texto = dialogoEleccion.showAndWait();
        if (texto.isPresent()) {
            return texto.get();
        }
        dialogoEleccion.close();
        return "";
    }

    public static String tipoMovimiento() {
        List<String> opciones = new ArrayList<String>();
        opciones.add("INGRESAR PRODUCTO");
        opciones.add("SACAR PRODUCTO");

        ChoiceDialog<String> dialogoEleccion = new ChoiceDialog<String>("INGRESAR PRODUCTO", opciones);
        dialogoEleccion.getDialogPane().getStylesheets().add(temaController.getTema());
        dialogoEleccion.getDialogPane().getStyleClass().add("dialogo");
        dialogoEleccion.setTitle("TIPO DE MOVIMIENTO");
        dialogoEleccion.setHeaderText(null);
        dialogoEleccion.setContentText("ELIJA EL TIPO DE MOVIMIENTO:");
        Optional<String> texto = dialogoEleccion.showAndWait();
        if (texto.isPresent()) {
            return texto.get();
        }
        dialogoEleccion.close();
        return "";
    }

    public static String retornaFacturaNum() {
        TextInputDialog dialogoTexto = new TextInputDialog("0000020100000001");
        dialogoTexto.getDialogPane().getStylesheets().add(temaController.getTema());
        dialogoTexto.getDialogPane().getStyleClass().add("dialogo");
        dialogoTexto.setTitle("FACTURA");
        dialogoTexto.setHeaderText("INGRESE EL NUMERO DE LA FACTURA AFECTADA!");
        dialogoTexto.setContentText("FACTURA #:");
        dialogoTexto.getEditor().addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.enteros(event, dialogoTexto.getEditor(), 16));
        Optional<String> texto = dialogoTexto.showAndWait();

        if (texto.isPresent()) {
            return texto.get();
        }
        
        return "SALIR";
    }

    public static String retornaValorCaja() {
        TextInputDialog dialogoTexto = new TextInputDialog("0");
        dialogoTexto.getDialogPane().getStylesheets().add(temaController.getTema());
        dialogoTexto.getDialogPane().getStyleClass().add("dialogo");
        dialogoTexto.setTitle("CANTIDAD");
        dialogoTexto.setHeaderText("VALOR DE INICIO DE CAJA");
        dialogoTexto.setContentText("INGRESE LA CANTIDAD:");
        dialogoTexto.getEditor().addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.decimales(event, dialogoTexto.getEditor(), 15));
        Optional<String> texto = dialogoTexto.showAndWait();

        if (texto.isPresent()) {
            return texto.get();
        }

        return "SALIR";
    }
}
