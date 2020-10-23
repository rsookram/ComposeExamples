package io.github.rsookram.compose.example

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import io.github.rsookram.compose.example.databinding.ViewBindingExampleBinding

class ViewFromComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Column {
                    AndroidView(::TextView) { v ->
                        v.text = "TextView from Compose"
                    }

                    AndroidViewBinding(ViewBindingExampleBinding::inflate) {
                        startText.setOnClickListener {
                            Log.d("ViewFromComposeActivity", "start text clicked")
                        }
                    }
                }
            }
        }
    }
}
