package first;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import data.TextFile;

public class temaController {
    
    static ArrayList<String> datos = new ArrayList<String>();
    private static String tema;

    public static String getTema(){

        File file = new File("src/ficheros/InfoTemas.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
                temaController.logic("1");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        datos = TextFile.getFromFile("src/ficheros/InfoTemas.txt");
        String[] info = TextFile.sacarData(datos);

        if(info[0].equals("1")){
            tema = "/estilos/Style.css";
        }else{
            tema = "/estilos/Styletwo.css";
        }
        
        return tema;
    }

    public static void logic(String val) {
        datos.add("aux");
        datos.set(0, val);

        File file = new File("src/ficheros/InfoTemas.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            TextFile.setToFile(datos, "src/ficheros/InfoTemas.txt");
        } else {
            TextFile.setToFile(datos, "src/ficheros/InfoTemas.txt");
        }
    }
}
