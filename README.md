# Glance

Glance is an open source Android database toolkit library which can help you browser all the database data with clean UI interface.

Glance is inspired by LeakCanary to be included into your project as dependency library. It will search both internal and external storage of current app to find out all the database files automatically.

Glance provides a clean UI interface to display all the data in the database, and make the database debugging work very easy.

You can experience it right now with below dependency.

```groovy
dependencies {
    // debugImplementation because Glance should only run in debug builds.
    debugImplementation 'com.glance.guolindev:glance:1.0.0-alpha02'
}
```

After adding the dependency to your project, you will see A new Glance icon on your launcher.

<img src="screenshots/1.png" width="38%" />

Click it to browse all the database data of your app. It's quite easy.

The below animation shows how it works.

<img src="screenshots/2.gif" width="38%" />

Note that Glance only supports AndroidX projects. Android-Support projects can't use this library, and have no plan to support for them in the future release either.

## License

```
Copyright (C) guolin, Glance Open Source Project

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
