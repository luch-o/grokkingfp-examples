case class Point(x: Int, y: Int)

val points   = List(Point(5, 2), Point(1, 1))
val riskyRadiuses = List(-10, 0, 2)

def isInside(point: Point, radius: Int): Boolean = {
  radius * radius >= point.x * point.x + point.y * point.y
}
for {
  r     <- riskyRadiuses
  point <- points.filter(p => isInside(p, r))
} yield s"$point is within a radius of $r"

// filter invalid radiuses with
// 1. using filter on list
for {
  r     <- riskyRadiuses.filter(r => r > 0)
  point <- points.filter(p => isInside(p, r))
} yield s"$point is within a radius of $r"

// 2. with guard expression (if in the for comprenhension)
for {
  r     <- riskyRadiuses
  if (r > 0)
  point <- points
  if (isInside(point, r))
} yield s"$point is within a radius of $r"

// 3. using a funtion passed to flatmap using it as an enumerator
def validateRadius(radius: Int): List[Int] = {
    if (radius > 0) List(radius) else List.empty
}
def isInsideFilter(point: Point, radius: Int): List[Point] = {
    if (isInside(point, radius)) List (point) else List.empty
}
for {
  r           <- riskyRadiuses
  validRadius <- validateRadius(r)
  point       <- points
  inPoint     <- inPoint(point, validRadius)
} yield s"$point is within a radius of $r"
