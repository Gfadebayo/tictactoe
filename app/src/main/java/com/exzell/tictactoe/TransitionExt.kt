package com.exzell.tictactoe

import androidx.transition.Transition

fun Transition.addListener(start: (() -> Unit)? = null,
                           end: (() -> Unit)? = null,
                           cancel: (() -> Unit)? = null,
                           pause: (() -> Unit)? = null,
                           resume: (() -> Unit)? = null){
    addListener(object: Transition.TransitionListener{
        override fun onTransitionStart(transition: Transition) {
            start?.invoke()
        }

        override fun onTransitionEnd(transition: Transition) {
            end?.invoke()
        }

        override fun onTransitionCancel(transition: Transition) {
            cancel?.invoke()
        }

        override fun onTransitionPause(transition: Transition) {
            pause?.invoke()
        }

        override fun onTransitionResume(transition: Transition) {
            resume?.invoke()
        }

    })
}