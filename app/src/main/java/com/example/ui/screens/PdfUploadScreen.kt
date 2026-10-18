package com.example.ui.screens

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.data.EduDxRepository
import com.example.model.ScreenRoute
import com.example.ui.theme.*

@Composable
fun PdfUploadScreen(
    onBackClick: () -> Unit = { EduDxRepository.navigateBack() },
    modifier: Modifier = Modifier
) {
    var uploadProgress by remember { mutableFloatStateOf(0.78f) }
    var isProcessing by remember { mutableStateOf(false) }
    var selectedLang by remember { mutableStateOf("English (Academic & Conversational)") }
    var isSummaryChecked by remember { mutableStateOf(true) }
    var isFlashcardsChecked by remember { mutableStateOf(true) }
    var isPodcastChecked by remember { mutableStateOf(true) }

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
                            text = "Notes & PDF Upload",
                            color = DarkOnSurface,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    IconButton(
                        onClick = { EduDxRepository.showToast("Shared document upload studio") },
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

        // 2. Info Badge & Dropzone Area
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(CyanSecondaryContainer.copy(alpha = 0.2f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(CyanSecondary))
                        Text(text = "AI NOTE SYNTHESIZER", color = CyanSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                        Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(16.dp))
                        Text(text = "Fast Lane", color = DarkOnSurfaceVariant, fontSize = 11.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Dropzone Box
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = DarkSurfaceContainerLow,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, DarkSurfaceContainerHighest, RoundedCornerShape(16.dp))
                        .clickable {
                            uploadProgress = 0.85f
                            EduDxRepository.showToast("Selected document: Physics_Chapter6.pdf")
                        }
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        listOf(OrangePrimaryContainer.copy(alpha = 0.3f), CyanSecondaryContainer.copy(alpha = 0.3f))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.CloudUpload, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(32.dp))
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(text = "Drop textbook, lecture or scan", color = DarkOnSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = "Drag notes or handwritten PDFs here to instantly turn them into interactive audio & flashcards.",
                            color = DarkOnSurfaceVariant,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                        )

                        Button(
                            onClick = {
                                uploadProgress = 0.95f
                                EduDxRepository.showToast("Picked: NCERT_Class12_Physics_Chapter6_EMI.pdf")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerHighest),
                            shape = RoundedCornerShape(50),
                            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.FolderOpen, contentDescription = null, tint = OrangePrimary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Browse Files", color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf("PDF (up to 50MB)", "Handwritten Scans (JPG/PNG)", "Max 80 pages").forEach { badge ->
                                Text(
                                    text = badge,
                                    color = DarkOnSurfaceVariant,
                                    fontSize = 9.sp,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(DarkSurfaceContainer)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. Active Upload & Extraction Progress Card
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
                        verticalAlignment = Alignment.Top
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(DarkSurfaceContainerHighest),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.PictureAsPdf, contentDescription = null, tint = OrangePrimaryContainer, modifier = Modifier.size(24.dp))
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "NCERT_Class12_Physics_Chapter6_EMI.pdf",
                                    color = DarkOnSurface,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(text = "18.4 MB • AI Document Parser v2.1", color = CyanSecondary, fontSize = 10.sp)
                            }
                        }

                        Text(
                            text = "${(uploadProgress * 100).toInt()}%",
                            color = CyanSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(DarkSurfaceContainerHighest)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    LinearProgressIndicator(
                        progress = { uploadProgress },
                        modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(50)),
                        color = OrangePrimaryContainer,
                        trackColor = DarkSurfaceContainerHighest,
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Stages Checklist
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(DarkSurfaceContainerLow)
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        UploadStageRow("File validated & virus scanned", "Completed", true, CyanSecondary)
                        UploadStageRow("OCR & mathematical formula extraction", "14 eqns found", true, CyanSecondary)
                        UploadStageRow("AI chapter structure & concept mapping", "In Progress...", false, OrangePrimaryContainer)
                        UploadStageRow("Audio script generation & narration sync", "Pending", false, DarkOnSurfaceVariant)
                    }
                }
            }
        }

        // 4. Document Preview Panel (Mini Viewer Card)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "DOCUMENT PREVIEW (PAGE 1)", color = DarkOnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                        Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(14.dp))
                        Text(text = "Verified Scan", color = CyanSecondary, fontSize = 11.sp)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = DarkSurfaceContainerLow,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp, 100.dp)
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = "https://lh3.googleusercontent.com/aida-public/AB6AXuD2A3LSeEJtZ4oKdNiY-2Dv0RkmdLD9qEHpScjqa7JCV4RHCcSw5KOsPkS5W8Q0OIGCDTzJbY5fybkPY7jsMnmKBrofG2FjHAZosOU-zPqA38C_8nXujjZWNl1Ou4-W8EUvKTbTmGWIgUXGthI2hI_9yybjuMDoYE-gbq877PYf9bdPC5E025fkjhFJlXgZ-ktiWEnYcxOLBKNnavdW9-0m9TfEagx6I9wOMfFgfWXNpcTWn---LkVh-g"),
                                contentDescription = "Textbook Scan Preview",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = "p. 204",
                                color = DarkOnSurface,
                                fontSize = 9.sp,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(DarkBackground.copy(alpha = 0.8f))
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Physics Part 1",
                                color = OrangePrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(OrangePrimary.copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                            Text(
                                text = "Chapter 6: Electromagnetic Induction",
                                color = DarkOnSurface,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            Text(text = "Faraday's Experiments & Lenz's Law", color = CyanSecondary, fontSize = 11.sp)
                            Text(
                                text = "“When the magnetic flux linking a conducting circuit changes, an electromotive force is induced in the circuit...”",
                                color = DarkOnSurfaceVariant,
                                fontSize = 10.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(top = 4.dp)
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.padding(top = 6.dp)
                            ) {
                                Text(text = "14 Equations", color = DarkOnSurfaceVariant, fontSize = 9.sp, modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(DarkSurfaceContainer).padding(4.dp))
                                Text(text = "8 Core Ideas", color = DarkOnSurfaceVariant, fontSize = 9.sp, modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(DarkSurfaceContainer).padding(4.dp))
                            }
                        }
                    }
                }
            }
        }

        // 5. Synthesis Options (Checkboxes & Voice)
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(text = "Synthesis Options", color = DarkOnSurface, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Select artifacts to generate and customize voiceover pacing.", color = DarkOnSurfaceVariant, fontSize = 11.sp)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "TARGET OUTPUT FORMATS", color = DarkOnSurfaceVariant, fontSize = 10.sp, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(6.dp))

                    SynthesisCheckRow(
                        title = "AI Summary",
                        desc = "Structured chapter digest with bulleted highlights",
                        checked = isSummaryChecked,
                        onCheckedChange = { isSummaryChecked = it },
                        icon = Icons.Default.Summarize,
                        iconTint = OrangePrimaryContainer
                    )

                    SynthesisCheckRow(
                        title = "Interactive Flashcards",
                        desc = "Active recall cards for formula & law drills",
                        checked = isFlashcardsChecked,
                        onCheckedChange = { isFlashcardsChecked = it },
                        icon = Icons.Default.Style,
                        iconTint = CyanSecondary
                    )

                    SynthesisCheckRow(
                        title = "Podcast Audio Lesson",
                        desc = "Narrated 8-minute conversational study stream",
                        checked = isPodcastChecked,
                        onCheckedChange = { isPodcastChecked = it },
                        icon = Icons.Default.Podcasts,
                        iconTint = PeriwinkleTertiary
                    )
                }
            }
        }

        // 6. Primary Action Buttons
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        isProcessing = true
                        EduDxRepository.showToast("Synthesizing notes with OCR & AI...")
                        EduDxRepository.navigateTo(ScreenRoute.AI_NOTE_SUMMARY)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = OrangePrimaryContainer),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("process_notes_btn")
                ) {
                    Icon(imageVector = Icons.Default.SmartToy, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Process & Generate AI Summary", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onBackClick,
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSurfaceContainerLow),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) {
                    Text("Cancel / Upload Different File", color = DarkOnSurfaceVariant, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun UploadStageRow(
    title: String,
    status: String,
    isDone: Boolean,
    statusColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = if (isDone) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                contentDescription = null,
                tint = if (isDone) statusColor else DarkOnSurfaceVariant,
                modifier = Modifier.size(16.dp)
            )
            Text(text = title, color = DarkOnSurface, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Text(text = status, color = statusColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SynthesisCheckRow(
    title: String,
    desc: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = DarkSurfaceContainerHigh.copy(alpha = 0.7f),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .clickable { onCheckedChange(!checked) }
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
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(iconTint.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(18.dp))
                }
                Column {
                    Text(text = title, color = DarkOnSurface, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text(text = desc, color = DarkOnSurfaceVariant, fontSize = 10.sp)
                }
            }

            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(checkedColor = OrangePrimaryContainer)
            )
        }
    }
}
