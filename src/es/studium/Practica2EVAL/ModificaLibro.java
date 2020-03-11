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

public class ModificaLibro extends Frame
		implements
			WindowListener,
			ActionListener {
	private static final long serialVersionUID = 1L;
	Label lblModificarLibro = new Label("Libro a modificar:");
	Choice choLibro = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Dialog seguro;
	Button btnSi;
	Button btnNo;
	Frame modificarLibro;
	Button btnAceptarCambios;
	Button btnCancelarCambios;
	TextField txtIdLibro;
	TextField txtTituloLibro;
	TextField txtEditorialLibro;
	TextField txtISBNLibro;
	TextField txtPaginasLibro;

	ModificaLibro() {
		add(lblModificarLibro);
		setTitle("Modificar libro");
		setLayout(new FlowLayout());
		// Montar el Choice
		choLibro.add("Seleccionar uno...");
		// Conectar a la base de datos
		Connection con = conectar();
		// Sacar los datos de la tabla edificios
		// Rellenar el Choice
		String sqlSelect = "SELECT * FROM libro";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) {
				choLibro.add(
						rs.getInt("idLibro") + "-" + rs.getString("tituloLibro")
								+ ", " + rs.getString("editorialLibro") + ", "
								+ rs.getString("isbnLibro") + ", "
								+ rs.getString("paginasLibro"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		// Cerrar la conexión
		desconectar(con);
		add(choLibro);
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
			choLibro.select(0);
		} else if (objetoPulsado.equals(btnAceptar)) {
			// Sacar el id del elemento elegido
			int id = Integer.parseInt(choLibro.getSelectedItem().split("-")[0]);
			// Crear un Frame igual que el ALTA
			modificarLibro = new Frame("Modificar Libro");
			modificarLibro.setLayout(new FlowLayout());
			Label lblIdLibro = new Label("idLibro:");
			Label lblTituloLibro = new Label("Titulo:");
			Label lblEditorialLibro = new Label("Editorial:");
			Label lblISBNLibro = new Label("ISBN:");
			Label lblPaginasLibro = new Label("Paginas:");

			txtIdLibro = new TextField(20);

			txtTituloLibro = new TextField(20);

			txtEditorialLibro = new TextField(20);

			txtISBNLibro = new TextField(20);

			txtPaginasLibro = new TextField(20);

			btnAceptarCambios = new Button("Aceptar");
			btnCancelarCambios = new Button("Cancelar");

			modificarLibro.add(lblIdLibro);
			txtIdLibro.setEnabled(false);
			modificarLibro.add(txtIdLibro);
			modificarLibro.add(lblTituloLibro);
			modificarLibro.add(txtTituloLibro);

			modificarLibro.add(lblEditorialLibro);
			modificarLibro.add(txtEditorialLibro);

			modificarLibro.add(lblISBNLibro);
			modificarLibro.add(txtISBNLibro);

			modificarLibro.add(lblPaginasLibro);
			modificarLibro.add(txtPaginasLibro);

			btnAceptarCambios.addActionListener(this);
			btnCancelarCambios.addActionListener(this);
			modificarLibro.add(btnAceptarCambios);
			modificarLibro.add(btnCancelarCambios);
			// Pero relleno-->
			// Conectar a la base de datos
			Connection con = conectar();
			// Seleccionar los datos del elemento
			mostrarDatos(con, id, txtIdLibro, txtTituloLibro, txtEditorialLibro,
					txtISBNLibro, txtPaginasLibro);
			// seleccionado
			// Mostrarlos
			desconectar(con);
			modificarLibro.addWindowListener(this);
			modificarLibro.setResizable(false);
			modificarLibro.setSize(200, 600);
			modificarLibro.setLocationRelativeTo(null);
			modificarLibro.setVisible(true);
		} else if (objetoPulsado.equals(btnNo)) {
			seguro.setVisible(false);
		} else if (objetoPulsado.equals(btnCancelarCambios)) {
			modificarLibro.setVisible(false);
		} else if (objetoPulsado.equals(btnAceptarCambios)) {
			int id = Integer.parseInt(txtIdLibro.getText());
			String tituloLibro = txtTituloLibro.getText();
			String editorialLibro = txtEditorialLibro.getText();
			String isbnLibro = txtISBNLibro.getText();
			String paginasLibro = txtPaginasLibro.getText();

			// Conectar a la base de datos
			Connection con = conectar();
			// Ejecutar el UPDATE
			String sql = "UPDATE libro SET tituloLibro = '" + tituloLibro
					+ "', editorialLibro = '" + editorialLibro
					+ "', isbnLibro = '" + isbnLibro + "', paginasLibro = '"
					+ paginasLibro + "' WHERE idLibro=" + id;
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
			modificarLibro.setVisible(false);
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
		} else if (modificarLibro.isActive()) {
			modificarLibro.setVisible(false);
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
	public int borrar(Connection con, int idLibro) {
		int respuesta = 0;
		String sql = "DELETE FROM libros WHERE idLibro = " + idLibro;
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
	public void mostrarDatos(Connection con, int idLibro, TextField id,
			TextField tituloLibro, TextField editorialLibro,
			TextField isbnLibro, TextField paginasLibro) {
		String sql = "SELECT * FROM libro WHERE idLibro = " + idLibro;
		try {
			id.setText(idLibro + "");
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			ResultSet rs = sta.executeQuery(sql);

			while (rs.next()) {
				String t = rs.getString("tituloLibro");
				tituloLibro.setText(t);
				String e = rs.getString("editorialLibro");
				editorialLibro.setText(e);
				String i = rs.getString("isbnLibro");
				isbnLibro.setText(i);
				String p = rs.getString("paginasLibro");
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
