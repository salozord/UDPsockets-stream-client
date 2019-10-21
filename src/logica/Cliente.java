package logica;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import interfaz.InterfazCliente;

public class Cliente {

	public static final String HOST = "localhost";
	public static final int PORT = 8787;
	public static final int TAMANIO_SEGMENTO = 8192;
	public static final String SEPARADOR = ";";
	public static final String AUTENTICADO = "AUTENTICADO";
	public static final String ERROR = "ERROR";
	public static final String VIDEO = "VIDEO";
	public static final String PROTOCOLO = "rtp://@";
	
	private Socket s;
	private BufferedReader in;
	private PrintWriter out;
	private ArrayList<String> listaCanales;
	private File seleccionado;
	private String canalActual;
	private int progreso;
	
	public Cliente() {
		listaCanales = new ArrayList<>();
		seleccionado = null;
		canalActual = null;
		progreso = 0;
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
			
		}
		else if(!confirmacion.equals(ERROR)) {
			throw new Exception("Ocurrió un error con la autenticación"); 
		}
		
		return autenticado;
	}
	
	public String getCanalActual() {
		return canalActual;
	}
	
	public void setCanalActual(String nuevo) {
		canalActual = PROTOCOLO + nuevo;
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
	
	public int getProgreso() {
		return progreso;
	}

	public void enviarArchivo(InterfazCliente i) throws Exception {
		progreso = 0;
		Thread envio = new Thread(new Runnable() {
			@Override
			public void run() {
				try {					
					// Se notifica al servidor que se enviará un video
					
					out.println(VIDEO + SEPARADOR + seleccionado.getName());
					
					byte[] contenedor = new byte[TAMANIO_SEGMENTO];
					BufferedInputStream bis = new BufferedInputStream(new FileInputStream(seleccionado));
					DataOutputStream dos =  new DataOutputStream(s.getOutputStream());
					
					double total = (double)seleccionado.length();
					
					dos.writeLong(seleccionado.length());
					dos.flush();
					
					int r;
					int suma = 0;
					while ((r = bis.read(contenedor)) != -1) {
						dos.write(contenedor, 0, r);
						dos.flush();
						suma += r;
						progreso = (int) ((suma/total)*100);
					}
					bis.close();
					
					String nuevaLista = in.readLine();
					if(nuevaLista.equals(ERROR)) {
						throw new Exception("Ocurrió un error al enviar el archivo o al recibirlo por parte del cliente. Intentelo nuevamente.");
					}
					
					String[] nuevos = nuevaLista.split(SEPARADOR);
					listaCanales = new ArrayList<>();
					for (String c : nuevos) {
						listaCanales.add(c);
					}

					i.notificarEnvio();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		envio.start();
	}
}
