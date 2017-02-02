create database android_api /** Creating Database **/
 
use android_api /** Selecting Database **/
 
create table users(
   id int(11) primary key auto_increment,
   unique_id varchar(23) not null unique,
   name varchar(50) not null,
   email varchar(100) not null unique,
   encrypted_password varchar(80) not null,
   salt varchar(10) not null,
   created_at datetime,
   updated_at datetime null
); /** Creating Users Table **/


--
-- Table structure for table `employee`
--
CREATE TABLE employee (
   Employee_ID INT(11) NOT NULL AUTO_INCREMENT,
   First_Name VARCHAR (20) NOT NULL,
   Middle_Name VARCHAR (20),
   Last_Name VARCHAR (20) NOT NULL,
   Phone_Number VARCHAR (20) NOT NULL,
   Account_ID INT(11) NOT NULL,
 PRIMARY KEY (Employee_ID),
    KEY Users (Account_ID)
);

--
-- Table structure for table `time_sheet`
--
CREATE TABLE time_sheet (
   Time_Sheet_ID INT(11) NOT NULL AUTO_INCREMENT,
   Hours INT(11) NOT NULL,
   _Date DATE NOT NULL,
   _Month VARCHAR (15) NOT NULL,
   Job_Type VARCHAR (20) NOT NULL,
   Customer VARCHAR (20) NOT NULL,
   Description VARCHAR (20) NOT NULL,
   Employee_ID INT(11) NOT NULL,
 PRIMARY KEY (Time_Sheet_ID),
    KEY Employee (Employee_ID)
);

--
-- Table structure for table `account`
--
CREATE TABLE account (
   Account_ID INT(11) NOT NULL AUTO_INCREMENT,
   Account_Type VARCHAR (15) NOT NULL,
   Username VARCHAR (20) NOT NULL,
   Password VARCHAR (20) NOT NULL,
 PRIMARY KEY (Account_ID)
);