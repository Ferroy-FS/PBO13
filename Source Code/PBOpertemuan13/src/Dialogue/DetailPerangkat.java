/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dialogue;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author LEGION
 */
@Entity
@Table(name = "detail_perangkat")
@NamedQueries({
    @NamedQuery(name = "DetailPerangkat.findAll", query = "SELECT d FROM DetailPerangkat d"),
    @NamedQuery(name = "DetailPerangkat.findByIdDetail", query = "SELECT d FROM DetailPerangkat d WHERE d.idDetail = :idDetail"),
    @NamedQuery(name = "DetailPerangkat.findByWarna", query = "SELECT d FROM DetailPerangkat d WHERE d.warna = :warna"),
    @NamedQuery(name = "DetailPerangkat.findByHarga", query = "SELECT d FROM DetailPerangkat d WHERE d.harga = :harga"),
    @NamedQuery(name = "DetailPerangkat.findByStok", query = "SELECT d FROM DetailPerangkat d WHERE d.stok = :stok")})
public class DetailPerangkat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detail")
    private Integer idDetail;
    
    @Basic(optional = false)
    @Column(name = "warna")
    private String warna;
    
    @Basic(optional = false)
    @Column(name = "harga")
    private long harga;
    
    @Basic(optional = false)
    @Column(name = "stok")
    private int stok;
    
    @JoinColumn(name = "nomor_seri", referencedColumnName = "nomor_seri")
    @ManyToOne(optional = false)
    private PerangkatElektronik perangkatElektronik;

    public DetailPerangkat() {
    }

    public DetailPerangkat(Integer idDetail) {
        this.idDetail = idDetail;
    }

    public DetailPerangkat(Integer idDetail, String warna, long harga, int stok) {
        this.idDetail = idDetail;
        this.warna = warna;
        this.harga = harga;
        this.stok = stok;
    }

    public Integer getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(Integer idDetail) {
        this.idDetail = idDetail;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public long getHarga() {
        return harga;
    }

    public void setHarga(long harga) {
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public PerangkatElektronik getPerangkatElektronik() {
        return perangkatElektronik;
    }

    public void setPerangkatElektronik(PerangkatElektronik perangkatElektronik) {
        this.perangkatElektronik = perangkatElektronik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetail != null ? idDetail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DetailPerangkat)) {
            return false;
        }
        DetailPerangkat other = (DetailPerangkat) object;
        if ((this.idDetail == null && other.idDetail != null) || (this.idDetail != null && !this.idDetail.equals(other.idDetail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dialogue.DetailPerangkat[ idDetail=" + idDetail + " ]";
    }
}