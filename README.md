# Automation Practice Website Testing

This project contains automated test cases for the [Automation Exercise](https://www.automationexercise.com/) website using Java, Selenium WebDriver, TestNG, and MySQL.

## 📁 Project Structure

```
automatinpracticewebsite/
├── automatinpracticewebsite/
│   ├── AppTest.java
│   ├── MySetUps.java
│   └── ...
```

## 🔧 Technologies Used

- Java
- Selenium WebDriver
- TestNG
- MySQL (JDBC)
- Maven (recommended for dependency management)

## 🚀 How to Run

1. **Clone the repository**
2. **Setup the database**:
   - Ensure MySQL is running
   - Import the `classicmodels` database
3. **Configure Database Connection** in `MySetUps.java`:
   ```java
   con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "your_password");
   ```
4. **Run the tests** using TestNG.

## 🧪 Test Cases Included

| Test Case       | Description                                 |
| --------------- | ------------------------------------------- |
| `SingUp`        | Registers a new user using data from the DB |
| `Login`         | Logs in with an existing user               |
| `Searchproduct` | Searches and adds random products to cart   |
| `Checkout`      | Deletes a random item from the cart         |
| `Contactus`     | Submits a contact form with file upload     |
| `Logout`        | Logs out the user                           |

## 🗃️ Database

- Make sure `classicmodels` DB exists
- Table used: `customers`
- Sample fields: `customerNumber`, `contactFirstName`, `contactLastName`, `addressLine1`, etc.

## 📸 Screenshot Path Used

Make sure this path is valid on your machine:

```
**Your File Path**
```

## 💡 Notes

- Make sure the ChromeDriver version matches your Chrome browser version.
- Use WebDriverManager or set system property for `webdriver.chrome.driver` if needed.

---

Happy Testing! 🧪✨
