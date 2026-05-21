# Java Chat Application

A simple multi-client chat application built with Java using **Socket Programming**, **Swing GUI**, and **SQLite Database**.

The application supports:

- User Registration & Login
- Real-time Messaging
- File Sharing
- Persistent Message Storage using SQLite
- Multi-client communication through a server

---

# Features

## Authentication
- User registration
- User login
- SQLite database for storing user accounts

## Chat System
- Real-time text messaging
- Multiple users connected simultaneously
- Broadcast messaging to all connected clients

## File Sharing
- Send files between connected users
- Automatically saves received files into a `downloads/` folder

## GUI
- Built using Java Swing
- Simple and user-friendly interface

---

# Technologies Used

- Java 17
- Java Swing
- Java Socket Programming
- SQLite
- Maven

---

# Project Structure

```text
Chat bot/
│
├── src/main/java/chatapp/
│   ├── Client.java
│   ├── ClientHandler.java
│   ├── Database.java
│   └── Server.java
│
├── chat.db
├── pom.xml
└── downloads/
```

---

# How It Works

## Server
The server:
- Starts on port `5000`
- Accepts multiple client connections
- Creates a new thread for every client
- Broadcasts messages/files to connected users

## Client
The client:
- Connects to the server
- Allows login or registration
- Opens a chat window after successful login
- Sends and receives messages/files

## Database
SQLite stores:
- User accounts
- Chat messages

---

# Requirements

Make sure you have installed:

- Java JDK 17+
- Maven

Check versions:

```bash
java -version
mvn -version
```

---

# Installation & Setup

## 1. Clone or Extract Project

```bash
git clone <repository-url>
```

Or extract the ZIP file.

---

## 2. Navigate to Project Folder

```bash
cd "Chat bot"
```

---

## 3. Build the Project

```bash
mvn clean install
```

---

# Running the Application

## Start the Server

Run:

```bash
mvn exec:java -Dexec.mainClass="chatapp.Server"
```

You should see:

```text
Database ready.
Server started on port 5000...
```

---

## Start the Client

Open another terminal and run:

```bash
mvn exec:java -Dexec.mainClass="chatapp.Client"
```

You can open multiple clients to test chatting.

---

# Usage

## Register
1. Open client
2. Choose `REGISTER`
3. Enter username and password

## Login
1. Open client
2. Choose `LOGIN`
3. Enter credentials

## Send Messages
- Type a message
- Click `Send`

## Send Files
- Click `Send File`
- Choose a file
- File is shared with other users

---

# Database Tables

## users

| Column | Type |
|---|---|
| id | INTEGER |
| username | TEXT |
| password | TEXT |

---

## messages

| Column | Type |
|---|---|
| id | INTEGER |
| sender | TEXT |
| message | TEXT |
| time | TIMESTAMP |

---

# Important Notes

- The server must be running before clients connect.
- Files received are saved in:

```text
downloads/
```

- Current implementation uses localhost:

```java
new Socket("localhost", 5000);
```

To use across different machines, replace `localhost` with the server IP address.

---

# Future Improvements

Possible enhancements:
- Private messaging
- Encryption/security
- Online user list
- Better UI design
- Chat history viewer
- Password hashing
- File upload progress
- Group chats

---
# Screenshots

## Login Screen
![Login Screen](screenshoot/Screenshot%202026-05-21%20094215.png)

## Registration Screen
![Registration Screen](screenshoot/Screenshot%202026-05-21%20094227.png)

## Chat Window
![Chat Window](screenshoot/Screenshot%202026-05-21%20094238.png)

## File Sharing
![File Sharing](screenshoot/Screenshot%202026-05-21%20100153.png)

## Multiple Clients
![Multiple Clients](screenshoot/Screenshot%202026-05-21%20100252.png)



Java Socket Programming Chat Application project.
