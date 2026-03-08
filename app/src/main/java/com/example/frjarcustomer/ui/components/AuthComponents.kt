package com.example.frjarcustomer.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.animation.core.*
import com.example.frjarcustomer.R

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.draw.alpha
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.frjarcustomer.appstate.resourceString
import com.example.frjarcustomer.ui.theme.AuthBorder
import com.example.frjarcustomer.ui.theme.ButtonPrimaryPressed
import com.example.frjarcustomer.ui.theme.ErrorRedLight
import com.example.frjarcustomer.ui.theme.TextBlackDark
import com.example.frjarcustomer.ui.theme.TextPrimary
import com.example.frjarcustomer.ui.theme.TextGreyscale500
import com.example.frjarcustomer.ui.theme.TextOptional
import com.example.frjarcustomer.ui.theme.White
import com.example.frjarcustomer.ui.util.dpScaled
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp
import kotlin.math.roundToInt

@Composable
fun AuthToggle(
    titles: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(12.sdp)
    val innerShape = RoundedCornerShape(8.sdp)

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dpScaled())
            .clip(shape)
            .background(White)
            .padding(6.dpScaled())
    ) {
        val totalWidth = maxWidth
        val tabCount = titles.size
        val gapTotal = 4.sdp * (tabCount - 1)
        val tabWidth = (totalWidth - gapTotal) / tabCount

        val tabWidthPx = with(LocalDensity.current) { tabWidth.toPx() }
        val gapPx = with(LocalDensity.current) { 4.sdp.toPx() }
        val stepPx = tabWidthPx + gapPx

        val indicatorOffset by animateFloatAsState(
            targetValue = selectedIndex * stepPx,
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            ),
            label = "indicator_offset"
        )

        Box(
            modifier = Modifier
                .width(tabWidth)
                .height(44.dpScaled())
                .offset { IntOffset(x = indicatorOffset.roundToInt(), y = 0) }
                .clip(innerShape)
                .background(TextPrimary)
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(4.sdp)
        ) {
            titles.forEachIndexed { index, title ->

                var isPressed by remember { mutableStateOf(false) }
                val pressScale by animateFloatAsState(
                    targetValue = if (isPressed) 0.93f else 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessHigh
                    ),
                    label = "press_$index"
                )

                val textColor by animateColorAsState(
                    targetValue = if (index == selectedIndex) White else TextPrimary,
                    animationSpec = tween(durationMillis = 150),
                    label = "text_color_$index"
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dpScaled())
                        .graphicsLayer {
                            scaleX = pressScale
                            scaleY = pressScale
                        }
                        .clip(innerShape)
                        .pointerInput(index) {
                            detectTapGestures(
                                onPress = {
                                    isPressed = true
                                    tryAwaitRelease()
                                    isPressed = false
                                },
                                onTap = { onTabSelected(index) }
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    GenericText(
                        text = title,
                        color = textColor,
                        fontSize = 11.ssp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
    }
}

@Composable
fun AuthTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onTrailingIconClick: (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    prefixText: String? = null,
    isOptionalHeader: Boolean = false,
    enabled: Boolean = true,
    fieldIndex: Int? = null,
    validationShake: ValidationShakeState? = null,
) {
    val shape = RoundedCornerShape(8.dp)
    val shakeTrigger = when {
        fieldIndex != null && validationShake != null && fieldIndex in validationShake.invalidIndices -> validationShake.triggerId
        else -> 0
    }
    val shakeOffset = remember { Animatable(0f) }
    LaunchedEffect(shakeTrigger) {
        if (shakeTrigger > 0) {
            shakeOffset.snapTo(0f)
            shakeOffset.animateTo(
                0f,
                animationSpec = keyframes {
                    durationMillis = 400
                    0f at 0
                    -12f at 50
                    12f at 100
                    -12f at 150
                    12f at 200
                    0f at 400
                }
            )
        }
    }
    val borderColor by animateColorAsState(
        targetValue = if (value.isNotEmpty()) TextPrimary else AuthBorder,
        animationSpec = tween(durationMillis = 300),
        label = "borderColor"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer { translationX = shakeOffset.value }
    ) {
        Row(Modifier.wrapContentSize(), verticalAlignment = Alignment.CenterVertically) {
            GenericText(
                text = label,
                color = TextBlackDark,
                fontSize = 10.ssp
            )
            if (isOptionalHeader) {
                Spacer(Modifier.width(2.sdp))
                GenericText(
                    text = resourceString(R.string.optional),
                    color = TextOptional,
                    fontSize = 10.ssp
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.sdp)
                .height(43.sdp)
                .clip(shape)
                .border(1.dp, borderColor, shape)
                .padding(horizontal = 15.sdp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart),
                singleLine = true,
                visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (isPassword) KeyboardType.Password else keyboardType,
                    autoCorrect = false
                ),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = TextPrimary,
                    fontSize = 11.ssp,
                ),
                cursorBrush = SolidColor(TextPrimary),
                decorationBox = { inner ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (leadingIcon != null) {
                            Box(modifier = Modifier.padding(end = 10.sdp)) {
                                leadingIcon()
                            }
                        }
                        if (prefixText != null) {
                            GenericText(
                                text = prefixText,
                                color = TextPrimary,
                                fontSize = 11.ssp,
                                modifier = Modifier.padding(end = 4.sdp)
                            )
                        }

                        Box(modifier = Modifier.weight(1f)) {
                            if (value.isEmpty()) {
                                GenericText(
                                    text = placeholder,
                                    color = TextGreyscale500.copy(0.6f),
                                    fontSize = 10.ssp
                                )
                            }
                            inner()
                        }

                        if (trailingIcon != null && onTrailingIconClick != null) {
                            Box(modifier = Modifier.clickable(onClick = onTrailingIconClick)) {
                                trailingIcon()
                            }
                        }
                    }
                }
            )
        }
    }
}


// ───────────────────────────────────────────
// Validation Rule System
// ───────────────────────────────────────────

data class ValidationRule(
    val errorMessage: String,
    val validate: (String) -> Boolean
)

object ValidationRules {
    fun required(message: String = "This field is required") =
        ValidationRule(message) { it.isNotBlank() }

    fun minLength(min: Int, message: String = "Minimum $min characters required") =
        ValidationRule(message) { it.length >= min }

    fun maxLength(max: Int, message: String = "Maximum $max characters allowed") =
        ValidationRule(message) { it.length <= max }

    fun email(message: String = "Enter a valid email address") =
        ValidationRule(message) { android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() }

    fun phone(message: String = "Enter a valid phone number") =
        ValidationRule(message) { it.matches(Regex("^[+]?[0-9]{10,15}$")) }

    fun noSpecialChars(message: String = "Special characters are not allowed") =
        ValidationRule(message) { it.matches(Regex("^[a-zA-Z0-9 ]*$")) }

    fun passwordStrength(message: String = "Password must be at least 6 characters") =
        ValidationRule(message) { it.length >= 6 }

    fun match(other: String, message: String = "Values do not match") =
        ValidationRule(message) { it == other }

    fun custom(message: String, rule: (String) -> Boolean) =
        ValidationRule(message, rule)
}

data class ValidationResult(
    val firstErrorMessage: String,
    val invalidIndices: List<Int>
)

data class ValidationShakeState(
    val triggerId: Int = 0,
    val invalidIndices: Set<Int> = emptySet()
)

object AuthValidation {
    fun validate(fields: List<Pair<String, List<ValidationRule>>>): ValidationResult? {
        val invalidIndices = mutableListOf<Int>()
        var firstError: String? = null
        fields.forEachIndexed { index, (value, rules) ->
            val error = rules.firstOrNull { !it.validate(value) }?.errorMessage
            if (error != null) {
                invalidIndices.add(index)
                if (firstError == null) firstError = error
            }
        }
        return firstError?.let { ValidationResult(it, invalidIndices) }
    }
}

@Composable
fun OtpDigitsRow(
    otp: String,
    onOtpChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    shakeTrigger: Int = 0
) {
    val shape = RoundedCornerShape(6.sdp)
    val shakeOffset = remember { Animatable(0f) }
    LaunchedEffect(shakeTrigger) {
        if (shakeTrigger > 0) {
            shakeOffset.snapTo(0f)
            shakeOffset.animateTo(
                0f,
                animationSpec = keyframes {
                    durationMillis = 400
                    0f at 0
                    -12f at 50
                    12f at 100
                    -12f at 150
                    12f at 200
                    0f at 400
                }
            )
        }
    }
    Box(modifier = modifier.graphicsLayer { translationX = shakeOffset.value }) {
        BasicTextField(
            value = otp,
            onValueChange = { onOtpChange(it.filter { c -> c.isDigit() }.take(4)) },
            modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = androidx.compose.ui.graphics.Color.Transparent,
            fontSize = 11.ssp,

            ),
        decorationBox = { inner ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(43.sdp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(7.sdp)
                ) {
                    repeat(4) { index ->
                        val char = otp.getOrNull(index)?.toString() ?: ""
                        val isFocused = otp.length == index
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(43.sdp)
                                .clip(shape)
                                .border(
                                    1.dp,
                                    if (isFocused) TextPrimary else AuthBorder,
                                    shape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            GenericText(
                                text = char,
                                color = ButtonPrimaryPressed,
                                fontSize = 11.ssp,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    inner()
                }
            }
        }
        )
    }
}

