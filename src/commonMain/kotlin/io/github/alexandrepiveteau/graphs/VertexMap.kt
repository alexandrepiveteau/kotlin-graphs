package io.github.alexandrepiveteau.graphs

import kotlin.jvm.JvmInline

/**
 * Creates a new [VertexMap] with the given [size], with all elements initialized to the result of
 * calling the specified [init] function on the index of each element.
 *
 * @param size the number of elements in the map.
 * @param init the function used to initialize the value of each element.
 * @return a new [VertexMap] with the specified [size].
 */
public inline fun VertexMap(
    size: Int,
    init: (index: Vertex) -> Vertex,
): VertexMap {
  return VertexMap(size).apply {
    for (index in 0 ..< size) {
      this[Vertex(index)] = init(Vertex(index))
    }
  }
}

/**
 * A map of [Vertex] to [Vertex]. When targeting the JVM, instances of this class are represented as
 * `int[]`. The key vertices must be consecutive, starting from 0, and the [size] of the map will
 * remain fixed.
 */
@JvmInline
public value class VertexMap private constructor(private val array: VertexArray) {

  /** Creates a new [VertexMap] with no elements. */
  public constructor() : this(0)

  /** Creates a new [VertexMap] with the given [size], with all elements initialized to zero. */
  public constructor(size: Int) : this(VertexArray(size))

  /** Returns the number of elements in the map. */
  public val size: Int
    get() = array.size

  /**
   * Returns the map element for the given [vertex]. This method can be called using the index
   * operator.
   *
   * If the [vertex] is not in the map, throws a [NoSuchVertexException].
   */
  public operator fun get(vertex: Vertex): Vertex {
    if (vertex.index < 0 || vertex.index >= array.size) throw NoSuchVertexException()
    return array[vertex.index]
  }

  /**
   * Sets the element for the given [vertex] to the given [value]. This method can be called using
   * the index operator.
   *
   * If the [vertex] is not in the map, throws a [NoSuchVertexException].
   */
  public operator fun set(vertex: Vertex, value: Vertex) {
    if (vertex.index < 0 || vertex.index >= array.size) throw NoSuchVertexException()
    array[vertex.index] = value
  }

  /**
   * Returns a copy of the values in the map. Modifications to the returned array will not affect
   * the original map.
   */
  public fun values(): VertexArray = array.copyOf()
}

/**
 * Iterates over the [Vertex]s of this [VertexMap], and calls the specified [action] for each
 * vertex.
 *
 * @param action the action to be performed for each vertex.
 * @return the original [VertexMap].
 * @receiver the [VertexMap] to iterate over.
 */
public inline fun VertexMap.forEach(action: (Vertex, Vertex) -> Unit) {
  for (index in 0 ..< size) {
    val v = Vertex(index)
    action(v, this[v])
  }
}
