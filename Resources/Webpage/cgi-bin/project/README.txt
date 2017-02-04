This folder contains an IntelliJ IDEA project which can be opened inside IntelliJ IDEA IDE. It acts similar to Eclipse.

Java classes are located in the "src" and packages right after, so create new, edit and delete old ones in/from there.

All Java classes should output to "WEB-INF/classes" when compiled and the project's output path needs to be adjusted to the correct path on any new machines. To do it manually, simply compile the ".java" classes, making sure the correct packages are specified, and then mvoe the compiled ".class" files to "WEB-INF/classes".