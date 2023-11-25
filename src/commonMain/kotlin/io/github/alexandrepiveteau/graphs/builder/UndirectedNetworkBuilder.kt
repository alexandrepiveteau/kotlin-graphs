@file:JvmName("GraphBuilders")
@file:JvmMultifileClass

package io.github.alexandrepiveteau.graphs.builder

import io.github.alexandrepiveteau.graphs.Edge
import io.github.alexandrepiveteau.graphs.MutableUndirectedNetworkScope
import io.github.alexandrepiveteau.graphs.UndirectedNetwork
import io.github.alexandrepiveteau.graphs.internal.graphs.AdjacencyListUndirectedNetwork
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

/** An [UndirectedNetworkBuilder] is a [NetworkBuilder] for [UndirectedNetwork]s. */
public interface UndirectedNetworkBuilder : NetworkBuilder, MutableUndirectedNetworkScope {
  override fun toGraph(): UndirectedNetwork
}

/** Returns a new [UndirectedNetworkBuilder] instance. */
@JvmName("undirectedNetwork")
public fun UndirectedNetwork.Companion.builder(): UndirectedNetworkBuilder =
    MutableListUndirectedNetworkBuilder()

/**
 * Builds a [UndirectedNetwork] using the given [scope].
 *
 * @param scope the scope in which the graph is built.
 * @return the newly built graph.
 */
public inline fun buildUndirectedNetwork(
    scope: MutableUndirectedNetworkScope.() -> Unit
): UndirectedNetwork = UndirectedNetwork.builder().apply(scope).toGraph()

/** A [MutableListNetworkBuilder] for [UndirectedNetworkBuilder]. */
private class MutableListUndirectedNetworkBuilder() :
    MutableListNetworkBuilder(), UndirectedNetworkBuilder {
  override fun addEdge(edge: Edge, weight: Int) {
    val (u, v) = edge
    checkLink(u, v)
    neighbors[u.index] += v.index
    neighbors[v.index] += u.index
    weights[u.index] += weight
    weights[v.index] += weight
  }

  override fun toGraph(): UndirectedNetwork {
    val (n, w) = compactToVertexAndWeightsArray(neighbors, weights)
    return AdjacencyListUndirectedNetwork(n, w)
  }
}
