package dev.bluepitaya.example

import com.raquo.laminar.api.L._
import org.scalajs.dom
import dev.bluepitaya.laminarcolor.models.Hsv
import dev.bluepitaya.laminarcolor.pickers.ChromePicker
import dev.bluepitaya.laminarcolor.pickers.SimplePicker
import dev.bluepitaya.laminarcolor.pickers.SketchPicker
import com.raquo.laminar.nodes.ReactiveHtmlElement

object Main extends App {
  val color = Var(Hsv(200, 0.75, 0.75, 1))
  val hslValueSignal = color.signal.map(_.toCssHsl)
  val rgbaValueSignal = color.signal.map(_.toCssRgba)

  val app = div(
    div(
      h2("Current color"),
      div(child.text <-- hslValueSignal),
      div(child.text <-- rgbaValueSignal)
    ),
    h2("Color picerks"),
    div(
      display.flex,
      flexDirection.row,
      styleProp("gap")("30px"),
      div(
        h3("Chrome picker"),
        ChromePicker.component(color.signal, color.writer)
      ),
      div(
        h3("Sketch picker"),
        SketchPicker.component(color.signal, color.writer)
      ),
      div(
        h3("Simple picker"),
        SimplePicker.component(color.signal, color.writer)
      )
    )
  )

  val containerNode = dom.document.querySelector("#app")

  render(containerNode, app)
}
