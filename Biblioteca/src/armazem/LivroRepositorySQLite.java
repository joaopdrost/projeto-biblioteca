package armazem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import conectbdd.Livroslist;
import models.Livrolist;

public class LivroRepositorySQLite implements LivroRepository {

	@Override
	public void insert(Livrolist livrolist) {
		Connection con;
		PreparedStatement prepStmt;
		String sql = "INSERT INTO "
				+ "Livroslist ( nome, autor) "
				+ "VALUES (?, ?)";
		try {
			con = DriverManager.getConnection(Livroslist.URL); //vai mostar o que tem no banco de dados na janela principal
			prepStmt = con.prepareStatement(sql);
			
			prepStmt.setString(1, livrolist.getnome());
			prepStmt.setString(2, livrolist.getautor());
			prepStmt.executeUpdate();
			prepStmt.close();
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	@Override 
	public void remove(int id) { //vai remover o arquivo selecionado do banco de dados
		Connection con;
		PreparedStatement prepStmt;
		try {
			con = DriverManager.getConnection(Livroslist.URL);
			prepStmt = con.prepareStatement("DELETE FROM Livroslist WHERE id=?");
			prepStmt.setInt(1, id);
			prepStmt.executeUpdate();
			prepStmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	// serve para fazer a busca
	
	public Livrolist[] findBy(String fieldName, String value) {
		Connection con;
		PreparedStatement stmt;
		String sql = "SELECT * FROM Livroslist WHERE ";
		if(fieldName.equals("*")) {
			sql += "id like? OR nome like ? OR autor like? ";
		} else  {
			sql +=fieldName+" like ?";
		}
		
		try {
			
			con = DriverManager.getConnection(Livroslist.URL); //vai fazer a conexão com o banco de dados
			stmt = con.prepareStatement(sql);
			
			if(!fieldName.equals("*")) {
				stmt.setString(1, "%"+value+"%");
			}else {
				for (int i = 1; i <= 3; i++) {
					stmt.setString(i, "%"+value+"%");
				}
			}
			
			ResultSet rs = stmt.executeQuery();
			int count = 0;
			while (rs.next()) {
				count++;
			}

			Livrolist[] Livroslist = new Livrolist[count];
			rs = stmt.executeQuery(); 
			int index = 0;
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String autor = rs.getString("autor");

				Livrolist l = new Livrolist(id, nome, autor);
				Livroslist[index] = l;
				index++;
			}
			rs.close();
			stmt.close();
			con.close();

			return Livroslist;
			
		} catch (Exception e) {
			e.printStackTrace();

			return new Livrolist[0];
		}
	}
	
	@Override
	
	public Livrolist[] findAll() { //vai fazer a busca
		Connection con;
		Statement stmt;
		String sql = "SELECT id, nome, autor FROM Livroslist";

		try {
			con = DriverManager.getConnection(Livroslist.URL);
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			int count = 0;
			while (rs.next()) {
				count++;
			}
			
			Livrolist[] Livroslist = new Livrolist[count];
			rs = stmt.executeQuery(sql); 
			int index = 0;
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String autor = rs.getString("autor");
				
				

				Livrolist l = new Livrolist(id, nome, autor);
				Livroslist[index] = l;
				index++;
			}
			rs.close();
			stmt.close();
			con.close();

			return Livroslist;

		} catch (Exception e) {
			e.printStackTrace();

			return new Livrolist[0];
		}
	}
	
	@Override
	
	public void update(Livrolist livrolist) { //vai poder modificar os livros já existentes
		Connection con;
		PreparedStatement prepStmt;
		String sql = "UPDATE Livroslist SET nome=?, autor=? "
				+ "WHERE id=?";
		try {
			con = DriverManager.getConnection(Livroslist.URL);
			prepStmt = con.prepareStatement(sql);
			
			prepStmt.setString(1, livrolist.getnome());
			prepStmt.setString(2, livrolist.getautor());
			prepStmt.setInt(3, livrolist.getId());
			
			prepStmt.executeUpdate();
			prepStmt.close();
			con.close();
		}catch(SQLException ex) {
			ex.printStackTrace();
		}           

	}
}