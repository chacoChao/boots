# Socket   
wiki: https://blog.csdn.net/yinwenjie/column/info/sys-communication/3

`````
通道类	通道作用	可关注的事件
ServerSocketChannel	服务器端通道	SelectionKey.OP_ACCEPT
DatagramChannel	UDP协议通道	SelectionKey.OP_READ、SelectionKey.OP_WRITE
SocketChannel	TCP协议通道	SelectionKey.OP_READ、SelectionKey.OP_WRITE、SelectionKey.OP_CONNECT
`````