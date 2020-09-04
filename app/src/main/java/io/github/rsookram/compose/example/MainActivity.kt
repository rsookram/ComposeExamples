package io.github.rsookram.compose.example

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.foundation.Box
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.contentColor
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ConfigurationAmbient
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import io.github.rsookram.compose.example.ui.AppTheme

class MainActivity : ComponentActivity() {

    private val examples: List<@Composable () -> Unit> = listOf(
        { FontScale() },
        { Locale() },
        { UiMode() },
        { ScreenSize() },
        { Orientation() },
    )

    private val currentIndex = mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("MainActivity", "onCreate called for activity $this")

        setContent {
            AppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val onNextClick: (() -> Unit)? = if (hasNext()) {
                        { goToNext() }
                    } else {
                        null
                    }
                    val onPreviousClick: (() -> Unit)? = if (hasPrevious()) {
                        { goToPrevious() }
                    } else {
                        null
                    }

                    ExampleContainer(
                        currentIndex = currentIndex.value + 1,
                        totalCount = examples.size,
                        onNextClick = onNextClick,
                        onPreviousClick = onPreviousClick,
                    ) {
                        examples[currentIndex.value]()
                    }
                }
            }
        }
    }

    private fun hasNext() = currentIndex.value < examples.lastIndex

    private fun goToNext() {
        currentIndex.value++
    }

    private fun hasPrevious() = currentIndex.value > 0

    private fun goToPrevious() {
        currentIndex.value--
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean =
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                if (hasPrevious()) goToPrevious()
                true
            }
            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                if (hasNext()) goToNext()
                true
            }
            else -> super.onKeyUp(keyCode, event)
        }
}

@Composable
fun FontScale() {
    Text("Configuration Changes in Compose")
}

@Preview(group = "fontScale")
@Preview(group = "fontScale", fontScale = 2.0f)
@Composable
private fun FontScalePreview() = AppTheme {
    FontScale()
}

@Composable
fun Locale() {
    Text(localizedString(R.string.android))
}

/**
 * Replacement for [androidx.compose.ui.res.stringResource] which handles
 * recomposing on [Configuration] changes (`stringResource` doesn't as of
 * 1.0.0-alpha01).
 *
 * https://issuetracker.google.com/issues/167352819
 */
@Composable
fun localizedString(@StringRes id: Int): String {
    val context = ContextAmbient.current
    val resources = remember(ConfigurationAmbient.current) { context.resources }
    return resources.getString(id)
}

// Setting locale is broken in 1.0.0-alpha02
@Preview(group = "locale")
@Preview(group = "locale", locale = "ja-JP")
@Composable
private fun LocalePreview() = AppTheme {
    Locale()
}

@Composable
fun UiMode() {
    val text = if (isSystemInDarkTheme()) "Dark" else "Light"

    // Increasing the elevation so that the colour difference is more
    // noticeable in dark mode
    Card(Modifier.padding(16.dp), elevation = 4.dp) {
        Text(text, Modifier.padding(16.dp))
    }
}

@Preview(group = "uiMode")
@Composable
private fun UiModeLightPreview() = AppTheme {
    UiMode()
}

@Preview(group = "uiMode")
@Composable
private fun UiModeDarkPreview() {
    // Hack. Ideally the uiMode parameter in @Preview should handle this
    ConfigurationAmbient.current.uiMode = Configuration.UI_MODE_NIGHT_YES

    AppTheme {
        UiMode()
    }
}

@Composable
fun ScreenSize() {
    WithConstraints {
        if (maxHeight > 400.dp) {
            Column {
                repeat(4) {
                    Text("Big $maxHeight")
                }
            }
        } else {
            Text("Small $maxHeight")
        }
    }
}

@Preview(group = "screenSize")
@Preview(group = "screenSize", heightDp = 410)
@Composable
private fun ScreenSizePreview() = AppTheme {
    ScreenSize()
}

@Composable
fun Orientation() {
    val isPortrait = ConfigurationAmbient.current.orientation == Configuration.ORIENTATION_PORTRAIT

    if (isPortrait) {
        Column {
            Card(Modifier.padding(16.dp)) {
                Text("portrait", Modifier.padding(16.dp))
            }

            Card(Modifier.padding(16.dp)) {
                Text("portrait", Modifier.padding(16.dp))
            }
        }
    } else {
        Row {
            Card(Modifier.padding(16.dp)) {
                Text("landscape", Modifier.padding(16.dp))
            }

            Card(Modifier.padding(16.dp)) {
                Text("landscape", Modifier.padding(16.dp))
            }
        }
    }
}

@Preview(group = "orientation")
@Composable
private fun OrientationPortraitPreview() = AppTheme {
    Orientation()
}

@Preview(group = "orientation")
@Composable
private fun OrientationLandscapePreview() = AppTheme {
    ConfigurationAmbient.current.orientation = Configuration.ORIENTATION_LANDSCAPE

    Orientation()
}

@Composable
fun ExampleContainer(
    currentIndex: Int,
    totalCount: Int,
    onNextClick: (() -> Unit)?,
    onPreviousClick: (() -> Unit)?,
    child: @Composable () -> Unit,
) {
    Column {
        Box(Modifier.weight(1f), children = child)

        Row(
            Modifier.padding(16.dp),
            Arrangement.SpaceBetween,
            verticalGravity = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onPreviousClick ?: {}, enabled = onPreviousClick != null) {
                Icon(
                    Icons.Default.ArrowBack,
                    tint = if (onPreviousClick != null) contentColor() else Color.Transparent,
                )
            }

            Text(
                "$currentIndex / $totalCount",
                Modifier.weight(1f),
                textAlign = TextAlign.Center,
            )

            IconButton(onClick = onNextClick ?: {}, enabled = onNextClick != null) {
                Icon(
                    Icons.Default.ArrowForward,
                    tint = if (onNextClick != null) contentColor() else Color.Transparent,
                )
            }
        }
    }
}

@Preview(group = "exampleContainer", widthDp = 360, heightDp = 128)
@Composable
private fun ExampleContainerPreview() = AppTheme {
    ExampleContainer(
        currentIndex = 2,
        totalCount = 5,
        onNextClick = {},
        onPreviousClick = {},
    ) {}
}

@Preview(group = "exampleContainer", widthDp = 360, heightDp = 128)
@Composable
private fun ExampleContainerFirstPagePreview() = AppTheme {
    ExampleContainer(
        currentIndex = 1,
        totalCount = 5,
        onNextClick = {},
        onPreviousClick = null,
    ) {}
}

@Preview(group = "exampleContainer", widthDp = 360, heightDp = 128)
@Composable
private fun ExampleContainerLastPagePreview() = AppTheme {
    ExampleContainer(
        currentIndex = 5,
        totalCount = 5,
        onNextClick = null,
        onPreviousClick = {},
    ) {}
}
