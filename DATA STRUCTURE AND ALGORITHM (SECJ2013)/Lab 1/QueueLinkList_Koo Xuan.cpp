#include <iostream>  
using namespace std; 

// Define the structure for a queue node  
struct nodeQ {  
    // Declare variables for the node here  
    int item;
    // e.g., data type and pointer to the next node  
    nodeQ* next;
};  

// Define the Queue class  
class Queue {  
public:  
    // Declare pointers for front and back of the queue  
    // e.g., nodeQ* frontPtr;  
    //       nodeQ* backPtr;  
	nodeQ* frontPtr;  
    nodeQ* backPtr;  
    
    // Constructor to initialize the queue  
    Queue() {  
        // Initialize front and back pointers to NULL
		frontPtr = NULL;
		backPtr = NULL;  
    }  

    // Function to check if the queue is empty  
    bool isEmpty() {  
        // Return true if the queue is empty  
        return (frontPtr == NULL && backPtr == NULL);;
    }  

    // Function to enqueue an element  
    void enQueue(int value) {  
        nodeQ* newPtr = new nodeQ;  
        newPtr->item = value;       
        newPtr->next = NULL;  
		
		if (isEmpty()) {            
           // If empty, set both front and back pointers to the new node
		   frontPtr = newPtr;
		   backPtr = newPtr;  
        } else {                    
           // Link the new node to the back of the queue  
           // Update the back pointer to the new node 
		   backPtr->next = newPtr;
           backPtr = newPtr; 
        }        

    }  

    // Function to display the queue  
    void displayQueue() {  
        nodeQ* current = frontPtr;   
        while (current != NULL) {     
          // Print the item of the current node 
		  cout << current -> item<< " "; 
          // Move to the next node  
          current = current ->next;
        }  
        cout << endl;    
    }  

    // Destructor to clean up the queue  
    ~Queue() {  
        // Call a function to destroy the queue 
		destroyQueue(); 
    }  

    // Function to destroy all nodes in the queue  
    void destroyQueue() {  
        nodeQ* temp = frontPtr; 
         while (temp) {           
            // Move the front pointer to the next node  
            frontPtr = frontPtr->next;
            // Delete the current node
			delete temp;  
            // Move to the next node 
			temp = frontPtr; 
        }  
        // Set the back pointer to NULL 
        backPtr = NULL;
    }  
};  

int main() {  

    Queue myQueue;   

    myQueue.enQueue(20); 
    myQueue.enQueue(15); 
    myQueue.enQueue(26);  
    myQueue.enQueue(84); 
 
    cout << "Queue elements: ";  
    myQueue.displayQueue();  

    cout << "Is the queue empty? " << (myQueue.isEmpty() ? "Yes" : "No") << endl;  

    return 0;   
}
