package net.breezeware.cosspringproject.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_role_map")
public class UserRoleMap {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @OneToOne
    @JoinColumn(name = "id")
    private User userId;
    @OneToOne
    @JoinColumn(name = "id")
    private Role roleId;
    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "modified_on")
    private Instant modifiedOn;
}
