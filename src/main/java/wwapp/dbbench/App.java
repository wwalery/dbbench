package wwapp.dbbench;

import com.querydsl.core.types.Projections;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.HSQLDBTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.inject.Provider;
import org.hibernate.Session;
import org.wwlib.db.Database;

/**
 *
 * @author Walery Wysotsky <dev@wysotsky.info>
 */
public class App {
  
  private final static int TEST_COUNT = 10_000;
  
  public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
    if (args.length == 0) {
      System.err.println("Usage: App 1|2|3");
      System.err.println(" 1 - pseudoJDO test");
      System.err.println(" 2 - hibernate test");
      System.err.println(" 3 - QueryDSL test");
    }
    System.err.println(new File(".").getAbsolutePath());
    switch (args[0]) {
      case "1":
// Open/close connections for each test to guarantee clear all internal caches
        DriverManager.registerDriver((Driver) Class.forName("org.hsqldb.jdbc.JDBCDriver").newInstance());
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:example/example", "sa", "");
        Database db = new Database(conn);
        testPseudoJDOInsert(db);
        conn.close();
        conn = DriverManager.getConnection("jdbc:hsqldb:example/example", "sa", "");
        db = new Database(conn);
        testPseudoJDOSelect(db);
        conn.close();
        conn = DriverManager.getConnection("jdbc:hsqldb:example/example", "sa", "");
        db = new Database(conn);
        testPseudoJDOUpdate(db);
        conn.close();
        break;
      case "2":
        Session session = HibernateUtil.getSessionFactory().openSession();
        testHibernateInsert(session);
        HibernateUtil.getSessionFactory().close();
        session = HibernateUtil.getSessionFactory().openSession();
        testHibernateSelect(session);
        HibernateUtil.getSessionFactory().close();
        session = HibernateUtil.getSessionFactory().openSession();
        testHibernateUpdate(session);
        HibernateUtil.getSessionFactory().close();
        break;
      case "3":
        DriverManager.registerDriver((Driver) Class.forName("org.hsqldb.jdbc.JDBCDriver").newInstance());
        conn = DriverManager.getConnection("jdbc:hsqldb:example/example", "sa", "");
        db = new Database(conn);
        testQueryDSLInsert(db);
        conn.close();
        conn = DriverManager.getConnection("jdbc:hsqldb:example/example", "sa", "");
        db = new Database(conn);
        testQueryDSLSelect(db);
        conn.close();
        conn = DriverManager.getConnection("jdbc:hsqldb:example/example", "sa", "");
        db = new Database(conn);
        testQueryDSLUpdate(db);
        conn.close();
        break;
      default:
        System.err.println("Invalid argument: [" + args[0] + "]");
    }
    
  }
  
  public static void testHibernateInsert(Session session) {
    long startTime = System.currentTimeMillis();
    //start transaction    
    session.beginTransaction();
    for (int i = 0; i < TEST_COUNT; i++) {
      Example1 ex = new Example1((long) i, new Date(), new Date(), Integer.toString(i + 33), false, new Date(), i + 20);
      session.save(ex);
      Example2 ex2 = new Example2((long) i, (long) i, Integer.toString(i + 100), Integer.toString(i + 101), Integer.toString(i + 102), Integer.toString(i + 103));
      session.save(ex2);
    }
    session.getTransaction().commit();
    System.out.println("Hibernate insert execution time: " + (System.currentTimeMillis() - startTime));
  }
  
  public static void testHibernateSelect(Session session) {
    long startTime = System.currentTimeMillis();
    Random rnd = new Random(startTime);
    //start transaction    
    session.beginTransaction();
    for (int i = 0; i < TEST_COUNT; i++) {
      int key = rnd.nextInt(TEST_COUNT);
      Example1 ex = (Example1) session.get(Example1.class, (long) key);
      key = rnd.nextInt(TEST_COUNT);
      Example2 ex2 = (Example2) session.get(Example2.class, (long) key);
    }
    session.getTransaction().commit();
    System.out.println("Hibernate select execution time: " + (System.currentTimeMillis() - startTime));
  }
  
  public static void testHibernateUpdate(Session session) {
    long startTime = System.currentTimeMillis();
    Random rnd = new Random(startTime);
    //start transaction    
    session.beginTransaction();
    for (int i = 0; i < TEST_COUNT; i++) {
      int key = rnd.nextInt(TEST_COUNT);
      Example1 ex = (Example1) session.get(Example1.class, (long) key);
      ex.data = Integer.toHexString(i + 200);
      session.save(ex);
      key = rnd.nextInt(TEST_COUNT);
      Example2 ex2 = (Example2) session.get(Example2.class, (long) key);
      ex2.value3 = Integer.toHexString(i + 155);
      session.save(ex2);
    }
    session.getTransaction().commit();
    System.out.println("Hibernate update execution time: " + (System.currentTimeMillis() - startTime));
  }
  
  public static void testPseudoJDOInsert(Database db) throws SQLException {
    long startTime = System.currentTimeMillis();
    //start transaction    
    for (int i = 0; i < TEST_COUNT; i++) {
      example.pjdo.db.Example1 ex = new example.pjdo.db.Example1(db, (long) i, LocalDate.now(), LocalDate.now(), Integer.toString(i + 33), false, LocalDateTime.now(), i + 20);
      ex.insert();
      example.pjdo.db.Example2 ex2 = new example.pjdo.db.Example2(db, (long) i, (long) i, Integer.toString(i + 100), Integer.toString(i + 101), Integer.toString(i + 102), Integer.toString(i + 103));
      ex2.insert();
    }
    db.commit();
    System.out.println("PseudoJDO insert execution time: " + (System.currentTimeMillis() - startTime));
  }
  
  public static void testPseudoJDOSelect(Database db) throws SQLException {
    long startTime = System.currentTimeMillis();
    Random rnd = new Random(startTime);
    //start transaction    
    for (int i = 0; i < TEST_COUNT; i++) {
      int key = rnd.nextInt(TEST_COUNT);
      example.pjdo.db.Example1 ex = new example.pjdo.db.Example1(db);
      ex.getById(key);
      example.pjdo.db.Example2 ex2 = new example.pjdo.db.Example2(db);
      ex2.getByMasterId(key);
    }
    db.commit();
    System.out.println("PseudoJDO select execution time: " + (System.currentTimeMillis() - startTime));
  }
  
  public static void testPseudoJDOUpdate(Database db) throws SQLException {
    long startTime = System.currentTimeMillis();
    Random rnd = new Random(startTime);
    //start transaction    
    for (int i = 0; i < TEST_COUNT; i++) {
      int key = rnd.nextInt(TEST_COUNT);
      example.pjdo.db.Example1 ex = new example.pjdo.db.Example1(db);
      ex.getById(key);
      ex.setData(Integer.toHexString(i + 200));
      ex.update();
      example.pjdo.db.Example2 ex2 = new example.pjdo.db.Example2(db);
      ex2.getByMasterId(key);
      ex2.setValue3(Integer.toHexString(i + 155));
      ex2.update();
    }
    db.commit();
    System.out.println("PseudoJDO update execution time: " + (System.currentTimeMillis() - startTime));
  }
  
  public static void testQueryDSLInsert(Database db) throws SQLException {
    long startTime = System.currentTimeMillis();
    //start transaction    
    SQLTemplates templates = new HSQLDBTemplates();
    Configuration configuration = new Configuration(templates);
    Provider<Connection> connProvider = new SingleConnectionProvider(db.getConnection());
    SQLQueryFactory queryFactory = new SQLQueryFactory(configuration, connProvider);
    SQLTemplates dialect = new HSQLDBTemplates();
    for (int i = 0; i < TEST_COUNT; i++) {
      QExample1 example1 = QExample1.example1;
      queryFactory.insert(example1)
        .columns(example1.exampleId, example1.startDate, example1.stopDate,
                 example1.data, example1.deleted, example1.changeTime, example1.intValue)
        .values((long) i, new Date(), new Date(), Integer.toString(i + 33), false, new Date(), i + 20).execute();
      QExample2 example2 = QExample2.example2;
      queryFactory.insert(example2)
        .columns(example2.example2Id, example2.exampleId, example2.value1,
                 example2.value2, example2.value3, example2.value4)
        .values((long) i, (long) i, Integer.toString(i + 100), Integer.toString(i + 101), Integer.toString(i + 102), Integer.toString(i + 103)).execute();
    }
    db.commit();
    System.out.println("QueryDSL insert execution time: " + (System.currentTimeMillis() - startTime));
  }
  
  public static void testQueryDSLSelect(Database db) throws SQLException {
    long startTime = System.currentTimeMillis();
    Random rnd = new Random(startTime);
    //start transaction    
    SQLTemplates templates = new HSQLDBTemplates();
    Configuration configuration = new Configuration(templates);
    Provider<Connection> connProvider = new SingleConnectionProvider(db.getConnection());
    SQLQueryFactory queryFactory = new SQLQueryFactory(configuration, connProvider);
    SQLTemplates dialect = new HSQLDBTemplates();
    for (int i = 0; i < TEST_COUNT; i++) {
      int key = rnd.nextInt(TEST_COUNT);
      QExample1 example1 = QExample1.example1;
      queryFactory.select(example1.exampleId, example1.startDate, example1.stopDate,
                          example1.data, example1.deleted, example1.changeTime, example1.intValue).from(example1)
        .where(example1.exampleId.eq(new Long(key)))
        .fetch();
      
      QExample2 example2 = QExample2.example2;
      queryFactory.select(example2.example2Id, example2.exampleId, example2.value1,
                          example2.value2, example2.value3, example2.value4).from(example2)
        .where(example2.exampleId.eq(new Long(key)))
        .fetch();
    }
    db.commit();
    System.out.println("QueryDSL select execution time: " + (System.currentTimeMillis() - startTime));
  }
  
  public static void testQueryDSLUpdate(Database db) throws SQLException {
    long startTime = System.currentTimeMillis();
    Random rnd = new Random(startTime);
    //start transaction    
    SQLTemplates templates = new HSQLDBTemplates();
    Configuration configuration = new Configuration(templates);
    Provider<Connection> connProvider = new SingleConnectionProvider(db.getConnection());
    SQLQueryFactory queryFactory = new SQLQueryFactory(configuration, connProvider);
    SQLTemplates dialect = new HSQLDBTemplates();
    for (int i = 0; i < TEST_COUNT; i++) {
      int key = rnd.nextInt(TEST_COUNT);
      QExample1 example1 = QExample1.example1;
      List<Example1> res1 = queryFactory.select(Projections.fields(Example1.class, example1.exampleId, example1.startDate, example1.stopDate,
                                                                   example1.data, example1.deleted, example1.changeTime, example1.intValue))
        .from(example1)
        .where(example1.exampleId.eq(new Long(key)))
        .fetch();
      queryFactory.update(example1)
        .where(example1.exampleId.eq(new Long(key)))
        .set(example1.data, Integer.toHexString(i + 200))
        .execute();
      
      QExample2 example2 = QExample2.example2;
      List<Example2> res2 = queryFactory.select(Projections.fields(Example2.class, example2.example2Id, example2.exampleId, example2.value1,
                                                                   example2.value2, example2.value3, example2.value4))
        .from(example2)
        .where(example2.exampleId.eq(new Long(key)))
        .fetch();
      
      queryFactory.update(example2)
        .where(example2.example2Id.eq(new Long(key)))
        .set(example2.value3, Integer.toHexString(i + 155))
        .execute();
    }
    db.commit();
    System.out.println("QueryDSL update execution time: " + (System.currentTimeMillis() - startTime));
  }
}
