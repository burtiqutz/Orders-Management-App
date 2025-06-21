# Orders Management 

Java desktop application for managing client orders in a warehouse using a MySQL database.

## Features
- Add, edit, delete, and view clients and products 
- Create product orders by selecting clients and products
- Automatically update stock; display under-stock warnings
- Generate a bill (as a Java record) and log it to the database

## Tech & Design
- Implemented using **JavaFX** for the graphical user interface
- MySQL database 
- Java Reflection for dynamic DB operations and table rendering
- Java Streams and Lambdas for data processing
- JavaDoc comments and generated docs
- Layered architecture: 
  - `model`, `businessLayer`, `dataAccessLayer`, `presentation`

## Data Persistence
- SQL dump file provided to create and populate tables
- Bill data stored as immutable records, only inserted and read (no update)

## Included
- Java source files
- UML diagrams (`draw.io`)
- JavaDoc files
- SQL dump file
