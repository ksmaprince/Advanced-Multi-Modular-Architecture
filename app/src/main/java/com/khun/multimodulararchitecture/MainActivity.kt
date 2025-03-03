package com.khun.multimodulararchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.khun.datastore.settings.AppSettings
import com.khun.datastore.settings.AppSettingsSerializer
import com.khun.datastore.settings.Language
import com.khun.datastore.settings.Location
import com.khun.multimodulararchitecture.ui.theme.MultiModularArchitectureTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
  lateinit var appSettingDataStore: DataStore<AppSettings>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    appSettingDataStore = DataStoreFactory.create(
      serializer = AppSettingsSerializer(),
      produceFile = { dataStoreFile("app_settings.json") },
      scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    )

    enableEdgeToEdge()
    setContent {
      MultiModularArchitectureTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//          Greeting(
//            name = MapProvider.mapId,
//            modifier = Modifier.padding(innerPadding),
//          )
          SettingsScreen(appSettingDataStore, Modifier.padding(innerPadding))
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    Text(
      text = "Base Url: ${BuildConfig.BASE_URL}!",
      modifier = modifier,
    )
    Text(
      text = "DB Version: ${BuildConfig.DB_VERSION}!",
      modifier = modifier,
    )
    Text(
      text = "Can Clear Cache: ${BuildConfig.CAN_CLEAR_CACHE}!",
      modifier = modifier,
    )
    Text(
      text = "Map Key: ${BuildConfig.MAP_KEY}!",
      modifier = modifier,
    )
  }
}

@Composable
fun SettingsScreen(appSettingDataStore: DataStore<AppSettings>, modifier: Modifier) {
  val scope = rememberCoroutineScope()
  val appSettings by appSettingDataStore.data.collectAsState(initial = AppSettings())
  Column(modifier = Modifier.padding(50.dp)) {
    // display saved language
    Text(text = "Language: " + appSettings.language)
    Spacer(modifier = Modifier.height(16.dp))

    // display saved locations
    Text(text = "Last known locations: ")
    appSettings.lastKnownLocations.forEach { location ->
      Spacer(modifier = Modifier.height(16.dp))
      Text(text = "Lat: ${location.lat} Lng: ${location.long}")
    }
    Spacer(modifier = Modifier.height(16.dp))
    val newLocation = Location(37.123, 122.908)

    // create drop down menu to display language options and set location as well
    Language.entries.forEach { language ->
      DropdownMenuItem(text = { Text(text = language.name) }, onClick = {
        scope.launch {
          appSettingDataStore.updateData { currentSettings ->
            currentSettings.copy(
              language = language,
              lastKnownLocations = currentSettings.lastKnownLocations.add(
                newLocation,
              ),
            )
          }
        }
      })
    }
  }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  MultiModularArchitectureTheme {
    Greeting("Android")
  }
}
