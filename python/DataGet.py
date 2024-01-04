#!/usr/bin/python3

## websocket-client
import websocket
import json
import threading
import copy

class DataGet(object):
    def __init__(self) -> None:
        self.__host = "ws://m7.ctymc.cn:21443"
        self.__url: str = "/get/new-data/"
        self.__ws: websocket.WebSocketApp
        self.__isConnect: bool = False
        self.__msgState: bool = False
        self.__msgContent: str = "No Data"
        self.__msgDict: dict = {}
        self.__wsThread: threading.Thread = threading.Thread()

    def __ws_on_message(self, ws, message) -> None:
        # print(message)
        msgDict = json.loads(message) 
        if msgDict.get("state") == 1:
            self.__msgState = True
            self.__msgDict = msgDict.get("data")
        elif msgDict.get("state") == 0:
            self.__msgState = False
        self.__msgContent = str( msgDict.get("data") )

    def __ws_stop_socket(self):
        self.__isConnect = False
        self.__msgState = False
        self.__msgContent = "No Data"
        self.__msgDict = {}

    def __ws_on_close(self, ws, close_status_code, close_msg) -> None:
        self.__ws_stop_socket()
        # print("=== websocket close ===")

    def __ws_on_open(self, ws) -> None:
        self.__isConnect = True
        # print("=== websocket open ===")

    def __ws_on_error(self, ws, error) -> None:
        print(error)
        self.__ws_stop_socket()
        print("=== websocket error ===")

    def buildConnect(self, playerName: str) -> bool:
        if self.__isConnect == False and self.__wsThread.is_alive()==False:
            url = self.__host + self.__url + playerName
            self.__ws = websocket.WebSocketApp(url,
                                               on_error=self.__ws_on_error,
                                               on_open=self.__ws_on_open,
                                               on_message=self.__ws_on_message,
                                               on_close=self.__ws_on_close)
            self.__wsThread =  threading.Thread(target=self.__ws.run_forever)
            self.__wsThread.start()
            return True
        return False

    def closeConnect(self) -> None:
        if self.__isConnect == True or self.__wsThread.is_alive():
            self.__ws_stop_socket()
            self.__ws.close()

    def getState(self) -> bool:
        return self.__msgState

    def getDataString(self) -> str:
        return self.__msgContent

    def getDataDictDeep(self) -> dict:
        return copy.deepcopy(self.__msgDict)

    def isWebsocketClose(self) -> bool:
        if self.__wsThread.is_alive() == True:
            return False
        return True


