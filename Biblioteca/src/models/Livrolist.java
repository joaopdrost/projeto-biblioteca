package models;

public class Livrolist {
	private int id;
	private String nome;
	private String autor;
	
	public Livrolist() {}
	
	public Livrolist(String nome, String autor) {
		super();
		this.nome = nome;
		this.autor = autor;
	}
	
	public Livrolist(int id, String nome, String autor) {
		this(nome, autor);
		setId(id);
	}
	
	public String getnome() {
		return nome;
	}
	
	public void setnome(String nome) {
		this.nome = nome;
	}
	
	public String getautor() {
		return autor;
	}
	
	public void setautor(String autor) {
		this.autor = autor;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		String s = id+" : " + nome+ " - "+autor;
		return s;
	}

}