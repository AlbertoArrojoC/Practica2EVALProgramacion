package es.studium.Practica2EVAL;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class menuPrincipal extends Frame
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

	MenuItem mniAltaEjemplares = new MenuItem("Alta");
	MenuItem mniConsultaEjemplares = new MenuItem("Consulta");
	MenuItem mniModificaEjemplares = new MenuItem("Modificación");

	MenuItem mniAltaLectores = new MenuItem("Alta");
	MenuItem mniBajaLectores = new MenuItem("Baja");
	MenuItem mniConsultaLectores = new MenuItem("Consulta");
	MenuItem mniModificaLectores = new MenuItem("Modificación");

	MenuItem mniAltaSacar = new MenuItem("Alta");
	MenuItem mniConsultaSacar = new MenuItem("Consulta");
	MenuItem mniModificaSacar = new MenuItem("Modificación");

	menuPrincipal() {
		setTitle("Biblioteca de Alberto");
		setLayout(new FlowLayout());

		mnuLibros.add(mniAltaLibro);
		mnuLibros.add(mniBajaLibro);
		mnuLibros.add(mniConsultaLibro);
		mnuLibros.add(mniModificaLibro);

		mnuEjemplares.add(mniAltaEjemplares);
		mnuEjemplares.add(mniConsultaEjemplares);
		mnuEjemplares.add(mniModificaEjemplares);

		mnuLectores.add(mniAltaLectores);
		mnuLectores.add(mniBajaLectores);
		mnuLectores.add(mniConsultaLectores);
		mnuLectores.add(mniModificaLectores);

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


		setMenuBar(barraMenu);
		setSize(400, 150);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new menuPrincipal();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object objetoPulsado = e.getSource();
		if (objetoPulsado.equals(mniAltaLibro))
		{
			new AltaLibro();
		} 
		else if(objetoPulsado.equals(mniBajaLibro)) {
			new BajaLibro();
		}
		else if(objetoPulsado.equals(mniConsultaLibro)) {
			new ConsultaLibro();
		}
		else if(objetoPulsado.equals(mniModificaLibro)) {
			new ModificaLibro();
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