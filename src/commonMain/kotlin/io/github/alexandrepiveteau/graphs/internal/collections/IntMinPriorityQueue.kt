package io.github.alexandrepiveteau.graphs.internal.collections

/**
 * An [IntMinPriorityQueue] is a priority queue, with [Int] values and [Int] priorities. The first
 * element has the lowest priority. Elements must be in the range `[0, size)`.
 *
 * @param size the maximum size of the priority queue. Inserted elements may not have a value
 *   greater than or equal to this value.
 */
internal class IntMinPriorityQueue(size: Int) {

  /** The priorities of the elements, organized as a heap. */
  private val priorities = IntVector()

  /**
   * The values of the elements, as an array. The value at the index is associated to the priority
   * at the same index in the [priorities] array.
   */
  private val prioritiesToElements = IntVector()

  /**
   * The indices in the [priorities] array, for each element. The value at the index is the index of
   * the element in the [priorities] array. A value of `-1` means that the element is not in the
   * [priorities] array.
   *
   * This is what allows constant-time updates of the priorities.
   */
  private val elementsToIndices = IntArray(size) { -1 }

  /** Returns the index of the parent of the element at the given [index]. */
  private fun parent(index: Int): Int = (index - 1) / 2

  /** Returns the index of the left child of the element at the given [index]. */
  private fun left(index: Int): Int = 2 * index + 1

  /** Returns the index of the right child of the element at the given [index]. */
  private fun right(index: Int): Int = 2 * index + 2

  /** Returns true if the element at the given [index] is a leaf. */
  private fun isLeaf(index: Int): Boolean = left(index) >= size

  /**
   * Swaps the elements at the given indices from the [priorities] arrays, and updates the
   * [elementsToIndices] array accordingly.
   *
   * @param a the first index.
   * @param b the second index.
   */
  private fun swap(a: Int, b: Int) {
    // Swap the priorities.
    val tmp = priorities[a]
    priorities[a] = priorities[b]
    priorities[b] = tmp

    // Swap the elements.
    val tmp2 = prioritiesToElements[a]
    prioritiesToElements[a] = prioritiesToElements[b]
    prioritiesToElements[b] = tmp2

    // Update the indices.
    elementsToIndices[prioritiesToElements[a]] = a
    elementsToIndices[prioritiesToElements[b]] = b
  }

  /** Returns the number of elements in the priority queue. */
  val size: Int
    get() = priorities.size

  /** Returns true if the [element] is in this [IntMinPriorityQueue], false otherwise. */
  operator fun contains(element: Int): Boolean = elementsToIndices[element] != -1

  /**
   * Returns the priority of the given [element], or throws a [NoSuchElementException] if the
   * element is not in the priority queue.
   *
   * @param element the element to query.
   * @return the priority of the element.
   */
  operator fun get(element: Int): Int {
    val index = elementsToIndices[element]
    if (index == -1) throw NoSuchElementException()
    return priorities[index]
  }

  /**
   * Sets the priority of [element] to [priority]. If the element is not in the priority queue, it
   * will be inserted.
   *
   * @param element the element to insert or update.
   * @param priority the priority of the element.
   */
  operator fun set(element: Int, priority: Int) {
    var index = elementsToIndices[element]
    if (index == -1) {
      // The element is not in the priority queue. Insert it.
      index = priorities.size
      priorities += priority
      prioritiesToElements += element
      elementsToIndices[element] = index
    }
    // Update the priority.
    priorities[index] = priority
    // Restore the heap property.
    shiftUp(elementsToIndices[element])
    shiftDown(elementsToIndices[element])
  }

  /**
   * Removes the element with the lowest priority from the priority queue, and returns it. If the
   * priority queue is empty, throws a [NoSuchElementException].
   *
   * @return the element with the lowest priority.
   */
  fun remove(): Int {
    if (size == 0) throw NoSuchElementException()
    val element = prioritiesToElements[0]
    swap(0, size - 1)
    priorities.removeLast()
    prioritiesToElements.removeLast()
    elementsToIndices[element] = -1
    shiftDown(0)
    return element
  }

  /**
   * Restores the heap property, by shifting the element at the given [index] up the heap, if its
   * priority is lower than its parent's.
   *
   * @param index the index of the element to shift up.
   */
  private fun shiftUp(index: Int) {
    var idx = index
    while (idx > 0 && priorities[parent(idx)] > priorities[idx]) {
      swap(idx, parent(idx))
      idx = parent(idx)
    }
  }

  /**
   * Restores the heap property, by shifting the element at the given [index] down the heap, if its
   * priority is greater than its children's.
   *
   * @param index the index of the element to shift down.
   */
  private fun shiftDown(index: Int) {
    var idx = index
    while (!isLeaf(idx)) {
      val left = left(idx)
      val right = right(idx)
      val smallest = if (right < size && priorities[right] < priorities[left]) right else left
      if (priorities[idx] <= priorities[smallest]) return
      swap(idx, smallest)
      idx = smallest
    }
  }
}
