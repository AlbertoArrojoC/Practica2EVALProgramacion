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

public class ModificaSacar extends Frame
		implements
			WindowListener,
			ActionListener {
	private static final long serialVersionUID = 1L;
	Choice choSacar = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Dialog seguro;
	Button btnSi;
	Button btnNo;
	Frame modificarSacar;
	Button btnAceptarCambios;
	Button btnCancelarCambios;
	TextField txtIdSaca;
	TextField txtidEjemplarFK2;
	TextField txtidLectorFK3;
	TextField txtfechaDelPrestamo;
	TextField txtfechaDeDevolucion;

	ModificaSacar() {
		setTitle("Modificar Sacar");
		setLayout(new FlowLayout());
		// Montar el Choice
		choSacar.add("Seleccionar uno...");
		// Conectar a la base de datos
		Connection con = conectar();
		// Sacar los datos de la tabla edificios
		// Rellenar el Choice
		String sqlSelect = "SELECT * FROM saca";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) {
				choSacar.add(
						rs.getInt("idSaca") + "-" + rs.getString("idEjemplarFK2")
								+ ", " + rs.getString("idLectorFK3") + ", "
								+ rs.getString("fechaDelPrestamo") + ", "
								+ rs.getString("fechaDeDevolucion"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		// Cerrar la conexión
		desconectar(con);
		add(choSacar);
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
			choSacar.select(0);
		} else if (objetoPulsado.equals(btnAceptar)) {
			// Sacar el id del elemento elegido
			int id = Integer.parseInt(choSacar.getSelectedItem().split("-")[0]);
			// Crear un Frame igual que el ALTA
			modificarSacar = new Frame("Modificar Sacar");
			modificarSacar.setLayout(new FlowLayout());
			Label lblIdSaca = new Label("idSaca:");
			Label lblEjemplarFK = new Label("idEjemplarFK2:");
			Label lblLectorFK = new Label("idLectorFK3:");
			Label lblfechaDelPrestamo = new Label("Fecha del préstamo:");
			Label lblfechaDeDevolucion = new Label("Fecha de devolución:");

			txtIdSaca = new TextField(20);

			txtidEjemplarFK2 = new TextField(20);

			txtidLectorFK3 = new TextField(20);

			txtfechaDelPrestamo = new TextField(20);

			txtfechaDeDevolucion = new TextField(20);

			btnAceptarCambios = new Button("Aceptar");
			btnCancelarCambios = new Button("Cancelar");

			modificarSacar.add(lblIdSaca);
			txtIdSaca.setEnabled(false);
			modificarSacar.add(txtIdSaca);
			modificarSacar.add(lblEjemplarFK);
			modificarSacar.add(txtidEjemplarFK2);

			modificarSacar.add(lblLectorFK);
			modificarSacar.add(txtidLectorFK3);

			modificarSacar.add(lblfechaDelPrestamo);
			modificarSacar.add(txtfechaDelPrestamo);

			modificarSacar.add(lblfechaDeDevolucion);
			modificarSacar.add(txtfechaDeDevolucion);

			btnAceptarCambios.addActionListener(this);
			btnCancelarCambios.addActionListener(this);
			modificarSacar.add(btnAceptarCambios);
			modificarSacar.add(btnCancelarCambios);
			// Pero relleno-->
			// Conectar a la base de datos
			Connection con = conectar();
			// Seleccionar los datos del elemento
			mostrarDatos(con, id, txtIdSaca, txtidEjemplarFK2, txtidLectorFK3,
					txtfechaDelPrestamo, txtfechaDeDevolucion);
			// seleccionado
			// Mostrarlos
			desconectar(con);
			modificarSacar.addWindowListener(this);
			modificarSacar.setResizable(false);
			modificarSacar.setSize(200, 600);
			modificarSacar.setLocationRelativeTo(null);
			modificarSacar.setVisible(true);
		} else if (objetoPulsado.equals(btnNo)) {
			seguro.setVisible(false);
		} else if (objetoPulsado.equals(btnCancelarCambios)) {
			modificarSacar.setVisible(false);
		} else if (objetoPulsado.equals(btnAceptarCambios)) {
			int id = Integer.parseInt(txtIdSaca.getText());
			String idEjemplarFK2 = txtidEjemplarFK2.getText();
			String  idLectorFK3 = txtidLectorFK3.getText();
			String fechaDelPrestamo = txtfechaDelPrestamo.getText();
			String fechaDeDevolucion = txtfechaDeDevolucion.getText();

			// Conectar a la base de datos
			Connection con = conectar();
			// Ejecutar el UPDATE
			String sql = "UPDATE saca SET idEjemplarFK2 = '" + idEjemplarFK2
					+ "', idLectorFK3 = '" + idLectorFK3
					+ "', fechaDelPrestamo = '" + fechaDelPrestamo + "', fechaDeDevolucion = '"
					+ fechaDeDevolucion + "' WHERE idSaca=" + id;
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
			modificarSacar.setVisible(false);
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
		} else if (modificarSacar.isActive()) {
			modificarSacar.setVisible(false);
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
	public int borrar(Connection con, int idSaca) {
		int respuesta = 0;
		String sql = "DELETE FROM saca WHERE idSaca = " + idSaca;
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
	public void mostrarDatos(Connection con, int idSaca, TextField id,
			TextField idEjemplarFK2, TextField idLectorFK3,
			TextField fechaDelPrestamo, TextField fechaDeDevolucion) {
		String sql = "SELECT * FROM saca WHERE idSaca = " + idSaca;
		try {
			id.setText(idSaca + "");
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			ResultSet rs = sta.executeQuery(sql);

			while (rs.next()) {
				String t = rs.getString("idEjemplarFK2");
				idEjemplarFK2.setText(t);
				String e = rs.getString("idLectorFK3");
				idLectorFK3.setText(e);
				String i = rs.getString("fechaDelPrestamo");
				fechaDelPrestamo.setText(i);
				String p = rs.getString("fechaDeDevolucion");
				fechaDeDevolucion.setText(p);

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
