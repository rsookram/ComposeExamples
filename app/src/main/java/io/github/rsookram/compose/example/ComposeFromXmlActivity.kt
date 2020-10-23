package io.github.rsookram.compose.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Text
import io.github.rsookram.compose.example.databinding.ActivityComposeFromXmlBinding

class ComposeFromXmlActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityComposeFromXmlBinding.inflate(layoutInflater)

        binding.composeText.setContent {
            Text("Text in Compose")
        }

        setContentView(binding.root)
    }
}
