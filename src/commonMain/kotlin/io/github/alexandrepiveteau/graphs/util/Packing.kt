package io.github.alexandrepiveteau.graphs.util

/** Packs two [Int] values into one [Long] value for use in inline classes. */
@PublishedApi
internal fun packInts(
    val1: Int,
    val2: Int,
): Long = val1.toLong().shl(32) or (val2.toLong() and 0xFFFFFFFF)

/** Unpacks the first [Int] value in [packInts] from its returned [Long]. */
@PublishedApi internal fun unpackInt1(value: Long): Int = value.shr(32).toInt()

/** Unpacks the second [Int] value in [packInts] from its returned [Long]. */
@PublishedApi internal fun unpackInt2(value: Long): Int = value.and(0xFFFFFFFF).toInt()
