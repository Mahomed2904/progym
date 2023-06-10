/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mahomed
 */
@Entity
@Table(name = "Pagamento", catalog = "ProGym", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"CobrancaID"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagamento.findAll", query = "SELECT p FROM Pagamento p"),
    @NamedQuery(name = "Pagamento.findByPagamentoID", query = "SELECT p FROM Pagamento p WHERE p.pagamentoID = :pagamentoID"),
    @NamedQuery(name = "Pagamento.findByData", query = "SELECT p FROM Pagamento p WHERE p.data = :data"),
    @NamedQuery(name = "Pagamento.findByTaxa", query = "SELECT p FROM Pagamento p WHERE p.taxa = :taxa"),
    @NamedQuery(name = "Pagamento.findByValor", query = "SELECT p FROM Pagamento p WHERE p.valor = :valor"),
    @NamedQuery(name = "Pagamento.findByCodigoRecibo", query = "SELECT p FROM Pagamento p WHERE p.codigoRecibo = :codigoRecibo"),
    @NamedQuery(name = "Pagamento.findByBanco", query = "SELECT p FROM Pagamento p WHERE p.banco = :banco")})
public class Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PagamentoID", nullable = false)
    private Integer pagamentoID;
    @Column(name = "Data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Taxa", precision = 22, scale = 0)
    private Double taxa;
    @Column(name = "Valor", precision = 22, scale = 0)
    private Double valor;
    @Size(max = 200)
    @Column(name = "CodigoRecibo", length = 200)
    private String codigoRecibo;
    @Size(max = 50)
    @Column(name = "Banco", length = 50)
    private String banco;
    @JoinColumn(name = "AlunoID", referencedColumnName = "AlunoID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Aluno alunoID;
    @JoinColumn(name = "CobrancaID", referencedColumnName = "CobrancaID", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Cobranca cobrancaID;
    @JoinColumn(name = "MatriculaID", referencedColumnName = "MatriculaID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Matricula matriculaID;
    @JoinColumn(name = "Secret\u00e1riaID", referencedColumnName = "Secret\u00e1riaID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Secretária secretáriaID;

    public Pagamento() {
    }

    public Pagamento(Integer pagamentoID) {
        this.pagamentoID = pagamentoID;
    }

    public Integer getPagamentoID() {
        return pagamentoID;
    }

    public void setPagamentoID(Integer pagamentoID) {
        this.pagamentoID = pagamentoID;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getTaxa() {
        return taxa;
    }

    public void setTaxa(Double taxa) {
        this.taxa = taxa;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getCodigoRecibo() {
        return codigoRecibo;
    }

    public void setCodigoRecibo(String codigoRecibo) {
        this.codigoRecibo = codigoRecibo;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public Aluno getAlunoID() {
        return alunoID;
    }

    public void setAlunoID(Aluno alunoID) {
        this.alunoID = alunoID;
    }

    public Cobranca getCobrancaID() {
        return cobrancaID;
    }

    public void setCobrancaID(Cobranca cobrancaID) {
        this.cobrancaID = cobrancaID;
    }

    public Matricula getMatriculaID() {
        return matriculaID;
    }

    public void setMatriculaID(Matricula matriculaID) {
        this.matriculaID = matriculaID;
    }

    public Secretária getSecretáriaID() {
        return secretáriaID;
    }

    public void setSecretáriaID(Secretária secretáriaID) {
        this.secretáriaID = secretáriaID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pagamentoID != null ? pagamentoID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagamento)) {
            return false;
        }
        Pagamento other = (Pagamento) object;
        if ((this.pagamentoID == null && other.pagamentoID != null) || (this.pagamentoID != null && !this.pagamentoID.equals(other.pagamentoID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Pagamento[ pagamentoID=" + pagamentoID + " ]";
    }
    
}
