import kotlin.math.abs

class Square(p1: Point, p2: Point) : Rectangle(p1, p2) {
    init {
        require(abs(p1.x - p2.x) == abs(p1.y - p2.y)) { "A square cannot be formed with unequal length and width." }
    }
}