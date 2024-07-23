package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import data.Alerta;
import data.Sentencias;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Cliente;

public class agregarClienteController implements Initializable {

    private ToggleGroup estado;
    private static ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @FXML
    private RadioButton act;

    @FXML
    private TextField apellido;

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnAgregar;

    @FXML
    private TextField buscar;

    @FXML
    private TextField cedula;

    @FXML
    private TableColumn<Cliente, String> columCedula;

    @FXML
    private TableColumn<Cliente, String> columNombre;

    @FXML
    private TextField correo;

    @FXML
    private RadioButton inac;

    @FXML
    private TextField nombre;

    @FXML
    private TextField rtn;

    @FXML
    private TableView<Cliente> tblClientes;

    @FXML
    private TextField telefono;

    @FXML
    private VBox box1;

    @FXML
    private VBox box2;

    public <T> void detecta() {

        box1.getStyleClass().add("vbox2");

        box2.getStyleClass().add("vbox2");

        estado = new ToggleGroup();

        act.setToggleGroup(estado);
        inac.setToggleGroup(estado);

        btnAgregar.setOnAction(event -> {
            if (btnAgregar.getText().equals("Agregar")) {
                if (!cedula.getText().isEmpty() && !nombre.getText().isEmpty()
                        && !apellido.getText().isEmpty()
                        && !telefono.getText().isEmpty()
                        && (act.isSelected() || inac.isSelected())) {
                    if (Sentencias.validaNickUsuario("CALL validaCedulaCliente('" + cedula.getText() + "')") == 0) {
                        Sentencias.crud("Call crearCliente('" + cedula.getText() + "','" + nombre.getText() + "','"
                                + apellido.getText() + "','" + telefono.getText() + "','" + correo.getText()
                                + "','" + rtn.getText() + "','" + sacaEstadoCli() + "')");
                        Alerta.mostrarAlertWarning("SE AGREGO EL CLIENTE!!");
                        String[] arre = { cedula.getText(), nombre.getText(), apellido.getText() };
                        cajaController.setCliente(arre);
                        Node source = (Node) event.getSource();
                        Stage stage = (Stage) source.getScene().getWindow();
                        stage.close();
                    } else {
                        Alerta.mostrarAlertWarning("El cliente ya fue registrado anteriormente!!");
                    }
                } else {
                    Alerta.mostrarAlertWarning("RELLENE TODOS LOS CAMPOS!!");
                }
            }
        });

        btnAceptar.setOnAction(event -> {
            if (tblClientes.getSelectionModel().getSelectedIndex() != -1) {
                String cedula = listaClientes.get(tblClientes.getSelectionModel().getSelectedIndex()).getCedula();
                String nombre = listaClientes.get(tblClientes.getSelectionModel().getSelectedIndex()).getNombre();
                String apellido = listaClientes.get(tblClientes.getSelectionModel().getSelectedIndex()).getApellido();
                String[] arre = { cedula, nombre, apellido };
                cajaController.setCliente(arre);
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        });

        buscar.setOnAction(event -> {
            listaClientes.clear();
            Sentencias.buscarCliente("Call retornaClientesActivos();", buscar.getText(), tblClientes, listaClientes);
        });
    }

    public int sacaEstadoCli() {
        int valCli = 0;
        if (act.isSelected()) {
            return valCli = 1;
        } else if (inac.isSelected()) {
            return valCli = 0;
        }
        return valCli;
    }

    public void llenaTabla() {
        listaClientes.clear();
        ArrayList<Cliente> lista = Sentencias.retornaClientes("Call retornaClientesActivos();");

        listaClientes.addAll(lista);

        columCedula.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cedula"));
        columNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));

        tblClientes.setItems(listaClientes);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        detecta();
        llenaTabla();
    }

}
