package com.glance.guolindev.logic.typechange

import java.util.*

/**
 * This class deals with text type.
 *
 * @author guolin
 * @since 2020/11/8
 */

const val TEXT_FIELD_TYPE = "TEXT"

class TextMap : OrmMap {

    override fun columnType2FieldType(columnType: String): String? {
        val upperColumnType = columnType.toUpperCase(Locale.US)
        return when {
            upperColumnType == "TEXT" ||
            upperColumnType == "CLOB" ||
            upperColumnType.startsWith("CHARACTER") ||
            upperColumnType.startsWith("VARCHAR") ||
            upperColumnType.startsWith("VARYING") ||
            upperColumnType.startsWith("NCHAR") ||
            upperColumnType.startsWith("NATIVE CHARACTER") ||
            upperColumnType.startsWith("NVARCHAR") -> TEXT_FIELD_TYPE
            else -> null
        }
    }

}