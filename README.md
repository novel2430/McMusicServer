# Minecraft Music Data Transfer Server
## Introduction
本程序为一个简单的Spring服务器程序，为**Minecraft游戏端**以及**python数据生成端**的数据传输提供一个解决方案。  
> Minecraft游戏端的数据获取mod，请见[McMusic](https://github.com/novel2430/McMusic)

本程序数据传输流程概念如下图所示，简单来说，就是建立一个数据转发的中间程序，彻底将大模型或OCR流程与游戏进行机器解绑。
![image](https://github.com/novel2430/MyImage/blob/main/MCMUSC-SERVER-001.png?raw=true)
## Compile
### Requirement
- JDK >= 17
- maven
### Command
```sh
mvn clean package
# Ouput Jar Package will in './target'
```
## Feature
### One to One
`< xxx >` 表示为玩家名称
![image](https://github.com/novel2430/MyImage/blob/main/MCMUSC-SERVER-002.png?raw=true)
### One to Many
![image](https://github.com/novel2430/MyImage/blob/main/MCMUSC-SERVER-003.png?raw=true)
## Using
程序辨识数据的方式使用了玩家名称`PlayerName`，数据自Minecraft游戏端发送至服务器时，会同时发送当前数据所属的玩家名称，同样接收数据端向服务器请求数据时，也需要告知所需数据属于的玩家名称。  
所以不管是从哪端建立与服务器的连接，最需要注意的就是**玩家名称**为何。
### Python Side
python端通常为数据接收端，而本程序数据接收端与服务器连接的协议采用了Websocket协议，方便实现当新的数据抵达服务器时，服务器主动发送数据给接收端。  
Webosocket的url可见Api章节，为了不增加使用者自己实现python的Websocket客户端的麻烦，本程序也提供了相应模块实现了与服务器建立连接，同时提供相应函数令使用者调用已接收之数据。
> python 模块文件 [GetData.py](https://github.com/novel2430/McMusicServer/blob/main/python/DataGet.py)
#### GetData Module
##### Requirement
```sh
pip install websocket-client
```
##### Use
```python
import DataGet
dataGet = DataGet.DataGet()
```
##### Example
本程序同样提供了使用模块的简单例子，模拟了`获取到数据 -> OCR处里 -> 音乐生成`的整体过程，细节在例子程序中注释。  
[example.py](https://github.com/novel2430/McMusicServer/blob/main/python/example.py)
##### GetData Class Public Function
- setServerHost  
设置当前需要建立连接之服务器地址，预设是我目前已部属的服务器地址`ws://m7.ctymc.cn:21443`，注意以下两点：
    - 若调用此函数时，未关闭调用前已创建的连接，服务器新地址生效要等到旧连接关闭，重新建立连接之时
    - 传入的服务器地址格式为`ws://<host>:<port>`或是使用wss协议的`wss://<host>:<port>`
```python
def setServerHost(host: str) -> None:
    # host : 服务器地址
    # return value : bool, 当前连接是否关闭
```
- buildConnect  
此函数用来建立与服务器的Websocket连接，注意：若当前已**建立了连接且并未断开**，调用此函数将直接return
```python
def buildConnect(playerName: str) -> None:
    # playerName : 需要获取哪个玩家的数据
    # return value : None
```
- closeConnect  
此函数用来断开与服务器的Websocket连接，注意：若当前**未建立连接**，调用此函数将直接return
```python
def closeConnect() -> None:
    # return value : None
```
- getState  
此函数用来获取当前的数据状态，区分为可用与不可用，不可用状态的数据就不是游戏数据了，通常是一些服务器发送过来的提示信息。
```python
def getState() -> bool:
    # return value : bool, 表示当前数据是否为可用状态
```
- getDataDictDeep  
此函数用来获取当前的数据，以字典的形式回传，注意：此函数为**深拷贝**了目前类中储存的数据值，并非引用。  
建议在调用此函数之前，先对数据状态`getState()`进行检查，若当前状态为不可用，回传值为空字典。
```python
def getDataDictDeep() -> dict:
    # return value : dict, 当前数据
```
- getDataString  
此函数用来获取当前接收到的消息，如果当前状态为不可用的话，此函数返回的便是服务器发送来的提示消息。  
若当前状态为可用，此函数返回的是当前数据的字符串形式。
```python
def getDataString() -> str:
    # return value : str, 当前接收到的消息
```
- isWebsocketClose  
检查当前连接是否关闭。
```python
def isWebsocketClose() -> bool:
    # return value : bool, 当前连接是否关闭
```
### Minecraft Mod Side
Minecraft端为数据发送端，搭配可获取数据以及发送数据的模组之整体项目为[McMusic](https://github.com/novel2430/McMusic)。  
> 注：当前McMusic项目的main分支已同步了与服务器连接功能，旧版项目在backup分支

本程序限制了**相同玩家名**的数据发送端，只能有**一个**发送端能发送数据，所以尽量不要重名。

设置当前Minecraft数据发送端玩家名称的方式也很简单，更改McMusic项目中配置文件`run/outputDataConfig.json`。  
同样，更改要上传数据的服务器地址也是在配置文件中更改，格式为`server:port`
```
// run/outputDataConfig.json 
// *Default
{
  "PauseSecond": 5.0, // how long between each output
  "SavePath": ".", // where to save ouput files
                   // e.g. /home/novel2430
                   // --> DO NOT put "/" at the path end! <--
                   // --> path "." means directory `run` <--
  "Debug": true, // need or not to print thread log
  "Calculate": true, // need or not to do calculation
  "URL": "m7.ctymc.cn:21443", // Data Transfer Server Address
  "PlayerName": "novel" // Player Name
}
```
### Others
本程序对于数据的上传采用的是HTTP协议，而数据的获取则是Websocket协议，所以不管是发送端还是获取端，只要能建立相应协议的连接，都能与服务器进行通信。  
而程序的Api接口，则详见Api章节。
## Output
![image](https://github.com/novel2430/MyImage/blob/main/MCMUSC-SERVER-005.png?raw=true)
