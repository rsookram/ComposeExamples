package io.github.rsookram.compose.example

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ConfigurationAmbient
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview
import io.github.rsookram.compose.example.ui.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("MainActivity", "onCreate called for activity $this")

        val examples: List<@Composable () -> Unit> = listOf(
            { FontScale() },
            { Locale() },
        )

        val currentIndex = mutableStateOf(0)

        setContent {
            AppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val onNextClick: (() -> Unit)? = if (currentIndex.value < examples.lastIndex) {
                        { currentIndex.value++ }
                    } else {
                        null
                    }
                    val onPreviousClick: (() -> Unit)? = if (currentIndex.value > 0) {
                        { currentIndex.value-- }
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
}

@Composable
fun FontScale() {
    Text("Configuration Changes in Compose")
}

@Preview
@Composable
fun FontScalePreview() = AppTheme {
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

@Preview
@Composable
fun LocalePreview() = AppTheme {
    Locale()
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

        Row {
            if (onPreviousClick != null) {
                Button(onClick = onPreviousClick) { Text("Previous") }
            } else {
                Spacer(Modifier.weight(1f))
            }

            Text("$currentIndex / $totalCount")

            if (onNextClick != null) {
                Button(onClick = onNextClick) { Text("Next") }
            } else {
                Spacer(Modifier.weight(1f))
            }
        }
    }
}
