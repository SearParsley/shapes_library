import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName

// Assuming the Point class is defined in the same module or accessible
// (e.g., in src/main/kotlin/yourpackage/Point.kt)
class PointTest() {

    @Test
    @DisplayName("Point instantiation should correctly set x and y coordinates")
    fun testPointInstantiation() {
        val p = Point(10.0, 20.0)
        assertEquals(10.0, p.x, "X coordinate should be 10.0")
        assertEquals(20.0, p.y, "Y coordinate should be 20.0")
    }

    @Test
    @DisplayName("move() method should correctly update x and y coordinates in-place")
    fun testMoveMethod() {
        val p = Point(5.0, 7.0)
        p.move(2.0, -3.0)
        assertEquals(7.0, p.x, "X coordinate after move should be 7.0")
        assertEquals(4.0, p.y, "Y coordinate after move should be 4.0")

        p.move(-7.0, -4.0) // Move back to origin
        assertEquals(0.0, p.x, "X coordinate after second move should be 0.0")
        assertEquals(0.0, p.y, "Y coordinate after second move should be 0.0")
    }

    @Test
    @DisplayName("move() method should mutate the original object, not return a new one")
    fun testMoveMutatesOriginal() {
        val p = Point(1.0, 1.0)
        val originalHashCode = p.hashCode()
        val originalReference = p // Store original reference

        p.move(10.0, 10.0) // Mutate

        // The object itself should be the same instance
        assertTrue(originalReference === p, "move() should mutate the original object, not return a new instance")
        // Hash code might change if it depends on mutable fields, but the reference should be identical
    }


    @Test
    @DisplayName("clone() method should return a new Point object with same coordinates")
    fun testCloneMethodCopiesValues() {
        val original = Point(15.0, 25.0)
        val cloned = original.clone()

        assertNotSame(original, cloned, "Cloned object should be a different instance")
        assertEquals(original.x, cloned.x, "Cloned X coordinate should match original")
        assertEquals(original.y, cloned.y, "Cloned Y coordinate should match original")
    }

    @Test
    @DisplayName("Modifying cloned object should not affect the original object")
    fun testCloneIndependence() {
        val original = Point(1.0, 2.0)
        val cloned = original.clone()

        cloned.move(5.0, 5.0) // Modify the clone

        assertEquals(1.0, original.x, "Original X should not change after cloning and moving clone")
        assertEquals(2.0, original.y, "Original Y should not change after cloning and moving clone")
        assertEquals(6.0, cloned.x, "Cloned X should reflect its move")
        assertEquals(7.0, cloned.y, "Cloned Y should reflect its move")
    }

    @Test
    @DisplayName("equals() method should return true for points with identical coordinates")
    fun testEqualsTrueForSameCoordinates() {
        val p1 = Point(1.0, 2.0)
        val p2 = Point(1.0, 2.0)
        assertTrue(p1.equals(p2), "Points with same coordinates should be equal")
    }

    @Test
    @DisplayName("equals() method should return false for points with different x coordinates")
    fun testEqualsFalseForDifferentX() {
        val p1 = Point(1.0, 2.0)
        val p2 = Point(3.0, 2.0)
        assertFalse(p1.equals(p2), "Points with different X should not be equal")
    }

    @Test
    @DisplayName("equals() method should return false for points with different y coordinates")
    fun testEqualsFalseForDifferentY() {
        val p1 = Point(1.0, 2.0)
        val p2 = Point(1.0, 4.0)
        assertFalse(p1.equals(p2), "Points with different Y should not be equal")
    }

    @Test
    @DisplayName("equals() method should return false for null object")
    fun testEqualsFalseForNull() {
        val p1 = Point(1.0, 2.0)
        assertFalse(p1.equals(null), "Point should not be equal to null")
    }

    @Test
    @DisplayName("equals() method should return false for different class type")
    fun testEqualsFalseForDifferentType() {
        val p1 = Point(1.0, 2.0)
        val someOtherObject = "I am not a Point"
        assertFalse(p1.equals(someOtherObject), "Point should not be equal to an object of different type")
    }

    @Test
    @DisplayName("equals() method should return true for same object instance")
    fun testEqualsTrueForSameInstance() {
        val p1 = Point(1.0, 2.0)
        val pSame = p1
        assertTrue(p1.equals(pSame), "Point should be equal to itself")
    }

    @Test
    @DisplayName("hashCode() method should return same hash code for equal objects")
    fun testHashCodeForEqualObjects() {
        val p1 = Point(1.0, 2.0)
        val p2 = Point(1.0, 2.0)
        assertEquals(p1.hashCode(), p2.hashCode(), "Equal points should have equal hash codes")
    }

    @Test
    @DisplayName("hashCode() method should return different hash code for unequal objects")
    fun testHashCodeForUnequalObjects() {
        val p1 = Point(1.0, 2.0)
        val p2 = Point(3.0, 4.0)
        assertNotEquals(p1.hashCode(), p2.hashCode(), "Unequal points should have different hash codes")
    }

}