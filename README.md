# Hopfield-Network #

## Main Program ##
To start the main program, start and run HopfieldNetworkControl.java in command line and simply follow the prompt.
### Features of Program ###
Test Mode:
Run accuracy test and print test result to a seperate file for future use.

Word Mode:
This is for fun! Input a word and add noise to it! See what the program will recover the word to.

Custom Mode:
Train whatever pattern you want and recover a noised pattern based on the noise level you choose!
## Data ##
In the data folder, you can find two text file that stores the standard patterns for the network to train. Please make sure not to move these file around. You can add whatever patterns you want to the folder, as long as the file containing you pattern follows the guide below:

First line of the file should specify the number of nodes, width of string representation of your pattern, height of string representation of your pattern, each seperated by a tab. For each pattern, start the pattern by the abstarct value that your pattern represents on a sperate line and than print the pattern. There should be one empty line between each pattern. 

For more detailed information, please follow the file I provided in the folder.

Be aware that malformed file will cause the program to crash!
## Test ##
In the test folder, there is a test.txt file containing pre-run test that I generated when doing accuracy testing. Any file created during accuracy testing will be stored in this folder.

#### That all! Have fun playing! ####
