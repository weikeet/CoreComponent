<h1 align="center">Bundler</h1>

## Usage

### Intent

`intentOf` is an expression for creating an Intent using Kotlin DSL style and we can put extras using the `putExtra` method. Also, we can put extras using the `+` keyword in front of a key/value pair.

```kotlin
val intent = intentOf {
  putExtra("posterId", poster.id) // put a Long type 'posterId' value.
  putExtra("posterName" to poster.name) // put a String type 'posterName' value.
  putExtra("poster", poster) // put a Parcelable type 'poster' value.

  +("id" to userInfo.id) // put a Long type 'id' value.
  +("name" to userInfo.nickname) // put a String type 'name' value.

  -"name" // remove a String type 'name' value.
}
```

### StartActivity

We can start activities using the `intentOf` expression like below.

```kotlin
intentOf<SecondActivity> {
  putExtra("id" to userInfo.id)
  putExtra("name" to userInfo.nickname)
  putExtra("poster", poster)
  startActivity(this@MainActivity)
}
```

We can also use other options for creating an intent.

```kotlin
intentOf {
  setAction(Intent.ACTION_MAIN)
  addCategory(Intent.CATEGORY_APP_MUSIC)
  setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
  startActivity(this@MainActivity)
}
```

### bundle

`bundle` is an expression for initializing lazily extra values from the intent.

```kotlin
class SecondActivity : AppCompatActivity() {

  private val id: Long by bundle("id", -1) // initializes a Long extra value lazily.
  private val name: String by bundle("name", "") // initializes a String extra value lazily.
  private val poster: Poster? by bundle("poster") // initializes a Parcelable extra value lazily.

// -- stubs -- //
```

We can initialize a Parcelable value with a defaut value.

```kotlin
private val poster: Poster? by bundle("poster") { Poster.create() }
```

Also, we can initialize type of Array and ArrayList using `bundleArray` and `bundleArrayList` expression.

```kotlin
// initialize lazily without default values.
private val posterArray by bundleArray<Poster>("posterArray")
private val posterListArray by bundleArrayList<Poster>("posterArrayList")

or

// initialize lazily with default values.
private val posterArray by bundleArray<Poster>("posterArray") { arrayOf() }
private val posterListArray by bundleArrayList<Poster>("posterArrayList") { arrayListOf() }
```

### bundle in Fragment

The below example shows setting arguments using the `intentOf` expression.

```kotlin
arguments = intentOf {
  +("id" to userInfo.id)
  +("name" to userInfo.nickname)
  +("poster" to poster)
}.extras
```

We can initialize argument values lazily in Fragments using the `bundle` expression like below.

```diff
- val id: Long = arguments?.getLong("id", -1) ?: -1
+ val id: Long by bundle("id", -1)
- val poster: Poster? = arguments?.getParcelable("poster")
+ val poster: Poster? by bundle("poster")
```

### bundleNonNull

The `bundle` expression for initializing objects (e.g. Bundle, CharSequence, Parcelable, Serializable, Arrays), the property type must be null-able. But If we want to initialize them non-nullable type, we can initialize them to non-nullable type using the `bundleNonNull` expression.

```diff
- val poster: Poster? by bundle("poster")
+ val poster: Poster by bundleNotNull("poster")
```

### observeBundle

We can observe the bundle data as `LiveData` using the `observeBundle` expression. If there are no extra & arguments in the Activity or Fragment, `null` will be passed to the observers. The `observeBundle` emits data only a single time to a single observer. So We can observe only once using one observer. And the observer will be unregistered from the LiveData after observing data at once.

```kotlin
private val id: LiveData<Long> by observeBundle("id", -1L)
private val poster: LiveData<Poster> by observeBundle("poster")

id.observe(this) {
  vm.id = it
}

poster.observe(this) {
  binding.name = it.name
}
```

### bundleValue

We can also retrieve intent & arguments extra values from Activity and Fragment immediately. We can use `bundleValue`, `bundleNonNullValue`, `bundleArrayValue`, `bundleArrayListValue`.

```kotlin
val id = bundleValue("id", 100L)
val name = bundleValue("name", "")
val poster = bundleValue<Poster>("poster")
```

## License

```
Copyright 2021 Weicools

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
