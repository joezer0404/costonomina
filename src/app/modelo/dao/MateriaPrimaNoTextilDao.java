/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modelo.dao;

import app.conexion.DbException;
import app.conexion.IDbConnection;
import app.conexion.MySqlDbConnection;
import app.modelo.vo.MateriaPrimaNoTextilVo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hector
 */
public class MateriaPrimaNoTextilDao implements IDataDao<MateriaPrimaNoTextilVo>{
    boolean localOpen;
    
    @Override
    public MateriaPrimaNoTextilVo getRecord(IDbConnection db, HashMap<String, Object> options) {
        List<MateriaPrimaNoTextilVo> list = getList(db, options);
        if (list.size()>0) return list.get(0);
        return null;
    }

    @Override
    public List<MateriaPrimaNoTextilVo> getList(IDbConnection db, HashMap<String, Object> options) {
        List<MateriaPrimaNoTextilVo> list = new ArrayList<>();
        MySqlDbConnection mysqlDb = (MySqlDbConnection) db;
        
        try {
            if (!mysqlDb.isOpen()){
                mysqlDb.open(true);
                localOpen = true;
            }    
            
            String sql = "SELECT * FROM MATERIA_PRIMA INNER JOIN NO_TEXTIL "
                    + "ON MATERIA_PRIMA.id=NO_TEXTIL.id WHERE 1=1";
            
            if(options != null){
                
                Integer id = (Integer) options.get("id");
                if (id != null)
                    sql += " AND id ='" + id + "'";
            
                String descripcion = (String) options.get("descripcion");
                if (id != null)
                    sql += " AND descripcion ='" + descripcion + "'";
                
                String order = (String) options.get("order");
                if (order != null)
                    sql += " ORDER BY " + order;
            }
            
            ResultSet rs = mysqlDb.getResultSet(sql);
            while (rs.next()){
                MateriaPrimaNoTextilVo l = new MateriaPrimaNoTextilVo(rs.getFloat("peso"),
                                        rs.getInt("id"),
                                        rs.getString("descripcion"), 
                                        rs.getFloat("mts_pso"),
                                        rs.getFloat("mts"), 
                                        rs.getFloat("precio"),
                                        rs.getFloat("total"));
                
                list.add(l);
            }
        } catch (DbException | SQLException ex) {
            Logger.getLogger(MateriaPrimaNoTextilDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (localOpen){
            localOpen = false;
            mysqlDb.close();
        }
        return list;
    }

    @Override
    public boolean insertRecord(IDbConnection db, MateriaPrimaNoTextilVo record, HashMap<String, Object> options) {
        MySqlDbConnection mysqlDb = (MySqlDbConnection) db;
        
        try { 
            if (!mysqlDb.isOpen()){
                mysqlDb.open(false);
                localOpen = true;
            }
            
            MateriaPrimaDao mpd = new MateriaPrimaDao();
            mpd.insertRecord(db, record, options);
            
            String sql = "INSERT INTO NO_TEXTIL VALUES(?,?)";
            
            PreparedStatement sentencia = mysqlDb.getPS(sql, true);
            
            sentencia.setInt(1, record.getId());
            sentencia.setFloat(2, record.getPeso());
            
            sentencia.executeUpdate();
            
            ResultSet rs = sentencia.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            
            record.setId(id);
            
        } catch (DbException | SQLException ex) {
            Logger.getLogger(MateriaPrimaNoTextilDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (localOpen){
            localOpen = false;
            mysqlDb.close();
        }
        
        return true;
    }

    @Override
    public boolean updateRecord(IDbConnection db, MateriaPrimaNoTextilVo record, HashMap<String, Object> options) {
        MySqlDbConnection mysqlDb = (MySqlDbConnection) db;
        int updatedRecords = 0;
        
        try { 
            if (!mysqlDb.isOpen()){
                mysqlDb.open(false);
                localOpen = true;
            }    
            
            MateriaPrimaDao materiaPrimaDao = new MateriaPrimaDao();
            materiaPrimaDao.updateRecord(db, record, options);
            
            String sql = "UPDATE NO_TEXIL " 
                    + "SET peso = '" + record.getPeso() + "'"
                    + " WHERE id = '" + record.getId() + "'";
               
            System.out.println("UPDATE SQL:" + sql);

            updatedRecords = mysqlDb.executeUpdate(sql);
            
        } catch (DbException | SQLException ex) {
            Logger.getLogger(MateriaPrimaNoTextilDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (localOpen){
            localOpen = false;
            mysqlDb.close();
        }
        
        return (updatedRecords>0);
    }

    @Override
    public boolean deleteRecord(IDbConnection db, MateriaPrimaNoTextilVo record, HashMap<String, Object> options) {
        MySqlDbConnection mysqlDb = (MySqlDbConnection) db;
        int deletedRecords = 0;
        
        try { 
            if (!mysqlDb.isOpen()){
                mysqlDb.open(false);
                localOpen = true;
            }    
            
            String sql = "DELETE FROM MATERIA_PRIMA " 
                    + "WHERE id = '" + record.getId() + "'";
            
            System.out.println("DELETE SQL:" + sql);
            
            deletedRecords = mysqlDb.executeUpdate(sql);
        } catch (DbException | SQLException ex) {
            Logger.getLogger(MateriaPrimaNoTextilDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (localOpen){
            localOpen = false;
            mysqlDb.close();
        }
        
        return (deletedRecords > 0);        
    }
}
