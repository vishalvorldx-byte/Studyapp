package com.example.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.data.EduDxRepository
import com.example.model.ScreenRoute
import com.example.ui.theme.*

@Composable
fun SchoolClassroomsScreen(
    onBackClick: () -> Unit = { EduDxRepository.navigateBack() },
    modifier: Modifier = Modifier
) {
    var selectedGrade by remember { mutableStateOf("All Grades (9-12)") }
    val grades = listOf("All Grades (9-12)", "Class 12", "Class 11", "Class 10", "Class 9")

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
                                .testTag("school_classrooms_back_btn")
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DarkOnSurface, modifier = Modifier.size(18.dp))
                        }
                        Column {
                            Text(text = "INSTITUTIONAL PORTAL", color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            Text(text = "School & Classrooms", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
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

        // 2. School Identity Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(DarkSurfaceContainerHigh),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.AccountBalance, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(28.dp))
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Surface(shape = RoundedCornerShape(50), color = CyanSecondary.copy(alpha = 0.15f)) {
                                    Text(text = "• Tier 1 Partner", color = CyanSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                                Text(text = "Code: DPS-DEL-2025", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                            }
                            Text(text = "Delhi Public School", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 2.dp))
                            Text(text = "R.K. Puram, New Delhi • CBSE Board", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(shape = RoundedCornerShape(8.dp), color = DarkSurfaceContainer, modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(imageVector = Icons.Default.WorkspacePremium, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(16.dp))
                                Text(text = "School Partner License: Active", color = CyanSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                            Text(text = "Renews Mar 2026", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                        }
                    }
                }
            }
        }

        // 3. Term & Grade Filter
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(16.dp))
                        Text(text = "TERM 2 (2024-25)", color = DarkOnSurface, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        text = "Change Term >",
                        color = OrangePrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { EduDxRepository.showToast("Term switcher opened") }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(grades) { grd ->
                        val isSel = selectedGrade == grd
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(if (isSel) OrangePrimaryContainer else DarkSurfaceContainerHigh)
                                .clickable { selectedGrade = grd }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(text = grd, color = if (isSel) Color.White else DarkOnSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 4. Institutional KPI Grid (2x2)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SchoolKpiCard("1,420", "Enrolled Students", "94% onb.", Icons.Default.Group, OrangePrimary, Modifier.weight(1f))
                    SchoolKpiCard("8,450 hrs", "Audio Study Hours", "+18% MoM", Icons.Default.Headphones, CyanSecondary, Modifier.weight(1f))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SchoolKpiCard("28 Sec.", "Active Classrooms", "Sci/Comm/Hum", Icons.Default.MeetingRoom, PeriwinkleTertiary, Modifier.weight(1f))
                    SchoolKpiCard("78.4%", "Avg Mastery", "+9.4% Nat.", Icons.Default.MilitaryTech, CyanSecondary, Modifier.weight(1f))
                }
            }
        }

        // 5. Listening Patterns Chart
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
                        Column {
                            Text(text = "Listening Patterns", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Weekly hours across school day periods", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                        }
                        Surface(shape = RoundedCornerShape(4.dp), color = DarkSurfaceContainerHigh) {
                            Text(text = "Peak 7:30 AM & 6 PM", color = CyanSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(CyanSecondary))
                            Text(text = "Commute (58%)", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(OrangePrimaryContainer))
                            Text(text = "In-School Study (42%)", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Waveform Timeline Canvas
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(DarkSurfaceContainer)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        // Commute Path (Cyan) with peaks at morning and evening
                        val commutePath = Path().apply {
                            moveTo(0f, size.height * 0.8f)
                            cubicTo(size.width * 0.15f, size.height * 0.15f, size.width * 0.25f, size.height * 0.2f, size.width * 0.35f, size.height * 0.8f)
                            cubicTo(size.width * 0.55f, size.height * 0.85f, size.width * 0.7f, size.height * 0.2f, size.width * 0.8f, size.height * 0.25f)
                            cubicTo(size.width * 0.9f, size.height * 0.8f, size.width * 0.95f, size.height * 0.85f, size.width, size.height * 0.85f)
                        }
                        drawPath(commutePath, color = Color(0xFF6BD8CB), style = Stroke(width = 3.5f, cap = StrokeCap.Round))

                        // In-school Path (Orange) with midday peak
                        val schoolPath = Path().apply {
                            moveTo(0f, size.height * 0.9f)
                            cubicTo(size.width * 0.3f, size.height * 0.85f, size.width * 0.45f, size.height * 0.3f, size.width * 0.55f, size.height * 0.3f)
                            cubicTo(size.width * 0.65f, size.height * 0.4f, size.width * 0.85f, size.height * 0.9f, size.width, size.height * 0.9f)
                        }
                        drawPath(schoolPath, color = Color(0xFFF97316), style = Stroke(width = 3.5f, cap = StrokeCap.Round))
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "6:00 AM", color = DarkOnSurfaceVariant, fontSize = 9.sp)
                        Text(text = "7:30 AM (Bus)", color = CyanSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        Text(text = "12:00 PM (Lab)", color = OrangePrimary, fontSize = 9.sp)
                        Text(text = "6:00 PM (Metro)", color = CyanSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        Text(text = "9:30 PM", color = DarkOnSurfaceVariant, fontSize = 9.sp)
                    }
                }
            }
        }

        // 6. Seat Allocation & License Usage
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(imageVector = Icons.Default.Key, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(18.dp))
                            Text(text = "Seat Allocation (Annual Quota)", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                        Surface(shape = RoundedCornerShape(50), color = CyanSecondary.copy(alpha = 0.15f)) {
                            Text(text = "80 Seats Open", color = CyanSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "1,420 Active Seats", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(text = "1,500 Total Licensed (94.7%)", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                    }

                    LinearProgressIndicator(
                        progress = { 0.947f },
                        modifier = Modifier.fillMaxWidth().height(6.dp).padding(top = 6.dp).clip(RoundedCornerShape(50)),
                        color = OrangePrimaryContainer,
                        trackColor = DarkSurfaceContainerHighest
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { EduDxRepository.showToast("Batch CSV student importer opened") },
                        colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.fillMaxWidth().height(42.dp)
                    ) {
                        Icon(imageVector = Icons.Default.UploadFile, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "+ Invite Students via CSV", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // 7. Classroom Leaderboard
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Classroom Leaderboard", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Audio course completion & mastery", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                    }
                    Text(text = "View all 28 >", color = OrangePrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                ClassroomLeaderboardItem("#1", "Class 12-A", "PCM Cohort • 42 Students", "92% Done", "Top: Electromagnetism • 84% Acc.", OrangePrimaryContainer)
                ClassroomLeaderboardItem("#2", "Class 12-B", "PCB Cohort • 38 Students", "88% Done", "Top: Organic Mechanisms • 81% Acc.", CyanSecondary)
                ClassroomLeaderboardItem("#3", "Class 11-A", "Foundation • 45 Students", "79% Done", "Top: Kinematics & Motion • 75% Acc.", PeriwinkleTertiary)
            }
        }

        // 8. Institutional Operations Shortcuts
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(text = "Institutional Operations", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)

                InstitutionalShortcutCard(
                    title = "Assign Homework to Class",
                    subtitle = "Select audio modules and quiz milestones",
                    icon = Icons.Default.AssignmentTurnedIn,
                    onClick = { EduDxRepository.navigateTo(ScreenRoute.FACULTY_ASSIGNMENTS) }
                )

                InstitutionalShortcutCard(
                    title = "Download Term 2 PDF Report",
                    subtitle = "Accreditation & CBSE audit compliance export",
                    icon = Icons.Default.PictureAsPdf,
                    onClick = { EduDxRepository.showToast("Generating consolidated Term 2 compliance report...") }
                )

                InstitutionalShortcutCard(
                    title = "Manage Teacher Accounts",
                    subtitle = "34 Faculty educators across 4 departments",
                    icon = Icons.Default.SupervisorAccount,
                    onClick = { EduDxRepository.navigateTo(ScreenRoute.ADMIN_USERS) }
                )
            }
        }
    }
}

@Composable
private fun SchoolKpiCard(value: String, label: String, badge: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, modifier: Modifier) {
    Surface(shape = RoundedCornerShape(12.dp), color = DarkSurfaceContainerLow, modifier = modifier) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Box(modifier = Modifier.size(30.dp).clip(RoundedCornerShape(8.dp)).background(color.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                    Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
                }
                Surface(shape = RoundedCornerShape(50), color = color.copy(alpha = 0.15f)) {
                    Text(text = badge, color = color, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                }
            }
            Text(text = value, color = DarkOnSurface, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
            Text(text = label, color = DarkOnSurfaceVariant, fontSize = 10.sp)
        }
    }
}

@Composable
private fun ClassroomLeaderboardItem(rank: String, title: String, subtitle: String, progress: String, tag: String, color: Color) {
    Surface(shape = RoundedCornerShape(12.dp), color = DarkSurfaceContainerLow, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(modifier = Modifier.size(30.dp).clip(RoundedCornerShape(8.dp)).background(color.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {
                        Text(text = rank, color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Column {
                        Text(text = title, color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Text(text = subtitle, color = DarkOnSurfaceVariant, fontSize = 10.sp)
                    }
                }
                Text(text = progress, color = color, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
            Surface(shape = RoundedCornerShape(6.dp), color = DarkSurfaceContainer, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                Text(text = tag, color = DarkOnSurfaceVariant, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
            }
        }
    }
}

@Composable
private fun InstitutionalShortcutCard(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = DarkSurfaceContainerLow,
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(DarkSurfaceContainerHigh), contentAlignment = Alignment.Center) {
                Icon(imageVector = icon, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(18.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Text(text = subtitle, color = DarkOnSurfaceVariant, fontSize = 10.sp)
            }
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(18.dp))
        }
    }
}
