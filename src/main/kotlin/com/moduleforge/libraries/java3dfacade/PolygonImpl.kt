package com.moduleforge.libraries.java3dfacade

import com.moduleforge.libraries.geometry._3d.Point
import javax.media.j3d.Appearance
import javax.media.j3d.GeometryArray
import javax.media.j3d.TriangleFanArray
import javax.vecmath.Color3f

internal class PolygonImpl : Polygon {

	override val geometryArray: GeometryArray
	override val appearance: Appearance

	internal constructor(points: List<Point>, appearance: Appearance) :
			  super(points) {
		val initialGeometryArray = TriangleFanArray(points.size, GeometryArray.COORDINATES, intArrayOf(points.size))
		for ((index, elem) in points.withIndex())
			initialGeometryArray.setCoordinate(index, elem.asJ3DPoint())
		geometryArray = generateNormals(initialGeometryArray).geometryArray
		this.appearance = appearance
	}

	internal constructor(points: List<Point>) :
			  this(points, makeDefaultAppearance())

	internal constructor(points: List<Point>, color: Color3f) :
			  this(points, makeAppearance(makeMaterial(color)))

}
