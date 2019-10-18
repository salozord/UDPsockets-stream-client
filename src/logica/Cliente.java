package logica;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Cliente {

	public static final String HOST = "localhost";
	public static final int PORT = 8000;
	public static final String SEPARADOR = ";";
	public static final String AUTENTICADO = "AUTENTICADO";
	public static final String ERROR = "ERROR";
	
	private Socket s;
	private ArrayList<String> listaCanales;
	
	public Cliente() {
		listaCanales = new ArrayList<>();
		
	}
	
	public boolean autenticacion(String usuario, String contrasenia) throws Exception {
		
		// Inicializa el socket para la autenticación.
		boolean autenticado = false;
		s = new Socket(HOST, PORT);
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintWriter out = new PrintWriter(s.getOutputStream(), true);
		
		// Envía cadena con la autenticación para iniciar el proceso.
		String auth = usuario  + SEPARADOR + contrasenia;
		out.println(auth);
		
		String confirmacion = in.readLine();
		if(confirmacion.equals(AUTENTICADO)) {
			autenticado = true;
			
			in.close();
			out.close();
			s.close();
		}
		else if(!confirmacion.equals(ERROR)) {
			throw new Exception("Ocurrió un error con la autenticación"); 
		}
		
		return autenticado;
	}
}
