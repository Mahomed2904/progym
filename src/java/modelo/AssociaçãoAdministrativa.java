/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mahomed
 */
@Entity
@Table(name = "Associa\u00e7\u00e3oAdministrativa", catalog = "ProGym", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Associa\u00e7\u00e3oAdministrativa.findAll", query = "SELECT a FROM Associa\u00e7\u00e3oAdministrativa a"),
    @NamedQuery(name = "Associa\u00e7\u00e3oAdministrativa.findByAssocia\u00e7\u00e3oAdministrativaID", query = "SELECT a FROM Associa\u00e7\u00e3oAdministrativa a WHERE a.associa\u00e7\u00e3oAdministrativaID = :associa\u00e7\u00e3oAdministrativaID"),
    @NamedQuery(name = "Associa\u00e7\u00e3oAdministrativa.findByDescricao", query = "SELECT a FROM Associa\u00e7\u00e3oAdministrativa a WHERE a.descricao = :descricao")})
public class AssociaçãoAdministrativa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Associa\u00e7\u00e3oAdministrativaID", nullable = false)
    private Integer associaçãoAdministrativaID;
    @Column(name = "Descricao")
    private Integer descricao;

    public AssociaçãoAdministrativa() {
    }

    public AssociaçãoAdministrativa(Integer associaçãoAdministrativaID) {
        this.associaçãoAdministrativaID = associaçãoAdministrativaID;
    }

    public Integer getAssociaçãoAdministrativaID() {
        return associaçãoAdministrativaID;
    }

    public void setAssociaçãoAdministrativaID(Integer associaçãoAdministrativaID) {
        this.associaçãoAdministrativaID = associaçãoAdministrativaID;
    }

    public Integer getDescricao() {
        return descricao;
    }

    public void setDescricao(Integer descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (associaçãoAdministrativaID != null ? associaçãoAdministrativaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssociaçãoAdministrativa)) {
            return false;
        }
        AssociaçãoAdministrativa other = (AssociaçãoAdministrativa) object;
        if ((this.associaçãoAdministrativaID == null && other.associaçãoAdministrativaID != null) || (this.associaçãoAdministrativaID != null && !this.associaçãoAdministrativaID.equals(other.associaçãoAdministrativaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Associa\u00e7\u00e3oAdministrativa[ associa\u00e7\u00e3oAdministrativaID=" + associaçãoAdministrativaID + " ]";
    }
    
}
