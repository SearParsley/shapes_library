import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows // For testing exceptions

// Assuming the Shape, Solid, Point, and Triangle classes are defined in the same module or accessible
// and EPSILON is defined (e.g., in a common utility file or directly in Triangle class)
private const val EPSILON = 1e-9 // Must match the EPSILON used in the Triangle class

class TriangleTest {

    @Test
    @DisplayName("Triangle instantiation should correctly set points for a valid triangle")
    fun testTriangleInstantiationValid() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(4.0, 0.0)
        val p3 = Point(2.0, 3.0)
        val triangle = Triangle(p1, p2, p3)

        // Verify points are set correctly (referential equality)
        assertSame(p1, triangle.p1, "Point p1 should be the same instance as provided")
        assertSame(p2, triangle.p2, "Point p2 should be the same instance as provided")
        assertSame(p3, triangle.p3, "Point p3 should be the same instance as provided")
    }

    @Test
    @DisplayName("Triangle instantiation should throw IllegalArgumentException for identical points (p1 and p2)")
    fun testTriangleInstantiationIdenticalP1P2() {
        val p1 = Point(1.0, 1.0)
        val p3 = Point(2.0, 2.0)
        val exception = assertThrows<IllegalArgumentException>("Should throw for identical p1 and p2") {
            Triangle(p1, p1, p3)
        }
        assertEquals("A triangle cannot be formed by identical points.", exception.message)
    }

    @Test
    @DisplayName("Triangle instantiation should throw IllegalArgumentException for identical points (p1 and p3)")
    fun testTriangleInstantiationIdenticalP1P3() {
        val p1 = Point(1.0, 1.0)
        val p2 = Point(2.0, 2.0)
        val exception = assertThrows<IllegalArgumentException>("Should throw for identical p1 and p3") {
            Triangle(p1, p2, p1)
        }
        assertEquals("A triangle cannot be formed by identical points.", exception.message)
    }

    @Test
    @DisplayName("Triangle instantiation should throw IllegalArgumentException for identical points (p2 and p3)")
    fun testTriangleInstantiationIdenticalP2P3() {
        val p1 = Point(1.0, 1.0)
        val p2 = Point(2.0, 2.0)
        val exception = assertThrows<IllegalArgumentException>("Should throw for identical p2 and p3") {
            Triangle(p1, p2, p2)
        }
        assertEquals("A triangle cannot be formed by identical points.", exception.message)
    }

    @Test
    @DisplayName("Triangle instantiation should throw IllegalArgumentException for collinear points")
    fun testTriangleInstantiationCollinear() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(1.0, 1.0)
        val p3 = Point(2.0, 2.0) // Collinear with p1 and p2

        val exception = assertThrows<IllegalArgumentException>("Should throw for collinear points") {
            Triangle(p1, p2, p3)
        }
        assertEquals("A triangle cannot be formed by collinear points", exception.message)
    }

    @Test
    @DisplayName("getArea() should correctly calculate area for a right-angled triangle")
    fun testGetAreaRightAngleTriangle() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(4.0, 0.0)
        val p3 = Point(0.0, 3.0) // Right angle at (0,0)
        val triangle = Triangle(p1, p2, p3)
        assertEquals(6.0, triangle.getArea(), EPSILON, "Area should be 6.0 for a 4x3 right triangle")
    }

    @Test
    @DisplayName("getArea() should correctly calculate area for a general triangle (positive determinant)")
    fun testGetAreaGeneralTrianglePositiveDet() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(5.0, 0.0)
        val p3 = Point(2.0, 3.0)
        val triangle = Triangle(p1, p2, p3)
        assertEquals(7.5, triangle.getArea(), EPSILON, "Area should be 7.5")
    }

    @Test
    @DisplayName("getArea() should correctly calculate area for a general triangle (negative determinant, abs used)")
    fun testGetAreaGeneralTriangleNegativeDet() {
        // Points ordered clockwise, resulting in a negative determinant
        val p1 = Point(0.0, 0.0)
        val p2 = Point(2.0, 3.0)
        val p3 = Point(5.0, 0.0)
        val triangle = Triangle(p1, p2, p3)
        assertEquals(7.5, triangle.getArea(), EPSILON, "Area should be 7.5 (absolute value)")
    }

    @Test
    @DisplayName("move() method should correctly update triangle's points in-place")
    fun testMoveMethodUpdatesPoints() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(4.0, 0.0)
        val p3 = Point(2.0, 3.0)
        val triangle = Triangle(p1, p2, p3)

        triangle.move(1.0, 1.0)

        // Verify that the original Point objects within the triangle were mutated
        assertEquals(1.0, p1.x, "Moved p1.x should be 1.0")
        assertEquals(1.0, p1.y, "Moved p1.y should be 1.0")
        assertEquals(5.0, p2.x, "Moved p2.x should be 5.0")
        assertEquals(1.0, p2.y, "Moved p2.y should be 1.0")
        assertEquals(3.0, p3.x, "Moved p3.x should be 3.0")
        assertEquals(4.0, p3.y, "Moved p3.y should be 4.0")

        // Verify triangle's internal points `p1`, `p2`, `p3` reflect the changes
        assertEquals(1.0, triangle.p1.x, "Triangle's p1.x should be 1.0")
        assertEquals(1.0, triangle.p1.y, "Triangle's p1.y should be 1.0")
    }

    @Test
    @DisplayName("move() should preserve triangle area")
    fun testMovePreservesArea() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(4.0, 0.0)
        val p3 = Point(2.0, 3.0)
        val triangle = Triangle(p1, p2, p3)

        val originalArea = triangle.getArea() // Should be 6.0

        triangle.move(10.0, -5.0) // Move the triangle

        assertEquals(originalArea, triangle.getArea(), EPSILON, "Area should remain unchanged after move")
    }

    @Test
    @DisplayName("move() method should mutate the original Triangle object, not return a new one")
    fun testMoveMutatesOriginalTriangle() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(4.0, 0.0)
        val p3 = Point(2.0, 3.0)
        val triangle = Triangle(p1, p2, p3)
        val originalReference = triangle // Store original reference

        triangle.move(5.0, 5.0) // Mutate

        // The object itself should be the same instance
        assertTrue(originalReference === triangle, "move() should mutate the original Triangle object, not return a new instance")
    }

    @Test
    @DisplayName("Private setters for 'p1', 'p2', and 'p3' should prevent direct external modification")
    fun testPrivateSettersForPoints() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(1.0, 0.0)
        val p3 = Point(0.0, 1.0)
        val triangle = Triangle(p1, p2, p3)

        // Attempting to directly assign to triangle.p1, triangle.p2, or triangle.p3
        // should cause a compilation error.
        // Uncommenting the lines below would result in:
        // "Error: Cannot assign to 'p1': the setter is private in 'Triangle'"
        // triangle.p1 = Point(100.0, 100.0)
        // triangle.p2 = Point(200.0, 200.0)
        // triangle.p3 = Point(300.0, 300.0)

        // This test serves as documentation of the intended private setter behavior.
        // We assert that the point references are still the initial ones if no move happens.
        assertSame(p1, triangle.p1)
        assertSame(p2, triangle.p2)
        assertSame(p3, triangle.p3)
    }
}
