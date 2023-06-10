/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Atividade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Contrato;
import modelo.Professor;

/**
 *
 * @author mahomed
 */
public class ProfessorJpaController implements Serializable {

    public ProfessorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Professor professor) {
        if (professor.getAtividadeList() == null) {
            professor.setAtividadeList(new ArrayList<Atividade>());
        }
        if (professor.getContratoList() == null) {
            professor.setContratoList(new ArrayList<Contrato>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Atividade> attachedAtividadeList = new ArrayList<Atividade>();
            for (Atividade atividadeListAtividadeToAttach : professor.getAtividadeList()) {
                atividadeListAtividadeToAttach = em.getReference(atividadeListAtividadeToAttach.getClass(), atividadeListAtividadeToAttach.getAtividadeID());
                attachedAtividadeList.add(atividadeListAtividadeToAttach);
            }
            professor.setAtividadeList(attachedAtividadeList);
            List<Contrato> attachedContratoList = new ArrayList<Contrato>();
            for (Contrato contratoListContratoToAttach : professor.getContratoList()) {
                contratoListContratoToAttach = em.getReference(contratoListContratoToAttach.getClass(), contratoListContratoToAttach.getContratoID());
                attachedContratoList.add(contratoListContratoToAttach);
            }
            professor.setContratoList(attachedContratoList);
            em.persist(professor);
            for (Atividade atividadeListAtividade : professor.getAtividadeList()) {
                Professor oldProfessorIDOfAtividadeListAtividade = atividadeListAtividade.getProfessorID();
                atividadeListAtividade.setProfessorID(professor);
                atividadeListAtividade = em.merge(atividadeListAtividade);
                if (oldProfessorIDOfAtividadeListAtividade != null) {
                    oldProfessorIDOfAtividadeListAtividade.getAtividadeList().remove(atividadeListAtividade);
                    oldProfessorIDOfAtividadeListAtividade = em.merge(oldProfessorIDOfAtividadeListAtividade);
                }
            }
            for (Contrato contratoListContrato : professor.getContratoList()) {
                Professor oldProfessorIDOfContratoListContrato = contratoListContrato.getProfessorID();
                contratoListContrato.setProfessorID(professor);
                contratoListContrato = em.merge(contratoListContrato);
                if (oldProfessorIDOfContratoListContrato != null) {
                    oldProfessorIDOfContratoListContrato.getContratoList().remove(contratoListContrato);
                    oldProfessorIDOfContratoListContrato = em.merge(oldProfessorIDOfContratoListContrato);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Professor professor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Professor persistentProfessor = em.find(Professor.class, professor.getProfessorID());
            List<Atividade> atividadeListOld = persistentProfessor.getAtividadeList();
            List<Atividade> atividadeListNew = professor.getAtividadeList();
            List<Contrato> contratoListOld = persistentProfessor.getContratoList();
            List<Contrato> contratoListNew = professor.getContratoList();
            List<String> illegalOrphanMessages = null;
            for (Atividade atividadeListOldAtividade : atividadeListOld) {
                if (!atividadeListNew.contains(atividadeListOldAtividade)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Atividade " + atividadeListOldAtividade + " since its professorID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Atividade> attachedAtividadeListNew = new ArrayList<Atividade>();
            for (Atividade atividadeListNewAtividadeToAttach : atividadeListNew) {
                atividadeListNewAtividadeToAttach = em.getReference(atividadeListNewAtividadeToAttach.getClass(), atividadeListNewAtividadeToAttach.getAtividadeID());
                attachedAtividadeListNew.add(atividadeListNewAtividadeToAttach);
            }
            atividadeListNew = attachedAtividadeListNew;
            professor.setAtividadeList(atividadeListNew);
            List<Contrato> attachedContratoListNew = new ArrayList<Contrato>();
            for (Contrato contratoListNewContratoToAttach : contratoListNew) {
                contratoListNewContratoToAttach = em.getReference(contratoListNewContratoToAttach.getClass(), contratoListNewContratoToAttach.getContratoID());
                attachedContratoListNew.add(contratoListNewContratoToAttach);
            }
            contratoListNew = attachedContratoListNew;
            professor.setContratoList(contratoListNew);
            professor = em.merge(professor);
            for (Atividade atividadeListNewAtividade : atividadeListNew) {
                if (!atividadeListOld.contains(atividadeListNewAtividade)) {
                    Professor oldProfessorIDOfAtividadeListNewAtividade = atividadeListNewAtividade.getProfessorID();
                    atividadeListNewAtividade.setProfessorID(professor);
                    atividadeListNewAtividade = em.merge(atividadeListNewAtividade);
                    if (oldProfessorIDOfAtividadeListNewAtividade != null && !oldProfessorIDOfAtividadeListNewAtividade.equals(professor)) {
                        oldProfessorIDOfAtividadeListNewAtividade.getAtividadeList().remove(atividadeListNewAtividade);
                        oldProfessorIDOfAtividadeListNewAtividade = em.merge(oldProfessorIDOfAtividadeListNewAtividade);
                    }
                }
            }
            for (Contrato contratoListOldContrato : contratoListOld) {
                if (!contratoListNew.contains(contratoListOldContrato)) {
                    contratoListOldContrato.setProfessorID(null);
                    contratoListOldContrato = em.merge(contratoListOldContrato);
                }
            }
            for (Contrato contratoListNewContrato : contratoListNew) {
                if (!contratoListOld.contains(contratoListNewContrato)) {
                    Professor oldProfessorIDOfContratoListNewContrato = contratoListNewContrato.getProfessorID();
                    contratoListNewContrato.setProfessorID(professor);
                    contratoListNewContrato = em.merge(contratoListNewContrato);
                    if (oldProfessorIDOfContratoListNewContrato != null && !oldProfessorIDOfContratoListNewContrato.equals(professor)) {
                        oldProfessorIDOfContratoListNewContrato.getContratoList().remove(contratoListNewContrato);
                        oldProfessorIDOfContratoListNewContrato = em.merge(oldProfessorIDOfContratoListNewContrato);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = professor.getProfessorID();
                if (findProfessor(id) == null) {
                    throw new NonexistentEntityException("The professor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Professor professor;
            try {
                professor = em.getReference(Professor.class, id);
                professor.getProfessorID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The professor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Atividade> atividadeListOrphanCheck = professor.getAtividadeList();
            for (Atividade atividadeListOrphanCheckAtividade : atividadeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Professor (" + professor + ") cannot be destroyed since the Atividade " + atividadeListOrphanCheckAtividade + " in its atividadeList field has a non-nullable professorID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Contrato> contratoList = professor.getContratoList();
            for (Contrato contratoListContrato : contratoList) {
                contratoListContrato.setProfessorID(null);
                contratoListContrato = em.merge(contratoListContrato);
            }
            em.remove(professor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Professor> findProfessorEntities() {
        return findProfessorEntities(true, -1, -1);
    }

    public List<Professor> findProfessorEntities(int maxResults, int firstResult) {
        return findProfessorEntities(false, maxResults, firstResult);
    }

    private List<Professor> findProfessorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Professor.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Professor findProfessor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Professor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProfessorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Professor> rt = cq.from(Professor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
