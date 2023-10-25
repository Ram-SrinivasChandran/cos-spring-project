package net.breezeware.cosspringproject.user.entity;

import net.breezeware.cosspringproject.user.enumeration.RoleOfPerson;

import javax.persistence.Entity;
import java.time.Instant;

@Entity
public class Role {
    private long id;
    private String role;
    private String description;
    private Instant createdOn;
    private Instant modifiedOn;
}
