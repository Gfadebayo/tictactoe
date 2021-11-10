package com.exzell.tictactoe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.exzell.tictactoe.R
import com.exzell.tictactoe.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var mBinding: FragmentHomeBinding

    companion object{
        fun getInstance(): HomeFragment{
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentHomeBinding.inflate(inflater).run {
            mBinding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.buttonNew.setOnClickListener {
            requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, GameFragment.getInstance())
                    .addToBackStack(null)
                    .commit()
        }
    }
}