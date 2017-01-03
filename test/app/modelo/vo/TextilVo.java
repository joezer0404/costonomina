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
public class TextilVo extends MateriaPrimaVo{

    public enum Medida {
        KG,
        MT
    }
    
    private Medida medida;
    private float  peso_mts;
    
    public TextilVo(int id, String descripcion, float mts_pso, float precio, Medida medida, float peso_mts){
        super(id, descripcion, mts_pso, precio);
        
        this.medida = medida;
        this.peso_mts = peso_mts;
        
        actualizarCalculo();
    }

    public Medida getMedida() {
        return medida;
    }

    public void setMedida(Medida medida) {
        this.medida = medida;
    }

    public float getPeso_mts() {
        return peso_mts;
    }

    public void setPeso_mts(float peso_mts) {
        this.peso_mts = peso_mts;
        actualizarCalculo();
    }
    
    @Override
    protected void actualizarCalculo() {
        this.mts = this.peso_mts * this.mts_pso;
        this.total = this.mts * this.precio;
    }
    
}
