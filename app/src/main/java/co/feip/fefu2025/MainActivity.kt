package co.feip.fefu2025

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private val genres = listOf(
        "Сёнен" to Color.parseColor("#0000FF"),
        "Сёдзё" to Color.parseColor("#FF1493"),
        "Сэйнен" to Color.parseColor("#FF00FF"),
        "Дзёсэй" to Color.parseColor("#800080"),
        "Исссссссекай" to Color.parseColor("#DC143C")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.add_button)
        val myLayout = findViewById<MyFlexBoxLayout>(R.id.flexbox_layout)

        button.setOnClickListener {
            val (name, color) = genres.random()
            val genreView = AnimeGenreView(this)
            genreView.setGenreName(name)
            genreView.setBackColor(color)
            myLayout.addView(genreView)
        }

    }
}