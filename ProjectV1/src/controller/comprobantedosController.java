package controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import data.Alerta;
import data.GenerarReporte;
import data.NumerosLetras;
import data.Sentencias;
import data.ValidacionCampos;
import first.loadWindow3;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Producto;

public class comprobantedosController implements Initializable {

    private ComboBox<String> coincidenciasAux;
    private ObservableList<Producto> listaProd = FXCollections.observableArrayList();
    private ObservableList<Producto> listaProdConFormato = FXCollections.observableArrayList();
    private ObservableList<Producto> listaProdAux = FXCollections.observableArrayList();
    private loadWindow3 lw = new loadWindow3();
    private String cantidadProd;
    private NumberFormat df = new DecimalFormat("#0.00");
    private NumberFormat df2 = new DecimalFormat("#,##0.00");
    private static String facNum;
    private static String facFinal;

    @FXML
    private Spinner<String> spCantidad;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnImprimirFactura;

    @FXML
    private Button btnQuitar;

    @FXML
    private TextField buscar;

    @FXML
    private TextField stock;

    @FXML
    private TextArea detalle;

    @FXML
    private ComboBox<String> categorias;

    @FXML
    private ComboBox<String> coincidencias;

    @FXML
    private Label laAtras;

    @FXML
    private Label laDescuento;

    @FXML
    private Label laImpuesto;

    @FXML
    private Label laSubtotal;

    @FXML
    private Label laTotal;

    @FXML
    private Label laFacNum;

    @FXML
    private Label laNotaNum;

    @FXML
    private TableView<Producto> tblListaProd;

    @FXML
    private TableColumn<Producto, String> columCantidad;

    @FXML
    private TableColumn<Producto, String> columCodigo;

    @FXML
    private TableColumn<Producto, String> columDescrip;

    @FXML
    private TableColumn<Producto, String> columPrecio;

    @FXML
    private TableColumn<Producto, String> columValor;

    @FXML
    void actionTeclaControl(KeyEvent event) {
        // btnImprimirFactura.requestFocus();
    }

    @FXML
    void buscarOnActionEvent(ActionEvent event) {
        Object avt = event.getSource();

        if (avt.equals(buscar)) {
            Sentencias.buscarProd2("call retornaProductosActivos()", buscar.getText(), coincidencias, coincidenciasAux);
            coincidencias.getSelectionModel().select(0);
            if (coincidencias.getSelectionModel().getSelectedItem() != null && coincidencias.getItems().size() == 1) {
                llenaCamposProducto("CALL retornaProductos()");
                tblListaProd.setItems(listaProdConFormato);
                laSubtotal.setText(retornaSubTotalConFormato() + " Lps");
                laImpuesto.setText(retornaImpuestoConFormato() + " Lps");
                laDescuento.setText(retornaDescuentoConFormato() + " Lps");
                laTotal.setText(df2.format(retornaTotal()) + " Lps");
            }

        }
    }

    @FXML
    void laAtrasMouseClicked(MouseEvent event) {
        Object evt = event.getSource();

        if (evt.equals(laAtras)) {
            listaProd.clear();
            listaProdConFormato.clear();
            listaProdAux.clear();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
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

    public void datosTabla() {
        columCodigo.setCellValueFactory(new PropertyValueFactory<Producto, String>("codigo"));
        columDescrip.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));
        columPrecio.setCellValueFactory(new PropertyValueFactory<Producto, String>("costoUtili"));
        columCantidad.setCellValueFactory(new PropertyValueFactory<Producto, String>("cantidad"));
        columValor.setCellValueFactory(new PropertyValueFactory<Producto, String>("valor"));
    }

    private void llenaBoxProd(String sql) {

        ArrayList<Producto> productos = Sentencias.retornaProductos(sql);
        Producto producto = new Producto();
        coincidencias.getItems().clear();
        coincidenciasAux.getItems().clear();
        for (int i = 0; i < productos.size(); i++) {
            producto = productos.get(i);
            coincidenciasAux.getItems().add(producto.getCodigo());
            coincidencias.getItems().add(producto.getNombre());
        }

    }

    public <T> void detecta() {

        coincidenciasAux = new ComboBox<>();

        laFacNum.setText(facNum);

        if (Sentencias.retornaNotaCreditoNum("Call retornaNotaDebitoNum();").equals("")) {
            laNotaNum.setText(1 + "");
        } else {
            laNotaNum.setText(
                    "" + (Integer.parseInt(Sentencias.retornaNotaCreditoNum("Call retornaNotaDebitoNum();")) + 1));
        }

        tblListaProd.setStyle("-fx-font: 18px \"System\";");
        columPrecio.setStyle("-fx-alignment: CENTER-RIGHT;");
        columCantidad.setStyle("-fx-alignment: CENTER-RIGHT;");
        columValor.setStyle("-fx-alignment: CENTER-RIGHT;");

        coincidencias.setStyle("-fx-font: 18px \"System\";");

        categorias.setStyle("-fx-font: 18px \"System\";");
        Sentencias.llenaComboBox("call retornaCategorias()", categorias, new ComboBox<>());
        categorias.setOnAction(event -> {
            llenaBoxProd("CALL retornaProductosCategoriaActivos('" + categorias.getSelectionModel().getSelectedItem()
                    + "')");
            buscar.clear();
        });

        coincidencias.setOnAction(event -> {
            if (coincidencias.getSelectionModel().getSelectedItem() != null) {
                stock.setText(Sentencias.retornaStock("Call retornaStockUnProducto('"
                        + coincidenciasAux.getItems().get(coincidencias.getSelectionModel().getSelectedIndex()) + "')")
                        + "");
                buscar.setText(coincidencias.getSelectionModel().getSelectedItem() + "");
            }
        });

        btnBuscar.setOnAction(event -> {
            Sentencias.buscarProd2("call retornaProductosActivos()", buscar.getText(), coincidencias, coincidenciasAux);
            coincidencias.getSelectionModel().select(0);
            System.out.print("rellenarrrrrr");
            if (coincidencias.getSelectionModel().getSelectedItem() != null && coincidencias.getItems().size() == 1) {
                llenaCamposProducto("CALL retornaProductos()");
                tblListaProd.setItems(listaProdConFormato);
                laSubtotal.setText(retornaSubTotalConFormato() + " Lps");
                laImpuesto.setText(retornaImpuestoConFormato() + " Lps");
                laDescuento.setText(retornaDescuentoConFormato() + " Lps");
                laTotal.setText(df2.format(retornaTotal()) + " Lps");
            }
        });

        btnAgregar.setOnAction(event -> {
            if (coincidencias.getSelectionModel().getSelectedItem() != null) {
                llenaCamposProducto("CALL retornaProductos()");
                tblListaProd.setItems(listaProdConFormato);
                laSubtotal.setText(retornaSubTotalConFormato() + " Lps");
                laImpuesto.setText(retornaImpuestoConFormato() + " Lps");
                laDescuento.setText(retornaDescuentoConFormato() + " Lps");
                laTotal.setText(df2.format(retornaTotal()) + " Lps");
            }

        });

        btnQuitar.setOnAction(event -> {
            int aux = tblListaProd.getSelectionModel().getSelectedIndex();
            if (aux != -1) {
                listaProd.remove(aux);
                listaProdConFormato.remove(aux);
                listaProdAux.remove(aux);
                tblListaProd.setItems(listaProdConFormato);
                laSubtotal.setText(retornaSubTotalConFormato() + " Lps");
                laTotal.setText(df2.format(retornaTotal()) + " Lps");
                laDescuento.setText(retornaDescuentoConFormato() + " Lps");
                laImpuesto.setText(retornaImpuestoConFormato() + " Lps");
            }
        });

        btnImprimirFactura.setOnAction(event -> {
            finalizarVenta(event);
        });
    }

    public void finalizarVenta(ActionEvent event) {
        if (tblListaProd.getItems().size() > 0) {
            if (!detalle.getText().isEmpty()) {
                if (Alerta.mostrarAlertConfirmation("Seguro que desea generar el comprobante?") == 1) {
                    guardar(event);
                    genera();
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    lw.loadStage("/view/viewContabilidad.fxml", event, 1280, 720, stage);
                }
            } else {
                Alerta.mostrarAlertWarning("Agregue el consepto de la nota!!");
            }
        } else {
            Alerta.mostrarAlertWarning("No hay PRODUCTOS agregados!!");
        }
    }

    public void guardar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        String[] usuario = stage.getTitle().split("\\s+");

        Sentencias.crud("Call guardarTransaccion('" + 4 + "','select now()','"
                + Sentencias.retornaClienteFactura("Call retornaClienteFactura('" + facFinal + "');") + "','"
                + Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');")
                + "','"
                + "N/A" + "','"
                + retornaSubTotal0() + "','"
                + retornaSubTotal15() + "','"
                + retornaSubTotal18() + "','"
                + retornaImpuesto15() + "','"
                + retornaImpuesto18() + "','"
                + retornaDescuento() + "','"
                + "0.00" + "','"
                + retornaValorReal(laTotal.getText().replace(",", "")) + "','" + 0.00 + "','"
                + 0.00 + "','" + "N/A"
                + "','" + "N/A" + "','"
                + NumerosLetras.Convertir(retornaValorReal(laTotal.getText().replace(",", "")), true) + "','"
                + detalle.getText() + "','"
                + 1 + "');");

        for (int i = 0; i < listaProd.size(); i++) {
            double isv = 0.0;
            double desc = 0.0;
            isv += ((listaProdAux.get(i).getPcosto()
                    + listaProdAux.get(i).getPcosto() * (listaProdAux.get(i).getUtilidad() / 100))
                    * (listaProdAux.get(i).getImpuesto() / 100)) * listaProd.get(i).getCantidad();

            desc += ((listaProdAux.get(i).getPcosto()
                    + listaProdAux.get(i).getPcosto() * (listaProdAux.get(i).getUtilidad() / 100))
                    * (listaProdAux.get(i).getDescuento() / 100)) * listaProd.get(i).getCantidad();

            Sentencias.crud("Call guardarTransaccionDetalle('"
                    + Sentencias.retornaUltimaTransaccion("Call retornaUltimaTransaccion();") + "','" + (i + 1) + "','"
                    + listaProd.get(i).getCodigo().toString() + "','" + listaProd.get(i).getNombre() + "','"
                    + listaProd.get(i).getCantidad() + "','" + listaProdAux.get(i).getPcosto() + "','"
                    + listaProd.get(i).getCostoUtili() + "','" + isv + "','" + desc + "','"
                    + listaProd.get(i).getValor()
                    + "');");
        }

        Sentencias.crud("Call guardarNotaDebito('" + facFinal + "','"
                + Sentencias.retornaUltimaTransaccion("Call retornaUltimaTransaccion();") + "','" + 1 + "');");

        Sentencias.crud("Call crearRegistroBitacora('Se genero nota de debito','CONTABILIDAD','"
                + Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');")
                + "')");

        clear();
        Alerta.mostrarAlertInfo("Transacción completada!");
    }

    public String retornaValorReal(String val) {
        String[] arre = val.split("\\s+");
        return arre[0];
    }

    public void genera() {
        String notaCode = Sentencias.retornaNotaCreditoNum("Call retornaNotaDebitoNum();");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("code", notaCode);
        GenerarReporte jasper = new GenerarReporte();
        jasper.verReporte("/Reportes/NotaDebito.jasper", parameters);
    }

    public void clear() {
        buscar.clear();
        laDescuento.setText("0.00 Lps");
        laImpuesto.setText("0.00 Lps");
        laSubtotal.setText("0.00 Lps");
        laTotal.setText("0.00 Lps");
        detalle.clear();
        stock.clear();
        tblListaProd.getItems().clear();
        listaProd.clear();
        listaProdConFormato.clear();
        listaProdAux.clear();
    }

    public String retornaSubTotal() {
        double sTotal = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            sTotal += Double.parseDouble(listaProd.get(i).getValor());
        }
        return df.format(sTotal);
    }

    public String retornaSubTotalConFormato() {
        double sTotal = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            sTotal += Double.parseDouble(listaProd.get(i).getValor());
        }
        return df2.format(sTotal);
    }

    public double retornaSubTotal0() {
        double sTotal = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            if (listaProdAux.get(i).getImpuesto() == 0.00) {
                sTotal += Double.parseDouble(listaProd.get(i).getValor());
            }
        }
        return Double.parseDouble(df.format(sTotal));
    }

    public double retornaSubTotal15() {
        double sTotal = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            if (listaProdAux.get(i).getImpuesto() == 15.00) {
                sTotal += Double.parseDouble(listaProd.get(i).getValor());
            }
        }
        return Double.parseDouble(df.format(sTotal));
    }

    public double retornaSubTotal18() {
        double sTotal = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            if (listaProdAux.get(i).getImpuesto() == 18.00) {
                sTotal += Double.parseDouble(listaProd.get(i).getValor());
            }
        }
        return Double.parseDouble(df.format(sTotal));
    }

    public double retornaTotal() {
        double gTotal = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            gTotal += listaProdAux.get(i).getPventa() * listaProd.get(i).getCantidad();
        }
        return Double.parseDouble(df.format(gTotal));
    }

    public String retornaTotalCompra() {
        double gTotal = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            gTotal += listaProdAux.get(i).getPcosto() * listaProd.get(i).getCantidad();
        }
        return df.format(gTotal);
    }

    public String retornaTotalCompraConFormato() {
        double gTotal = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            gTotal += listaProdAux.get(i).getPcosto() * listaProd.get(i).getCantidad();
        }
        return df2.format(gTotal);
    }

    public String retornaImpuesto() {
        double isv = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            isv += ((listaProdAux.get(i).getPcosto()
                    + listaProdAux.get(i).getPcosto() * (listaProdAux.get(i).getUtilidad() / 100))
                    * (listaProdAux.get(i).getImpuesto() / 100)) * listaProd.get(i).getCantidad();
        }
        return df.format(isv);
    }

    public String retornaImpuestoConFormato() {
        double isv = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            isv += ((listaProdAux.get(i).getPcosto()
                    + listaProdAux.get(i).getPcosto() * (listaProdAux.get(i).getUtilidad() / 100))
                    * (listaProdAux.get(i).getImpuesto() / 100)) * listaProd.get(i).getCantidad();
        }
        return df2.format(isv);
    }

    public double retornaImpuesto15() {
        double isv = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            if (listaProdAux.get(i).getImpuesto() == 15.00) {
                isv += ((listaProdAux.get(i).getPcosto()
                        + listaProdAux.get(i).getPcosto() * (listaProdAux.get(i).getUtilidad() / 100))
                        * (listaProdAux.get(i).getImpuesto() / 100)) * listaProd.get(i).getCantidad();
            }
        }
        return Double.parseDouble(df.format(isv));
    }

    public double retornaImpuesto18() {
        double isv = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            if (listaProdAux.get(i).getImpuesto() == 18.00) {
                isv += ((listaProdAux.get(i).getPcosto()
                        + listaProdAux.get(i).getPcosto() * (listaProdAux.get(i).getUtilidad() / 100))
                        * (listaProdAux.get(i).getImpuesto() / 100)) * listaProd.get(i).getCantidad();
            }
        }
        return Double.parseDouble(df.format(isv));
    }

    public double retornaDescuento() {
        double desc = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {

            desc += ((listaProdAux.get(i).getPcosto()
                    + listaProdAux.get(i).getPcosto() * (listaProdAux.get(i).getUtilidad() / 100))
                    * (listaProdAux.get(i).getDescuento() / 100)) * listaProd.get(i).getCantidad();
            // System.out.println(desc);
        }

        return Double.parseDouble(df.format(desc));
    }

    public String retornaDescuentoConFormato() {
        double desc = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {

            desc += ((listaProdAux.get(i).getPcosto()
                    + listaProdAux.get(i).getPcosto() * (listaProdAux.get(i).getUtilidad() / 100))
                    * (listaProdAux.get(i).getDescuento() / 100)) * listaProd.get(i).getCantidad();
            // System.out.println(desc);
        }

        return df2.format(desc);
    }

    private void llenaCamposProducto(String sql) {
        String code = coincidenciasAux.getItems().get(coincidencias.getSelectionModel().getSelectedIndex()) + "";
        ArrayList<Producto> productos = Sentencias.retornaProductos(sql);
        Producto producto = new Producto();
        for (int i = 0; i < productos.size(); i++) {
            producto = productos.get(i);

            if (code.equals(producto.getCodigo())) {
                if (producto.getMedida().equals("Unidad")) {
                    cantidadProd = Alerta.retornaCantidadUnidad(producto.getNombre());
                } else if (producto.getMedida().equals("Libra")) {
                    cantidadProd = Alerta.retornaCantidadLibra(producto.getNombre());
                }
                if (!cantidadProd.equals("0") && !cantidadProd.equals("") && !cantidadProd.equals(".")) {
                    if (Sentencias
                            .retornaStock("Call retornaStockUnProducto('" + producto.getCodigo() + "')") >= Double
                                    .parseDouble(cantidadProd)) {
                        if (cuentaProductos(producto.getCodigo()) < Sentencias
                                .retornaStock("Call retornaStockUnProducto('" + producto.getCodigo() + "')")
                                && (cuentaProductos(producto.getCodigo())
                                        + Double.parseDouble(cantidadProd)) <= Sentencias.retornaStock(
                                                "Call retornaStockUnProducto('" + producto.getCodigo() + "')")) {
                            listaProdAux.add(producto);

                            double precioUnit = Double.parseDouble(df.format(producto.getPcosto()))
                                    + Double.parseDouble(
                                            df.format((producto.getPcosto() * (producto.getUtilidad() / 100))));
                            double valor = Double.parseDouble(df.format(Double.parseDouble(cantidadProd)))
                                    * Double.parseDouble(df.format(precioUnit));

                            String precioU = df.format(precioUnit);
                            String valFil = df.format(valor);

                            String precioUCF = df2.format(precioUnit);
                            String valFilCF = df2.format(valor);

                            listaProd.add(new Producto(producto.getCodigo(), producto.getNombre(), precioU,
                                    Double.parseDouble(cantidadProd), valFil));

                            listaProdConFormato.add(new Producto(producto.getCodigo(), producto.getNombre(), precioUCF,
                                    Double.parseDouble(cantidadProd), valFilCF));

                            stock.setText(
                                    Sentencias
                                            .retornaStock("Call retornaStockUnProducto('" + producto.getCodigo() + "')")
                                            + "");
                            buscar.selectAll();
                            return;
                        } else {
                            Alerta.mostrarAlertWarning(
                                    "La cantidad de este producto a excedido el límite disponible en el inventario!!");
                        }
                    } else {
                        Alerta.mostrarAlertWarning("Stock insuficiente! \n Disponible: "
                                + Sentencias
                                        .retornaStock("Call retornaStockUnProducto('" + producto.getCodigo() + "')"));
                        stock.setText(
                                Sentencias.retornaStock("Call retornaStockUnProducto('" + producto.getCodigo() + "')")
                                        + "");
                    }
                }
            }
        }
    }

    public double cuentaProductos(String codigo) {
        double cont = 0;
        for (int i = 0; i < listaProd.size(); i++) {
            if (codigo.equals(listaProd.get(i).getCodigo())) {
                cont += listaProd.get(i).getCantidad();
            }
        }
        return cont;
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
                    Alerta.mostrarAlertWarning("No se encontro el numuero de factura ingresado!");
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

    public void campos() {
        buscar.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letrasNumeros(event, buscar, true, 15));
    }

    public void focus() {
        categorias.setFocusTraversable(false);
        btnQuitar.setFocusTraversable(false);
        tblListaProd.setFocusTraversable(false);
        stock.setFocusTraversable(false);
        btnBuscar.setFocusTraversable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        detecta();
        datosTabla();
        laAtras.setGraphic(new ImageView("/Images/back.png"));
        campos();
        focus();
    }

}
