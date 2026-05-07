# III.	Exercise programs on Python Script.
# c)	Implement Python script to print factorial of a number.

numbers = [-10, 0, 3, 5, 7]  # you can change or add more numbers here

for num in numbers:
    factorial = 1
    if num < 0:
        print(f"Factorial does not exist for negative number {num}.")
    elif num == 0:
        print("Factorial of 0 is 1.")
    else:
        for i in range(1, num + 1):
            factorial *= i
        print(f"The factorial of {num} is {factorial}.")

