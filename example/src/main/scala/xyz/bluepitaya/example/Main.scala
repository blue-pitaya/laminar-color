package xyz.bluepitaya.example

import com.raquo.laminar.api.L._
import org.scalajs.dom
import xyz.bluepitaya.laminarcolor.ColorPicker

object Main extends App {
  val hsvColor = Var(ColorPicker.Hsv(200, 0.75, 0.75))
  val app = div(
    ColorPicker.createExample(hsvColor)
  )
  val containerNode = dom.document.querySelector("#app")

  render(containerNode, app)
}
