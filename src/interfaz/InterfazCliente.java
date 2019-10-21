package interfaz;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logica.Cliente;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

public class InterfazCliente extends JFrame
{

	private static final long serialVersionUID = 1L;
	
	private Cliente cliente;
	private EmbeddedMediaPlayerComponent reproductor;
	
	private DialogoAcceso dialogoAcceso;
	private PanelEnviarVideo panelEnviarVideo;
	private PanelCanales panelCanales;
	
	public InterfazCliente() {
		
		cliente = new Cliente();
		dialogoAcceso = new DialogoAcceso(this);
		
	}
	
	public void inicializar() 
	{
		dialogoAcceso = null;
		setTitle("Video Streaming Client");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1100, 600);
		setResizable(false);
		setLayout(new BorderLayout());
		
		reproductor = new EmbeddedMediaPlayerComponent();
		reproductor.setSize(800, 600);
        
		JPanel aux = new JPanel();
		aux.setLayout(new BorderLayout());
		panelEnviarVideo = new PanelEnviarVideo(this);
		panelCanales = new PanelCanales(this);
		
		aux.add(panelEnviarVideo, BorderLayout.NORTH);
		aux.add(panelCanales, BorderLayout.CENTER);
		
		add(aux, BorderLayout.WEST);
		add(reproductor, BorderLayout.CENTER);
		setVisible(true);
		repaint();
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void autenticacion(String usuario, String pass) {
		
		try {
			boolean auth = cliente.autenticacion(usuario, pass);
			// Si se autentica correctamente
			if(auth) {
				dialogoAcceso.dispose();
				inicializar();
			}
			else {
				throw new Exception("Usuario o contraseña incorrectos. Por favor intentelo nuevamente.");
			}
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}


	public void conectar(int index) throws Exception {
		// Cambia el canal actual al cual el cliente está conectado para recibir vídeo
		String nuevoCanal = cliente.getListaCanales().get(index).split("/")[0];
		cliente.setCanalActual(nuevoCanal);
		
		//Actualiza el reproductor en el canal actual para recibir el streaming
		reproductor.mediaPlayer().media().play(cliente.getCanalActual());
	}
	
	public void enviarArchivo() {
		try {
			JOptionPane.showMessageDialog(this, "Espere mientas se envía su vídeo al servidor.", "Información", JOptionPane.INFORMATION_MESSAGE);
			cliente.enviarArchivo();
			panelEnviarVideo.actualizarNombreArchivo("-----");
			panelEnviarVideo.habilitarBotonEnviar(false);
			panelCanales.actualizarLista();
			validate();
			repaint();
			JOptionPane.showMessageDialog(this, "El archivo fue enviado correctamente al servidor!\nRevise los canales actualizados.", "Archivo Enviado", JOptionPane.INFORMATION_MESSAGE);
		} 
		catch (Exception e) {
			panelEnviarVideo.actualizarNombreArchivo("-----");
			panelEnviarVideo.habilitarBotonEnviar(false);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void playPausa() {
		reproductor.mediaPlayer().controls().pause();
	}
	
	public static void main(String[] args) {
		try {			
			@SuppressWarnings("unused")
			InterfazCliente i = new InterfazCliente();
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
}
