import time
import random
from typing import TextIO
from PolyphaseSort import *

if __name__ == "__main__":
    #генерація файлу, який потрібно відсортувати
    with open("source.txt", "w") as file:
        for i in range(0, 100000):
            file.write(str(random.randint(1, 10000)) + " ")
            if i%100000==0:
                print(i)
    
    #сортування
    start_time = time.time()
    print("source.txt: ")
    PolyphaseSort.initial_distribution("source.txt", "0.txt", "1.txt")
    if PolyphaseSort.optimize_to_fibonacci():
        PolyphaseSort.merge("0.txt", "1.txt", "2.txt")
    print("Sorting ended")
    end_time = time.time()
    print("Total time: " + str(end_time - start_time) + " seconds")
    with open("time.txt", "w") as a:
        a.write("Total time: " + str(end_time - start_time) + " seconds")