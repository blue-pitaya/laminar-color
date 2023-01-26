package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._

import Util.toPxStr

object Circles {
  private def circleBase(widthInPx: Int, heightInPx: Int) = div(
    width(toPxStr(widthInPx)),
    height(toPxStr(heightInPx)),
    borderRadius("50%"),
    transform(
      s"translate(${toPxStr(-widthInPx / 2)}, ${toPxStr(-heightInPx / 2)})"
    )
  )

  def rectangle(widthInPx: Int, heightInPx: Int) = div(
    width(toPxStr(widthInPx)),
    height(toPxStr(heightInPx)),
    borderRadius("1px"),
    backgroundColor("#fff"),
    boxShadow("rgb(0 0 0 / 60%) 0px 0px 2px"),
    transform(
      s"translate(${toPxStr(-widthInPx / 2)}, ${toPxStr(-heightInPx / 2)})"
    )
  )

  def blankCircle(widthInPx: Int, heightInPx: Int) =
    circleBase(widthInPx, heightInPx)
      .amend(boxShadow("rgb(255 255 255) 0px 0px 0px 1px inset"))

  def blankCircleSpecialShadow(widthInPx: Int, heightInPx: Int) =
    circleBase(widthInPx, heightInPx).amend(
      boxShadow(
        "rgb(255 255 255) 0px 0px 0px 1.5px, rgb(0 0 0 / 30%) 0px 0px 1px 1px inset, rgb(0 0 0 / 40%) 0px 0px 1px 2px"
      )
    )

  def filledCircle(widthInPx: Int, heightInPx: Int) =
    circleBase(widthInPx, heightInPx).amend(
      backgroundColor("rgb(248, 248, 248)"),
      boxShadow("rgb(0 0 0 / 37%) 0px 1px 4px 0px")
    )
}
