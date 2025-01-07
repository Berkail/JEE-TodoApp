package org.myTaskApp.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
	private int priority;
	private LocalDateTime completionDt;

	@ManyToOne
	@JoinColumn(name = "task_space_id", nullable = false)
	private TaskSpace taskSpace;
}