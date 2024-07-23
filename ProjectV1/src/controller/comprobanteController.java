package controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import data.Alerta;
import data.GenerarReporte;
import data.NumerosLetras;
import data.Sentencias;
import first.loadWindow3;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Producto;

public class comprobanteController implements Initializable {

    private static String facNum;
    private static String facFinal;
    private loadWindow3 lw = new loadWindow3();
    private ObservableList<Producto> listaProdFac = FXCollections.observableArrayList();
    private ObservableList<Producto> listaProdNota = FXCollections.observableArrayList();
    private ObservableList<Producto> listaProdNotaConFormato = FXCollections.observableArrayList();
    private ObservableList<Producto> listaProdNotaAux = FXCollections.observableArrayList();
    private NumberFormat df = new DecimalFormat("#0.00");
    private NumberFormat df2 = new DecimalFormat("#,##0.00");

    @FXML
    private Button addTodos;

    @FXML
    private Button addUno;

    @FXML
    private Button btnGenerar;

    @FXML
    private TextArea consepto;

    @FXML
    private Button delTodos;

    @FXML
    private Button delUno;

    @FXML
    private Label laAtras;

    @FXML
    private Label laDescuentoFac;

    @FXML
    private Label laDescuentoNota;

    @FXML
    private Label laFacNum;

    @FXML
    private Label laAnulada;

    @FXML
    private Label laImpuestoFac;

    @FXML
    private Label laImpuestoNota;

    @FXML
    private Label laNotaCredito;

    @FXML
    private Label laSuTotalFac;

    @FXML
    private Label laSubTotalNota;

    @FXML
    private Label laTotalFac;

    @FXML
    private Label laTotalNota;

    @FXML
    private TableColumn<Producto, String> columCantidadFac;

    @FXML
    private TableColumn<Producto, String> columCantidadNota;

    @FXML
    private TableColumn<Producto, String> columCodigoFac;

    @FXML
    private TableColumn<Producto, String> columCodigoNota;

    @FXML
    private TableColumn<Producto, String> columDescripFac;

    @FXML
    private TableColumn<Producto, String> columDescripNota;

    @FXML
    private TableColumn<Producto, String> columPrecioFac;

    @FXML
    private TableColumn<Producto, String> columPrecioNota;

    @FXML
    private TableColumn<Producto, String> columValorFac;

    @FXML
    private TableColumn<Producto, String> columValorNota;

    @FXML
    private TableView<Producto> tblFac;

    @FXML
    private TableView<Producto> tblNota;

    @FXML
    void laAtrasMouseClicked(MouseEvent event) {
        Object evt = event.getSource();

        if (evt.equals(laAtras)) {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            listaProdFac.clear();
            listaProdNota.clear();
            listaProdNotaConFormato.clear();
            listaProdNotaAux.clear();
            lw.loadStage("/view/viewContabilidad.fxml", event, 1280, 720, stage);
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

    public static String recibe() {
        while (true) {
            facFinal = "";

            facNum = Alerta.retornaFacturaNum();

            if (facNum.length() > 15) {
                for (int i = 0; i < 3; i++) {
                    facFinal += facNum.charAt(i);
                }
                facFinal += "-";
                for (int i = 3; i < 6; i++) {
                    facFinal += facNum.charAt(i);
                }
                facFinal += "-";
                for (int i = 6; i < 8; i++) {
                    facFinal += facNum.charAt(i);
                }
                facFinal += "-";
                for (int i = 8; i < 16; i++) {
                    facFinal += facNum.charAt(i);
                }
                if (Sentencias.verificaFactura("Call buscaFactura('" + facFinal + "');") == 1) {
                    facNum = facFinal;
                    return "1";
                } else {
                    Alerta.mostrarAlertWarning("No se encontro el número de factura ingresado!");
                }
            } else {
                Alerta.mostrarAlertWarning("El número de factura ingresado no es valido!");
            }
            if (facNum.equals("SALIR")) {
                break;
            }
        }
        return "0";
    }

    public void llenaTablaFac() {
        listaProdFac.addAll(Sentencias.retornaProductosFactura("Call retornaDetalleFactura('" + facFinal + "');"));
        tblFac.setItems(listaProdFac);
        Sentencias.retornaTotalesfactura("Call retornaTotalesFactura('" + facFinal + "');", laSuTotalFac, laImpuestoFac,
                laDescuentoFac, laTotalFac);
    }

    public void columnasTabla() {

        columCodigoFac.setCellValueFactory(new PropertyValueFactory<Producto, String>("codigo"));
        columDescripFac.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));
        columPrecioFac.setCellValueFactory(new PropertyValueFactory<Producto, String>("costoUtili"));
        columCantidadFac.setCellValueFactory(new PropertyValueFactory<Producto, String>("cantidad"));
        columValorFac.setCellValueFactory(new PropertyValueFactory<Producto, String>("valor"));

        columCodigoNota.setCellValueFactory(new PropertyValueFactory<Producto, String>("codigo"));
        columDescripNota.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));
        columPrecioNota.setCellValueFactory(new PropertyValueFactory<Producto, String>("costoUtili"));
        columCantidadNota.setCellValueFactory(new PropertyValueFactory<Producto, String>("cantidad"));
        columValorNota.setCellValueFactory(new PropertyValueFactory<Producto, String>("valor"));
    }

    private void detecta() {

        tblFac.setStyle("-fx-font: 18px \"System\";");
        tblNota.setStyle("-fx-font: 18px \"System\";");

        columCantidadFac.setStyle("-fx-alignment: CENTER-RIGHT;");
        columPrecioFac.setStyle("-fx-alignment: CENTER-RIGHT;");
        columValorFac.setStyle("-fx-alignment: CENTER-RIGHT;");

        columCantidadNota.setStyle("-fx-alignment: CENTER-RIGHT;");
        columPrecioNota.setStyle("-fx-alignment: CENTER-RIGHT;");
        columValorNota.setStyle("-fx-alignment: CENTER-RIGHT;");

        consepto.setWrapText(true);

        laFacNum.setText("DETALLE FACTURA #: " + facNum);

        if (Sentencias.retornaEstadoFactura("Call retornaEstadoFactura('" + facFinal + "');").equals("1")) {
            laAnulada.setText("ANULADA: NO");
        } else if (Sentencias.retornaEstadoFactura("Call retornaEstadoFactura('" + facFinal + "');").equals("0")) {
            laAnulada.setText("ANULADA: SI");
        }

        if (Sentencias.retornaNotaCreditoNum("Call retornaNotaCreditoNum();").equals("")) {
            laNotaCredito.setText("NOTA DE CRÉDITO#: " + 1);
        } else {
            laNotaCredito.setText("NOTA DE CRÉDITO#: "
                    + (Integer.parseInt(Sentencias.retornaNotaCreditoNum("Call retornaNotaCreditoNum();")) + 1));
        }

        addUno.setOnAction(event -> {
            if (tblFac.getSelectionModel().getSelectedIndex() != -1) {
                agregaProductoTblNota(listaProdFac.get(tblFac.getSelectionModel().getSelectedIndex()).getCodigo(),
                        listaProdFac.get(tblFac.getSelectionModel().getSelectedIndex()).getCantidad());
            } else {
                Alerta.mostrarAlertWarning("Seleccione un elemento de la primer tabla!");
            }
        });

        addTodos.setOnAction(event -> {
            tblNota.getItems().clear();
            listaProdNota.clear();
            listaProdNotaConFormato.clear();
            listaProdNotaAux.clear();
            for (int i = 0; i < listaProdFac.size(); i++) {
                agregaTodosProductosTblNota(listaProdFac.get(i).getCodigo(),
                        listaProdFac.get(i).getCantidad());
            }

        });

        delUno.setOnAction(event -> {
            int aux = tblNota.getSelectionModel().getSelectedIndex();
            if (aux != -1) {
                listaProdNota.remove(aux);
                listaProdNotaConFormato.remove(aux);
                listaProdNotaAux.remove(aux);
                tblNota.setItems(listaProdNota);
                laSubTotalNota.setText(retornaSubTotalConFormato() + " Lps");
                laImpuestoNota.setText(retornaImpuestoConFormato() + " Lps");
                laDescuentoNota.setText(retornaDescuentoConFormato() + " Lps");
                laTotalNota.setText(df2.format(retornaTotal() - Double.parseDouble(retornaDescuento())) + " Lps");
            }
        });

        delTodos.setOnAction(event -> {
            listaProdNota.clear();
            listaProdNotaConFormato.clear();
            listaProdNotaAux.clear();
            tblNota.getItems().clear();
            laSubTotalNota.setText(retornaSubTotalConFormato() + " Lps");
            laImpuestoNota.setText(retornaImpuestoConFormato() + " Lps");
            laDescuentoNota.setText(retornaDescuentoConFormato() + " Lps");
            laTotalNota.setText(df2.format(retornaTotal() - Double.parseDouble(retornaDescuento())) + " Lps");
        });

        btnGenerar.setOnAction(event -> {
            if (tblNota.getItems().size() > 0) {
                if (!consepto.getText().isEmpty()) {
                    if (Alerta.mostrarAlertConfirmation("Seguro que desea generar el comprobante?") == 1) {
                        generarNota(event);
                        laNotaCredito.setText("NOTA DE CRÉDITO#: "
                                + (Integer.parseInt(Sentencias.retornaNotaCreditoNum("Call retornaNotaCreditoNum();"))
                                        + 1));
                        Alerta.mostrarAlertInfo("Nota de crédito generada!");
                        Node source = (Node) event.getSource();
                        Stage stage = (Stage) source.getScene().getWindow();
                        listaProdFac.clear();
                        listaProdNotaAux.clear();
                        listaProdNotaConFormato.clear();
                        listaProdNota.clear();
                        lw.loadStage("/view/viewContabilidad.fxml", event, 1280, 720, stage);
                    }
                } else {
                    Alerta.mostrarAlertWarning("Agregue el concepto de la nota!");
                }
            } else {
                Alerta.mostrarAlertWarning("Agregue los datos a la tabla inferior!");
            }
        });
    }

    public void generarNota(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        String[] usuario = stage.getTitle().split("\\s+");

        Sentencias.crud("Call guardarTransaccion('" + 3 + "','select now()','"
                + Sentencias.retornaClienteFactura("Call retornaClienteFactura('" + facFinal + "');") + "','"
                + Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');") + "','"
                + "N/A" + "','"
                + retornaSubTotal0() + "','"
                + retornaSubTotal15() + "','"
                + retornaSubTotal18() + "','"
                + retornaImpuesto15() + "','"
                + retornaImpuesto18() + "','"
                + retornaValorReal(laDescuentoNota.getText().replace(",", "")) + "','"
                + "0.00" + "','" + retornaValorReal(laTotalNota.getText().replace(",", "")) + "','" + 0.00 + "','"
                + 0.00 + "','" + "N/A" + "','" + "N/A" + "','"
                + NumerosLetras.Convertir(retornaValorReal(laTotalNota.getText().replace(",", "")), true) + "','"
                + consepto.getText() + "','" + 1 + "');");

        for (int i = 0; i < listaProdNota.size(); i++) {
            double isv = 0.0;
            double desc = 0.0;
            isv += ((listaProdNotaAux.get(i).getPcosto()
                    + listaProdNotaAux.get(i).getPcosto() * (listaProdNotaAux.get(i).getUtilidad() / 100))
                    * (listaProdNotaAux.get(i).getImpuesto() / 100)) * listaProdNota.get(i).getCantidad();

            desc += ((listaProdNotaAux.get(i).getPcosto()
                    + listaProdNotaAux.get(i).getPcosto() * (listaProdNotaAux.get(i).getUtilidad() / 100))
                    * (listaProdNotaAux.get(i).getDescuento() / 100)) * listaProdNota.get(i).getCantidad();

            Sentencias.crud("Call guardarTransaccionDetalle('"
                    + Sentencias.retornaUltimaTransaccion("Call retornaUltimaTransaccion();") + "','" + (i + 1) + "','"
                    + listaProdNota.get(i).getCodigo().toString() + "','" + listaProdNota.get(i).getNombre() + "','"
                    + listaProdNota.get(i).getCantidad() + "','" + listaProdNotaAux.get(i).getPcosto() + "','"
                    + listaProdNota.get(i).getCostoUtili() + "','" + isv + "','" + desc + "','"
                    + listaProdNota.get(i).getValor()
                    + "');");
        }

        Sentencias.crud("Call guardarNotaCredito('" + facFinal + "','"
                + Sentencias.retornaUltimaTransaccion("Call retornaUltimaTransaccion();") + "','" + 1 + "');");

        Sentencias.crud("Call crearRegistroBitacora('Se genero nota de credito','CONTABILIDAD','"
                + Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');")
                + "')");

        Sentencias.crud("Call anularFactura('" + facFinal + "');");
        genera();
    }

    public void genera() {
        String notaCode = Sentencias.retornaNotaCreditoNum("Call retornaNotaCreditoNum();");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("code", notaCode);
        GenerarReporte jasper = new GenerarReporte();
        jasper.ImprimirDirecto("/Reportes/NotaCredito.jasper", parameters);
    }

    private void agregaProductoTblNota(String codigo, double cantidad) {
        Producto prod = Sentencias.retornaUnProducto("Call retornaUnProducto('" + codigo + "');");

        String cuantos = "";

        if (prod.getMedida().equals("Unidad")) {
            cuantos = Alerta.retornaCantidadUnidad(prod.getNombre());
        } else if (prod.getMedida().equals("Libra")) {
            cuantos = Alerta.retornaCantidadLibra(prod.getNombre());
        }

        if (!cuantos.equals("0") && !cuantos.equals("") && !cuantos.equals(".")) {

            double precio = prod.getPcosto() + (prod.getPcosto() * (prod.getUtilidad() / 100));
            double valor = precio * Double.parseDouble(cuantos);

            if (Double.parseDouble(cuantos) <= cuentaProductosFac(codigo)) {
                if (cuentaProductosNota(codigo) < cuentaProductosFac(codigo)
                        && (cuentaProductosNota(codigo) + Double.parseDouble(cuantos)) <= cuentaProductosFac(codigo)) {

                    Producto prodFinal = new Producto(codigo, prod.getNombre(), df.format(precio),
                            Double.parseDouble(cuantos),
                            df.format(valor));

                    Producto prodFinalCF = new Producto(codigo, prod.getNombre(), df2.format(precio),
                            Double.parseDouble(cuantos),
                            df2.format(valor));

                    listaProdNota.add(prodFinal);
                    listaProdNotaConFormato.add(prodFinalCF);
                    listaProdNotaAux.add(prod);
                    tblNota.setItems(listaProdNotaConFormato);
                    laSubTotalNota.setText(retornaSubTotalConFormato() + " Lps");
                    laImpuestoNota.setText(retornaImpuestoConFormato() + " Lps");
                    laDescuentoNota.setText(retornaDescuentoConFormato() + " Lps");
                    laTotalNota.setText(df2.format(retornaTotal() - Double.parseDouble(retornaDescuento())) + " Lps");
                }
            }
        }
    }

    private void agregaTodosProductosTblNota(String codigo, double cantidad) {
        Producto prod = Sentencias.retornaUnProducto("Call retornaUnProducto('" + codigo + "');");

        double precio = prod.getPcosto() + (prod.getPcosto() * (prod.getUtilidad() / 100));
        double valor = precio * cantidad;

        Producto prodFinal = new Producto(codigo, prod.getNombre(), df.format(precio), cantidad,
                df.format(valor));

        Producto prodFinalCF = new Producto(codigo, prod.getNombre(), df2.format(precio), cantidad,
                df2.format(valor));

        listaProdNota.add(prodFinal);
        listaProdNotaConFormato.add(prodFinalCF);
        listaProdNotaAux.add(prod);
        tblNota.setItems(listaProdNotaConFormato);
        laSubTotalNota.setText(retornaSubTotalConFormato() + " Lps");
        laImpuestoNota.setText(retornaImpuestoConFormato() + " Lps");
        laDescuentoNota.setText(retornaDescuentoConFormato() + " Lps");
        laTotalNota.setText(df2.format(retornaTotal() - Double.parseDouble(retornaDescuento())) + " Lps");
    }

    public String retornaValorReal(String val) {
        String[] arre = val.split("\\s+");
        return arre[0];
    }

    public double cuentaProductosNota(String codigo) {
        double cont = 0;
        for (int i = 0; i < listaProdNota.size(); i++) {
            if (codigo.equals(listaProdNota.get(i).getCodigo())) {
                cont += listaProdNota.get(i).getCantidad();
            }
        }
        return cont;
    }

    public double cuentaProductosFac(String codigo) {
        double cont = 0;
        for (int i = 0; i < listaProdFac.size(); i++) {
            if (codigo.equals(listaProdFac.get(i).getCodigo())) {
                cont += listaProdFac.get(i).getCantidad();
            }
        }
        return cont;
    }

    public String retornaSubTotal() {
        double sTotal = 0.0;
        for (int i = 0; i < listaProdNota.size(); i++) {
            sTotal += Double.parseDouble(listaProdNota.get(i).getValor());
        }
        return df.format(sTotal);
    }

    public String retornaSubTotalConFormato() {
        double sTotal = 0.0;
        for (int i = 0; i < listaProdNota.size(); i++) {
            sTotal += Double.parseDouble(listaProdNota.get(i).getValor());
        }
        return df2.format(sTotal);
    }

    public double retornaSubTotal0() {
        double sTotal = 0.0;
        for (int i = 0; i < listaProdNota.size(); i++) {
            if (listaProdNotaAux.get(i).getImpuesto() == 0.00) {
                sTotal += Double.parseDouble(listaProdNota.get(i).getValor());
            }
        }
        return Double.parseDouble(df.format(sTotal));
    }

    public double retornaSubTotal15() {
        double sTotal = 0.0;
        for (int i = 0; i < listaProdNota.size(); i++) {
            if (listaProdNotaAux.get(i).getImpuesto() == 15.00) {
                sTotal += Double.parseDouble(listaProdNota.get(i).getValor());
            }
        }
        return Double.parseDouble(df.format(sTotal));
    }

    public double retornaSubTotal18() {
        double sTotal = 0.0;
        for (int i = 0; i < listaProdNota.size(); i++) {
            if (listaProdNotaAux.get(i).getImpuesto() == 18.00) {
                sTotal += Double.parseDouble(listaProdNota.get(i).getValor());
            }
        }
        return Double.parseDouble(df.format(sTotal));
    }

    public double retornaTotal() {
        double gTotal = 0.0;
        for (int i = 0; i < listaProdNota.size(); i++) {
            gTotal += listaProdNotaAux.get(i).getPventa() * listaProdNota.get(i).getCantidad();
        }
        return Double.parseDouble(df.format(gTotal));
    }

    public String retornaImpuesto() {
        double isv = 0.0;
        for (int i = 0; i < listaProdNota.size(); i++) {
            isv += ((listaProdNotaAux.get(i).getPcosto()
                    + listaProdNotaAux.get(i).getPcosto() * (listaProdNotaAux.get(i).getUtilidad() / 100))
                    * (listaProdNotaAux.get(i).getImpuesto() / 100)) * listaProdNota.get(i).getCantidad();
        }
        return df.format(isv);
    }

    public String retornaImpuestoConFormato() {
        double isv = 0.0;
        for (int i = 0; i < listaProdNota.size(); i++) {
            isv += ((listaProdNotaAux.get(i).getPcosto()
                    + listaProdNotaAux.get(i).getPcosto() * (listaProdNotaAux.get(i).getUtilidad() / 100))
                    * (listaProdNotaAux.get(i).getImpuesto() / 100)) * listaProdNota.get(i).getCantidad();
        }
        return df2.format(isv);
    }

    public double retornaImpuesto15() {
        double isv = 0.0;
        for (int i = 0; i < listaProdNota.size(); i++) {
            if (listaProdNotaAux.get(i).getImpuesto() == 15.00) {
                isv += ((listaProdNotaAux.get(i).getPcosto()
                        + listaProdNotaAux.get(i).getPcosto() * (listaProdNotaAux.get(i).getUtilidad() / 100))
                        * (listaProdNotaAux.get(i).getImpuesto() / 100)) * listaProdNota.get(i).getCantidad();
            }
        }
        return Double.parseDouble(df.format(isv));
    }

    public double retornaImpuesto18() {
        double isv = 0.0;
        for (int i = 0; i < listaProdNota.size(); i++) {
            if (listaProdNotaAux.get(i).getImpuesto() == 18.00) {
                isv += ((listaProdNotaAux.get(i).getPcosto()
                        + listaProdNotaAux.get(i).getPcosto() * (listaProdNotaAux.get(i).getUtilidad() / 100))
                        * (listaProdNotaAux.get(i).getImpuesto() / 100)) * listaProdNota.get(i).getCantidad();
            }
        }
        return Double.parseDouble(df.format(isv));
    }

    public String retornaDescuento() {
        double desc = 0.0;
        for (int i = 0; i < listaProdNota.size(); i++) {

            desc += ((listaProdNotaAux.get(i).getPcosto()
                    + listaProdNotaAux.get(i).getPcosto() * (listaProdNotaAux.get(i).getUtilidad() / 100))
                    * (listaProdNotaAux.get(i).getDescuento() / 100)) * listaProdNota.get(i).getCantidad();
        }

        return df.format(desc);
    }

    public String retornaDescuentoConFormato() {
        double desc = 0.0;
        for (int i = 0; i < listaProdNota.size(); i++) {

            desc += ((listaProdNotaAux.get(i).getPcosto()
                    + listaProdNotaAux.get(i).getPcosto() * (listaProdNotaAux.get(i).getUtilidad() / 100))
                    * (listaProdNotaAux.get(i).getDescuento() / 100)) * listaProdNota.get(i).getCantidad();
        }

        return df2.format(desc);
    }

    public void agregarIconos() {
        laAtras.setGraphic(new ImageView("/Images/back.png"));
        addUno.setGraphic(new ImageView("/Images/addOne.png"));
        addTodos.setGraphic(new ImageView("/Images/addAll.png"));
        delUno.setGraphic(new ImageView("/Images/delOne.png"));
        delTodos.setGraphic(new ImageView("/Images/delAll.png"));
    }

    public void validaCampos() {
        // consepto.addEventHandler(KeyEvent.KEY_TYPED, event ->
        // ValidacionCampos.letrasNumeros(event, consepto, true, 15));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        detecta();
        columnasTabla();
        llenaTablaFac();
        agregarIconos();
        validaCampos();
    }
}
