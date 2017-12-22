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

}
