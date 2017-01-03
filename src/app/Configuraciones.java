/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 * Clase que contendra los valores globales a utilizar 
 * en la aplicacion, varian entre constantes de mano de obra
 * costos, etc.
 * 
 * - Base de datos
 * - Constantes de calculo
 * 
 * 
 * @author Hector
 */
public class Configuraciones {
    // Base de datos
    public static final String DB_URL  = "jdbc:mysql://localhost/";
    public static final String DB_NAME = "SISTEMA";
    public static final String DB_USER = "root";
    public static final String DB_PASS = "";
    // Constantes de calculo
    public static float CESTATICKET = 6750;
    // Constantes de Nomina
    public static final String EMPRESA   = "FABRICA TEXTIL CONDOR C.A";
    public static final String RIF       = "J-0000000";
    public static final String DIRECCION = "Av. XXXXX Dir. XXXXXX";
    
    
}
