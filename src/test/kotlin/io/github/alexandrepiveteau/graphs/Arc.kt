package io.github.alexandrepiveteau.graphs

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class ArcTests {

  @Test
  fun `opposite arcs are not equal`() {
    val a = Vertex(0)
    val b = Vertex(1)
    val e = a arcTo b
    val f = b arcTo a
    assertNotEquals(e, f)
  }

  @Test
  fun `arcs are properly reversed`() {
    val a = Vertex(0)
    val b = Vertex(1)
    val e = a arcTo b
    val r = e.reversed()
    assertEquals(a, r.to)
    assertEquals(b, r.from)
  }

  @Test
  fun `arc contains both extremities`() {
    val a = Vertex(0)
    val b = Vertex(1)
    val e = a arcTo b
    assertTrue(a in e)
    assertTrue(b in e)
  }

  @Test
  fun `arc destructuring returns extremities in order`() {
    val a = Vertex(0)
    val b = Vertex(1)
    val e = a arcTo b
    val (from, to) = e
    assertEquals(a, from)
    assertEquals(b, to)
  }
}
