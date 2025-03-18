package co.feip.fefu2025

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.view.LayoutInflater

class AnimeGenreView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val genreNameText: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_anime_genre, this, true)
        genreNameText = view.findViewById(R.id.genre_name)

        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.AnimeGenreView)
        val backgroundColor = typedArray.getColor(R.styleable.AnimeGenreView_backgroundColor, Color.BLACK)
        val genreName = typedArray.getString(R.styleable.AnimeGenreView_genreName) ?: "Жанр"

        this.background = context.getDrawable(R.drawable.anime_genre_background)

        setBackColor(backgroundColor)
        setGenreName(genreName)

        typedArray.recycle()
    }

    fun setGenreName(name: String) {
        genreNameText.text = name
    }

    fun setBackColor(color: Int) {
        val backgroundDrawable = this.background as? GradientDrawable
        backgroundDrawable?.setColor(color)
    }
}