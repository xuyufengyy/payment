***************README**************
1.此支付项目使用spring boot开发.
2.修改application.properties(修改数据库配置,修改相关支付的相关配置).
3.修改logback.xml(修改日志生成路径).
4.修改SignVerProp.properties(修改证书路径,配置自己的证书),此证书为联动优势支付证书.
5.正式支付环境需要将TestController注释掉.
6.项目启动,测试地址为localhost:8083/test/index
7.此项目采用3Des加密,请求参数加密,支付异步回调重定向到接口端的请求参数加密.加密key写在配置文件(使用中key应写在数据库,这样接口,服务端<统一支付>加密解密才能一致).
