package com.moduleforge.libraries.java3dfacade

import javax.vecmath.Point3d
import javax.media.j3d.*
import com.sun.j3d.utils.geometry.GeometryInfo;
import javax.vecmath.Color3f

internal class Quad : Polygon {

	override val geometryArray: GeometryArray
	override val appearance: Appearance

	internal constructor(pointA: Point3d, pointB: Point3d, pointC: Point3d, pointD: Point3d, appearance: Appearance) :
			  super(listOf(pointA, pointB, pointC, pointD)) {
      val initialGeometryArray = QuadArray(4, GeometryArray.COORDINATES)
      initialGeometryArray.setCoordinate(0, pointA)
      initialGeometryArray.setCoordinate(1, pointB)
      initialGeometryArray.setCoordinate(2, pointC)
      initialGeometryArray.setCoordinate(3, pointD)
		geometryArray = generateNormals(initialGeometryArray).getGeometryArray()
		this.appearance = appearance
	}

	internal constructor(pointA: Point3d, pointB: Point3d, pointC: Point3d, pointD: Point3d) :
			  this(pointA, pointB, pointC, pointD, makeDefaultAppearance())

	internal constructor(pointA: Point3d, pointB: Point3d, pointC: Point3d, pointD: Point3d, color: Color3f) :
			  this(pointA, pointB, pointC, pointD, makeAppearance(makeMaterial(color)))

}
