package org.myTaskApp.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "tasks")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Task extends TaskComponent {
	private String content;
	private boolean isCompleted;
	private int priority;

	@ManyToOne
	@JoinColumn(name = "task_space_id", nullable = false)
	private TaskSpace taskSpace;
}