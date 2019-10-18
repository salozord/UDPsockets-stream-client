package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DialogoAcceso extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private static final String LOGIN = "LOGIN";
	
	private InterfazCliente principal;
	private JTextField nombreUsuario;
	private JPasswordField contrasenia;
	private JButton butLogin;
	
	public DialogoAcceso(InterfazCliente p) {
		principal = p;
		
		setSize(400, 180);
		setTitle("Autenticación");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		setLayout(new BorderLayout());
		
		JPanel aux = new JPanel();
		aux.setLayout(new GridLayout(4, 4, 10, 6));
		
		JLabel lb = new JLabel("Usuario:");
		lb.setHorizontalAlignment(JLabel.CENTER);
		nombreUsuario = new JTextField();
		JLabel lb2 = new JLabel("Contraseña:");
		lb2.setHorizontalAlignment(JLabel.CENTER);
		contrasenia = new JPasswordField();
		
		aux.add(new JLabel());
		aux.add(new JLabel());
		aux.add(new JLabel());
		aux.add(new JLabel());
		aux.add(new JLabel());
		aux.add(lb);
		aux.add(nombreUsuario);
		aux.add(new JLabel());
		aux.add(new JLabel());
		aux.add(lb2);
		aux.add(contrasenia);
		aux.add(new JLabel());
		aux.add(new JLabel());
		aux.add(new JLabel());
		aux.add(new JLabel());
		aux.add(new JLabel());

		
		JPanel buts = new JPanel();
		buts.setLayout(new GridLayout(1, 3, 0, 20));
		
		butLogin = new JButton("Ingresar");
		butLogin.setActionCommand(LOGIN);
		butLogin.addActionListener(this);
		
		buts.add(new JLabel());
		buts.add(butLogin);
		buts.add(new JLabel());
		
		add(aux, BorderLayout.CENTER);
		add(buts, BorderLayout.SOUTH);
		setVisible(true);
	}

	private void ingresar() throws Exception {
		String usuario = nombreUsuario.getText().trim();
		char[] chars = contrasenia.getPassword();
		
		if(usuario == null || chars == null || usuario.equals("") || chars.length == 0 )
			throw new Exception("No se han llenado todos los campos. Por favor intente nuevamente");
		
		String pass = new String(chars);
		if(pass.contains(" ") || usuario.contains(" "))
			throw new Exception("El nombre de usuario o la contraseña no deberían contener espacios. Por favor intente nuevamente");
		
		principal.autenticacion(usuario, pass);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		String command = event.getActionCommand();
		if(command.equals(LOGIN)) {
			try {
				ingresar();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}			
		}
	}

}
