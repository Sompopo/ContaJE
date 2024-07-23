package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import data.Alerta;
import data.Encoder;
import data.Sentencias;
import data.ValidacionCampos;
import first.loadWindow;
import first.loadWindow3;
import first.loadWindow4;
import javafx.fxml.Initializable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Usuario;

public class usuarioController implements Initializable {

    private int val;
    private ToggleGroup estado;
    private ListView listUserAux;
    private ListView listAccesosAux;
    private ListView listAccesosUserAux;
    private loadWindow3 lw = new loadWindow3();
    private loadWindow4 lw4 = new loadWindow4();
    private loadWindow lww = new loadWindow();

    @FXML
    private HBox boxReport;

    @FXML
    private HBox boxUser;

    @FXML
    private VBox box1;

    @FXML
    private VBox box2;

    @FXML
    private VBox boxLateral;

    @FXML
    private TextArea descrip;

    @FXML
    private RadioButton act;

    @FXML
    private Button add;

    @FXML
    private Button addTodos;

    @FXML
    private Button addUno;

    @FXML
    private TextField apellido;

    @FXML
    private HBox boxAll;

    @FXML
    private HBox boxProd;

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnReport;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnUser;

    @FXML
    private TextField buscar;

    @FXML
    private ComboBox<String> categoria;

    @FXML
    private TextField codigo;

    @FXML
    private TextField conPasswd;

    @FXML
    private TextField correo;

    @FXML
    private Button del;

    @FXML
    private Button delTodos;

    @FXML
    private Button delUno;

    @FXML
    private Button find;

    @FXML
    private Button guardar;

    @FXML
    private RadioButton inac;

    @FXML
    private ListView<String> listAccesos;

    @FXML
    private ListView<String> listAccesosUser;

    @FXML
    private ListView<String> listUser;

    @FXML
    private Button mod;

    @FXML
    private TextField nombre;

    @FXML
    private TextField passwd;

    @FXML
    private Button res;

    @FXML
    private TextField telefono;

    @FXML
    private TextField usu;

    @FXML
    private ImageView imaLogo;

    public void togleButton() {

        box1.getStyleClass().add("vbox");

        box2.getStyleClass().add("vbox2");

        boxLateral.getStyleClass().add("vbox");

        estado = new ToggleGroup();

        act.setToggleGroup(estado);
        inac.setToggleGroup(estado);

        categoria.setStyle("-fx-font: 18px \"System\";");
        categoria.getItems().add("Categoria (NINGUNA)");
        categoria.getItems().addAll(Sentencias.retornaCategoriasAccesos("Call retornaCategoriasAccesos();"));
        categoria.setOnAction(event -> {
            listAccesos.getItems().clear();
            listAccesosAux.getItems().clear();
            Sentencias.retornaAccesos(
                    "call retornaAccesosCategoria('" + categoria.getSelectionModel().getSelectedItem() + "')",
                    listAccesos, listAccesosAux);
            if (categoria.getSelectionModel().getSelectedItem().equals("Categoria (NINGUNA)")) {
                Sentencias.retornaAccesos("call retornaAccesos()", listAccesos, listAccesosAux);
            }
        });

    }

    public <T> void detecta() {

        listUserAux = new ListView<>();
        listAccesosAux = new ListView<>();
        listAccesosUserAux = new ListView<>();

        listAccesos.setStyle("-fx-font: 18px \"System\";");
        listUser.setStyle("-fx-font: 18px \"System\";");
        listAccesosUser.setStyle("-fx-font: 18px \"System\";");
        
        descrip.setWrapText(true);

        llenaListUser("Call retornaUsuariosActivos()");

        listUser.getSelectionModel().selectedIndexProperty()
                .addListener((ChangeListener<? super Number>) new ChangeListener<T>() {

                    @Override
                    public void changed(ObservableValue<? extends T> arg0, T arg1, T arg2) {

                        // codigo.setText(listProd.getSelectionModel().getSelectedItem().toString());
                        // System.out.print(listUserAux.getItems().get(listUser.getSelectionModel().getSelectedIndex()));
                        if (listUser.getSelectionModel().getSelectedIndex() > -1) {
                            llenaCamposUsuario("CALL retornaUsuarios;");
                            llenaListAccesosUser("call retornaAccesosUsuario('"
                                    + listUserAux.getItems().get(listUser.getSelectionModel().getSelectedIndex())
                                    + "');");
                        }
                        if (guardar.getText().equals("MODIFICAR")) {
                            enableCampos();
                            codigo.setEditable(false);
                            usu.setEditable(false);
                        }

                    }

                });

        listAccesos.getSelectionModel().selectedIndexProperty()
                .addListener((ChangeListener<? super Number>) new ChangeListener<T>() {

                    @Override
                    public void changed(ObservableValue<? extends T> arg0, T arg1, T arg2) {

                        if (listAccesos.getSelectionModel().getSelectedIndex() > -1) {
                            descrip.setText(Sentencias.retonarDescripAcceso("call retornaDescripAcceso('"
                                    + listAccesosAux.getItems().get(listAccesos.getSelectionModel().getSelectedIndex())
                                    + "')"));
                        }
                    }

                });

        find.setOnAction(event -> {
            if (!guardar.getText().equals("RESTAURAR")) {
                Sentencias.buscarUser("CALL retornaUsuariosActivos", buscar.getText(), listUser, listUserAux);
                ;
            } else {
                Sentencias.buscarUser("CALL retornaUsuariosInactivos", buscar.getText(), listUser, listUserAux);
            }

        });

        addUno.setTooltip(new Tooltip("Agregar un acceso"));
        addUno.setOnAction(event -> {
            if (!codigo.getText().isEmpty()) {
                if (listAccesos.getSelectionModel().getSelectedIndex() != -1) {
                    if (Sentencias.validaAcceso("call validaAcceso('" + codigo.getText() + "','"
                            + listAccesosAux.getItems().get(listAccesos.getSelectionModel().getSelectedIndex())
                            + "')") == 1) {
                        Alerta.mostrarAlertWarning("El acceso ya esta agregado!!");
                    } else {
                        listAccesosUser.getItems().add(listAccesos.getSelectionModel().getSelectedItem());
                        listAccesosUserAux.getItems()
                                .add(listAccesosAux.getItems().get(listAccesos.getSelectionModel().getSelectedIndex()));
                        Sentencias.crud("call agregarAcceso('" + codigo.getText() + "','"
                                + listAccesosAux.getItems().get(listAccesos.getSelectionModel().getSelectedIndex())
                                + "');");
                                Sentencias.crud("Call crearRegistroBitacora('Se agrego el acceso "+listAccesos.getSelectionModel().getSelectedItem()+" al usuario: "+usu.getText()+"','USUARIOS','"+Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")+"')");
                        Alerta.mostrarAlertInfo("El acceso fue agregado!!");
                    }
                } else {
                    Alerta.mostrarAlertWarning("Seleccione un acceso!!");
                }
            } else {
                Alerta.mostrarAlertWarning("Seleccione un usuario!!");
            }
        });

        addTodos.setTooltip(new Tooltip("Agregar todos los accesos"));
        addTodos.setOnAction(event -> {
            if (!codigo.getText().isEmpty()) {
                // Sentencias.crud("call borrarAccesos('" + codigo.getText() + "');");
                for (int i = 0; i < listAccesos.getItems().size(); i++) {
                    if (Sentencias.validaAcceso("call validaAcceso('" + codigo.getText() + "','"
                            + listAccesosAux.getItems().get(i)
                            + "')") != 1) {
                        Sentencias.crud("call agregarAcceso('" + codigo.getText() + "','"
                                + listAccesosAux.getItems().get(i)
                                + "');");
                    }
                }
                llenaListAccesosUser("call retornaAccesosUsuario('"
                        + listUserAux.getItems().get(listUser.getSelectionModel().getSelectedIndex())
                        + "');");
                        Sentencias.crud("Call crearRegistroBitacora('Se agregaron todos los accesos al usuario: "+usu.getText()+"','USUARIOS','"+Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")+"')");
                Alerta.mostrarAlertWarning("Se agregaron todos los accesos al usuario: " + usu.getText());
            } else {
                Alerta.mostrarAlertWarning("Seleccione un usuario!!");
            }
        });

        delUno.setTooltip(new Tooltip("Quitar un acceso"));
        delUno.setOnAction(event -> {
            if (!codigo.getText().isEmpty()) {
                if (listAccesosUser.getSelectionModel().getSelectedIndex() != -1) {
                    Sentencias.crud("Call crearRegistroBitacora('Se quito el acceso "+listAccesosUser.getSelectionModel().getSelectedItem()+" del usuario: "+usu.getText()+"','USUARIOS','"+Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")+"')");
                    Sentencias.crud("call borrarAcceso('" + codigo.getText() + "','"
                            + listAccesosUserAux.getItems().get(listAccesosUser.getSelectionModel().getSelectedIndex())
                            + "')");
                    llenaListAccesosUser("call retornaAccesosUsuario('"
                            + listUserAux.getItems().get(listUser.getSelectionModel().getSelectedIndex())
                            + "');");
                    Alerta.mostrarAlertWarning("El acceso fue eliminado!!");

                } else {
                    Alerta.mostrarAlertWarning("Seleccione un acceso!!");
                }
            } else {
                Alerta.mostrarAlertWarning("Seleccione un usuario!!");
            }
        });

        delTodos.setTooltip(new Tooltip("Quitar todos los accesos"));
        delTodos.setOnAction(event -> {
            if (codigo.getText().isEmpty()) {
                Alerta.mostrarAlertWarning("Seleccione un usuario!!");
            } else {
                Sentencias.crud("call borrarAccesos('" + codigo.getText() + "');");
                llenaListAccesosUser("call retornaAccesosUsuario('"
                        + listUserAux.getItems().get(listUser.getSelectionModel().getSelectedIndex())
                        + "');");
                        Sentencias.crud("Call crearRegistroBitacora('Se eliminaron todos los accesos del usuario: "+usu.getText()+"','USUARIOS','"+Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")+"')");
                Alerta.mostrarAlertInfo("Se eliminaron los accesos del usuario: " + usu.getText());
            }
        });

        add.setTooltip(new Tooltip("Presione para crear un usuario"));
        add.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "11")) {
                botonGuardar();
            }else{
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        mod.setTooltip(new Tooltip("Presione para modificar un usuario"));
        mod.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "12")) {
                botonModificar();
            }else{
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        del.setTooltip(new Tooltip("Presione para eliminar un usuario"));
        del.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "13")) {
                botonEliminar();
            }else{
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        res.setTooltip(new Tooltip("Presione para restaurar un usuario eliminado"));
        res.setOnAction(event -> {
            if (Sentencias.validaAccesosUser(event, "14")) {
                botonRestaurar();
            }else{
                Alerta.mostrarAlertWarning("No tienes acceso a esta función!");
            }
        });

        guardar.setOnAction(event -> {
            acciones(event);
        });

        btnReport.setOnAction(event -> {
            boxAll.getChildren().clear();
            boxAll.getChildren().add(boxReport);

        });

        btnUser.setOnAction(event -> {
            boxAll.getChildren().clear();
            boxAll.getChildren().add(boxUser);

        });

        btnAtras.setOnAction(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            lw.loadStage("/view/viewInicio.fxml", event, 1280, 720, stage);
        });

        btnSalir.setOnAction(event -> {
            lw4.loadStage("/view/viewLogin.fxml", event, 897, 652, "");
        });
    }

    public String retornaUsuario(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        String[] usuario = stage.getTitle().split("\\s+");

        return usuario[usuario.length - 1];
    }

    private void botonRestaurar() {
        listAccesosUser.getItems().clear();
        listAccesosUserAux.getItems().clear();
        listUser.getItems().clear();
        listUserAux.getItems().clear();
        llenaListUser("CALL retornaUsuariosInactivos;");
        guardar.setText("RESTAURAR");
        limpiar();
        disableCampos();
        guardar.setDisable(false);
        listUser.setDisable(false);
    }

    private void botonEliminar() {
        listAccesosUser.getItems().clear();
        listAccesosUserAux.getItems().clear();
        listUser.getItems().clear();
        listUserAux.getItems().clear();
        llenaListUser("CALL retornaUsuariosActivos;");
        guardar.setText("ELIMINAR");
        limpiar();
        disableCampos();
        guardar.setDisable(false);
        listUser.setDisable(false);
    }

    private void botonModificar() {
        listAccesosUser.getItems().clear();
        listAccesosUserAux.getItems().clear();
        codigo.setEditable(false);
        listUser.getItems().clear();
        listUserAux.getItems().clear();
        llenaListUser("CALL retornaUsuariosActivos;");
        guardar.setText("MODIFICAR");
        limpiar();
        disableCampos();
        listUser.setDisable(false);
        codigo.setEditable(false);
    }

    private void botonGuardar() {

        listAccesosUser.getItems().clear();
        listAccesosUserAux.getItems().clear();
        listUser.getItems().clear();
        listUserAux.getItems().clear();
        llenaListUser("CALL retornaUsuariosActivos;");
        guardar.setText("AGREGAR");
        limpiar();
        enableCampos();
        addUno.setDisable(true);
        addTodos.setDisable(true);
        delUno.setDisable(true);
        delTodos.setDisable(true);
        listUser.setDisable(true);
        codigo.setEditable(false);
    }

    public void sacaEstado() {
        if (act.isSelected()) {
            val = 1;
        } else if (inac.isSelected()) {
            val = 0;
        }
    }

    private void acciones(ActionEvent event) {
        if (guardar.getText().equals("AGREGAR")) {
            if (!nombre.getText().isEmpty()
                    && !apellido.getText().isEmpty()
                    && !usu.getText().isEmpty() && !passwd.getText().isEmpty()
                    && !conPasswd.getText().isEmpty()
                    && !telefono.getText().isEmpty() && !correo.getText().isEmpty()
                    && (act.isSelected() || inac.isSelected())) {
                if (passwd.getText().equals(conPasswd.getText())) {
                    if (Sentencias.validaNickUsuario("CALL validaNickUsuario('" + usu.getText() + "')") != 1) {
                        if (Alerta.mostrarAlertConfirmation("Seguro que desea crear el usuario?") == 1) {
                            sacaEstado();
                            Sentencias
                                    .crud("Call crearUsuario('" + nombre.getText() + "','" + apellido.getText() + "','"
                                            + usu.getText() + "','" + Encoder.ecnode(passwd.getText()) + "','"
                                            + telefono.getText()
                                            + "','" + correo.getText() + "','" + val + "')");
                                            Sentencias.crud("Call crearRegistroBitacora('Se creo el usuario: "+usu.getText()+"','USUARIOS','"+Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")+"')");
                            Alerta.mostrarAlertWarning("SE AGREGO EL USUARIO!!");
                            limpiar();
                            llenaListUser("call retornaUsuariosActivos();");
                        }
                    } else {
                        Alerta.mostrarAlertWarning("EL NOMBRE DE USUARIO YA ESTA EN USO, INTENTE CON OTRO!!");
                    }
                } else {
                    Alerta.mostrarAlertWarning("Las contraseñas no coinciden!");
                }
            } else {
                Alerta.mostrarAlertWarning("RELLENE TODOS LOS CAMPOS!!");
            }
        }
        if (guardar.getText().equals("MODIFICAR")) {
            if (!nombre.getText().isEmpty()
                    && !apellido.getText().isEmpty()
                    && !usu.getText().isEmpty() && !passwd.getText().isEmpty()
                    && !conPasswd.getText().isEmpty()
                    && !telefono.getText().isEmpty() && !correo.getText().isEmpty()
                    && (act.isSelected() || inac.isSelected())) {
                if (passwd.getText().equals(conPasswd.getText())) {
                    if (Alerta.mostrarAlertConfirmation("Seguro que desea modificar el usuario?") == 1) {
                        sacaEstado();
                        Sentencias.crud("Call modificarUsuario('" + codigo.getText() + "','" + nombre.getText() + "','"
                                + apellido.getText() + "','" + usu.getText() + "','" + Encoder.ecnode(passwd.getText())
                                + "','" + telefono.getText() + "','" + correo.getText() + "','" + val + "')");
                                Sentencias.crud("Call crearRegistroBitacora('Se modifico el usuario: "+usu.getText()+"','USUARIOS','"+Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")+"')");
                        Alerta.mostrarAlertWarning("SE MODIFICO EL USUARIO!!");
                        limpiar();
                        llenaListUser("call retornaUsuariosActivos();");
                    }
                } else {
                    Alerta.mostrarAlertWarning("Las contraseñas no coinciden!");
                }
            } else {
                Alerta.mostrarAlertWarning("RELLENE TODOS LOS CAMPOS!!");
            }
        }
        if (guardar.getText().equals("ELIMINAR")) {
            if (listUser.getSelectionModel().getSelectedIndex() != -1) {
                if (Alerta.mostrarAlertConfirmation("Seguro que desea eliminar el usuario?") == 1) {
                    Sentencias.crud("call eliminarUsuario('" + codigo.getText() + "')");
                    Sentencias.crud("Call crearRegistroBitacora('Se elimino el usuario: "+usu.getText()+"','USUARIOS','"+Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")+"')");
                    Alerta.mostrarAlertInfo("Se Elimino");
                    limpiar();
                    llenaListUser("call retornaUsuariosActivos();");
                }
            } else {
                Alerta.mostrarAlertInfo("Seleccione un usuario!!");
            }
        }
        if (guardar.getText().equals("RESTAURAR")) {
            if (listUser.getSelectionModel().getSelectedIndex() != -1) {
                if (Alerta.mostrarAlertConfirmation("Seguro que desea restaurar el usuario?") == 1) {
                    Sentencias.crud("call restaurarUsuario('" + codigo.getText() + "')");
                    Sentencias.crud("Call crearRegistroBitacora('Se restauro el usuario: "+usu.getText()+"','USUARIOS','"+Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")+"')");
                    Alerta.mostrarAlertInfo("Se Restauro");
                    limpiar();
                    llenaListUser("call retornaUsuariosInactivos();");
                }
            } else {
                Alerta.mostrarAlertInfo("Seleccione un usuario!!");
            }
        }
    }

    private void llenaListUser(String sql) {

        ArrayList<Usuario> usuarios = Sentencias.retornaUsuarios(sql);
        Usuario usuario = new Usuario();
        listUser.getItems().clear();
        listUserAux.getItems().clear();
        for (int i = 0; i < usuarios.size(); i++) {
            usuario = usuarios.get(i);
            listUser.getItems().add(usuario.getNombre() + " " + usuario.getApellido());
            listUserAux.getItems().add(usuario.getId());
        }

    }

    private void llenaListAccesos() {

        listAccesos.getItems().clear();
        listAccesosAux.getItems().clear();
        Sentencias.retornaAccesos("call retornaAccesos();", listAccesos, listAccesosAux);

    }

    private void llenaListAccesosUser(String sql) {

        listAccesosUser.getItems().clear();
        listAccesosUserAux.getItems().clear();
        Sentencias.retornaAccesosUsuario(sql, listAccesosUser, listAccesosUserAux);

    }

    private void llenaCamposUsuario(String sql) {
        String code = listUserAux.getItems().get(listUser.getSelectionModel().getSelectedIndex()) + "";
        ArrayList<Usuario> usuarios = Sentencias.retornaUsuarios(sql);
        Usuario usuario = new Usuario();
        for (int i = 0; i < usuarios.size(); i++) {
            usuario = usuarios.get(i);

            if (Integer.parseInt(code) == usuario.getId()) {

                codigo.setText(usuario.getId() + "");
                nombre.setText(usuario.getNombre());
                apellido.setText(usuario.getApellido());
                usu.setText(usuario.getUsuario());
                passwd.setText(Encoder.deecnode(usuario.getContrasena()));
                conPasswd.setText(Encoder.deecnode(usuario.getContrasena()));
                telefono.setText(usuario.getTelefono());
                correo.setText(usuario.getCorreo());
                if (usuario.getEstado() == 1) {
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
        apellido.setEditable(true);
        usu.setEditable(true);
        passwd.setEditable(true);
        conPasswd.setEditable(true);
        telefono.setEditable(true);
        correo.setEditable(true);
        descrip.setEditable(true);
        act.setDisable(false);
        inac.setDisable(false);
        guardar.setDisable(false);
        addUno.setDisable(false);
        addTodos.setDisable(false);
        delUno.setDisable(false);
        delTodos.setDisable(false);

    }

    public void limpiar() {

        codigo.clear();
        nombre.clear();
        apellido.clear();
        usu.clear();
        passwd.clear();
        conPasswd.clear();
        telefono.clear();
        correo.clear();
        descrip.clear();
        act.setSelected(false);
        inac.setSelected(false);
        listAccesosUser.getItems().clear();
        listAccesosUserAux.getItems().clear();
    }

    public void disableCampos() {

        codigo.setEditable(false);
        nombre.setEditable(false);
        apellido.setEditable(false);
        usu.setEditable(false);
        passwd.setEditable(false);
        conPasswd.setEditable(false);
        telefono.setEditable(false);
        correo.setEditable(false);
        descrip.setEditable(false);
        act.setDisable(true);
        inac.setDisable(true);
        guardar.setDisable(true);
        addUno.setDisable(true);
        addTodos.setDisable(true);
        delUno.setDisable(true);
        delTodos.setDisable(true);

    }

    public void campos() {
        buscar.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letrasNumeros(event, buscar, true, 15));
        codigo.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letrasNumeros(event, codigo, false, 15));
        nombre.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letras(event, nombre, true, 15));
        apellido.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letras(event, apellido, true, 15));
        usu.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letrasNumeros(event, usu, true, 15));
        passwd.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.letrasNumeros(event, passwd, true, 15));
        conPasswd.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.letrasNumeros(event, conPasswd, true, 15));
        telefono.addEventHandler(KeyEvent.KEY_TYPED, event -> ValidacionCampos.enteros(event, telefono, 10));

    }

    public void focus() {
        codigo.setFocusTraversable(false);
        nombre.setFocusTraversable(false);
        apellido.setFocusTraversable(false);
        telefono.setFocusTraversable(false);
        conPasswd.setFocusTraversable(false);
        passwd.setFocusTraversable(false);
        correo.setFocusTraversable(false);
        usu.setFocusTraversable(false);
        guardar.setFocusTraversable(false);
        add.setFocusTraversable(false);
        addTodos.setFocusTraversable(false);
        addUno.setFocusTraversable(false);
        delTodos.setFocusTraversable(false);
        delUno.setFocusTraversable(false);
        find.setFocusTraversable(false);
        del.setFocusTraversable(false);
        mod.setFocusTraversable(false);
        res.setFocusTraversable(false);
        buscar.setFocusTraversable(false);
        descrip.setFocusTraversable(false);
        listAccesos.setFocusTraversable(false);
        listAccesosUser.setFocusTraversable(false);
        listUser.setFocusTraversable(false);
        act.setFocusTraversable(false);
        inac.setFocusTraversable(false);
        categoria.setFocusTraversable(false);
        btnUser.setFocusTraversable(true);
    }

    public void agregaIconos() {
        add.setGraphic(new ImageView("/Images/add.png"));
        mod.setGraphic(new ImageView("/Images/mod.png"));
        del.setGraphic(new ImageView("/Images/del.png"));
        res.setGraphic(new ImageView("/Images/res.png"));
        find.setGraphic(new ImageView("/Images/search.png"));
        addUno.setGraphic(new ImageView("/Images/addUno.png"));
        addTodos.setGraphic(new ImageView("/Images/addTodos.png"));
        delUno.setGraphic(new ImageView("/Images/delUno.png"));
        delTodos.setGraphic(new ImageView("/Images/delTodos.png"));
        btnUser.setGraphic(new ImageView("/Images/user.png"));
        btnReport.setGraphic(new ImageView("/Images/report.png"));
        btnAtras.setGraphic(new ImageView("/Images/backButton.png"));
        btnSalir.setGraphic(new ImageView("/Images/logoutButton.png"));
    }

    public void toltip(){
        codigo.setTooltip(new Tooltip("Código del usuario"));
        nombre.setTooltip(new Tooltip("Nombre del usuario"));
        apellido.setTooltip(new Tooltip("Apellido del usuario"));
        usu.setTooltip(new Tooltip("Usuario del usuario"));
        correo.setTooltip(new Tooltip("Correo del usuario"));
        passwd.setTooltip(new Tooltip("contraseña del usuario"));
        conPasswd.setTooltip(new Tooltip("Confirmar contraseña del usuario"));
        telefono.setTooltip(new Tooltip("Telefono del usuario"));
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        boxAll.getChildren().clear();
        boxAll.getChildren().add(boxUser);

        imaLogo.setImage(Sentencias.retornaEmpresaLogo("Call retornaLogoEmpresa()"));

        togleButton();
        detecta();
        llenaListAccesos();
        disableCampos();
        campos();

        agregaIconos();
        toltip();
        //focus();

    }
}
