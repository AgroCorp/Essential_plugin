package me.agronaut.essentials.DAO;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Player")
public class Player {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "player_id")
    private Long id;

    @Column(name = "name")
    private String name;
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
    @Column(name = "asd")
    private String asd;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Permission_group_id")
    private Groups group;
}
