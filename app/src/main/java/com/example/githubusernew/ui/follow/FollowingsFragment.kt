package com.example.githubusernew.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.githubusernew.databinding.FragmentFollowingsBinding
import com.example.githubusernew.ui.adapter.FollowAdapter
import com.example.githubusernew.ui.adapter.SearchUserAdapter
import com.example.githubusernew.ui.detailuser.DetailFragment
import com.example.githubusernew.utils.UserViewModelFactory

class FollowingsFragment : Fragment() {

    private val factory: UserViewModelFactory by lazy {
        UserViewModelFactory.getInstance(requireActivity())
    }
    private val viewModel: FollowingsViewModel by viewModels {
        factory
    }

    private var _binding: FragmentFollowingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var followingsAdapter: FollowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingsBinding.inflate(inflater, container, false)
        postponeEnterTransition()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataUsername = arguments?.getString(DetailFragment.EXTRA_USERNAME)

        // setListFollower
        if (dataUsername != null) {
            setListFollowing(dataUsername)
        }

        // getListFollower
        getListFollower()
    }

    private fun setListFollowing(dataUsername: String) {
        viewModel.setListFollowings(dataUsername)
        showLoading(true)
    }

    private fun getListFollower() {
        viewModel.getListFollowings().observe(requireActivity()){result->
            if (result != null) {
                showLoading(false)
                followingsAdapter.setListUser(result)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbFollowing.visibility = View.VISIBLE
        } else {
            binding.pbFollowing.visibility = View.GONE
        }
    }
}