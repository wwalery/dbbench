package wwapp.dbbench;

import javax.persistence.*;

/**
 *
 * @author Walery Wysotsky <dev@wysotsky.info>
 */
@Entity
public class Example2 {

  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Example2_Id", nullable = false, unique = true, length = 12)
  public long example2Id;

  @Column(name = "Example_Id", nullable = true, length = 12)
  public long exampleId;

  @Column(name = "Value1", nullable = false)
  public String value1;

  @Column(name = "Value2", nullable = false)
  public String value2;

  @Column(name = "Value3", nullable = false)
  public String value3;

  @Column(name = "Value4", nullable = false)
  public String value4;

  public Example2(long example2Id, long exampleId, String value1, String value2, String value3, String value4) {
    this.example2Id = example2Id;
    this.exampleId = exampleId;
    this.value1 = value1;
    this.value2 = value2;
    this.value3 = value3;
    this.value4 = value4;
  }

  public Example2() {

  }

}
