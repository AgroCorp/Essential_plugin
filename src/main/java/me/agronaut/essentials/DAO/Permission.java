package me.agronaut.essentials.DAO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "permission")
@Setter
@Getter
@RequiredArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "permission_id")
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    @NonNull
    private String name;
    @ManyToOne
    @JoinColumn(name = "permission_group", referencedColumnName = "permission_group_id", nullable = false)
    @NonNull
    private PermissionGroup group;
}
