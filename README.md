# Firebase Authentication & Book Management App

This is an Android application developed using **Firebase Authentication** and **Firebase Firestore**.  
The app allows users to register, login, reset password, and manage books with full CRUD functionality.

---

## 🔹 Features

### 🔐 Authentication
- User Registration (Email & Password)
- User Login
- Forgot Password (Email Reset)
- Delete User Account

### 📚 Book Management
- Add Book
- View All Books
- Update Book Details
- Delete Book

Each book contains:
- Title  
- Author  
- ISBN  
- Publication Year  

---

## 🛠 Technologies Used

- Android (Java)
- Firebase Authentication
- Firebase Firestore
- Android Studio

---

## 📂 Project Structure

- `activities/` → Login, Register, Main, AddBook
- `models/` → Book model class
- `firebase/` → Firebase configuration
- `layouts/` → XML UI files

---

## 🔧 Firebase Setup

1. Create a project in Firebase Console  
2. Enable:
   - Email/Password Authentication  
   - Firestore Database  
3. Download `google-services.json`
4. Place it inside the `app/` folder

---

## ▶️ How to Run

1. Clone the repository  
2. Open in Android Studio  
3. Sync Gradle  
4. Run on Emulator or Physical Device  

---

## 👨‍💻 Author

**Muhammad Adnan**  
BSCS Student  
Firebase & Android Developer

---

## 📌 Note

This project is created for academic purposes and demonstrates basic Firebase integration in Android.
