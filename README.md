# The Assignment

"Could you please get the candidate to write the code for parking app :

When the car comes in, check if there is a space and allocate space if available, otherwise give message saying full.
Let us assume the car park size is 100.
When the car leaves, calculate the time spent and charge £2 per hour.
We don’t need the payment module but just returning the amount is enough.
Handle multiple cars coming at the same time.

Wherever the candidate can think of multiple options, they can pick any one and implement but should be able to justify why they picked that option.
I will expect the code to be clean similar to production code.
The candidate can refer to google but let us limit the time to either 2 or 3 hours."

# My assumptions

I imagined this as the business logic for a car park with cameras to read a vehicles number plate on entry and exit.
The system therefore isn't concerned with the specific space a vehicle stays in.
It holds an entry time for each vehicle then on exit calculates a fee based on the elapsed time.
The hours stayed are rounded up (1 hour and 1 minute counts as 2 hours) because this is how most car parks I've been to seem to operate.
In addition to checking a car entering won't put it over capacity I also implemented checks that the same vehicles isn't entering multiple
times without leaving, or attempting to leave without having entered. I'm not sure either would occur in the real world but I thought it best
handle them just in case.
