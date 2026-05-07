# I.	Exercise programs on basic control structures & loops.
# c)	Write a program for displaying reversal of a number.

num = 12345
reverse = 0
original = num

while num != 0:
    digit = num % 10
    reverse = reverse * 10 + digit
    num //= 10

print("Original number:", original)
print("Reversed number:", reverse)
