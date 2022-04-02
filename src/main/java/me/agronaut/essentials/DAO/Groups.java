package me.agronaut.essentials.DAO;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Permission_group")
@Setter
@Getter
public class Groups {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "Permission_group_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ElementCollection
    @CollectionTable(name = "Permissions", joinColumns = @JoinColumn(name = "Permission_group_id"))
    @Column(name = "permission")
    private List<String> permissions;
}
