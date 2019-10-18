package interfaz;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import logica.Cliente;

public class InterfazCliente extends JFrame
{

	private static final long serialVersionUID = 1L;
	private DialogoAcceso dialogoAcceso;
	
	private Cliente cliente;
	
	public InterfazCliente() {
		
		cliente = new Cliente();
		dialogoAcceso = new DialogoAcceso(this);
		
	}
	
	public void inicializar() 
	{
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 800);
		setResizable(false);
		
		setLayout(new BorderLayout());
		Canvas c = new Canvas();
		c.setBackground(Color.black);
		
		add(c, BorderLayout.NORTH);
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
				
			}
			else {
				throw new Exception("Usuario o contraseña incorrectos. Por favor intentelo nuevamente.");
			}
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
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
