package io.github.rsookram.compose.example

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.AndroidView

class ViewFromComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column {
                AndroidView(::TextView) { v ->
                    v.text = "TextView from Compose"
                }
            }
        }
    }
}
