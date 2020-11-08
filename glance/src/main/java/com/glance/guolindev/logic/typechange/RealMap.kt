package com.glance.guolindev.logic.typechange

import java.util.*

/**
 * This class deals with real type.
 *
 * @author guolin
 * @since 2020/11/8
 */

const val REAL_FIELD_TYPE = "REAL"

class RealMap : OrmMap {

    private val realTypeList = listOf("REAL", "DOUBLE", "DOUBLE PRECISION", "FLOAT")

    override fun columnType2FieldType(columnType: String): String? {
        return when (columnType.toUpperCase(Locale.US)) {
            in realTypeList -> REAL_FIELD_TYPE
            else -> null
        }
    }

}