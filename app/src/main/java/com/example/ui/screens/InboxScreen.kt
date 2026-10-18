package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.data.EduDxRepository
import com.example.model.ScreenRoute
import com.example.ui.theme.*

data class NotificationItem(
    val id: String,
    val category: String, // reminders, releases, quizzes, system
    val titleTag: String,
    val timeAgo: String,
    val title: String,
    val description: String,
    val actionLabel: String,
    val actionRoute: ScreenRoute,
    val icon: ImageVector,
    val iconColor: Color,
    val isUnread: Boolean = true
)

@Composable
fun InboxScreen(
    onBackClick: () -> Unit = { EduDxRepository.navigateBack() },
    modifier: Modifier = Modifier
) {
    val initialNotifications = remember {
        listOf(
            NotificationItem(
                id = "n_1",
                category = "releases",
                titleTag = "Fresh Audio Lecture",
                timeAgo = "15m ago",
                title = "Electromagnetic Induction: Ep 4 Motional EMF & Eddy Currents",
                description = "Master the core concepts of rotating coils and counter-torques with Dr. Rao's audio deep-dive.",
                actionLabel = "Listen Now (19m)",
                actionRoute = ScreenRoute.PLAYER,
                icon = Icons.Default.Headphones,
                iconColor = OrangePrimaryContainer,
                isUnread = true
            ),
            NotificationItem(
                id = "n_2",
                category = "quizzes",
                titleTag = "Streak at Risk!",
                timeAgo = "2h ago",
                title = "Protect your 7-Day Chemistry & Physics Sprint",
                description = "Complete your rapid 4-min audio challenge on Lenz's Law before midnight to keep your flame blazing.",
                actionLabel = "Start Quiz (+50 XP)",
                actionRoute = ScreenRoute.QUIZ_HUB,
                icon = Icons.Default.LocalFireDepartment,
                iconColor = OrangePrimaryContainer,
                isUnread = true
            ),
            NotificationItem(
                id = "n_3",
                category = "reminders",
                titleTag = "Audio AI Synthesizer",
                timeAgo = "4h ago",
                title = "Synthesis Ready: Organic Chemistry",
                description = "Your PDF \"Aldehydes & Ketones\" was transformed into an 11-min smart lecture with 15 interactive flashcards.",
                actionLabel = "Open Lesson",
                actionRoute = ScreenRoute.AI_NOTE_SUMMARY,
                icon = Icons.Default.AutoAwesome,
                iconColor = PeriwinkleTertiary,
                isUnread = true
            ),
            NotificationItem(
                id = "n_4",
                category = "reminders",
                titleTag = "Weekly Milestones",
                timeAgo = "Yesterday",
                title = "You clocked 14h 35m of audio study!",
                description = "Phenomenal dedication. You climbed into the Top 8% of all Class 12 Science aspirants this week.",
                actionLabel = "View Analytics",
                actionRoute = ScreenRoute.ANALYTICS,
                icon = Icons.Default.Insights,
                iconColor = CyanSecondary,
                isUnread = true
            )
        )
    }

    var notifications by remember { mutableStateOf(initialNotifications) }
    var selectedCategory by remember { mutableStateOf("all") }
    var showReadToast by remember { mutableStateOf(false) }

    val categories = listOf(
        "all" to "All (${notifications.size})",
        "reminders" to "Reminders",
        "releases" to "New Releases",
        "quizzes" to "Quizzes",
        "system" to "System"
    )

    val unreadCount = notifications.count { it.isUnread }
    val filteredList = notifications.filter {
        selectedCategory == "all" || it.category == selectedCategory
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {
        // 1. Top Header Bar
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
                                .testTag("inbox_back_btn")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = DarkOnSurface,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Inbox",
                                color = DarkOnSurface,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            if (unreadCount > 0) {
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = OrangePrimaryContainer
                                ) {
                                    Text(
                                        text = "$unreadCount New",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }

                    TextButton(
                        onClick = {
                            notifications = notifications.map { it.copy(isUnread = false) }
                            showReadToast = true
                            EduDxRepository.showToast("All notifications marked as read")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.DoneAll,
                            contentDescription = null,
                            tint = OrangePrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Mark all read",
                            color = OrangePrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // 2. Filter Tabs Carousel
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                items(categories) { (key, label) ->
                    val isSelected = selectedCategory == key
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (isSelected) OrangePrimaryContainer else DarkSurfaceContainerLow)
                            .clickable { selectedCategory = key }
                            .padding(horizontal = 14.dp, vertical = 7.dp)
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

        // 3. Section Title
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "UNREAD ACTIVITY",
                    color = OrangePrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Priority Queue",
                    color = DarkOnSurfaceVariant,
                    fontSize = 10.sp
                )
            }
        }

        // 4. Notifications List
        items(filteredList, key = { it.id }) { notif ->
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = if (notif.isUnread) DarkSurfaceContainerLow else DarkSurfaceContainerLowest.copy(alpha = 0.6f),
                shadowElevation = if (notif.isUnread) 4.dp else 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 5.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    // Icon with status dot
                    Box(modifier = Modifier.size(40.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(notif.iconColor.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = notif.icon,
                                contentDescription = null,
                                tint = notif.iconColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        if (notif.isUnread) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .align(Alignment.TopEnd)
                                    .clip(CircleShape)
                                    .background(OrangePrimaryContainer)
                            )
                        }
                    }

                    // Content
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = notif.titleTag,
                                color = notif.iconColor,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = notif.timeAgo,
                                color = DarkOnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }

                        Text(
                            text = notif.title,
                            color = DarkOnSurface,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 2.dp)
                        )

                        Text(
                            text = notif.description,
                            color = DarkOnSurfaceVariant,
                            fontSize = 11.sp,
                            lineHeight = 16.sp,
                            modifier = Modifier.padding(top = 3.dp, bottom = 8.dp)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    notifications = notifications.map {
                                        if (it.id == notif.id) it.copy(isUnread = false) else it
                                    }
                                    EduDxRepository.navigateTo(notif.actionRoute)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = notif.iconColor),
                                shape = RoundedCornerShape(50),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Text(
                                    text = notif.actionLabel,
                                    color = if (notif.iconColor == CyanSecondary) Color(0xFF003732) else Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            IconButton(
                                onClick = {
                                    notifications = notifications.filter { it.id != notif.id }
                                    EduDxRepository.showToast("Notification dismissed")
                                },
                                modifier = Modifier.size(34.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "Dismiss",
                                    tint = DarkOnSurfaceVariant,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // 5. Earlier Activity Section
        item {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "EARLIER ACTIVITY",
                        color = DarkOnSurfaceVariant,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Read Archive",
                        color = DarkOnSurfaceVariant,
                        fontSize = 10.sp
                    )
                }

                // Earlier Item 1
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = DarkSurfaceContainerLowest.copy(alpha = 0.6f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(DarkSurfaceContainerHigh),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Forum, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(18.dp))
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "Doubt Forum", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                                Text(text = "2d ago", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                            }
                            Text(text = "Mentor Aisha resolved your query regarding Eddy Current power loss equations.", color = DarkOnSurface, fontSize = 12.sp)
                        }
                    }
                }

                // Earlier Item 2
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = DarkSurfaceContainerLowest.copy(alpha = 0.6f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(DarkSurfaceContainerHigh),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.SettingsSuggest, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(18.dp))
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "System Update", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                                Text(text = "3d ago", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                            }
                            Text(text = "Scheduled maintenance completed: Neural TTS v3.1 is active with lower synthesis delay.", color = DarkOnSurface, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // 6. Clear History Footer
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                TextButton(
                    onClick = {
                        notifications = emptyList()
                        EduDxRepository.showToast("Notification history cleared")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteSweep,
                        contentDescription = null,
                        tint = DarkOnSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Clear notification history",
                        color = DarkOnSurfaceVariant,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
