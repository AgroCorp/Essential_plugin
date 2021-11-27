package me.agronaut.essentials.Classes;

import me.agronaut.essentials.Essentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLcontroller {
    private static SQLcontroller instance;
    private static Connection connection;
    private Essentials plugin;
    private String host;
    private String port;
    private String db;
    private String username;
    private String password;

    public SQLcontroller(String host, String port, String db, String username, String password, Essentials plugin) {
        this.host = host;
        this.port = port;
        this.db = db;
        this.username = username;
        this.password = password;
        this.plugin = plugin;
    }

    private SQLcontroller() throws SQLException
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://"+host+":"+port+"/"+db, username, password);
        } catch (ClassNotFoundException e)
        {
            plugin.getLogger().warning("Database connection Creation failed: " + e.getMessage());
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public static SQLcontroller getInstance() throws SQLException
    {
        if (instance == null)
        {
            instance = new SQLcontroller();
        } else if (instance.getConnection().isClosed()){
            instance = new SQLcontroller();
        }
        return instance;
    }
}
