package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubuser.data.remote.repository.UserRepositoryImpl
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.databinding.UserItemBinding
import com.example.githubuser.domain.model.User
import com.example.githubuser.presentation.viewmodel.UserViewModel
import com.example.githubuser.presentation.view.adapter.UserAdapter
import com.example.githubuser.util.UserViewModelFactory
import com.example.githubuser.util.capitilizeFirst

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerview: RecyclerView
    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter<User, UserItemBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = UserViewModelFactory(application, UserRepositoryImpl())
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]
        recyclerview = binding.userRecyclerview

        binding.progressBar.visibility = View.VISIBLE

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

            result.onSuccess { users ->
                userAdapter.loadUsers(users)
            }

            result.onFailure { exception ->
                Toast.makeText(this, "Error loading users: ${exception.message}", Toast.LENGTH_LONG).show()
            }
        }

        userViewModel.getAllUsers()

        recyclerview.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
    }
}