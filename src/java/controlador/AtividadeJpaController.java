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
import modelo.CoordenaçãoDesportiva;
import modelo.Filial;
import modelo.Professor;
import modelo.Matricula;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.Atividade;

/**
 *
 * @author mahomed
 */
public class AtividadeJpaController implements Serializable {

    public AtividadeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Atividade atividade) throws RollbackFailureException, Exception {
        if (atividade.getMatriculaList() == null) {
            atividade.setMatriculaList(new ArrayList<Matricula>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CoordenaçãoDesportiva coordenaçãoDesportivaID = atividade.getCoordenaçãoDesportivaID();
            if (coordenaçãoDesportivaID != null) {
                coordenaçãoDesportivaID = em.getReference(coordenaçãoDesportivaID.getClass(), coordenaçãoDesportivaID.getCoordenaçãoDesportivaID());
                atividade.setCoordenaçãoDesportivaID(coordenaçãoDesportivaID);
            }
            Filial filialID = atividade.getFilialID();
            if (filialID != null) {
                filialID = em.getReference(filialID.getClass(), filialID.getFilialID());
                atividade.setFilialID(filialID);
            }
            Professor professorID = atividade.getProfessorID();
            if (professorID != null) {
                professorID = em.getReference(professorID.getClass(), professorID.getProfessorID());
                atividade.setProfessorID(professorID);
            }
            List<Matricula> attachedMatriculaList = new ArrayList<Matricula>();
            for (Matricula matriculaListMatriculaToAttach : atividade.getMatriculaList()) {
                matriculaListMatriculaToAttach = em.getReference(matriculaListMatriculaToAttach.getClass(), matriculaListMatriculaToAttach.getMatriculaID());
                attachedMatriculaList.add(matriculaListMatriculaToAttach);
            }
            atividade.setMatriculaList(attachedMatriculaList);
            em.persist(atividade);
            if (coordenaçãoDesportivaID != null) {
                coordenaçãoDesportivaID.getAtividadeList().add(atividade);
                coordenaçãoDesportivaID = em.merge(coordenaçãoDesportivaID);
            }
            if (filialID != null) {
                filialID.getAtividadeList().add(atividade);
                filialID = em.merge(filialID);
            }
            if (professorID != null) {
                professorID.getAtividadeList().add(atividade);
                professorID = em.merge(professorID);
            }
            for (Matricula matriculaListMatricula : atividade.getMatriculaList()) {
                Atividade oldAtividadeIDOfMatriculaListMatricula = matriculaListMatricula.getAtividadeID();
                matriculaListMatricula.setAtividadeID(atividade);
                matriculaListMatricula = em.merge(matriculaListMatricula);
                if (oldAtividadeIDOfMatriculaListMatricula != null) {
                    oldAtividadeIDOfMatriculaListMatricula.getMatriculaList().remove(matriculaListMatricula);
                    oldAtividadeIDOfMatriculaListMatricula = em.merge(oldAtividadeIDOfMatriculaListMatricula);
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

    public void edit(Atividade atividade) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Atividade persistentAtividade = em.find(Atividade.class, atividade.getAtividadeID());
            CoordenaçãoDesportiva coordenaçãoDesportivaIDOld = persistentAtividade.getCoordenaçãoDesportivaID();
            CoordenaçãoDesportiva coordenaçãoDesportivaIDNew = atividade.getCoordenaçãoDesportivaID();
            Filial filialIDOld = persistentAtividade.getFilialID();
            Filial filialIDNew = atividade.getFilialID();
            Professor professorIDOld = persistentAtividade.getProfessorID();
            Professor professorIDNew = atividade.getProfessorID();
            List<Matricula> matriculaListOld = persistentAtividade.getMatriculaList();
            List<Matricula> matriculaListNew = atividade.getMatriculaList();
            List<String> illegalOrphanMessages = null;
            for (Matricula matriculaListOldMatricula : matriculaListOld) {
                if (!matriculaListNew.contains(matriculaListOldMatricula)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Matricula " + matriculaListOldMatricula + " since its atividadeID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (coordenaçãoDesportivaIDNew != null) {
                coordenaçãoDesportivaIDNew = em.getReference(coordenaçãoDesportivaIDNew.getClass(), coordenaçãoDesportivaIDNew.getCoordenaçãoDesportivaID());
                atividade.setCoordenaçãoDesportivaID(coordenaçãoDesportivaIDNew);
            }
            if (filialIDNew != null) {
                filialIDNew = em.getReference(filialIDNew.getClass(), filialIDNew.getFilialID());
                atividade.setFilialID(filialIDNew);
            }
            if (professorIDNew != null) {
                professorIDNew = em.getReference(professorIDNew.getClass(), professorIDNew.getProfessorID());
                atividade.setProfessorID(professorIDNew);
            }
            List<Matricula> attachedMatriculaListNew = new ArrayList<Matricula>();
            for (Matricula matriculaListNewMatriculaToAttach : matriculaListNew) {
                matriculaListNewMatriculaToAttach = em.getReference(matriculaListNewMatriculaToAttach.getClass(), matriculaListNewMatriculaToAttach.getMatriculaID());
                attachedMatriculaListNew.add(matriculaListNewMatriculaToAttach);
            }
            matriculaListNew = attachedMatriculaListNew;
            atividade.setMatriculaList(matriculaListNew);
            atividade = em.merge(atividade);
            if (coordenaçãoDesportivaIDOld != null && !coordenaçãoDesportivaIDOld.equals(coordenaçãoDesportivaIDNew)) {
                coordenaçãoDesportivaIDOld.getAtividadeList().remove(atividade);
                coordenaçãoDesportivaIDOld = em.merge(coordenaçãoDesportivaIDOld);
            }
            if (coordenaçãoDesportivaIDNew != null && !coordenaçãoDesportivaIDNew.equals(coordenaçãoDesportivaIDOld)) {
                coordenaçãoDesportivaIDNew.getAtividadeList().add(atividade);
                coordenaçãoDesportivaIDNew = em.merge(coordenaçãoDesportivaIDNew);
            }
            if (filialIDOld != null && !filialIDOld.equals(filialIDNew)) {
                filialIDOld.getAtividadeList().remove(atividade);
                filialIDOld = em.merge(filialIDOld);
            }
            if (filialIDNew != null && !filialIDNew.equals(filialIDOld)) {
                filialIDNew.getAtividadeList().add(atividade);
                filialIDNew = em.merge(filialIDNew);
            }
            if (professorIDOld != null && !professorIDOld.equals(professorIDNew)) {
                professorIDOld.getAtividadeList().remove(atividade);
                professorIDOld = em.merge(professorIDOld);
            }
            if (professorIDNew != null && !professorIDNew.equals(professorIDOld)) {
                professorIDNew.getAtividadeList().add(atividade);
                professorIDNew = em.merge(professorIDNew);
            }
            for (Matricula matriculaListNewMatricula : matriculaListNew) {
                if (!matriculaListOld.contains(matriculaListNewMatricula)) {
                    Atividade oldAtividadeIDOfMatriculaListNewMatricula = matriculaListNewMatricula.getAtividadeID();
                    matriculaListNewMatricula.setAtividadeID(atividade);
                    matriculaListNewMatricula = em.merge(matriculaListNewMatricula);
                    if (oldAtividadeIDOfMatriculaListNewMatricula != null && !oldAtividadeIDOfMatriculaListNewMatricula.equals(atividade)) {
                        oldAtividadeIDOfMatriculaListNewMatricula.getMatriculaList().remove(matriculaListNewMatricula);
                        oldAtividadeIDOfMatriculaListNewMatricula = em.merge(oldAtividadeIDOfMatriculaListNewMatricula);
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
                Integer id = atividade.getAtividadeID();
                if (findAtividade(id) == null) {
                    throw new NonexistentEntityException("The atividade with id " + id + " no longer exists.");
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
            Atividade atividade;
            try {
                atividade = em.getReference(Atividade.class, id);
                atividade.getAtividadeID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The atividade with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Matricula> matriculaListOrphanCheck = atividade.getMatriculaList();
            for (Matricula matriculaListOrphanCheckMatricula : matriculaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Atividade (" + atividade + ") cannot be destroyed since the Matricula " + matriculaListOrphanCheckMatricula + " in its matriculaList field has a non-nullable atividadeID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CoordenaçãoDesportiva coordenaçãoDesportivaID = atividade.getCoordenaçãoDesportivaID();
            if (coordenaçãoDesportivaID != null) {
                coordenaçãoDesportivaID.getAtividadeList().remove(atividade);
                coordenaçãoDesportivaID = em.merge(coordenaçãoDesportivaID);
            }
            Filial filialID = atividade.getFilialID();
            if (filialID != null) {
                filialID.getAtividadeList().remove(atividade);
                filialID = em.merge(filialID);
            }
            Professor professorID = atividade.getProfessorID();
            if (professorID != null) {
                professorID.getAtividadeList().remove(atividade);
                professorID = em.merge(professorID);
            }
            em.remove(atividade);
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

    public List<Atividade> findAtividadeEntities() {
        return findAtividadeEntities(true, -1, -1);
    }

    public List<Atividade> findAtividadeEntities(int maxResults, int firstResult) {
        return findAtividadeEntities(false, maxResults, firstResult);
    }

    private List<Atividade> findAtividadeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Atividade.class));
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

    public Atividade findAtividade(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Atividade.class, id);
        } finally {
            em.close();
        }
    }

    public int getAtividadeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Atividade> rt = cq.from(Atividade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
