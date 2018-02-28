package com.moduleforge.libraries.java3dfacade

import com.moduleforge.libraries.geometry._3d.Point
import javax.media.j3d.Appearance
import javax.media.j3d.GeometryArray
import javax.media.j3d.GeometryArray.COORDINATES
import javax.media.j3d.TriangleArray
import javax.vecmath.Color3f

internal class Triangle : Polygon {
	override val geometryArray: GeometryArray
	override val appearance: Appearance
	internal constructor(pointA: Point, pointB: Point, pointC: Point, appearance: Appearance) : super(listOf(pointA, pointB, pointC)) {
      val initialGeometryArray = TriangleArray(3, COORDINATES)
      initialGeometryArray.setCoordinate(0, asJ3DPoint(pointA))
      initialGeometryArray.setCoordinate(1, asJ3DPoint(pointB))
      initialGeometryArray.setCoordinate(2, asJ3DPoint(pointC))
		geometryArray = generateNormals(initialGeometryArray).getGeometryArray()
		this.appearance = appearance
	}
	internal constructor(pointA: Point, pointB: Point, pointC: Point) :
			  this(pointA, pointB, pointC, makeDefaultAppearance())
	internal constructor(pointA: Point, pointB: Point, pointC: Point, color: Color3f) :
			  this(pointA, pointB, pointC, makeAppearance(makeMaterial(color)))
}
