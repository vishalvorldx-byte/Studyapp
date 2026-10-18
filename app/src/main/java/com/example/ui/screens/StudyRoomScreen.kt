package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun StudyRoomScreen(
    onBackClick: () -> Unit = { EduDxRepository.navigateBack() },
    modifier: Modifier = Modifier
) {
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
                            text = "Study Room",
                            color = DarkOnSurface,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconButton(
                            onClick = { EduDxRepository.navigateTo(ScreenRoute.ANALYTICS) },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(DarkSurfaceContainer)
                        ) {
                            Icon(imageVector = Icons.Default.Equalizer, contentDescription = "Analytics", tint = CyanSecondary, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }
        }

        // 2. Personal Study Room Header Sub-bar & Goal Ring
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(DarkSurfaceContainerLow)
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(modifier = Modifier.size(52.dp)) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBqx_hB95-LEDkF8Ud6v9fYa2eK2N6ZlfoN4vifABpqHtvjaLXgGWG3_jG7MeWGski4hWeTx7B-OaQObgIXC3LgbprWuqcN0bNZld3wvG7_wDBSpoWC-5AX6L1SBgA-VddXSP_W0htXMXuxyi1eCo2aOTBcoiuhTN4oUmYacT8W2pH2rcVdfzmLEOa9BTS7FlvQKw-QwRIWW9_f7pOF-nO2I0Vk4dhrif88Cf3DwvW2r24Cx8GPdezlxQ"
                                ),
                                contentDescription = "Student Avatar",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .align(Alignment.BottomEnd)
                                    .clip(CircleShape)
                                    .background(CyanSecondary)
                                    .border(2.dp, DarkBackground, CircleShape)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "Personal Study Room",
                                    color = DarkOnSurface,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Icon(imageVector = Icons.Default.WorkspacePremium, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(18.dp))
                            }
                            Text(
                                text = "Arjun K. • Class 12 Science (CBSE Prep)",
                                color = DarkOnSurfaceVariant,
                                fontSize = 11.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    // Goal Ring
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(DarkSurfaceContainer)
                            .clickable { EduDxRepository.navigateTo(ScreenRoute.ANALYTICS) }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Box(modifier = Modifier.size(36.dp), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                progress = { 0.70f },
                                modifier = Modifier.fillMaxSize(),
                                color = OrangePrimaryContainer,
                                trackColor = DarkSurfaceContainerHighest,
                                strokeWidth = 3.dp
                            )
                            Text(text = "70%", color = DarkOnSurface, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(text = "Daily Goal", color = DarkOnSurface, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(12.dp))
                            }
                            Text(text = "42 / 60m", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                        }
                    }
                }
            }
        }

        // 3. Quick Studio Actions (2x2 Grid)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Quick Studio Actions", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "AI Pipeline Active",
                        color = CyanSecondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(CyanSecondary.copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Row 1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Action 1: Upload Notes
                    ActionSquareCard(
                        icon = Icons.Default.UploadFile,
                        iconColor = Color.White,
                        iconBg = OrangePrimaryContainer,
                        badge = "New AI Parser",
                        badgeColor = OrangePrimaryContainer,
                        title = "Upload Notes / PDF",
                        subtitle = "Sync OCR & formulas",
                        hasGlow = true,
                        onClick = { EduDxRepository.navigateTo(ScreenRoute.PDF_UPLOAD) },
                        modifier = Modifier.weight(1f)
                    )

                    // Action 2: Generate Audio
                    ActionSquareCard(
                        icon = Icons.Default.Headphones,
                        iconColor = CyanSecondary,
                        iconBg = DarkSurfaceContainerHighest,
                        badge = "TTS Studio",
                        badgeColor = CyanSecondary,
                        title = "Generate Audio",
                        subtitle = "Convert notes to podcasts",
                        hasGlow = false,
                        onClick = { EduDxRepository.navigateTo(ScreenRoute.AUDIO_STUDIO) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Row 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Action 3: Daily Flashcards
                    ActionSquareCard(
                        icon = Icons.Default.Style,
                        iconColor = OrangePrimary,
                        iconBg = DarkSurfaceContainerHighest,
                        badge = "24 cards due",
                        badgeColor = OrangePrimary,
                        title = "Daily Flashcards",
                        subtitle = "Spaced repetition queue",
                        hasGlow = false,
                        onClick = { EduDxRepository.navigateTo(ScreenRoute.FLASHCARDS) },
                        modifier = Modifier.weight(1f)
                    )

                    // Action 4: Practice Quiz
                    ActionSquareCard(
                        icon = Icons.Default.Quiz,
                        iconColor = PeriwinkleTertiary,
                        iconBg = DarkSurfaceContainerHighest,
                        badge = "✓ Ready",
                        badgeColor = PeriwinkleTertiary,
                        title = "Practice Quiz",
                        subtitle = "Adaptive 10-question set",
                        hasGlow = false,
                        onClick = { EduDxRepository.navigateTo(ScreenRoute.QUIZ_HUB) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // 4. Recent AI Notes & Summaries
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Recent AI Notes & Summaries", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Auto-structured syllabi with synthesized lectures", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                    }
                    Text(text = "View All", color = OrangePrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Note Card 1
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .testTag("note_card_emi")
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(DarkSurfaceContainerHighest),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.PictureAsPdf, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(24.dp))
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Electromagnetic Induction & Faraday's Laws.pdf",
                                    color = DarkOnSurface,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(text = "Uploaded 2h ago • 14 pages", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(CyanSecondaryContainer.copy(alpha = 0.2f))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(text = "8 Key Concepts", color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        // Tags
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            listOf("#Physics", "#Class12", "#Electromagnetism").forEach { tag ->
                                Text(
                                    text = tag,
                                    color = DarkOnSurfaceVariant,
                                    fontSize = 10.sp,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(DarkSurfaceContainerHighest)
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }

                        // Action Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { EduDxRepository.navigateTo(ScreenRoute.AI_NOTE_SUMMARY) },
                                colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                                shape = RoundedCornerShape(50),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Icon(imageVector = Icons.Default.MenuBook, contentDescription = null, tint = DarkOnSurface, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("View Summary", color = DarkOnSurface, fontSize = 11.sp)
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                                colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                                shape = RoundedCornerShape(50),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Listen Audio Lesson (12m)", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // 5. Active Study Revision Decks (Horizontal Carousel)
        item {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Active Study Revision Decks", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Mastery progression powered by Leitner algorithms", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                    }
                    Text(text = "Swipe", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Deck 1
                    item {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer),
                            modifier = Modifier.width(260.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Physics",
                                        color = OrangePrimaryContainer,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(50))
                                            .background(OrangePrimaryContainer.copy(alpha = 0.2f))
                                            .padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                    Text(text = "85% Mastery", color = CyanSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }

                                Text(
                                    text = "Physics High-Yield Formulas",
                                    color = DarkOnSurface,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                Text(text = "38 Total Cards • Optics & Wave Mechanics", color = DarkOnSurfaceVariant, fontSize = 10.sp)

                                Spacer(modifier = Modifier.height(10.dp))
                                LinearProgressIndicator(
                                    progress = { 0.85f },
                                    modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(50)),
                                    color = OrangePrimaryContainer,
                                    trackColor = DarkSurfaceContainerHighest,
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Button(
                                    onClick = { EduDxRepository.navigateTo(ScreenRoute.FLASHCARDS) },
                                    colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                                    shape = RoundedCornerShape(50),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(imageVector = Icons.Default.RestartAlt, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Review Now", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    // Deck 2
                    item {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer),
                            modifier = Modifier.width(260.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Chemistry",
                                        color = CyanSecondary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(50))
                                            .background(CyanSecondaryContainer.copy(alpha = 0.2f))
                                            .padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                    Text(text = "60% Mastery", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                                }

                                Text(
                                    text = "Organic Chemistry Reagents",
                                    color = DarkOnSurface,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                Text(text = "25 Total Cards • Reductions & Reagents", color = DarkOnSurfaceVariant, fontSize = 10.sp)

                                Spacer(modifier = Modifier.height(10.dp))
                                LinearProgressIndicator(
                                    progress = { 0.60f },
                                    modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(50)),
                                    color = CyanSecondary,
                                    trackColor = DarkSurfaceContainerHighest,
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Button(
                                    onClick = { EduDxRepository.navigateTo(ScreenRoute.FLASHCARDS) },
                                    colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                                    shape = RoundedCornerShape(50),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = DarkOnSurface, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Continue Deck", color = DarkOnSurface, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // 6. Connected Flow CTA Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(DarkSurfaceContainerHigh, DarkSurfaceContainer, DarkSurfaceContainerLow)
                        )
                    )
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(OrangePrimaryContainer.copy(alpha = 0.2f))
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Podcasts, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(14.dp))
                        Text(text = "Audio-First Learning Engine", color = OrangePrimaryContainer, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Turn any PDF into conversational podcast lessons.",
                        color = DarkOnSurface,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 24.sp
                    )
                    Text(
                        text = "Upload your textbook PDF, lecture slides, or handwritten notebook pages to instantly generate structured, podcast-style audio lessons for commute & revision.",
                        color = DarkOnSurfaceVariant,
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { EduDxRepository.navigateTo(ScreenRoute.PDF_UPLOAD) },
                        colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                    ) {
                        Icon(imageVector = Icons.Default.CloudUpload, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Upload Document Now", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun ActionSquareCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    iconBg: Color,
    badge: String,
    badgeColor: Color,
    title: String,
    subtitle: String,
    hasGlow: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer),
        modifier = modifier
            .clickable(onClick = onClick)
            .then(
                if (hasGlow) Modifier.border(1.dp, OrangePrimaryContainer.copy(alpha = 0.35f), RoundedCornerShape(14.dp))
                else Modifier
            )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
                }

                Text(
                    text = badge,
                    color = badgeColor,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(badgeColor.copy(alpha = 0.15f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = title,
                color = DarkOnSurface,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtitle,
                color = DarkOnSurfaceVariant,
                fontSize = 10.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
