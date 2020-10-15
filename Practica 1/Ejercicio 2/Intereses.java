package practica1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Intereses {
	
	private ArrayList<String> intereses;
	
	private static Intereses instance=null;
	
	private Intereses() {
		
		this.intereses=new ArrayList<String>();
		cargarIntereses();
	}
	
	public static Intereses getInstance() {
		
		
		if(instance==null) {
			instance=new Intereses();
		}
		return instance;
	}
	
	private void setIntereses(ArrayList<String> aux) {
		
		this.intereses=aux;
		
	}
	
	public void cargarIntereses() {
		
		try {
	        
			 setIntereses((ArrayList<String>) Files.readAllLines(Paths.get("intereses.txt")));
	        
	      }
	      catch(Exception e){
	         
	         crearInteresesFile();
	      }
	}
	
	
	public void crearInteresesFile() {
		
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("intereses.txt",true);
            pw = new PrintWriter(fichero);

            //pw.println(cadena);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
        
		
	}
	
	public void addInteres(String cadena) {
		
		this.intereses.add(cadena);
		guardarInteres(cadena);
		
		
	}
	
	public void guardarInteres(String cadena) {
		
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("properties.txt",true);
            pw = new PrintWriter(fichero);

            pw.println(cadena);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           
        	   if (null != fichero)
        		  fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
        
		
	}
	
	public ArrayList<String> getIntereses(){
		
		return intereses;
	}

}
