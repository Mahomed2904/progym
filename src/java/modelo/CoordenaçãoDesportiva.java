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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mahomed
 */
@Entity
@Table(name = "Coordena\u00e7\u00e3oDesportiva", catalog = "ProGym", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coordena\u00e7\u00e3oDesportiva.findAll", query = "SELECT c FROM Coordena\u00e7\u00e3oDesportiva c"),
    @NamedQuery(name = "Coordena\u00e7\u00e3oDesportiva.findByCoordena\u00e7\u00e3oDesportivaID", query = "SELECT c FROM Coordena\u00e7\u00e3oDesportiva c WHERE c.coordena\u00e7\u00e3oDesportivaID = :coordena\u00e7\u00e3oDesportivaID"),
    @NamedQuery(name = "Coordena\u00e7\u00e3oDesportiva.findByDescricao", query = "SELECT c FROM Coordena\u00e7\u00e3oDesportiva c WHERE c.descricao = :descricao")})
public class CoordenaçãoDesportiva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Coordena\u00e7\u00e3oDesportivaID", nullable = false)
    private Integer coordenaçãoDesportivaID;
    @Size(max = 100)
    @Column(name = "Descricao", length = 100)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coordena\u00e7\u00e3oDesportivaID", fetch = FetchType.LAZY)
    private List<Filial> filialList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coordena\u00e7\u00e3oDesportivaID", fetch = FetchType.LAZY)
    private List<Atividade> atividadeList;
    @OneToMany(mappedBy = "coordena\u00e7\u00e3oDesportivaID", fetch = FetchType.LAZY)
    private List<Contrato> contratoList;

    public CoordenaçãoDesportiva() {
    }

    public CoordenaçãoDesportiva(Integer coordenaçãoDesportivaID) {
        this.coordenaçãoDesportivaID = coordenaçãoDesportivaID;
    }

    public Integer getCoordenaçãoDesportivaID() {
        return coordenaçãoDesportivaID;
    }

    public void setCoordenaçãoDesportivaID(Integer coordenaçãoDesportivaID) {
        this.coordenaçãoDesportivaID = coordenaçãoDesportivaID;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Filial> getFilialList() {
        return filialList;
    }

    public void setFilialList(List<Filial> filialList) {
        this.filialList = filialList;
    }

    @XmlTransient
    public List<Atividade> getAtividadeList() {
        return atividadeList;
    }

    public void setAtividadeList(List<Atividade> atividadeList) {
        this.atividadeList = atividadeList;
    }

    @XmlTransient
    public List<Contrato> getContratoList() {
        return contratoList;
    }

    public void setContratoList(List<Contrato> contratoList) {
        this.contratoList = contratoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coordenaçãoDesportivaID != null ? coordenaçãoDesportivaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CoordenaçãoDesportiva)) {
            return false;
        }
        CoordenaçãoDesportiva other = (CoordenaçãoDesportiva) object;
        if ((this.coordenaçãoDesportivaID == null && other.coordenaçãoDesportivaID != null) || (this.coordenaçãoDesportivaID != null && !this.coordenaçãoDesportivaID.equals(other.coordenaçãoDesportivaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Coordena\u00e7\u00e3oDesportiva[ coordena\u00e7\u00e3oDesportivaID=" + coordenaçãoDesportivaID + " ]";
    }
    
}
