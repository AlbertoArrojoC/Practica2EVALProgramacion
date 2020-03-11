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

public class ModificaEjemplar extends Frame
		implements
			WindowListener,
			ActionListener {
	private static final long serialVersionUID = 1L;
	Choice choEjemplar = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Dialog seguro;
	Button btnSi;
	Button btnNo;
	Frame modificarEjemplar;
	Button btnAceptarCambios;
	Button btnCancelarCambios;
	TextField txtIdEjemplar;
	TextField txtidLocalizacionEjemplar;
	TextField txtidLibroFK;
	TextField txtfechaDelPrestamo;
	TextField txtfechaDeDevolucion;

	ModificaEjemplar() {
		setTitle("Modificar Ejemplar");
		setLayout(new FlowLayout());
		// Montar el Choice
		choEjemplar.add("Seleccionar uno...");
		// Conectar a la base de datos
		Connection con = conectar();
		// Sacar los datos de la tabla edificios
		// Rellenar el Choice
		String sqlSelect = "SELECT * FROM ejemplar";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) {
				choEjemplar.add(rs.getInt("idEjemplar") + "-"
						+ rs.getString("localizacionEjemplar")+", "
						+ rs.getString("idLibroFK1"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		// Cerrar la conexión
		desconectar(con);
		add(choEjemplar);
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
			choEjemplar.select(0);
		} else if (objetoPulsado.equals(btnAceptar)) {
			// Sacar el id del elemento elegido
			int id = Integer
					.parseInt(choEjemplar.getSelectedItem().split("-")[0]);
			// Crear un Frame igual que el ALTA
			modificarEjemplar = new Frame("Modificar Ejemplar");
			modificarEjemplar.setLayout(new FlowLayout());
			Label lblidEjemplar = new Label("idEjemplar:");
			Label lblLocalizacionEjemplar = new Label("Localización ejemplar:");
			Label lblLibroFK = new Label("idLibroFK1:");

			txtIdEjemplar = new TextField(20);

			txtidLocalizacionEjemplar = new TextField(20);

			txtidLibroFK = new TextField(20);

			btnAceptarCambios = new Button("Aceptar");
			btnCancelarCambios = new Button("Cancelar");

			modificarEjemplar.add(lblidEjemplar);
			txtIdEjemplar.setEnabled(false);
			modificarEjemplar.add(txtIdEjemplar);
			modificarEjemplar.add(lblLocalizacionEjemplar);
			modificarEjemplar.add(txtidLocalizacionEjemplar);

			modificarEjemplar.add(lblLibroFK);
			modificarEjemplar.add(txtidLibroFK);

			btnAceptarCambios.addActionListener(this);
			btnCancelarCambios.addActionListener(this);
			modificarEjemplar.add(btnAceptarCambios);
			modificarEjemplar.add(btnCancelarCambios);
			// Pero relleno-->
			// Conectar a la base de datos
			Connection con = conectar();
			// Seleccionar los datos del elemento
			mostrarDatos(con, id, txtIdEjemplar, txtidLocalizacionEjemplar,
					txtidLibroFK);
			// seleccionado
			// Mostrarlos
			desconectar(con);
			modificarEjemplar.addWindowListener(this);
			modificarEjemplar.setResizable(false);
			modificarEjemplar.setSize(200, 600);
			modificarEjemplar.setLocationRelativeTo(null);
			modificarEjemplar.setVisible(true);
		} else if (objetoPulsado.equals(btnNo)) {
			seguro.setVisible(false);
		} else if (objetoPulsado.equals(btnCancelarCambios)) {
			modificarEjemplar.setVisible(false);
		} else if (objetoPulsado.equals(btnAceptarCambios)) {
			int id = Integer.parseInt(txtIdEjemplar.getText());
			String localizacionEjemplar = txtidLocalizacionEjemplar.getText();
			String idLibroFK = txtidLibroFK.getText();

			// Conectar a la base de datos
			Connection con = conectar();
			// Ejecutar el UPDATE
			String sql = "UPDATE ejemplar SET localizacionEjemplar = '"
					+ localizacionEjemplar + "', idLibroFK1 = '" + idLibroFK
					+ "' WHERE idEjemplar=" + id;
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
			modificarEjemplar.setVisible(false);
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
		} else if (modificarEjemplar.isActive()) {
			modificarEjemplar.setVisible(false);
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
			// Establecer la conexión con la BD empresa
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
	public int borrar(Connection con, int idEjemplar) {
		int respuesta = 0;
		String sql = "DELETE FROM ejemplar WHERE idEjemplar = " + idEjemplar;
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
	public void mostrarDatos(Connection con, int idEjemplar, TextField id,
			TextField localizacionEjemplar, TextField idLibroFK1) {
		String sql = "SELECT * FROM ejemplar WHERE idEjemplar = " + idEjemplar;
		try {
			id.setText(idEjemplar + "");
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			ResultSet rs = sta.executeQuery(sql);

			while (rs.next()) {
				String t = rs.getString("localizacionEjemplar");
				localizacionEjemplar.setText(t);
				String e = rs.getString("idLibroFK1");
				idLibroFK1.setText(e);


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
