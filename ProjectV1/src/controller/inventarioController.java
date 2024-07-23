package controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import data.Alerta;
import data.GenerarCBarra2;
import data.Sentencias;
import data.ValidacionCampos;
import first.loadWindow2;
import first.loadWindow3;
import first.loadWindow4;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Cliente;
import model.Producto;
import model.Proveedor;

public class inventarioController implements Initializable {

    private String codigoAux;
    private ToggleGroup estado;
    private ListView listProdAux;
    private int val, valDescuento;
    private Double valImpuesto, valUtilidad;
    private ComboBox categoriaAux, filtroAux;
    private loadWindow3 lw = new loadWindow3();
    private loadWindow4 lw4 = new loadWindow4();
    private loadWindow2 lw2 = new loadWindow2();
    //private loadWindow lww = new loadWindow();
    private DecimalFormat df = new DecimalFormat("0");
    private DecimalFormat formater = new DecimalFormat("#.00");
    private NumberFormat df2 = new DecimalFormat("#,##0.00"); 
    private String costoAux, costoAux2, utilidadAux, impuestoAux, precioventaAux;

    @FXML
    private HBox boxAll;

    @FXML
    private HBox boxProd;

    @FXML
    private HBox boxProv;

    @FXML
    private HBox boxCli;

    @FXML
    private HBox boxCat;

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnProd;

    @FXML
    private Button btnProv;

    @FXML
    private Button btnCli;

    @FXML
    private Button btnCat;

    @FXML
    private Button btnSalir;

    @FXML
    private RadioButton act;

    @FXML
    private Button add;

    @FXML
    private Button btnComprar;

    @FXML
    private TextField buscar;

    @FXML
    private ComboBox<String> categoria;

    @FXML
    private ComboBox<String> medida;

    @FXML
    private ComboBox<String> descuento;

    @FXML
    private TextField codigo;

    @FXML
    private TextField costo;

    @FXML
    private Button del;

    @FXML
    private TextArea descrip;

    @FXML
    private ComboBox<String> filtro;

    @FXML
    private Button find;

    @FXML
    private Button guardar;

    @FXML
    private ComboBox<String> impuesto;

    @FXML
    private TextField isv;

    @FXML
    private RadioButton inac;

    @FXML
    private TextField inventario_max;

    @FXML
    private TextField inventario_min;

    @FXML
    private ListView<String> listProd;

    @FXML
    private Button mod;

    @FXML
    private TextField nombre;

    @FXML
    private TextField precio_venta;

    @FXML
    private Button res;

    @FXML
    private TextField utili;

    @FXML
    private ComboBox<String> utilidad;

    @FXML
    private Label stock;

    @FXML
    private ImageView imaLogo;

    @FXML
    private Button btnGenerar;

    /////////////////////////////////////////////////////// VARIABLES PARA
    /////////////////////////////////////////////////////// PROVEEDOR//////////////////////////////////////////////////////////

    private ToggleGroup estadoProv;
    private ListView listProvAux;
    private int valProv;

    @FXML
    private RadioButton actProv;

    @FXML
    private Button addProv;

    @FXML
    private TextField buscarProv;

    @FXML
    private TextField codigoProv;

    @FXML
    private Button delProv;

    @FXML
    private TextField direccionProv;

    @FXML
    private Button findProv;

    @FXML
    private Button guardarProv;

    @FXML
    private RadioButton inacProv;

    @FXML
    private ListView<String> listProv;

    @FXML
    private Button modProv;

    @FXML
    private TextField nombreProv;

    @FXML
    private Button resProv;

    @FXML
    private TextField telefonoProv;

    /////////////////////////////////////////////////////// VARIABLES PARA
    /////////////////////////////////////////////////////// CLIENTE//////////////////////////////////////////////////////////////

    private ToggleGroup estadoCli;
    private ListView listCliAux;
    private int valCli;
    private String cedulaAux;

    @FXML
    private RadioButton inacCli;

    @FXML
    private RadioButton actCli;

    @FXML
    private Button addCli;

    @FXML
    private TextField apellidoCli;

    @FXML
    private TextField buscarCli;

    @FXML
    private TextField codigoCli;

    @FXML
    private TextField correoCli;

    @FXML
    private Button delCli;

    @FXML
    private Button findCli;

    @FXML
    private Button guardarCli;

    @FXML
    private ListView<String> listCli;

    @FXML
    private Button modCli;

    @FXML
    private TextField nombreCli;

    @FXML
    private Button resCli;

    @FXML
    private TextField rtnCli;

    @FXML
    private TextField telefonoCli;

    @FXML
    private Button btnAbono;

    @FXML
    private Label laSaldo;

    /////////////////////////////////// VARIABLES PARA
    /////////////////////////////////// CATEGORIA/////////////////////////////////////////////////////////

    private ToggleGroup estadoCat;
    private ComboBox catNombresAux;

    @FXML
    private ComboBox<String> catDesc;

    @FXML
    private ComboBox<String> catNombres;

    @FXML
    private TextField addCat;

    @FXML
    private Button btnModCat;

    @FXML
    private Button btnNewCat;

    @FXML
    private Button btnDelCat;

    @FXML
    private TextArea catDescrip;

    @FXML
    private TextField catNombre;

    @FXML
    private RadioButton con;

    @FXML
    private RadioButton sin;

    ///////////////////////////////////////////////////////// PRODUCTO

    @FXML
    private HBox boxLateral;

    @FXML
    private VBox box1;

    @FXML
    private HBox box2;

    /////////////////////////////////////////////////////////// PROVEEDOR

    @FXML
    private VBox box3;

    @FXML
    private VBox box4;

    //////////////////////////////////////////////////////////// CLIENTE

    @FXML
    private VBox box5;

    @FXML
    private VBox box6;

    ////////////////////////////////////////////////////////////// CATEGORIA

    @FXML
    private HBox box7;

    @FXML
    private VBox box8;

    public void togleButton() {

        boxLateral.getStyleClass().add("vbox");

        box1.getStyleClass().add("vbox");

        box2.getStyleClass().add("vbox2");

        box3.getStyleClass().add("vbox");

        box4.getStyleClass().add("vbox2");

        box5.getStyleClass().add("vbox");

        box6.getStyleClass().add("vbox2");

        box7.getStyleClass().add("vbox");

        box8.getStyleClass().add("vbox");

        boxCat.getStyleClass().add("vbox2");

        categoriaAux = new ComboBox<>();
        filtroAux = new ComboBox<>();

        descrip.setWrapText(true);
        catDescrip.setWrapText(true);

        listProd.setStyle("-fx-font: 18px \"System\";");

        filtro.setStyle("-fx-font: 18px \"System\";");
        Sentencias.llenaComboBox("call retornaCategorias()", filtro, filtroAux);
        filtro.setValue("Categoría (Ninguna)");
        filtro.setOnAction(event -> {
            if (guardar.getText().equals("RESTAURAR")) {
                llenaListaProd("CALL retornaProductosCategoriaInactivos('"
                        + filtro.getSelectionModel().getSelectedItem() + "')");
            } else {
                llenaListaProd("CALL retornaProductosCategoriaActivos('" + filtro.getSelectionModel().getSelectedItem()
                        + "')");
            }
        });

        estado = new ToggleGroup();

        act.setToggleGroup(estado);
        inac.setToggleGroup(estado);

        medida.setStyle("-fx-font: 18px \"System\";");
        medida.getItems().add("Unidad");
        medida.getItems().add("Libra");

        categoria.setStyle("-fx-font: 18px \"System\";");
        Sentencias.llenaComboBox("call retornaCategorias()", categoria, categoriaAux);
        categoria.setValue("Categoría (Ninguna)");
        categoria.setOnAction(event -> {
            if(categoria.getSelectionModel().getSelectedItem() != null && !categoria.getSelectionModel().getSelectedItem().equals("Categoría (NINGUNA)")){
                descuento.setValue(Sentencias
                    .retornaDescuentoCategoria("Call retornaDescuentoCategoria('"
                            + categoriaAux.getItems().get(categoria.getSelectionModel().getSelectedIndex()) + "');")
                    + "%");
            }
        });

        impuesto.setStyle("-fx-font: 18px \"System\";");
        impuesto.getItems().add("0%");
        impuesto.getItems().add("15%");
        impuesto.getItems().add("18%");
        impuesto.setOnAction(event -> {
            if (!impuesto.getSelectionModel().isEmpty()) {
                calIsv();
                total();
            }
        });

        utilidad.setStyle("-fx-font: 18px \"System\";");
        for (int x = 0; x < 51; x++) {
            utilidad.getItems().add(x + "%");
        }
        utilidad.setOnAction(event -> {
            if (!utilidad.getSelectionModel().isEmpty()) {
                if(guardar.getText().equals("MODIFICAR") || guardar.getText().equals("AGREGAR")){
                    impuesto.setDisable(false);
                }
                impuesto.setValue("0%");
                isv.setText(".00");
                calUtilidad();
                total();
            }
        });

        descuento.setStyle("-fx-font: 18px \"System\";");
        for (int x = 0; x < 51; x++) {
            descuento.getItems().add(x + "%");
        }
    }

    private String calUtilidad() {

        DecimalFormat formateador;
        formateador = new DecimalFormat("#.00");

        costoAux = costo.getText().replace(",", "");

        if (!costo.getText().isEmpty()) {
            String valor = utilidad.getSelectionModel().getSelectedItem().toString();
            String[] val = valor.split("%");
            double utilidad2 = Double.parseDouble(val[0]);
            double cost = Double.parseDouble(costoAux);
            double u = cost * (utilidad2 / 100);
            utili.setText(df2.format(u) + "");
            utilidadAux = formateador.format(u);
            return formateador.format(u);
        } else {
            String valor = utilidad.getSelectionModel().getSelectedItem().toString();
            String[] val = valor.split("%");
            double utilidad2 = Double.parseDouble(val[0]);
            double cost = 0.0;
            double u = cost * (utilidad2 / 100);
            utili.setText(df2.format(u) + "");
            utilidadAux = formateador.format(u);
            return formateador.format(u);
        }
    }

    private String calIsv() {

        DecimalFormat formateador;
        formateador = new DecimalFormat("#.00");

        costoAux = costo.getText().replace(",", "");

        if (!costo.getText().isEmpty()) {
            String valor = impuesto.getSelectionModel().getSelectedItem().toString();
            String[] val = valor.split("%");
            double impuesto2 = Double.parseDouble(val[0]);
            double cost = Double.parseDouble(costoAux);
            double u = (cost + Double.parseDouble(utilidadAux)) * (impuesto2 / 100);
            isv.setText(df2.format(u) + "");
            impuestoAux = formateador.format(u);
            return formateador.format(u);
        } else {
            String valor = impuesto.getSelectionModel().getSelectedItem().toString();
            String[] val = valor.split("%");
            double impuesto2 = Double.parseDouble(val[0]);
            double cost = 0.0;
            double u = (cost) * (impuesto2 / 100);
            isv.setText(df2.format(u) + "");
            impuestoAux = formateador.format(u);
            return formateador.format(u);
        }
    }

    public void total() {
        DecimalFormat formateador;
        formateador = new DecimalFormat("#.00");
        Double costo2 = 0.0;
        Double impuesto2 = 0.0;
        Double utilidad2 = 0.0;
        Double pventa = 0.0;

        costoAux = costo.getText().replace(",", "");

        if (!costo.getText().isEmpty()) {
            if (costo.getText().charAt(0) != '.' || costo.getText().length() > 1) {
                costo2 = Double.parseDouble(costoAux);
            } else {
                costo2 = 0.0;
            }
        } else {
            costo2 = 0.0;
        }
        if (!impuesto.getSelectionModel().isEmpty()) {
            String valor = impuesto.getSelectionModel().getSelectedItem().toString();
            String[] val = valor.split("%");
            impuesto2 = Double.parseDouble(val[0]);
        } else {
            impuesto2 = 0.0;
        }
        if (!utilidad.getSelectionModel().isEmpty()) {
            String valor = utilidad.getSelectionModel().getSelectedItem().toString();
            String[] val = valor.split("%");
            utilidad2 = Double.parseDouble(val[0]);

        } else {
            utilidad2 = 0.0;
        }

        pventa = costo2 + (costo2 * (utilidad2 / 100)) + ((costo2 + (costo2 * (utilidad2 / 100))) * (impuesto2 / 100));
        precioventaAux = formateador.format(pventa);
        precio_venta.setText(df2.format(pventa));
        /*
         * System.out.print(costo2 + "\n");
         * System.out.print(impuesto2 + "\n");
         * System.out.print(utilidad2 + "\n");
         * System.out.print(pventa + "\n");
         */
    }

    private void llenaListaProd(String sql) {

        ArrayList<Producto> productos = Sentencias.retornaProductos(sql);
        Producto producto = new Producto();
        listProd.getItems().clear();
        listProdAux.getItems().clear();
        for (int i = 0; i < productos.size(); i++) {
            producto = productos.get(i);
            listProd.getItems().add(producto.getNombre());
            listProdAux.getItems().add(producto.getCodigo());
        }

    }

    private void llenaCamposProducto(String sql) {
        String code = listProdAux.getItems().get(listProd.getSelectionModel().getSelectedIndex()) + "";
        ArrayList<Producto> productos = Sentencias.retornaProductos(sql);
        Producto producto = new Producto();
        for (int i = 0; i < productos.size(); i++) {
            producto = productos.get(i);

            if (code.equals(producto.getCodigo())) {

                codigoAux = producto.getCodigo() + "";
                codigo.setText(producto.getCodigo() + "");
                nombre.setText(producto.getNombre());
                descrip.setText(producto.getDescrip());
                costo.setText(df2.format(producto.getPcosto()) + "");
                costoAux = formater.format(producto.getPcosto()) + "";
                //costoAux2 = df2.format(producto.getPcosto()) + "";
                utilidad.setValue(producto.getUtilidad() + "%");
                calUtilidad();
                //utili.setText(df2.format(calUtilidad()));
                impuesto.setValue(producto.getImpuesto() + "%");
                calIsv();
                //isv.setText(df2.format(calIsv()));
                descuento.setValue(df.format(producto.getDescuento()) + "%");
                precio_venta.setText(df2.format(producto.getPventa()) + "");
                precioventaAux = producto.getPventa()+"";
                inventario_max.setText(producto.getImax() + "");
                inventario_min.setText(producto.getImin() + "");
                stock.setText("Disponible: "
                        + Sentencias.retornaStock("Call retornaStockUnProducto('" + producto.getCodigo() + "');"));
                categoria.setValue(producto.getCategoria());
                medida.setValue(producto.getMedida());
                if (producto.getEstado() == 1) {
                    act.setSelected(true);
                } else {
                    inac.setSelected(true);
                }

                return;
            }
        }

    }

    public <T> void detecta() {

        listProdAux = new ListView();
        llenaListaProd("CALL retornaProductosActivos();");
        listProd.getSelectionModel().selectedIndexProperty()
                .addListener((ChangeListener<? super Number>) new ChangeListener<T>() {

                    @Override
                    public void changed(ObservableValue<? extends T> arg0, T arg1, T arg2) {

                        if (listProd.getSelectionModel().getSelectedIndex() > -1) {
                            llenaCamposProducto("CALL retornaProductos;");
                        }
                        if (guardar.getText().equals("MODIFICAR")) {
                            enableCampos();
                        }

                    }

                });

        add.setTooltip(new Tooltip("Presione para crear un producto"));
        add.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "61")) {
                botonGuardar();
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        mod.setTooltip(new Tooltip("Presione para modificar un producto"));
        mod.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "62")) {
                botonModificar();
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        del.setTooltip(new Tooltip("Presione para eliminar un producto"));
        del.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "63")) {
                botonEliminar();
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        res.setTooltip(new Tooltip("Presione para restaurar un producto eliminado"));
        res.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "64")) {
                botonRestaurar();
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        guardar.setOnAction(event -> {
            acciones(event);
        });

        btnProv.setOnAction(event -> {
            boxAll.getChildren().clear();
            boxAll.getChildren().add(boxProv);

        });

        btnProd.setOnAction(event -> {
            boxAll.getChildren().clear();
            boxAll.getChildren().add(boxProd);

        });

        btnCli.setOnAction(event -> {
            boxAll.getChildren().clear();
            boxAll.getChildren().add(boxCli);

        });

        btnCat.setOnAction(event -> {
            boxAll.getChildren().clear();
            boxAll.getChildren().add(boxCat);

        });

        btnGenerar.setOnAction(event -> {
            generarCodigoAleatorio();
        });

        btnComprar.setOnAction(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            if (Sentencias.validaAccesosUser(event, "65")) {
                lw2.loadStage("/view/viewComprar.fxml", event, 1000, 650, stage.getTitle());
            }
        });

        btnAtras.setOnAction(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            //matarProcesos();
            lw.loadStage("/view/viewInicio.fxml", event, 1280, 720, stage);
        });

        btnSalir.setOnAction(event -> {
            lw4.loadStage("/view/viewLogin.fxml", event, 897, 652, "");
        });

        find.setOnAction(event -> {
            if (!guardar.getText().equals("RESTAURAR")) {
                Sentencias.buscarProd("CALL retornaProductosActivos", buscar.getText(), filtro, listProd, listProdAux);
            } else {
                Sentencias.buscarProd("CALL retornaProductosInactivos", buscar.getText(), filtro, listProd,
                        listProdAux);
            }

        });

    }

    private void matarProcesos() {
        listCli.getItems().clear();
        listProd.getItems().clear();
        listProv.getItems().clear();

        listCliAux.getItems().clear();
        listProdAux.getItems().clear();
        listProvAux.getItems().clear();
    }

    private void botonRestaurar() {
        listProd.getItems().clear();
        listProdAux.getItems().clear();
        llenaListaProd("CALL retornaProductosInactivos;");
        guardar.setText("RESTAURAR");
        limpiar();
        disableCampos();
        guardar.setDisable(false);
        listProd.setDisable(false);
    }

    private void botonEliminar() {
        listProd.getItems().clear();
        listProdAux.getItems().clear();
        llenaListaProd("CALL retornaProductosActivos;");
        guardar.setText("ELIMINAR");
        limpiar();
        disableCampos();
        guardar.setDisable(false);
        listProd.setDisable(false);
    }

    private void botonModificar() {
        codigo.setEditable(false);
        listProd.getItems().clear();
        listProdAux.getItems().clear();
        llenaListaProd("CALL retornaProductosActivos;");
        guardar.setText("MODIFICAR");
        limpiar();
        disableCampos();
        listProd.setDisable(false);
    }

    private void botonGuardar() {

        listProd.getItems().clear();
        listProdAux.getItems().clear();
        llenaListaProd("CALL retornaProductosActivos;");
        guardar.setText("AGREGAR");
        limpiar();
        enableCampos();
        listProd.setDisable(true);
        btnComprar.setDisable(true);
    }

    public void limpiar() {

        codigo.clear();
        nombre.clear();
        costo.clear();
        isv.clear();
        utili.clear();
        impuesto.getSelectionModel().select(0);
        precio_venta.clear();
        utilidad.getSelectionModel().select(0);
        descuento.getSelectionModel().select(0);
        categoria.getSelectionModel().select(0);
        medida.getSelectionModel().select(0);
        descrip.clear();
        inventario_max.clear();
        inventario_min.clear();
        stock.setText("Disponible:");
        act.setSelected(false);
        inac.setSelected(false);
        costoAux = "0.00";
        costoAux2 = "0.00";
        precioventaAux = "0.00";
        utilidadAux = "0.00";
        impuestoAux = "0.00";
    }

    public void enableCampos() {

        codigo.setEditable(true);
        nombre.setEditable(true);
        costo.setEditable(true);
        precio_venta.setEditable(true);
        utilidad.setDisable(false);
        descuento.setDisable(false);
        categoria.setDisable(false);
        medida.setDisable(false);
        descrip.setEditable(true);
        inventario_max.setEditable(true);
        inventario_min.setEditable(true);
        act.setDisable(false);
        inac.setDisable(false);
        btnComprar.setDisable(false);
        btnGenerar.setDisable(false);
        guardar.setDisable(false);

    }

    public void disableCampos() {

        codigo.setEditable(false);
        nombre.setEditable(false);
        costo.setEditable(false);
        impuesto.setDisable(true);
        isv.setEditable(false);
        precio_venta.setEditable(false);
        utilidad.setDisable(true);
        utili.setEditable(false);
        descuento.setDisable(true);
        categoria.setDisable(true);
        medida.setDisable(true);
        descrip.setEditable(false);
        inventario_max.setEditable(false);
        inventario_min.setEditable(false);
        act.setDisable(true);
        inac.setDisable(true);
        btnComprar.setDisable(true);
        btnGenerar.setDisable(true);
        guardar.setDisable(true);

    }

    public void sacaImpuestoUtilidadDescuentoEstado() {

        if (act.isSelected()) {
            val = 1;
        } else if (inac.isSelected()) {
            val = 0;
        }

        String valor = impuesto.getSelectionModel().getSelectedItem().toString();
        String[] val = valor.split("%");
        valImpuesto = Double.parseDouble(val[0]);

        valor = utilidad.getSelectionModel().getSelectedItem().toString();
        val = valor.split("%");
        valUtilidad = Double.parseDouble(val[0]);

        valor = descuento.getSelectionModel().getSelectedItem().toString();
        val = valor.split("%");
        valDescuento = Integer.parseInt(val[0]);
    }

    private void acciones(ActionEvent event) {
        if (guardar.getText().equals("AGREGAR")) {
            if (!codigo.getText().isEmpty() && !nombre.getText().isEmpty()
                    && !categoria.getSelectionModel().getSelectedItem().equals("Categoría (NINGUNA)")
                    && !costo.getText().isEmpty() && !impuesto.getSelectionModel().getSelectedItem().equals("0%")
                    && !utilidad.getSelectionModel().getSelectedItem().equals("0%")
                    && !precio_venta.getText().isEmpty() && !inventario_min.getText().isEmpty()
                    && !inventario_max.getText().isEmpty() && !descrip.getText().isEmpty()
                    && (act.isSelected() || inac.isSelected())) {
                if (!Sentencias.existeProducto("CALL existeProducto('" + codigo.getText() + "')", codigo.getText())) {
                    if (validaLongitudCodigoProd()) {
                        if (Alerta.mostrarAlertConfirmation(
                                "Desea generar un código de barras para este producto?") == 1) {
                            if (Alerta.mostrarAlertConfirmation("Seguro que desea crear el producto?") == 1) {
                                sacaImpuestoUtilidadDescuentoEstado();
                                guardarProducto();
                                GenerarCBarra2.retornaBarra(codigo.getText());
                                abrirUbicacionArchivo();
                                Sentencias
                                        .crud("Call crearRegistroBitacora('Se creo el producto: " + nombre.getText()
                                                + "','INVENTARIO','"
                                                + Sentencias.retornaUserCodigo(
                                                        "Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                                + "')");
                                limpiar();
                                llenaListaProd("CALL retornaProductosActivos;");
                            }
                        } else {
                            if (Alerta.mostrarAlertConfirmation("Seguro que desea crear el producto?") == 1) {
                                sacaImpuestoUtilidadDescuentoEstado();
                                guardarProducto();
                                Sentencias
                                        .crud("Call crearRegistroBitacora('Se creo el producto: " + nombre.getText()
                                                + "','INVENTARIO','"
                                                + Sentencias.retornaUserCodigo(
                                                        "Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                                + "')");
                                limpiar();
                                llenaListaProd("CALL retornaProductosActivos;");
                            }
                        }
                    } else {
                        Alerta.mostrarAlertWarning("El código debe tener al menos 8 caracteres!");
                    }
                } else {
                    Alerta.mostrarAlertWarning("EL CÓDIGO DE PRODUCTO YA EXISTE!!");
                }
            } else {
                Alerta.mostrarAlertWarning("RELLENE TODOS LOS CAMPOS!!");
            }
        }
        if (guardar.getText().equals("MODIFICAR")) {
            if (!codigo.getText().isEmpty() && !nombre.getText().isEmpty()
                    && !categoria.getSelectionModel().getSelectedItem().equals("Categoría (NINGUNA)")
                    && !costo.getText().isEmpty() // && !impuesto.getSelectionModel().getSelectedItem().equals("0%")
                    && !utilidad.getSelectionModel().getSelectedItem().equals("0%")
                    && !precio_venta.getText().isEmpty() && !inventario_min.getText().isEmpty()
                    && !inventario_max.getText().isEmpty() && !descrip.getText().isEmpty()
                    && (act.isSelected() || inac.isSelected())) {
                if (!codigo.getText().equals(codigoAux)) {
                    if (!Sentencias.existeProducto("CALL existeProducto('" + codigo.getText() + "')",
                            codigo.getText())) {
                        if (validaLongitudCodigoProd()) {
                            if (Alerta.mostrarAlertConfirmation(
                                    "Desea generar un código de barras para este producto?") == 1) {
                                if (Alerta.mostrarAlertConfirmation("Seguro que desea modificar el producto?") == 1) {
                                    sacaImpuestoUtilidadDescuentoEstado();
                                    modificarProducto(1);// con codigo
                                    GenerarCBarra2.retornaBarra(codigo.getText());
                                    abrirUbicacionArchivo();
                                    Sentencias
                                            .crud("Call crearRegistroBitacora('Se modifico el producto: "
                                                    + nombre.getText() + "','INVENTARIO','"
                                                    + Sentencias.retornaUserCodigo(
                                                            "Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                                    + "')");
                                    limpiar();
                                    llenaListaProd("CALL retornaProductosActivos;");
                                }
                            } else {
                                if (Alerta.mostrarAlertConfirmation("Seguro que desea modificar el producto?") == 1) {
                                    sacaImpuestoUtilidadDescuentoEstado();
                                    modificarProducto(1);// con codigo
                                    Sentencias
                                            .crud("Call crearRegistroBitacora('Se modifico el producto: "
                                                    + nombre.getText() + "','INVENTARIO','"
                                                    + Sentencias.retornaUserCodigo(
                                                            "Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                                    + "')");
                                    limpiar();
                                    llenaListaProd("CALL retornaProductosActivos;");
                                }
                            }
                        } else {
                            Alerta.mostrarAlertWarning("El código debe tener almenos 8 caracteres!");
                        }
                    } else {
                        Alerta.mostrarAlertWarning("EL CÓDIGO DE PRODUCTO YA EXISTE!!");
                    }
                } else {
                    if (validaLongitudCodigoProd()) {
                        if (Alerta.mostrarAlertConfirmation(
                                "Desea generar un código de barras para este producto?") == 1) {
                            if (Alerta.mostrarAlertConfirmation("Seguro que desea modificar el producto?") == 1) {
                                sacaImpuestoUtilidadDescuentoEstado();
                                modificarProducto(2);// sin codigo
                                GenerarCBarra2.retornaBarra(codigo.getText());
                                abrirUbicacionArchivo();
                                Sentencias
                                        .crud("Call crearRegistroBitacora('Se modifico el producto: " + nombre.getText()
                                                + "','INVENTARIO','"
                                                + Sentencias.retornaUserCodigo(
                                                        "Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                                + "')");
                                limpiar();
                                llenaListaProd("CALL retornaProductosActivos;");
                            }
                        } else {
                            if (Alerta.mostrarAlertConfirmation("Seguro que desea modificar el producto?") == 1) {
                                sacaImpuestoUtilidadDescuentoEstado();
                                modificarProducto(2);// sin codigo
                                Sentencias
                                        .crud("Call crearRegistroBitacora('Se modifico el producto: " + nombre.getText()
                                                + "','INVENTARIO','"
                                                + Sentencias.retornaUserCodigo(
                                                        "Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                                + "')");
                                limpiar();
                                llenaListaProd("CALL retornaProductosActivos;");
                            }
                        }
                    } else {
                        Alerta.mostrarAlertWarning("El código debe tener almenos 8 caracteres!");
                    }
                }
            } else {
                Alerta.mostrarAlertWarning("RELLENE TODOS LOS CAMPOS!!");
            }
        }
        if (guardar.getText().equals("ELIMINAR")) {
            if (listProd.getSelectionModel().getSelectedIndex() != -1) {
                if (Alerta.mostrarAlertConfirmation("Seguro que desea eliminar el producto?") == 1) {
                    Sentencias.crud("call eliminarProducto('" + codigo.getText() + "')");
                    Alerta.mostrarAlertInfo("Se Elimino");
                    Sentencias.crud("Call crearRegistroBitacora('Se elimino el producto: " + nombre.getText()
                            + "','INVENTARIO','"
                            + Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                            + "')");
                    limpiar();
                    llenaListaProd("CALL retornaProductosActivos;");
                }
            } else {
                Alerta.mostrarAlertInfo("Seleccione un producto!!");
            }
        }
        if (guardar.getText().equals("RESTAURAR")) {
            if (listProd.getSelectionModel().getSelectedIndex() != -1) {
                if (Alerta.mostrarAlertConfirmation("Seguro que desea restaurar el producto?") == 1) {
                    Sentencias.crud("call restaurarProducto('" + codigo.getText() + "')");
                    Alerta.mostrarAlertInfo("Se Restauro");
                    Sentencias.crud("Call crearRegistroBitacora('Se restauro el producto: " + nombre.getText()
                            + "','INVENTARIO','"
                            + Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                            + "')");
                    limpiar();
                    llenaListaProd("CALL retornaProductosInactivos;");
                }
            } else {
                Alerta.mostrarAlertInfo("Seleccione un producto!!");
            }
        }
    }

    private void modificarProducto(int tipo) {
        costoAux = costo.getText().replace(",", "");
        total();
        if (tipo == 1) {
            Sentencias.crud("Call modificarProductoConCodigo('" + codigo.getText() + "','" + nombre.getText()
                    + "','"
                    + descrip.getText() + "','" + costoAux + "','" + valImpuesto + "','"
                    + valUtilidad
                    + "','" + precioventaAux + "','" + inventario_max.getText() + "','"
                    + inventario_min.getText() + "','"
                    + categoria.getSelectionModel().getSelectedItem() + "','"
                    + valDescuento + "','" + medida.getSelectionModel().getSelectedItem() + "','"
                    + val + "','" + codigoAux + "')");
        } else {
            Sentencias.crud("Call modificarProducto('" + codigo.getText() + "','" + nombre.getText()
                    + "','"
                    + descrip.getText() + "','" + costoAux + "','" + valImpuesto + "','"
                    + valUtilidad
                    + "','" + precioventaAux + "','" + inventario_max.getText() + "','"
                    + inventario_min.getText() + "','"
                    + categoria.getSelectionModel().getSelectedItem() + "','"
                    + valDescuento + "','" + medida.getSelectionModel().getSelectedItem() + "','"
                    + val + "','" + codigoAux + "')");
        }

        Alerta.mostrarAlertWarning("SE MODIFICO EL PRODUCTO!!");
    }

    private void guardarProducto() {
        costoAux = costo.getText().replace(",", "");
        Sentencias.crud("Call crearProducto('" + codigo.getText() + "','" + nombre.getText()
                + "','"
                + descrip.getText() + "','" + costoAux + "','" + valImpuesto + "','"
                + valUtilidad
                + "','" + precioventaAux + "','" + inventario_max.getText() + "','"
                + inventario_min.getText() + "','"
                + categoria.getSelectionModel().getSelectedItem()
                + "','"
                + valDescuento + "','" + medida.getSelectionModel().getSelectedItem() + "','" + val + "')");
        Alerta.mostrarAlertWarning("SE AGREGO EL PRODUCTO!!");
    }

    public boolean validaLongitudCodigoProd() {
        if (codigo.getText().length() >= 8) {
            return true;
        } else {
            return false;
        }
    }

    public void generarCodigoAleatorio() {
        int min_val = 0;
        int max_val = 9;
        int randomNum = 0;
        String code = "";
        Random ran = new Random();
        for (int i = 0; i < 8; i++) {
            randomNum = ran.nextInt(max_val) + min_val;
            code += randomNum;
        }

        codigo.setText(code);
    }

    public void abrirUbicacionArchivo() {
        File path = new File("C:/ContaJESistem/resources/CodeBarra");
        try {
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void campos() {
        buscar.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letrasNumeros(event, buscar, true, 15));
        codigo.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letrasNumeros(event, codigo, false, 15));
        nombre.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letrasNumeros(event, nombre, true, 25));
        costo.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.decimales(event, costo, 15));
        precio_venta.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.decimales(event, precio_venta, 15));
        inventario_min.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.enteros(event, inventario_min, 15));
        inventario_max.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.enteros(event, inventario_max, 15));
        descrip.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.validaLongitudTextArea(event, descrip, true, 100));
    }

    public void toltipProd() {
        codigo.setTooltip(new Tooltip("Código del producto"));
        nombre.setTooltip(new Tooltip("Nombre del producto"));
        categoria.setTooltip(new Tooltip("Categoría del producto"));
        costo.setTooltip(new Tooltip("Costo del producto"));
        utili.setTooltip(new Tooltip("Utilidad en valor del producto"));
        utilidad.setTooltip(new Tooltip("Utilidad en porcentaje del producto"));
        isv.setTooltip(new Tooltip("Impuesto en valor del producto"));
        impuesto.setTooltip(new Tooltip("Impuesto en porcentaje del producto"));
        descuento.setTooltip(new Tooltip("Descuesto en porcentaje del producto"));
        precio_venta.setTooltip(new Tooltip("Precio de venta del producto"));
        inventario_max.setTooltip(new Tooltip("Inventario maximo del producto"));
        inventario_min.setTooltip(new Tooltip("Inventario minimo del producto"));
        descrip.setTooltip(new Tooltip("Descripción del producto"));
    }

    /////////////////////////////////////////////////////// LOGICA PARA
    /////////////////////////////////////////////////////// PROVEEDOR//////////////////////////////////////////////////////////

    public void togleButtonProv() {

        estadoProv = new ToggleGroup();

        actProv.setToggleGroup(estadoProv);
        inacProv.setToggleGroup(estadoProv);
    }

    private void llenaListaProv(String sql) {

        ArrayList<Proveedor> proveedores = Sentencias.retornaProveedores(sql);
        Proveedor proveedor;
        listProv.getItems().clear();
        listProvAux.getItems().clear();
        for (int i = 0; i < proveedores.size(); i++) {
            proveedor = proveedores.get(i);
            listProv.getItems().add(proveedor.getNombre());
            listProvAux.getItems().add(proveedor.getCodigo());
        }

    }

    private void llenaCamposProveedor(String sql) {
        String code = listProvAux.getItems().get(listProv.getSelectionModel().getSelectedIndex()) + "";
        ArrayList<Proveedor> proveedores = Sentencias.retornaProveedores(sql);
        Proveedor proveedor;
        for (int i = 0; i < proveedores.size(); i++) {
            proveedor = proveedores.get(i);
            if (Integer.parseInt(code) == (proveedor.getCodigo())) {
                codigoProv.setText(proveedor.getCodigo() + "");
                nombreProv.setText(proveedor.getNombre());
                direccionProv.setText(proveedor.getDireccion());
                telefonoProv.setText(proveedor.getTelefono() + "");
                if (proveedor.getEstado() == 1) {
                    actProv.setSelected(true);
                } else {
                    inacProv.setSelected(true);
                }

                return;
            }
        }

    }

    public void enableCamposProv() {

        // codigoProv.setEditable(true);
        nombreProv.setEditable(true);
        direccionProv.setEditable(true);
        telefonoProv.setEditable(true);
        actProv.setDisable(false);
        inacProv.setDisable(false);
        guardarProv.setDisable(false);
    }

    public <T> void detectaProv() {

        listProvAux = new ListView();

        listProv.setStyle("-fx-font: 18px \"System\";");

        llenaListaProv("CALL retornaProveedoresActivos();");
        listProv.getSelectionModel().selectedIndexProperty()
                .addListener((ChangeListener<? super Number>) new ChangeListener<T>() {

                    @Override
                    public void changed(ObservableValue<? extends T> arg0, T arg1, T arg2) {

                        // codigo.setText(listProd.getSelectionModel().getSelectedItem().toString());
                        // System.out.print(listProv.getSelectionModel().getSelectedIndex());
                        if (listProv.getSelectionModel().getSelectedIndex() > -1) {
                            llenaCamposProveedor("CALL retornaProveedores();");
                        }
                        if (guardarProv.getText().equals("MODIFICAR")) {
                            enableCamposProv();
                        }

                    }

                });

        addProv.setTooltip(new Tooltip("Presione para crear un proveedor"));
        addProv.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "81")) {
                botonGuardarProv();
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        modProv.setTooltip(new Tooltip("Presione para modificar un proveedor"));
        modProv.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "82")) {
                botonModificarProv();
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        delProv.setTooltip(new Tooltip("Presione para eliminar un proveedor"));
        delProv.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "83")) {
                botonEliminarProv();
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        resProv.setTooltip(new Tooltip("Presione para restaurar un proveedor eliminado"));
        resProv.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "84")) {
                botonRestaurarProv();
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        guardarProv.setOnAction(event -> {
            accionesProv(event);
        });

        findProv.setOnAction(event -> {
            if (!guardarProv.getText().equals("RESTAURAR")) {
                Sentencias.buscarProd("CALL retornaProveedoresActivos", buscarProv.getText(), new ComboBox<>(),
                        listProv,
                        listProvAux);
            } else {
                Sentencias.buscarProd("CALL retornaProveedoresInactivos", buscarProv.getText(), new ComboBox<>(),
                        listProv, listProvAux);
            }

        });
    }

    private void botonRestaurarProv() {
        listProv.getItems().clear();
        listProvAux.getItems().clear();
        llenaListaProv("CALL retornaProveedoresInactivos;");
        guardarProv.setText("RESTAURAR");
        limpiarProv();
        disableCamposProv();
        guardarProv.setDisable(false);
        listProv.setDisable(false);
    }

    private void botonEliminarProv() {
        listProv.getItems().clear();
        listProvAux.getItems().clear();
        llenaListaProv("CALL retornaProveedoresActivos;");
        guardarProv.setText("ELIMINAR");
        limpiarProv();
        disableCamposProv();
        guardarProv.setDisable(false);
        listProv.setDisable(false);
    }

    private void botonModificarProv() {
        codigoProv.setEditable(false);
        listProv.getItems().clear();
        listProvAux.getItems().clear();
        llenaListaProv("CALL retornaProveedoresActivos;");
        guardarProv.setText("MODIFICAR");
        limpiarProv();
        disableCamposProv();
        listProv.setDisable(false);
    }

    private void botonGuardarProv() {

        listProv.getItems().clear();
        listProvAux.getItems().clear();
        llenaListaProv("CALL retornaProveedoresActivos;");
        guardarProv.setText("AGREGAR");
        limpiarProv();
        enableCamposProv();
        listProv.setDisable(true);
    }

    public void limpiarProv() {

        codigoProv.clear();
        nombreProv.clear();
        direccionProv.clear();
        telefonoProv.clear();
        actProv.setSelected(false);
        inacProv.setSelected(false);
    }

    public void disableCamposProv() {

        codigoProv.setEditable(false);
        nombreProv.setEditable(false);
        direccionProv.setEditable(false);
        telefonoProv.setEditable(true);
        actProv.setDisable(true);
        inacProv.setDisable(true);
        guardarProv.setDisable(true);

    }

    public void sacaEstadoProv() {
        if (actProv.isSelected()) {
            valProv = 1;
        } else if (inacProv.isSelected()) {
            valProv = 0;
        }
    }

    private void accionesProv(ActionEvent event) {
        if (guardarProv.getText().equals("AGREGAR")) {
            if (!nombreProv.getText().isEmpty()
                    && !direccionProv.getText().isEmpty()
                    && !telefonoProv.getText().isEmpty()
                    && (actProv.isSelected() || inacProv.isSelected())) {
                if (Alerta.mostrarAlertConfirmation("Seguro que desea crear el proveedor?") == 1) {
                    sacaEstadoProv();
                    Sentencias.crud("Call crearProveedor('" + nombreProv.getText()
                            + "','"
                            + direccionProv.getText() + "','" + telefonoProv.getText() + "','" + valProv + "')");
                    Alerta.mostrarAlertWarning("SE AGREGO EL PROVEEDOR!!");
                    Sentencias.crud("Call crearRegistroBitacora('Se agrego el proveedor: " + nombreProv.getText()
                            + "','INVENTARIO','"
                            + Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                            + "')");
                    limpiarProv();
                    llenaListaProv("CALL retornaProveedoresActivos;");
                }
            } else {
                Alerta.mostrarAlertWarning("RELLENE TODOS LOS CAMPOS!!");
            }
        }
        if (guardarProv.getText().equals("MODIFICAR")) {
            if (!nombreProv.getText().isEmpty()
                    && !direccionProv.getText().isEmpty() && !telefonoProv.getText().isEmpty()
                    && (actProv.isSelected() || inacProv.isSelected())) {
                if (Alerta.mostrarAlertConfirmation("Seguro que desea modificar el proveedor?") == 1) {
                    sacaEstadoProv();
                    Sentencias
                            .crud("call modificarProveedor('" + codigoProv.getText() + "','" + nombreProv.getText()
                                    + "','"
                                    + direccionProv.getText() + "','" + telefonoProv.getText() + "','" + valProv
                                    + "');");
                    Alerta.mostrarAlertWarning("SE MODIFICO EL PROVEEDOR!!");
                    Sentencias.crud("Call crearRegistroBitacora('Se modifico el proveedor: " + nombreProv.getText()
                            + "','INVENTARIO','"
                            + Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                            + "')");
                    limpiarProv();
                    llenaListaProv("CALL retornaProveedoresActivos;");
                }
            } else {
                Alerta.mostrarAlertWarning("RELLENE TODOS LOS CAMPOS!!");
            }
        }
        if (guardarProv.getText().equals("ELIMINAR")) {
            if (listProv.getSelectionModel().getSelectedIndex() != -1) {
                if (Alerta.mostrarAlertConfirmation("Seguro que desea eliminar el proveedor?") == 1) {
                    Sentencias.crud("call eliminarProveedor('" + codigoProv.getText() + "')");
                    Alerta.mostrarAlertInfo("Se Elimino");
                    Sentencias.crud("Call crearRegistroBitacora('Se elimino el proveedor: " + nombreProv.getText()
                            + "','INVENTARIO','"
                            + Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                            + "')");
                    limpiarProv();
                    llenaListaProv("CALL retornaProveedoresActivos;");
                }
            } else {
                Alerta.mostrarAlertInfo("Seleccione un proveedor!!");
            }
        }
        if (guardarProv.getText().equals("RESTAURAR")) {
            if (listProv.getSelectionModel().getSelectedIndex() != -1) {
                if (Alerta.mostrarAlertConfirmation("Seguro que desea restaurar el proveedor?") == 1) {
                    Sentencias.crud("call restaurarProveedor('" + codigoProv.getText() + "')");
                    Alerta.mostrarAlertInfo("Se Restauro");
                    Sentencias.crud("Call crearRegistroBitacora('Se restauro el proveedor: " + nombreProv.getText()
                            + "','INVENTARIO','"
                            + Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                            + "')");
                    limpiarProv();
                    llenaListaProv("CALL retornaProveedoresInactivos;");
                }
            } else {
                Alerta.mostrarAlertInfo("Seleccione un proveedor!!");
            }
        }
    }

    public void camposProv() {
        buscarProv.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.letrasNumeros(event, buscarProv, true, 15));
        codigoProv.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.letrasNumeros(event, codigoProv, false, 15));
        nombreProv.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.letras(event, nombreProv, true, 15));
        telefonoProv.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.enteros(event, telefonoProv, 15));
        direccionProv.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.letrasNumeros(event, direccionProv, true, 150));
    }

    public void toltipProv() {
        codigoProv.setTooltip(new Tooltip("Código del proveedor"));
        nombreProv.setTooltip(new Tooltip("Nombre del proveedor"));
        direccionProv.setTooltip(new Tooltip("Dirección del proveedor"));
        telefonoProv.setTooltip(new Tooltip("Telefono del proveedor"));

    }

    /////////////////////////////////////////////////////// LOGICA PARA
    /////////////////////////////////////////////////////// CLIENTE//////////////////////////////////////////////////////////////

    public void togleButtonCli() {
        estadoCli = new ToggleGroup();

        actCli.setToggleGroup(estadoCli);
        inacCli.setToggleGroup(estadoCli);
    }

    private void llenaListaCli(String sql) {

        ArrayList<Cliente> clientes = Sentencias.retornaClientes(sql);
        Cliente cliente;
        listCli.getItems().clear();
        listCliAux.getItems().clear();
        for (int i = 0; i < clientes.size(); i++) {
            cliente = clientes.get(i);
            listCli.getItems().add(cliente.getNombre()+" "+cliente.getApellido());
            listCliAux.getItems().add(cliente.getCedula());
        }

    }

    private void llenaCamposClientes(String sql) {
        String code = listCliAux.getItems().get(listCli.getSelectionModel().getSelectedIndex()) + "";
        ArrayList<Cliente> clientes = Sentencias.retornaClientes(sql);
        Cliente cliente;
        for (int i = 0; i < clientes.size(); i++) {
            cliente = clientes.get(i);
            if (code.equals(cliente.getCedula())) {
                cedulaAux = cliente.getCedula();
                codigoCli.setText(cliente.getCedula() + "");
                nombreCli.setText(cliente.getNombre());
                apellidoCli.setText(cliente.getApellido());
                telefonoCli.setText(cliente.getTelefono() + "");
                correoCli.setText(cliente.getCorreo() + "");
                rtnCli.setText(cliente.getRtn() + "");
                laSaldo.setText("Deuda: "
                        + Sentencias.retornaCredito("Call retornaCreditoUnCliente('" + cliente.getCedula() + "')")
                        + " Lps");
                if (cliente.getEstado() == 1) {
                    actCli.setSelected(true);
                } else {
                    inacCli.setSelected(true);
                }

                return;
            }
        }

    }

    public void enableCamposCli() {

        codigoCli.setEditable(true);
        nombreCli.setEditable(true);
        apellidoCli.setEditable(true);
        telefonoCli.setEditable(true);
        correoCli.setEditable(true);
        rtnCli.setEditable(true);
        actCli.setDisable(false);
        inacCli.setDisable(false);
        guardarCli.setDisable(false);
    }

    public <T> void detectaCli() {

        listCliAux = new ListView();

        listCli.setStyle("-fx-font: 18px \"System\";");

        llenaListaCli("CALL retornaClientesActivos();");
        listCli.getSelectionModel().selectedIndexProperty()
                .addListener((ChangeListener<? super Number>) new ChangeListener<T>() {

                    @Override
                    public void changed(ObservableValue<? extends T> arg0, T arg1, T arg2) {

                        // codigo.setText(listProd.getSelectionModel().getSelectedItem().toString());
                        // System.out.print(listProv.getSelectionModel().getSelectedIndex());
                        if (listCli.getSelectionModel().getSelectedIndex() > -1) {
                            llenaCamposClientes("CALL retornaClientes();");
                        }
                        if (guardarCli.getText().equals("MODIFICAR")) {
                            enableCamposCli();
                        }

                    }

                });

        addCli.setTooltip(new Tooltip("Presione para crear un cliente"));
        addCli.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "71")) {
                botonGuardarCli();
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        modCli.setTooltip(new Tooltip("Presione para modificar un cliente"));
        modCli.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "72")) {
                botonModificarCli();
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        delCli.setTooltip(new Tooltip("Presione para eliminar un cliente"));
        delCli.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "73")) {
                botonEliminarCli();
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        resCli.setTooltip(new Tooltip("Presione para restaurar un cliente eliminado"));
        resCli.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "74")) {
                botonRestaurarCli();
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        guardarCli.setOnAction(event -> {
            accionesCli(event);
        });

        findCli.setOnAction(event -> {
            if (!guardarCli.getText().equals("RESTAURAR")) {
                Sentencias.buscarProd("CALL retornaClientesActivos", buscarCli.getText(), new ComboBox<>(), listCli,
                        listCliAux);
            } else {
                Sentencias.buscarProd("CALL retornaClientesInactivos", buscarCli.getText(), new ComboBox<>(),
                        listCli, listCliAux);
            }

        });
    }

    private void botonRestaurarCli() {
        listCli.getItems().clear();
        listCliAux.getItems().clear();
        llenaListaCli("CALL retornaClientesInactivos;");
        guardarCli.setText("RESTAURAR");
        limpiarCli();
        disableCamposCli();
        guardarCli.setDisable(false);
        listCli.setDisable(false);
    }

    private void botonEliminarCli() {
        listCli.getItems().clear();
        listCliAux.getItems().clear();
        llenaListaCli("CALL retornaClientesActivos;");
        guardarCli.setText("ELIMINAR");
        limpiarCli();
        disableCamposCli();
        guardarCli.setDisable(false);
        listCli.setDisable(false);
    }

    private void botonModificarCli() {
        codigoCli.setEditable(false);
        listCli.getItems().clear();
        listCliAux.getItems().clear();
        llenaListaCli("CALL retornaClientesActivos;");
        guardarCli.setText("MODIFICAR");
        limpiarCli();
        disableCamposCli();
        listCli.setDisable(false);
    }

    private void botonGuardarCli() {

        listCli.getItems().clear();
        listCliAux.getItems().clear();
        llenaListaCli("CALL retornaClientesActivos;");
        guardarCli.setText("AGREGAR");
        limpiarCli();
        enableCamposCli();
        listCli.setDisable(true);
    }

    public void limpiarCli() {

        codigoCli.clear();
        nombreCli.clear();
        apellidoCli.clear();
        telefonoCli.clear();
        correoCli.clear();
        rtnCli.clear();
        actCli.setSelected(false);
        inacCli.setSelected(false);
        laSaldo.setText("Deuda: ");
    }

    public void disableCamposCli() {

        codigoCli.setEditable(false);
        nombreCli.setEditable(false);
        apellidoCli.setEditable(false);
        telefonoCli.setEditable(false);
        correoCli.setEditable(false);
        rtnCli.setEditable(false);
        actCli.setDisable(true);
        inacCli.setDisable(true);
        guardarCli.setDisable(true);

    }

    public void sacaEstadoCli() {
        if (actCli.isSelected()) {
            valCli = 1;
        } else if (inacCli.isSelected()) {
            valCli = 0;
        }
    }

    private void accionesCli(ActionEvent event) {
        if (guardarCli.getText().equals("AGREGAR")) {
            if (!codigoCli.getText().isEmpty() && !nombreCli.getText().isEmpty()
                    && !apellidoCli.getText().isEmpty()
                    && !telefonoCli.getText().isEmpty()
                    && !correoCli.getText().isEmpty()
                    && !rtnCli.getText().isEmpty()
                    && (actCli.isSelected() || inacCli.isSelected())) {
                if(validaLongitudCedula(codigoCli.getText())){
                    if (Sentencias.validaNickUsuario("CALL validaCedulaCliente('" + codigoCli.getText() + "')") == 0) {
                        if (Alerta.mostrarAlertConfirmation("Seguro que desea crear el cliente?") == 1) {
                            sacaEstadoCli();
                            Sentencias.crud("Call crearCliente('" + codigoCli.getText() + "','" + nombreCli.getText()
                                    + "','"
                                    + apellidoCli.getText() + "','" + telefonoCli.getText() + "','" + correoCli.getText()
                                    + "','" + rtnCli.getText() + "','" + valCli + "')");
                            Alerta.mostrarAlertWarning("SE AGREGO EL CLIENTE!!");
                            Sentencias.crud("Call crearRegistroBitacora('Se agrego el cliente: " + nombreCli.getText()
                                    + "','INVENTARIO','" + Sentencias.retornaUserCodigo(
                                            "Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                    + "')");
                            limpiarCli();
                            llenaListaCli("CALL retornaClientesActivos;");
                        }
                    } else {
                        Alerta.mostrarAlertWarning("El cliente ya fue registrado anteriormente!!");
                    }
                }else{
                    Alerta.mostrarAlertWarning("La longitud de la identidad no es valida!!");
                }
            } else {
                Alerta.mostrarAlertWarning("RELLENE TODOS LOS CAMPOS!!");
            }
        }
        if (guardarCli.getText().equals("MODIFICAR")) {
            if (!codigoCli.getText().isEmpty() && !nombreCli.getText().isEmpty()
                    && !apellidoCli.getText().isEmpty() && !telefonoCli.getText().isEmpty()
                    && !correoCli.getText().isEmpty() && !rtnCli.getText().isEmpty()
                    && (actCli.isSelected() || inacCli.isSelected())) {
                if (validaLongitudCedula(codigoCli.getText())) {
                    if (codigoCli.getText().equals(cedulaAux)) {
                        if (Alerta.mostrarAlertConfirmation("Seguro que desea modificar el cliente?") == 1) {
                            sacaEstadoCli();
                            Sentencias
                                    .crud("call modificarCliente('" + codigoCli.getText() + "','" + nombreCli.getText()
                                            + "','"
                                            + apellidoCli.getText() + "','" + telefonoCli.getText() + "','"
                                            + correoCli.getText() + "','"
                                            + rtnCli.getText() + "','" + valCli + "');");
                            Alerta.mostrarAlertWarning("SE MODIFICO EL CLIENTE!!");
                            Sentencias.crud("Call crearRegistroBitacora('Se modifico el cliente: " + nombreCli.getText()
                                    + "','INVENTARIO','"
                                    + Sentencias.retornaUserCodigo(
                                            "Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                    + "')");
                            limpiarCli();
                            llenaListaCli("CALL retornaClientesActivos;");
                        }
                    } else {
                        if (Sentencias
                                .validaNickUsuario("CALL validaCedulaCliente('" + codigoCli.getText() + "')") == 0) {
                            if (Alerta.mostrarAlertConfirmation("Seguro que desea modificar el cliente?") == 1) {
                                sacaEstadoCli();
                                Sentencias
                                        .crud("call modificarClienteConCedula('" + codigoCli.getText() + "','"
                                                + nombreCli.getText() + "','"
                                                + apellidoCli.getText() + "','" + telefonoCli.getText() + "','"
                                                + correoCli.getText() + "','"
                                                + rtnCli.getText() + "','" + valCli + "','" + cedulaAux + "');");
                                Alerta.mostrarAlertWarning("SE MODIFICO EL CLIENTE!!");
                                Sentencias.crud(
                                        "Call crearRegistroBitacora('Se modifico el cliente: " + nombreCli.getText()
                                                + "','INVENTARIO','"
                                                + Sentencias.retornaUserCodigo(
                                                        "Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                                + "')");
                                limpiarCli();
                                llenaListaCli("CALL retornaClientesActivos;");
                            }
                        } else {
                            Alerta.mostrarAlertWarning("El cliente ya fue registrado anteriormente!!");
                        }
                    }
                } else {
                    Alerta.mostrarAlertWarning("La longitud de la identidad no es valida!!");
                }
            } else {
                Alerta.mostrarAlertWarning("RELLENE TODOS LOS CAMPOS!!");
            }
        }
        if (guardarCli.getText().equals("ELIMINAR")) {
            if (listCli.getSelectionModel().getSelectedIndex() != -1) {
                if (Sentencias.retornaCredito("Call retornaCreditoUnCliente('" + codigoCli.getText() + "')") == 0.0) {
                    if (Alerta.mostrarAlertConfirmation("Seguro que desea eliminar el cliente?") == 1) {
                        Sentencias.crud("call eliminarCliente('" + codigoCli.getText() + "')");
                        Alerta.mostrarAlertInfo("Se Elimino");
                        Sentencias.crud("Call crearRegistroBitacora('Se elimino el cliente: " + nombreCli.getText()
                                + "','INVENTARIO','" + Sentencias.retornaUserCodigo(
                                        "Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                + "')");
                        limpiarCli();
                        llenaListaCli("CALL retornaClientesActivos;");
                    }
                } else {
                    Alerta.mostrarAlertWarning("No se pueden eliminar clientes con saldo pendiente!!");
                }
            } else {
                Alerta.mostrarAlertInfo("Seleccione un cliente!!");
            }
        }
        if (guardarCli.getText().equals("RESTAURAR")) {
            if (listCli.getSelectionModel().getSelectedIndex() != -1) {
                if (Alerta.mostrarAlertConfirmation("Seguro que desea restaurar el cliente?") == 1) {
                    Sentencias.crud("call restaurarCliente('" + codigoCli.getText() + "')");
                    Alerta.mostrarAlertInfo("Se Restauro");
                    Sentencias.crud("Call crearRegistroBitacora('Se restauro el cliente: " + nombreCli.getText()
                            + "','INVENTARIO','"
                            + Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                            + "')");
                    limpiarCli();
                    llenaListaCli("CALL retornaClientesInactivos;");
                }
            } else {
                Alerta.mostrarAlertInfo("Seleccione un cliente!!");
            }
        }
    }

    public void camposCli() {
        buscarCli.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.letrasNumeros(event, buscarCli, true, 15));
        codigoCli.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.enteros(event, codigoCli, 13));
        nombreCli.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.letras(event, nombreCli, true, 15));
        apellidoCli.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letras(event, apellidoCli, true, 15));
        telefonoCli.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.enteros(event, telefonoCli, 15));
        // correoCli.addEventHandler(KeyEvent.KEY_TYPED, event ->
        // ValidacionCampos.letrasNumeros(event, correoCli, true, 15));
        rtnCli.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.enteros(event, rtnCli, 14));
    }

    public void toltipCli() {
        codigoCli.setTooltip(new Tooltip("Cédula del cliente"));
        nombreCli.setTooltip(new Tooltip("Nombre del cliente"));
        apellidoCli.setTooltip(new Tooltip("Apellido del cliente"));
        telefonoCli.setTooltip(new Tooltip("Telefono del cliente"));
        correoCli.setTooltip(new Tooltip("Correo del cliente"));
        rtnCli.setTooltip(new Tooltip("RTN del cliente"));

    }

    public boolean validaLongitudCedula(String cedula) {
        if (cedula.length() == 13) {
            return true;
        } else {
            return false;
        }
    }

    ////////////////////////////////////////// LOGICA PARA
    ////////////////////////////////////////// CATEGORIA//////////////////////////////////////////////////////////////////

    public <T> void detectaCat() {

        catNombresAux = new ComboBox<String>();
        estadoCat = new ToggleGroup();

        con.setToggleGroup(estadoCat);
        sin.setToggleGroup(estadoCat);

        catNombres.setStyle("-fx-font: 18px \"System\";");
        catDesc.setStyle("-fx-font: 18px \"System\";");
        for (int i = 0; i < 51; i++) {
            catDesc.getItems().add(i + "%");
        }

        btnNewCat.setOnAction(event -> {
            if (!addCat.getText().isEmpty()) {
                if (Sentencias.validaNombreCategoria("Call validaCategoriaNombre('" + addCat.getText() + "');") != 1) {
                    if (Alerta.mostrarAlertConfirmation("Seguro que desea crear la categoría?") == 1) {
                        Sentencias.crud("call crearCategoria('" + addCat.getText() + "','" + 0 + "')");
                        Alerta.mostrarAlertWarning("Se creo una nueva Categoría!!");
                        Sentencias.crud("Call crearRegistroBitacora('Se creo una nueva Categoría: " + addCat.getText()
                                + "','INVENTARIO','"
                                + Sentencias
                                        .retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                + "')");
                        clearCat();
                        if (con.isSelected()) {
                            Sentencias.llenaComboBox("call retornaCategoriasConDescuento()", catNombres, catNombresAux);
                            catNombres.setValue("Categoría (Ninguna)");
                        } else if (sin.isSelected()) {
                            Sentencias.llenaComboBox("call retornaCategoriasSinDescuento()", catNombres, catNombresAux);
                            catNombres.setValue("Categoría (Ninguna)");
                        }
                    }
                } else {

                    if (Sentencias.retornaCategoriaEstado("Call retornaCategoriaEstado('" + addCat.getText() + "');")
                            .equals("0")) {
                        if (Alerta.mostrarAlertConfirmation(
                                "Ya existe una categoría con ese nombre, pero fue borrada anteriormente, desea restaurarla?") == 1) {
                            Sentencias.crud("Call restaurarCategoria('" + addCat.getText() + "');");
                            Alerta.mostrarAlertInfo("La categoría fue restaurada!");
                            Sentencias.crud("Call crearRegistroBitacora('Se restauro la categoría: " + addCat.getText()
                                    + "','INVENTARIO','"
                                    + Sentencias.retornaUserCodigo(
                                            "Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                    + "')");
                        }
                    } else {
                        Alerta.mostrarAlertWarning("Ya existe una categoría con ese nombre!!");
                    }
                }
            } else {
                Alerta.mostrarAlertWarning("Ingrese el nombre de la Nueva Categoría!!");
            }
        });

        con.setOnAction(event -> {
            Sentencias.llenaComboBox("call retornaCategoriasConDescuento()", catNombres, catNombresAux);
            catNombres.setValue("Categoría (Ninguna)");
        });

        sin.setOnAction(event -> {
            Sentencias.llenaComboBox("call retornaCategoriasSinDescuento()", catNombres, catNombresAux);
            catNombres.setValue("Categoría (Ninguna)");
        });

        catNombres.setOnAction(event -> {
            if (catNombres.getSelectionModel().getSelectedItem() != null && !catNombres.getSelectionModel().getSelectedItem().equals("Categoría (Ninguna)")) {
                if (con.isSelected()) {
                    Sentencias.retornaCategoria("Call retornaCategoriasConDescuento()", catNombre, catDescrip, catDesc,
                            catNombresAux.getItems().get(catNombres.getSelectionModel().getSelectedIndex()).toString());
                } else if (sin.isSelected()) {
                    Sentencias.retornaCategoria("Call retornaCategoriasSinDescuento()", catNombre, catDescrip, catDesc,
                            catNombresAux.getItems().get(catNombres.getSelectionModel().getSelectedIndex()).toString());
                }

            }
        });

        btnModCat.setOnAction(event -> {
            modCategotia(event);
        });

        btnDelCat.setOnAction(event -> {
            delCategoria(event);
        });
    }

    public void delCategoria(ActionEvent event) {
        if (catNombres.getSelectionModel().getSelectedIndex() != -1) {
            if (Alerta.mostrarAlertConfirmation(
                    "Seguro que desea borrar esta categoría? todos los productos pertenecientes a esta categoría seran desvinculados de ella, asi como de algun posible descuento aplicado a la misma!") == 1) {
                Sentencias.crud("Call modificarCategoriaProducto('" + catNombre.getText() + "');");
                Sentencias.crud("Call eliminarCategoria('"
                        + catNombresAux.getItems().get(catNombres.getSelectionModel().getSelectedIndex()).toString()
                        + "');");
                Alerta.mostrarAlertWarning("Se elimino la categoría: " + catNombre.getText());
                Sentencias.crud("Call crearRegistroBitacora('Se Elimino la categoría: "
                        + catNombre.getText() + "','INVENTARIO','" + Sentencias
                                .retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                        + "')");
                clearCat();
            }
        } else {
            Alerta.mostrarAlertWarning("Seleccione una categoría!!");
        }
    }

    public void modCategotia(ActionEvent event) {
        if (catNombres.getSelectionModel().getSelectedItem() != null) {
            if (!catNombres.getSelectionModel().getSelectedItem().equals("Categoría (Ninguna)")) {
                if (!catNombre.getText().isEmpty()
                        && !catDescrip.getText().isEmpty()
                        && catDesc.getSelectionModel().getSelectedItem() != null) {
                    String pala = catDesc.getSelectionModel().getSelectedItem().toString();
                    String[] arre = pala.split("%");
                    if (Alerta.mostrarAlertConfirmation("Seguro que desea modificar la categoría?") == 1) {
                        if (catDesc.getSelectionModel().getSelectedItem().equals("0%")) {
                            Sentencias.crud("Call modificarCategoria('"
                                    + catNombresAux.getItems()
                                            .get(catNombres.getSelectionModel().getSelectedIndex())
                                            .toString()
                                    + "','" + catNombre.getText() + "','" + catDescrip.getText() + "','" + 0 + "','"
                                    + 0
                                    + "')");
                            Sentencias.crud("Call modificarDescuentoProductos('" + 0 + "','"
                                    + catNombres.getSelectionModel().getSelectedItem().toString() + "')");
                        } else {
                            Sentencias.crud("Call modificarCategoria('"
                                    + catNombresAux.getItems()
                                            .get(catNombres.getSelectionModel().getSelectedIndex())
                                            .toString()
                                    + "','" + catNombre.getText() + "','" + catDescrip.getText() + "','" + 1 + "','"
                                    + arre[0] + "')");
                            Sentencias.crud("Call modificarDescuentoProductos('" + arre[0] + "','"
                                    + catNombres.getSelectionModel().getSelectedItem().toString() + "')");
                        }
                        Alerta.mostrarAlertInfo("Se modifico la categoría!!");
                        Sentencias.crud("Call crearRegistroBitacora('Se modifico la categoría: "
                                + catNombre.getText() + " con un descuento de "
                                + catDesc.getSelectionModel().getSelectedItem() + "','INVENTARIO','" + Sentencias
                                        .retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                + "')");
                        clearCat();
                    }
                } else {
                    Alerta.mostrarAlertWarning("Rellene todos los campos");
                }
            } else {
                Alerta.mostrarAlertWarning("Seleccione la categoría que desea modificar!!");
            }
        } else {
            Alerta.mostrarAlertWarning("Seleccione la categoría que desea modificar!!");
        }
    }

    public void camposCat() {
        addCat.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.letrasNumeros(event, addCat, true, 15));
        catNombre.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.letrasNumeros(event, catNombre, true, 15));
    }

    public String retornaUsuario(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        String[] usuario = stage.getTitle().split("\\s+");

        return usuario[usuario.length - 1];
    }

    public void clearCat() {
        addCat.clear();
        catNombre.clear();
        catDescrip.clear();
        catNombres.getItems().clear();
        catDesc.setValue("0%");
        con.setSelected(false);
        sin.setSelected(false);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void agregaIconos() {
        add.setGraphic(new ImageView("/Images/add.png"));
        mod.setGraphic(new ImageView("/Images/mod.png"));
        del.setGraphic(new ImageView("/Images/del.png"));
        res.setGraphic(new ImageView("/Images/res.png"));
        find.setGraphic(new ImageView("/Images/search.png"));
        btnProd.setGraphic(new ImageView("/Images/product.png"));
        btnCli.setGraphic(new ImageView("/Images/client.png"));
        btnProv.setGraphic(new ImageView("/Images/provider.png"));
        btnCat.setGraphic(new ImageView("/Images/cat.png"));
        btnAtras.setGraphic(new ImageView("/Images/backButton.png"));
        btnSalir.setGraphic(new ImageView("/Images/logoutButton.png"));

        addCli.setGraphic(new ImageView("/Images/add.png"));
        modCli.setGraphic(new ImageView("/Images/mod.png"));
        delCli.setGraphic(new ImageView("/Images/del.png"));
        resCli.setGraphic(new ImageView("/Images/res.png"));
        findCli.setGraphic(new ImageView("/Images/search.png"));

        addProv.setGraphic(new ImageView("/Images/add.png"));
        modProv.setGraphic(new ImageView("/Images/mod.png"));
        delProv.setGraphic(new ImageView("/Images/del.png"));
        resProv.setGraphic(new ImageView("/Images/res.png"));
        findProv.setGraphic(new ImageView("/Images/search.png"));

        btnDelCat.setGraphic(new ImageView("/Images/del.png"));
        btnNewCat.setGraphic(new ImageView("/Images/save.png"));
    }

    public void focus() {
        codigo.setFocusTraversable(false);
        nombre.setFocusTraversable(false);
        descrip.setFocusTraversable(false);
        precio_venta.setFocusTraversable(false);
        costo.setFocusTraversable(false);
        isv.setFocusTraversable(false);
        utili.setFocusTraversable(false);
        impuesto.setFocusTraversable(false);
        guardar.setFocusTraversable(false);
        add.setFocusTraversable(false);
        utilidad.setFocusTraversable(false);
        categoria.setFocusTraversable(false);
        filtro.setFocusTraversable(false);
        inventario_max.setFocusTraversable(false);
        inventario_min.setFocusTraversable(false);
        find.setFocusTraversable(false);
        del.setFocusTraversable(false);
        mod.setFocusTraversable(false);
        res.setFocusTraversable(false);
        buscar.setFocusTraversable(false);
        descrip.setFocusTraversable(false);
        listProd.setFocusTraversable(false);
        listCli.setFocusTraversable(false);
        listProv.setFocusTraversable(false);
        act.setFocusTraversable(false);
        inac.setFocusTraversable(false);
        categoria.setFocusTraversable(false);

        btnProd.setFocusTraversable(true);
    }

    public void toltipCat() {
        addCat.setTooltip(new Tooltip("Agregar nueva categoría"));
        catNombres.setTooltip(new Tooltip("Categorías"));
        catNombre.setTooltip(new Tooltip("Nombre de la categoría"));
        catDescrip.setTooltip(new Tooltip("DESCRIPCIÓN DE LA CATEGORÍA"));
        catDesc.setTooltip(new Tooltip("Descuento de la categoría"));
        btnModCat.setTooltip(new Tooltip("Modifica la categoría"));
        btnDelCat.setTooltip(new Tooltip("Elimina de la categoría"));

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        boxAll.getChildren().clear();
        boxAll.getChildren().add(boxProd);

        imaLogo.setImage(Sentencias.retornaEmpresaLogo("Call retornaLogoEmpresa()"));

        campos();
        disableCampos();
        detecta();
        togleButton();

        camposProv();
        disableCamposProv();
        detectaProv();
        togleButtonProv();

        camposCli();
        disableCamposCli();
        detectaCli();
        togleButtonCli();

        camposCat();
        detectaCat();

        agregaIconos();
        // focus();
        toltipProd();
        toltipProv();
        toltipCli();
        toltipCat();

    }
}