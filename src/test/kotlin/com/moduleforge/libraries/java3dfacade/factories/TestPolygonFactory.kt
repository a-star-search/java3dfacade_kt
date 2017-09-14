package com.moduleforge.libraries.java3dfacade

import org.junit.Test
import javax.vecmath.Point3d
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

class TestPolygonFactory {
	
   @Test(expected = IllegalArgumentException::class)
	fun testPolygonsFromPointsOnAPlane_TwoPointsInSpace() {
		val points = listOf(Point3d(3.0, 4.0, 5.0), Point3d(6.0, 7.0, 8.0))
		DefaultPolygonFactory.polygonsFromPointsOnAPlane(points)
	}

   @Test
	fun testPolygonsFromPointsOnAPlane_ThreePointsInSpaceReturnsOnePolygon() {
		//any three random different points
		val points = listOf(Point3d(1.0, 10.0, 5.0), Point3d(0.0, -7.0, 8.0), Point3d(3.0, 10.0, 1.0))
		val polygon = DefaultPolygonFactory.polygonsFromPointsOnAPlane(points)
		//returns a single polygon
		assertEquals(polygon.size, 1)
	}

   @Test
	fun testPolygonsFromPointsOnAPlane_FourPointsInSpaceReturnsOnePolygon() {
		val points = listOf(Point3d(3.0, 4.0, 0.0), Point3d(5.0, 6.0, 0.0),
				  Point3d(9.0, 10.0, 0.0), Point3d(12.0, 13.0, 0.0)) //all of them are in the x, y plane
		val polygon = DefaultPolygonFactory.polygonsFromPointsOnAPlane(points)
		//returns a single polygon
		assertEquals(polygon.size, 1)
	}
	
	
   @Test(expected = IllegalArgumentException::class)
	fun testPolygonsFromPointsOnAPlane_FourPointsInSpaceNotInSamePlane() {
		val points = listOf(Point3d(3.0, 4.0, 0.0), Point3d(5.0, 6.0, 0.0),
				  Point3d(9.0, 10.0, 0.0), Point3d(12.0, 13.0, 1.0)) //all of them are in the x, y plane except the last one
		DefaultPolygonFactory.polygonsFromPointsOnAPlane(points)
	}
	
   @Test
	fun testPolygonsFromPointsOnAPlane_FourPointsInSpaceReturnsOnePolygonWithSamePoints() {
		val points = listOf(Point3d(3.0, 4.0, 0.0), Point3d(5.0, 6.0, 0.0),
				  Point3d(9.0, 10.0, 0.0), Point3d(12.0, 13.0, 0.0)) //all of them are in the x, y plane
		val polygon = DefaultPolygonFactory.polygonsFromPointsOnAPlane(points)
		
		//same points
		//actually, to be very precise, I should use an almost-equal comparator, but
		//there is never any reason for the algorithm to create new, different points
		assertEquals(polygon.get(0).points.toSet(), points.toSet())
	}
	
   @Test
	fun testPolygonsFromPointsOnAPlane_MinimalNumberOfPolygonsCreated() {
		//all of them are in the x, y plane. This is an heptagon and the points are ordered
		val points = listOf(
				  Point3d(2.0, 0.0, 0.0), Point3d(1.0, 0.0, 0.0),
				  Point3d(0.0, 1.0, 0.0), Point3d(1.0, 2.0, 0.0),
				  Point3d(1.5, 2.1, 0.0), Point3d(2.0, 2.0, 0.0),
				  Point3d(3.0, 1.0, 0.0) )

		val polygonsFromFiveSides = DefaultPolygonFactory.polygonsFromPointsOnAPlane(points.subList(0, 5))
		val polygonsFromSixSides = DefaultPolygonFactory.polygonsFromPointsOnAPlane(points.subList(0, 6))
		val polygonsFromSevenSides = DefaultPolygonFactory.polygonsFromPointsOnAPlane(points)
		assertEquals(polygonsFromFiveSides.size, 2)
		assertEquals(polygonsFromSixSides.size, 2)
		assertEquals(polygonsFromSevenSides.size, 3)
	}
}
