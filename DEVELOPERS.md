Developers
=========

## Java

This project use Java 8.

You can download the JDK [here](https://jdk8.java.net/download.html).

### Eclipse

#### JRE

1. `Window` > `Preferences`;
* `Java` > `Installed JREs`;
* `Add`;
* Choose `Standard VM` on list and click `Next`;
* Click `Directory...` and choose your *JRE folder*;
* Click `Finish`;
* Select the added JRE;
* Click `OK`.

#### Support

1. `Help` > `Install New Software...`;
* Enter the following URL into the `Work with` field:  
	`http://build.eclipse.org/eclipse/builds/4P/siteDir/updates/4.3-P-builds`
* Press *Enter*;
* Select category `Eclipse Java 8 Support (BETA)`;
* Click `Next`;
* Click `Next`;
* Accept the licence;
* Click `Finish`;
* Restart Eclipse when asked.

Sources: http://wiki.eclipse.org/JDT/Eclipse_Java_8_Support_%28BETA%29.

#### Compilation

1. Properties window of project:  
	1. `Package Explorer` view, *right click* project > `Properties`;
	* or
		1. `Package Explorer` view, select the project;
		* `Project` > `Properties`.
* `Java Compiler`;
* Enable `Enable project specific settings`;
* At `Compiler compliance level` key, choose `1.8 (BETA)` in combo;
* Click `OK`.
