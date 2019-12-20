# Repositories - Android Application

A Github Repository listing application is created using MVVM pattern. Android Jetpack's Architecture Components like `ViewModel`, `LiveData`, `Room` are used. Users can query GitHub to get the repositories of a certain user and add repositories to their favorites list. This application is created for an Android Interview Project where requirements can be found [here](https://github.com/barkinak/Repositories/blob/master/content/Android%20Developer%20Interview%20Project.pdf). It can be tested with the apk file [here](https://github.com/barkinak/Repositories/blob/master/content/app-debug.apk). The diagram below shows the recommended architecture between modules by Android.

<p align="center">
    <img src="https://developer.android.com/topic/libraries/architecture/images/final-architecture.png" width="720" title="Architecture Diagram">
</p>

### View
The UI of the app is demonstrated below and consists of 2 pages. First page contains the list of repositories of the queried GitHub user. The second page opens when the user clicks on a repository showing detailed information on that repository. <br>

Repository list is displayed using a `RecyclerView`. Each repository is displayed within a container called `CardView`. A `SearchView` is added to ActionBar. Using this widget, users can enter the GitHub username they want to query.
<br><br>

<p align="center">
  <img align="center" src="https://github.com/barkinak/Repositories/blob/master/content/Repositories.gif" width="360" title="App GIF">
</p>

<br><br>
Navigation is handled by creating a navigation graph. This implements best practices for navigation and keeps all screens in a single activity. It makes is easy to pass data between screens and simplifies animations. 

### ViewModel
Two ViewModels are created for each Fragment which are `ListFragmentViewModel` and `DetailFragmentViewModel`. They provide data for the UI. The data is being hold using the `LiveData` class which notifies observers about updates. For example, RepoDetailFragment observes DetailFragmentViewModel and gets notified when user goes to the repository details page as shown below. 

```java
// DetailFragmentViewModel class
public MutableLiveData<Repository> mRepository = new MutableLiveData<>();
public void getRepositoryById(int id){
    executor.execute(() -> {
        mRepository.postValue(mAppRepository.getRepositoryById(id));
    });
}

// RepoDetailFragment class
mDetailFragmentViewModel.mRepository.observe(this, repository -> {
    if(repository != null){
        // update UI
    }
});
```

### Model
The `AppRepository` class is actually not part of Architecture components library but is a suggested best practice for code seperation. It abstracts access to multiple data sources. For example, it could be used to decide whether to fetch data from network or use results that are cached locally. It should be a Singleton class by referencing same instance of class from anywhere in the app. This is achieved by having a private constructor to restrict instantiation to the class itself and a static `getInstance()` method that creates a single instance of the class.

<br>

```java
  private AppRepository(Context context) {
      mDb = AppDatabase.getInstance(context);
  }

  public static AppRepository getInstance(Context context){
      if(instance == null){
          instance = new AppRepository(context);
      }
      return instance;
  }
```

<br>

`Room` provides simplified creation and management of SQLite databases. Entity class describes a database table and DAO class is a mapping of SQL queries to functions. It is common to have one DAO class for each Entity class. For example, `updateIsFavorite(int id, boolean b)` function is mapped to the following query below which updates the `is_favorite` field of the repository with the given id. 

<br>

```java
@Query("UPDATE repositories SET is_favorite =:b WHERE id = :id")
int updateIsFavorite(int id, boolean b);
```

<br>

The repositories table have columns representing a Repository object returned from GitHub API. A sample repository is shown below. The `is_favorite` field is set to true when user clicks on the favorite button on `RepoDetailFragment`. When the adapter populates the RecyclerView on `RepoListFragment`, this field is used to determine whether a star icon should be shown or not.<br>

id |repo_id  |name        |description  |stargazers_count|watchers_count|language|avatar_url |user_id |is_favorite
---|---------|------------|-------------|:--------------:|:------------:|--------|-----------|--------|-----------
1  |205407027|Repositories|Android app..|0               |0             |Java    |https://a..|barkinak|true

### Future Work
The application can be further developed in the future, by adding more functionality provided by the GitHub API like statistics of a repository or content of README files. AsyncTask can be replaced with Retrofit 2 or SearchView can have a local cache of previously searched repositories.
