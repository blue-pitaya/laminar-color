package xyz.bluepitaya.example

import com.raquo.laminar.api.L._
import org.scalajs.dom
import xyz.bluepitaya.laminarcolor.models.Hsv
import xyz.bluepitaya.laminarcolor.pickers.ChromePicker
import xyz.bluepitaya.laminarcolor.pickers.SimplePicker
import xyz.bluepitaya.laminarcolor.pickers.SketchPicker

object Main extends App {
  val color1 = Var(Hsv(200, 0.75, 0.75, 1))
  val color2 = Var(Hsv(100, 0.75, 0.75, 1))

  val app = div(
    div(h2("Current color1"), child.text <-- color1.signal.map(_.toCssRgba)),
    div(h2("Current color2"), child.text <-- color2.signal.map(_.toCssRgba)),
    h2("Color picerks"),
    div(
      display.flex,
      flexDirection.row,
      ChromePicker.component(color1.signal, color1.writer),
      SketchPicker
        .component(color1.signal, color1.writer)
        .amend(marginLeft("30px")),
      SimplePicker
        .component(color2.signal, color2.writer)
        .amend(marginLeft("30px"))
    )
  )

  val containerNode = dom.document.querySelector("#app")

  render(containerNode, app)
}
