package net.breezeware.cosspringproject.user.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a user role in the system.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Schema(description = "Role of the User")
@Table(schema = "user_svc", name = "role")
public class Role {
    /**
     * The unique identifier for the role .
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq_gen")
    @SequenceGenerator(name = "role_seq_gen", sequenceName = "role_seq", schema = "user_svc", allocationSize = 1)
    @Schema(description = "Role Id", example = "1")
    private long id;

    /**
     * The name of the role. It must be a valid and unique value.
     */
    @NotBlank(message = "Please Enter a Valid Name.")
    @Size(min = 1, max = 20)
    @Column(name = "name", length = 20, nullable = false)
    @Schema(description = "Role Name", example = "Admin")
    private String name;

    /**
     * A brief description of the role.
     */
    @NotBlank(message = "Please Enter a Valid Description.")
    @Size(min = 1, max = 20)
    @Column(name = "description", length = 20, nullable = false)
    @Schema(description = "Description of the Role", example = "Have All access")
    private String description;
    /**
     * The timestamp when this mapping was created.
     */
    @Column(name = "created_on")
    @Schema(description = "Role Created on", example = "2023-11-15T05:27:10.787Z")
    private Instant createdOn;
    /**
     * The timestamp when this mapping was last modified.
     */
    @Column(name = "modified_on")
    @Schema(description = "Role Modified on", example = "2023-11-15T05:27:10.787Z")
    private Instant modifiedOn;
}
