# ServiceEase Platform 🛠️

A **desktop-based Java application** that connects **service seekers** with **service providers** through a secure, scalable, and user-friendly platform.  
Developed using **Java Swing (WindowBuilder, Eclipse IDE)** with **Microsoft SQL Server (SSMS)** as the backend, and integrated with **JDBC** for seamless database connectivity.  

---

## 📌 Features

- **User Registration & Login**
  - Separate registration for **Customers** and **Service Providers**
  - Secure login with **role-based redirection**
  - Passwords hashed with **SHA-256 + Salt**

- **Service Posting & Management (Providers)**
  - Post services with title, category, description, availability, price, duration
  - Manage services (update, delete, track live status)

- **Service Booking System (Customers)**
  - Search services
  - Book and track services
  - Integrated payment interface

- **Dashboard & Profile Management**
  - Role-specific dashboards (Customer/Provider)
  - View & edit profile (Full Name, Email, Password)
  - Track bookings & posted services

- **Database Reliability**
  - Audit logging with **Triggers (Insert, Update, Delete)**
  - **Non-clustered indexing** for query optimization
  - **Transactions & Concurrency Control** using `UPDLOCK` to prevent race conditions

---

## 📌 Use Cases

- A homeowner booking a plumber to fix a leakage
- A student hiring a math tutor for weekend classes
- An individual booking a makeup artist for a wedding
- A company hiring an event manager for a conference

---

## 📌 Advantages

- Centralized service hub
- Time-saving, user-friendly interface
- Enhanced trust via **verified profiles & reviews**
- Audit trail for accountability

---

## ⚠️ Potential Challenges

- Verifying authenticity of service providers
- Handling disputes between users & providers
- Scalability for large user base

---

## 🛠️ Tech Stack

- **Programming Language:** Java (Swing, WindowBuilder, Eclipse IDE)
- **Database:** Microsoft SQL Server (SSMS)
- **Database Connectivity:** JDBC
- **Security:**
  - SHA-256 hashing with Salt
  - Role-based authentication
- **SQL Features:**
  - Triggers (Insert, Update, Delete → Audit Logging)
  - Stored Procedures & Functions
  - Non-clustered Indexes
  - Concurrency Control using `SERIALIZABLE` + `UPDLOCK`
- **Libraries:**
  - MigLayout (UI Layout)
  - Timing Framework (Animations)
  - MSSQL JDBC Driver

---

## 📂 Folder Structure
```
ServiceEasePlatform/
│
├── README.md # Project description, features, screenshots, run instructions
├── LICENSE # MIT License
├── .gitignore # Ignore unnecessary files (bin/, .classpath, .project, etc.)
│
├── src/ # Java source code files
│ └── com/serviceease/
│ ├── BookedServ.java # Provider: view booked services
│ ├── CM.java # Customer: view/search services
│ ├── CM_Dashboard.java # Customer dashboard (track bookings, provider info, status)
│ ├── CM_EditProfile.java # Customer profile management
│ ├── CM_Payment.java # Customer payment interface
│ ├── Dashboard.java # Provider profile view
│ ├── EditProfile.java # Provider services dashboard (status, updates, availability)
│ ├── Login.java # Login screen with role selection
│ ├── PasswordHash.java # Password hashing (SHA-256 + Salt)
│ ├── Service.java # Service entity & posting
│ ├── Signup.java # Registration form
│ └── (helper classes)
│
├── resources/
│ ├── images/ # Logo, icons, banners
│ └── database/
│ └── sample_db.sql # Sample DB schema
│
├── lib/ # External Libraries (JARs)
│ ├── miglayout-5.3.jar
│ ├── timingframework-1.0.jar
│ ├── mssql-jdbc-12.2.0.jre11.jar
│
├── backup/ # Compiled files (ignored in git)
│
└── docs/
├── screenshots/ # UI Screenshots
│ ├── login_page.png
│ ├── dashboard.png
│ └── ...
├── erd.drawio # ERD diagram
├── eerd.drawio # EERD diagram
└── user_guide.pdf
```

---

## 📸 Screenshots & Demo

- ![Login Page](docs/screenshots/login_page.png)
- ![Dashboard](docs/screenshots/dashboard.png)
- ![Service Booking](docs/screenshots/booking.png)

📹 **Live Demo Video:** [Google Drive Link](https://drive.google.com/your-demo-link) *(to be added)*  
📊 **ERD & EERD Diagrams:** [View Diagrams](docs/erd.drawio)

---

## 🚀 Installation & Setup

### Prerequisites
- JDK 11+
- Eclipse IDE with WindowBuilder
- Microsoft SQL Server (SSMS)
- Required JARs in `lib/`

### Steps
```
1. Clone this repository:
   ```bash
   git clone https://github.com/obaidu11ah/ServiceEasePlatform.git
2. Import project into Eclipse IDE
3. Add external libraries (lib/) to the project build path
4. Restore database from resources/database/sample_db.sql
5. Configure DB connection in JDBC (SQL Server credentials)
6. Run Login.java to start application

```

📜 License

This project is licensed under the MIT License – see the LICENSE file for details.

👨‍💻 Author

Obaid Ullah
🔗 GitHub
