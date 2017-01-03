package app.vista.gastosventas;

import app.conexion.IDbConnection;
import app.conexion.MySqlDbConnection;
import app.modelo.dao.GastosVentaDao;
import app.modelo.vo.GastosVentaVo;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author S.C
 */
public class GastosVentasTableModel extends AbstractTableModel {
     /**
     * 1- COLUMN COUNT representara la cantidad de columnas que llevara la tabla,
     * depende de la cantidad de atributos que quieres mostrar en la tabla.
     */
    private static final int COLUMN_COUNT = 3;
    /**
     * 2- Column Names son los nombres asignados por cada columna, deben tener la misma
     * cantidad de columnas que se indican en COLUMN COUNT.
     */
    private static final String columnNames[] = {"id", "Descripcion", "Monto"};
    
    private MySqlDbConnection connection;
    /**
     * 3- El Dao del objeto a utilizar, usar Dao adecuado
     */
    private GastosVentaDao     gastosVentaDao;
    /**
     * 4- La lista de objetos a mostrar en la tabla, usar Vo adecuado
     */
    private List<GastosVentaVo>  gastosventa;
    
    /**
     * 5- Adaptar con el Dao elegido
     * @param conn 
     */
    public GastosVentasTableModel(IDbConnection conn){
        this.connection = (MySqlDbConnection) conn;
        gastosVentaDao = new GastosVentaDao();
        gastosventa = gastosVentaDao.getList(conn, null);
    }
    
    @Override
    public int getRowCount() {
        if (gastosventa == null)
            return 0;
        return gastosventa.size();
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
        if (gastosventa == null || rowIndex >= gastosventa.size())
            return null;
        
        GastosVentaVo empleado = gastosventa.get(rowIndex);
        
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
    public GastosVentaVo getGastosVenta(int index){
        if (index < 0 || index >= gastosventa.size())
            return null;
        return gastosventa.get(index);
    }
    
    /**
     * 8- Utilizar Vo correcto
     * @param l 
     */
    public void addRow(GastosVentaVo l){
        gastosventa.add(l);
        fireTableRowsInserted(gastosventa.size(), gastosventa.size());
    }
    
    /**
     * 9- Utilizar Vo correcto
     * @param index
     * @param l 
     */
    public void updateRow(int index, GastosVentaVo l){
        gastosventa.set(index, l);
        fireTableRowsUpdated(index, index);
    }
    
    public void deleteRow(int index){
        gastosventa.remove(index);
        fireTableRowsDeleted(index, index);
    }
    
    /**
     * 10- Utilizar Vo correcto
     * @param gastosventa 
     */
    public void setGastosVenta(List<GastosVentaVo> gastosventa){
        this.gastosventa = gastosventa;
        fireTableDataChanged();
    }
    
    
    
}