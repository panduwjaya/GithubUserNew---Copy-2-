package com.example.githubusernew.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.githubusernew.databinding.FragmentFollowersBinding
import com.example.githubusernew.ui.adapter.FollowAdapter
import com.example.githubusernew.ui.adapter.SearchUserAdapter
import com.example.githubusernew.ui.detailuser.DetailFragment
import com.example.githubusernew.utils.UserViewModelFactory

class FollowersFragment : Fragment() {

    private val factory: UserViewModelFactory by lazy {
        UserViewModelFactory.getInstance(requireActivity())
    }
    private val viewModel: FollowersViewModel by viewModels {
        factory
    }

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private lateinit var followersAdapter: FollowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        postponeEnterTransition()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // receive data from detail fragment
        val dataUsername = arguments?.getString(DetailFragment.EXTRA_USERNAME)

        // setListFollower
        if (dataUsername != null) {
            setListFollower(dataUsername)
        }

        // getListFollower
        getListFollower()
    }

    private fun setListFollower(dataUsername: String) {
        viewModel.setListFollowers(dataUsername)
        showLoading(true)
    }

    private fun getListFollower() {
        viewModel.getListFollowers().observe(requireActivity()){result->
            if (result != null) {
                showLoading(false)
                followersAdapter.setListUser(result)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbFollower.visibility = View.VISIBLE
        } else {
            binding.pbFollower.visibility = View.GONE
        }
    }

}