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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mahomed
 */
@Entity
@Table(name = "Atividade", catalog = "ProGym", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atividade.findAll", query = "SELECT a FROM Atividade a"),
    @NamedQuery(name = "Atividade.findByAtividadeID", query = "SELECT a FROM Atividade a WHERE a.atividadeID = :atividadeID"),
    @NamedQuery(name = "Atividade.findByNome", query = "SELECT a FROM Atividade a WHERE a.nome = :nome"),
    @NamedQuery(name = "Atividade.findByDescricao", query = "SELECT a FROM Atividade a WHERE a.descricao = :descricao")})
public class Atividade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AtividadeID", nullable = false)
    private Integer atividadeID;
    @Column(name = "Nome", length = 100)
    private String nome;
    @Column(name = "Descricao", length = 100)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "atividadeID", fetch = FetchType.LAZY)
    private List<Matricula> matriculaList;
    @JoinColumn(name = "Coordena\u00e7\u00e3oDesportivaID", referencedColumnName = "Coordena\u00e7\u00e3oDesportivaID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CoordenaçãoDesportiva coordenaçãoDesportivaID;
    @JoinColumn(name = "FilialID", referencedColumnName = "FilialID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Filial filialID;
    @JoinColumn(name = "ProfessorID", referencedColumnName = "ProfessorID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Professor professorID;

    public Atividade() {
    }

    public Atividade(Integer atividadeID) {
        this.atividadeID = atividadeID;
    }

    public Integer getAtividadeID() {
        return atividadeID;
    }

    public void setAtividadeID(Integer atividadeID) {
        this.atividadeID = atividadeID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @XmlTransient
    public List<Matricula> getMatriculaList() {
        return matriculaList;
    }

    public void setMatriculaList(List<Matricula> matriculaList) {
        this.matriculaList = matriculaList;
    }

    public CoordenaçãoDesportiva getCoordenaçãoDesportivaID() {
        return coordenaçãoDesportivaID;
    }

    public void setCoordenaçãoDesportivaID(CoordenaçãoDesportiva coordenaçãoDesportivaID) {
        this.coordenaçãoDesportivaID = coordenaçãoDesportivaID;
    }

    public Filial getFilialID() {
        return filialID;
    }

    public void setFilialID(Filial filialID) {
        this.filialID = filialID;
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
        hash += (atividadeID != null ? atividadeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atividade)) {
            return false;
        }
        Atividade other = (Atividade) object;
        if ((this.atividadeID == null && other.atividadeID != null) || (this.atividadeID != null && !this.atividadeID.equals(other.atividadeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Atividade[ atividadeID=" + atividadeID + " ]";
    }
    
}
