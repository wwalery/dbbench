package wwapp.dbbench;

import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Walery Wysotsky <dev@wysotsky.info>
 */
@Entity
public class Example1 {

  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Example_Id", nullable = false, unique = true, length = 12)
  public long exampleId;

  @Column(name = "StartDate", nullable = false)
  public Date startDate;

  @Column(name = "StopDate", nullable = false)
  public Date stopDate;

  @Column(name = "Data", nullable = true)
  public String data;

  @Column(name = "Is_Deleted", nullable = false)
  public boolean deleted;

  @Column(name = "ChangeTime", nullable = true)
  public Date changeTime;

  @Column(name = "IntValue", nullable = false)
  public int intValue;

  public Example1(long exampleId, Date startDate, Date stopDate, String data, boolean deleted, Date changeTime, int intValue) {
    this.exampleId = exampleId;
    this.startDate = startDate;
    this.stopDate = stopDate;
    this.data = data;
    this.deleted = deleted;
    this.changeTime = changeTime;
    this.intValue = intValue;
  }

  public Example1() {
    
  }
  
}
