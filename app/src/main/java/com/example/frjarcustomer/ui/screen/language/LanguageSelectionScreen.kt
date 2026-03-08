package com.example.frjarcustomer.ui.screen.language

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import com.example.frjarcustomer.R
import com.example.frjarcustomer.appstate.resourceString
import com.example.frjarcustomer.image.CoilImage
import com.example.frjarcustomer.ui.components.GenericButton
import com.example.frjarcustomer.ui.components.GenericText
import com.example.frjarcustomer.ui.theme.AuthBorderNew
import com.example.frjarcustomer.ui.theme.ButtonPrimary
import com.example.frjarcustomer.ui.theme.TextBlackDark
import com.example.frjarcustomer.ui.theme.TextBlackDarkTitle
import com.example.frjarcustomer.ui.theme.TextOnLightGray
import com.example.frjarcustomer.ui.theme.TextSecondary
import com.example.frjarcustomer.ui.theme.White
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun LanguageSelectionScreen(
    viewModel: LanguageSelectionViewModel,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val selectedLanguage by viewModel.selectedLanguage.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = White,
        topBar = {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.sdp, horizontal = 16.sdp)
                ) {
                    CoilImage(
                        url = R.drawable.ic_close_back,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.sdp)
                            .align(Alignment.CenterStart)
                            .clickable { onBack() },
                        mirrorInRtl = true
                    )
                    GenericText(
                        text = resourceString(R.string.language),
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextBlackDarkTitle,
                        fontSize = 18.ssp
                    )
                }
                Divider(color = AuthBorderNew, thickness = 1.dp)
            }
        },
        bottomBar = {
            GenericButton(
                onClick = {
                    scope.launch {
                        viewModel.save()
                        onBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.sdp)
                    .height(48.sdp),
                backgroundColor = ButtonPrimary,
                shape = RoundedCornerShape(10.sdp)
            ) {
                GenericText(
                    text = resourceString(R.string.save),
                    color = TextBlackDark,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.ssp
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.sdp)
        ) {
            item {
                SectionHeader(resourceString(R.string.suggested))
            }
            items(viewModel.suggestedList) { item ->
                LanguageItem(
                    name = if (item.language == com.example.frjarcustomer.appstate.AppLanguage.DEFAULT) resourceString(R.string.english_us) else item.language.languageName,
                    flagIcon = item.flagResId,
                    isSelected = selectedLanguage == item.language,
                    enabled = item.enabled,
                    onClick = { if (item.enabled) viewModel.setSelected(item.language) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(20.sdp))
                SectionHeader(resourceString(R.string.other_languages))
            }
            items(viewModel.otherList) { item ->
                LanguageItem(
                    name = item.language.languageName,
                    flagIcon = item.flagResId,
                    isSelected = selectedLanguage == item.language,
                    enabled = item.enabled,
                    onClick = { if (item.enabled) viewModel.setSelected(item.language) }
                )
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    GenericText(
        text = title,
        modifier = Modifier.padding(vertical = 12.sdp),
        color = TextOnLightGray,
        fontSize = 14.ssp,
        fontWeight = FontWeight.Medium
    )
}

@Composable
fun LanguageItem(
    name: String,
    flagIcon: Int,
    isSelected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (enabled) 1f else 0.5f)
            .then(
                if (enabled) Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick
                ) else Modifier
            )
            .padding(vertical = 12.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = flagIcon),
            contentDescription = null,
            modifier = Modifier.size(24.sdp)
        )
        Spacer(modifier = Modifier.width(12.sdp))
        GenericText(
            text = name,
            modifier = Modifier.weight(1f),
            color = if (isSelected) TextBlackDark else TextSecondary,
            fontSize = 16.ssp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
        Image(
            painter = painterResource(id = if (isSelected) R.drawable.ic_radio_selected else R.drawable.ic_radio_unselected),
            contentDescription = null,
            modifier = Modifier.size(20.sdp)
        )
    }
}
