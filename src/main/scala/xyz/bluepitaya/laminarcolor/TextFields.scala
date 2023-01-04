package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import org.scalajs.dom

object TextFields {
  def component(color: Var[ColorPicker.Hsv]) = {
    val colorValue = Var(color.now().toHashedHexValue)

    input(
      controlled(value <-- colorValue, onInput.mapToValue --> colorValue),
      // FIXME: change color if value is ok on input
      onBlur.mapToValue.map(v => ColorPicker.Hsv.fromHashedHexValue(v)) -->
        Observer[Option[ColorPicker.Hsv]] { v =>
          v match {
            case None => colorValue.set(color.now().toHashedHexValue)
            case Some(value) =>
              color.set(value)
              colorValue.set(color.now().toHashedHexValue)
          }
        },
      color.signal.map(_.toHashedHexValue) --> colorValue.writer
    )
  }
}
