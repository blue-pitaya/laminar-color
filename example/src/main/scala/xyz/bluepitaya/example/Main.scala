package xyz.bluepitaya.example

import com.raquo.laminar.api.L._
import org.scalajs.dom
import xyz.bluepitaya.laminarcolor.pickers.ChromePicker
import xyz.bluepitaya.laminarcolor.pickers.SketchPicker
import xyz.bluepitaya.laminarcolor.pickers.SimplePicker
import xyz.bluepitaya.laminarcolor.Hsv
import xyz.bluepitaya.laminarcolor.State

object Main extends App {
  val hsvColor = Var(Hsv(200, 0.75, 0.75, 1))

  implicit val hsvVarState = new State[Var[Hsv]] {

    override def signal(a: Var[Hsv]): Signal[Hsv] = a.signal

    override def updateHue(a: Var[Hsv], hue: Double): Unit = a
      .update(s => s.copy(h = hue))

    override def updateAlpha(a: Var[Hsv], alpha: Double): Unit = a
      .update(s => s.copy(a = alpha))

    override def updateSaturationAndBrigthness(
        a: Var[Hsv],
        s: Double,
        v: Double
    ): Unit = a.update(x => x.copy(s = s, v = v))

    override def now(a: Var[Hsv]): Hsv = a.now()

    override def set(a: Var[Hsv], v: Hsv): Unit = a.set(v)

  }

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
