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
import modelo.Matricula;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.Aluno;
import modelo.Pagamento;

/**
 *
 * @author mahomed
 */
public class AlunoJpaController implements Serializable {

    public AlunoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Aluno aluno) throws RollbackFailureException, Exception {
        if (aluno.getMatriculaList() == null) {
            aluno.setMatriculaList(new ArrayList<Matricula>());
        }
        if (aluno.getPagamentoList() == null) {
            aluno.setPagamentoList(new ArrayList<Pagamento>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Matricula> attachedMatriculaList = new ArrayList<Matricula>();
            for (Matricula matriculaListMatriculaToAttach : aluno.getMatriculaList()) {
                matriculaListMatriculaToAttach = em.getReference(matriculaListMatriculaToAttach.getClass(), matriculaListMatriculaToAttach.getMatriculaID());
                attachedMatriculaList.add(matriculaListMatriculaToAttach);
            }
            aluno.setMatriculaList(attachedMatriculaList);
            List<Pagamento> attachedPagamentoList = new ArrayList<Pagamento>();
            for (Pagamento pagamentoListPagamentoToAttach : aluno.getPagamentoList()) {
                pagamentoListPagamentoToAttach = em.getReference(pagamentoListPagamentoToAttach.getClass(), pagamentoListPagamentoToAttach.getPagamentoID());
                attachedPagamentoList.add(pagamentoListPagamentoToAttach);
            }
            aluno.setPagamentoList(attachedPagamentoList);
            em.persist(aluno);
            for (Matricula matriculaListMatricula : aluno.getMatriculaList()) {
                Aluno oldAlunoIDOfMatriculaListMatricula = matriculaListMatricula.getAlunoID();
                matriculaListMatricula.setAlunoID(aluno);
                matriculaListMatricula = em.merge(matriculaListMatricula);
                if (oldAlunoIDOfMatriculaListMatricula != null) {
                    oldAlunoIDOfMatriculaListMatricula.getMatriculaList().remove(matriculaListMatricula);
                    oldAlunoIDOfMatriculaListMatricula = em.merge(oldAlunoIDOfMatriculaListMatricula);
                }
            }
            for (Pagamento pagamentoListPagamento : aluno.getPagamentoList()) {
                Aluno oldAlunoIDOfPagamentoListPagamento = pagamentoListPagamento.getAlunoID();
                pagamentoListPagamento.setAlunoID(aluno);
                pagamentoListPagamento = em.merge(pagamentoListPagamento);
                if (oldAlunoIDOfPagamentoListPagamento != null) {
                    oldAlunoIDOfPagamentoListPagamento.getPagamentoList().remove(pagamentoListPagamento);
                    oldAlunoIDOfPagamentoListPagamento = em.merge(oldAlunoIDOfPagamentoListPagamento);
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

    public void edit(Aluno aluno) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Aluno persistentAluno = em.find(Aluno.class, aluno.getAlunoID());
            List<Matricula> matriculaListOld = persistentAluno.getMatriculaList();
            List<Matricula> matriculaListNew = aluno.getMatriculaList();
            List<Pagamento> pagamentoListOld = persistentAluno.getPagamentoList();
            List<Pagamento> pagamentoListNew = aluno.getPagamentoList();
            List<String> illegalOrphanMessages = null;
            for (Matricula matriculaListOldMatricula : matriculaListOld) {
                if (!matriculaListNew.contains(matriculaListOldMatricula)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Matricula " + matriculaListOldMatricula + " since its alunoID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Matricula> attachedMatriculaListNew = new ArrayList<Matricula>();
            for (Matricula matriculaListNewMatriculaToAttach : matriculaListNew) {
                matriculaListNewMatriculaToAttach = em.getReference(matriculaListNewMatriculaToAttach.getClass(), matriculaListNewMatriculaToAttach.getMatriculaID());
                attachedMatriculaListNew.add(matriculaListNewMatriculaToAttach);
            }
            matriculaListNew = attachedMatriculaListNew;
            aluno.setMatriculaList(matriculaListNew);
            List<Pagamento> attachedPagamentoListNew = new ArrayList<Pagamento>();
            for (Pagamento pagamentoListNewPagamentoToAttach : pagamentoListNew) {
                pagamentoListNewPagamentoToAttach = em.getReference(pagamentoListNewPagamentoToAttach.getClass(), pagamentoListNewPagamentoToAttach.getPagamentoID());
                attachedPagamentoListNew.add(pagamentoListNewPagamentoToAttach);
            }
            pagamentoListNew = attachedPagamentoListNew;
            aluno.setPagamentoList(pagamentoListNew);
            aluno = em.merge(aluno);
            for (Matricula matriculaListNewMatricula : matriculaListNew) {
                if (!matriculaListOld.contains(matriculaListNewMatricula)) {
                    Aluno oldAlunoIDOfMatriculaListNewMatricula = matriculaListNewMatricula.getAlunoID();
                    matriculaListNewMatricula.setAlunoID(aluno);
                    matriculaListNewMatricula = em.merge(matriculaListNewMatricula);
                    if (oldAlunoIDOfMatriculaListNewMatricula != null && !oldAlunoIDOfMatriculaListNewMatricula.equals(aluno)) {
                        oldAlunoIDOfMatriculaListNewMatricula.getMatriculaList().remove(matriculaListNewMatricula);
                        oldAlunoIDOfMatriculaListNewMatricula = em.merge(oldAlunoIDOfMatriculaListNewMatricula);
                    }
                }
            }
            for (Pagamento pagamentoListOldPagamento : pagamentoListOld) {
                if (!pagamentoListNew.contains(pagamentoListOldPagamento)) {
                    pagamentoListOldPagamento.setAlunoID(null);
                    pagamentoListOldPagamento = em.merge(pagamentoListOldPagamento);
                }
            }
            for (Pagamento pagamentoListNewPagamento : pagamentoListNew) {
                if (!pagamentoListOld.contains(pagamentoListNewPagamento)) {
                    Aluno oldAlunoIDOfPagamentoListNewPagamento = pagamentoListNewPagamento.getAlunoID();
                    pagamentoListNewPagamento.setAlunoID(aluno);
                    pagamentoListNewPagamento = em.merge(pagamentoListNewPagamento);
                    if (oldAlunoIDOfPagamentoListNewPagamento != null && !oldAlunoIDOfPagamentoListNewPagamento.equals(aluno)) {
                        oldAlunoIDOfPagamentoListNewPagamento.getPagamentoList().remove(pagamentoListNewPagamento);
                        oldAlunoIDOfPagamentoListNewPagamento = em.merge(oldAlunoIDOfPagamentoListNewPagamento);
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
                Integer id = aluno.getAlunoID();
                if (findAluno(id) == null) {
                    throw new NonexistentEntityException("The aluno with id " + id + " no longer exists.");
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
            Aluno aluno;
            try {
                aluno = em.getReference(Aluno.class, id);
                aluno.getAlunoID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aluno with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Matricula> matriculaListOrphanCheck = aluno.getMatriculaList();
            for (Matricula matriculaListOrphanCheckMatricula : matriculaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Aluno (" + aluno + ") cannot be destroyed since the Matricula " + matriculaListOrphanCheckMatricula + " in its matriculaList field has a non-nullable alunoID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Pagamento> pagamentoList = aluno.getPagamentoList();
            for (Pagamento pagamentoListPagamento : pagamentoList) {
                pagamentoListPagamento.setAlunoID(null);
                pagamentoListPagamento = em.merge(pagamentoListPagamento);
            }
            em.remove(aluno);
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

    public List<Aluno> findAlunoEntities() {
        return findAlunoEntities(true, -1, -1);
    }

    public List<Aluno> findAlunoEntities(int maxResults, int firstResult) {
        return findAlunoEntities(false, maxResults, firstResult);
    }

    private List<Aluno> findAlunoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Aluno.class));
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

    public Aluno findAluno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Aluno.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlunoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Aluno> rt = cq.from(Aluno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
