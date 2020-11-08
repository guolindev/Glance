package com.glance.guolindev.logic.typechange

/**
 * This is the interface to map the object field types to database column types. The purpose of this
 * interface is to define a method, and let all subclasses implement it. Each subclass deals the
 * mapping work for one type and each subclass will do their own logic to finish the mapping job.
 *
 * The mapping rule should follow the SQLite3 data types definition in this link:
 * https://www.sqlite.org/datatype3.html
 *
 * @author guolin
 * @since 2020/11/7
 */
interface OrmMap {

    /**
     * Subclasses implement this method to to get corresponding field type with column type.
     */
    fun columnType2FieldType(columnType: String): String?

}