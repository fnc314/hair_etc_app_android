# Hair Etc App for Android

### Background

Originally this app was generated with a `WebView` and special styling sheets for the mobile view.  This method is inefficient because `WebView`s are rendered using Chromium on Android 4.4+ and a WebKit based rendering engine on earlier versions of Android.  Therefore, using `WebView` would lead to an inconsistent experience for mobile device users.  To improve the performance and standardize the user experience, the app was re-written as a native Android application using the same Rails server and PostgreSQL database.

The webapp for this Android application can be viewed [here](https://hairetcapp.herokuapp.com/).

### A User's Experience

There is a simple user flow for this project.  Upon loading the app the user is greeted with a splash screen (currently set to a scenic photo of the Milky Way Galaxy) and then directed to the log-in page.  The user inputs their credentials and, assuming everything checks out on the server, is directed to a single button.  This button launches the form that allows users to create a new appointment.  Upon completion of creating the appointment, users submit the information to the Rails server.  Once the data has been written to the database, and the required text messages sent out, users are redirected to the single button for creating new appointments.

### Some Key Nitty Gritty Details

Because this application has to make calls to the Rails server, there are many instances of `AsyncTask` sub-classes through-out most of the activities.  This includes user sign-in and and appointment creation.  Due to the unique nature of Android-to-server communications, it was best to namespace all of the routes that Android needs and make them respond exclusively with/to JSON objects.  This approach allows the Rails server to have separate concerns and be able to handle both HTML communications from browsers and JSON requests from Android simultaneously.

Since my Rails server uses Devise to manage users and Devise does not use Authentication Tokens, it was necessary to add such a feature independently.  Therefore, on the Rails server there are a few methods that automatically creates (and ensures uniqueness of) such tokens:

```ruby
before_save :ensure_authentication_token!

  def generate_secure_token_string
    SecureRandom.urlsafe_base64(25).tr('lIO0', 'sxyz')
  end

  def ensure_authentication_token!
    if authentication_token.blank?
      self.authentication_token = generate_authentication_token
    end
  end

  def generate_authentication_token
    loop do
      token = generate_secure_token_string
      break token unless Client.where(authentication_token: token).first
    end
  end
```

These tokens are used to validate users and find the correct client upon appointment creation.  They are stored locally on the Android device

```java
FileOutputStream fos = openFileOutput(APP_TOKEN, Context.MODE_PRIVATE);
  fos.write(result.getBytes());
  fos.write(">>".getBytes());
  fos.write(CLIENT_EMAIL.getBytes());
  fos.close();
```
and sent in the HTTP request header to the server with each request.

```java
  // clientAuth is a String[] with the token and client email inside
  httpPost.setHeader("Accept", "application/json");
  httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
  httpPost.setHeader("X-CLIENT-EMAIL", clientAuth[1]);
  httpPost.setHeader("X-CLIENT-TOKEN", clientAuth[0]);
```
Without this token, contact with the server is not possible and results in an error `Toast` being shown on the Android application.

### Future Expansions

In later versions of this app, users will be able to apply all CRUD operations on their appointments (as they can on the Web application).  Users will be able to sign up for an account through the app as well.  There will then be a complete stylistic overhaul after all functionality is fully built.

### Related Links

Completion of this Android app required the use of the following resources:

- [YouTube](https://www.youtube.com/)
- [Google](https://www.google.com/)
- [Reddit](http://www.reddit.com/r/android)
- [Tim Garcia](https://github.com/tigarcia)

The Ruby on Rails application repository can be found [here](https://github.com/fnc314/hair_etc_app).