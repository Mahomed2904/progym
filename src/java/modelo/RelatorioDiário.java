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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mahomed
 */
@Entity
@Table(name = "RelatorioDi\u00e1rio", catalog = "ProGym", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelatorioDi\u00e1rio.findAll", query = "SELECT r FROM RelatorioDi\u00e1rio r"),
    @NamedQuery(name = "RelatorioDi\u00e1rio.findByRelatorioDi\u00e1rioID", query = "SELECT r FROM RelatorioDi\u00e1rio r WHERE r.relatorioDi\u00e1rioID = :relatorioDi\u00e1rioID"),
    @NamedQuery(name = "RelatorioDi\u00e1rio.findByDatadeemissao", query = "SELECT r FROM RelatorioDi\u00e1rio r WHERE r.datadeemissao = :datadeemissao"),
    @NamedQuery(name = "RelatorioDi\u00e1rio.findByHoradeemissao", query = "SELECT r FROM RelatorioDi\u00e1rio r WHERE r.horadeemissao = :horadeemissao"),
    @NamedQuery(name = "RelatorioDi\u00e1rio.findByMatriclulascanceladas", query = "SELECT r FROM RelatorioDi\u00e1rio r WHERE r.matriclulascanceladas = :matriclulascanceladas"),
    @NamedQuery(name = "RelatorioDi\u00e1rio.findByMatriculasefetuadas", query = "SELECT r FROM RelatorioDi\u00e1rio r WHERE r.matriculasefetuadas = :matriculasefetuadas"),
    @NamedQuery(name = "RelatorioDi\u00e1rio.findByVagasdisponiveis", query = "SELECT r FROM RelatorioDi\u00e1rio r WHERE r.vagasdisponiveis = :vagasdisponiveis")})
public class RelatorioDiário implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RelatorioDi\u00e1rioID", nullable = false)
    private Integer relatorioDiárioID;
    @Column(name = "Datadeemissao")
    @Temporal(TemporalType.DATE)
    private Date datadeemissao;
    @Column(name = "Horadeemissao")
    @Temporal(TemporalType.TIME)
    private Date horadeemissao;
    @Column(name = "Matriclulascanceladas")
    private Integer matriclulascanceladas;
    @Column(name = "Matriculasefetuadas")
    private Integer matriculasefetuadas;
    @Column(name = "Vagasdisponiveis")
    private Integer vagasdisponiveis;
    @JoinColumn(name = "Origem", referencedColumnName = "GestorDeFilialID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Filial origem;
    @JoinColumn(name = "GestorDeFilialID", referencedColumnName = "GestorDeFilialID")
    @ManyToOne(fetch = FetchType.LAZY)
    private GestorDeFilial gestorDeFilialID;

    public RelatorioDiário() {
    }

    public RelatorioDiário(Integer relatorioDiárioID) {
        this.relatorioDiárioID = relatorioDiárioID;
    }

    public Integer getRelatorioDiárioID() {
        return relatorioDiárioID;
    }

    public void setRelatorioDiárioID(Integer relatorioDiárioID) {
        this.relatorioDiárioID = relatorioDiárioID;
    }

    public Date getDatadeemissao() {
        return datadeemissao;
    }

    public void setDatadeemissao(Date datadeemissao) {
        this.datadeemissao = datadeemissao;
    }

    public Date getHoradeemissao() {
        return horadeemissao;
    }

    public void setHoradeemissao(Date horadeemissao) {
        this.horadeemissao = horadeemissao;
    }

    public Integer getMatriclulascanceladas() {
        return matriclulascanceladas;
    }

    public void setMatriclulascanceladas(Integer matriclulascanceladas) {
        this.matriclulascanceladas = matriclulascanceladas;
    }

    public Integer getMatriculasefetuadas() {
        return matriculasefetuadas;
    }

    public void setMatriculasefetuadas(Integer matriculasefetuadas) {
        this.matriculasefetuadas = matriculasefetuadas;
    }

    public Integer getVagasdisponiveis() {
        return vagasdisponiveis;
    }

    public void setVagasdisponiveis(Integer vagasdisponiveis) {
        this.vagasdisponiveis = vagasdisponiveis;
    }

    public Filial getOrigem() {
        return origem;
    }

    public void setOrigem(Filial origem) {
        this.origem = origem;
    }

    public GestorDeFilial getGestorDeFilialID() {
        return gestorDeFilialID;
    }

    public void setGestorDeFilialID(GestorDeFilial gestorDeFilialID) {
        this.gestorDeFilialID = gestorDeFilialID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (relatorioDiárioID != null ? relatorioDiárioID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelatorioDiário)) {
            return false;
        }
        RelatorioDiário other = (RelatorioDiário) object;
        if ((this.relatorioDiárioID == null && other.relatorioDiárioID != null) || (this.relatorioDiárioID != null && !this.relatorioDiárioID.equals(other.relatorioDiárioID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.RelatorioDi\u00e1rio[ relatorioDi\u00e1rioID=" + relatorioDiárioID + " ]";
    }
    
}
