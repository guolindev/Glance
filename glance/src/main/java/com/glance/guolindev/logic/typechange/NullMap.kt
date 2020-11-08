package com.glance.guolindev.logic.typechange

import java.util.*

/**
 * This class deals with null type.
 *
 * @author guolin
 * @since 2020/11/8
 */

const val NULL_FIELD_TYPE = "NULL"

class NullMap : OrmMap {

    override fun columnType2FieldType(columnType: String): String? {
        return when(columnType.toUpperCase(Locale.US)) {
            "null" -> NULL_FIELD_TYPE
            else -> null
        }
    }

}