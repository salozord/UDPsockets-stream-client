package interfaz;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logica.Cliente;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.factory.VideoSurfaceApi;
import uk.co.caprica.vlcj.player.base.ControlsApi;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.ComponentVideoSurface;

public class InterfazCliente extends JFrame
{

	private static final long serialVersionUID = 1L;
	
	private Cliente cliente;
//	private MediaPlayerFactory mediaPlayerFactory;
//	private EmbeddedMediaPlayer mediaPlayer;
	
	private DialogoAcceso dialogoAcceso;
	private Canvas canvas;
	private PanelEnviarVideo panelEnviarVideo;
	private PanelCanales panelCanales;
	
	public InterfazCliente() {
		
		cliente = new Cliente();
		dialogoAcceso = new DialogoAcceso(this);
		
	}
	
	public void inicializar() 
	{
		
		setLocationRelativeTo(null);
		setTitle("Video Streaming Client");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1100, 600);
		setResizable(false);
		setLayout(new BorderLayout());
		
		canvas = new Canvas();
		canvas.setBackground(Color.black);
		canvas.setSize(800, 600);
		EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        EmbeddedMediaPlayer embeddedMediaPlayer = mediaPlayerComponent.mediaPlayer();
        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(new String[] {"--no-plugins-cache", "--no-video-title-show", "--no-snapshot-preview"}) ;
        embeddedMediaPlayer.videoSurface().set(mediaPlayerFactory.videoSurfaces().newVideoSurface(canvas));
//        embeddedMediaPlayer.chapters()..setPlaySubItems(true);
        
		JPanel aux = new JPanel();
		aux.setLayout(new BorderLayout());
		panelEnviarVideo = new PanelEnviarVideo(this);
		panelCanales = new PanelCanales(this);
		
		aux.add(panelEnviarVideo, BorderLayout.NORTH);
		aux.add(panelCanales, BorderLayout.CENTER);
		
		add(aux, BorderLayout.WEST);
		add(canvas, BorderLayout.CENTER);
		setVisible(true);
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


	public void conectar(int index) {
		
	}
	
	public void enviarArchivo() {
		// TODO Auto-generated method stub
		
	}
	
	public void reproducir() {
		// TODO Auto-generated method stub
		
	}

	public void pausar() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		try {			
			InterfazCliente i = new InterfazCliente();
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
}
