#IMPORTANT

The currently used API uses API Keys as form of authentication. These keys are private and you would need to create your own account with a private key from https://darksky.net/dev/.

Once you create an account, you can find the private key by going to your account/profile information.

Copy the key.

Next, make sure you have the file "template.properties" under 'src/resources'. Create a copy of the file and rename it to "config.properties" (if you have not done so already).

Open the file and replace the placeholder text with your API Key.

-
Make sure Git is not tracking the new "config.properties" file. It should remain private!
-

As we progress, we will need to add more content. Therefore the template file will always be present to include an up-to-date structure of the properties. All that would be needed is to replace the template information with your private information whenever necessary.
