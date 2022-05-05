package me.agronaut.essentials.Utils;

import me.agronaut.essentials.DAO.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.schema.TargetType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class HibernateUtils {
    private static SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(HibernateUtils.class.getName());

    public static void initialize ()
    {
        logger.info("configure hibernate");
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();

        Metadata metadata = new MetadataSources(standardServiceRegistry).getMetadataBuilder().build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();

        logger.info("configuration is done");
    }

    public static Session getHibernateSession() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    public static void closeHibernateSession(Session session) {
        if (session != null) {
            session.getTransaction().commit();
            session.close();
        }
    }
}
