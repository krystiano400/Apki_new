package com.example.starwars;
import MainViewModel
import UiState
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.starwars.R
import com.example.starwars.details.DetailsActivity
import com.example.starwars.ui.theme.StarWarsTheme
import kotlin.random.Random
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getData()
        setContent {
            StarWarsTheme {


                Scaffold(
                    topBar = {

                               MyTopView(viewModel = viewModel)
                            },

//                    content = {
//                        PeopleList(viewModel, onClick = { id -> navigateToDetails(id) })
//                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = { }) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }
                ){innerPadding->
                    Column (
                        modifier = Modifier
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                    ){
                        PeopleList(viewModel, onClick = { id -> navigateToDetails(id)})
                    }

                }
            }
        }
    }

    fun navigateToDetails(id: String ) {
        val detailsIntent = Intent(this, DetailsActivity::class.java)
        detailsIntent.putExtra("id", id)
        startActivity(detailsIntent)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopView(viewModel: MainViewModel) {
    var searchText by remember { mutableStateOf("") }

    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = searchText,
        onQueryChange = { wpisywanyTekst -> searchText = wpisywanyTekst },
        onSearch = { viewModel.updateFilterQuery(it) },
        placeholder = { Text(text = "Wyszukaj...") },
        active = false,
        onActiveChange = { },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        },
        trailingIcon = {
            Image(
                modifier = Modifier.clickable {
                    searchText = ""
                    viewModel.updateFilterQuery("")
                },
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear"
            )
        }
    ) {

    }
}

@Composable
fun CharacterInfoTile(name: String , gender:String, birth_year:String, onClick:()-> Unit) {


    val characterInfo = mapOf(
        "Hero" to name,
        "Gender" to gender,
        "Birth year" to birth_year


    )

    Column(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center



    ) {

        characterInfo.forEach { (key, value) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = key, fontWeight = FontWeight.Bold)
                Text(text = value)
            }
        }




        Spacer(modifier = Modifier.height(16.dp))

        val array = arrayOf(
            R.drawable.stawars1,
            R.drawable.stawars2,
            R.drawable.stawars3,
            R.drawable.stawars4,
            R.drawable.stawars5
        )
        val index = (0..4).random()
        Image(
            painter = painterResource(id = array[index]), // You may want to replace this with an appropriate image for the character
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        )
    }
}

@Composable
fun PeopleList(viewModel: MainViewModel, onClick: (String) ->Unit) {
    val query by viewModel.filterQuery.observeAsState("")


    //val people by viewModel.immutablePeopleData.observeAsState(emptyList())
    val uiState by viewModel.immutablePeopleData.observeAsState()


    when {
        uiState?.isLoading == true -> {

            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
            )

        }
        uiState?.error != null -> {
            Toast.makeText(LocalContext.current, "${uiState!!.error}", Toast.LENGTH_SHORT).show()
        }
        else -> {
            uiState?.data?.let { restpeopleList ->
                restpeopleList.filter { it.name.contains(query, true) }.let { peopleList ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(peopleList) { index, people ->
                        CharacterInfoTile(
                            name = people.name,
                            gender = people.gender,
                            birth_year = people.birth_year,
                            onClick = {onClick((index+1).toString())}
                        )
                    }
                }

            }
        }
    }}}