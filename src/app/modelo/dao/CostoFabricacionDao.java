/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import app.conexion.DbException;
import app.conexion.IDbConnection;
import app.conexion.MySqlDbConnection;
import app.modelo.vo.CostoFabricacionVo;
import java.sql.PreparedStatement;

public class CostoFabricacionDao implements IDataDao<CostoFabricacionVo> {
   boolean localOpen;

    @Override
    public List<CostoFabricacionVo> getList(IDbConnection db, 
            HashMap<String, Object> options) {
        List<CostoFabricacionVo> list = new ArrayList<>();
        MySqlDbConnection mysqlDb = (MySqlDbConnection) db;
        
        try { 
            if (!mysqlDb.isOpen()){
                mysqlDb.open(true);
                localOpen = true;
            }    
            
            String sql = "SELECT * FROM COSTO_INDIRECTO WHERE 1=1";
            
            if (options != null){
                String id = (String) options.get("id");
                if (id != null)
                    sql += " AND id ='" + id + "'";
                
                String descripcion = (String) options.get("descripcion");
                if (descripcion != null)
                    sql += " AND descripcion ='" + descripcion + "'";
                
                String monto = (String) options.get("monto");
                if (monto != null)
                    sql += " AND monto ='" + monto + "'";
                
                String order = (String) options.get("order");
                if (order != null)
                    sql += " ORDER BY " + order;
            }
            
            ResultSet rs = mysqlDb.getResultSet(sql);
            while (rs.next()){
                CostoFabricacionVo l = new CostoFabricacionVo(rs.getInt("id"),
                                        rs.getString("descripcion"),
                                        rs.getFloat("monto"));
                list.add(l);
            }
        } catch (DbException | SQLException ex) {
            Logger.getLogger(CostoFabricacionVo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (localOpen){
            localOpen = false;
            mysqlDb.close();
        }
        return list;
    }

    
    @Override
    public CostoFabricacionVo getRecord(IDbConnection db, HashMap<String, Object> options) {
        List<CostoFabricacionVo> list = getList(db, options);
        if (list.size()>0) return list.get(0);
        return null;
    }
    
    @Override
    public boolean insertRecord(IDbConnection db, CostoFabricacionVo record, HashMap<String, Object> options) {
        MySqlDbConnection mysqlDb = (MySqlDbConnection) db;
        int insertedRecords = 0;
        
        try { 
            if (!mysqlDb.isOpen()){
                mysqlDb.open(false);
                localOpen = true;
            }
            
            // Son dos "?" de (descripcion, monto)
            // El id es null por auto increment
            // Las tablas usan piso bajo "_"
            String sql = "INSERT INTO COSTO_INDIRECTO VALUES(NULL,?,?)"; 
            
            PreparedStatement sentencia = mysqlDb.getPS(sql, true);
            // Aqui se agregan en orden  los valores
            sentencia.setString(1, record.getDescripcion()); // La descripcion es un setString()
            sentencia.setFloat(2, record.getMonto()); // El monto es un setFloat()
            
            insertedRecords = sentencia.executeUpdate();
            
            ResultSet rs = sentencia.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            
            record.setId(id);

        } catch (DbException | SQLException ex) {
            Logger.getLogger(PersonalDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (localOpen){
            localOpen = false;
            mysqlDb.close();
        }
        
        return (insertedRecords>0);
    }
 

      @Override
    public boolean updateRecord(IDbConnection db, CostoFabricacionVo record, HashMap<String, Object> options) {
        MySqlDbConnection mysqlDb = (MySqlDbConnection) db;
        int updatedRecords = 0;
        
        try { 
            if (!mysqlDb.isOpen()){
                mysqlDb.open(false);
                localOpen = true;
            }    
            
            String sql = "UPDATE COSTO_INDIRECTO " 
                    + "SET descripcion = '" + record.getDescripcion() + "' ,"
                    + "monto = " + record.getMonto()
                    + " WHERE id = " + record.getId();
                    // No olvidar el Where al hacer update o delete
                    // de lo contrario catastrofe, caos y destruccion llegará
               
            System.out.println("UPDATE SQL:" + sql);

            updatedRecords = mysqlDb.executeUpdate(sql);
        } catch (DbException | SQLException ex) {
            Logger.getLogger(CostoFabricacionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (localOpen){
            localOpen = false;
            mysqlDb.close();
        }
        
        return (updatedRecords>0);
    }

    @Override
    public boolean deleteRecord(IDbConnection db, CostoFabricacionVo record, HashMap<String, Object> options) {
        MySqlDbConnection mysqlDb = (MySqlDbConnection) db;
        int deletedRecords = 0;
        
        try { 
            if (!mysqlDb.isOpen()){
                mysqlDb.open(false);
                localOpen = true;
            }    
            
            String sql = "DELETE FROM COSTO_INDIRECTO " // todo saldra bien
                    + "WHERE id = '" + record.getId() + "'";
            
            System.out.println("DELETE SQL:" + sql);
            
            deletedRecords = mysqlDb.executeUpdate(sql);
        } catch (DbException | SQLException ex) {
            Logger.getLogger(PersonalDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (localOpen){
            localOpen = false;
            mysqlDb.close();
        }
        
        return (deletedRecords > 0);       
    }
}