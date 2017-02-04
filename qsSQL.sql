--
-- Table structure for table `user_account`
--
CREATE TABLE user_account (
   Account_ID INT(5) NOT NULL AUTO_INCREMENT,
   First_Name VARCHAR (20) NOT NULL,
   Middle_Name VARCHAR (20),
   Last_Name VARCHAR (20) NOT NULL,
   Phone_Number VARCHAR (20) NOT NULL,
   Email VARCHAR (40) NOT NULL,
   Time_Sheet_ID INT(5) NOT NULL,
 PRIMARY KEY (Account_ID),
    KEY Sheets (Time_Sheet_ID)
);

--
-- Table structure for table `time_sheet`
--
CREATE TABLE time_sheet (
   Time_Sheet_ID INT(5) NOT NULL AUTO_INCREMENT,
   Title VARCHAR (25) NOT NULL,
   Start_Date DATETIME NOT NULL,
   End_Date DATETIME NOT NULL,
   TS_Entry_ID INT(5) NOT NULL,
 PRIMARY KEY (Time_Sheet_ID),
    KEY Contents (TS_Entry_ID)
);

--
-- Table structure for table `ts_entry`
--
CREATE TABLE ts_entry (
   TS_Entry_ID INT(5) NOT NULL AUTO_INCREMENT,
   Job_Type VARCHAR (20),
   Customer VARCHAR (20) NOT NULL,
   Description VARCHAR (50) NOT NULL,
   Hours INT(2) NOT NULL,
   TS_Date DATE NOT NULL,
 PRIMARY KEY (TS_Entry_ID),
);

