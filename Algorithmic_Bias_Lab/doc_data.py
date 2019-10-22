import numpy as np
import matplotlib.pyplot as plt
from datascience import *
import random

def gen_table():
    
    #Create initials column
    def create_initials():
        initials = ''
        letters = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']
        for i in range(2):
            initials += random.choice(letters)
        return initials.upper()
    doc_table = Table().with_column("Initials", np.array([]))
    for i in range(1000):
        doc_table = doc_table.with_row([create_initials()])
        
    #Create average rating
    doc_table = doc_table.with_column("Satisfaction", doc_table.apply(lambda x: random.randint(0,5)))
    
    #Create num ratings
    def gen_num_ratings(rating):
        if rating == 0:
            return 0
        return random.randint(1, 5000)
    doc_table = doc_table.with_column("Num Ratings", doc_table.apply(gen_num_ratings, "Satisfaction"))
    
    #Create specialty
    specialties = ["internal medicine", "gastro", "cardiology", "surgery", "nutrition", "sports medicine", "pediatrics", "hematology", "radiology", "OBGYN", "urology", "ENT", "neurology"]
    doc_table = doc_table.with_column("Specialty", doc_table.apply(lambda x: random.choice(specialties), "Initials"))
    
    #Create Years Experience
    doc_table = doc_table.with_column("Experience", doc_table.apply(lambda x: random.randint(2, 35)))
    
    #Create Medicare
    def gen_medicare(num):
        if num == 0:
            return random.choices(["Yes", "No"], weights=[20, 1])[0]
        else:
            return random.choices(["Yes", "No"], weights=[1, 5])[0]
    doc_table = doc_table.with_column("Medicare?", doc_table.apply(gen_medicare, "Num Ratings"))
    
    #Create Medicaid
    def gen_medicaid(num):
        if num == 0:
            return random.choices(["Yes", "No"], weights=[20, 1])[0]
        else:
            return random.choices(["Yes", "No"], weights=[1, 10])[0]
    doc_table = doc_table.with_column("Medicaid?", doc_table.apply(gen_medicaid, "Num Ratings"))
    
    #Create Location
    locations = ["Berkeley", "San Francisco", "Oakland", "Richmond", "Walnut Creek", "Alameda", "Albany", "El Cerrito", "El Sobrante", "San Pablo", "Benicia", "Vallejo", "Mill Valley", "San Jose", "Fremont"]
    doc_table = doc_table.with_column("Location", doc_table.apply(lambda x: random.choice(locations), "Initials"))
    
    #Create Bus
    def gen_bus(num):
        if num <= 5:
            return random.choices(["Yes", "No"], weights=[20, 1])[0]
        elif num <= 10:
            return random.choices(["Yes", "No"], weights=[10, 1])[0]
        elif num <= 15:
            return random.choice(["Yes", "No"])
        elif num <= 20:
            return random.choices(["Yes", "No"], weights=[1, 2])[0]
        elif num <= 25:
            return random.choices(["Yes", "No"], weights=[1, 10])[0]
        else:
            return random.choices(["Yes", "No"], weights=[1, 20])[0]
    doc_table = doc_table.with_column("Bus?", doc_table.apply(gen_bus, "Experience"))
    
    #Create Spanish
    def gen_spanish(sat):
        if sat == 0:
            return random.choices(["Yes", "No"], weights=[20, 1])[0]
        elif sat <= 2:
            return random.choices(["Yes", "No"], weights=[10, 1])[0]
        else:
            return random.choices(["Yes", "No"], weights=[1, 10])[0]
    doc_table = doc_table.with_column("Spanish?", doc_table.apply(gen_spanish, "Satisfaction"))
    
    #Create School
    schools = ["UCLA", "UCSF", "Stanford", "Johns Hopkins", "University of Colorado", "George Washington", "Howard", "Mercer", "Loyola", "Rush", "Indiana", "Louisville", "Maryland","Boston University", "Tufts", "Harvard"]
    doc_table = doc_table.with_column("Med School", doc_table.apply(lambda x: random.choice(schools), "Initials"))
    
    #Create Awards
    doc_table = doc_table.with_column("Num Awards", doc_table.apply(lambda x: random.randint(0, 10)))
    
    #Create Gender
    def gen_gender(num):
        if num < 2:
            return random.choices(["Male", "Female", "Nonconforming"], weights=[1, 10, 3])[0]
        if num < 5:
            return random.choices(["Male", "Female", "Nonconforming"], weights=[5, 3, 2])[0]
        else:
            return random.choices(["Male", "Female", "Nonconforming"], weights=[10, 2, 1])[0]
    doc_table = doc_table.with_column("Gender", doc_table.apply(gen_gender, "Num Awards"))
    
    return doc_table