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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.data.EduDxRepository
import com.example.model.ScreenRoute
import com.example.ui.theme.*

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier
) {
    val activeTab by EduDxRepository.libraryFilterTab.collectAsState()

    val tabs = listOf(
        Pair("series", "Saved Series (8)"),
        Pair("offline", "Offline Downloads (5)"),
        Pair("bookmarks", "Bookmarked Moments (19)"),
        Pair("history", "Listening History (42)")
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentPadding = PaddingValues(bottom = 110.dp)
    ) {
        // 1. Screen Header
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "STUDY VAULT",
                            color = CyanSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "My Learning Library",
                            color = DarkOnSurface,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        IconButton(
                            onClick = { EduDxRepository.navigateTo(ScreenRoute.EXPLORE) },
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(DarkSurfaceContainer)
                        ) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = DarkOnSurfaceVariant, modifier = Modifier.size(18.dp))
                        }
                        IconButton(
                            onClick = { EduDxRepository.showToast("Library Settings") },
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(DarkSurfaceContainer)
                        ) {
                            Icon(imageVector = Icons.Default.Tune, contentDescription = "Tune", tint = DarkOnSurfaceVariant, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }

        // 2. Horizontal Filter Chips Scroller
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                items(tabs) { (id, label) ->
                    val isSelected = activeTab == id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (isSelected) OrangePrimaryContainer else DarkSurfaceContainer)
                            .clickable { EduDxRepository.setLibraryFilterTab(id) }
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                            .testTag("lib_tab_$id")
                    ) {
                        Text(
                            text = label,
                            color = if (isSelected) Color.White else DarkOnSurfaceVariant,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }

        // 3. Offline Storage Management Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(CyanSecondaryContainer.copy(alpha = 0.25f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.CloudDownload, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(16.dp))
                            }
                            Column {
                                Text(text = "Downloaded Storage", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                Text(text = "1.4 GB used of 32 GB available", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(DarkSurfaceContainer)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(CyanSecondary))
                            Text(text = "Ready Offline", color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Storage Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(50))
                            .background(DarkSurfaceContainerHighest)
                    ) {
                        Box(modifier = Modifier.fillMaxWidth(0.12f).fillMaxHeight().background(OrangePrimaryContainer))
                        Box(modifier = Modifier.fillMaxWidth(0.08f).fillMaxHeight().background(CyanSecondary))
                    }
                }
            }
        }

        // 4. Downloaded Courses Shelf
        item {
            Column(modifier = Modifier.padding(top = 10.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = "Downloaded Courses", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = "5 Ready",
                            color = CyanSecondary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(CyanSecondary.copy(alpha = 0.15f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Text(
                        text = "Manage",
                        color = OrangePrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clickable { EduDxRepository.showToast("Opening Storage Manager") }
                            .padding(4.dp)
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(EduDxRepository.downloadedCourses) { course ->
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer),
                            modifier = Modifier
                                .width(190.dp)
                                .clickable { EduDxRepository.navigateTo(ScreenRoute.PLAYER) }
                                .testTag("downloaded_course_${course.id}")
                        ) {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(110.dp)
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(model = course.coverUrl),
                                        contentDescription = course.title,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                                        modifier = Modifier
                                            .padding(6.dp)
                                            .clip(RoundedCornerShape(50))
                                            .background(DarkSurfaceContainerLowest.copy(alpha = 0.85f))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(10.dp))
                                        Text(text = "${course.sizeMb} MB", color = DarkOnSurface, fontSize = 10.sp)
                                    }
                                }

                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(text = course.subjectTag.uppercase(), color = CyanSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                    Text(
                                        text = course.title,
                                        color = DarkOnSurface,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(text = course.episodesText, color = DarkOnSurfaceVariant, fontSize = 10.sp)

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Button(
                                        onClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                                        colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(vertical = 4.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Text("Listen Offline", color = DarkOnSurface, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 5. Active Saved Series List
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Active Saved Series", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(text = "8 Series Total", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                EduDxRepository.activeSavedSeries.forEach { series ->
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = DarkSurfaceContainer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { EduDxRepository.navigateTo(ScreenRoute.SERIES_DETAIL) }
                            .testTag("saved_series_${series.id}")
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(68.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = series.coverUrl),
                                    contentDescription = series.title,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = series.badgeText,
                                    color = OrangePrimary,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(4.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(DarkSurfaceContainerLowest.copy(alpha = 0.9f))
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = series.category, color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    Text(text = "${series.progressPercent}%", color = OrangePrimaryContainer, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                                Text(
                                    text = series.title,
                                    color = DarkOnSurface,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(text = series.subtitle, color = DarkOnSurfaceVariant, fontSize = 10.sp)

                                Spacer(modifier = Modifier.height(6.dp))
                                LinearProgressIndicator(
                                    progress = { series.progressPercent / 100f },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(4.dp)
                                        .clip(RoundedCornerShape(50)),
                                    color = OrangePrimaryContainer,
                                    trackColor = DarkSurfaceContainerHighest,
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(OrangePrimaryContainer)
                                    .clickable { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
            }
        }

        // 6. Bookmarked Notes & Timestamps
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(imageVector = Icons.Default.Bookmark, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(18.dp))
                        Text(text = "Bookmarked Notes & Timestamps", color = DarkOnSurface, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(text = "19 Saved", color = CyanSecondary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                EduDxRepository.bookmarkedNotes.forEach { note ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .testTag("bookmark_${note.id}")
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(DarkSurfaceContainerHigh)
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(12.dp))
                                            Spacer(modifier = Modifier.width(3.dp))
                                            Text(text = note.timestamp, color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        }
                                        Text(text = note.episodeMeta, color = DarkOnSurfaceVariant, fontSize = 10.sp)
                                    }
                                    Text(
                                        text = note.title,
                                        color = DarkOnSurface,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }

                                IconButton(
                                    onClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) },
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(DarkSurfaceContainerHigh)
                                ) {
                                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play at timestamp", tint = OrangePrimaryContainer, modifier = Modifier.size(18.dp))
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Note Callout
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = DarkSurfaceContainerLow,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.EditNote, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(16.dp))
                                    Text(text = note.noteQuote, color = DarkOnSurfaceVariant, fontSize = 11.sp, fontStyle = FontStyle.Italic, lineHeight = 16.sp)
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = note.savedTimeAgo, color = BorderOutline, fontSize = 10.sp)
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.clickable { EduDxRepository.showToast("Shared bookmarked note") }
                                    ) {
                                        Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(12.dp))
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Text(text = "Share", color = OrangePrimary, fontSize = 11.sp)
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.clickable { EduDxRepository.navigateTo(ScreenRoute.AI_TUTOR) }
                                    ) {
                                        Icon(imageVector = Icons.Default.Chat, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(12.dp))
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Text(text = "Ask AI Tutor", color = OrangePrimary, fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 7. Listening History Feed
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Listening History", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "Clear",
                        color = OrangePrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { EduDxRepository.showToast("Listening history cleared") }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Grouped Timeline Items
                val groups = EduDxRepository.listeningHistory.groupBy { it.timeGroup }
                groups.forEach { (groupName, items) ->
                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = groupName, color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            val firstHeader = items.firstOrNull()?.groupListeningTime ?: ""
                            if (firstHeader.isNotEmpty()) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(CyanSecondaryContainer.copy(alpha = 0.2f))
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Headphones, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(text = firstHeader, color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        items.forEach { hist ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = DarkSurfaceContainerLow,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp)
                                    .clickable { EduDxRepository.navigateTo(ScreenRoute.PLAYER) }
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(38.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(DarkSurfaceContainerHighest),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(imageVector = Icons.Default.GraphicEq, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(18.dp))
                                        }
                                        Column {
                                            Text(text = hist.title, color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                            Text(text = hist.subtitle, color = DarkOnSurfaceVariant, fontSize = 10.sp)
                                        }
                                    }

                                    IconButton(
                                        onClick = { EduDxRepository.showToast("Episode options") },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Options", tint = DarkOnSurfaceVariant, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
