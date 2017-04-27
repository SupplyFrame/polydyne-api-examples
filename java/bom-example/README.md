# PolyDyne API BOM Example
## Overview
This example code creates a project and populates the BOM with the assemblies and parts specified in an input file. The input file can be generated from an existing project within QuoteWin or DesignWin.

## Running the example
1. Ensure Java 8 is installed. Run:

	```
	java -version
	```
	Ensure the resulting version is at least 1.8
2. [Download] (https://github.com/SupplyFrame/polydyne-api-examples/releases/download/v1.0/bom-example.jar) the executable jar from the repository
3. Execute the example

		java \
		  -Dusername=<username> \
		  -Dpassword=<password> \
		  -Dproject_name='Test Project' \
		  -Dinput_file=<input.csv> \
		  -jar polydyne-bom-example.jar

## Opening the example in an IDE
This example is a standard maven project which most Java IDEs work well with. After importing the code into an IDE as a maven project and setting up the arguments as described above run Main.java from within the IDE. 
## Program Flow
1. Populate caches:

	As with most RestFUL APIs, each entity has a unique ID. These IDs are used when creating references to other entities. Since the input file specifies names instead of IDs, we must lookup the name in order to find the correct ID. Rather than doing many potentially duplicate lookups, it is good practice to setup a lookup table or map for entities that must be referenced. In this example, the following entities are cached:
	- Commodities
	- Units
	- Manufacturers
	- MPN Statuses
	- Package Types
	- Custom Fields

1. Parse CSV:

	All of the necessary data to populate the BOM is contained in the input CSV file. The input files that this example code can parse are those created by exporting a BOM as CSV in QuoteWin or DesignWin. Depending on your use case, data could be sourced from a database, another API or an ERP system. Regardless of the data source, the fields necessary to create a BOM will generally be similar to those used in this example.
	
	More than one API entity may be required for each line in the CSV file. For example, if the line describes a part, the example code will create a part entity as well as a BOM item entity to include the part in an assembly. In the code, lists of lines that correspond to the following entities are created:
	- Assemblies
	- Parts
	- BOM Items
	- Manufacturer Parts

	These lines will later be parsed and used to create the API entities for insertion.
	
3. Create a project using the specified project name. The project description and number of volume breaks can be set in the code.
4. Parse each list of lines in order to create lists of objects for insertion.
5. Break the list of assemblies into batches of 100 and use the batch API insert each batch in a single request. Cache the generated IDs for each assembly.
6. Break the list of parts into batches of 100 and use the batch API to insert each batch in a single request. Cache the generated IDs for each part
7. Break the list of manfacturer parts into batches of 100. Associate the generated partId with each manufacturer part. Use the batch API to insert each batch of manufacturer parts in a single request.
8. Break the list of BOM items in batches of 100. Set the componentId using the cached generated IDs from steps 5 and 6. Use the batch API to insert each batch of bom items in a single request.
9. Update the volume of each top-level assembly at each volume break. In the example, all of volumes for the top-level assemblies are set to powers of 10 (10, 100, 1000, etc)
