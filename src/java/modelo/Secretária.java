/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mahomed
 */
@Entity
@Table(name = "Secret\u00e1ria", catalog = "ProGym", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Secret\u00e1ria.findAll", query = "SELECT s FROM Secret\u00e1ria s"),
    @NamedQuery(name = "Secret\u00e1ria.findBySecret\u00e1riaID", query = "SELECT s FROM Secret\u00e1ria s WHERE s.secret\u00e1riaID = :secret\u00e1riaID"),
    @NamedQuery(name = "Secret\u00e1ria.findByEmail", query = "SELECT s FROM Secret\u00e1ria s WHERE s.email = :email"),
    @NamedQuery(name = "Secret\u00e1ria.findBySenha", query = "SELECT s FROM Secret\u00e1ria s WHERE s.senha = :senha"),
    @NamedQuery(name = "Secret\u00e1ria.findByNome", query = "SELECT s FROM Secret\u00e1ria s WHERE s.nome = :nome")})
public class Secretária implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Secret\u00e1riaID", nullable = false)
    private Integer secretáriaID;
    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Basic(optional = false)
    @Column(name = "senha", nullable = false, length = 100)
    private String senha;
    @Column(name = "Nome", length = 50)
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "secret\u00e1riaID", fetch = FetchType.LAZY)
    private List<Matricula> matriculaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "secret\u00e1riaID", fetch = FetchType.LAZY)
    private List<Pagamento> pagamentoList;

    public Secretária() {
    }

    public Secretária(Integer secretáriaID) {
        this.secretáriaID = secretáriaID;
    }

    public Secretária(Integer secretáriaID, String email, String senha) {
        this.secretáriaID = secretáriaID;
        this.email = email;
        this.senha = senha;
    }

    public Integer getSecretáriaID() {
        return secretáriaID;
    }

    public void setSecretáriaID(Integer secretáriaID) {
        this.secretáriaID = secretáriaID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public List<Matricula> getMatriculaList() {
        return matriculaList;
    }

    public void setMatriculaList(List<Matricula> matriculaList) {
        this.matriculaList = matriculaList;
    }

    @XmlTransient
    public List<Pagamento> getPagamentoList() {
        return pagamentoList;
    }

    public void setPagamentoList(List<Pagamento> pagamentoList) {
        this.pagamentoList = pagamentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secretáriaID != null ? secretáriaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Secretária)) {
            return false;
        }
        Secretária other = (Secretária) object;
        if ((this.secretáriaID == null && other.secretáriaID != null) || (this.secretáriaID != null && !this.secretáriaID.equals(other.secretáriaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Secret\u00e1ria[ secret\u00e1riaID=" + secretáriaID + " ]";
    }
    
}
