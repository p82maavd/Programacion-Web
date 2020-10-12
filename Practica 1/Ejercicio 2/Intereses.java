package practica1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Intereses {
	
	private ArrayList<String> intereses;
	
	
	public Intereses() {
		
		this.intereses=new ArrayList<String>();
		cargarIntereses();
	}
	
	private void setIntereses(ArrayList<String> aux) {
		
		this.intereses=aux;
		
	}
	
	public void cargarIntereses() {
		
		File archivo;
		FileReader fr=null;
		BufferedReader br;
		ArrayList<String> aux=new ArrayList<String>();
		try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).
	         archivo = new File ("properties.txt");
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);

	         // Lectura del fichero
	         String linea;
	         while((linea=br.readLine())!=null)
	            aux.add(linea);
	         setIntereses(aux);
	      }
	      catch(Exception e){
	         //e.printStackTrace();
	         crearProperties();
	      }finally{
	          // En el finally cerramos el fichero, para asegurarnos
	          // que se cierra tanto si todo va bien como si salta 
	          // una excepcion.
	          try{                    
	             if( null != fr ){   
	                fr.close();     
	             }                  
	          }catch (Exception e2){ 
	             e2.printStackTrace();
	          }
	      }
		
		
	}
	
	public void crearProperties() {
		
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("properties.txt",true);
            pw = new PrintWriter(fichero);

            //pw.println(cadena);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
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
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
        
		
	}
	
	public ArrayList<String> getIntereses(){
		cargarIntereses();
		return intereses;
	}

}
