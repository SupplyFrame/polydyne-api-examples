# Supplier API Autoquoter Example
## Overview
This example code reads a TSV file that contains manufacturer name, mpn, price, leadtime and moq information. The given information is used to find the average price, leadtime and moq for a given mpn for a particular manufacturer.  These values will be auto-quoted if they exist and are not filled.

## Running the example
1. Ensure Java 8 is installed. Run:

	```
	java -version
	```
	Ensure the resulting version is at least 1.8
2. [Download](https://github.com/SupplyFrame/polydyne-api-examples/releases/download/v1.1/supplierapi-autoquoter-example.jar) the executable jar from the repository
3. Execute the example

		java \
		  -Dusername=<username> \
		  -Dpassword=<password> \ \
		  -Dinput_file=<input.csv> \
		  -jar supplierapi-autoquoter-example.jar

## Opening the example in an IDE
This example is a standard maven project which most Java IDEs work well with. After importing the code into an IDE as a maven project and setting up the arguments as described above run Main.java from within the IDE. 
## Program Flow
1. Populate caches:

	As with most RestFUL APIs, each entity has a unique ID. These IDs are used when creating references to other entities. Since the input file specifies names instead of IDs, we must lookup the name in order to find the correct ID. Rather than doing many potentially duplicate lookups, it is good practice to setup a lookup table or map for entities that must be referenced. In this example, the following entities are cached:
	- Customers
	- Manufacturers


1. Parse TSV:

	All of the necessary data is contained in the input TSV file. The input file contains five columns namely manufacturer name, manufacturer part number and the values for price, leadtime and moq. Price, leadtime and moq may or may not be null.Parse the TSV file and populate the following hashmaps:
	- Map of manufacturer information and price list
	- Map of manufacturer information and leadtime list
	- Map of manufacturer information and moq list
	
	Use the above hashmaps to populate three corresponding hashmaps that calculate the average value for each list, so that we do no have to recalculate the averages during auto-quoting.
	
	
2. Populate the caches to store customer, manufacturer and other required information
3. Get all customers and cache them.
4. Get all manufacturers for the customers and store them in a map of customers to map of manufacturers.
5. Get all projects for the customers and store them in a set.
6. Get siteviews for siteview projects and store projects and siteviews in a set with projectId, siteId(if it exists) and customerId.
7. Get rfqs for the projects and siteview projects and store in a set.
8. For each rfq that does not have a value for price, moq or leadtime, auto-quote price, leadtime and moq using the average price, leadtime and moq maps.
9. If the rfq has any autoquoted value add it to a list of rfqs to be updated.
10. Break the rfq list into batches of 100 and use the batch API to insert each batch in a single request.
11. Display the rfqId and projectId of the updated rfqs and display percentage of rfqs auto-quoted.

