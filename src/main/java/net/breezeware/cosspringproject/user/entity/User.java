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

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a user in the system.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Schema(description = "User")
@Table(schema = "user_svc", name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_name" }) })
public class User {
    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq", schema = "user_svc", allocationSize = 1)
    @Schema(description = "User Id", example = "1")
    private long id;

    /**
     * The name of the user. It must be a valid value.
     */
    @NotBlank(message = "Please Enter a Valid Name.")
    @Size(min = 1, max = 20)
    @Column(name = "name", length = 20, nullable = false)
    @Schema(description = "User Full Name",example = "Seenu")
    private String name;

    /**
     * The user's username. It must be a valid, unique value.
     */
    @NotBlank(message = "Please Enter a Valid UserName.")
    @Size(min = 1, max = 20)
    @Column(name = "user_name", unique = true, length = 20, nullable = false)
    @Schema(description = "User Name of the User",example = "seenu_01")
    private String userName;

    /**
     * The user's password. It must be a valid value.
     */
    @NotBlank(message = "Please Enter a Valid Password.")
    @Size(min = 1, max = 20)
    @Column(name = "password", length = 20, nullable = false)
    @Schema(description = "User's Password",example = "breeze123")
    private String password;
    /**
     * The timestamp when this mapping was created.
     */
    @Column(name = "created_on")
    @Schema(description = "User Created on",example = "2023-11-15T05:27:10.787Z")
    private Instant createdOn;
    /**
     * The timestamp when this mapping was last modified.
     */
    @Column(name = "modified_on")
    @Schema(description = "User Modified on",example = "2023-11-15T05:27:10.787Z")
    private Instant modifiedOn;
    /**
     * Marks the 'roleId' field as transient, indicating that it should not be
     * persisted in the database.
     */
    @Schema(description = "User Role Id",example = "1")
    @Transient
    private long roleId;
}
