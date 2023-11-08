package net.breezeware.cosspringproject.user.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(schema = "user_svc", name = "user_role_map")
public class UserRoleMap {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_map_seq_gen")
    @SequenceGenerator(name = "user_role_map_seq_gen", sequenceName = "user_role_map_seq", schema = "user_svc",
            allocationSize = 1)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "modified_on")
    private Instant modifiedOn;
}
