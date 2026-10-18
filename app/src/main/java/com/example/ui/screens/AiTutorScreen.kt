package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.data.EduDxRepository
import com.example.model.ScreenRoute
import com.example.ui.theme.*

@Composable
fun AiTutorScreen(
    modifier: Modifier = Modifier
) {
    val activePersona by EduDxRepository.aiTutorPersona.collectAsState()
    val activeMode by EduDxRepository.aiTutorMode.collectAsState()
    val isListening by EduDxRepository.isMicListening.collectAsState()
    val isExplanationPlaying by EduDxRepository.isAiExplanationPlaying.collectAsState()
    val aiSpeed by EduDxRepository.aiPlaybackSpeed.collectAsState()
    var formulaChatText by remember { mutableStateOf("") }

    // Pulsing animation for Orb
    val infiniteTransition = rememberInfiniteTransition(label = "orbPulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutQuad),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentPadding = PaddingValues(bottom = 110.dp)
    ) {
        // 1. Status Bar & Active Context Bar
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(CyanSecondary)
                        )
                        Text(
                            text = "AI Study Companion • Online",
                            color = CyanSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(DarkSurfaceContainerHigh)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = "Fast Synth v2", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Active Context Chip
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = DarkSurfaceContainerLow,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(DarkSurfaceContainerHighest),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(18.dp))
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "ACTIVE CONTEXT", color = DarkOnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Text(
                                    text = "Physics • Electromagnetism & Induction",
                                    color = DarkOnSurface,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        Button(
                            onClick = { EduDxRepository.navigateTo(ScreenRoute.CATEGORIES) },
                            colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHighest),
                            shape = RoundedCornerShape(50),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text("Change", color = CyanSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(2.dp))
                            Icon(imageVector = Icons.Default.SwapHoriz, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }
        }

        // 2. Mode Switcher Tabs (Voice Tutor vs Formula Chat)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .widthIn(max = 280.dp)
                        .clip(RoundedCornerShape(50))
                        .background(DarkSurfaceContainerLowest)
                        .padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(50))
                            .background(if (activeMode == "voice") OrangePrimaryContainer else Color.Transparent)
                            .clickable { EduDxRepository.setAiMode("voice") }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(imageVector = Icons.Default.Mic, contentDescription = null, tint = if (activeMode == "voice") Color.White else DarkOnSurfaceVariant, modifier = Modifier.size(16.dp))
                            Text(text = "Voice Tutor", color = if (activeMode == "voice") Color.White else DarkOnSurfaceVariant, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(50))
                            .background(if (activeMode == "text") OrangePrimaryContainer else Color.Transparent)
                            .clickable { EduDxRepository.setAiMode("text") }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(imageVector = Icons.Default.EditNote, contentDescription = null, tint = if (activeMode == "text") Color.White else DarkOnSurfaceVariant, modifier = Modifier.size(16.dp))
                            Text(text = "Formula Chat", color = if (activeMode == "text") Color.White else DarkOnSurfaceVariant, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 3. Central Interactive Audio Orb Visualizer & Mic Launcher
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.size(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Pulse ring 1
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .scale(if (isListening) pulseScale else 1f)
                            .clip(CircleShape)
                            .background(OrangePrimaryContainer.copy(alpha = if (isListening) 0.25f else 0.1f))
                    )

                    // Pulse ring 2
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .background(CyanSecondary.copy(alpha = if (isListening) 0.2f else 0.08f))
                    )

                    // Core Orb
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(DarkSurfaceContainerLowest, DarkSurfaceContainer, DarkSurfaceContainerHigh)
                                )
                            )
                            .border(1.dp, OrangePrimaryContainer.copy(alpha = 0.4f), CircleShape)
                            .clickable { EduDxRepository.toggleMicListening() },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(3.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.height(28.dp)
                            ) {
                                val heights = if (isListening) listOf(14.dp, 26.dp, 12.dp, 22.dp, 10.dp) else listOf(8.dp, 16.dp, 10.dp, 14.dp, 6.dp)
                                heights.forEachIndexed { i, h ->
                                    Box(
                                        modifier = Modifier
                                            .width(4.dp)
                                            .height(h)
                                            .clip(RoundedCornerShape(50))
                                            .background(if (i % 2 == 0) CyanSecondary else OrangePrimaryContainer)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isListening) "LISTENING" else "READY",
                                color = CyanSecondary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { EduDxRepository.toggleMicListening() },
                    colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 22.dp, vertical = 10.dp),
                    modifier = Modifier.testTag("ai_mic_btn")
                ) {
                    Icon(imageVector = Icons.Default.Mic, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isListening) "Listening... Speak Now" else "Tap to Ask by Voice",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(top = 6.dp)
                ) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = BorderOutline, modifier = Modifier.size(14.dp))
                    Text(text = "Speak freely in English, Hindi, or mixed technical terms", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                }
            }
        }

        // 4. Active Voice Persona Selector
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "AI VOICE MODEL", color = DarkOnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                            Icon(imageVector = Icons.Default.RecordVoiceOver, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(14.dp))
                            Text(text = "Natural Prosody", color = CyanSecondary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Mentor Aisha
                        val isAisha = activePersona == "aisha"
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isAisha) DarkSurfaceContainerHigh else DarkSurfaceContainer,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { EduDxRepository.setAiPersona("aisha") }
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(CyanSecondaryContainer.copy(alpha = 0.3f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.Face, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(18.dp))
                                }
                                Column {
                                    Text(text = "Mentor Aisha", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    Text(text = "Warm & Encouraging", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                                }
                            }
                        }

                        // Prof. Vikram
                        val isVikram = activePersona == "vikram"
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isVikram) DarkSurfaceContainerHigh else DarkSurfaceContainer,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { EduDxRepository.setAiPersona("vikram") }
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(DarkSurfaceContainerHighest),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.FaceRetouchingNatural, contentDescription = null, tint = BorderOutline, modifier = Modifier.size(18.dp))
                                }
                                Column {
                                    Text(text = "Prof. Vikram", color = DarkOnSurfaceVariant, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    Text(text = "Analytical & Deep", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // 5. Resolved Doubt Feed (Session #481)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Resolved Doubt Feed", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Session #481", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Student Query Balloon
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Surface(
                        shape = RoundedCornerShape(16.dp).copy(topEnd = CornerSize(2.dp)),
                        color = DarkSurfaceContainerHighest,
                        modifier = Modifier
                            .fillMaxWidth(0.88f)
                            .padding(end = 6.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Help, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(14.dp))
                                Text(text = "Your Voice Query • 2m ago", color = OrangePrimary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            }
                            Text(
                                text = "\"Why is there a negative sign in Faraday's law of induction?\"",
                                color = DarkOnSurface,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(OrangePrimaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "ME", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // AI Tutor Instant Audio Response Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(CyanSecondaryContainer.copy(alpha = 0.3f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.SmartToy, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(18.dp))
                                }
                                Column {
                                    Text(text = "Aisha • Instant Audio Response", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                    Text(text = "Physics Expert Engine", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                                }
                            }

                            Text(
                                text = "0:45 Duration",
                                color = CyanSecondary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(CyanSecondary.copy(alpha = 0.15f))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Audio Player Strip
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = DarkSurfaceContainerHigh,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(OrangePrimaryContainer)
                                        .clickable { EduDxRepository.toggleAiExplanationAudio() },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (isExplanationPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                        contentDescription = "Play AI voice",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = if (isExplanationPlaying) "0:28" else "0:14", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                                        Text(text = "0:45", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    LinearProgressIndicator(
                                        progress = { if (isExplanationPlaying) 0.62f else 0.31f },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(4.dp)
                                            .clip(RoundedCornerShape(50)),
                                        color = OrangePrimaryContainer,
                                        trackColor = DarkSurfaceContainerHighest,
                                    )
                                }

                                Text(
                                    text = aiSpeed,
                                    color = DarkOnSurfaceVariant,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(DarkSurfaceContainerLowest)
                                        .clickable { EduDxRepository.cycleAiPlaybackSpeed() }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Live Transcript
                        Text(
                            text = "Live Transcript: The minus sign represents Lenz's Law. It physically guarantees that the induced electromotive force creates an opposing magnetic field to resist the change in flux. Without it, you would violate the law of conservation of energy by creating free infinite power!",
                            color = DarkOnSurfaceVariant,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Key Formula Breakdown Callout Box
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = DarkSurfaceContainer,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Functions, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(16.dp))
                                    Text(text = "Key Formula Breakdown", color = CyanSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(DarkSurfaceContainerLowest)
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Ε = − N × (ΔΦ_B / Δt)",
                                        color = OrangePrimary,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    text = "Where (−) signifies the induced counter-torque, adhering directly to Energy Conservation.",
                                    color = DarkOnSurfaceVariant,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { EduDxRepository.showToast("Saved explanation to Episode Notes") },
                                colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                                shape = RoundedCornerShape(50),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Icon(imageVector = Icons.Default.BookmarkAdd, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Save to Episode Notes", color = DarkOnSurface, fontSize = 11.sp)
                            }

                            Button(
                                onClick = { EduDxRepository.showToast("Listening for follow-up question...") },
                                colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                                shape = RoundedCornerShape(50),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Repeat, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Ask Follow-up", color = DarkOnSurface, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }

        // 6. Suggested Inquiries
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "SUGGESTED INQUIRIES", color = DarkOnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Tap to send", color = CyanSecondary, fontSize = 11.sp)
                }

                Spacer(modifier = Modifier.height(6.dp))

                listOf(
                    Pair("Explain Lenz's Law with a real-world demo", Icons.Default.Lightbulb to OrangePrimary),
                    Pair("Generate 3 audio flashcard questions for this episode", Icons.Default.Quiz to CyanSecondary),
                    Pair("Summarize key equations from Chapter 4", Icons.Default.Summarize to PeriwinkleTertiary)
                ).forEach { (prompt, iconData) ->
                    val (icon, color) = iconData
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = DarkSurfaceContainerLow,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                            .clickable {
                                EduDxRepository.showToast("AI Processing: \"$prompt\"")
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
                                Text(text = prompt, color = DarkOnSurface, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }

        // 7. Interactive Visual Aid Diagram Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Interactive Visual Aid", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Fig 3.2 Diagram", color = OrangePrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = "https://lh3.googleusercontent.com/aida-public/AB6AXuAiJ84tLcvqLdbuGWCUsPvR1_Rc-zmMaRczJWhHnIUpW7ba1hOSZwtvllCsQFIWdU4KH_igUbCyyeE70g4zttQ7INfk7nq47ynuwkKpLV_zUz8ymaoyacwlqdFKE9fCPzUAH1Z8FYv4d6MA8cBOslrNacftxTPgypZc6F4ZsjcpKUWDGwZRO18MJGP0ip6AHqpkU1wNl2xikqrNggO2ek-koWVHwtWkLHxdFYCV8hoxZglGMiHYwJTZ0A"),
                            contentDescription = "Opposing Flux Visualized",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(50))
                                .background(DarkSurfaceContainerLowest.copy(alpha = 0.85f))
                                .padding(horizontal = 8.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Visibility, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(12.dp))
                            Text(text = "Opposing Flux Visualized", color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "This visual auto-synced with timestamp 14:20 of Dr. Rao's lecture. As the magnet drops, the repelling north pole creates counter-clockwise current.",
                        color = DarkOnSurfaceVariant,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}
