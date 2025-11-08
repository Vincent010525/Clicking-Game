# Clicking Game

A reaction-based mobile game built with **Kotlin** and **Android Studio**.  
The goal is to tap enemies that appear on the screen before the timer reaches zero.  
Enemies appear one at a time in randomized positions, and the difficulty level determines how many possible enemy positions are used. The game tracks the player's score and saves the all-time high score using **DataStore**.

---

## Features

- Three difficulty levels: Easy, Medium, Hard  
- Randomized enemy appearance across multiple screen positions  
- Timer that decreases based on performance  
- Score and high score tracking  
- High score saved persistently with DataStore  
- Smooth gameplay with instant enemy switching  
- Game over dialog displaying final score or new high score

---

## How the Game Works

### Difficulty Selection
When the game starts, the player chooses a difficulty:
- **Easy:** Small number of enemy positions  
- **Medium:** Moderate number of enemy positions  
- **Hard:** Maximum number of enemy positions  

The selected difficulty determines how many enemy `ImageView` objects are added to the active enemy list.

### Gameplay
- One enemy becomes visible at a random position.  
- When the player taps the enemy:
  - The score increases by 1  
  - A new enemy appears at a different random position  
  - The timer resets  

Every **10 points**, the time allowed to tap the next enemy is reduced, increasing the challenge over time.

### Timer
A `CountDownTimer` starts (initially 5 seconds) each time an enemy is tapped.  
If the timer reaches zero, the game ends.

### High Score
- Uses **Jetpack DataStore** to store the all-time high score.
- High scores are only saved when playing on **Hard** difficulty.
- If the current score beats the existing high score, it becomes the new saved score.

---

## Development Overview

This game was built by:
- Creating 18 enemy `ImageView` objects and positioning them in the layout.
- Implementing difficulty logic by choosing a subset of these enemies dynamically.
- Using an `ArrayList<ImageView>` to manage all active enemies during gameplay.
- Handling randomized enemy selection using `Random.nextInt()`.
- Implementing a restartable `CountDownTimer` for time-pressure gameplay.
- Adding score tracking and UI updates via `TextView` components.
- Persisting high scores using DataStore with `intPreferencesKey`, `Flow`, and coroutines.
- Triggering game-over dialogs that display final or high score results.

This structure demonstrates work with Android UI, Kotlin logic, state management, and persistent storage.

---

## Core Technologies

- **Kotlin**
- **Android Studio**
- **Android Views**
- **CountDownTimer**
- **Jetpack DataStore**
- **Coroutines (Dispatchers.IO / Main)**

---

## Project Structure

```
/app
 ├── MainActivity.kt              # Main game logic, timer, scoring, UI updates
 ├── dataStore setup              # High score persistence
 ├── activity_main.xml            # Layout with all enemy ImageViews and UI elements
 ├── drawable/                    # Enemy images and other assets
 └── values/                      # Styles, colors, strings
```

---

## How to Run

1. Clone the repository.
2. Open the project in **Android Studio**.
3. Sync Gradle.
4. Run on an emulator or physical Android device.

No additional setup is required.

---

## Author

**Vincent Bejbom**
