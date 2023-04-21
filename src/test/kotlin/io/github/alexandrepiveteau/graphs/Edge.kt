package io.github.alexandrepiveteau.graphs

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class EdgeTests {

  @Test
  fun `opposite edges are equal`() {
    val a = Vertex(0)
    val b = Vertex(1)
    val e = a edgeTo b
    val f = b edgeTo a
    assertEquals(e, f)
  }

  @Test
  fun `edge contains both extremities`() {
    val a = Vertex(0)
    val b = Vertex(1)
    val e = a edgeTo b
    assertTrue(a in e)
    assertTrue(b in e)
  }

  @Test
  fun `other returns other edge extremity`() {
    val a = Vertex(0)
    val b = Vertex(1)
    val e = a edgeTo b

    assertEquals(a, e.other(b))
    assertEquals(b, e.other(a))
  }

  @Test
  fun `any returns an extremity`() {
    val a = Vertex(1000)
    val b = Vertex(1001)

    val e = a edgeTo b
    assertTrue(e.any() in e)
  }

  @Test
  fun `other with an unknown vertex throws an IllegalArgumentException`() {
    val a = Vertex(0)
    val b = Vertex(1)
    val c = Vertex(2)
    val e = a edgeTo b
    assertFailsWith<IllegalArgumentException> { e.other(c) }
  }
}
