package com.example.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.example.data.EduDxRepository
import com.example.model.ScreenRoute
import com.example.ui.theme.*

@Composable
fun TopHeaderBar(
    onSearchClick: () -> Unit = { EduDxRepository.navigateTo(ScreenRoute.EXPLORE) },
    onProfileClick: () -> Unit = { EduDxRepository.navigateTo(ScreenRoute.ACCOUNT_SETTINGS) },
    onStreakClick: () -> Unit = { EduDxRepository.showToast("7-Day Streak active! Keep listening daily.") },
    onNotificationsClick: () -> Unit = { EduDxRepository.navigateTo(ScreenRoute.INBOX) },
    onSettingsClick: () -> Unit = { EduDxRepository.navigateTo(ScreenRoute.ACCOUNT_SETTINGS) },
    onPremiumClick: () -> Unit = { EduDxRepository.navigateTo(ScreenRoute.PREMIUM) }
) {
    var showPortalDialog by remember { mutableStateOf(false) }

    Surface(
        color = DarkBackground.copy(alpha = 0.95f),
        shadowElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Brand Logo & Title & PRO pill
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { EduDxRepository.navigateTo(ScreenRoute.HOME) }
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuCBLH5m8dUS9Wh1JuGCehJgjVpFTccuhJLtsKG1W9wrSVnml0bvGQVWIkiLSy7eYoGM2n0yQtx0ss3xQAFXYJoRA8Oy-P5ejUehsnbOCefCAjqTlYyqi3g2Hq40S3u8p0E657KVhOItKVq-2IE-C6EAnMxm4zdcrwpP-Q37SufQeCDcmMUmYEDMAo_MFCLheJ6zbxNq6581XxEjyTjxN1nU9y4LeQiNc-I8zD4LWgznBJGzs1Q22rcHXw"
                        ),
                        contentDescription = "EduDX Logo",
                        modifier = Modifier.size(28.dp),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = "EduDX",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.5).sp,
                            color = DarkOnSurface
                        )
                    )
                }

                // PRO badge button
                Surface(
                    shape = RoundedCornerShape(50),
                    color = OrangePrimaryContainer.copy(alpha = 0.18f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, OrangePrimaryContainer.copy(alpha = 0.35f)),
                    modifier = Modifier
                        .clickable(onClick = onPremiumClick)
                        .testTag("header_pro_badge")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.WorkspacePremium,
                            contentDescription = "PRO",
                            tint = OrangePrimaryContainer,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "PRO",
                            color = OrangePrimary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Right side items: Notifications, Settings, Profile Avatar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Notification Bell with badge
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onNotificationsClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = DarkOnSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                    // Unread badge dot
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = (-8).dp, y = 8.dp)
                            .clip(CircleShape)
                            .background(OrangePrimaryContainer)
                    )
                }

                // Settings icon button
                IconButton(
                    onClick = onSettingsClick,
                    modifier = Modifier.size(38.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = DarkOnSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Profile Avatar with Role Ring & Indicator
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .clickable { showPortalDialog = true }
                        .testTag("header_profile_btn"),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuD9ITZgBQ45cnglT6S6s5LyoxnLYSATf8VKRg99VLt_fBok7QB_7jGameuFR3iGTEiSoC8dkhBVBeDpQ64wETMxoMnrXTJQJERWQ6uzpgadAaUiq6gQPRF52aIwYz6gdGAtBprxwXxoIWobEGqMzN9c5r4DbfF8pBn-yEDQkjVBn_p9wnw-ixbrrsXW6EG6Xgl0bFKAzxiKjBA37OX3nEyB7kycdoQfRQ9ig7s6IqWIx3XcxSaIt1tG8Q"
                        ),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .border(1.dp, CyanSecondary.copy(alpha = 0.5f), CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    // Role pill icon at corner
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(CyanSecondary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = null,
                            tint = Color(0xFF003732),
                            modifier = Modifier.size(8.dp)
                        )
                    }
                }
            }
        }
    }

    // Portal Switcher Dialog
    if (showPortalDialog) {
        Dialog(onDismissRequest = { showPortalDialog = false }) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = DarkSurfaceContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "PORTAL & ROLE SWITCHER",
                                color = OrangePrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Switch EduDX Workspace",
                                color = DarkOnSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        IconButton(
                            onClick = { showPortalDialog = false },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = DarkOnSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        PortalOptionItem(
                            title = "Student Learning App",
                            subtitle = "Class 12 • JEE/Boards • Audio Lessons",
                            icon = Icons.Default.School,
                            iconColor = CyanSecondary,
                            onClick = {
                                showPortalDialog = false
                                EduDxRepository.navigateTo(ScreenRoute.HOME)
                            }
                        )

                        PortalOptionItem(
                            title = "EduDX Ops Central",
                            subtitle = "Infrastructure, Cloud Costs & Realtime Health",
                            icon = Icons.Default.Dns,
                            iconColor = OrangePrimary,
                            onClick = {
                                showPortalDialog = false
                                EduDxRepository.navigateTo(ScreenRoute.ADMIN_OVERVIEW)
                            }
                        )

                        PortalOptionItem(
                            title = "Curriculum & Content Hub",
                            subtitle = "Publish series, audio tracks & inspect VTT",
                            icon = Icons.Default.AccountTree,
                            iconColor = PeriwinkleTertiary,
                            onClick = {
                                showPortalDialog = false
                                EduDxRepository.navigateTo(ScreenRoute.ADMIN_CONTENT)
                            }
                        )

                        PortalOptionItem(
                            title = "User Directory & Moderation",
                            subtitle = "AI answer queue, 48k users & audit logs",
                            icon = Icons.Default.SupervisorAccount,
                            iconColor = CyanSecondary,
                            onClick = {
                                showPortalDialog = false
                                EduDxRepository.navigateTo(ScreenRoute.ADMIN_USERS)
                            }
                        )

                        PortalOptionItem(
                            title = "Faculty Assignments Portal",
                            subtitle = "Mrs. Sunita Sharma • Class 12-A & 12-C",
                            icon = Icons.Default.AssignmentTurnedIn,
                            iconColor = OrangePrimaryContainer,
                            onClick = {
                                showPortalDialog = false
                                EduDxRepository.navigateTo(ScreenRoute.FACULTY_ASSIGNMENTS)
                            }
                        )

                        PortalOptionItem(
                            title = "School & Classrooms Portal",
                            subtitle = "Delhi Public School R.K. Puram • CBSE",
                            icon = Icons.Default.AccountBalance,
                            iconColor = CyanSecondary,
                            onClick = {
                                showPortalDialog = false
                                EduDxRepository.navigateTo(ScreenRoute.SCHOOL_CLASSROOMS)
                            }
                        )

                        PortalOptionItem(
                            title = "EduDX Pro Subscription",
                            subtitle = "Unlock unlimited AI studio & 320kbps audio",
                            icon = Icons.Default.WorkspacePremium,
                            iconColor = OrangePrimary,
                            onClick = {
                                showPortalDialog = false
                                EduDxRepository.navigateTo(ScreenRoute.PREMIUM)
                            }
                        )

                        PortalOptionItem(
                            title = "Account & System Settings",
                            subtitle = "Preferences, dialect, cache & security",
                            icon = Icons.Default.Settings,
                            iconColor = DarkOnSurfaceVariant,
                            onClick = {
                                showPortalDialog = false
                                EduDxRepository.navigateTo(ScreenRoute.ACCOUNT_SETTINGS)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PortalOptionItem(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = DarkSurfaceContainerHigh,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(18.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = DarkOnSurface,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitle,
                    color = DarkOnSurfaceVariant,
                    fontSize = 11.sp,
                    maxLines = 1
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = DarkOnSurfaceVariant,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
