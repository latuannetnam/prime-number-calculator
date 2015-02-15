# Scalable prime number calculator based on Akka

How to build
------------
- git clone https://github.com/latuannetnam/prime-number-calculator.git
- mvn package
- The runnable file is target/prime-number-calculator-1.0.jar

General usage
-------------
* -f N: first number to calculate
* -l N: last number to calculate
* -m run_mode: Running mode. There a 4 choice: s (single threaded); t (multi-threaded); m (master node in cluster); w (worker node in cluster)
* -i IP-address: IP address of master node in cluster mode
* -p N: master port in cluster mode
* -h: usage help

Single threaded mode example
--------------------
* Calculate prime number from 1-1000000
java -jar target/prime-number-calculator-1.0.jar -f 1 -l 1000000

Mult-threaded mode example
--------------------
* Calculate prime number from 1-1000000
     java -jar target/prime-number-calculator-1.0.jar -m t -f 1 -l 1000000

Cluster mode example (in localhost)
--------------------
* Calculate prime number from 1-1000000
* Master node:
     java -jar target/prime-number-calculator-1.0.jar -m m -f 1 -l 1000000
* Worker node:
     java -jar target/prime-number-calculator-1.0.jar -m w

Cluster mode example (in multiple hosts)
--------------------
* Calculate prime number from 1-1000000. Master IP address = 192.168.0.100, master port = 5000
* Master node:
     java -jar target/prime-number-calculator-1.0.jar -m m -i 192.168.0.100 -p 5000 -f 1 -l 1000000
* Worker node:
     java -jar target/prime-number-calculator-1.0.jar -m w -i 192.168.0.100 -p 5000





 
