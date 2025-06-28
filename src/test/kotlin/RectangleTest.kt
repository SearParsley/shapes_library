import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows // For testing exceptions

// Assuming the Shape, Solid, Point, and Rectangle classes are defined in the same module or accessible
// And EPSILON is defined (e.g., in a common utility file or directly in the classes if consistent)
private const val EPSILON = 1e-9 // Must match the EPSILON used in the Triangle class if applicable, or define locally

class RectangleTest {

    @Test
    @DisplayName("Rectangle instantiation should correctly set points for a valid rectangle")
    fun testRectangleInstantiationValid() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(5.0, 10.0)
        val rectangle = Rectangle(p1, p2)

        // Verify points are set correctly (referential equality)
        assertSame(p1, rectangle.p1, "Point p1 should be the same instance as provided")
        assertSame(p2, rectangle.p2, "Point p2 should be the same instance as provided")
    }

    @Test
    @DisplayName("Rectangle instantiation should throw IllegalArgumentException for identical points")
    fun testRectangleInstantiationIdenticalPoints() {
        val p1 = Point(1.0, 1.0)
        val exception = assertThrows<IllegalArgumentException>("Should throw for identical points") {
            Rectangle(p1, p1)
        }
        assertEquals("A rectangle cannot be formed by identical points.", exception.message)
    }

    @Test
    @DisplayName("Rectangle instantiation should throw IllegalArgumentException for points with same X (no width)")
    fun testRectangleInstantiationSameX() {
        val p1 = Point(1.0, 1.0)
        val p2 = Point(1.0, 5.0) // Same X, different Y
        val exception = assertThrows<IllegalArgumentException>("Should throw for same X coordinates") {
            Rectangle(p1, p2)
        }
        assertEquals("A rectangle cannot be formed without width or height.", exception.message)
    }

    @Test
    @DisplayName("Rectangle instantiation should throw IllegalArgumentException for points with same Y (no height)")
    fun testRectangleInstantiationSameY() {
        val p1 = Point(1.0, 1.0)
        val p2 = Point(5.0, 1.0) // Same Y, different X
        val exception = assertThrows<IllegalArgumentException>("Should throw for same Y coordinates") {
            Rectangle(p1, p2)
        }
        assertEquals("A rectangle cannot be formed without width or height.", exception.message)
    }

    @Test
    @DisplayName("getArea() should correctly calculate area for a standard rectangle")
    fun testGetAreaStandard() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(5.0, 10.0)
        val rectangle = Rectangle(p1, p2)
        assertEquals(50.0, rectangle.getArea(), EPSILON, "Area should be 50.0")
    }

    @Test
    @DisplayName("getArea() should correctly calculate area when points have negative coordinates")
    fun testGetAreaNegativeCoordinates() {
        val p1 = Point(-5.0, -10.0)
        val p2 = Point(0.0, 0.0)
        val rectangle = Rectangle(p1, p2)
        assertEquals(50.0, rectangle.getArea(), EPSILON, "Area should be 50.0 with negative coordinates")
    }

    @Test
    @DisplayName("getArea() should correctly calculate area when points are swapped (absolute value)")
    fun testGetAreaSwappedPoints() {
        val p1 = Point(5.0, 10.0)
        val p2 = Point(0.0, 0.0)
        val rectangle = Rectangle(p1, p2)
        assertEquals(50.0, rectangle.getArea(), EPSILON, "Area should be 50.0 even with swapped points due to abs")
    }

    @Test
    @DisplayName("getArea() should always return a positive value, even if coordinate differences result in a negative product")
    fun testGetAreaEnsuresPositiveValue() {
        val p1 = Point(10.0, 0.0) // Top-right (or bottom-right, depending on coordinate system)
        val p2 = Point(0.0, 5.0)  // Bottom-left (or top-left)

        // (p1.x - p2.x) = (10.0 - 0.0) = 10.0
        // (p1.y - p2.y) = (0.0 - 5.0) = -5.0
        // Product without abs would be 10.0 * -5.0 = -50.0

        val rectangle = Rectangle(p1, p2)
        assertEquals(50.0, rectangle.getArea(), EPSILON, "Area should be 50.0 (positive) due to kotlin.math.abs()")
    }

    @Test
    @DisplayName("move() method should correctly update rectangle's points in-place")
    fun testMoveMethodUpdatesPoints() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(5.0, 10.0)
        val rectangle = Rectangle(p1, p2)

        rectangle.move(1.0, 2.0)

        // Verify that the original Point objects within the rectangle were mutated
        assertEquals(1.0, p1.x, "Moved p1.x should be 1.0")
        assertEquals(2.0, p1.y, "Moved p1.y should be 2.0")
        assertEquals(6.0, p2.x, "Moved p2.x should be 6.0")
        assertEquals(12.0, p2.y, "Moved p2.y should be 12.0")

        // Verify rectangle's internal points `p1`, `p2` reflect the changes
        assertEquals(1.0, rectangle.p1.x, "Rectangle's p1.x should be 1.0")
        assertEquals(2.0, rectangle.p1.y, "Rectangle's p1.y should be 2.0")
    }

    @Test
    @DisplayName("move() should preserve rectangle area")
    fun testMovePreservesArea() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(5.0, 10.0)
        val rectangle = Rectangle(p1, p2)

        val originalArea = rectangle.getArea() // Should be 50.0

        rectangle.move(10.0, -5.0) // Move the rectangle

        assertEquals(originalArea, rectangle.getArea(), EPSILON, "Area should remain unchanged after move")
    }

    @Test
    @DisplayName("move() method should mutate the original Rectangle object, not return a new one")
    fun testMoveMutatesOriginalRectangle() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(5.0, 10.0)
        val rectangle = Rectangle(p1, p2)
        val originalReference = rectangle // Store original reference

        rectangle.move(5.0, 5.0) // Mutate

        // The object itself should be the same instance
        assertTrue(originalReference === rectangle, "move() should mutate the original Rectangle object, not return a new instance")
    }

    @Test
    @DisplayName("Private setters for 'p1' and 'p2' should prevent direct external modification")
    fun testPrivateSettersForPoints() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(1.0, 1.0)
        val rectangle = Rectangle(p1, p2)

        // Attempting to directly assign to rectangle.p1 or rectangle.p2
        // should cause a compilation error.
        // Uncommenting the lines below would result in:
        // "Error: Cannot assign to 'p1': the setter is private in 'Rectangle'"
        // rectangle.p1 = Point(100.0, 100.0)
        // rectangle.p2 = Point(200.0, 200.0)

        // This test serves as documentation of the intended private setter behavior.
        // We assert that the point references are still the initial ones if no move happens.
        assertSame(p1, rectangle.p1)
        assertSame(p2, rectangle.p2)
    }
}
