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
import modelo.Filial;
import modelo.GestorDeFilial;
import modelo.RelatorioDiário;

/**
 *
 * @author mahomed
 */
public class RelatorioDiárioJpaController implements Serializable {

    public RelatorioDiárioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RelatorioDiário relatorioDiário) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Filial origem = relatorioDiário.getOrigem();
            if (origem != null) {
                origem = em.getReference(origem.getClass(), origem.getFilialID());
                relatorioDiário.setOrigem(origem);
            }
            GestorDeFilial gestorDeFilialID = relatorioDiário.getGestorDeFilialID();
            if (gestorDeFilialID != null) {
                gestorDeFilialID = em.getReference(gestorDeFilialID.getClass(), gestorDeFilialID.getGestorDeFilialID());
                relatorioDiário.setGestorDeFilialID(gestorDeFilialID);
            }
            em.persist(relatorioDiário);
            if (origem != null) {
                origem.getRelatorioDiárioList().add(relatorioDiário);
                origem = em.merge(origem);
            }
            if (gestorDeFilialID != null) {
                gestorDeFilialID.getRelatorioDiárioList().add(relatorioDiário);
                gestorDeFilialID = em.merge(gestorDeFilialID);
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

    public void edit(RelatorioDiário relatorioDiário) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RelatorioDiário persistentRelatorioDiário = em.find(RelatorioDiário.class, relatorioDiário.getRelatorioDiárioID());
            Filial origemOld = persistentRelatorioDiário.getOrigem();
            Filial origemNew = relatorioDiário.getOrigem();
            GestorDeFilial gestorDeFilialIDOld = persistentRelatorioDiário.getGestorDeFilialID();
            GestorDeFilial gestorDeFilialIDNew = relatorioDiário.getGestorDeFilialID();
            if (origemNew != null) {
                origemNew = em.getReference(origemNew.getClass(), origemNew.getFilialID());
                relatorioDiário.setOrigem(origemNew);
            }
            if (gestorDeFilialIDNew != null) {
                gestorDeFilialIDNew = em.getReference(gestorDeFilialIDNew.getClass(), gestorDeFilialIDNew.getGestorDeFilialID());
                relatorioDiário.setGestorDeFilialID(gestorDeFilialIDNew);
            }
            relatorioDiário = em.merge(relatorioDiário);
            if (origemOld != null && !origemOld.equals(origemNew)) {
                origemOld.getRelatorioDiárioList().remove(relatorioDiário);
                origemOld = em.merge(origemOld);
            }
            if (origemNew != null && !origemNew.equals(origemOld)) {
                origemNew.getRelatorioDiárioList().add(relatorioDiário);
                origemNew = em.merge(origemNew);
            }
            if (gestorDeFilialIDOld != null && !gestorDeFilialIDOld.equals(gestorDeFilialIDNew)) {
                gestorDeFilialIDOld.getRelatorioDiárioList().remove(relatorioDiário);
                gestorDeFilialIDOld = em.merge(gestorDeFilialIDOld);
            }
            if (gestorDeFilialIDNew != null && !gestorDeFilialIDNew.equals(gestorDeFilialIDOld)) {
                gestorDeFilialIDNew.getRelatorioDiárioList().add(relatorioDiário);
                gestorDeFilialIDNew = em.merge(gestorDeFilialIDNew);
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
                Integer id = relatorioDiário.getRelatorioDiárioID();
                if (findRelatorioDiário(id) == null) {
                    throw new NonexistentEntityException("The relatorioDi\u00e1rio with id " + id + " no longer exists.");
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
            RelatorioDiário relatorioDiário;
            try {
                relatorioDiário = em.getReference(RelatorioDiário.class, id);
                relatorioDiário.getRelatorioDiárioID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The relatorioDi\u00e1rio with id " + id + " no longer exists.", enfe);
            }
            Filial origem = relatorioDiário.getOrigem();
            if (origem != null) {
                origem.getRelatorioDiárioList().remove(relatorioDiário);
                origem = em.merge(origem);
            }
            GestorDeFilial gestorDeFilialID = relatorioDiário.getGestorDeFilialID();
            if (gestorDeFilialID != null) {
                gestorDeFilialID.getRelatorioDiárioList().remove(relatorioDiário);
                gestorDeFilialID = em.merge(gestorDeFilialID);
            }
            em.remove(relatorioDiário);
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

    public List<RelatorioDiário> findRelatorioDiárioEntities() {
        return findRelatorioDiárioEntities(true, -1, -1);
    }

    public List<RelatorioDiário> findRelatorioDiárioEntities(int maxResults, int firstResult) {
        return findRelatorioDiárioEntities(false, maxResults, firstResult);
    }

    private List<RelatorioDiário> findRelatorioDiárioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RelatorioDiário.class));
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

    public RelatorioDiário findRelatorioDiário(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RelatorioDiário.class, id);
        } finally {
            em.close();
        }
    }

    public int getRelatorioDiárioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RelatorioDiário> rt = cq.from(RelatorioDiário.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
