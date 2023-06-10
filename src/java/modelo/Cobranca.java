/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mahomed
 */
@Entity
@Table(name = "Cobranca", catalog = "ProGym", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cobranca.findAll", query = "SELECT c FROM Cobranca c"),
    @NamedQuery(name = "Cobranca.findByCobrancaID", query = "SELECT c FROM Cobranca c WHERE c.cobrancaID = :cobrancaID"),
    @NamedQuery(name = "Cobranca.findByDataInicio", query = "SELECT c FROM Cobranca c WHERE c.dataInicio = :dataInicio"),
    @NamedQuery(name = "Cobranca.findByDataTermino", query = "SELECT c FROM Cobranca c WHERE c.dataTermino = :dataTermino"),
    @NamedQuery(name = "Cobranca.findByTaxa", query = "SELECT c FROM Cobranca c WHERE c.taxa = :taxa"),
    @NamedQuery(name = "Cobranca.findByValor", query = "SELECT c FROM Cobranca c WHERE c.valor = :valor"),
    @NamedQuery(name = "Cobranca.findByPago", query = "SELECT c FROM Cobranca c WHERE c.pago = :pago")})
public class Cobranca implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CobrancaID", nullable = false)
    private Integer cobrancaID;
    @Column(name = "DataInicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicio;
    @Column(name = "DataTermino")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTermino;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Taxa", nullable = false)
    private float taxa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Valor", nullable = false)
    private float valor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Pago", nullable = false)
    private boolean pago;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cobrancaID", fetch = FetchType.LAZY)
    private Pagamento pagamento;
    @JoinColumn(name = "MatriculaID", referencedColumnName = "MatriculaID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Matricula matriculaID;

    public Cobranca() {
    }

    public Cobranca(Integer cobrancaID) {
        this.cobrancaID = cobrancaID;
    }

    public Cobranca(Integer cobrancaID, float taxa, float valor, boolean pago) {
        this.cobrancaID = cobrancaID;
        this.taxa = taxa;
        this.valor = valor;
        this.pago = pago;
    }

    public Integer getCobrancaID() {
        return cobrancaID;
    }

    public void setCobrancaID(Integer cobrancaID) {
        this.cobrancaID = cobrancaID;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public float getTaxa() {
        return taxa;
    }

    public void setTaxa(float taxa) {
        this.taxa = taxa;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public boolean getPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Matricula getMatriculaID() {
        return matriculaID;
    }

    public void setMatriculaID(Matricula matriculaID) {
        this.matriculaID = matriculaID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cobrancaID != null ? cobrancaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cobranca)) {
            return false;
        }
        Cobranca other = (Cobranca) object;
        if ((this.cobrancaID == null && other.cobrancaID != null) || (this.cobrancaID != null && !this.cobrancaID.equals(other.cobrancaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Cobranca[ cobrancaID=" + cobrancaID + " ]";
    }
    
}
