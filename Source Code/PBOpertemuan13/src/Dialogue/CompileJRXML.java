/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dialogue;

/**
 *
 * @author LEGION
 */
import net.sf.jasperreports.engine.JasperCompileManager;
import javax.swing.JOptionPane;

public class CompileJRXML {

    public static boolean compileLaporan() {
        try {

            String jrxmlPath = "src/reports/laporan_gabungan_perangkat.jrxml";

            String jasperPath = "laporan_gabungan_perangkat.jasper";

            System.out.println("=== COMPILE JRXML ===");
            System.out.println("Input: " + jrxmlPath);
            System.out.println("Output: " + jasperPath);

            java.io.File jrxmlFile = new java.io.File(jrxmlPath);
            if (!jrxmlFile.exists()) {
                System.out.println("ERROR: File " + jrxmlPath + " tidak ditemukan!");
                System.out.println("Working dir: " + new java.io.File(".").getAbsolutePath());
                return false;
            }

            System.out.println("File JRXML ditemukan");
            System.out.println("Memulai compile...");

            JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);

            java.io.File jasperFile = new java.io.File(jasperPath);
            if (jasperFile.exists()) {
                System.out.println("SUKSES: File " + jasperPath + " berhasil dibuat!");
                System.out.println("Size: " + jasperFile.length() + " bytes");
                return true;
            } else {
                System.out.println("ERROR: File JASPER tidak terbentuk setelah compile");
                return false;
            }

        } catch (Exception e) {
            System.out.println("ERROR COMPILE: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean fileJasperAda() {
        String jasperPath = "laporan_gabungan_perangkat.jasper";
        java.io.File jasperFile = new java.io.File(jasperPath);
        boolean ada = jasperFile.exists();

        if (ada) {
            System.out.println("File JASPER ditemukan: " + jasperPath);
        } else {
            System.out.println("File JASPER tidak ditemukan: " + jasperPath);
        }

        return ada;
    }

    public static boolean fileJrxmlAda() {
        String jrxmlPath = "src/reports/laporan_gabungan_perangkat.jrxml";
        java.io.File jrxmlFile = new java.io.File(jrxmlPath);
        boolean ada = jrxmlFile.exists();

        if (ada) {
            System.out.println("File JRXML ditemukan: " + jrxmlPath);
        } else {
            System.out.println("File JRXML tidak ditemukan: " + jrxmlPath);
        }

        return ada;
    }

    public static boolean compileLaporanWithDialog(java.awt.Component parent) {

        if (!fileJrxmlAda()) {
            JOptionPane.showMessageDialog(parent,
                    "File JRXML tidak ditemukan!\n\n"
                    + "Pastikan file ada di:\n"
                    + "src/reports/laporan_perangkat.jrxml",
                    "File Tidak Ditemukan",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        boolean success = compileLaporan();

        if (success) {
            JOptionPane.showMessageDialog(parent,
                    "Compile Berhasil!\n\n"
                    + "File laporan_perangkat.jasper telah dibuat di root project.",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(parent,
                    "Compile Gagal!\n\n"
                    + "Lihat console untuk detail error.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return success;
    }

    public static boolean compileLaporanGabungan() {
        try {
            String jrxmlPath = "src/reports/laporan_gabungan_perangkat.jrxml";
            String jasperPath = "laporan_gabungan_perangkat.jasper";

            System.out.println("=== COMPILE LAPORAN GABUNGAN ===");
            System.out.println("Input: " + jrxmlPath);
            System.out.println("Output: " + jasperPath);

            java.io.File jrxmlFile = new java.io.File(jrxmlPath);
            if (!jrxmlFile.exists()) {
                System.out.println("ERROR: File " + jrxmlPath + " tidak ditemukan!");
                return false;
            }

            JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);

            java.io.File jasperFile = new java.io.File(jasperPath);
            if (jasperFile.exists()) {
                System.out.println("SUKSES: File " + jasperPath + " berhasil dibuat!");
                return true;
            } else {
                System.out.println("ERROR: File JASPER tidak terbentuk setelah compile");
                return false;
            }

        } catch (Exception e) {
            System.out.println("ERROR COMPILE: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}