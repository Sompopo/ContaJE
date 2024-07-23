package data;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.cj.jdbc.Blob;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Asiento;
import model.Asiento2;
import model.Asiento3;
import model.Bitacora;
import model.Cliente;
import model.Cuenta;
import model.Empresa;
import model.Producto;
import model.Proveedor;
import model.Usuario;

import java.sql.ResultSet;

public class Sentencias {

    Conexion con = new Conexion();
    private static NumberFormat df = new DecimalFormat("#0.00"); 

    public static int validaUsuario(String sql) {
        int code = 0;
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                code = rs.getInt(1);
                return code;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 1 " + exp.getMessage());
        }
        return code;
    }

    public static int retornaUserCodigo(String sql) {
        int code = 0;
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                code = rs.getInt(1);
                return code;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 37 " + exp.getMessage());
        }
        return code;
    }

    public static Usuario retornaUsuario(String sql) {
        Usuario user = new Usuario();
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                user.setNombre(rs.getString(2));
                user.setApellido(rs.getString(3));
                user.setUsuario(rs.getString(4));
                user.setTelefono(rs.getString(6));
                user.setCorreo(rs.getString(7));
                return user;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 2 " + exp.getMessage());
        }
        return user;
    }

    public static int totalUsuarios(String sql) {
        int valor = 0;
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getInt(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 3 " + exp.getMessage());
        }
        return valor;
    }

    public static int validaNickUsuario(String sql) {
        int code = 0;
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                code = rs.getInt(1);
                return code;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 4 " + exp.getMessage());
        }
        return code;
    }

    public static boolean crearUsuario(Usuario user) {
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();

            s1.execute("CALL crearUsuario('" + user.getNombre() + "','" + user.getApellido() + "','" + user.getUsuario()
                    + "','" + user.getContrasena() + "','" + user.getTelefono() + "','" + user.getCorreo() + "','"
                    + user.getEstado() + "')");

            con.close();

            return true;
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 5 " + exp.getMessage());
            return false;
        }
    }

    public static ArrayList<Producto> retornaProductos(String sql) {
        ListView listaProd = new ListView();
        ArrayList<Producto> productos = new ArrayList();

        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                Producto prod = new Producto();
                prod.setCodigo(rs.getString(1));
                prod.setNombre(rs.getString(2));
                prod.setDescrip(rs.getString(3));
                prod.setPcosto(rs.getDouble(4));
                prod.setImpuesto(rs.getDouble(5));
                prod.setUtilidad(rs.getDouble(6));
                prod.setPventa(rs.getDouble(7));
                prod.setImax(rs.getInt(8));
                prod.setImin(rs.getInt(9));
                prod.setCategoria(rs.getString(10));
                prod.setDescuento(rs.getDouble(12));
                prod.setMedida(rs.getString(13));
                prod.setEstado(rs.getInt(14));

                productos.add(prod);
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 6 " + exp.getMessage());
        }

        return productos;
    }

    public static boolean existeProducto(String sql, String codigo) {
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString(1).equals(codigo)) {
                    return true;
                }
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 7 " + exp.getMessage());
        }
        return false;
    }

    public static void buscarProd(String sql, String code, ComboBox box, ListView list, ListView listAux) {
        LinkedList<Producto> productos = new LinkedList<>();
        try {
            Connection con = new Conexion().getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            list.getItems().clear();
            listAux.getItems().clear();
            while (rs.next()) {
                String codigo = rs.getString(1);
                String nombre = rs.getString(2);
                Producto prod = new Producto();
                prod.setCodigo(codigo);
                prod.setNombre(nombre);
                productos.add(prod);
                if (code.equals(codigo) || code.equals(nombre)) {
                    // double precioU = rs.getDouble(4)+rs.getDouble(6);
                    box.getItems().add(nombre);

                }

            }
            con.close();
            LinkedList<Producto> filtrada = new LinkedList<>();
            for (Producto producto : productos) {
                if (producto.getCodigo().toLowerCase().contains(code.toLowerCase())
                        || producto.getNombre().toLowerCase().contains(code.toLowerCase())) {
                    filtrada.add(producto);
                } 
            } 
            list.getItems().clear();
            listAux.getItems().clear();
            for (Producto producto : filtrada) {
                listAux.getItems().add(producto.getCodigo());
                list.getItems().add(producto.getNombre());
            }
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 8 " + exp.getMessage());
        }

    }

    public static ArrayList<Producto> retornaProductosCategoria(String sql) {
        ArrayList<Producto> productos = new ArrayList();

        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                Producto prod = new Producto();
                prod.setCodigo(rs.getString(1));
                prod.setNombre(rs.getString(2));
                prod.setDescrip(rs.getString(3));
                prod.setPcosto(rs.getDouble(4));
                prod.setImpuesto(rs.getDouble(5));
                prod.setUtilidad(rs.getDouble(6));
                prod.setPventa(rs.getDouble(7));
                prod.setImax(rs.getInt(8));
                prod.setImin(rs.getInt(9));
                prod.setCategoria(rs.getString(10));
                prod.setDescuento(rs.getDouble(12));
                prod.setMedida(rs.getString(13));
                prod.setEstado(rs.getInt(14));

                productos.add(prod);
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 9 " + exp.getMessage());
        }

        return productos;
    }

    public static void crud(String sql) {
        try {
            Connection con = new Conexion().getConexion();
            Statement s1 = con.createStatement();

            s1.execute(sql);

            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 10 " + exp.getMessage());
        }
    }

    public static boolean llenaComboBox(String sql, ComboBox box, ComboBox boxAux) {
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            boxAux.getItems().clear();
            box.getItems().clear();
            box.getItems().add("Categoría (Ninguna)");
            boxAux.getItems().add("0");
            while (rs.next()) {
                boxAux.getItems().add(rs.getString(1));
                box.getItems().add(rs.getString(2));
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 28 " + exp.getMessage());
        }
        return false;
    }

    public static boolean retornaCategoria(String sql, TextField nombre, TextArea descrip, ComboBox descuento, String code) {
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);

            nombre.clear();;
            descrip.clear();;
            descuento.setValue(0+"%");
        
            while (rs.next()) {
                if(code.equals(rs.getString(1))){
                    nombre.setText(rs.getString(2));
                    if(rs.getString(3) != null){
                        descrip.setText(rs.getString(3));
                    }
                    descuento.setValue(rs.getString(5)+"%");
                }
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 28 " + exp.getMessage());
        }
        return false;
    }

    public static void buscarCliente(String sql, String code, TableView tblClientes, javafx.collections.ObservableList<Cliente> listaClientes) {
        LinkedList<Cliente> clientes = new LinkedList<>();
        try {
            Connection con = new Conexion().getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            tblClientes.getItems().clear();
            listaClientes.clear();
            while (rs.next()) {
                Cliente cliente = new Cliente(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7));
                clientes.add(cliente);

            }
            con.close();
            LinkedList<Cliente> filtrada = new LinkedList<>();
            for (Cliente cliente : clientes) {
                String aux = cliente.getCedula() + "";
                if (aux.toLowerCase().contains(code.toLowerCase())
                        || cliente.getNombre().toLowerCase().contains(code.toLowerCase())) {
                    filtrada.add(cliente);
                } 
            } 
            tblClientes.getItems().clear();
            listaClientes.clear();
            for (Cliente cliente : filtrada) {
                listaClientes.add(cliente);
            }
            tblClientes.setItems(listaClientes);
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 18 " + exp.getMessage());
        }
    }

    public static int retornaDescuentoCategoria(String sql) {

        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                return rs.getInt(1);
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 19 " + exp.getMessage());
        }
        return 0;
    }

    ////////////////////////////////////////////////////////////////////// CONSULTAS
    ////////////////////////////////////////////////////////////////////// USUARIOS////////////////////////////////////////////////////

    public static ArrayList<Usuario> retornaUsuarios(String sql) {
        ListView listaUser = new ListView();
        ArrayList<Usuario> usuarios = new ArrayList();

        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                Usuario user = new Usuario();
                user.setId(rs.getInt(1));
                user.setNombre(rs.getString(2));
                user.setApellido(rs.getString(3));
                user.setUsuario(rs.getString(4));
                user.setContrasena(rs.getString(5));
                user.setTelefono(rs.getString(6));
                user.setCorreo(rs.getString(7));
                user.setEstado(rs.getInt(8));

                usuarios.add(user);
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 11 " + exp.getMessage());
        }

        return usuarios;
    }

    public static void retornaAccesos(String sql, ListView listAccesos, ListView listAccesosAux) {

        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                listAccesosAux.getItems().add(rs.getInt(1));
                listAccesos.getItems().add(rs.getString(2));
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 12 " + exp.getMessage());
        }
    }

    public static void retornaAccesosUsuario(String sql, ListView listAccesosUser, ListView listAccesosUserAux) {

        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                listAccesosUserAux.getItems().add(rs.getInt(1));
                listAccesosUser.getItems().add(rs.getString(2));
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 13 " + exp.getMessage());
        }
    }

    public static ArrayList retornaAccesosUsuario2(String sql) {

        ArrayList listaAccesos = new ArrayList<>();
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                listaAccesos.add(rs.getString(1));
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 40 " + exp.getMessage());
        }
        return listaAccesos;
    }

    public static boolean validaAccesosUser(ActionEvent event, String acceso) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        String[] usuario = stage.getTitle().split("\\s+");
        ArrayList listaAccesos = retornaAccesosUsuario2("Call retornaAccesosUsuario('"
                + retornaUserCodigo("Call retornaUserCodigo('" + usuario[usuario.length - 1] + "');")
                + "');");

        for (int i = 0; i < listaAccesos.size(); i++) {
            if (listaAccesos.get(i).equals(acceso)) {
                return true;
            }
        }
        return false;
    }

    public static String retonarDescripAcceso(String sql) {

        try {
            String descrip;
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                descrip = rs.getString(1);
                return descrip;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 14 " + exp.getMessage());
        }
        return "";
    }

    public static ArrayList<String> retornaTodosAccesos(String sql) {
        ArrayList<String> accesos = new ArrayList<>();
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                accesos.add(rs.getString(1));
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 36 " + exp.getMessage());
        }
        return accesos;
    }

    public static ArrayList<String> retornaCategoriasAccesos(String sql) {
        ArrayList<String> categorias = new ArrayList<>();
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                categorias.add(rs.getString(1));
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 36.1 " + exp.getMessage());
        }
        return categorias;
    }

    public static void buscarUser(String sql, String code, ListView list, ListView listAux) {
        LinkedList<Usuario> usuarios = new LinkedList<>();
        try {
            Connection con = new Conexion().getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            list.getItems().clear();
            listAux.getItems().clear();
            while (rs.next()) {
                String codigo = rs.getString(1);
                String nombre = rs.getString(2);
                Usuario user = new Usuario();
                user.setId(Integer.parseInt(codigo));
                user.setNombre(nombre);
                usuarios.add(user);

            }
            con.close();
            LinkedList<Usuario> filtrada = new LinkedList<>();
            for (Usuario usuario : usuarios) { 
                String aux = usuario.getId() + "";
                if (aux.toLowerCase().contains(code.toLowerCase())
                        || usuario.getNombre().toLowerCase().contains(code.toLowerCase())) { 
                    filtrada.add(usuario); 
                }  
            }  
            list.getItems().clear();
            listAux.getItems().clear(); 
            for (Usuario usuario : filtrada) { 
                listAux.getItems().add(usuario.getId());
                list.getItems().add(usuario.getNombre()); 
            }
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 15 " + exp.getMessage());
        }

    }

    public static int validaAcceso(String sql) {

        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                return rs.getInt(1);
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 16 " + exp.getMessage());
        }
        return 0;
    }

    /*
     * //////////////////////////////////////////////////AGREGAR
     * ASIENTOS///////////////////////////////////////////////////////////
     */

    public static void llenaTablaCuentas(String sql, TableView tblDesde) {

        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                Asiento2 asiento = new Asiento2(rs.getString(1), rs.getString(2), "", "");
                tblDesde.getItems().add(asiento);
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 17 " + exp.getMessage());
        }
    }

    public static void buscarAsiento(String sql, String code, TableView tblAsientos) {
        LinkedList<Asiento2> asientos = new LinkedList<>();
        try {
            Connection con = new Conexion().getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            tblAsientos.getItems().clear();
            while (rs.next()) {
                String codigo = rs.getString(1);
                String nombre = rs.getString(2);
                Asiento2 asiento = new Asiento2(codigo, nombre, "", "");
                asientos.add(asiento);

            }
            con.close();
            LinkedList<Asiento2> filtrada = new LinkedList<>();
            for (Asiento2 asiento : asientos) {
                String aux = asiento.getCodigo() + "";
                if (aux.toLowerCase().contains(code.toLowerCase())
                        || asiento.getNombre().toLowerCase().contains(code.toLowerCase())) {
                    filtrada.add(asiento);
                } 
            } 
            tblAsientos.getItems().clear();
            for (Asiento2 asiento : filtrada) {
                tblAsientos.getItems().add(asiento);
            }
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 18 " + exp.getMessage());
        }

    }

    public static int numeroAsiento(String sql) {

        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                return rs.getInt(1);
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 19 " + exp.getMessage());
        }
        return 0;
    }

    /////////////////////////////////////////////////// CONSULTAS
    /////////////////////////////////////////////////// PROVEEDOR////////////////////////////////////////////////

    public static ArrayList<Proveedor> retornaProveedores(String sql) {
        ListView listaProv = new ListView();
        ArrayList<Proveedor> proveedores = new ArrayList();

        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                Proveedor prov = new Proveedor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getInt(5));
                proveedores.add(prov);
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 20 " + exp.getMessage());
        }

        return proveedores;
    }

    /////////////////////////////////////////////////// CONSULTAS
    /////////////////////////////////////////////////// CLIENTES////////////////////////////////////////////////

    public static ArrayList<Cliente> retornaClientes(String sql) {
        ListView listaCli = new ListView();
        ArrayList<Cliente> clientes = new ArrayList();

        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                Cliente cli = new Cliente(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getInt(7));
                clientes.add(cli);
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 21 " + exp.getMessage());
        }

        return clientes;
    }

    public static int validaClienteCedula(String sql) {
        int code = 0;
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                code = rs.getInt(1);
                return code;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 22 " + exp.getMessage());
        }
        return code;
    }

    public static int validaClienteAuxiliar(String sql) {
        int code = 0;
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                code = rs.getInt(1);
                return code;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 35 " + exp.getMessage());
        }
        return code;
    }

    public static double retornaCredito(String sql) {
        double valor = 0;
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getDouble(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 28 " + exp.getMessage());
        }
        return valor;
    }

    ///////////////////////////////////////////////////////// VER ASIENTOS
    ///////////////////////////////////////////////////////// /////////////////////////////////////////////////////

    public static ArrayList<Asiento> retornaAsientos(String sql) {
        ListView listaAsientos = new ListView();
        ArrayList<Asiento> asientos = new ArrayList();

        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                Asiento asien = new Asiento(rs.getString(1), rs.getString(2), rs.getString(3));
                asientos.add(asien);
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 23 " + exp.getMessage());
        }

        return asientos;
    }

    public static ArrayList<Asiento3> retornaUnAsiento(String codigo) {
        ListView listaAsientos = new ListView();
        NumberFormat df2 = new DecimalFormat("#,##0.00");
        ArrayList<Asiento3> asientos = new ArrayList();
        Asiento3 asien;

        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery("CALL retornaUnAsiento('" + codigo + "')");
            while (rs.next()) {
                asien = new Asiento3(rs.getString(1), "                     Pda#" + rs.getString(2), "", "", "");
                asientos.add(asien);
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 24 " + exp.getMessage());
        }

        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery("CALL retornaUnAsiento2('" + codigo + "')");
            while (rs.next()) {
                String debe = df2.format(rs.getDouble(3));
                String haber = df2.format(rs.getDouble(4));
                asien = new Asiento3("", rs.getString(1), rs.getString(2), debe, haber);
                asientos.add(asien);
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 25 " + exp.getMessage());
        }

        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery("CALL retornaUnAsiento3('" + codigo + "')");
            while (rs.next()) {
                asien = new Asiento3("", rs.getString(1), "", "", "");
                asientos.add(asien);
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 26 " + exp.getMessage());
        }

        return asientos;
    }


    ////////////////////////////////////////////////////// CAJAAA//////////////////////////////////////////////////


    public static void buscarProd2(String sql, String code, ComboBox box, ComboBox boxAux) {
        LinkedList<Producto> productos = new LinkedList<>();
        try {
            Connection con = new Conexion().getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            box.getItems().clear();
            boxAux.getItems().clear();
            while (rs.next()) {
                String codigo = rs.getString(1);
                String nombre = rs.getString(2);
                Producto prod = new Producto();
                prod.setCodigo(codigo);
                prod.setNombre(nombre);
                productos.add(prod);
                if (code.equals(codigo) || code.equals(nombre)) {
                    
                    box.getItems().add(nombre);

                }

            }
            con.close();
            LinkedList<Producto> filtrada = new LinkedList<>();
            for (Producto producto : productos) {
                if (producto.getCodigo().toLowerCase().contains(code.toLowerCase())
                        || producto.getNombre().toLowerCase().contains(code.toLowerCase())) {
                    filtrada.add(producto);
                } 
            } 
            box.getItems().clear();
            boxAux.getItems().clear();
            for (Producto producto : filtrada) {
                boxAux.getItems().add(producto.getCodigo());
                box.getItems().add(producto.getNombre());
            }
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 27 " + exp.getMessage());
        }

    }

    public static double retornaStock(String sql) {
        double valor = 0;
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getDouble(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 28 " + exp.getMessage());
        }
        return valor;
    }

    public static int retornaUltimaTransaccion(String sql) {
        int valor = 0;
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getInt(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 29 " + exp.getMessage());
        }
        return valor;
    }
    
    public static String retornaUltimaFactura(String sql) {
        String valor = "";
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getString(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 30 " + exp.getMessage());
        }
        return valor;
    }

    public static int verificaFactura(String sql) {
        int valor = 0;
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getInt(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 34 " + exp.getMessage());
        }
        return valor;
    }

    public static String retornaNumInicioFac(String sql) {
        String num = "";
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                if(rs.getString(3).equals("")){
                    for(int i = 0; i < 3; i++){
                        num += rs.getString(1).charAt(i);
                        if(i == 2){num += "-";}
                    }
                    for(int i = 3; i < 6; i++){
                        num += rs.getString(1).charAt(i);
                        if(i == 5){num += "-";}
                    }
                    for(int i = 6; i < 8; i++){
                        num += rs.getString(1).charAt(i);
                        if(i == 7){num += "-";}
                    }
                    for(int i = 8; i < 16; i++){
                        num += rs.getString(1).charAt(i);
                    }
                    
                }else{
                    for(int i = 0; i < 3; i++){
                        num += rs.getString(3).charAt(i);
                        if(i == 2){num += "-";}
                    }
                    for(int i = 3; i < 6; i++){
                        num += rs.getString(3).charAt(i);
                        if(i == 5){num += "-";}
                    }
                    for(int i = 6; i < 8; i++){
                        num += rs.getString(3).charAt(i);
                        if(i == 7){num += "-";}
                    }
                    for(int i = 8; i < 16; i++){
                        num += rs.getString(3).charAt(i);
                    }
                }
                return num;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 34 " + exp.getMessage());
        }
        return num;
    }

    public static Date retornaFechaMaxFacturacion(String sql) {
        Date date = null;
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                date = rs.getDate(4);
                return date;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 38 " + exp.getMessage());
        }
        return date;
    }

    ////////////////////////////////////////////////EMPRESA//////////////////////////////////////////////

    public static Empresa retornaEmpresa(String sql) {
        Empresa empresa;
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                Date date = rs.getDate(14);
                LocalDate ldate = date.toLocalDate();
                empresa = new Empresa(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getBlob(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), ldate);
                return empresa;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 31 " + exp.getMessage());
        }
        return null;
    }

    public static int verificaEmpresa(String sql) {
        int valor = 0;
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getInt(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 32 " + exp.getMessage());
        }
        return valor;
    }

    public static String retornaEmpresaNombre(String sql) {
        String valor = "";
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getString(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 39 " + exp.getMessage());
        }
        return valor;
    }

    public static int retornaCodigoEmpresa(String sql) {
        int valor = 0;
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getInt(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 33 " + exp.getMessage());
        }
        return valor;
    }

    public static void modificarImagen(String ruta, String code) {
        FileInputStream fis = null;
        PreparedStatement ps = null;
        try {
            //conexion.setAutoCommit(false);
            Connection con = Conexion.getConexion();
            File file = new File(ruta);
            fis = new FileInputStream(file);
            ps = con.prepareStatement("update tbl_empresa set empresa_logo = ? where empresa_codigo = '"+code+"'");
            ps.setBinaryStream(1,fis,(int)file.length());
            ps.executeUpdate();
            con.close();
        } catch (Exception ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                ps.close();
                fis.close();
            } catch (Exception ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }

    public static Image retornaEmpresaLogo(String sql) {
        Image img = null;
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                Blob logo = (Blob) rs.getBlob(1);
                byte byteImage[] = null;
                byteImage = logo.getBytes(1, (int) logo.length());
                img = new Image(new ByteArrayInputStream(byteImage));
                return img;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 34 " + exp.getMessage());
        }
        return img;
    }

    //////////////////////////////////////////// NOTA DE CREDITO ////////////////////////////////////////////

    public static ArrayList<Producto> retornaProductosFactura(String sql) {
        ListView listaProd = new ListView();
        NumberFormat df2 = new DecimalFormat("#,##0.00");
        ArrayList<Producto> productos = new ArrayList();

        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                Producto prod = new Producto();
                String precioU = df2.format(rs.getDouble(3));
                String valor = df2.format(rs.getDouble(5));
                prod = new Producto(rs.getString(1),rs.getString(2),precioU,rs.getDouble(4),valor);
                productos.add(prod);
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 41 " + exp.getMessage());
        }

        return productos;
    }

    public static void retornaTotalesfactura(String sql, Label subTotal, Label impuesto, Label descuento, Label total) {
        NumberFormat df2 = new DecimalFormat("#,##0.00");
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
               subTotal.setText(df2.format((rs.getDouble(1)+rs.getDouble(2)+rs.getDouble(3)))+" Lps");
               impuesto.setText(df2.format((rs.getDouble(5)+rs.getDouble(6)))+" Lps");
               descuento.setText(df2.format(rs.getDouble(4))+" Lps");
               total.setText(df2.format(rs.getDouble(8))+" Lps");
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 42 " + exp.getMessage());
        }
    }

    public static Producto retornaUnProducto(String sql) {
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                
                Producto prod = new Producto();
                prod.setCodigo(rs.getString(1));
                prod.setNombre(rs.getString(2));
                prod.setDescrip(rs.getString(3));
                prod.setPcosto(rs.getDouble(4));
                prod.setImpuesto(rs.getDouble(5));
                prod.setUtilidad(rs.getDouble(6));
                prod.setPventa(rs.getDouble(7));
                prod.setImax(rs.getInt(8));
                prod.setImin(rs.getInt(9));
                prod.setCategoria(rs.getString(10));
                prod.setDescuento(rs.getInt(12));
                prod.setMedida(rs.getString(13));
                prod.setEstado(rs.getInt(14));

                return prod;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 43 " + exp.getMessage());
        }

        return new Producto();
    }

    public static String retornaNotaCreditoNum(String sql) {
        String valor = "";
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getString(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 44 " + exp.getMessage());
        }
        return valor;
    }

    public static String retornaClienteFactura(String sql) {
        String valor = "";
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getString(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 45 " + exp.getMessage());
        }
        return valor;
    }

    public static String retornaEstadoFactura(String sql) {
        String valor = "";
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getString(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 46 " + exp.getMessage());
        }
        return valor;
    }

    public static String retornaUltimaAperturaCaja(String sql) {
        String valor = "";
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getString(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 47 " + exp.getMessage());
        }
        return valor;
    }

    public static String validaAperturaAnteriorActiva(String sql) {
        String valor = "";
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getString(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 48 " + exp.getMessage());
        }
        return valor;
    }

    public static String retornaCodigoAperturaAnteriorActiva(String sql) {
        String valor = "";
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getString(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 49 " + exp.getMessage());
        }
        return valor;
    }

    public static String[] retornaAperturaCajaActiva(String sql) {
        String valor[] = new String[4];
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor[0] = rs.getString(1);
                valor[1] = rs.getString(2);
                valor[2] = rs.getString(3);
                valor[3] = rs.getString(4);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 50 " + exp.getMessage());
        }
        return valor;
    }

    public static double retornaEfectivoCaja(String sql) {
        double valor = 0.0;
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getDouble(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 51 " + exp.getMessage());
        }
        return valor;
    }

    public static String[] retornaAperturaAnteriorActiva(String sql) {
        String valor[] = new String[4];
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor[0] = rs.getString(1);
                valor[1] = rs.getString(2);
                valor[2] = rs.getString(3);
                valor[3] = rs.getString(4);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 52 " + exp.getMessage());
        }
        return valor;
    }

    public static String retornaUltimoDoc(String sql) {
        String valor = "";
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getString(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 53 " + exp.getMessage());
        }
        return valor;
    }

    public static ArrayList<Bitacora> retornaRegistroBitacora(String sql) {
        ArrayList<Bitacora> registros = new ArrayList();

        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                Bitacora registro = new Bitacora(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
                registros.add(registro);
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 54 " + exp.getMessage());
        }

        return registros;
    }

    public static int validaNombreCategoria(String sql) {
        int valor = 0;
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getInt(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 55 " + exp.getMessage());
        }
        return valor;
    }

    public static String retornaCategoriaEstado(String sql) {
        String valor = "";
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getString(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 56 " + exp.getMessage());
        }
        return valor;
    }

    public static String retornaUltimoRecibo(String sql) {
        String valor = "";
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getInt(1)+"";
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 56.1 " + exp.getMessage());
        }
        return valor;
    }

    public static ArrayList<Cuenta> retornaCuentas(String sql) {
        ArrayList<Cuenta> cuentas = new ArrayList();

        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                Cuenta cuenta = new Cuenta();
                cuenta = new Cuenta(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
                cuentas.add(cuenta);
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 56.2 " + exp.getMessage());
        }

        return cuentas;
    }

    public static int validaCuentaCodigo(String sql) {
        int code = 0;
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                code = rs.getInt(1);
                return code;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 56.3 " + exp.getMessage());
        }
        return code;
    }

    public static void buscarCuenta(String sql, String code, ListView list, ListView listAux) {
        LinkedList<Cuenta> cuentas = new LinkedList<>();
        try {
            Connection con = new Conexion().getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            list.getItems().clear();
            listAux.getItems().clear();
            while (rs.next()) {
                String codigo = rs.getString(1);
                String nombre = rs.getString(2);
                Cuenta cuenta = new Cuenta();
                cuenta.setCodigo(codigo);
                cuenta.setNombre(nombre);
                cuentas.add(cuenta);

            }
            con.close();
            LinkedList<Cuenta> filtrada = new LinkedList<>();
            for (Cuenta cuenta : cuentas) {
                String aux = cuenta.getCodigo() + "";
                if (aux.toLowerCase().contains(code.toLowerCase())
                        || cuenta.getNombre().toLowerCase().contains(code.toLowerCase())) {
                    filtrada.add(cuenta);
                } 
            } 
            list.getItems().clear();
            listAux.getItems().clear();
            for (Cuenta cuenta : filtrada) {
                listAux.getItems().add(cuenta.getCodigo());
                list.getItems().add(cuenta.getNombre());
            }
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 56.4 " + exp.getMessage());
        }

    }

    public static String retornaTipoDoc(String sql) {
        String valor = "";
        try {
            Connection con = Conexion.getConexion();
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery(sql);
            while (rs.next()) {
                valor = rs.getString(1);
                return valor;
            }
            con.close();
        } catch (Exception exp) {
            Alerta.mostrarAlertError("Error 56.5 " + exp.getMessage());
        }
        return valor;
    }
}
