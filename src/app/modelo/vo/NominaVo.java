/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.modelo.vo;

import app.Configuraciones;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Nomina visual object
 * 
 * Los valores que normalmente son cargados por el usuario desde la app
 * son id, fecha, el nombre de la empresa, el rif y la direccion
 * 
 * Los demas atributos son calculados automaticamente por la base de datos,
 * sin embargo se incluira un metodo que permita calcular desde la app los
 * valores calculables llamado "actualizarCalculo".
 * 
 * - Agregar opcion que consulte al usuario cual ivss utilizar para calcular el
 * monto y realizar logica de la funcion "obtenerLunes".
 * 
 * (LISTO) - Llamar al metodo "actualizarCalculo" del empleado antes de insertarlo en la
 * nomina (lista empleados).
 * 
 * @author Hector
 */
public class NominaVo {
    // Cargados por el usuario
    private int    id;
    private Date   fecha;
    private String empresa;
    private String rif;
    private String direccion;
    // Cargados por la base de datos
    private float salarioM;
    private float salarioS;
    private float ivss4;
    private float ivss5;
    private float inces;
    private float lph;
    private float prestaciones;
    private float utilidades;
    private float cestaticket;
    private float total;
    
    private ArrayList<PersonalVo> empleados;
    
    // Constructor personalizado
    public NominaVo(int id, Date fecha, String empresa, String rif, String direccion) {
        this.empleados = new ArrayList();
        this.id = id;
        this.fecha = fecha;
        this.empresa = empresa;
        this.rif = rif;
        this.direccion = direccion;
        this.empleados = new ArrayList();
        this.limpiarLista();
    }

    // Constructor default
    public NominaVo() {
        this.id = 0;
        this.fecha = new Date();
        this.empresa = Configuraciones.EMPRESA;
        this.rif = Configuraciones.RIF;
        this.direccion = Configuraciones.DIRECCION;
        this.empleados = new ArrayList();
        this.limpiarLista();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.sql.Date getFecha() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = formatter.parse(formatter.format(fecha));
            java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
            
            return sqlDate;
        } catch (ParseException ex) {
            Logger.getLogger(NominaVo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getRif() {
        return rif;
    }

    public void setRif(String rif) {
        this.rif = rif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public ArrayList<PersonalVo> getPersonal(){
        return empleados;
    }
    
    public void setPersonal(ArrayList<PersonalVo> empleados){
        this.empleados = empleados;
        this.actualizarCalculo();
    }

    public float getSalarioM() {
        return salarioM;
    }
    
    public float getSalarioS() {
        return salarioS;
    }

    public float getIvss4() {
        return ivss4;
    }

    public float getIvss5() {
        return ivss5;
    }

    public float getInces() {
        return inces;
    }

    public float getLph() {
        return lph;
    }

    public float getPrestaciones() {
        return prestaciones;
    }

    public float getUtilidades() {
        return utilidades;
    }

    public float getCestaticket() {
        return cestaticket;
    }

    public float getTotal() {
        return total;
    }
    
    /**
     * Agrega un empleado a la nomina
     * @param e Empleado a agregar
     * @return Si fue exitoso la agregacion
     */
    public boolean addEmpleado(PersonalVo e){
        if(!this.existEmpleado(e.getCedula())){
            empleados.add(e);
            actualizarCalculo();
            
            return true;
        }
        
        return false;
    }
    
    /**
     * Elimina un empleado de la nomina
     * @param cedula Cedula del empleado a eliminar
     * @return Si se pudo eliminar el empleado exitosamente
     */
    public boolean removeEmpleado(int cedula){
        for(PersonalVo e : empleados){
            if(cedula == e.getCedula()){
                empleados.remove(e);
                this.actualizarCalculo();
                
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Valida si existe el empleado en la nomina
     * @param cedula
     * @return 
     */
    private boolean existEmpleado(int cedula){
        for(PersonalVo e: empleados){
            if(e.getCedula() == cedula){
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Elimina todos los empleados de la nomina
     * y reinicia los valores de esta.
     */
    public final void limpiarLista() {
        empleados.clear();
        this.salarioM = 0;
        this.salarioS = 0;
        this.ivss4 = 0;
        this.ivss5 = 0;
        this.inces = 0;
        this.lph = 0;
        this.prestaciones = 0;
        this.utilidades = 0;
        this.cestaticket = 0;
        this.total = 0;
    }
    
    /**
     * Calcula los valores de la nomina en funcion de sus empleados
     */
    private void actualizarCalculo() {
        for(PersonalVo empleado: empleados){
            this.salarioM += empleado.getSalarioM();
            this.salarioS += empleado.getSalarioS();
            this.ivss4 += empleado.getIvss4();
            this.ivss5 += empleado.getIvss5();
            this.inces += empleado.getInces();
            this.lph   += empleado.getLph();
            this.prestaciones += empleado.getPrestaciones();
            this.utilidades   += empleado.getUtilidades();
            this.cestaticket  += empleado.getCestaticket();
            
            this.total = (this.obtenerLunes(fecha) == 4? ivss4 : ivss5) + inces 
                    + lph + prestaciones + utilidades;
        }
    }
    
    /**
     * Determina cuantos lunes tiene la fecha registrada
     * @return Cantidad de lunes que posee el mes de la fecha registrada
     */
    public int obtenerLunes(Date fecha) {
       
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);

        Calendar calendar = Calendar.getInstance();
        // Note that month is 0-based in calendar, bizarrely.
        calendar.set(year, month - 1, 1);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        int count = 0;
        for (int day = 1; day <= daysInMonth; day++) {
            calendar.set(year, month - 1, day);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.MONDAY) {
                count++;
                // Or do whatever you need to with the result.
            }
        }
        return count;
    }
}
