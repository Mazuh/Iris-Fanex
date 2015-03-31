/*
 * The MIT License
 *
 * Copyright 2015 mazuh.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package br.com.fanex.mazuh.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.fanex.mazuh.acesso.Usuario;
import br.com.fanex.mazuh.edu.Curso;
import br.com.fanex.mazuh.edu.Exercicio;
import br.com.fanex.mazuh.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author mazuh
 */
public class ExercicioJpaController implements Serializable {

    public ExercicioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Exercicio exercicio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idInstrutor = exercicio.getIdInstrutor();
            if (idInstrutor != null) {
                idInstrutor = em.getReference(idInstrutor.getClass(), idInstrutor.getId());
                exercicio.setIdInstrutor(idInstrutor);
            }
            Usuario idAluno = exercicio.getIdAluno();
            if (idAluno != null) {
                idAluno = em.getReference(idAluno.getClass(), idAluno.getId());
                exercicio.setIdAluno(idAluno);
            }
            Curso idCurso = exercicio.getIdCurso();
            if (idCurso != null) {
                idCurso = em.getReference(idCurso.getClass(), idCurso.getId());
                exercicio.setIdCurso(idCurso);
            }
            em.persist(exercicio);
            if (idInstrutor != null) {
                idInstrutor.getExercicioList().add(exercicio);
                idInstrutor = em.merge(idInstrutor);
            }
            if (idAluno != null) {
                idAluno.getExercicioList().add(exercicio);
                idAluno = em.merge(idAluno);
            }
            if (idCurso != null) {
                idCurso.getExercicioList().add(exercicio);
                idCurso = em.merge(idCurso);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Exercicio exercicio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Exercicio persistentExercicio = em.find(Exercicio.class, exercicio.getId());
            Usuario idInstrutorOld = persistentExercicio.getIdInstrutor();
            Usuario idInstrutorNew = exercicio.getIdInstrutor();
            Usuario idAlunoOld = persistentExercicio.getIdAluno();
            Usuario idAlunoNew = exercicio.getIdAluno();
            Curso idCursoOld = persistentExercicio.getIdCurso();
            Curso idCursoNew = exercicio.getIdCurso();
            if (idInstrutorNew != null) {
                idInstrutorNew = em.getReference(idInstrutorNew.getClass(), idInstrutorNew.getId());
                exercicio.setIdInstrutor(idInstrutorNew);
            }
            if (idAlunoNew != null) {
                idAlunoNew = em.getReference(idAlunoNew.getClass(), idAlunoNew.getId());
                exercicio.setIdAluno(idAlunoNew);
            }
            if (idCursoNew != null) {
                idCursoNew = em.getReference(idCursoNew.getClass(), idCursoNew.getId());
                exercicio.setIdCurso(idCursoNew);
            }
            exercicio = em.merge(exercicio);
            if (idInstrutorOld != null && !idInstrutorOld.equals(idInstrutorNew)) {
                idInstrutorOld.getExercicioList().remove(exercicio);
                idInstrutorOld = em.merge(idInstrutorOld);
            }
            if (idInstrutorNew != null && !idInstrutorNew.equals(idInstrutorOld)) {
                idInstrutorNew.getExercicioList().add(exercicio);
                idInstrutorNew = em.merge(idInstrutorNew);
            }
            if (idAlunoOld != null && !idAlunoOld.equals(idAlunoNew)) {
                idAlunoOld.getExercicioList().remove(exercicio);
                idAlunoOld = em.merge(idAlunoOld);
            }
            if (idAlunoNew != null && !idAlunoNew.equals(idAlunoOld)) {
                idAlunoNew.getExercicioList().add(exercicio);
                idAlunoNew = em.merge(idAlunoNew);
            }
            if (idCursoOld != null && !idCursoOld.equals(idCursoNew)) {
                idCursoOld.getExercicioList().remove(exercicio);
                idCursoOld = em.merge(idCursoOld);
            }
            if (idCursoNew != null && !idCursoNew.equals(idCursoOld)) {
                idCursoNew.getExercicioList().add(exercicio);
                idCursoNew = em.merge(idCursoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = exercicio.getId();
                if (findExercicio(id) == null) {
                    throw new NonexistentEntityException("The exercicio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Exercicio exercicio;
            try {
                exercicio = em.getReference(Exercicio.class, id);
                exercicio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The exercicio with id " + id + " no longer exists.", enfe);
            }
            Usuario idInstrutor = exercicio.getIdInstrutor();
            if (idInstrutor != null) {
                idInstrutor.getExercicioList().remove(exercicio);
                idInstrutor = em.merge(idInstrutor);
            }
            Usuario idAluno = exercicio.getIdAluno();
            if (idAluno != null) {
                idAluno.getExercicioList().remove(exercicio);
                idAluno = em.merge(idAluno);
            }
            Curso idCurso = exercicio.getIdCurso();
            if (idCurso != null) {
                idCurso.getExercicioList().remove(exercicio);
                idCurso = em.merge(idCurso);
            }
            em.remove(exercicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Exercicio> findExercicioEntities() {
        return findExercicioEntities(true, -1, -1);
    }

    public List<Exercicio> findExercicioEntities(int maxResults, int firstResult) {
        return findExercicioEntities(false, maxResults, firstResult);
    }

    private List<Exercicio> findExercicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Exercicio.class));
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

    public Exercicio findExercicio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Exercicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getExercicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Exercicio> rt = cq.from(Exercicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
