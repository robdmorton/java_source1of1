
-on server side:
	-put myJavaServerDebug.bat in c:\tmp
	-in a DOS-PROMPT, cd c:\tmp
		-run myJavaServerDebug.bat
		-note that port 1044 is already bound to some other PID
	-for wireshark pcaps:
		wireshark filter:
		tcp.seq == 1234

-on client side:
	-put myJavaClientDebug.bat in c:\tmp\socketTest
	-put BartClient.class in c:\tmp\socketTest\sockets
	-then run myJavaClientDebug.bat

-N.B.
	-classpath has to point to folder where package reference starts
		-e.g. the sockets package refers to the sockets folder where BartServer.class resides
		-in Windows:
			D:\myprogram\
				  |
				  ---> org\  
						|
						---> mypackage\
								 |
								 ---> HelloWorld.class       
								 ---> SupportClass.class   
								 ---> UtilClass.class
			java -classpath D:\myprogram org.mypackage.HelloWorld	
		-in Linux/Unix: 
			/home/user/myprogram/
				  |
				  ---> org/  
						|
						---> mypackage/
								 |
								 ---> HelloWorld.class       
								 ---> SupportClass.class   
								 ---> UtilClass.class  
			java -classpath /home/user/myprogram org.mypackage.HelloWorld 
	-classpath for a jar file:
		-D:\myprogram\
			  |
			  ---> lib\
					|
					---> supportLib.jar
			  |
			  ---> org\
					|
					--> mypackage\
							   |
							   ---> HelloWorld.class
							   ---> SupportClass.class
							   ---> UtilClass.class

		We should use the following command-line option:
		java -classpath D:\myprogram;D:\myprogram\lib\supportLib.jar org.mypackage.HelloWorld
