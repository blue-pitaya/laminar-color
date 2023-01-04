package xyz.bluepitaya.example

import com.raquo.laminar.api.L._
import org.scalajs.dom
import xyz.bluepitaya.laminarcolor.ColorPicker
import xyz.bluepitaya.laminarcolor.pickers.ChromePicker

object Main extends App {
  val hsvColor = Var(ColorPicker.Hsv(200, 0.75, 0.75, 1))
  val app = div(
    div(h2("Current color"), child.text <-- hsvColor.signal.map(_.toCssRgba)),
    div(h2("Color picerks"), ChromePicker.component(hsvColor))
  )
  val containerNode = dom.document.querySelector("#app")

  render(containerNode, app)
}
