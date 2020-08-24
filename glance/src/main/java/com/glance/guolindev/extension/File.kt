package com.glance.guolindev.extension

import java.io.File

/**
 * File extension methods.
 * @author guolin
 * @since 2020/8/24
 */
fun File.isDBFile() = name.endsWith(".db")