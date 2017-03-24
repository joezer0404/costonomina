/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import app.vista.MainView;

/**
 * Clase desde se iniciara la aplicacion:
 * 
 * 1) Hara la configuracion incial
 * 2) Cargara el controlador con su vista inicial
 * 
 * @see MainView
 * @author Hector
 */
public class NominaApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainView v = new MainView();
        v.setLocationRelativeTo(null);
        v.setVisible(true);
    }
    
}
