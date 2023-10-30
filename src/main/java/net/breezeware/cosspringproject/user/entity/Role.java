package net.breezeware.cosspringproject.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "user_svc", name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "role_seq_gen")
    @SequenceGenerator(name = "role_seq_gen", sequenceName = "role_seq",schema = "user_svc",allocationSize = 1)
    private long id;

    @NotBlank(message = "Please Enter a Valid Name.")
    @Size(min = 1,max = 20)
    @Column(name = "name",length = 20,nullable = false)
    private String name;

    @NotBlank(message = "Please Enter a Valid Description.")
    @Size(min = 1,max = 20)
    @Column(name = "description",length = 20,nullable = false)
    private String description;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "modified_on")
    private Instant modifiedOn;
}
