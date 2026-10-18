package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
fun AdminUsersScreen(
    onBackClick: () -> Unit = { EduDxRepository.navigateBack() },
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf("All Users") }
    var selectedRoleFilter by remember { mutableStateOf("All Roles") }
    var searchQuery by remember { mutableStateOf("") }
    var showRoleModal by remember { mutableStateOf(false) }
    var selectedUserForRole by remember { mutableStateOf("Arjun K.") }

    val roleTabs = listOf("All Roles", "Student (44.1k)", "Teacher (3.2k)", "School Admin (820)", "Moderator (48)", "Superadmin (6)")

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
                                .testTag("admin_users_back_btn")
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DarkOnSurface, modifier = Modifier.size(18.dp))
                        }
                        Column {
                            Text(text = "USER ROSTER & SECURITY", color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            Text(text = "User Directory", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    IconButton(
                        onClick = { EduDxRepository.navigateTo(ScreenRoute.INBOX) },
                        modifier = Modifier.size(38.dp).clip(CircleShape).background(DarkSurfaceContainerHigh)
                    ) {
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }

        // 2. AI Compute & Audio Quota Barometer
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(CyanSecondary))
                            Text(text = "AI Compute & Audio Quota", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                        Surface(shape = RoundedCornerShape(50), color = DarkSurfaceContainerHigh) {
                            Text(text = "Realtime Health", color = CyanSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        QuotaMetricPill("842m", "TTS Audio Today", OrangePrimary, Modifier.weight(1f))
                        QuotaMetricPill("1.42M", "Tokens Billed", CyanSecondary, Modifier.weight(1f))
                        QuotaMetricPill("$1,248", "Limit $2,000", DarkOnSurface, Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { 0.624f },
                        modifier = Modifier.fillMaxWidth().height(5.dp).clip(RoundedCornerShape(50)),
                        color = OrangePrimaryContainer,
                        trackColor = DarkSurfaceContainerHighest
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "62.4% Budget Consumed", color = DarkOnSurfaceVariant, fontSize = 9.sp)
                        Text(text = "12 Days Left in Billing Cycle", color = OrangePrimary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // 3. Search & Role Filters
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search by name, roll no, student email, or school...", color = DarkOnSurfaceVariant, fontSize = 12.sp) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(18.dp)) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = DarkSurfaceContainerHigh,
                        unfocusedContainerColor = DarkSurfaceContainerHigh,
                        focusedBorderColor = OrangePrimaryContainer,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = DarkOnSurface,
                        unfocusedTextColor = DarkOnSurface
                    ),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(roleTabs) { role ->
                        val isSel = selectedRoleFilter == role
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(if (isSel) DarkSurfaceContainerHighest else DarkSurfaceContainerLow)
                                .clickable { selectedRoleFilter = role }
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(text = role, color = if (isSel) OrangePrimary else DarkOnSurfaceVariant, fontSize = 10.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal)
                        }
                    }
                }
            }
        }

        // 4. AI Moderation Queue
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(imageVector = Icons.Default.Campaign, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(18.dp))
                        Text(text = "AI Moderation Queue", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                    Surface(shape = RoundedCornerShape(50), color = Color(0xFF93000A).copy(alpha = 0.3f)) {
                        Text(text = "14 Urgent", color = Color(0xFFFFB4AB), fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Report Card 1
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Surface(shape = RoundedCornerShape(4.dp), color = DarkSurfaceContainer) {
                                Text(text = "Physics - CBSE XII • 3m ago", color = OrangePrimary, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                            Text(text = "Content Issue", color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }

                        Text(text = "Incorrect formula derivation in Lenz's law", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))

                        Surface(shape = RoundedCornerShape(8.dp), color = DarkSurfaceContainer, modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(text = "Student Query: \"Why is there a minus sign in induced EMF equation?\"", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                                Text(text = "AI Answer: \"The minus sign represents positive resistance accumulation rather than opposing flux change...\"", color = Color(0xFFFFB4AB), fontSize = 10.sp, modifier = Modifier.padding(top = 2.dp))
                                Text(text = "Flagged by student: Arjun K. (Roll #48102)", color = DarkOnSurfaceVariant, fontSize = 9.sp, modifier = Modifier.padding(top = 4.dp))
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Button(
                                onClick = { EduDxRepository.showToast("Formula fix verified & pushed to vector index") },
                                colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                                shape = RoundedCornerShape(50),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                modifier = Modifier.weight(1f).height(34.dp)
                            ) {
                                Text("Approve Fix", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = { EduDxRepository.showToast("Voice synthesizer regenerating audio stem...") },
                                colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                                shape = RoundedCornerShape(50),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                modifier = Modifier.weight(1f).height(34.dp)
                            ) {
                                Text("Regenerate Audio", color = CyanSecondary, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }

        // 5. User Directory Roster
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                Text(text = "User Directory", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(text = "Active users, creators & educators", color = DarkOnSurfaceVariant, fontSize = 10.sp)

                Spacer(modifier = Modifier.height(8.dp))

                // User 1: Student
                UserRosterCard(
                    initials = "AK",
                    name = "Arjun K.",
                    plan = "Pro Plan",
                    role = "Student",
                    roleColor = CyanSecondary,
                    subtitle = "Class 12 CBSE • Roll #48102 • Active Now",
                    metric1 = "14h 20m this week",
                    metric2 = "9 Days Streak 🔥",
                    onRoleClick = {
                        selectedUserForRole = "Arjun K."
                        showRoleModal = true
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // User 2: Creator
                UserRosterCard(
                    initials = "VR",
                    name = "Dr. Vikram Rao",
                    plan = "Verified",
                    role = "Creator",
                    roleColor = OrangePrimary,
                    subtitle = "Instructor • IIT Madras Alum • Offline 2h ago",
                    metric1 = "12 Audio Courses",
                    metric2 = "4.9 Avg Rating ★",
                    onRoleClick = {
                        selectedUserForRole = "Dr. Vikram Rao"
                        showRoleModal = true
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // User 3: Teacher
                UserRosterCard(
                    initials = "SS",
                    name = "Sunita Sharma",
                    plan = "DPS R.K. Puram",
                    role = "Teacher",
                    roleColor = PeriwinkleTertiary,
                    subtitle = "Lead Physics Faculty • 4 Batches Active",
                    metric1 = "186 Assigned Students",
                    metric2 = "AI Copilot Enabled ✓",
                    onRoleClick = {
                        selectedUserForRole = "Sunita Sharma"
                        showRoleModal = true
                    }
                )
            }
        }

        // 6. Security Audit Log
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(text = "Security & Audit Logs", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))

                Surface(shape = RoundedCornerShape(12.dp), color = DarkSurfaceContainerLow, modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(Color(0xFF93000A).copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {
                                Icon(imageVector = Icons.Default.GppBad, contentDescription = null, tint = Color(0xFFFFB4AB), modifier = Modifier.size(14.dp))
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = "401 Unauthorized Blocked", color = Color(0xFFFFB4AB), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    Text(text = "14:22:08", color = DarkOnSurfaceVariant, fontSize = 9.sp)
                                }
                                Text(text = "Brute force on /api/admin/content intercepted from IP 103.21.244.18", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                            }
                        }

                        HorizontalDivider(color = DarkSurfaceContainerHighest.copy(alpha = 0.5f))

                        Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(CyanSecondary.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {
                                Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(14.dp))
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = "Script Edit Approved", color = CyanSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    Text(text = "13:58:40", color = DarkOnSurfaceVariant, fontSize = 9.sp)
                                }
                                Text(text = "Moderator Priya S. approved revised audio script for Episode 12 (Optics)", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
        }
    }

    // Role Elevation Dialog
    if (showRoleModal) {
        Dialog(onDismissRequest = { showRoleModal = false }) {
            Surface(shape = RoundedCornerShape(16.dp), color = DarkSurfaceContainer, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(text = "Elevate Role: $selectedUserForRole", color = DarkOnSurface, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Promoting this account unlocks administrative permissions. Requires 2FA verification.", color = DarkOnSurfaceVariant, fontSize = 11.sp)

                    Surface(shape = RoundedCornerShape(8.dp), color = DarkSurfaceContainerHigh, modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = "• Student (Default Learner)", color = DarkOnSurface, fontSize = 11.sp)
                            Text(text = "• Teacher (Curriculum & Homework Assign)", color = CyanSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
                            Text(text = "• Moderator (AI Moderation Queue Access)", color = OrangePrimary, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = { showRoleModal = false }, colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh), shape = RoundedCornerShape(50), modifier = Modifier.weight(1f)) {
                            Text("Cancel", color = DarkOnSurface, fontSize = 11.sp)
                        }
                        Button(
                            onClick = {
                                showRoleModal = false
                                EduDxRepository.showToast("2FA OTP confirmation push sent to admin authenticator.")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier.weight(1.2f)
                        ) {
                            Text("Request 2FA OTP", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuotaMetricPill(value: String, label: String, color: Color, modifier: Modifier) {
    Surface(shape = RoundedCornerShape(8.dp), color = DarkSurfaceContainer, modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = value, color = color, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(text = label, color = DarkOnSurfaceVariant, fontSize = 9.sp, maxLines = 1)
        }
    }
}

@Composable
private fun UserRosterCard(
    initials: String,
    name: String,
    plan: String,
    role: String,
    roleColor: Color,
    subtitle: String,
    metric1: String,
    metric2: String,
    onRoleClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(DarkSurfaceContainerHighest), contentAlignment = Alignment.Center) {
                        Text(text = initials, color = roleColor, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = name, color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Surface(shape = RoundedCornerShape(4.dp), color = DarkSurfaceContainer) {
                                Text(text = plan, color = roleColor, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                            }
                        }
                        Text(text = subtitle, color = DarkOnSurfaceVariant, fontSize = 10.sp)
                    }
                }
                Surface(shape = RoundedCornerShape(50), color = roleColor.copy(alpha = 0.15f)) {
                    Text(text = role, color = roleColor, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(DarkSurfaceContainer)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = metric1, color = DarkOnSurface, fontSize = 10.sp)
                Text(text = metric2, color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Button(
                    onClick = onRoleClick,
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.height(30.dp)
                ) {
                    Text("Manage Role", color = DarkOnSurface, fontSize = 10.sp)
                }

                Button(
                    onClick = { EduDxRepository.showToast("Session reset for $name") },
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.height(30.dp)
                ) {
                    Text("Reset Session", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                }
            }
        }
    }
}
