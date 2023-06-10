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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Matricula", catalog = "ProGym", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"AlunoID", "AtividadeID"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Matricula.findAll", query = "SELECT m FROM Matricula m"),
    @NamedQuery(name = "Matricula.findByMatriculaID", query = "SELECT m FROM Matricula m WHERE m.matriculaID = :matriculaID"),
    @NamedQuery(name = "Matricula.findByData", query = "SELECT m FROM Matricula m WHERE m.data = :data"),
    @NamedQuery(name = "Matricula.findByEstado", query = "SELECT m FROM Matricula m WHERE m.estado = :estado")})
public class Matricula implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MatriculaID", nullable = false)
    private Integer matriculaID;
    @Column(name = "Data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Column(name = "Estado")
    private Integer estado;
    @JoinColumn(name = "AlunoID", referencedColumnName = "AlunoID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Aluno alunoID;
    @JoinColumn(name = "AtividadeID", referencedColumnName = "AtividadeID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Atividade atividadeID;
    @JoinColumn(name = "Secret\u00e1riaID", referencedColumnName = "Secret\u00e1riaID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Secretária secretáriaID;
    @OneToMany(mappedBy = "matriculaID", fetch = FetchType.LAZY)
    private List<Pagamento> pagamentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matriculaID", fetch = FetchType.LAZY)
    private List<Cobranca> cobrancaList;

    public Matricula() {
    }

    public Matricula(Integer matriculaID) {
        this.matriculaID = matriculaID;
    }

    public Integer getMatriculaID() {
        return matriculaID;
    }

    public void setMatriculaID(Integer matriculaID) {
        this.matriculaID = matriculaID;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Aluno getAlunoID() {
        return alunoID;
    }

    public void setAlunoID(Aluno alunoID) {
        this.alunoID = alunoID;
    }

    public Atividade getAtividadeID() {
        return atividadeID;
    }

    public void setAtividadeID(Atividade atividadeID) {
        this.atividadeID = atividadeID;
    }

    public Secretária getSecretáriaID() {
        return secretáriaID;
    }

    public void setSecretáriaID(Secretária secretáriaID) {
        this.secretáriaID = secretáriaID;
    }

    @XmlTransient
    public List<Pagamento> getPagamentoList() {
        return pagamentoList;
    }

    public void setPagamentoList(List<Pagamento> pagamentoList) {
        this.pagamentoList = pagamentoList;
    }

    @XmlTransient
    public List<Cobranca> getCobrancaList() {
        return cobrancaList;
    }

    public void setCobrancaList(List<Cobranca> cobrancaList) {
        this.cobrancaList = cobrancaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matriculaID != null ? matriculaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Matricula)) {
            return false;
        }
        Matricula other = (Matricula) object;
        if ((this.matriculaID == null && other.matriculaID != null) || (this.matriculaID != null && !this.matriculaID.equals(other.matriculaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Matricula[ matriculaID=" + matriculaID + " ]";
    }
    
}
