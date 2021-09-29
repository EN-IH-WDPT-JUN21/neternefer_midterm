# neternefer_Midterm

### Description
Basic API implemented with Spring, SpringSecurity and MySQL database

### How to use
1. Clone the repository
```
git clone git@github.com:EN-IH-WDPT-JUN21/neternefer_midterm.git
```
2. Start the program by running MidtermApplication class

3. MySQL database connection credentials
```
   jdbc:mysql://localhost:3306/bankdemo
   username=bankerdemo
   password=pass
```

3. MySQL database connection credentials
```
   jdbc:mysql://localhost:3306/bankdemo
   username=bankerdemo
   password=pass
```

### Status codes
| Status Code | Description |
| :--- | :--- |
| 200 | `OK` |
| 201 | `CREATED` |
| 400 | `BAD REQUEST` |
| 404 | `NOT FOUND` |
| 500 | `INTERNAL SERVER ERROR` |

### Endpoints
| Endpoint | Method | Description | Path Params | Authorization |
| :--- | :--- | :--- | :--- | :---
| /accounts/balance/* | `GET` | Check account balance | None | ACCOUNT_HOLDER, ADMIN
| /accounts | `POST` | Create new accounts | None | ADMIN
| /users | `POST` | Add new users | None | ADMIN
| /users/* | `PATCH` | Update users | `id=[long]` | ADMIN
| /accounts/transfer | `PATCH` | Transfer funds | `id=[long]` | ACCOUNT_HOLDER, THIRD_PARTY


### Security
| Auth Type | Encryption | Roles | 
| :--- | :--- | :--- 
| BasicAuth | Bcrypt | ADMIN, ACCOUNT_HOLDER, THIRD_PARTY

### Class Diagram
![Class diagram](src/main/java/com/ironhack/Midterm/assets/diagram.jpeg?raw=true "Class diagram")


