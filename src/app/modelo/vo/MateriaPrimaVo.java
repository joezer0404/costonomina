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
public class MateriaPrimaVo {
   
    protected int       id;
    protected String    descripcion;
    protected float     mtsPso;
    protected float     mts;
    protected float     precio;
    protected float     total;
    
    public MateriaPrimaVo(){
        this.id = -1;
        this.descripcion = "";
        this.mtsPso = 0.0f;
        this.mts = 0.0f;
        this.precio = 0.0f;
        this.total = 0.0f;
    }

    public MateriaPrimaVo(int id, String descripcion, float mtsPso, float mts, float precio, float total) {
        this.id = id;
        this.descripcion = descripcion;
        this.mtsPso = mtsPso;
        this.mts = mts;
        this.precio = precio;
        this.total = total;
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

    public float getMtsPso() {
        return mtsPso;
    }

    public void setMtsPso(float mtsPso) {
        this.mtsPso = mtsPso;
    }

    public float getMts() {
        return mts;
    }

    public void setMts(float mts) {
        this.mts = mts;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    
}
