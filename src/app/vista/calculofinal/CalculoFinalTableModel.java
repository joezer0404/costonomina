/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.vista.calculofinal;

import app.modelo.vo.CalculoFinalVo;
import app.modelo.vo.MateriaPrimaTextilVo;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author S.C
 */
public class CalculoFinalTableModel extends AbstractTableModel {
     /**
     * 1- COLUMN COUNT representara la cantidad de columnas que llevara la tabla,
     * depende de la cantidad de atributos que quieres mostrar en la tabla.
     */
    private static final int COLUMN_COUNT = 5;
    /**
     * 2- Column Names son los nombres asignados por cada columna, deben tener la misma
     * cantidad de columnas que se indican en COLUMN COUNT.
     */
    private static final String columnNames[] = {"nombre", "unidad", "precio","cantidad","total"};
    
    private CalculoFinalVo calculo;
    
    /**
     * 5- Adaptar con el Dao elegido
     * @param conn 
     */
    public CalculoFinalTableModel(CalculoFinalVo calculo){
        this.calculo = calculo;
    }
    
    @Override
    public int getRowCount() {
        if (calculo == null)
            return 0;
        return calculo.size();
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
        if (calculo == null || rowIndex >= calculo.size())
            return null;
        
        CalculoFinalVo.MateriaPrima material = calculo.getMaterial(rowIndex);
        
        switch(columnIndex){
            case 0:
                return material.material.getDescripcion();
            case 1:
                return material.material instanceof MateriaPrimaTextilVo? "MTS":"UND";
            case 2:
                return material.material.getPrecio();
            case 3:
                return material.cantidad;
            case 4:
                return material.total;
        }
        
        return null;
    }
    
    /**
     * 7- Utilizar Vo correcto
     * @param index
     * @return 
     */
    public CalculoFinalVo.MateriaPrima getMateriaPrima(int index){
        if (index < 0 || index >= calculo.size())
            return null;
        return calculo.getMaterial(index);
    }
    
    /**
     * 8- Utilizar Vo correcto
     * @param l 
     */
    public void addRow(CalculoFinalVo.MateriaPrima l){
        calculo.agregarMaterial(l.material, l.cantidad);
        fireTableRowsInserted(calculo.size(), calculo.size());
    }
    
    /**
     * 9- Utilizar Vo correcto
     * @param index
     * @param l 
     */
    public void updateRow(int index, CalculoFinalVo.MateriaPrima l){
        calculo.agregarMaterial(l.material, l.cantidad);
        fireTableRowsUpdated(index, index);
    }
    
    public void deleteRow(int index){
        calculo.removerMaterial(index);
        fireTableRowsDeleted(index, index);
    }
    
    /**
     *
     * @param materiales
     */
    public void setCostoFabricacion(List<CalculoFinalVo.MateriaPrima> materiales){
        for(CalculoFinalVo.MateriaPrima m : materiales){
            calculo.agregarMaterial(m.material, m.cantidad);
        }
        fireTableDataChanged();
    }
        
}

