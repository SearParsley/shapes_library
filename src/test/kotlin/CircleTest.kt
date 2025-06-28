import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows // For testing exceptions
import kotlin.math.PI // Ensure PI is accessible

// Assuming the Shape, Solid, Point, Ellipse.kt, and Circle classes are defined
// in the same module or accessible.
// Also assuming EPSILON is consistent across all geometric classes.
private const val EPSILON = 1e-9 // Must be consistent with other geometric classes

class CircleTest {

    @Test
    @DisplayName("Circle instantiation should correctly set center and radius for a valid circle")
    fun testCircleInstantiationValid() {
        val center = Point(0.0, 0.0)
        val circle = Circle(center, 5.0)

        // Verify properties are set correctly (inherited from Ellipse.kt)
        assertSame(center, circle.center, "Center point should be the same instance as provided")
        assertEquals(5.0, circle.radiusX, EPSILON, "RadiusX (radius) should be 5.0")
        assertEquals(5.0, circle.radiusY, EPSILON, "RadiusY (radius) should be 5.0")
    }

    @Test
    @DisplayName("Circle instantiation should throw IllegalArgumentException for zero radius (inherited from Ellipse.kt)")
    fun testCircleInstantiationZeroRadius() {
        val center = Point(0.0, 0.0)
        val exception = assertThrows<IllegalArgumentException>("Should throw for zero radius") {
            Circle(center, 0.0)
        }
        assertEquals("An ellipse cannot be formed with a non-positive radius.", exception.message)
    }

    @Test
    @DisplayName("Circle instantiation should throw IllegalArgumentException for negative radius (inherited from Ellipse.kt)")
    fun testCircleInstantiationNegativeRadius() {
        val center = Point(0.0, 0.0)
        val exception = assertThrows<IllegalArgumentException>("Should throw for negative radius") {
            Circle(center, -5.0)
        }
        assertEquals("An ellipse cannot be formed with a non-positive radius.", exception.message)
    }

    @Test
    @DisplayName("getArea() should correctly calculate area for a standard circle")
    fun testGetAreaStandardCircle() {
        val center = Point(0.0, 0.0)
        val circle = Circle(center, 5.0) // Area = PI * 5 * 5 = 25 * PI
        assertEquals(25.0 * PI, circle.getArea(), EPSILON, "Area should be 25*PI")
    }

    @Test
    @DisplayName("getArea() should calculate area for non-integer radius")
    fun testGetAreaNonIntegerRadius() {
        val center = Point(0.0, 0.0)
        val circle = Circle(center, 2.5) // Area = PI * 2.5 * 2.5 = 6.25 * PI
        assertEquals(6.25 * PI, circle.getArea(), EPSILON, "Area should be 6.25*PI")
    }

    @Test
    @DisplayName("move() method should correctly update circle's center point in-place (inherited from Ellipse.kt)")
    fun testMoveMethodUpdatesCenter() {
        val center = Point(0.0, 0.0)
        val circle = Circle(center, 5.0)

        circle.move(10.0, 20.0)

        // Verify that the original Point object for the center was mutated
        assertEquals(10.0, center.x, "Moved center.x should be 10.0")
        assertEquals(20.0, center.y, "Moved center.y should be 20.0")

        // Verify circle's internal center reflects the changes
        assertEquals(10.0, circle.center.x, "Circle's center.x should be 10.0")
        assertEquals(20.0, circle.center.y, "Circle's center.y should be 20.0")
    }

    @Test
    @DisplayName("move() should preserve circle area (inherited from Ellipse.kt)")
    fun testMovePreservesArea() {
        val center = Point(0.0, 0.0)
        val circle = Circle(center, 5.0)

        val originalArea = circle.getArea() // Should be 25*PI

        circle.move(100.0, -50.0) // Move the circle

        assertEquals(originalArea, circle.getArea(), EPSILON, "Area should remain unchanged after move")
    }

    @Test
    @DisplayName("move() method should mutate the original Circle object, not return a new one (inherited from Ellipse.kt)")
    fun testMoveMutatesOriginalCircle() {
        val center = Point(0.0, 0.0)
        val circle = Circle(center, 5.0)
        val originalReference = circle // Store original reference

        circle.move(5.0, 5.0) // Mutate

        // The object itself should be the same instance
        assertTrue(originalReference === circle, "move() should mutate the original Circle object, not return a new instance")
    }

    @Test
    @DisplayName("Private setters for 'center', 'radiusX', and 'radiusY' should prevent direct external modification (inherited from Ellipse.kt)")
    fun testPrivateSettersForProperties() {
        val center = Point(0.0, 0.0)
        val circle = Circle(center, 5.0)

        // Attempting to directly assign to circle.center, circle.radiusX, or circle.radiusY
        // should cause a compilation error.
        // Uncommenting the lines below would result in:
        // "Error: Cannot assign to 'center': the setter is private in 'Ellipse.kt'"
        // circle.center = Point(100.0, 100.0)
        // circle.radiusX = 10.0 // inherited from Ellipse.kt, private setter
        // circle.radiusY = 20.0 // inherited from Ellipse.kt, private setter

        // This test serves as documentation of the intended private setter behavior.
        // We assert that the properties retain their initial values if no move happens.
        assertSame(center, circle.center)
        assertEquals(5.0, circle.radiusX)
        assertEquals(5.0, circle.radiusY)
    }
}
