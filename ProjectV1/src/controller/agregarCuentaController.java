package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

import data.Alerta;
import data.Sentencias;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Asiento2;

public class agregarCuentaController implements Initializable {

    ObservableList<Asiento2> datos = FXCollections.observableArrayList();

    @FXML
    private Button btnBuscar;

    @FXML
    private TextField buscar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnPoner;

    @FXML
    private Button btnQuitar;

    @FXML
    private TableView<Asiento2> tblDesde;

    @FXML
    private TableView<Asiento2> tblHasta;

    private <T> void detecta() {
        btnBuscar.setOnAction(event -> {
            Sentencias.buscarAsiento("call retornaCuentasActivas();", buscar.getText(), tblDesde);
        });

        btnAgregar.setOnAction(event -> {
            for(int i = 0; i < tblHasta.getItems().size(); i++){
                datos.add(new Asiento2(tblHasta.getItems().get(i).getCodigo(), tblHasta.getItems().get(i).getNombre(),"0.00","0.00"));
            }
            registroAsientosController.setRecibe(datos);
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });

        btnPoner.setOnAction(event -> {
            if (tblDesde.getSelectionModel().getSelectedIndex() != -1) {
                if(tblHasta.getItems().size() > 0){
                    if(validaCuentas() != 1){
                        tblHasta.getItems().add(tblDesde.getSelectionModel().getSelectedItem());
                    }
                    else{
                        Alerta.mostrarAlertWarning("La cuenta ya fue agregada!!");
                    }
                }
                else{
                    tblHasta.getItems().add(tblDesde.getSelectionModel().getSelectedItem());
                }
            }
        });

        btnQuitar.setOnAction(event -> {
            if (tblHasta.getSelectionModel().getSelectedIndex() != -1) {
                tblHasta.getItems().remove(tblHasta.getSelectionModel().getSelectedIndex());
            }
        });

    }

    public int validaCuentas(){
        int act = 0;
        for(int i = 0; i < tblHasta.getItems().size(); i++ ){
            if(!tblDesde.getColumns().get(0).getCellData(tblDesde.getSelectionModel().getSelectedIndex()).equals(tblHasta.getColumns().get(0).getCellData(i))){
                act = 0;
            }else{
                act = 1;
                return act;
            }
        }
        
        return act;
    }

    private void llenaTabla() {
        tblDesde.getColumns().add(createColumn("Codigo", Asiento2::codigoProperty));
        tblDesde.getColumns().add(createColumn("Nombre de la Cuenta", Asiento2::nombreProperty));

        tblHasta.getColumns().add(createColumn("Codigo", Asiento2::codigoProperty));
        tblHasta.getColumns().add(createColumn("Nombre de la Cuenta", Asiento2::nombreProperty));

        Sentencias.llenaTablaCuentas("call llenaTablaCuentas();", tblDesde);
    }

    public <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col ;
    }

    public void agregaIconos(){
        btnPoner.setGraphic(new ImageView("/Images/rigth.png"));
        btnQuitar.setGraphic(new ImageView("/Images/left.png"));
        btnBuscar.setGraphic(new ImageView("/Images/search.png"));
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        agregaIconos();
        detecta();
        llenaTabla();
    }
}
