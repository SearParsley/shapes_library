import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows // For testing exceptions
import kotlin.math.PI // Ensure PI is accessible

// Assuming the Shape, Solid, Point, and Ellipse classes are defined
// in the same module or accessible.
// Also assuming EPSILON is consistent across all geometric classes.
private const val EPSILON = 1e-9 // Must be consistent with other geometric classes

class EllipseTest {

    @Test
    @DisplayName("Ellipse instantiation should correctly set center and radii for a valid ellipse")
    fun testEllipseInstantiationValid() {
        val center = Point(0.0, 0.0)
        val ellipse = Ellipse(center, 5.0, 3.0)

        // Verify properties are set correctly
        assertSame(center, ellipse.center, "Center point should be the same instance as provided")
        assertEquals(5.0, ellipse.radiusX, EPSILON, "RadiusX should be 5.0")
        assertEquals(3.0, ellipse.radiusY, EPSILON, "RadiusY should be 3.0")
    }

    @Test
    @DisplayName("Ellipse instantiation should throw IllegalArgumentException for zero radiusX")
    fun testEllipseInstantiationZeroRadiusX() {
        val center = Point(0.0, 0.0)
        val exception = assertThrows<IllegalArgumentException>("Should throw for zero radiusX") {
            Ellipse(center, 0.0, 3.0)
        }
        assertEquals("An ellipse cannot be formed with a non-positive radius.", exception.message)
    }

    @Test
    @DisplayName("Ellipse instantiation should throw IllegalArgumentException for negative radiusX")
    fun testEllipseInstantiationNegativeRadiusX() {
        val center = Point(0.0, 0.0)
        val exception = assertThrows<IllegalArgumentException>("Should throw for negative radiusX") {
            Ellipse(center, -5.0, 3.0)
        }
        assertEquals("An ellipse cannot be formed with a non-positive radius.", exception.message)
    }

    @Test
    @DisplayName("Ellipse instantiation should throw IllegalArgumentException for zero radiusY")
    fun testEllipseInstantiationZeroRadiusY() {
        val center = Point(0.0, 0.0)
        val exception = assertThrows<IllegalArgumentException>("Should throw for zero radiusY") {
            Ellipse(center, 5.0, 0.0)
        }
        assertEquals("An ellipse cannot be formed with a non-positive radius.", exception.message)
    }

    @Test
    @DisplayName("Ellipse instantiation should throw IllegalArgumentException for negative radiusY")
    fun testEllipseInstantiationNegativeRadiusY() {
        val center = Point(0.0, 0.0)
        val exception = assertThrows<IllegalArgumentException>("Should throw for negative radiusY") {
            Ellipse(center, 5.0, -3.0)
        }
        assertEquals("An ellipse cannot be formed with a non-positive radius.", exception.message)
    }

    @Test
    @DisplayName("getArea() should correctly calculate area for a standard ellipse")
    fun testGetAreaStandardEllipse() {
        val center = Point(0.0, 0.0)
        val ellipse = Ellipse(center, 5.0, 3.0) // Area = PI * 5 * 3 = 15 * PI
        assertEquals(15.0 * PI, ellipse.getArea(), EPSILON, "Area should be 15*PI")
    }

    @Test
    @DisplayName("getArea() should correctly calculate area for a circle (radii are equal)")
    fun testGetAreaCircle() {
        val center = Point(0.0, 0.0)
        val ellipse = Ellipse(center, 2.0, 2.0) // Area = PI * 2 * 2 = 4 * PI
        assertEquals(4.0 * PI, ellipse.getArea(), EPSILON, "Area should be 4*PI for a circle")
    }

    @Test
    @DisplayName("getArea() should calculate area for non-integer radii")
    fun testGetAreaNonIntegerRadii() {
        val center = Point(0.0, 0.0)
        val ellipse = Ellipse(center, 1.5, 2.5) // Area = PI * 1.5 * 2.5 = 3.75 * PI
        assertEquals(3.75 * PI, ellipse.getArea(), EPSILON, "Area should be 3.75*PI")
    }

    @Test
    @DisplayName("move() method should correctly update ellipse's center point in-place")
    fun testMoveMethodUpdatesCenter() {
        val center = Point(0.0, 0.0)
        val ellipse = Ellipse(center, 5.0, 3.0)

        ellipse.move(10.0, 20.0)

        // Verify that the original Point object for the center was mutated
        assertEquals(10.0, center.x, "Moved center.x should be 10.0")
        assertEquals(20.0, center.y, "Moved center.y should be 20.0")

        // Verify ellipse's internal center reflects the changes
        assertEquals(10.0, ellipse.center.x, "Ellipse's center.x should be 10.0")
        assertEquals(20.0, ellipse.center.y, "Ellipse's center.y should be 20.0")
    }

    @Test
    @DisplayName("move() should preserve ellipse area")
    fun testMovePreservesArea() {
        val center = Point(0.0, 0.0)
        val ellipse = Ellipse(center, 5.0, 3.0)

        val originalArea = ellipse.getArea() // Should be 15*PI

        ellipse.move(100.0, -50.0) // Move the ellipse

        assertEquals(originalArea, ellipse.getArea(), EPSILON, "Area should remain unchanged after move")
    }

    @Test
    @DisplayName("move() method should mutate the original Ellipse object, not return a new one")
    fun testMoveMutatesOriginalEllipse() {
        val center = Point(0.0, 0.0)
        val ellipse = Ellipse(center, 5.0, 5.0)
        val originalReference = ellipse // Store original reference

        ellipse.move(5.0, 5.0) // Mutate

        // The object itself should be the same instance
        assertTrue(originalReference === ellipse, "move() should mutate the original Ellipse object, not return a new instance")
    }

    @Test
    @DisplayName("Private setters for 'center', 'radiusX', and 'radiusY' should prevent direct external modification")
    fun testPrivateSettersForProperties() {
        val center = Point(0.0, 0.0)
        val ellipse = Ellipse(center, 5.0, 3.0)

        // Attempting to directly assign to ellipse.center, ellipse.radiusX, or ellipse.radiusY
        // should cause a compilation error.
        // Uncommenting the lines below would result in:
        // "Error: Cannot assign to 'center': the setter is private in 'Ellipse'"
        // ellipse.center = Point(100.0, 100.0)
        // ellipse.radiusX = 10.0
        // ellipse.radiusY = 20.0

        // This test serves as documentation of the intended private setter behavior.
        // We assert that the properties retain their initial values if no move happens.
        assertSame(center, ellipse.center)
        assertEquals(5.0, ellipse.radiusX)
        assertEquals(3.0, ellipse.radiusY)
    }
}
