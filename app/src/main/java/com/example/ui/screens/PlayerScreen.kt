package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
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
fun PlayerScreen(
    onBackClick: () -> Unit = { EduDxRepository.navigateBack() },
    modifier: Modifier = Modifier
) {
    val track by EduDxRepository.currentTrack.collectAsState()
    var selectedTab by remember { mutableStateOf("transcript") } // transcript, formulas, ask_ai
    var showSleepModal by remember { mutableStateOf(false) }
    var userAiQuestion by remember { mutableStateOf("") }
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    fun formatSeconds(sec: Int): String {
        val m = sec / 60
        val s = sec % 60
        return "%d:%02d".format(m, s)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 30.dp)
        ) {
            // 1. Header Bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(DarkSurfaceContainer)
                            .testTag("player_minimize_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Minimize",
                            tint = DarkOnSurface,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "NOW PLAYING", color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
                        Text(
                            text = "Episode 3 of 16: Electromagnetism",
                            color = DarkOnSurfaceVariant,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconButton(
                            onClick = { showSleepModal = true },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(DarkSurfaceContainer)
                                .testTag("player_sleep_timer_btn")
                        ) {
                            Icon(imageVector = Icons.Default.Bedtime, contentDescription = "Sleep Timer", tint = DarkOnSurface, modifier = Modifier.size(20.dp))
                        }
                        IconButton(
                            onClick = { EduDxRepository.showToast("Shared episode link") },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(DarkSurfaceContainer)
                        ) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = DarkOnSurface, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }

            // 2. Artwork Container with 3D Spatial Audio badge & Unit tag
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(280.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .shadow(24.dp, RoundedCornerShape(20.dp))
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = track.coverUrl),
                            contentDescription = track.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        // 3D Spatial Audio badge bottom left
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp)
                                .clip(RoundedCornerShape(50))
                                .background(DarkSurfaceContainerLowest.copy(alpha = 0.85f))
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Equalizer, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(14.dp))
                            Text(text = "3D SPATIAL AUDIO", color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }

                        // Unit Tag top right
                        Text(
                            text = track.unitTag,
                            color = CyanSecondary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                                .clip(RoundedCornerShape(50))
                                .background(DarkSurfaceContainerLowest.copy(alpha = 0.85f))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // 3. Track Meta Information & Bookmark button
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = track.title,
                            color = DarkOnSurface,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = track.subjectSubtitle,
                            color = OrangePrimary,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(14.dp))
                            Text(text = track.instructor, color = DarkOnSurfaceVariant, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            Text(text = "• ${track.affiliation}", color = BorderOutline, fontSize = 12.sp)
                        }
                    }

                    IconButton(
                        onClick = { EduDxRepository.toggleBookmarkTrack() },
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(DarkSurfaceContainer)
                            .testTag("player_bookmark_btn")
                    ) {
                        Icon(
                            imageVector = if (track.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (track.isBookmarked) OrangePrimaryContainer else DarkOnSurfaceVariant,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            // 4. Interactive Timeline & Scrubber with Chapter Markers
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                    val progressFloat = track.currentSeconds.toFloat() / track.totalSeconds.toFloat()
                    Slider(
                        value = progressFloat,
                        onValueChange = { frac ->
                            val sec = (frac * track.totalSeconds).toInt()
                            EduDxRepository.seekTo(sec)
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = OrangePrimaryContainer,
                            inactiveTrackColor = DarkSurfaceContainerHighest
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("player_scrubber_slider")
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = formatSeconds(track.currentSeconds), color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text(text = "•", color = BorderOutline, fontSize = 12.sp)
                            Text(text = track.currentChapterName, color = CyanSecondary, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                        }
                        Text(text = formatSeconds(track.totalSeconds), color = DarkOnSurfaceVariant, fontSize = 12.sp)
                    }
                }
            }

            // 5. Central Playback Controls
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Skip -15s
                    IconButton(
                        onClick = {
                            EduDxRepository.seekRelative(-15)
                            EduDxRepository.showToast("-15 seconds back")
                        },
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(DarkSurfaceContainer)
                    ) {
                        Icon(imageVector = Icons.Default.Replay10, contentDescription = "Rewind 15s", tint = DarkOnSurface, modifier = Modifier.size(24.dp))
                    }

                    // Previous Track
                    IconButton(
                        onClick = { EduDxRepository.showToast("Playing previous: Ep 2. Magnetic Flux & Gauss Law") },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(DarkSurfaceContainer)
                    ) {
                        Icon(imageVector = Icons.Default.SkipPrevious, contentDescription = "Previous", tint = DarkOnSurface, modifier = Modifier.size(28.dp))
                    }

                    // Main Vivid Orange Play/Pause Button
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape)
                            .background(OrangePrimaryContainer)
                            .clickable { EduDxRepository.togglePlayPause() }
                            .shadow(16.dp, CircleShape)
                            .testTag("player_main_play_btn"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (track.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (track.isPlaying) "Pause" else "Play",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    // Next Track
                    IconButton(
                        onClick = { EduDxRepository.showToast("Loading next: Ep 4. Self & Mutual Induction") },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(DarkSurfaceContainer)
                    ) {
                        Icon(imageVector = Icons.Default.SkipNext, contentDescription = "Next", tint = DarkOnSurface, modifier = Modifier.size(28.dp))
                    }

                    // Skip +15s
                    IconButton(
                        onClick = {
                            EduDxRepository.seekRelative(15)
                            EduDxRepository.showToast("+15 seconds forward")
                        },
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(DarkSurfaceContainer)
                    ) {
                        Icon(imageVector = Icons.Default.Forward30, contentDescription = "Forward 15s", tint = DarkOnSurface, modifier = Modifier.size(24.dp))
                    }
                }
            }

            // 6. Secondary Audio Action Toolbar (Speed, Quality, Sleep, Bookmark)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Speed
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = DarkSurfaceContainer,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { EduDxRepository.cycleSpeed() }
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Speed, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = track.speed, color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Audio Quality
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = DarkSurfaceContainer,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { EduDxRepository.toggleAudioQuality() }
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.GraphicEq, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = track.quality, color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Sleep Timer
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = DarkSurfaceContainer,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { showSleepModal = true }
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.Snooze, contentDescription = null, tint = PeriwinkleTertiary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = track.sleepTimerLabel, color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Bookmark Formula
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = DarkSurfaceContainer,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { EduDxRepository.showToast("Formula bookmarked at ${formatSeconds(track.currentSeconds)}") }
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.BookmarkAdd, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = formatSeconds(track.currentSeconds), color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // 7. Interactive Learning Panel (Transcript, Formulas, Ask AI)
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        // Tabs
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(50))
                                .background(DarkSurfaceContainer)
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            listOf(
                                Triple("transcript", "Transcript", Icons.Default.Notes),
                                Triple("formulas", "Formulas", Icons.Default.Functions),
                                Triple("ask_ai", "Ask AI", Icons.Default.SmartToy)
                            ).forEach { (id, label, icon) ->
                                val isSelected = selectedTab == id
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(50))
                                        .background(if (isSelected) OrangePrimaryContainer else Color.Transparent)
                                        .clickable { selectedTab = id }
                                        .padding(vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = icon,
                                            contentDescription = null,
                                            tint = if (isSelected) Color.White else DarkOnSurfaceVariant,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = label,
                                            color = if (isSelected) Color.White else DarkOnSurfaceVariant,
                                            fontSize = 12.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Tab Content
                        when (selectedTab) {
                            "transcript" -> {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    // 13:50
                                    Surface(shape = RoundedCornerShape(8.dp), color = DarkSurfaceContainer.copy(alpha = 0.5f)) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text(text = "13:50", color = BorderOutline, fontSize = 10.sp)
                                                Text(text = "Experiment Setup", color = BorderOutline, fontSize = 10.sp)
                                            }
                                            Text(
                                                text = "\"Notice what happens as the north pole of our bar magnet approaches the copper solenoid. An induced current emerges instantaneously...\"",
                                                color = DarkOnSurfaceVariant,
                                                fontSize = 12.sp,
                                                lineHeight = 16.sp
                                            )
                                        }
                                    }

                                    // 14:28 Active Synchronized Segment
                                    Surface(shape = RoundedCornerShape(8.dp), color = DarkSurfaceContainerHigh) {
                                        Row(modifier = Modifier.fillMaxWidth()) {
                                            Box(
                                                modifier = Modifier
                                                    .width(4.dp)
                                                    .fillMaxHeight()
                                                    .background(OrangePrimaryContainer)
                                            )
                                            Column(modifier = Modifier.padding(10.dp)) {
                                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                    Text(text = "14:28 • Speaking Now", color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                                    Text(
                                                        text = "Core Axiom",
                                                        color = OrangePrimary,
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        modifier = Modifier
                                                            .clip(RoundedCornerShape(4.dp))
                                                            .background(OrangePrimary.copy(alpha = 0.2f))
                                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                                    )
                                                }
                                                Text(
                                                    text = "\"According to Lenz's law, the polarity of the induced EMF is always such that it produces a current whose magnetic field opposes the change in magnetic flux that produces it.\"",
                                                    color = DarkOnSurface,
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    lineHeight = 18.sp,
                                                    modifier = Modifier.padding(top = 4.dp)
                                                )
                                            }
                                        }
                                    }

                                    // 15:10
                                    Surface(shape = RoundedCornerShape(8.dp), color = DarkSurfaceContainer.copy(alpha = 0.5f)) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text(text = "15:10", color = BorderOutline, fontSize = 10.sp)
                                                Text(text = "Conservation Law", color = BorderOutline, fontSize = 10.sp)
                                            }
                                            Text(
                                                text = "\"This minus sign isn't arbitrary mathematical convention. It is a direct consequence of the Conservation of Energy in electromagnetism...\"",
                                                color = DarkOnSurfaceVariant,
                                                fontSize = 12.sp,
                                                lineHeight = 16.sp
                                            )
                                        }
                                    }
                                }
                            }

                            "formulas" -> {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    FormulaRowItem(
                                        title = "Faraday-Lenz Law",
                                        formula = "ε = - dΦ_B / dt",
                                        detail = "Negative sign represents Lenz's opposition direction",
                                        onCopy = {
                                            clipboardManager.setText(AnnotatedString("ε = - dΦ_B / dt"))
                                            EduDxRepository.showToast("Copied: ε = - dΦ_B / dt")
                                        }
                                    )
                                    FormulaRowItem(
                                        title = "Magnetic Flux Definition",
                                        formula = "Φ_B = B · A · cos(θ)",
                                        detail = "Flux unit: Weber (Wb) or Tesla meter squared",
                                        onCopy = {
                                            clipboardManager.setText(AnnotatedString("Φ_B = B · A · cos(θ)"))
                                            EduDxRepository.showToast("Copied: Φ_B = B · A · cos(θ)")
                                        }
                                    )
                                    FormulaRowItem(
                                        title = "Motional Electromotive Force",
                                        formula = "ε = B · l · v",
                                        detail = "Conductor length l moving with velocity v through field B",
                                        onCopy = {
                                            clipboardManager.setText(AnnotatedString("ε = B · l · v"))
                                            EduDxRepository.showToast("Copied: ε = B · l · v")
                                        }
                                    )
                                }
                            }

                            "ask_ai" -> {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.Top,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(DarkSurfaceContainer)
                                            .padding(10.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(32.dp)
                                                .clip(CircleShape)
                                                .background(CyanSecondaryContainer),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(imageVector = Icons.Default.SmartToy, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                        }
                                        Column {
                                            Text(text = "EduDX Physics Companion", color = CyanSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                            Text(
                                                text = "I am synchronized to timestamp 14:28. What would you like to clarify about why the flux change induces opposing current?",
                                                color = DarkOnSurfaceVariant,
                                                fontSize = 11.sp,
                                                lineHeight = 16.sp
                                            )
                                        }
                                    }

                                    // Quick prompt chips
                                    listOf(
                                        "Why is there a minus sign in Faraday's Law?",
                                        "Give me an everyday example of Lenz's law",
                                        "How to apply the right-hand grip rule here?"
                                    ).forEach { prompt ->
                                        Text(
                                            text = prompt,
                                            color = DarkOnSurface,
                                            fontSize = 11.sp,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(50))
                                                .background(DarkSurfaceContainer)
                                                .clickable {
                                                    userAiQuestion = prompt
                                                    EduDxRepository.showToast("Selected: \"$prompt\"")
                                                }
                                                .padding(horizontal = 10.dp, vertical = 6.dp)
                                        )
                                    }

                                    // Input row
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(50))
                                            .background(DarkSurfaceContainer)
                                            .padding(horizontal = 10.dp, vertical = 2.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        TextField(
                                            value = userAiQuestion,
                                            onValueChange = { userAiQuestion = it },
                                            placeholder = { Text("Ask about this audio section...", color = DarkOnSurfaceVariant, fontSize = 12.sp) },
                                            colors = TextFieldDefaults.colors(
                                                focusedContainerColor = Color.Transparent,
                                                unfocusedContainerColor = Color.Transparent,
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent,
                                                focusedTextColor = DarkOnSurface,
                                                unfocusedTextColor = DarkOnSurface
                                            ),
                                            modifier = Modifier.weight(1f)
                                        )

                                        IconButton(
                                            onClick = {
                                                if (userAiQuestion.isNotEmpty()) {
                                                    EduDxRepository.navigateTo(ScreenRoute.AI_TUTOR)
                                                } else {
                                                    EduDxRepository.showToast("Speech recognized: Explaining Lenz's Law")
                                                    EduDxRepository.navigateTo(ScreenRoute.AI_TUTOR)
                                                }
                                            },
                                            modifier = Modifier
                                                .size(34.dp)
                                                .clip(CircleShape)
                                                .background(CyanSecondaryContainer)
                                        ) {
                                            Icon(imageVector = Icons.Default.Mic, contentDescription = "Voice Query", tint = Color.White, modifier = Modifier.size(18.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Sleep Timer Bottom Sheet Modal
        if (showSleepModal) {
            AlertDialog(
                onDismissRequest = { showSleepModal = false },
                containerColor = DarkSurfaceContainerHigh,
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Sleep Timer", color = DarkOnSurface, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        IconButton(onClick = { showSleepModal = false }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = DarkOnSurfaceVariant)
                        }
                    }
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Off", "15m", "30m", "End of Episode").forEach { opt ->
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (track.sleepTimerLabel == opt) OrangePrimaryContainer.copy(alpha = 0.25f) else DarkSurfaceContainer,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        EduDxRepository.setSleepTimer(opt)
                                        showSleepModal = false
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = opt, color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                                    Icon(
                                        imageVector = when (opt) {
                                            "Off" -> Icons.Default.Cancel
                                            "15m", "30m" -> Icons.Default.Timer
                                            else -> Icons.Default.Flag
                                        },
                                        contentDescription = null,
                                        tint = if (track.sleepTimerLabel == opt) OrangePrimaryContainer else DarkOnSurfaceVariant,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {}
            )
        }
    }
}

@Composable
fun FormulaRowItem(
    title: String,
    formula: String,
    detail: String,
    onCopy: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = DarkSurfaceContainer,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, color = CyanSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text(text = formula, color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 2.dp))
                Text(text = detail, color = DarkOnSurfaceVariant, fontSize = 10.sp, modifier = Modifier.padding(top = 2.dp))
            }

            IconButton(
                onClick = onCopy,
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(DarkSurfaceContainerHigh)
            ) {
                Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy formula", tint = DarkOnSurface, modifier = Modifier.size(16.dp))
            }
        }
    }
}
