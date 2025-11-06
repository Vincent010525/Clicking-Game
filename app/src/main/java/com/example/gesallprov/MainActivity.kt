package com.example.gesallprov

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.opengl.Visibility
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.gesallprov.ui.theme.GesallprovTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "highScore")
    private val key = intPreferencesKey("highScoreKey")

    private var difficulty: String = "test"
    private val easy: String = "Easy"
    private val medium: String = "Medium"
    private val hard: String = "Hard (will count high score)"
    private var score: Int = 0
    private var highScore = 0

    private var currentTime: Long = 5000
    private val intervalTime: Long = 500
    private val endingTime: Long = 1000
    private var timer: CountDownTimer? = null

    private lateinit var highScoreTx: TextView
    private lateinit var timerTx: TextView

    private lateinit var enemy1: ImageView
    private lateinit var enemy2: ImageView
    private lateinit var enemy3: ImageView
    private lateinit var enemy4: ImageView
    private lateinit var enemy5: ImageView
    private lateinit var enemy6: ImageView
    private lateinit var enemy7: ImageView
    private lateinit var enemy8: ImageView
    private lateinit var enemy9: ImageView
    private lateinit var enemy10: ImageView
    private lateinit var enemy11: ImageView
    private lateinit var enemy12: ImageView
    private lateinit var enemy13: ImageView
    private lateinit var enemy14: ImageView
    private lateinit var enemy15: ImageView
    private lateinit var enemy16: ImageView
    private lateinit var enemy17: ImageView
    private lateinit var enemy18: ImageView

    private var enemies: ArrayList<ImageView> = ArrayList()

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setContentView(R.layout.activity_main)

            highScoreTx = findViewById(R.id.highScoreTextView)
            timerTx = findViewById(R.id.timerTextView)

            val dataFlow: Flow<Int> = baseContext.dataStore.data.map { preferences ->
                preferences[key] ?: 0
            }
            CoroutineScope(Dispatchers.IO).launch {
                highScore = dataFlow.first()
                runBlocking(Dispatchers.Main) {
                    highScoreTx.text = "High Score: " + highScore.toString()
                }
            }

            enemy1 = findViewById(R.id.imageView1)
            enemy2 = findViewById(R.id.imageView2)
            enemy3 = findViewById(R.id.imageView3)
            enemy4 = findViewById(R.id.imageView4)
            enemy5 = findViewById(R.id.imageView5)
            enemy6 = findViewById(R.id.imageView6)
            enemy7 = findViewById(R.id.imageView7)
            enemy8 = findViewById(R.id.imageView8)
            enemy9 = findViewById(R.id.imageView9)
            enemy10 = findViewById(R.id.imageView10)
            enemy11 = findViewById(R.id.imageView11)
            enemy12 = findViewById(R.id.imageView12)
            enemy13 = findViewById(R.id.imageView13)
            enemy14 = findViewById(R.id.imageView14)
            enemy15 = findViewById(R.id.imageView15)
            enemy16 = findViewById(R.id.imageView16)
            enemy17 = findViewById(R.id.imageView17)
            enemy18 = findViewById(R.id.imageView18)

            hideEnemies()
            selectDifficulty()
        }
    }

    private fun selectDifficulty() {
        val difficulties = arrayOf(easy, medium, hard)
        var selectedItem = -1

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Choose a difficulty")
        builder.setSingleChoiceItems(difficulties, selectedItem) { _, which ->
            selectedItem = which
        }
        builder.setPositiveButton("Select Difficulty") { _, _ ->

        }
        builder.setCancelable(false)
        val dialog = builder.create()

        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if (selectedItem != -1) {
                difficulty = difficulties[selectedItem]
                Toast.makeText(this, "Difficulty set to $difficulty", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                addEnemies()
            } else {
                Toast.makeText(this, "Please select a difficulty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hideEnemies() {
        enemy1.visibility = View.INVISIBLE
        enemy2.visibility = View.INVISIBLE
        enemy3.visibility = View.INVISIBLE
        enemy4.visibility = View.INVISIBLE
        enemy5.visibility = View.INVISIBLE
        enemy6.visibility = View.INVISIBLE
        enemy7.visibility = View.INVISIBLE
        enemy8.visibility = View.INVISIBLE
        enemy9.visibility = View.INVISIBLE
        enemy10.visibility = View.INVISIBLE
        enemy11.visibility = View.INVISIBLE
        enemy12.visibility = View.INVISIBLE
        enemy13.visibility = View.INVISIBLE
        enemy14.visibility = View.INVISIBLE
        enemy15.visibility = View.INVISIBLE
        enemy16.visibility = View.INVISIBLE
        enemy17.visibility = View.INVISIBLE
        enemy18.visibility = View.INVISIBLE
    }

    private fun addEnemies() {
        enemies.add(enemy7)
        enemies.add(enemy8)
        enemies.add(enemy9)
        enemies.add(enemy10)
        enemies.add(enemy11)
        enemies.add(enemy12)
        if (difficulty == medium || difficulty == hard) {
            enemies.add(enemy1)
            enemies.add(enemy2)
            enemies.add(enemy3)
            enemies.add(enemy4)
            enemies.add(enemy5)
            enemies.add(enemy6)
        }
        if (difficulty == hard) {
            enemies.add(enemy13)
            enemies.add(enemy14)
            enemies.add(enemy15)
            enemies.add(enemy16)
            enemies.add(enemy17)
            enemies.add(enemy18)
        }
        play()
    }

    private fun play() {
        val firstRandomValue = Random.nextInt(0, enemies.size - 1)
        enemies[firstRandomValue].visibility = View.VISIBLE
        for (enemy in enemies) {
            enemy.setOnClickListener {
                score += 1
                // Showing score instead of highscore when game starts
                highScoreTx.text = "Score: " + score.toString()
                if (score % 10 == 0) {
                    if (currentTime > endingTime) {
                        currentTime -= intervalTime
                    }
                }
                enemy.visibility = View.INVISIBLE
                val randomValue = Random.nextInt(0, enemies.size - 1)
                enemies[randomValue].visibility = View.VISIBLE
                startTimer()
            }
        }
    }

    private fun startTimer() {
        timer?.cancel()

        timer = object: CountDownTimer(currentTime, 1) {
            override fun onTick(millisUntilFinished: Long) {
                timerTx.text = millisUntilFinished.toString()
            }

            override fun onFinish() {
                val description: String
                if (score > highScore && difficulty == hard) {
                    description = "New High Score!: " + score.toString()
                    CoroutineScope(Dispatchers.IO).launch {
                        updateHighScore()
                    }
                } else {
                    description = "Final Score: " + score.toString()
                }
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Game Over")
                builder.setMessage(description)
                builder.setCancelable(false)
                builder.create()
                builder.show()
            }
        }

        timer?.start()
    }

    private suspend fun updateHighScore() {
        baseContext.dataStore.edit { highScoreKey ->
            highScoreKey[key] = score
        }
    }
}