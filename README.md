

# Anypoint Template: SAP to Salesforce Product Broadcast

Broadcast changes or creations of materials in SAP as products to Salesforce in real time. The detection criteria, and fields that should be moved are configurable. Additional systems can be added to be notified of the changes. Real time synchronization is achieved via rapid polling of SAP. This template uses both Mule batching and watermarking capabilities to ensure that only recent changes are captured, and to efficiently process large amounts of records.

![0ca18425-7417-4fe0-9a96-cc8b2ae955c2-image.png](https://exchange2-file-upload-service-kprod.s3.us-east-1.amazonaws.com:443/0ca18425-7417-4fe0-9a96-cc8b2ae955c2-image.png)

# License Agreement
This template is subject to the conditions of the <a href="https://s3.amazonaws.com/templates-examples/AnypointTemplateLicense.pdf">MuleSoft License Agreement</a>. Review the terms of the license before downloading and using this template. You can use this template for free with the Mule Enterprise Edition, CloudHub, or as a trial in Anypoint Studio.

# Use Case
This template should serve as a foundation for setting an online sync of materials from SAP to Salesforce. Every time there is a new material (Salesforce product) or a change in an already existing one, SAP sends the IDoc with it to the running template which updates or creates a product in Salesforce target instance.

Requirements have been set not only to be used as examples, but also to establish a starting point to adapt your integration to your requirements.

As implemented, this template leverages the Batch Module. The batch job is divided into Process and On Complete stages.

The integration is triggered by a Document Source component that receives the SAP Material as IDoc XML. This XML (SAP Material) is transformed to a Salesforce Product. Salesforce Product is passed to the batch process. and is upserted in the Batch Step to Salesforce using a Batch Aggregator. Finally during the On Complete stage the template logs output statistics data into the console.

# Considerations

To make this template run, there are certain preconditions that must be considered. All of which deal with the preparations in both the source (SAP) and destination (Salesforce) systems, that must be made for the template to run smoothly. Failing to do so can lead to unexpected behavior of the template.

Before continue with the use of this template, you may want to check out this [Documentation Page](http://www.mulesoft.org/documentation/display/current/SAP+Connector#SAPConnector-EnablingYourStudioProjectforSAP), that teaches you how to work with SAP and Anypoint Studio.

## Disclaimer

This Anypoint template uses a few private Maven dependencies from Mulesoft in order to work. If you intend to run this template with Maven support, you need to add three extra dependencies for SAP to the pom.xml file.

## SAP Considerations

Here's what you need to know to get this template to work with SAP.

### As a Data Source

The SAP backend system is used as a source of data. The SAP connector is used to send and receive the data from the SAP backend. The connector can either use RFC calls of BAPI functions and/or IDoc messages for data exchange, and needs to be properly customized per the "Properties to Configure" section.

The Partner profile needs to have a customized type of logical system set as partner type. An outbound parameter of message type MATMAS should be defined in the partner profile. A RFC destination created earlier should be defined as Receiver Port. Idoc Type base type should be set as MATMAS01.

## Salesforce Considerations

Here's what you need to know about Salesforce to get this template to work:

- Where can I check that the field configuration for my Salesforce instance is the right one? See: <a href="https://help.salesforce.com/HTViewHelpDoc?id=checking_field_accessibility_for_a_particular_field.htm&language=en_US">Salesforce: Checking Field Accessibility for a Particular Field</a>.
- Can I modify the Field Access Settings? How? See: <a href="https://help.salesforce.com/HTViewHelpDoc?id=modifying_field_access_settings.htm&language=en_US">Salesforce: Modifying Field Access Settings</a>.

### As a Data Destination

This template makes use of the `External ID` field offered by Salesforce. Here is a short description on how Salesforce define external IDs: [What is an external ID?](http://help.salesforce.com/apex/HTViewHelpDoc?id=faq_import_general_what_is_an_external.htm)

The template uses the External ID in order to do xRef between the entities in both systems. The idea is, once an entity is created in Salesforce, it's given an ID from the source system which is used afterwards for the template to reference it.

Create a new custom field in your **Product** entity in Salesforce with the following name: 

+ `sap_external_id`

For instructions on how to create a custom field in Salesforce, see: [Create Custom Fields](https://help.salesforce.com/HTViewHelpDoc?id=adding_fields.htm)

# Run it!
Simple steps to get SAP to Salesforce Product Broadcast running.

## Running On Premises
In this section we help you run your template on your computer.

### Where to Download Anypoint Studio and the Mule Runtime
If you are a newcomer to Mule, here is where to get the tools.

+ [Download Anypoint Studio](https://www.mulesoft.com/platform/studio)
+ [Download Mule runtime](https://www.mulesoft.com/lp/dl/mule-esb-enterprise)

### Importing a Template into Studio
In Studio, click the Exchange X icon in the upper left of the taskbar, log in with your Anypoint Platform credentials, search for the template, and click **Open**.

### Running on Studio
After you import your template into Anypoint Studio, follow these steps to run it:

+ Locate the properties file `mule.dev.properties`, in src/main/resources.
+ Complete all the properties required as per the examples in the "Properties to Configure" section.
+ Right click the template project folder.
+ Hover your mouse over `Run as`.
+ Click `Mule Application (configure)`.
+ Inside the dialog, select Environment and set the variable `mule.env` to the value `dev`.
+ Click `Run`. To make this template run in Studio, check the [SAP Connector guide](https://docs.mulesoft.com/connectors/sap-connector#configuring-the-connector-in-studio-7).

### Running on Mule Standalone
Complete all properties in one of the property files, for example in mule.prod.properties and run your app with the corresponding environment variable. To follow the example, this is `mule.env=prod`. 

## Running on CloudHub
While creating your application on CloudHub (or you can do it later as a next step), go to Runtime Manager > Manage Application > Properties to set the environment variables listed in "Properties to Configure" as well as the **mule.env**.

### Deploying your Anypoint Template on CloudHub
In Studio, right click your project name in Package Explorer and select Anypoint Platform > Deploy on CloudHub.

## Properties to Configure
To use this template, configure properties (credentials, configurations, etc.) in the properties file or in CloudHub from Runtime Manager > Manage Application > Properties. The sections that follow list example values.

### Application Configuration

**Application Configuration**

+ page.size `100`

**SAP Connector Configuration**

+ sap.jco.ashost `your.sap.address.com`
+ sap.jco.user `SAP_USER`
+ sap.jco.passwd `SAP_PASS`
+ sap.jco.sysnr `14`
+ sap.jco.client `800`
+ sap.jco.lang `EN`

**SAP Endpoint Configuration**

sap.jco.operationtimeout `1000`
sap.jco.connectioncount `2`
sap.jco.gwhost `your.sap.address.com`
sap.jco.gwservice `sapgw14`
sap.jco.idoc.programid `PROGRAM_ID`

**SalesForce Connector Configuration**

+ sfdc.username `bob.dylan@sfdc`
+ sfdc.password `DylanPassword123`
+ sfdc.securityToken `avsfwCUl7apQs56Xq2AKi3X`

# API Calls
SalesForce imposes limits on the number of API calls that can be made. Therefore calculating this amount may be an important factor to consider. This template calls to the API can be calculated using the formula:

**X / 200**

**X** is the number of Products to be synchronized on each run.

Divide by 200 because by default, Users are gathered in groups of 200 for each Upsert API Call in the commit step. Also consider that this calls are executed repeatedly every polling cycle.

For instance if 10 records are fetched from origin instance, then 1 API calls to Salesforce is made ( 1).

# Customize It!
This brief guide intends to give a high level idea of how this template is built and how you can change it according to your needs. As Mule applications are based on XML files, this page describes the XML files used with this template.

More files are available such as test classes and Mule application files, but to keep it simple, we focus on these XML files:

* config.xml
* businessLogic.xml
* endpoints.xml
* errorHandling.xml

## config.xml
Configuration for connectors and configuration properties are set in this file. If you change the configuration here, put all parameters that can be modified in a properties file. However if you want to do core changes to the logic, you need to modify this file.

In the Studio visual editor, the properties are on the *Global Element* tab.

## businessLogic.xml
The business logic XML file creates or updates objects in the destination system for a represented use case. You can customize and extend the logic of this template in this XML file to more meet your needs.

## endpoints.xml
This file contains the endpoints for triggering the template and for retrieving the objects that meet the defined criteria in the query. You can then execute the batch job process with the query results.

## errorHandling.xml
This file  handles how your integration reacts depending on the different exceptions. 
This file provides error handling that is referenced by the main flow in the business logic.
