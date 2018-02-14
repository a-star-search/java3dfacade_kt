package com.moduleforge.libraries.java3dfacade

import com.moduleforge.libraries.geometry._3d.Point
import javax.media.j3d.*
import javax.vecmath.Color3f

internal class Quad : Polygon {
	override val geometryArray: GeometryArray
	override val appearance: Appearance
	internal constructor(pointA: Point, pointB: Point, pointC: Point, pointD: Point, appearance: Appearance) :
			  super(listOf(pointA, pointB, pointC, pointD)) {
      val initialGeometryArray = QuadArray(4, GeometryArray.COORDINATES)
      initialGeometryArray.setCoordinate(0, pointA.asJ3DPoint())
      initialGeometryArray.setCoordinate(1, pointB.asJ3DPoint())
      initialGeometryArray.setCoordinate(2, pointC.asJ3DPoint())
      initialGeometryArray.setCoordinate(3, pointD.asJ3DPoint())
		geometryArray = generateNormals(initialGeometryArray).getGeometryArray()
		this.appearance = appearance
	}
	internal constructor(pointA: Point, pointB: Point, pointC: Point, pointD: Point) :
			  this(pointA, pointB, pointC, pointD, makeDefaultAppearance())
	internal constructor(pointA: Point, pointB: Point, pointC: Point, pointD: Point, color: Color3f) :
			  this(pointA, pointB, pointC, pointD, makeAppearance(makeMaterial(color)))
}
