/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Login;

import javax.persistence.*;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author LEGION
 */
public class LupaPassword extends javax.swing.JFrame {

    private EntityManagerFactory emf;
    private EntityManager em;
    private String username;

    public LupaPassword(String username) {
        initComponents();
        connectToDatabase();
        centerFrame();
        addUsernameValidation();
        this.username = username;

        if (username != null) {
            // Tampilkan username yang sedang diubah (read-only)
            txtUsername.setText(username);
            txtUsername.setEditable(false); // Tidak bisa diubah
            jLabel3.setText("Username: " + username); // Ubah label
        } else {
            // Mode tanpa username tertentu
            txtUsername.setText("");
            txtUsername.setEditable(true);
            jLabel3.setText("Konfirmasi Username");
        }
    }

    private void connectToDatabase() {
        try {
            emf = Persistence.createEntityManagerFactory("PBOpertemuan6PU");
            em = emf.createEntityManager();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal terhubung ke database: " + e.getMessage());
        }
    }

    private void centerFrame() {
        setLocationRelativeTo(null);
    }

    private boolean isUsernameExists(String username) {
        try {
            String query = "SELECT COUNT(l) FROM LoginEntity l WHERE l.username = :username";
            TypedQuery<Long> typedQuery = em.createQuery(query, Long.class);
            typedQuery.setParameter("username", username);
            Long count = typedQuery.getSingleResult();
            return count > 0;
        } catch (Exception e) {
            System.out.println("Error cek username: " + e.getMessage());
            return false;
        }
    }

    private boolean updatePassword(String username, String newPassword) {
        try {
            LoginEntity user = em.find(LoginEntity.class, username);
            if (user == null) {
                JOptionPane.showMessageDialog(this,
                        "Error: User tidak ditemukan saat akan update password!\n"
                        + "Silakan coba lagi atau hubungi administrator.");
                return false;
            }

            // Cek jika password baru sama dengan password lama
            if (user.getPasswordnya().equals(newPassword)) {
                JOptionPane.showMessageDialog(this,
                        "Password baru tidak boleh sama dengan password lama!\n"
                        + "Silakan gunakan password yang berbeda.");
                return false;
            }

            em.getTransaction().begin();
            user.setPasswordnya(newPassword);
            em.getTransaction().commit();

            System.out.println("Password berhasil diupdate untuk user: " + username);
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(this,
                    "Error sistem saat mengubah password: " + e.getMessage() + "\n"
                    + "Silakan coba lagi.");
            e.printStackTrace();
            return false;
        }
    }

    private void addUsernameValidation() {
        txtUsername.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                checkUsername();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                checkUsername();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                checkUsername();
            }

            private void checkUsername() {
                String username = txtUsername.getText().trim();
                if (username.length() >= 3) {
                    SwingUtilities.invokeLater(() -> {
                        if (isUsernameExists(username)) {
                            txtUsername.setBackground(new java.awt.Color(200, 255, 200));
                        } else {
                            txtUsername.setBackground(new java.awt.Color(255, 200, 200));
                        }
                    });
                } else {
                    txtUsername.setBackground(java.awt.Color.WHITE);
                }
            }
        });
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
        txtPassword = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("Ubah Password");

        jLabel2.setText("Masukkan password baru");

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        jLabel3.setText("Konfirmasi Username");

        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        jButton1.setText("Enter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2)
                                .addComponent(txtPassword)
                                .addComponent(jLabel3)
                                .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)))))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(98, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String currentUsername = this.username != null ? this.username : txtUsername.getText().trim();
        String newPassword = txtPassword.getText().trim();

        if (currentUsername.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return;
        }

        // Jika username dari input field, validasi dulu
        if (this.username == null && !isUsernameExists(currentUsername)) {
            JOptionPane.showMessageDialog(this, "Username tidak ditemukan!");
            return;
        }

        // Validasi panjang password
        if (newPassword.length() < 3) {
            JOptionPane.showMessageDialog(this, "Password baru harus minimal 3 karakter!");
            return;
        }

        // Konfirmasi update password
        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin mengubah password untuk username: " + currentUsername + "?",
                "Konfirmasi Ubah Password",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (updatePassword(currentUsername, newPassword)) {
                JOptionPane.showMessageDialog(this,
                        "Password berhasil diubah!\n\n"
                        + "Username: " + currentUsername + "\n"
                        + "Password baru telah disimpan.\n\n"
                        + "Silakan login dengan password baru Anda.");

                // Kembali ke form login
                Login loginForm = new Login();
                loginForm.setVisible(true);
                this.dispose();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}