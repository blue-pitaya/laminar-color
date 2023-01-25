package xyz.bluepitaya.laminarcolor

import com.raquo.laminar.api.L._
import org.scalajs.dom

trait State[A] {
  def signal(a: A): Signal[Hsv]
  def updateHue(a: A, hue: Double): Unit
  def updateAlpha(a: A, alpha: Double): Unit
  def updateSaturationAndBrigthness(a: A, s: Double, v: Double): Unit
  def now(a: A): Hsv
  def set(a: A, v: Hsv): Unit
}
