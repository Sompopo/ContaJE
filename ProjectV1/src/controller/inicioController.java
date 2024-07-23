package controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import data.Alerta;
import data.Sentencias;
import first.Imagen;
import first.loadWindow3;
import first.loadWindow4;
import first.loadWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class inicioController implements Initializable {

    loadWindow3 lw = new loadWindow3();
    loadWindow4 lw4 = new loadWindow4();
    loadWindow lww = new loadWindow();
    private NumberFormat df2 = new DecimalFormat("#,##0.00"); 

    @FXML
    private Button btnCaja;

    @FXML
    private Button btnCont;

    @FXML
    private Button btnInven;

    @FXML
    private Button btnUser;

    @FXML
    private Button btnSettings;

    @FXML
    private Label laAtras;

    @FXML
    private ImageView logoInicio;

    @FXML
    void btnContActionEvent(ActionEvent event) {
        Object avt = event.getSource();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        if (Sentencias.validaAccesosUser(event, "3")) {
            if (avt.equals(btnCont)) {
                lw.loadStage("/view/viewContabilidad.fxml", event, 1280, 720, stage);
            }
        } else {
            Alerta.mostrarAlertWarning("No puedes acceder a este modulo!");
        }
    }

    @FXML
    void btnCajaActionEvent(ActionEvent event) {
        Object avt = event.getSource();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        String[] usuario = stage.getTitle().split("\\s+");

        if (Sentencias.validaAccesosUser(event, "4")) {
            if (avt.equals(btnCaja)) {
                if (Sentencias.validaAperturaAnteriorActiva("Call validaAperturaAnteriorActiva()").equals("1")) {
                    Alerta.mostrarAlertWarning(
                            "Al parecer no se generó el cierre de caja la última vez,\npuede consultarlo en cualquier momento en el módulo de contabilidad!");

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

                    Date ahora = new Date(Calendar.getInstance().getTimeInMillis());
                    
                    String[] infoCaja = Sentencias
                            .retornaAperturaAnteriorActiva("Call retornaAperturaAnteriorActiva()");
                            Date anterior = new Date();
                            try {
                                anterior = formatter2.parse(infoCaja[2]);
                                System.out.print(anterior);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }  
                    double valorInicialCaja = Double.parseDouble(infoCaja[1]);
                    double facturadoEfectivo = Sentencias
                            .retornaEfectivoCaja(
                                    "Call retornaEfectivoCaja('" + infoCaja[2]
                                            + "','" + formatter2.format(ahora) + " 23:59:59" + "')");

                    Alerta.mostrarAlertInfo("CIERRE DE CAJA\n\n-CAJA ABIERTA A LAS: " + infoCaja[2]
                            + "\n-CAJA CERRADA A LAS: " + formatter.format(ahora) + "\n\n-FACTURADO EN EFECTIVO: L "
                            + df2.format(facturadoEfectivo)
                            + "\n-VALOR INICIAL DE LA CAJA: L " + df2.format(valorInicialCaja)
                            + "\n-TOTAL, DE EFECTIVO EN LA CAJA: L "
                            + df2.format((valorInicialCaja + facturadoEfectivo))
                            + "\n\n-USUARIO ACTIVO: " + usuario[usuario.length - 1]);

                    Sentencias.crud("Call modificarAperturaCaja('" + formatter.format(ahora) + "','" + infoCaja[0] + "')");

                    Alerta.mostrarAlertInfo("CAJA LISTA PARA SER UTILIZADA!");
                    return;
                }
                if (!Sentencias.retornaUltimaAperturaCaja("Call retornaEstadoUltimaApertura();").equals("1")) {
                    if (Alerta.mostrarAlertConfirmation("Seguro que desea abrir la caja?") == 1) {
                        String valor = Alerta.retornaValorCaja();
                        if (!valor.equals("SALIR") && !valor.equals("") && !valor.equals(".")) {
                            cajaController
                                    .abrirCaja(valor,
                                            Sentencias.retornaUserCodigo(
                                                    "Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');")
                                                    + "");
                            
                            lw.loadStage("/view/viewCaja.fxml", event, 1280, 720, stage);
                        }
                    }
                } else {
                    lw.loadStage("/view/viewCaja.fxml", event, 1280, 720, stage);
                }
            }
        } else {
            Alerta.mostrarAlertWarning("No puedes acceder a este modulo!");
        }
    }

    @FXML
    void btnInvenActionEvent(ActionEvent event) {
        Object avt = event.getSource();

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        if (Sentencias.validaAccesosUser(event, "2")) {
            if (avt.equals(btnInven)) {
                lw.loadStage("/view/viewInventario.fxml", event, 1280, 720, stage);
            }
        } else {
            Alerta.mostrarAlertWarning("No puedes acceder a este modulo!");
        }
    }

    @FXML
    void btnUserActionEvent(ActionEvent event) {
        Object avt = event.getSource();

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        if (Sentencias.validaAccesosUser(event, "1")) {
            if (avt.equals(btnUser)) {
                lw.loadStage("/view/viewUsuario.fxml", event, 1280, 720, stage);
            }
        } else {
            Alerta.mostrarAlertWarning("No puedes acceder a este modulo!");
        }
    }

    @FXML
    void btnSettingsActionEvent(ActionEvent event) {
        Object avt = event.getSource();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        if (Sentencias.validaAccesosUser(event, "5")) {
            if (avt.equals(btnSettings)) {
                lw.loadStage("/view/viewSettings.fxml", event, 1280, 720, stage);
            }
        } else {
            Alerta.mostrarAlertWarning("No puedes acceder a este modulo!");
        }

    }

    @FXML
    void laAtrasMouseClicked(MouseEvent event) {
        Object evt = event.getSource();

        if (evt.equals(laAtras)) {
            lw4.loadStage("/view/viewLogin.fxml", event, 897, 652, "");            
        }
    }

    @FXML
    void laAtrasMouseEntered(MouseEvent event) {
        Object evt = event.getSource();

        if (evt.equals(laAtras)) {
            laAtras.setGraphic(new ImageView("/Images/logoutAzul.png"));
        }
    }

    @FXML
    void laAtrasMouseExited(MouseEvent event) {
        Object evt = event.getSource();

        if (evt.equals(laAtras)) {
            laAtras.setGraphic(new ImageView("/Images/logout.png"));
        }
    }

    public void validaRangoFacturacion() {
        if (Sentencias.verificaFactura("Call verificaFactura();") > 0) {
            String num = Sentencias.retornaUltimaFactura("Call retornaUltimaFactura();");
            String[] arre = num.split("-");
            String val = arre[0] + arre[1] + arre[2] + arre[3];
            if (val.equals(Sentencias.retornaUltimaFactura("Call retornaRangoMaxFac();"))) {
                btnCaja.setDisable(true);
                Alerta.mostrarAlertWarning(
                        "RANGO MAXIMO DE FACTURACION ALCANZADO, ACTUALICE SUS DATOS EN EL MODULO DE CONFIGURACION!!");
            }
        }
    }

    public void validaFechaMaxFacturacion() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaMax;
        Date fechaActual;
        try {
            fechaMax = Sentencias.retornaFechaMaxFacturacion("Call retornaInfoFac();");
            fechaActual = dateFormat.parse(LocalDate.now() + "");

            if (fechaMax.after(fechaActual) || fechaMax.equals(fechaActual)) {
                // System.out.println("Fecha valida");
            } else {
                btnCaja.setDisable(true);
                Alerta.mostrarAlertWarning(
                        "FECHA MAXIMA DE FACTURACION ALCANZADA, ACTUALICE SUS DATOS EN EL MODULO DE CONFIGURACION!");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String retornaUsuario(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        String[] usuario = stage.getTitle().split("\\s+");

        return usuario[usuario.length - 1];
    }

    public void detecta() {
        if (Sentencias.validaClienteAuxiliar("Call validaClienteAuxiliar();") == 0) {
            Sentencias.crud("Call crearClienteAuxiliar();");
        }

        if (Sentencias.verificaEmpresa("Call verificaEmpresa();") != 0) {
            validaRangoFacturacion();
            validaFechaMaxFacturacion();
        }

        if (Sentencias.verificaEmpresa("Call verificaEmpresa();") != 1) {
            btnCont.setDisable(true);
            btnCaja.setDisable(true);
            btnInven.setDisable(true);
            Alerta.mostrarAlertWarning(
                    "INGRESE LA INFORMACIÓN CORRESPONDIENTE A SU EMPRESA EN EL MÓDULO DE CONFIGURACIÓN!");
        }
    }

    public void imagesIcon(){
        btnCont.setGraphic(Imagen.imagen("/Images/contabilidad.jpg"));
        btnInven.setGraphic(Imagen.imagen("/Images/inventario.jpg"));
        btnCaja.setGraphic(Imagen.imagen("/Images/caja.jpg"));
        btnUser.setGraphic(Imagen.imagen("/Images/usuarios.jpg"));
        btnSettings.setGraphic(Imagen.imagen("/Images/settings.jpg"));
        laAtras.setGraphic(new ImageView("/Images/logout.png"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagesIcon();
        detecta();
        laAtras.setTooltip(new Tooltip("Cerrar sesión"));
        logoInicio.setImage(Sentencias.retornaEmpresaLogo("Call retornaLogoEmpresa()"));
    }

}
