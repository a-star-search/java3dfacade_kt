package com.moduleforge.libraries.java3dfacade

import javax.media.j3d.Appearance
import javax.media.j3d.GeometryArray
import javax.media.j3d.TriangleFanArray
import javax.vecmath.Color3f
import javax.vecmath.Point3d

internal class PolygonImpl : Polygon {

	override val geometryArray: GeometryArray
	override val appearance: Appearance

	internal constructor(points: List<Point3d>, appearance: Appearance) :
			  super(points) {
		val initialGeometryArray = TriangleFanArray(points.size, GeometryArray.COORDINATES, intArrayOf(points.size))
		for ((index, elem) in points.withIndex()) {
			initialGeometryArray.setCoordinate(index, elem)
		}
		geometryArray = generateNormals(initialGeometryArray).geometryArray
		this.appearance = appearance
	}

	internal constructor(points: List<Point3d>) :
			  this(points, makeDefaultAppearance())

	internal constructor(points: List<Point3d>, color: Color3f) :
			  this(points, makeAppearance(makeMaterial(color)))

}
