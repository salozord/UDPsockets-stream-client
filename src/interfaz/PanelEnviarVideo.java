package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PanelEnviarVideo extends JPanel implements ActionListener 
{

	private static final long serialVersionUID = 1L;
	public static final String ENVIAR = "ENVIAR";
	public static final String SELECCIONAR = "SELECCIONAR";
	
	private InterfazCliente principal;
	
	private JTextField nombreArchivo;
	private JButton butSeleccionar;
	private JButton	butEnviar;
	private JProgressBar progressBar;
	
	public PanelEnviarVideo(InterfazCliente p) {
		principal = p;
		setPreferredSize(new Dimension(300, 200));
		setLayout(new BorderLayout());
		setBorder(new TitledBorder("Enviar Vídeo"));
		
		inicializarPanelEnviar();
	}
	
	private void inicializarPanelEnviar() {
		
		// Creación y configuración del panel central
		JPanel centro = new JPanel();
		centro.setLayout(new GridLayout(4, 1, 0, 4));
		
		JLabel nomArch = new JLabel("A Enviar:");
		nomArch.setHorizontalAlignment(JLabel.CENTER);
		nombreArchivo = new JTextField("----");
		nombreArchivo.setHorizontalAlignment(JTextField.CENTER);
		nombreArchivo.setEditable(false);
		
		progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
		
		centro.add(new JLabel());
		centro.add(nomArch);
		centro.add(nombreArchivo);
		centro.add(progressBar);
		
		// Creación y configuración del panel de botones
		JPanel buts = new JPanel();
		buts.setLayout(new GridLayout(1, 2, 4, 0));
		
		butSeleccionar = new JButton("Seleccionar");
		butSeleccionar.setActionCommand(SELECCIONAR);
		butSeleccionar.addActionListener(this);
		butEnviar = new JButton("Enviar");
		butEnviar.setActionCommand(ENVIAR);
		butEnviar.addActionListener(this);
		butEnviar.setEnabled(false);
		
		buts.add(butSeleccionar);
		buts.add(butEnviar);
		
		// Añadir paneles al panel global
		add(centro, BorderLayout.CENTER);
		add(buts, BorderLayout.SOUTH);
	}

	private File seleccionarArchivo() {
		JFileChooser fc = new JFileChooser( "./data" );
		FileFilter filter = new FileNameExtensionFilter("Video files", new String[] {"3gp", "avi", "flv", "m4v", "mov", "mp4", "mpg", "mpeg", "wmv", "webm", "mkv"});
		fc.setFileFilter(filter);
		fc.setDialogTitle( "Seleccionar archivo a enviar al servidor" );
		int resultado = fc.showOpenDialog( this );
		File seleccionado = null;

		if( resultado == JFileChooser.APPROVE_OPTION )
		{
			seleccionado = fc.getSelectedFile( );
		}
		return seleccionado;
	}
	
	public String getNombreArchivo() {
		return nombreArchivo.getText();
	}
	
	public void actualizarNombreArchivo(String nuevo) {
		nombreArchivo.setText(nuevo);
	}
	
	public void habilitarBotonEnviar(boolean habilitar) {
		butEnviar.setEnabled(habilitar);
	}
	
	public void actualizarProgreso(int nuevo) {
		progressBar.setValue(nuevo);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		
		if(comando.equals(ENVIAR)) {
			habilitarBotonEnviar(false);
			principal.enviarArchivo();
		}
		else if(comando.equals(SELECCIONAR)) {
			File sel = seleccionarArchivo();
			if(sel != null) {
				principal.getCliente().setArchivoEnviar(sel);
				actualizarNombreArchivo(sel.getName());
				habilitarBotonEnviar(true);
			}
		}
	}
}
