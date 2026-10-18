package com.example.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ScreenRoute
import com.example.ui.theme.*

@Composable
fun BottomNavigationBar(
    currentRoute: ScreenRoute,
    onNavigate: (ScreenRoute) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = DarkBackground.copy(alpha = 0.95f),
        shadowElevation = 12.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(64.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            NavItem(
                route = ScreenRoute.HOME,
                label = "Home",
                icon = Icons.Default.Home,
                isSelected = currentRoute == ScreenRoute.HOME,
                onClick = { onNavigate(ScreenRoute.HOME) }
            )
            NavItem(
                route = ScreenRoute.EXPLORE,
                label = "Explore",
                icon = Icons.Default.Explore,
                isSelected = currentRoute == ScreenRoute.EXPLORE,
                onClick = { onNavigate(ScreenRoute.EXPLORE) }
            )
            // AI Tutor special highlighted item
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onNavigate(ScreenRoute.AI_TUTOR) }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .testTag("nav_item_ai_tutor")
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(CyanSecondaryContainer.copy(alpha = 0.25f))
                        .border(1.dp, CyanSecondary.copy(alpha = 0.6f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SmartToy,
                        contentDescription = "AI Tutor",
                        tint = if (currentRoute == ScreenRoute.AI_TUTOR) OrangePrimaryContainer else CyanSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Text(
                    text = "AI Tutor",
                    color = if (currentRoute == ScreenRoute.AI_TUTOR) OrangePrimaryContainer else CyanSecondary,
                    fontSize = 11.sp,
                    fontWeight = if (currentRoute == ScreenRoute.AI_TUTOR) FontWeight.Bold else FontWeight.Medium,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
            NavItem(
                route = ScreenRoute.LIBRARY,
                label = "Library",
                icon = Icons.Default.LocalLibrary,
                isSelected = currentRoute == ScreenRoute.LIBRARY,
                onClick = { onNavigate(ScreenRoute.LIBRARY) }
            )
            NavItem(
                route = ScreenRoute.ACCOUNT_SETTINGS,
                label = "Account",
                icon = Icons.Default.AccountCircle,
                isSelected = currentRoute == ScreenRoute.ACCOUNT_SETTINGS,
                onClick = { onNavigate(ScreenRoute.ACCOUNT_SETTINGS) }
            )
        }
    }
}

@Composable
private fun NavItem(
    route: ScreenRoute,
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .testTag("nav_item_${route.name.lowercase()}")
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) OrangePrimaryContainer else DarkOnSurfaceVariant,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = label,
            color = if (isSelected) OrangePrimaryContainer else DarkOnSurfaceVariant,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}
