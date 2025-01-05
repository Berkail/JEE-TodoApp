package org.myTaskApp.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.util.HashSet;
import java.util.Set;

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