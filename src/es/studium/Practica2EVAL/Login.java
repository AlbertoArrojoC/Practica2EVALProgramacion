package es.studium.Practica2EVAL;

import java.awt.Button;

import java.awt.Dialog;

import java.awt.FlowLayout;

import java.awt.Frame;

import java.awt.Label;

import java.awt.TextField;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import java.security.MessageDigest;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

public class Login extends WindowAdapter implements ActionListener

{

	Frame login = new Frame("Login");

	Frame menuPrincipal = new Frame("Menú Principal");

	Dialog errorLogin = new Dialog(login, "ERROR", true);

	Label lblUsuario = new Label("Usuario:");

	Label lblClave = new Label("Clave:");

	TextField txtUsuario = new TextField(20);

	TextField txtClave = new TextField(20);

	Button btnAceptar = new Button("Aceptar");

	Button btnLimpiar = new Button("Limpiar");

	// Conexión a la base de datos

	String driver = "com.mysql.jdbc.Driver";

	String url = "jdbc:mysql://localhost:3306/bibliotecapr?useSSL=false";

	String usuario = "root";

	String clave = "Studium2019;";

	String sentencia = "";

	Connection connection = null;

	Statement statement = null;

	ResultSet rs = null;

	Login()

	{

		login.setLayout(new FlowLayout());

		login.setSize(270, 150);

		login.setResizable(false);

		login.setLocationRelativeTo(null);

		login.addWindowListener(this);

		btnAceptar.addActionListener(this);

		btnLimpiar.addActionListener(this);

		login.add(lblUsuario);

		login.add(txtUsuario);

		login.add(lblClave);

		txtClave.setEchoChar('*');

		login.add(txtClave);

		login.add(btnAceptar);

		login.add(btnLimpiar);

		login.setVisible(true);

	}

	public static void main(String[] args)

	{

		new Login();

	}

	public void windowClosing(WindowEvent arg0)

	{

		if (errorLogin.isActive())

		{

			errorLogin.setVisible(false);

		}

		else

		{

			System.exit(0);

		}

	}

	public void actionPerformed(ActionEvent evento)

	{

		if (evento.getSource().equals(btnLimpiar))

		{

			// Tareas del botón Limpiar

			txtUsuario.selectAll();

			txtUsuario.setText("");

			txtClave.selectAll();

			txtClave.setText("");

			txtUsuario.requestFocus();

		}

		else if (evento.getSource().equals(btnAceptar))

		{

			// Tareas del botón Aceptar

			String cadenaEncriptada = getSHA256(txtClave.getText());

			sentencia = "SELECT * FROM usuarios WHERE ";

			sentencia += "nombreUsuario = '" + txtUsuario.getText() + "'";

			sentencia += " AND claveUsuario = '" + cadenaEncriptada + "'";

			System.out.println(sentencia);

			try

			{

				Class.forName(driver);

				connection = DriverManager.getConnection(url, usuario, clave);

				statement = connection.createStatement();

				rs = statement.executeQuery(sentencia);

				if (rs.next())

				{
					String tipoUsuario = rs.getString("tipoUsuario");
					System.out.println("Tipo de usuario = " + tipoUsuario);
					if (tipoUsuario.equals("Administrador")) {
						new menuPrincipalAdmin();

					} else {
						new menuPrincipalUsuario();
					}

				}

				else

				{

					errorLogin.setLayout(new FlowLayout());

					errorLogin.setSize(160, 90);

					errorLogin.setResizable(false);

					errorLogin.addWindowListener(this);

					errorLogin.add(new Label("Credenciales Incorrectas"));

					Button btnVolver = new Button("Volver");

					btnVolver.addActionListener(this);

					errorLogin.add(btnVolver);

					errorLogin.setLocationRelativeTo(null);

					errorLogin.setVisible(true);

				}

			}

			catch (ClassNotFoundException cnfe)

			{

				System.out.println("Error 1-" + cnfe.getMessage());

			}

			catch (SQLException sqle)

			{

				System.out.println("Error 2-" + sqle.getMessage());

			}

			finally

			{

				try

				{

					if (connection != null)

					{

						connection.close();

					}

				}

				catch (SQLException e)

				{

					System.out.println("Error 3-" + e.getMessage());

				}

			}

		}

		else

		{

			// Tareas del Volver

			errorLogin.setVisible(false);

		}

	}

	public static String getSHA256(String data)

	{

		StringBuffer sb = new StringBuffer();

		try

		{

			MessageDigest md = MessageDigest.getInstance("SHA-256");

			md.update(data.getBytes());

			byte byteData[] = md.digest();

			for (int i = 0; i < byteData.length; i++)

			{

				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100,

						16).substring(1));

			}

		}

		catch (Exception e)

		{

			e.printStackTrace();

		}

		return sb.toString();

	}

}