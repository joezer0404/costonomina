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
public class NoTextilVo extends MateriaPrimaVo{

    private float  peso;
    
    public NoTextilVo(int id, String descripcion, float mts_pso, float precio, float peso){
        super(id, descripcion, mts_pso, precio);
        
        this.peso = peso;
        
        actualizarCalculo();
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
        actualizarCalculo();
    }
    
    @Override
    protected void actualizarCalculo() {
        this.mts = this.mts_pso / this.peso;
        this.total = this.mts * this.precio;
    }
    
}