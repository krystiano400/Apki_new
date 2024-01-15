package com.example.starwars.details
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.starwars.R
import com.example.starwars.ui.theme.StarWarsTheme

class DetailsActivity : AppCompatActivity() {

private val viewModel: DetailsViewModel by viewModel()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            val id: String = intent.getStringExtra("id") ?: ""

            setContent {
                StarWarsTheme {


                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        CharacterDetails(viewModel,id)

                    }
                }
            }

        }
    }

@Composable
fun CharacterDetails(viewModel: DetailsViewModel, id: String) {
    val characterInfo by viewModel.immutablePersonDetails.observeAsState()

    LaunchedEffect(id) {
        viewModel.getData(id)
    }

    if (characterInfo?.isLoading == true) {
        // Display loading indicator
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
                .padding(16.dp)

        )
    } else {
        // Display character details
        characterInfo?.data?.let { details ->
            val characterInfoMap = mapOf(
                "Hero" to details.name,
                "Gender" to details.gender,
                "Birth year" to details.birth_year,
                "Eye color" to details.eye_color,
                "Skin color" to details.skin_color
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                characterInfoMap.forEach { (key, value) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = key, fontWeight = FontWeight.Bold)
                        if (value != null) {
                            Text(text = value)
                        }
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
        } ?: run {
            // Handle the case when data is null (error case)
            Text(
                text = "Error loading character details",
                color = Color.Red,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)

            )
        }
    }
}
