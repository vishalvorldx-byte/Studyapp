package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
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
fun AdminOverviewScreen(
    onBackClick: () -> Unit = { EduDxRepository.navigateBack() },
    modifier: Modifier = Modifier
) {
    var selectedTimeScope by remember { mutableStateOf("24h") }
    var isSyncing by remember { mutableStateOf(false) }

    val activeStudentsCount = when (selectedTimeScope) {
        "24h" -> "48,250"
        "7d" -> "72,190"
        else -> "118,400"
    }

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
                                .testTag("admin_overview_back_btn")
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DarkOnSurface, modifier = Modifier.size(18.dp))
                        }
                        Column {
                            Text(text = "EDUDX OPS CENTRAL", color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            Text(text = "Admin Overview", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(50),
                        color = CyanSecondary.copy(alpha = 0.15f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(12.dp))
                            Text(text = "SUPERADMIN", color = CyanSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 2. System Status & Region Pill
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Surface(shape = RoundedCornerShape(10.dp), color = DarkSurfaceContainerLow, modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(imageVector = Icons.Default.CloudDone, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(16.dp))
                            Text(text = "Platform Healthy • 99.98% uptime", color = DarkOnSurface, fontSize = 11.sp)
                        }
                        Text(text = "ap-south-1", color = CyanSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Surface(shape = RoundedCornerShape(10.dp), color = OrangePrimaryContainer.copy(alpha = 0.15f), modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(16.dp))
                            Text(text = "Sandbox Node active (Read-Write Mode)", color = OrangePrimary, fontSize = 11.sp)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                isSyncing = true
                                EduDxRepository.showToast("Database and cache cluster synchronized")
                                isSyncing = false
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Sync, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(text = "Sync", color = OrangePrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 3. Time Scope Selector & Region
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 24h, 7d, 30d
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(DarkSurfaceContainerLow)
                        .padding(2.dp)
                ) {
                    listOf("24h", "7d", "30d").forEach { scope ->
                        val isSelected = selectedTimeScope == scope
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(if (isSelected) OrangePrimaryContainer else Color.Transparent)
                                .clickable { selectedTimeScope = scope }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(text = scope, color = if (isSelected) Color.White else DarkOnSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = DarkSurfaceContainerLow,
                    modifier = Modifier.clickable { EduDxRepository.showToast("Scope: India (All-Boards & Competitive)") }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.TravelExplore, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(16.dp))
                        Text(text = "India (All-Boards)", color = DarkOnSurface, fontSize = 11.sp)
                        Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(14.dp))
                    }
                }
            }
        }

        // 4. Core Operational KPI Grid (2x2)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // KPI 1: Active Students
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "ACTIVE STUDENTS", color = DarkOnSurfaceVariant, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(CyanSecondary.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.Group, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(14.dp))
                                }
                            }
                            Text(text = activeStudentsCount, color = DarkOnSurface, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(12.dp))
                                Text(text = "+12.4% w/w", color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }

                            // Sparkline
                            Canvas(modifier = Modifier.fillMaxWidth().height(26.dp).padding(top = 6.dp)) {
                                val path = Path().apply {
                                    moveTo(0f, size.height * 0.8f)
                                    quadraticTo(size.width * 0.25f, size.height * 0.7f, size.width * 0.45f, size.height * 0.4f)
                                    quadraticTo(size.width * 0.75f, size.height * 0.5f, size.width, size.height * 0.15f)
                                }
                                drawPath(path, color = Color(0xFF6BD8CB), style = Stroke(width = 3f, cap = StrokeCap.Round))
                            }
                        }
                    }

                    // KPI 2: Stream Audio
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "STREAM AUDIO", color = DarkOnSurfaceVariant, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(OrangePrimaryContainer.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.Headphones, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(14.dp))
                                }
                            }
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(text = "142.8k", color = DarkOnSurface, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
                                Text(text = " hrs", color = DarkOnSurfaceVariant, fontSize = 11.sp, modifier = Modifier.padding(bottom = 2.dp))
                            }
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                Icon(imageVector = Icons.Default.GraphicEq, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(12.dp))
                                Text(text = "Optimal throughput", color = CyanSecondary, fontSize = 10.sp)
                            }

                            // Mini Audio Wave Bars
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(26.dp)
                                    .padding(top = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.Bottom
                            ) {
                                listOf(0.4f, 0.7f, 1f, 0.55f, 0.85f, 0.45f, 0.9f).forEachIndexed { idx, frac ->
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight(frac)
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(if (idx == 6) CyanSecondary else OrangePrimaryContainer)
                                    )
                                }
                            }
                        }
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // KPI 3: AI Voice TTS
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "AI VOICE TTS", color = DarkOnSurfaceVariant, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(PeriwinkleTertiary.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.RecordVoiceOver, contentDescription = null, tint = PeriwinkleTertiary, modifier = Modifier.size(14.dp))
                                }
                            }
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(text = "18,940", color = DarkOnSurface, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
                                Text(text = " min", color = DarkOnSurfaceVariant, fontSize = 10.sp, modifier = Modifier.padding(bottom = 2.dp))
                            }
                            Text(text = "⚡ Neural V3 Engine", color = PeriwinkleTertiary, fontSize = 10.sp)
                            LinearProgressIndicator(
                                progress = { 0.76f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .padding(top = 8.dp)
                                    .clip(RoundedCornerShape(50)),
                                color = PeriwinkleTertiary,
                                trackColor = DarkSurfaceContainerHighest
                            )
                        }
                    }

                    // KPI 4: Cloud Cost
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "EST. AI CLOUD", color = DarkOnSurfaceVariant, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(OrangePrimaryContainer.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.Payments, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(14.dp))
                                }
                            }
                            Text(text = "$1,248.50", color = DarkOnSurface, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
                            Text(text = "$0.065 / student", color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            LinearProgressIndicator(
                                progress = { 0.42f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .padding(top = 8.dp)
                                    .clip(RoundedCornerShape(50)),
                                color = OrangePrimaryContainer,
                                trackColor = DarkSurfaceContainerHighest
                            )
                        }
                    }
                }
            }
        }

        // 5. Infrastructure Health
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
                            Icon(imageVector = Icons.Default.Dns, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(18.dp))
                            Text(text = "Infrastructure Health", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                        Surface(shape = RoundedCornerShape(50), color = CyanSecondary.copy(alpha = 0.15f)) {
                            Text(text = "3 Nodes Active", color = CyanSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Metric 1: CDN Storage
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Cloud CDN Media Storage", color = DarkOnSurface, fontSize = 11.sp)
                            Text(text = "4.2 TB / 10 TB (42%)", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                        }
                        LinearProgressIndicator(
                            progress = { 0.42f },
                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(50)),
                            color = CyanSecondary,
                            trackColor = DarkSurfaceContainerHighest
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Metric 2: TTS Worker Pool
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "TTS Worker Node Pool", color = DarkOnSurface, fontSize = 11.sp)
                            Text(text = "8 Nodes (84% load)", color = OrangePrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        LinearProgressIndicator(
                            progress = { 0.84f },
                            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(50)),
                            color = OrangePrimaryContainer,
                            trackColor = DarkSurfaceContainerHighest
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Metric 3: Vector DB
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Vector Embeddings (Pinecone)", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Curriculum concept semantic graph", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                        }
                        Text(text = "124.0k", color = PeriwinkleTertiary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // 6. Curriculum Assets Mosaic
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                Text(text = "Curriculum Assets (Published & Live)", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AssetStatCard("34", "Active Audio Series", Icons.Default.AutoStories, OrangePrimary, Modifier.weight(1f))
                    AssetStatCard("412", "Master Episodes", Icons.Default.Podcasts, CyanSecondary, Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AssetStatCard("1,840", "AI Checkpoint Quizzes", Icons.Default.Quiz, PeriwinkleTertiary, Modifier.weight(1f))
                    AssetStatCard("62", "School Portals", Icons.Default.Domain, OrangePrimary, Modifier.weight(1f))
                }
            }
        }

        // 7. Administrative Actions
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                Text(text = "Administrative Actions", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = OrangePrimaryContainer,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { EduDxRepository.navigateTo(ScreenRoute.ADMIN_CONTENT) }
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(imageVector = Icons.Default.AddCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                            Column {
                                Text(text = "Publish Series", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text(text = "Author & push audio", color = Color.White.copy(alpha = 0.8f), fontSize = 10.sp)
                            }
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = DarkSurfaceContainerLow,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { EduDxRepository.navigateTo(ScreenRoute.ADMIN_USERS) }
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(imageVector = Icons.Default.FactCheck, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(20.dp))
                            Column {
                                Text(text = "Moderate AI", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text(text = "3 pending review", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = DarkSurfaceContainerLow,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { EduDxRepository.navigateTo(ScreenRoute.ADMIN_USERS) }
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(imageVector = Icons.Default.ManageAccounts, contentDescription = null, tint = PeriwinkleTertiary, modifier = Modifier.size(20.dp))
                            Column {
                                Text(text = "Manage Roles", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text(text = "RBAC permissions", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                            }
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = DarkSurfaceContainerLow,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { EduDxRepository.showToast("System Error Logs: 0 fatal unhandled events") }
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(imageVector = Icons.Default.BugReport, contentDescription = null, tint = Color(0xFFFFB4AB), modifier = Modifier.size(20.dp))
                            Column {
                                Text(text = "Error Logs", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text(text = "0 fatal unhandled", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
        }

        // 8. Live Audit Log
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(imageVector = Icons.Default.HistoryEdu, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(18.dp))
                            Text(text = "Live Audit Log", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(text = "• Streaming events", color = CyanSecondary, fontSize = 10.sp)
                    }

                    AuditFeedItem("Episode #412 Published", "\"Thermodynamics Ch.4\" approved by Dr. Rao", "4m ago", Icons.Default.Publish, CyanSecondary)
                    AuditFeedItem("Batch Student Onboarding", "50 audio keys assigned to Delhi Public School", "18m ago", Icons.Default.School, OrangePrimary)
                    AuditFeedItem("TTS Latency Resolved", "Worker scaled in ap-south-1; delay 112ms", "32m ago", Icons.Default.Speed, PeriwinkleTertiary)
                    AuditFeedItem("Session Auth Rotated", "Automatic key exchange signed for 14 educator terminals", "1h ago", Icons.Default.Security, CyanSecondary)
                }
            }
        }
    }
}

@Composable
private fun AssetStatCard(count: String, label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, modifier: Modifier) {
    Surface(shape = RoundedCornerShape(12.dp), color = DarkSurfaceContainerLow, modifier = modifier) {
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(DarkSurfaceContainerHigh), contentAlignment = Alignment.Center) {
                Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
            }
            Column {
                Text(text = count, color = DarkOnSurface, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(text = label, color = DarkOnSurfaceVariant, fontSize = 10.sp, maxLines = 1)
            }
        }
    }
}

@Composable
private fun AuditFeedItem(title: String, desc: String, time: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color) {
    Surface(shape = RoundedCornerShape(10.dp), color = DarkSurfaceContainer, modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(modifier = Modifier.size(28.dp).clip(CircleShape).background(color.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {
                Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(14.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = title, color = DarkOnSurface, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text(text = time, color = DarkOnSurfaceVariant, fontSize = 10.sp)
                }
                Text(text = desc, color = DarkOnSurfaceVariant, fontSize = 10.sp, modifier = Modifier.padding(top = 2.dp))
            }
        }
    }
}
