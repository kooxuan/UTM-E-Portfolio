# II.	Exercise programs on operators & I/O operations.
# c)	Implement python script to read person’s age from keyboard and display whether he is eligible for voting or not.

age = int(input("Enter your age: "))

if age >= 18:
    print("You are eligible to vote.")
else:
    print("You are not eligible to vote yet.")
