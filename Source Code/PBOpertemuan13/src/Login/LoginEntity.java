/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author LEGION
 */
@Entity
@Table(name = "login")
@NamedQueries({
    @NamedQuery(name = "LoginEntity.findAll", query = "SELECT l FROM LoginEntity l"),
    @NamedQuery(name = "LoginEntity.findByUsername", query = "SELECT l FROM LoginEntity l WHERE l.username = :username"),
    @NamedQuery(name = "LoginEntity.findByUsernameAndPassword", query = "SELECT l FROM LoginEntity l WHERE l.username = :username AND l.passwordnya = :password")
})
public class LoginEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "passwordnya", length = 100)
    private String passwordnya;

    public LoginEntity() {
    }

    public LoginEntity(String username, String passwordnya) {
        this.username = username;
        this.passwordnya = passwordnya;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordnya() {
        return passwordnya;
    }

    public void setPasswordnya(String passwordnya) {
        this.passwordnya = passwordnya;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LoginEntity)) {
            return false;
        }
        LoginEntity other = (LoginEntity) object;
        return !((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username)));
    }

    @Override
    public String toString() {
        return "Login.LoginEntity[ username=" + username + " ]";
    }
}
