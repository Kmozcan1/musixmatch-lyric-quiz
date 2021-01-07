package com.kmozcan1.lyricquizapp.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.kmozcan1.lyricquizapp.R
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.ArtistDomainModel


/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */

class OptionsView : ConstraintLayout {



    private var optionButtonClickListener: OptionButtonClickListener? = null
    private val buttonList = mutableListOf<Button>()
    var buttonsAreSet: Boolean = false

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
            val button = Button(context)
            button.text = options[i].name
            button.id = generateViewId()
            this.addView(button)

            // Set onClickListener
            button.setOnClickListener {
                if (optionButtonClickListener != null) {
                    optionButtonClickListener?.onOptionButtonClicked(button.text.toString())
                }
            }

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
            buttonsAreSet = true
            set.constrainHeight(button.id, 200)
            set.applyTo(this)
            buttonList.add(button)
        }
    }

    fun renameOptionButtons(options: List<ArtistDomainModel>) {
        for (i in buttonList.indices) {
            buttonList[i].refreshDrawableState()
            buttonList[i].text = options[i].name
        }
    }

    // Listener to inform fragment
    interface OptionButtonClickListener {
        fun onOptionButtonClicked(artistName: String)
    }

    fun setOptionButtonClickListener(optionButtonClickListener: OptionButtonClickListener?) {
        this.optionButtonClickListener = optionButtonClickListener
    }
}