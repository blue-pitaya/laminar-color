package xyz.bluepitaya.example

import xyz.bluepitaya.laminarcolor
import com.raquo.laminar.api.L._
import org.scalajs.dom

object Main extends App {
  val app = div(
    laminarcolor.ColorPicker.component
  )
  val containerNode = dom.document.querySelector("#app")

  render(containerNode, app)
}
