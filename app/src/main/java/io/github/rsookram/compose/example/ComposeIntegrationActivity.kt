package io.github.rsookram.compose.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import dev.chrisbanes.accompanist.glide.GlideImage

class ComposeIntegrationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm = ViewModelProvider(this).get<MainViewModel>()

        setContent {
            MaterialTheme {
                val isImageVisible = vm.isImageVisible.observeAsState(initial = false)
                Main(isImageVisible.value, vm::onButtonClick)
            }
        }
    }
}

@Composable
fun Main(isImageVisible: Boolean, onButtonClick: () -> Unit) {
    Column {
        Button(onClick = onButtonClick) {
            Text(if (isImageVisible) "Hide image" else "Show image")
        }

        if (isImageVisible) {
            GlideImage("https://developer.android.com/images/jetpack/compose-tutorial/lesson2.gif")
        }
    }
}

class MainViewModel : ViewModel() {

    private val _isImageVisible = MutableLiveData(false)
    val isImageVisible: LiveData<Boolean> = _isImageVisible

    fun onButtonClick() {
        val current = _isImageVisible.value ?: false
        _isImageVisible.value = !current
    }
}
