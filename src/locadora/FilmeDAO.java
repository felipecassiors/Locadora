package locadora;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmeDAO {
    
    private static final String URL = "jdbc:mysql://localhost:3306/locadora?useSSL=false&serverTimezone=Brazil/East";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection conectaBanco() throws SQLException
    {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());	
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public boolean inserir(Filme filme)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean sucesso = true;
        try {
            connection = conectaBanco();
            preparedStatement = connection.prepareStatement("INSERT INTO filme(titulo,genero,duracao,preco) VALUES(?,?,?,?)");
            preparedStatement.setString(1, filme.getTitulo());
            preparedStatement.setString(2, filme.getGenero());
            preparedStatement.setInt(3, filme.getDuracao());
            preparedStatement.setDouble(4, filme.getPreco());
            preparedStatement.execute();
            System.out.println("Filme inserido com sucesso!");
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir filme: "+ex.getMessage());
            sucesso = false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar as conexões: "+ex.getMessage());
            }
        }
        return sucesso;
    }
    
    public boolean atualizar(Filme filme)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean sucesso = true;
        try {
            connection = conectaBanco();
            preparedStatement = connection.prepareStatement("UPDATE filme SET titulo = ?, genero = ?, duracao = ?, preco = ? WHERE id = ?");
            preparedStatement.setString(1, filme.getTitulo());
            preparedStatement.setString(2, filme.getGenero());
            preparedStatement.setInt(3, filme.getDuracao());
            preparedStatement.setDouble(4, filme.getPreco());
            preparedStatement.setInt(5, filme.getId());
            preparedStatement.execute();
            System.out.println("Filme atualizado com sucesso!");
        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar filme: "+ex.getMessage());
            sucesso = false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar as conexões: "+ex.getMessage());
            }
        }
        return sucesso;
    }
    
    public boolean remover(int id)
    {
         Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean sucesso = true;
        try {
            connection = conectaBanco();
            preparedStatement = connection.prepareStatement("DELETE FROM filme WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            System.out.println("Filme removido com sucesso!");
        } catch (SQLException ex) {
            System.err.println("Erro ao remover filme: "+ex.getMessage());
            sucesso = false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar as conexões: "+ex.getMessage());
            }
        }
        return sucesso;
    }
    
    public Filme buscar(int id)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Filme filme = null;
        try {
            connection = conectaBanco();
            preparedStatement = connection.prepareStatement("SELECT * FROM filme WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet != null && resultSet.first()) {
                filme = new Filme();
                filme.setId(resultSet.getInt(1));
                filme.setTitulo(resultSet.getString(2));
                filme.setGenero(resultSet.getString(3));
                filme.setDuracao(resultSet.getInt(4));
                filme.setPreco(resultSet.getDouble(5));
            } 
            System.out.println("Filme buscado com sucesso!");
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar filme: "+ex.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar as conexões: "+ex.getMessage());
            }
        }
        return filme;
    }
    
    public List<Filme> listar()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Filme> filmes = new ArrayList<>();
        try {
            connection = conectaBanco();
            preparedStatement = connection.prepareStatement("SELECT * FROM filme");
            resultSet = preparedStatement.executeQuery();
            while(resultSet != null && resultSet.next()) {
                Filme filme = new Filme();
                filme.setId(resultSet.getInt(1));
                filme.setTitulo(resultSet.getString(2));
                filme.setGenero(resultSet.getString(3));
                filme.setDuracao(resultSet.getInt(4));
                filme.setPreco(resultSet.getDouble(5));
                filmes.add(filme);
            } 
            System.out.println("Filmes listados com sucesso!");
        } catch (SQLException ex) {
            System.err.println("Erro ao listar filmes: "+ex.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar as conexões: "+ex.getMessage());
            }
        }
        return filmes;
    }
    
    public List<Filme> buscarPorTitulo(String titulo)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Filme> filmes = new ArrayList<>();
        try {
            connection = conectaBanco();
            preparedStatement = connection.prepareStatement("SELECT * FROM filme WHERE titulo LIKE ?");
            preparedStatement.setString(1, "%" + titulo + "%");
            resultSet = preparedStatement.executeQuery();
            while(resultSet != null && resultSet.next()) {
                Filme filme = new Filme();
                filme.setId(resultSet.getInt(1));
                filme.setTitulo(resultSet.getString(2));
                filme.setGenero(resultSet.getString(3));
                filme.setDuracao(resultSet.getInt(4));
                filme.setPreco(resultSet.getDouble(5));
                filmes.add(filme);
            } 
            System.out.println("Filmes buscados por título com sucesso!");
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar filmes por título: "+ex.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar as conexões: "+ex.getMessage());
            }
        }
        return filmes;
    }
}
