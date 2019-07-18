# POC - P&L

## Objectives

This Proof of Concept is intended to build an application for P&L calculations that allow us get familiarized with the relevant concepts, formulas and ultimately to iterate a design flexible enough to enable:
- Multiple Valuation configurations and services
- Different Asset type P&L calculations
- Data input requirement discovery to allow service evolution


## Components
Following  *[Fuse](https://github.com/GalateaRaj/fuse-starter-java#components)* conventions for component structure

-	Domain: entity object with no business logic.
-	Repository: classes interacting with the persistance layer.
-	Services: classes performing business logic.
-	Entry points: components interacting with the outside world.

## P&L workflow
[P&L workflow](https://mermaidjs.github.io/mermaid-live-editor/#/view/eyJjb2RlIjoiZ3JhcGggVERcbiAgICBTdGFydCgoU3RhcnQpKVxuICAgIEdldEVPRFtSZWFkIEVPRF1cbiAgICBHZXRSZWZlcmVuY2VEYXRlW1JlYWQgUmVmZXJlbmNlIERhdGVdXG4gICAgR2V0Qm9va3NbR2V0IEJvb2tzXVxuICAgIE1vcmVCb29rc3tNb3JlIGJvb2tzP31cbiAgICBHZXRJbnN0cnVtZW50c1tHZXQgIEluc3RydW1lbnRzXVxuICAgIE1vcmVJbnN0cnVtZW50c3tNb3JlIGluc3RydW1lbnRzP31cbiAgICBDYWxjdWxhdGVQbkxbQ2FsY3VsYXRlIFAmTCBmb3IgSW5zdHJ1bWVudF1cbiAgICBHZXRQb3NpdGlvbnNbR2V0IFBvc2l0aW9uc11cbiAgICBDYWxjdWxhdGVVbnJlYWxpemVkUG5MW0NhbGN1bGF0ZSBVbnJlYWxpemVkIFAmTF1cbiAgICBHZXRUcmFkZXNbR2V0IFRyYWRlc11cbiAgICBDYWxjdWxhdGVSZWFsaXplZFBuTFtDYWxjdWxhdGUgUmVhbGl6ZWQgUCZMXVxuICAgIFBlcnNpc3RQbkxbUGVyc2lzdCBQJkxdXG5cbiAgICBEb25lKChEb25lKSlcblxuICAgIFxuICAgIFN0YXJ0IC0tPiBHZXRFT0RcbiAgICBHZXRFT0QgLS0-IEdldFJlZmVyZW5jZURhdGVcbiAgICBHZXRSZWZlcmVuY2VEYXRlIC0tPiAgIEdldEJvb2tzW0dldCBCb29rc11cbiAgICBHZXRCb29rc1tHZXQgQm9va3NdIC0tPiBNb3JlQm9va3NcbiAgICBNb3JlQm9va3MgLS15ZXMtLT4gR2V0SW5zdHJ1bWVudHNcbiAgICBHZXRJbnN0cnVtZW50cyAtLT4gTW9yZUluc3RydW1lbnRzXG4gICAgTW9yZUluc3RydW1lbnRzIC0tbm8tLT5Nb3JlQm9va3NcbiAgICBNb3JlSW5zdHJ1bWVudHMgLS15ZXMtLT5DYWxjdWxhdGVQbkxcbiAgICBDYWxjdWxhdGVQbkwgLS0-IEdldFBvc2l0aW9uc1xuICAgIEdldFBvc2l0aW9ucyAtLT4gQ2FsY3VsYXRlVW5yZWFsaXplZFBuTFxuICAgIENhbGN1bGF0ZVBuTCAtLT4gR2V0VHJhZGVzXG4gICAgR2V0VHJhZGVzIC0tPiBDYWxjdWxhdGVSZWFsaXplZFBuTFxuICAgIENhbGN1bGF0ZVVucmVhbGl6ZWRQbkwgLS0-IFBlcnNpc3RQbkxcbiAgICBDYWxjdWxhdGVSZWFsaXplZFBuTCAtLT4gUGVyc2lzdFBuTFxuICAgIFBlcnNpc3RQbkwgLS0-IE1vcmVJbnN0cnVtZW50c1xuICAgIE1vcmVCb29rcyAtLW5vLS0-IERvbmVcblxuXG5cblxuXG4iLCJtZXJtYWlkIjp7InRoZW1lIjoibmV1dHJhbCJ9fQ) 

[Unrealized PnL workflow](https://mermaidjs.github.io/mermaid-live-editor/#/view/eyJjb2RlIjoiZ3JhcGggVEJcbiAgICBTdGFydCgoU3RhcnQpKVxuICAgIEdldEVPRFBvc2l0aW9uW0dldCBFT0QgUG9zaXRpb25dXG4gICAgU3VibWl0Rm9yVmFsdWF0aW9uW1N1Ym1pdCBmb3IgdmFsdWF0aW9uXVxuICAgIFZhbHVhdGlvblJlc3BvbnNlW0dldCB2YWx1YXRpb24gcmVzcG9uc2VdXG4gICAgQXVnbWVudElucHV0W0F1Z21lbnQgSW5wdXRdXG4gICAgR2V0UmVmZXJlbmNlVmFsdWF0aW9uW0dldCBSZWZlcmVuY2UgVmFsdWF0aW9uXVxuICAgIFZhbGlkYXRlSW5wdXR7TW9yZSBpbnB1dCBuZWVkZWQ_fVxuICAgIENhbGN1bGF0ZVZhbHVhdGlvbltDYWxjdWxhdGUgVmFsdWF0aW9uXVxuICAgIE1pc3NpbmdJbnB1dHtNaXNzaW5nIGlucHV0P31cbiAgICBSZXF1ZXN0TWlzc2luZ0RhdGFbUmVxdWVzdCBNaXNzaW5nIERhdGFdXG4gICAgQ2FsY3VsYXRlTXRtUG5sW0NhbGN1bGF0ZSBNVE0gUCZsXVxuICAgQ2FsY3VsYXRlTXRtUG5MRnhbQ2FsY3VsYXRlIE1UTSBQJkwgRnhdXG4gIENhbGN1bGF0ZUZ4UG5MW0NhbGN1bGF0ZSBGeCBQJkxdXG4gICAgRG9uZSgoRG9uZSkpXG5cbnN1YmdyYXBoIFBuTFNlcnZpY2VcblN0YXJ0IC0tPiBHZXRFT0RQb3NpdGlvblxuR2V0RU9EUG9zaXRpb24gLS0-IFN1Ym1pdEZvclZhbHVhdGlvblxuU3VibWl0Rm9yVmFsdWF0aW9uIC0tPiBHZXRSZWZlcmVuY2VWYWx1YXRpb25cblZhbHVhdGlvblJlc3BvbnNlIC0tPiBNaXNzaW5nSW5wdXRcblN1Ym1pdEZvclZhbHVhdGlvbiAtLT4gTWlzc2luZ0lucHV0XG5NaXNzaW5nSW5wdXQgLS15ZXMtLT5BdWdtZW50SW5wdXRcbkF1Z21lbnRJbnB1dCAtLT4gU3VibWl0Rm9yVmFsdWF0aW9uXG5NaXNzaW5nSW5wdXQgLS1uby0tPkNhbGN1bGF0ZU10bVBubFxuQ2FsY3VsYXRlTXRtUG5sIC0tPiBDYWxjdWxhdGVNdG1QbkxGeFxuQ2FsY3VsYXRlTXRtUG5MRnggLS0-IENhbGN1bGF0ZUZ4UG5MXG5DYWxjdWxhdGVGeFBuTCAtLT4gRG9uZVxuZW5kXG5zdWJncmFwaCBWYWx1YXRpb25TZXJ2aWNlXG5TdWJtaXRGb3JWYWx1YXRpb24gLS0-IFZhbGlkYXRlSW5wdXRcblZhbGlkYXRlSW5wdXQgLS15ZXMtLT4gUmVxdWVzdE1pc3NpbmdEYXRhXG5SZXF1ZXN0TWlzc2luZ0RhdGEgLS0-IFZhbHVhdGlvblJlc3BvbnNlXG5WYWxpZGF0ZUlucHV0IC0tbm8tLT4gQ2FsY3VsYXRlVmFsdWF0aW9uXG5DYWxjdWxhdGVWYWx1YXRpb24gLS0-VmFsdWF0aW9uUmVzcG9uc2VcbmVuZFxuICAgIFxuXG5cblxuXG5cblxuIiwibWVybWFpZCI6eyJ0aGVtZSI6Im5ldXRyYWwifX0)

[Realized PnL workflow](https://mermaidjs.github.io/mermaid-live-editor/#/view/eyJjb2RlIjoiZ3JhcGggVEJcbiAgICBDYWxjdWxhdGVSZWFsaXplZFBubFtDYWxjdWxhdGUgUmVhbGl6ZWQgUCZMIGZvciBpbnN0cnVtZW50XVxuICAgIEdldFRyYWRlc1tHZXQgVHJhZGVzXVxuICAgIE1vcmVUcmFkZXN7TW9yZSBUcmFkZXM_fVxuICAgQWRkUHJvY2VlZHNbQWRkIFByb2NlZWRzXVxuICAgQWRkRmVlc1tBZGQgRmVlc11cbiAgIEFkZENvbW1pc3Npb25zW0FkZCBDb21taXNzaW9uc11cbiAgICBEb25lKChEb25lKSlcblxuc3ViZ3JhcGggUG5MU2VydmljZVxuQ2FsY3VsYXRlUmVhbGl6ZWRQbmwgLS0-IEdldFRyYWRlc1xuR2V0VHJhZGVzIC0tPiBNb3JlVHJhZGVzXG5Nb3JlVHJhZGVzIC0teWVzLS0-IEFkZFByb2NlZWRzXG5BZGRQcm9jZWVkcyAtLT4gQWRkRmVlc1xuQWRkRmVlcyAtLT4gQWRkQ29tbWlzc2lvbnNcbkFkZENvbW1pc3Npb25zIC0tPiBNb3JlVHJhZGVzXG5Nb3JlVHJhZGVzIC0tbm8tLT4gRG9uZVxuZW5kIiwibWVybWFpZCI6eyJ0aGVtZSI6Im5ldXRyYWwifX0)

## Dependencies

See pom.xml for entire list of dependencies.

- spring-boot-starter-web
- spring-boot-starter-thymeleaf
- spring-boot-devtools
- spring-boot-starter-test
- spring-boot-starter-data-jpa
- h2
- gson
- lombok

## Setting up in Eclipse

### Code Style
Source: [https://github.com/GalateaRaj/fuse-starter-java#eclipse](https://github.com/GalateaRaj/fuse-starter-java#eclipse)

Set code style settings, which will allow auto-formatting of code to match the Google style guide

-   Navigate to Window -> Preferences -> Java -> Code Style -> Formatter
-   Click Import and select <project_directory>/style/eclipse-java-google-style.xml
-   Navigate to Window -> Preferences -> Java -> Code Style -> Organize Imports
-   Click Import and select <project_directory>/style/eclipse-java-google-style.importorder
-   Navigate to Window -> Preferences -> Java -> Editor -> Save Actions
-   Select the "Perform the selected actions on save", "Format source code", "Format edited lines", and "Organize imports" options

### Installing lombok
Install lombok:  [https://projectlombok.org/setup/eclipse](https://projectlombok.org/setup/eclipse). Note if you're doing this step last because you raced ahead and nothing compiles you'll have to do some cleans and re-compiles to get lombok involved in generating all the class files.

### Running the project
Eclipse: r-click -> run as 'Java Application' on src/main/java/org/galatea/pocpnl/App.java

After startup, UI should be available http://localhost:8080/pnl

## Initial Data Import

Initial Data can be imported by the *@Service* DataImportService which reads text files containing a single json object per line. The DataImportService reads each file, parses each json into an object and uses the corresponfing *Repository* to persist the object.

*src/main/resources/data/books.txt*
```
{"bookId": "EQTY1", "currency": "USD"}
{"bookId": "EQTY2", "currency": "USD"}

```

```
public void importData() {
    importData(bookRepository, getLines("books.txt"), Book.class);
  }
  
```

## Testing

This application is tested using a series of scenarios defined in json files. Each test will read a single json file and parse it into a TestScenario object.

A TestScenario object should have all the information required to perform the test:
- Inputs
  - Instruments
  - Static Data
  - FxRates
  - Trades
  - Existing Valuations
- Expected Output
  - Expected P&L results

### Running the tests
Eclipse: r-click 'Run As -> JUnit Test' on src/test/java/org/galatea/pocpnl/PocPnlApplicationTests

### Adding new tests
To add a new tests:
1. Define a new json file witht the test scenario following the TestScenario.java structure.
2. Create a new test in PocPnlApplicationTests following the existing test conventions.

## Future work
See open [issues](https://github.com/galatea-associates/poc-pnl/issues)
* Swaps (cashflows)
* Dividends/Coupons

## References
Section with relevant links to information

- Cost Basis https://www.investopedia.com/terms/c/costbasis.asp
- Constant Yield Method https://www.investopedia.com/terms/a/accretion-of-discount.asp
- Amortization/Accretion:
  - https://www.investopedia.com/terms/a/accretion-of-discount.asp
  - https://www.investopedia.com/terms/a/amortizable-bond-premium.asp
- Clean vs. dirty price https://www.investopedia.com/terms/c/cleanprice.asp
- Yield
  - Yield To Maturity https://www.investopedia.com/terms/y/yieldtomaturity.asp
  - Yield To Call https://www.fool.com/knowledge-center/how-to-calculate-yield-for-a-callable-bond.aspx

