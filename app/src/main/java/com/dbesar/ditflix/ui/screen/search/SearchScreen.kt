@file:OptIn(ExperimentalMaterial3Api::class)

package com.dbesar.ditflix.ui.screen.search

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dbesar.ditflix.R
import com.dbesar.ditflix.ui.component.SearchItem

/**
 * Created by Dede Dari Rahmadi on 26/11/24
 */

@Composable
fun SearchScreen(
    searchState: SearchState,
    onSearchClick: (query: String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val animatePadding by animateDpAsState(
        targetValue = if (expanded) 0.dp else 16.dp, label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = animatePadding, end = animatePadding),
            inputField = {
                SearchBarDefaults.InputField(
                    query = text,
                    onQueryChange = {
                        text = it
                    },
                    onSearch = {
                        onSearchClick(text)
                    },
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = it

                    },
                    placeholder = { Text("Search by title") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (expanded) {
                            Icon(
                                modifier = Modifier.clickable {
                                    if (text.isNotEmpty()) {
                                        text = ""
                                    } else {
                                        expanded = false
                                    }

                                },
                                imageVector = Icons.Default.Clear,
                                contentDescription = null
                            )
                        }

                    }
                )
            },
            expanded = expanded,
            onExpandedChange = {
                expanded = it
            },
        ) {
            Spacer(Modifier.height(8.dp))
            if (searchState.isLoading) {
                Text(stringResource(R.string.searching), modifier = Modifier.padding(start = 16.dp, end = 16.dp))

            }
            if (searchState.error != null) {
                Text(stringResource(R.string.error, searchState.error), modifier = Modifier.padding(start = 16.dp, end = 16.dp))
            }

            if(searchState.movies.isNotEmpty()){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp),
                ) {
                    items(searchState.movies){ movie ->
                        SearchItem(movie = movie)
                        Spacer(Modifier.height(4.dp))
                    }
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        if(searchState.movies.isNotEmpty()){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp),
            ) {
                items(searchState.movies){ movie ->
                    SearchItem(movie = movie)
                    Spacer(Modifier.height(4.dp))
                }
            }
        }
    }

}




