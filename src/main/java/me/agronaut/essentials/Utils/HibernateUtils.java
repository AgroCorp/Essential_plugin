package me.agronaut.essentials.Utils;

import me.agronaut.essentials.DAO.Groups;
import me.agronaut.essentials.DAO.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class HibernateUtils {
    private static SessionFactory sessionFactory;

    public static void initialize(){
        initialize("192.168.2.55", "admin", "Gaborka11", 3306, "Essentials_plugin");
    }

    public static void initialize(String ip, String username, String password, Integer port, String database){
        // A SessionFactory is set up once for an application!
        Map<String, String> settings = new HashMap<>();
        settings.put("connection.driver_class", "com.mysql.jdbc.Driver");
        settings.put("hibernate.dialect", "org.hibernate.dialect.MariaDB103Dialect");
        settings.put("hibernate.connection.url",
                "jdbc:mysql://"+ ip + ":" + (port != null? port.toString() : "3306") + "/" + database + "?createDatabaseIfNotExist=true");
        settings.put("hibernate.connection.username", username);
        settings.put("hibernate.connection.password", password);
        settings.put("hibernate.current_session_context_class", "thread");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.format_sql", "true");
        settings.put("hbm2ddl.auto", "update");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(settings).build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(Player.class);
        metadataSources.addAnnotatedClass(Groups.class);

        EnumSet<TargetType> enumSet = EnumSet.of(TargetType.DATABASE);
        SchemaExport export = new SchemaExport();
        Metadata metadata = metadataSources.buildMetadata();
        export.execute(enumSet, SchemaExport.Action.CREATE, metadata);

        // here we build the SessionFactory (Hibernate 5.4)
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    public static Session getHibernateSession() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    public static void closeHibernateSession(Session session) {
        session.getTransaction().commit();
        session.close();
    }
}
