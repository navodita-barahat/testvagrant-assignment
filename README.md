Following steps to be followed for local execution of the automation project
Install Java
Install Maven
Add maven environment variables
Install Eclipse
Clone the project using ssh - git clone git@github.com:navodita-barahat/testvagrant-assignment.git
Checkout a branch from master - git checkout -b branchName
Go to Eclipse >> File >> Import >> Import as existing Maven project >> Browse and Select the cloned automation project
Basic Framework architecture is as follows:
Following are Core/Generic classes to enable reusability of code:
DriverInit Class - Initializes Browser
Library Class extends DriverInit Class - Generic selenium/java functions
Common Class extends Library Class - Generic finctions wrt application under test
Following are utility classes:
Utils Class - Generic functions to read from excel and csv
APIRequests - Function to trigger api 
Page Class as follow:
WeatherPage - This class contains methods corresponding to the acrtions done on weather page
Then there is a BaseTest class which contains basic configuration for test to execute
Then there is a testWeatherPageConditions class to perform tests on weather page

