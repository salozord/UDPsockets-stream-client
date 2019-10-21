package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class PanelCanales extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	public static final String CONECTAR = "CONECTAR";
	public static final String PLAYPAUSA = "PLAYPAUSA";
//	public static final String PAUSA = "PAUSA";
	
	
	private InterfazCliente principal;
	
	private JComboBox<String> listaCanales;
	private JButton butConectar;
	private JButton butPlay;
//	private JButton butPausa;

	public PanelCanales(InterfazCliente p) {
		principal = p;
		setPreferredSize(new Dimension(300, 400));
		setLayout(new BorderLayout());
		
		inicializarPanelCanales();
	}
	
	private void inicializarPanelCanales() {
		
		// Inicializo y configuro panel central de canales
		JPanel centro = new JPanel();
		centro.setLayout(new GridLayout(4, 1, 0, 4));
		centro.setBorder(new TitledBorder("Canales"));
		
		Object[] objs = principal.getCliente().getListaCanales().toArray();
		listaCanales = new JComboBox<String>(Arrays.copyOf(objs, objs.length, String[].class));
		butConectar = new JButton("Conectar");
		butConectar.addActionListener(this);
		butConectar.setActionCommand(CONECTAR);
		
		centro.add(new JLabel("Seleccionar Canal:"));
		centro.add(listaCanales);
		centro.add(new JLabel());
		centro.add(butConectar);
		
		// Inicializo y configuro panel de controles
		JPanel sur = new JPanel();
		sur.setLayout(new GridLayout(1, 3, 4, 4));
		sur.setBorder(new TitledBorder("Controles"));
		
		butPlay = new JButton("Play");
		butPlay.addActionListener(this);
		butPlay.setActionCommand(PLAYPAUSA);
		butPlay.setEnabled(false);
		
//		butPausa = new JButton("Pausa");
//		butPausa.addActionListener(this);
//		butPausa.setActionCommand(PAUSA);
//		butPausa.setEnabled(false);
		
		sur.add(new JLabel());
		sur.add(butPlay);
		sur.add(new JLabel());
//		sur.add(butPausa);
		
		add(centro, BorderLayout.CENTER);
		add(sur, BorderLayout.SOUTH);
	}
	
	private void actualizarBotones(String comando) {
		if(comando.equals(PLAYPAUSA)) {
			if(butPlay.getText().equals("Play")) {
				butPlay.setText("Pausar");
			}
			else {
				butPlay.setText("Play");
			}
		}
	}
	
	public void actualizarLista() {
		Object[] objs = principal.getCliente().getListaCanales().toArray();
		listaCanales = new JComboBox<String>(Arrays.copyOf(objs, objs.length, String[].class));
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if(comando.equals(CONECTAR)) {
			principal.conectar(listaCanales.getSelectedIndex());
			butPlay.setText("Play");
			actualizarBotones(PLAYPAUSA);
		}
		else if (comando.equals(PLAYPAUSA)) {				
			actualizarBotones(comando);
			principal.playPausa();
		}
//		else if(comando.equals(PAUSA)) {
//			principal.pausar();
//		}
	}
}
