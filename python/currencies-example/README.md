# Update Exchange Rates (Python)
## Overview
This example demonstrates how you can use the PolyDyne API to update exchange rates automatically. Exchange rates can be pulled from a number of different sources, this example uses [openexchangerates.org](https://openexchangerates.org/).

Python 3 has native functionality to make API calls but it is somewhat verbose and difficult to use. This example uses [Requests](http://docs.python-requests.org/en/master/) in order to simplify interacting with APIs.

## Setup
1. [Install Python 3](https://www.python.org/downloads/)
2. [Install Requests](http://docs.python-requests.org/en/master/user/install/)
3. [Get a free App ID](https://openexchangerates.org/signup/free) from [openexchangerates.org](https://openexchangerates.org/)
4. Clone the example repository

	`git clone git@github.com:SupplyFrame/polydyne-api-examples.git`

## Run
1. Clone the example repository

	`git clone git@github.com:SupplyFrame/polydyne-api-examples.git`
2. Change to the correct directory

	`cd polydyne-api-examples/python/currencies-example`
3. Using the command below, replace the following:
	
	- &#60;username> with your PolyDyne API username
	- &#60;password> with your PolyDyne API password
	- &#60;app_id> with your openexchangerates.org API key	

	```
	USERNAME=<username> \
	PASSWORD=<password> \
	APP_ID=<app_id> \
	python3 update-currencies.py
	```

