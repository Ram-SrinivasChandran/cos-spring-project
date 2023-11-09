package net.breezeware.cosspringproject.user.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a user in the system.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(schema = "user_svc", name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_name" }) })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq", schema = "user_svc", allocationSize = 1)
    private long id;

    /**
     * The name of the user. It must be a valid value.
     */
    @NotBlank(message = "Please Enter a Valid Name.")
    @Size(min = 1, max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    /**
     * The user's username. It must be a valid, unique value.
     */
    @NotBlank(message = "Please Enter a Valid UserName.")
    @Size(min = 1, max = 20)
    @Column(name = "user_name", unique = true, length = 20, nullable = false)
    private String userName;

    /**
     * The user's password. It must be a valid value.
     */
    @NotBlank(message = "Please Enter a Valid Password.")
    @Size(min = 1, max = 20)
    @Column(name = "password", length = 20, nullable = false)
    private String password;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "modified_on")
    private Instant modifiedOn;

    @Transient
    private long roleId;
}
