class Point(x: Double, y: Double) : Shape() {
    var x: Double = x
        private set

    var y: Double = y
        private set

    fun clone(): Point {
        return Point(x, y)
    }

    override fun move(deltaX: Double, deltaY: Double) {
        this.x += deltaX
        this.y += deltaY
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}