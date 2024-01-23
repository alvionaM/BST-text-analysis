<div align="center">
<a title="Y.samadzadeh, CC BY-SA 4.0 &lt;https://creativecommons.org/licenses/by-sa/4.0&gt;, via Wikimedia Commons" href="https://commons.wikimedia.org/wiki/File:Binary-search-tree-insertion-animation.gif"><img width="256" alt="Binary-search-tree-insertion-animation" src="https://upload.wikimedia.org/wikipedia/commons/8/83/Binary-search-tree-insertion-animation.gif"></a>
</div>

## Description📌
<p align="justify">The main function of the Word Counter is to be able to "load" an English text file and count how many times each word appears. For example, let's look at the following text:</p>

<i>\- Hello, how are you?<br>
\- Very well, thank you. I am studying for the exams. How about you?<br>
\- Fine, thank you. How many exams will you have?<br>
\- Too many…</i>

<p align="justify">The word "you" appears 5 times, the word "how" 3 times, the words "thank", "many", and "exams" appear 2 times each, while the words "hello", "are", "very", "well", "I", "am", "studying", "about", "fine", "will", and "have" appear once each. The Word Counter does not take into account punctuation marks, such as , . ? ; ! - :, as well as parentheses, brackets, symbols of operations, or any other character that is not a letter of the English alphabet. Only a single apostrophe inside a word is allowed, e.g., "don't" will be considered as one word. Additionally, all strings containing numbers are completely ignored, e.g., "17:25" or "1980’s".</p>

<p align="justify">The library allows the user to define <b>special words (stop words)</b> that they want to be completely ignored. For example, in relevant applications, articles like "a", "an", and "the" are typically ignored. Also the program is case-insensitive. For example, the words "Hello" and "hello" are considered the same.<p>

<p align="justify">The Word Counter is implemented as a Binary Search Tree (BST) and supports the following operations:</p>

|Supported operations✅|
|---|
|void **load**(String filename)|
|WordFreq **search**(String w)|
|void **insert**(String w)|
|void **remove**(String w)|
|void **addStopWord**(String w)|
|void **removeStopWord**(String w)|
|int **getTotalWords**()|
|int **getDistinctWords**()|
|int **getFrequency**(String w)|
|double **getMeanFrequency**()|
|WordFreq **getMaximumFrequency**()|
|void **printTreeAlphabetically**(PrintStream stream)|
|void **printTreeByFrequency**(PrintStream stream)|

<a href="#implementation-details📜"><i>(see the Implementation Details section for more)</i></a>

## Run 💻
#### Compile and run the demo: 
1. ```javac *.java```
2. ```java Demo AChristmasCarol.txt```

#### Compile and run for your own text file:
You can write your own driver class that utilizes any of the supported operations you want and use a text file of your choice. To do so, include YourClass.java file in the src folder locally, and then:
1. ```javac *.java```
2. ```java YourClass yourText.txt```



## Implementation Details📜

### ● Code Structure
- **WordCounter** interface: It defines all the available operations supported by a Word Counter in the form of abstract methods *(see the following part for more on these methods)*.
- **BST** class: The class for the symbol table which is implemented as a Binary Search Tree (BST). It implements the WordCounter interface.
- **WordFreq** class: For each word in the text, there is an object of type WordFreq associated with it. This class contains both the word itself (private String) and the number of occurrences (private int) as fields.
- **LinkedList** class: A class that supports constructing a linked list and performing operations on it. It is used to host the stopwords.
- **Sort** class: It contains the definition and implementation of the Heapsort algorithm, used to print words by frequency (as the symbol table is structured according to the alphabetical order, not according to each word's frequency).
- **Demo** class: The class that contains the main() method and demonstrates the Word Counter operations on the text given as argument.



### ● WordCounter interface methods and their implementation in BST class
```java 
void load(String filename)
```
- <p align="justify">Starting from the current BST (which can be empty), it reads the file with the name 'filename', containing English text, and updates the tree with the words read so that the final BST is constructed. </p>
- Time Complexity: $\mathcal{O}(n^2)$

```java 
WordFreq search(String w) 
```
- <p align="justify">It searches the tree for the existence of the word 'w' (returns null if it doesn't exist). When it finds the word 'w' in the tree, if the frequency of 'w' is greater than the mean frequency (from 'getMeanFrequency()'), it uses rotations to bring this word to the root of the tree. This is done by calling 'remove' to remove this node from the Binary Search Tree (BST) and then insert it at the root. The rationale is that words with high frequency are words for which many searches may be performed, so we want to have them as close to the top of the tree as possible.</p>
- Time Complexity: $\mathcal{O}(n)$

```java 
void insert(String w)
``` 
- <p align="justify">It finds if a node with the key 'w' already exists in the tree. If it does, it increments its frequency by 1. If not, it inserts a new node into the tree (using simple insertion as a leaf) with the key 'w' and a frequency of 1.</p>
- Time Complexity: $\mathcal{O}(n)$

```java 
void remove(String w)
``` 
- <p align="justify">This method removes the node with the key 'w' (if such a node exists).</p>
- Time Complexity: $\mathcal{O}(n)$

``` java 
void addStopWord(String w)
``` 
- <p align="justify">Adds the word 'w' to the stopWords list.</p>
- Time Complexity: $\mathcal{O}(1)$

``` java 
void removeStopWord(String w)
```
- <p align="justify">Removes the word 'w' from the stopWords list.</p>
- Time Complexity: $\mathcal{O}(m)$

``` java 
int getTotalWords()
```
- <p align="justify">Returns the total number of words in the text that has been loaded into the BST, taking into account the frequency of each word (i.e., after ignoring stopwords, numbers, etc.).</p>
- Time Complexity: $\mathcal{O}(1)$

``` java 
int getDistinctWords()
```
- <p align="justify">Returns the number of distinct words in the tree.</p>
- Time Complexity: $\mathcal{O}(1)$

``` java 
int getFrequency(String w)
```
- <p align="justify">Returns the number of occurrences of the word 'w' (returns 0 if the word does not exist in the tree).</p>
- Time Complexity: $\mathcal{O}(n)$

``` java 
double getMeanFrequency()
```
- <p align="justify">Calculates and returns the mean frequency. The mean is generated from the frequencies of all different words in the text.</p>
- Time Complexity: $\mathcal{O}(1)$

``` java 
WordFreq getMaximumFrequency()
```
- <p align="justify">Returns a WordFreq object containing the word with the most occurrences.</p>
- Time Complexity: $\mathcal{O}(n)$

``` java 
void printTreeAlphabetically(PrintStream stream)
```
- <p align="justify">Prints the words in the tree, along with the number of occurrences of each word, in alphabetical order. This should be implemented using inorder traversal.</p>
- Time Complexity: $\mathcal{O}(n)$

``` java 
void printTreeByFrequency(PrintStream stream)
```
- <p align="justify">Prints the words and their occurrence counts in ascending order by frequency.</p>
- Time Complexity: $\mathcal{O}(n\log{}n)$

## Contributors
- Alviona Mancho [<a href="https://github.com/alvionaM">alvionaM</a>]
- Christos Patrinopoulos [<a href="https://github.com/techristosP">techristosP</a>]

