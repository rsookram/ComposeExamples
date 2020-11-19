package io.github.rsookram.compose.example

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.activity.ComponentActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView
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

class CompoundView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    @Composable
    override fun Content() {
        Text("Compose text in a View")
    }
}
