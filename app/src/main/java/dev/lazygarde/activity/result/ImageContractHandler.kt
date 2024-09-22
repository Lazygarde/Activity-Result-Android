package dev.lazygarde.activity.result

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner


class ImageContractHandler(private val registry: ActivityResultRegistry) :
    DefaultLifecycleObserver {

    private var getContent: ActivityResultLauncher<String>? = null
    private var onResult: (Uri?) -> Unit = {}

    override fun onCreate(owner: LifecycleOwner) {
        getContent =
            registry.register(REGISTRY_KEY, owner, ActivityResultContracts.GetContent()) { uri ->
                onResult(uri)
            }
    }

    fun selectImage(onResult: (Uri?) -> Unit) {
        this.onResult = onResult
        getContent?.launch("image/*")
    }

    companion object {
        private const val REGISTRY_KEY = "Image Picker"
    }
}