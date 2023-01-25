package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import org.scalajs.dom

object Palette {
  def defaultColorButton[A](s: A, buttonColor: Hsv)(implicit state: State[A]) =
    ColorField
      .staticColorComponent(buttonColor)
      .amend(
        width("16px"),
        height("16px"),
        cursor.pointer,
        outline("none"),
        borderRadius("3px"),
        boxShadow("rgb(0 0 0 / 15%) 0px 0px 0px 1px inset"),
        onClick.map(_ => buttonColor) -->
          Observer[Hsv] { x =>
            state.set(s, x)
          }
      )

  def component(
      gridWidth: Int,
      gridHeight: Int,
      colors: Seq[Hsv],
      getElement: Hsv => HtmlElement
  ) = {
    val colorButtons = colors.map(c => getElement(c))

    div(display.flex, flexWrap.wrap, colorButtons)
  }
}
