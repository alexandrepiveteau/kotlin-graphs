package io.github.alexandrepiveteau.graphs

// TODO : Document this.
@JvmInline
public value class VertexMap private constructor(private val array: VertexArray) {

  public constructor() : this(0)

  public constructor(size: Int) : this(VertexArray(size))

  public val size: Int
    get() = array.size

  public operator fun get(vertex: Vertex): Vertex {
    if (vertex.index < 0 || vertex.index >= array.size) throw NoSuchVertexException()
    return array[vertex.index]
  }

  public operator fun set(vertex: Vertex, value: Vertex) {
    if (vertex.index < 0 || vertex.index >= array.size) throw NoSuchVertexException()
    array[vertex.index] = value
  }

  public fun values(): VertexArray = array.copyOf()
}
