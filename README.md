# POC - P&L

## Objectives

## Components
Following  *[Fuse](https://github.com/GalateaRaj/fuse-starter-java#components)* conventions for component structure

-	Domain
-	Repository
-	Services
-	Entry points

## P&L workflow
[P&L workflow](https://mermaidjs.github.io/mermaid-live-editor/#/view/eyJjb2RlIjoiZ3JhcGggVERcbiAgICBTdGFydCgoU3RhcnQpKVxuICAgIEdldEVPRFtSZWFkIEVPRF1cbiAgICBHZXRSZWZlcmVuY2VEYXRlW1JlYWQgUmVmZXJlbmNlIERhdGVdXG4gICAgR2V0Qm9va3NbR2V0IEJvb2tzXVxuICAgIE1vcmVCb29rc3tNb3JlIGJvb2tzP31cbiAgICBHZXRJbnN0cnVtZW50c1tHZXQgIEluc3RydW1lbnRzXVxuICAgIE1vcmVJbnN0cnVtZW50c3tNb3JlIGluc3RydW1lbnRzP31cbiAgICBDYWxjdWxhdGVQbkxbQ2FsY3VsYXRlIFAmTCBmb3IgSW5zdHJ1bWVudF1cbiAgICBHZXRQb3NpdGlvbnNbR2V0IFBvc2l0aW9uc11cbiAgICBDYWxjdWxhdGVVbnJlYWxpemVkUG5MW0NhbGN1bGF0ZSBVbnJlYWxpemVkIFAmTF1cbiAgICBHZXRUcmFkZXNbR2V0IFRyYWRlc11cbiAgICBDYWxjdWxhdGVSZWFsaXplZFBuTFtDYWxjdWxhdGUgUmVhbGl6ZWQgUCZMXVxuICAgIFBlcnNpc3RQbkxbUGVyc2lzdCBQJkxdXG5cbiAgICBEb25lKChEb25lKSlcblxuICAgIFxuICAgIFN0YXJ0IC0tPiBHZXRFT0RcbiAgICBHZXRFT0QgLS0-IEdldFJlZmVyZW5jZURhdGVcbiAgICBHZXRSZWZlcmVuY2VEYXRlIC0tPiAgIEdldEJvb2tzW0dldCBCb29rc11cbiAgICBHZXRCb29rc1tHZXQgQm9va3NdIC0tPiBNb3JlQm9va3NcbiAgICBNb3JlQm9va3MgLS15ZXMtLT4gR2V0SW5zdHJ1bWVudHNcbiAgICBHZXRJbnN0cnVtZW50cyAtLT4gTW9yZUluc3RydW1lbnRzXG4gICAgTW9yZUluc3RydW1lbnRzIC0tbm8tLT5Nb3JlQm9va3NcbiAgICBNb3JlSW5zdHJ1bWVudHMgLS15ZXMtLT5DYWxjdWxhdGVQbkxcbiAgICBDYWxjdWxhdGVQbkwgLS0-IEdldFBvc2l0aW9uc1xuICAgIEdldFBvc2l0aW9ucyAtLT4gQ2FsY3VsYXRlVW5yZWFsaXplZFBuTFxuICAgIENhbGN1bGF0ZVBuTCAtLT4gR2V0VHJhZGVzXG4gICAgR2V0VHJhZGVzIC0tPiBDYWxjdWxhdGVSZWFsaXplZFBuTFxuICAgIENhbGN1bGF0ZVVucmVhbGl6ZWRQbkwgLS0-IFBlcnNpc3RQbkxcbiAgICBDYWxjdWxhdGVSZWFsaXplZFBuTCAtLT4gUGVyc2lzdFBuTFxuICAgIFBlcnNpc3RQbkwgLS0-IE1vcmVJbnN0cnVtZW50c1xuICAgIE1vcmVCb29rcyAtLW5vLS0-IERvbmVcblxuXG5cblxuXG4iLCJtZXJtYWlkIjp7InRoZW1lIjoibmV1dHJhbCJ9fQ) 

[Edit diagram](https://mermaidjs.github.io/mermaid-live-editor/#/edit/eyJjb2RlIjoiZ3JhcGggVERcbiAgICBTdGFydCgoU3RhcnQpKVxuICAgIEdldEVPRFtSZWFkIEVPRF1cbiAgICBHZXRSZWZlcmVuY2VEYXRlW1JlYWQgUmVmZXJlbmNlIERhdGVdXG4gICAgR2V0Qm9va3NbR2V0IEJvb2tzXVxuICAgIE1vcmVCb29rc3tNb3JlIGJvb2tzP31cbiAgICBHZXRJbnN0cnVtZW50c1tHZXQgIEluc3RydW1lbnRzXVxuICAgIE1vcmVJbnN0cnVtZW50c3tNb3JlIGluc3RydW1lbnRzP31cbiAgICBDYWxjdWxhdGVQbkxbQ2FsY3VsYXRlIFAmTCBmb3IgSW5zdHJ1bWVudF1cbiAgICBHZXRQb3NpdGlvbnNbR2V0IFBvc2l0aW9uc11cbiAgICBDYWxjdWxhdGVVbnJlYWxpemVkUG5MW0NhbGN1bGF0ZSBVbnJlYWxpemVkIFAmTF1cbiAgICBHZXRUcmFkZXNbR2V0IFRyYWRlc11cbiAgICBDYWxjdWxhdGVSZWFsaXplZFBuTFtDYWxjdWxhdGUgUmVhbGl6ZWQgUCZMXVxuICAgIFBlcnNpc3RQbkxbUGVyc2lzdCBQJkxdXG5cbiAgICBEb25lKChEb25lKSlcblxuICAgIFxuICAgIFN0YXJ0IC0tPiBHZXRFT0RcbiAgICBHZXRFT0QgLS0-IEdldFJlZmVyZW5jZURhdGVcbiAgICBHZXRSZWZlcmVuY2VEYXRlIC0tPiAgIEdldEJvb2tzW0dldCBCb29rc11cbiAgICBHZXRCb29rc1tHZXQgQm9va3NdIC0tPiBNb3JlQm9va3NcbiAgICBNb3JlQm9va3MgLS15ZXMtLT4gR2V0SW5zdHJ1bWVudHNcbiAgICBHZXRJbnN0cnVtZW50cyAtLT4gTW9yZUluc3RydW1lbnRzXG4gICAgTW9yZUluc3RydW1lbnRzIC0tbm8tLT5Nb3JlQm9va3NcbiAgICBNb3JlSW5zdHJ1bWVudHMgLS15ZXMtLT5DYWxjdWxhdGVQbkxcbiAgICBDYWxjdWxhdGVQbkwgLS0-IEdldFBvc2l0aW9uc1xuICAgIEdldFBvc2l0aW9ucyAtLT4gQ2FsY3VsYXRlVW5yZWFsaXplZFBuTFxuICAgIENhbGN1bGF0ZVBuTCAtLT4gR2V0VHJhZGVzXG4gICAgR2V0VHJhZGVzIC0tPiBDYWxjdWxhdGVSZWFsaXplZFBuTFxuICAgIENhbGN1bGF0ZVVucmVhbGl6ZWRQbkwgLS0-IFBlcnNpc3RQbkxcbiAgICBDYWxjdWxhdGVSZWFsaXplZFBuTCAtLT4gUGVyc2lzdFBuTFxuICAgIFBlcnNpc3RQbkwgLS0-IE1vcmVJbnN0cnVtZW50c1xuICAgIE1vcmVCb29rcyAtLW5vLS0-IERvbmVcblxuXG5cblxuXG4iLCJtZXJtYWlkIjp7InRoZW1lIjoibmV1dHJhbCJ9fQ)


## Dependencies

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

## Initial Data Import

## Testing

### Running the tests

### Adding new tests

## Future work
See open issues

## References
Section with relevant links to information




# Phases
## Phase 1
* Value positions only (no trades, balances, cashflows) (i.e. unrealized P&L only)
* For 1 book only
* Interact with Valuation Service (hardcoded or random valuation response)
* Calculate P&L
## Future Phases
* Store P&L & valuation inputs/outputs for future 
* Trades (i.e. realized P&L)
* FX
* Valuation Input discovery (very basic, e.g. 'need price')
* Fixed Income vs. Equities
* Swaps (cashflows)
* Dividends/Coupons

