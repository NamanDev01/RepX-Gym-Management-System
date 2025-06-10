RepX Fitness Management System
=============================

RepX is a comprehensive Java-based desktop application designed to streamline fitness management for both gym members and administrators. With an intuitive user interface, RepX allows users to track workouts, nutrition, and health progress, while administrators can manage member data efficiently.

## Features

- *User Authentication*
  - Members and admins can log in securely using their credentials.
  - New members can register, while existing users access personalized dashboards[5][6].

- *Member Dashboard*
  - Members view and manage workout plans, nutrition schedules, and health metrics.
  - The dashboard features a sidebar for easy navigation: Workout Plan, Track Progress, Health Tracker, Nutrition, Profile, and Logout[10].
  - Each section is accessible via a visually appealing, hover-responsive menu.

- *Workout Management*
  - Members can view daily workout plans, including exercises, sets, reps, and completion status.
  - The "Completed" column is editable, allowing users to update progress, and changes are saved to the database[2].

- *Nutrition Tracking*
  - Members can view their daily nutrition plans, including meals, times, calories, and completion status.
  - The "Completed" column allows tracking of meal completion, and progress is saved with a single click[1].

- *Progress Tracking*
  - Members can record and track their weight, height, and BMI over time.
  - The application calculates BMI automatically and displays a history of progress records[3].

- *Admin Dashboard*
  - Admins can view, search, and manage member health details.
  - The dashboard displays member information such as ID, name, age, gender, email, BMI, weight, height, and fitness goals.
  - Admins can search for members by name, ID, or goal using a dedicated search bar[9].

- *Responsive UI*
  - The application features a modern, intuitive interface with hover effects, rounded buttons, and clear navigation[7][10].

- *Data Persistence*
  - All member and admin data is stored in a relational database, ensuring data persistence and security.

## Technical Details

- *Programming Language:* Java
- *GUI Framework:* Java Swing
- *Database:* SQL-based relational database (e.g., MySQL, PostgreSQL)
- *Architecture:* Modular design with separate panels for each major feature[1][2][3]
- *Security:* User credentials are validated against the database (recommended: use password hashing for production)
- *Error Handling:* Basic error handling and user feedback for invalid inputs or database errors

## Installation and Usage

1. *Prerequisites:*
   - Java Development Kit (JDK) 8 or later
   - A compatible SQL database (e.g., MySQL, PostgreSQL)

2. *Setup:*
   - Clone the repository.
   - Configure the database connection in the DBConnection utility class.
   - Import the SQL schema and sample data if provided.

3. *Running the Application:*
   - Compile the Java source files.
   - Run the main entry point (e.g., MemberLoginFrame for members, AdminLoginFrame for admins)[5][6].

4. *User Guide:*
   - *Members:* Log in to access your dashboard, track workouts, nutrition, and progress.
   - *Admins:* Log in to manage member data and view health metrics.
