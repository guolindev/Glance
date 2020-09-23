package com.glance.guolindev.exception

import java.lang.RuntimeException

/**
 * When a database column type is not supported by Glance, throw this exception.
 *
 * @author guolin
 * @since 2020/9/23
 */
class ColumnTypeUnsupportedException(message: String) : RuntimeException(message)