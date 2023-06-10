/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import modelo.Contrato;
import modelo.CoordenaçãoDesportiva;
import modelo.Professor;

/**
 *
 * @author mahomed
 */
public class ContratoJpaController implements Serializable {

    public ContratoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contrato contrato) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CoordenaçãoDesportiva coordenaçãoDesportivaID = contrato.getCoordenaçãoDesportivaID();
            if (coordenaçãoDesportivaID != null) {
                coordenaçãoDesportivaID = em.getReference(coordenaçãoDesportivaID.getClass(), coordenaçãoDesportivaID.getCoordenaçãoDesportivaID());
                contrato.setCoordenaçãoDesportivaID(coordenaçãoDesportivaID);
            }
            Professor professorID = contrato.getProfessorID();
            if (professorID != null) {
                professorID = em.getReference(professorID.getClass(), professorID.getProfessorID());
                contrato.setProfessorID(professorID);
            }
            em.persist(contrato);
            if (coordenaçãoDesportivaID != null) {
                coordenaçãoDesportivaID.getContratoList().add(contrato);
                coordenaçãoDesportivaID = em.merge(coordenaçãoDesportivaID);
            }
            if (professorID != null) {
                professorID.getContratoList().add(contrato);
                professorID = em.merge(professorID);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contrato contrato) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Contrato persistentContrato = em.find(Contrato.class, contrato.getContratoID());
            CoordenaçãoDesportiva coordenaçãoDesportivaIDOld = persistentContrato.getCoordenaçãoDesportivaID();
            CoordenaçãoDesportiva coordenaçãoDesportivaIDNew = contrato.getCoordenaçãoDesportivaID();
            Professor professorIDOld = persistentContrato.getProfessorID();
            Professor professorIDNew = contrato.getProfessorID();
            if (coordenaçãoDesportivaIDNew != null) {
                coordenaçãoDesportivaIDNew = em.getReference(coordenaçãoDesportivaIDNew.getClass(), coordenaçãoDesportivaIDNew.getCoordenaçãoDesportivaID());
                contrato.setCoordenaçãoDesportivaID(coordenaçãoDesportivaIDNew);
            }
            if (professorIDNew != null) {
                professorIDNew = em.getReference(professorIDNew.getClass(), professorIDNew.getProfessorID());
                contrato.setProfessorID(professorIDNew);
            }
            contrato = em.merge(contrato);
            if (coordenaçãoDesportivaIDOld != null && !coordenaçãoDesportivaIDOld.equals(coordenaçãoDesportivaIDNew)) {
                coordenaçãoDesportivaIDOld.getContratoList().remove(contrato);
                coordenaçãoDesportivaIDOld = em.merge(coordenaçãoDesportivaIDOld);
            }
            if (coordenaçãoDesportivaIDNew != null && !coordenaçãoDesportivaIDNew.equals(coordenaçãoDesportivaIDOld)) {
                coordenaçãoDesportivaIDNew.getContratoList().add(contrato);
                coordenaçãoDesportivaIDNew = em.merge(coordenaçãoDesportivaIDNew);
            }
            if (professorIDOld != null && !professorIDOld.equals(professorIDNew)) {
                professorIDOld.getContratoList().remove(contrato);
                professorIDOld = em.merge(professorIDOld);
            }
            if (professorIDNew != null && !professorIDNew.equals(professorIDOld)) {
                professorIDNew.getContratoList().add(contrato);
                professorIDNew = em.merge(professorIDNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contrato.getContratoID();
                if (findContrato(id) == null) {
                    throw new NonexistentEntityException("The contrato with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Contrato contrato;
            try {
                contrato = em.getReference(Contrato.class, id);
                contrato.getContratoID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contrato with id " + id + " no longer exists.", enfe);
            }
            CoordenaçãoDesportiva coordenaçãoDesportivaID = contrato.getCoordenaçãoDesportivaID();
            if (coordenaçãoDesportivaID != null) {
                coordenaçãoDesportivaID.getContratoList().remove(contrato);
                coordenaçãoDesportivaID = em.merge(coordenaçãoDesportivaID);
            }
            Professor professorID = contrato.getProfessorID();
            if (professorID != null) {
                professorID.getContratoList().remove(contrato);
                professorID = em.merge(professorID);
            }
            em.remove(contrato);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contrato> findContratoEntities() {
        return findContratoEntities(true, -1, -1);
    }

    public List<Contrato> findContratoEntities(int maxResults, int firstResult) {
        return findContratoEntities(false, maxResults, firstResult);
    }

    private List<Contrato> findContratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contrato.class));
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

    public Contrato findContrato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contrato> rt = cq.from(Contrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
