package com.kmozcan1.lyricquizapp.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
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
            // Programmatically create the buttons and add them to the map
            buttonMap[options[i].id] = MaterialButton(context).apply {
                text = options[i].name
                id = generateViewId()

                Paris.styleBuilder(this).add(R.style.ButtonStyle).apply()

                this@OptionsView.addView(this)

                // Attach to bottom if it's the first button, otherwise the previous button's top if not
                set.run {
                    if (previousButtonId == 0) {
                        connect(
                                id,
                                ConstraintSet.BOTTOM,
                                ConstraintSet.PARENT_ID,
                                ConstraintSet.BOTTOM,
                                0
                        )
                    } else {
                        connect(
                                id,
                                ConstraintSet.BOTTOM,
                                previousButtonId,
                                ConstraintSet.TOP,
                                0
                        )
                    }

                    previousButtonId = id

                    // Right constraint
                    connect(
                            id,
                            ConstraintSet.RIGHT,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.RIGHT,
                            0
                    )

                    // Left constraint
                    connect(
                            id,
                            ConstraintSet.LEFT,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.LEFT,
                            0
                    )

                    // Min height is 200, wrap content if the text is larger
                    // for rap songs that feature like 10 artists
                    constrainMinHeight(id, 200)
                    constrainHeight(id, ViewGroup.LayoutParams.WRAP_CONTENT)

                    set.applyTo(this@OptionsView)
                }
            }.also{ button ->
                // Set onClick listener
                button.setOnClickListener {
                    if (optionButtonClickListener != null) {
                        optionButtonClickListener?.onOptionButtonClicked(options[i].id)
                    }
                }
            }
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

        // Reset color and visibility, set text and height of all buttons
        for (i in options.indices) {
            options[i].let{ artist ->
                buttonList[i].run {
                    setOnClickListener {
                        if (optionButtonClickListener != null) {
                            optionButtonClickListener?.onOptionButtonClicked(options[i].id)
                        }
                    }
                    text = artist.name
                    visibility = VISIBLE
                    backgroundTintList =
                        ContextCompat.getColorStateList(context, R.color.colorDarkRed)
                    minHeight = 200
                    height = LayoutParams.WRAP_CONTENT
                    buttonMap[artist.id] = this
                    isEnabled = true
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