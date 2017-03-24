/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modelo.vo;

import app.Configuraciones;

/**
 * Personal Visual Object:
 * 
 * Los valores que normalmente son cargados por el usuario desde la app
 * son cedula, nombre, apellido, cargo y salarioM (salario mensual).
 * 
 * Los demas atributos son calculados automaticamente por la base de datos,
 * sin embargo se incluira un metodo que permita calcular desde la app los
 * valores calculables llamado "actualizarCalculo".
 * 
 * - Agregar en configuraciones variables
 * - Remover set de los valores calculados automaticamente
 * 
 * @see NominaVo
 * @see PersonalDao
 * @see Configuraciones
 * @author Hector
 */
public class PersonalVo {
    
    public enum Cargo {
        ADM,
        MOD
    };
    
    // Atributos agregados por el usuario
    private int    cedula;
    private String nombre;
    private String apellido;
    private Cargo cargo;
    private float salarioM;
    // Atributos calculados por la base de datos / la aplicacion
    private float salarioS;
    private float ivss4;
    private float ivss5;
    private float inces;
    private float lph;
    private float prestaciones;
    private float utilidades;
    private float cestaticket;

    // Constructor default
    public PersonalVo(){
        this.cedula = 0;
        this.nombre = "";
        this.apellido = "";
        this.cargo = Cargo.ADM;
        this.salarioM = 0;
        actualizarCalculos();
    }
    
    // Constructor personalizado por el usuario
    public PersonalVo(int cedula, String nombre, String apellido, Cargo cargo, float salarioM){
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cargo = cargo;
        this.salarioM = salarioM;
        actualizarCalculos();
    }
    
    // Constructor para la base de datos
    public PersonalVo(int cedula, String nombre, String apellido, Cargo cargo, float salarioM, float salarioS, float ivss4, float ivss5, float inces, float lph, float prestaciones, float utilidades, float cestaticket) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cargo = cargo;
        this.salarioM = salarioM;
        this.salarioS = salarioS;
        this.ivss4 = ivss4;
        this.ivss5 = ivss5;
        this.inces = inces;
        this.lph = lph;
        this.prestaciones = prestaciones;
        this.utilidades = utilidades;
        this.cestaticket = cestaticket;
    }

    // Getters y Setters
    
    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public void setCargo(int cargo){
        this.cargo = (cargo == 0)? Cargo.ADM : Cargo.MOD;
    }
    
    public float getSalarioM() {
        return salarioM;
    }

    public void setSalarioM(float salarioM) {
        this.salarioM = salarioM;
        this.actualizarCalculos();
    }

    public float getSalarioS() {
        return salarioS;
    }

    public void setSalarioS(float salarioS) {
        this.salarioS = salarioS;
    }

    public float getIvss4() {
        return ivss4;
    }

    public void setIvss4(float ivss4) {
        this.ivss4 = ivss4;
    }

    public float getIvss5() {
        return ivss5;
    }

    public void setIvss5(float ivss5) {
        this.ivss5 = ivss5;
    }

    public float getInces() {
        return inces;
    }

    public void setInces(float inces) {
        this.inces = inces;
    }

    public float getLph() {
        return lph;
    }

    public void setLph(float lph) {
        this.lph = lph;
    }

    public float getPrestaciones() {
        return prestaciones;
    }

    public void setPrestaciones(float prestaciones) {
        this.prestaciones = prestaciones;
    }

    public float getUtilidades() {
        return utilidades;
    }

    public void setUtilidades(float utilidades) {
        this.utilidades = utilidades;
    }

    public float getCestaticket() {
        return cestaticket;
    }

    public void setCestaticket(float cestaticket) {
        this.cestaticket = cestaticket;
    }
    
    /**
     * Actualiza los valores que se deben calcular en funcion
     * del salario mensual.
     * @deprecated La logica se implemento en la base de datos
     */
    private void actualizarCalculos(){
        salarioS     = (salarioM / (30 * 7));
        ivss4        = (float) (((salarioM * 12) / 52) * 0.12 * 4);
        ivss5        = (float) (((salarioM * 12) / 52) * 0.12 * 5);
	inces        = (float) (salarioM * 0.02);
        lph          = (float) (salarioM * 0.02);
	prestaciones = (float) (salarioM * 0.1825);
        utilidades   = (float) (salarioM * 0.925);
	cestaticket  = Configuraciones.CESTATICKET;
    }
}