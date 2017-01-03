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
import app.modelo.vo.PersonalVo;
import java.sql.PreparedStatement;

/**
 * Data access object de PersonalVo, esta clase permite la conexion
 * de la base de datos y la obtencion de sus elementos, aparte de editarlos.
 * 
 * @author Jorge
 */
public class PersonalDao implements IDataDao<PersonalVo>{
    //flag to drive open/close connection in local methods
    boolean localOpen;
    
    @Override
    public List<PersonalVo> getList(IDbConnection db, 
            HashMap<String, Object> options) {
        List<PersonalVo> list = new ArrayList<>();
        MySqlDbConnection mysqlDb = (MySqlDbConnection) db;
        
        try { 
            if (!mysqlDb.isOpen()){
                mysqlDb.open(true);
                localOpen = true;
            }    
            
            String sql = "SELECT * FROM Personal WHERE 1=1";
            
            if (options != null){
                String cedula = (String) options.get("cedula");
                if (cedula != null)
                    sql += " AND cedula ='" + cedula + "'";
                
                String nombre = (String) options.get("nombre");
                if (nombre != null)
                    sql += " AND nombre LIKE '%" + nombre + "%'";
                
                String apellido = (String) options.get("apellido");
                if (apellido != null)
                    sql += " AND apellido LIKE '%" + apellido + "%'";
                
                PersonalVo.Cargo cargo = (PersonalVo.Cargo) options.get("cargo");
                if (cargo != null)
                    sql += " AND cargo ='" + cargo.name() + "'";
                
                String order = (String) options.get("order");
                if (order != null)
                    sql += " ORDER BY " + order;
            }
            
            ResultSet rs = mysqlDb.getResultSet(sql);
            while (rs.next()){
                PersonalVo l = new PersonalVo(rs.getInt("cedula"),
                                        rs.getString("nombre"),
                                        rs.getString("apellido"),
                                        PersonalVo.Cargo.valueOf(rs.getString("cargo")),
                                        rs.getFloat("salarioM"));
                list.add(l);
            }
        } catch (DbException | SQLException ex) {
            Logger.getLogger(PersonalDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (localOpen){
            localOpen = false;
            mysqlDb.close();
        }
        return list;
    }

    @Override
    public PersonalVo getRecord(IDbConnection db, HashMap<String, Object> options) {
        List<PersonalVo> list = getList(db, options);
        if (list.size()>0) return list.get(0);
        return null;
    }
    
    @Override
    public boolean insertRecord(IDbConnection db, PersonalVo record, HashMap<String, Object> options) {
        MySqlDbConnection mysqlDb = (MySqlDbConnection) db;
        int insertedRecords = 0;
        
        try { 
            if (!mysqlDb.isOpen()){
                mysqlDb.open(false);
                localOpen = true;
            }
            
            String sql = "INSERT INTO Personal VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement sentencia = mysqlDb.getPS(sql);
            sentencia.setInt(1, record.getCedula());
            sentencia.setString(2, record.getNombre());
            sentencia.setString(3, record.getApellido());
            sentencia.setString(4, record.getCargo().name());
            sentencia.setFloat(5, record.getSalarioM());
            sentencia.setFloat(6, record.getSalarioS());
            sentencia.setFloat(7, record.getIvss4());
            sentencia.setFloat(8, record.getIvss5());
            sentencia.setFloat(9, record.getInces());
            sentencia.setFloat(10, record.getLph());
            sentencia.setFloat(11, record.getPrestaciones());
            sentencia.setFloat(12, record.getUtilidades());
            sentencia.setFloat(13, record.getCestaticket());
            
            insertedRecords = sentencia.executeUpdate();

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
    public boolean updateRecord(IDbConnection db, PersonalVo record, HashMap<String, Object> options) {
        MySqlDbConnection mysqlDb = (MySqlDbConnection) db;
        int updatedRecords = 0;
        
        try { 
            if (!mysqlDb.isOpen()){
                mysqlDb.open(false);
                localOpen = true;
            }    
            
            String sql = "UPDATE Personal " 
                    + "SET nombre = '" + record.getNombre() + "' ,"
                    + "apellido = '" + record.getApellido() + "' ,"
                    + "cargo = '" + record.getCargo().name() + "' ,"
                    + "salarioM = '" + record.getSalarioM() + "' ,"
                    + "salarioS = '" + record.getSalarioS() + "' ,"
                    + "ivss4 = '" + record.getIvss4() + "' ,"
                    + "ivss5 = '" + record.getIvss5() + "' ,"
                    + "inces = '" + record.getInces() + "' ,"
                    + "lph   = '" + record.getLph() + "' ,"
                    + "prestaciones = '" + record.getPrestaciones() + "' ,"
                    + "utilidades   = '" + record.getUtilidades() + "' ,"
                    + "cestaticket  = '" + record.getCestaticket() + "'"
                    + "WHERE cedula = '" + record.getCedula() + "'";
               
            System.out.println("UPDATE SQL:" + sql);

            updatedRecords = mysqlDb.executeUpdate(sql);
        } catch (DbException | SQLException ex) {
            Logger.getLogger(PersonalDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (localOpen){
            localOpen = false;
            mysqlDb.close();
        }
        
        return (updatedRecords>0);
    }

    @Override
    public boolean deleteRecord(IDbConnection db, PersonalVo record, HashMap<String, Object> options) {
        MySqlDbConnection mysqlDb = (MySqlDbConnection) db;
        int deletedRecords = 0;
        
        try { 
            if (!mysqlDb.isOpen()){
                mysqlDb.open(false);
                localOpen = true;
            }    
            
            String sql = "DELETE FROM Personal " 
                    + "WHERE cedula = '" + record.getCedula() + "'";
            
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