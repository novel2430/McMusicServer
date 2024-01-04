#! /usr/bin/python3
import DataGet
from time import sleep

if __name__ == "__main__":
    dataGet = DataGet.DataGet() 
    dataGet.buildConnect("novel2430") # get <playerName>'s data
    dataIndex:int = -1 # ensure any data that [index > -1] will be update in first run
    times:int = 1

    while(dataGet.isWebsocketClose() == False): # loop stop when game stop
        print("===== Start {} =====".format(times))

        print("===== Wait For Right Data... =====")
        data:dict = {}
        while(dataGet.isWebsocketClose() == False):
            if dataGet.getState() == True: # wait for the data that can be used [data.getState() == True]
                data = dataGet.getDataDictDeep() # get the data in dict form
                if(dataIndex < int(data.get("Index"))): # ensure data is the newest
                    dataIndex = int(data.get("Index")) # update newest data index value
                    break
            else:
                print(dataGet.getDataString())
            sleep(2) # make loop run slower 

        print("===== Data is the newest =====")
        print(data)
        print("Do OCR..") # Do Some OCR
        sleep(2)

        print("Generate Music..") # Do Some Music Generate
        sleep(3)

        print("===== End {} =====".format(times))
        times = times + 1
