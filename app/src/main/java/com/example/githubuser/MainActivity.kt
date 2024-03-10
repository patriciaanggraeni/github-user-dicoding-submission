package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubuser.data.remote.repository.UserRepositoryImpl
import com.example.githubuser.data.remote.response.SearchResult
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.databinding.UserItemBinding
import com.example.githubuser.domain.model.User
import com.example.githubuser.presentation.viewmodel.UserViewModel
import com.example.githubuser.presentation.view.adapter.UserAdapter
import com.example.githubuser.presentation.viewmodel.UserViewModelFactory
import com.example.githubuser.util.FunctionHelper
import com.example.githubuser.util.capitilizeFirst
import com.facebook.shimmer.ShimmerFrameLayout

class MainActivity : AppCompatActivity(), FunctionHelper {

    private lateinit var binding: ActivityMainBinding

    private lateinit var recyclerview: RecyclerView
    private lateinit var shimmer: ShimmerFrameLayout

    private lateinit var userViewModel: UserViewModel
    private lateinit var userViewModelFactory: UserViewModelFactory
    private lateinit var userAdapter: UserAdapter<User, UserItemBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
        setupComponents()

        userViewModel.getAllUsers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar, menu)
        val searchItem = menu?.findItem(R.id.search)
        val searchView: SearchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Cari Pengguna..."

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { userViewModel.searchUser(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?) = true
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                Toast.makeText(this, "Go to Setting", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun initComponents() {
        super.initComponents()

        shimmer = binding.shimmer
        recyclerview = binding.userRecyclerview

        userViewModelFactory = UserViewModelFactory(application, UserRepositoryImpl())
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
                Toast.makeText(this, "Hello, ${user.login}", Toast.LENGTH_LONG).show()
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

        userViewModel.users.observe(this) { result ->
            binding.progressBar.visibility = View.GONE
            shimmer.stopShimmer()
            shimmer.visibility = View.GONE

            result.onSuccess { users ->
                userAdapter.loadUsers(users)
            }

            result.onFailure { exception ->
                Toast.makeText(this, "Error loading users: ${exception.message}", Toast.LENGTH_LONG).show()
            }
        }

        userViewModel.searchResults.observe(this) { result ->
            result.onSuccess { searchResult ->
                userAdapter.loadUsers(searchResult.items)
            }

            result.onFailure {
                result.onFailure { exception ->
                    Toast.makeText(this@MainActivity, "Pengguna Tidak Ditemukan: ${exception.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        recyclerview.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
    }
}