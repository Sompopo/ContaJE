package controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.mysql.cj.jdbc.Blob;

import data.Alerta;
import data.Sentencias;
import data.TextFile;
import data.ValidacionCampos;
import first.loadWindow3;
import first.loadWindow4;
import first.temaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Bitacora;
import model.Empresa;

import java.io.ByteArrayInputStream;

public class settingsController implements Initializable {

    String link, code;
    private loadWindow3 lw = new loadWindow3();
    private loadWindow4 lw4 = new loadWindow4(); 
    private static ObservableList<Bitacora> listaRegis = FXCollections.observableArrayList();
    private ToggleGroup estado;

    @FXML
    private DatePicker bitDesde;

    @FXML
    private DatePicker bitHasta;

    @FXML
    private HBox boxAll;

    @FXML
    private VBox boxLateral;

    @FXML
    private HBox boxBitacora;

    @FXML
    private HBox boxEmpresa;

    @FXML
    private HBox boxTema;

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnBitacora;

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnEmpresa;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnTemas;

    @FXML
    private Button btnVer;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnAplicarCambios;

    @FXML
    private TextField cai;

    @FXML
    private TextField cel1;

    @FXML
    private TextField cel2;

    @FXML
    private TableView<Bitacora> tblBitacora;

    @FXML
    private TableColumn<Bitacora, String> columFecha;

    @FXML
    private TableColumn<Bitacora, String> columModulo;

    @FXML
    private TableColumn<Bitacora, String> columNombre;

    @FXML
    private TableColumn<Bitacora, String> columRegis;

    @FXML
    private TableColumn<Bitacora, String> columUsuario;

    @FXML
    private TextField correo;

    @FXML
    private TextField desde;

    @FXML
    private TextField desdeAux;

    @FXML
    private TextArea direccion;

    @FXML
    private DatePicker fecha;

    @FXML
    private TextField hasta;

    @FXML
    private ImageView ima;

    @FXML
    private ImageView imaLogo;

    @FXML
    private ImageView claro;

    @FXML
    private ImageView oscuro;

    @FXML
    private Label laInfo;

    @FXML
    private ComboBox<String> modulo;

    @FXML
    private TextField nombre;

    @FXML
    private TextField rtn;

    @FXML
    private TextField ruta;

    @FXML
    private TextField user;

    @FXML
    private RadioButton dark;

    @FXML
    private RadioButton light;

    public void togle(){
        estado = new ToggleGroup();

        light.setToggleGroup(estado);
        dark.setToggleGroup(estado);
    }

    public <T> void detecta() {

        boxEmpresa.getStyleClass().add("vbox2");

        boxBitacora.getStyleClass().add("vbox2");

        boxTema.getStyleClass().add("vbox2");

        boxLateral.getStyleClass().add("vbox");

        direccion.setWrapText(true);

        imaLogo.setImage(Sentencias.retornaEmpresaLogo("Call retornaLogoEmpresa()"));

        fecha.setStyle("-fx-font: 18px \"System\";");
        tblBitacora.setStyle("-fx-font: 18px \"System\";");
        fecha.setEditable(false);

        laInfo.setText("INGRESE EL RANGO DE FACTURACIÓN AUTORIZADO\nY EL LÍMITE DE FECHA DE EMISIÓN!!");

        btnCargar.setOnAction(event -> {
            try {
                //ImageView imagen = new ImageView();
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Buscar Imagen");
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Archivos Imagen", "*.png", "*.jpg"));
                File selectedFile = fileChooser.showOpenDialog(stage);

                if (selectedFile != null) {
                    FileInputStream input = new FileInputStream(selectedFile);
                    link = selectedFile.getAbsolutePath();
                    ruta.setText(selectedFile.getName());
                    Image image = new Image(input);
                    ima.setImage(image);
                }
            } catch (Exception e) {
                System.out.println("Debes manejar el error: " + e.getMessage());
            }
        });

        btnGuardar.setOnAction(event -> {
            accion(event);
        });

        btnEmpresa.setOnAction(event -> {
            boxAll.getChildren().clear();
            boxAll.getChildren().add(boxEmpresa);
        });

        btnBitacora.setOnAction(event -> {
            boxAll.getChildren().clear();
            boxAll.getChildren().add(boxBitacora);
        });

        btnTemas.setOnAction(event -> {
            boxAll.getChildren().clear();
            boxAll.getChildren().add(boxTema);
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

    private void accion(ActionEvent event) {
        if (!nombre.getText().isEmpty() && !rtn.getText().isEmpty() && !cai.getText().isEmpty()
                && !direccion.getText().isEmpty() && !correo.getText().isEmpty()
                && !cel1.getText().isEmpty()
                && !ruta.getText().isEmpty()
                && !desde.getText().isEmpty()
                && !hasta.getText().isEmpty()
                && fecha.getValue() != null) {
            if (validaLongitudCampos()) {
                if (validaRangoFacturacion()) {
                    if (validaFechaMaxFacturacion()) {
                        if (desdeAux.getText().isEmpty()) {
                            accionFinal(event);
                        } else {
                            if (validaInicioFacturacionAux()) {
                                accionFinal(event);
                            } else {
                                Alerta.mostrarAlertWarning(
                                        "El valor inicial de facturación está fuera del rango establecido!!");
                            }
                        }
                    } else {
                        Alerta.mostrarAlertWarning("La fecha ingresada no es valida!!");
                    }
                } else {
                    Alerta.mostrarAlertWarning("El rango de facturación es incorrecto!!");
                }
            }
        } else {
            Alerta.mostrarAlertWarning("Rellene todos los campos!!");
        }
    }

    public void accionFinal(ActionEvent event) {
        if (Sentencias.verificaEmpresa("Call verificaEmpresa();") != 0) {
            if (Alerta
                    .mostrarAlertConfirmation("Seguro que desea modificar los datos de la empresa?") == 1) {
                if (link != null) {
                    Sentencias.modificarImagen(link, code);
                }
                Sentencias.crud(
                        "Call modificarEmpresa('"
                                + Sentencias.retornaCodigoEmpresa("Call retornaCodigoEmpresa();")
                                + "','" + nombre.getText() + "','" + rtn.getText() + "','" + cai.getText().toUpperCase()
                                + "','"
                                + direccion.getText() + "','" + cel1.getText() + "','" + cel2.getText()
                                + "','"
                                + correo.getText() + "','" + ruta.getText() + "','"
                                + desde.getText() + "','" + hasta.getText() + "','" + desdeAux.getText()
                                + "','"
                                + fecha.getValue() + "');");
                Alerta.mostrarAlertInfo("Se modificaron los datos!!");
                Sentencias.crud("Call crearRegistroBitacora('Se modificaron los datos de la empresa i/o facturacion','CONFIGURACIÓN','"+Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")+"')");
            }
        } else {
            if (Alerta
                    .mostrarAlertConfirmation("Seguro que desea registrar los datos de la empresa?") == 1) {
                Sentencias.crud(
                        "Call crearEmpresa('" + 1 + "','" + nombre.getText() + "','" + rtn.getText() + "','"
                                + cai.getText().toUpperCase() + "','" + direccion.getText() + "','" + cel1.getText()
                                + "','"
                                + cel2.getText() + "','" + correo.getText() + "','" + ruta.getText() + "','"
                                + desde.getText() + "','" + hasta.getText() + "','" + desdeAux.getText()
                                + "','"
                                + fecha.getValue() + "');");

                Sentencias.modificarImagen(link,
                        Sentencias.retornaCodigoEmpresa("Call retornaCodigoEmpresa();") + "");
                Alerta.mostrarAlertInfo("Se crearon los datos!!");
                Sentencias.crud("Call crearRegistroBitacora('Se crearon los datos de la empresa y facturacion','CONFIGURACIÓN','"+Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + retornaUsuario(event) + "');")+"')");
            }
        }
        //Sentencias.crud("Call crearRegistroBitacora('Inicio de Sesion','NINGUNO','"+Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + user + "');")+"')");
    }

    public boolean validaLongitudCampos() {
        /// rtn 14
        /// cai 32
        /// fac num 16
        if (rtn.getText().length() == 14) {
            if (cai.getText().length() == 32) {
                if (desde.getText().length() == 16) {
                    if (hasta.getText().length() == 16) {
                        if (desdeAux.getText().isEmpty()) {
                            return true;
                        } else {
                            if (desdeAux.getText().length() == 16) {
                                return true;
                            } else {
                                Alerta.mostrarAlertWarning("El rango de facturación ingresado no es valido!!");
                            }
                        }
                    } else {
                        Alerta.mostrarAlertWarning("El rango de facturación ingresado no es valido!!");
                    }
                } else {
                    Alerta.mostrarAlertWarning("El rango de facturación ingresado no es valido!!");
                }
            } else {
                Alerta.mostrarAlertWarning("El CAI ingresa no es valido");
            }
        } else {
            Alerta.mostrarAlertWarning("El RTN ingresa no es valido");
        }

        return false;
    }

    public boolean validaRangoFacturacion() {
        String ini1 = "";
        String fin1 = "";
        String ini2 = "";
        String fin2 = "";
        String[] ultima = null;

        for (int i = 0; i < 8; i++) {
            ini1 += desde.getText().charAt(i) + "";
        }

        for (int i = 0; i < 8; i++) {
            fin1 += hasta.getText().charAt(i) + "";
        }

        for (int i = 8; i < 16; i++) {
            ini2 += desde.getText().charAt(i) + "";
        }

        for (int i = 8; i < 16; i++) {
            fin2 += hasta.getText().charAt(i) + "";
        }

        if (Integer.parseInt(ini2) < Integer.parseInt(fin2)) {
            if (ini1.equals(fin1)) {
                if (Sentencias.verificaFactura("Call verificaFactura();") > 0) {
                    ultima = Sentencias.retornaUltimaFactura("Call retornaUltimaFactura();").split("-");
                    if (Integer.parseInt(fin2) > Integer.parseInt(ultima[3]) && Integer.parseInt(ini2) > Integer.parseInt(ultima[3])) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean validaInicioFacturacionAux() {
        String ini1 = "";
        String ini2 = "";
        String fin1 = "";
        String iniAux1 = "";
        String iniAux2 = "";

        for (int i = 8; i < 16; i++) {
            ini1 += desde.getText().charAt(i) + "";
        }

        for (int i = 0; i < 8; i++) {
            ini2 += desde.getText().charAt(i) + "";
        }

        for (int i = 8; i < 16; i++) {
            fin1 += hasta.getText().charAt(i) + "";
        }

        for (int i = 8; i < 16; i++) {
            iniAux1 += desdeAux.getText().charAt(i) + "";
        }

        for (int i = 0; i < 8; i++) {
            iniAux2 += desdeAux.getText().charAt(i) + "";
        }

        if ((Integer.parseInt(iniAux1) < Integer.parseInt(fin1))
                && (Integer.parseInt(iniAux1) > Integer.parseInt(ini1))) {
            if (ini2.equals(iniAux2)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean validaFechaMaxFacturacion() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaMax;
        Date fechaActual;
        try {
            fechaMax = dateFormat.parse(fecha.getValue() + "");
            fechaActual = dateFormat.parse(LocalDate.now() + "");

            if (fechaMax.after(fechaActual)) {
                return true;// System.out.println("Fecha valida");
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void cargarEmpresa() throws SQLException {
        if (Sentencias.retornaEmpresa("Call retornaEmpresa();") != null) {
            Empresa empresa = Sentencias.retornaEmpresa("Call retornaEmpresa();");

            code = empresa.getCodigo() + "";
            nombre.setText(empresa.getNombre());
            rtn.setText(empresa.getRtn());
            cai.setText(empresa.getCai());
            correo.setText(empresa.getCorreo());
            direccion.setText(empresa.getDireccion());
            cel1.setText(empresa.getTelefono1());
            cel2.setText(empresa.getTelefono2());
            ruta.setText(empresa.getLogoname());
            Blob logo = (Blob) empresa.getLogo();
            byte byteImage[] = null;
            byteImage = logo.getBytes(1, (int) logo.length());
            Image img = new Image(new ByteArrayInputStream(byteImage));
            ima.setImage(img);
            desde.setText(empresa.getDesde());
            hasta.setText(empresa.getHasta());
            desdeAux.setText(empresa.getDesdeAux());
            fecha.setValue(empresa.getFechaLimite());
        }
        if (Sentencias.verificaFactura("Call verificaFactura();") > 0) {
            desdeAux.setDisable(true);
        }
    }

    public String retornaUsuario(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        String[] usuario = stage.getTitle().split("\\s+");

        return usuario[usuario.length - 1];
    }

    public void focus() {
        nombre.setFocusTraversable(false);
        rtn.setFocusTraversable(false);
        cai.setFocusTraversable(false);
        cel1.setFocusTraversable(false);
        cel2.setFocusTraversable(false);
        correo.setFocusTraversable(false);
        direccion.setFocusTraversable(false);
        ruta.setFocusTraversable(false);
        btnGuardar.setFocusTraversable(false);
        btnCargar.setFocusTraversable(false);
        btnEmpresa.setFocusTraversable(true);
    }

    public void campos() {
        nombre.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.letrasNumeros(event, nombre, true, 25));
        rtn.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.enteros(event, rtn, 14));
        cai.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.letrasNumeros(event, cai, false, 32));
        cel1.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.enteros(event, cel1, 9));
        cel2.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.enteros(event, cel2, 9));
        desde.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.enteros(event, desde, 16));
        desdeAux.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.enteros(event, desdeAux, 16));
        hasta.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.enteros(event, hasta, 16));

        user.addEventHandler(KeyEvent.KEY_TYPED,
                event -> ValidacionCampos.letrasNumeros(event, hasta,false,15));

    }

    //////////////////////////////////////////////////////////////////////////////////////////// BITACORA /////////////////////////

    public void datosTabla() {
        columFecha.setCellValueFactory(new PropertyValueFactory<Bitacora, String>("fecha"));
        columRegis.setCellValueFactory(new PropertyValueFactory<Bitacora, String>("accion"));
        columModulo.setCellValueFactory(new PropertyValueFactory<Bitacora, String>("modulo"));
        columNombre.setCellValueFactory(new PropertyValueFactory<Bitacora, String>("nombre"));
        columUsuario.setCellValueFactory(new PropertyValueFactory<Bitacora, String>("user"));
    }

    public <T> void detectaBit(){

        modulo.setStyle("-fx-font: 18px \"System\";");
        bitDesde.setStyle("-fx-font: 18px \"System\";");
        bitHasta.setStyle("-fx-font: 18px \"System\";");

        modulo.getItems().add("NINGUNO");
        modulo.getItems().add("USUARIOS");
        modulo.getItems().add("INVENTARIO");
        modulo.getItems().add("CAJA");
        modulo.getItems().add("CONTABILIDAD");
        modulo.getItems().add("CONFIGURACIÓN");

        btnVer.setOnAction(event -> {
            if(bitDesde.getValue() != null && bitHasta.getValue() != null){
                llenarTablaBitacora("Call retornaRegistrosBitacora('"+bitDesde.getValue()+" 00:00:00','"+bitHasta.getValue()+" 23:59:59','"+Sentencias.retornaUserCodigo("Call retornaUserCodigo('" + user.getText() + "');")+"','"+modulo.getSelectionModel().getSelectedItem()+"');");
            }
        });

        btnLimpiar.setOnAction(event -> {
            listaRegis.clear();
            tblBitacora.getItems().clear();
        });
    }

    private void llenarTablaBitacora(String sql) {
        listaRegis.clear();
        ArrayList <Bitacora> registros = Sentencias.retornaRegistroBitacora(sql);
        listaRegis.addAll(registros);
        tblBitacora.setItems(listaRegis);
        
    }

    /////////////////////////////////////////////////////////////////TEMAS/////////////////////////////////////////////////

    public <T>void detectaTemas(){
        ArrayList<String> datos = TextFile.getFromFile("src/ficheros/InfoTemas.txt");
        String[] info = TextFile.sacarData(datos);

        System.out.print(info[0]);
        if(info[0].equals("1")){
            dark.setSelected(true);
            light.setSelected(false);
        }else if(info[0].equals("2")){
            dark.setSelected(false);
            light.setSelected(true);
        }

        btnAplicarCambios.setOnAction(event -> {
            if(dark.isSelected() || light.isSelected()){
                if(Alerta.mostrarAlertConfirmation("Seguro que desea cambiar el tema?") == 1){
                    if(dark.isSelected()){
                        temaController.logic("1");
                        Alerta.mostrarAlertInfo("Se ha cambiado al tema Oscuro!");
                    }else if(light.isSelected()){
                        temaController.logic("2");
                        Alerta.mostrarAlertInfo("Se ha cambiado al tema Claro!");
                    }
                }
            }else{
                Alerta.mostrarAlertWarning("Seleccione un tema!!");
            }
        });
    }

    public void toltip(){
        nombre.setTooltip(new Tooltip("Nombre de la Empresa"));
        rtn.setTooltip(new Tooltip("RTN de la Empresa"));
        cai.setTooltip(new Tooltip("CAI de la Empresa"));
        correo.setTooltip(new Tooltip("Correo de la Empresa"));
        cel1.setTooltip(new Tooltip("Teléfono de la Empresa"));
        cel2.setTooltip(new Tooltip("Teléfono de la Empresa"));
        desde.setTooltip(new Tooltip("Rango Incial de Facturación"));
        hasta.setTooltip(new Tooltip("Rango Final de Facturación"));
        fecha.setTooltip(new Tooltip("Fecha Límite de Facturación"));
        desdeAux.setTooltip(new Tooltip("Numero Inicial de Facturación"));
        ruta.setTooltip(new Tooltip("Cargar Logo de la Empresa"));
        direccion.setTooltip(new Tooltip("Dirección de la Empresa"));

        user.setTooltip(new Tooltip("Nombre de un usuario"));
        bitDesde.setTooltip(new Tooltip("Fecha Inicial"));
        bitHasta.setTooltip(new Tooltip("Fecha Final"));
    }

    public void agregaIconos() {
        btnBitacora.setGraphic(new ImageView("/Images/bitacora.png"));
        btnEmpresa.setGraphic(new ImageView("/Images/edificio.png"));
        btnTemas.setGraphic(new ImageView("/Images/tema.png"));
        btnAtras.setGraphic(new ImageView("/Images/backButton.png"));
        btnSalir.setGraphic(new ImageView("/Images/logoutButton.png"));

        claro.setImage(new Image("/Images/claro.png"));
        oscuro.setImage(new Image("/Images/oscuro.png"));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        boxAll.getChildren().clear();
        boxAll.getChildren().add(boxEmpresa);

        try {
            cargarEmpresa();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        detecta();
        focus();
        campos();

        detectaBit();
        datosTabla();

        detectaTemas();
        togle();

        toltip();
        agregaIconos();

    }

}
