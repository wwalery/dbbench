package wwapp.dbbench;


import com.querydsl.core.types.dsl.*;

import com.querydsl.sql.RelationalPathBase;

/**
 * QExample1 is a Querydsl query type for Example1
 */
//@Generated("com.querydsl.codegen.EntitySerializer")
public class QExample1 extends RelationalPathBase<Example1> {

  private static final long serialVersionUID = -263496400L;

  public static final QExample1 example1 = new QExample1("example1", "", "example1");

  public final DateTimePath<java.util.Date> changeTime = createDateTime("changeTime", java.util.Date.class);

  public final StringPath data = createString("data");

  public final BooleanPath deleted = createBoolean("is_deleted");

  public final NumberPath<Long> exampleId = createNumber("example_Id", Long.class);

  public final NumberPath<Integer> intValue = createNumber("intValue", Integer.class);

  public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

  public final DateTimePath<java.util.Date> stopDate = createDateTime("stopDate", java.util.Date.class);

  public QExample1(String variable, String schema, String table) {
    super(Example1.class, variable, schema, table);
  }

}
