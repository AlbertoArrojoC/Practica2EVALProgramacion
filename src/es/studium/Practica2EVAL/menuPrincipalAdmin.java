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

public class menuPrincipalAdmin extends Frame
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
	MenuItem mniBajaLibro = new MenuItem("Baja");
	MenuItem mniConsultaLibro = new MenuItem("Consulta");
	MenuItem mniModificaLibro = new MenuItem("Modificación");

	MenuItem mniAltaEjemplar = new MenuItem("Alta");
	MenuItem mniConsultaEjemplar = new MenuItem("Consulta");
	MenuItem mniModificaEjemplar = new MenuItem("Modificación");

	MenuItem mniAltaLector = new MenuItem("Alta");
	MenuItem mniBajaLector = new MenuItem("Baja");
	MenuItem mniConsultaLector = new MenuItem("Consulta");
	MenuItem mniModificaLector = new MenuItem("Modificación");

	MenuItem mniAltaSacar = new MenuItem("Alta");
	MenuItem mniConsultaSacar = new MenuItem("Consulta");
	MenuItem mniModificaSacar = new MenuItem("Modificación");

	Label lblCopyright = new Label("© Biblioteca 2019/2020 - Administrador");
	Label lblNombre = new Label("Alberto Arrojo Carrasco, 1º DAW");

	menuPrincipalAdmin() {
		setTitle("Biblioteca de Alberto");
		setLayout(new FlowLayout());

		mnuLibros.add(mniAltaLibro);
		mnuLibros.add(mniBajaLibro);
		mnuLibros.add(mniConsultaLibro);
		mnuLibros.add(mniModificaLibro);

		mnuEjemplares.add(mniAltaEjemplar);
		mnuEjemplares.add(mniConsultaEjemplar);
		mnuEjemplares.add(mniModificaEjemplar);

		mnuLectores.add(mniAltaLector);
		mnuLectores.add(mniBajaLector);
		mnuLectores.add(mniConsultaLector);
		mnuLectores.add(mniModificaLector);

		mnuSacar.add(mniAltaSacar);
		mnuSacar.add(mniConsultaSacar);
		mnuSacar.add(mniModificaSacar);

		barraMenu.add(mnuLibros);
		barraMenu.add(mnuEjemplares);
		barraMenu.add(mnuLectores);
		barraMenu.add(mnuSacar);

		addWindowListener(this);
		mniAltaLibro.addActionListener(this);
		mniBajaLibro.addActionListener(this);
		mniConsultaLibro.addActionListener(this);
		mniModificaLibro.addActionListener(this);
		mniAltaLector.addActionListener(this);
		mniBajaLector.addActionListener(this);
		mniConsultaLector.addActionListener(this);
		mniModificaLector.addActionListener(this);
		mniAltaSacar.addActionListener(this);
		mniConsultaSacar.addActionListener(this);
		mniModificaSacar.addActionListener(this);
		mniAltaEjemplar.addActionListener(this);
		mniConsultaEjemplar.addActionListener(this);
		mniModificaEjemplar.addActionListener(this);

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
		} else if (objetoPulsado.equals(mniBajaLibro)) {
			new BajaLibro();
		} else if (objetoPulsado.equals(mniConsultaLibro)) {
			new ConsultaLibro();
		} else if (objetoPulsado.equals(mniModificaLibro)) {
			new ModificaLibro();
		} else if (objetoPulsado.equals(mniAltaLector)) {
			new AltaLector();
		} else if (objetoPulsado.equals(mniBajaLector)) {
			new BajaLector();
		} else if (objetoPulsado.equals(mniConsultaLector)) {
			new ConsultaLector();
		} else if (objetoPulsado.equals(mniModificaLector)) {
			new ModificaLector();
		} else if (objetoPulsado.equals(mniAltaSacar)) {
			new AltaSacar();
		} else if (objetoPulsado.equals(mniConsultaSacar)) {
			new ConsultaSacar();
		} else if (objetoPulsado.equals(mniModificaSacar)) {
			new ModificaSacar();
		} else if (objetoPulsado.equals(mniAltaEjemplar)) {
			new AltaEjemplar();
		} else if (objetoPulsado.equals(mniConsultaEjemplar)) {
			new ConsultaEjemplar();
		} else if (objetoPulsado.equals(mniModificaEjemplar)) {
			new ModificaEjemplar();
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