package commons;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFrame;

import models.Livrolist;

public class LivrosDialog extends JDialog implements ActionListener {
	
	private Livrolist livrolist;
	
	private JButton btnAction, btnCancel;
	
	private JTextField tfNome, tfAutor;
	
	public LivrosDialog(JFrame owner, Livrolist livrolist) {
		super(owner, true);
		setLocationRelativeTo(owner);
		
		tfNome = new JTextField(15);
		tfAutor = new JTextField(15);
		
		if (livrolist != null) { //editar livro já existente da lista
			this.livrolist = livrolist;
			btnAction = new JButton("Editar");
			setTitle("Editar Livro");
			
			tfNome.setText(livrolist.getnome());
			tfAutor.setText(livrolist.getautor());
		} else { //adicionar livros a lista
			this.livrolist = new Livrolist();
			btnAction = new JButton("Adicionar");
			setTitle("Adicionar Livro");
		}
		setSize(300, 150); //tamanho da janela de adicionar livro
		setLayout(new BorderLayout());
		
		JPanel panelFields = new JPanel(new FlowLayout(FlowLayout.RIGHT)); //painel de inserir livros
		panelFields.add(new JLabel("Nome do livro"));
		panelFields.add(tfNome);
		panelFields.add(new JLabel("Autor do livro"));
		panelFields.add(tfAutor);
		
		JPanel panelButtons = new JPanel(new FlowLayout()); //funcionalidade do botão CANCELAR
		btnCancel = new JButton("Cancelar");
		btnCancel.addActionListener(this);
		btnAction.addActionListener(this);
		panelButtons.add(btnAction);
		panelButtons.add(btnCancel);
		
		add(panelFields);
		add("South", panelButtons);

		setVisible(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	
	public Livrolist getLivrolist() {
		return livrolist;
	}
	
	
	public void actionPerformed(ActionEvent e) { // faz a conexão com o Nome e o Autor do pacote LIVROLIST
		String nome = tfNome.getText().trim();
		String autor = tfAutor.getText().trim();
		
		
		if (e.getSource() == btnCancel) {
			livrolist = null;
		} else {
			if (nome.equals("") || autor.equals("")) {
				JOptionPane.showMessageDialog(this, "Prencha o título e autor do livro"); //se não tiver nada no bloco de nome e autor aparece essa notificação
				return;
			}
			
			livrolist.setnome(nome);
			livrolist.setautor(autor);
		}
		setVisible(false);
	}
	
}	