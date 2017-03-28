package wwapp.dbbench;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Walery Wysotsky <dev@wysotsky.info>
 */
public class HibernateUtil {

  //Annotation based configuration
  private static SessionFactory sessionFactory;

  private static SessionFactory buildSessionFactory() {
    try {
      // Create the SessionFactory from hibernate.cfg.xml
      AnnotationConfiguration configuration = new AnnotationConfiguration();
      configuration.configure();
      System.out.println("Hibernate Annotation Configuration loaded");

//      ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
//      System.out.println("Hibernate Annotation serviceRegistry created");

//      SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
      SessionFactory sessionFactory = configuration.buildSessionFactory();

      return sessionFactory;
    } catch (Throwable ex) {
      // Make sure you log the exception, as it might be swallowed
      System.err.println("Initial SessionFactory creation failed." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      sessionFactory = buildSessionFactory();
    }
    return sessionFactory;
  }

}
