# Dicoding Events

Dicoding Events is an Android application that provides information about upcoming and finished events from Dicoding. Users can explore various events, view event details, bookmark their favorite events, and receive daily notifications for the nearest upcoming events.

## Features

- **Home**: Discover a summary of upcoming and finished events.
- **Upcoming Events**: View a list of scheduled events.
- **Finished Events**: Browse through events that have already taken place.
- **Search**: Easily find events by name.
- **Event Details**: Get comprehensive information including description, time, quota, and direct links to the event page.
- **Bookmark**: Save events locally to access them later, even offline.
- **Dark Mode**: Support for system-wide dark theme.
- **Daily Reminder**: Daily notifications at 08:00 AM for the next upcoming event.

## Tech Stack & Libraries

- **Language**: [Kotlin](https://kotlinlang.org/)
- **Architecture**: MVVM (Model-View-ViewModel)
- **UI Framework**: View Binding, Material Design Components
- **Networking**: [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/)
- **Database**: [Room](https://developer.android.com/training/data-storage/room) (Local Persistence)
- **Image Loading**: [Glide](https://github.com/bumptech/glide)
- **Background Tasks**: [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) (for Daily Reminders)
- **Dependency Management**: Gradle Version Catalog (libs.versions.toml)
- **Navigation**: [Jetpack Navigation Component](https://developer.android.com/guide/navigation)
- **Data Persistence**: [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) (for Settings)
- **Other**: LiveData, Lifecycle-aware components

## API Reference

This app consumes the Dicoding Event API:
`https://event-api.dicoding.dev/`

## Project Structure

```text
com.gabsee.dicodingevents
├── data
│   ├── local           # Room Database, DAOs, Entities, DataStore
│   ├── remote          # Retrofit Service, API Config, DTOs
│   └── repository      # Single source of truth for data
├── ui
│   ├── adapter         # RecyclerView Adapters
│   ├── screen          # Fragments (Home, Upcoming, Finished, Detail, etc.)
│   └── viewmodel       # ViewModels & ViewModelFactory
├── utils               # Helper classes and Mappers
└── worker              # WorkManager implementations
```

## Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/DicodingEvents.git
   ```
2. **Open the project** in Android Studio.
3. **Build and Run** the application on an emulator or physical device.

## Screenshots

<p align="center">
  <img src="demo.gif" width="300" title="Application Demo">
</p>

## License

```text
Copyright (c) 2024 Gabsee

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
