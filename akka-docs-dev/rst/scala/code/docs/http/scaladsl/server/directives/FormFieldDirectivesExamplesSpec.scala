/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package docs.http.scaladsl.server
package directives

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model._

class FormFieldDirectivesExamplesSpec extends RoutingSpec {

  "formFields" in {
    val route =
      formFields('color, 'age.as[Int]) { (color, age) =>
        complete(s"The color is '$color' and the age ten years ago was ${age - 10}")
      }

    Post("/", FormData(Seq("color" -> "blue", "age" -> "68"))) ~> route ~> check {
      responseAs[String] shouldEqual "The color is 'blue' and the age ten years ago was 58"
    }

    Get("/") ~> Route.seal(route) ~> check {
      status shouldEqual StatusCodes.BadRequest
      responseAs[String] shouldEqual "Request is missing required form field 'color'"
    }
  }

}
