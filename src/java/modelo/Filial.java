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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mahomed
 */
@Entity
@Table(name = "Filial", catalog = "ProGym", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Filial.findAll", query = "SELECT f FROM Filial f"),
    @NamedQuery(name = "Filial.findByFilialID", query = "SELECT f FROM Filial f WHERE f.filialID = :filialID"),
    @NamedQuery(name = "Filial.findByHorainicio", query = "SELECT f FROM Filial f WHERE f.horainicio = :horainicio"),
    @NamedQuery(name = "Filial.findByHoratermino", query = "SELECT f FROM Filial f WHERE f.horatermino = :horatermino"),
    @NamedQuery(name = "Filial.findByLocalizacao", query = "SELECT f FROM Filial f WHERE f.localizacao = :localizacao")})
public class Filial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FilialID", nullable = false)
    private Integer filialID;
    @Column(name = "Horainicio")
    @Temporal(TemporalType.TIME)
    private Date horainicio;
    @Column(name = "Horatermino")
    @Temporal(TemporalType.TIME)
    private Date horatermino;
    @Size(max = 30)
    @Column(name = "Localizacao", length = 30)
    private String localizacao;
    @JoinColumn(name = "Coordena\u00e7\u00e3oDesportivaID", referencedColumnName = "Coordena\u00e7\u00e3oDesportivaID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CoordenaçãoDesportiva coordenaçãoDesportivaID;
    @JoinColumn(name = "GestorDeFilialID", referencedColumnName = "GestorDeFilialID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private GestorDeFilial gestorDeFilialID;
    @OneToMany(mappedBy = "origem", fetch = FetchType.LAZY)
    private List<RelatorioDiário> relatorioDiárioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "filialID", fetch = FetchType.LAZY)
    private List<Atividade> atividadeList;

    public Filial() {
    }

    public Filial(Integer filialID) {
        this.filialID = filialID;
    }

    public Integer getFilialID() {
        return filialID;
    }

    public void setFilialID(Integer filialID) {
        this.filialID = filialID;
    }

    public Date getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(Date horainicio) {
        this.horainicio = horainicio;
    }

    public Date getHoratermino() {
        return horatermino;
    }

    public void setHoratermino(Date horatermino) {
        this.horatermino = horatermino;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public CoordenaçãoDesportiva getCoordenaçãoDesportivaID() {
        return coordenaçãoDesportivaID;
    }

    public void setCoordenaçãoDesportivaID(CoordenaçãoDesportiva coordenaçãoDesportivaID) {
        this.coordenaçãoDesportivaID = coordenaçãoDesportivaID;
    }

    public GestorDeFilial getGestorDeFilialID() {
        return gestorDeFilialID;
    }

    public void setGestorDeFilialID(GestorDeFilial gestorDeFilialID) {
        this.gestorDeFilialID = gestorDeFilialID;
    }

    @XmlTransient
    public List<RelatorioDiário> getRelatorioDiárioList() {
        return relatorioDiárioList;
    }

    public void setRelatorioDiárioList(List<RelatorioDiário> relatorioDiárioList) {
        this.relatorioDiárioList = relatorioDiárioList;
    }

    @XmlTransient
    public List<Atividade> getAtividadeList() {
        return atividadeList;
    }

    public void setAtividadeList(List<Atividade> atividadeList) {
        this.atividadeList = atividadeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (filialID != null ? filialID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Filial)) {
            return false;
        }
        Filial other = (Filial) object;
        if ((this.filialID == null && other.filialID != null) || (this.filialID != null && !this.filialID.equals(other.filialID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Filial[ filialID=" + filialID + " ]";
    }
    
}
