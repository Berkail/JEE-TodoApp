package org.myTaskApp.Entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class TaskComponent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	protected LocalDateTime creationDt;
	@Column(nullable = true)
	protected LocalDateTime deletionDt;
	protected LocalDateTime lastModifiedDt;
}
