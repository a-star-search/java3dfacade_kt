package com.moduleforge.libraries.java3dfacade

import com.moduleforge.libraries.geometry._3d.Point
import org.junit.Before

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
      triangleOnXY = PolygonImpl(listOf(Point(0, 0,0), Point(10, 0, 0), Point(5,10,0)))
      nonIntersectingTriangle = PolygonImpl(listOf(Point(100,100, 0), Point(101, 100,0), Point(101,101,0)))
      nonIntersectingSharingASide = PolygonImpl(listOf(Point(10,0, 0), Point(5, 10, 0), Point(20,20,0)))
      intersectingTriangleOnXY = PolygonImpl(listOf(Point(5,0,0), Point(15, 0, 0), Point(10, 10, 0)))
      intersectingTriangleFacingOppositeOnXY = PolygonImpl(listOf(Point(5,0,0), Point(10,10,0), Point(15,0,0)))
      //plane equation: 4x - 7y + 4z = 25
      triangleOnRandomPlane = PolygonImpl(listOf(Point(1,1,7), Point(2, 2,7.75), Point(0,2,9.75)))
      intersectingTriangleOnRandomPlane = PolygonImpl(
              listOf(Point(1.5, 1.5, 7.375), Point(3, 3, 8.5), Point(2, 3, 9.5)))
      nonIntersectingTriangleOnRandomPlane = PolygonImpl(
              listOf ( Point(3, 3, 8.5), Point(2, 3, 9.5), Point(5, 4, 8.25)))
      concavePolygon = PolygonImpl(
              listOf ( Point(0, 0, 0), Point(10, 0, 0), Point(10, 2, 0), Point(5, 2, 0),
                      Point(5, 8, 0), Point(10, 8, 0), Point(10, 10, 0), Point(0, 10, 0) ))
      polygonInsideGapOfConcavePolygonButNotIntersected = PolygonImpl(
              listOf(Point(6, 3, 0),
                      Point(8, 3, 0),
                      Point(8, 4, 0),
                      Point(6, 4, 0)))
   }
//   @Test
//   fun simpleCases(){
//      assertTrue(triangleOnXY.isThereIntersection(intersectingTriangleOnXY))
//      assertTrue(triangleOnXY.isThereIntersection(intersectingTriangleFacingOppositeOnXY))
//      assertTrue(triangleOnRandomPlane.isThereIntersection(intersectingTriangleOnRandomPlane))
//      assertFalse(triangleOnXY.isThereIntersection(nonIntersectingTriangle))
//      assertFalse(triangleOnXY.isThereIntersection(nonIntersectingSharingASide))
//      assertFalse(triangleOnRandomPlane.isThereIntersection(nonIntersectingTriangleOnRandomPlane))
//   }
//   @Test
//   fun concavePolygon(){
//      assertFalse(concavePolygon.isThereIntersection(polygonInsideGapOfConcavePolygonButNotIntersected))
//   }
}