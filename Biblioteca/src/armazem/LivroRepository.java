package armazem;

import models.Livrolist;

public interface LivroRepository { //o que vai lincar todos os comandos
		
		public void insert(Livrolist livrolist);
		
		public void remove(int id);
		
		public Livrolist[] findBy(String fielName, String value );
		
		public Livrolist[] findAll();
		
		public void update(Livrolist livrolist);
	}


