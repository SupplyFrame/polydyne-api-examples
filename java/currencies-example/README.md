# Update Exchange Rates
## Overview
This example demonstrates how you can use the PolyDyne API to update exchange rates automatically. Exchange rates can be pulled from a number of different sources, this example uses [openexchangerates.org](https://openexchangerates.org/).

## Setup
1. [Get a free App ID](https://openexchangerates.org/signup/free) from [openexchangerates.org](https://openexchangerates.org/)
2. Clone the example repository

	`git clone git@github.com:SupplyFrame/polydyne-api-examples.git`
	
## Running the example
1. [Download](https://github.com/SupplyFrame/polydyne-api-examples/releases/download/v1.0/polydyne-currencies-example.jar) the executable jar from the repository
2. Execute the example

		java \
		  -Dusername=<username> \
		  -Dpassword=<password> \
		  -Dapp_id=<app_id> \
		  -jar polydyne-currencies-example.jar

## Opening the example in an IDE
This example is a standard maven project which most Java IDEs work well with. After importing the code into an IDE as a maven project and setting up the arguments as described above run Main.java from within the IDE. 
