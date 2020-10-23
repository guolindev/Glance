# Glance

Glance is an open source Android database debugging library which is still under developing. The mainly functionality is finished, and will be released soon.

Glance is inspired by LeakCanary to be integrated into projects as dependency library. It will search both internal and external storage of current app to find out all the database files automatically.

Then Glance provides a visual interface to display all the data in the database, and make the database debugging work very easy.

You can experience it right now with below dependency.

```groovy
dependencies {
    // debugImplementation because Glance should only run in debug builds.
    debugImplementation 'com.glance.guolindev:glance:0.9.0'
}
```

After adding the dependency to your project, you will see A new Glance icon on your launcher. Click it to browse all the data in the database of your app. This is quite easy.

The below animation shows how it works.

<img src="screenshots/1.gif" width="38%" />

P.S. Glance only support AndroidX projects. Android-Support projects can't use this library and no plan to support for them in the future release either.