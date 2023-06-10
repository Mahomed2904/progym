/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mahomed
 */
@Entity
@Table(name = "Contrato", catalog = "ProGym", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contrato.findAll", query = "SELECT c FROM Contrato c"),
    @NamedQuery(name = "Contrato.findByContratoID", query = "SELECT c FROM Contrato c WHERE c.contratoID = :contratoID")})
public class Contrato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ContratoID", nullable = false)
    private Integer contratoID;
    @JoinColumn(name = "Coordena\u00e7\u00e3oDesportivaID", referencedColumnName = "Coordena\u00e7\u00e3oDesportivaID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CoordenaçãoDesportiva coordenaçãoDesportivaID;
    @JoinColumn(name = "ProfessorID", referencedColumnName = "ProfessorID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Professor professorID;

    public Contrato() {
    }

    public Contrato(Integer contratoID) {
        this.contratoID = contratoID;
    }

    public Integer getContratoID() {
        return contratoID;
    }

    public void setContratoID(Integer contratoID) {
        this.contratoID = contratoID;
    }

    public CoordenaçãoDesportiva getCoordenaçãoDesportivaID() {
        return coordenaçãoDesportivaID;
    }

    public void setCoordenaçãoDesportivaID(CoordenaçãoDesportiva coordenaçãoDesportivaID) {
        this.coordenaçãoDesportivaID = coordenaçãoDesportivaID;
    }

    public Professor getProfessorID() {
        return professorID;
    }

    public void setProfessorID(Professor professorID) {
        this.professorID = professorID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contratoID != null ? contratoID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contrato)) {
            return false;
        }
        Contrato other = (Contrato) object;
        if ((this.contratoID == null && other.contratoID != null) || (this.contratoID != null && !this.contratoID.equals(other.contratoID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Contrato[ contratoID=" + contratoID + " ]";
    }
    
}
