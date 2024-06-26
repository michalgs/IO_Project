package com.example.io_project.ui.screens.dialogs

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.io_project.Constants.BUSY_INDICATOR
import com.example.io_project.Constants.DATE_FORMATTER_PATTERN
import com.example.io_project.dataclasses.WeatherForecast
import com.example.io_project.datamanagement.getIcon
import com.example.io_project.ui.components.DatePickerCustom
import com.example.io_project.ui.components.DropDownPicker
import com.example.io_project.ui.components.TimePickerCustom
import com.example.io_project.ui.dialogs.AssistantViewModel
import com.example.io_project.ui.theme.IO_ProjectTheme
import com.example.io_project.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun AssistantDialog(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {

    val context: Context = LocalContext.current
    val assistantViewModel: AssistantViewModel = hiltViewModel()

    var state by remember { mutableIntStateOf(0) } // można zmieniać żeby zobaczyć podgląd danego stanu
    // 0 -> pytanie czy użytkownik ma pomysł na aktywność
    // 1 -> użytkownik nie ma pomysłu na aktywność
    // 2 -> użytkownik ma pomysł na aktywność (wybór kategorii)
    // kiedy użytkownik wybierze kategorię to przechodzimy do wyboru jak w state == 1
    // 3 -> pytanie o preferencje co do terminu
    // 4 -> brak preferencji co do terminu
    // 5 -> użytkownik ma preferencje do terminu (date picker)
    // 6 -> pytanie o zaproszenie kogoś
    // 8 -> pytanie o potwierdzenie dodania aktywności
    // 9 -> aktywność dodana
    Dialog(
        onDismissRequest = {
            navigateBack()
        }
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(
                text = "Asystent",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = modifier.padding(8.dp))

            when (state) {
                0 -> {
                    Text(
                        text = "Czy masz już pomysł na aktywność?",
                        fontSize = 14.sp
                    )
                    Spacer(modifier = modifier.padding(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = modifier
                            .fillMaxWidth()
                    ) {
                        Button(
                            onClick = { state = 2 },
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text(
                                text = "Tak",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        Button(
                            onClick = {
                                state = 1
                                assistantViewModel.getRandomSuggestions()
                            },
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text(
                                text = "Nie",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }

                1 -> {
                    Text(text = "Proponowane aktywności:", fontSize = 14.sp)
                    for (i in 0..3) {
                        Row(
                            modifier = modifier
                                .padding(top = 6.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            Button(
                                onClick = {
                                    state = 3
                                    assistantViewModel.chooseSuggestion(
                                        assistantViewModel.suggestionDisplayList[i]
                                    )
                                },
                                modifier = modifier
                                    .size(height = 40.dp, width = 200.dp),
                                shape = RoundedCornerShape(8.dp)
                            )
                            {
                                Text(
                                    text = assistantViewModel.suggestionDisplayList[i].name, // nazwa aktywności numer i
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                        }
                    }
                    Row(
                        modifier = modifier
                            .padding(top = 10.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        var refreshedCount by remember { mutableStateOf(0) }
                        Button(
                            onClick = {
                                assistantViewModel.getRandomSuggestions()
                                refreshedCount += 1
                            },
                            enabled = refreshedCount < 3,
                            modifier = modifier
                                .size(height = 40.dp, width = 120.dp),
                            shape = RoundedCornerShape(8.dp)
                        )
                        {
                            Text(
                                text = "Odśwież",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }

                2 -> {
                    Text(text = "Wybierz kategorię:", fontSize = 14.sp)
                    var pickedCategory by remember{
                        mutableStateOf("Inne")
                    }
                    val categories =
                        listOf("Szkoła", "Inne", "Praca", "Aktywność fizyczna", "Znajomi")
                    var isEnabled by remember { mutableStateOf(true) }
                    DropDownPicker(
                        onValueChange = {
                            pickedCategory = it
                            isEnabled = true
                        },
                        argList = categories,
                        label = "Kategoria"
                    )
                    Spacer(modifier = modifier.padding(10.dp))
                    Button(
                        onClick = {
                            assistantViewModel.getSuggestionsByCategory(pickedCategory)
                            state = 1
                        },
                        enabled = isEnabled,
                        modifier = modifier
                            .size(height = 40.dp, width = 120.dp),
                        shape = RoundedCornerShape(8.dp)
                    )
                    {
                        Text(
                            text = "Zatwierdź",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                3 -> {
                    Text(
                        text = "Czy masz preferencje co do terminu?",
                        fontSize = 14.sp
                    )
                    Spacer(modifier = modifier.padding(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = modifier
                            .fillMaxWidth()
                    ) {
                        Button(
                            onClick = { state = 5 },
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text(
                                text = "Tak",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        Button(
                            onClick = { state = 4 },
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text(
                                text = "Nie",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }

                4 -> {
                    val todayDate = LocalDate.now()
                    val formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER_PATTERN, Locale.ENGLISH)
                    Text(
                        text = "Wybierz termin:",
                        fontSize = 14.sp
                    )
                    Spacer(modifier = modifier.padding(8.dp))
                    for (i in 0..3) {
                        val nextDate = todayDate.plusDays(i.toLong()).format(formatter)
                        val nextTime = assistantViewModel.getFreeTime(nextDate)

                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .shadow(elevation = 2.dp, shape = RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.inverseOnSurface)
                                .clickable {
                                    if (nextTime != BUSY_INDICATOR) {
                                        assistantViewModel.changeDate(nextDate)
                                        assistantViewModel.changeTime(nextTime)
                                        state = 6
                                    }
                                }
                                .fillMaxWidth()
                                .padding(dimensionResource(id = R.dimen.padding_small))
                        ) {
                            Text(
                                text = "$nextTime $nextDate",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                WeatherForecast.codes[i]?.let {
                                    Image(
                                        painter = painterResource(
                                            id = getIcon(it)
                                        ),
                                        contentDescription = "Ikona pogody",
                                        modifier = modifier
                                            .size(48.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                                WeatherForecast.temperatures[i]?.let {
                                    Text(
                                        text = "$it°C",
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                }
                            }
                        }
                        Spacer(modifier = modifier.padding(4.dp))
                    }
                }

                5 -> {
                    var datePicked by remember { mutableStateOf(false) }
                    var timePicked by remember { mutableStateOf(false) }
                    Text(text = "Wybierz datę i godzinę:", fontSize = 14.sp)
                    Row() {
                        DatePickerCustom(
                            onValueChange = {
                                assistantViewModel.changeDate(it)
                                datePicked = true
                            }, //changeDate
                            label = "Data",
                            modifier = Modifier.weight(2f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TimePickerCustom(
                            onValueChange = {
                                assistantViewModel.changeTime(it)
                                timePicked = true
                            }, //changeTime
                            label = "Godzina",
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = modifier.padding(10.dp))
                    Button(
                        onClick = {
                            state = 6
                        },
                        enabled = datePicked && timePicked,
                        modifier = modifier
                            .size(height = 40.dp, width = 120.dp),
                        shape = RoundedCornerShape(8.dp)
                    )
                    {
                        Text(
                            text = "Zatwierdź",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                6 -> {
                    Text(
                        text = "Czy chcesz zaprosić znajomych?",
                        fontSize = 14.sp
                    )
                    Spacer(modifier = modifier.padding(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = modifier
                            .fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                assistantViewModel.changeVisible(true)
                                state = 8
                            },
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text(
                                text = "Tak",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        Button(
                            onClick = { state = 8 },
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text(
                                text = "Nie",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }

                7 -> {
                    /*TODO*/
                }

                8 -> {
                    Text(
                        text = "Czy na pewno chcesz dodać aktywność?",
                        fontSize = 14.sp
                    )
                    Spacer(modifier = modifier.padding(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = modifier
                            .fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                assistantViewModel.submitSuggestedEvent()
                                Toast.makeText(context, "Dodano wydarzenie", Toast.LENGTH_SHORT)
                                    .show()
                                navigateBack()
                            },
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text(
                                text = "Tak",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        Button(
                            onClick = { navigateBack() },
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text(
                                text = "Nie",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }

                9 -> {
                    // addEvent
                    navigateBack()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssistantDialogPreview() {
    IO_ProjectTheme {
        AssistantDialog(navigateBack = {})
    }
}