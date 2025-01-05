package org.myTaskApp.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(exclude = "taskSpaces")
@ToString(exclude = "taskSpaces")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDateTime deletionDt;
    private LocalDateTime creationDt;
    private LocalDateTime lastConnDt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<TaskSpace> taskSpaces = new HashSet<>();
}
