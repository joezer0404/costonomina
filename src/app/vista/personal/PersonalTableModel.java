package app.vista.personal;

import app.conexion.IDbConnection;
import app.conexion.MySqlDbConnection;
import app.modelo.dao.PersonalDao;
import app.modelo.vo.PersonalVo;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo de la tabla PersonaVo, esta clase sera utilizada para representar
 * el JTable de la vista PersonalFrameView.
 * 
 * @see PersonalFrameView
 * @see PersonalVo
 * @see PersonalDao
 * @author Hector
 */
public class PersonalTableModel extends AbstractTableModel{
    /**
     * 1- COLUMN COUNT representara la cantidad de columnas que llevara la tabla,
     * depende de la cantidad de atributos que quieres mostrar en la tabla.
     */
    private static final int COLUMN_COUNT = 13;
    /**
     * 2- Column Names son los nombres asignados por cada columna, deben tener la misma
     * cantidad de columnas que se indican en COLUMN COUNT.
     */
    private static final String columnNames[] = {"Cedula", "Nombre", "Apellido",
    "Cargo", "Salario mensual", "Salario semanal", "IVSS 4", "IVSS 5", "INCES", "LPH",
    "Prestaciones", "Utilidades", "Cestaticket"};
    
    private MySqlDbConnection connection;
    /**
     * 3- El Dao del objeto a utilizar, usar Dao adecuado
     */
    private PersonalDao       personalDao; 
    /**
     * 4- La lista de objetos a mostrar en la tabla, usar Vo adecuado
     */
    private List<PersonalVo>  empleados;
    
    /**
     * 5- Adaptar con el Dao elegido
     * @param conn 
     */
    public PersonalTableModel(IDbConnection conn){
        this.connection = (MySqlDbConnection) conn;
        personalDao = new PersonalDao();
        empleados = personalDao.getList(conn, null);
    }
    
    @Override
    public int getRowCount() {
        if (empleados == null)
            return 0;
        return empleados.size();
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
        if (empleados == null || rowIndex >= empleados.size())
            return null;
        
        PersonalVo empleado = empleados.get(rowIndex);
        
        switch(columnIndex){
            case 0:
                return empleado.getCedula();
            case 1:
                return empleado.getNombre();
            case 2:
                return empleado.getApellido();
            case 3:
                return empleado.getCargo();
            case 4:
                return empleado.getSalarioM();
            case 5:
                return empleado.getSalarioS();
            case 6:
                return empleado.getIvss4();
            case 7:
                return empleado.getIvss5();
            case 8:
                return empleado.getInces();
            case 9:
                return empleado.getLph();
            case 10:
                return empleado.getUtilidades();
            case 11:
                return empleado.getPrestaciones();
            case 12:
                return empleado.getCestaticket();
        }
        
        return null;
    }
    
    /**
     * 7- Utilizar Vo correcto
     * @param index
     * @return 
     */
    public PersonalVo getPersonal(int index){
        if (index < 0 || index >= empleados.size())
            return null;
        return empleados.get(index);
    }
    
    /**
     * 8- Utilizar Vo correcto
     * @param l 
     */
    public void addRow(PersonalVo l){
        empleados.add(l);
        fireTableRowsInserted(empleados.size(), empleados.size());
    }
    
    /**
     * 9- Utilizar Vo correcto
     * @param index
     * @param l 
     */
    public void updateRow(int index, PersonalVo l){
        empleados.set(index, l);
        fireTableRowsUpdated(index, index);
    }
    
    public void deleteRow(int index){
        empleados.remove(index);
        fireTableRowsDeleted(index, index);
    }
    
    /**
     * 10- Utilizar Vo correcto
     * @param empleados 
     */
    public void setPersonal(List<PersonalVo> empleados){
        this.empleados = empleados;
        fireTableDataChanged();
    }
    
    public ArrayList<PersonalVo> getPersonal(){
        return (ArrayList)empleados;
    }
}