package me.agronaut.essentials.DAO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "player_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "uuid", nullable = false)
    private UUID uuid;
    @Column(name = "money")
    private Long money;
    @Column(name = "coord_x")
    private Long coordX;
    @Column(name = "coord_y")
    private Long coordY;
    @Column(name = "coord_z")
    private Long coordZ;
    @Column(name = "ip_address")
    private String ipAddress;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Permission_group_id")
    private PermissionGroup group;
}
