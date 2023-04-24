package xyz.bluepitaya.laminarcolor.models

package xyz.bluepitaya.common

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class HsvSpec extends AnyFlatSpec with Matchers {
  "hsv and rgb convertions" should "be the same both ways" in {
    (0 to 255)
      .zip((0 to 255))
      .zip(0 to 255)
      .map { case ((r, g), b) =>
        val hsv = Hsv.fromRgb(r, g, b)
        val rgb = hsv.toRgba

        (r, g, b) shouldEqual (rgb.r, rgb.g, rgb.b)
      }
  }
}
