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
fun SchoolAssignmentsScreen(
    onBackClick: () -> Unit = { EduDxRepository.navigateBack() },
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("Active (3)") }
    var showCreateModal by remember { mutableStateOf(false) }
    var showSubmissionsDrawer by remember { mutableStateOf(false) }
    var selectedAssignmentTitle by remember { mutableStateOf("Faraday's Law & Lenz's Rule") }
    var selectedBatch by remember { mutableStateOf("12-A") }

    val filterTabs = listOf("Active (3)", "Scheduled (1)", "Completed (14)")

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
                                .testTag("assignments_back_btn")
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DarkOnSurface, modifier = Modifier.size(18.dp))
                        }
                        Column {
                            Text(text = "FACULTY PORTAL", color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            Text(text = "School Assignments", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Button(
                        onClick = { EduDxRepository.showToast("Generating batch PDF & CSV performance report...") },
                        colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Icon(imageVector = Icons.Default.FileDownload, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Report", color = DarkOnSurface, fontSize = 11.sp)
                    }
                }
            }
        }

        // 2. Faculty Header Card
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
                        Image(
                            painter = rememberAsyncImagePainter(model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBdieTOIrQ9iAxFnZFdl6G4EC14GnvLWmw9CFsCbxeatOymNDKDK4Ld_a6mrhLTSdTETHk7UyoPdlUb7dxRNQJPFgdVMyHpNCQJFqY5KecZW-kOkrpEiU752emE9571c4xqD3eVHzIPy9U_419W8nbpiyHY6LduY_7BXZgurMMr5nwH1KBFrl7WNLsOLuFbq0sHkuDTogxr7cW1MV0yFZjIXCyGkkHB8PnCntUhczCylbBBjQXxyOMoqw"),
                            contentDescription = "Mrs. Sunita Sharma",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "FACULTY PORTAL", color = CyanSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Mrs. Sunita Sharma", color = DarkOnSurface, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Senior Physics Faculty • Dept. of Sciences", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Surface(shape = RoundedCornerShape(50), color = DarkSurfaceContainer) {
                            Text(text = "Class 12-A (42)", color = DarkOnSurface, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                        }
                        Surface(shape = RoundedCornerShape(50), color = DarkSurfaceContainer) {
                            Text(text = "Class 12-C (39)", color = DarkOnSurface, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                        }
                        Surface(shape = RoundedCornerShape(50), color = CyanSecondary.copy(alpha = 0.15f)) {
                            Text(text = "🔥 88% Listen Rate", color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                        }
                    }
                }
            }
        }

        // 3. Create Assignment CTA Button
        item {
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp)) {
                Button(
                    onClick = { showCreateModal = true },
                    colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("create_assignment_cta_btn")
                ) {
                    Icon(imageVector = Icons.Default.AddCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Create New Audio Assignment", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // 4. Filter Tabs
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                items(filterTabs) { tab ->
                    val isSel = selectedFilter == tab
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (isSel) OrangePrimaryContainer else DarkSurfaceContainerLow)
                            .clickable { selectedFilter = tab }
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                    ) {
                        Text(text = tab, color = if (isSel) Color.White else DarkOnSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // 5. Active Assignment 1: Faraday's Law
        item {
            AssignmentCard(
                batch = "Class 12-A • 42 Students",
                dueText = "Due Tomorrow, 8:00 PM",
                title = "Faraday's Law & Lenz's Rule - Audio Revision + Concept Quiz",
                episodeMeta = "Ep. 3 (26 mins) • 5 Checkpoint MCQs",
                completedCount = 34,
                totalCount = 42,
                percent = 0.81f,
                avgScore = "Avg. 84% Score",
                onViewSubmissions = {
                    selectedAssignmentTitle = "Faraday's Law & Lenz's Rule"
                    selectedBatch = "12-A"
                    showSubmissionsDrawer = true
                },
                onNudge = { EduDxRepository.showToast("Quick audio reminder broadcasted to pending students in Class 12-A!") },
                onExtend = { EduDxRepository.showToast("Deadline extended by +24 hours.") }
            )
        }

        // 6. Active Assignment 2: Eddy Currents
        item {
            AssignmentCard(
                batch = "Class 12-C • 39 Students",
                dueText = "Due Oct 28",
                title = "Eddy Currents & Transformer Losses",
                episodeMeta = "Ep. 5 (19 mins) • 4 Checkpoint MCQs",
                completedCount = 18,
                totalCount = 39,
                percent = 0.46f,
                avgScore = "Avg. 79% Score",
                onViewSubmissions = {
                    selectedAssignmentTitle = "Eddy Currents & Transformer Losses"
                    selectedBatch = "12-C"
                    showSubmissionsDrawer = true
                },
                onNudge = { EduDxRepository.showToast("Quick audio reminder broadcasted to pending students in Class 12-C!") },
                onExtend = { EduDxRepository.showToast("Deadline extended by +24 hours.") }
            )
        }
    }

    // Submissions Drawer Dialog
    if (showSubmissionsDrawer) {
        Dialog(onDismissRequest = { showSubmissionsDrawer = false }) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = DarkSurfaceContainerLow,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text(text = "Class $selectedBatch Roster", color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text(text = selectedAssignmentTitle, color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                        IconButton(onClick = { showSubmissionsDrawer = false }, modifier = Modifier.size(28.dp)) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = DarkOnSurfaceVariant)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "34 of 42 Submitted", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = CyanSecondary.copy(alpha = 0.2f),
                            modifier = Modifier.clickable { EduDxRepository.showToast("Broadcast audio nudge sent to all 8 pending students.") }
                        ) {
                            Text(text = "⚡ Nudge All Pending", color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        StudentSubmissionRow("Arjun K.", "AK", "Completed", "100% Audio • 2 Notes", "85%", CyanSecondary)
                        StudentSubmissionRow("Priya M.", "PM", "Top Score", "100% Audio • Checkpoint", "100%", OrangePrimary)
                        StudentSubmissionRow("Rohan S.", "RS", "In Progress", "45% Listened • Quiz Locked", "Nudge", Color(0xFFFFB4AB), isPending = true)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { EduDxRepository.showToast("Exporting section gradebook to CSV...") },
                            colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = DarkOnSurface, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Export CSV", color = DarkOnSurface, fontSize = 11.sp)
                        }

                        Button(
                            onClick = { showSubmissionsDrawer = false },
                            colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Done", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // Create Assignment Modal
    if (showCreateModal) {
        Dialog(onDismissRequest = { showCreateModal = false }) {
            Surface(shape = RoundedCornerShape(20.dp), color = DarkSurfaceContainerLow, modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "New Audio Assignment", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        IconButton(onClick = { showCreateModal = false }, modifier = Modifier.size(28.dp)) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = DarkOnSurfaceVariant)
                        }
                    }

                    Text(text = "Target Class Section", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        Surface(shape = RoundedCornerShape(8.dp), color = DarkSurfaceContainerHigh, modifier = Modifier.weight(1f)) {
                            Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(16.dp))
                                Text(text = "Class 12-A (42)", color = DarkOnSurface, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Surface(shape = RoundedCornerShape(8.dp), color = DarkSurfaceContainerHigh, modifier = Modifier.weight(1f)) {
                            Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(16.dp))
                                Text(text = "Class 12-C (39)", color = DarkOnSurface, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Text(text = "Curriculum Audio Episode", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                    Surface(shape = RoundedCornerShape(8.dp), color = DarkSurfaceContainer, modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(imageVector = Icons.Default.GraphicEq, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(20.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "Ep 06: Electromagnetic Waves", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text(text = "Duration: 24 mins • Audio Concept Master", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                            }
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = { showCreateModal = false }, colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh), shape = RoundedCornerShape(50), modifier = Modifier.weight(1f)) {
                            Text("Cancel", color = DarkOnSurface, fontSize = 11.sp)
                        }
                        Button(
                            onClick = {
                                showCreateModal = false
                                EduDxRepository.showToast("Audio Assignment published & broadcasted to students!")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier.weight(1.3f)
                        ) {
                            Text("Publish Assignment", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AssignmentCard(
    batch: String,
    dueText: String,
    title: String,
    episodeMeta: String,
    completedCount: Int,
    totalCount: Int,
    percent: Float,
    avgScore: String,
    onViewSubmissions: () -> Unit,
    onNudge: () -> Unit,
    onExtend: () -> Unit
) {
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
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(OrangePrimaryContainer))
                    Text(text = batch, color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                Surface(shape = RoundedCornerShape(50), color = DarkSurfaceContainer) {
                    Text(text = dueText, color = DarkOnSurfaceVariant, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                }
            }

            Text(text = title, color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
            Text(text = episodeMeta, color = CyanSecondary, fontSize = 11.sp, modifier = Modifier.padding(top = 2.dp, bottom = 8.dp))

            Surface(shape = RoundedCornerShape(10.dp), color = DarkSurfaceContainer, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "$completedCount / $totalCount Completed", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(text = "${(percent * 100).toInt()}% • $avgScore", color = CyanSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    LinearProgressIndicator(
                        progress = { percent },
                        modifier = Modifier.fillMaxWidth().height(5.dp).padding(top = 6.dp).clip(RoundedCornerShape(50)),
                        color = CyanSecondary,
                        trackColor = DarkSurfaceContainerHighest
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Button(
                    onClick = onViewSubmissions,
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f).height(36.dp)
                ) {
                    Icon(imageVector = Icons.Default.Analytics, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Submissions List", color = DarkOnSurface, fontSize = 11.sp)
                }

                Button(
                    onClick = onNudge,
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(imageVector = Icons.Default.Campaign, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Audio Nudge", color = CyanSecondary, fontSize = 11.sp)
                }

                IconButton(onClick = onExtend, modifier = Modifier.size(36.dp).clip(RoundedCornerShape(8.dp)).background(DarkSurfaceContainerHigh)) {
                    Icon(imageVector = Icons.Default.Event, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
private fun StudentSubmissionRow(name: String, initials: String, status: String, meta: String, score: String, scoreColor: Color, isPending: Boolean = false) {
    Surface(shape = RoundedCornerShape(10.dp), color = DarkSurfaceContainer, modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(modifier = Modifier.size(34.dp).clip(CircleShape).background(DarkSurfaceContainerHighest), contentAlignment = Alignment.Center) {
                    Text(text = initials, color = scoreColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = name, color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Surface(shape = RoundedCornerShape(4.dp), color = scoreColor.copy(alpha = 0.15f)) {
                            Text(text = status, color = scoreColor, fontSize = 8.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                        }
                    }
                    Text(text = meta, color = DarkOnSurfaceVariant, fontSize = 10.sp)
                }
            }

            if (!isPending) {
                Text(text = score, color = scoreColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            } else {
                Button(
                    onClick = { EduDxRepository.showToast("Direct push audio reminder sent to $name.") },
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                    modifier = Modifier.height(28.dp)
                ) {
                    Text("Nudge", color = CyanSecondary, fontSize = 10.sp)
                }
            }
        }
    }
}
