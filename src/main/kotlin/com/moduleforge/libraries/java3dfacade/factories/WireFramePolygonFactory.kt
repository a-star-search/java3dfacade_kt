package com.moduleforge.libraries.java3dfacade

import com.moduleforge.libraries.java3dfacade.Polygon.Companion.makeWireFrameAppearance
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
object WireFramePolygonFactory : PolygonFactory {

	override fun makePolygon(points: List<Point3d>) = makePolygon(*points.toTypedArray())

	override fun makePolygon(appearance: Appearance, points: List<Point3d>) = makePolygon(appearance, *points.toTypedArray())

	override fun makePolygon(color: Color3f, points: List<Point3d>) = makePolygon(color, *points.toTypedArray())

	override fun makePolygon(vararg points: Point3d): Polygon {
		if (points.size < 3) 
			throw IllegalArgumentException("Wrong number of points.")
		return if (points.size == 3) {
			Triangle(points[0], points[1], points[2], makeWireFrameAppearance())
		}else{
			PolygonImpl(points.toList(), makeWireFrameAppearance())
		} 
	}

	override fun makePolygon(appearance: Appearance, vararg points: Point3d): Polygon {
		if (points.size < 3) 
			throw IllegalArgumentException("Wrong number of points.")
		return if (points.size == 3) {
			Triangle(points[0], points[1], points[2], makeWireFrameAppearance())
		} else {
			PolygonImpl(points.toList(), makeWireFrameAppearance())
		}
	}

	override fun makePolygon(color: Color3f, vararg points: Point3d): Polygon {
		if (points.size < 3) 
			throw IllegalArgumentException("Wrong number of points.")
		return if (points.size == 3) {
			Triangle(points[0], points[1], points[2], makeWireFrameAppearance())
		}else{
			PolygonImpl(points.toList(), makeWireFrameAppearance())
		}
	}
}