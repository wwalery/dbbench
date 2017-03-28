package wwapp.dbbench;


import com.querydsl.core.types.dsl.*;

import com.querydsl.sql.RelationalPathBase;


/**
 * QExample2 is a Querydsl query type for Example2
 */
//@Generated("com.querydsl.codegen.EntitySerializer")
public class QExample2 extends RelationalPathBase<Example2> {

    private static final long serialVersionUID = -263496399L;

    public static final QExample2 example2 = new QExample2("example2", "", "example2");

    public final NumberPath<Long> example2Id = createNumber("example2_Id", Long.class);

    public final NumberPath<Long> exampleId = createNumber("example_Id", Long.class);

    public final StringPath value1 = createString("value1");

    public final StringPath value2 = createString("value2");

    public final StringPath value3 = createString("value3");

    public final StringPath value4 = createString("value4");

    
    public QExample2(String variable, String schema, String table) {
        super(Example2.class, variable, schema, table);
    }

}

