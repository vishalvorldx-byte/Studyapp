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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.example.data.EduDxRepository
import com.example.model.ScreenRoute
import com.example.ui.theme.*

@Composable
fun AccountSettingsScreen(
    onBackClick: () -> Unit = { EduDxRepository.navigateBack() },
    modifier: Modifier = Modifier
) {
    var defaultSpeed by remember { mutableStateOf("1.25x") }
    var downloadQuality by remember { mutableStateOf("256 kbps") }
    var autoPlayNext by remember { mutableStateOf(true) }
    var backgroundPlayback by remember { mutableStateOf(true) }
    var mnemonicCues by remember { mutableStateOf(true) }
    var episodeDropNotif by remember { mutableStateOf(true) }
    var weeklyDigestNotif by remember { mutableStateOf(true) }
    var syncCaptions by remember { mutableStateOf(true) }
    var textSizeSlider by remember { mutableFloatStateOf(2f) }
    var cacheCleared by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentPadding = PaddingValues(bottom = 120.dp)
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
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(DarkSurfaceContainerHigh)
                                .testTag("account_back_btn")
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DarkOnSurface, modifier = Modifier.size(18.dp))
                        }
                        Text(text = "Account & Settings", color = DarkOnSurface, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }

                    IconButton(
                        onClick = { EduDxRepository.navigateTo(ScreenRoute.INBOX) },
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(DarkSurfaceContainerHigh)
                    ) {
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = "Inbox", tint = DarkOnSurfaceVariant, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }

        // 2. Profile & Membership Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Box(modifier = Modifier.size(56.dp)) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = "https://lh3.googleusercontent.com/aida-public/AB6AXuCQnd-6onTAToVIXaGBYUtIxy5D7TO7bxeENjnkxVpvMYz4QLT1l4lAEe7uNRJGo3mBZkN0NiYPZeyr3U5Gs3UOnISxEqyH5cqOSSJZRq6cgDIgv0OeKT1_s4iaShKnF90IjVU9HrBxAWsCw7HSd1MvgIST0yAZieZEXTQhJwnX5rFf5ffdFa9Wf2Xa3XkXluMeILkSwdrcti6vSIE5eMgoITNlVN1j1Upxr0T8QhTqQ0WxvdpxIJStAg"),
                                    contentDescription = "Avatar",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .align(Alignment.BottomEnd)
                                        .clip(CircleShape)
                                        .background(CyanSecondary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = Color(0xFF003732), modifier = Modifier.size(10.dp))
                                }
                            }

                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text(text = "Arjun K.", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                    Surface(shape = RoundedCornerShape(50), color = OrangePrimaryContainer.copy(alpha = 0.2f)) {
                                        Text(text = "PRO", color = OrangePrimary, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                    }
                                }
                                Text(text = "arjun.cbse24@gmail.com", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 4.dp)) {
                                    Surface(shape = RoundedCornerShape(50), color = CyanSecondaryContainer.copy(alpha = 0.2f)) {
                                        Text(text = "Class 12 CBSE • PCM", color = CyanSecondary, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                    }
                                    Text(text = "🔥 14d Streak", color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        IconButton(onClick = { EduDxRepository.showToast("Profile editing enabled") }, modifier = Modifier.size(32.dp)) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", tint = DarkOnSurfaceVariant, modifier = Modifier.size(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Quick Stats Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(DarkSurfaceContainer)
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "48.5h", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Audio Studied", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "142", color = OrangePrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Episodes Done", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "94%", color = CyanSecondary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Quiz Recall", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                        }
                    }
                }
            }
        }

        // 3. Academic Curriculum Section
        item {
            SettingsSectionHeader(title = "Academic Curriculum", icon = Icons.Default.School, badge = "Active Term")
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { EduDxRepository.navigateTo(ScreenRoute.AUTH) },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Academic Stage & Board", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Class 12 • CBSE Board • Science Stream", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                        }
                        Text(text = "Edit >", color = OrangePrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "Target Competitions", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 4.dp)) {
                        CompetitionPill("JEE Main & Adv", true)
                        CompetitionPill("CBSE Boards 2025", true)
                        CompetitionPill("+ Add Exam", false)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "Priority Subjects Deck", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SubjectDeckMini("Physics", "Part I & II", OrangePrimaryContainer, Icons.Default.Bolt, Modifier.weight(1f))
                        SubjectDeckMini("Chemistry", "Org + Inorg", CyanSecondary, Icons.Default.Science, Modifier.weight(1f))
                        SubjectDeckMini("Mathematics", "Calculus & Vec", PeriwinkleTertiary, Icons.Default.Calculate, Modifier.weight(1f))
                    }
                }
            }
        }

        // 4. Playback & Audio Engine
        item {
            SettingsSectionHeader(title = "Playback & Audio Engine", icon = Icons.Default.GraphicEq)
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    // Default Speed
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Default Playback Speed", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Optimized for conceptual clarity", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                        }

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(DarkSurfaceContainerHighest)
                                .padding(2.dp)
                        ) {
                            listOf("1.0x", "1.25x", "1.5x").forEach { spd ->
                                val sel = defaultSpeed == spd
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(if (sel) OrangePrimaryContainer else Color.Transparent)
                                        .clickable { defaultSpeed = spd }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(text = spd, color = if (sel) Color.White else DarkOnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = DarkSurfaceContainerHighest.copy(alpha = 0.5f))

                    // Download Quality
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Offline Download Quality", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Balances storage with vocal clarity", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                        }

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(DarkSurfaceContainerHighest)
                                .padding(2.dp)
                        ) {
                            listOf("128 kbps", "256 kbps").forEach { q ->
                                val sel = downloadQuality == q
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(if (sel) CyanSecondary else Color.Transparent)
                                        .clickable { downloadQuality = q }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(text = q, color = if (sel) Color(0xFF003732) else DarkOnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = DarkSurfaceContainerHighest.copy(alpha = 0.5f))

                    // Auto-play Next Chapter
                    ToggleRow(
                        title = "Auto-play Next Chapter",
                        desc = "Continuous chapter flow in syllabus order",
                        checked = autoPlayNext,
                        onCheckedChange = { autoPlayNext = it }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = DarkSurfaceContainerHighest.copy(alpha = 0.5f))

                    // Background Audio
                    ToggleRow(
                        title = "Background Audio Playback",
                        desc = "Continue audio when screen is locked",
                        checked = backgroundPlayback,
                        onCheckedChange = { backgroundPlayback = it }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = DarkSurfaceContainerHighest.copy(alpha = 0.5f))

                    // Mnemonic Audio Cues
                    ToggleRow(
                        title = "Mnemonic Audio Cues",
                        desc = "Auditory chimes highlighting formula definitions & traps",
                        checked = mnemonicCues,
                        onCheckedChange = { mnemonicCues = it }
                    )
                }
            }
        }

        // 5. Notifications & Study Streak
        item {
            SettingsSectionHeader(title = "Notifications & Study Streak", icon = Icons.Default.NotificationsActive)
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Daily Study Streak Alarm", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Keep your 14-day record alive", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                        }
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = DarkSurfaceContainerHigh,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(14.dp))
                                Text(text = "07:00 PM", color = DarkOnSurface, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = DarkSurfaceContainerHighest.copy(alpha = 0.5f))

                    ToggleRow(
                        title = "New Syllabus Episode Drops",
                        desc = "Instant notification when JEE modules release",
                        checked = episodeDropNotif,
                        onCheckedChange = { episodeDropNotif = it }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = DarkSurfaceContainerHighest.copy(alpha = 0.5f))

                    ToggleRow(
                        title = "Weekly AI Progress Digest",
                        desc = "Sunday analysis of weak topics & mastery",
                        checked = weeklyDigestNotif,
                        onCheckedChange = { weeklyDigestNotif = it }
                    )
                }
            }
        }

        // 6. Security & Local Storage
        item {
            SettingsSectionHeader(title = "Security & Local Storage", icon = Icons.Default.Shield)
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "2-Step Verification (OTP)", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Active on +91 ••••• ••482", color = CyanSecondary, fontSize = 11.sp)
                        }
                        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(18.dp))
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = DarkSurfaceContainerHighest.copy(alpha = 0.5f))

                    // Offline Cache
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Offline Audio Cache", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(
                                text = if (cacheCleared) "0 MB used • Cache cleared" else "18 episodes stored (1.4 GB / 64 GB)",
                                color = if (cacheCleared) CyanSecondary else DarkOnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }

                        Button(
                            onClick = {
                                cacheCleared = true
                                EduDxRepository.showToast("Offline audio cache cleared")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHighest),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(text = if (cacheCleared) "Cleared" else "Clear Cache", color = DarkOnSurface, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // 7. Danger Zone
        item {
            SettingsSectionHeader(title = "Danger Zone", icon = Icons.Default.Warning, isDanger = true)
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF93000A).copy(alpha = 0.4f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { EduDxRepository.showToast("Exporting learning journey JSON...") },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Export Learning Journey", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Download notes, bookmarks, and quiz stats as JSON", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                        }
                        Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(18.dp))
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = DarkSurfaceContainerHighest.copy(alpha = 0.5f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Delete Student Account", color = Color(0xFFFFB4AB), fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Permanently removes your profile & progress", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                        }

                        Button(
                            onClick = { showDeleteDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF93000A)),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(text = "Delete", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // Delete Account Dialog
    if (showDeleteDialog) {
        Dialog(onDismissRequest = { showDeleteDialog = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = DarkSurfaceContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF93000A).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.DeleteForever, contentDescription = null, tint = Color(0xFFFFB4AB), modifier = Modifier.size(24.dp))
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "Delete Student Account?", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "This will irreversibly delete all your curated study notes, 14-day study streak, JEE audio bookmarks, and diagnostic records.",
                        color = DarkOnSurfaceVariant,
                        fontSize = 12.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 16.sp,
                        modifier = Modifier.padding(top = 6.dp, bottom = 14.dp)
                    )

                    Button(
                        onClick = {
                            showDeleteDialog = false
                            EduDxRepository.showToast("Account deletion cancelled for safety in demo mode.")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF93000A)),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.fillMaxWidth().height(42.dp)
                    ) {
                        Text(text = "Yes, Delete Everything", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Button(
                        onClick = { showDeleteDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.fillMaxWidth().height(42.dp)
                    ) {
                        Text(text = "Cancel & Keep My Account", color = DarkOnSurface, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsSectionHeader(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, badge: String? = null, isDanger: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = if (isDanger) Color(0xFFFFB4AB) else CyanSecondary, modifier = Modifier.size(18.dp))
            Text(text = title, color = if (isDanger) Color(0xFFFFB4AB) else DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
        if (badge != null) {
            Surface(shape = RoundedCornerShape(50), color = CyanSecondary.copy(alpha = 0.15f)) {
                Text(text = badge, color = CyanSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
            }
        }
    }
}

@Composable
private fun ToggleRow(title: String, desc: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
            Text(text = title, color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Text(text = desc, color = DarkOnSurfaceVariant, fontSize = 11.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = OrangePrimaryContainer,
                uncheckedTrackColor = DarkSurfaceContainerHighest
            )
        )
    }
}

@Composable
private fun CompetitionPill(label: String, isSelected: Boolean) {
    Surface(
        shape = RoundedCornerShape(50),
        color = if (isSelected) OrangePrimaryContainer else DarkSurfaceContainerHigh,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = label, color = if (isSelected) Color.White else DarkOnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
            if (isSelected) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
            }
        }
    }
}

@Composable
private fun SubjectDeckMini(name: String, desc: String, color: Color, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = DarkSurfaceContainerHigh,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
            Text(text = name, color = DarkOnSurface, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 2.dp))
            Text(text = desc, color = DarkOnSurfaceVariant, fontSize = 9.sp)
        }
    }
}
