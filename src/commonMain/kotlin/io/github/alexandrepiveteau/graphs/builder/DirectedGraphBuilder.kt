@file:JvmName("GraphBuilders")
@file:JvmMultifileClass

package io.github.alexandrepiveteau.graphs.builder

import io.github.alexandrepiveteau.graphs.Arc
import io.github.alexandrepiveteau.graphs.DirectedGraph
import io.github.alexandrepiveteau.graphs.MutableDirectedGraphScope
import io.github.alexandrepiveteau.graphs.internal.graphs.AdjacencyListDirectedGraph
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

/** A [MutableDirectedGraphScope] is a [GraphBuilder] for [DirectedGraph]s. */
public interface DirectedGraphBuilder : GraphBuilder, MutableDirectedGraphScope {
  override fun toGraph(): DirectedGraph
}

/** Returns a new [DirectedGraphBuilder] instance. */
@JvmName("directedGraph")
public fun DirectedGraph.Companion.builder(): DirectedGraphBuilder =
    MutableListDirectedGraphBuilder()

/**
 * Builds a [DirectedGraph] using the given [scope].
 *
 * @param scope the scope in which the graph is built.
 * @return the newly built graph.
 */
public inline fun buildDirectedGraph(
    scope: MutableDirectedGraphScope.() -> Unit,
): DirectedGraph = DirectedGraph.builder().apply(scope).toGraph()

/** A [MutableListGraphBuilder] for [DirectedGraphBuilder]. */
private class MutableListDirectedGraphBuilder : MutableListGraphBuilder(), DirectedGraphBuilder {
  override fun addArc(arc: Arc) {
    val (u, v) = arc
    checkLink(u, v)
    neighbors[u.index] += v.index
  }
  override fun toGraph() = AdjacencyListDirectedGraph(compactToVertexArray(neighbors))
}
