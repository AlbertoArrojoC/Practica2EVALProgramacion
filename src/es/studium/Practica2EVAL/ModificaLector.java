package es.studium.Practica2EVAL;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ModificaLector extends Frame
		implements
			WindowListener,
			ActionListener {
	private static final long serialVersionUID = 1L;
	Label lblModificarLector = new Label("lector a modificar:");
	Choice choLector = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Dialog seguro;
	Button btnSi;
	Button btnNo;
	Frame modificarLector;
	Button btnAceptarCambios;
	Button btnCancelarCambios;
	TextField txtidLector;
	TextField txtnombreLector;
	TextField txtapellidosLector;
	TextField txtdireccionLector;
	TextField txtTelefonoLector;

	ModificaLector() {
		add(lblModificarLector);
		setTitle("Modificar lector");
		setLayout(new FlowLayout());
		// Montar el Choice
		choLector.add("Seleccionar uno...");
		// Conectar a la base de datos
		Connection con = conectar();
		// Sacar los datos de la tabla edificios
		// Rellenar el Choice
		String sqlSelect = "SELECT * FROM lector";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) {
				choLector.add(rs.getInt("idLector") + "-"
						+ rs.getString("nombreLector") + ", "
						+ rs.getString("apellidosLector") + ", "
						+ rs.getString("direccionLector") + ", "
						+ rs.getString("telefonoLector"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		// Cerrar la conexión
		desconectar(con);
		add(choLector);
		add(btnAceptar);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setSize(380, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object objetoPulsado = e.getSource();
		if (objetoPulsado.equals(btnLimpiar)) {
			choLector.select(0);
		} else if (objetoPulsado.equals(btnAceptar)) {
			// Sacar el id del elemento elegido
			int id = Integer
					.parseInt(choLector.getSelectedItem().split("-")[0]);
			// Crear un Frame igual que el ALTA
			modificarLector = new Frame("Modificar lector");
			modificarLector.setLayout(new FlowLayout());
			Label lblidLector = new Label("idLector:");
			Label lblnombreLector = new Label("Nombre:");
			Label lblapellidosLector = new Label("Apellidos:");
			Label lbldireccionLector = new Label("Direccion:");
			Label lbltelefonoLector = new Label("Telefono:");

			txtidLector = new TextField(20);

			txtnombreLector = new TextField(20);

			txtapellidosLector = new TextField(20);

			txtdireccionLector = new TextField(20);

			txtTelefonoLector = new TextField(20);

			btnAceptarCambios = new Button("Aceptar");
			btnCancelarCambios = new Button("Cancelar");

			modificarLector.add(lblidLector);
			txtidLector.setEnabled(false);
			modificarLector.add(txtidLector);
			modificarLector.add(lblnombreLector);
			modificarLector.add(txtnombreLector);

			modificarLector.add(lblapellidosLector);
			modificarLector.add(txtapellidosLector);

			modificarLector.add(lbldireccionLector);
			modificarLector.add(txtdireccionLector);

			modificarLector.add(lbltelefonoLector);
			modificarLector.add(txtTelefonoLector);

			btnAceptarCambios.addActionListener(this);
			btnCancelarCambios.addActionListener(this);
			modificarLector.add(btnAceptarCambios);
			modificarLector.add(btnCancelarCambios);
			// Pero relleno-->
			// Conectar a la base de datos
			Connection con = conectar();
			// Seleccionar los datos del elemento
			mostrarDatos(con, id, txtidLector, txtnombreLector,
					txtapellidosLector, txtdireccionLector, txtTelefonoLector);
			// seleccionado
			// Mostrarlos
			desconectar(con);
			modificarLector.addWindowListener(this);
			modificarLector.setResizable(false);
			modificarLector.setSize(200, 600);
			modificarLector.setLocationRelativeTo(null);
			modificarLector.setVisible(true);
		} else if (objetoPulsado.equals(btnNo)) {
			seguro.setVisible(false);
		} else if (objetoPulsado.equals(btnCancelarCambios)) {
			modificarLector.setVisible(false);
		} else if (objetoPulsado.equals(btnAceptarCambios)) {
			int id = Integer.parseInt(txtidLector.getText());
			String nombreLector = txtnombreLector.getText();
			String apellidosLector = txtapellidosLector.getText();
			String direccionLector = txtdireccionLector.getText();
			String telefonoLector = txtTelefonoLector.getText();

			// Conectar a la base de datos
			Connection con = conectar();
			// Ejecutar el UPDATE
			String sql = "UPDATE lector SET nombreLector = '" + nombreLector
					+ "', apellidosLector = '" + apellidosLector
					+ "', direccionLector = '" + direccionLector
					+ "', telefonoLector = '" + telefonoLector
					+ "' WHERE idLector=" + id;
			try {
				// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
				Statement stmt = con.createStatement();
				stmt.executeUpdate(sql);
				stmt.close();
			} catch (SQLException ex) {
				System.out.println("ERROR:al consultar");
				ex.printStackTrace();
			}
			// Cerrar la conexión
			desconectar(con);
			modificarLector.setVisible(false);
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
		if (this.isActive()) {
			setVisible(false);
		} else if (modificarLector.isActive()) {
			modificarLector.setVisible(false);
		} else {
			seguro.setVisible(false);
		}
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
	public int borrar(Connection con, int idLector) {
		int respuesta = 0;
		String sql = "DELETE FROM libros WHERE idLector = " + idLector;
		System.out.println(sql);
		try {
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			sta.executeUpdate(sql);
			sta.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al hacer un Delete");
			ex.printStackTrace();
			respuesta = 1;
		}
		return respuesta;
	}
	public void mostrarDatos(Connection con, int idLector, TextField id,
			TextField tituloLibro, TextField editorialLibro,
			TextField isbnLibro, TextField paginasLibro) {
		String sql = "SELECT * FROM lector WHERE idLector = " + idLector;
		try {
			id.setText(idLector + "");
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			ResultSet rs = sta.executeQuery(sql);

			while (rs.next()) {
				String t = rs.getString("nombreLector");
				tituloLibro.setText(t);
				String e = rs.getString("apellidosLector");
				editorialLibro.setText(e);
				String i = rs.getString("direccionLector");
				isbnLibro.setText(i);
				String p = rs.getString("telefonoLector");
				paginasLibro.setText(p);

			}
			sta.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al hacer un Delete");
			ex.printStackTrace();
		}
	}
	public void desconectar(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
		}
	}
}
