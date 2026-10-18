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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.data.EduDxRepository
import com.example.model.ScreenRoute
import com.example.ui.theme.*

@Composable
fun AiNoteSummaryScreen(
    onBackClick: () -> Unit = { EduDxRepository.navigateBack() },
    modifier: Modifier = Modifier
) {
    var selectedViewSegment by remember { mutableStateOf("Key Summary") }
    var isProofExpanded by remember { mutableStateOf(false) }
    var isFlashcardFlipped by remember { mutableStateOf(false) }
    var isBookmarked by remember { mutableStateOf(false) }
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    val viewSegments = listOf("Key Summary", "Formulas & Derivations (12)", "Flashcards (4)", "Original PDF Text")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
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
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(DarkSurfaceContainer)
                            ) {
                                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DarkOnSurface)
                            }
                            Text(
                                text = "AI Note Summary",
                                color = DarkOnSurface,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        IconButton(
                            onClick = { EduDxRepository.showToast("Summary shared with study group") },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(DarkSurfaceContainer)
                        ) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = DarkOnSurfaceVariant, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }

            // 2. Metadata Banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(DarkSurfaceContainerHigh)
                        .padding(16.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "AI SYNTHESIZED",
                                    color = CyanSecondary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(CyanSecondaryContainer.copy(alpha = 0.2f))
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                                Text(text = "Physics • Ch. 6", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                            }

                            IconButton(
                                onClick = {
                                    isBookmarked = !isBookmarked
                                    EduDxRepository.showToast(if (isBookmarked) "Saved to Bookmarks" else "Removed bookmark")
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = "Bookmark",
                                    tint = if (isBookmarked) OrangePrimaryContainer else DarkOnSurfaceVariant,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "NCERT Class 12: Electromagnetic Induction",
                            color = DarkOnSurface,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Synthesized from 18-page PDF • 8 Concepts • 12 Formulas • 4 Flashcards",
                            color = DarkOnSurfaceVariant,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                        )

                        Button(
                            onClick = { EduDxRepository.navigateTo(ScreenRoute.AUDIO_STUDIO) },
                            colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Headphones, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Generate Audio Lesson (11 mins)", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            // 3. Segmented View Switcher
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    items(viewSegments) { seg ->
                        val isSelected = seg == selectedViewSegment
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(if (isSelected) OrangePrimaryContainer else DarkSurfaceContainerHigh)
                                .clickable {
                                    selectedViewSegment = seg
                                    if (seg.startsWith("Flashcards")) {
                                        EduDxRepository.navigateTo(ScreenRoute.FLASHCARDS)
                                    }
                                }
                                .padding(horizontal = 14.dp, vertical = 7.dp)
                        ) {
                            Text(
                                text = seg,
                                color = if (isSelected) Color.White else DarkOnSurfaceVariant,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // 4. Executive 3-Minute Briefing
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
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
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(OrangePrimaryContainer.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.Timer, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(16.dp))
                                }
                                Text(text = "Executive 3-Minute Briefing", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            }
                            Text(
                                text = "High Retention",
                                color = DarkOnSurfaceVariant,
                                fontSize = 10.sp,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(DarkSurfaceContainerHigh)
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            BriefingBulletItem(
                                title = "Faraday's Crucial Discovery",
                                desc = "Relative motion between a magnetic field and a conductive circuit induces an electromotive force (EMF), proving electric currents can stem directly from changing magnetic states.",
                                icon = Icons.Default.Explore,
                                iconColor = CyanSecondary
                            )
                            BriefingBulletItem(
                                title = "Rate of Magnetic Flux Change",
                                desc = "The magnitude of induced EMF is strictly proportional to the time rate of change of magnetic flux through the loop (ΔΦ_B / Δt).",
                                icon = Icons.Default.ElectricBolt,
                                iconColor = OrangePrimary
                            )
                            BriefingBulletItem(
                                title = "Lenz's Counter-Action Principle",
                                desc = "The polarity of induced EMF always creates a current whose magnetic field resists the prime change producing it, maintaining fundamental conservation of energy.",
                                icon = Icons.Default.Cached,
                                iconColor = PeriwinkleTertiary
                            )
                            BriefingBulletItem(
                                title = "Motional EMF & Eddy Currents",
                                desc = "Moving a conductor across a uniform perpendicular field generates Ε = B · l · v, while bulk conductor movement yields circulating eddy damping loops.",
                                icon = Icons.Default.Speed,
                                iconColor = CyanSecondary
                            )
                        }
                    }
                }
            }

            // 5. Visual Break / Illustrated Context
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = DarkSurfaceContainerLow,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = "https://lh3.googleusercontent.com/aida-public/AB6AXuABVNJuKEbMfTOpLK_B72RPHedMjQgc3ew5n-gwkvspjVTpCGQQmxNcXm-TnR0KO3N-IYpFp8qXAlVXddilpmehVXKJxgAgQ6MEViOHIuFyDWTpgMk53OnttA9P69NsUuabehDrZ87LhgcQ25_cueLtJQFxRQ04HDujoK-dw3rdu7iNo0-rCD8Pa5NT7_Q0eJMcmG3hXaTzyHXhEOkRxYsWcm0gUAUTgOahA3veZdPDlZYPob-IE1szzA"),
                            contentDescription = "Field-Loop Interaction",
                            modifier = Modifier
                                .size(70.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "VISUAL MICRO-CONCEPT", color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Field-Loop Interaction", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Text(
                                text = "Flux lines compress as pole advances • Current swirls counter-clockwise",
                                color = DarkOnSurfaceVariant,
                                fontSize = 11.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

            // 6. Core Concepts & Exam Pointers
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Core Concepts & Exam Pointers", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = "3 of 8 Displayed", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Concept 1: Magnetic Flux
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerHigh),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "CONCEPT 01", color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Text(text = "Fundamental", color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Text(text = "Magnetic Flux (Φ_B)", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 2.dp))
                            Text(
                                text = "The total number of magnetic field lines passing perpendicular through a designated surface area. Defined mathematically as the scalar dot product of field & area vectors.",
                                color = DarkOnSurfaceVariant,
                                fontSize = 11.sp,
                                lineHeight = 16.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Surface(shape = RoundedCornerShape(8.dp), color = DarkSurfaceContainerLowest, modifier = Modifier.weight(1f)) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Text(text = "SI Unit", color = DarkOnSurfaceVariant, fontSize = 9.sp)
                                        Text(text = "Weber (Wb) / T·m²", color = DarkOnSurface, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Surface(shape = RoundedCornerShape(8.dp), color = DarkSurfaceContainerLowest, modifier = Modifier.weight(1f)) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Text(text = "Physical Nature", color = DarkOnSurfaceVariant, fontSize = 9.sp)
                                        Text(text = "Scalar Quantity", color = CyanSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }

                    // Concept 2: Lenz's Law & Energy Conservation
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainerHigh),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(text = "CONCEPT 02", color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Lenz's Law & Energy Conservation", color = DarkOnSurface, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 2.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier
                                    .padding(vertical = 6.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(OrangePrimaryContainer.copy(alpha = 0.15f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(16.dp))
                                Text(text = "Frequent 3-mark Question in CBSE & State Boards", color = OrangePrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }

                            Text(
                                text = "The direction of the induced current opposes the motion that produces it. If work were not done against this opposing magnetic force, spontaneous mechanical energy would violate thermodynamics.",
                                color = DarkOnSurfaceVariant,
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Expandable Step-by-Step Proof
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = DarkSurfaceContainerLowest,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { isProofExpanded = !isProofExpanded }
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                            Icon(imageVector = Icons.Default.Schema, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(16.dp))
                                            Text(text = "Step-by-Step Board Exam Proof", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                        }
                                        Icon(
                                            imageVector = Icons.Default.ExpandMore,
                                            contentDescription = null,
                                            tint = DarkOnSurfaceVariant,
                                            modifier = Modifier.rotate(if (isProofExpanded) 180f else 0f)
                                        )
                                    }

                                    AnimatedVisibility(visible = isProofExpanded) {
                                        Column(
                                            modifier = Modifier.padding(top = 8.dp),
                                            verticalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Text(text = "Step 1: Assume North pole of magnet approaches coil. If induced current reinforced movement, North pole would attract it, accelerating without external input.", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                                            Text(text = "Step 2: This would yield perpetual kinetic gain without fuel, contradicting the First Law of Thermodynamics.", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                                            Text(text = "Step 3: Hence, face of coil facing approaching North must develop North polarity (repulsion), demanding mechanical work to push it forward.", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 7. Interactive Formula Card Spotlight
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
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
                            Text(text = "FORMULA SPOTLIGHT #04", color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .clickable {
                                        clipboardManager.setText(AnnotatedString("Ε = − L · (dI / dt)"))
                                        EduDxRepository.showToast("Copied formula to clipboard")
                                    }
                                    .padding(4.dp)
                            ) {
                                Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(text = "Copy", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                            }
                        }

                        Text(text = "Self-Induced Back EMF", color = DarkOnSurface, fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 2.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(DarkSurfaceContainerLowest)
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Ε = − L · (dI / dt)", color = OrangePrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                Text(text = "Negative sign designates opposition according to Lenz's Law", color = DarkOnSurfaceVariant, fontSize = 10.sp, modifier = Modifier.padding(top = 2.dp))
                            }
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            FormulaVarRow("Ε", "Induced Electromotive Force", "Volts (V)", OrangePrimaryContainer)
                            FormulaVarRow("L", "Coefficient of Self Inductance", "Henry (H)", CyanSecondary)
                            FormulaVarRow("dI/dt", "Rate of Current Change", "Amperes/sec (A/s)", PeriwinkleTertiary)
                        }
                    }
                }
            }

            // 8. Quick Flashcard Teaser
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(imageVector = Icons.Default.Style, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(18.dp))
                            Text(text = "Quick Flashcard Teaser", color = DarkOnSurface, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(text = "Card 1 of 4", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Interactive Flashcard
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = DarkSurfaceContainerHigh,
                        shadowElevation = 6.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isFlashcardFlipped = !isFlashcardFlipped }
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "RAPID RECALL", color = OrangePrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                                    Text(text = if (isFlashcardFlipped) "Tap to reset" else "Tap to flip", color = DarkOnSurfaceVariant, fontSize = 10.sp)
                                    Icon(imageVector = Icons.Default.Sync, contentDescription = null, tint = DarkOnSurfaceVariant, modifier = Modifier.size(12.dp))
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            if (!isFlashcardFlipped) {
                                Text(
                                    text = "Q: What is the polarity and directional tendency of induced EMF when magnetic flux through a coil is decreasing?",
                                    color = DarkOnSurface,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    lineHeight = 20.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Icon(imageVector = Icons.Default.TouchApp, contentDescription = null, tint = BorderOutline, modifier = Modifier.size(14.dp))
                                    Text(text = "Tap anywhere to inspect answer & core rule", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                                }
                            } else {
                                Text(text = "VERIFIED SOLUTION", color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Text(
                                    text = "A: It induces EMF in a direction that supports and reinforces the existing magnetic field to prevent it from decaying.",
                                    color = CyanSecondary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 20.sp,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                                Text(
                                    text = "Rule: Lenz's Law dictates opposition to any variation. Since flux is dropping, induced current circulates to augment lines in the original direction.",
                                    color = DarkOnSurfaceVariant,
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Sticky Bottom Action Bar
        Surface(
            color = DarkBackground.copy(alpha = 0.95f),
            shadowElevation = 16.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Button(
                    onClick = { EduDxRepository.navigateTo(ScreenRoute.AUDIO_STUDIO) },
                    colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.fillMaxWidth().height(46.dp)
                ) {
                    Text("Create Audio Episode (TTS Studio)", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                }

                Button(
                    onClick = { EduDxRepository.navigateTo(ScreenRoute.QUIZ_HUB) },
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHigh),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.fillMaxWidth().height(42.dp)
                ) {
                    Icon(imageVector = Icons.Default.FactCheck, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Test Yourself with Daily Quiz", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
fun BriefingBulletItem(
    title: String,
    desc: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = DarkSurfaceContainerLow,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(16.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(text = desc, color = DarkOnSurfaceVariant, fontSize = 11.sp, lineHeight = 16.sp, modifier = Modifier.padding(top = 2.dp))
            }
        }
    }
}

@Composable
fun FormulaVarRow(
    symbol: String,
    name: String,
    unit: String,
    badgeColor: Color
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = DarkSurfaceContainerLow,
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(badgeColor.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = symbol, color = badgeColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Text(text = name, color = DarkOnSurface, fontSize = 11.sp)
            }
            Text(text = unit, color = DarkOnSurfaceVariant, fontSize = 11.sp)
        }
    }
}
