/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Dialogue;

import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

/**
 *
 * @author LEGION
 */
public class DetailDialog extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DetailDialog.class.getName());

    private javax.persistence.EntityManagerFactory emf;
    private javax.persistence.EntityManager em;
    private String operationType;
    private boolean confirmed = false;

    public DetailDialog() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setupDialogProperties();
        connectToDatabase();
    }

    public DetailDialog(String operation, Integer idDetail, String nomorSeri, String warna, String harga, String stok) {
        initComponents();
        setupDialogProperties();
        connectToDatabase();
        setOperation(operation);
        setFieldValues(idDetail, nomorSeri, warna, harga, stok);
    }

    private void setupDialogProperties() {
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void connectToDatabase() {
        try {
            emf = javax.persistence.Persistence.createEntityManagerFactory("PBOpertemuan6PU");
            em = emf.createEntityManager();
            System.out.println("Koneksi database berhasil dari DetailDialog (JPA)!");
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal terhubung ke database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setOperation(String operation) {
        this.operationType = operation;
        updateUIForOperation();
    }

    private void updateUIForOperation() {
        switch (operationType) {
            case "INSERT":
                setTitle("Tambah Detail Perangkat Baru");
                btnSubmit.setText("Simpan");
                txtIdDetail.setEnabled(false); // AUTO GENERATED
                txtNomorSeri.setEnabled(true);
                txtWarna.setEnabled(true);
                txtHarga.setEnabled(true);
                txtStok.setEnabled(true);
                break;
            case "UPDATE":
                setTitle("Update Detail Perangkat");
                btnSubmit.setText("Update");
                txtIdDetail.setEnabled(false); // TIDAK BISA DIUBAH
                txtNomorSeri.setEnabled(false); // ✅ TIDAK BISA DIUBAH UNTUK UPDATE
                txtWarna.setEnabled(true);
                txtHarga.setEnabled(true);
                txtStok.setEnabled(true);
                break;
            case "DELETE":
                setTitle("Hapus Detail Perangkat");
                btnSubmit.setText("Hapus");
                txtIdDetail.setEnabled(false); // ✅ TIDAK BISA DIUBAH (SUDAH BENAR)
                txtNomorSeri.setEnabled(true);
                txtWarna.setEnabled(false);
                txtHarga.setEnabled(false);
                txtStok.setEnabled(false);
                break;
        }
    }

    public void setFieldValues(Integer idDetail, String nomorSeri, String warna, String harga, String stok) {
        txtIdDetail.setText(idDetail != null ? idDetail.toString() : "");
        txtNomorSeri.setText(nomorSeri != null ? nomorSeri : "");
        txtWarna.setText(warna != null ? warna : "");
        txtHarga.setText(harga != null ? harga : "");
        txtStok.setText(stok != null ? stok : "");
    }

    public Integer getIdDetail() {
        String text = txtIdDetail.getText().trim();
        return text.isEmpty() ? null : Integer.parseInt(text);
    }

    public String getNomorSeri() {
        return txtNomorSeri.getText().trim();
    }

    public String getWarna() {
        return txtWarna.getText().trim();
    }

    public long getHarga() {
        try {
            return Long.parseLong(txtHarga.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public int getStok() {
        try {
            return Integer.parseInt(txtStok.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    private boolean validateInput() {
        if (getNomorSeri().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Nomor seri harus diisi!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!operationType.equals("DELETE")) {
            if (getWarna().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Warna harus diisi!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (getHarga() <= 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Harga harus lebih dari 0!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (getStok() < 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Stok tidak boleh negatif!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private boolean isNomorSeriExists(String nomorSeri) {
        try {
            String query = "SELECT COUNT(p) FROM PerangkatElektronik p WHERE p.nomorSeri = :nomorSeri";
            javax.persistence.TypedQuery<Long> typedQuery = em.createQuery(query, Long.class);
            typedQuery.setParameter("nomorSeri", nomorSeri);
            return typedQuery.getSingleResult() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isDetailExistsForPerangkat(String nomorSeri, String warna) {
        try {
            String query = "SELECT COUNT(d) FROM DetailPerangkat d WHERE d.perangkatElektronik.nomorSeri = :nomorSeri AND d.warna = :warna";
            javax.persistence.TypedQuery<Long> typedQuery = em.createQuery(query, Long.class);
            typedQuery.setParameter("nomorSeri", nomorSeri);
            typedQuery.setParameter("warna", warna);
            return typedQuery.getSingleResult() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isDetailExistsForUpdate(String nomorSeri, String warna, Integer excludeId) {
        try {
            String query = "SELECT COUNT(d) FROM DetailPerangkat d WHERE d.perangkatElektronik.nomorSeri = :nomorSeri AND d.warna = :warna AND d.idDetail != :excludeId";
            javax.persistence.TypedQuery<Long> typedQuery = em.createQuery(query, Long.class);
            typedQuery.setParameter("nomorSeri", nomorSeri);
            typedQuery.setParameter("warna", warna);
            typedQuery.setParameter("excludeId", excludeId);
            return typedQuery.getSingleResult() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean executeInsertDetail() {
        try {
            if (!isNomorSeriExists(getNomorSeri())) {
                JOptionPane.showMessageDialog(this,
                        "Nomor seri '" + getNomorSeri() + "' tidak ditemukan!\nSilakan masukkan nomor seri yang valid.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (isDetailExistsForPerangkat(getNomorSeri(), getWarna())) {
                JOptionPane.showMessageDialog(this,
                        "Detail dengan nomor seri '" + getNomorSeri() + "' dan warna '" + getWarna() + "' sudah ada!\n"
                        + "Silakan gunakan kombinasi nomor seri dan warna yang berbeda.",
                        "Data Duplikat",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }

            PerangkatElektronik perangkat = em.find(PerangkatElektronik.class, getNomorSeri());
            if (perangkat == null) {
                JOptionPane.showMessageDialog(this, "Perangkat tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            DetailPerangkat newDetail = new DetailPerangkat();
            newDetail.setWarna(getWarna());
            newDetail.setHarga(getHarga());
            newDetail.setStok(getStok());
            newDetail.setPerangkatElektronik(perangkat);

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            em.getTransaction().begin();
            em.persist(newDetail);
            em.getTransaction().commit();

            JOptionPane.showMessageDialog(this, "Detail perangkat berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(this, "Gagal menambahkan detail: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    private boolean executeUpdateDetail() {
        try {
            DetailPerangkat existingDetail = em.find(DetailPerangkat.class, getIdDetail());
            if (existingDetail == null) {
                JOptionPane.showMessageDialog(this, "Data detail tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            String originalNomorSeri = existingDetail.getPerangkatElektronik().getNomorSeri();
            if (!originalNomorSeri.equals(getNomorSeri())) {
                JOptionPane.showMessageDialog(this,
                        "Tidak boleh mengubah nomor seri!\nNomor seri asli: " + originalNomorSeri,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (isDetailExistsForUpdate(getNomorSeri(), getWarna(), getIdDetail())) {
                JOptionPane.showMessageDialog(this,
                        "Detail dengan warna '" + getWarna() + "' sudah ada untuk perangkat ini!\n"
                        + "Silakan gunakan warna yang berbeda.",
                        "Data Duplikat",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }

            PerangkatElektronik perangkat = em.find(PerangkatElektronik.class, getNomorSeri());
            if (perangkat == null) {
                JOptionPane.showMessageDialog(this, "Perangkat tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            existingDetail.setWarna(getWarna());
            existingDetail.setHarga(getHarga());
            existingDetail.setStok(getStok());
            existingDetail.setPerangkatElektronik(perangkat);

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            em.getTransaction().begin();
            em.merge(existingDetail);
            em.getTransaction().commit();

            JOptionPane.showMessageDialog(this, "Detail perangkat berhasil diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(this, "Gagal mengupdate detail: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    private boolean executeDeleteDetail() {
        try {
            if (getIdDetail() == null) {
                JOptionPane.showMessageDialog(this, "ID Detail harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            DetailPerangkat detailToDelete = em.find(DetailPerangkat.class, getIdDetail());
            if (detailToDelete == null) {
                JOptionPane.showMessageDialog(this,
                        "Data detail dengan ID " + getIdDetail() + " tidak ditemukan!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            String info = "Data yang akan dihapus:\n\n"
                    + "ID Detail: " + detailToDelete.getIdDetail() + "\n"
                    + "Nomor Seri: " + detailToDelete.getPerangkatElektronik().getNomorSeri() + "\n"
                    + "Nama Perangkat: " + detailToDelete.getPerangkatElektronik().getNamaPerangkat() + "\n"
                    + "Warna: " + detailToDelete.getWarna() + "\n"
                    + "Harga: " + detailToDelete.getHarga() + "\n"
                    + "Stok: " + detailToDelete.getStok();

            int confirm = JOptionPane.showConfirmDialog(this,
                    info,
                    "Konfirmasi Hapus Detail",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm != JOptionPane.YES_OPTION) {
                return false;
            }

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            em.getTransaction().begin();
            em.remove(detailToDelete);
            em.getTransaction().commit();

            JOptionPane.showMessageDialog(this,
                    "Detail perangkat berhasil dihapus!\n\n"
                    + "ID Detail: " + getIdDetail(),
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(this,
                    "Gagal menghapus detail: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    private String findNomorSeriByIdDetail(Integer idDetail) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String query = "SELECT d.perangkatElektronik.nomorSeri FROM DetailPerangkat d WHERE d.idDetail = :idDetail";
            javax.persistence.TypedQuery<String> typedQuery = em.createQuery(query, String.class);
            typedQuery.setParameter("idDetail", idDetail);
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
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
        txtIdDetail = new javax.swing.JTextField();
        txtNomorSeri = new javax.swing.JTextField();
        txtWarna = new javax.swing.JTextField();
        txtHarga = new javax.swing.JTextField();
        txtStok = new javax.swing.JTextField();
        btnSubmit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Id detail");

        jLabel2.setText("Nomor seri");

        jLabel3.setText("Warna");

        jLabel4.setText("Harga");

        jLabel5.setText("Stok");

        txtIdDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdDetailActionPerformed(evt);
            }
        });

        txtNomorSeri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomorSeriActionPerformed(evt);
            }
        });

        txtWarna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtWarnaActionPerformed(evt);
            }
        });

        txtHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaActionPerformed(evt);
            }
        });

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(97, 97, 97)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSubmit)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtIdDetail)
                        .addComponent(txtNomorSeri)
                        .addComponent(txtWarna)
                        .addComponent(txtHarga)
                        .addComponent(txtStok, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNomorSeri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtWarna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnSubmit)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomorSeriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomorSeriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomorSeriActionPerformed

    private void txtIdDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdDetailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdDetailActionPerformed

    private void txtWarnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtWarnaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtWarnaActionPerformed

    private void txtHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        if (!validateInput()) {
            return;
        }

        boolean success = false;

        switch (operationType) {
            case "INSERT":
                success = executeInsertDetail();
                break;
            case "UPDATE":
                success = executeUpdateDetail();
                break;
            case "DELETE":
                int result = javax.swing.JOptionPane.showConfirmDialog(
                        this,
                        "Apakah Anda yakin ingin menghapus detail perangkat dengan ID: " + getIdDetail() + "?",
                        "Konfirmasi Hapus",
                        javax.swing.JOptionPane.YES_NO_OPTION,
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );
                if (result == javax.swing.JOptionPane.YES_OPTION) {
                    success = executeDeleteDetail();
                } else {
                    return;
                }
                break;
            default:
                javax.swing.JOptionPane.showMessageDialog(this, "Operasi tidak valid!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
        }

        if (success) {
            confirmed = true;
            try {
                if (em != null && em.isOpen() && !em.getTransaction().isActive()) {
                    em.clear();
                }
            } catch (Exception e) {
                System.out.println("Info: Clear entity manager - " + e.getMessage());
            }
            dispose();
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtIdDetail;
    private javax.swing.JTextField txtNomorSeri;
    private javax.swing.JTextField txtStok;
    private javax.swing.JTextField txtWarna;
    // End of variables declaration//GEN-END:variables
}
