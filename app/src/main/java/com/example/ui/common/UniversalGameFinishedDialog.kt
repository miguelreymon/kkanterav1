package com.example.ui.common

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.stats.HypeRewardBreakdown
import com.example.vision.ReactionVideoShareFormat
import com.example.vision.VideoRecordingFormat

/**
 * Pantalla / Modal de Final de Partida unificado, moderno e inmersivo.
 *
 * Flujo:
 * - Paso 1: Celebración de Hype y Rendimiento Equilibrado (+ desglose de Hype, nuevo récord y subida de nivel).
 * - Paso 2 (Botón "SIGUIENTE ⚡"): Selección estética para compartir el vídeo con marca de agua Kantera AI.
 */
@Composable
fun UniversalGameFinishedDialog(
    gameTitle: String,
    score: Int,
    scoreLabel: String = "PUNTOS",
    secondaryStatValue: String? = null,
    secondaryStatLabel: String? = null,
    hypeReward: HypeRewardBreakdown?,
    // Opciones de vídeo y compartir
    hasRecordedVideo: Boolean = false,
    selectedVideoFormat: ReactionVideoShareFormat = ReactionVideoShareFormat.HIGHLIGHTS,
    recordingFormat: VideoRecordingFormat = VideoRecordingFormat.VERTICAL,
    isMusicEnabled: Boolean = true,
    isGeneratingHighlight: Boolean = false,
    onSelectVideoFormat: (ReactionVideoShareFormat) -> Unit = {},
    onSelectRecordingFormat: (VideoRecordingFormat) -> Unit = {},
    onToggleMusic: () -> Unit = {},
    onShareVideo: (Context, String) -> Unit = { _, _ -> },
    onRestart: () -> Unit,
    onExit: () -> Unit
) {
    val context = LocalContext.current
    var currentStep by remember { mutableStateOf(1) } // 1: Hype & Stats, 2: Share & Video

    // Animación de pulso para el Hype
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val hypeGlowScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowScale"
    )

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xDB0A0E1A))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF131B2E),
                                Color(0xFF0D1220),
                                Color(0xFF090D17)
                            )
                        )
                    )
                    .border(
                        width = 1.5.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF00E5FF),
                                Color(0xFFFF9800),
                                Color(0xFF7C4DFF)
                            )
                        ),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(22.dp)
                    .testTag("universal_game_finished_dialog")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    // Barra indicadora de pasos superior
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StepIndicatorPill(
                            title = "1. RECOMPENSA",
                            isActive = currentStep == 1,
                            onClick = { currentStep = 1 }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = Color(0x66FFFFFF),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        StepIndicatorPill(
                            title = "2. COMPARTIR VÍDEO",
                            isActive = currentStep == 2,
                            onClick = { currentStep = 2 }
                        )
                    }

                    if (currentStep == 1) {
                        // ==========================================
                        // PASO 1: HYPE, RÉCORD Y ESTADÍSTICAS
                        // ==========================================
                        Text(
                            text = "🏀 ¡ENTRENAMIENTO COMPLETADO!",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF00E5FF),
                            letterSpacing = 0.5.sp,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = gameTitle,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFCBD5E1)
                        )

                        // TARJETA DE PUNTOS REALIZADOS
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Puntuación Principal
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(Color(0xFF1E283D))
                                    .border(1.dp, Color(0x4400E5FF), RoundedCornerShape(18.dp))
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "$score",
                                        fontSize = 38.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White
                                    )
                                    Text(
                                        text = scoreLabel,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF00E5FF),
                                        letterSpacing = 1.sp
                                    )
                                }
                            }

                            // Puntuación Secundaria (si existe: Ej. Canastas, BPM, Combos)
                            if (secondaryStatValue != null) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(18.dp))
                                        .background(Color(0xFF1E283D))
                                        .border(1.dp, Color(0x4400E676), RoundedCornerShape(18.dp))
                                        .padding(vertical = 12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = secondaryStatValue,
                                            fontSize = 38.sp,
                                            fontWeight = FontWeight.Black,
                                            color = Color(0xFF00E676)
                                        )
                                        Text(
                                            text = secondaryStatLabel ?: "ACIERTOS",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF00E676),
                                            letterSpacing = 1.sp
                                        )
                                    }
                                }
                            }
                        }

                        // ==========================================
                        // BANNER DE RECOMPENSA DE HYPE CALCULADA
                        // ==========================================
                        val earnedHype = hypeReward?.totalHypeEarned ?: (score / 2).coerceAtLeast(15)
                        val isRecord = hypeReward?.isNewRecord == true
                        val leveledUp = hypeReward?.leveledUp == true

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .scale(hypeGlowScale)
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFF2A1B4E),
                                            Color(0xFF1E2A5E),
                                            Color(0xFF102844)
                                        )
                                    )
                                )
                                .border(
                                    width = 2.dp,
                                    brush = Brush.horizontalGradient(
                                        listOf(Color(0xFFFF9800), Color(0xFF00E5FF))
                                    ),
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 14.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocalFireDepartment,
                                        contentDescription = "Hype",
                                        tint = Color(0xFFFF9800),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = "+$earnedHype HYPE",
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFFFFD700),
                                        letterSpacing = 1.sp
                                    )
                                }

                                Text(
                                    text = "⚡ Recompensa equilibrada por esfuerzo de juego",
                                    fontSize = 10.sp,
                                    color = Color(0xBBFFFFFF),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        // DESGLOSE DEL HYPE GANADO
                        if (hypeReward != null) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(Color(0x55111827))
                                    .border(1.dp, Color(0x33446699), RoundedCornerShape(14.dp))
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                HypeBreakdownRow(
                                    label = "⭐ Base por entrenamiento completado",
                                    value = "+${hypeReward.baseEffortHype} Hype"
                                )
                                HypeBreakdownRow(
                                    label = "🎯 Rendimiento (${hypeReward.gameTitle.take(18)}...)",
                                    value = "+${hypeReward.performanceHype} Hype"
                                )
                                if (hypeReward.accuracyOrComboBonusHype > 0) {
                                    HypeBreakdownRow(
                                        label = "⚡ Bonus de Racha / Combos",
                                        value = "+${hypeReward.accuracyOrComboBonusHype} Hype",
                                        valueColor = Color(0xFF00E676)
                                    )
                                }
                                if (isRecord) {
                                    HypeBreakdownRow(
                                        label = "🏆 ¡¡NUEVO RÉCORD PERSONAL!!",
                                        value = "+${hypeReward.recordBonusHype} Hype",
                                        valueColor = Color(0xFFFFD700)
                                    )
                                }
                                if (leveledUp) {
                                    HypeBreakdownRow(
                                        label = "🎉 ¡¡SUBIDA DE NIVEL!!",
                                        value = "NIVEL ${hypeReward.newLevel} 🚀",
                                        valueColor = Color(0xFF00E5FF)
                                    )
                                }
                            }
                        }

                        // BOTÓN "SIGUIENTE: COMPARTIR VÍDEO"
                        Button(
                            onClick = { currentStep = 2 },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .shadow(8.dp, RoundedCornerShape(14.dp))
                                .testTag("game_finished_next_step_button"),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E5FF))
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "SIGUIENTE (VÍDEO & COMPARTIR)",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.Black,
                                    letterSpacing = 0.5.sp
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                    } else {
                        // ==========================================
                        // PASO 2: VÍDEO CON MARCA DE AGUA Y COMPARTIR
                        // ==========================================
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Videocam,
                                    contentDescription = null,
                                    tint = Color(0xFF00E5FF),
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "VÍDEO CON MARCA DE AGUA",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                            }

                            // Insignia marca de agua
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0x3300E5FF))
                                    .border(1.dp, Color(0x6600E5FF), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "KANTERA AI LOGO",
                                    fontSize = 8.5.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF00E5FF),
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }

                        // PRIVACIDAD TOTAL
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color(0x1A00E676))
                                .border(1.dp, Color(0x4400E676), RoundedCornerShape(14.dp))
                                .padding(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = Color(0xFF00E676),
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "El vídeo solo se guarda o envía si tú decides compartirlo. Si sales, se borra automáticamente de la memoria.",
                                    fontSize = 11.sp,
                                    color = Color(0xFFE2E8F0),
                                    lineHeight = 15.sp
                                )
                            }
                        }

                        // SELECTOR FORMATO: VÍDEO COMPLETO VS HIGHLIGHTS
                        val isHighlight = selectedVideoFormat == ReactionVideoShareFormat.HIGHLIGHTS
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FormatOptionCard(
                                title = "🔥 Highlights",
                                subtitle = "Mejores botes & combos",
                                isSelected = isHighlight,
                                activeColor = Color(0xFFFF9800),
                                modifier = Modifier.weight(1f),
                                onClick = { onSelectVideoFormat(ReactionVideoShareFormat.HIGHLIGHTS) }
                            )

                            FormatOptionCard(
                                title = "🎬 Vídeo Íntegro",
                                subtitle = "Sesión completa",
                                isSelected = !isHighlight,
                                activeColor = Color(0xFF00E5FF),
                                modifier = Modifier.weight(1f),
                                onClick = { onSelectVideoFormat(ReactionVideoShareFormat.FULL_VIDEO) }
                            )
                        }

                        // SELECTOR ORIENTACIÓN: VERTICAL (TIKTOK/REELS) VS HORIZONTAL
                        val isHorizontal = recordingFormat == VideoRecordingFormat.HORIZONTAL
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF192233))
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Formato:",
                                fontSize = 11.sp,
                                color = Color(0xFFAAAAAA),
                                fontWeight = FontWeight.Bold
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                FilterChip(
                                    selected = !isHorizontal,
                                    onClick = { onSelectRecordingFormat(VideoRecordingFormat.VERTICAL) },
                                    label = { Text("📱 Vertical 9:16", fontSize = 10.sp, fontWeight = FontWeight.Bold) }
                                )
                                FilterChip(
                                    selected = isHorizontal,
                                    onClick = { onSelectRecordingFormat(VideoRecordingFormat.HORIZONTAL) },
                                    label = { Text("🖥️ Horizontal 16:9", fontSize = 10.sp, fontWeight = FontWeight.Bold) }
                                )
                            }
                        }

                        // MÚSICA TRAP / BEAT DE FONDO
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF192233))
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = null,
                                    tint = if (isMusicEnabled) Color(0xFFFF9800) else Color.Gray,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "Música Trap Beat",
                                    fontSize = 11.5.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Switch(
                                checked = isMusicEnabled,
                                onCheckedChange = { onToggleMusic() },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color(0xFFFF9800),
                                    checkedTrackColor = Color(0x66FF9800)
                                )
                            )
                        }

                        // INDICADOR DE PROCESAMIENTO
                        if (isGeneratingHighlight) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    color = Color(0xFF00E5FF),
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Preparando vídeo y marca de agua...",
                                    fontSize = 11.sp,
                                    color = Color(0xFF00E5FF)
                                )
                            }
                        }

                        // BOTONES DE REDES SOCIALES (WHATSAPP E INSTAGRAM)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // WhatsApp
                            Button(
                                onClick = { onShareVideo(context, "whatsapp") },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(46.dp)
                                    .testTag("reaction_share_whatsapp_button"),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                                enabled = !isGeneratingHighlight
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Send,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "WhatsApp",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }

                            // Instagram Stories / Reels
                            Button(
                                onClick = { onShareVideo(context, "instagram") },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(46.dp)
                                    .testTag("reaction_share_instagram_button"),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE1306C)),
                                enabled = !isGeneratingHighlight
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "Instagram",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }

                        // Botón Galería / Más apps
                        OutlinedButton(
                            onClick = { onShareVideo(context, "general") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .testTag("reaction_share_general_button"),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x66446699)),
                            enabled = !isGeneratingHighlight
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "Guardar en Galería / Más opciones",
                                    fontSize = 11.5.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        // Botón para volver a ver los puntos
                        TextButton(
                            onClick = { currentStep = 1 }
                        ) {
                            Text(
                                text = "← Volver a ver mis puntos y Hype",
                                fontSize = 11.sp,
                                color = Color(0xFFAAAAAA)
                            )
                        }
                    }

                    // ACCIONES FINALES (REINICIAR / SALIR)
                    Divider(color = Color(0x22FFFFFF), modifier = Modifier.padding(vertical = 4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onRestart,
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("universal_game_restart_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "REPETIR",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.Black
                                )
                            }
                        }

                        Button(
                            onClick = onExit,
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("universal_game_exit_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E283D))
                        ) {
                            Text(
                                text = "SALIR",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFCBD5E1)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StepIndicatorPill(
    title: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isActive) Color(0x3300E5FF) else Color(0x221E283D))
            .border(
                1.dp,
                if (isActive) Color(0xFF00E5FF) else Color(0x33446699),
                RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = title,
            fontSize = 9.5.sp,
            fontWeight = if (isActive) FontWeight.Black else FontWeight.Bold,
            color = if (isActive) Color(0xFF00E5FF) else Color(0x88CBD5E1)
        )
    }
}

@Composable
private fun FormatOptionCard(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    activeColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(if (isSelected) activeColor else Color(0xFF192233))
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.Black else Color.White
            )
            Text(
                text = subtitle,
                fontSize = 9.sp,
                color = if (isSelected) Color(0xCC000000) else Color(0xFFAAAAAA)
            )
        }
    }
}

@Composable
private fun HypeBreakdownRow(
    label: String,
    value: String,
    valueColor: Color = Color(0xFFFFB300)
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            color = Color(0xFFCBD5E1),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 11.sp,
            color = valueColor,
            fontWeight = FontWeight.Black
        )
    }
}
