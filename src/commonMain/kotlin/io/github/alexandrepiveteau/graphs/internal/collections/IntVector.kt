package io.github.alexandrepiveteau.graphs.internal.collections

/** A vector of integers, which will always grow in size when new elements are inserted. */
@PublishedApi
internal class IntVector {

  /** The number of elements in the vector. */
  var size: Int = 0
    private set

  /** The internal buffer used to store the elements. */
  private var buffer: IntArray = IntArray(16)

  /** Returns the element at the given [index]. */
  operator fun get(index: Int): Int {
    if (index < 0 || index >= size) throw IndexOutOfBoundsException()
    return buffer[index]
  }

  /** Sets the element at the given [index] to the given [value]. */
  operator fun set(index: Int, value: Int) {
    if (index < 0 || index >= size) throw IndexOutOfBoundsException()
    buffer[index] = value
  }

  /** Appends the given [value] to the final position of the vector. */
  operator fun plusAssign(value: Int) {
    if (size == buffer.size) buffer = buffer.copyOf(buffer.size * 2)
    buffer[size] = value
    size++
  }

  /** Returns a copy of the vector as an [IntArray]. */
  fun toIntArray(): IntArray = buffer.copyOf(size)
}
