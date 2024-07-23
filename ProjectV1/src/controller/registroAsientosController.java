package controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.Function;

import data.Alerta;
import data.Sentencias;
import first.loadWindow3;
import first.loadWindow2;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Asiento2;

public class registroAsientosController implements Initializable {

    private loadWindow2 lw2 = new loadWindow2();
    private loadWindow3 lw = new loadWindow3();
    private static ObservableList<Asiento2> listOb;
    private static ObservableList<Asiento2> listObFinal;
    private NumberFormat df = new DecimalFormat("#0.00"); 
    NumberFormat df2 = new DecimalFormat("#,##0.00");

    @FXML
    private TextField detalle;

    @FXML
    private TableView<Asiento2> tblAsientos;

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnBuscarCuenta;

    @FXML
    private Button btnGrabar;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnBorrar;

    @FXML
    private Label laDebe;

    @FXML
    private Label laHaber;

    @FXML
    private Label laNumAsiento;

    @FXML
    private Label laFecha;

    public void datosTabla() {

        tblAsientos.getSelectionModel().setCellSelectionEnabled(true);
        tblAsientos.setEditable(true);

        tblAsientos.getColumns().add(createColumn("Código", Asiento2::codigoProperty));
        tblAsientos.getColumns().add(createColumn("Nombre de la Cuenta", Asiento2::nombreProperty));
        tblAsientos.getColumns().add(createColumn("Debe", Asiento2::debeProperty));
        tblAsientos.getColumns().add(createColumn("Haber", Asiento2::haberProperty));

    }

    @FXML
    void btnActualizarActionEvent(ActionEvent event) {

        Object evt = event.getSource();

        if (evt.equals(btnActualizar)) {
            if (tblAsientos.getColumns().size() < 1) {
                datosTabla();
            }
            if (tblAsientos.getItems().size() == 0) {
                if (listOb != null) {
                    tblAsientos.setItems(listOb);
                    listObFinal = listOb;
                }
            } else {
                if (listOb != null) {
                    for (int i = 0; i < listOb.size(); i++) {
                        if (validaTablaAsientos(listOb.get(i).getCodigo()) != 1) {
                            tblAsientos.getItems().add(listOb.get(i));
                        } else {
                            // Alerta.mostrarAlertWarning("La cuenta ya fue agregada");
                        }
                    }
                }
            }
        }
    }

    @FXML
    void btnBorrarActionEvent(ActionEvent event) {
        Object evt = event.getSource();

        if (evt.equals(btnBorrar)) {
            if (tblAsientos.getSelectionModel().getSelectedIndex() != -1) {
                tblAsientos.getItems().remove(tblAsientos.getSelectionModel().getSelectedIndex());
                laDebe.setText("0.00");
                laHaber.setText("0.00");
            }
        }
    }

    @FXML
    void btnGrabarActionEvent(ActionEvent event) {

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        String[] usuario = stage.getTitle().split("\\s+");

        double debe = 0.00;
        double haber = 0.00;

        int act = 1;
        int act2 = 1;

        for (int i = 0; i < tblAsientos.getItems().size(); i++) {

            if (tblAsientos.getColumns().get(2).getCellData(i).toString().isEmpty()) {
                listOb.get(i).setDebe("0.00");
            }

            if (!tblAsientos.getColumns().get(2).getCellData(i).toString().isEmpty()) {
                for (int x = 0; x < tblAsientos.getColumns().get(2).getCellData(i).toString().length(); x++) {
                    if ((tblAsientos.getColumns().get(2).getCellData(i).toString().charAt(x) < '0'
                            || tblAsientos.getColumns().get(2).getCellData(i).toString().charAt(x) > '9')
                            && (tblAsientos.getColumns().get(2).getCellData(i).toString().charAt(x) < '.'
                                    || tblAsientos.getColumns().get(2).getCellData(i).toString().charAt(x) > '.')) {
                        act = 0;
                        listOb.get(i).setDebe("0.00");
                    }
                }
                if (act == 1) {
                    String pala = tblAsientos.getColumns().get(2).getCellData(i).toString();
                    if (pala.equals(".")) {
                        listOb.get(i).setDebe("0.00");
                    }
                    if (contarCaracteres(pala, '.') < 2) {
                        debe += Double.parseDouble((String) tblAsientos.getColumns().get(2).getCellData(i));
                    }
                    if (contarCaracteres(pala, '.') > 1) {
                        listOb.get(i).setDebe("0.00");
                    }
                }
            }
        }

        for (int i = 0; i < tblAsientos.getItems().size(); i++) {

            if (tblAsientos.getColumns().get(3).getCellData(i).toString().isEmpty()) {
                listOb.get(i).setHaber("0.00");
            }

            if (!tblAsientos.getColumns().get(3).getCellData(i).toString().isEmpty()) {
                for (int x = 0; x < tblAsientos.getColumns().get(3).getCellData(i).toString().length(); x++) {
                    if ((tblAsientos.getColumns().get(3).getCellData(i).toString().charAt(x) < '0'
                            || tblAsientos.getColumns().get(3).getCellData(i).toString().charAt(x) > '9')
                            && (tblAsientos.getColumns().get(3).getCellData(i).toString().charAt(x) < '.'
                                    || tblAsientos.getColumns().get(3).getCellData(i).toString().charAt(x) > '.')) {
                        act2 = 0;
                        listOb.get(i).setHaber("0.00");
                    }
                }
                if (act2 == 1) {
                    String pala = tblAsientos.getColumns().get(3).getCellData(i).toString();
                    if (pala.equals(".")) {
                        listOb.get(i).setHaber("0.00");
                    }
                    if (contarCaracteres(pala, '.') < 2) {
                        haber += Double.parseDouble((String) tblAsientos.getColumns().get(3).getCellData(i));
                    }
                    if (contarCaracteres(pala, '.') > 1) {
                        listOb.get(i).setHaber("0.00");
                    }
                }
            }
        }

        laDebe.setText(df2.format(debe) + "");
        laHaber.setText(df2.format(haber) + "");

        if (tblAsientos.getItems().size() > 1) {
            if (!detalle.getText().isEmpty()) {
                if (debe == haber) {
                    if (debe > 0 && haber > 0) {
                        if (Alerta.mostrarAlertConfirmation("Seguro que desea grabar el asiento?") == 1) {
                            Sentencias.crud("call crearAsiento('"
                                    + Sentencias.retornaUserCodigo(
                                            "Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');")
                                    + "',now(),'" + detalle.getText() + "','" + debe
                                    + "','" + haber + "');");
                            for (int i = 0; i < tblAsientos.getItems().size(); i++) {
                                Sentencias.crud("call crearAsientoDetalle('"
                                        + tblAsientos.getColumns().get(0).getCellData(i).toString() + "','"
                                        + Integer.parseInt(laNumAsiento.getText()) + "','"
                                        + tblAsientos.getColumns().get(2).getCellData(i) + "','"
                                        + tblAsientos.getColumns().get(3).getCellData(i) + "');");
                            }
                            Alerta.mostrarAlertWarning("Se agrego el Asiento!!");
                            laNumAsiento.setText((Sentencias.numeroAsiento("call numeroAsiento()") + 1) + "");
                            Sentencias.crud(
                                    "Call crearRegistroBitacora('Se grabo un nuevo asiento contable','CONTABILIDAD','"
                                            + Sentencias.retornaUserCodigo(
                                                    "Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');")
                                            + "')");
                            clear();
                        }
                    } else {
                        Alerta.mostrarAlertWarning("Los totales no pueden ser 0!!");
                    }
                } else {
                    Alerta.mostrarAlertWarning("El balance no cuadra!!!");
                }
            } else {
                Alerta.mostrarAlertWarning("Agregue el detalle del Asiento!!");
            }
        } else {
            Alerta.mostrarAlertWarning("Agregue las cuentas al asiento!!!");
        }
    }

    public static void setRecibe(ObservableList<Asiento2> received) {
        listOb = received;
    }

    public int validaTablaAsientos(String val) {
        int act = 0;
        for (int e = 0; e < tblAsientos.getItems().size(); e++) {
            if (val.equals(tblAsientos.getColumns().get(0).getCellData(e))) {
                act = 1;
                return act;
            }
        }
        return act;
    }

    @FXML
    void btnBuscarCuentaActionEvent(ActionEvent event) {
        Object evt = event.getSource();

        if (evt.equals(btnBuscarCuenta)) {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            lw2.loadStage("/view/viewAgregarCuenta.fxml", event, 600, 400, stage.getTitle());
        }
    }

    public <T> void detecta() {

        tblAsientos.setStyle("-fx-font: 18px \"System\";");

        tblAsientos.setOnKeyPressed(event -> {
            TablePosition<Asiento2, ?> pos = tblAsientos.getFocusModel().getFocusedCell();
            if (pos != null && event.getCode().isLetterKey()) {
                tblAsientos.edit(pos.getRow(), pos.getTableColumn());
            }
        });

        btnAtras.setOnAction(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            lw.loadStage("/view/viewContabilidad.fxml", event, 1280, 720, stage);
        });
    }

    public static int contarCaracteres(String cadena, char caracter) {
        int posicion, contador = 0;
        posicion = cadena.indexOf(caracter);
        while (posicion != -1) {
            contador++;
            posicion = cadena.indexOf(caracter, posicion + 1);
        }
        return contador;
    }

    public <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));

        col.setCellFactory(column -> EditCell.createStringEditCell());
        if (title.equals("Código") || title.equals("Nombre de la Cuenta")) {
            col.setEditable(false);
        }
        if (title.equals("Debe") || title.equals("Haber")) {
            col.setStyle("-fx-alignment: CENTER-RIGHT;");
        }
        return col;
    }

    public void clear() {
        tblAsientos.getItems().clear();
        detalle.clear();
        laDebe.setText("0.00");
        laHaber.setText("0.00");
    }

    public void agregaIconos() {
        btnBuscarCuenta.setGraphic(new ImageView("/Images/search.png"));
        btnActualizar.setGraphic(new ImageView("/Images/refresh.png"));
        btnBorrar.setGraphic(new ImageView("/Images/trash.png"));
        btnAtras.setGraphic(new ImageView("/Images/back.png"));
        btnGrabar.setGraphic(new ImageView("/Images/save.png"));
    }

    public void tooltips() {
        btnGrabar.setTooltip(new Tooltip("Guardar asiento"));
        btnActualizar.setTooltip(new Tooltip("Actualizar tabla"));
        btnBorrar.setTooltip(new Tooltip("Borrar cuenta de la tabla"));
        btnAtras.setTooltip(new Tooltip("Atras"));
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        agregaIconos();
        DateTimeFormatter dtf5 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        laNumAsiento.setText((Sentencias.numeroAsiento("call numeroAsiento()") + 1) + "");
        laFecha.setText(dtf5.format(LocalDateTime.now()));
        detecta();
        tooltips();
    }

}