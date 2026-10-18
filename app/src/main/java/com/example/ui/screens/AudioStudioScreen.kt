package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.data.EduDxRepository
import com.example.model.ScreenRoute
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun AudioStudioScreen(
    onBackClick: () -> Unit = { EduDxRepository.navigateBack() },
    modifier: Modifier = Modifier
) {
    var selectedVoice by remember { mutableStateOf("aisha") } // aisha, vikram, ananya
    var selectedLang by remember { mutableStateOf("hinglish") }
    var selectedDuration by remember { mutableIntStateOf(10) }
    var mnemonicChecked by remember { mutableStateOf(true) }
    var formulaAidsChecked by remember { mutableStateOf(true) }
    var pauseChecksChecked by remember { mutableStateOf(true) }
    var isScriptExpanded by remember { mutableStateOf(false) }
    var isSynthesizing by remember { mutableStateOf(false) }
    var synthesisProgress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(isSynthesizing) {
        if (isSynthesizing) {
            synthesisProgress = 0f
            while (synthesisProgress < 1f) {
                delay(120)
                synthesisProgress += 0.1f
            }
            isSynthesizing = false
            EduDxRepository.showToast("Master audio lesson synthesized! Saved to Library.")
            EduDxRepository.navigateTo(ScreenRoute.PLAYER)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentPadding = PaddingValues(bottom = 110.dp)
    ) {
        // 1. Top Bar
        item {
            Surface(
                color = DarkBackground.copy(alpha = 0.95f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(DarkSurfaceContainer)
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DarkOnSurface)
                        }
                        Text(
                            text = "Audio Lesson Studio",
                            color = DarkOnSurface,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    IconButton(
                        onClick = { EduDxRepository.showToast("Shared TTS Studio configuration") },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(DarkSurfaceContainer)
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = DarkOnSurfaceVariant, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }

        // 2. Source Context Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerHigh),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(DarkSurfaceContainerHighest),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.AutoStories, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(22.dp))
                        }
                        Column {
                            Text(text = "SOURCE MATERIAL ATTACHED", color = DarkOnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text(text = "NCERT Class 12 Physics", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Chapter 6: Electromagnetic Induction", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(CyanSecondaryContainer.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Icon(imageVector = Icons.Default.RecordVoiceOver, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(text = "1,420 words", color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(DarkSurfaceContainer)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Timer, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(text = "~10-12 mins", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(OrangePrimaryContainer.copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(text = "Verified Formulae", color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 3. Select Narration Style & Voice
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(OrangePrimaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "1", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(text = "Select Narration Style & Voice", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(text = "3 AI Voices Ready", color = CyanSecondary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Voice 1: Aisha
                    VoiceSelectorCard(
                        name = "Mentor Aisha",
                        badge = "Recommended",
                        desc = "Warm, conversational bilingual tone",
                        tags = "Best for concept clarity • Hindi + English",
                        avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAuhnIxjWRGgWZyHiDmwNYtiIR_mf1A762ZK7vNcw1EhROHm7q93zdfsA0cFoOgzMVbbzcepiN0anSDNV_HeOABnE4Ysyh_2vhy90PzPCHWiVs8in-SMU2Nq23QhQCKCFbskczvzcrfx84c61z6r4MyYoIj3SRTIcpPXRUfR6156lFHnntRx1ap-ZnZ0fseKGVNyW4D9ZYoxoZMozfMJ1QuDJaXuczBOzWUDzzdfaXj4hif6MN4uyTaCQ",
                        isSelected = selectedVoice == "aisha",
                        onClick = { selectedVoice = "aisha" },
                        onPlaySample = { EduDxRepository.showToast("Previewing Mentor Aisha (15s sample)") }
                    )

                    // Voice 2: Vikram
                    VoiceSelectorCard(
                        name = "Prof. Vikram",
                        badge = "Academic",
                        desc = "Deep baritone with steady derivations",
                        tags = "Best for formula derivations • Deliberate pace",
                        avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBoirtkhbxAtAAeuUsSbthQT_Kz7wdLY7Bs6H16ps5AK-xmi84jDbKa7dlWEqs0WP_glYJmsTNEbM8DLSHWET0iIab3zvlqj0_a6_Cilh98KA0xWXdwf-WMnXiZsqtEsyYS1T_JiuVluny0bx_4d3pCDTefv6SVJ6_Ynh3N_YmAccjtEqgRiMT3pP0q1eiR9EpkqseqXxAacn9l8D8IJb6cILRvzvzZJx74d0CeaSQ4-LGtpFwKMp5thQ",
                        isSelected = selectedVoice == "vikram",
                        onClick = { selectedVoice = "vikram" },
                        onPlaySample = { EduDxRepository.showToast("Previewing Prof. Vikram (15s sample)") }
                    )

                    // Voice 3: Ananya
                    VoiceSelectorCard(
                        name = "Dr. Ananya",
                        badge = "Fast Track",
                        desc = "Fast-paced, energetic exam prep delivery",
                        tags = "Rapid board recap • 1.15x cadenced",
                        avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCSFGbATOK_z0bQT1FWFClbZqe_vYCJssxBGdow3yCGSB17RcXSfwfjQZYZUSU_3bDi-0rZmKBrizuGY96fTvSW-eemqk_WZXGnmTtaGGSsIFN8CLKNS9Cvx5t6a7dBbt7iJKdmOJFGH-Z4MwdP1D8KU44J-7hEquE_h358s8CpUcaFjqZZMbZfvq0tvdgBlC53-Ze2sX0CVKoERctCF87TJqr2aqqIHhfE8wDlsRjzmroSkauFn7O6kA",
                        isSelected = selectedVoice == "ananya",
                        onClick = { selectedVoice = "ananya" },
                        onPlaySample = { EduDxRepository.showToast("Previewing Dr. Ananya (15s sample)") }
                    )
                }
            }
        }

        // 4. Language & Code-Switching
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(modifier = Modifier.size(20.dp).clip(CircleShape).background(OrangePrimaryContainer), contentAlignment = Alignment.Center) {
                        Text(text = "2", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(text = "Language & Code-Switching", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(
                        Triple("hinglish", "Hinglish (Conversational Mix)", "Technical terms kept in English, intuition & connecting logic explained in fluid Hindi."),
                        Triple("english", "English (Pure Academic)", "Formal STEM terminology matching global standards and textbook prose."),
                        Triple("hindi", "Hindi (Shuddh Gyan)", "High-clarity formal Hindi explanation suited for national exams.")
                    ).forEach { (id, title, desc) ->
                        val isSelected = selectedLang == id
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) DarkSurfaceContainerHigh else DarkSurfaceContainerLow,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedLang = id }
                                .then(
                                    if (isSelected) Modifier.border(1.dp, OrangePrimaryContainer.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                                    else Modifier
                                )
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { selectedLang = id },
                                    colors = RadioButtonDefaults.colors(selectedColor = OrangePrimaryContainer)
                                )
                                Column {
                                    Text(text = title, color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                    Text(text = desc, color = DarkOnSurfaceVariant, fontSize = 11.sp, modifier = Modifier.padding(top = 2.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        // 5. Target Duration & Density
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Box(modifier = Modifier.size(20.dp).clip(CircleShape).background(OrangePrimaryContainer), contentAlignment = Alignment.Center) {
                            Text(text = "3", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(text = "Target Duration & Density", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(text = "$selectedDuration mins selected", color = OrangePrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(
                        Triple(5, "5 mins", "Bite-sized quick commute recap • ~600 words"),
                        Triple(10, "10 mins (Optimal)", "Balanced concept, diagrams & equations • ~1,420 words"),
                        Triple(18, "18 mins", "Comprehensive lecture with numericals • ~2,500 words")
                    ).forEach { (mins, title, desc) ->
                        val isSelected = selectedDuration == mins
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) DarkSurfaceContainerHigh else DarkSurfaceContainerLow,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedDuration = mins }
                                .then(
                                    if (isSelected) Modifier.border(1.dp, OrangePrimaryContainer.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                                    else Modifier
                                )
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(text = title, color = if (isSelected) OrangePrimary else DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                    Text(text = desc, color = DarkOnSurfaceVariant, fontSize = 11.sp)
                                }
                                if (isSelected) {
                                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        // 6. Script Preview Panel
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Script Preview Panel", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text(text = "155 wpm", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = DarkSurfaceContainerLow,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "\"Welcome to your EduDX audio masterclass on Electromagnetic Induction. Before we jump into Faraday’s flux experiments, let’s visualize a simple bar magnet approaching a copper ring. Notice how the magnetic field lines cut through the loop area? As the flux changes with time, an induced electromotive force opposes that very motion — this is the beauty of Lenz's Law...\"",
                            color = DarkOnSurface,
                            fontSize = 11.sp,
                            lineHeight = 16.sp,
                            maxLines = if (isScriptExpanded) 10 else 3,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = if (isScriptExpanded) "Read Less" else "Read Full Script",
                            color = OrangePrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clickable { isScriptExpanded = !isScriptExpanded }
                                .padding(top = 6.dp)
                        )
                    }
                }
            }
        }

        // 7. Generation Status & Action Bar
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                if (isSynthesizing) {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerHigh),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "Cloud Audio Synthesis", color = CyanSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text(text = "${(synthesisProgress * 100).toInt()}%", color = OrangePrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            LinearProgressIndicator(
                                progress = { synthesisProgress },
                                modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(50)),
                                color = OrangePrimaryContainer,
                                trackColor = DarkSurfaceContainerHighest
                            )
                        }
                    }
                }

                Button(
                    onClick = { isSynthesizing = true },
                    enabled = !isSynthesizing,
                    colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("generate_master_audio_btn")
                ) {
                    Icon(imageVector = Icons.Default.Mic, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("🎙️ Generate Master Audio Lesson", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { EduDxRepository.showToast("Streaming 15s sample of $selectedVoice...") },
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) {
                    Icon(imageVector = Icons.Default.PlayCircle, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Test 15-Second Voice Sample", color = DarkOnSurface, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun VoiceSelectorCard(
    name: String,
    badge: String,
    desc: String,
    tags: String,
    avatarUrl: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    onPlaySample: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) DarkSurfaceContainerHigh else DarkSurfaceContainerLow,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .then(
                if (isSelected) Modifier.border(1.dp, OrangePrimaryContainer.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                else Modifier
            )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(modifier = Modifier.size(46.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(model = avatarUrl),
                    contentDescription = name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(OrangePrimaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
                    }
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = name, color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = badge,
                        color = OrangePrimary,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(OrangePrimary.copy(alpha = 0.15f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Text(text = desc, color = DarkOnSurfaceVariant, fontSize = 11.sp)
                Text(text = tags, color = CyanSecondary, fontSize = 10.sp, modifier = Modifier.padding(top = 2.dp))
            }

            IconButton(
                onClick = onPlaySample,
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(DarkSurfaceContainer)
            ) {
                Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Play voice sample", tint = OrangePrimary, modifier = Modifier.size(18.dp))
            }
        }
    }
}
