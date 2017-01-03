/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.conexion;

/**
 *
 * @author ejorge
 */
public interface IDbConnection {
    
    public void open(boolean readOnly) throws DbException;
    
    public void close();
    
    public boolean isOpen();

    public void beginTransaction() throws DbException;
    
    public void commitTransaction() throws DbException;
    
    public void rollbackTransaction() throws DbException;
        
}
