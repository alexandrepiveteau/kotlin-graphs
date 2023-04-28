package io.github.alexandrepiveteau.graphs.builder

import io.github.alexandrepiveteau.graphs.MutableNetworkScope
import io.github.alexandrepiveteau.graphs.Network
import io.github.alexandrepiveteau.graphs.Vertex
import io.github.alexandrepiveteau.graphs.VertexArray
import io.github.alexandrepiveteau.graphs.internal.collections.IntVector
import io.github.alexandrepiveteau.graphs.util.packInts
import io.github.alexandrepiveteau.graphs.util.unpackInt1
import io.github.alexandrepiveteau.graphs.util.unpackInt2

/** A [NetworkBuilder] is a [GraphBuilder] for [Network]s. */
public interface NetworkBuilder : GraphBuilder, MutableNetworkScope {
  override fun toGraph(): Network
}

// BUILDER HELPERS

/**
 * An implementation of [NetworkBuilder] which uses a [MutableList] to store the neighbors of each
 * vertex.
 */
internal abstract class MutableListNetworkBuilder : MutableListGraphBuilder() {

  /** The list of weights of each vertex. */
  protected val weights: MutableList<IntVector> = mutableListOf()

  override fun addVertex(): Vertex = super.addVertex().also { weights += IntVector() }
}

/**
 * Compacts the given [neighbors] and [weights] into a single array of [VertexArray] and [IntArray],
 * suitable for being used in an adjacency list representation.
 *
 * @param neighbors the list of neighbors of each vertex.
 * @param weights the list of weights of each vertex.
 * @return the pair of arrays of [VertexArray]s and [IntArray]s.
 */
internal fun compactToVertexAndWeightsArray(
    neighbors: MutableList<IntVector>,
    weights: MutableList<IntVector>,
): Pair<Array<VertexArray>, Array<IntArray>> {
  val encoded =
      Array(neighbors.size) {
        var last = -1
        var count = 0
        // Encode the neighbors and weights into a single long, such that the first 32 bits are the
        // neighbor, and the last 32 bits are the weight. This way, we can sort the array of longs
        // by neighbor, and then sum the weights.
        val list =
            LongArray(neighbors[it].size) { i ->
                  val neighbor = neighbors[it][i]
                  val weight = weights[it][i]
                  packInts(neighbor, weight)
                }
                .apply { sort() }
        for (j in list.indices) {
          val neighbor = unpackInt1(list[j])
          val weight = unpackInt2(list[j])
          if (neighbor != last) {
            list[count++] = list[j] // Update the array in place.
            last = neighbor
          } else {
            list[count - 1] = packInts(neighbor, weight + unpackInt2(list[count - 1]))
          }
        }
        list.copyOf(count)
      }

  return Array(encoded.size) { i ->
    VertexArray(encoded[i].size) { j -> Vertex((encoded[i][j] ushr 32).toInt()) }
  } to Array(encoded.size) { i -> IntArray(encoded[i].size) { j -> encoded[i][j].toInt() } }
}
