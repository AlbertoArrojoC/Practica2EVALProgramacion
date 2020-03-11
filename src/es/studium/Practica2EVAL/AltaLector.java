package es.studium.Practica2EVAL;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AltaLector extends Frame
		implements
			WindowListener,
			ActionListener {
	private static final long serialVersionUID = 1L;

	Label lblNombre = new Label("Nombre:");
	TextField txtNombre = new TextField(20);

	Label lblApellidos = new Label("Apellidos:");
	TextField txtApellidos = new TextField(20);

	Label lblDireccion = new Label("Direccion:");
	TextField txtDireccion = new TextField(20);

	Label lblTelefono = new Label("Telefono:");
	TextField txtTelefono = new TextField(20);

	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	AltaLector() {
		setTitle("Alta de Lector");
		setLayout(new FlowLayout());
		add(lblNombre);
		add(txtNombre);

		add(lblApellidos);
		add(txtApellidos);

		add(lblDireccion);
		add(txtDireccion);

		add(lblTelefono);
		add(txtTelefono);

		add(btnAceptar);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setSize(300, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object objetoPulsado = e.getSource();
		if (objetoPulsado.equals(btnLimpiar)) {
			txtNombre.selectAll();
			txtNombre.setText("");

			txtApellidos.selectAll();
			txtApellidos.setText("");

			txtDireccion.selectAll();
			txtDireccion.setText("");

			txtTelefono.selectAll();
			txtTelefono.setText("");

			txtNombre.requestFocus();
		} else if (objetoPulsado.equals(btnAceptar)) {
			// Conectar BD
			Connection con = conectar();
			// Hacer el INSERT
			int respuesta = insertar(con, "lector", txtNombre.getText(),
					txtApellidos.getText(), txtDireccion.getText(),
					txtTelefono.getText());

			// Mostramos resultado
			if (respuesta == 0) {
				System.out.println("Alta de lector correcta");
			} else {
				System.out.println("Error en el alta de lector");
			}
			// Desconectar de la base
			desconectar(con);
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		setVisible(false);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public Connection conectar() {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/bibliotecapr?autoReconnect=true&useSSL=false";
		String user = "root";
		String password = "Studium2019;";
		Connection con = null;
		try {
			// Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			// Establecer la conexión con la BD 
			con = DriverManager.getConnection(url, user, password);
			if (con != null) {
				System.out.println("Conectado a la base de datos");
			}
		} catch (SQLException ex) {
			System.out.println(
					"ERROR:La dirección no es válida o el usuario y clave");
			ex.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error 1-" + cnfe.getMessage());
		}
		return con;
	}

	public int insertar(Connection con, String tabla, String nombreLector,
			String apellidosLector, String direccionLector, String telefonoLector) {
		int respuesta = 0;
		try {
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			String cadenaSQL = "INSERT INTO " + tabla + " VALUES (null, '"
					+ nombreLector + "', '" + apellidosLector + "', '" + direccionLector
					+ "','" + telefonoLector + "')";

			System.out.println(cadenaSQL);
			sta.executeUpdate(cadenaSQL);
			sta.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al hacer un Insert");
			ex.printStackTrace();
			respuesta = 1;
		}
		return respuesta;
	}

	public void desconectar(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
		}
	}
}
