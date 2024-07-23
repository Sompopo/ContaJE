package controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Producto;

public class cajaController implements Initializable {

    private ComboBox<String> coincidenciasAux;
    private static ObservableList<Producto> listaProd = FXCollections.observableArrayList();
    private static ObservableList<Producto> listaProdConFormato = FXCollections.observableArrayList();
    private static ObservableList<Producto> listaProdAux = FXCollections.observableArrayList();
    private loadWindow3 lw = new loadWindow3();
    private loadWindow2 lw2 = new loadWindow2();
    private static String[] nombre;
    private String cantidadProd;
    private NumberFormat df = new DecimalFormat("#0.00");
    private NumberFormat df2 = new DecimalFormat("#,##0.00");

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
    private Button btnRefresh;

    @FXML
    private Button btnVerFactura;

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
        if (event.getCode() == KeyCode.CONTROL) {
            if (tblListaProd.getItems().size() > 0) {
                forma.setValue(Alerta.formaPago());
                String tipoPago = Alerta.tipoPago();
                if (tipoPago.equals("CONTADO")) {
                    tipo.setValue(tipoPago);
                    pago.setText(Alerta.retornaPago(laTotal.getText()));
                    retornaCambio();
                    btnImprimirFactura.requestFocus();
                } else {
                    tipo.setValue(tipoPago);
                    pago.setText("0.00");
                    laCambio.setText("0.00 Lps");
                    btnImprimirFactura.requestFocus();
                }
            } else {
                Alerta.mostrarAlertWarning("No hay PRODUCTOS agregados!!");
            }
        }
    }

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
                laTotal.setText(df2.format(retornaTotal() - Double.parseDouble(retornaDescuentoSinFormato())) + " Lps");
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
            listaProdConFormato.clear();
            listaProdAux.clear();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            // String[] arre = stage.getTitle().split("\\s+");
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

    public void validaRangoFacturacion() {
        String num = Sentencias.retornaUltimaFactura("Call retornaUltimaFactura();");
        String[] arre = num.split("-");
        String val = arre[0] + arre[1] + arre[2] + arre[3];
        if (val.equals(Sentencias.retornaUltimaFactura("Call retornaRangoMaxFac();"))) {
            btnImprimirFactura.setTooltip(new Tooltip("Rango máximo de facturación alcanzado!!"));
            btnImprimirFactura.setDisable(true);
        }
    }

    public <T> void detecta() {

        coincidenciasAux = new ComboBox<>();
        nombre = null;

        tblListaProd.setStyle("-fx-font: 18px \"System\";");
        columPrecio.setStyle( "-fx-alignment: CENTER-RIGHT;");
        columCantidad.setStyle( "-fx-alignment: CENTER-RIGHT;");
        columValor.setStyle( "-fx-alignment: CENTER-RIGHT;");

        coincidencias.setStyle("-fx-font: 18px \"System\";");

        forma.setStyle("-fx-font: 18px \"System\";");
        forma.getItems().add("EFECTIVO");
        forma.getItems().add("TARJETA");
        forma.getItems().add("CHEQUÉ");
        forma.getItems().add("DEPÓSITO");

        tipo.setStyle("-fx-font: 18px \"System\";");
        tipo.getItems().add("CONTADO");
        tipo.getItems().add("CRÉDITO");
        tipo.setOnAction(event -> {
            if(tipo.getSelectionModel().getSelectedIndex() != -1){
            if (tipo.getSelectionModel().getSelectedItem().equals("CONTADO") ) {
                cliente.setText("Consumidor Final");
                pago.setDisable(false);
            } else if (tipo.getSelectionModel().getSelectedItem().equals("CRÉDITO")) {
                cliente.setText("");
                pago.setDisable(true);
            }
        }
        });

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
            buscar.selectAll();
        });

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
                laTotal.setText(df2.format(retornaTotal() - Double.parseDouble(retornaDescuentoSinFormato())) + " Lps");
            }
        });

        btnAgregar.setOnAction(event -> {
            if (coincidencias.getSelectionModel().getSelectedItem() != null) {
                llenaCamposProducto("CALL retornaProductos()");
                tblListaProd.setItems(listaProdConFormato);
                laSubtotal.setText(retornaSubTotal() + " Lps");
                laImpuesto.setText(retornaImpuesto() + " Lps");
                laDescuento.setText(retornaDescuento() + " Lps");
                laTotal.setText(df2.format(retornaTotal() - Double.parseDouble(retornaDescuentoSinFormato())) + " Lps");
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
                laTotal.setText(df2.format(retornaTotal() - Double.parseDouble(retornaDescuentoSinFormato())) + " Lps");
                laDescuento.setText(retornaDescuento() + " Lps");
                laImpuesto.setText(retornaImpuesto() + " Lps");
                laCambio.setText("0.00 Lps");
                pago.clear();;
            }
        });

        btnVerFactura.setOnAction(event -> {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

            Date ahora = new Date(Calendar.getInstance().getTimeInMillis());

            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            String[] usuario = stage.getTitle().split("\\s+");

            if (Alerta.mostrarAlertConfirmation("Seguro que desea cerrar la caja?") == 1) {
                String[] infoCaja = Sentencias.retornaAperturaCajaActiva("Call retornaAperturaCajaActiva()");
                double valorInicialCaja = Double.parseDouble(infoCaja[1]);
                double facturadoEfectivo = Sentencias
                        .retornaEfectivoCaja("Call retornaEfectivoCaja('" + infoCaja[2]
                                + "','" + formatter2.format(ahora) + " 23:59:59" + "')");

                Alerta.mostrarAlertInfo("CIERRE DE CAJA\n\n-CAJA ABIERTA A LAS: " + infoCaja[2]
                        + "\n-CAJA CERRADA A LAS: " + formatter.format(ahora) + "\n\n-FACTURADO EN EFECTIVO: L "
                        + df2.format(facturadoEfectivo)
                        + "\n-VALOR INICIAL DE LA CAJA: L " + df2.format(valorInicialCaja) + "\n-TOTAL, DE EFECTIVO EN LA CAJA: L "
                        + df2.format((valorInicialCaja + facturadoEfectivo))
                        + "\n\n-USUARIO ACTIVO: " + usuario[usuario.length - 1]);

                genera2(infoCaja[2], formatter.format(ahora), Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');")+"");

                Sentencias.crud("Call modificarAperturaCaja('" + formatter.format(ahora) + "','" + infoCaja[0] + "')");
                Sentencias.crud("Call crearRegistroBitacora('Se cerro la caja','CAJA','"+Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');")+"')");

                lw.loadStage("/view/viewInicio.fxml", event, 1280, 720, stage);
            }
        });

        btnImprimirFactura.setOnAction(event -> {
            finalizarVenta(event);
        });

        btnRefresh.setOnAction(event -> {
            if (nombre != null) {
                cliente.setText(nombre[1] + " " + nombre[2]);
            }
        });
    }

    public void finalizarVenta(ActionEvent event) {
        if (tblListaProd.getItems().size() > 0) {
            if (forma.getSelectionModel().getSelectedItem() != null) {
                if (tipo.getSelectionModel().getSelectedItem() != null) {
                    if (!cliente.getText().isEmpty()) {
                        if (validaFechaMaxFacturacion()) {
                            if (tipo.getSelectionModel().getSelectedItem().equals("CONTADO")) {
                                if (!pago.getText().isEmpty()) {
                                    // String aux = laTotal.getText();
                                    // String[] arre = aux.split("\\s+");
                                    if (Double.parseDouble(pago.getText()) >= (retornaTotal()
                                            - Double.parseDouble(retornaDescuentoSinFormato()))) {
                                        laCambio.setText(
                                                (df2.format(Double.parseDouble(pago.getText())
                                                        - (retornaTotal()
                                                                - Double.parseDouble(retornaDescuentoSinFormato()))))
                                                        + " Lps");
                                        if (Alerta.mostrarAlertConfirmation(
                                                "¿Seguro que desea completar la transacción?") == 1) {
                                            String val = "";
                                            if (Sentencias.verificaFactura("Call verificafactura();") != 0) {
                                                String num = Sentencias
                                                        .retornaUltimaFactura("Call retornaUltimaFactura();");
                                                String[] arre2 = num.split("-");
                                                val = arre2[0] + arre2[1] + arre2[2] + arre2[3];
                                            }
                                            if (!val.equals(
                                                    Sentencias
                                                            .retornaUltimaFactura("Call retornaRangoMaxFac();"))) {
                                                guardar(event);
                                                genera();
                                            } else {
                                                Alerta.mostrarAlertWarning(
                                                        "Rango máximo de facturación alcanzado!!");
                                            }
                                        }
                                    } else {
                                        Alerta.mostrarAlertWarning("El pago debe ser IGUAL o MAYOR al total!!");
                                    }
                                } else {
                                    Alerta.mostrarAlertWarning("Agregue el PAGO!!");
                                }
                            } else if (tipo.getSelectionModel().getSelectedItem().equals("CRÉDITO")) {
                                if (Alerta.mostrarAlertConfirmation(
                                        "¿Seguro que desea completar la transacción?") == 1) {
                                    String val = "";
                                    if (Sentencias.verificaFactura("Call verificafactura();") != 0) {
                                        String num = Sentencias
                                                .retornaUltimaFactura("Call retornaUltimaFactura();");
                                        String[] arre2 = num.split("-");
                                        val = arre2[0] + arre2[1] + arre2[2] + arre2[3];
                                    }
                                    if (!val.equals(
                                            Sentencias.retornaUltimaFactura("Call retornaRangoMaxFac();"))) {

                                        guardar(event);
                                        genera();
                                    } else {
                                        Alerta.mostrarAlertWarning("Rango máximo de facturación alcanzado!!");
                                    }
                                }
                            }
                        } else {
                            Alerta.mostrarAlertWarning("Fecha máxima de facturación alcanzada!!");
                        }
                    } else {
                        Alerta.mostrarAlertWarning("Agregue un cliente!!");
                    }
                } else {
                    Alerta.mostrarAlertWarning("Seleccione un tipo de pago!!");
                }
            } else {
                Alerta.mostrarAlertWarning("Seleccione una forma de pago!!");
            }
        } else {
            Alerta.mostrarAlertWarning("No hay PRODUCTOS agregados!!");
        }
    }

    public void guardar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        String[] usuario = stage.getTitle().split("\\s+");

        String valAct = "";
        if (tipo.getSelectionModel().getSelectedItem().equals("CONTADO")) {
            retornaCambio();
            valAct = "1";
            if (cliente.getText().equals("Consumidor Final")) {
                String[] arre = { "1", "", "" };
                nombre = arre;
            }
        } else if (tipo.getSelectionModel().getSelectedItem().equals("CRÉDITO")) {
            pago.setText("0.00");
            valAct = "0";

        }
        Sentencias.crud("Call guardarTransaccion('" + 1 + "','select now()','" + nombre[0] + "','"
                + Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');") + "','"
                + "N/A" + "','"
                + retornaSubTotal0() + "','"
                + retornaSubTotal15() + "','"
                + retornaSubTotal18() + "','"
                + retornaImpuesto15() + "','"
                + retornaImpuesto18() + "','"
                + retornaDescuentoSinFormato() + "','"
                + "0.00" + "','"
                + (retornaTotal() - Double.parseDouble(retornaDescuentoSinFormato())) + "','" + pago.getText() + "','"
                + (Double.parseDouble(pago.getText())
                        - (retornaTotal() - Double.parseDouble(retornaDescuentoSinFormato())))
                + "','" + tipo.getSelectionModel().getSelectedItem().toString()
                + "','" + forma.getSelectionModel().getSelectedItem().toString() + "','"
                + NumerosLetras.Convertir(df.format(retornaTotal() - Double.parseDouble(retornaDescuentoSinFormato())) + "",
                        true)
                + "','" + "N/A" + "','"
                + valAct + "');");

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

        if (Sentencias.verificaFactura("Call verificaFactura();") == 0) {
            String init = Sentencias.retornaNumInicioFac("Call retornaInfoFac();");
            Sentencias.crud("Call guardarFactura('" + init + "','"
                    + Sentencias.retornaUltimaTransaccion("Call retornaUltimaTransaccion();") + "','" + 1 + "','" + init
                    + "-" + generarCodigoAleatorio() + "');");

        } else if (Sentencias.verificaFactura("Call verificaFactura();") > 0) {
            String numFac = Sentencias.retornaUltimaFactura("Call retornaUltimaFactura();");
            String[] arre = numFac.split("-");
            int digito1 = Integer.parseInt(arre[3].charAt(7) + "");
            int digito2 = Integer.parseInt(arre[3].charAt(6) + "");
            int digito3 = Integer.parseInt(arre[3].charAt(5) + "");
            int digito4 = Integer.parseInt(arre[3].charAt(4) + "");
            int digito5 = Integer.parseInt(arre[3].charAt(3) + "");
            int digito6 = Integer.parseInt(arre[3].charAt(2) + "");
            int digito7 = Integer.parseInt(arre[3].charAt(1) + "");
            int digito8 = Integer.parseInt(arre[3].charAt(0) + "");

            int todo = Integer.parseInt(arre[3]);

            if (todo < 9) {
                int val = digito1 + 1;
                Sentencias
                        .crud("Call guardarFactura('" + arre[0] + "-" + arre[1] + "-" + arre[2] + "-" + "0000000" + val
                                + "','"
                                + Sentencias.retornaUltimaTransaccion("Call retornaUltimaTransaccion();") + "','" + 1
                                + "','" + arre[0] + "-" + arre[1] + "-" + arre[2] + "-" + "0000000" + val + "-"
                                + generarCodigoAleatorio() + "');");

            } else if (todo < 99) {
                String num = digito2 + "" + digito1;
                int val = Integer.parseInt(num) + 1;
                Sentencias.crud("Call guardarFactura('" + arre[0] + "-" + arre[1] + "-" + arre[2] + "-" + "000000" + val
                        + "','"
                        + Sentencias.retornaUltimaTransaccion("Call retornaUltimaTransaccion();") + "','" + 1 + "','"
                        + arre[0] + "-" + arre[1] + "-" + arre[2] + "-" + "000000" + val + "-"
                        + generarCodigoAleatorio() + "');");

            } else if (todo < 999) {
                String num = digito3 + "" + digito2 + "" + digito1;
                int val = Integer.parseInt(num) + 1;
                Sentencias.crud("Call guardarFactura('" + arre[0] + "-" + arre[1] + "-" + arre[2] + "-" + "00000" + val
                        + "','"
                        + Sentencias.retornaUltimaTransaccion("Call retornaUltimaTransaccion();") + "','" + 1 + "','"
                        + arre[0] + "-" + arre[1] + "-" + arre[2] + "-" + "00000" + val + "-" + generarCodigoAleatorio()
                        + "');");

            } else if (todo < 9999) {
                String num = digito4 + "" + digito3 + "" + digito2 + "" + digito1;
                int val = Integer.parseInt(num) + 1;
                Sentencias.crud("Call guardarFactura('" + arre[0] + "-" + arre[1] + "-" + arre[2] + "-" + "0000" + val
                        + "','"
                        + Sentencias.retornaUltimaTransaccion("Call retornaUltimaTransaccion();") + "','" + 1 + "','"
                        + arre[0] + "-" + arre[1] + "-" + arre[2] + "-" + "0000" + val + "-" + generarCodigoAleatorio()
                        + "');");

            } else if (todo < 99999) {
                String num = digito5 + "" + digito4 + "" + digito3 + "" + digito2 + "" + digito1;
                int val = Integer.parseInt(num) + 1;
                Sentencias.crud("Call guardarFactura('" + arre[0] + "-" + arre[1] + "-" + arre[2] + "-" + "000" + val
                        + "','"
                        + Sentencias.retornaUltimaTransaccion("Call retornaUltimaTransaccion();") + "','" + 1 + "','"
                        + arre[0] + "-" + arre[1] + "-" + arre[2] + "-" + "000" + val + "-" + generarCodigoAleatorio()
                        + "');");

            } else if (todo < 999999) {
                String num = digito6 + "" + digito5 + "" + digito4 + "" + digito3 + "" + digito2 + "" + digito1;
                int val = Integer.parseInt(num) + 1;
                Sentencias.crud("Call guardarFactura('" + arre[0] + "-" + arre[1] + "-" + arre[2] + "-" + "00" + val
                        + "','"
                        + Sentencias.retornaUltimaTransaccion("Call retornaUltimaTransaccion();") + "','" + 1 + "','"
                        + arre[0] + "-" + arre[1] + "-" + arre[2] + "-" + "00" + val + "-" + generarCodigoAleatorio()
                        + "');");

            } else if (todo < 9999999) {
                String num = digito7 + "" + digito6 + "" + digito5 + "" + digito4 + "" + digito3 + "" + digito2 + ""
                        + digito1;
                int val = Integer.parseInt(num) + 1;
                Sentencias.crud("Call guardarFactura('" + arre[0] + "-" + arre[1] + "-" + arre[2] + "-" + "0" + val
                        + "','"
                        + Sentencias.retornaUltimaTransaccion("Call retornaUltimaTransaccion();") + "','" + 1 + "','"
                        + arre[0] + "-" + arre[1] + "-" + arre[2] + "-" + "0" + val + "-" + generarCodigoAleatorio()
                        + "');");

            } else if (todo < 99999999) {
                String num = digito8 + "" + digito7 + "" + digito6 + "" + digito5 + "" + digito4 + "" + digito3 + ""
                        + digito2 + "" + digito1;
                int val = Integer.parseInt(num) + 1;
                Sentencias.crud("Call guardarFactura('" + arre[0] + "-" + arre[1] + "-" + arre[2] + "-" + val + "','"
                        + Sentencias.retornaUltimaTransaccion("Call retornaUltimaTransaccion();") + "','" + 1 + "','"
                        + arre[0] + "-" + arre[1] + "-" + arre[2] + "-" + val + "-" + generarCodigoAleatorio() + "');");
            } else {
                Alerta.mostrarAlertWarning("Se exedio el numero máximo de facturas!!");
            }

        }
        Sentencias.crud("Call crearRegistroBitacora('Se realizo una transacción en caja','CAJA','"
                + Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');")
                + "')");
        Alerta.mostrarAlertInfo("Transacción completada!");
        clear();
    }

    public boolean validaFechaMaxFacturacion() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaMax;
        Date fechaActual;
        try {
            fechaMax = Sentencias.retornaFechaMaxFacturacion("Call retornaInfoFac();");
            fechaActual = dateFormat.parse(LocalDate.now() + "");

            if (fechaMax.after(fechaActual) || fechaMax.equals(fechaActual)) {
                return true;// System.out.println("Fecha valida");
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String retornaValorReal(String val) {
        String[] arre = val.split("\\s+");
        return arre[0];
    }

    public void genera() {
        String numFac = Sentencias.retornaUltimaFactura("Call retornaUltimaFactura();");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("facNum", numFac);
        GenerarReporte jasper = new GenerarReporte();
        jasper.ImprimirDirecto("/Reportes/Factura.jasper", parameters);
    }

    public void genera2(String a, String b, String c) {
        String param1 = a;
        String param2 = b;
        String param3 = c;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("fechaDesde", param1);
        parameters.put("fechaHasta", param2);
        parameters.put("usu", param3);
        GenerarReporte jasper = new GenerarReporte();

        jasper.verReporte("/Reportes/CierreCaja.jasper", parameters);
    }

    public void clear() {
        buscar.clear();
        laCambio.setText("0.00 Lps");
        laDescuento.setText("0.00 Lps");
        laImpuesto.setText("0.00 Lps");
        laDescuento.setText("0.00 Lps");
        laSubtotal.setText("0.00 Lps");
        laTotal.setText("0.00 Lps");
        tipo.getSelectionModel().clearSelection();
        forma.getSelectionModel().clearSelection();
        cliente.clear();
        pago.clear();
        stock.clear();
        tblListaProd.getItems().clear();
        listaProd.clear();
        listaProdConFormato.clear();
        listaProdAux.clear();
        nombre = null;
    }

    public String generarCodigoAleatorio() {
        int min_val = 0;
        int max_val = 9;
        int randomNum = 0;
        String code = "";
        Random ran = new Random();
        for (int i = 0; i < 5; i++) {
            randomNum = ran.nextInt(max_val) + min_val;
            code += randomNum;
        }
        return code;
    }

    public void retornaCambio() {
        if (tblListaProd.getItems().size() > 0) {
            if (!pago.getText().isEmpty()) {
                // String aux = laTotal.getText();
                // String[] arre = aux.split("\\s+");
                if (Double.parseDouble(
                        pago.getText()) >= (retornaTotal() - Double.parseDouble(retornaDescuentoSinFormato()))) {
                    laCambio.setText((df2.format(Double.parseDouble(pago.getText())
                            - (retornaTotal() - Double.parseDouble(retornaDescuentoSinFormato())))) + " Lps");
                } else {
                    Alerta.mostrarAlertWarning("El pago debe ser IGUAL o MAYOR al total!!");
                    laCambio.setText("0.00 Lps");
                }
            } else {
                Alerta.mostrarAlertWarning("Agregue el PAGO!!");
            }
        } else {
            Alerta.mostrarAlertWarning("No hay PRODUCTOS agregados!!");
        }
    }

    public String retornaSubTotal() {
        double sTotal = 0.00;
        for (int i = 0; i < listaProd.size(); i++) {
            sTotal += Double.parseDouble(listaProd.get(i).getValor());
        }
        return df2.format(sTotal);
    }

    public String retornaSubTotalSinFormato() {
        double sTotal = 0.00;
        for (int i = 0; i < listaProd.size(); i++) {
            sTotal += Double.parseDouble(listaProd.get(i).getValor());
        }
        return df.format(sTotal);
    }

    public double retornaSubTotal0() {
        double sTotal = 0.00;
        for (int i = 0; i < listaProd.size(); i++) {
            if (listaProdAux.get(i).getImpuesto() == 0.00) {
                sTotal += Double.parseDouble(listaProd.get(i).getValor());
            }
        }
        return Double.parseDouble(df.format(sTotal));
    }

    public double retornaSubTotal15() {
        double sTotal = 0.00;
        for (int i = 0; i < listaProd.size(); i++) {
            if (listaProdAux.get(i).getImpuesto() == 15.00) {
                sTotal += Double.parseDouble(listaProd.get(i).getValor());
            }
        }
        return Double.parseDouble(df.format(sTotal));
    }

    public double retornaSubTotal18() {
        double sTotal = 0.00;
        for (int i = 0; i < listaProd.size(); i++) {
            if (listaProdAux.get(i).getImpuesto() == 18.00) {
                sTotal += Double.parseDouble(listaProd.get(i).getValor());
            }
        }
        return Double.parseDouble(df.format(sTotal));
    }

    public double retornaTotal() {
        double gTotal = 0.00;
        for (int i = 0; i < listaProd.size(); i++) {
            gTotal += listaProdAux.get(i).getPventa() * listaProd.get(i).getCantidad();
        }
        return Double.parseDouble(df.format(gTotal));
    }

    public String retornaImpuesto() {
        double isv = 0.00;
        for (int i = 0; i < listaProd.size(); i++) {
            isv += ((listaProdAux.get(i).getPcosto()
                    + listaProdAux.get(i).getPcosto() * (listaProdAux.get(i).getUtilidad() / 100))
                    * (listaProdAux.get(i).getImpuesto() / 100)) * listaProd.get(i).getCantidad();
        }
        return df2.format(isv);
    }

    public String retornaImpuestoSinFormato() {
        double isv = 0.00;
        for (int i = 0; i < listaProd.size(); i++) {
            isv += ((listaProdAux.get(i).getPcosto()
                    + listaProdAux.get(i).getPcosto() * (listaProdAux.get(i).getUtilidad() / 100))
                    * (listaProdAux.get(i).getImpuesto() / 100)) * listaProd.get(i).getCantidad();
        }
        return df.format(isv);
    }

    public double retornaImpuesto15() {
        double isv = 0.00;
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
        double isv = 0.00;
        for (int i = 0; i < listaProd.size(); i++) {
            if (listaProdAux.get(i).getImpuesto() == 18.00) {
                isv += ((listaProdAux.get(i).getPcosto()
                        + listaProdAux.get(i).getPcosto() * (listaProdAux.get(i).getUtilidad() / 100))
                        * (listaProdAux.get(i).getImpuesto() / 100)) * listaProd.get(i).getCantidad();
            }
        }
        return Double.parseDouble(df.format(isv));
    }

    public String retornaDescuento() {
        double desc = 0.00;
        for (int i = 0; i < listaProd.size(); i++) {

            desc += ((listaProdAux.get(i).getPcosto()
                    + listaProdAux.get(i).getPcosto() * (listaProdAux.get(i).getUtilidad() / 100))
                    * (listaProdAux.get(i).getDescuento() / 100)) * listaProd.get(i).getCantidad();
        }

        return df2.format(desc);
    }

    public String retornaDescuentoSinFormato() {
        double desc = 0.00;
        for (int i = 0; i < listaProd.size(); i++) {

            desc += ((listaProdAux.get(i).getPcosto()
                    + listaProdAux.get(i).getPcosto() * (listaProdAux.get(i).getUtilidad() / 100))
                    * (listaProdAux.get(i).getDescuento() / 100)) * listaProd.get(i).getCantidad();
        }

        return df.format(desc);
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

                            String precioU = df2.format(precioUnit);
                            String valFil = df2.format(valor);

                            String precioUSf = df.format(precioUnit);
                            String valFilSF = df.format(valor);

                            listaProdConFormato.add(new Producto(producto.getCodigo(), producto.getNombre(), precioU,
                                    Double.parseDouble(cantidadProd), valFil));

                            listaProd.add(new Producto(producto.getCodigo(), producto.getNombre(), precioUSf,
                                    Double.parseDouble(cantidadProd), valFilSF));

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

    public static void setCliente(String[] name) {
        nombre = name;
    }

    public static void abrirCaja(String valor, String userCodigo) {
        Sentencias.crud("Call guardarAperturaCaja('" + valor + "','1','" + userCodigo + "');");
        Alerta.mostrarAlertWarning("CAJA ABIERTA CON: L " + valor);
        Sentencias.crud("Call crearRegistroBitacora('Apertura de la caja','CAJA','" + userCodigo + "')");
    }

    public void campos() {
        buscar.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letrasNumeros(event, buscar, true, 15));
        cliente.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letras(event, cliente, true, 15));
        pago.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.decimales(event, pago, 15));
    }

    public void focus() {
        forma.setFocusTraversable(false);
        tipo.setFocusTraversable(false);
        categorias.setFocusTraversable(false);
        btnVerFactura.setFocusTraversable(false);
        btnQuitar.setFocusTraversable(false);
        tblListaProd.setFocusTraversable(false);
        btnRefresh.setFocusTraversable(false);
        cliente.setFocusTraversable(false);
        pago.setFocusTraversable(false);
        stock.setFocusTraversable(false);
        btnBuscar.setFocusTraversable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        detecta();
        datosTabla();
        laAtras.setGraphic(new ImageView("/Images/back.png"));
        btnRefresh.setGraphic(new ImageView("/Images/refresh.png"));
        campos();
        focus();
    }
}