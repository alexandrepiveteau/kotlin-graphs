package io.github.alexandrepiveteau.graphs

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class EdgeTests {

  @Test
  fun oppositeEdgesAreEqual() {
    val a = Vertex(0)
    val b = Vertex(1)
    val e = a edgeTo b
    val f = b edgeTo a
    assertEquals(e, f)
  }

  @Test
  fun edgeContainsBothExtremities() {
    val a = Vertex(0)
    val b = Vertex(1)
    val e = a edgeTo b
    assertTrue(a in e)
    assertTrue(b in e)
  }

  @Test
  fun otherReturnsOtherEdgeExtremity() {
    val a = Vertex(0)
    val b = Vertex(1)
    val e = a edgeTo b

    assertEquals(a, e.other(b))
    assertEquals(b, e.other(a))
  }

  @Test
  fun anyReturnsAnExtremity() {
    val a = Vertex(1000)
    val b = Vertex(1001)

    val e = a edgeTo b
    assertTrue(e.any() in e)
  }

  @Test
  fun otherWithAnUnknownVertexThrowsAnIllegalArgumentException() {
    val a = Vertex(0)
    val b = Vertex(1)
    val c = Vertex(2)
    val e = a edgeTo b
    assertFailsWith<IllegalArgumentException> { e.other(c) }
  }
}
