package com.glance.guolindev.logic.typechange

import java.util.*

/**
 * This class deals with blob type.
 *
 * @author guolin
 * @since 2020/11/8
 */

const val BLOB_FIELD_TYPE = "BLOB"

class BlobMap : OrmMap {

    override fun columnType2FieldType(columnType: String): String? {
        return when(columnType.toUpperCase(Locale.US)) {
            "BLOB" -> BLOB_FIELD_TYPE
            else -> null
        }
    }

}