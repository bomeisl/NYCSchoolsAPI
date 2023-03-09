This app functions as a frontend client for the official NYC pubic school data web API.


It is designed according to MVVM architecture, fully automatically dependency injected by Hilt, is a single Activity pure Kotlin, pure Compose built app, and caches the last successful network call in
a Room database in case the end user of the app has a flaky or no internet connection at all.


An outine of the app and its features is as follows:


I. Data Layer:

A. |Room Database|
Each successful network call with the Ktor client to the NYC public school web API endpoint that retrieves JSON data first
deserializes it, then converts it to a list of network layer data classes to represent the network data. Each network data
class is then converted to a database layer data class to represent Room database data to be cached in the App database through
a utility function in my models. The database is the sole source of truth for the state of the app at all layers. State flows from
the App database through the Daos, to the Repositories, to the View Model, and finally is fed to the UI and collected as State.
If the network connection fails for any reason, the app doesn't skip a beat and there is no inturruption of state. The database simply
is not refreshed and continues to feed state to the UI based on the last successful network call. A Singleton instance of the App
database is provided in the Application context by Hilt through my Hilt Module.

B. |Network Data Sources|
Ktor based clients, CIO web engines, and GSON deserializers retrieve and deserialize JSON from the web API endpoint containing 
NYC public school data. This network fetch can be triggered by the user manually from the UI (say by swiping down on the screen 
to refresh it or pressing a refresh button) and is also triggered by the init block of the appropriate view model. The results of
a successful network call then refresh the database and that new updated state is immediately emitted to other layers of the app by 
Flow. In this way, we can ensure that the app is kept up to date with the current data on NYC public schools.

C. |School Repository|
Mediates the business logic between the web API endpoint, on one hand, and the App database on the other as sources of the 
'sole source of truth' of the app and decides how to keep that source of truth up to date and emitting to appropriate subscribers.
As previously discussed, the App database is this source of truth and the logic and methods through which the network clients may retrieve
new information from the API endpoint and update the database is exposed through the repositories. An immutable Flow of data from the 
database is also exposed by the repository

D. |SAT Repository|
Works similarly to the School Repository


II. UI Layer

A. |Home View Model|
Provides a state holder for the Home Composable and exposes an immutable state flow to be consumed by the home Composable with
mutable backing property that is private to the view model and can only be mutated within that class by the methods it has defined.
Also defines the events by which a user can trigger updates and events in other layers.

B. |Details View Model|
Similar to the home view model, but for SAT scores and takes a Compose navigation parameter argument to uniquely identify which school 
it should provide the SAT scores for.

C. |Compose Navigation Host Composable with Navigation Graph|
This is a single Activity app that hosts one Screen Level Navigation Host Composable in which the Navigation Host Controller and
the Nav Graph sit. All navigation is done by simply changing which Composable is being rendered or 'composed' within the scaffolded 
content slot through the Jetpack Compose-Navigation library. All view models are initialized in this host composable and each subsidiary
composable is fed the correct state and callbacks from each view model by this host composable according to their lifecycles.
