package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import data.Alerta;
import data.GenerarReporte;
import data.Sentencias;
import data.ValidacionCampos;
import first.Imagen;
import first.loadWindow2;
import first.loadWindow3;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class contabilidadController implements Initializable {

    private loadWindow3 lw = new loadWindow3();
    private loadWindow2 lw2 = new loadWindow2();
    private ToggleGroup estado;
    private String lastDay;
    private GregorianCalendar calendar = new GregorianCalendar();
    private LocalDate current_date = LocalDate.now();

    @FXML
    private Button btnAsientos;

    @FXML
    private Button btnAsientos2;

    @FXML
    private Button btnAuxiliares;

    @FXML
    private Button btnCuentas;

    @FXML
    private Button btnDiario;

    @FXML
    private Button btnImpCierre;

    @FXML
    private Button btnImpFac;

    @FXML
    private Button btnImpNotaC;

    @FXML
    private Button btnImpDoc;

    @FXML
    private Button btnImpRec;

    @FXML
    private Button btnMayor;

    @FXML
    private Button btnNotaCredito;

    @FXML
    private Button btnVerCierre;

    @FXML
    private Button btnVerFac;

    @FXML
    private Button btnVerInven;

    @FXML
    private Button btnVerNotaC;

    @FXML
    private Button btnVerNotaD;

    @FXML
    private Button btnVerDoc;

    @FXML
    private Button btnVerRec;

    @FXML
    private Button btnNA1;

    @FXML
    private Button btnNA2;

    @FXML
    private Button btnNA3;

    @FXML
    private DatePicker cierreDesde;

    @FXML
    private DatePicker cierreHasta;

    @FXML
    private TextField factura;

    @FXML
    private TextArea descReport;

    @FXML
    private DatePicker invenDesde;

    @FXML
    private DatePicker invenHasta;

    @FXML
    private Label laAtras;

    @FXML
    private TextField notaC;

    @FXML
    private TextField notaD;

    @FXML
    private TextField doc;

    @FXML
    private TextField recibo;

    @FXML
    private TextField prodCodigo;

    @FXML
    private ComboBox<String> reportes;

    @FXML
    private RadioButton normal;

    @FXML
    private RadioButton carta;

    @FXML
    private ComboBox<String> mes;

    ////////////////////////////////////////////////////////// REGISTROS

    @FXML
    private HBox box1;

    @FXML
    private HBox box2;

    @FXML
    private HBox box3;

    @FXML
    private HBox box4;

    //////////////////////////////////////////////////////////// REPORTES

    @FXML
    private VBox box5;

    @FXML
    private VBox box6;

    @FXML
    private VBox box7;

    public void togleButton() {

        box1.getStyleClass().add("vbox2");

        box2.getStyleClass().add("vbox2");

        box3.getStyleClass().add("vbox2");

        box4.getStyleClass().add("vbox2");

        box5.getStyleClass().add("vbox2");

        box6.getStyleClass().add("vbox2");

        box7.getStyleClass().add("vbox2");

        estado = new ToggleGroup();

        normal.setToggleGroup(estado);
        carta.setToggleGroup(estado);
    }

    public void repInvent(boolean a, boolean b, boolean c, boolean d) {
        invenDesde.setDisable(a);
        invenHasta.setDisable(b);
        prodCodigo.setEditable(c);
        mes.setDisable(d);
        prodCodigo.clear();
    }

    public <T> void detecta() {

        descReport.setWrapText(true);

        mes.setStyle("-fx-font: 18px \"System\";");
        cierreDesde.setStyle("-fx-font: 18px \"System\";");
        cierreHasta.setStyle("-fx-font: 18px \"System\";");
        reportes.setStyle("-fx-font: 18px \"System\";");
        invenDesde.setStyle("-fx-font: 18px \"System\";");
        invenHasta.setStyle("-fx-font: 18px \"System\";");

        invenDesde.setDisable(true);
        invenHasta.setDisable(true);
        prodCodigo.setEditable(false);
        mes.setDisable(true);

        mes.getItems().add("ENERO");
        mes.getItems().add("FEBRERO");
        mes.getItems().add("MARZO");
        mes.getItems().add("ABRIL");
        mes.getItems().add("MAYO");
        mes.getItems().add("JUNIO");
        mes.getItems().add("JULIO");
        mes.getItems().add("AGOSTO");
        mes.getItems().add("SEPTIEMBRE");
        mes.getItems().add("OCTUBRE");
        mes.getItems().add("NOVIEMBRE");
        mes.getItems().add("DICIEMBRE");

        mes.setOnAction(event -> {      
            if(mes.getSelectionModel().getSelectedItem() != null){
                switch (mes.getSelectionModel().getSelectedIndex()+1) {
                    case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                      lastDay = "31";
                      break;
                    case 4: case 6: case 9: case 11:
                      lastDay = "30";
                      break;
                    case 2:
                        int anio = current_date.getYear();
                        if (calendar.isLeapYear(anio)){
                            lastDay = "29";
                            //System.out.println("El año es bisiesto");
                        }
                        else{
                            lastDay = "28";
                            //System.out.println("El año no es bisiesto");
                        } 
                      break;
                    default:
                      lastDay = "01";
                  }
            }
            //System.out.print(retornaFechaInicial()+" | "+retornaFechaFinal());
        });

        reportes.getItems().add("Reporte de Valor de Inventario");
        reportes.getItems().add("Reporte de Stock Critico");
        reportes.getItems().add("Reporte de Productos mas Vendidos");
        reportes.getItems().add("Reporte de Movimiento de un Producto");
        reportes.getItems().add("Reporte de Exceso de Stock");
        reportes.getItems().add("Reporte de Saldo de las Cuentas");
        reportes.getItems().add("Reporte de Ventas en un Mes");
        reportes.getItems().add("Reporte de Ventas por Facturas");

        reportes.setOnAction(event -> {
            if (reportes.getSelectionModel().getSelectedIndex() == 0) {
                repInvent(true, true, false,true);
                descReport.setText("Reporte que muestra el valor de compra de la existencia de los productos.");
            } else if (reportes.getSelectionModel().getSelectedIndex() == 1) {
                repInvent(true, true, false,true);
                descReport.setText(
                        "Reporte que muestra los productos que están por debajo o igual que su inventario mínimo, ósea el mínimo de producto que puede haber en existencia.");
            } else if (reportes.getSelectionModel().getSelectedIndex() == 2) {
                repInvent(false, false, false,true);
                descReport.setText(
                        "Reporte que muestra una lista de los 25 productos más vendidos en un rango determinado de fechas, un día, una semana, un mes, etc.");
            } else if (reportes.getSelectionModel().getSelectedIndex() == 3) {
                repInvent(false, false, true,true);
                descReport.setText(
                        "Reporte que muestra las entradas y salidas de un producto determinado entre un rango de fechas específicos, ventas, compras, etc.");
            } else if (reportes.getSelectionModel().getSelectedIndex() == 4) {
                repInvent(true, true, false,true);
                descReport.setText(
                        "Reporte que muestra los productos que están por encima o igual que su inventario máximo, ósea el máximo de producto que puede haber en existencia.");
            } else if (reportes.getSelectionModel().getSelectedIndex() == 5) {
                repInvent(false, false, false,true);
                descReport.setText("Reporte que muestra los saldos de las cuentas contables que están en uso.");
            } else if (reportes.getSelectionModel().getSelectedIndex() == 6) {
                repInvent(true, true, false,false);
                descReport.setText("Reporte que muestra las ventas en un mes específico.");
            } else if (reportes.getSelectionModel().getSelectedIndex() == 7) {
                repInvent(false, false, false,true);
                descReport.setText("Reporte que muestra las ventas por facturas en un rango de fechas especificas.");
            }
        });

        btnAsientos.setOnAction(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            if (Sentencias.validaAccesosUser(event, "31")) {
                lw.loadStage("/view/viewRegistroAsientos.fxml", event, 1280, 720, stage);
            }
        });

        btnDiario.setOnAction(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            if (Sentencias.validaAccesosUser(event, "34")) {
                lw.loadStage("/view/viewVerAsientos.fxml", event, 1280, 720, stage);
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        btnAsientos2.setOnAction(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            if (Sentencias.validaAccesosUser(event, "38")) {
                String movimiento = Alerta.tipoMovimiento();
                if (movimiento.equals("INGRESAR PRODUCTO")) {
                    lw.loadStage("/view/viewIngresarProd.fxml", event, 1280, 720, stage);
                } else if (movimiento.equals("SACAR PRODUCTO")) {
                    lw.loadStage("/view/viewSacarProd.fxml", event, 1280, 720, stage);
                }
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        btnAuxiliares.setOnAction(event -> {

            if (Sentencias.validaAccesosUser(event, "33")) {

            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        btnCuentas.setOnAction(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            if (Sentencias.validaAccesosUser(event, "36")) {
                if (Sentencias.validaAccesosUser(event, "65")) {
                    lw2.loadStage("/view/viewRegistroCuentas.fxml", event, 1000, 650, stage.getTitle());
                }
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        btnMayor.setOnAction(event -> {

            if (Sentencias.validaAccesosUser(event, "32")) {

            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        btnNotaCredito.setOnAction(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            if (Sentencias.validaAccesosUser(event, "37")) {
                String comprobante = Alerta.tipoNota();
                if (comprobante.equals("NOTA DE CRÉDITO")) {
                    if (comprobanteController.recibe().equals("1")) {
                        lw.loadStage("/view/viewNotaCredito.fxml", event, 1280, 720, stage);
                    }
                } else if (comprobante.equals("NOTA DE DEBITO")) {
                    if (comprobantedosController.recibe().equals("1")) {
                        lw.loadStage("/view/viewNotaDebito.fxml", event, 1280, 720, stage);
                    }
                }
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        btnVerFac.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "91")) {
                if (factura.getText().length() == 16) {
                    String fac = "";
                    for (int x = 0; x < 3; x++) {
                        fac += factura.getText().charAt(x);
                    }
                    fac += "-";
                    for (int x = 3; x < 6; x++) {
                        fac += factura.getText().charAt(x);
                    }
                    fac += "-";
                    for (int x = 6; x < 8; x++) {
                        fac += factura.getText().charAt(x);
                    }
                    fac += "-";
                    for (int x = 8; x < 16; x++) {
                        fac += factura.getText().charAt(x);
                    }
                    if (normal.isSelected()) {
                        genera(fac, "facNum", "/Reportes/Factura.jasper", "ver");
                    } else if (carta.isSelected()) {
                        genera(fac, "facNum", "/Reportes/FacturaCarta.jasper", "ver");
                    }
                } else {
                    Alerta.mostrarAlertWarning("La factura ingresada no es valida!");
                }
                factura.clear();
                factura.clear();
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        btnImpFac.setOnAction(event -> {
            if (normal.isSelected()) {
                genera(factura.getText(), "facNum", "/Reportes/Factura.jasper", "imprimir");
            } else if (carta.isSelected()) {
                genera(factura.getText(), "facNum", "/Reportes/FacturaCarta.jasper", "imprimir");
            }
            factura.clear();
        });

        btnVerNotaC.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "92")) {
                if (!notaC.getText().isEmpty()) {
                    genera(notaC.getText(), "code", "/Reportes/NotaCredito.jasper", "ver");
                    notaC.clear();
                } else {
                    Alerta.mostrarAlertWarning("Ingrese el código/número de la nota de credito!");
                }
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        btnVerNotaD.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "92")) {
                if (!notaD.getText().isEmpty()) {
                    genera(notaD.getText(), "code", "/Reportes/NotaDebito.jasper", "ver");
                    notaD.clear();
                } else {
                    Alerta.mostrarAlertWarning("Ingrese el código/número de la nota de debito!");
                }
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        btnImpNotaC.setOnAction(event -> {
            genera(notaC.getText(), "code", "/Reportes/NotaCredito.jasper", "imprimir");
            notaC.clear();
        });

        btnVerCierre.setOnAction(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            String[] usuario = stage.getTitle().split("\\s+");
            if (Sentencias.validaAccesosUser(event, "95")) {
                if (cierreDesde.getValue() != null && cierreHasta.getValue() != null) {
                    genera2(cierreDesde.getValue() + "", cierreHasta.getValue() + "",
                            Sentencias
                                    .retornaUserCodigo("Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');")
                                    + "",
                            "fechaDesde", "fechaHasta", "usu",
                            "/Reportes/CierreCaja.jasper", "ver");
                } else {
                    Alerta.mostrarAlertWarning("Ingrese el rango de Fechas!");
                }
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        btnImpCierre.setOnAction(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            String[] usuario = stage.getTitle().split("\\s+");
            genera2(cierreDesde.getValue() + "", cierreHasta.getValue() + "",
                    Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');") + "",
                    "fechaDesde", "fechaHasta", "usu",
                    "/Reportes/CierreCaja.jasper", "imprimir");
        });

        btnVerInven.setOnAction(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            String[] usuario = stage.getTitle().split("\\s+");

            if (Sentencias.validaAccesosUser(event, "96")) {
                if (reportes.getSelectionModel().getSelectedIndex() == 0) {
                    genera(Sentencias
                            .retornaUserCodigo("Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');") + "",
                            "userCodigo", "/Reportes/InventarioValorCompra.jasper", "ver");
                } else if (reportes.getSelectionModel().getSelectedIndex() == 1) {
                    genera(Sentencias
                            .retornaUserCodigo("Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');") + "",
                            "userCodigo", "/Reportes/InventarioStockCritico.jasper", "ver");
                } else if (reportes.getSelectionModel().getSelectedIndex() == 2) {
                    if(invenDesde.getValue() != null && invenHasta.getValue() != null){
                        genera2(invenDesde.getValue() + "", invenHasta.getValue() + "",
                            Sentencias.retornaUserCodigo(
                                    "Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');") + "",
                            "desde", "hasta", "userCodigo",
                            "/Reportes/ProductosMasVendido.jasper", "ver");
                    }else{
                        Alerta.mostrarAlertWarning("Ingrese el rango de fechas para mostrar el reporte!");
                    }
                } else if (reportes.getSelectionModel().getSelectedIndex() == 3) {
                    if(invenDesde.getValue() != null && invenHasta.getValue() != null && !prodCodigo.getText().isEmpty()){
                        genera3(invenDesde.getValue() + "", invenHasta.getValue() + "",
                            Sentencias.retornaUserCodigo(
                                    "Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');") + "",
                            prodCodigo.getText(), "desde", "hasta", "userCodigo", "prodCodigo",
                            "/Reportes/MovimientoUnProducto.jasper", "ver");
                    }else{
                        Alerta.mostrarAlertWarning("Ingrese el rango de fechas y el código del producto para mostrar reporte!");
                    }
                } else if (reportes.getSelectionModel().getSelectedIndex() == 4) {
                    genera(Sentencias
                            .retornaUserCodigo("Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');") + "",
                            "userCodigo", "/Reportes/InventarioExcesoStock.jasper", "ver");
                } else if (reportes.getSelectionModel().getSelectedIndex() == 5) {
                    if(invenDesde.getValue() != null && invenHasta.getValue() != null){
                        genera2(invenDesde.getValue() + "", invenHasta.getValue() + "",
                            Sentencias.retornaUserCodigo(
                                    "Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');") + "",
                            "desde", "hasta", "userCodigo",
                            "/Reportes/TotalCuentas.jasper", "ver");
                    }else{
                        Alerta.mostrarAlertWarning("Ingrese el rango de fechas para mostrar el reporte!");
                    }
                } else if (reportes.getSelectionModel().getSelectedIndex() == 6) {
                    if(mes.getSelectionModel().getSelectedItem() != null){
                        genera3(retornaFechaInicial() + "", retornaFechaFinal() + "",
                            Sentencias.retornaUserCodigo(
                                    "Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');") + "",
                                    mes.getSelectionModel().getSelectedItem(), "desde", "hasta", "userCodigo", "mes",
                            "/Reportes/VentasEnUnMes.jasper", "ver");
                    }else{
                        Alerta.mostrarAlertWarning("Elija el mes en que desea ver el reporte de ventas!");
                    }
                } else if (reportes.getSelectionModel().getSelectedIndex() == 7) {
                    if(invenDesde.getValue() != null && invenHasta.getValue() != null){
                        genera2(invenDesde.getValue() + "", invenHasta.getValue() + "",
                            Sentencias.retornaUserCodigo(
                                    "Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');") + "",
                            "desde", "hasta", "userCodigo",
                            "/Reportes/VentasPorFactura.jasper", "ver");
                    }else{
                        Alerta.mostrarAlertWarning("Ingrese el rango de fechas para mostrar el reporte!");
                    }
                }
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        btnVerDoc.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "94")) {
                if (!doc.getText().isEmpty()) {
                    String tipo = Sentencias.retornaTipoDoc("Call retornaTipoDoc('" + doc.getText() + "')");
                    if (tipo.equals("Entrada")) {
                        genera(doc.getText(), "code", "/Reportes/IngresarProd.jasper", "ver");
                        doc.clear();
                    } else if (tipo.equals("Salida")) {
                        genera(doc.getText(), "code", "/Reportes/SacarProd.jasper", "ver");
                        doc.clear();
                    }
                } else {
                    Alerta.mostrarAlertWarning("Ingrese el código/número del documento!");
                }
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        btnImpDoc.setOnAction(event -> {

            genera(doc.getText(), "code", "/Reportes/SacarProd.jasper", "imprimir");
            doc.clear();
        });

        btnVerRec.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "93")) {
                if (!recibo.getText().isEmpty()) {
                    genera(recibo.getText(), "code", "/Reportes/ReciboCompra.jasper", "ver");
                    recibo.clear();
                } else {
                    Alerta.mostrarAlertWarning("Ingrese el código/número del recibo!");
                }
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        btnImpRec.setOnAction(event -> {
            genera(recibo.getText(), "code", "/Reportes/ReciboCompra.jasper", "imprimir");
            recibo.clear();
        });

    }

    @FXML
    void laAtrasMouseClicked(MouseEvent event) {
        Object evt = event.getSource();

        if (evt.equals(laAtras)) {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            lw.loadStage("/view/viewInicio.fxml", event, 1280, 720, stage);
        }
    }

    @FXML
    void laAtrasMouseEntered(MouseEvent event) {
        Object evt = event.getSource();

        if (evt.equals(laAtras)) {
            laAtras.setGraphic(new ImageView("/Images/backAzul.png"));
        }
    }

    @FXML
    void laAtrasMouseExited(MouseEvent event) {
        Object evt = event.getSource();

        if (evt.equals(laAtras)) {
            laAtras.setGraphic(new ImageView("/Images/back.png"));
        }
    }

    public void genera(String num, String nomParam, String ruta, String tipo) {
        if (tipo.equals("ver")) {
            String param = num;
            Map<String, String> parameters = new HashMap<>();
            parameters.put(nomParam, param);
            GenerarReporte jasper = new GenerarReporte();
            jasper.verReporte(ruta, parameters);
        } else if (tipo.equals("imprimir")) {
            String param = num;
            Map<String, String> parameters = new HashMap<>();
            parameters.put(nomParam, param);
            GenerarReporte jasper = new GenerarReporte();
            jasper.Imprimir2(ruta, parameters);
        }
    }

    public void genera2(String a, String b, String c, String nomParam1, String nomParam2, String nomParam3,
            String ruta, String tipo) {
        String param1 = a + " 00:00:00";
        String param2 = b + " 23:59:59";
        String param3 = c;

        Map<String, String> parameters = new HashMap<>();
        parameters.put(nomParam1, param1);
        parameters.put(nomParam2, param2);
        parameters.put(nomParam3, param3);
        GenerarReporte jasper = new GenerarReporte();

        if (tipo.equals("ver")) {
            jasper.verReporte(ruta, parameters);
        } else if (tipo.equals("imprimir")) {
            jasper.Imprimir2(ruta, parameters);
        }
    }

    public void genera3(String a, String b, String c, String d, String nomParam1, String nomParam2, String nomParam3,
            String nomParam4,
            String ruta, String tipo) {
        String param1 = a + " 00:00:00";
        String param2 = b + " 23:59:59";
        String param3 = c;
        String param4 = d;

        Map<String, String> parameters = new HashMap<>();
        parameters.put(nomParam1, param1);
        parameters.put(nomParam2, param2);
        parameters.put(nomParam3, param3);
        parameters.put(nomParam4, param4);
        GenerarReporte jasper = new GenerarReporte();

        if (tipo.equals("ver")) {
            jasper.verReporte(ruta, parameters);
        } else if (tipo.equals("imprimir")) {
            jasper.Imprimir2(ruta, parameters);
        }
    }

    public void genera4(String cat, String usu, String nomParam1, String nomParam2, String ruta, String tipo) {
        if (tipo.equals("ver")) {
            String param1 = cat;
            String param2 = usu;
            Map<String, String> parameters = new HashMap<>();
            parameters.put(nomParam1, param1);
            parameters.put(nomParam2, param2);
            GenerarReporte jasper = new GenerarReporte();
            jasper.verReporte(ruta, parameters);
        } else if (tipo.equals("imprimir")) {
            String param1 = cat;
            String param2 = usu;
            Map<String, String> parameters = new HashMap<>();
            parameters.put(nomParam1, param1);
            parameters.put(nomParam2, param2);
            GenerarReporte jasper = new GenerarReporte();
            jasper.Imprimir2(ruta, parameters);
        }
    }

    public String retornaFechaFinal(){
        int anio = current_date.getYear();
        String fecha = "";
        switch (mes.getSelectionModel().getSelectedIndex()+1) {
            case 1: 
              fecha = anio+"-01-"+lastDay;
              break;
            case 2:
              fecha = anio+"-02-"+lastDay;
              break;
            case 3:
              fecha = anio+"-03-"+lastDay;
              break;
            case 4:
              fecha = anio+"-04-"+lastDay;
              break;
            case 5: 
              fecha = anio+"-05-"+lastDay;
              break;
            case 6:			
              fecha = anio+"-06-"+lastDay;
              break;			
            case 7:			
              fecha = anio+"-07-"+lastDay;
              break;
            case 8:			
              fecha = anio+"-08-"+lastDay;
              break;
            case 9: 
              fecha = anio+"-09-"+lastDay;
              break;
            case 10:			
              fecha = anio+"-10-"+lastDay;
              break;			
            case 11:			
              fecha = anio+"-11-"+lastDay;
              break;
            case 12:			
              fecha = anio+"-12-"+lastDay;
              break;
            default:
              fecha = "1900-01-01";
          }
        return fecha;    
    }

    public String retornaFechaInicial(){
        int anio = current_date.getYear();
        String fecha = "";
        switch (mes.getSelectionModel().getSelectedIndex()+1) {
            case 1: 
              fecha = anio+"-01-01";
              break;
            case 2:
              fecha = anio+"-02-01";
              break;
            case 3:
              fecha = anio+"-03-01";
              break;
            case 4:
              fecha = anio+"-04-01";
              break;
            case 5: 
              fecha = anio+"-05-01";
              break;
            case 6:			
              fecha = anio+"-06-01";
              break;			
            case 7:			
              fecha = anio+"-07-01";
              break;
            case 8:			
              fecha = anio+"-08-01";
              break;
            case 9: 
              fecha = anio+"-09-01";
              break;
            case 10:			
              fecha = anio+"-10-01";
              break;			
            case 11:			
              fecha = anio+"-11-01";
              break;
            case 12:			
              fecha = anio+"-12-01";
              break;
            default:
              fecha = "1900-01-01";
          }
        return fecha;    
    }

    public void campos() {
        factura.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.enteros(event, factura, 16));
        notaC.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.enteros(event, notaC, 16));
        notaD.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.enteros(event, notaD, 16));
        recibo.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.enteros(event, recibo, 16));
        doc.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.enteros(event, doc, 16));
        prodCodigo.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.enteros(event, prodCodigo, 16));
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        detecta();
        togleButton();
        btnAuxiliares.setGraphic(Imagen.imagen("/Images/auxiliares.jpg"));
        btnDiario.setGraphic(Imagen.imagen("/Images/diario.jpg"));
        btnMayor.setGraphic(Imagen.imagen("/Images/mayor.jpg"));
        btnAsientos.setGraphic(Imagen.imagen("/Images/asientos.jpg"));
        btnAsientos2.setGraphic(Imagen.imagen("/Images/ajusteExistencia.jpg"));
        btnCuentas.setGraphic(Imagen.imagen("/Images/regiscuen.jpg"));
        btnNotaCredito.setGraphic(Imagen.imagen("/Images/notacredito.jpg"));
        btnNA1.setGraphic(Imagen.imagen("/Images/NA.jpg"));
        btnNA2.setGraphic(Imagen.imagen("/Images/NA.jpg"));
        btnNA3.setGraphic(Imagen.imagen("/Images/NA.jpg"));
        laAtras.setGraphic(new ImageView("/Images/back.png"));
        campos();
    }

}
