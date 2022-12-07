package commons;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import armazem.LivroRepositorySQLite;
import models.Livrolist;

public class MainL extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	protected DefaultListModel listModel;
	protected JList list;
	protected JTextField searchField;
	protected JButton btnInsert, btnEdit, btnRemove, btnClose, btnSearch, btnClear;
	private LivroRepositorySQLite repository;
	
	public MainL() {
		repository = new LivroRepositorySQLite();
		setSize(500, 200);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		JScrollPane scroll = createList();
		JPanel buttons = createPanelButtons();
		JPanel sidePanel = new JPanel(new FlowLayout());
		sidePanel.add(buttons);
		add("East", sidePanel);
		add("North", createSearchPanel());
		add(scroll);
	}
	
	private JPanel createSearchPanel() { //botões da área de pesquisa
		JPanel panel = new JPanel();
		searchField = new JTextField(20);
		searchField.addActionListener(this);
		btnSearch = new JButton("🔎");
		btnClear = new JButton("❌");
		btnSearch.addActionListener(this);
		btnClear.addActionListener(this);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.add(new JLabel("Pesquisar: "));
		panel.add(searchField);
		panel.add(btnSearch);
		panel.add(btnClear);
		return panel;
	}
	
	private JPanel createPanelButtons() { //adiciona os botões na janela
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 1, 5, 5));
		btnInsert = new JButton("Inserir o livro e autor");
		btnInsert.addActionListener(this);
		btnRemove = new JButton("deletar os livros");
		btnRemove.addActionListener(this);
		btnEdit = new JButton("Editar o nome dos livros e autor");
		btnEdit.addActionListener(this);
		btnClose = new JButton("Fechar");
		btnClose.addActionListener(this);
		panel.add(btnInsert);
		panel.add(btnEdit);
		panel.add(btnRemove);
		panel.add(btnClose);
		return panel;
	}
	
	private JScrollPane createList() { //bloco de pesquisa pra procurar sobre os livros
		listModel = new DefaultListModel();
		populate();
		list = new JList(listModel);
		return new JScrollPane(list);
	}
	
	private void populate(Livrolist[] livrolist) {
		listModel.removeAllElements();
		for (Livrolist l : livrolist) {
			listModel.addElement(l);}
		}
		
	private void populate() {
		Livrolist[] livrolist = repository.findAll();
		populate(livrolist);
		}
	
	private void editItem() { //funcionalidade do botão de EDITAR
		int index = list.getSelectedIndex();
		if (index < 0) {
			JOptionPane.showMessageDialog(this, "Selecione um item para editar");
			return;
		}
		Livrolist livro = (Livrolist) list.getSelectedValue();
		LivrosDialog dialog = new LivrosDialog(this, livro);
		if (dialog.getLivrolist() == null)
			return;
		repository.update(dialog.getLivrolist());
		dialog.dispose();
		this.populate();
	}
	
	private void removeItem() { //funcionalidade do botão de REMOVER
		int index = list.getSelectedIndex();
		if (index < 0) {
			JOptionPane.showMessageDialog(this, "Selecione um livro para remover");
			return;
		}
		Livrolist l = (Livrolist) list.getSelectedValue();
		int response = JOptionPane.showConfirmDialog(this, "Tem certeza que quer apagar esse livro?",
				"Remover livro", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (response == JOptionPane.YES_OPTION) {
			repository.remove(l.getId());
			listModel.removeElementAt(index);
		}
	}
	
	private void insertItem() { //funcionalidade do botão de INSERIR
		LivrosDialog dialog = new LivrosDialog(this, null);
		Livrolist l = dialog.getLivrolist();
		dialog.dispose();
		if (l == null) {
			return;
		}
		repository.insert(l);
		populate();
	}
	
	private void searchItem() { //funcionalidade do botão de PESQUISA
		String search = searchField.getText().trim();
		if(search=="") { 
			populate();
			return;
		}
		Livrolist[] livrolist = repository.findBy("*", search);
		populate(livrolist);
	}
	
	public void actionPerformed(ActionEvent e) { //adição de todos os botões na janela principal
		if (e.getSource() == btnInsert) {
			insertItem();
			return;
		}
		if (e.getSource() == btnRemove) {
			removeItem();
			return;
		}
		if (e.getSource() == btnEdit) {
			editItem();
			return;
		}
		if (e.getSource() == btnSearch || e.getSource() == searchField) {
			searchItem();
			return;
		}
		if(e.getSource() == btnClear) {
			searchField.setText("");
			populate();
			return;
		}
		System.exit(0);
	}
	
	public static void main(String[] args) {
		JFrame window = new MainL();
		window.setVisible(true);
	}
}