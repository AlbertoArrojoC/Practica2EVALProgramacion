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

public class AltaSacar extends Frame implements WindowListener, ActionListener {
	private static final long serialVersionUID = 1L;

	Label lblidEjemplarFK2 = new Label("ID del Ejemplar");
	Label lblidLectorFK3 = new Label("ID del Lector");

	TextField txtidEjemplarFK2 = new TextField(20);
	TextField txtidLectorFK3 = new TextField(20);

	Label lblfechaPrestamo = new Label("Fecha del préstamo:");
	Label lblfechaDevolucion = new Label("Fecha de devolución:");

	TextField txtfechaPrestamo = new TextField(20);
	TextField txtfechaDevolucion = new TextField(20);

	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	AltaSacar() {
		setTitle("Alta de Sacar");
		setLayout(new FlowLayout());

		add(lblidEjemplarFK2);
		add(txtidEjemplarFK2);

		add(lblidLectorFK3);
		add(txtidLectorFK3);

		add(lblfechaPrestamo);
		add(txtfechaPrestamo);

		add(lblfechaDevolucion);
		add(txtfechaDevolucion);

		add(btnAceptar);
		add(btnLimpiar);

		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setSize(320, 250);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object objetoPulsado = e.getSource();
		if (objetoPulsado.equals(btnLimpiar)) {
			txtidEjemplarFK2.selectAll();
			txtidEjemplarFK2.setText("");

			txtidLectorFK3.selectAll();
			txtidLectorFK3.setText("");
			txtidEjemplarFK2.requestFocus();

		} else if (objetoPulsado.equals(btnAceptar)) {
			// Conectar BD
			Connection con = conectar();
			// Hacer el INSERT
			int respuesta = insertar(con, "saca", txtidEjemplarFK2.getText(),
					txtidLectorFK3.getText(), txtfechaPrestamo.getText(),
					txtfechaDevolucion.getText());
			// Mostramos resultado
			if (respuesta == 0) {
				System.out.println("ALTA de sacar correcta");
			} else {
				System.out.println("Error en ALTA de sacar");
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

	public int insertar(Connection con, String tabla, String idEjemplarFK2,
			String idLectorFK3, String fechaDelPrestamo,
			String fechaDeDevolucion) {
		int respuesta = 0;
		try {
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			String cadenaSQL = "INSERT INTO " + tabla + " VALUES (null,"
					+ idEjemplarFK2 + "," + idLectorFK3 + ",'"
					+ fechaDelPrestamo + "','" + fechaDeDevolucion + "')";

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
