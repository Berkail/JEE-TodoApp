package org.myTaskApp.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

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
