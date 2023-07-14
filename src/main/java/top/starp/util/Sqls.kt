package top.starp.util


public val selectTableNames="""
SELECT 

table_name 

FROM

information_schema.tables 

WHERE table_schema = ? 

AND table_type = ? 



"""

val  selectTableCols="""
    select * from information_schema.columns
   where table_schema= ? and table_name = ? 
""".trimIndent()