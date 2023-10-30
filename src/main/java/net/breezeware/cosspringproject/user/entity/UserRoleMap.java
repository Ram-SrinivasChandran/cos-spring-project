package net.breezeware.cosspringproject.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(schema = "user_svc", name = "user_role_map")
public class UserRoleMap {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_role_map_seq_gen")
    @SequenceGenerator(name = "user_role_map_seq_gen", sequenceName = "user_role_map_seq",schema = "user_svc",allocationSize = 1)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private Role role;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "modified_on")
    private Instant modifiedOn;
}
