package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import xyz.bluepitaya.common.Hsv

object TextFields {
  // FIXME: enable alpha on hex value
  def hexField(colorSignal: Signal[Hsv], onColorChanged: Observer[Hsv]) = {
    val colorValue = Var("")
    val bus: EventBus[Hsv] = new EventBus

    input(
      controlled(value <-- colorValue, onInput.mapToValue --> colorValue),
      onBlur
        .mapToValue
        .map(v => Hsv.fromHashedHexValue(v))
        .collect { case Some(color) =>
          color
        } --> onColorChanged,
      colorSignal.map(_.toHashedHexValue) --> colorValue
    )
  }
}
