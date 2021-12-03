package com.exzell.tictactoe.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.exzell.tictactoe.viewmodel.GameViewModel
import com.exzell.tictactoe.viewmodel.MainViewModel
import com.exzell.tictactoe.R
import com.exzell.tictactoe.addListener
import com.exzell.tictactoe.databinding.DialogWinBinding
import com.exzell.tictactoe.databinding.FragmentGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GameFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    companion object{
        const val LEVEL_X = 10

        const val LEVEL_Y = 110

        fun getInstance(): GameFragment{
            return GameFragment()
        }
    }

    private lateinit var mBinding: FragmentGameBinding

    private lateinit var mViewModel: GameViewModel

    private val mMainViewModel: MainViewModel by lazy { ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java) }

    private var mCurrentLevel = LEVEL_X

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TransitionInflater.from(requireContext()).apply {
            inflateTransition(R.transition.frag_enter).let {
                enterTransition = it
                reenterTransition = it
            }

            inflateTransition(R.transition.frag_exit).let {
                exitTransition = it
                returnTransition = it
            }
        }

        mViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
                .get(GameViewModel::class.java)

        PreferenceManager.getDefaultSharedPreferences(requireContext())
                .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentGameBinding.inflate(inflater).run {
            mBinding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.apply {
            val listener = {v: View -> onBoxClicked(v as ImageView)}

            box1.setOnClickListener(listener)
            box2.setOnClickListener(listener)
            box3.setOnClickListener(listener)
            box4.setOnClickListener(listener)
            box5.setOnClickListener(listener)
            box6.setOnClickListener(listener)
            box7.setOnClickListener(listener)
            box8.setOnClickListener(listener)
            box9.setOnClickListener(listener)

        }
    }

    private fun onBoxClicked(view: ImageView){
        if(view.drawable != null) return

        view.setImageResource(R.drawable.level_piece)
        view.setImageLevel(mCurrentLevel)

        mMainViewModel.playSound(mCurrentLevel)

        val id = view.id

        val addon = if(id == R.id.box1) 1
        else if(id == R.id.box2) 2
        else if(id == R.id.box3) 3
        else if(id == R.id.box4) 4
        else if(id == R.id.box5) 5
        else if(id == R.id.box6) 6
        else if(id == R.id.box7) 7
        else if(id == R.id.box8) 8
        else 9

        val gameOver = mViewModel.checkIfWin(mCurrentLevel, addon)

        if(gameOver) displayWinAnimationAndShowDialog()

        mViewModel.addMove(mCurrentLevel, addon)

        if(!gameOver) switchLevel()
    }

    private fun displayWinAnimationAndShowDialog() {
        setWinLineRotationAndTranslation(mViewModel.winningMove1)
        animateWinningLine()
    }

    private fun setWinLineRotationAndTranslation(winPair: Pair<Int, Int>){
        with(mBinding.winLine){

            val translation = requireContext().resources.getDimension(R.dimen.win_line_translation)

            //default 0 in both already does win move 4 5 6, so nothing is performed

            if(winPair == Pair(1, 2)){
                translationY = 0-translation

            }else if(winPair == Pair(1, 4)){
                rotation = 90F
                translationX = 0-translation

            }else if(winPair == Pair(1, 5)){
                rotation = 45F

            }else if(winPair == Pair(2, 5)){
                rotation = 90F

            }else if(winPair == Pair(3, 5)){
                rotation = 135F

            }else if(winPair == Pair(3, 6)){
                rotation = 90F
                translationX = translation

            }else if(winPair == Pair(7, 8)){
                translationY = translation
            }
        }
    }

    private fun animateWinningLine() {

        mBinding.winLine.post {
            mBinding.winLine.visibility = View.VISIBLE

            val measuredWidth = mBinding.winLine.measuredWidth

            mBinding.winLine.updateLayoutParams { width = 0 }

            val bound = ChangeBounds()
            bound.addTarget(mBinding.winLine)
            bound.duration = 3000
            bound.startDelay = 30
            bound.addListener(start = { mBinding.winLine.visibility = View.VISIBLE },
                    end = { showWinDialog() })

            TransitionManager.beginDelayedTransition(mBinding.root, bound)

            mBinding.winLine.updateLayoutParams { width = measuredWidth }
        }
    }

    private fun showWinDialog(){
        val dialogView = DialogWinBinding.inflate(requireActivity().layoutInflater)
        dialogView.imagePiece.post { dialogView.imagePiece.setImageLevel(mCurrentLevel) }

        MaterialAlertDialogBuilder(requireContext())
                .setView(dialogView.root)
                .setOnDismissListener { requireActivity().supportFragmentManager.popBackStackImmediate() }
                .show()
    }

    private fun switchLevel(){
        mCurrentLevel = if(mCurrentLevel == LEVEL_X) LEVEL_Y else LEVEL_X
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().getPreferences(Context.MODE_PRIVATE)
                .unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(pref: SharedPreferences, key: String?) {
    }
}