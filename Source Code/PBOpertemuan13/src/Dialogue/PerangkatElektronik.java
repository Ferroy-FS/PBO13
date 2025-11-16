/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dialogue;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author LEGION
 */
@Entity
@Table(name = "perangkat_elektronik")
@NamedQueries({
    @NamedQuery(name = "PerangkatElektronik.findAll", query = "SELECT p FROM PerangkatElektronik p"),
    @NamedQuery(name = "PerangkatElektronik.findByNomorSeri", query = "SELECT p FROM PerangkatElektronik p WHERE p.nomorSeri = :nomorSeri"),
    @NamedQuery(name = "PerangkatElektronik.findByJenisPerangkat", query = "SELECT p FROM PerangkatElektronik p WHERE p.jenisPerangkat = :jenisPerangkat"),
    @NamedQuery(name = "PerangkatElektronik.findByMerekPerangkat", query = "SELECT p FROM PerangkatElektronik p WHERE p.merekPerangkat = :merekPerangkat"),
    @NamedQuery(name = "PerangkatElektronik.findByNamaPerangkat", query = "SELECT p FROM PerangkatElektronik p WHERE p.namaPerangkat = :namaPerangkat"),
    @NamedQuery(name = "PerangkatElektronik.findByModelPerangkat", query = "SELECT p FROM PerangkatElektronik p WHERE p.modelPerangkat = :modelPerangkat")})
public class PerangkatElektronik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nomor_seri")
    private String nomorSeri;
    
    @Basic(optional = false)
    @Column(name = "jenis_perangkat")
    private String jenisPerangkat;
    
    @Basic(optional = false)
    @Column(name = "merek_perangkat")
    private String merekPerangkat;
    
    @Basic(optional = false)
    @Column(name = "nama_perangkat")
    private String namaPerangkat;
    
    @Column(name = "model_perangkat")
    private String modelPerangkat;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perangkatElektronik")
    private List<DetailPerangkat> detailPerangkatList;

    public PerangkatElektronik() {
    }

    public PerangkatElektronik(String nomorSeri) {
        this.nomorSeri = nomorSeri;
    }

    public PerangkatElektronik(String nomorSeri, String jenisPerangkat, String merekPerangkat, String namaPerangkat) {
        this.nomorSeri = nomorSeri;
        this.jenisPerangkat = jenisPerangkat;
        this.merekPerangkat = merekPerangkat;
        this.namaPerangkat = namaPerangkat;
    }

    public String getNomorSeri() {
        return nomorSeri;
    }

    public void setNomorSeri(String nomorSeri) {
        this.nomorSeri = nomorSeri;
    }

    public String getJenisPerangkat() {
        return jenisPerangkat;
    }

    public void setJenisPerangkat(String jenisPerangkat) {
        this.jenisPerangkat = jenisPerangkat;
    }

    public String getMerekPerangkat() {
        return merekPerangkat;
    }

    public void setMerekPerangkat(String merekPerangkat) {
        this.merekPerangkat = merekPerangkat;
    }

    public String getNamaPerangkat() {
        return namaPerangkat;
    }

    public void setNamaPerangkat(String namaPerangkat) {
        this.namaPerangkat = namaPerangkat;
    }

    public String getModelPerangkat() {
        return modelPerangkat;
    }

    public void setModelPerangkat(String modelPerangkat) {
        this.modelPerangkat = modelPerangkat;
    }

    public List<DetailPerangkat> getDetailPerangkatList() {
        return detailPerangkatList;
    }

    public void setDetailPerangkatList(List<DetailPerangkat> detailPerangkatList) {
        this.detailPerangkatList = detailPerangkatList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nomorSeri != null ? nomorSeri.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PerangkatElektronik)) {
            return false;
        }
        PerangkatElektronik other = (PerangkatElektronik) object;
        if ((this.nomorSeri == null && other.nomorSeri != null) || (this.nomorSeri != null && !this.nomorSeri.equals(other.nomorSeri))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dialogue.PerangkatElektronik[ nomorSeri=" + nomorSeri + " ]";
    }
}