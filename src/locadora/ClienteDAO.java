package locadora;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
    public boolean inserir(Cliente cliente)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean sucesso = true;
        try {
            connection = ConexaoBanco.conectaBanco();
            preparedStatement = connection.prepareStatement("INSERT INTO cliente(nome,telefone,email,sexo,idade) VALUES(?,?,?,?,?)");
            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setString(2, cliente.getTelefone());
            preparedStatement.setString(3, cliente.getEmail());
            preparedStatement.setString(4, cliente.getSexo());
            preparedStatement.setInt(5, cliente.getIdade());
            preparedStatement.execute();
            System.out.println("Cliente inserido com sucesso!");
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir cliente: "+ex.getMessage());
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
    
    public boolean atualizar(Cliente cliente)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean sucesso = true;
        try {
            connection = ConexaoBanco.conectaBanco();
            preparedStatement = connection.prepareStatement("UPDATE cliente SET nome = ?, telefone = ?, email = ?, sexo = ?, idade = ? WHERE id = ?");
            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setString(2, cliente.getTelefone());
            preparedStatement.setString(3, cliente.getEmail());
            preparedStatement.setString(4, cliente.getSexo());
            preparedStatement.setInt(5, cliente.getIdade());
            preparedStatement.setInt(6, cliente.getId());
            preparedStatement.execute();
            System.out.println("Cliente atualizado com sucesso!");
        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar cliente: "+ex.getMessage());
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
            connection = ConexaoBanco.conectaBanco();
            preparedStatement = connection.prepareStatement("DELETE FROM cliente WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            System.out.println("Cliente removido com sucesso!");
        } catch (SQLException ex) {
            System.err.println("Erro ao remover cliente: "+ex.getMessage());
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
    
    public Cliente buscar(int id)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Cliente cliente =  null;
        try {
            connection = ConexaoBanco.conectaBanco();
            preparedStatement = connection.prepareStatement("SELECT * FROM cliente WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet != null && resultSet.first()) {
                cliente = new Cliente();
                cliente.setId(resultSet.getInt(1));
                cliente.setNome(resultSet.getString(2));
                cliente.setTelefone(resultSet.getString(3));
                cliente.setEmail(resultSet.getString(4));
                cliente.setSexo(resultSet.getString(5));
                cliente.setIdade(resultSet.getInt(6));
            } 
            System.out.println("Cliente buscado com sucesso!");
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar cliente: "+ex.getMessage());
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
        return cliente;
    }
    
    public List<Cliente> listar()
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Cliente> clientes = new ArrayList<>();
        try {
            connection = ConexaoBanco.conectaBanco();
            preparedStatement = connection.prepareStatement("SELECT * FROM cliente");
            resultSet = preparedStatement.executeQuery();
            while(resultSet != null && resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt(1));
                cliente.setNome(resultSet.getString(2));
                cliente.setTelefone(resultSet.getString(3));
                cliente.setEmail(resultSet.getString(4));
                cliente.setSexo(resultSet.getString(5));
                cliente.setIdade(resultSet.getInt(6));
                clientes.add(cliente);
            } 
            System.out.println("Clientes listados com sucesso!");
        } catch (SQLException ex) {
            System.err.println("Erro ao listar clientes: "+ex.getMessage());
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
        return clientes;
    }
    
    public List<Cliente> buscarPorNome(String nome)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Cliente> clientes = new ArrayList<>();
        try {
            connection = ConexaoBanco.conectaBanco();
            preparedStatement = connection.prepareStatement("SELECT * FROM cliente WHERE nome LIKE ?");
            preparedStatement.setString(1, "%" + nome + "%");
            resultSet = preparedStatement.executeQuery();
            while(resultSet != null && resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt(1));
                cliente.setNome(resultSet.getString(2));
                cliente.setTelefone(resultSet.getString(3));
                cliente.setEmail(resultSet.getString(4));
                cliente.setSexo(resultSet.getString(5));
                cliente.setIdade(resultSet.getInt(6));
                clientes.add(cliente);
            } 
            System.out.println("Clientes buscados por nome com sucesso!");
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar clientes por nome: "+ex.getMessage());
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
        return clientes;
    }
}