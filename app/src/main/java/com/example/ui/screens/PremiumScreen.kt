package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import kotlinx.coroutines.delay

@Composable
fun PremiumScreen(
    onBackClick: () -> Unit = { EduDxRepository.navigateBack() },
    modifier: Modifier = Modifier
) {
    var isAnnual by remember { mutableStateOf(false) }
    var isUpgraded by remember { mutableStateOf(false) }
    var isProcessing by remember { mutableStateOf(false) }

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
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(DarkSurfaceContainerHigh)
                            .testTag("premium_back_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = DarkOnSurface,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(50),
                        color = OrangePrimaryContainer.copy(alpha = 0.2f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, OrangePrimaryContainer.copy(alpha = 0.4f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.WorkspacePremium, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(14.dp))
                            Text("EDUDX PRO", color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 2. Hero Banner
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerHigh),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = DarkSurfaceContainerHighest,
                        modifier = Modifier.padding(bottom = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.WorkspacePremium, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(14.dp))
                            Text(text = "EDUDX PRO ACCESS", color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        }
                    }

                    Text(
                        text = "Unlock Unlimited Masterclasses & AI Studio",
                        color = DarkOnSurface,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 26.sp
                    )

                    Text(
                        text = "Immerse yourself in 320kbps Spatial Audio, instant textbook-to-podcast synthesis, and live AI tutor solving.",
                        color = DarkOnSurfaceVariant,
                        fontSize = 12.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
                    )

                    // Audio Waveform Graphic
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.height(24.dp)
                    ) {
                        listOf(8, 14, 22, 28, 20, 12, 6).forEachIndexed { idx, h ->
                            Box(
                                modifier = Modifier
                                    .width(3.dp)
                                    .height(h.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(if (idx == 3) CyanSecondary else OrangePrimaryContainer)
                            )
                        }
                    }
                }
            }
        }

        // 3. Billing Switcher (Monthly vs Annual)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = DarkSurfaceContainerHighest,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(if (!isAnnual) OrangePrimaryContainer else Color.Transparent)
                                .clickable { isAnnual = false }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "Monthly",
                                color = if (!isAnnual) Color.White else DarkOnSurfaceVariant,
                                fontSize = 12.sp,
                                fontWeight = if (!isAnnual) FontWeight.Bold else FontWeight.Medium
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(if (isAnnual) OrangePrimaryContainer else Color.Transparent)
                                .clickable { isAnnual = true }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "Annual",
                                    color = if (isAnnual) Color.White else DarkOnSurfaceVariant,
                                    fontSize = 12.sp,
                                    fontWeight = if (isAnnual) FontWeight.Bold else FontWeight.Medium
                                )
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = CyanSecondaryContainer
                                ) {
                                    Text(
                                        text = "SAVE 40%",
                                        color = Color(0xFF003732),
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Annual plans include 2 months free + priority cloud TTS",
                    color = CyanSecondary,
                    fontSize = 11.sp
                )
            }
        }

        // 4. Pro Tier Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer),
                border = androidx.compose.foundation.BorderStroke(1.dp, OrangePrimaryContainer.copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(OrangePrimaryContainer.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(20.dp))
                            }
                            Column {
                                Text(text = "EduDX Pro", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                Text(text = "Peak Exam Performance", color = CyanSecondary, fontSize = 11.sp)
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(50),
                            color = OrangePrimaryContainer
                        ) {
                            Text(
                                text = "MOST POPULAR",
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = if (isAnnual) "₹208" else "₹299",
                            color = DarkOnSurface,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "/month",
                            color = DarkOnSurfaceVariant,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
                        )
                        if (isAnnual) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = CyanSecondary.copy(alpha = 0.15f),
                                modifier = Modifier.padding(bottom = 6.dp, start = 8.dp)
                            ) {
                                Text(
                                    text = "Billed ₹2,499 yearly",
                                    color = CyanSecondary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        ProFeatureRow("Unlimited offline downloads & spatial play")
                        ProFeatureRow("320kbps Ultra-HD dynamic spatial audio")
                        ProFeatureRow("Unlimited AI Voice doubt resolution tutor")
                        ProFeatureRow("Unlimited PDF Synthesis notes to audio")
                        ProFeatureRow("Exclusive NCERT, JEE & NEET Ranker master series")
                        ProFeatureRow("Completely ad-free immersive study flow")
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            if (!isUpgraded) {
                                isProcessing = true
                                EduDxRepository.showToast("Activating EduDX Pro subscription...")
                                isProcessing = false
                                isUpgraded = true
                            } else {
                                EduDxRepository.showToast("EduDX Pro is already active!")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isUpgraded) CyanSecondary else OrangePrimaryContainer
                        ),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("upgrade_pro_cta_btn")
                    ) {
                        Icon(
                            imageVector = if (isUpgraded) Icons.Default.CheckCircle else Icons.Default.Bolt,
                            contentDescription = null,
                            tint = if (isUpgraded) Color(0xFF003732) else Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isUpgraded) "Pro Plan Active" else if (isAnnual) "Upgrade to Pro • ₹2,499/yr" else "Upgrade to Pro • ₹299/mo",
                            color = if (isUpgraded) Color(0xFF003732) else Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "7-day refund guarantee • Cancel anytime", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                    }
                }
            }
        }

        // 5. Free Plan Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerLow),
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
                            Text(text = "Starter Basic", color = DarkOnSurface, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Daily syllabus preview", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                        }
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = DarkSurfaceContainerHighest
                        ) {
                            Text(
                                text = "Current Plan",
                                color = DarkOnSurfaceVariant,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(text = "₹0", color = DarkOnSurface, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(text = "/forever", color = DarkOnSurfaceVariant, fontSize = 12.sp, modifier = Modifier.padding(bottom = 2.dp, start = 4.dp))
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        FreeFeatureRow("3 audio masterclass episodes/day")
                        FreeFeatureRow("Standard 64kbps audio quality")
                        FreeFeatureRow("5 AI voice questions per day")
                        FreeFeatureRow("1 PDF synthesis/week (max 5 pages)")
                    }
                }
            }
        }

        // 6. Limits Comparison Matrix
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                Text(text = "Allowance & Limits", color = DarkOnSurface, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = DarkSurfaceContainerLow,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "FEATURE", color = DarkOnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text(text = "FREE TIER", color = DarkOnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text(text = "PRO TIER", color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        MatrixLimitRow("AI Voice Minutes", "15 mins / mo", "Unlimited")
                        MatrixLimitRow("PDF Synthesis", "1 doc / week", "Unlimited")
                        MatrixLimitRow("Audio Streaming", "64 kbps standard", "320k Spatial 48kHz")
                        MatrixLimitRow("Offline Caching", "Disabled", "Full Device Sync")
                        MatrixLimitRow("AI Voice Engine", "Shared Queue", "Priority Ultra-TTS")
                    }
                }
            }
        }

        // 7. Student Community Proof
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer),
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
                        Text(text = "Loved by 42,000+ Aspirants", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                            Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(14.dp))
                            Text(text = "4.9/5", color = OrangePrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Testimonial 1
                        Surface(shape = RoundedCornerShape(10.dp), color = DarkSurfaceContainerHigh, modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(text = "Aarav M. (AIR 84)", color = DarkOnSurface, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Text(text = "\"The audio summary converted my 60-page Botany PDF into an easy commute podcast.\"", color = DarkOnSurfaceVariant, fontSize = 10.sp, lineHeight = 14.sp, modifier = Modifier.padding(top = 2.dp))
                            }
                        }

                        // Testimonial 2
                        Surface(shape = RoundedCornerShape(10.dp), color = DarkSurfaceContainerHigh, modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(text = "Pooja S. (JEE '24)", color = DarkOnSurface, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Text(text = "\"AI voice doubt solver helped me grasp Faraday laws while walking in the park.\"", color = DarkOnSurfaceVariant, fontSize = 10.sp, lineHeight = 14.sp, modifier = Modifier.padding(top = 2.dp))
                            }
                        }
                    }
                }
            }
        }

        // 8. Past Transactions
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(text = "Past Transactions", color = DarkOnSurface, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))

                Surface(shape = RoundedCornerShape(10.dp), color = DarkSurfaceContainerLow, modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Icon(imageVector = Icons.Default.ReceiptLong, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(20.dp))
                            Column {
                                Text(text = "Welcome Free Onboarding", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                Text(text = "Sep 14, 2024 • ID #EDX-8821", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(text = "₹0", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            IconButton(onClick = { EduDxRepository.showToast("Receipt #EDX-8821 downloaded") }, modifier = Modifier.size(28.dp)) {
                                Icon(imageVector = Icons.Default.Download, contentDescription = "Download", tint = CyanSecondary, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProFeatureRow(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(
            modifier = Modifier
                .size(18.dp)
                .clip(CircleShape)
                .background(CyanSecondary.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(12.dp))
        }
        Text(text = text, color = DarkOnSurface, fontSize = 12.sp)
    }
}

@Composable
private fun FreeFeatureRow(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(imageVector = Icons.Default.Done, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(16.dp))
        Text(text = text, color = DarkOnSurfaceVariant, fontSize = 12.sp)
    }
}

@Composable
private fun MatrixLimitRow(feature: String, free: String, pro: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = feature, color = DarkOnSurface, fontSize = 11.sp, modifier = Modifier.weight(1.2f))
        Text(text = free, color = DarkOnSurfaceVariant, fontSize = 10.sp, modifier = Modifier.weight(1f))
        Text(text = pro, color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.2f))
    }
}
