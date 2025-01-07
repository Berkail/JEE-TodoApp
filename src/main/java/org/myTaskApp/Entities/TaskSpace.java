package org.myTaskApp.Entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "task_spaces")
@Data
@EqualsAndHashCode(callSuper = true, exclude = "tasks")
@ToString(callSuper = true, exclude = "tasks")
public class TaskSpace extends TaskComponent {
	private String title;
	private String description;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "taskSpace", cascade = CascadeType.ALL)
	private Set<Task> tasks = new HashSet<>();
}