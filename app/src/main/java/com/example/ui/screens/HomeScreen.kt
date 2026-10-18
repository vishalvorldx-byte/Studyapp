package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.data.EduDxRepository
import com.example.model.ScreenRoute
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val streakClaimed by EduDxRepository.streakClaimed.collectAsState()
    val selectedCategory by EduDxRepository.selectedCategoryPill.collectAsState()
    var showQuizDialog by remember { mutableStateOf(false) }
    var quizAnswerSelected by remember { mutableStateOf<Int?>(null) }
    var quizSubmitted by remember { mutableStateOf(false) }

    val categories = listOf("All", "Physics", "Chemistry", "Biology", "Mathematics", "History", "Coding & AI")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentPadding = PaddingValues(bottom = 110.dp)
    ) {
        // 1. Streak Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                DarkSurfaceContainerHigh,
                                DarkSurfaceContainer,
                                DarkSurfaceContainerLow
                            )
                        )
                    )
                    .padding(16.dp)
                    .testTag("streak_card")
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(OrangePrimaryContainer.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalFireDepartment,
                                    contentDescription = null,
                                    tint = OrangePrimaryContainer,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "7-Day Learning Streak!",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = DarkOnSurface,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = "Top 5% of active science learners today",
                                    color = DarkOnSurfaceVariant,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Button(
                            onClick = { EduDxRepository.claimStreak() },
                            enabled = !streakClaimed,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (streakClaimed) CyanSecondary else OrangePrimaryContainer,
                                contentColor = if (streakClaimed) CyanOnSecondary else Color.White
                            ),
                            shape = RoundedCornerShape(50),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("claim_xp_btn")
                        ) {
                            Icon(
                                imageVector = if (streakClaimed) Icons.Default.CheckCircle else Icons.Default.Celebration,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (streakClaimed) "Claimed" else "Claim 50 XP",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Day Dots Tracker
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(DarkSurfaceContainerHighest.copy(alpha = 0.5f))
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val days = listOf("M", "T", "W", "T", "F", "S", "S")
                        days.forEachIndexed { index, day ->
                            val isToday = index == 6
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = day,
                                    color = if (isToday) OrangePrimaryContainer else DarkOnSurfaceVariant,
                                    fontSize = 11.sp,
                                    fontWeight = if (isToday) FontWeight.Bold else FontWeight.Medium
                                )
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(if (isToday) OrangePrimaryContainer else CyanSecondary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (isToday) Icons.Default.Bolt else Icons.Default.Check,
                                        contentDescription = null,
                                        tint = if (isToday) Color.White else CyanOnSecondary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2. Horizontal Category Pills
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    val isSelected = category == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { EduDxRepository.setCategoryPill(category) },
                        label = { Text(category, fontSize = 12.sp, fontWeight = FontWeight.SemiBold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = OrangePrimaryContainer,
                            selectedLabelColor = Color.White,
                            containerColor = DarkSurfaceContainerHigh,
                            labelColor = DarkOnSurface
                        ),
                        shape = RoundedCornerShape(50),
                        border = null,
                        modifier = Modifier.testTag("cat_pill_$category")
                    )
                }
            }
        }

        // 2b. Smart Audio Toolkit (Quick Access)
        item {
            Column(modifier = Modifier.padding(top = 10.dp, bottom = 4.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Smart Audio Tools",
                        color = DarkOnSurface,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "AI Powered",
                        color = CyanSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(CyanSecondary.copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Tool 1: PDF to Audio
                    item {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = DarkSurfaceContainer,
                            modifier = Modifier
                                .width(138.dp)
                                .clickable { EduDxRepository.navigateTo(ScreenRoute.PDF_UPLOAD) }
                                .testTag("tool_pdf_upload")
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(OrangePrimaryContainer.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Description,
                                        contentDescription = null,
                                        tint = OrangePrimaryContainer,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Notes to Audio",
                                    color = DarkOnSurface,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "PDF into podcast",
                                    color = DarkOnSurfaceVariant,
                                    fontSize = 10.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                    // Tool 2: Daily Quiz Hub
                    item {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = DarkSurfaceContainer,
                            modifier = Modifier
                                .width(138.dp)
                                .clickable { EduDxRepository.navigateTo(ScreenRoute.QUIZ_HUB) }
                                .testTag("tool_quiz_hub")
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(CyanSecondary.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Quiz,
                                        contentDescription = null,
                                        tint = CyanSecondary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Concept Drills",
                                    color = DarkOnSurface,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Daily audio quiz",
                                    color = DarkOnSurfaceVariant,
                                    fontSize = 10.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                    // Tool 3: Study Room
                    item {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = DarkSurfaceContainer,
                            modifier = Modifier
                                .width(138.dp)
                                .clickable { EduDxRepository.navigateTo(ScreenRoute.STUDY_ROOM) }
                                .testTag("tool_study_room")
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(PeriwinkleTertiary.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Groups,
                                        contentDescription = null,
                                        tint = PeriwinkleTertiary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Live Study Room",
                                    color = DarkOnSurface,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Audio study lounge",
                                    color = DarkOnSurfaceVariant,
                                    fontSize = 10.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                    // Tool 4: Audio Studio
                    item {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = DarkSurfaceContainer,
                            modifier = Modifier
                                .width(138.dp)
                                .clickable { EduDxRepository.navigateTo(ScreenRoute.AUDIO_STUDIO) }
                                .testTag("tool_audio_studio")
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(OrangePrimary.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Mic,
                                        contentDescription = null,
                                        tint = OrangePrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Voice Studio",
                                    color = DarkOnSurface,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "TTS & Voice cloning",
                                    color = DarkOnSurfaceVariant,
                                    fontSize = 10.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                    // Tool 5: Analytics
                    item {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = DarkSurfaceContainer,
                            modifier = Modifier
                                .width(138.dp)
                                .clickable { EduDxRepository.navigateTo(ScreenRoute.ANALYTICS) }
                                .testTag("tool_analytics")
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(CyanSecondary.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.BarChart,
                                        contentDescription = null,
                                        tint = CyanSecondary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Analytics",
                                    color = DarkOnSurface,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Retention & streak",
                                    color = DarkOnSurfaceVariant,
                                    fontSize = 10.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. Featured Hero Masterclass Series Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .aspectRatio(16f / 10f)
                    .clip(RoundedCornerShape(16.dp))
                    .shadow(12.dp, RoundedCornerShape(16.dp))
                    .clickable { EduDxRepository.navigateTo(ScreenRoute.SERIES_DETAIL) }
                    .testTag("hero_series_card")
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBEKf-n9eEYy2nURAsnLuTYp0I7SpEETIdfpU8FOeQzcZC0JQ41CFnWhXU5uKk_hHAGIy-z_9Whxiv0mBjCluDWI5IUE_R6m_rvTNYaT55ql47alxB4KnWkWeQa3OfniE-ZGJKZRQbrWJXv4fCm6cu3M2UjEcQQB8ofoRAtQM3O_HksE9uHD_t3b31Kt62clIMMkBr8bDhixRssD-5VruyzABSFmHrj-SHyM2nxyJ8bLjLVQUHZbtRtRg"
                    ),
                    contentDescription = "Quantum Masterclass",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Scrim
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Black.copy(alpha = 0.2f),
                                    Color.Black.copy(alpha = 0.4f),
                                    DarkBackground.copy(alpha = 0.95f)
                                )
                            )
                        )
                )

                // Top Badges
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "MASTERCLASS SERIES",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(OrangePrimaryContainer)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(DarkSurfaceContainerLowest.copy(alpha = 0.8f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = OrangePrimaryContainer,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(text = "4.9 (4.8k)", color = CyanSecondary, fontSize = 10.sp)
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(DarkSurfaceContainerLowest.copy(alpha = 0.7f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Subscriptions,
                            contentDescription = null,
                            tint = OrangePrimary,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = "18 Episodes", color = DarkOnSurface, fontSize = 10.sp)
                    }
                }

                // Bottom Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Dr. Arvind Verma • Class 12 Advanced",
                        color = OrangePrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Quantum Physics & Wave Optics: Masterclass",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                            colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier
                                .weight(1f)
                                .height(42.dp)
                                .testTag("hero_resume_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayCircle,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Resume Ep 4 (12m left)", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }

                        IconButton(
                            onClick = { EduDxRepository.showToast("Saved to Library") },
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(DarkSurfaceBright.copy(alpha = 0.8f))
                                .testTag("hero_bookmark_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.BookmarkAdd,
                                contentDescription = "Bookmark",
                                tint = DarkOnSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        // 4. Continue Listening (Carousel)
        item {
            Column(modifier = Modifier.padding(top = 10.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = null,
                            tint = OrangePrimaryContainer,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Continue Listening",
                            color = DarkOnSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "View All (4)",
                        color = OrangePrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clickable { EduDxRepository.navigateTo(ScreenRoute.LIBRARY) }
                            .padding(4.dp)
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        ContinueListeningCard(
                            tag = "Modern History",
                            tagColor = CyanSecondary,
                            title = "Revolt of 1857",
                            instructor = "Prof. B. Sen • Ep 5",
                            progressPercent = 65,
                            timeLeft = "8m left",
                            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCPRlhEKkQmrjdwrd2QMaBIi_xPuXyF61UJciaG08nlU7E3OCY1fIUcCEp3fyTQnN9Xzu-au9SaFY8HokXBHOMknSkbdm6RensfT2kmy372DljKiYxTYUAPAH0vErcV6lqgp8KSbPVp-80k38wnnrIwmtne4zrYST0B2F43qKqHR99Sdsf6VQBhxWd4cBhcyzZ1WtU_wOdiJm8RISFz8kSSoh30SHHvcX7mFxo_YHL-4r2Eo7J4LOXPXA"
                        )
                    }
                    item {
                        ContinueListeningCard(
                            tag = "Biology",
                            tagColor = PeriwinkleTertiary,
                            title = "Biomolecules & Genetics",
                            instructor = "Dr. Shweta Rao • Ep 2",
                            progressPercent = 40,
                            timeLeft = "16m left",
                            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCOm7Ot9ZJgJAAok8-u-8qHPsR2kKt2RD19uxAlgKWjRiZIppwXhyh5qtxyaZ-ZUo3LxV2GDvZ4XJXD5s9mHGKNQnX78s4Fd2XM8ga_uTLMxIeHwQMcCuUGNpaUBqoY94MFF4ZR59vX-VR1VtJuoan-x5tMCAFK70Fe2zCNqPRYtaUCzgIirjcJwoO7vxclHI3DTGplDhA4rBwu7FAuhaaDRAsuxJdZXarf_VV6zJoXu43b-2lEzJjwYQ"
                        )
                    }
                    item {
                        ContinueListeningCard(
                            tag = "Chemistry",
                            tagColor = OrangePrimary,
                            title = "Aldehydes & Ketones",
                            instructor = "Er. K. Grover • Ep 7",
                            progressPercent = 85,
                            timeLeft = "3m left",
                            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCThh2kGxBVR-XgkWo3IJ5m3v-wRGoH33GkEA59_v4n39rfLnAumZcrjjtX76H9m25vBRbmESLagFW3e7CZFuZqc-vn8QAz6V5I4wBGouM3rEBzKjtBfwA7Ymx1IkeIeYvU__z2IsNgDqtA77CxR8TOCpMEwTav1fArRwUDxDpJ29CZAWxzh7uhTNni7IDTlKm2zgbZUQOhS9ZcB8zvpz8YsV5deUHPrrJAxLkzIKTIkIgzYtW9sBLoqQ"
                        )
                    }
                }
            }
        }

        // 5. Daily Concept Audio Quiz Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                DarkSurfaceContainerHigh,
                                DarkSurfaceContainer,
                                DarkSurfaceContainerLow
                            )
                        )
                    )
                    .padding(16.dp)
                    .testTag("daily_quiz_card")
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(CyanSecondaryContainer.copy(alpha = 0.25f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = null,
                                tint = CyanSecondary,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "Interactive Daily",
                                    color = CyanSecondary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(CyanSecondaryContainer.copy(alpha = 0.3f))
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                                Text(
                                    text = "⚡ +50 XP",
                                    color = OrangePrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "Daily Concept Audio Quiz",
                                color = DarkOnSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            Text(
                                text = "Reinforce today's physics formulas with 3 rapid audio questions.",
                                color = DarkOnSurfaceVariant,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                tint = DarkOnSurfaceVariant,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(text = "2 Mins • 3 Questions", color = DarkOnSurfaceVariant, fontSize = 12.sp)
                        }

                        Button(
                            onClick = { showQuizDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = CyanSecondary),
                            shape = RoundedCornerShape(50),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("start_quiz_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = CyanOnSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Start Quiz", color = CyanOnSecondary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 6. Trending in Class 12 Science (Vertical 3:4 Cards)
        item {
            Column(modifier = Modifier.padding(top = 6.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.TrendingUp,
                            contentDescription = null,
                            tint = OrangePrimaryContainer,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Trending in Class 12 Science",
                            color = DarkOnSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "Explore",
                        color = OrangePrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clickable { EduDxRepository.navigateTo(ScreenRoute.EXPLORE) }
                            .padding(4.dp)
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        TrendingCard(
                            badge = "#1 Trend",
                            badgeColor = CyanSecondary,
                            episodesCount = "24 EP",
                            playsCount = "38.2k plays",
                            title = "Electromagnetic Induction",
                            instructor = "Dr. R. K. Kashyap",
                            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDR8ndsoYmjsXZt-pmvbN6Z1KsuLbeVcrG5upjO9nldmf7xmVvyWnVXnE2vTMpCbKellHpQOQh5HLT5QIStAog9u8Uc1MChb_mX-h-aMHceyRO4JE3hgSmMhGS2bMW7CFsG2T-jtIgunBYZ_I1F35LhCYD4a_5p0Gc9rH_IrQpxCcbzRUjFaAerYw1qaO8Bp57xwgl-eryfFmS4vHXKcu_RfJ8jNBEetGCvkq_xy86bD02ECkoogUtDkQ",
                            onClick = { EduDxRepository.navigateTo(ScreenRoute.SERIES_DETAIL) }
                        )
                    }
                    item {
                        TrendingCard(
                            badge = "Recommended",
                            badgeColor = PeriwinkleTertiary,
                            episodesCount = "14 EP",
                            playsCount = "24.1k plays",
                            title = "Neural Control & Action",
                            instructor = "Prof. Nalini Pillai",
                            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAJpkAHXCsg9KBCLi7odd43h9_oivnaisuy5slfxXrlGbxW7Nuk5b1Pp2xDlvCjfzkRvKw_X6ofJ_UUOPREwcjVYpmbD3MbAuOWn9mXg25AOyyEwcl6Uf6EbK3KfG3ZItkBrKpuEiVdZ9A1l55OU8NuzL2BM_akMxdCVlldZyxyEP2m10X9nO7AgI9i5LH7SpIznLW-aQyYhg1u_Loo-pyN7OJcNarmdcXxxe532SSqMp8IZh2cF4M1Iw",
                            onClick = { EduDxRepository.navigateTo(ScreenRoute.SERIES_DETAIL) }
                        )
                    }
                    item {
                        TrendingCard(
                            badge = "Popular",
                            badgeColor = OrangePrimary,
                            episodesCount = "11 EP",
                            playsCount = "19.7k plays",
                            title = "Solid State Chemistry",
                            instructor = "Dr. Anand Swaroop",
                            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuC8S7ESQmQZQbk6lXZ1ejREGTasokbSbjQuEtLTchQTq1Z7TfL4mAOQ2XKWEI1dZoV6i7lcccaOZdMDkksHr_-6HQCILGqw9-43wueKAVm-X76R18rTBp9GkdGmb5QDHMSpexdDImf5iOnIyntdVoRRm7OV1Rcs4RK1vSraya8Zp-yKkX-47ggE7cIqV4VbjDRRkhLy2H5w1NDJdqGrJ2jXl6_4Qn1nVosbTK5LR1HNmRuCPzIHxOW6MA",
                            onClick = { EduDxRepository.navigateTo(ScreenRoute.SERIES_DETAIL) }
                        )
                    }
                    item {
                        TrendingCard(
                            badge = "JEE High",
                            badgeColor = CyanSecondary,
                            episodesCount = "20 EP",
                            playsCount = "31.5k plays",
                            title = "Integral Calculus Unleashed",
                            instructor = "Er. Vikram Joshi",
                            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBI5Vywe9I3s9BNSHzfCrxueBMtJX_iZCtMC9GbvK4jjtEQgUlv2jxZmZEDjEM-5ZYL3MlHL2oG_2hXCwyShB4ls88TxFnISo8WDZkrp7cfBZ4BwpIVWisOVFD61TR4T93rUhNtFW4FtUrFMurIHDbHv07nX2nFlI6lufTT4xK8uPiIrx0b6wyL363DP9kIhc9Q_p-Y51866ssVDMkBGibmQzPOpTy0iLNkisqSJLHe77dr4Mavm4mu0A",
                            onClick = { EduDxRepository.navigateTo(ScreenRoute.SERIES_DETAIL) }
                        )
                    }
                }
            }
        }

        // 7. Flash Audio Revision (NEET & JEE)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Speed,
                                contentDescription = null,
                                tint = CyanSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Flash Audio Revision (NEET & JEE)",
                                color = DarkOnSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "5-minute high-yield formula breakdowns",
                            color = DarkOnSurfaceVariant,
                            fontSize = 12.sp
                        )
                    }
                    Text(
                        text = "View All",
                        color = CyanSecondary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clickable { EduDxRepository.navigateTo(ScreenRoute.EXPLORE) }
                            .padding(4.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FlashAudioItem(
                        tag = "JEE • Physics",
                        tagColor = CyanSecondary,
                        duration = "4m 30s",
                        title = "Rotational Dynamics: Moment of Inertia Matrix",
                        instructor = "Dr. Arvind Verma • 12 Formulas",
                        icon = Icons.Default.Bolt,
                        iconTint = OrangePrimaryContainer,
                        onClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) }
                    )
                    FlashAudioItem(
                        tag = "NEET • Biology",
                        tagColor = PeriwinkleTertiary,
                        duration = "5m 15s",
                        title = "Mendelian Genetics Ratios & Pedigree Rapid Review",
                        instructor = "Dr. Shweta Rao • 10 Core Tricks",
                        icon = Icons.Default.Bloodtype,
                        iconTint = CyanSecondary,
                        onClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) }
                    )
                    FlashAudioItem(
                        tag = "JEE • Chemistry",
                        tagColor = OrangePrimary,
                        duration = "6m 00s",
                        title = "Thermodynamics: Hess's Law & Free Energy",
                        instructor = "Er. K. Grover • 8 Shortcuts",
                        icon = Icons.Default.DeviceThermostat,
                        iconTint = PeriwinkleTertiary,
                        onClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) }
                    )
                }
            }
        }
    }

    // Daily Concept Quiz Dialog
    if (showQuizDialog) {
        AlertDialog(
            onDismissRequest = {
                showQuizDialog = false
                quizAnswerSelected = null
                quizSubmitted = false
            },
            containerColor = DarkSurfaceContainerHigh,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Quiz, contentDescription = null, tint = CyanSecondary)
                    Text(text = "Daily Concept Quiz #481", color = DarkOnSurface, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Question: According to Lenz's Law, what does the negative sign in ε = -dΦ/dt represent?",
                        color = DarkOnSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )

                    val options = listOf(
                        "A. Opposition to change in magnetic flux (Energy Conservation)",
                        "B. The decay of resistance over time",
                        "C. Thermal dissipation of power",
                        "D. Earth's magnetic reversal effect"
                    )

                    options.forEachIndexed { idx, opt ->
                        val isSelected = quizAnswerSelected == idx
                        val isCorrect = idx == 0
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = when {
                                quizSubmitted && isCorrect -> CyanSecondaryContainer.copy(alpha = 0.5f)
                                quizSubmitted && isSelected && !isCorrect -> Color(0xFF93000A)
                                isSelected -> OrangePrimaryContainer.copy(alpha = 0.3f)
                                else -> DarkSurfaceContainer
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { if (!quizSubmitted) quizAnswerSelected = idx }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { if (!quizSubmitted) quizAnswerSelected = idx },
                                    colors = RadioButtonDefaults.colors(selectedColor = OrangePrimaryContainer)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = opt,
                                    color = DarkOnSurface,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    if (quizSubmitted) {
                        Text(
                            text = if (quizAnswerSelected == 0) "✅ Correct! +50 XP added to daily streak!" else "❌ Incorrect. Lenz's law indicates induced field opposes flux change.",
                            color = if (quizAnswerSelected == 0) CyanSecondary else Color(0xFFFFB4AB),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            },
            confirmButton = {
                if (!quizSubmitted) {
                    Button(
                        onClick = {
                            if (quizAnswerSelected != null) {
                                quizSubmitted = true
                                if (quizAnswerSelected == 0) {
                                    EduDxRepository.claimStreak()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer)
                    ) {
                        Text("Submit Answer", color = Color.White)
                    }
                } else {
                    Button(
                        onClick = {
                            showQuizDialog = false
                            quizAnswerSelected = null
                            quizSubmitted = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CyanSecondary)
                    ) {
                        Text("Done", color = CyanOnSecondary)
                    }
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showQuizDialog = false
                        quizAnswerSelected = null
                        quizSubmitted = false
                    }
                ) {
                    Text("Close", color = DarkOnSurfaceVariant)
                }
            }
        )
    }
}

@Composable
fun ContinueListeningCard(
    tag: String,
    tagColor: Color,
    title: String,
    instructor: String,
    progressPercent: Int,
    timeLeft: String,
    imageUrl: String
) {
    Card(
        modifier = Modifier
            .width(240.dp)
            .clickable { EduDxRepository.navigateTo(ScreenRoute.PLAYER) }
            .testTag("continue_card_$title"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUrl),
                        contentDescription = title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.35f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = tag.uppercase(), color = tagColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = title,
                        color = DarkOnSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = instructor,
                        color = DarkOnSurfaceVariant,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Progress Bar & Time Left
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "$progressPercent% completed", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                Text(text = timeLeft, color = OrangePrimary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { progressPercent / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .clip(RoundedCornerShape(50)),
                color = OrangePrimaryContainer,
                trackColor = DarkSurfaceContainerHighest,
            )
        }
    }
}

@Composable
fun TrendingCard(
    badge: String,
    badgeColor: Color,
    episodesCount: String,
    playsCount: String,
    title: String,
    instructor: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clickable(onClick = onClick)
            .testTag("trending_card_$title")
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f)
                .clip(RoundedCornerShape(14.dp))
                .shadow(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Scrim
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, DarkBackground.copy(alpha = 0.85f))
                        )
                    )
            )

            // Badge Top Left
            Text(
                text = badge,
                color = badgeColor,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(DarkSurfaceContainerLowest.copy(alpha = 0.85f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )

            // Bottom Count
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            ) {
                Text(
                    text = episodesCount,
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(OrangePrimaryContainer)
                        .padding(horizontal = 4.dp, vertical = 1.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Headset,
                        contentDescription = null,
                        tint = CyanSecondary,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(text = playsCount, color = DarkOnSurface, fontSize = 11.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            color = DarkOnSurface,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = instructor,
            color = DarkOnSurfaceVariant,
            fontSize = 11.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun FlashAudioItem(
    tag: String,
    tagColor: Color,
    duration: String,
    title: String,
    instructor: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = DarkSurfaceContainerHigh,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("flash_item_$title")
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(DarkSurfaceContainerHighest),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(22.dp))
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = tag, color = tagColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text(text = duration, color = DarkOnSurfaceVariant, fontSize = 10.sp)
                    }
                    Text(
                        text = title,
                        color = DarkOnSurface,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = instructor,
                        color = DarkOnSurfaceVariant,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(DarkSurfaceContainerHighest)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = OrangePrimaryContainer,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
