package com.moduleforge.libraries.java3dfacade

import javax.media.j3d.Appearance
import javax.vecmath.Color3f
import javax.vecmath.Point3d

/**
 * When making a polygon (triangle or quad) the order of points determines the direction of the
 * face.
 * 
 * In order means that the counterclockwise direction of the vertices indicate
 * the side of the face
 * 
 * It doesn't really matter what element goes in which position of the list,
 * only the relative ordering. It should be understood as a circular list.
 * 
 */
object PolygonFactory {
	fun makePolygon(points: List<Point3d>): Polygon {
		return makePolygon(*points.toTypedArray())
	}

	fun makePolygon(appearance: Appearance, points: List<Point3d>): Polygon {
		return makePolygon(appearance, *points.toTypedArray())
	}

	fun makePolygon(color: Color3f, points: List<Point3d>): Polygon {
		return makePolygon(color, *points.toTypedArray())
	}

	fun makePolygon(vararg points: Point3d): Polygon {
		if (points.size < 3 || points.size > 4) {
			throw IllegalArgumentException("Wrong number of points.")
		}
		if (points.size == 3) {
			return Triangle(points[0], points[1], points[2])
		}
		return Quad(points[0], points[1], points[2], points[3])
	}

	fun makePolygon(appearance: Appearance, vararg points: Point3d): Polygon {
		if (points.size < 3 || points.size > 4) {
			throw IllegalArgumentException("Wrong number of points.")
		}
		if (points.size == 3) {
			return Triangle(points[0], points[1], points[2], appearance)
		}
		return Quad(points[0], points[1], points[2], points[3], appearance)
	}

	fun makePolygon(color: Color3f, vararg points: Point3d): Polygon {
		if (points.size < 3 || points.size > 4) {
			throw IllegalArgumentException("Wrong number of points.")
		}
		if (points.size == 3) {
			return Triangle(points[0], points[1], points[2], color)
		}
		return Quad(points[0], points[1], points[2], points[3], color)
	}

	fun makeTriangle(pointA: Point3d, pointB: Point3d, pointC: Point3d): Polygon {
		return Triangle(pointA, pointB, pointC)
	}

	fun makeTriangle(appearance: Appearance, pointA: Point3d, pointB: Point3d, pointC: Point3d): Polygon {
		return Triangle(pointA, pointB, pointC, appearance)
	}

	fun makeTriangle(color: Color3f, pointA: Point3d, pointB: Point3d, pointC: Point3d): Polygon {
		return Triangle(pointA, pointB, pointC, color)
	}

	fun makeQuad(appearance: Appearance, pointA: Point3d, pointB: Point3d, pointC: Point3d, pointD: Point3d): Polygon {
		return Quad(pointA, pointB, pointC, pointD, appearance)
	}

	fun makeQuad(pointA: Point3d, pointB: Point3d, pointC: Point3d, pointD: Point3d): Polygon {
		return Quad(pointA, pointB, pointC, pointD)
	}	
}