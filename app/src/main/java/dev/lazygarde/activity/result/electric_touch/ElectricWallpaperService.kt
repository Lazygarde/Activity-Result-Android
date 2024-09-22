package dev.lazygarde.activity.result.electric_touch

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.os.Handler
import android.os.Looper
import android.service.wallpaper.WallpaperService
import android.view.MotionEvent
import android.view.SurfaceHolder
import dev.lazygarde.activity.result.electric_touch.ElectricTouchUtils.generateLightningPoints
import dev.lazygarde.activity.result.electric_touch.ElectricTouchUtils.vibrateDevice

class ElectricWallpaperService : WallpaperService() {

    override fun onCreateEngine(): Engine {
        return ElectricEngine()
    }

    inner class ElectricEngine : Engine() {
        private val paint = Paint().apply {
            style = Paint.Style.STROKE
            color = Color.WHITE
            strokeWidth = 4f
            isAntiAlias = true
        }

        private val path = Path()
        private var touchPosition: PointF? = null

        private val handler = Handler(Looper.getMainLooper())
        private val drawRunnable = Runnable { drawFrame() }

        private var width: Int = 0
        private var height: Int = 0

        private val cornerPositions: List<PointF>
            get() = listOf(
                PointF(0f, 0f),
                PointF(width.toFloat(), 0f),
                PointF(width.toFloat(), height.toFloat()),
                PointF(0f, height.toFloat())
            )

        private var visible = true
        private var surfaceHolder: SurfaceHolder? = null

        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            this.surfaceHolder = surfaceHolder
        }

        override fun onSurfaceChanged(
            holder: SurfaceHolder?,
            format: Int,
            width: Int,
            height: Int
        ) {
            this.width = width
            this.height = height
            super.onSurfaceChanged(holder, format, width, height)
        }

        override fun onTouchEvent(event: MotionEvent) {
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    touchPosition = PointF(event.x, event.y)
                    vibrateDevice(applicationContext)
                    drawFrame()
                }

                MotionEvent.ACTION_UP -> {
                    touchPosition = null
                    drawFrame()
                }
            }
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            if (visible) {
                drawFrame()
            } else {
                handler.removeCallbacks(drawRunnable)
            }
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
            visible = false
            handler.removeCallbacks(drawRunnable)
        }

        private fun drawFrame() {
            val canvas = surfaceHolder?.lockCanvas() ?: return
            try {
                canvas.drawColor(Color.BLACK)

                touchPosition?.let { position ->
                    cornerPositions.forEach { cornerPosition ->
                        repeat(2) {
                            val lightningPoints = generateLightningPoints(
                                cornerPosition,
                                position,
                                60f
                            )
                            path.reset()
                            path.moveTo(lightningPoints.first().x, lightningPoints.first().y)
                            lightningPoints.drop(1).forEach { point ->
                                path.lineTo(point.x, point.y)
                            }

                            canvas.drawPath(path, paint)
                        }
                    }
                }
            } finally {
                surfaceHolder?.unlockCanvasAndPost(canvas)
            }

            if (visible) {
                handler.removeCallbacks(drawRunnable)
                handler.postDelayed(drawRunnable, 16)
            }
        }
    }
}