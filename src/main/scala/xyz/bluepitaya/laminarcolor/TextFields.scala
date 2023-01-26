package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import xyz.bluepitaya.common.Hsv

object TextFields {
  // FIXME: enable alpha on hex value
  def hexField[A](s: A)(implicit state: State[A]) = {
    val colorValue = Var(state.now(s).toHashedHexValue)

    input(
      controlled(value <-- colorValue, onInput.mapToValue --> colorValue),
      // FIXME: change color if value is ok on input
      onBlur.mapToValue.map(v => Hsv.fromHashedHexValue(v)) -->
        Observer[Option[Hsv]] { v =>
          v match {
            case None => colorValue.set(state.now(s).toHashedHexValue)
            case Some(value) =>
              state.set(s, value)
              colorValue.set(state.now(s).toHashedHexValue)
          }
        },
      state.signal(s).map(_.toHashedHexValue) --> colorValue.writer
    )
  }
}
