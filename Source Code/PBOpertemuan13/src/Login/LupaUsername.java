/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Login;

import javax.persistence.*;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author LEGION
 */
public class LupaUsername extends javax.swing.JFrame {

    private EntityManagerFactory emf;
    private EntityManager em;

    /**
     * Creates new form LupaUsername
     */
    public LupaUsername() {
        initComponents();
        connectToDatabase();
        centerFrame();
    }

    private void testDatabaseConnection() {
        try {
            String query = "SELECT COUNT(l) FROM LoginEntity l";
            TypedQuery<Long> typedQuery = em.createQuery(query, Long.class);
            Long count = typedQuery.getSingleResult();
            System.out.println("Koneksi database berhasil! Total user: " + count);
        } catch (Exception e) {
            System.out.println("Error test connection: " + e.getMessage());
        }
    }

    private void connectToDatabase() {
        try {
            emf = Persistence.createEntityManagerFactory("PBOpertemuan6PU");
            em = emf.createEntityManager();
            System.out.println("Koneksi database berhasil di LupaUsername!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal terhubung ke database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void centerFrame() {
        setLocationRelativeTo(null);
    }

    private String findUsernameByPassword(String password) {
        try {
            String query = "SELECT l.username FROM LoginEntity l WHERE l.passwordnya = :password";
            TypedQuery<String> typedQuery = em.createQuery(query, String.class);
            typedQuery.setParameter("password", password);

            List<String> results = typedQuery.getResultList(); // GUNAKAN List<String>
            if (!results.isEmpty()) {
                return results.get(0); // Return username pertama yang ditemukan
            }
            return null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saat mencari username: " + e.getMessage());
            return null;
        }
    }

    private List<String> searchUsernamesByKeyword(String keyword) { // GUNAKAN List<String>
        try {
            String query = "SELECT l.username FROM LoginEntity l WHERE LOWER(l.username) LIKE LOWER(:keyword)";
            TypedQuery<String> typedQuery = em.createQuery(query, String.class);
            typedQuery.setParameter("keyword", "%" + keyword + "%");

            return typedQuery.getResultList();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saat mencari username: " + e.getMessage());
            return new ArrayList<>(); // GUNAKAN ArrayList<>()
        }
    }

    private boolean validateCredentials(String username, String password) {
        try {
            String query = "SELECT COUNT(l) FROM LoginEntity l WHERE l.username = :username AND l.passwordnya = :password";
            TypedQuery<Long> typedQuery = em.createQuery(query, Long.class);
            typedQuery.setParameter("username", username);
            typedQuery.setParameter("password", password);

            return typedQuery.getSingleResult() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saat validasi: " + e.getMessage());
            return false;
        }
    }

    private void showUsernameSelection(List<String> usernames, String password) { // GUNAKAN List<String>
        // Buat dialog pemilihan username
        String[] options = usernames.toArray(new String[0]);

        String selectedUsername = (String) JOptionPane.showInputDialog(
                this,
                "Ditemukan " + usernames.size() + " username:\nPilih username Anda:",
                "Pilih Username",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (selectedUsername != null) {
            // Validasi password untuk username yang dipilih
            if (validateCredentials(selectedUsername, password)) {
                JOptionPane.showMessageDialog(this,
                        "Login berhasil! Username: " + selectedUsername);

                // Buka form utama
                openMainApplication();
            } else {
                JOptionPane.showMessageDialog(this, "Password salah untuk username: " + selectedUsername);
            }
        }
    }

    private void openMainApplication() {
        Dialogue.PenjualanPerangkatElektronik mainForm = new Dialogue.PenjualanPerangkatElektronik();
        mainForm.setVisible(true);
        this.dispose();
    }

    @Override
    public void dispose() {
        try {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.dispose();
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
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnEnter = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("Lupa Username");

        jLabel2.setText("Masukkan kata kunci username");

        jLabel3.setText("Konfirmasi Password");

        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        jLabel4.setText("Lupa Password? Klik disini");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        btnEnter.setText("Enter");
        btnEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEnter)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3)
                        .addComponent(txtUsername)
                        .addComponent(jLabel2)
                        .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(95, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(74, 74, 74))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(37, 37, 37))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnEnter)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void btnEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnterActionPerformed
        // TODO add your handling code here:
        String keyword = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (keyword.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kata kunci dan password harus diisi!");
            return;
        }

        // Cari username yang mengandung kata kunci
        List<String> foundUsernames = searchUsernamesByKeyword(keyword); // GUNAKAN List<String>

        if (foundUsernames.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Tidak ditemukan username yang mengandung: '" + keyword + "'");
            return;
        }

        // Jika hanya ditemukan 1 username langsung validasi
        if (foundUsernames.size() == 1) {
            String username = foundUsernames.get(0);
            if (validateCredentials(username, password)) {
                JOptionPane.showMessageDialog(this,
                        "Username Anda adalah: " + username + "\nLogin berhasil!");

                // Buka form utama
                openMainApplication();
            } else {
                JOptionPane.showMessageDialog(this, "Password salah!");
            }
        } // Jika ditemukan multiple usernames, tampilkan pilihan
        else {
            showUsernameSelection(foundUsernames, password);
        }
    }//GEN-LAST:event_btnEnterActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        LupaPassword lupaPasswordForm = new LupaPassword(null);
        lupaPasswordForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel4MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
