# Update Exchange Rates
## Overview
This example demonstrates how you can use the PolyDyne API to update exchange rates automatically. Exchange rates can be pulled from a number of different sources, this example uses [openexchangerates.org](https://openexchangerates.org/).

## Setup
1. [Get a free App ID](https://openexchangerates.org/signup/free) from [openexchangerates.org](https://openexchangerates.org/)
2. Clone the example repository

	`git clone git@github.com:SupplyFrame/polydyne-api-examples.git`
3. Open the correct example in the directory 
4. Import the solution to an IDE
5. Packages used are RestSharp for making API calls and Json.NET, which are available in NuGet
6. Project references include the Package references and System

## Running the example
After importing the code to an IDE, set the following environment variables and run the solution.

- username : with your PolyDyne API username
- password : with your PolyDyne API password
- app_id : with your openexchangerates.org API key
