package app.vista.textil;

import app.vista.textil.*;
import app.conexion.IDbConnection;
import app.conexion.MySqlDbConnection;
import app.modelo.dao.TextilDao;
import app.modelo.vo.TextilVo;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author S.C
 */
public class TextilTableModel extends AbstractTableModel {
     /**
     * 1- COLUMN COUNT representara la cantidad de columnas que llevara la tabla,
     * depende de la cantidad de atributos que quieres mostrar en la tabla.
     */
    private static final int COLUMN_COUNT = 6;
    /**
     * 2- Column Names son los nombres asignados por cada columna, deben tener la misma
     * cantidad de columnas que se indican en COLUMN COUNT.
     */
    private static final String columnNames[] = {"Descripcion", "Peso", "MtsxPso", "Mts", "Precio", "Total"};
    
    private MySqlDbConnection connection;
    /**
     * 3- El Dao del objeto a utilizar, usar Dao adecuado
     */
    private TextilDao     TextilDao;
    /**
     * 4- La lista de objetos a mostrar en la tabla, usar Vo adecuado
     */
    private List<TextilVo>  materiatextil;
    
    /**
     * 5- Adaptar con el Dao elegido
     * @param conn 
     */
    public TextilTableModel(IDbConnection conn){
        this.connection = (MySqlDbConnection) conn;
        TextilDao = new TextilDao();
        materiatextil = TextilDao.getList(conn, null);
    }
    
    @Override
    public int getRowCount() {
        if (materiatextil == null)
            return 0;
        return materiatextil.size();
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
        if (materiatextil == null || rowIndex >= materiatextil.size())
            return null;
        
        TextilVo textil = materiatextil.get(rowIndex);
        
        switch(columnIndex){
            case 0:
                return textil.getDescripcion();
            case 1:
                return textil.getPeso();
            case 2:
                return textil.getMtsxPso();
            case 3:
                return textil.getMts(); 
            case 4:
                return textil.getPrecio();
            case 5:
                return textil.getTotal();     
        }
        
        return null;
    }
    
    /**
     * 7- Utilizar Vo correcto
     * @param index
     * @return 
     */
    public TextilVo getTextil(int index){
        if (index < 0 || index >= materiatextil.size())
            return null;
        return materiatextil.get(index);
    }
    
    /**
     * 8- Utilizar Vo correcto
     * @param l 
     */
    public void addRow(TextilVo l){
        materiatextil.add(l);
        fireTableRowsInserted(materiatextil.size(), materiatextil.size());
    }
    
    /**
     * 9- Utilizar Vo correcto
     * @param index
     * @param l 
     */
    public void updateRow(int index, TextilVo l){
        materiatextil.set(index, l);
        fireTableRowsUpdated(index, index);
    }
    
    public void deleteRow(int index){
        materiatextil.remove(index);
        fireTableRowsDeleted(index, index);
    }
    
    /**
     * 10- Utilizar Vo correcto
     * @param materiatextil 
     */
    public void setGastosVenta(List<TextilVo> materiatextil){
        this.materiatextil = materiatextil;
        fireTableDataChanged();
    }
    
    
    
}