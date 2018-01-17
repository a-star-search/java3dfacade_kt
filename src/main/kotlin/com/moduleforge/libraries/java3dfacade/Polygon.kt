package com.moduleforge.libraries.java3dfacade

import com.google.common.collect.ImmutableList
import com.moduleforge.libraries.geometry.GeometryUtil
import com.moduleforge.libraries.geometry.GeometryUtil.*
import com.sun.j3d.utils.geometry.GeometryInfo
import com.sun.j3d.utils.geometry.NormalGenerator
import java.lang.Math.sqrt
import java.util.Collections
import javax.media.j3d.Appearance
import javax.media.j3d.GeometryArray
import javax.media.j3d.Material
import javax.media.j3d.PolygonAttributes
import javax.media.j3d.Shape3D
import javax.media.j3d.Texture
import javax.media.j3d.Texture2D
import javax.media.j3d.TextureAttributes
import javax.vecmath.Color3f
import javax.vecmath.Color4f
import javax.vecmath.Point3d
import org.locationtech.jts.geom.Coordinate as JTSCoordinate
import org.locationtech.jts.geom.GeometryFactory as JTSGeometryFactory
import org.locationtech.jts.geom.Polygon as JTSPolygon

abstract class Polygon protected constructor(points: List<Point3d>) {

	protected abstract val geometryArray: GeometryArray
	abstract val appearance: Appearance

	/**
	 * Points do have an order that determines the face direction
	 */
	val points: List<Point3d>
	val segments: Set<Pair<Point3d, Point3d>>
   private val jtsPolygon: JTSPolygon
   val shape: Shape3D by lazy {
      Shape3D(geometryArray, appearance)
   }

   private fun toJTSPolygon(points: List<Point3d>): JTSPolygon {
      val coords = points.map { JTSCoordinate(it.x, it.y, it.z) }.toMutableList()
      val first = points[0]
      coords.add(JTSCoordinate(first.x, first.y, first.z)) //append the first point to close the polygon
      return JTSGeometryFactory().createPolygon(coords.toTypedArray())
   }

	/**
	 * Determine if this polygon intersects another in the same plane.
    *
    * If the polygon crosses in a different plane or if they share a border (either on the same plane
    * or in a different plane) the method returns false.
	 */
   fun isThereIntersection(other: Polygon): Boolean {
      val intersection =  jtsPolygon.intersection(other.jtsPolygon).area
      val intersectionInLinearUnits = sqrt(intersection)
      return !almostZero(intersectionInLinearUnits)
   }

	fun areaExactlyMatch(other: Polygon): Boolean {
      val intersection = jtsPolygon.intersection(other.jtsPolygon).area
      val diffArea = intersection - jtsPolygon.area
      val diffAreaInLinearUnits = sqrt(diffArea)
      return almostZero(diffAreaInLinearUnits)
   }


	companion object {

		@JvmStatic private fun makeSegments(points: List<Point3d>): Set<Pair<Point3d, Point3d>> {
			val segments: MutableSet<Pair<Point3d, Point3d>> = mutableSetOf()
			var previousPoint = points.last()
			for (point in points) {
				segments.add(Pair(previousPoint, point))
				previousPoint = point
			}
			return Collections.unmodifiableSet(segments);
		}

		@JvmStatic protected fun generateNormals(ga: GeometryArray): GeometryInfo {
			val geometryInfo = GeometryInfo(ga)
			NormalGenerator().generateNormals(geometryInfo)
			return geometryInfo
		}

		@JvmStatic protected fun makeDefaultAppearance() = makeAppearance(makeMaterial())

		@JvmStatic protected fun makeAppearance(material: Material): Appearance {
			val texAttr = TextureAttributes()
			texAttr.textureMode = TextureAttributes.MODULATE
			val appearance = Appearance()
			appearance.textureAttributes = texAttr
			appearance.material = material
			val texture = Texture2D()
			texture.boundaryModeS = Texture.WRAP
			texture.boundaryModeT = Texture.WRAP
			texture.setBoundaryColor(Color4f(0f, 1f, 0f, 0f))
			appearance.texture = texture
			return appearance
		}

		@JvmStatic internal fun makeWireFrameAppearance(): Appearance {
			val pa = PolygonAttributes()
			pa.polygonMode = PolygonAttributes.POLYGON_LINE
			pa.cullFace = PolygonAttributes.CULL_NONE
			return Appearance().apply{setPolygonAttributes(pa)}
		}

		/**
		 * default color is white
		 */
		@JvmStatic protected fun makeMaterial(color: Color3f = Color3f(1.0f, 1.0f, 1.0f)): Material {
			val black = Color3f(0.0f, 0.0f, 0.0f)
			val white = Color3f(1.0f, 1.0f, 1.0f)
			return Material(color, black, color, white, 70f)
		}
	}

   init {
      if (points.size < 3)
         throw IllegalArgumentException("Too few points to build a polygon.")
      this.points = ImmutableList.copyOf(points)
      segments = makeSegments(points)
      jtsPolygon = toJTSPolygon(points)
   }

}
