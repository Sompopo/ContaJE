package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import data.Alerta;
import data.Sentencias;
import data.ValidacionCampos;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Cuenta;

public class registroCuentasController implements Initializable {

    private ToggleGroup estado;
    private ListView listCuentasAux;
    private String codigoAux;
    private int val;

    @FXML
    private RadioButton act;

    @FXML
    private Button add;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnGuardar;

    @FXML
    private TextField buscar;

    @FXML
    private TextField codigo;

    @FXML
    private Button del;

    @FXML
    private TextArea descrip;

    @FXML
    private RadioButton inac;

    @FXML
    private ListView<String> listCuentas;

    @FXML
    private Button mod;

    @FXML
    private TextField nombre;

    @FXML
    private TextField order;

    @FXML
    private Button res;

    @FXML
    private ComboBox<String> tipo;

    public <T> void detecta() {

        listCuentasAux = new ListView<>();

        llenaListCuentas("Call retornaListaCuentasActivas();");

        estado = new ToggleGroup();

        act.setToggleGroup(estado);
        inac.setToggleGroup(estado);

        tipo.setStyle("-fx-font: 18px \"System\";");
        tipo.getItems().add("Activo");
        tipo.getItems().add("Pasivo");
        tipo.getItems().add("Ingreso");
        tipo.getItems().add("Gasto");
        tipo.getItems().add("Costo");
        tipo.getItems().add("Capital/Patrimonio");

        listCuentas.setStyle("-fx-font: 18px \"System\";");

        listCuentas.getSelectionModel().selectedIndexProperty()
                .addListener((ChangeListener<? super Number>) new ChangeListener<T>() {

                    @Override
                    public void changed(ObservableValue<? extends T> arg0, T arg1, T arg2) {

                        // codigo.setText(listProd.getSelectionModel().getSelectedItem().toString());
                        // System.out.print(listUserAux.getItems().get(listUser.getSelectionModel().getSelectedIndex()));
                        if (listCuentas.getSelectionModel().getSelectedIndex() > -1) {
                            llenaCamposCuenta("CALL retornaListaCuentas;");
                        }
                        if (btnGuardar.getText().equals("MODIFICAR")) {
                            enableCampos();
                            // codigo.setEditable(false);
                        }

                    }

                });

        add.setTooltip(new Tooltip("Presione para crear una cuenta"));
        add.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "361")) {
                botonGuardar();
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        mod.setTooltip(new Tooltip("Presione para modificar una cuenta"));
        mod.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "362")) {
                botonModificar();
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        del.setTooltip(new Tooltip("Presione para eliminar una cuenta"));
        del.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "363")) {
                botonEliminar();
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        res.setTooltip(new Tooltip("Presione para restaurar una cuenta eliminada"));
        res.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "364")) {
                botonRestaurar();
            } else {
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        btnGuardar.setOnAction(event -> {
            acciones(event);
        });

        btnBuscar.setOnAction(event -> {
            if (!btnGuardar.getText().equals("RESTAURAR")) {
                Sentencias.buscarCuenta("call retornaListaCuentasActivas()", buscar.getText(), listCuentas,
                        listCuentasAux);
                ;
            } else {
                Sentencias.buscarCuenta("call retornaListaCuentasInactivas()", buscar.getText(), listCuentas,
                        listCuentasAux);
            }

        });
    }

    public void sacaEstado() {
        if (act.isSelected()) {
            val = 1;
        } else if (inac.isSelected()) {
            val = 0;
        }
    }

    private void acciones(ActionEvent event) {
        if (btnGuardar.getText().equals("AGREGAR")) {
            if (!codigo.getText().isEmpty()
                    && !nombre.getText().isEmpty()
                    && !order.getText().isEmpty()
                    && !descrip.getText().isEmpty()
                    && !tipo.getSelectionModel().getSelectedItem().equals("Categoria (NINGUNA)")
                    && (act.isSelected() || inac.isSelected())) {
                if (Sentencias.validaNickUsuario("CALL validaNickUsuario('" + codigo.getText() + "')") != 1) {
                    if (Sentencias.validaCuentaCodigo("call validaCuentaCodigo('" + codigo.getText() + "')") == 0) {
                        if (validaLongitudCodigo(codigo.getText())) {
                            if (Alerta.mostrarAlertConfirmation("Seguro que desea crear la cuenta?") == 1) {
                                sacaEstado();
                                Sentencias
                                        .crud("Call crearCuenta('" + codigo.getText() + "','" + nombre.getText() + "','"
                                                + tipo.getSelectionModel().getSelectedItem() + "','"
                                                + Sentencias.retornaUserCodigo(
                                                        "Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                                + "','" + descrip.getText()
                                                + "','" + order.getText() + "','" + val + "')");
                                Sentencias.crud("Call crearRegistroBitacora('Se creo la cuenta: " + nombre.getText()
                                        + "','USUARIOS','" + Sentencias.retornaUserCodigo(
                                                "Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                        + "')");
                                Alerta.mostrarAlertWarning("SE AGREGO LA CUENTA!!");
                                limpiar();
                                llenaListCuentas("call retornaListaCuentasActivas();");
                            }
                        } else {
                            Alerta.mostrarAlertWarning("Longitud del código no valida!!");
                        }
                    } else {
                        Alerta.mostrarAlertWarning("El código de la cuenta ya esta en uso, pruebe con otro!!");
                    }
                } else {
                    Alerta.mostrarAlertWarning("EL CÓDIGO DE LA CUENTA YA ESTA EN USO, INTENTE CON OTRO!!");
                }
            } else {
                Alerta.mostrarAlertWarning("RELLENE TODOS LOS CAMPOS!!");
            }
        }
        if (btnGuardar.getText().equals("MODIFICAR")) {
            if (!nombre.getText().isEmpty()
                    && !nombre.getText().isEmpty()
                    && !order.getText().isEmpty()
                    && !descrip.getText().isEmpty()
                    && !tipo.getSelectionModel().getSelectedItem().equals("Categoria (NINGUNA)")
                    && (act.isSelected() || inac.isSelected())) {
                if (validaLongitudCodigo(codigo.getText())) {
                    sacaEstado();
                    if (!codigo.getText().equals(codigoAux)) {
                        if (Sentencias
                                .validaCuentaCodigo("call validaCuentaCodigo('" + codigo.getText() + "')") == 0) {
                            if (Alerta.mostrarAlertConfirmation("Seguro que desea modificar la cuenta!!") == 1) {

                                Sentencias.crud("Call modificarCuentaConCodigo('" + codigo.getText() + "','"
                                        + nombre.getText() + "','"
                                        + tipo.getSelectionModel().getSelectedItem() + "','"
                                        + Sentencias.retornaUserCodigo(
                                                "Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                        + "','" + descrip.getText()
                                        + "','" + order.getText() + "','" + val + "','" + codigoAux + "')");

                                Sentencias.crud("Call crearRegistroBitacora('Se modifico la cuenta: " + nombre.getText()
                                        + "','USUARIOS','" + Sentencias.retornaUserCodigo(
                                                "Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                        + "')");

                                Alerta.mostrarAlertWarning("SE MODIFICO LA CUENTA!!");
                                limpiar();
                                llenaListCuentas("call retornaListaCuentasActivas();");

                            }
                        } else {
                            Alerta.mostrarAlertWarning("El código de la cuenta ya esta en uso, pruebe con otro!!");
                        }
                    } else {
                        if (Alerta.mostrarAlertConfirmation("Seguro que desea modificar la cuenta!!") == 1) {

                            Sentencias.crud("Call modificarCuenta('" + codigo.getText() + "','"
                                    + nombre.getText() + "','"
                                    + tipo.getSelectionModel().getSelectedItem() + "','"
                                    + Sentencias.retornaUserCodigo(
                                            "Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                    + "','" + descrip.getText()
                                    + "','" + order.getText() + "','" + val + "')");

                            Sentencias.crud("Call crearRegistroBitacora('Se modifico la cuenta: " + nombre.getText()
                                    + "','USUARIOS','" + Sentencias.retornaUserCodigo(
                                            "Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                                    + "')");

                            Alerta.mostrarAlertWarning("SE MODIFICO LA CUENTA!!");
                            limpiar();
                            llenaListCuentas("call retornaListaCuentasActivas();");

                        }
                    }
                } else {
                    Alerta.mostrarAlertWarning("Longitud del código no valida");
                }
            } else {
                Alerta.mostrarAlertWarning("RELLENE TODOS LOS CAMPOS!!");
            }
        }
        if (btnGuardar.getText().equals("ELIMINAR")) {
            if (listCuentas.getSelectionModel().getSelectedIndex() != -1) {
                if (Alerta.mostrarAlertConfirmation("Seguro que desea eliminar la cuenta?") == 1) {
                    Sentencias.crud("call eliminarCuenta('" + codigo.getText() + "')");
                    Sentencias.crud("Call crearRegistroBitacora('Se elimino la cuenta: " + nombre.getText()
                            + "','USUARIOS','"
                            + Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                            + "')");
                    Alerta.mostrarAlertInfo("Se Elimino");
                    limpiar();
                    llenaListCuentas("call retornaListaCuentasActivas();");
                }
            } else {
                Alerta.mostrarAlertInfo("Seleccione una cuenta!!");
            }
        }
        if (btnGuardar.getText().equals("RESTAURAR")) {
            if (listCuentas.getSelectionModel().getSelectedIndex() != -1) {
                if (Alerta.mostrarAlertConfirmation("Seguro que desea restaurar la cuenta?") == 1) {
                    Sentencias.crud("call restaurarCuenta('" + codigo.getText() + "')");
                    Sentencias.crud("Call crearRegistroBitacora('Se restauro la cuenta: " + nombre.getText()
                            + "','USUARIOS','"
                            + Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")
                            + "')");
                    Alerta.mostrarAlertInfo("Se Restauro");
                    limpiar();
                    llenaListCuentas("call retornaListaCuentasInactivas();");
                }
            } else {
                Alerta.mostrarAlertInfo("Seleccione una cuenta!!");
            }
        }
    }

    private void botonRestaurar() {
        listCuentas.getItems().clear();
        listCuentasAux.getItems().clear();
        llenaListCuentas("CALL retornaListaCuentasInactivas();");
        btnGuardar.setText("RESTAURAR");
        limpiar();
        disableCampos();
        btnGuardar.setDisable(false);
        listCuentas.setDisable(false);
    }

    private void botonEliminar() {
        listCuentas.getItems().clear();
        listCuentasAux.getItems().clear();
        llenaListCuentas("Call retornaListaCuentasActivas();");
        btnGuardar.setText("ELIMINAR");
        limpiar();
        disableCampos();
        btnGuardar.setDisable(false);
        listCuentas.setDisable(false);
    }

    private void botonModificar() {
        codigo.setEditable(false);
        listCuentas.getItems().clear();
        listCuentasAux.getItems().clear();
        llenaListCuentas("Call retornaListaCuentasActivas();");
        btnGuardar.setText("MODIFICAR");
        limpiar();
        disableCampos();
        listCuentas.setDisable(false);
        codigo.setEditable(false);
    }

    private void botonGuardar() {

        listCuentas.getItems().clear();
        listCuentasAux.getItems().clear();
        llenaListCuentas("Call retornaListaCuentasActivas();");
        btnGuardar.setText("AGREGAR");
        limpiar();
        enableCampos();
        listCuentas.setDisable(true);
    }

    public void agregaIconos() {
        add.setGraphic(new ImageView("/Images/add.png"));
        mod.setGraphic(new ImageView("/Images/mod.png"));
        del.setGraphic(new ImageView("/Images/del.png"));
        res.setGraphic(new ImageView("/Images/res.png"));
        btnBuscar.setGraphic(new ImageView("/Images/search.png"));
    }

    private void llenaListCuentas(String sql) {

        ArrayList<Cuenta> cuentas = Sentencias.retornaCuentas(sql);
        Cuenta cuenta = new Cuenta();
        listCuentas.getItems().clear();
        listCuentasAux.getItems().clear();
        for (int i = 0; i < cuentas.size(); i++) {
            cuenta = cuentas.get(i);
            listCuentas.getItems().add(cuenta.getNombre());
            listCuentasAux.getItems().add(cuenta.getCodigo());
        }
    }

    private void llenaCamposCuenta(String sql) {
        String code = listCuentasAux.getItems().get(listCuentas.getSelectionModel().getSelectedIndex()) + "";
        ArrayList<Cuenta> cuentas = Sentencias.retornaCuentas(sql);
        Cuenta cuenta = new Cuenta();
        for (int i = 0; i < cuentas.size(); i++) {
            cuenta = cuentas.get(i);

            if (code.equals(cuenta.getCodigo())) {

                codigoAux = cuenta.getCodigo();
                codigo.setText(cuenta.getCodigo() + "");
                nombre.setText(cuenta.getNombre());
                tipo.setValue(cuenta.getTipo());
                descrip.setText(cuenta.getDescrip());
                order.setText(cuenta.getOrder());
                if (cuenta.getEstado().equals("1")) {
                    act.setSelected(true);
                } else {
                    inac.setSelected(true);
                }

                return;
            }
        }

    }

    public void enableCampos() {

        codigo.setEditable(true);
        nombre.setEditable(true);
        tipo.setDisable(false);
        order.setEditable(true);
        descrip.setEditable(true);
        act.setDisable(false);
        inac.setDisable(false);
        btnGuardar.setDisable(false);
    }

    public void limpiar() {

        codigo.clear();
        nombre.clear();
        tipo.getSelectionModel().select(0);
        order.clear();
        descrip.clear();
        act.setSelected(false);
        inac.setSelected(false);
    }

    public void disableCampos() {

        codigo.setEditable(false);
        nombre.setEditable(false);
        tipo.setDisable(true);
        order.setEditable(false);
        descrip.setEditable(false);
        act.setDisable(true);
        inac.setDisable(true);
        btnGuardar.setDisable(true);

    }

    public boolean validaLongitudCodigo(String codigo) {
        if (codigo.length() == 6) {
            return true;
        } else {
            return false;
        }
    }

    public String retornaUsuario(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        String[] usuario = stage.getTitle().split("\\s+");

        return usuario[usuario.length - 1];
    }

    public void toltip() {
        codigo.setTooltip(new Tooltip("Código de la cuenta"));
        nombre.setTooltip(new Tooltip("Nombre de la cuenta"));
        tipo.setTooltip(new Tooltip("Tipo de cuenta"));
        order.setTooltip(new Tooltip("Orden de la cuenta"));
        descrip.setTooltip(new Tooltip("Descripción de la cuenta"));
    }

    public void campos() {
        buscar.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letrasNumeros(event, buscar, true, 15));
        codigo.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.enteros(event, codigo, 6));
        nombre.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letras(event, nombre, true, 30));
        order.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.enteros(event, order, 5));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        detecta();
        agregaIconos();
        disableCampos();
        campos();
        toltip();
    }

}
