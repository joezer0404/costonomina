package app.vista.gastosadministracion;

import app.conexion.IDbConnection;
import app.conexion.MySqlDbConnection;
import app.modelo.dao.GastosAdministracionDao;
import app.modelo.vo.GastosAdministracionVo;
import java.util.List;
import javax.swing.table.AbstractTableModel;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author S.C
 */
public class GastosAdministracionTableModel extends AbstractTableModel {
     /**
     * 1- COLUMN COUNT representara la cantidad de columnas que llevara la tabla,
     * depende de la cantidad de atributos que quieres mostrar en la tabla.
     */
    private static final int COLUMN_COUNT = 3;
    /**
     * 2- Column Names son los nombres asignados por cada columna, deben tener la misma
     * cantidad de columnas que se indican en COLUMN COUNT.
     */
    private static final String columnNames[] = {"id", "Descripcion", "monto"};
    
    private MySqlDbConnection connection;
    /**
     * 3- El Dao del objeto a utilizar, usar Dao adecuado
     */
    private GastosAdministracionDao     gastosAdministracionDao;
    /**
     * 4- La lista de objetos a mostrar en la tabla, usar Vo adecuado
     */
    private List<GastosAdministracionVo>  gastosadministracion;
    
    /**
     * 5- Adaptar con el Dao elegido
     * @param conn 
     */
    public GastosAdministracionTableModel(IDbConnection conn){
        this.connection = (MySqlDbConnection) conn;
        gastosAdministracionDao = new GastosAdministracionDao();
        gastosadministracion = gastosAdministracionDao.getList(conn, null);
    }
    
    @Override
    public int getRowCount() {
        if (gastosadministracion == null)
            return 0;
        return gastosadministracion.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }

            
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    /**
     * 6- Utilizar el Vo correcto y un case, en donde cada caso representara el 
     * orden de los atributos, debe tener el mismo orden que el que se representara
     * en las columnas.
     * 
     * @param rowIndex
     * @param columnIndex
     * @return 
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (gastosadministracion == null || rowIndex >= gastosadministracion.size())
            return null;
        
        GastosAdministracionVo empleado = gastosadministracion.get(rowIndex);
        
        switch(columnIndex){
            case 0:
                return empleado.getId();
            case 1:
                return empleado.getDescripcion();
            case 2:
                return empleado.getMonto();
        }
        
        return null;
    }
    
    /**
     * 7- Utilizar Vo correcto
     * @param index
     * @return 
     */
    public GastosAdministracionVo getGastosAdministracion(int index){
        if (index < 0 || index >= gastosadministracion.size())
            return null;
        return gastosadministracion.get(index);
    }
    
    /**
     * 8- Utilizar Vo correcto
     * @param l 
     */
    public void addRow(GastosAdministracionVo l){
        gastosadministracion.add(l);
        fireTableRowsInserted(gastosadministracion.size(), gastosadministracion.size());
    }
    
    /**
     * 9- Utilizar Vo correcto
     * @param index
     * @param l 
     */
    public void updateRow(int index, GastosAdministracionVo l){
        gastosadministracion.set(index, l);
        fireTableRowsUpdated(index, index);
    }
    
    public void deleteRow(int index){
        gastosadministracion.remove(index);
        fireTableRowsDeleted(index, index);
    }
    
    /**
     * 10- Utilizar Vo correcto
     * @param empleados 
     */
    public void setGastosAdministracion(List<GastosAdministracionVo> gastosadministracion){
        this.gastosadministracion = gastosadministracion;
        fireTableDataChanged();
    }
    
    
    
}