# PolyDyne API Outsource Assembly Price Example
## Overview
This example code read all the data to generate the Outsource 
creates a project and populates the BOM with the assemblies and parts specified in an input file. The input file can be generated from an existing project within QuoteWin or DesignWin.

## Running the example
1. Ensure Java 8 is installed. Run:

	```
	java -version
	```
	Ensure the resulting version is at least 1.8
2. You can download the executable file from the [release page](https://github.com/SupplyFrame/polydyne-api-examples/releases) 
3. Execute the example

		java \
		  -Dusername=<username> \
		  -Dpassword=<password> \
		  -DprojectId=<projectId> \
                  -jar assembly-price-example.jar

## Sample Output
- This sample code will print out the Outsource Assembly Pricing table to the standard output.
- It will only print the first volume.  If your project has multiple volume breaks, you will only see the price for volume #1.
- The output is in CSV format.

```
Populating caches

"Assembly: LTS500-NETWORK", "Custom Manufacturer:SAMPLE CUSTOM MANUFACTURER", "Volume ID: 1", "volume:1.0"
----------------------------------------------
"Category", "CM Target", "Vol#1 Price", "Notes"
"Raw Material",1400,1650,""
"MATERIAL AQUISITION",90.99,100,""
"Total Material",,1750,""
"Raw Labor",,2500,""
"FUNCTIONAL TEST",,225,""
"Total Labor",,2725,""
"FUNCTIONAL TEST",,225,""
"Total SG&A",,225,""
"FUNCTIONAL TEST",,225,""
"Total Other",,225,""
"Assembly Cost",,4925,""
"Material Profit",,800,""
"Labor Profit",,1250,""
"Overhead Profit",,400,""
"Other Profit",,299.95,""
"Assembly Price",,7674.95,""
"Volume Price",,7674.950,""
"PCB TOOLING",,2500,""
"BRACKET TOOLING",,1500,""
"Total NRE",,4000,""
"NRE Profit",,1350,""
"NRE Price",,5350,""
"Volume Price with NRE",,13024.950,""
```

## Opening the example in an IDE
This example is a standard maven project which most Java IDEs work well with. After importing the code into an IDE as a maven project and setting up the arguments as described above run Main.java from within the IDE. 

## Program Flow
1. Populate caches
    As with most RestFUL APIs, each entity has a unique ID. These IDs are used when creating references to other entities. Since the input file specifies names instead of IDs, we must lookup the name in order to find the correct ID. Rather than doing many potentially duplicate lookups, it is good practice to setup a lookup table or map for entities that must be referenced. 

    In this example, the we are caching:
    - Cost Rollup Attributes

2. Get a list of top level assemblies
    The outsource assembly pricing only applicable to the top level assemblies only.  You can pull a list of top level assemblies using the **bom_item** api end point and filter for **parentAssemblyId=0** as shown in the *BomItemService.getTopAssemblies(projectId)*.
		
3. Get assembly prices
    As you can see in the *AssemblyPriceService.getAssemblyPrices(projectId)*, we are using the **outsource_assembly_prices** end point to pull all outsource assembly prices data.
    To organize the data better, we use multiple HashMaps to group the data.  
    - level 1: We use the project Id, assembly Id, and custom manufacturer Id as the key
    - level 2: 
        - We use the project Id, assembly Id, custom manufacturer Id, and price type
        - When the price type is "Attributes", we will use project Id, assembly Id, custom manufacturer Id, price type, cost rollup attribute type, and cost rollup attribute Id
    - level 3: We use the volume ID to store the prices for each volume break

4. Printing the data
    When printing the data, we divide the printing processes to **printRawOrProfit** method and **printAttributes**.
    -printRawOrProfit: handles all non-attribute data, such as "Raw Material", "Raw Labor", "Material Profit", etc.
    -printAttributes: print all the attributes info and lookup the cache (cost rollup attributes) to find the attribute name.

## Formula for Calculating Outsource Assembly Price
|Category|Vol#1 Price|Formula / Description|
|-----|-----|-----|
|Raw Material|1650||
|MATERIAL AQUISITION|100|sample material attribute|
|Total Material|1750|Raw Material + all material attributes|
|Raw Labor|2500||
|FUNCTIONAL TEST|225|sample labor attribute|
|Total Labor|2725|Raw Labor + all labor attributes|
|FUNCTIONAL TEST|225|sample SG&A attribute|
|Total SG&A|225|all SG&A attribute|
|FUNCTIONAL TEST|225|sample other attribute|
|Total Other|225|all other attribute|
|Assembly Cost|4925|Total Material + total labor + total SG&A + total other|
|Material Profit|800||
|Labor Profit|1250||
|Overhead Profit|400||
|Other Profit|299.95||
|Assembly Price|7674.95|Assembly Cost + all profits|
|Volume Price|7674.950|Assembly Price * volume (in this example the volume is 1)|
|PCB TOOLING|2500|sample NRE attribute|
|BRACKET TOOLING|1500|sample NRE attribute|
|Total NRE|4000|all NRE attributes|
|NRE Profit|1350||
|NRE Price|5350|Total NRE + NRE Profit|
|Volume Price with NRE|13024.950|Volume price + NRE Price|

