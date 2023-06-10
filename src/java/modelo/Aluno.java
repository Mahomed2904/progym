/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mahomed
 */
@Entity
@Table(name = "Aluno", catalog = "ProGym", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"}),
    @UniqueConstraint(columnNames = {"Nome", "email", "dataNascimento"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aluno.findAll", query = "SELECT a FROM Aluno a"),
    @NamedQuery(name = "Aluno.findByAlunoID", query = "SELECT a FROM Aluno a WHERE a.alunoID = :alunoID"),
    @NamedQuery(name = "Aluno.findByNome", query = "SELECT a FROM Aluno a WHERE a.nome = :nome"),
    @NamedQuery(name = "Aluno.findByDataNascimento", query = "SELECT a FROM Aluno a WHERE a.dataNascimento = :dataNascimento"),
    @NamedQuery(name = "Aluno.findByEmail", query = "SELECT a FROM Aluno a WHERE a.email = :email"),
    @NamedQuery(name = "Aluno.findByFotoUrl", query = "SELECT a FROM Aluno a WHERE a.fotoUrl = :fotoUrl")})
public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AlunoID", nullable = false)
    private Integer alunoID;
    @Column(name = "Nome", length = 50)
    private String nome;
    @Column(name = "dataNascimento")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    @Column(name = "email", length = 50)
    private String email;
    @Column(name = "fotoUrl", length = 100)
    private String fotoUrl;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alunoID", fetch = FetchType.LAZY)
    private List<Matricula> matriculaList;
    @OneToMany(mappedBy = "alunoID", fetch = FetchType.LAZY)
    private List<Pagamento> pagamentoList;

    public Aluno() {
    }

    public Aluno(Integer alunoID) {
        this.alunoID = alunoID;
    }

    public Integer getAlunoID() {
        return alunoID;
    }

    public void setAlunoID(Integer alunoID) {
        this.alunoID = alunoID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
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
        hash += (alunoID != null ? alunoID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aluno)) {
            return false;
        }
        Aluno other = (Aluno) object;
        if ((this.alunoID == null && other.alunoID != null) || (this.alunoID != null && !this.alunoID.equals(other.alunoID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Aluno[ alunoID=" + alunoID + " ]";
    }
    
}
