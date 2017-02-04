============
HOW IT WORKS
============

This is a collection of files representing an example of how to setup a webpage where user input can invoke a java method on the server side.

Method calls are achieved the following way:

1. Javascript function is invoked in browser.
2. Javascript function invokes a $.ajax (or similar) method to call a specific ".jsp" file.
3. Called ".jsp" file is executed. It runs any Java code specified within. Can use built-in and custom-made Java classes.
4.It may return information which can be used to adjust the page (adding new HTML, etc.), specified in the $.ajax call's "success" attribute and page is updated if necessary.

===================
CUSTOM JAVA CLASSES
===================

Put all compiled java ".class" files inside the "classes" folder. Then, you can import them with no problem in any ".jsp" file. Use your brand new class with ease just like you would in normal Java!