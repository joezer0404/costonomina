/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.vista.textil;

import app.vista.textil.*;
import app.conexion.IDbConnection;
import app.conexion.MySqlDbConnection;
import app.modelo.vo.MateriaPrimaTextilVo;
import app.modelo.dao.MateriaPrimaTextilDao;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Hector
 */
public class TextilFrameView extends javax.swing.JFrame {

    /**
     * 1- Usar TableModel adecuado
     */
    TextilTableModel tableModel;
    
    /**
     * Creates new form PersonalView
     * @param connection
     */
    public TextilFrameView(IDbConnection connection) {
        initComponents();
        // 2.1 - Usar Table model adecuado
        tableModel = new TextilTableModel(connection);
        tbBuscar.setModel(tableModel);
        
        bBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 2.3 - Adaptar dao 
                // Obtener datos del textfield
                
                //Revisar 
                
                String tipo = (String) cbBuscar.getSelectedItem();
                String dato = txBuscar.getText();                
                
                MateriaPrimaTextilDao TextilDao = new MateriaPrimaTextilDao();
                                                              
                HashMap<String, Object> options = new HashMap();
                
                if(!dato.isEmpty()){
                    options.put(tipo.toLowerCase(), dato);
                }
                
                // 2.4 - AdaptarVo
                List<MateriaPrimaTextilVo> materiatextil = TextilDao.getList(connection, options);
                tableModel.setTextil(materiatextil);
            }
        });
        
        bAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 2.5 - Adaptar EditView, Vo y Dao  
                TextilEditView editView = new TextilEditView(TextilFrameView.this, true, new MateriaPrimaTextilVo());
                editView.setVisible(true);
                if (editView.isOk()){
                    MateriaPrimaTextilVo l = editView.getTextil();
                    MateriaPrimaTextilDao TextilDao = new MateriaPrimaTextilDao();
                    boolean flag = TextilDao.insertRecord(connection, l, null);
                    if (flag){
                        tableModel.addRow(l);
                    }
                }
            }
        });
        
        bModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 2.6 Adaptar Vo, mensaje en caso de error, EditView y Dao
                MateriaPrimaTextilVo l = tableModel.getTextil(tbBuscar.getSelectedRow());
                if (l == null){
                    JOptionPane.showMessageDialog(TextilFrameView.this, "No se eligio ninguna descripcion.", "Eliminar Item", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                TextilEditView editView = new TextilEditView(TextilFrameView.this, true, l);
                editView.setVisible(true);
                if (editView.isOk()){
                    l = editView.getTextil();
                    MateriaPrimaTextilDao TextilDao = new MateriaPrimaTextilDao();
                    boolean flag = TextilDao.updateRecord(connection, l, null);
                    if (flag){
                        tableModel.updateRow(tbBuscar.getSelectedRow(),l);
                    }
                }
            }
        });
        
        bEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 2.7 Adaptar Vo, mensaje de error y Dao
                MateriaPrimaTextilVo l = tableModel.getTextil(tbBuscar.getSelectedRow());
                if (l == null){
                    JOptionPane.showMessageDialog(TextilFrameView.this, "No item elegido.", "Eliminar item", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                MateriaPrimaTextilDao TextilDao = new MateriaPrimaTextilDao();
                boolean flag = TextilDao.deleteRecord(connection, l, null);
                if (flag){
                    tableModel.deleteRow(tbBuscar.getSelectedRow());
                }
            }
        });
        
        bSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pMain = new javax.swing.JPanel();
        pBuscar = new javax.swing.JPanel();
        lBuscar = new javax.swing.JLabel();
        txBuscar = new javax.swing.JTextField();
        cbBuscar = new javax.swing.JComboBox();
        bBuscar = new javax.swing.JButton();
        pTable = new javax.swing.JPanel();
        spBuscar = new javax.swing.JScrollPane();
        tbBuscar = new javax.swing.JTable();
        pBotones = new javax.swing.JPanel();
        bModificar = new javax.swing.JButton();
        bSalir = new javax.swing.JButton();
        bEliminar = new javax.swing.JButton();
        bAgregar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gastos ventas");

        pBuscar.setBackground(java.awt.Color.lightGray);

        lBuscar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lBuscar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lBuscar.setText("Buscar");

        txBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txBuscarActionPerformed(evt);
            }
        });

        cbBuscar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Descripcion" }));

        bBuscar.setText("Buscar");

        javax.swing.GroupLayout pBuscarLayout = new javax.swing.GroupLayout(pBuscar);
        pBuscar.setLayout(pBuscarLayout);
        pBuscarLayout.setHorizontalGroup(
            pBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbBuscar, 0, 112, Short.MAX_VALUE)
                .addGap(116, 116, 116)
                .addComponent(bBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        pBuscarLayout.setVerticalGroup(
            pBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pBuscarLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbBuscar)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pBuscarLayout.createSequentialGroup()
                        .addGroup(pBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txBuscar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lBuscar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(bBuscar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );

        tbBuscar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbBuscarMousePressed(evt);
            }
        });
        spBuscar.setViewportView(tbBuscar);

        pBotones.setOpaque(false);

        bModificar.setText("Modificar");

        bSalir.setText("Salir");

        bEliminar.setText("Eliminar");

        bAgregar.setText("Agregar");

        javax.swing.GroupLayout pBotonesLayout = new javax.swing.GroupLayout(pBotones);
        pBotones.setLayout(pBotonesLayout);
        pBotonesLayout.setHorizontalGroup(
            pBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pBotonesLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(pBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );
        pBotonesLayout.setVerticalGroup(
            pBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pBotonesLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(bAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout pTableLayout = new javax.swing.GroupLayout(pTable);
        pTable.setLayout(pTableLayout);
        pTableLayout.setHorizontalGroup(
            pTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(pBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        pTableLayout.setVerticalGroup(
            pTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout pMainLayout = new javax.swing.GroupLayout(pMain);
        pMain.setLayout(pMainLayout);
        pMainLayout.setHorizontalGroup(
            pMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pMainLayout.setVerticalGroup(
            pMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMainLayout.createSequentialGroup()
                .addComponent(pBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txBuscarActionPerformed
        bBuscar.doClick();
    }//GEN-LAST:event_txBuscarActionPerformed

    private void tbBuscarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBuscarMousePressed
        JTable table =(JTable) evt.getSource();
        Point p = evt.getPoint();
        int row = table.rowAtPoint(p);
        if (evt.getClickCount() == 2) {
            bModificar.doClick();
        }
    }//GEN-LAST:event_tbBuscarMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAgregar;
    private javax.swing.JButton bBuscar;
    private javax.swing.JButton bEliminar;
    private javax.swing.JButton bModificar;
    private javax.swing.JButton bSalir;
    private javax.swing.JComboBox cbBuscar;
    private javax.swing.JLabel lBuscar;
    private javax.swing.JPanel pBotones;
    private javax.swing.JPanel pBuscar;
    private javax.swing.JPanel pMain;
    private javax.swing.JPanel pTable;
    private javax.swing.JScrollPane spBuscar;
    private javax.swing.JTable tbBuscar;
    private javax.swing.JTextField txBuscar;
    // End of variables declaration//GEN-END:variables

    // Adaptar FrameView
    public static void main(String[] args) {
        new TextilFrameView(new MySqlDbConnection()).setVisible(true);
    }
}
