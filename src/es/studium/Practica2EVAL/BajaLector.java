package es.studium.Practica2EVAL;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BajaLector extends Frame implements WindowListener, ActionListener {
	private static final long serialVersionUID = 1L;
	Label lblLectorBorrar = new Label("Elige el lector a borrar:");
	Choice choLector = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Dialog seguro;
	Button btnSi;
	Button btnNo;

	BajaLector() {
		setTitle("Baja de lector");
		setLayout(new FlowLayout());
		add(lblLectorBorrar);
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
				choLector.add(
						rs.getInt("idLector") + "-" + rs.getString("nombreLector")
								+ ", " + rs.getString("ApellidosLector") + ", "
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
		setSize(350, 250);
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
			seguro = new Dialog(this, "¿Seguro?", true);
			btnSi = new Button("Sí");
			btnNo = new Button("No");
			Label lblEtiqueta = new Label("¿Está seguro de eliminar?");
			seguro.setLayout(new FlowLayout());
			seguro.setSize(200, 100);
			btnSi.addActionListener(this);
			btnNo.addActionListener(this);
			seguro.add(lblEtiqueta);
			seguro.add(btnSi);
			seguro.add(btnNo);
			seguro.addWindowListener(this);
			seguro.setResizable(false);
			seguro.setLocationRelativeTo(null);
			seguro.setVisible(true);
		} else if (objetoPulsado.equals(btnNo)) {
			seguro.setVisible(false);
		} else if (objetoPulsado.equals(btnSi)) {
			// Conectar a BD
			Connection con = conectar();
			// Borrar
			String[] Edificio = choLector.getSelectedItem().split("-");
			int respuesta = borrar(con, Integer.parseInt(Edificio[0]));
			// Mostramos resultado
			if (respuesta == 0) {
				System.out.println("Borrado de lector correcto");
			} else {
				System.out.println("Error en borrado de lector");
			}
			// Actualizar el Choice
			choLector.removeAll();
			choLector.add("Seleccionar uno...");
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
			// Desconectar
			desconectar(con);
			seguro.setVisible(false);
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
		String sql = "DELETE FROM lector WHERE idLector = " + idLector;
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
	public void desconectar(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
		}
	}
}
