class Point(x: Double, y: Double) {
    // TODO: Implement Point class

    var x: Double = x
        private set

    var y: Double = y
        private set

    fun clone(): Point {
        return Point(x, y)
    }

    // make an override when Shape class is implemented
    fun move(deltaX: Double, deltaY: Double) {
        this.x += deltaX
        this.y += deltaY
    }
}