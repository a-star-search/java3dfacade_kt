package com.moduleforge.libraries.java3dfacade

import com.google.common.collect.ImmutableList
import com.sun.j3d.utils.geometry.GeometryInfo
import com.sun.j3d.utils.geometry.NormalGenerator
import java.util.Collections
import javax.media.j3d.Appearance
import javax.media.j3d.GeometryArray
import javax.media.j3d.Material
import javax.media.j3d.Shape3D
import javax.media.j3d.Texture
import javax.media.j3d.Texture2D
import javax.media.j3d.TextureAttributes
import javax.vecmath.Color3f
import javax.vecmath.Color4f
import javax.vecmath.Point3d
import com.google.common.base.Preconditions
import com.moduleforge.libraries.geometry.GeometryUtil.differentEnough
import com.moduleforge.libraries.geometry.GeometryUtil.inSamePlane
import java.util.Arrays
import com.moduleforge.libraries.java3dfacade.PolygonFactory.makePolygon

abstract class Polygon {

	val shape: Shape3D by lazy {
		initializeShape()
	}
	/**
	 * Points do have an order that determines the face direction
	 */
	val points: List<Point3d>
	val segments: Set<Pair<Point3d, Point3d>>

	protected constructor(points: List<Point3d>) {
		if (points.size < 3)
			throw IllegalArgumentException("Too few points to build a polygon.")
		this.points = ImmutableList.Builder<Point3d>().addAll(points).build();
		segments = makeSegments(points)
	}

	protected abstract fun initializeShape(): Shape3D

	companion object {

		@JvmStatic private fun makeSegments(points: List<Point3d>): Set<Pair<Point3d, Point3d>> {
			val segments: Set<Pair<Point3d, Point3d>> = mutableSetOf()
			var previousPoint = points.last()
			for (point in points) {
				segments.plusElement(Pair(previousPoint, point))
				previousPoint = point
			}
			return Collections.unmodifiableSet(segments);
		}

		@JvmStatic protected fun generateNormals(ga: GeometryArray): GeometryInfo {
			val geometryInfo: GeometryInfo = GeometryInfo(ga)
			val ng: NormalGenerator = NormalGenerator()
			ng.generateNormals(geometryInfo)
			return geometryInfo
		}

		@JvmStatic protected fun makeDefaultAppearance() = makeAppearance(makeMaterial())

		@JvmStatic protected fun makeAppearance(material: Material): Appearance {
			val texAttr = TextureAttributes()
			texAttr.setTextureMode(TextureAttributes.MODULATE)
			val appearance = Appearance()
			appearance.setTextureAttributes(texAttr)
			appearance.setMaterial(material)
			val texture = Texture2D()
			texture.setBoundaryModeS(Texture.WRAP)
			texture.setBoundaryModeT(Texture.WRAP)
			texture.setBoundaryColor(Color4f(0.0f, 1.0f, 0.0f, 0.0f))
			appearance.setTexture(texture)
			return appearance
		}

		/**
		 * default color is white
		 */
		@JvmStatic protected fun makeMaterial(color: Color3f = Color3f(1.0f, 1.0f, 1.0f)): Material {
			val black: Color3f = Color3f(0.0f, 0.0f, 0.0f)
			val white: Color3f = Color3f(1.0f, 1.0f, 1.0f)
			return Material(color, black, color, white, 70f)
		}

		/**
		 * The faces created are all connected to each other.
		 *
		 * All the vertices must lay on the same plane. They must also have an ordering,
		 * so that the direction of the face can be stablished. As you can suppose all
		 * faces created in this way should face the same direction
		 *
		 */
		@JvmStatic fun polygonsFromPointsOnAPlane(pointsOnPlane: List<Point3d>): List<Polygon> {
			Preconditions.checkArgument(pointsOnPlane.size >= 3, "There should be a minimum of three vertices.")
			Preconditions.checkArgument(allPointsAreDifferentEnough(pointsOnPlane), "Some of the vertices are equal or almost equal.")
			Preconditions.checkArgument(inSamePlane(pointsOnPlane), "The vertices do not lay on a plane.")
			val polygons: MutableList<Polygon> = mutableListOf()
			polygonsFromPointsOnAPlaneRecursive(ArrayList(pointsOnPlane), polygons)
			return polygons
		}

		private fun allPointsAreDifferentEnough(points: List<Point3d>): Boolean {
			for ((index, outerLoopPoint) in points.withIndex())
				for (innerLoopPoint in points.subList(index + 1, points.size))
					if (!differentEnough(outerLoopPoint, innerLoopPoint)) return false
			return true
		}

		private fun polygonsFromPointsOnAPlaneRecursive(points: MutableList<Point3d>, polygons: MutableList<Polygon>) {
			val pointsNewPolygon = makePointListNextPolygon(points)
			polygons.add(makePolygon(pointsNewPolygon))
			val morePolygons = points.size > 4
			if (morePolygons) {
				cullPointList(points, pointsNewPolygon.size)
				polygonsFromPointsOnAPlaneRecursive(points, polygons)
			}
		}

		private fun makePointListNextPolygon(points: List<Point3d>): List<Point3d> {
			//a polygon is made of either three or four elements
			return if (points.size == 3)
				listOf(points.get(0), points.get(1), points.get(2))
			else
				listOf(points.get(0), points.get(1), points.get(2), points.get(3))
		}

		private fun cullPointList(points: MutableList<Point3d>, pointCountOfLastPolygon: Int) {
			val fromIndex = 1 //the first element is not removed, as it is part of the next polygon
			val removeCount = if ((pointCountOfLastPolygon == 3)) 1 else 2 //remove one if a triangle was made, two if it was a quad
			val toIndex = fromIndex + removeCount
			points.subList(fromIndex, toIndex).clear()
		}
	}

}
