package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.internal.IntVector

/**
 * A [GraphBuilder] is a mutable data structure which can be used to build a [Graph]. It is
 * implemented as a builder, and can be used to build a graph in a single expression.
 */
public fun interface GraphBuilder {

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

// BUILDER HELPERS

/**
 * A data structure which represents the data used to build a [Graph].
 *
 * @param size the number of vertices in the graph.
 * @param links the list of links in the graph.
 */
@PublishedApi
internal data class GraphData(val size: Int, val links: IntVector) {
  inline fun forEachLink(action: (Int, Int) -> Unit) {
    for (i in 0 until links.size step 2) action(links[i], links[i + 1])
  }
}

@PublishedApi
internal fun interface GraphDataAdapter {
  fun addLink(from: Vertex, to: Vertex)
}

@PublishedApi
internal inline fun <B : GraphBuilder> buildGraphData(
    scope: B.() -> Unit,
    context: (GraphDataAdapter, GraphBuilder) -> B,
): GraphData {
  var size = 0
  val links = IntVector()
  val adapter = GraphDataAdapter { from, to ->
    if (from.index < 0 || from.index >= size) throw NoSuchVertexException()
    if (to.index < 0 || to.index >= size) throw NoSuchVertexException()
    links += from.index
    links += to.index
  }
  val builder = GraphBuilder { Vertex(size++) }
  context(adapter, builder).apply(scope)
  return GraphData(size, links)
}
