show databases;

create database powerhouse;

use powerhouse;

CREATE TABLE User_Types (
    user_type_id varchar(100) PRIMARY KEY ,
    type ENUM('Owner','Administrator','Trainer') NOT NULL
);

CREATE TABLE Users (
    user_Id varchar(100) PRIMARY KEY ,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    user_Type_Id varchar(100),
    registration_Date DATE,
    Status varchar(8) not null,
    FOREIGN KEY (User_Type_Id) REFERENCES User_Types(User_Type_Id)
);

CREATE TABLE Employee (
    employee_id varchar(100) PRIMARY KEY ,
    name VARCHAR(100) NOT NULL,
    contact VARCHAR(50) NOT NULL,
    address VARCHAR(255),
    age INT,
    hire_date DATE NOT NULL,
    position VARCHAR(100) NOT NULL,
    salary DECIMAL(10,2),
    emergency_contact VARCHAR(100)
);

CREATE TABLE Trainer (
    trainer_id varchar(100) PRIMARY KEY ,
    user_id varchar(100) UNIQUE,
    name VARCHAR(100) NOT NULL,
    contact VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    age INT,
    specialization VARCHAR(100),
    certification TEXT,
    hire_date DATE,
    bio TEXT,
    rating DECIMAL(3,2),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE Member (
    member_id varchar(100) PRIMARY KEY ,
    name VARCHAR(100) NOT NULL,
    weight DECIMAL(5,2),
    height DECIMAL(5,2),
    age INT,
    contact VARCHAR(100) NOT NULL,
    emergency_contact VARCHAR(100),
    medical_conditions TEXT,
    fitness_goals TEXT,
    register_date DATE NOT NULL,
    membership_status varchar(8) not null,
    added_by varchar(100),
    FOREIGN KEY (added_by) REFERENCES Users(user_id)
);

CREATE TABLE Payment_Plan (
    plan_id varchar(100) PRIMARY KEY ,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    duration INT NOT NULL,
    description TEXT,
    features TEXT,
    created_date DATE,
    updated_date DATE,
    Status varchar(8) not null
);

CREATE TABLE Payment (
    payment_id varchar(100) PRIMARY KEY ,
    plan_id varchar(100) ,
    member_id varchar(100) NOT NULL,
    payment_date DATETIME NOT NULL,
    paid_value DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    description VARCHAR(255),
    payment_method ENUM('Cash', 'Card', 'Bank Transfer') NOT NULL,
    transaction_id VARCHAR(100),
    receipt_number VARCHAR(50),
    recorded_by varchar(100) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES Member(member_id),
    FOREIGN KEY (recorded_by) REFERENCES Users(user_id),
    FOREIGN KEY (plan_id) REFERENCES Payment_Plan(plan_id)
);

CREATE TABLE Exercise (
    exercise_id varchar(100) PRIMARY KEY ,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    muscle_group VARCHAR(100),
    equipment_needed VARCHAR(255),
    difficulty_level varchar (15),
    video_url VARCHAR(255),
    instructions TEXT,
    added_by varchar(100),
    FOREIGN KEY (added_by) REFERENCES Trainer(trainer_id)
);

table Exercise;

CREATE TABLE Workout_Plan (
    workout_plan_id varchar(100) PRIMARY KEY ,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    difficulty_level ENUM('Beginner', 'Intermediate', 'Advanced'),
    created_by varchar(100) NOT NULL,
    created_date DATE,
    duration_weeks INT,
    FOREIGN KEY (created_by) REFERENCES Trainer(trainer_id)
);

CREATE TABLE Workout_Exercise (
    workout_plan_id varchar(100) ,
    exercise_id varchar(100),
    day_number INT NOT NULL,
    sets INT,
    reps INT,
    duration INT,
    notes TEXT,
    PRIMARY KEY (workout_plan_id, exercise_id),
    FOREIGN KEY (workout_plan_id) REFERENCES Workout_Plan(workout_plan_id),
    FOREIGN KEY (exercise_id) REFERENCES Exercise(exercise_id)
);

CREATE TABLE Diet_Plan (
    diet_plan_id varchar(100) PRIMARY KEY ,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_by varchar(100) NOT NULL,
    created_date DATE,
    calorie_target INT,
    protein_target INT,
    carbs_target INT, 
    fat_target INT,
    notes TEXT,
    FOREIGN KEY (created_by) REFERENCES Trainer(trainer_id)
);

CREATE TABLE Meal (
    meal_id varchar(100) PRIMARY KEY ,
    diet_plan_id varchar(100),
    name VARCHAR(100) NOT NULL,
    description TEXT,
    meal_time VARCHAR(50),
    calories INT,
    protein INT,
    carbs INT, 
    fat INT, 
    recipe TEXT,
    FOREIGN KEY (diet_plan_id) REFERENCES Diet_Plan(diet_plan_id)
);

CREATE TABLE Member_Diet_Plan (
    member_id varchar(100),
    diet_plan_id varchar(100),
    assigned_date DATE NOT NULL,
    end_date DATE,
    notes TEXT,
    assigned_by varchar(100) NOT NULL,
    PRIMARY KEY (member_id, diet_plan_id),
    FOREIGN KEY (member_id) REFERENCES Member(member_id),
    FOREIGN KEY (diet_plan_id) REFERENCES Diet_Plan(diet_plan_id),
    FOREIGN KEY (assigned_by) REFERENCES Users(user_id)
);

CREATE TABLE Member_Workout_Plan (
    member_id varchar(100),
    workout_plan_id varchar(100),
    assigned_date DATE NOT NULL,
    end_date DATE,
    progress INT DEFAULT 0,
    notes TEXT,
    assigned_by varchar(100) NOT NULL,
    PRIMARY KEY (member_id, workout_plan_id),
    FOREIGN KEY (member_id) REFERENCES Member(member_id),
    FOREIGN KEY (workout_plan_id) REFERENCES Workout_Plan(workout_plan_id),
    FOREIGN KEY (assigned_by) REFERENCES Users(user_id)
);

CREATE TABLE Equipment (
    equipment_id varchar(100) PRIMARY KEY ,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    purchase_date DATE,
    cost DECIMAL(10,2),
    quantity int,
    maintenance_schedule TEXT,
    last_maintenance_date DATE,
    status varchar(16),
    added_by varchar(100) NOT NULL,
    FOREIGN KEY (added_by) REFERENCES Users(user_id)
);

CREATE TABLE Attendance (
    attendance_id varchar(100) PRIMARY KEY ,
    member_id varchar(100) NOT NULL,
    check_in DATETIME NOT NULL,
    check_out time,
    recorded_by varchar(100) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES Member(member_id),
    FOREIGN KEY (recorded_by) REFERENCES Users(user_id)
);

CREATE TABLE Membership_Type (
    membership_type_id varchar(100) PRIMARY KEY ,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    duration INT,
    price DECIMAL(10,2) NOT NULL,
    features TEXT,
    Status varchar(8) not null
);

CREATE TABLE Member_Membership (
    member_membership_id varchar(100) PRIMARY KEY,
    member_id varchar(100) NOT NULL,
    membership_type_id varchar(100) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status ENUM('Active', 'Expired', 'Cancelled') DEFAULT 'Active',
    assigned_by varchar(100) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES Member(member_id),
    FOREIGN KEY (membership_type_id) REFERENCES Membership_Type(membership_type_id),
    FOREIGN KEY (assigned_by) REFERENCES Users(user_id)
);

CREATE TABLE Supplier (
    supplier_id VARCHAR(100) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    contact_person VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    company_website VARCHAR(255),
    status VARCHAR(8) NOT NULL
);

show table status where name='Member_Membership';

show columns from Member_Membership;

table Meal;

table Employee;

show tables;