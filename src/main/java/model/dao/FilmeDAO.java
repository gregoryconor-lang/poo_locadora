/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.bean.Filme;
import model.bean.Genero;

/**
 *
 * @author gregory
 */
public class FilmeDAO {
    public void create(Filme f) {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        
        try {
            String query = "INSERT INTO filme(titulo, sinopse, duracao, id_genero) VALUES(?,?,?,?)";
            stmt = con.prepareStatement(query);
            stmt.setString(1, f.getTitulo());
            stmt.setString(2, f.getSinopse());
            stmt.setInt(3, f.getDuracao());
            stmt.setInt(4, f.getGenero().getId()); // Associação com a classe Genero
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Filme cadastrado com sucesso!");
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Falha ao cadastrar Filme. Erro: " + ex.getMessage());
        }
        finally {
            Conexao.fecharConexao(con, stmt);
        }
    }
    
    public ArrayList<Filme> read() {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Filme> listaFilmes = new ArrayList();
        
        try {
            String query = "SELECT * FROM filme";
            stmt = con.prepareStatement(query);
            
            rs = stmt.executeQuery();
            
            while(rs.next()) {
                Filme f = new Filme();
                f.setId(rs.getInt("id_filme"));
                f.setTitulo(rs.getString("titulo"));
                f.setDuracao(rs.getInt("duracao"));
                f.setSinopse(rs.getString("sinopse"));
                
                // Definir o atributo da associação (fk)
                GeneroDAO daoGenero = new GeneroDAO();
                Genero g = daoGenero.read(rs.getInt("id_genero"));
                f.setGenero(g);                
                
                listaFilmes.add(f);
            }
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Falha ao consultar Filmes. Erro: " + ex.getMessage());
        }
        finally {
            Conexao.fecharConexao(con, stmt, rs);
        }
        
        return listaFilmes;
    }
    
    public Filme read(int id) {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;        
        
        try {
            String query = "SELECT * FROM filme WHERE id_filme = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();
            
            if(rs.next()) {
                Filme f = new Filme();
                f.setId(rs.getInt("id_filme"));
                f.setTitulo(rs.getString("titulo"));
                f.setDuracao(rs.getInt("duracao"));
                f.setSinopse(rs.getString("sinopse"));
                
                return f;
            }
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Falha ao buscar Filme. Erro: " + ex.getMessage());
        }
        finally {
            Conexao.fecharConexao(con, stmt, rs);
        }
        
        return null;
    }
    
    public void update(Filme f) {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        
        try {
            String query = "UPDATE filme "
                    + "SET titulo = ?, sinopse = ?, duracao = ? "
                    + "WHERE id_filme = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, f.getTitulo());
            stmt.setString(2, f.getSinopse());
            stmt.setInt(3, f.getDuracao());
            stmt.setInt(4, f.getId());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Filme atualizado com sucesso!");
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Falha ao editar Filme. Erro: " + ex.getMessage());
        }
        finally {
            Conexao.fecharConexao(con, stmt);
        }
    }
    
    public void destroy(Filme f) {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        
        try {
            String query = "DELETE FROM filme WHERE id_filme = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, f.getId());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Filme excluído com sucesso!");
        }
        catch(SQLException ex) {
            JOptionPane.showMessageDialog(null, "Falha ao excluir Filme. Erro: " + ex.getMessage());
        }
        finally {
            Conexao.fecharConexao(con, stmt);
        }
    }
}
