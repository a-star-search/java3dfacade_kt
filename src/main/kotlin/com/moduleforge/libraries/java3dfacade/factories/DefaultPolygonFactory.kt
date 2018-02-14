package com.moduleforge.libraries.java3dfacade.factories

import com.moduleforge.libraries.geometry._3d.Point
import com.moduleforge.libraries.java3dfacade.Polygon
import com.moduleforge.libraries.java3dfacade.PolygonImpl
import com.moduleforge.libraries.java3dfacade.Triangle
import java.awt.Color
import javax.media.j3d.Appearance
import javax.vecmath.Color3f

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
object DefaultPolygonFactory: PolygonFactory {
	override fun makePolygon(points: List<Point>) = makePolygon(*points.toTypedArray())
	override fun makePolygon(appearance: Appearance, points: List<Point>): Polygon = makePolygon(appearance, *points.toTypedArray())
	override fun makePolygon(color: Color, points: List<Point>): Polygon = makePolygon(Color3f(color), *points.toTypedArray())
	override fun makePolygon(color: Color3f, points: List<Point>): Polygon = makePolygon(color, *points.toTypedArray())
	override fun makePolygon(vararg points: Point): Polygon {
		if (points.size < 3) {
			throw IllegalArgumentException("Wrong number of points.")
		}
		return if (points.size == 3) {
			Triangle(points[0], points[1], points[2])
		} else {
			PolygonImpl(points.toList())
		}
	}
	override fun makePolygon(appearance: Appearance, vararg points: Point): Polygon {
		if (points.size < 3) 
			throw IllegalArgumentException("Wrong number of points.")
		return if (points.size == 3) {
			Triangle(points[0], points[1], points[2], appearance)
		} else {
			PolygonImpl(points.toList(), appearance)
		}
	}
	override fun makePolygon(color: Color, vararg points: Point): Polygon = makePolygon(Color3f(color), *points)
	override fun makePolygon(color: Color3f, vararg points: Point): Polygon {
		if (points.size < 3) 
			throw IllegalArgumentException("Wrong number of points.")
		return if (points.size == 3)
			 	Triangle(points[0], points[1], points[2], color)
			else
				PolygonImpl(points.toList(), color)
	}

}