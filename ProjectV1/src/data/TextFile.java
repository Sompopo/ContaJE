package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class TextFile
{
    public static void prueba()
    {
        String nombreFichero = "src/datos.txt";
        //Declarar una variable BufferedReader
        BufferedReader br = null;
        try {
           //Crear un objeto BufferedReader al que se le pasa 
           //   un objeto FileReader con el nombre del fichero
           br = new BufferedReader(new FileReader(nombreFichero));
           //Leer la primera línea, guardando en un String
           String texto = br.readLine();
           //Repetir mientras no se llegue al final del fichero
           while(texto != null)
           {
               //Hacer lo que sea con la línea leída
               System.out.println(texto);
               //Leer la siguiente línea
               texto = br.readLine();
           }
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: Fichero no encontrado");
            System.out.println(e.getMessage());
        }
        catch(Exception e) {
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(br != null)
                    br.close();
            }
            catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }
    }

    public static ArrayList<String> getFromFile(String path) {
        ArrayList<String> todo = new ArrayList<>();
        try {
            String line;
            File f = new File(path);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                todo.add(line);
            }
            fr.close();
            br.close();
        } catch (Exception e) {
        }
        return todo;
    }

    public static void setToFile(ArrayList<String> data, String path) {
        try {
            File f = new File(path);
            FileWriter fw = new FileWriter(f, false);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String xl : data) {
                bw.write(xl);
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
        }
    }

    public static String[] sacarData(ArrayList<String> data)
    {
        String [] ordena = new String[4];
        String pala;
        
            pala = data.get(0);
            ordena = pala.split("/");
        
        return ordena;
    }

    public static String[][] sacarDatos(ArrayList<String> data,int t)
    {
        String [][] ordena = new String[data.size()][t];
        String [] ordena2 = new String[data.size()];
        String pala = "";
        for (int x = 0; x < ordena.length; x++){
            pala = data.get(x);
            ordena2 = pala.split("/");
            for (int y = 0; y < ordena[x].length; y++)
                ordena[x][y] = ordena2[y];}
        return ordena;
    }
}



