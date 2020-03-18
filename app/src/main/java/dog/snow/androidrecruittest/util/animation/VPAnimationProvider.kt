package dog.snow.androidrecruittest.util.animation

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import dog.snow.androidrecruittest.R
import javax.inject.Inject

class VPAnimationProvider
@Inject constructor(
    private val context: Context
) {

    fun comeInToRight(): Animation = AnimationUtils.loadAnimation(context, R.anim.come_in_right)

    fun comeInToLeft(): Animation = AnimationUtils.loadAnimation(context, R.anim.come_in_left)

    fun slideUp(): Animation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
}