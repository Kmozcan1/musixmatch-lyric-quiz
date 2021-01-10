package com.kmozcan1.lyricquizapp.presentation.ui

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.airbnb.paris.Paris
import com.google.android.material.button.MaterialButton
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.domain.model.ArtistDomainModel


/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */

class OptionsView : ConstraintLayout {



    private var optionButtonClickListener: OptionButtonClickListener? = null
    private val buttonMap = mutableMapOf<Int, MaterialButton>()
    var buttonsAreSet: Boolean = false
    var buttonsAreEnabled: Boolean = false
    private lateinit var correctButton: MaterialButton

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        inflate(context, R.layout.view_options, this)
    }

    fun setOptionButtons(options: List<ArtistDomainModel>) {
        val set = ConstraintSet()
        set.clone(this)
        var previousButtonId = 0
        for (i in options.indices) {
            val button = MaterialButton(context)
            button.text = options[i].name
            button.id = generateViewId()

            Paris.styleBuilder(button).add(R.style.ButtonStyle).apply()

            this.addView(button)

            // Attach to bottom if it's the first button, otherwise the previous button's top if not
            if (previousButtonId == 0) {
                set.connect(
                    button.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM,
                    0
                )
            } else {
                set.connect(
                    button.id,
                    ConstraintSet.BOTTOM,
                    previousButtonId,
                    ConstraintSet.TOP,
                    0
                )
            }

            previousButtonId = button.id

            set.connect(
                button.id,
                ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.RIGHT,
                0
            )
            set.connect(
                button.id,
                ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.LEFT,
                0
            )

            buttonMap[options[i].id] = button

            // Set onClickListener
            button.setOnClickListener {
                if (optionButtonClickListener != null) {
                    optionButtonClickListener?.onOptionButtonClicked(options[i].id)
                }
            }

            set.constrainHeight(button.id, 200)
            set.applyTo(this)
        }
        buttonsAreSet = true
        buttonsAreEnabled = true
    }

    fun showCorrectAnswer(selectedArtistId: Int?, correctArtistId: Int) {
        buttonMap[correctArtistId]?.backgroundTintList =
            ContextCompat.getColorStateList(context, R.color.colorCorrectOption)

        // Hide all but the correct and selected buttons
        for ((id, button) in buttonMap) {
            if (id != selectedArtistId && id != correctArtistId) {
                button.visibility = INVISIBLE
            }
        }
    }

    fun refreshOptionButtons(options: List<ArtistDomainModel>) {
        val buttonList = mutableListOf<MaterialButton>()
        // Refill the buttonMap with the new set of artistIds (used for showing the correct answer)
        for ((_, button) in buttonMap) {
            buttonList.add(button)
        }
        buttonMap.clear()
        for (i in options.indices) {
            options[i].let{ artist ->
                buttonList[i].let { button ->
                    button.setOnClickListener {
                        if (optionButtonClickListener != null) {
                            optionButtonClickListener?.onOptionButtonClicked(options[i].id)
                        }
                    }
                    button.text = artist.name
                    button.visibility = VISIBLE
                    button.backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.colorDarkRed)
                    buttonMap[artist.id] = button
                    button.isEnabled = true
                    refreshDrawableState()
                }
            }
        }
        buttonsAreEnabled = true
    }

    // Listener to inform fragment
    interface OptionButtonClickListener {
        fun onOptionButtonClicked(artistId: Int)
    }

    fun setOptionButtonClickListener(optionButtonClickListener: OptionButtonClickListener?) {
        this.optionButtonClickListener = optionButtonClickListener
    }
}