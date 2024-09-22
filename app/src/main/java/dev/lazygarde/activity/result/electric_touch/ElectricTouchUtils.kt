package dev.lazygarde.activity.result.electric_touch

import android.content.Context
import android.graphics.PointF
import android.os.VibrationEffect
import android.os.Vibrator
import kotlin.random.Random

object ElectricTouchUtils {

    fun generateLightningPoints(start: PointF, end: PointF, displacement: Float): List<PointF> {
        val points = mutableListOf<PointF>()

        if (displacement < 1) {
            points.add(start)
            points.add(end)
        } else {
            val mid = (start + end) / 2f
            val randomOffset = PointF(
                Random.nextFloat() * displacement * 4 - displacement * 2,
                Random.nextFloat() * displacement * 4 - displacement * 2
            )
            val midWithOffset = mid + randomOffset

            points.addAll(generateLightningPoints(start, midWithOffset, displacement / 2))
            points.addAll(generateLightningPoints(midWithOffset, end, displacement / 2))
        }

        return points
    }

    @Suppress("DEPRECATION")
    fun vibrateDevice(context: Context, duration: Long = 100L) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        duration,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(duration)
            }
        }
    }


    private operator fun PointF.plus(other: PointF) = PointF(x + other.x, y + other.y)

    private operator fun PointF.div(other: Float) = PointF(x / other, y / other)

}