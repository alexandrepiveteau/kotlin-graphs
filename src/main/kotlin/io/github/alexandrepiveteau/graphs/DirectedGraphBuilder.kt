package io.github.alexandrepiveteau.graphs

/** A [DirectedGraphBuilder] is a [GraphBuilder] for [DirectedGraph]s. */
public interface DirectedGraphBuilder : GraphBuilder {

  /**
   * Adds a new arc to the graph.
   *
   * @param arc the arc to add.
   */
  public fun addArc(arc: Arc)
}

/**
 * Builds a [DirectedGraph] using the given [scope].
 *
 * @param scope the scope in which the graph is built.
 * @return the newly built graph.
 */
public inline fun buildDirectedGraph(scope: DirectedGraphBuilder.() -> Unit): DirectedGraph {
  val data =
      buildGraphData<DirectedGraphBuilder>(scope) { adapter, builder ->
        object : DirectedGraphBuilder, GraphBuilder by builder {
          override fun addArc(arc: Arc) = adapter.addLink(arc.component1(), arc.component2())
        }
      }
  // TODO : Use a more compact representation depending on the density of the graph.
  val matrix = Array(data.size) { BooleanArray(data.size) }
  data.forEachLink { u, v -> matrix[u][v] = true }
  return AdjacencyMatrixDirectedGraph(matrix)
}
