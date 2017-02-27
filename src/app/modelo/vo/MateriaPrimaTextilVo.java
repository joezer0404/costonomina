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
public class MateriaPrimaTextilVo extends MateriaPrimaVo{
    
    public enum Unidad{
        KG,
        MT
    }
    
    protected Unidad unidad;
    protected float pesoMtrs;

    public MateriaPrimaTextilVo(){
        super();
        this.unidad   = Unidad.KG;
        this.pesoMtrs = 0;
    }
    
    public MateriaPrimaTextilVo(Unidad unidad, float pesoMtrs) {
        this.unidad = unidad;
        this.pesoMtrs = pesoMtrs;
    }

    public MateriaPrimaTextilVo(Unidad unidad, float pesoMtrs, int id, String descripcion, float mtsPso, float mts, float precio, float total) {
        super(id, descripcion, mtsPso, mts, precio, total);
        this.unidad = unidad;
        this.pesoMtrs = pesoMtrs;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public float getPesoMtrs() {
        return pesoMtrs;
    }

    public void setPesoMtrs(float pesoMtrs) {
        this.pesoMtrs = pesoMtrs;
    }
    
}
