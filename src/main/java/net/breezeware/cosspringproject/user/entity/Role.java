package net.breezeware.cosspringproject.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.cosspringproject.user.enumeration.RoleOfPerson;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "modified_on")
    private Instant modifiedOn;
}
