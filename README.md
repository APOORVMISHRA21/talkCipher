<div align="center"><h1>talkCipher</h1></div>
<h1><b>Overview</b></h1>
As the Internet evolved, so did the privacy issues, resulting in it becoming one of the most
important concerns for a user before putting hands on any of the applications in front.<br>
<b>talkCipher</b> is an android based chat application offering end-to-end encryption of
messages to the user based on his/her choice of encryption methods, assuring ultimate
safety, with absolutely lovable UI for best experience.

<h1>Features</h1>

  * Splash Screen
  * User Authentication through mobile number OTP Verification.
  * One to one chat facility.
  * Panel to seek list of users with username, profile picture and last message of conversation(if any) displayed.
  * Panel to chat, with interface properties similar to Whatsapp, displaying 
      * two different chat bubbles to differentiate between sender and reciever.
      * time of message sent is also displayed.
  * End to end encryption of chats.
  * Database management to store chats(in Encrypted form).
  * Panel to provide choice amongst different Encryption methods to secure each message.
  * Panel to upload and update Profile Picture from device.
  * Offline data persistency enabled.
  
<h1>Implementation</h1>
Below mentioned are the highlights of our codebase, touching all the major aspects of this project.<br><br>

  * <b>User Authentication</b><br>
      This project uses <b><i>[FirebaseAuth](https://firebase.google.com/docs/auth)</i></b> package for authentication using mobile number and OTP Verification.
      SignUp page is used to authenticate the details and push it to the database user node if verified.
      SignIn page matches the input details with that in database and provide access to account after OTP Verification if such user exists.<br><br>
  * <b>Chat & Cipher Fragment</b><br>
      The code has been written with a try of using best practises, hence the different tabs mentioned are implemented using tab layout with each tab details using <b><i>[Fragments](https://developer.android.com/guide/fragments)</i></b>.
      The details of previous chats are displayed in <b><i>[RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview)</i></b>.<br><br>
  * <b>Load Images using Picasso</b><br>
      Picasso provides a hassle-free image loading facility to your application. This project uses this to display profile pictures.
      [Click here](https://square.github.io/picasso/) to read more about Picasso.<br><br>
  * <b>AES, DES and RSA Implementation for encryption</b><br>
    * <b>AES: </b>Advanced Encryption Standard aka Rijndael is a symmetric key-based approach of data encryption.
           This application uses <b><i>[AES](https://docs.oracle.com/cd/E17904_01/apirefs.1111/e10668/oracle/security/crypto/core/AES.html)</i></b> class of Java for implentation of AES Algorithm.<br>
    * <b>DES: </b>Digital Encryption Standard is also a symmetric-key block cipher algorithm used for data encryption. It accepts the plain text in 64-bit blocks and changes it to the cipher text that uses the 64-bit keys to encrypt the data. It uses same key for encryption and decryption.
            The algorithm functionalities are achieved using <b><i>[DES](https://docs.oracle.com/cd/E17904_01/apirefs.1111/e10668/oracle/security/crypto/core/DES.html)</i></b> class of Java.<br>
    * <b>RSA: </b>It is an asymmetric encryption algorithm, that is, two different keys are responsible for encryption and decryption of data.
            The algorithm functionalities are achieved using <b><i>[RSA](https://docs.oracle.com/cd/E17904_01/apirefs.1111/e10668/oracle/security/crypto/core/RSA.html)</i></b> class of Java.<br><br>
  * <b>Update Profile Picture</b>
      <b><i>[FirebaseStorage](https://firebase.google.com/docs/storage)</i></b> is used to store the profile pictures with offline capabilities enabled thus reducing the rendering time of pictures.<br><br>

<h1>Package, Platforms and Resources Used</h1>

  * [Android Studio](https://developer.android.com/studio) as the development environment.<br>
  * [Android Documentation](https://developer.android.com/docs).
  * [Firebase](https://firebase.google.com/docs) for authentication, storage and database.
  * [Circular Image View](https://github.com/hdodenhof/CircleImageView)
  * [Picasso](https://square.github.io/picasso/).
  * [Chat bubbles layout](https://stackoverflow.com/questions/7974847/android-drawable-speech-bubble).
  
<br><br><div align="center"><h1><i>Hit the ⭐ if you liked the project</i></h1></div>
  
