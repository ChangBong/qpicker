package com.peopulley.core.dialet;

import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MySQL57DialectCustom extends MySQL57Dialect {
    public MySQL57DialectCustom() {
        super();
        registerFunction("GROUP_CONCAT", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));

        registerFunction( "DATE_SUB", new SQLFunctionTemplate(StandardBasicTypes.INTEGER
                , "date_sub(?1, INTERVAL ?2 ?3)")
        );

        registerFunction( "AES_CAST", new SQLFunctionTemplate(StandardBasicTypes.STRING
                , "cast(aes_decrypt(from_base64(?1), ?2) as nchar)")
        );
    }
}
