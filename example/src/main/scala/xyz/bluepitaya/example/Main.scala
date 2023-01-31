package xyz.bluepitaya.example

import com.raquo.laminar.api.L._
import org.scalajs.dom
import xyz.bluepitaya.common.Hsv
import xyz.bluepitaya.laminarcolor.pickers.ChromePicker
import xyz.bluepitaya.laminarcolor.pickers.SimplePicker
import xyz.bluepitaya.laminarcolor.pickers.SketchPicker

object Main extends App {
  val hsvColor = Var(Hsv(200, 0.75, 0.75, 1))
  val colorSignal = hsvColor.signal
  val updateHue = (h: Double) => hsvColor.update(c => c.copy(h = h))

  val app = div(
    div(h2("Current color"), child.text <-- hsvColor.signal.map(_.toCssRgba)),
    h2("Color picerks"),
    div(
      display.flex,
      flexDirection.row,
      ChromePicker.component(hsvColor),
      SketchPicker.component(hsvColor).amend(marginLeft("30px")),
      SimplePicker.component(hsvColor).amend(marginLeft("30px"))
    )
  )

  val containerNode = dom.document.querySelector("#app")

  render(containerNode, app)
}
