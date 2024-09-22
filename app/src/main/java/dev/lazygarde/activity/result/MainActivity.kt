package dev.lazygarde.activity.result

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.lazygarde.activity.result.databinding.ActivityMainBinding
import dev.lazygarde.activity.result.electric_touch.ElectricWallpaperService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val wallpaperContractHandler = WallpaperContractHandler(activityResultRegistry)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(wallpaperContractHandler)

        binding.btnSetLiveWallpaper.setOnClickListener {
            wallpaperContractHandler.setLiveWallpaper(ElectricWallpaperService::class.java.name) { isSetWallpaper ->
                Toast.makeText(
                    this,
                    "Set wallpaper ${if (isSetWallpaper) "successfully" else "failed"}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}