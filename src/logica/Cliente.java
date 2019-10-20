package logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Cliente {

	public static final String HOST = "localhost";
	public static final int PORT = 8787;
	public static final String SEPARADOR = ";";
	public static final String AUTENTICADO = "AUTENTICADO";
	public static final String ERROR = "ERROR";
	public static final String VIDEO = "VIDEO";
	
	private Socket s;
	private BufferedReader in;
	private PrintWriter out;
	private ArrayList<String> listaCanales;
	private File seleccionado;
	
	public Cliente() {
		listaCanales = new ArrayList<>();
		seleccionado = null;
	}
	
	public boolean autenticacion(String usuario, String contrasenia) throws Exception {
		
		// Inicializa el socket para la autenticación.
		boolean autenticado = false;
		s = new Socket(HOST, PORT);
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
		
		// Envía cadena con la autenticación para iniciar el proceso.
		String auth = usuario  + SEPARADOR + contrasenia;
		out.println(auth);
		
		String confirmacion = in.readLine();
		if(confirmacion.equals(AUTENTICADO)) {
			autenticado = true;
			
			String lista = in.readLine();
			if(lista == null)
				throw new Exception("Se autenticó pero no se recibió la lista de canales.");
			
			String[] elementos = lista.split(SEPARADOR);
			for(String canal: elementos)
				listaCanales.add(canal);
			
			// YA ANO SE MUEREN
//			in.close();
//			out.close();
//			s.close();
		}
		else if(!confirmacion.equals(ERROR)) {
			throw new Exception("Ocurrió un error con la autenticación"); 
		}
		
		return autenticado;
	}
	
	public ArrayList<String> getListaCanales() {
		return listaCanales;
	}

	public void setArchivoEnviar(File sel) {
		seleccionado = sel;
	}
	
	public File getArchivoEnviar() {
		return seleccionado;
	}
}
