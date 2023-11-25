@file:JvmName("GraphBuilders")
@file:JvmMultifileClass

package io.github.alexandrepiveteau.graphs.builder

import io.github.alexandrepiveteau.graphs.Arc
import io.github.alexandrepiveteau.graphs.DirectedNetwork
import io.github.alexandrepiveteau.graphs.MutableDirectedNetworkScope
import io.github.alexandrepiveteau.graphs.internal.graphs.AdjacencyListDirectedNetwork
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

/** A [DirectedNetworkBuilder] is a [DirectedGraphBuilder] which can also add arcs with a weight. */
public interface DirectedNetworkBuilder : NetworkBuilder, MutableDirectedNetworkScope {
  override fun toGraph(): DirectedNetwork
}

/** Returns a new [DirectedNetworkBuilder] instance. */
@JvmName("directedNetwork")
public fun DirectedNetwork.Companion.builder(): DirectedNetworkBuilder =
    MutableListDirectedNetworkBuilder()

/**
 * Builds a [DirectedNetwork] using the given [scope].
 *
 * @param scope the scope in which the graph is built.
 * @return the newly built graph.
 */
public inline fun buildDirectedNetwork(
    scope: MutableDirectedNetworkScope.() -> Unit,
): DirectedNetwork {
  contract { callsInPlace(scope, InvocationKind.EXACTLY_ONCE) }
  return DirectedNetwork.builder().apply(scope).toGraph()
}

/** A [MutableListNetworkBuilder] for [DirectedNetworkBuilder]. */
private class MutableListDirectedNetworkBuilder :
    MutableListNetworkBuilder(), DirectedNetworkBuilder {
  override fun addArc(arc: Arc, weight: Int) {
    val (u, v) = arc
    checkLink(u, v)
    neighbors[u.index] += v.index
    weights[u.index] += weight
  }

  override fun toGraph(): DirectedNetwork {
    val (n, w) = compactToVertexAndWeightsArray(neighbors, weights)
    return AdjacencyListDirectedNetwork(n, w)
  }
}
