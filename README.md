# HotMovie



Udacity Nanodegree Android Developer Project-phase-3

Project Name：HotMovies

####updated on 11.29:
- 将网络访问放到了FetchDataService中，在app启动时判断如果数据库为空则启动服务。
- 在FetchDtaService的onHandleIntent方法中，基于第一次网络访问的结果（从中取得id，放进一个列表），进行后续的网络访问和数据写入（遍历列表）。
 
