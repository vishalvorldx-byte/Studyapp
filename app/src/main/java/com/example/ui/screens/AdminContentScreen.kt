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
fun AdminContentScreen(
    onBackClick: () -> Unit = { EduDxRepository.navigateBack() },
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf("All (42)") }
    var selectedClass by remember { mutableStateOf("Class 12") }
    var selectedSubject by remember { mutableStateOf("Physics (12 Series)") }
    var showCreateModal by remember { mutableStateOf(false) }
    var showArchiveModal by remember { mutableStateOf(false) }
    var showInspectorDrawer by remember { mutableStateOf(true) }

    val statusTabs = listOf("All (42)", "Published (28)", "Draft (9)", "Under Review (3)", "Archived (2)")

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
                                .testTag("admin_content_back_btn")
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DarkOnSurface, modifier = Modifier.size(18.dp))
                        }
                        Column {
                            Text(text = "STUDIO PORTAL", color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            Text(text = "Curriculum & Content Hub", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Button(
                        onClick = { showCreateModal = true },
                        colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                        shape = RoundedCornerShape(50),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.height(36.dp).testTag("new_series_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "New Series", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // 2. Search & Live Status Chips
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search by series, NCERT chapter, or tutor...", color = DarkOnSurfaceVariant, fontSize = 12.sp) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(18.dp)) },
                    trailingIcon = { Icon(imageVector = Icons.Default.Mic, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(18.dp)) },
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

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(statusTabs) { tab ->
                        val isSelected = selectedStatus == tab
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(if (isSelected) OrangePrimaryContainer else DarkSurfaceContainerHigh)
                                .clickable { selectedStatus = tab }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(text = tab, color = if (isSelected) Color.White else DarkOnSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 3. Curriculum Hierarchy Selector
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(imageVector = Icons.Default.AccountTree, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(14.dp))
                            Text(text = "CURRICULUM HIERARCHY", color = DarkOnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(text = "Active: Class 12 › Physics", color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Class pills
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("Class 9", "Class 10", "Class 11", "Class 12", "NEET/JEE").forEach { cls ->
                            val sel = selectedClass == cls
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (sel) CyanSecondaryContainer else DarkSurfaceContainerHigh)
                                    .clickable { selectedClass = cls }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(text = cls, color = if (sel) Color(0xFF003732) else DarkOnSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Subject pills
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("Physics (12 Series)", "Chemistry (9)", "Mathematics (14)", "Biology (7)").forEach { sub ->
                            val sel = selectedSubject == sub
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(if (sel) DarkSurfaceContainerHighest else DarkSurfaceContainerLowest)
                                    .clickable { selectedSubject = sub }
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(text = sub, color = if (sel) OrangePrimary else DarkOnSurfaceVariant, fontSize = 10.sp, fontWeight = if (sel) FontWeight.Bold else FontWeight.Normal)
                            }
                        }
                    }
                }
            }
        }

        // 4. Section Title
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Active Series (3)", color = DarkOnSurface, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(text = "Filtered by Subject", color = DarkOnSurfaceVariant, fontSize = 11.sp)
            }
        }

        // 5. Active Series 1: Electromagnetism & AC
        item {
            SeriesAdminCard(
                code = "12-PH",
                title = "Electromagnetism & AC",
                author = "Dr. Rajesh Sharma • NCERT Ch. 4-7",
                episodes = "16",
                duration = "4h 45m",
                metric3 = "98.4% VTT Synced",
                metric3Color = CyanSecondary,
                status = "Published",
                statusColor = CyanSecondary,
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDkYoLGPFY9_meoMAWNjNQreMO6LkRrMSl2xNS8uxAArJ47PhtWFdffxlLD49a5d47qvjDiw91kcqGnvBtznhyr_5tmgMoq6OQ4gcZfz-29YxVfrrKjE6E9b6yjcuXMWXAmVJto_Xxu67JSfJBdcgJSGjX-jY-TMbQp12zXwfKfsPHiCuE6mxlxThftVGsUwlGmnF-1ICIRZ5sNQG-_SVny65AZN98rF5ihFQGUmrhFdzWMIFLm1RvKcw",
                onArchiveClick = { showArchiveModal = true },
                onManageClick = { showInspectorDrawer = true }
            )
        }

        // 6. Active Series 2: Organic Chemistry
        item {
            SeriesAdminCard(
                code = "12-CH",
                title = "Reaction Mechanisms",
                author = "Prof. Aanya Sen • NCERT Ch. 10-12",
                episodes = "14",
                duration = "3h 50m",
                metric3 = "100% Quiz Stems",
                metric3Color = CyanSecondary,
                status = "Published",
                statusColor = CyanSecondary,
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCQODtXiYib4U35GWJZMA292ktV2EwxRepBxIxOAKHzLx5Kw-_WB_bNRL7IQ3p8TjZAZFJlkoAIaMev5yLEWmR81pS9PJeQc57nccrs5QejuROJiHRCe6Nud5dpyzK0vRXkt40oij76m_ALW7D15m4240e2o6TVvVfq82l1iFHfU6YSWvJ8Dnk66mH51KBgXcjYwHjKyJ4VKJ4vCHATPldHx02_wEPEMEvgsQSbzBXgN7QD3KVCEohnIQ",
                onArchiveClick = { showArchiveModal = true },
                onManageClick = { showInspectorDrawer = true }
            )
        }

        // 7. Active Series 3: NEET High-Yield Biology (Draft)
        item {
            SeriesAdminCard(
                code = "NEET",
                title = "High-Yield Biology Pods",
                author = "Dr. P. Varma • 8 of 12 Episodes Ready",
                episodes = "8",
                duration = "2h 10m",
                metric3 = "Draft Review",
                metric3Color = OrangePrimary,
                status = "Draft Review",
                statusColor = OrangePrimaryContainer,
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAgekMRcstZQ48APugDFUQG8ur8maA7KFkMZEqZA0d4Bzpg-Mmt1ocFWUkdOtr9_E1C4aO66uXXU8-eByHd9oIUV9jYnQnD2WQ1ET0kdkP6mAjLZy9YjIdFkby1AySZ0V1qToMlPItrTSZgbfIyAjgFr1ijNLp-BBExzpHbXj8l6umfyYUHW5HSuZw5amiziJXOfnj0PKLTDCPeEE581N-qcylTk0Vkm7dVhDrMiLVE81l40-BpslpcoQ",
                warningBanner = "TTS voice model generation queued for Track 9 & 10. Synthesized script pending audio lead review.",
                onArchiveClick = { showArchiveModal = true },
                onManageClick = { showInspectorDrawer = true }
            )
        }

        // 8. Episode & Quiz Question Inspector
        if (showInspectorDrawer) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(CyanSecondary))
                                Text(text = "Inspector: Ep. 03", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            }
                            Surface(shape = RoundedCornerShape(50), color = DarkSurfaceContainerHigh) {
                                Text(text = "Electromagnetism Series", color = DarkOnSurfaceVariant, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Track Card
                        Surface(shape = RoundedCornerShape(12.dp), color = DarkSurfaceContainerLow, modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = "Track 03 • 18m 24s", color = CyanSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    Text(text = "✓ 1,420 words verified", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                                }
                                Text(text = "Faraday's Law & Lenz's Rule Dynamics", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 2.dp))

                                // Play scrubber bar
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(OrangePrimaryContainer),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                    }
                                    Column(modifier = Modifier.weight(1f)) {
                                        LinearProgressIndicator(
                                            progress = { 0.4f },
                                            modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(50)),
                                            color = OrangePrimaryContainer,
                                            trackColor = DarkSurfaceContainerHighest
                                        )
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Text(text = "07:18", color = DarkOnSurfaceVariant, fontSize = 9.sp)
                                            Text(text = "18:24", color = DarkOnSurfaceVariant, fontSize = 9.sp)
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Attached Quizzes
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(imageVector = Icons.Default.Quiz, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(14.dp))
                                Text(text = "Attached Quiz Prompts (5 Questions)", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Text(
                                text = "+ Add Stem",
                                color = OrangePrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable { EduDxRepository.showToast("Add stem form opened") }
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Q1
                        InspectorQuizStemItem(
                            number = "Q1 • At Timestamp 06:14",
                            badge = "Concept Check",
                            question = "What physical quantity dictates the polarity of the induced electromotive force (EMF)?",
                            solution = "Correct: Lenz's Conservation of Energy",
                            passRate = "91% Student Pass"
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Q2
                        InspectorQuizStemItem(
                            number = "Q2 • At Timestamp 14:40",
                            badge = "Formula Calc",
                            question = "Calculate magnetic flux Φ through a coil of 50 turns when field changes at 0.4 T/s.",
                            solution = "Formula: ε = -N (dΦ/dt)",
                            passRate = "78% Student Pass"
                        )
                    }
                }
            }
        }
    }

    // Create New Series Modal
    if (showCreateModal) {
        Dialog(onDismissRequest = { showCreateModal = false }) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = DarkSurfaceContainerLow,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Create New Audio Series", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        IconButton(onClick = { showCreateModal = false }, modifier = Modifier.size(28.dp)) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = DarkOnSurfaceVariant)
                        }
                    }

                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text("Series Title", fontSize = 11.sp) },
                        placeholder = { Text("e.g. Modern Physics: Dual Nature & Atoms", fontSize = 11.sp) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            label = { Text("Instructor", fontSize = 11.sp) },
                            placeholder = { Text("Dr. K. Ramanathan", fontSize = 11.sp) },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            label = { Text("NCERT Mapping", fontSize = 11.sp) },
                            placeholder = { Text("Class 12 Ch 11", fontSize = 11.sp) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Artwork dropzone
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = DarkSurfaceContainerHigh,
                        modifier = Modifier.fillMaxWidth().clickable { EduDxRepository.showToast("Select 1400x1400 PNG/WEBP cover") }
                    ) {
                        Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(imageVector = Icons.Default.CloudUpload, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(24.dp))
                            Text(text = "Tap to upload 1400x1400 PNG cover", color = DarkOnSurface, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Max 4MB with high-contrast layout", color = DarkOnSurfaceVariant, fontSize = 9.sp)
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { showCreateModal = false },
                            colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel", color = DarkOnSurface, fontSize = 12.sp)
                        }

                        Button(
                            onClick = {
                                showCreateModal = false
                                EduDxRepository.showToast("New audio series published to Studio Hub!")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Save & Continue", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // Archive Confirmation Modal
    if (showArchiveModal) {
        Dialog(onDismissRequest = { showArchiveModal = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = DarkSurfaceContainer,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFF93000A).copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {
                        Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = Color(0xFFFFB4AB), modifier = Modifier.size(22.dp))
                    }
                    Text(text = "Archive Series?", color = DarkOnSurface, fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    Text(text = "This course is active for 1,842 students. Archiving removes it from public mobile hubs.", color = DarkOnSurfaceVariant, fontSize = 11.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center, modifier = Modifier.padding(vertical = 8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = { showArchiveModal = false }, colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh), shape = RoundedCornerShape(50), modifier = Modifier.weight(1f)) {
                            Text("Cancel", color = DarkOnSurface, fontSize = 11.sp)
                        }
                        Button(
                            onClick = {
                                showArchiveModal = false
                                EduDxRepository.showToast("Course archived successfully.")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF93000A)),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Confirm Archive", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SeriesAdminCard(
    code: String,
    title: String,
    author: String,
    episodes: String,
    duration: String,
    metric3: String,
    metric3Color: Color,
    status: String,
    statusColor: Color,
    imageUrl: String,
    warningBanner: String? = null,
    onArchiveClick: () -> Unit,
    onManageClick: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(modifier = Modifier.size(64.dp).clip(RoundedCornerShape(10.dp))) {
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUrl),
                        contentDescription = title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Surface(shape = RoundedCornerShape(4.dp), color = DarkBackground.copy(alpha = 0.9f), modifier = Modifier.align(Alignment.BottomEnd).padding(2.dp)) {
                        Text(text = code, color = CyanSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = RoundedCornerShape(50), color = statusColor.copy(alpha = 0.15f)) {
                            Text(text = status, color = statusColor, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }

                        Box {
                            IconButton(onClick = { showMenu = !showMenu }, modifier = Modifier.size(24.dp)) {
                                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(18.dp))
                            }
                            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                                DropdownMenuItem(text = { Text("View Analytics") }, onClick = { showMenu = false; EduDxRepository.navigateTo(ScreenRoute.ANALYTICS) })
                                DropdownMenuItem(text = { Text("Archive Series", color = Color(0xFFFFB4AB)) }, onClick = { showMenu = false; onArchiveClick() })
                            }
                        }
                    }

                    Text(text = title, color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 2.dp))
                    Text(text = author, color = DarkOnSurfaceVariant, fontSize = 11.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Metrics Strip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(DarkSurfaceContainer)
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = episodes, color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Episodes", color = DarkOnSurfaceVariant, fontSize = 9.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = duration, color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Duration", color = DarkOnSurfaceVariant, fontSize = 9.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = metric3, color = metric3Color, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Status", color = DarkOnSurfaceVariant, fontSize = 9.sp)
                }
            }

            if (warningBanner != null) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = OrangePrimaryContainer.copy(alpha = 0.15f),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                ) {
                    Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(imageVector = Icons.Default.PendingActions, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(16.dp))
                        Text(text = warningBanner, color = OrangePrimary, fontSize = 10.sp, lineHeight = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Button(
                    onClick = onManageClick,
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f).height(34.dp)
                ) {
                    Icon(imageVector = Icons.Default.PlaylistPlay, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Manage Episodes", color = DarkOnSurface, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = { EduDxRepository.showToast("Metadata editor opened") },
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text(text = "Meta", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                }

                Button(
                    onClick = { EduDxRepository.showToast("VTT subtitle tracks synced") },
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text(text = "VTT", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
private fun InspectorQuizStemItem(number: String, badge: String, question: String, solution: String, passRate: String) {
    Surface(shape = RoundedCornerShape(10.dp), color = DarkSurfaceContainerHigh, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = number, color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Surface(shape = RoundedCornerShape(4.dp), color = DarkSurfaceContainer) {
                    Text(text = badge, color = CyanSecondary, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                }
            }
            Text(text = question, color = DarkOnSurface, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = solution, color = DarkOnSurfaceVariant, fontSize = 10.sp)
                Text(text = passRate, color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
