/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modelo.dao;

import app.conexion.DbException;
import app.conexion.IDbConnection;
import app.conexion.MySqlDbConnection;
import app.modelo.vo.NominaVo;
import app.modelo.vo.PersonalVo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data access object de NominaVo, esta clase permite la conexion
 * de la base de datos y la obtencion de sus elementos, aparte de editarlos.
 * 
 * Manipula tambien PesonalDao para obtener los empleados registrados en la
 * nomina
 * 
 * - Acomodar logica de Fechas
 * 
 * @author Hector
 */
public class NominaDao implements IDataDao<NominaVo>{

    boolean localOpen;
    
    @Override
    public NominaVo getRecord(IDbConnection db, HashMap<String, Object> options) {
        List<NominaVo> list = getList(db, options);
        if (list.size()>0) return list.get(0);
        return null;
    }

    @Override
    public List<NominaVo> getList(IDbConnection db, HashMap<String, Object> options) {
        List<NominaVo> list = new ArrayList<>();
        MySqlDbConnection mysqlDb = (MySqlDbConnection) db;
        
        try { 
            if (!mysqlDb.isOpen()){
                mysqlDb.open(true);
                localOpen = true;
            }    
            
            String sql = "SELECT * FROM NOMINA WHERE 1=1";
            
            if(options != null){
                Integer id = (Integer) options.get("id");
                if (id != null)
                    sql += " AND id ='" + id + "'";
            
                Date fecha = (Date) options.get("fecha");
                if (fecha != null)
                    sql += " AND fecha ='" + fecha + "'";
                
                String order = (String) options.get("order");
                    if (order != null)
                        sql += " ORDER BY " + order;
            }
            
            
            ResultSet rs = mysqlDb.getResultSet(sql);
            while (rs.next()){
                NominaVo l = new NominaVo(rs.getInt("id"),
                                        rs.getDate("fecha"), 
                                        rs.getString("empresa"),
                                        rs.getString("rif"), 
                                        rs.getString("direccion"));
                
                PersonalDao personalDao = new PersonalDao();
                PersonalVo p;
                HashMap<String, Object> _options = new HashMap();
                sql = "SELECT * FROM NOMINA_PERSONAL WHERE nomina = " + rs.getInt("id");
                ResultSet _rs = mysqlDb.getResultSet(sql);
                while(_rs.next()){    
                    _options.put("cedula", _rs.getString("cedula"));
                    p = personalDao.getRecord(db, _options);
                    
                    System.out.println("Nomina: " + l.getId() + " - Cedula: " + p.getCedula());
                    
                    l.addEmpleado(p);
                    _options.clear();
                }
                
                list.add(l);
            }
        } catch (DbException | SQLException ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (localOpen){
            localOpen = false;
            mysqlDb.close();
        }
        return list;
    }

    @Override
    public boolean insertRecord(IDbConnection db, NominaVo record, HashMap<String, Object> options) {
        MySqlDbConnection mysqlDb = (MySqlDbConnection) db;
        
        try { 
            if (!mysqlDb.isOpen()){
                mysqlDb.open(false);
                localOpen = true;
            }
            
            String sql = "INSERT INTO NOMINA VALUES(NULL,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement sentencia = mysqlDb.getPS(sql, true);
            sentencia.setDate(1, record.getFecha());
            sentencia.setString(2, record.getEmpresa());
            sentencia.setString(3, record.getRif());
            sentencia.setString(4, record.getDireccion());
            sentencia.setFloat(5, record.getSalarioM());
            sentencia.setFloat(6, record.getSalarioS());
            sentencia.setFloat(7, record.getIvss4());
            sentencia.setFloat(8, record.getIvss5());
            sentencia.setFloat(9, record.getInces());
            sentencia.setFloat(10, record.getLph());
            sentencia.setFloat(11, record.getPrestaciones());
            sentencia.setFloat(12, record.getUtilidades());
            sentencia.setFloat(13, record.getCestaticket());
            sentencia.setFloat(14, record.getTotal());
            
            sentencia.executeUpdate();
            
            ResultSet rs = sentencia.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            
            record.setId(id);
            
            List<PersonalVo> empleados = record.getPersonal();
            for(PersonalVo empleado: empleados){
                sql = "INSERT INTO NOMINA_PERSONAL VALUES(?,?)";
                sentencia = mysqlDb.getPS(sql);
                sentencia.setInt(1, empleado.getCedula());
                sentencia.setInt(2, id);
                sentencia.executeUpdate();
            }
            
        } catch (DbException | SQLException ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (localOpen){
            localOpen = false;
            mysqlDb.close();
        }
        
        return true;
    }

    @Override
    public boolean updateRecord(IDbConnection db, NominaVo record, HashMap<String, Object> options) {
        MySqlDbConnection mysqlDb = (MySqlDbConnection) db;
        int updatedRecords = 0;
        
        try { 
            if (!mysqlDb.isOpen()){
                mysqlDb.open(false);
                localOpen = true;
            }    
            
            String sql = "UPDATE NOMINA " 
                    + "SET salarioM = '" + record.getSalarioM() + "' ,"
                    + "salarioS = '" + record.getSalarioS() + "' ,"
                    + "ivss4 = '" + record.getIvss4() + "' ,"
                    + "ivss5 = '" + record.getIvss5() + "' ,"
                    + "inces = '" + record.getInces() + "' ,"
                    + "lph   = '" + record.getLph() + "' ,"
                    + "prestaciones = '" + record.getPrestaciones() + "' ,"
                    + "utilidades   = '" + record.getUtilidades() + "' ,"
                    + "cestaticket  = '" + record.getCestaticket() + "'"
                    + " WHERE id = '" + record.getId() + "'";
               
            System.out.println("UPDATE SQL:" + sql);

            updatedRecords = mysqlDb.executeUpdate(sql);
            sql = "DELETE FROM NOMINA_PERSONAL WHERE nomina = " + record.getId();
            mysqlDb.executeUpdate(sql);
            
            List<PersonalVo> empleados = record.getPersonal();
            for(PersonalVo empleado: empleados){
                sql = "INSERT INTO NOMINA_PERSONAL VALUES(?,?)";
                PreparedStatement sentencia = mysqlDb.getPS(sql);
                sentencia.setInt(1, empleado.getCedula());
                sentencia.setInt(2, record.getId());
                sentencia.executeUpdate();
            }
            
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
    public boolean deleteRecord(IDbConnection db, NominaVo record, HashMap<String, Object> options) {
        MySqlDbConnection mysqlDb = (MySqlDbConnection) db;
        int deletedRecords = 0;
        
        try { 
            if (!mysqlDb.isOpen()){
                mysqlDb.open(false);
                localOpen = true;
            }    
            
            String sql = "DELETE FROM NOMINA " 
                    + "WHERE id = '" + record.getId() + "'";
            
            System.out.println("DELETE SQL:" + sql);
            
            deletedRecords = mysqlDb.executeUpdate(sql);
        } catch (DbException | SQLException ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (localOpen){
            localOpen = false;
            mysqlDb.close();
        }
        
        return (deletedRecords > 0);        
    }
    
}
