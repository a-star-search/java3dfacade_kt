package com.moduleforge.libraries.java3dfacade.factories

import com.moduleforge.libraries.geometry._3d.Point
import com.moduleforge.libraries.java3dfacade.Polygon
import javax.media.j3d.Appearance
import javax.vecmath.Color3f
import java.awt.Color

interface PolygonFactory {
	fun makePolygon(points: List<Point>): Polygon
	fun makePolygon(appearance: Appearance, points: List<Point>): Polygon
	fun makePolygon(color: Color, points: List<Point>): Polygon
	fun makePolygon(color: Color3f, points: List<Point>): Polygon
	fun makePolygon(vararg points: Point): Polygon
	fun makePolygon(appearance: Appearance, vararg points: Point): Polygon
	fun makePolygon(color: Color, vararg points: Point): Polygon
	fun makePolygon(color: Color3f, vararg points: Point): Polygon
}
