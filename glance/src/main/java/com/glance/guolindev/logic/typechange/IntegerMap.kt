package com.glance.guolindev.logic.typechange

import java.util.*

/**
 * This class deals with integer type.
 *
 * @author guolin
 * @since 2020/11/8
 */

const val INTEGER_FIELD_TYPE = "INTEGER"

class IntegerMap : OrmMap {

    private val integerTypeList = listOf("INT", "INTEGER", "TINYINT", "SMALLINT", "MEDIUMINT", "BIGINT",
            "UNSIGNED BIG INT", "INT2", "INT8")

    override fun columnType2FieldType(columnType: String): String? {
        return when (columnType.toUpperCase(Locale.US)) {
            in integerTypeList -> INTEGER_FIELD_TYPE
            else -> null
        }
    }

}