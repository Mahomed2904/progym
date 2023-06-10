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
@Table(name = "GestorDeFilial", catalog = "ProGym", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GestorDeFilial.findAll", query = "SELECT g FROM GestorDeFilial g"),
    @NamedQuery(name = "GestorDeFilial.findByGestorDeFilialID", query = "SELECT g FROM GestorDeFilial g WHERE g.gestorDeFilialID = :gestorDeFilialID"),
    @NamedQuery(name = "GestorDeFilial.findByNome", query = "SELECT g FROM GestorDeFilial g WHERE g.nome = :nome")})
public class GestorDeFilial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GestorDeFilialID", nullable = false)
    private Integer gestorDeFilialID;
    @Size(max = 150)
    @Column(name = "Nome", length = 150)
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gestorDeFilialID", fetch = FetchType.LAZY)
    private List<Filial> filialList;
    @OneToMany(mappedBy = "gestorDeFilialID", fetch = FetchType.LAZY)
    private List<RelatorioDiário> relatorioDiárioList;

    public GestorDeFilial() {
    }

    public GestorDeFilial(Integer gestorDeFilialID) {
        this.gestorDeFilialID = gestorDeFilialID;
    }

    public Integer getGestorDeFilialID() {
        return gestorDeFilialID;
    }

    public void setGestorDeFilialID(Integer gestorDeFilialID) {
        this.gestorDeFilialID = gestorDeFilialID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public List<Filial> getFilialList() {
        return filialList;
    }

    public void setFilialList(List<Filial> filialList) {
        this.filialList = filialList;
    }

    @XmlTransient
    public List<RelatorioDiário> getRelatorioDiárioList() {
        return relatorioDiárioList;
    }

    public void setRelatorioDiárioList(List<RelatorioDiário> relatorioDiárioList) {
        this.relatorioDiárioList = relatorioDiárioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gestorDeFilialID != null ? gestorDeFilialID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GestorDeFilial)) {
            return false;
        }
        GestorDeFilial other = (GestorDeFilial) object;
        if ((this.gestorDeFilialID == null && other.gestorDeFilialID != null) || (this.gestorDeFilialID != null && !this.gestorDeFilialID.equals(other.gestorDeFilialID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.GestorDeFilial[ gestorDeFilialID=" + gestorDeFilialID + " ]";
    }
    
}
