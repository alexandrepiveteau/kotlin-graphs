package io.github.alexandrepiveteau.graphs

/** A [MutableGraphScope] is scope which can be used to build a [Graph]. */
public fun interface MutableGraphScope {

  /** Adds a new vertex to the graph, and returns it. */
  public fun addVertex(): Vertex

  /** Returns a new [Vertices] instance. */
  public fun addVertices(): Vertices = Vertices { addVertex() }
}

/** A [Vertices] is a helper class which allows to create multiple [Vertex]s at once. */
public fun interface Vertices {
  public operator fun invoke(): Vertex
  public operator fun component1(): Vertex = invoke()
  public operator fun component2(): Vertex = invoke()
  public operator fun component3(): Vertex = invoke()
  public operator fun component4(): Vertex = invoke()
  public operator fun component5(): Vertex = invoke()
  public operator fun component6(): Vertex = invoke()
  public operator fun component7(): Vertex = invoke()
  public operator fun component8(): Vertex = invoke()
  public operator fun component9(): Vertex = invoke()
}
