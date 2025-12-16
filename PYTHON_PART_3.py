"""
Question:

Define a function which can generate a list where 
the values are square of numbers between 1 and 20 (both included). 
Then the function needs to print all values except the first 5 elements in the list.
    
 sqrt(1),sqrt(2)   
 
    12345?
    lis_t= []
    n>=1
    n<=20
    def generate_lsit_of_sqrt():
"""
    
def generate_list_of_sqrt():
    
    sqrt_list=[]
    
    for i in range(1,20):
        
        sqrt_list.append((i)**0.5)
        
    print(sqrt_list[5:])
    
    return sqrt_list

def tuple_of_one_to_twenty():
    
    tup1 = tuple(i for i in range(1,20))
    tup2 = tuple(i**2 for i in range(1,20))   
    
    #tup_zip = {(t1,t2) for t1,t2 in zip(tup1,tup2)}
    #tup_zip = {t1:t2 for t1,t2 in zip(tup1,tup2)}
        
    #print(tup_zip)
    print()

def dictionary_is_good(x,y):
    
    dictio = {x:x**2 for x in range(1,100,2)}

    x = (lambda x,y: x+y)

    print(dictio)


import random as r

def number_guess():
    
    
    
    
    mult="""
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠛⡛⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⢁⣴⣿⣿⣇⠈⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⢸⣿⣿⣿⣿⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠁⢸⢻⣿⣿⣿⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⣸⣭⣿⣿⣿⠀⠈⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⣿⣿⣿⣿⣿⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⣿⣿⣿⣿⣿⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⣿⣿⣿⣿⣿⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⣿⣿⣿⣿⣿⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠟⠛⡛⢿⡇⠀⣿⣿⣿⣿⣗⠀⠀⣿⡽⠟⠻⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⡿⢁⣤⣶⣷⣏⠀⠙⠀⣿⣿⣿⣿⡿⠀⠀⠈⢠⣤⣧⣴⡂⠙⣿⣿⡿⠿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⠃⣼⣿⣿⣿⣯⠀⠀⡆⣿⣿⣿⣿⡿⠀⠀⢸⣿⣿⣿⣿⣿⠀⠸⢉⣠⣦⣤⡉⢿⣿⣿
⣿⣿⣿⣿⣿⣿⡇⢀⣿⣿⣿⣿⣿⠀⢘⢴⣿⣿⣿⣿⣿⠀⠀⢸⣿⣿⣿⣿⣿⠀⠀⣼⣿⣿⣿⣧⠈⣿⣿
⣿⣿⣿⣿⣿⣿⠀⣸⣿⣿⣿⣿⡷⠀⢈⣼⣿⣿⣿⣿⣿⠀⠀⢸⣿⣿⣿⣿⣿⡃⠀⣿⣿⣿⣿⣿⡀⢹⣿
⣿⣿⣿⣿⠟⠃⡀⣿⣿⣿⣿⣿⠇⠀⢸⣿⣿⣿⣿⣿⣿⠀⠀⢸⣿⣿⣿⣿⣿⡇⠀⣿⣿⣿⣿⣿⠇⠈⣿
⣿⡿⢋⡄⠀⠸⢀⣿⣿⣿⣿⣿⣷⣤⣾⣿⣿⣿⣿⣿⣿⣷⣤⣾⣿⣿⣿⣿⣿⣧⣤⣾⣿⣿⣿⣿⡇⠀⣿
⡟⢠⢫⠄⠀⠘⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⢸
⡇⢲⢬⢇⠶⡡⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠸
⣧⢘⢮⣫⠽⡁⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀
⣿⠈⣞⡲⣛⡄⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀
⣿⡀⢧⢫⡵⢺⡤⣉⡛⣋⠥⡴⣤⣉⠛⢿⣿⣿⡿⠿⢛⡛⣛⠛⠿⣿⣿⣿⣿⠿⠛⡛⠛⠿⠿⠿⢟⠀⠀
⣿⣇⢩⡳⢭⡳⢭⣳⢹⡜⣭⢳⠲⣍⢾⡰⢆⡶⢲⡭⢳⣙⢮⡹⢖⡴⡬⢥⡲⣬⢳⡙⢯⡖⣖⠮⡍⠀⢸
⣿⣿⣄⢫⢳⢭⡳⢎⡗⢮⡕⣫⠝⣎⠶⡹⣍⠾⣱⢞⡱⡭⣖⣝⠺⣜⡱⢧⢳⡱⢏⡼⣓⡞⡭⠞⠀⢠⣿
⣿⣿⣿⣦⠉⡞⣵⢫⡞⣵⣭⢳⢹⣬⢳⡝⣬⢳⣭⡎⢳⡝⢲⡜⢻⡌⡟⣬⢳⣭⠛⣴⢫⣼⠙⠀⠀⣾⣿
⣿⣿⣿⣿⣷⣌⠱⣎⠷⣱⠭⣞⢣⡎⢷⡱⣋⢶⢣⡛⡶⣙⣎⢳⢣⣛⡵⣋⢶⡹⣚⡵⢫⠖⠁⠀⣼⣿⣿
⣿⣿⣿⣿⣿⣿⣷⣌⠙⢧⡻⢬⢇⢯⣓⠧⣏⢮⠳⣭⠳⣭⡚⡵⣋⢶⢳⡹⢎⡵⢫⡼⠍⠀⢀⣾⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣷⣌⢱⢫⣎⡳⣜⡳⢎⣧⠻⣴⢋⡶⣍⡳⢭⢞⣣⡝⣎⡳⢏⡜⠀⣠⣾⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⡗⣮⢕⡧⢫⡳⣜⢳⡎⡝⣖⣣⢛⡮⡝⣦⢛⡴⣫⣝⠀⠀⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡆⡝⡶⣩⢞⢧⡳⢎⣗⡺⣙⠶⣩⠷⡜⣝⠲⣍⡞⡵⢮⠀⠀⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣇⠸⢳⣙⠮⣇⢯⣝⡲⡝⣭⢫⡳⢭⣛⡼⣛⣬⢳⡭⠓⠀⢀⣸⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣶⣶⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣴⣶⣶⣶⣶⣿⣿⣿⣿⣿⣿⣿⣿
    
    """

    
    print("welcome to the number game\n")
    
    print(f"{r.randint(1,10001)} this is a random number from (1 to 10k)\nthe next one should be guessed by you!")
    
    rand = r.randint(1,10001)
    print(f"here : {rand}")
    print("Good luck,")
    
    while True:
        
        user_number = int(input("pick a number: "))
               
        if(user_number < rand):

            print("too low\n")
            print("HINT:\n")
            
            if(user_number*4 < rand):
                print("your pick is way too low")                  
                continue
            
            elif(user_number*2 < rand):
                print("good pick but still too low")          
                continue
           
            else:
                print("you are close")    
               
        if(user_number > rand):

            print("too high\n")
            print("HINT:\n")
        
            if(user_number*1/4 > rand):
                
                print("your pick is way higher than my pick")            
                continue
            
            elif(user_number*1/2 > rand):
            
                print("good pick but still too high")
                continue
            
            else:
                print("you are close")

        if(user_number == rand):
            
            print("YOU DID IT HERE'S YOUR REWARD\n")
            print(f"{mult}")   
""" 
Question:

Please write a program to generate a list with 5 random numbers between 100 and 200 inclusive.    
"""
import random as r
def rand_list_of_nums_between_100_200():

    print("_________________________________________________________")
    arr = [r.randint(100,201) for x in range(0,5)]
    
    
    f = (lambda x,y: x+y)
    print(f(1,2))
    print(arr)


"""
Question:

Write a program to solve a classic ancient Chinese puzzle: 
We count 35 heads and 94 legs among the chickens and rabbits in a farm. How many rabbits and how many chickens do we have?

35 heads
94 legs

(chickens and rabbits)total
"""
def chicken_rabbit():
    print("2x + 4y = 94\n")
    print("x + y = 35\n")
     
     
    print("x = -y + 35\n")   
    print("\t(...)\n")
     
    print("so we got 12 rabbits\n")
    print("23 chickens\n")


    

    


#-------------------------------------------------------------------------------------------------------------------------------------
def main():    
    
    print("hi")
    #generate_list_of_sqrt()
    #tuple_of_one_to_twenty()
    #dictionary_is_good()
    #rand_list_of_nums_between_100_200()
    #chicken_rabbit()
    number_guess()
    
if __name__ == "__main__":
     
    main()   