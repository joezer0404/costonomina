/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modelo.dao;

import java.util.HashMap;
import java.util.List;
import app.conexion.IDbConnection;

/**
 *
 * @author Jorge
 * @param <T>
 */
public interface IDataDao<T> {
    
    public T getRecord(IDbConnection db, HashMap<String,Object> options);

    public List<T> getList(IDbConnection db, HashMap<String,Object> options);
    
    public boolean insertRecord(IDbConnection db, T record, HashMap<String,Object> options);
    
    public boolean updateRecord(IDbConnection db, T record, HashMap<String,Object> options);
    
    public boolean deleteRecord(IDbConnection db, T record, HashMap<String,Object> options);
}
