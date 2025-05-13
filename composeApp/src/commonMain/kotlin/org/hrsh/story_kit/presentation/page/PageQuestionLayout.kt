package org.hrsh.story_kit.presentation.page

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Data class that defines layout of the elements on the question page.
 *
 * @property questionAlignment The alignment of the question text.
 * @property answerAlignment The alignment of the answer block.
 * @property buttonPaddingStart The padding start of the buttons in the answer block.
 * @property buttonPaddingTop The padding top of the buttons in the answer block.
 * @property buttonPaddingEnd The padding end of the buttons in the answer block.
 * @property buttonPaddingBottom The padding bottom of the buttons in the answer block.
 * @property buttonsInRow The number of buttons per line in the answer block.
 * @property fixedButtonSize A flag indicating whether buttons in the same row have the same width..
 */
@Serializable
data class PageQuestionLayout(
    val questionAlignment: SerializableAlignment = SerializableAlignment.TOP_CENTER,
    val answerAlignment: SerializableAlignment = SerializableAlignment.BOTTOM_CENTER,
    val buttonPaddingStart: Int = 5,
    val buttonPaddingTop: Int = 5,
    val buttonPaddingEnd: Int = 5,
    val buttonPaddingBottom: Int = 5,
    val buttonsInRow: Int = 1,
    val fixedButtonSize: Boolean = true
)

@Serializable
enum class SerializableAlignment {
    TOP_START, TOP_CENTER, TOP_END,
    CENTER_START, CENTER, CENTER_END,
    BOTTOM_START, BOTTOM_CENTER, BOTTOM_END
}

internal fun SerializableAlignment.toAlignment(): Alignment {
    return when (this) {
        SerializableAlignment.TOP_START -> Alignment.TopStart
        SerializableAlignment.TOP_CENTER -> Alignment.TopCenter
        SerializableAlignment.TOP_END -> Alignment.TopEnd
        SerializableAlignment.CENTER_START -> Alignment.CenterStart
        SerializableAlignment.CENTER -> Alignment.Center
        SerializableAlignment.CENTER_END -> Alignment.CenterEnd
        SerializableAlignment.BOTTOM_START -> Alignment.BottomStart
        SerializableAlignment.BOTTOM_CENTER -> Alignment.BottomCenter
        SerializableAlignment.BOTTOM_END -> Alignment.BottomEnd
    }
}