# Expense Tracker

A Java full-stack application for tracking personal expenses, featuring a JavaFX desktop GUI and a structured backend for managing budgets and transactions.

##  Overview

Expense Tracker is designed to help users log, categorize, and monitor their daily expenses. The project follows a clean separation of concerns with dedicated directories for design, implementation, testing, and maintenance.

##  Features

-  **Expense Logging** – Record and categorize expenses
-  **Budget Tracking** – Monitor spending against set budgets
-  **JavaFX GUI** – Simple, interactive desktop interface
-  **Input Validation** – Ensures only valid data is accepted
-  **Fail-Safe Mechanisms** – Robust error handling in the controller layer
-  **Test Coverage** – Dedicated testing directory for unit tests

##  Project Structure

```
expense-tracker/
├── Design/                          # UI/UX designs and wireframes
├── Implementation/
│   └── expense_tracker/             # Main application source code
├── Maintanance/                     # Maintenance utilities and fail-safes
├── Problem_descrption/              # Problem statement and requirements
├── Testing/                         # Unit and integration tests
└── .gitignore
```

### Directory Details

| Directory | Purpose |
|-----------|---------|
| `Design/` | Contains UI mockups and design documentation for the JavaFX interface |
| `Implementation/expense_tracker/` | Core application logic, including models, controllers, and views |
| `Maintanance/` | Fail-safe mechanisms and controller refactoring utilities |
| `Problem_descrption/` | The initial problem statement and requirements specification |
| `Testing/` | Test cases covering input validation and core functionality |

##  Tech Stack

- **Language:** Java (100%)
- **GUI Framework:** JavaFX
- **Build Tool:** *(Add Maven/Gradle if applicable)*
- **Version Control:** Git

##  Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or higher
- JavaFX SDK (if not bundled with your JDK)
- An IDE such as IntelliJ IDEA, Eclipse, or VS Code
- *(Optional)* Maven or Gradle if configured

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/daniel-oyoo/expense-tracker.git
   cd expense-tracker
   ```

2. **Open the project** in your preferred Java IDE.

3. **Configure JavaFX** in your IDE's run configuration if required.

4. **Build the project**
   ```bash
   # If using Maven
   mvn clean install

   # If using Gradle
   ./gradlew build
   ```

### Running the Application

1. Navigate to `Implementation/expense_tracker/`
2. Run the main application class (e.g., `Main.java` or `ExpenseTrackerApp.java`)
3. The JavaFX window will launch, allowing you to add and track expenses.

##  Testing

Tests are located in the `Testing/` directory. Run them using your IDE's test runner or via the build tool:

```bash
# Maven
mvn test

# Gradle
./gradlew test
```

##  Contributing

Contributions are welcome! To contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

##  Roadmap

- [ ] Add persistent data storage (SQLite / file-based)
- [ ] Implement expense categories and filtering
- [ ] Add monthly/yearly expense reports and charts
- [ ] User authentication for multi-user support
- [ ] Export data to CSV/PDF

##  Author

**Daniel Oyoo**
- GitHub: [@daniel-oyoo](https://github.com/daniel-oyoo)

##  License

This project currently has no license specified. If you intend to use or distribute this code, please contact the author.

---

 If you find this project useful, consider giving it a star!
