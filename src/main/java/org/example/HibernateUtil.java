package org.example;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Task.class);

        configuration.setProperty(
                "hibernate.connection.url",
                System.getenv().getOrDefault("TODO_DB_URL", "jdbc:mysql://localhost:3306/todo_db")
        );
        configuration.setProperty(
                "hibernate.connection.username",
                System.getenv().getOrDefault("TODO_DB_USER", "root")
        );
        configuration.setProperty(
                "hibernate.connection.password",
                System.getenv().getOrDefault("TODO_DB_PASSWORD", "")
        );

        return configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private HibernateUtil() {
    }
}
