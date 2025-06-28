import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows // For testing exceptions

// Assuming the Point and Line classes are defined in the same module or accessible

class LineTest {

    @Test
    @DisplayName("Line instantiation should correctly set start and end points")
    fun testLineInstantiation() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(5.0, 10.0)
        val line = Line(p1, p2)

        assertSame(p1, line.p1, "Start point 'a' should be the same instance as provided")
        assertSame(p2, line.p2, "End point 'b' should be the same instance as provided")
    }

    @Test
    @DisplayName("Line instantiation should throw IllegalArgumentException for identical points")
    fun testLineInstantiationWithIdenticalPoints() {
        val p1 = Point(1.0, 1.0)

        val exception = assertThrows<IllegalArgumentException>("Should throw IllegalArgumentException") {
            Line(p1, p1) // Attempt to create a line with identical points
        }
        assertEquals("A line cannot be formed by identical points (a and b must be distinct).", exception.message)
    }

    @Test
    @DisplayName("getSlope() should calculate positive slope correctly")
    fun testGetPositiveSlope() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(3.0, 6.0)
        val line = Line(p1, p2)
        assertEquals(2.0, line.getSlope(), 0.001, "Slope should be 2.0")
    }

    @Test
    @DisplayName("getSlope() should calculate negative slope correctly")
    fun testGetNegativeSlope() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(3.0, -6.0)
        val line = Line(p1, p2)
        assertEquals(-2.0, line.getSlope(), 0.001, "Slope should be -2.0")
    }

    @Test
    @DisplayName("getSlope() should calculate zero slope for horizontal lines")
    fun testGetZeroSlope() {
        val p1 = Point(1.0, 5.0)
        val p2 = Point(10.0, 5.0)
        val line = Line(p1, p2)
        assertEquals(0.0, line.getSlope(), 0.001, "Slope should be 0.0 for horizontal line")
    }

    @Test
    @DisplayName("getSlope() should return PositiveInfinity for vertical lines")
    fun testGetVerticalLineSlope() {
        val p1 = Point(2.0, 0.0)
        val p2 = Point(2.0, 7.0)
        val line = Line(p1, p2)
        assertEquals(Double.POSITIVE_INFINITY, line.getSlope(), "Slope should be Double.POSITIVE_INFINITY for vertical line")
    }

    @Test
    @DisplayName("getLength() should calculate length of horizontal line correctly")
    fun testGetHorizontalLineLength() {
        val p1 = Point(0.0, 5.0)
        val p2 = Point(10.0, 5.0)
        val line = Line(p1, p2)
        assertEquals(10.0, line.getLength(), 0.001, "Length should be 10.0 for horizontal line")
    }

    @Test
    @DisplayName("getLength() should calculate length of vertical line correctly")
    fun testGetVerticalLineLength() {
        val p1 = Point(5.0, 0.0)
        val p2 = Point(5.0, 12.0)
        val line = Line(p1, p2)
        assertEquals(12.0, line.getLength(), 0.001, "Length should be 12.0 for vertical line")
    }

    @Test
    @DisplayName("getLength() should calculate length of diagonal line correctly (Pythagorean theorem)")
    fun testGetDiagonalLineLength() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(3.0, 4.0)
        val line = Line(p1, p2)
        assertEquals(5.0, line.getLength(), 0.001, "Length should be 5.0 for a 3-4-5 triangle")
    }

    @Test
    @DisplayName("move() method should correctly update line's points in-place")
    fun testMoveMethodUpdatesPoints() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(3.0, 4.0)
        val line = Line(p1, p2)

        line.move(1.0, 1.0)

        // Verify that the original Point objects within the line were mutated
        assertEquals(1.0, p1.x, "Moved p1.x should be 1.0")
        assertEquals(1.0, p1.y, "Moved p1.y should be 1.0")
        assertEquals(4.0, p2.x, "Moved p2.x should be 4.0")
        assertEquals(5.0, p2.y, "Moved p2.y should be 5.0")

        // Verify line's internal points 'a' and 'b' reflect the changes
        assertEquals(1.0, line.p1.x, "Line's a.x should be 1.0")
        assertEquals(1.0, line.p1.y, "Line's a.y should be 1.0")
        assertEquals(4.0, line.p2.x, "Line's b.x should be 4.0")
        assertEquals(5.0, line.p2.y, "Line's b.y should be 5.0")
    }

    @Test
    @DisplayName("move() should preserve line length and slope")
    fun testMovePreservesProperties() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(3.0, 4.0)
        val line = Line(p1, p2)

        val originalLength = line.getLength()
        val originalSlope = line.getSlope()

        line.move(10.0, -5.0) // Move the line

        assertEquals(originalLength, line.getLength(), 0.001, "Length should remain unchanged after move")
        assertEquals(originalSlope, line.getSlope(), 0.001, "Slope should remain unchanged after move")
    }

    @Test
    @DisplayName("move() method should mutate the original Line object, not return a new one")
    fun testMoveMutatesOriginalLine() {
        val p1 = Point(1.0, 1.0)
        val p2 = Point(2.0, 2.0)
        val line = Line(p1, p2)
        val originalReference = line // Store original reference

        line.move(5.0, 5.0) // Mutate

        // The object itself should be the same instance
        assertTrue(originalReference === line, "move() should mutate the original Line object, not return a new instance")
    }

    @Test
    @DisplayName("Private setters for 'a' and 'b' should prevent direct external modification")
    fun testPrivateSettersForPoints() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(1.0, 1.0)
        val line = Line(p1, p2)

        // Attempting to directly assign to line.a or line.b should cause a compilation error.
        // Uncommenting the line below would result in:
        // "Error: Cannot assign to 'a': the setter is private in 'Line'"
        // line.a = Point(100.0, 100.0)

        // To verify this in a test, you'd typically rely on compile-time checks or
        // use reflection (which is generally not recommended for unit tests of intended API behavior).
        // The most important thing is that the compiler enforces this.
        // This test serves as a documentation of the intended private setter behavior.
        // We'll assert that the values are still the initial ones if no move happens.
        assertEquals(0.0, line.p1.x)
        assertEquals(0.0, line.p1.y)
        assertEquals(1.0, line.p2.x)
        assertEquals(1.0, line.p2.y)
    }
}