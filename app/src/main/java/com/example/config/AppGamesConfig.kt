package com.example.config

import com.example.R
import com.example.home.HeroWorkoutSlide

/**
 * =========================================================================================
 * ⚙️ PANEL DE CONFIGURACIÓN MAESTRO DEL SLIDE Y ENTRENAMIENTOS (GAME & PRO)
 * =========================================================================================
 *
 * ¡EDÍTALO LIBREMENTE DESDE AQUÍ!
 * En este archivo puedes:
 * 1. 🔀 Reordenar, añadir, eliminar o duplicar tarjetas del slide o workouts.
 * 2. 🔒 Poner cualquier juego bloqueado (`isLocked = true`) o desbloqueado (`isLocked = false`).
 * 3. ⚡ Definir el HYPE / XP requerido para desbloquear (`requiredHype = 500`).
 * 4. 📝 Cambiar títulos, subtítulos, nombres, duración y dificultad.
 * 5. 🖼️ Cambiar imágenes fácilmente usando [SlideImage]:
 *    - SlideImage.POINT ("kantera_point")
 *    - SlideImage.POINT2 ("kantera_point2")
 *    - SlideImage.MANO ("kantera_mano")
 *    - SlideImage.LASER ("kantera_laser")
 *    - SlideImage.TIRO ("kantera_tiro")
 *    - SlideImage.KID ("kantera_kid")
 *    - SlideImage.DRIBBLING ("img_drill_dribbling")
 *    - SlideImage.MANITA ("kantera_manita")
 *    - SlideImage.ONBOARDING ("img_onboarding_player")
 *    - SlideImage.AVATAR ("avatarchico")
 * 6. 🎮 Asociar a qué juego real corresponde cada tarjeta mediante [GameTarget]:
 *    - GameTarget.REACTION_BALL_TOUCH -> Reaction Points (Ball & Touch)
 *    - GameTarget.REACTION_CROSS_TOUCH -> Reaction Points (Cross Touch)
 *    - GameTarget.DEFEND_ZONE -> Defend the Zone
 *    - GameTarget.LASER_ZONE -> Defend the Zone (Laser)
 *    - GameTarget.SHOOTING -> Análisis de Tiro
 *    - GameTarget.KIDS_MINI_BASKET -> Kids Mini Basket
 *    - GameTarget.DRIBBLING -> Dribble Training
 *    - GameTarget.CATALOG_ONLY -> Abre el catálogo de entrenamientos
 *    - GameTarget.NONE -> Sin acción (por ejemplo si está bloqueado)
 *
 * Cada modo (MODO GAME y MODO PRO) tiene sus listas independientes tanto para
 * el Slide del Inicio como para el Catálogo de Workouts.
 */

enum class GameTarget {
    REACTION_BALL_TOUCH,
    REACTION_CROSS_TOUCH,
    DEFEND_ZONE,
    LASER_ZONE,
    SHOOTING,
    KIDS_MINI_BASKET,
    DRIBBLING,
    SPEED_TRAP,
    CATALOG_ONLY,
    NONE
}

enum class SlideImage(val resId: Int) {
    POINT(R.drawable.kantera_point),
    POINT2(R.drawable.kantera_point2),
    MANO(R.drawable.kantera_mano),
    LASER(R.drawable.kantera_laser),
    TIRO(R.drawable.kantera_tiro),
    KID(R.drawable.kantera_kid),
    DRIBBLING(R.drawable.img_drill_dribbling),
    SPEED_TRAP(R.drawable.kantera_speedtrap),
    MANITA(R.drawable.kantera_manita),
    ONBOARDING(R.drawable.img_onboarding_player),
    AVATAR(R.drawable.avatarchico)
}

/**
 * Definición individual de una tarjeta/juego configurable.
 */
data class GameSlideItemConfig(
    val id: String,
    val title: String,
    val subtitle: String,
    val drillName: String,
    val duration: String = "2 MIN",
    val difficulty: String = "BEGINNER",
    val image: SlideImage,
    val gameTarget: GameTarget,
    val isLocked: Boolean = false,
    val requiredHype: Int = 0,
    val levelNumber: Int = 1
)

object AppGamesConfig {

    // =====================================================================================
    // 🕹️ 1. MODO GAME - TARJETAS DEL SLIDE DE INICIO (HOME)
    // =====================================================================================
    // Cambia aquí el orden de las tarjetas arrastrando o moviendo las líneas en la lista:
    val gameModeHomeSlide: List<GameSlideItemConfig> = listOf(
        // 1. SPEED TRAP (Radar de Velocidad BPM y Bote de Fuego)
        GameSlideItemConfig(
            id = "game_speed_trap",
            title = "¡NUEVO! SPEED TRAP (BOTE DE FUEGO)",
            subtitle = "Tacómetro de carreras, radar BPM y aguante de 20s en llamas",
            drillName = "SPEED TRAP",
            duration = "20 SEG",
            difficulty = "ARCADE",
            image = SlideImage.SPEED_TRAP,
            gameTarget = GameTarget.SPEED_TRAP,
            isLocked = false,
            requiredHype = 0,
            levelNumber = 1
        ),
        // 2. REACTION POINTS (Ball & Touch)
        GameSlideItemConfig(
            id = "game_reaction_touch",
            title = "NEXT UP: DRIBBLE RUSH",
            subtitle = "Toca los objetivos luminosos mientras mantienes el bote",
            drillName = "Ball & Touch",
            duration = "2 MIN",
            difficulty = "BEGINNER",
            image = SlideImage.POINT,
            gameTarget = GameTarget.REACTION_BALL_TOUCH,
            isLocked = false,
            requiredHype = 0,
            levelNumber = 2
        ),
        // 2. REACTION POINTS (Cross Touch)
        GameSlideItemConfig(
            id = "game_reaction_cross",
            title = "NEXT UP: DRIBBLE RUSH",
            subtitle = "Toca los objetivos luminosos mientras mantienes el bote",
            drillName = "Cross Touch",
            duration = "2 MIN",
            difficulty = "BEGINNER",
            image = SlideImage.POINT2,
            gameTarget = GameTarget.REACTION_CROSS_TOUCH,
            isLocked = false,
            requiredHype = 0,
            levelNumber = 2
        ),
        // 3. DEFEND THE ZONE
        GameSlideItemConfig(
            id = "game_defend_zone",
            title = "¡NUEVO! DEFEND THE ZONE",
            subtitle = "Esquiva los defensores fantasma y protege tu bote (3 vidas)",
            drillName = "DEFEND ZONE",
            duration = "1 MIN",
            difficulty = "PRO",
            image = SlideImage.MANO,
            gameTarget = GameTarget.DEFEND_ZONE,
            isLocked = false,
            requiredHype = 0,
            levelNumber = 3
        ),
        // 4. LASER ZONE
        GameSlideItemConfig(
            id = "game_laser_zone",
            title = "¡NUEVO! DEFEND THE ZONE",
            subtitle = "Esquiva los defensores fantasma y protege tu bote (3 vidas)",
            drillName = "LASER ZONE",
            duration = "1 MIN",
            difficulty = "PRO",
            image = SlideImage.LASER,
            gameTarget = GameTarget.LASER_ZONE,
            isLocked = false,
            requiredHype = 0,
            levelNumber = 4
        ),
        // 5. SHOOTING
        GameSlideItemConfig(
            id = "game_shooting",
            title = "NEXT UP: SHOOTING FORM",
            subtitle = "Perfecciona tu mecánica, arco y aciertos",
            drillName = "SHOOTING",
            duration = "3 MIN",
            difficulty = "BEGINNER",
            image = SlideImage.TIRO,
            gameTarget = GameTarget.SHOOTING,
            isLocked = false,
            requiredHype = 0,
            levelNumber = 5
        ),
        // 6. KIDS MINI BASKET
        GameSlideItemConfig(
            id = "game_kids_mini",
            title = "¡NUEVO! KIDS MINI BASKET",
            subtitle = "Tiro infantil en casa con calibración de aro y pelota por foto",
            drillName = "MINI BASKET",
            duration = "1 MIN",
            difficulty = "KIDS / CASA",
            image = SlideImage.KID,
            gameTarget = GameTarget.KIDS_MINI_BASKET,
            isLocked = false,
            requiredHype = 0,
            levelNumber = 6
        ),
        // 7. DRIBBLING
        GameSlideItemConfig(
            id = "game_dribbling",
            title = "NEXT UP: DRIBBLING COMBO",
            subtitle = "Entrena tu control y precisión de bote",
            drillName = "DRIBBLING",
            duration = "2 MIN",
            difficulty = "BEGINNER",
            image = SlideImage.DRIBBLING,
            gameTarget = GameTarget.DRIBBLING,
            isLocked = false,
            requiredHype = 0,
            levelNumber = 7
        ),
        // 8. EJEMPLO BLOQUEADO: CROSSOVER TARGETS (Requiere 500 Hype)
        GameSlideItemConfig(
            id = "game_crossover_locked",
            title = "CROSSOVER TARGETS",
            subtitle = "Sube de nivel para desbloquear este entrenamiento",
            drillName = "CROSSOVER",
            duration = "2 MIN",
            difficulty = "LEVEL 2",
            image = SlideImage.MANITA,
            gameTarget = GameTarget.NONE,
            isLocked = true,
            requiredHype = 500,
            levelNumber = 8
        ),
        // 9. EJEMPLO BLOQUEADO: STEP-BACK SHOOTING (Requiere 750 Hype)
        GameSlideItemConfig(
            id = "game_stepback_locked",
            title = "STEP-BACK SHOOTING",
            subtitle = "Sube de nivel para desbloquear este entrenamiento",
            drillName = "STEP-BACK",
            duration = "3 MIN",
            difficulty = "LEVEL 3",
            image = SlideImage.ONBOARDING,
            gameTarget = GameTarget.NONE,
            isLocked = true,
            requiredHype = 750,
            levelNumber = 9
        ),
        // 10. EJEMPLO BLOQUEADO: PRO AGILITY & ATTACK (Requiere 1000 Hype)
        GameSlideItemConfig(
            id = "game_agility_locked",
            title = "PRO AGILITY & ATTACK",
            subtitle = "Sube de nivel para desbloquear este entrenamiento",
            drillName = "AGILITY PRO",
            duration = "4 MIN",
            difficulty = "ALL-STAR",
            image = SlideImage.AVATAR,
            gameTarget = GameTarget.NONE,
            isLocked = true,
            requiredHype = 1000,
            levelNumber = 10
        )
    )

    // =====================================================================================
    // 🏀 2. MODO PRO - TARJETAS DEL SLIDE DE INICIO (HOME)
    // =====================================================================================
    // En Modo PRO puedes tener un orden diferente, tarjetas exclusivas o todas desbloqueadas:
    val proModeHomeSlide: List<GameSlideItemConfig> = listOf(
        // 1. SPEED TRAP PRO (Cadencia máxima y velocidad de bote)
        GameSlideItemConfig(
            id = "pro_speed_trap",
            title = "SPEED TRAP (CADENCE BPM)",
            subtitle = "Control de cadencia máxima y aceleración de bote a +120 BPM",
            drillName = "SPEED TRAP",
            duration = "20 SEG",
            difficulty = "PRO",
            image = SlideImage.SPEED_TRAP,
            gameTarget = GameTarget.SPEED_TRAP,
            isLocked = false,
            requiredHype = 0,
            levelNumber = 1
        ),
        // 2. SHOOTING (Análisis de tiro profesional en Modo PRO)
        GameSlideItemConfig(
            id = "pro_shooting",
            title = "PRO SHOOTING LAB",
            subtitle = "Métricas avanzadas de tiro, arco de entrada y porcentaje real",
            drillName = "TIRO DE 3",
            duration = "SIN TIEMPO",
            difficulty = "PRO",
            image = SlideImage.TIRO,
            gameTarget = GameTarget.SHOOTING,
            isLocked = false,
            requiredHype = 0,
            levelNumber = 2
        ),
        // 3. DEFEND ZONE
        GameSlideItemConfig(
            id = "pro_defend_zone",
            title = "DEFENSIVE REACTION & FOOTWORK",
            subtitle = "Defensa de zona de alto impacto con tracking corporal en tiempo real",
            drillName = "DEFEND ZONE",
            duration = "3 MIN",
            difficulty = "ELITE",
            image = SlideImage.MANO,
            gameTarget = GameTarget.DEFEND_ZONE,
            isLocked = false,
            requiredHype = 0,
            levelNumber = 3
        ),
        // 4. DRIBBLING COMBO PRO
        GameSlideItemConfig(
            id = "pro_dribbling",
            title = "BALL HANDLING & CADENCE",
            subtitle = "Control de bote a doble ritmo y aceleración explosiva",
            drillName = "DRIBBLING PRO",
            duration = "4 MIN",
            difficulty = "PRO",
            image = SlideImage.DRIBBLING,
            gameTarget = GameTarget.DRIBBLING,
            isLocked = false,
            requiredHype = 0,
            levelNumber = 4
        ),
        // 5. REACTION POINTS (Ball & Touch)
        GameSlideItemConfig(
            id = "pro_reaction_point",
            title = "PERIPHERAL VISION DRILL",
            subtitle = "Entrenamiento de visión periférica y velocidad de respuesta",
            drillName = "Ball & Touch",
            duration = "2 MIN",
            difficulty = "PRO",
            image = SlideImage.POINT,
            gameTarget = GameTarget.REACTION_BALL_TOUCH,
            isLocked = false,
            requiredHype = 0,
            levelNumber = 5
        ),
        // 6. LASER ZONE
        GameSlideItemConfig(
            id = "pro_laser_zone",
            title = "AGILITY LASER GRID",
            subtitle = "Coordinación lateral y cambios de dirección rápidos",
            drillName = "LASER ZONE",
            duration = "3 MIN",
            difficulty = "ELITE",
            image = SlideImage.LASER,
            gameTarget = GameTarget.LASER_ZONE,
            isLocked = false,
            requiredHype = 0,
            levelNumber = 6
        )
    )

    // =====================================================================================
    // 📋 3. MODO GAME - CATÁLOGO DE WORKOUTS / JUEGOS
    // =====================================================================================
    // Si quieres que el catálogo del modo GAME tenga los mismos o una lista personalizada:
    val gameModeWorkoutsCatalog: List<GameSlideItemConfig> = gameModeHomeSlide

    // =====================================================================================
    // 📋 4. MODO PRO - CATÁLOGO DE WORKOUTS PROFESIONAL
    // =====================================================================================
    // Catálogo específico para la pestaña de Workouts en Modo PRO:
    val proModeWorkoutsCatalog: List<GameSlideItemConfig> = proModeHomeSlide

    // =====================================================================================
    // 🛠️ MÉTODOS DE CONVERSIÓN (No necesitas tocar esto, se encarga de conectar con la UI)
    // =====================================================================================
    fun resolveAction(
        target: GameTarget,
        onLaunchDefendZoneDrill: () -> Unit,
        onLaunchDribbleDrill: () -> Unit,
        onLaunchReactionPointsDrill: () -> Unit,
        onLaunchShootingDrill: () -> Unit,
        onLaunchKidsMiniBasketDrill: () -> Unit,
        onLaunchSpeedTrapDrill: () -> Unit = {},
        onNavigateToWorkouts: () -> Unit
    ): () -> Unit = {
        when (target) {
            GameTarget.REACTION_BALL_TOUCH -> onLaunchReactionPointsDrill()
            GameTarget.REACTION_CROSS_TOUCH -> onLaunchReactionPointsDrill()
            GameTarget.DEFEND_ZONE -> onLaunchDefendZoneDrill()
            GameTarget.LASER_ZONE -> onLaunchDefendZoneDrill()
            GameTarget.SHOOTING -> onLaunchShootingDrill()
            GameTarget.KIDS_MINI_BASKET -> onLaunchKidsMiniBasketDrill()
            GameTarget.DRIBBLING -> onLaunchDribbleDrill()
            GameTarget.SPEED_TRAP -> onLaunchSpeedTrapDrill()
            GameTarget.CATALOG_ONLY -> onNavigateToWorkouts()
            GameTarget.NONE -> { /* Bloqueado o sin acción */ }
        }
    }

    fun buildHeroSlides(
        configs: List<GameSlideItemConfig>,
        currentXp: Int,
        onLaunchDefendZoneDrill: () -> Unit,
        onLaunchDribbleDrill: () -> Unit,
        onLaunchReactionPointsDrill: () -> Unit,
        onLaunchShootingDrill: () -> Unit,
        onLaunchKidsMiniBasketDrill: () -> Unit,
        onLaunchSpeedTrapDrill: () -> Unit = {},
        onNavigateToWorkouts: () -> Unit
    ): List<HeroWorkoutSlide> {
        return configs.map { item ->
            // Un item está bloqueado si `isLocked` es true y el XP del usuario no alcanza `requiredHype`
            val actuallyLocked = item.isLocked && (item.requiredHype > 0 && currentXp < item.requiredHype)
            HeroWorkoutSlide(
                title = item.title,
                subtitle = item.subtitle,
                drillName = item.drillName,
                duration = item.duration,
                difficulty = item.difficulty,
                isLocked = actuallyLocked,
                requiredXp = item.requiredHype,
                currentXp = currentXp,
                levelNumber = item.levelNumber,
                imageResId = item.image.resId,
                onAction = if (actuallyLocked) { {} } else {
                    resolveAction(
                        target = item.gameTarget,
                        onLaunchDefendZoneDrill = onLaunchDefendZoneDrill,
                        onLaunchDribbleDrill = onLaunchDribbleDrill,
                        onLaunchReactionPointsDrill = onLaunchReactionPointsDrill,
                        onLaunchShootingDrill = onLaunchShootingDrill,
                        onLaunchKidsMiniBasketDrill = onLaunchKidsMiniBasketDrill,
                        onLaunchSpeedTrapDrill = onLaunchSpeedTrapDrill,
                        onNavigateToWorkouts = onNavigateToWorkouts
                    )
                }
            )
        }
    }
}
