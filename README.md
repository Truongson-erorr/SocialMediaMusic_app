# 🎵 MusicSon

**MusicSon** is a modern Android music streaming application developed using **Kotlin** and **Jetpack Compose**, designed to deliver a smooth, immersive, and personalized music listening experience.

In the era of digital entertainment and on-demand content, MusicSon provides users with a centralized platform to **discover, stream, and manage music** efficiently. The application allows users to explore songs by artists, genres, and charts while enjoying high-quality audio playback with a clean and responsive user interface.

Beyond basic music playback, MusicSon supports **background playing**, **offline downloads**, and **personalized playlists**, enabling users to enjoy their favorite tracks anytime and anywhere. User data and music content are synchronized in real time using **Firebase**, ensuring a seamless and secure experience across sessions.

A key highlight of the application is the **Admin management system**, which allows administrators to manage songs, artists, and users dynamically without updating the app. This makes MusicSon scalable and suitable for real-world music streaming scenarios.

With a **modern UI built entirely with Jetpack Compose**, efficient media playback using **ExoPlayer**, and a well-structured **MVVM architecture**, MusicSon demonstrates best practices in Android development and serves as a strong portfolio project for mobile developers.

# 🎥 Demo Video
*(Add Google Drive / YouTube demo link here)*

# 🧩 Core Features

### 🔐 User Authentication
- Sign up and sign in using **Firebase Authentication**
- Secure access to personalized music content

### 🎶 Music Library
- Browse songs by **genre**, **artist**, and **charts**
- View song details, album artwork, and artist information

### 🎧 Music Player
- Play, pause, next, and previous controls  
- Background playback support  
- Display song metadata and album cover  
- Smooth audio streaming using **ExoPlayer**

### ❤️ Favorites & Playlists
- Mark songs as favorites  
- Create and manage personal playlists  
- Quick access to saved music

### ⬇️ Offline Download
- Download songs for offline listening  
- Enjoy music without an internet connection

### 🧑‍💼 Admin Dashboard
- Add, edit, and delete songs  
- Manage artists and music metadata  
- Manage users and access permissions  

### 🔔 Real-time Data Sync
- Sync music content and user data in real time  
- Powered by **Firebase Firestore**

# 🧰 Technologies Used

| Technology | Description | Purpose in Project |
|-----------|------------|-------------------|
| **Kotlin** | Official Android programming language with concise syntax and strong safety features. | Core language for implementing application logic. |
| **Jetpack Compose** | Modern declarative UI toolkit for Android. | Builds all application screens and UI components. |
| **MVVM Architecture** | Model-View-ViewModel architectural pattern. | Improves scalability, maintainability, and separation of concerns. |
| **Firebase Authentication** | Secure authentication service. | Handles user sign-up and sign-in. |
| **Firebase Firestore** | NoSQL cloud database with real-time updates. | Stores users, songs, playlists, and app data. |
| **Firebase Storage** | Cloud-based file storage. | Stores music files and images securely. |
| **ExoPlayer** | High-performance media playback library. | Handles audio streaming and background playback. |
| **Hilt / Dagger** | Dependency injection framework. | Manages dependencies efficiently. |
| **Navigation Compose** | Navigation framework for Compose. | Handles in-app screen navigation. |
| **Coil** | Image loading library optimized for Compose. | Loads album covers and artist images. |
| **Cloudinary** | Cloud media management platform. | Stores and delivers images efficiently. |
| **Material 3** | Google’s modern design system. | Ensures a clean and consistent UI experience. |

## 📸 Screenshots

Here are some screenshots of the **MusicSon** app:

<table align="center">
  <tr>
    <td><img src="https://res.cloudinary.com/dq64aidpx/image/upload/v1752124073/z6789895816875_427be0a774aec2fcf653d4faeb7119b2_dyh3xw.jpg" width="150"/></td>
    <td><img src="https://res.cloudinary.com/dq64aidpx/image/upload/v1752124074/z6789895829816_7a71bd31e8531994c34e8cf336516528_yubp0y.jpg" width="150"/></td>
    <td><img src="https://res.cloudinary.com/dq64aidpx/image/upload/v1752124074/z6789895840217_45ced197aa5582745146d8763a2b630b_fnvqfv.jpg" width="150"/></td>
    <td><img src="https://res.cloudinary.com/dq64aidpx/image/upload/v1752124074/z6789895856188_3b6a0a67a7155b8aaf8fecb264a82fb8_tqh9oo.jpg" width="150"/></td>
    <td><img src="https://res.cloudinary.com/dq64aidpx/image/upload/v1752124074/z6789895874631_98ccc79994514383f69155188181e631_zudrwk.jpg" width="150"/></td>
  </tr>
  <tr>
    <td><img src="https://res.cloudinary.com/dq64aidpx/image/upload/v1752124074/z6789895868777_584a7b0dcf5ba0607ff3e8c37c023d97_haj5cl.jpg" width="150"/></td>
    <td><img src="https://res.cloudinary.com/dq64aidpx/image/upload/v1752124075/z6789895895518_36705959821be43c537d74c4c9ace213_zw8em2.jpg" width="150"/></td>
    <td><img src="https://res.cloudinary.com/dq64aidpx/image/upload/v1752124074/z6789895901288_46f14ecbff60db9ae153b88c32680d45_ayvnuh.jpg" width="150"/></td>
    <td><img src="https://res.cloudinary.com/dq64aidpx/image/upload/v1752124075/z6789895918212_4c02484489d06ba6b733e18644bbef85_bzsrkx.jpg" width="150"/></td>
    <td><img src="https://res.cloudinary.com/dq64aidpx/image/upload/v1752124075/z6789895930139_43dd9cf0d573c044411a1f6791cc04a4_xhjk80.jpg" width="150"/></td>
  </tr>
</table>

## 📂 Project Structure

```text
.
├── admin/
│   └── screens/
├── data/
│   └── model/
├── navigation/
├── ui/
│   └── theme/
│   └── screens/
├── viewmodel/
└── MainActivity.kt
```

# ▶️ How to Run the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/Truongson-erorr/SocialMediaMusic_app.git
   ```
2. Open the project in **Android Studio**

3. Add your Firebase configuration:
   - Download `google-services.json` from Firebase Console
   - Place it inside the `app/` directory

4. Enable required Firebase services:
   - Authentication (Email/Password)
   - Firestore Database
   - Firebase Storage

5. Sync Gradle dependencies:
   - Click **Sync Now** when prompted by Android Studio

6. Run the application:
   - Choose an emulator or connect a real Android device
   - Click **Run ▶️** in Android Studio

# 🚀 Future Improvements

### 🤖 AI & Personalization
- AI-powered music recommendation based on user listening history
- Smart playlist suggestions using behavior analysis
- Personalized home screen content

### 🎶 Music Experience Enhancements
- Real-time lyrics synchronization with audio playback
- Audio equalizer and sound enhancement options
- Gapless playback and crossfade support

### 🌙 UI / UX & Customization
- Dark Mode support
- Advanced theme and accent color customization
- Improved animations and transitions

### ⬇️ Offline & Performance Optimization
- Smarter offline caching mechanism
- Background downloads for playlists and albums
- Performance optimization for large music libraries

### 🔍 Search & Discovery
- Advanced search with filters by artist, album, genre, and duration
- Smart suggestions while searching
- Recently played and trending sections

### 📊 Analytics & User Insights
- User listening statistics (daily / weekly / monthly)
- Most played songs, artists, and genres
- Listening history timeline

### 🔔 Notifications & Engagement
- Push notifications for new releases and playlists
- Personalized notifications based on user interests
- In-app announcements and updates

### 🔐 Security & Access Control
- Role-based access control for admin features
- Improved authentication security
- Activity logging for admin actions

### 🧪 Testing & Code Quality
- Unit testing for ViewModels and business logic
- UI testing for critical user flows
- Improve code coverage and stability

### 🌐 Cross-Platform Expansion
- Web version support
- iOS support in future releases
- Shared business logic using Kotlin Multiplatform


