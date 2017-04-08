/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.vista.calculofinal;

import app.conexion.IDbConnection;
import app.conexion.MySqlDbConnection;
import app.modelo.dao.MateriaPrimaDao;
import app.modelo.vo.MateriaPrimaTextilVo;
import app.modelo.vo.MateriaPrimaVo;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Hector
 */
public class MateriaPrimaTableModel extends AbstractTableModel {
     /**
     * 1- COLUMN COUNT representara la cantidad de columnas que llevara la tabla,
     * depende de la cantidad de atributos que quieres mostrar en la tabla.
     */
    private static final int COLUMN_COUNT = 5;
    /**
     * 2- Column Names son los nombres asignados por cada columna, deben tener la misma
     * cantidad de columnas que se indican en COLUMN COUNT.
     */
    private static final String columnNames[] = {"Descripcion", "MtsPso", "Mts", "Precio", "Total"};
    
    private MySqlDbConnection connection;
    /**
     * 3- El Dao del objeto a utilizar, usar Dao adecuado
     */
    private MateriaPrimaDao     mDao;
    /**
     * 4- La lista de objetos a mostrar en la tabla, usar Vo adecuado
     */
    private List<MateriaPrimaVo>  materia;
    
    /**
     * 5- Adaptar con el Dao elegido
     * @param conn 
     */
    public MateriaPrimaTableModel(IDbConnection conn){
        this.connection = (MySqlDbConnection) conn;
        mDao = new MateriaPrimaDao();
        materia = mDao.getList(conn, null);
    }
    
    @Override
    public int getRowCount() {
        if (materia == null)
            return 0;
        return materia.size();
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
        if (materia == null || rowIndex >= materia.size())
            return null;
        
        MateriaPrimaVo textil = materia.get(rowIndex);
        
        switch(columnIndex){
            case 0:
                return textil.getDescripcion();
            case 1:
                return textil.getMtsPso();
            case 2:
                return textil.getMts();    
            case 3:
                return textil.getPrecio();
            case 4:
                return textil.getTotal();     
        }
        
        return null;
    }
    
    /**
     * 7- Utilizar Vo correcto
     * @param index
     * @return 
     */
    public MateriaPrimaVo getTextil(int index){
        if (index < 0 || index >= materia.size())
            return null;
        return materia.get(index);
    }
    
    /**
     * 8- Utilizar Vo correcto
     * @param l 
     */
    public void addRow(MateriaPrimaTextilVo l){
        materia.add(l);
        fireTableRowsInserted(materia.size(), materia.size());
    }
    
    /**
     * 9- Utilizar Vo correcto
     * @param index
     * @param l 
     */
    public void updateRow(int index, MateriaPrimaTextilVo l){
        materia.set(index, l);
        fireTableRowsUpdated(index, index);
    }
    
    public void deleteRow(int index){
        materia.remove(index);
        fireTableRowsDeleted(index, index);
    }
    
    /**
     * 10- Utilizar Vo correcto
     * @param materiatextil 
     */
    public void setTextil(List<MateriaPrimaVo> materia){
        this.materia = materia;
        fireTableDataChanged();
    }
    
}