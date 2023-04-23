package io.github.alexandrepiveteau.graphs

import kotlin.jvm.JvmInline

/** Creates a new array of the specified [size], with all elements initialized to zero. */
public fun VertexArray(size: Int): VertexArray = VertexArray(IntArray(size))

/**
 * Creates a new array of the specified [size], where each element is initialized by calling the
 * [init] function.
 *
 * The function [init] is called for each array element sequentially starting from the first one.
 * * It should return the value for an array element given its index.
 */
public inline fun VertexArray(
    size: Int,
    init: (Int) -> Vertex,
): VertexArray = VertexArray(IntArray(size) { init(it).index })

/** Returns a [VertexArray] for the corresponding [IntArray]. */
public fun IntArray.asVertexArray(): VertexArray = VertexArray(this)

/**
 * An array of [Vertex]. When targeting the JVM, instances of this class are represented as `int[]`.
 *
 * @constructor Creates a new [VertexArray] with the given backing [IntArray].
 */
@JvmInline
public value class VertexArray
@PublishedApi
internal constructor(
    internal val array: IntArray,
) {
  /** Returns the number of elements in the array. */
  public val size: Int
    get() = array.size

  /**
   * Returns the array element at the given [index]. This method can be called using the index
   * operator.
   *
   * If the [index] is out of bounds of this array, throws an [IndexOutOfBoundsException] except in
   * Kotlin/JS where the behavior is unspecified.
   */
  public operator fun get(index: Int): Vertex = Vertex(array[index])

  /**
   * Sets the element at the given [index] to the given [value]. This method can be called using the
   * index operator.
   *
   * If the [index] is out of bounds of this array, throws an [IndexOutOfBoundsException] except in
   * Kotlin/JS where the behavior is unspecified.
   */
  public operator fun set(index: Int, value: Vertex) {
    array[index] = value.index
  }

  /** Creates an iterator over the elements of the array. */
  public operator fun iterator(): VertexIterator {
    val iterator = array.iterator()
    return object : VertexIterator() {
      override fun hasNext(): Boolean = iterator.hasNext()
      override fun nextVertex(): Vertex = Vertex(iterator.nextInt())
    }
  }
}

/** An iterator over a sequence of values of type `Vertex`. */
public abstract class VertexIterator : Iterator<Vertex> {
  override fun next(): Vertex = nextVertex()

  /** Returns the next value in the sequence without boxing. */
  public abstract fun nextVertex(): Vertex
}

/** Returns new array which is a copy of the original array. */
public fun VertexArray.copyOf(): VertexArray = VertexArray(array.copyOf())

/**
 * Returns new array which is a copy of the original array, resized to the given [newSize]. The copy
 * is either truncated or padded at the end with zero values if necessary.
 * - If [newSize] is less than the size of the original array, the copy array is truncated to the
 *   [newSize].
 * - If [newSize] is greater than the size of the original array, the extra elements in the copy
 *   array are filled with zero values.
 */
public fun VertexArray.copyOf(newSize: Int): VertexArray = VertexArray(array.copyOf(newSize))

/**
 * Copies this array or its subrange into the [destination] array and returns that array.
 *
 * It's allowed to pass the same array in the [destination] and even specify the subrange so that it
 * overlaps with the destination range.
 *
 * @param destination the array to copy to.
 * @param destinationOffset the position in the [destination] array to copy to, 0 by default.
 * @param startIndex the beginning (inclusive) of the subrange to copy, 0 by default.
 * @param endIndex the end (exclusive) of the subrange to copy, size of this array by default.
 * @return the [destination] array.
 * @throws IndexOutOfBoundsException or [IllegalArgumentException] when [startIndex] or [endIndex]
 *   is out of range of this array indices or when `startIndex > endIndex`.
 * @throws IndexOutOfBoundsException when the subrange doesn't fit into the [destination] array
 *   starting at the specified [destinationOffset], or when that index is out of the [destination]
 *   array indices range.
 */
public fun VertexArray.copyInto(
    destination: VertexArray,
    destinationOffset: Int = 0,
    startIndex: Int = 0,
    endIndex: Int = size,
): VertexArray =
    VertexArray(
        array.copyInto(
            destination = destination.array,
            destinationOffset = destinationOffset,
            startIndex = startIndex,
            endIndex = endIndex,
        ),
    )

/**
 * Returns a new array which is a copy of the specified range of the original array.
 *
 * @param fromIndex the start of the range (inclusive) to copy.
 * @param toIndex the end of the range (exclusive) to copy.
 * @throws IndexOutOfBoundsException if [fromIndex] is less than zero or [toIndex] is greater than
 *   the size of this array.
 * @throws IllegalArgumentException if [fromIndex] is greater than [toIndex].
 */
public fun VertexArray.copyOfRange(
    fromIndex: Int,
    toIndex: Int,
): VertexArray = VertexArray(array.copyOfRange(fromIndex, toIndex))

/**
 * Searches the array or the range of the array for the provided [element] using the binary search
 * algorithm. The array is expected to be sorted, otherwise the result is undefined.
 *
 * If the array contains multiple elements equal to the specified [element], there is no guarantee
 * which one will be found.
 *
 * @param element the to search for.
 * @param fromIndex the start of the range (inclusive) to search in, 0 by default.
 * @param toIndex the end of the range (exclusive) to search in, size of this array by default.
 * @return the index of the element, if it is contained in the array within the specified range;
 *   otherwise, the inverted insertion point `(-insertion point - 1)`. The insertion point is
 *   defined as the index at which the element should be inserted, so that the array (or the
 *   specified subrange of array) still remains sorted.
 * @throws IndexOutOfBoundsException if [fromIndex] is less than zero or [toIndex] is greater than
 *   the size of this array.
 * @throws IllegalArgumentException if [fromIndex] is greater than [toIndex].
 */
public fun VertexArray.binarySearch(
    element: Vertex,
    fromIndex: Int = 0,
    toIndex: Int = size,
): Int {
  if (fromIndex < 0) throw IndexOutOfBoundsException()
  if (toIndex > size) throw IndexOutOfBoundsException()
  if (fromIndex > toIndex) throw IllegalArgumentException()

  var low = fromIndex
  var high = toIndex - 1

  while (low <= high) {
    val mid = (low + high).ushr(1) // safe from overflows
    val midVal = get(mid)
    val cmp = midVal.index.compareTo(element.index)

    if (cmp < 0) {
      low = mid + 1
    } else if (cmp > 0) {
      high = mid - 1
    } else {
      return mid
    } // key found
  }
  return -(low + 1) // key not found
}
