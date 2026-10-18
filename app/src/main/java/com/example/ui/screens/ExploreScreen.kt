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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.data.EduDxRepository
import com.example.model.ScreenRoute
import com.example.ui.theme.*

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier
) {
    val searchQuery by EduDxRepository.searchQuery.collectAsState()
    val selectedTime by EduDxRepository.selectedTimeFilter.collectAsState()
    val selectedType by EduDxRepository.selectedTypeFilter.collectAsState()
    var emptyStateManualToggle by remember { mutableStateOf(false) }

    val showEmptyState = emptyStateManualToggle || (searchQuery.isNotEmpty() && searchQuery.contains("xyz", ignoreCase = true))

    val trendingTags = listOf(
        "Organic Chemistry mechanisms",
        "Newtonian mnemonics",
        "UPSC Polity summary",
        "Calculus integration tricks"
    )

    val timeFilters = listOf("All Durations", "< 15 mins", "15–30 mins", "30+ mins")
    val typeFilters = listOf("Concept Bite", "Full Course", "Audio Drama", "Exam Summary")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentPadding = PaddingValues(bottom = 110.dp)
    ) {
        // 1. Search Box & Trending Pills
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                // Search Input
                Surface(
                    shape = RoundedCornerShape(50),
                    color = DarkSurfaceContainerHigh,
                    shadowElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = BorderOutline,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = searchQuery,
                            onValueChange = { EduDxRepository.setSearchQuery(it) },
                            placeholder = {
                                Text(
                                    "Search chapters, topics, formulas...",
                                    color = DarkOnSurfaceVariant.copy(alpha = 0.7f),
                                    fontSize = 13.sp
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedTextColor = DarkOnSurface,
                                unfocusedTextColor = DarkOnSurface
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("global_search_input"),
                            singleLine = true
                        )

                        if (searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = { EduDxRepository.setSearchQuery("") },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Cancel,
                                    contentDescription = "Clear",
                                    tint = DarkOnSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(DarkSurfaceContainer)
                                .clickable {
                                    EduDxRepository.showToast("Voice Search listening...")
                                    EduDxRepository.setSearchQuery("Faraday's Law")
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = "Voice Search",
                                tint = OrangePrimaryContainer,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Trending right now tags
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.TrendingUp,
                        contentDescription = null,
                        tint = OrangePrimaryContainer,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "TRENDING RIGHT NOW",
                        color = DarkOnSurfaceVariant,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(trendingTags) { tag ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(DarkSurfaceContainerLow)
                                .clickable { EduDxRepository.setSearchQuery(tag) }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .testTag("trending_pill_$tag")
                        ) {
                            Text(text = "# ", color = OrangePrimaryContainer, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text(text = tag, color = DarkOnSurface, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // 2. Active Filters Matrix
        item {
            Column(modifier = Modifier.padding(vertical = 6.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Active Filters", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "Reset All",
                        color = OrangePrimaryContainer,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clickable {
                                EduDxRepository.setTimeFilter("All Durations")
                                EduDxRepository.setTypeFilter("Concept Bite")
                                EduDxRepository.setSearchQuery("")
                            }
                            .padding(4.dp)
                    )
                }

                // Time Row
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 3.dp)
                ) {
                    item {
                        Text(text = "TIME", color = BorderOutline, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    items(timeFilters) { filter ->
                        val isSelected = filter == selectedTime
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(if (isSelected) OrangePrimaryContainer else DarkSurfaceContainerHigh)
                                .clickable { EduDxRepository.setTimeFilter(filter) }
                                .padding(horizontal = 12.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = filter,
                                color = if (isSelected) Color.White else DarkOnSurfaceVariant,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }

                // Type Row
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 3.dp)
                ) {
                    item {
                        Text(text = "TYPE", color = BorderOutline, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    items(typeFilters) { filter ->
                        val isSelected = filter == selectedType
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(if (isSelected) CyanSecondaryContainer else DarkSurfaceContainerHigh)
                                .clickable { EduDxRepository.setTypeFilter(filter) }
                                .padding(horizontal = 12.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = filter,
                                color = if (isSelected) Color.White else DarkOnSurfaceVariant,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // If Empty State is active
        if (showEmptyState) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(DarkSurfaceContainerLow)
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(OrangePrimaryContainer.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.TravelExplore,
                                contentDescription = null,
                                tint = OrangePrimaryContainer,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Text(
                            text = "No audio waves found here",
                            color = DarkOnSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "We searched across 4,500+ syllabus lectures and formulas, but couldn't find an exact resonance.",
                            color = DarkOnSurfaceVariant,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "DID YOU MEAN TO EXPLORE?",
                            color = BorderOutline,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            listOf("Kinematics 1D Equations", "Thermodynamics Laws").forEach { chip ->
                                Text(
                                    text = chip,
                                    color = DarkOnSurface,
                                    fontSize = 11.sp,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(DarkSurfaceContainerHighest)
                                        .clickable { EduDxRepository.setSearchQuery(chip) }
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }

                        Button(
                            onClick = { EduDxRepository.navigateTo(ScreenRoute.AI_TUTOR) },
                            colors = ButtonDefaults.buttonColors(containerColor = CyanSecondaryContainer),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.SmartToy, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Ask AI Tutor to generate audio summary", color = Color.White, fontSize = 12.sp)
                        }
                    }
                }
            }
        } else {
            // 3. Top Voice Mentors
            item {
                Column(modifier = Modifier.padding(top = 8.dp)) {
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
                            Icon(imageVector = Icons.Default.Podcasts, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(18.dp))
                            Text(text = "Top Voice Mentors", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(text = "View All", color = DarkOnSurfaceVariant, fontSize = 12.sp)
                    }

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(EduDxRepository.voiceMentors) { mentor ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .width(88.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(DarkSurfaceContainerLow)
                                    .clickable { EduDxRepository.navigateTo(ScreenRoute.SERIES_DETAIL) }
                                    .padding(8.dp)
                                    .testTag("mentor_${mentor.id}")
                            ) {
                                Box(modifier = Modifier.size(54.dp)) {
                                    Image(
                                        painter = rememberAsyncImagePainter(model = mentor.avatarUrl),
                                        contentDescription = mentor.name,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                    if (mentor.verified) {
                                        Box(
                                            modifier = Modifier
                                                .size(16.dp)
                                                .align(Alignment.BottomEnd)
                                                .clip(CircleShape)
                                                .background(CyanSecondaryContainer),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = mentor.name, color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, maxLines = 1)
                                Text(text = mentor.subject, color = CyanSecondary, fontSize = 10.sp, maxLines = 1)
                                Text(text = mentor.plays, color = BorderOutline, fontSize = 9.sp)
                            }
                        }
                    }
                }
            }

            // 4. Mastery Audio Series
            item {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Mastery Audio Series", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Complete NCERT and exam syllabus modules", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                        }
                        Text(
                            text = "34 Series",
                            color = CyanSecondary,
                            fontSize = 11.sp,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(DarkSurfaceContainerHigh)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            MasterySeriesCard(
                                badge = "NCERT Aligned",
                                badgeColor = CyanSecondary,
                                rating = "4.9",
                                subject = "Chemistry • Grade 12",
                                title = "Reaction Pathways & Carbon Mechanisms",
                                subtitle = "18 AI Flashcards",
                                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDBR68TNaqH36NNuVIaRj2YIAcFOrHQNUWIksLsNWKRisB-0k1p9IvIKlr_IguusxKtG1p5oilOKXRIDQCBBkWRTHODNbWzUzBIri2xA1d3mFa8rT_4sqAYEdKfNc71rgSCZ0jPAq8kixwOh-V7_kgpRQKNbQA05DBD0WpMiig4AmV7BSWNshT7QIriIGt-H2QoYoJo5uPnScPDP3r-XqCEnxLMf89meuH3I37-XuRdzNZ9XPT8r0xvpA",
                                onClick = { EduDxRepository.navigateTo(ScreenRoute.SERIES_DETAIL) }
                            )
                        }
                        item {
                            MasterySeriesCard(
                                badge = "UPSC High-Yield",
                                badgeColor = OrangePrimary,
                                rating = "4.8",
                                subject = "Polity • Civil Services",
                                title = "Constitution: Articles 1-51A Decoded",
                                subtitle = "12 Episodes • 4.2h",
                                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAYu4MTco7Uqu3ftv4gL3v3sMsJNTXczNyWeCCLlWrbDtRy51xMVaeHXuB_cIlGMa7-ioXKvz1K4WhXOFfkbIZQ9MyFI4pIDaejf--rRuAoA_vElpoc-YwchTlvx4KbOxxnIa9mvDMxwPXKCoSlGpWkK9Zm40HylUW-tkqQhg7uwJgYriFqsdbwHRSCpoDq5A-hSBm8fD1yOA7ZmYLr2DobAEMkUaYf9dxHYBz1ruGH-Z4ghDuBdJe_KA",
                                onClick = { EduDxRepository.navigateTo(ScreenRoute.SERIES_DETAIL) }
                            )
                        }
                        item {
                            MasterySeriesCard(
                                badge = "Audio Drama Format",
                                badgeColor = CyanSecondary,
                                rating = "5.0",
                                subject = "Mathematics • JEE Core",
                                title = "Calculus Without Pen: Visual Audio",
                                subtitle = "8 Audio Immersion",
                                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCtKcYSZ98nF7FgtgKZZx05aimONmFcfR0_KIUevzQuh6MtMS-4mJSMt1kU8yECIMPQYY3_E_sxzDOrVTkEqrfIJmTLZARssLgYW9Gq-gFYh6kbBvgtekUUWT1taE-60xeMp11a6dYIDrHxrqyjbRoYaHiADt1Bst4UP-g-yoEt13fyA8hoWcYIVICdYxNq9ptmM7aUgyqZWu9lut55fy1vr5PvVAfUOPN3ZcE5ZOqT3xVa1RvuDjdc3Q",
                                onClick = { EduDxRepository.navigateTo(ScreenRoute.SERIES_DETAIL) }
                            )
                        }
                    }
                }
            }

            // 5. Top Matches
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(text = "Top Matches", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text(
                                text = "18 found",
                                color = DarkOnSurfaceVariant,
                                fontSize = 11.sp,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(DarkSurfaceContainerHigh)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { EduDxRepository.showToast("Sorted by relevancy") }
                        ) {
                            Text(text = "Sort: Relevancy", color = DarkOnSurfaceVariant, fontSize = 12.sp)
                            Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        TopMatchEpisodeCard(
                            tag = "Concept Bite",
                            duration = "11m 45s",
                            title = "Faraday's Law & Lenz's Rule Intuition",
                            author = "Dr. Rao • NCERT Class 12",
                            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuACbAsjhoX7MvpubsyTH_i_ririC2-hrdTUR7of27C3eDAe04-fv24EzUQoBPi1CjTS_Ilrg3itafgpFYwOsnKVMUxqSa_erbkT3Cwc1cIU3qzjZlsDOKvfDdk4nVLASmctQK7EUZSTrdnvq0vMkwdSqCDTRzSkgNXkL4a-5SsRTozaZjeG4-kyt-vnIvuxrh6ZfsUHYaUu1TNahO_nkDytdfCF-bE67afaTC0GJbKuXYYDZsm72ooxxg",
                            onClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) }
                        )
                        TopMatchEpisodeCard(
                            tag = "Exam Summary",
                            duration = "18m 20s",
                            title = "Electrophilic Aromatic Substitution Mnemonics",
                            author = "Aanya Sen • Hinglish Audio",
                            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCcoTfhDZ7WTFjxWVjxwNQJiOBmiVombyQOJRMp-HpcqQjIbFDQQyVMjFR37jgsOsuDsMDJeDkwmLIreLbXiM9lqqtF9NGTYPHqDV367e-eDCJgBpFVbFHqSX9QWpOcmeKX4pyB_C7yrMqxTVxKYrzmwnaShCjGT_X60p42cfzZXkGJ1O59KiULh3LzblSFs5FQQfJpU2AMykiiT0hW8NlBaK2mxKHBUaKSNhpm5leC94jJCPR5PAP6rw",
                            onClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) }
                        )
                        TopMatchEpisodeCard(
                            tag = "Full Course",
                            duration = "26m 10s",
                            title = "Fundamental Rights: Article 19 to 21 Breakdown",
                            author = "Vikram Roy • English",
                            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAuKRr0zqK_5K-Lh_WHgniLZw2ErPvhBhXrTKq03nK8PaKmB6IDzQSVBcwFOP7H6klQsZCgaQ7R9tCZ7HN-ZDYScK3GguZOGdw4QER2TMn_De_WduVZEiKYHIeyrRbg67Au7hdkM79vTOTGIfZw6Fz5yWJzQa5Zc-CTt4Xf9vZWsawKZMCub41r3lEXnjGHjbsxWK2Ltq8ocrhCAgUahOJoeUsJ4vKTLzIayE1_5sSjK2-oDIY65AqwHA",
                            onClick = { EduDxRepository.navigateTo(ScreenRoute.PLAYER) }
                        )
                    }
                }
            }
        }

        // 6. Empty State Playground Toggle Button
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "EMPTY STATE PLAYGROUND", color = BorderOutline, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = if (emptyStateManualToggle) "Hide View" else "Toggle View",
                    color = OrangePrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable { emptyStateManualToggle = !emptyStateManualToggle }
                        .padding(4.dp)
                        .testTag("toggle_empty_state_btn")
                )
            }
        }
    }
}

@Composable
fun MasterySeriesCard(
    badge: String,
    badgeColor: Color,
    rating: String,
    subject: String,
    title: String,
    subtitle: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
        modifier = Modifier
            .width(220.dp)
            .clickable(onClick = onClick)
            .testTag("mastery_series_$title")
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
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
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, DarkBackground.copy(alpha = 0.8f))
                            )
                        )
                )

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

                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(OrangePrimaryContainer)
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = rating, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color.White, modifier = Modifier.size(10.dp))
                }
            }

            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = subject.uppercase(), color = badgeColor, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = title,
                    color = DarkOnSurface,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = subtitle, color = BorderOutline, fontSize = 11.sp)
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(OrangePrimaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun TopMatchEpisodeCard(
    tag: String,
    duration: String,
    title: String,
    author: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = DarkSurfaceContainerLow,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("match_card_$title")
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(10.dp))
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
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.Headphones, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = tag,
                        color = CyanSecondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(DarkSurfaceContainerHighest)
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    )
                    Text(text = "• $duration", color = BorderOutline, fontSize = 10.sp)
                }
                Text(
                    text = title,
                    color = DarkOnSurface,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Text(
                    text = author,
                    color = DarkOnSurfaceVariant,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(DarkSurfaceContainerHigh)
            ) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play", tint = OrangePrimaryContainer, modifier = Modifier.size(20.dp))
            }
        }
    }
}
