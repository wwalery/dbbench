package wwapp.dbbench;

import java.sql.Connection;
import javax.inject.Provider;

/**
 *
 * @author Walery Wysotsky <dev@wysotsky.info>
 */
public class SingleConnectionProvider implements Provider<Connection> {

  private Connection conn;

  public SingleConnectionProvider(Connection conn) {
    this.conn = conn;
  }

  @Override
  public Connection get() {
    return conn;
  }

}
