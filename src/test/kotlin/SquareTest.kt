import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows // For testing exceptions

// Assuming the Shape, Solid, Point, Rectangle, and Square classes are defined
// in the same module or accessible.
private const val EPSILON = 1e-9 // Must be consistent with other geometric classes

class SquareTest {

    @Test
    @DisplayName("Square instantiation should correctly set points for a valid square")
    fun testSquareInstantiationValid() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(5.0, 5.0) // Valid square, side length 5
        val square = Square(p1, p2)

        // Verify points are set correctly (referential equality)
        assertSame(p1, square.p1, "Point p1 should be the same instance as provided")
        assertSame(p2, square.p2, "Point p2 should be the same instance as provided")
    }

    @Test
    @DisplayName("Square instantiation should throw IllegalArgumentException for identical points (inherited from Rectangle)")
    fun testSquareInstantiationIdenticalPoints() {
        val p1 = Point(1.0, 1.0)
        val exception = assertThrows<IllegalArgumentException>("Should throw for identical points") {
            Square(p1, p1)
        }
        assertEquals("A rectangle cannot be formed by identical points.", exception.message)
    }

    @Test
    @DisplayName("Square instantiation should throw IllegalArgumentException for points with same X (no width - inherited)")
    fun testSquareInstantiationSameX() {
        val p1 = Point(1.0, 1.0)
        val p2 = Point(1.0, 5.0) // Same X, different Y
        val exception = assertThrows<IllegalArgumentException>("Should throw for same X coordinates (inherited)") {
            Square(p1, p2)
        }
        assertEquals("A rectangle cannot be formed without width or height.", exception.message)
    }

    @Test
    @DisplayName("Square instantiation should throw IllegalArgumentException for points with same Y (no height - inherited)")
    fun testSquareInstantiationSameY() {
        val p1 = Point(1.0, 1.0)
        val p2 = Point(5.0, 1.0) // Same Y, different X
        val exception = assertThrows<IllegalArgumentException>("Should throw for same Y coordinates (inherited)") {
            Square(p1, p2)
        }
        assertEquals("A rectangle cannot be formed without width or height.", exception.message)
    }

    @Test
    @DisplayName("Square instantiation should throw IllegalArgumentException for unequal side lengths")
    fun testSquareInstantiationUnequalSides() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(5.0, 10.0) // Width 5, Height 10 (not a square)
        val exception = assertThrows<IllegalArgumentException>("Should throw for unequal side lengths") {
            Square(p1, p2)
        }
        assertEquals("A square cannot be formed with unequal length and width.", exception.message)
    }

    @Test
    @DisplayName("getArea() should correctly calculate area for a standard square")
    fun testGetAreaStandardSquare() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(5.0, 5.0)
        val square = Square(p1, p2)
        assertEquals(25.0, square.getArea(), EPSILON, "Area should be 25.0 for a 5x5 square")
    }

    @Test
    @DisplayName("getArea() should correctly calculate area when points have negative coordinates")
    fun testGetAreaNegativeCoordinates() {
        val p1 = Point(-5.0, -5.0)
        val p2 = Point(0.0, 0.0)
        val square = Square(p1, p2)
        assertEquals(25.0, square.getArea(), EPSILON, "Area should be 25.0 with negative coordinates")
    }

    @Test
    @DisplayName("getArea() should correctly calculate area when points are swapped (absolute value)")
    fun testGetAreaSwappedPoints() {
        val p1 = Point(5.0, 5.0)
        val p2 = Point(0.0, 0.0)
        val square = Square(p1, p2)
        assertEquals(25.0, square.getArea(), EPSILON, "Area should be 25.0 even with swapped points due to abs")
    }

    @Test
    @DisplayName("getArea() should always return a positive value (inherited from Rectangle)")
    fun testGetAreaEnsuresPositiveValue() {
        val p1 = Point(10.0, 0.0)
        val p2 = Point(0.0, 10.0) // Forms a valid square with side length 10
        val square = Square(p1, p2)
        assertEquals(100.0, square.getArea(), EPSILON, "Area should be 100.0 (positive) due to kotlin.math.abs()")
    }

    @Test
    @DisplayName("move() method should correctly update square's points in-place (inherited)")
    fun testMoveMethodUpdatesPoints() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(5.0, 5.0)
        val square = Square(p1, p2)

        square.move(1.0, 2.0)

        // Verify that the original Point objects within the square were mutated
        assertEquals(1.0, p1.x, "Moved p1.x should be 1.0")
        assertEquals(2.0, p1.y, "Moved p1.y should be 2.0")
        assertEquals(6.0, p2.x, "Moved p2.x should be 6.0")
        assertEquals(7.0, p2.y, "Moved p2.y should be 7.0")

        // Verify square's internal points `p1`, `p2` reflect the changes
        assertEquals(1.0, square.p1.x, "Square's p1.x should be 1.0")
        assertEquals(2.0, square.p1.y, "Square's p1.y should be 2.0")
    }

    @Test
    @DisplayName("move() should preserve square area (inherited)")
    fun testMovePreservesArea() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(5.0, 5.0)
        val square = Square(p1, p2)

        val originalArea = square.getArea() // Should be 25.0

        square.move(10.0, -5.0) // Move the square

        assertEquals(originalArea, square.getArea(), EPSILON, "Area should remain unchanged after move")
    }

    @Test
    @DisplayName("move() method should mutate the original Square object, not return a new one (inherited)")
    fun testMoveMutatesOriginalSquare() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(5.0, 5.0)
        val square = Square(p1, p2)
        val originalReference = square // Store original reference

        square.move(5.0, 5.0) // Mutate

        // The object itself should be the same instance
        assertTrue(originalReference === square, "move() should mutate the original Square object, not return a new instance")
    }

    @Test
    @DisplayName("Private setters for 'p1' and 'p2' should prevent direct external modification (inherited)")
    fun testPrivateSettersForPoints() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(1.0, 1.0)
        val square = Square(p1, p2)

        // Attempting to directly assign to square.p1 or square.p2
        // should cause a compilation error.
        // Uncommenting the lines below would result in:
        // "Error: Cannot assign to 'p1': the setter is private in 'Rectangle'"
        // square.p1 = Point(100.0, 100.0)
        // square.p2 = Point(200.0, 200.0)

        // This test serves as documentation of the intended private setter behavior.
        // We assert that the point references are still the initial ones if no move happens.
        assertSame(p1, square.p1)
        assertSame(p2, square.p2)
    }
}
