package dev.lazygarde.activity.result

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.lazygarde.activity.result.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
                .addCategory(Intent.CATEGORY_OPENABLE)
                .setType("image/*")

            @Suppress("DEPRECATION")
            startActivityForResult(intent, GET_IMAGE_REQUEST_CODE)
        }
    }

    @Deprecated("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GET_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                binding.iv.setImageURI(uri)
            }
        }
    }

    companion object {
        private const val GET_IMAGE_REQUEST_CODE = 100
    }
}