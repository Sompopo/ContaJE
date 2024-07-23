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
import first.loadWindow2;
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
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Producto;

public class comprarController implements Initializable {

    private ComboBox coincidenciasAux;
    private static ObservableList<Producto> listaProd = FXCollections.observableArrayList();
    private static ObservableList<Producto> listaProdConFormato = FXCollections.observableArrayList();
    private static ObservableList<Producto> listaProdAux = FXCollections.observableArrayList();
    private loadWindow3 lw = new loadWindow3();
    private loadWindow2 lw2 = new loadWindow2();
    private static String[] nombre;
    private NumberFormat df = new DecimalFormat("#0.00");
    private NumberFormat df2 = new DecimalFormat("#,##0.00");

    @FXML
    private VBox boxUno;

    @FXML
    private VBox boxDos;

    @FXML
    private HBox boxPrincipal;

    @FXML
    private Spinner spCantidad;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnImprimirFactura;

    @FXML
    private Button btnQuitar;

    @FXML
    private Button btnRefresh;

    @FXML
    private TextField buscar;

    @FXML
    private TextField stock;

    @FXML
    private ComboBox<String> categorias;

    @FXML
    private TextField cliente;

    @FXML
    private ComboBox<String> coincidencias;

    @FXML
    private ComboBox<String> forma;

    @FXML
    private ComboBox<String> tipo;

    @FXML
    private Label laAtras;

    @FXML
    private TextField pago;

    @FXML
    private Label laCambio;

    @FXML
    private Label laDescuento;

    @FXML
    private Label laImpuesto;

    @FXML
    private Label laSubtotal;

    @FXML
    private Label laTotal;

    @FXML
    private Label laFPago;

    @FXML
    private Label laTPago;

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
    void clienteOnMouseClicked(MouseEvent event) {
        Object evt = event.getSource();

        if (evt.equals(cliente)) {
            cliente.setText("");
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            lw2.loadStage("/view/viewAgregarCliente.fxml", event, 600, 400, stage.getTitle());
        }
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
                laSubtotal.setText(retornaSubTotal() + " Lps");
                laImpuesto.setText(retornaImpuesto() + " Lps");
                laDescuento.setText(retornaDescuento() + " Lps");
                laTotal.setText(retornaTotal() - retornaDescuento() + " Lps");
            }

        }
    }

    @FXML
    void pagoOnActionEvent(ActionEvent event) {
        Object avt = event.getSource();

        if (avt.equals(pago)) {
            retornaCambio();
        }
    }

    @FXML
    void laAtrasMouseClicked(MouseEvent event) {
        Object evt = event.getSource();

        if (evt.equals(laAtras)) {
            listaProd.clear();
            listaProdAux.clear();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
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

        laFPago.setVisible(false);
        laTPago.setVisible(false);
        forma.setVisible(false);
        tipo.setVisible(false);

        coincidenciasAux = new ComboBox<>();
        nombre = null;

        tblListaProd.setStyle("-fx-font: 18px \"System\";");
        columPrecio.setStyle("-fx-alignment: CENTER-RIGHT;");
        columCantidad.setStyle("-fx-alignment: CENTER-RIGHT;");
        columValor.setStyle("-fx-alignment: CENTER-RIGHT;");

        coincidencias.setStyle("-fx-font: 18px \"System\";");

        forma.setStyle("-fx-font: 18px \"System\";");
        forma.getItems().add("EFECTIVO");
        forma.getItems().add("TARJETA");
        forma.getItems().add("CHEQUÉ");
        forma.getItems().add("DEPÓSITO");

        tipo.setStyle("-fx-font: 18px \"System\";");
        tipo.getItems().add("CONTADO");
        tipo.getItems().add("CRÉDITO");

        categorias.setStyle("-fx-font: 18px \"System\";");
        Sentencias.llenaComboBox("call retornaCategorias()", categorias, new ComboBox());
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

        spCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999, 1));

        btnBuscar.setOnAction(event -> {
            Sentencias.buscarProd2("call retornaProductosActivos()", buscar.getText(), coincidencias, coincidenciasAux);
            coincidencias.getSelectionModel().select(0);
            System.out.print("rellenarrrrrr");
            if (coincidencias.getSelectionModel().getSelectedItem() != null && coincidencias.getItems().size() == 1) {
                llenaCamposProducto("CALL retornaProductos()");
                tblListaProd.setItems(listaProdConFormato);
                laSubtotal.setText(retornaSubTotal() + " Lps");
                laImpuesto.setText(retornaImpuesto() + " Lps");
                laDescuento.setText(retornaDescuento() + " Lps");
                laTotal.setText(retornaTotal() - retornaDescuento() + " Lps");
            }
        });

        btnAgregar.setOnAction(event -> {
            if (coincidencias.getSelectionModel().getSelectedItem() != null) {
                llenaCamposProducto("CALL retornaProductos()");
                tblListaProd.setItems(listaProdConFormato);
                laSubtotal.setText(retornaSubTotal() + " Lps");
                laImpuesto.setText(retornaImpuesto() + " Lps");
                laDescuento.setText(retornaDescuento() + " Lps");
                laTotal.setText(retornaTotal() - retornaDescuento() + " Lps");
            }

        });

        btnQuitar.setOnAction(event -> {
            int aux = tblListaProd.getSelectionModel().getSelectedIndex();
            if (aux != -1) {
                listaProd.remove(aux);
                listaProdConFormato.remove(aux);
                listaProdAux.remove(aux);
                tblListaProd.setItems(listaProdConFormato);
                laSubtotal.setText(retornaSubTotal() + " Lps");
                laTotal.setText(retornaTotal() - retornaDescuento() + " Lps");
                laDescuento.setText(retornaDescuento() + " Lps");
                laImpuesto.setText(retornaImpuesto() + " Lps");
                laCambio.setText("0.0 Lps");
                pago.setText(" Lps");
            }
        });

        btnImprimirFactura.setOnAction(event -> {
            if (tblListaProd.getItems().size() > 0) {
                if (Alerta.mostrarAlertConfirmation(
                        "¿Seguro que desea completar la transacción?") == 1) {
                    guardar(event);
                    genera();
                }
            } else {
                Alerta.mostrarAlertWarning("No hay PRODUCTOS agregados!!");
            }
        });

        btnRefresh.setOnAction(event -> {
            if (nombre != null) {
                cliente.setText(nombre[1] + " " + nombre[2]);
            }
        });
    }

    public void guardar(ActionEvent event) {

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        String[] usuario = stage.getTitle().split("\\s+");

        Sentencias.crud("Call guardarTransaccion('" + 2 + "','select now()','" + "N/A" + "','"
                + Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');") + "','"
                + "N/A" + "','"
                + retornaSubTotal0() + "','"
                + retornaSubTotal15() + "','"
                + retornaSubTotal18() + "','"
                + retornaImpuesto15() + "','"
                + retornaImpuesto18() + "','"
                + retornaValorReal(laDescuento.getText()) + "','"
                + "0.0" + "','"
                + retornaValorReal(laTotal.getText()) + "','" + retornaValorReal(laTotal.getText()) + "','"
                + retornaValorReal(laCambio.getText()) + "','" + "CONTADO"
                + "','" + "EFECTIVO" + "','"
                + NumerosLetras.Convertir(df.format(retornaTotal() - retornaDescuento()), true) + "','" + "Compra de productos."
                + "','" + 1
                + "');");

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

        Sentencias.crud("Call guardarRecibo('" + Sentencias.retornaUltimaTransaccion("Call retornaUltimaTransaccion();")
                + "');");

        Sentencias.crud("Call crearRegistroBitacora('Se realizo una transaccion de compra','INVENTARIO','"
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
        String code = Sentencias.retornaUltimoRecibo("Call retornaUltimoRecibo();");
        Map parameters = new HashMap();
        parameters.put("code", code);
        GenerarReporte jasper = new GenerarReporte("/Reportes/ReciboCompra.jasper", parameters);
    }

    public void clear() {
        buscar.clear();
        laCambio.setText("0.0 Lps");
        laDescuento.setText("0.0 Lps");
        laImpuesto.setText("0.0 Lps");
        laDescuento.setText("0.0 Lps");
        laSubtotal.setText("0.0 Lps");
        laTotal.setText("0.0 Lps");
        cliente.clear();
        pago.clear();
        stock.clear();
        tblListaProd.getItems().clear();
        listaProd.clear();
        listaProdConFormato.clear();
        listaProdAux.clear();
    }

    public void retornaCambio() {
        if (tblListaProd.getItems().size() > 0) {
            if (!pago.getText().isEmpty()) {
                String aux = laTotal.getText();
                String[] arre = aux.split("\\s+");
                if (Double.parseDouble(pago.getText()) >= Double.parseDouble(arre[0])) {
                    laCambio.setText((Double.parseDouble(pago.getText()) - Double.parseDouble(arre[0])) + " Lps");
                } else {
                    Alerta.mostrarAlertWarning("El pago debe ser IGUAL o MAYOR al total!!");
                }
            } else {
                Alerta.mostrarAlertWarning("Agregue el PAGO!!");
            }
        } else {
            Alerta.mostrarAlertWarning("No hay PRODUCTOS agregados!!");
        }
    }

    public double retornaSubTotal() {
        double sTotal = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            sTotal += Double.parseDouble(listaProd.get(i).getValor());
        }
        return sTotal;
    }

    public double retornaSubTotal0() {
        double sTotal = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            if (listaProdAux.get(i).getImpuesto() == 0.00) {
                sTotal += Double.parseDouble(listaProd.get(i).getValor());
            }
        }
        return sTotal;
    }

    public double retornaSubTotal15() {
        double sTotal = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            if (listaProdAux.get(i).getImpuesto() == 15.00) {
                sTotal += Double.parseDouble(listaProd.get(i).getValor());
            }
        }
        return sTotal;
    }

    public double retornaSubTotal18() {
        double sTotal = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            if (listaProdAux.get(i).getImpuesto() == 18.00) {
                sTotal += Double.parseDouble(listaProd.get(i).getValor());
            }
        }
        return sTotal;
    }

    public double retornaTotal() {
        double gTotal = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            gTotal += listaProdAux.get(i).getPventa() * listaProd.get(i).getCantidad();
        }
        return gTotal;
    }

    public double retornaImpuesto() {
        double isv = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            isv += ((listaProdAux.get(i).getPcosto()
                    + listaProdAux.get(i).getPcosto() * (listaProdAux.get(i).getUtilidad() / 100))
                    * (listaProdAux.get(i).getImpuesto() / 100)) * listaProd.get(i).getCantidad();
        }
        return isv;
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
        return isv;
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
        return isv;
    }

    public double retornaDescuento() {
        double desc = 0.0;
        for (int i = 0; i < listaProd.size(); i++) {
            desc += ((listaProdAux.get(i).getPcosto()
                    + listaProdAux.get(i).getPcosto() * (listaProdAux.get(i).getUtilidad() / 100))
                    * (listaProdAux.get(i).getDescuento() / 100)) * listaProd.get(i).getCantidad();
        }
        return desc;
    }

    private void llenaCamposProducto(String sql) {
        String code = coincidenciasAux.getItems().get(coincidencias.getSelectionModel().getSelectedIndex()) + "";
        ArrayList<Producto> productos = Sentencias.retornaProductos(sql);
        Producto producto = new Producto();
        for (int i = 0; i < productos.size(); i++) {
            producto = productos.get(i);

            if (code.equals(producto.getCodigo())) {

                listaProdAux.add(producto);

                double precioUnit = Double.parseDouble(df.format(producto.getPcosto()))
                        + Double.parseDouble(df.format((producto.getPcosto() * (producto.getUtilidad() / 100))));
                double valor = Double.parseDouble(df.format(Double.parseDouble(spCantidad.getValue().toString())))
                        * Double.parseDouble(df.format(precioUnit));

                String precioU = df.format(precioUnit);
                String valFil = df.format(valor);

                String precioUCF = df2.format(precioUnit);
                String valFilCF = df2.format(valor);

                listaProd.add(new Producto(producto.getCodigo(), producto.getNombre(), precioU,
                        Double.parseDouble(spCantidad.getValue().toString()), valFil));

                listaProdConFormato.add(new Producto(producto.getCodigo(), producto.getNombre(), precioUCF,
                        Double.parseDouble(spCantidad.getValue().toString()), valFilCF));

                stock.setText(Sentencias.retornaStock("Call retornaStockUnProducto('" + producto.getCodigo() + "')")
                        + "");
                return;

            }
        }
    }

    public static void setCliente(String[] name) {
        nombre = name;
    }

    public void campos() {
        buscar.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letrasNumeros(event, buscar, true, 15));
        cliente.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letras(event, cliente, true, 15));
        pago.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.decimales(event, pago, 15));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boxPrincipal.getChildren().clear();
        boxPrincipal.getChildren().add(boxUno);
        detecta();
        datosTabla();
        laAtras.setGraphic(new ImageView("/Images/back.png"));
        campos();
    }
}