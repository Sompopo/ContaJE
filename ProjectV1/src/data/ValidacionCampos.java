package data;


import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class ValidacionCampos {

    static boolean haySigno = false;

    public static void decimales(KeyEvent keyEvent, TextField campo, int tam) {

        char letra = keyEvent.getCharacter().charAt(0);
        String word = campo.getText();

        if (existeSigno(campo.getText(), ".") == false) {
            if (((letra < '0') || (letra > '9')) && (letra != '.')) {
                keyEvent.consume();

                if (letra == '.') {
                    haySigno = true;
                }
            }
        } else {
            if ((letra < '0') || (letra > '9')) {
                keyEvent.consume();
            }
        }
        if (word.length() == tam) {
            keyEvent.consume();
        }

    }

    public static boolean existeSigno(String cadena, String signo) {
        char[] letras = cadena.toCharArray();

        for (int cont = 0; cont < letras.length; cont++) {
            String l = letras[cont] + "";

            if (l.equals(signo)) {
                return true;
            }
        }

        return false;
    }

    public static void enteros(KeyEvent keyEvent, TextField campo, int tam) {

        char letra = keyEvent.getCharacter().charAt(0);
        String word = campo.getText();

        if ((letra < '0') || (letra > '9')) {
            keyEvent.consume();
        }
        if (word.length() == tam) {
            keyEvent.consume();
        }

    }

    public static void enteros2(KeyEvent keyEvent, TextField campo, int tam) {

        char letra = keyEvent.getCharacter().charAt(0);
        String word = campo.getText();

        if ((letra < '1') || (letra > '9')) {
            keyEvent.consume();
        }
        if (word.length() == tam) {
            keyEvent.consume();
        }

    }

    public static void letras(KeyEvent keyEvent, TextField campo, boolean conEspacios, int tam) {

        char letra = keyEvent.getCharacter().charAt(0);
        String word = campo.getText();

        if (conEspacios == true) {
            if (((letra < 'a') || (letra > 'z')) && ((letra < 'A') || (letra > 'Z')) && (letra != ' ')) {
                keyEvent.consume();
            }
            if (word.length() == tam) {
                keyEvent.consume();
            }
        } else {
            if (((letra < 'a') || (letra > 'z')) && ((letra < 'A') || (letra > 'Z'))) {
                keyEvent.consume();
            }
            if (word.length() == tam) {
                keyEvent.consume();
            }
        }

    }

    public static void letrasNumeros(KeyEvent keyEvent, TextField descrip, boolean conEspacios, int tam) {
        char letra = keyEvent.getCharacter().charAt(0);
        String pal = descrip.getText();

        if (conEspacios == true) {
            if (((letra < 'a') || (letra > 'z')) && ((letra < 'A') || (letra > 'Z'))
                    && ((letra < '0') || (letra > '9')) && (letra != ' ')) {
                keyEvent.consume();
            }
            if (pal.length() == tam) {
                keyEvent.consume();
            }
        } else {
            if (((letra < 'a') || (letra > 'z')) && ((letra < 'A') || (letra > 'Z'))
                    && ((letra < '0') || (letra > '9'))) {
                keyEvent.consume();
            }
            if (pal.length() == tam) {
                keyEvent.consume();
            }
        }

    }

    public static void todoSinEspacios(KeyEvent keyEvent, TextField campo, boolean conEspacios, int tam) {
        char letra = keyEvent.getCharacter().charAt(0);
        String word = campo.getText();

        if (conEspacios == true) {
            if (((letra < 'a') || (letra > 'z')) && ((letra < 'A') || (letra > 'Z'))
                    && ((letra < '0') || (letra > '9'))
                    && ((letra == '@') || (letra == '-') || (letra == '_') || (letra == '.'))
                    && (letra != ' ')) {
                keyEvent.consume();
            }
        } else {
            if (((letra < 'a') || (letra > 'z')) && ((letra < 'A') || (letra > 'Z'))
                    && ((letra < '0') || (letra > '9'))
                    && ((letra != '@') || (letra != '-') || (letra != '_') || (letra != '.'))) {
                keyEvent.consume();
            }
            if (word.length() == tam) {
                keyEvent.consume();
            }
        }

    }

    public static void signosNumeros(KeyEvent keyEvent, TextField campo, boolean conEspacios, int tam) {
        char letra = keyEvent.getCharacter().charAt(0);
        String word = campo.getText();

        if (conEspacios == true) {
            if (((letra < '-') || (letra > '.')) && ((letra < '0') || (letra > '9')) && (letra != ' ')) {
                keyEvent.consume();
            }
        } else {
            if (((letra < '-') || (letra > '.')) && ((letra < '0') || (letra > '9'))) {
                keyEvent.consume();
            }
            if (word.length() == tam) {
                keyEvent.consume();
            }
        }

    }

    public static void validaLongitud(KeyEvent keyEvent, TextField campo, boolean conEspacios, int size) {

        char letra = keyEvent.getCharacter().charAt(0);
        String pal = campo.getText();

        if (conEspacios == true) {
            if (pal.length() > size) {
                keyEvent.consume();
            }
        } else {
            if (((letra == ' '))) {
                keyEvent.consume();
            }
            if (pal.length() == size) {
                keyEvent.consume();
            }
        }
    }

    public static void validaLongitudTextArea(KeyEvent keyEvent, TextArea campo, boolean conEspacios, int size) {

        char letra = keyEvent.getCharacter().charAt(0);
        String pal = campo.getText();

        if (conEspacios == true) {
            if (pal.length() > size) {
                keyEvent.consume();
            }
        } else {
            if (((letra == ' '))) {
                keyEvent.consume();
            }
            if (pal.length() == size) {
                keyEvent.consume();
            }
        }
    }
}
