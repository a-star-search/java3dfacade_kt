package com.moduleforge.libraries.java3dfacade

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import javax.vecmath.Point3d

/**
 * visualize points if necessary on https://technology.cpm.org/general/3dgraph/
 */
class PolygonTest {

   private lateinit var triangleOnXY: Polygon
   private lateinit var intersectingTriangleOnXY: Polygon
   private lateinit var nonIntersectingTriangle: Polygon
   private lateinit var nonIntersectingSharingASide: Polygon
   private lateinit var intersectingTriangleFacingOppositeOnXY: Polygon
   private lateinit var triangleOnRandomPlane: Polygon
   private lateinit var intersectingTriangleOnRandomPlane: Polygon
   private lateinit var nonIntersectingTriangleOnRandomPlane: Polygon
   private lateinit var concavePolygon: Polygon
   private lateinit var polygonInsideGapOfConcavePolygonButNotIntersected: Polygon

   @Before
   fun setUp(){
      triangleOnXY = PolygonImpl(
              listOf (Point3d(0.0, 0.0, 0.0),
                      Point3d(10.0, 0.0, 0.0),
                      Point3d(5.0, 10.0, 0.0)))

      nonIntersectingTriangle = PolygonImpl(
              listOf (Point3d(100.0, 100.0, 0.0),
                      Point3d(101.0, 100.0, 0.0),
                      Point3d(101.0, 101.0, 0.0)))

      nonIntersectingSharingASide = PolygonImpl(
              listOf (Point3d(10.0, 0.0, 0.0),
                      Point3d(5.0, 10.0, 0.0),
                      Point3d(20.0, 20.0, 0.0)))

      intersectingTriangleOnXY = PolygonImpl(
              listOf (Point3d(5.0, 0.0, 0.0),
                      Point3d(15.0, 0.0, 0.0),
                      Point3d(10.0, 10.0, 0.0)))

      intersectingTriangleFacingOppositeOnXY = PolygonImpl(
              listOf ( Point3d(5.0, 0.0, 0.0),
                      Point3d(10.0, 10.0, 0.0),
                      Point3d(15.0, 0.0, 0.0)))

      //plane equation: 4x - 7y + 4z = 25
      triangleOnRandomPlane = PolygonImpl(
              listOf ( Point3d(1.0, 1.0, 7.0),
                      Point3d(2.0, 2.0, 7.75),
                      Point3d(0.0, 2.0, 9.75)))
      intersectingTriangleOnRandomPlane = PolygonImpl(
              listOf ( Point3d(1.5, 1.5, 7.375),
                      Point3d(3.0, 3.0, 8.5),
                      Point3d(2.0, 3.0, 9.5)))
      nonIntersectingTriangleOnRandomPlane = PolygonImpl(
              listOf ( Point3d(3.0, 3.0, 8.5),
                      Point3d(2.0, 3.0, 9.5),
                      Point3d(5.0, 4.0, 8.25)))
      concavePolygon = PolygonImpl(
              listOf ( Point3d(0.0, 0.0, 0.0),
                      Point3d(10.0, 0.0, 0.0),
                      Point3d(10.0, 2.0, 0.0),
                      Point3d(5.0, 2.0, 0.0),
                      Point3d(5.0, 8.0, 0.0),
                      Point3d(10.0, 8.0, 0.0),
                      Point3d(10.0, 10.0, 0.0),
                      Point3d(0.0, 10.0, 0.0) ))
      polygonInsideGapOfConcavePolygonButNotIntersected = PolygonImpl(
              listOf(javax.vecmath.Point3d(6.0, 3.0, 0.0),
                      javax.vecmath.Point3d(8.0, 3.0, 0.0),
                      javax.vecmath.Point3d(8.0, 4.0, 0.0),
                      javax.vecmath.Point3d(6.0, 4.0, 0.0)))
   }

   @Test
   fun simpleCases(){
      assertTrue(triangleOnXY.isThereIntersection(intersectingTriangleOnXY))
      assertTrue(triangleOnXY.isThereIntersection(intersectingTriangleFacingOppositeOnXY))
      assertTrue(triangleOnRandomPlane.isThereIntersection(intersectingTriangleOnRandomPlane))
      assertFalse(triangleOnXY.isThereIntersection(nonIntersectingTriangle))
      assertFalse(triangleOnXY.isThereIntersection(nonIntersectingSharingASide))
      assertFalse(triangleOnRandomPlane.isThereIntersection(nonIntersectingTriangleOnRandomPlane))
   }

   @Test
   fun concavePolygon(){
      assertFalse(concavePolygon.isThereIntersection(polygonInsideGapOfConcavePolygonButNotIntersected))
   }
}