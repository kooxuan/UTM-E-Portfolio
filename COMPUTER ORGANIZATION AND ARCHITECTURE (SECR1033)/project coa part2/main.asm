TITLE Project COA Part2
; Author:	Chin Pei Wen
;			Tan Zhao Hong
;			Koo Xuan
;			Ling Yu Qian
; Date: 4 July 2024

INCLUDE Irvine32.inc

.data
msec1 DWORD ?
msec2 DWORD ?
coef1 DWORD 18; student1 18 - 03 - 2003
coef2 DWORD 15; student2 15 - 06 - 2003
coef3 DWORD 25; student3 25 - 09 - 2004
coef4 DWORD 7; student4 07 - 12 - 2004
sum WORD ?
cputime DWORD ?
str0 BYTE "Welcome to CPU Benchmark Program", 0dh, 0ah, 0dh, 0ah, 0
str1 BYTE "Benchmark CPU time Using Equation y = 18*x^3 + 15*x^2 + 25*x +7", 0dh, 0ah
BYTE "			(with delay coef1, coef2, voef3, coef4 = 18, 15, 25, 07 msec)", 0dh, 0ah, 0
str2 BYTE "Enter Number of Looping (N) = ", 0
str3 BYTE "Value of Sum from the Stress Test (polynomial) = ", 0
str4 BYTE "CPU time Stress Test in progress... ", 0dh, 0ah, 0
str_result BYTE "Result : ", 0dh, 0ah, 0
time1 BYTE "First Capture Execution time in milisecond: ", 0
time2 BYTE "Second Capture Execution time in milisecond: ", 0
time3 BYTE "Different Execution time in milisecond: ", 0
decNum DWORD ?
promptBad BYTE "Invalid input, please enter again", 0dh, 0ah, 0
str5 BYTE "Press 'y' to continue or 'n' to exit the benchmark: ", 0
charIn BYTE ?
charY db "y"
str6 BYTE "Thank you for using this Benchmark Program... BYE!!", 0dh, 0ah, 0

.code
main PROC

startProg :

call Clrscr
call Crlf
mov edx, OFFSET str0
call WriteString; display welcome

mov edx, OFFSET str1
call WriteString; display equation

call Crlf

mov edx, OFFSET str2
call WriteString; display no._loop

read :

call ReadDec; input no._loop
jnc goodInput

mov edx, OFFSET promptBad
call WriteString
jmp read; go input again

goodInput :

mov decNum, eax; store good value

mov edx, OFFSET str4
call WriteString; display CPU in_progress

call Crlf
mov edx, OFFSET str_result
call WriteString; display result

call Crlf

call GetMseconds
mov msec1, eax; get capture_msec before loop

lea edx, time1
call WriteString
call WriteDec

call Crlf

mov BX, 1; set x = 1
mov ecx, decNum; max_loop N

LoopStress :

mov eax, coef1; delay factor coef1
call Delay
mul bx
mul bx
mul bx
add sum, ax; partial sum for coef1

mov eax, coef2; delay factor coef2
call Delay
mul bx
mul bx
add sum, ax; partial sum for coef2

mov eax, coef3; delay factor coef3
call Delay
mul bx
add sum, ax; partial sum for coef3

mov eax, coef4; delay factor coef4
call Delay
add sum, ax; partial sum for coef4

inc bx

loop LoopStress

call GetMseconds
mov msec2, eax

lea edx, time2
call WriteString
call WriteDec

call Crlf

mov eax, msec2
sub eax, msec1; calculate diff_CPUtime
mov cputime, eax

lea edx, time3
call WriteString
mov eax, cputime
call WriteDec

call Crlf
lea edx, str3
call WriteString; value sum
mov ax, sum
call WriteDec

call Crlf
call Crlf

mov edx, OFFSET str5
call WriteString; continue_or_exit

call ReadChar
mov charIn, AL

call Crlf
call Crlf

mov BL, charY
cmp BL, charIn

JE startProg

mov edx, OFFSET str6
call WriteString; thanks and bye

exit

main ENDP


END main