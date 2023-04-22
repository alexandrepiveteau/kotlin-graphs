package io.github.alexandrepiveteau.graphs

import io.github.alexandrepiveteau.graphs.internal.IntVector

/** A [NetworkBuilder] is a [GraphBuilder] for [Network]s. */
public interface NetworkBuilder : GraphBuilder

// BUILDER HELPERS

/**
 * An implementation of [NetworkBuilder] which uses a [MutableList] to store the neighbors of each
 * vertex.
 *
 * @param neighbors the list of neighbors of each vertex.
 * @param weights the list of weights of each vertex.
 */
@PublishedApi
internal open class MutableListNetworkBuilder(
    private val neighbors: MutableList<IntVector>,
    private val weights: MutableList<IntVector>,
) : MutableListGraphBuilder(neighbors) {
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
@PublishedApi
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
                  (neighbor.toLong() shl 32) or weight.toLong()
                }
                .apply { sort() }
        for (j in list.indices) {
          val neighbor = (list[j] ushr 32).toInt()
          val weight = list[j].toInt()
          if (neighbor != last) {
            list[count++] = list[j] // Update the array in place.
            last = neighbor
          } else {
            weights[it][count - 1] += weight // Sum the weights of the same neighbor.
          }
        }
        list.copyOf(count)
      }

  return Array(encoded.size) { i ->
    VertexArray(encoded[i].size) { j -> Vertex((encoded[i][j] ushr 32).toInt()) }
  } to Array(encoded.size) { i -> IntArray(encoded[i].size) { j -> encoded[i][j].toInt() } }
}
