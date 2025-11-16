/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Dialogue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;

/**
 *
 * @author LEGION
 */
public class Dialog extends javax.swing.JFrame {

    private EntityManagerFactory emf;
    private EntityManager em;
    private String operationType;
    private boolean confirmed = false;

    public Dialog() {
        initComponents();
        setupDialogProperties();
        initWindowListener();
        connectToDatabase();
    }

    public Dialog(String operation, String nomorSeri, String jenis, String merek, String nama, String model) {
        initComponents();
        setupDialogProperties();
        initWindowListener();
        connectToDatabase();
        setOperation(operation);
        setFieldValues(nomorSeri, jenis, merek, nama, model);
    }

    public Dialog(String operation, String nomorSeri) {
        this(operation, nomorSeri, null, null, null, null);
    }

    private void connectToDatabase() {
        try {
            emf = Persistence.createEntityManagerFactory("PBOpertemuan6PU");
            em = emf.createEntityManager();
            System.out.println("Koneksi database berhasil dari Dialog (JPA)!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal terhubung ke database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupDialogProperties() {
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void initWindowListener() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                closeConnection();
            }
        });
    }

    public void setOperation(String operation) {
        this.operationType = operation;
        updateUIForOperation();
    }

    private void updateUIForOperation() {
        switch (operationType) {
            case "INSERT":
                setTitle("Tambah Data Baru");
                jToggleButton1.setText("Simpan");
                break;
            case "UPDATE":
                setTitle("Update Data");
                jToggleButton1.setText("Update");
                jTextField1.setEditable(false);
                break;
            case "DELETE":
                setTitle("Hapus Data");
                jToggleButton1.setText("Hapus");
                jTextField1.setEditable(false);
                jTextField2.setEditable(false);
                jTextField3.setEditable(false);
                jTextField4.setEditable(false);
                jTextField5.setEditable(false);
                break;
        }
    }

    public void setFieldValues(String nomorSeri, String jenis, String merek, String nama, String model) {
        jTextField1.setText(nomorSeri != null ? nomorSeri : "");
        jTextField2.setText(jenis != null ? jenis : "");
        jTextField3.setText(merek != null ? merek : "");
        jTextField4.setText(nama != null ? nama : "");
        jTextField5.setText(model != null ? model : "");
    }

    private boolean isNomorSeriExists(String nomorSeri) {
        try {
            String query = "SELECT COUNT(p) FROM PerangkatElektronik p WHERE p.nomorSeri = :nomorSeri";
            TypedQuery<Long> typedQuery = em.createQuery(query, Long.class);
            typedQuery.setParameter("nomorSeri", nomorSeri);
            return typedQuery.getSingleResult() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isPerangkatDuplicate(String merek, String nama, String model) {
        try {
            String query = "SELECT COUNT(p) FROM PerangkatElektronik p WHERE "
                    + "p.merekPerangkat = :merek AND p.namaPerangkat = :nama AND p.modelPerangkat = :model";
            TypedQuery<Long> typedQuery = em.createQuery(query, Long.class);
            typedQuery.setParameter("merek", merek);
            typedQuery.setParameter("nama", nama);
            typedQuery.setParameter("model", model);
            return typedQuery.getSingleResult() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isPerangkatDuplicateForUpdate(String merek, String nama, String model, String currentNomorSeri) {
        try {
            String query = "SELECT COUNT(p) FROM PerangkatElektronik p WHERE "
                    + "p.merekPerangkat = :merek AND p.namaPerangkat = :nama AND p.modelPerangkat = :model "
                    + "AND p.nomorSeri != :currentNomorSeri";
            TypedQuery<Long> typedQuery = em.createQuery(query, Long.class);
            typedQuery.setParameter("merek", merek);
            typedQuery.setParameter("nama", nama);
            typedQuery.setParameter("model", model);
            typedQuery.setParameter("currentNomorSeri", currentNomorSeri);
            return typedQuery.getSingleResult() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean executeInsert() {
        try {
            if (isNomorSeriExists(getNomorSeri())) {
                JOptionPane.showMessageDialog(this, "Nomor seri sudah ada dalam database!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (isPerangkatDuplicate(getMerekPerangkat(), getNamaPerangkat(), getModelPerangkat())) {
                JOptionPane.showMessageDialog(this,
                        "Perangkat dengan spesifikasi ini sudah ada!\n\n"
                        + "Merek: " + getMerekPerangkat() + "\n"
                        + "Nama: " + getNamaPerangkat() + "\n"
                        + "Model: " + getModelPerangkat() + "\n\n"
                        + "Silakan gunakan data yang berbeda.",
                        "Data Duplikat", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            PerangkatElektronik newData = new PerangkatElektronik(); 
            newData.setNomorSeri(getNomorSeri());
            newData.setJenisPerangkat(getJenisPerangkat());
            newData.setMerekPerangkat(getMerekPerangkat());
            newData.setNamaPerangkat(getNamaPerangkat());
            newData.setModelPerangkat(getModelPerangkat());

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            em.getTransaction().begin();
            em.persist(newData);
            em.getTransaction().commit();

            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(this, "Gagal menambahkan data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    private boolean executeUpdate() {
        try {
            PerangkatElektronik existingData = em.find(PerangkatElektronik.class, getNomorSeri()); // GANTI DI SINI
            if (existingData == null) {
                JOptionPane.showMessageDialog(this, "Data tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            String oldModel = existingData.getModelPerangkat();
            boolean modelChanged = !getModelPerangkat().equals(oldModel);

            if (modelChanged && isPerangkatDuplicateForUpdate(getMerekPerangkat(), getNamaPerangkat(), getModelPerangkat(), getNomorSeri())) {
                JOptionPane.showMessageDialog(this,
                        "Model perangkat '" + getModelPerangkat() + "' sudah digunakan!\n\n"
                        + "Perangkat dengan model ini sudah ada dalam database.\n"
                        + "Silakan gunakan model yang berbeda.",
                        "Model Duplikat", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            if (isPerangkatDuplicateForUpdate(getMerekPerangkat(), getNamaPerangkat(), getModelPerangkat(), getNomorSeri())) {
                JOptionPane.showMessageDialog(this,
                        "Perangkat dengan spesifikasi ini sudah ada!\n\n"
                        + "Merek: " + getMerekPerangkat() + "\n"
                        + "Nama: " + getNamaPerangkat() + "\n"
                        + "Model: " + getModelPerangkat() + "\n\n"
                        + "Silakan gunakan data yang berbeda.",
                        "Data Duplikat", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            existingData.setJenisPerangkat(getJenisPerangkat());
            existingData.setMerekPerangkat(getMerekPerangkat());
            existingData.setNamaPerangkat(getNamaPerangkat());
            existingData.setModelPerangkat(getModelPerangkat());

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            em.getTransaction().begin();
            em.merge(existingData);
            em.getTransaction().commit();

            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(this, "Gagal mengupdate data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    private boolean executeDelete() {
        try {
            if (!isNomorSeriExists(getNomorSeri())) {
                JOptionPane.showMessageDialog(this, "Data dengan nomor seri tersebut tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            PerangkatElektronik dataToDelete = em.find(PerangkatElektronik.class, getNomorSeri()); // GANTI DI SINI

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            em.getTransaction().begin();
            em.remove(dataToDelete);
            em.getTransaction().commit();

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    public void closeConnection() {
        try {
            if (em != null && em.isOpen() && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean confirmDelete() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin menghapus data dengan nomor seri: " + getNomorSeri() + "?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }

    public void clearForm() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        confirmed = false;
    }

    private boolean validateInput() {
        if (getNomorSeri().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nomor seri harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!operationType.equals("DELETE")) {
            if (getJenisPerangkat().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Jenis perangkat harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (getMerekPerangkat().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Merek perangkat harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (getNamaPerangkat().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama perangkat harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (getModelPerangkat().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Model perangkat harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    public String getNomorSeri() {
        return jTextField1.getText().trim();
    }

    public String getJenisPerangkat() {
        return jTextField2.getText().trim();
    }

    public String getMerekPerangkat() {
        return jTextField3.getText().trim();
    }

    public String getNamaPerangkat() {
        return jTextField4.getText().trim();
    }

    public String getModelPerangkat() {
        return jTextField5.getText().trim();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 153, 110));

        jLabel1.setText("Masukkan nomer seri");

        jLabel2.setText("Masukkan jenis perangkat");

        jLabel3.setText("Masukkan merek perangkat");

        jLabel4.setText("Masukkan nama perangkat");

        jLabel5.setText("Masukkan model perangkat");

        jToggleButton1.setText("Enter");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField1)
                        .addComponent(jTextField2)
                        .addComponent(jTextField3)
                        .addComponent(jTextField4)
                        .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jToggleButton1)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        if (!validateInput()) {
            return;
        }

        boolean success = false;

        switch (operationType) {
            case "INSERT":
                success = executeInsert();
                break;
            case "UPDATE":
                success = executeUpdate();
                break;
            case "DELETE":
                if (confirmDelete()) {
                    success = executeDelete();
                } else {
                    return;
                }
                break;
            default:
                JOptionPane.showMessageDialog(this, "Operasi tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        }

        if (success) {
            confirmed = true;

            // PERBAIKAN: Hanya clear tanpa flush, dan pastikan tidak dalam transaction
            try {
                if (em != null && em.isOpen() && !em.getTransaction().isActive()) {
                    em.clear(); // Hanya clear, tidak perlu flush
                }
            } catch (Exception e) {
                System.out.println("Info: Clear entity manager - " + e.getMessage());
            }

            dispose();
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        closeConnection();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
}
