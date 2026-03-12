ICT3214 - Mobile Application Development-Group Project
Emergency Contact Helper 

Description
Emergency Contact Helper is a safety-focused Android application designed to provide users with quick access to their most important contacts during critical situations.

Features
User Authentication: Secure registration and login system with hashed password storage.
Emergency Contact Management: Full CRUD (Create, Read, Update, Delete) functionality to manage a personal list of contacts.
Quick Dial: A dedicated interface to instantly view and call contacts specifically marked as "Emergency."
SOS Messaging: A one-tap feature that opens the system's messaging app with a pre-configured "Emergency Help Needed!" message.
Session Management: Keeps users logged in across app restarts using local session tracking.

Note ---> In Add contact form Name and Number are requied to add contact but Number length validation is not set (Like number should contain
10 digits) because emergency numbers length can be different.

Technologies
1. Core Language & Framework
Java: The primary programming language for the Android application logic.
Android SDK: The development framework used to build and run the app.

2. Data Storage & Management
SQLite Database: A local relational database for storing User and Contact information.
Logical Data Relations: The contacts table is logically linked to the users table via a user_id column (implementing a one-to-many relationship).
SharedPreferences: Used for Session Management to maintain the user's login state and store the active user_id

3. UI & Frontend Components
XML Layouts: Used to define the user interface and styling 
ListView: The primary component for displaying the list of contacts.
Custom CursorAdapter: Specifically, ContactAdapter is used to bind data from the SQLite database directly to the ListView.
Material Design Components: Using standard Android widgets like Button, EditText, CheckBox, and ImageButton.

4. System Integrations (Intents)
ACTION_DIAL: For the "Quick Dial" and contact call functionality.
ACTION_VIEW (SMS): For the "SOS Message" feature to open the system's messaging app with a pre-filled message.

5. Security & Utilities
Custom Password Hashing: Utilizes a PasswordUtils class to hash passwords before storing them in the SQLite database.
CRUD Operations: Full implementation of Create, Read, Update, and Delete for the emergency contacts.

Team
- H.M.S.S.W. Bandara | ICT/2022/145 | 5946
- R.M.D.S.N. Ranabahu | ICT/2022/151 | 5947
- M.J.H.A.P. Madushani | ICT/2022/142 | 5743

