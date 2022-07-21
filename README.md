# Drill Human Name Functions
This collection of functions allows users to extract portions of human names.
It parses human names of arbitrary complexity and various wacky formats like:

* J. Walter Weatherman
* de la Cruz, Ana M.
* James C. ('Jimmy') O'Dell, Jr.
* Dr. Omar A.

And parses out the:

* leading initial (Like "J." in "J. Walter Weatherman")
* first name (or first initial in a name like 'R. Crumb')
* nicknames (like "Jimmy" in "James C. ('Jimmy') O'Dell, Jr.")
* middle names
* last name (including compound ones like "van der Sar' and "Ortega y Gasset"), and
* suffix (like 'Jr.', 'III')
* salutations (like 'Mr.', 'Mrs.', 'Dr')
* postnominals (like 'PHD', 'CPA')

## Functions

* `getFirstName(<name>)`:  Gets the first name from the input name
* `getLastName(<name>)`:  Gets the last name from the input name
* `getLeadingInitial(<name>)`:  Gets the leading initial from the input name
* `getNickName(<name>)`:  Gets the nickname from the input name
* `getMiddleNames(<name>)`:  Gets the middle names from the input name
* `getNameSuffix(<name>)`:  Gets the suffix from the input name (like 'Jr.', 'III')
* `getSalutation(<name>)`:  Gets the salutation from the input name (like 'Mr.', 'Mrs.', 'Dr')
* `getPostnominals(<name>)`:  Gets the postnominals from the input name (like 'PHD', 'CPA')

## Usage:
Simply include one of the functions above in a Drill query:

```sql
SELECT getFirstName(<namefield>) AS firstName, 
       getLastName(<namefield>) AS lastName 
FROM ...
```


### Credits
This UDF collection is based on the work below:

Java port Author: Bruno P. Kinoshita

Original library Author: [Jason Priem](https://github.com/jasonpriem) (credits go to him)

Original library Author Website: https://github.com/jasonpriem/HumanNameParser.php
