package dev.lazygarde.activity.result

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class WallpaperContractHandler(
    private val registry: ActivityResultRegistry
) : DefaultLifecycleObserver {

    private var launcher: ActivityResultLauncher<String>? = null
    private var onResult: (Boolean) -> Unit = {}

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        launcher =
            registry.register(REGISTRY_KEY, owner, SetLiveWallpaperContract()) { result ->
                onResult(result)
            }
    }

    fun setLiveWallpaper(input: String, onResult: (Boolean) -> Unit) {
        this.onResult = onResult
        launcher?.launch(input)
    }

    companion object {
        private const val REGISTRY_KEY = "Set Live Wallpaper"
    }


}