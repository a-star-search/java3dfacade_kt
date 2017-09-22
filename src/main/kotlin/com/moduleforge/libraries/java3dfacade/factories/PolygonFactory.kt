package com.moduleforge.libraries.java3dfacade

import javax.media.j3d.Appearance
import javax.vecmath.Color3f
import javax.vecmath.Point3d
import com.google.common.base.Preconditions.checkArgument
import com.moduleforge.libraries.geometry.GeometryUtil.differentEnough
import com.moduleforge.libraries.geometry.GeometryUtil.inSamePlane
import java.awt.Color

interface PolygonFactory {
	fun makePolygon(points: List<Point3d>): Polygon

	fun makePolygon(appearance: Appearance, points: List<Point3d>): Polygon

	fun makePolygon(color: Color, points: List<Point3d>): Polygon
	
	fun makePolygon(color: Color3f, points: List<Point3d>): Polygon

	fun makePolygon(vararg points: Point3d): Polygon

	fun makePolygon(appearance: Appearance, vararg points: Point3d): Polygon

	fun makePolygon(color: Color, vararg points: Point3d): Polygon

	fun makePolygon(color: Color3f, vararg points: Point3d): Polygon


	/**
	 * The faces created are all connected to each other.
	 *
	 * All the vertices must lay on the same plane. They must also have an ordering,
	 * so that the direction of the face can be stablished. As you can suppose all
	 * faces created in this way should face the same direction
	 *
	The number of polygons returned will be minimal
	That means five points will return two polygons, one of three and another of four points
	And six points will return two polygons, of four and four points
	 *
	 */
	fun polygonsFromPointsOnAPlane(pointsOnPlane: List<Point3d>): List<Polygon> {
		checkArgument(pointsOnPlane.size >= 3, "There should be a minimum of three vertices.")
		checkArgument(allPointsAreDifferentEnough(pointsOnPlane), "Some of the vertices are equal or almost equal.")
		checkArgument(inSamePlane(pointsOnPlane), "The vertices do not lay on a plane.")
		//TODO I should also check that no three points are in a line...
		//TODO check the direction is consistent
		val polygons: MutableList<Polygon> = mutableListOf()
		polygonsFromPointsOnAPlaneRecursive(ArrayList(pointsOnPlane), polygons)
		return polygons
	}

	private fun allPointsAreDifferentEnough(points: List<Point3d>): Boolean {
		for ((index, outerLoopPoint) in points.withIndex())
			for (innerLoopPoint in points.subList(index + 1, points.size))
				if (!differentEnough(outerLoopPoint, innerLoopPoint)) return false
		return true
	}

	private fun polygonsFromPointsOnAPlaneRecursive(points: MutableList<Point3d>, polygons: MutableList<Polygon>) {
		val pointsNewPolygon = makePointListNextPolygon(points)
		polygons.add(makePolygon(pointsNewPolygon))
		val morePolygons = points.size > 4
		if (morePolygons) {
			cullPointList(points, pointsNewPolygon.size)
			polygonsFromPointsOnAPlaneRecursive(points, polygons)
		}
	}

	private fun makePointListNextPolygon(points: List<Point3d>): List<Point3d> {
		//a polygon is made of either three or four elements
		return if (points.size == 3)
			listOf(points.get(0), points.get(1), points.get(2))
		else
			listOf(points.get(0), points.get(1), points.get(2), points.get(3))
	}

	private fun cullPointList(points: MutableList<Point3d>, pointCountOfLastPolygon: Int) {
		val fromIndex = 1 //the first element is not removed, as it is part of the next polygon
		val removeCount = if ((pointCountOfLastPolygon == 3)) 1 else 2 //remove one if a triangle was made, two if it was a quad
		val toIndex = fromIndex + removeCount
		points.subList(fromIndex, toIndex).clear()
	}
}
