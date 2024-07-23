package controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import data.Sentencias;
import first.loadWindow3;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.Asiento;
import model.Asiento3;

public class verAsientosController implements Initializable{

    private ListView listAsientosAux;
    loadWindow3 lw = new loadWindow3();
    private NumberFormat df2 = new DecimalFormat("#,##0.00");

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnTodo;

    @FXML
    private Button btnLimpiar;

    @FXML
    private DatePicker fechaDesde;

    @FXML
    private DatePicker fechaHasta;

    @FXML
    private ListView<String> listAsientos;

    @FXML
    private TableView<Asiento3> tblAsientos;

    @FXML
    private TableColumn<Asiento3, String> columDebe;

    @FXML
    private TableColumn<Asiento3, String> columDetalle;

    @FXML
    private TableColumn<Asiento3, String> columFecha;

    @FXML
    private TableColumn<Asiento3, String> columHaber;

    @FXML
    private TableColumn<Asiento3, String> columRm;

    @FXML
    private Label laDebeTotal;

    @FXML
    private Label laHaberTotal;

    private void llenaLisAsiento(String sql) {

        ArrayList<Asiento> asientos = Sentencias.retornaAsientos(sql);
        Asiento asiento;
        listAsientos.getItems().clear();
        listAsientosAux.getItems().clear();
        for (int i = 0; i < asientos.size(); i++) {
            asiento = asientos.get(i);
            listAsientos.getItems().add(asiento.getDetalle());
            listAsientosAux.getItems().add(asiento.getCodigo());
        }
    }

    public void datosTabla() {
        columFecha.setCellValueFactory(new PropertyValueFactory<Asiento3, String>("fecha"));
        columDetalle.setCellValueFactory(new PropertyValueFactory<Asiento3, String>("detalle"));
        columRm.setCellValueFactory(new PropertyValueFactory<Asiento3, String>("rm"));
        columDebe.setCellValueFactory(new PropertyValueFactory<Asiento3, String>("debe"));
        columHaber.setCellValueFactory(new PropertyValueFactory<Asiento3, String>("haber"));
    }

    public <T> void detecta(){

        tblAsientos.setStyle("-fx-font: 18px \"System\";");
        columDebe.setStyle( "-fx-alignment: CENTER-RIGHT;");
        columHaber.setStyle( "-fx-alignment: CENTER-RIGHT;");

        listAsientos.setStyle("-fx-font: 18px \"System\";");

        fechaDesde.setStyle("-fx-font: 18px \"System\";");
        fechaHasta.setStyle("-fx-font: 18px \"System\";");

        listAsientosAux = new ListView<>();

        listAsientos.getSelectionModel().selectedIndexProperty()
                .addListener((ChangeListener<? super Number>) new ChangeListener<T>() {
                    @Override
                    public void changed(ObservableValue<? extends T> arg0, T arg1, T arg2) {
                        if (listAsientos.getSelectionModel().getSelectedIndex() > -1) {
                            datosTabla();
                            tblAsientos.getItems().clear();
                            tblAsientos.getItems().addAll(Sentencias.retornaUnAsiento(listAsientosAux.getItems().get(listAsientos.getSelectionModel().getSelectedIndex())+""));
                            sumaTotales();
                        }
                    }

                });

        btnTodo.setOnAction(event -> {
            datosTabla();
            tblAsientos.getItems().clear();
            for(int i = 0; i < listAsientos.getItems().size(); i++){
                tblAsientos.getItems().addAll(Sentencias.retornaUnAsiento(listAsientosAux.getItems().get(i)+""));
            }
            sumaTotales();
        });

        btnBuscar.setOnAction(event -> {
            llenaLisAsiento("call retornaAsientos('"+fechaDesde.getValue()+" 00:00:00"+"','"+fechaHasta.getValue()+" 23:59:59"+"');");
        });

        btnAtras.setOnAction(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            lw.loadStage("/view/viewContabilidad.fxml", event,1280,720,stage);
        });

        btnLimpiar.setOnAction(event -> {
            tblAsientos.getItems().clear();
            laDebeTotal.setText("0.00 Lps");
            laHaberTotal.setText("0.00 Lps");
        });
    }

    public void sumaTotales(){
        double debe = 0.00;
        double haber = 0.00;
        for(int i = 0; i < tblAsientos.getItems().size(); i++ ){
            if(!tblAsientos.getColumns().get(3).getCellData(i).toString().isEmpty()){
                debe += Double.parseDouble(tblAsientos.getColumns().get(3).getCellData(i).toString().replace(",", ""));
            }
            if(!tblAsientos.getColumns().get(4).getCellData(i).toString().isEmpty()){
                haber += Double.parseDouble(tblAsientos.getColumns().get(4).getCellData(i).toString().replace(",", ""));
            }
        }
        laDebeTotal.setText(df2.format(debe)+" Lps");
        laHaberTotal.setText(df2.format(haber)+" Lps");
    }
    
    public void agregaIconos() {
        btnAtras.setGraphic(new ImageView("/Images/back.png"));
        btnLimpiar.setGraphic(new ImageView("/Images/clear.png"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        detecta();
        agregaIconos();
    }
    
}
