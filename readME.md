# Hair Etc App for Android

### Background

Originally this app was generated with a `WebView` and special styling sheets for the mobile view.  This method is inefficient because `WebView`s are rendered using Chromium on Android 4.4+ and a WebKit based rendering engine on any earlier version of Android.  Therefore, using `WebView` would lead to an inconsistent experience for mobile device users.  To improve the performance and standardize the user experience, the app was re-written as a native Android application using the same Rails server and PostgreSQL database.

The webapp for this Android application can be viewed [here](https://hairetcapp.herokuapp.com/).

### A User's Experience

There is a simple user flow for this project.  Upon loading the app the user is greeted with a splash screen (currently set to a scenic photo of the Milky Way Galaxy) and then directed to the log-in page.  The user inputs their credentials and, assuming everything checks out on the server, is directed to a single button.  This button launches the form that allows users to create a new appointment.  Upon completion of creating the appointment, users submit the information to the Rails server.  Once the data has been written to the database, and the required text messages sent out, users are redirected to the single button for creating new appointments.

### The Nitty Gritty Details

Because this application has to make calls to the Rails server, there are many instances of `AsyncTask` sub-classes through-out most of the activities.  This includes user sign-in and and appointment creation.  Due to the unique nature of Android-to-server communications, it was best to namespace all of the routes that Android needs and make them respond exclusively with/to JSON objects.  This approach allowed the Rails server to have separate concerns and be able to handle both HTML communications from browsers and JSON requests from Android simultaneous.

## Description

This app is a growing port of the [HairEtcApp](https://hairetcapp.herokuapp.com/) written natively for the Android OS.  As of **Monday March 31, 2014** the app is a `WebView` that requires Android 4.4 KitKat to render the proper mobile stylings.  If a lower version of Android is running the application then the standard `WebView` will not be able to support the extra stylings.  However, basic functionality will not be affected.

The app allows direct access to the HairEtcApp, giving users the ability to sign in or sign up for the service, create/edit appointments, and view appointment history.  Due to clever server-side view management, the mobile app is optimized for speed by only loading the small HTML files necessary for functionality and display.

The backbone of this app is a Ruby on Rails application hosted on Heroku whose source code can be found in [this repository](https://github.com/fnc314/hair_etc_app).

## Experience

I learned a lot about the different options present in Eclipse for Android development.  I utilized the Android development community on [Reddit](http://www.reddit.com/r/android) and [YouTube](https://www.youtube.com/) for help and tutorials to make sure my app would work.  There were many [Google](https://www.google.com/) searches that also showed me how to route the Eclipse emulator to the `localhost` server I was running on my machine.  This, coupled with Google Chrome DevTools, made the visual styling (using [Bootstrap CSS](http://www.getbootstrap.com/)) infinitely easier.  My studies also revealed the vast difference in developing with `WebView` on any version of Android that is 4.3 or earlier and Android 4.4 KitKat.

Of course, none of this work would be possible without [Tim Garcia](https://github.com/tigarcia) and his infinite support.