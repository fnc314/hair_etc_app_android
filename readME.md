# Hair Etc App for Android

## App Description

This app is a growing port of the [HairEtcApp](https:://hairetcapp.herokuapp.com/) written natively for the Android OS.  As of **Monday March 31, 2014** the app is a `WebView` that requires Android 4.4 KitKat to render the proper mobile stylings.  If a lower version of Android is running the application then the standard `WebView` will not be able to support the extra stylings.  However, basic functionality will not be affected.

The app allows direct access to the HairEtcApp, giving users the ability to sign in or sign up for the service, create/edit appointments, and view appointment history.  Due to clever server-side view management, the mobile app is optimized for speed by only loading the small HTML files necessary for functionality and display.

The backbone of this app is a Ruby on Rails application hosted on Heroku whose source code can be found in [this repository](https://github.com/fnc314/hair_etc_app).

## Experience

I learned a lot about the different options present in Eclipse for Android development.  I utilized the Android development community on [Reddit](http://www.reddit.com/r/android) and [YouTube](https://www.youtube.com/) for help and tutorials to make sure my app would work.  There were many [Google](https://www.google.com/) searches that also showed me how to route the Eclipse emulator to the `localhost` server I was running on my machine.  This, coupled with Google Chrome DevTools, made the visual styling (using [Bootstrap CSS](http://www.getbootstrap.com/)) infinitely easier.  My studies also revealed the vast difference in developing with `WebView` on any version of Android that is 4.3 or earlier and Android 4.4 KitKat.

Of course, none of this work would be possible without [Tim Garcia](https://github.com/tigarcia) and his infinite support.