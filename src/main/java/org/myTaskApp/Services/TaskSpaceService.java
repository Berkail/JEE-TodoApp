package org.myTaskApp.Services;

import org.myTaskApp.Entities.TaskSpace;
import org.myTaskApp.Entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class TaskSpaceService {
    @PersistenceContext
    private EntityManager em;
    
    public TaskSpace createTaskSpace(User user, String title, String description) {
        TaskSpace taskSpace = new TaskSpace();
        taskSpace.setUser(user);
        taskSpace.setTitle(title);
        taskSpace.setDescription(description);
        taskSpace.setCreationDt(LocalDateTime.now());
        taskSpace.setLastModifiedDt(LocalDateTime.now());
        em.persist(taskSpace);
        return taskSpace;
    }
    
    public List<TaskSpace> getUserTaskSpaces(User user) {
        return em.createQuery(
            "SELECT ts FROM TaskSpace ts WHERE ts.user = :user AND ts.deletionDt IS NULL ORDER BY ts.creationDt DESC",
            TaskSpace.class)
            .setParameter("user", user)
            .getResultList();
    }
    
    public TaskSpace getTaskSpace(Long id, User user) {
        return em.createQuery(
            "SELECT ts FROM TaskSpace ts WHERE ts.id = :id AND ts.user = :user AND ts.deletionDt IS NULL",
            TaskSpace.class)
            .setParameter("id", id)
            .setParameter("user", user)
            .getSingleResult();
    }
    
    public void deleteTaskSpace(TaskSpace taskSpace) {
        taskSpace.setDeletionDt(LocalDateTime.now());
        em.merge(taskSpace);
    }
}

