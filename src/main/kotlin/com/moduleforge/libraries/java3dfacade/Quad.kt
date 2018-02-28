package com.moduleforge.libraries.java3dfacade

import com.moduleforge.libraries.geometry._3d.Point
import javax.media.j3d.Appearance
import javax.media.j3d.GeometryArray
import javax.media.j3d.QuadArray
import javax.vecmath.Color3f

internal class Quad : Polygon {
	override val geometryArray: GeometryArray
	override val appearance: Appearance
	internal constructor(pointA: Point, pointB: Point, pointC: Point, pointD: Point, appearance: Appearance) :
			  super(listOf(pointA, pointB, pointC, pointD)) {
      val initialGeometryArray = QuadArray(4, GeometryArray.COORDINATES)
      initialGeometryArray.setCoordinate(0, asJ3DPoint(pointA))
      initialGeometryArray.setCoordinate(1, asJ3DPoint(pointB))
      initialGeometryArray.setCoordinate(2, asJ3DPoint(pointC))
      initialGeometryArray.setCoordinate(3, asJ3DPoint(pointD))
		geometryArray = generateNormals(initialGeometryArray).geometryArray
		this.appearance = appearance
	}
	internal constructor(pointA: Point, pointB: Point, pointC: Point, pointD: Point) :
			  this(pointA, pointB, pointC, pointD, makeDefaultAppearance())
	internal constructor(pointA: Point, pointB: Point, pointC: Point, pointD: Point, color: Color3f) :
			  this(pointA, pointB, pointC, pointD, makeAppearance(makeMaterial(color)))
}
