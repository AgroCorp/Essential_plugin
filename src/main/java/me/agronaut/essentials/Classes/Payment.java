package me.agronaut.essentials.Classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
@ToString(includeFieldNames = false)
public class Payment {
    private Player from;
    private Player to;
    private int amount;
    private boolean confirmed;
}
