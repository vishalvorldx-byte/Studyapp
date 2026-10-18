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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.data.EduDxRepository
import com.example.model.ScreenRoute
import com.example.ui.theme.*

@Composable
fun AnalyticsScreen(
    onBackClick: () -> Unit = { EduDxRepository.navigateBack() },
    modifier: Modifier = Modifier
) {
    var selectedTimeframe by remember { mutableStateOf("This Week") }

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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(DarkSurfaceContainerHigh)
                                .testTag("analytics_back_btn")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = DarkOnSurface,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "STUDY METRICS & RETENTION",
                                color = OrangePrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Learning Analytics",
                                color = DarkOnSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    IconButton(
                        onClick = { EduDxRepository.showToast("Weekly Study PDF report shared") },
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(DarkSurfaceContainerHigh)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = DarkOnSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // 2. High-Level Summary Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "AUDIO STUDY TIME",
                                color = DarkOnSurfaceVariant,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "18.4 Hours",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
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
                                Icon(
                                    imageVector = Icons.Default.TrendingUp,
                                    contentDescription = null,
                                    tint = CyanSecondary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "+24% vs Last Week",
                                    color = CyanSecondary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Metric 1: Retention Score
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = DarkSurfaceContainerLowest,
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(text = "Retention Score", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                                Text(text = "88% High", color = OrangePrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                Text(text = "Top 5% in CBSE", color = DarkOnSurfaceVariant, fontSize = 9.sp)
                            }
                        }

                        // Metric 2: Daily Streak
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = DarkSurfaceContainerLowest,
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(text = "Active Streak", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Icon(imageVector = Icons.Default.LocalFireDepartment, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(16.dp))
                                    Text(text = "7 Days", color = OrangePrimaryContainer, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                }
                                Text(text = "Claimed +50 XP", color = DarkOnSurfaceVariant, fontSize = 9.sp)
                            }
                        }

                        // Metric 3: Quizzes Passed
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = DarkSurfaceContainerLowest,
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(text = "Drills Solved", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                                Text(text = "48 Questions", color = PeriwinkleTertiary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                Text(text = "91% accuracy", color = DarkOnSurfaceVariant, fontSize = 9.sp)
                            }
                        }
                    }
                }
            }
        }

        // 3. Weekly Activity Histogram
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Daily Listening Rhythm",
                        color = DarkOnSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Peak focus occurs at 6:30 AM - 8:00 AM (Morning commutes)",
                        color = DarkOnSurfaceVariant,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                    )

                    val weekDays = listOf("Mon" to 0.6f, "Tue" to 0.8f, "Wed" to 0.45f, "Thu" to 0.9f, "Fri" to 0.75f, "Sat" to 1.0f, "Sun" to 0.85f)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        weekDays.forEach { (day, ratio) ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(18.dp)
                                        .fillMaxHeight(ratio)
                                        .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                                        .background(
                                            if (day == "Sat" || day == "Thu") OrangePrimaryContainer
                                            else CyanSecondary.copy(alpha = 0.7f)
                                        )
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = day,
                                    color = if (day == "Sat") OrangePrimary else DarkOnSurfaceVariant,
                                    fontSize = 10.sp,
                                    fontWeight = if (day == "Sat") FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        }

        // 4. Subject Mastery Breakdown
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                Text(
                    text = "Curriculum Mastery",
                    color = DarkOnSurface,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Tracked across Class 12 CBSE & JEE Syllabus",
                    color = DarkOnSurfaceVariant,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Subject 1: Physics
                SubjectMasteryCard(
                    subject = "Physics",
                    progress = 0.82f,
                    completedModules = "42 of 50 Episodes",
                    highlight = "Strong in Electromagnetism • Revise Wave Optics",
                    accentColor = CyanSecondary
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Subject 2: Chemistry
                SubjectMasteryCard(
                    subject = "Chemistry",
                    progress = 0.68f,
                    completedModules = "34 of 50 Episodes",
                    highlight = "Strong in Aldehydes & Ketones • Revise Electrochemistry",
                    accentColor = OrangePrimary
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Subject 3: Mathematics
                SubjectMasteryCard(
                    subject = "Mathematics",
                    progress = 0.75f,
                    completedModules = "38 of 50 Episodes",
                    highlight = "Calculus masterclass completed • 3-D Geometry pending",
                    accentColor = PeriwinkleTertiary
                )
            }
        }

        // 5. Actions / Audio Flashcard Review
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerHigh),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(OrangePrimaryContainer.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.FactCheck,
                            contentDescription = null,
                            tint = OrangePrimaryContainer,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "12 Audio Flashcards Due for Review",
                            color = DarkOnSurface,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Spaced repetition optimizes 30-day exam retention",
                            color = DarkOnSurfaceVariant,
                            fontSize = 11.sp
                        )
                    }

                    Button(
                        onClick = { EduDxRepository.navigateTo(ScreenRoute.QUIZ_HUB) },
                        colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                        shape = RoundedCornerShape(50),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text("Start Drill", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun SubjectMasteryCard(
    subject: String,
    progress: Float,
    completedModules: String,
    highlight: String,
    accentColor: Color
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = DarkSurfaceContainer,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(accentColor)
                    )
                    Text(text = subject, color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
                Text(text = "${(progress * 100).toInt()}% Mastery", color = accentColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .clip(RoundedCornerShape(50)),
                color = accentColor,
                trackColor = DarkSurfaceContainerHigh,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = completedModules, color = DarkOnSurfaceVariant, fontSize = 10.sp)
                Text(text = highlight, color = DarkOnSurfaceVariant, fontSize = 10.sp)
            }
        }
    }
}
