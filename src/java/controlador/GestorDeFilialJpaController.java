/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Filial;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.GestorDeFilial;
import modelo.RelatorioDiário;

/**
 *
 * @author mahomed
 */
public class GestorDeFilialJpaController implements Serializable {

    public GestorDeFilialJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GestorDeFilial gestorDeFilial) throws RollbackFailureException, Exception {
        if (gestorDeFilial.getFilialList() == null) {
            gestorDeFilial.setFilialList(new ArrayList<Filial>());
        }
        if (gestorDeFilial.getRelatorioDiárioList() == null) {
            gestorDeFilial.setRelatorioDiárioList(new ArrayList<RelatorioDiário>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Filial> attachedFilialList = new ArrayList<Filial>();
            for (Filial filialListFilialToAttach : gestorDeFilial.getFilialList()) {
                filialListFilialToAttach = em.getReference(filialListFilialToAttach.getClass(), filialListFilialToAttach.getFilialID());
                attachedFilialList.add(filialListFilialToAttach);
            }
            gestorDeFilial.setFilialList(attachedFilialList);
            List<RelatorioDiário> attachedRelatorioDiárioList = new ArrayList<RelatorioDiário>();
            for (RelatorioDiário relatorioDiárioListRelatorioDiárioToAttach : gestorDeFilial.getRelatorioDiárioList()) {
                relatorioDiárioListRelatorioDiárioToAttach = em.getReference(relatorioDiárioListRelatorioDiárioToAttach.getClass(), relatorioDiárioListRelatorioDiárioToAttach.getRelatorioDiárioID());
                attachedRelatorioDiárioList.add(relatorioDiárioListRelatorioDiárioToAttach);
            }
            gestorDeFilial.setRelatorioDiárioList(attachedRelatorioDiárioList);
            em.persist(gestorDeFilial);
            for (Filial filialListFilial : gestorDeFilial.getFilialList()) {
                GestorDeFilial oldGestorDeFilialIDOfFilialListFilial = filialListFilial.getGestorDeFilialID();
                filialListFilial.setGestorDeFilialID(gestorDeFilial);
                filialListFilial = em.merge(filialListFilial);
                if (oldGestorDeFilialIDOfFilialListFilial != null) {
                    oldGestorDeFilialIDOfFilialListFilial.getFilialList().remove(filialListFilial);
                    oldGestorDeFilialIDOfFilialListFilial = em.merge(oldGestorDeFilialIDOfFilialListFilial);
                }
            }
            for (RelatorioDiário relatorioDiárioListRelatorioDiário : gestorDeFilial.getRelatorioDiárioList()) {
                GestorDeFilial oldGestorDeFilialIDOfRelatorioDiárioListRelatorioDiário = relatorioDiárioListRelatorioDiário.getGestorDeFilialID();
                relatorioDiárioListRelatorioDiário.setGestorDeFilialID(gestorDeFilial);
                relatorioDiárioListRelatorioDiário = em.merge(relatorioDiárioListRelatorioDiário);
                if (oldGestorDeFilialIDOfRelatorioDiárioListRelatorioDiário != null) {
                    oldGestorDeFilialIDOfRelatorioDiárioListRelatorioDiário.getRelatorioDiárioList().remove(relatorioDiárioListRelatorioDiário);
                    oldGestorDeFilialIDOfRelatorioDiárioListRelatorioDiário = em.merge(oldGestorDeFilialIDOfRelatorioDiárioListRelatorioDiário);
                }
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

    public void edit(GestorDeFilial gestorDeFilial) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            GestorDeFilial persistentGestorDeFilial = em.find(GestorDeFilial.class, gestorDeFilial.getGestorDeFilialID());
            List<Filial> filialListOld = persistentGestorDeFilial.getFilialList();
            List<Filial> filialListNew = gestorDeFilial.getFilialList();
            List<RelatorioDiário> relatorioDiárioListOld = persistentGestorDeFilial.getRelatorioDiárioList();
            List<RelatorioDiário> relatorioDiárioListNew = gestorDeFilial.getRelatorioDiárioList();
            List<String> illegalOrphanMessages = null;
            for (Filial filialListOldFilial : filialListOld) {
                if (!filialListNew.contains(filialListOldFilial)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Filial " + filialListOldFilial + " since its gestorDeFilialID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Filial> attachedFilialListNew = new ArrayList<Filial>();
            for (Filial filialListNewFilialToAttach : filialListNew) {
                filialListNewFilialToAttach = em.getReference(filialListNewFilialToAttach.getClass(), filialListNewFilialToAttach.getFilialID());
                attachedFilialListNew.add(filialListNewFilialToAttach);
            }
            filialListNew = attachedFilialListNew;
            gestorDeFilial.setFilialList(filialListNew);
            List<RelatorioDiário> attachedRelatorioDiárioListNew = new ArrayList<RelatorioDiário>();
            for (RelatorioDiário relatorioDiárioListNewRelatorioDiárioToAttach : relatorioDiárioListNew) {
                relatorioDiárioListNewRelatorioDiárioToAttach = em.getReference(relatorioDiárioListNewRelatorioDiárioToAttach.getClass(), relatorioDiárioListNewRelatorioDiárioToAttach.getRelatorioDiárioID());
                attachedRelatorioDiárioListNew.add(relatorioDiárioListNewRelatorioDiárioToAttach);
            }
            relatorioDiárioListNew = attachedRelatorioDiárioListNew;
            gestorDeFilial.setRelatorioDiárioList(relatorioDiárioListNew);
            gestorDeFilial = em.merge(gestorDeFilial);
            for (Filial filialListNewFilial : filialListNew) {
                if (!filialListOld.contains(filialListNewFilial)) {
                    GestorDeFilial oldGestorDeFilialIDOfFilialListNewFilial = filialListNewFilial.getGestorDeFilialID();
                    filialListNewFilial.setGestorDeFilialID(gestorDeFilial);
                    filialListNewFilial = em.merge(filialListNewFilial);
                    if (oldGestorDeFilialIDOfFilialListNewFilial != null && !oldGestorDeFilialIDOfFilialListNewFilial.equals(gestorDeFilial)) {
                        oldGestorDeFilialIDOfFilialListNewFilial.getFilialList().remove(filialListNewFilial);
                        oldGestorDeFilialIDOfFilialListNewFilial = em.merge(oldGestorDeFilialIDOfFilialListNewFilial);
                    }
                }
            }
            for (RelatorioDiário relatorioDiárioListOldRelatorioDiário : relatorioDiárioListOld) {
                if (!relatorioDiárioListNew.contains(relatorioDiárioListOldRelatorioDiário)) {
                    relatorioDiárioListOldRelatorioDiário.setGestorDeFilialID(null);
                    relatorioDiárioListOldRelatorioDiário = em.merge(relatorioDiárioListOldRelatorioDiário);
                }
            }
            for (RelatorioDiário relatorioDiárioListNewRelatorioDiário : relatorioDiárioListNew) {
                if (!relatorioDiárioListOld.contains(relatorioDiárioListNewRelatorioDiário)) {
                    GestorDeFilial oldGestorDeFilialIDOfRelatorioDiárioListNewRelatorioDiário = relatorioDiárioListNewRelatorioDiário.getGestorDeFilialID();
                    relatorioDiárioListNewRelatorioDiário.setGestorDeFilialID(gestorDeFilial);
                    relatorioDiárioListNewRelatorioDiário = em.merge(relatorioDiárioListNewRelatorioDiário);
                    if (oldGestorDeFilialIDOfRelatorioDiárioListNewRelatorioDiário != null && !oldGestorDeFilialIDOfRelatorioDiárioListNewRelatorioDiário.equals(gestorDeFilial)) {
                        oldGestorDeFilialIDOfRelatorioDiárioListNewRelatorioDiário.getRelatorioDiárioList().remove(relatorioDiárioListNewRelatorioDiário);
                        oldGestorDeFilialIDOfRelatorioDiárioListNewRelatorioDiário = em.merge(oldGestorDeFilialIDOfRelatorioDiárioListNewRelatorioDiário);
                    }
                }
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
                Integer id = gestorDeFilial.getGestorDeFilialID();
                if (findGestorDeFilial(id) == null) {
                    throw new NonexistentEntityException("The gestorDeFilial with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            GestorDeFilial gestorDeFilial;
            try {
                gestorDeFilial = em.getReference(GestorDeFilial.class, id);
                gestorDeFilial.getGestorDeFilialID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gestorDeFilial with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Filial> filialListOrphanCheck = gestorDeFilial.getFilialList();
            for (Filial filialListOrphanCheckFilial : filialListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GestorDeFilial (" + gestorDeFilial + ") cannot be destroyed since the Filial " + filialListOrphanCheckFilial + " in its filialList field has a non-nullable gestorDeFilialID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<RelatorioDiário> relatorioDiárioList = gestorDeFilial.getRelatorioDiárioList();
            for (RelatorioDiário relatorioDiárioListRelatorioDiário : relatorioDiárioList) {
                relatorioDiárioListRelatorioDiário.setGestorDeFilialID(null);
                relatorioDiárioListRelatorioDiário = em.merge(relatorioDiárioListRelatorioDiário);
            }
            em.remove(gestorDeFilial);
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

    public List<GestorDeFilial> findGestorDeFilialEntities() {
        return findGestorDeFilialEntities(true, -1, -1);
    }

    public List<GestorDeFilial> findGestorDeFilialEntities(int maxResults, int firstResult) {
        return findGestorDeFilialEntities(false, maxResults, firstResult);
    }

    private List<GestorDeFilial> findGestorDeFilialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GestorDeFilial.class));
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

    public GestorDeFilial findGestorDeFilial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GestorDeFilial.class, id);
        } finally {
            em.close();
        }
    }

    public int getGestorDeFilialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GestorDeFilial> rt = cq.from(GestorDeFilial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
