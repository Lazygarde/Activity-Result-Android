package dev.lazygarde.activity.result

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity

class SetLiveWallpaperContract: ActivityResultContract<String, Boolean>()  {
    override fun createIntent(context: Context, input: String): Intent {
        val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
        intent.putExtra(
            WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
            ComponentName(BuildConfig.APPLICATION_ID, input)
        )
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return resultCode == AppCompatActivity.RESULT_OK
    }

}