package io.github.alexandrepiveteau.graphs.internal.collections

/** A double-ended queue of [Int]. This is a specialization of [ArrayDeque] for [Int]. */
@PublishedApi
internal class IntDequeue {

  /** The index of the first element in the buffer. */
  private var from: Int = 0

  /** The [IntArray] with the elements of the [IntDequeue]. */
  private var buffer: IntArray = IntArray(16)

  /** The number of elements in the [IntDequeue]. */
  var size: Int = 0
    private set

  /**
   * Returns the first element in the [IntDequeue], or throws a [NoSuchElementException] if the
   * [IntDequeue] is empty.
   */
  fun peekFirst(): Int {
    if (size == 0) throw NoSuchElementException()
    return buffer[from]
  }

  /**
   * Returns the last element in the [IntDequeue], or throws a [NoSuchElementException] if the
   * [IntDequeue] is empty.
   */
  fun peekLast(): Int {
    if (size == 0) throw NoSuchElementException()
    return buffer[(from + size - 1).mod(buffer.size)]
  }

  /**
   * Prepends the [value] to the [IntDequeue].
   *
   * @param value the value to prepend.
   */
  fun addFirst(value: Int) {
    if (size == buffer.size) {
      val newBuffer = IntArray(buffer.size * 2)
      buffer.copyInto(newBuffer, 0, 0, size)
      buffer.copyInto(newBuffer, size, 0, size)
      buffer = newBuffer
    }
    from = (from - 1).mod(buffer.size)
    buffer[from] = value
    size++
  }

  /**
   * Appends the [value] to the [IntDequeue].
   *
   * @param value the value to append.
   */
  fun addLast(value: Int) {
    if (size == buffer.size) {
      val newBuffer = IntArray(buffer.size * 2)
      buffer.copyInto(newBuffer, 0, 0, size)
      buffer.copyInto(newBuffer, size, 0, size)
      buffer = newBuffer
    }
    buffer[(from + size).mod(buffer.size)] = value
    size++
  }

  /**
   * Removes and returns the first element in the [IntDequeue], or throws a [NoSuchElementException]
   * if the [IntDequeue] is empty.
   */
  fun removeFirst(): Int {
    val first = peekFirst()
    from = (from + 1).mod(buffer.size)
    size--
    return first
  }

  /**
   * Removes and returns the last element in the [IntDequeue], or throws a [NoSuchElementException]
   * if the [IntDequeue] is empty.
   */
  fun removeLast(): Int {
    val last = peekLast()
    size--
    return last
  }

  /** Copies the contents of the [IntDequeue] to an [IntArray], and returns it. */
  fun toIntArray(): IntArray {
    val array = IntArray(size)
    val end = minOf(from + size, buffer.size)
    val len = end - from
    buffer.copyInto(array, 0, from, end)
    buffer.copyInto(array, len, 0, size - len)
    return array
  }
}
