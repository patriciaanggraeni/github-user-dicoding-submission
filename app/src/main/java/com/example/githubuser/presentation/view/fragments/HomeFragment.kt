package com.example.githubuser.presentation.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubuser.R
import com.example.githubuser.data.remote.repository.UserRepositoryImpl
import com.example.githubuser.databinding.FragmentHomeBinding
import com.example.githubuser.databinding.UserItemBinding
import com.example.githubuser.domain.model.User
import com.example.githubuser.presentation.view.adapter.UserAdapter
import com.example.githubuser.presentation.viewmodel.UserViewModel
import com.example.githubuser.presentation.viewmodel.UserViewModelFactory
import com.example.githubuser.util.FunctionHelper
import com.example.githubuser.util.capitilizeFirst
import com.facebook.shimmer.ShimmerFrameLayout

class HomeFragment : Fragment(), FunctionHelper {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerview: RecyclerView
    private lateinit var shimmer: ShimmerFrameLayout

    private lateinit var userViewModel: UserViewModel
    private lateinit var userViewModelFactory: UserViewModelFactory
    private lateinit var userAdapter: UserAdapter<User, UserItemBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        initComponents()
        setupComponents()

        userViewModel.getAllUsers()

        return binding.root
    }

     @Deprecated("Deprecated in Java")
     override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar, menu)
        val searchItem = menu?.findItem(R.id.search)
        val searchView: SearchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Cari Pengguna..."

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isEmpty()) {
                        userViewModel.getAllUsers()
                    } else {
                        userViewModel.searchUser(it) }
                    }
                return true
            }
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                Toast.makeText(requireContext(), "Go to Setting", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun initComponents() {
        super.initComponents()

        shimmer = binding.shimmer
        recyclerview = binding.userRecyclerview

        userViewModelFactory = UserViewModelFactory(requireActivity().application, UserRepositoryImpl())
        userViewModel = ViewModelProvider(this, userViewModelFactory)[UserViewModel::class.java]
    }

    override fun setupComponents() {
        super.setupComponents()

        binding.progressBar.visibility = View.VISIBLE
        shimmer.startShimmer()

         userAdapter = UserAdapter(
            binding = { inflater, parent, attachToParent ->
                UserItemBinding.inflate(inflater, parent, attachToParent)
            },
            itemClick = { user ->
                Toast.makeText(requireContext(), "Hello, ${user.login}", Toast.LENGTH_LONG).show()
            },
            bindViewHolder = { binding, item, _ ->
                binding.username.text = item.login.capitilizeFirst()
                binding.userAvatar.load(item.avatarUrl) {
                    crossfade(true)
                    error(R.drawable.user_dummy)
                    size(200, 200)
                    transformations(CircleCropTransformation())
                }
            }
        )

        userViewModel.users.observe(viewLifecycleOwner) { result ->
            binding.progressBar.visibility = View.GONE
            shimmer.stopShimmer()
            shimmer.visibility = View.GONE

            result.onSuccess { users ->
                userAdapter.loadUsers(users)
            }

            result.onFailure { exception ->
                Toast.makeText(requireContext(), "Error loading users: ${exception.message}", Toast.LENGTH_LONG).show()
            }
        }

        userViewModel.searchResults.observe(viewLifecycleOwner) { result ->
            result.onSuccess { searchResult ->
                userAdapter.loadUsers(searchResult.items)
            }

            result.onFailure {
                result.onFailure { exception ->
                    Toast.makeText(requireContext(), "Pengguna Tidak Ditemukan: ${exception.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        recyclerview.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }
    }
}