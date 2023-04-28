@file:JvmName("GraphBuilders")
@file:JvmMultifileClass

package io.github.alexandrepiveteau.graphs.builder

import io.github.alexandrepiveteau.graphs.Edge
import io.github.alexandrepiveteau.graphs.MutableUndirectedGraphScope
import io.github.alexandrepiveteau.graphs.UndirectedGraph
import io.github.alexandrepiveteau.graphs.internal.graphs.AdjacencyListUndirectedGraph
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

/** A [GraphBuilder] for [UndirectedGraph]s. */
public interface UndirectedGraphBuilder : GraphBuilder, MutableUndirectedGraphScope {
  override fun toGraph(): UndirectedGraph
}

/** Returns a new [UndirectedGraphBuilder] instance. */
@JvmName("undirectedGraph")
public fun UndirectedGraph.Companion.builder(): UndirectedGraphBuilder =
    MutableListUndirectedGraphBuilder()

/**
 * Builds a [UndirectedGraph] using the given [scope].
 *
 * @param scope the scope in which the graph is built.
 * @return the newly built graph.
 */
public inline fun buildUndirectedGraph(
    scope: MutableUndirectedGraphScope.() -> Unit,
): UndirectedGraph = UndirectedGraph.builder().apply(scope).toGraph()

/** A [MutableListGraphBuilder] for [UndirectedGraphBuilder]. */
private class MutableListUndirectedGraphBuilder :
    MutableListGraphBuilder(), UndirectedGraphBuilder {
  override fun addEdge(edge: Edge) {
    val (u, v) = edge
    checkLink(u, v)
    neighbors[u.index] += v.index
    neighbors[v.index] += u.index
  }
  override fun toGraph(): UndirectedGraph =
      AdjacencyListUndirectedGraph(compactToVertexArray(neighbors))
}
