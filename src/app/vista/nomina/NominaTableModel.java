/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.vista.nomina;

import app.conexion.IDbConnection;
import app.conexion.MySqlDbConnection;
import app.modelo.dao.NominaDao;
import app.modelo.vo.NominaVo;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo de la tabla NominaVo, esta clase sera utilizada para representar
 * el JTable de la vista NominaFrameView.
 * 
 * @see NominaFrameView
 * @see NominaVo
 * @see NominaDao
 * @author Hector
 */
public class NominaTableModel extends AbstractTableModel{
    /**
     * 1- COLUMN COUNT representara la cantidad de columnas que llevara la tabla,
     * depende de la cantidad de atributos que quieres mostrar en la tabla.
     */
    private static final int COLUMN_COUNT = 15;
    /**
     * 2- Column Names son los nombres asignados por cada columna, deben tener la misma
     * cantidad de columnas que se indican en COLUMN COUNT.
     */
    private static final String columnNames[] = {"Id", "Fecha", "Empresa", "Rif",
    "Direccion", "Salario mensual", "Salario semanal", "IVSS 4", "IVSS 5", "INCES", "LPH",
    "Prestaciones", "Utilidades", "Cestaticket", "Total"};
    
    private MySqlDbConnection connection;
    /**
     * 3- El Dao del objeto a utilizar, usar Dao adecuado
     */
    private NominaDao       nominaDao; 
    /**
     * 4- La lista de objetos a mostrar en la tabla, usar Vo adecuado
     */
    private List<NominaVo>  nominas;
    
    /**
     * 5- Adaptar con el Dao elegido
     * @param conn 
     */
    public NominaTableModel(IDbConnection conn){
        this.connection = (MySqlDbConnection) conn;
        nominaDao = new NominaDao();
        nominas = nominaDao.getList(conn, null);
    }
    
    @Override
    public int getRowCount() {
        if (nominas == null)
            return 0;
        return nominas.size();
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
        if (nominas == null || rowIndex >= nominas.size())
            return null;
        
        NominaVo empleado = nominas.get(rowIndex);
        
        switch(columnIndex){
            case 0:
                return empleado.getId();
            case 1:
                return empleado.getFecha();
            case 2:
                return empleado.getEmpresa();
            case 3:
                return empleado.getRif();
            case 4:
                return empleado.getDireccion();
            case 5:
                return empleado.getSalarioM();
            case 6:
                return empleado.getSalarioS();
            case 7:
                return empleado.getIvss4();
            case 8:
                return empleado.getIvss5();
            case 9:
                return empleado.getInces();
            case 10:
                return empleado.getLph();
            case 11:
                return empleado.getUtilidades();
            case 12:
                return empleado.getPrestaciones();
            case 13:
                return empleado.getCestaticket();
            case 14:
                return empleado.getTotal();
        }
        
        return null;
    }
    
    /**
     * 7- Utilizar Vo correcto
     * @param index
     * @return 
     */
    public NominaVo getNominas(int index){
        if (index < 0 || index >= nominas.size())
            return null;
        return nominas.get(index);
    }
    
    /**
     * 8- Utilizar Vo correcto
     * @param l 
     */
    public void addRow(NominaVo l){
        nominas.add(l);
        fireTableRowsInserted(nominas.size(), nominas.size());
    }
    
    /**
     * 9- Utilizar Vo correcto
     * @param index
     * @param l 
     */
    public void updateRow(int index, NominaVo l){
        nominas.set(index, l);
        fireTableRowsUpdated(index, index);
    }
    
    public void deleteRow(int index){
        nominas.remove(index);
        fireTableRowsDeleted(index, index);
    }
    
    /**
     * 10- Utilizar Vo correcto
     * @param empleados 
     */
    public void setNominas(List<NominaVo> nominas){
        this.nominas = nominas;
        fireTableDataChanged();
    }
}