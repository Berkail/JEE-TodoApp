package org.myTaskApp.Services;

import org.myTaskApp.Entities.Task;
import org.myTaskApp.Entities.TaskSpace;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class TaskService {
    @PersistenceContext
    private EntityManager em;
    
    public Task createTask(TaskSpace taskSpace, String content, int priority) {
        Task task = new Task();
        task.setTaskSpace(taskSpace);
        task.setContent(content);
        task.setPriority(priority);
        task.setCompleted(false);
        task.setCreationDt(LocalDateTime.now());
        task.setLastModifiedDt(LocalDateTime.now());
        em.persist(task);
        return task;
    }
    
    public List<Task> getTaskSpaceTasks(TaskSpace taskSpace) {
        return em.createQuery(
            "SELECT t FROM Task t WHERE t.taskSpace = :taskSpace AND t.deletionDt IS NULL ORDER BY t.priority DESC",
            Task.class)
            .setParameter("taskSpace", taskSpace)
            .getResultList();
    }
    
    public Task getHighestPriorityTask(TaskSpace taskSpace) {
        return em.createQuery(
            "SELECT t FROM Task t WHERE t.taskSpace = :taskSpace AND t.isCompleted = false AND t.deletionDt IS NULL ORDER BY t.priority DESC",
            Task.class)
            .setParameter("taskSpace", taskSpace)
            .setMaxResults(1)
            .getResultStream()
            .findFirst()
            .orElse(null);
    }
    
    public boolean completeTask(Task task) {
        Task highestPriorityTask = getHighestPriorityTask(task.getTaskSpace());
        if (highestPriorityTask != null && highestPriorityTask.getId().equals(task.getId())) {
            task.setCompleted(true);
            task.setLastModifiedDt(LocalDateTime.now());
            em.merge(task);
            return true;
        }
        return false;
    }
    
    public void deleteTask(Task task) {
        task.setDeletionDt(LocalDateTime.now());
        em.merge(task);
    }
}
