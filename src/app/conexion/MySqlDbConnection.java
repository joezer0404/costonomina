/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.conexion;

import app.Configuraciones;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementacion de los metodos de la clase AbstractDbConnection,
 * con el objetivo de crear un conector funcional para una base de datos
 * MySQL.
 * 
 * Los atributos tales como url, nombre de la base de datos, usuario y 
 * contraseña se encuentran en la clase "Configuraciones"
 * 
 * @see Configuraciones
 * @author Jorge
 */
public class MySqlDbConnection extends AbstractDbConnection{
    
    private Connection connection;
    
    public MySqlDbConnection(String serverUrl, String dbName, String login, String password){
        this.serverUrl      = serverUrl;
        this.dbName         = dbName;
        this.userLogin      = login;
        this.userPassword   = password;
    }
    
    public MySqlDbConnection(){
        this.serverUrl      = Configuraciones.DB_URL + Configuraciones.DB_NAME;
        this.dbName         = Configuraciones.DB_NAME;
        this.userLogin      = Configuraciones.DB_USER;
        this.userPassword   = Configuraciones.DB_PASS;
    }
    
    @Override
    public void open(boolean readOnly) throws DbException {
        super.open(readOnly);

        try { 
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(getServerUrl(), getUserLogin(), getUserPassword()); 
        } catch (ClassNotFoundException cnfe) { 
            throw new DbException("JDBC Driver not found"); 
        } catch (SQLException e) { 
            throw new DbException(e.getLocalizedMessage()); 
        }    
    }

    @Override
    public void close() {
        super.close();
        
        try {
            if (isOpen()){
                connection.close();
            }    
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isOpen(){
        try {
            return (connection != null && !connection.isClosed());
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDbConnection.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    @Override
    public void beginTransaction() throws DbException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            throw new DbException(ex.getLocalizedMessage());
        }
    }

    @Override
    public void commitTransaction() throws DbException {
        try {
            connection.commit();
        } catch (SQLException ex) {
            throw new DbException(ex.getLocalizedMessage());
        }
    }

    @Override
    public void rollbackTransaction() throws DbException {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            throw new DbException(ex.getLocalizedMessage());
        }
    }
    
    public int executeUpdate(String sql) throws SQLException{
        if (connection != null){
            Statement stat = connection.createStatement();
            int r = stat.executeUpdate(sql);
            return r;
        }
        return -1;
    }
    
    public ResultSet getResultSet(String sql) throws SQLException{
        if (connection != null){
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            return rs;
        }
        return null;
    }
    
    public PreparedStatement getPS(String sql) throws SQLException {
        return (PreparedStatement) connection.prepareStatement(sql);
    }
    
    public PreparedStatement getPS(String sql, boolean t) throws SQLException {
        return (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }
}
