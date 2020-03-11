package es.studium.Practica2EVAL;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class menuPrincipalUsuario extends Frame
		implements
			WindowListener,
			ActionListener {
	private static final long serialVersionUID = 1L;

	MenuBar barraMenu = new MenuBar();

	Menu mnuLibros = new Menu("Libros");
	Menu mnuEjemplares = new Menu("Ejemplares");
	Menu mnuLectores = new Menu("Lectores");
	Menu mnuSacar = new Menu("Sacar");

	MenuItem mniAltaLibro = new MenuItem("Alta");

	MenuItem mniAltaEjemplar = new MenuItem("Alta");

	MenuItem mniAltaLector = new MenuItem("Alta");

	MenuItem mniAltaSacar = new MenuItem("Alta");

	Label lblCopyright = new Label("© Biblioteca 2019/2020 - Usuario");
	Label lblNombre = new Label("Alberto Arrojo Carrasco, 1º DAW");

	menuPrincipalUsuario() {
		setTitle("Biblioteca de Alberto");
		setLayout(new FlowLayout());

		mnuLibros.add(mniAltaLibro);

		mnuEjemplares.add(mniAltaEjemplar);

		mnuLectores.add(mniAltaLector);

		mnuSacar.add(mniAltaSacar);

		barraMenu.add(mnuLibros);
		barraMenu.add(mnuEjemplares);
		barraMenu.add(mnuLectores);
		barraMenu.add(mnuSacar);

		addWindowListener(this);
		mniAltaLibro.addActionListener(this);

		mniAltaLector.addActionListener(this);

		mniAltaSacar.addActionListener(this);

		mniAltaEjemplar.addActionListener(this);

		setMenuBar(barraMenu);
		setSize(300, 150);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

		add(lblCopyright);
		add(lblNombre);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new menuPrincipalAdmin();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object objetoPulsado = e.getSource();
		if (objetoPulsado.equals(mniAltaLibro)) {
			new AltaLibro();

		} else if (objetoPulsado.equals(mniAltaLector)) {
			new AltaLector();

		} else if (objetoPulsado.equals(mniAltaSacar)) {
			new AltaSacar();

		} else if (objetoPulsado.equals(mniAltaEjemplar)) {
			new AltaEjemplar();
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
		System.exit(0);
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
}