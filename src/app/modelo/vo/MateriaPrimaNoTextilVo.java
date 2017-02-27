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
public class MateriaPrimaNoTextilVo extends MateriaPrimaVo{
    protected float peso;

    public MateriaPrimaNoTextilVo() {
        super();
        peso = 0;
    }

    public MateriaPrimaNoTextilVo(float peso, int id, String descripcion, float mtsPso, float mts, float precio, float total) {
        super(id, descripcion, mtsPso, mts, precio, total);
        this.peso = peso;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }
}
