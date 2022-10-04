# JB_PhoneBook
JetBrains PhoneBook Project

Description
You have to iterate over each element of the number list every time you want to find someone's number. This is the only way to search if your list contains unordered data. Any number can be anywhere on the list, so you have to check every element.

Now, let's try sorting the contents of the phone list and performing a different kind of search. Let's see if this search algorithm is more efficient.

Objectives
In this stage, you should sort the list of numbers alphabetically by the owner’s name. Sort the list using the bubble sort algorithm and search in the list using the jump search algorithm.

After sorting, search for 500 phone numbers using the list from the previous stage. Note how long it takes to sort the list, and also measure the time the program spends searching. Don't include the sorting time in the searching time, because the list of numbers stays sorted after every search request. If you want to save the sorted list into the file so you don’t have to sort it again, do not override the file that contains the unsorted list of phone numbers. This file will also be required in the next stage.

If the sorting process takes too long (more than 10 times longer than all 500 iterations of the linear search), you should stop sorting and use the linear search.

Output both approaches one after another and see which one is faster.

Look at the examples to see what your output should look like.

Example
The output example is shown below. Note that you can get totally different sorting and searching times!

Example 1:

Start searching (linear search)...
Found 500 / 500 entries. Time taken: 1 min. 56 sec. 328 ms.

Start searching (bubble sort + jump search)...
Found 500 / 500 entries. Time taken: 9 min. 15 sec. 291 ms.
Sorting time: 8 min. 45 sec. 251 ms.
Searching time: 0 min. 30 sec. 40 ms.
Example 2:

Start searching (linear search)...
Found 500 / 500 entries. Time taken: 2 min. 01 sec. 134 ms.

Start searching (bubble sort + jump search)...
Found 500 / 500 entries. Time taken: 22 min. 14 sec. 482 ms.
Sorting time: 20 min. 12 sec. 251 ms. - STOPPED, moved to linear search
Searching time: 2 min. 02 sec. 231 ms.
