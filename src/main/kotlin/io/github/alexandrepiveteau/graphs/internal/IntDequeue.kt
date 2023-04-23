package io.github.alexandrepiveteau.graphs.internal

/** A double-ended queue of integers. */
// TODO : Document this.
@PublishedApi
internal class IntDequeue {
  private var from: Int = 0
  private var buffer: IntArray = IntArray(16)
  var size: Int = 0
    private set

  fun peekFirst(): Int {
    if (size == 0) throw NoSuchElementException()
    return buffer[from]
  }

  fun peekLast(): Int {
    if (size == 0) throw NoSuchElementException()
    return buffer[(from + size - 1).mod(buffer.size)]
  }

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

  fun removeFirst(): Int {
    val first = peekFirst()
    from = (from + 1).mod(buffer.size)
    size--
    return first
  }

  fun removeLast(): Int {
    val last = peekLast()
    size--
    return last
  }

  fun toIntArray(): IntArray {
    val array = IntArray(size)
    // TODO : Chunk copy using copyInto.
    for (i in 0 until size) {
      array[i] = buffer[(from + i).mod(buffer.size)]
    }
    return array
  }
}
