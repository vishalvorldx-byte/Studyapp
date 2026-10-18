package com.example.ui.screens

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.data.EduDxRepository
import com.example.model.EpisodeItem
import com.example.model.EpisodeStatus
import com.example.model.ScreenRoute
import com.example.ui.theme.*

@Composable
fun SeriesDetailScreen(
    onBackClick: () -> Unit = { EduDxRepository.navigateBack() },
    modifier: Modifier = Modifier
) {
    val track by EduDxRepository.currentTrack.collectAsState()
    var isSavedCourse by remember { mutableStateOf(false) }
    var isDownloadedAll by remember { mutableStateOf(false) }

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
                                .testTag("series_back_btn")
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DarkOnSurface)
                        }
                        Text(
                            text = "Series",
                            color = DarkOnSurface,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        IconButton(
                            onClick = { EduDxRepository.showToast("Series link copied to clipboard") },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(DarkSurfaceContainer)
                        ) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = DarkOnSurfaceVariant, modifier = Modifier.size(20.dp))
                        }
                        Image(
                            painter = rememberAsyncImagePainter(model = "https://lh3.googleusercontent.com/aida-public/AB6AXuD9ITZgBQ45cnglT6S6s5LyoxnLYSATf8VKRg99VLt_fBok7QB_7jGameuFR3iGTEiSoC8dkhBVBeDpQ64wETMxoMnrXTJQJERWQ6uzpgadAaUiq6gQPRF52aIwYz6gdGAtBprxwXxoIWobEGqMzN9c5r4DbfF8pBn-yEDQkjVBn_p9wnw-ixbrrsXW6EG6Xgl0bFKAzxiKjBA37OX3nEyB7kycdoQfRQ9ig7s6IqWIx3XcxSaIt1tG8Q"),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        // 2. Artwork & Series Title Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Floating Artwork Card
                Box(
                    modifier = Modifier
                        .size(220.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .shadow(20.dp, RoundedCornerShape(18.dp))
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = "https://lh3.googleusercontent.com/aida-public/AB6AXuDb0E9oIAM2AU3MWYYffOd2wRBzThIuPsYWBVJnJpunPatr9jcsi0JpGc_cObG1Ykel_8JwiqOJ_LFef9hQJEBESnG0rBcHUNXO8XI6gg53DbVCt16ii66vvtr_Wz5Q70LFUjA0oVsPI7OULhwzBUtwJudAQ-QkY09E4pSoEKoIx-Q0F1n0d_8wHgT3dtVmD-tLobtbz2udyK6u-wWHuPN2SKxKMDxjXUjzmzG0F7wVZOCk6z5fL9Ccdw"),
                        contentDescription = "Class 12 Physics Electromagnetism",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.Black.copy(alpha = 0.3f), Color.Transparent, DarkBackground.copy(alpha = 0.85f))
                                )
                            )
                    )

                    // Tag Top
                    Text(
                        text = "NCERT & JEE Advanced",
                        color = OrangePrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(10.dp)
                            .clip(RoundedCornerShape(50))
                            .background(DarkSurfaceContainerLowest.copy(alpha = 0.85f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    )

                    // Bottom info
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomStart)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Audiobook • 16 Tracks",
                            color = DarkOnSurfaceVariant,
                            fontSize = 11.sp,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(DarkSurfaceContainerHigh.copy(alpha = 0.8f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(OrangePrimaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Equalizer, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Title & Details
                Text(
                    text = "Class 12 Physics: Electromagnetism & AC",
                    color = DarkOnSurface,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Prof. Rajesh Sharma • Ex-IIT Delhi • Physics Faculty",
                    color = CyanSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Metadata Pills
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(DarkSurfaceContainerLow)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = "4.9", color = DarkOnSurface, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text(text = " (4,280)", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(DarkSurfaceContainerLow)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = "4h 45m", color = DarkOnSurface, fontSize = 11.sp)
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(DarkSurfaceContainerLow)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Update, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = "2025 Syllabus", color = DarkOnSurface, fontSize = 11.sp)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Primary Resume CTA
                Button(
                    onClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                    colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("series_resume_btn")
                ) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Resume Episode 3 (14:28)", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Secondary Action Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            isSavedCourse = !isSavedCourse
                            EduDxRepository.showToast(if (isSavedCourse) "Course saved to your library" else "Course removed from library")
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = DarkSurfaceContainerHigh),
                        border = null,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = if (isSavedCourse) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = null,
                            tint = if (isSavedCourse) OrangePrimary else DarkOnSurface,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = if (isSavedCourse) "Saved" else "Save Course", color = DarkOnSurface, fontSize = 12.sp)
                    }

                    OutlinedButton(
                        onClick = {
                            isDownloadedAll = true
                            EduDxRepository.showToast("All 16 episodes queued for offline download")
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = DarkSurfaceContainerHigh),
                        border = null,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = if (isDownloadedAll) Icons.Default.DownloadDone else Icons.Default.DownloadForOffline,
                            contentDescription = null,
                            tint = if (isDownloadedAll) CyanSecondary else DarkOnSurface,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = if (isDownloadedAll) "Saved 210MB" else "210 MB", color = DarkOnSurface, fontSize = 12.sp)
                    }

                    OutlinedButton(
                        onClick = { EduDxRepository.showToast("Shared series link with peers") },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = DarkSurfaceContainerHigh),
                        border = null,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = DarkOnSurface, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Share", color = DarkOnSurface, fontSize = 12.sp)
                    }
                }
            }
        }

        // 3. Progress Card
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
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(16.dp))
                            Text(text = "Overall Progress", color = CyanSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(text = "2 of 16 Complete (35%)", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { 0.35f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(50)),
                        color = OrangePrimaryContainer,
                        trackColor = DarkSurfaceContainerHighest,
                    )
                }
            }
        }

        // 4. Course Blueprint & Mastery Goals
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(imageVector = Icons.Default.Lightbulb, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(20.dp))
                        Text(text = "Course Blueprint & Mastery", color = DarkOnSurface, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Specially mastered for high-yield acoustic comprehension. Deconstruct conceptual hurdles in electrodynamics, time-varying fields, and transformer impedances through intuitive analogies and formula derivations.",
                        color = DarkOnSurfaceVariant,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    val goals = listOf(
                        "Master Vector cross-products in Biot-Savart and Lorentz Force interactions.",
                        "Visualise Lenz's law through kinetic energy dissipation & eddy damping.",
                        "Solve LCR Phasor Diagrams and resonant frequencies in under 90 seconds."
                    )
                    goals.forEach { g ->
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(vertical = 3.dp)
                        ) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(16.dp))
                            Text(text = g, color = DarkOnSurface, fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = DarkSurfaceContainerHigh,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(imageVector = Icons.Default.Psychology, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(18.dp))
                                Text(text = "Have doubt on displacement current?", color = DarkOnSurface, fontSize = 12.sp)
                            }
                            Button(
                                onClick = { EduDxRepository.navigateTo(ScreenRoute.AI_TUTOR) },
                                colors = ButtonDefaults.buttonColors(containerColor = CyanSecondaryContainer),
                                shape = RoundedCornerShape(50),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text("Ask AI Tutor", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // 5. Syllabus Modules (Episodes list)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Syllabus Modules", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = "6 of 16 modules published for Term 2", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { EduDxRepository.showToast("Sorted syllabus modules") }
                    ) {
                        Icon(imageVector = Icons.Default.FilterList, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = "Sort", color = OrangePrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                EduDxRepository.seriesEpisodes.forEach { ep ->
                    EpisodeModuleCard(
                        episode = ep,
                        isPlaying = ep.status == EpisodeStatus.PLAYING,
                        onClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) }
                    )
                }
            }
        }

        // 6. Peer Reviews Section
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Peer Reviews", color = DarkOnSurface, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Rated 4.9 by aspirants across India", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { EduDxRepository.showToast("Opening 4.2k student reviews") }
                        ) {
                            Text(text = "See all (4.2k)", color = OrangePrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = DarkSurfaceContainerLow,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Image(
                                        painter = rememberAsyncImagePainter(model = "https://lh3.googleusercontent.com/aida-public/AB6AXuAUMyY59qM3UIdZxRqrABS-sGAo56vxewchIGIEdi9tnpU0wltCO0DvPhjtWOgSvM0_3I4BjEVfmZIH1q2dsEX2cDZcpGaaypZx-FdNFSkIUcJnxwL4kIfX6Np72zHJ-VpEZ-qJ1YimSwkzN9sZEIAiN--yCtT0KFZSpRMHnt7xSCFhEf0EO3v9A4MMasC8M2LZ-jITPUKOYpAPcYWtjvERINrgJ-1BOg5THoW24HN0suxNTxdkbIGzkw"),
                                        contentDescription = "Aarav Kulkarni",
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                    Column {
                                        Text(text = "Aarav Kulkarni", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                        Text(text = "AIR 412 JEE 2024 Aspirant", color = CyanSecondary, fontSize = 10.sp)
                                    }
                                }

                                Row {
                                    repeat(5) {
                                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(12.dp))
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "“Listening to episode 3 while commuting completely fixed my intuition on Lenz's rule minus sign. Prof. Rajesh makes AC Phasor diagrams sound so obvious.”",
                                color = DarkOnSurfaceVariant,
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }

        // 7. Related Series Carousel
        item {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Related Series", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Browse All", color = OrangePrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        RelatedSeriesCard(
                            title = "Wave Optics & Huygens",
                            instructor = "Prof. Sneha Deshmukh",
                            episodesCount = "12 Episodes",
                            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuA2IemzsWsKFO83Fr_B7cpmdI7JqojbcIh7uGuAca_kqKnExRhZhA6AimKGRyMsvkrQBoL51dQICQwwX3JJJWKnbtFo-Sj26eZVt2fKs7DXpWUAOvlxn7z1J5iKVZZMmc8t0FplkzjQnP3gs1chDVkpGIS0P-Rx9QAoyjEXLcv5tuKpIION3GiTxK6lrnD7bNuQPRiGxDaUlLpqiu-x1pc9CpWJGs69sJsdjvmTWYrvF98Bf2ImC2pYNg",
                            onClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) }
                        )
                    }
                    item {
                        RelatedSeriesCard(
                            title = "Modern Physics: Dual Nature",
                            instructor = "Dr. K. V. Raman",
                            episodesCount = "14 Episodes",
                            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBkN1fhkPMdb3z6yAF4zGgjObamBYbvGqN7AIkkNZuZhwhJWuBolVq01DWm708UyylC282RXioTasO1zYtXnCdgg_siVcI9MxUysq1kIRbSXnOR5WFGSNc82_8G15Dhde7QqzsUoIabOPWCgQgqMzj-oiT8HHleyptwail-3bclrf01cfwFK8CxJfrjQmIXMAPTn-dxbGuv3B3rTVf1J-sNKpM7tGeM-TBY6sJqZqFQM7J8Yu3tjVOZqQ",
                            onClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) }
                        )
                    }
                    item {
                        RelatedSeriesCard(
                            title = "Semiconductor Devices",
                            instructor = "Prof. Rajesh Sharma",
                            episodesCount = "9 Episodes",
                            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCZk8JpqoOEeNcdMnd1NEZVuBuNIb5mm2qjzwIEljpQnaUbDBiR1tDhB1IFd8pexSUfmsFp1wQ2_HqBo8_BEXmyN9UodoS2lGK2gSYyRl7vbVhELaX7tCakD5f3e1ibQd_3jtnOr5y84LbvZOWtPCbMwfEHYQSdgm3uJuZ_KuD-9Ssmbbc9g2BYGdWQbJdWE7sqgRBhOcHb-uDqI3S1eQR1EMFW2Z8Z7VVvXPIo0IVM7rMV1diZPhY0dQ",
                            onClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EpisodeModuleCard(
    episode: EpisodeItem,
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isPlaying) DarkSurfaceContainerHigh else DarkSurfaceContainerLow
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick)
            .testTag("episode_card_${episode.epNumber}")
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            when {
                                isPlaying -> OrangePrimaryContainer.copy(alpha = 0.25f)
                                episode.status == EpisodeStatus.COMPLETED -> DarkSurfaceContainerHighest
                                else -> DarkSurfaceContainer
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when {
                            isPlaying -> Icons.Default.PauseCircle
                            episode.status == EpisodeStatus.COMPLETED -> Icons.Default.CheckCircle
                            else -> Icons.Default.PlayCircle
                        },
                        contentDescription = null,
                        tint = when {
                            isPlaying -> OrangePrimaryContainer
                            episode.status == EpisodeStatus.COMPLETED -> CyanSecondary
                            else -> DarkOnSurfaceVariant
                        },
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        if (isPlaying) {
                            Text(
                                text = "NOW PLAYING",
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(OrangePrimaryContainer)
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                            Text(text = "14:28 / 26:40", color = OrangePrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        } else {
                            Text(
                                text = "EP 0${episode.epNumber}",
                                color = if (episode.status == EpisodeStatus.COMPLETED) CyanSecondary else DarkOnSurfaceVariant,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                            if (episode.status == EpisodeStatus.UP_NEXT) {
                                Text(
                                    text = "UP NEXT",
                                    color = PeriwinkleTertiary,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(DarkSurfaceContainerHighest)
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                            Text(text = "• ${episode.duration}", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                        }
                    }

                    Text(
                        text = episode.title,
                        color = DarkOnSurface,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Text(
                        text = episode.description,
                        color = DarkOnSurfaceVariant,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                IconButton(
                    onClick = { EduDxRepository.showToast("Downloaded episode ${episode.epNumber}") },
                    modifier = Modifier.size(34.dp)
                ) {
                    Icon(
                        imageVector = if (episode.isDownloaded) Icons.Default.DownloadDone else Icons.Default.Download,
                        contentDescription = "Download",
                        tint = if (episode.isDownloaded) CyanSecondary else DarkOnSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            if (isPlaying) {
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { 0.54f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(50)),
                    color = OrangePrimaryContainer,
                    trackColor = DarkSurfaceContainerHighest,
                )
            }
        }
    }
}

@Composable
fun RelatedSeriesCard(
    title: String,
    instructor: String,
    episodesCount: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Text(
                text = episodesCount,
                color = CyanSecondary,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(6.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(DarkSurfaceContainerLowest.copy(alpha = 0.8f))
                    .padding(horizontal = 5.dp, vertical = 2.dp)
            )

            Box(
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.BottomEnd)
                    .padding(6.dp)
                    .clip(CircleShape)
                    .background(OrangePrimaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(text = title, color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = instructor, color = DarkOnSurfaceVariant, fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}
