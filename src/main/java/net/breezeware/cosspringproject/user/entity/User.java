package net.breezeware.cosspringproject.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "user_svc", name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq",schema = "user_svc",allocationSize = 1)
    private long id;
    @NotBlank(message = "Please Enter a Valid Name.")
    @Size(min = 1,max = 20)
    @Column(name = "name")
    private String name;
    @NotBlank(message = "Please Enter a Valid UserName.")
    @Size(min = 1,max = 20)
    @Column(name = "user_name")
    private String userName;
    @NotBlank(message = "Please Enter a Valid Password.")
    @Size(min = 1,max = 20)
    @Column(name = "password")
    private String password;
    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "modified_on")
    private Instant modifiedOn;

}
