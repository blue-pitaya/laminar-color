package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import org.scalajs.dom
import Util._

object Sliders {
  private def getSliderXPercent(event: dom.PointerEvent, el: dom.Element) = {
    val rect = el.getBoundingClientRect()
    Util.getEventPositionPercent(event, rect).x
  }

  private def baseComponent(
      cssLeftPositionSignal: Signal[String],
      heightInPx: Int,
      background: HtmlElement,
      // TODO: maybe event stream rather than callback?
      sliderChange: Double => Unit
  ) = {
    val dragModule = DragLogic.enableDraggingInDocument()
    val handler = Circles.filledCircle(heightInPx + 2, heightInPx + 2)

    div(
      position.relative,
      height(Util.toPxStr(heightInPx)),
      background,
      handler
        .amend(position.absolute, left <-- cssLeftPositionSignal, top("50%")),
      inContext { el =>
        val docEvents = dragModule.docEvents
        val compEvents = dragModule.getComponentEvents(
          HtmlIdGenerator.ImpureRandomStringIdGenerator.randomId,
          Observer[DragLogic.DragEvent] {
            case DragLogic.DragEnd(e) => ()
            case DragLogic.DragMove(e) =>
              val xPercent = getSliderXPercent(e, el.ref)
              sliderChange(xPercent)
            case DragLogic.DragStart(e, _) =>
              val xPercent = getSliderXPercent(e, el.ref)
              sliderChange(xPercent)
          }
        )
        Seq(docEvents, compEvents)
      }
    )
  }

  private def hueToCssLeftAttr(h: Double) = s"${(h / 360.0) * 100.0}%"

  def hueComponent(color: Var[ColorPicker.Hsv], heightInPx: Int) = {
    val backgroundDiv = div(
      position.absolute,
      width("100%"),
      height("100%"),
      background(
        "linear-gradient(to right, #f00 0%, #ff0 17%, #0f0 33%, #0ff 50%, #00f 67%, #f0f 83%, #f00 100%)"
      )
    )

    def onSliderChange(xPercent: Double) = {
      val hue = xPercent * 360
      color.update(v => v.copy(h = hue))
    }

    val circleLeftPositionSignal = color.signal.map(x => hueToCssLeftAttr(x.h))

    baseComponent(
      circleLeftPositionSignal,
      heightInPx,
      backgroundDiv,
      onSliderChange
    )
  }

  private def alphaToCssLeftAttr(a: Double) = s"${a * 100.0}%"

  def alphaComponent(color: Var[ColorPicker.Hsv], heightInPx: Int) = {
    val backgroundGradientSignal = color
      .signal
      .map(hsv =>
        s"linear-gradient(to right, ${hsv.toCssRgbaTransparent} 0%, ${hsv.toCssRgb} 100%)"
      )

    def onSliderChange(xPercent: Double) = color
      .update(v => v.copy(a = xPercent))

    val circleLeftPositionSignal = color
      .signal
      .map(x => alphaToCssLeftAttr(x.a))

    val backgroundComponent = div(
      position.absolute,
      inset("0"),
      overflow.hidden,
      div(
        position.absolute,
        inset("0"),
        background(s"url('${TransparentImage.data}')")
      ),
      div(
        position.absolute,
        inset("0"),
        background <-- backgroundGradientSignal
      )
    )
    baseComponent(
      circleLeftPositionSignal,
      heightInPx,
      backgroundComponent,
      onSliderChange
    )
  }
}
