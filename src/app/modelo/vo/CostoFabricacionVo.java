/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modelo.vo;

import java.util.ArrayList;

/**
 *
 * @author Hector
 */
public class CostoFabricacionVo {
    
    private int id;
    private String descripcion;
    private float monto;
    
    public CostoFabricacionVo(String descripcion, float monto){
       this.id = 0;
       this.descripcion = descripcion;
       this.monto       = monto;
    }
    
    public CostoFabricacionVo(int id, String descripcion, float monto){
       this.id = id;
       this.descripcion = descripcion;
       this.monto = monto;
    }

    public CostoFabricacionVo() {
        this.id = 0;
        this.descripcion = "";
        this.monto = 0;
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

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

}