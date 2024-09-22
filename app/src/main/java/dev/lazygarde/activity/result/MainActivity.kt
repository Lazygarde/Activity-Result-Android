package dev.lazygarde.activity.result

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.lazygarde.activity.result.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val contractHandler = ImageContractHandler(activityResultRegistry)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(contractHandler)

        binding.btnSelectImage.setOnClickListener {
            contractHandler.selectImage { uri ->
                binding.iv.setImageURI(uri)
            }
        }
    }
}