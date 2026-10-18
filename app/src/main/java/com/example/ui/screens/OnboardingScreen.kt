package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.data.EduDxRepository
import com.example.model.ScreenRoute
import com.example.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OnboardingScreen(
    onGetStarted: () -> Unit = { EduDxRepository.navigateTo(ScreenRoute.AUTH) },
    onSkip: () -> Unit = { EduDxRepository.navigateTo(ScreenRoute.HOME) },
    modifier: Modifier = Modifier
) {
    val selectedLanguage by EduDxRepository.selectedLanguage.collectAsState()
    var isPreviewPlaying by remember { mutableStateOf(false) }

    val languages = listOf(
        Pair("English", ""),
        Pair("हिंदी", "(Hindi)"),
        Pair("Hinglish", "मिक्स"),
        Pair("தமிழ்", "(Tamil)"),
        Pair("తెలుగు", "(Telugu)"),
        Pair("मराठी", "(Marathi)"),
        Pair("বাংলা", "(Bengali)")
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // 1. Stepper & Skip Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(DarkSurfaceContainer)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Box(modifier = Modifier.width(22.dp).height(4.dp).clip(RoundedCornerShape(50)).background(OrangePrimaryContainer))
                    Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(DarkSurfaceBright))
                    Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(DarkSurfaceBright))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "STEP 1 OF 3", color = DarkOnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }

                Text(
                    text = "Skip",
                    color = OrangePrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable(onClick = onSkip)
                        .padding(8.dp)
                        .testTag("onboarding_skip_btn")
                )
            }
        }

        // 2. Branding & Hero Headline
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(listOf(OrangePrimaryContainer, CyanSecondaryContainer))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.GraphicEq, contentDescription = null, tint = Color.White, modifier = Modifier.size(26.dp))
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "EDUDX AUDIO LEARNING", color = CyanSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Text(
                    text = "Listen Smarter.\nLearn Better.",
                    color = DarkOnSurface,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 34.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = "Transform wasted transit and routine hours into masterclasses with elite educators.",
                    color = DarkOnSurfaceVariant,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 6.dp, start = 16.dp, end = 16.dp)
                )
            }
        }

        // 3. Transit Student Audio Hero Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .shadow(12.dp, RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = "https://lh3.googleusercontent.com/aida-public/AB6AXuC1YeUDs-wPtdpIxnIkxlN36SaHdQ4x_foGf31g2CQ3cyxGvKnC_4omXhuXnduuZBlkDHWiz6KN8qD_G_iOnDX5Lag4Dm-MJUEK8qFo4CGcnSSRFGXnfhT-b7ghASOlF-rWvguJ4QgHZnJZqBVQXrns-vGWsnOLRmXbag6q5CMWzJdtFQWk22vfZqkGT-dxjisIcdJfdCzIMyMA9W_KtUOJr6H4BvoEXKyM1SPQ0BzaudnvCfa-ArLZUw"),
                    contentDescription = "Student learning with headphones",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Scrim
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(listOf(Color.Transparent, DarkBackground.copy(alpha = 0.9f)))
                        )
                )

                // Bottom Simulated Audio Waveform Bar
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = DarkSurfaceContainer.copy(alpha = 0.85f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(imageVector = Icons.Default.Headphones, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(20.dp))
                            Column {
                                Text(text = "Physics: Quantum Entanglement", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text(text = "Prof. Vikram Rao • 14 min", color = CyanSecondary, fontSize = 10.sp)
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier.height(16.dp)
                        ) {
                            listOf(6.dp, 14.dp, 10.dp, 16.dp, 4.dp).forEach { h ->
                                Box(modifier = Modifier.width(3.dp).height(h).clip(RoundedCornerShape(50)).background(OrangePrimaryContainer))
                            }
                        }
                    }
                }
            }
        }

        // 4. Value Propositions
        item {
            Column(
                modifier = Modifier.padding(vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ValuePropCard(
                    icon = Icons.Default.DirectionsSubway,
                    iconColor = OrangePrimaryContainer,
                    badge = "High-Yield",
                    title = "Learn Anywhere",
                    description = "Curated master audio tracks designed for zero-friction listening during commutes, gym sets, or routine chores."
                )
                ValuePropCard(
                    icon = Icons.Default.Timer10,
                    iconColor = CyanSecondary,
                    badge = null,
                    title = "Bite-sized & Comprehensive",
                    description = "10 to 20 minute concept episodes that defeat screen fatigue while sustaining deep academic retention."
                )
                ValuePropCard(
                    icon = Icons.Default.RecordVoiceOver,
                    iconColor = PeriwinkleTertiary,
                    badge = "Live AI",
                    title = "AI Audio Companion",
                    description = "Interrupt anytime to resolve doubts via speech, extract formula sheets, and review instant chapter recaps."
                )
            }
        }

        // 5. Preferred Audio Language
        item {
            Column(modifier = Modifier.padding(vertical = 10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(imageVector = Icons.Default.Translate, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(18.dp))
                        Text(text = "Preferred Audio Language", color = DarkOnSurface, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(text = "Switch anytime", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                }
                Text(
                    text = "Audio tracks and AI Tutor explanations will tune automatically to your choice.",
                    color = DarkOnSurfaceVariant,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
                )

                // Language Pills
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    languages.forEach { (lang, hint) ->
                        val isSelected = lang == selectedLanguage
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = if (isSelected) OrangePrimaryContainer else DarkSurfaceContainer,
                            modifier = Modifier
                                .clickable { EduDxRepository.setLanguage(lang) }
                                .testTag("lang_pill_$lang")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = lang,
                                    color = if (isSelected) Color.White else DarkOnSurface,
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                                if (hint.isNotEmpty()) {
                                    Text(
                                        text = hint,
                                        color = if (isSelected) Color.White.copy(alpha = 0.8f) else DarkOnSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                }
                                if (isSelected) {
                                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        // 6. Audio Sample Preview Card
        item {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = DarkSurfaceContainerLow,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(CyanSecondaryContainer)
                                .clickable {
                                    isPreviewPlaying = !isPreviewPlaying
                                    EduDxRepository.showToast(if (isPreviewPlaying) "Playing $selectedLanguage audio sample..." else "Sample paused")
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isPreviewPlaying) Icons.Default.Pause else Icons.Default.VolumeUp,
                                contentDescription = "Play preview",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Column {
                            Text(text = "Hear Sample Intro", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(text = "EduDX Voice in $selectedLanguage", color = CyanSecondary, fontSize = 11.sp)
                        }
                    }

                    Text(text = "0:12", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                }
            }
        }

        // 7. Bottom Get Started CTA & Sign In
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onGetStarted,
                    colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("onboarding_get_started_btn")
                ) {
                    Text("Get Started", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Already have an account?", color = DarkOnSurfaceVariant, fontSize = 13.sp)
                    Text(
                        text = "Sign In",
                        color = OrangePrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable(onClick = onGetStarted)
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ValuePropCard(
    icon: ImageVector,
    iconColor: Color,
    badge: String?,
    title: String,
    description: String
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = DarkSurfaceContainer,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(DarkSurfaceContainerHighest),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = title, color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    if (badge != null) {
                        Text(
                            text = badge,
                            color = CyanSecondary,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(CyanSecondary.copy(alpha = 0.2f))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        )
                    }
                }
                Text(
                    text = description,
                    color = DarkOnSurfaceVariant,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}
