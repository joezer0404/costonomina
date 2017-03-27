/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modelo.vo;

import java.util.ArrayList;

/**
 * Representa los calculos necesarios para determinar el valor de una prenda.
 * 
 * El unico valor que dependera directamente del usuario sera el conjunto de 
 * materiales textil y no textil a utilizar en la prenda, indicando la cantidad
 * usada. El resto son valores obtenidos de la base de datos tales como el precio
 * de la mano de obra, gastos y costos, entre otros.
 * 
 * @see PersonalVo
 * @author Hector
 */
public class CalculoFinalVo {
    
    /**
     * Clase que representa el material primo utilizado y la cantidad utilizada,
     * puede venir en metros o unidades.
     */
    class MateriaPrima {
        MateriaPrimaVo material;
        float cantidad;
        float total;
        
        MateriaPrima(MateriaPrimaVo material, float cantidad){
            this.material = material;
            this.cantidad = cantidad;
            this.total = material.getPrecio() * cantidad;
        }
    }
    
    /** El conjunto de materia prima, pueden ser textil o no textil. */
    private ArrayList<MateriaPrima> materiales;
    
    /** El total de materia prima */
    private float totalMateriaPrima;
    
    /** El total de la mano de obra. */
    private float totalManoDeObra;
    
    /** El total de costo fijo. */
    private float totalCostoFijo;
    
    /** El total de costo de fabricacion. */
    private float totalCostoFabricacion;
    
    /** El total de gastos administrativos. */
    private float totalGastoAdministrativo;
    
    /** El total de gastos de ventas. */
    private float totalGastoVenta;
    
    /**
     * Constructor default.
     */
    public CalculoFinalVo() {
        this.materiales = new ArrayList();
        this.totalMateriaPrima = 0;
        this.totalManoDeObra = 0;
        this.totalCostoFijo = 0;
        this.totalCostoFabricacion = 0;
        this.totalGastoAdministrativo = 0;
        this.totalGastoVenta = 0;
    }
    
    /**
     * Agrega un material textil a la lista con su cantidad utilizada,
     * en caso de existir ya ese material, se reemplaza la cantidad usada.
     * @param material la materia prima textil a agregar al conjunto
     * @param cantidad la cantidad valida en metros a utilizar
     */
    public boolean agregarMaterial(MateriaPrimaVo material, float cantidad){
        if(!(material instanceof MateriaPrimaTextilVo)){
            System.out.println("[CalculoFinalVo.agregarMaterial()] Se debe ingresar"
                    + "un material textil.");
            return false;
        }else if(cantidad <= 0){
            System.out.println("[CalculoFinalVo.agregarMaterial()] Se debe ingresar"
                    + "una cantidad valida en metros.");
            return false;
        }
        
        if(getMaterial(material) == null){
            materiales.add(new MateriaPrima(material, cantidad));
        }else{
            getMaterial(material).cantidad = cantidad;
        }
        
        return true;
    }
    
    /**
     * Agrega un material no textil a la lista con sus unidades usadas,
     * en caso de existir ese material, se reemplaza la cantidad usada.
     * @param material la materia prima textil a agregar al conjunto
     * @param unidades la cantidad en unidades de materia no textil usada
     */
    public boolean agregarMaterial(MateriaPrimaVo material, int unidades){
        if(!(material instanceof MateriaPrimaNoTextilVo)){
            System.out.println("[CalculoFinalVo.agregarMaterial()] Se debe ingresar"
                    + "un material no textil.");
            return false;
        }else if(unidades <= 0){
            System.out.println("[CalculoFinalVo.agregarMaterial()] Se debe ingresar"
                    + "una cantidad valida en unidades.");
            return false;
        }
        
        if(getMaterial(material) == null){
            materiales.add(new MateriaPrima(material, unidades));
        }else{
            getMaterial(material).cantidad = unidades;
        }
        
        return true;
    }
    
    /**
     * Remueve un material de la lista.
     * @param material la materia prima a remover del conjunto
     */
    public boolean removerMaterial(MateriaPrimaVo material){
        
        if(getMaterial(material) != null){
            materiales.remove(getMaterial(material));
            return true;
        }
        
        return false;
    }
    
    /**
     * Retorna la materia prima por referencia del conjunto
     * @param materia la materia prima textil/no textil a buscar
     * @return el objeto <CODE>MateriaPrima</CODE> asociado a ese material, null en caso de no encontrarlo
     */
    public MateriaPrima getMaterial(MateriaPrimaVo materia){
        for(MateriaPrima m: materiales){
            if(m.material == materia){
                return m;
            }
        }
        
        return null;
    }
    
    /**
     * Actualiza el total de la materia prima.
     */
    public void actualizarMateriaPrima(){
        totalMateriaPrima = 0;
        for(MateriaPrima m: materiales){
            totalMateriaPrima += m.total;
        }
        
    }
    
    /**
     * Actualiza el total de mano de obra en funcion de la nomina actual.
     * @param nomina nomina de la que se obtiene el total.
     */
    public void actualizarManoDeObra(NominaVo nomina){
        totalManoDeObra = nomina.getTotal();
    }
    
    /**
     * Actualiza el total de de los costos.
     * @param fijos conjunto de costos fijos
     * @param fabricaciones conjunto de costos relacionados con la fabricacion
     */
    public void actualizarCostos(ArrayList<CostoFijoVo> fijos, ArrayList<CostoFabricacionVo> fabricaciones){
        totalCostoFijo = 0;
        for(CostoFijoVo f : fijos){
            totalCostoFijo += f.getMonto();
        }
        totalCostoFabricacion = 0;
        for(CostoFabricacionVo f : fabricaciones){
            totalCostoFabricacion += f.getMonto();
        }
    }
    
    /**
     * Actualiza el total de de los gastos.
     * @param gastos conjunto de gastos administrativos
     * @param ventas conjunto de gastos de ventas
     */
    public void actualizarGastos(ArrayList<GastosAdministracionVo> gastos, ArrayList<GastosVentaVo> ventas){
        totalGastoAdministrativo = 0;
        for(GastosAdministracionVo g : gastos){
            totalGastoAdministrativo += g.getMonto();
        }
        totalGastoVenta = 0;
        for(GastosVentaVo g : ventas){
            totalGastoVenta += g.getMonto();
        }
    }
}
