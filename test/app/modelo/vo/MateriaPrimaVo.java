/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modelo.vo;

/**
 *
 * @author Hector
 */
public abstract class MateriaPrimaVo {
    protected int     id;
    protected String  descripcion;
    protected float   mts_pso;
    protected float   mts;
    protected float   precio;
    protected float   total;
    
    public MateriaPrimaVo(int id, String descripcion, float mts_pso, float precio){
        this.id          = id;
        this.descripcion = descripcion;
        this.mts_pso     = mts_pso;
        this.precio      = precio;
        
        actualizarCalculo();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getMts_pso() {
        return mts_pso;
    }

    public void setMts_pso(float mts_pso) {
        this.mts_pso = mts_pso;
        actualizarCalculo();
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
        actualizarCalculo();
    }

    public float getMts() {
        return mts;
    }

    public float getTotal() {
        return total;
    }
    
    
    
    protected abstract void actualizarCalculo();
}
